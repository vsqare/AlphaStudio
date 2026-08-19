package com.alphastudio;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {

    private final int BG = Color.rgb(18, 18, 20);
    private final int PANEL = Color.rgb(29, 29, 34);
    private final int PANEL2 = Color.rgb(38, 38, 44);
    private final int TEXT = Color.WHITE;
    private final int MUTED = Color.rgb(160, 160, 170);
    private final int ACCENT = Color.rgb(82, 120, 255);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(BG);
        getWindow().setNavigationBarColor(BG);

        showDashboard();
    }

    private void showDashboard() {

        LinearLayout root = baseLayout();

        TextView title = text("AlphaStudio", 22, TEXT);
        title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        root.addView(title, margin(20, 25, 20, 5));

        TextView sub = text(
                "Professional Android IDE",
                13,
                MUTED
        );
        root.addView(sub, margin(20, 0, 20, 25));

        Button newProject = button(
                "＋   New Project",
                ACCENT
        );

        newProject.setOnClickListener(v -> showNewProject());

        root.addView(
                newProject,
                margin(20, 0, 20, 12)
        );

        Button openProject = button(
                "↗   Open Project",
                PANEL2
        );

        openProject.setOnClickListener(v ->
                Toast.makeText(
                        this,
                        "Open Project coming next",
                        Toast.LENGTH_SHORT
                ).show()
        );

        root.addView(
                openProject,
                margin(20, 0, 20, 25)
        );

        TextView recent = text(
                "Recent Projects",
                20,
                TEXT
        );

        recent.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        root.addView(
                recent,
                margin(20, 0, 20, 12)
        );

        root.addView(
                projectCard(
                        "AlphaStudio",
                        "com.alphastudio"
                ),
                margin(20, 0, 20, 8)
        );

        root.addView(
                projectCard(
                        "My First App",
                        "com.example.myapp"
                ),
                margin(20, 0, 20, 20)
        );

        TextView environment = text(
                "Environment",
                20,
                TEXT
        );

        environment.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        root.addView(
                environment,
                margin(20, 0, 20, 12)
        );

        root.addView(
                statusCard("●  Android SDK", "Ready"),
                margin(20, 0, 20, 8)
        );

        root.addView(
                statusCard("●  Gradle", "Ready"),
                margin(20, 0, 20, 8)
        );

        root.addView(
                statusCard("●  GitHub", "Connected"),
                margin(20, 0, 20, 20)
        );

        setContentView(root);
    }

    // =========================
    // NEW PROJECT SCREEN
    // =========================

    private void showNewProject() {

        LinearLayout root = baseLayout();

        TextView back = text(
                "‹  Back",
                16,
                MUTED
        );

        back.setPadding(20, 20, 20, 20);

        back.setOnClickListener(v ->
                showDashboard()
        );

        root.addView(back);

        TextView heading = text(
                "Create New Project",
                27,
                TEXT
        );

        heading.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        root.addView(
                heading,
                margin(20, 15, 20, 6)
        );

        TextView description = text(
                "Configure your Android application",
                14,
                MUTED
        );

        root.addView(
                description,
                margin(20, 0, 20, 25)
        );

        // Project Name

        root.addView(
                label("Project Name"),
                margin(20, 0, 20, 6)
        );

        EditText projectName = input(
                "MyApplication"
        );

        root.addView(
                projectName,
                margin(20, 0, 20, 18)
        );

        // Package Name

        root.addView(
                label("Package Name"),
                margin(20, 0, 20, 6)
        );

        EditText packageName = input(
                "com.example.myapplication"
        );

        root.addView(
                packageName,
                margin(20, 0, 20, 18)
        );

        // Language

        root.addView(
                label("Language"),
                margin(20, 0, 20, 6)
        );

        Spinner language = new Spinner(this);

        String[] languages = {
                "Java",
                "Kotlin"
        };

        language.setAdapter(
                new ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        languages
                )
        );

        root.addView(
                language,
                margin(20, 0, 20, 18)
        );

        // Minimum SDK

        root.addView(
                label("Minimum SDK"),
                margin(20, 0, 20, 6)
        );

        Spinner sdk = new Spinner(this);

        String[] sdks = {
                "Android 7.0 (API 24)",
                "Android 8.0 (API 26)",
                "Android 10 (API 29)",
                "Android 12 (API 31)",
                "Android 15 (API 35)"
        };

        sdk.setAdapter(
                new ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        sdks
                )
        );

        root.addView(
                sdk,
                margin(20, 0, 20, 25)
        );

        Button create = button(
                "Create Project",
                ACCENT
        );

        create.setOnClickListener(v -> {

            String name =
                    projectName.getText()
                            .toString()
                            .trim();

            String pkg =
                    packageName.getText()
                            .toString()
                            .trim();

            if (name.isEmpty()) {
                projectName.setError(
                        "Enter project name"
                );
                return;
            }

            if (pkg.isEmpty()) {
                packageName.setError(
                        "Enter package name"
                );
                return;
            }

            Toast.makeText(
                    this,
                    "Project configuration saved",
                    Toast.LENGTH_SHORT
            ).show();

            showProjectEditor(name, pkg);
        });

        root.addView(
                create,
                margin(20, 0, 20, 20)
        );

        setContentView(root);
    }

    // =========================
    // PROJECT EDITOR PREVIEW
    // =========================

    private void showProjectEditor(
            String projectName,
            String packageName
    ) {

        LinearLayout root = baseLayout();

        TextView title = text(
                "‹  " + projectName,
                21,
                TEXT
        );

        title.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        title.setPadding(20, 20, 20, 20);

        title.setOnClickListener(v ->
                showDashboard()
        );

        root.addView(title);

        TextView info = text(
                "Project Explorer\n\n" +
                "📁 app\n" +
                "   ├── 📁 manifests\n" +
                "   ├── 📁 java\n" +
                "   └── 📁 res\n\n" +
                "Package:\n" +
                packageName,
                16,
                TEXT
        );

        info.setPadding(25, 25, 25, 25);
        info.setBackground(
                round(16, PANEL)
        );

        root.addView(
                info,
                margin(20, 10, 20, 20)
        );

        TextView coming = text(
                "Code Editor\n\n" +
                "The professional code editor will be added next.",
                15,
                MUTED
        );

        coming.setPadding(20, 20, 20, 20);
        coming.setBackground(
                round(16, PANEL)
        );

        root.addView(
                coming,
                margin(20, 0, 20, 20)
        );

        setContentView(root);
    }

    // =========================
    // UI HELPERS
    // =========================

    private LinearLayout baseLayout() {

        LinearLayout layout =
                new LinearLayout(this);

        layout.setOrientation(
                LinearLayout.VERTICAL
        );

        layout.setBackgroundColor(BG);

        ScrollView scroll =
                new ScrollView(this);

        scroll.addView(layout);

        // Keep normal layout for current screen
        return layout;
    }

    private TextView label(String value) {

        TextView t =
                text(value, 14, TEXT);

        t.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        return t;
    }

    private EditText input(String hint) {

        EditText e = new EditText(this);

        e.setHint(hint);
        e.setHintTextColor(MUTED);
        e.setTextColor(TEXT);
        e.setTextSize(15);
        e.setSingleLine(true);
        e.setPadding(16, 14, 16, 14);

        e.setBackground(
                round(12, PANEL)
        );

        return e;
    }

    private Button button(
            String value,
            int color
    ) {

        Button b = new Button(this);

        b.setText(value);
        b.setTextColor(Color.WHITE);
        b.setTextSize(15);
        b.setAllCaps(false);
        b.setPadding(10, 8, 10, 8);

        b.setBackground(
                round(14, color)
        );

        return b;
    }

    private View projectCard(
            String name,
            String pkg
    ) {

        TextView card =
                text(
                        "▣  " + name +
                        "\n     " + pkg +
                        "  •  Android App",
                        15,
                        TEXT
                );

        card.setPadding(
                18,
                17,
                18,
                17
        );

        card.setBackground(
                round(14, PANEL)
        );

        return card;
    }

    private View statusCard(
            String name,
            String status
    ) {

        LinearLayout card =
                new LinearLayout(this);

        card.setOrientation(
                LinearLayout.HORIZONTAL
        );

        card.setGravity(
                Gravity.CENTER_VERTICAL
        );

        card.setPadding(
                18,
                16,
                18,
                16
        );

        card.setBackground(
                round(12, PANEL)
        );

        TextView n =
                text(name, 14, TEXT);

        TextView s =
                text(status, 13, Color.rgb(70, 190, 125));

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                );

        card.addView(n, p);
        card.addView(s);

        return card;
    }

    private TextView text(
            String value,
            float size,
            int color
    ) {

        TextView t =
                new TextView(this);

        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);

        return t;
    }

    private GradientDrawable round(
            int radius,
            int color
    ) {

        GradientDrawable d =
                new GradientDrawable();

        d.setColor(color);
        d.setCornerRadius(radius);

        return d;
    }

    private LinearLayout.LayoutParams margin(
            int left,
            int top,
            int right,
            int bottom
    ) {

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        -1,
                        -2
                );

        p.setMargins(
                left,
                top,
                right,
                bottom
        );

        return p;
    }
}
