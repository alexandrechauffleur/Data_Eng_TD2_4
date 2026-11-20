package com.example.td2app;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextClock;

/**
 * Simple clock: current time (updated every second) and today's date.
 * TextClock refreshes itself automatically, no timer needed.
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER);
        root.setBackgroundColor(Color.BLACK);

        TextClock time = new TextClock(this);
        time.setFormat24Hour("HH:mm:ss");
        time.setFormat12Hour("hh:mm:ss a");
        time.setTextSize(64);
        time.setTextColor(Color.WHITE);
        time.setGravity(Gravity.CENTER);

        TextClock date = new TextClock(this);
        date.setFormat24Hour("EEEE d MMMM yyyy");
        date.setFormat12Hour("EEEE d MMMM yyyy");
        date.setTextSize(20);
        date.setTextColor(Color.LTGRAY);
        date.setGravity(Gravity.CENTER);

        root.addView(time);
        root.addView(date);
        setContentView(root);
    }
}
