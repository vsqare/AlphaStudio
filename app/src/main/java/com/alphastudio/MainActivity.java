package com.alphastudio;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(Color.rgb(30, 30, 30));

        TextView title = new TextView(this);
        title.setText("AlphaStudio");
        title.setTextColor(Color.WHITE);
        title.setTextSize(22);
        title.setGravity(Gravity.CENTER_VERTICAL);
        title.setPadding(24, 20, 24, 20);

        root.addView(title);

        TextView welcome = new TextView(this);
        welcome.setText(
                "Professional Android IDE\n\n" +
                "Create • Code • Build • Run"
        );
        welcome.setTextColor(Color.LTGRAY);
        welcome.setTextSize(17);
        welcome.setPadding(24, 40, 24, 24);

        root.addView(welcome);

        setContentView(root);
    }
}
