# Data_Eng_TD2_4 — Practice 4 : CI/CD Android APK

Build d'APK Android **sans environnement local** (ni Android Studio, ni SDK, ni JDK) grâce à GitHub Actions.

## Contenu

| Fichier | Rôle |
|---|---|
| `app/` | Application Android minimale : une horloge simple (heure à la seconde + date, via `TextClock`) |
| `gradlew`, `gradlew.bat`, `gradle/wrapper/` | Gradle wrapper 8.7 (jar vérifié par checksum SHA-256 officiel) |
| `build.gradle`, `settings.gradle`, `gradle.properties` | Configuration Gradle (Android Gradle Plugin 8.5.2, compileSdk 34) |
| `app/build.gradle` | Config de signature `release` lue depuis les variables d'environnement |
| `.github/workflows/android-debug.yml` | APK debug à chaque push (`main`, `develop`) / PR vers `main` |
| `.github/workflows/android-release.yml` | APK release signé sur tag `v*` ou déclenchement manuel |

## Workflow debug

Déclenché automatiquement. Étapes : checkout → JDK 17 (Temurin) → Gradle avec cache
(`cache-read-only` hors `main`) → `./gradlew assembleDebug` → upload de l'artefact `app-debug`
(conservé 7 jours). L'APK se télécharge depuis la page du run, onglet **Artifacts**.

## Workflow release (APK signé)

### 1. Générer un keystore (Codespaces / cloud shell, ou n'importe quel JDK)

```bash
keytool -genkey -v -keystore upload-keystore.jks \
  -alias my-app \
  -keyalg RSA -keysize 2048 -validity 10000
base64 -w0 upload-keystore.jks > keystore.base64.txt
```

### 2. Ajouter les secrets GitHub

**Settings → Secrets and variables → Actions → New repository secret** :

| Secret | Valeur |
|---|---|
| `KEYSTORE_BASE64` | contenu de `keystore.base64.txt` |
| `KEYSTORE_PASSWORD` | mot de passe du keystore |
| `KEY_ALIAS` | `my-app` |
| `KEY_PASSWORD` | mot de passe de la clé |

Puis supprimer `upload-keystore.jks` / `keystore.base64.txt` (ou le Codespace).
Le `.gitignore` bloque `*.jks`, `*.keystore` et `keystore.base64.txt` : le keystore n'est **jamais** commité.

### 3. Lancer un build release

```bash
git tag v1.0.0
git push origin v1.0.0
```

Le keystore est décodé dans `$RUNNER_TEMP` (détruit en fin de job), jamais uploadé ;
seul `app-release.apk` est publié comme artefact `app-release`.

## Checklist

- [x] Workflows dans `.github/workflows/`
- [x] `actions/checkout` en première étape
- [x] JDK 17 via `actions/setup-java@v4`
- [x] Cache Gradle via `gradle/actions/setup-gradle@v4`
- [x] `./gradlew assembleDebug` / `assembleRelease`
- [x] Chemins d'artefact = `app/build/outputs/apk/{debug,release}/`
- [x] Keystore uniquement en secret base64
- [x] Release déclenché par tag `v*`
- [ ] APK téléchargé et installé au moins une fois sur un appareil
