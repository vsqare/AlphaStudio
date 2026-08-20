package com.alphastudio;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.text.InputType;
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

    // =====================================================
    // COLORS
    // =====================================================

    private static final int BG = Color.rgb(18, 18, 20);
    private static final int PANEL = Color.rgb(27, 27, 31);
    private static final int PANEL2 = Color.rgb(35, 35, 41);
    private static final int BORDER = Color.rgb(52, 52, 60);

    private static final int TEXT = Color.rgb(245, 245, 247);
    private static final int MUTED = Color.rgb(155, 155, 165);

    private static final int ACCENT = Color.rgb(82, 120, 255);
    private static final int GREEN = Color.rgb(70, 190, 125);
    private static final int RED = Color.rgb(235, 90, 90);

    // =====================================================
    // EDITOR STATE
    // =====================================================

    private EditText editor;

    private String currentFile = "MainActivity.java";

    private String javaCode = "";
    private String manifestCode = "";
    private String xmlCode = "";

    private boolean projectCreated = false;

    private String currentProjectName = "MyApplication";
    private String currentPackage = "com.example.myapplication";


    // =====================================================
    // ACTIVITY
    // =====================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        top.setPadding(18, 14, 12, 14);
        top.setBackgroundColor(PANEL);

        TextView logo = text("A", 21, Color.WHITE);

        logo.setGravity(Gravity.CENTER);
        logo.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        logo.setBackground(round(12, ACCENT));

        top.addView(
                logo,
                new LinearLayout.LayoutParams(46, 46)
        );

        LinearLayout titles = vertical();
        titles.setPadding(13, 0, 0, 0);

        TextView title = text(
                "AlphaStudio",
                19,
                TEXT
        );

        title.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        titles.addView(title);

        titles.addView(
                text(
                        "Android IDE",
                        12,
                        MUTED
                )
        );

        top.addView(
                titles,
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                )
        );

        TextView menu = text("⋮", 28, TEXT);
        menu.setGravity(Gravity.CENTER);

        top.addView(
                menu,
                new LinearLayout.LayoutParams(42, 46)
        );

        root.addView(top);

        // CONTENT
        ScrollView scroll = new ScrollView(this);

        LinearLayout content = vertical();

        content.setPadding(
                20,
                24,
                20,
                30
        );

        TextView welcome = text(
                "Welcome back",
                28,
                TEXT
        );

        welcome.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        content.addView(welcome);

        addMargin(
                content,
                text(
                        "Build professional Android apps directly from your phone.",
                        14,
                        MUTED
                ),
                0,
                6,
                0,
                22
        );

        // PROJECT BUTTONS
        LinearLayout projectRow = horizontal();

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

        LinearLayout.LayoutParams p1 =
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                );

        p1.setMargins(0, 0, 6, 0);

        LinearLayout.LayoutParams p2 =
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                );

        p2.setMargins(6, 0, 0, 0);

        projectRow.addView(newProject, p1);
        projectRow.addView(openProject, p2);

        content.addView(projectRow);

        // RECENT
        TextView recent = text(
                "Recent Projects",
                20,
                TEXT
        );

        recent.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        addMargin(
                content,
                recent,
                0,
                30,
                0,
                12
        );

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
                0,
                8,
                0,
                0
        );

        // QUICK ACTIONS
        TextView quick = text(
                "Quick Actions",
                20,
                TEXT
        );

        quick.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        addMargin(
                content,
                quick,
                0,
                30,
                0,
                12
        );

        HorizontalScrollView hsv =
                new HorizontalScrollView(this);

        hsv.setHorizontalScrollBarEnabled(false);

        LinearLayout quickRow = horizontal();

        quickRow.addView(
                quickCard("▣", "Build APK")
        );

        quickRow.addView(
                quickCard("⌘", "GitHub")
        );

        quickRow.addView(
                quickCard("⚙", "SDK Manager")
        );

        quickRow.addView(
                quickCard("▤", "Terminal")
        );

        hsv.addView(quickRow);

        content.addView(hsv);

        // ENVIRONMENT
        TextView env = text(
                "Environment",
                20,
                TEXT
        );

        env.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        addMargin(
                content,
                env,
                0,
                30,
                0,
                12
        );

        content.addView(
                status(
                        "Android SDK",
                        "Ready"
                )
        );

        content.addView(
                status(
                        "Gradle",
                        "Ready"
                )
        );

        content.addView(
                status(
                        "GitHub",
                        "Connected"
                )
        );

        scroll.addView(content);

        root.addView(
                scroll,
                new LinearLayout.LayoutParams(
                        -1,
                        0,
                        1
                )
        );

        setContentView(root);
    }


    // =====================================================
    // NEW PROJECT
    // =====================================================

    private void showNewProjectDialog() {

        LinearLayout box = vertical();

        box.setPadding(
                30,
                5,
                30,
                5
        );

        box.addView(
                label("Project Name")
        );

        EditText name =
                input("MyApplication");

        box.addView(name);

        addMargin(
                box,
                label("Package Name"),
                0,
                18,
                0,
                0
        );

        EditText pkg =
                input(
                        "com.example.myapplication"
                );

        box.addView(pkg);

        addMargin(
                box,
                label("Language"),
                0,
                18,
                0,
                0
        );

        Spinner language =
                spinner(
                        new String[]{
                                "Java",
                                "Kotlin"
                        }
                );

        box.addView(language);

        addMargin(
                box,
                label("Minimum SDK"),
                0,
                18,
                0,
                0
        );

        Spinner sdk =
                spinner(
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
                        .setTitle(
                                "Create New Project"
                        )
                        .setView(box)
                        .setNegativeButton(
                                "Cancel",
                                null
                        )
                        .setPositiveButton(
                                "Create",
                                null
                        )
                        .create();

        dialog.setOnShowListener(
                d -> {

                    Button create =
                            dialog.getButton(
                                    AlertDialog.BUTTON_POSITIVE
                            );

                    create.setOnClickListener(
                            v -> {

                                String project =
                                        name
                                                .getText()
                                                .toString()
                                                .trim();

                                String packageName =
                                        pkg
                                                .getText()
                                                .toString()
                                                .trim();

                                if (project.isEmpty()) {

                                    name.setError(
                                            "Enter project name"
                                    );

                                    return;
                                }

                                if (packageName.isEmpty()) {

                                    pkg.setError(
                                            "Enter package name"
                                    );

                                    return;
                                }

                                currentProjectName =
                                        project;

                                currentPackage =
                                        packageName;

                                createInitialFiles();

                                projectCreated = true;

                                dialog.dismiss();

                                buildWorkspace();
                            }
                    );
                }
        );

        dialog.show();
    }


    // =====================================================
    // INITIAL PROJECT FILES
    // =====================================================

    private void createInitialFiles() {

        javaCode =
                "package " +
                currentPackage +
                ";\n\n" +

                "import android.app.Activity;\n" +
                "import android.os.Bundle;\n\n" +

                "public class MainActivity extends Activity {\n\n" +

                "    @Override\n" +

                "    protected void onCreate(Bundle savedInstanceState) {\n" +

                "        super.onCreate(savedInstanceState);\n" +

                "        setContentView(R.layout.activity_main);\n" +

                "    }\n" +

                "}\n";


        manifestCode =
                "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n\n" +

                "<manifest xmlns:android=\"http://schemas.android.com/apk/res/android\">\n\n" +

                "    <application\n" +

                "        android:allowBackup=\"true\"\n" +

                "        android:label=\"" +
                currentProjectName +
                "\"\n" +

                "        android:theme=\"@android:style/Theme.Material.NoActionBar\">\n\n" +

                "        <activity\n" +

                "            android:name=\".MainActivity\"\n" +

                "            android:exported=\"true\">\n\n" +

                "            <intent-filter>\n" +

                "                <action android:name=\"android.intent.action.MAIN\" />\n" +

                "                <category android:name=\"android.intent.category.LAUNCHER\" />\n" +

                "            </intent-filter>\n\n" +

                "        </activity>\n\n" +

                "    </application>\n\n" +

                "</manifest>";


        xmlCode =
                "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n\n" +

                "<LinearLayout\n" +

                "    xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +

                "    android:layout_width=\"match_parent\"\n" +

                "    android:layout_height=\"match_parent\"\n" +

                "    android:orientation=\"vertical\">\n\n" +

                "    <TextView\n" +

                "        android:layout_width=\"wrap_content\"\n" +

                "        android:layout_height=\"wrap_content\"\n" +

                "        android:text=\"Hello AlphaStudio\" />\n\n" +

                "</LinearLayout>";
    }


    // =====================================================
    // WORKSPACE
    // =====================================================

    private void buildWorkspace() {

        currentFile =
                "MainActivity.java";

        LinearLayout root = vertical();

        root.setBackgroundColor(BG);

        // HEADER
        LinearLayout header = horizontal();

        header.setGravity(
                Gravity.CENTER_VERTICAL
        );

        header.setPadding(
                8,
                8,
                6,
                8
        );

        header.setBackgroundColor(PANEL);

        TextView back =
                text(
                        "‹",
                        34,
                        TEXT
                );

        back.setGravity(
                Gravity.CENTER
        );

        back.setOnClickListener(
                v -> buildDashboard()
        );

        header.addView(
                back,
                new LinearLayout.LayoutParams(
                        45,
                        48
                )
        );

        LinearLayout projectTitle =
                vertical();

        projectTitle.setPadding(
                8,
                0,
                0,
                0
        );

        TextView project =
                text(
                        currentProjectName,
                        17,
                        TEXT
                );

        project.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        projectTitle.addView(project);

        projectTitle.addView(
                text(
                        currentPackage,
                        10,
                        MUTED
                )
        );

        header.addView(
                projectTitle,
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                )
        );

        TextView save =
                text(
                        "💾",
                        20,
                        TEXT
                );

        save.setGravity(
                Gravity.CENTER
        );

        save.setOnClickListener(
                v -> saveCurrentFile()
        );

        header.addView(
                save,
                new LinearLayout.LayoutParams(
                        45,
                        48
                )
        );

        TextView run =
                text(
                        "▶",
                        18,
                        GREEN
                );

        run.setGravity(
                Gravity.CENTER
        );

        run.setOnClickListener(
                v -> Toast.makeText(
                        this,
                        "Build / Run engine will be connected next",
                        Toast.LENGTH_SHORT
                ).show()
        );

        header.addView(
                run,
                new LinearLayout.LayoutParams(
                        45,
                        48
                )
        );

        root.addView(header);


        // TOOLBAR
        HorizontalScrollView toolbarScroll =
                new HorizontalScrollView(this);

        toolbarScroll.setHorizontalScrollBarEnabled(
                false
        );

        LinearLayout toolbar =
                horizontal();

        toolbar.setPadding(
                8,
                7,
                8,
                7
        );

        toolbar.addView(
                tool(
                        "⌕",
                        "Search"
                )
        );

        toolbar.addView(
                tool(
                        "↶",
                        "Undo"
                )
        );

        toolbar.addView(
                tool(
                        "↷",
                        "Redo"
                )
        );

        toolbar.addView(
                tool(
                        "💾",
                        "Save"
                )
        );

        toolbar.addView(
                tool(
                        "▶",
                        "Run"
                )
        );

        toolbarScroll.addView(toolbar);

        root.addView(toolbarScroll);


        // MAIN AREA
        LinearLayout main =
                horizontal();


        // ================================================
        // PROJECT EXPLORER
        // ================================================

        LinearLayout explorer =
                vertical();

        explorer.setPadding(
                10,
                12,
                10,
                12
        );

        explorer.setBackgroundColor(
                PANEL
        );

        TextView explorerTitle =
                text(
                        "PROJECT",
                        11,
                        MUTED
                );

        explorerTitle.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        explorer.addView(
                explorerTitle
        );

        explorer.addView(
                treeItem(
                        "📁 app",
                        0
                )
        );

        explorer.addView(
                treeItem(
                        "📁 manifests",
                        1
                )
        );

        TextView manifest =
                treeItem(
                        "📄 AndroidManifest.xml",
                        2
                );

        manifest.setOnClickListener(
                v -> openFile(
                        "AndroidManifest.xml"
                )
        );

        explorer.addView(manifest);

        explorer.addView(
                treeItem(
                        "📁 java",
                        1
                )
        );

        TextView mainJava =
                treeItem(
                        "📄 MainActivity.java",
                        2
                );

        mainJava.setOnClickListener(
                v -> openFile(
                        "MainActivity.java"
                )
        );

        explorer.addView(mainJava);

        explorer.addView(
                treeItem(
                        "📁 res",
                        1
                )
        );

        explorer.addView(
                treeItem(
                        "📁 layout",
                        2
                )
        );

        TextView xml =
                treeItem(
                        "📄 activity_main.xml",
                        3
                );

        xml.setOnClickListener(
                v -> openFile(
                        "activity_main.xml"
                )
        );

        explorer.addView(xml);

        main.addView(
                explorer,
                new LinearLayout.LayoutParams(
                        185,
                        -1
                )
        );


        // ================================================
        // EDITOR
        // ================================================

        LinearLayout editorArea =
                vertical();

        editorArea.setBackgroundColor(
                BG
        );


        // TAB
        HorizontalScrollView tabsScroll =
                new HorizontalScrollView(this);

        tabsScroll.setHorizontalScrollBarEnabled(
                false
        );

        LinearLayout tabs =
                horizontal();

        tabs.setPadding(
                6,
                6,
                6,
                6
        );

        tabs.addView(
                fileTab(
                        "MainActivity.java"
                )
        );

        tabs.addView(
                fileTab(
                        "AndroidManifest.xml"
                )
        );

        tabs.addView(
                fileTab(
                        "activity_main.xml"
                )
        );

        tabsScroll.addView(tabs);

        editorArea.addView(tabsScroll);


        // EDITOR
        editor =
                new EditText(this);

        editor.setGravity(
                Gravity.TOP | Gravity.START
        );

        editor.setTextSize(
                13
        );

        editor.setTextColor(
                Color.rgb(
                        225,
                        225,
                        230
                )
        );

        editor.setHintTextColor(
                MUTED
        );

        editor.setTypeface(
                Typeface.MONOSPACE
        );

        editor.setInputType(
                InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_FLAG_MULTI_LINE |
                InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        );

        editor.setPadding(
                14,
                12,
                14,
                40
        );

        editor.setBackgroundColor(
                BG
        );

        loadCurrentFile();


        ScrollView editorScroll =
                new ScrollView(this);

        editorScroll.addView(
                editor
        );

        editorArea.addView(
                editorScroll,
                new LinearLayout.LayoutParams(
                        -1,
                        0,
                        1
                )
        );


        // EDITOR STATUS
        LinearLayout editorStatus =
                horizontal();

        editorStatus.setGravity(
                Gravity.CENTER_VERTICAL
        );

        editorStatus.setPadding(
                12,
                7,
                12,
                7
        );

        editorStatus.setBackgroundColor(
                PANEL
        );

        editorStatus.addView(
                text(
                        "● Ready",
                        11,
                        GREEN
                ),
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                )
        );

        editorStatus.addView(
                text(
                        "Ln 1  Col 1",
                        10,
                        MUTED
                )
        );

        editorArea.addView(editorStatus);


        main.addView(
                editorArea,
                new LinearLayout.LayoutParams(
                        0,
                        -1,
                        1
                )
        );


        root.addView(
                main,
                new LinearLayout.LayoutParams(
                        -1,
                        0,
                        1
                )
        );


        setContentView(root);
    }


    // =====================================================
    // FILE SWITCHING
    // =====================================================

    private void openFile(String file) {

        saveEditorToMemory();

        currentFile = file;

        loadCurrentFile();
    }


    private void loadCurrentFile() {

        if (editor == null) {
            return;
        }

        if (
                currentFile.equals(
                        "MainActivity.java"
                )
        ) {

            editor.setText(
                    javaCode
            );

        } else if (
                currentFile.equals(
                        "AndroidManifest.xml"
                )
        ) {

            editor.setText(
                    manifestCode
            );

        } else if (
                currentFile.equals(
                        "activity_main.xml"
                )
        ) {

            editor.setText(
                    xmlCode
            );
        }

        editor.setSelection(
                editor.length()
        );
    }


    private void saveEditorToMemory() {

        if (editor == null) {
            return;
        }

        String value =
                editor.getText()
                        .toString();

        if (
                currentFile.equals(
                        "MainActivity.java"
                )
        ) {

            javaCode = value;

        } else if (
                currentFile.equals(
                        "AndroidManifest.xml"
                )
        ) {

            manifestCode = value;

        } else if (
                currentFile.equals(
                        "activity_main.xml"
                )
        ) {

            xmlCode = value;
        }
    }


    private void saveCurrentFile() {

        saveEditorToMemory();

        Toast.makeText(
                this,
                currentFile + " saved",
                Toast.LENGTH_SHORT
        ).show();
    }


    // =====================================================
    // SEARCH
    // =====================================================

    private void showSearch() {

        final EditText search =
                input(
                        "Search in current file"
                );

        AlertDialog dialog =
                new AlertDialog.Builder(this)
                        .setTitle(
                                "Search"
                        )
                        .setView(search)
                        .setNegativeButton(
                                "Cancel",
                                null
                        )
                        .setPositiveButton(
                                "Find",
                                null
                        )
                        .create();

        dialog.setOnShowListener(
                d -> {

                    Button find =
                            dialog.getButton(
                                    AlertDialog.BUTTON_POSITIVE
                            );

                    find.setOnClickListener(
                            v -> {

                                String query =
                                        search
                                                .getText()
                                                .toString();

                                if (
                                        query.isEmpty()
                                ) {
                                    return;
                                }

                                String content =
                                        editor
                                                .getText()
                                                .toString();

                                int position =
                                        content.indexOf(
                                                query
                                        );

                                if (
                                        position >= 0
                                ) {

                                    editor.requestFocus();

                                    editor.setSelection(
                                            position,
                                            position +
                                            query.length()
                                    );

                                    Toast.makeText(
                                            this,
                                            "Found",
                                            Toast.LENGTH_SHORT
                                    ).show();

                                } else {

                                    Toast.makeText(
                                            this,
                                            "Not found",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }
                            }
                    );
                }
        );

        dialog.show();
    }


    // =====================================================
    // FILE TAB
    // =====================================================

    private View fileTab(
            String file
    ) {

        TextView tab =
                text(
                        file + "   ×",
                        11,
                        TEXT
                );

        tab.setPadding(
                14,
                11,
                14,
                11
        );

        int color =
                file.equals(
                        currentFile
                )
                ? PANEL2
                : PANEL;

        tab.setBackground(
                round(
                        8,
                        color
                )
        );

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        -2,
                        -2
                );

        p.setMargins(
                3,
                0,
                3,
                0
        );

        tab.setLayoutParams(p);

        tab.setOnClickListener(
                v -> {

                    saveEditorToMemory();

                    currentFile = file;

                    loadCurrentFile();
                }
        );

        return tab;
    }


    // =====================================================
    // UI HELPERS
    // =====================================================

    private View actionCard(
            String icon,
            String title,
            String subtitle,
            int color
    ) {

        LinearLayout card =
                vertical();

        card.setPadding(
                18,
                18,
                18,
                18
        );

        card.setBackground(
                round(
                        16,
                        color
                )
        );

        TextView iconView =
                text(
                        icon,
                        27,
                        TEXT
                );

        iconView.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        card.addView(iconView);

        TextView titleView =
                text(
                        title,
                        17,
                        TEXT
                );

        titleView.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        addMargin(
                card,
                titleView,
                0,
                12,
                0,
                3
        );

        card.addView(
                text(
                        subtitle,
                        12,
                        TEXT
                )
        );

        card.setClickable(true);

        card.setOnClickListener(
                v -> {

                    if (
                            title.equals(
                                    "New Project"
                            )
                    ) {

                        showNewProjectDialog();

                    } else {

                        Toast.makeText(
                                this,
                                "Open Project will be added next",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
        );

        return card;
    }


    private View projectCard(
            String name,
            String pkg
    ) {

        LinearLayout card =
                vertical();

        card.setPadding(
                18,
                16,
                18,
                16
        );

        card.setBackground(
                round(
                        14,
                        PANEL
                )
        );

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
                text(
                        pkg,
                        12,
                        MUTED
                ),
                0,
                5,
                0,
                2
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
                        icon +
                        "\n\n" +
                        title,
                        14,
                        TEXT
                );

        t.setGravity(
                Gravity.CENTER
        );

        t.setPadding(
                15,
                15,
                15,
                15
        );

        t.setBackground(
                round(
                        14,
                        PANEL
                )
        );

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        125,
                        105
                );

        p.setMargins(
                0,
                0,
                12,
                0
        );

        t.setLayoutParams(p);

        return t;
    }


    private View status(
            String name,
            String value
    ) {

        LinearLayout card =
                horizontal();

        card.setGravity(
                Gravity.CENTER_VERTICAL
        );

        card.setPadding(
                16,
                15,
                16,
                15
        );

        card.setBackground(
                round(
                        12,
                        PANEL
                )
        );

        card.addView(
                text(
                        "●",
                        14,
                        GREEN
                ),
                new LinearLayout.LayoutParams(
                        25,
                        -2
                )
        );

        card.addView(
                text(
                        name,
                        14,
                        TEXT
                ),
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                )
        );

        TextView s =
                text(
                        value,
                        13,
                        GREEN
                );

        s.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        card.addView(s);

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        -1,
                        -2
                );

        p.setMargins(
                0,
                0,
                0,
                8
        );

        card.setLayoutParams(p);

        return card;
    }


    private TextView tool(
            String icon,
            String title
    ) {

        TextView t =
                text(
                        icon +
                        "  " +
                        title,
                        12,
                        TEXT
                );

        t.setPadding(
                14,
                10,
                14,
                10
        );

        t.setBackground(
                round(
                        10,
                        PANEL
                )
        );

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        -2,
                        -2
                );

        p.setMargins(
                0,
                0,
                8,
                0
        );

        t.setLayoutParams(p);

        if (
                title.equals("Search")
        ) {

            t.setOnClickListener(
                    v -> showSearch()
            );

        } else if (
                title.equals("Save")
        ) {

            t.setOnClickListener(
                    v -> saveCurrentFile()
            );

        } else if (
                title.equals("Undo")
        ) {

            t.setOnClickListener(
                    v -> {

                        if (
                                editor != null &&
                                editor.canUndo()
                        ) {

                            editor.undo();
                        }
                    }
            );

        } else if (
                title.equals("Redo")
        ) {

            t.setOnClickListener(
                    v -> {

                        if (
                                editor != null &&
                                editor.canRedo()
                        ) {

                            editor.redo();
                        }
                    }
            );

        } else if (
                title.equals("Run")
        ) {

            t.setOnClickListener(
                    v -> Toast.makeText(
                            this,
                            "Build engine will be connected next",
                            Toast.LENGTH_SHORT
                    ).show()
            );
        }

        return t;
    }


    private TextView treeItem(
            String value,
            int level
    ) {

        TextView t =
                text(
                        value,
                        12,
                        TEXT
                );

        t.setPadding(
                8 + level * 12,
                9,
                4,
                9
        );

        return t;
    }


    private TextView label(
            String value
    ) {

        TextView t =
                text(
                        value,
                        13,
                        TEXT
                );

        t.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        return t;
    }


    private EditText input(
            String hint
    ) {

        EditText e =
                new EditText(this);

        e.setHint(hint);
        e.setSingleLine(true);
        e.setTextColor(TEXT);
        e.setHintTextColor(MUTED);

        return e;
    }


    private Spinner spinner(
            String[] values
    ) {

        Spinner s =
                new Spinner(this);

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


    private LinearLayout vertical() {

        LinearLayout l =
                new LinearLayout(this);

        l.setOrientation(
                LinearLayout.VERTICAL
        );

        return l;
    }


    private LinearLayout horizontal() {

        LinearLayout l =
                new LinearLayout(this);

        l.setOrientation(
                LinearLayout.HORIZONTAL
        );

        return l;
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

        parent.addView(
                view,
                p
        );
    }
}
