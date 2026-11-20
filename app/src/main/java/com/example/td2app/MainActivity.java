package com.example.td2app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView text = new TextView(this);
        text.setText(R.string.hello);
        text.setTextSize(22);
        text.setGravity(Gravity.CENTER);
        setContentView(text);
    }
}
