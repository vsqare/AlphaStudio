package com.alphastudio;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static final int BG = Color.rgb(18,18,20);
    private static final int PANEL = Color.rgb(27,27,31);
    private static final int PANEL2 = Color.rgb(35,35,41);
    private static final int TEXT = Color.rgb(245,245,247);
    private static final int MUTED = Color.rgb(155,155,165);
    private static final int ACCENT = Color.rgb(82,120,255);
    private static final int GREEN = Color.rgb(70,190,125);

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);

        getWindow().setStatusBarColor(BG);
        getWindow().setNavigationBarColor(BG);

        buildDashboard();
    }

    // =====================================================
    // DASHBOARD
    // =====================================================

    private void buildDashboard() {

        LinearLayout root = vertical();
        root.setBackgroundColor(BG);

        // TOP BAR
        LinearLayout top = horizontal();
        top.setGravity(Gravity.CENTER_VERTICAL);
        top.setPadding(18,14,12,14);
        top.setBackgroundColor(PANEL);

        TextView logo = text("A",21,Color.WHITE);
        logo.setGravity(Gravity.CENTER);
        logo.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        logo.setBackground(round(12,ACCENT));

        top.addView(logo,new LinearLayout.LayoutParams(46,46));

        LinearLayout titles = vertical();
        titles.setPadding(13,0,0,0);

        TextView title = text("AlphaStudio",19,TEXT);
        title.setTypeface(Typeface.DEFAULT,Typeface.BOLD);

        titles.addView(title);
        titles.addView(text("Android IDE",12,MUTED));

        top.addView(titles,new LinearLayout.LayoutParams(0,-2,1));

        TextView menu = text("⋮",28,TEXT);
        menu.setGravity(Gravity.CENTER);

        top.addView(menu,new LinearLayout.LayoutParams(42,46));

        root.addView(top);

        // CONTENT
        ScrollView scroll = new ScrollView(this);
        LinearLayout content = vertical();

        content.setPadding(20,24,20,30);

        TextView welcome = text("Welcome back",28,TEXT);
        welcome.setTypeface(Typeface.DEFAULT,Typeface.BOLD);

        content.addView(welcome);

        addMargin(
                content,
                text(
                        "Build professional Android apps directly from your phone.",
                        14,
                        MUTED
                ),
                0,6,0,22
        );

        // PROJECT BUTTONS
        LinearLayout row = horizontal();

        View newProject = actionCard(
                "＋",
                "New Project",
                "Create Android app",
                ACCENT
        );

        View openProject = actionCard(
                "↗",
                "Open Project",
                "Open existing project",
                PANEL2
        );

        LinearLayout.LayoutParams a =
                new LinearLayout.LayoutParams(0,-2,1);

        a.setMargins(0,0,6,0);

        LinearLayout.LayoutParams b =
                new LinearLayout.LayoutParams(0,-2,1);

        b.setMargins(6,0,0,0);

        row.addView(newProject,a);
        row.addView(openProject,b);

        content.addView(row);

        // RECENT
        TextView recent = text(
                "Recent Projects",
                20,
                TEXT
        );

        recent.setTypeface(Typeface.DEFAULT,Typeface.BOLD);

        addMargin(content,recent,0,30,0,12);

        content.addView(
                projectCard(
                        "AlphaStudio",
                        "com.alphastudio"
                )
        );

        addMargin(
                content,
                projectCard(
                        "My First App",
                        "com.example.myapp"
                ),
                0,8,0,0
        );

        // QUICK ACTIONS
        TextView quick = text(
                "Quick Actions",
                20,
                TEXT
        );

        quick.setTypeface(Typeface.DEFAULT,Typeface.BOLD);

        addMargin(content,quick,0,30,0,12);

        HorizontalScrollView hsv =
                new HorizontalScrollView(this);

        hsv.setHorizontalScrollBarEnabled(false);

        LinearLayout quickRow = horizontal();

        quickRow.addView(quickCard("▣","Build APK"));
        quickRow.addView(quickCard("⌘","GitHub"));
        quickRow.addView(quickCard("⚙","SDK Manager"));
        quickRow.addView(quickCard("▤","Terminal"));

        hsv.addView(quickRow);
        content.addView(hsv);

        // ENVIRONMENT
        TextView env = text(
                "Environment",
                20,
                TEXT
        );

        env.setTypeface(Typeface.DEFAULT,Typeface.BOLD);

        addMargin(content,env,0,30,0,12);

        content.addView(status("Android SDK","Ready"));
        content.addView(status("Gradle","Ready"));
        content.addView(status("GitHub","Connected"));

        scroll.addView(content);

        root.addView(
                scroll,
                new LinearLayout.LayoutParams(-1,0,1)
        );

        setContentView(root);
    }

    // =====================================================
    // NEW PROJECT
    // =====================================================

    private void showNewProjectDialog() {

        LinearLayout box = vertical();
        box.setPadding(30,5,30,5);

        box.addView(label("Project Name"));

        EditText name = input("MyApplication");
        box.addView(name);

        addMargin(
                box,
                label("Package Name"),
                0,18,0,0
        );

        EditText pkg =
                input("com.example.myapplication");

        box.addView(pkg);

        addMargin(
                box,
                label("Language"),
                0,18,0,0
        );

        Spinner language = spinner(
                new String[]{"Java","Kotlin"}
        );

        box.addView(language);

        addMargin(
                box,
                label("Minimum SDK"),
                0,18,0,0
        );

        Spinner sdk = spinner(
                new String[]{
                        "Android 7.0 (API 24)",
                        "Android 8.0 (API 26)",
                        "Android 10 (API 29)",
                        "Android 12 (API 31)",
                        "Android 15 (API 35)"
                }
        );

        box.addView(sdk);

        AlertDialog dialog =
                new AlertDialog.Builder(this)
                        .setTitle("Create New Project")
                        .setView(box)
                        .setNegativeButton("Cancel",null)
                        .setPositiveButton("Create",null)
                        .create();

        dialog.setOnShowListener(d -> {

            Button create =
                    dialog.getButton(
                            AlertDialog.BUTTON_POSITIVE
                    );

            create.setOnClickListener(v -> {

                String project =
                        name.getText().toString().trim();

                String packageName =
                        pkg.getText().toString().trim();

                if(project.isEmpty()) {
                    name.setError("Enter project name");
                    return;
                }

                if(packageName.isEmpty()) {
                    pkg.setError("Enter package name");
                    return;
                }

                dialog.dismiss();

                buildWorkspace(
                        project,
                        packageName
                );
            });
        });

        dialog.show();
    }

    // =====================================================
    // PROJECT WORKSPACE
    // =====================================================

    private void buildWorkspace(
            String projectName,
            String packageName
    ) {

        LinearLayout root = vertical();
        root.setBackgroundColor(BG);

        // HEADER
        LinearLayout header = horizontal();

        header.setGravity(Gravity.CENTER_VERTICAL);
        header.setPadding(12,10,8,10);
        header.setBackgroundColor(PANEL);

        TextView back = text("‹",34,TEXT);
        back.setGravity(Gravity.CENTER);

        back.setOnClickListener(v -> buildDashboard());

        header.addView(back,new LinearLayout.LayoutParams(48,50));

        LinearLayout names = vertical();
        names.setPadding(8,0,0,0);

        TextView project = text(
                projectName,
                18,
                TEXT
        );

        project.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        names.addView(project);

        names.addView(
                text(
                        packageName,
                        11,
                        MUTED
                )
        );

        header.addView(
                names,
                new LinearLayout.LayoutParams(
                        0,-2,1
                )
        );

        TextView run = text("▶",20,TEXT);
        run.setGravity(Gravity.CENTER);

        run.setOnClickListener(v ->
                Toast.makeText(
                        this,
                        "Build system will be connected next",
                        Toast.LENGTH_SHORT
                ).show()
        );

        header.addView(
                run,
                new LinearLayout.LayoutParams(48,50)
        );

        TextView more = text("⋮",25,TEXT);
        more.setGravity(Gravity.CENTER);

        header.addView(
                more,
                new LinearLayout.LayoutParams(40,50)
        );

        root.addView(header);

        // TOOL BAR
        HorizontalScrollView toolsScroll =
                new HorizontalScrollView(this);

        toolsScroll.setHorizontalScrollBarEnabled(false);

        LinearLayout tools = horizontal();

        tools.setPadding(10,8,10,8);

        tools.addView(tool("⌕","Search"));
        tools.addView(tool("↶","Undo"));
        tools.addView(tool("↷","Redo"));
        tools.addView(tool("▶","Run"));
        tools.addView(tool("⚙","Settings"));

        toolsScroll.addView(tools);

        root.addView(toolsScroll);

        // MAIN
        LinearLayout main = horizontal();

        // EXPLORER
        LinearLayout explorer = vertical();

        explorer.setPadding(14,14,14,14);
        explorer.setBackgroundColor(PANEL);

        TextView explorerTitle =
                text(
                        "PROJECT",
                        12,
                        MUTED
                );

        explorerTitle.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        explorer.addView(explorerTitle);

        explorer.addView(
                treeItem("📁 app",0)
        );

        explorer.addView(
                treeItem("📁 manifests",1)
        );

        explorer.addView(
                treeItem("📄 AndroidManifest.xml",2)
        );

        explorer.addView(
                treeItem("📁 java",1)
        );

        TextView mainFile =
                treeItem(
                        "📄 MainActivity.java",
                        2
                );

        explorer.addView(mainFile);

        explorer.addView(
                treeItem("📁 res",1)
        );

        explorer.addView(
                treeItem("📁 layout",2)
        );

        explorer.addView(
                treeItem("📄 activity_main.xml",3)
        );

        main.addView(
                explorer,
                new LinearLayout.LayoutParams(
                        190,-1
                )
        );

        // EDITOR
        ScrollView editorScroll =
                new ScrollView(this);

        LinearLayout editor =
                vertical();

        editor.setPadding(
                16,16,16,30
        );

        editor.setBackgroundColor(BG);

        TextView tab =
                text(
                        "MainActivity.java   ×",
                        13,
                        TEXT
                );

        tab.setPadding(
                12,12,12,12
        );

        tab.setBackground(
                round(8,PANEL)
        );

        editor.addView(tab);

        TextView code =
                text(
                        "package " + packageName + ";\n\n" +
                        "import android.app.Activity;\n" +
                        "import android.os.Bundle;\n\n" +
                        "public class MainActivity extends Activity {\n\n" +
                        "    @Override\n" +
                        "    protected void onCreate(Bundle savedInstanceState) {\n" +
                        "        super.onCreate(savedInstanceState);\n" +
                        "        setContentView(R.layout.activity_main);\n" +
                        "    }\n" +
                        "}\n",
                        13,
                        Color.rgb(220,220,225)
                );

        code.setTypeface(
                Typeface.MONOSPACE
        );

        code.setPadding(
                12,20,12,20
        );

        editor.addView(code);

        editorScroll.addView(editor);

        main.addView(
                editorScroll,
                new LinearLayout.LayoutParams(
                        0,-1,1
                )
        );

        root.addView(
                main,
                new LinearLayout.LayoutParams(
                        -1,0,1
                )
        );

        // BOTTOM BAR
        LinearLayout bottom = horizontal();

        bottom.setGravity(
                Gravity.CENTER_VERTICAL
        );

        bottom.setPadding(
                14,10,14,10
        );

        bottom.setBackgroundColor(PANEL);

        TextView status =
                text(
                        "●  Ready",
                        12,
                        GREEN
                );

        bottom.addView(
                status,
                new LinearLayout.LayoutParams(
                        0,-2,1
                )
        );

        bottom.addView(
                text(
                        "Java  •  Android",
                        11,
                        MUTED
                )
        );

        root.addView(bottom);

        setContentView(root);
    }

    // =====================================================
    // HELPERS
    // =====================================================

    private LinearLayout vertical() {
        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.VERTICAL);
        return l;
    }

    private LinearLayout horizontal() {
        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.HORIZONTAL);
        return l;
    }

    private TextView label(String value) {
        TextView t = text(value,13,TEXT);
        t.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );
        return t;
    }

    private EditText input(String hint) {

        EditText e = new EditText(this);

        e.setHint(hint);
        e.setSingleLine(true);
        e.setTextColor(TEXT);
        e.setHintTextColor(MUTED);

        return e;
    }

    private Spinner spinner(String[] values) {

        Spinner s = new Spinner(this);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        values
                );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        s.setAdapter(adapter);

        return s;
    }

    private View actionCard(
            String icon,
            String title,
            String subtitle,
            int color
    ) {

        LinearLayout card = vertical();

        card.setPadding(18,18,18,18);
        card.setBackground(round(16,color));

        TextView i = text(icon,27,TEXT);
        i.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        card.addView(i);

        TextView t = text(title,17,TEXT);
        t.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        addMargin(card,t,0,12,0,3);

        card.addView(
                text(subtitle,12,TEXT)
        );

        card.setClickable(true);

        card.setOnClickListener(v -> {

            if(title.equals("New Project")) {
                showNewProjectDialog();
            } else {
                Toast.makeText(
                        this,
                        "Open Project will be added next",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        return card;
    }

    private View projectCard(
            String name,
            String pkg
    ) {

        LinearLayout card = vertical();

        card.setPadding(18,16,18,16);
        card.setBackground(round(14,PANEL));

        TextView n =
                text(
                        "▣  " + name,
                        16,
                        TEXT
                );

        n.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        card.addView(n);

        addMargin(
                card,
                text(pkg,12,MUTED),
                0,5,0,2
        );

        card.addView(
                text(
                        "Android App",
                        11,
                        MUTED
                )
        );

        return card;
    }

    private View quickCard(
            String icon,
            String title
    ) {

        TextView t =
                text(
                        icon + "\n\n" + title,
                        14,
                        TEXT
                );

        t.setGravity(Gravity.CENTER);
        t.setPadding(15,15,15,15);
        t.setBackground(round(14,PANEL));

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        125,105
                );

        p.setMargins(0,0,12,0);

        t.setLayoutParams(p);

        return t;
    }

    private View status(
            String name,
            String value
    ) {

        LinearLayout card = horizontal();

        card.setGravity(
                Gravity.CENTER_VERTICAL
        );

        card.setPadding(16,15,16,15);
        card.setBackground(round(12,PANEL));

        TextView dot =
                text("●",14,GREEN);

        card.addView(
                dot,
                new LinearLayout.LayoutParams(
                        25,-2
                )
        );

        card.addView(
                text(name,14,TEXT),
                new LinearLayout.LayoutParams(
                        0,-2,1
                )
        );

        TextView s =
                text(value,13,GREEN);

        s.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        card.addView(s);

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        -1,-2
                );

        p.setMargins(0,0,0,8);

        card.setLayoutParams(p);

        return card;
    }

    private TextView tool(
            String icon,
            String title
    ) {

        TextView t =
                text(
                        icon + "  " + title,
                        12,
                        TEXT
                );

        t.setPadding(14,10,14,10);
        t.setBackground(round(10,PANEL));

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        -2,-2
                );

        p.setMargins(0,0,8,0);

        t.setLayoutParams(p);

        return t;
    }

    private TextView treeItem(
            String value,
            int level
    ) {

        TextView t =
                text(value,12,TEXT);

        t.setPadding(
                8 + (level * 12),
                9,
                4,
                9
        );

        t.setOnClickListener(
                v -> Toast.makeText(
                        this,
                        value,
                        Toast.LENGTH_SHORT
                ).show()
        );

        return t;
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

    private void addMargin(
            LinearLayout parent,
            View view,
            int l,
            int t,
            int r,
            int b
    ) {

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        -1,-2
                );

        p.setMargins(l,t,r,b);

        parent.addView(view,p);
    }
}
