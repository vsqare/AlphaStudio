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
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends Activity {

    // =====================================================
    // COLORS
    // =====================================================

    private static final int BG = Color.rgb(15, 16, 19);
    private static final int PANEL = Color.rgb(24, 25, 30);
    private static final int PANEL2 = Color.rgb(34, 35, 42);
    private static final int BORDER = Color.rgb(55, 57, 66);

    private static final int TEXT = Color.rgb(245, 245, 247);
    private static final int MUTED = Color.rgb(155, 158, 170);

    private static final int ACCENT = Color.rgb(82, 120, 255);
    private static final int GREEN = Color.rgb(70, 190, 125);
    private static final int RED = Color.rgb(235, 90, 90);
    private static final int ORANGE = Color.rgb(240, 165, 70);

    // =====================================================
    // PROJECT STATE
    // =====================================================

    private String currentProjectName = "MyApplication";
    private String currentPackage = "com.example.myapplication";
    private String currentFile = "MainActivity.java";

    private File projectDir;
    private File javaFile;
    private File manifestFile;
    private File xmlFile;

    private EditText editor;

    private final ArrayList<String> undoStack =
            new ArrayList<>();

    private final ArrayList<String> redoStack =
            new ArrayList<>();

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
        top.setPadding(16, 12, 10, 12);
        top.setBackgroundColor(PANEL);

        TextView logo = text("A", 21, Color.WHITE);
        logo.setGravity(Gravity.CENTER);
        logo.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        logo.setBackground(round(12, ACCENT));

        top.addView(
                logo,
                new LinearLayout.LayoutParams(46, 46)
        );

        LinearLayout titleBox = vertical();
        titleBox.setPadding(12, 0, 0, 0);

        TextView title = text(
                "AlphaStudio",
                19,
                TEXT
        );

        title.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        titleBox.addView(title);

        titleBox.addView(
                text(
                        "Professional Android IDE",
                        12,
                        MUTED
                )
        );

        top.addView(
                titleBox,
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
                new LinearLayout.LayoutParams(
                        42,
                        46
                )
        );

        root.addView(top);

        // SCROLL
        ScrollView scroll =
                new ScrollView(this);

        LinearLayout content =
                vertical();

        content.setPadding(
                20,
                24,
                20,
                35
        );

        // WELCOME
        TextView welcome =
                text(
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

        // ACTIONS
        LinearLayout actions = horizontal();

        View newProject = actionCard(
                "＋",
                "New Project",
                "Create Android app",
                ACCENT
        );

        View openProject = actionCard(
                "↗",
                "Open Project",
                "Open saved project",
                PANEL2
        );

        LinearLayout.LayoutParams a =
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                );

        a.setMargins(0, 0, 6, 0);

        LinearLayout.LayoutParams b =
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                );

        b.setMargins(6, 0, 0, 0);

        actions.addView(newProject, a);
        actions.addView(openProject, b);

        content.addView(actions);

        // RECENT PROJECTS
        addMargin(
                content,
                sectionTitle("Recent Projects"),
                0,
                30,
                0,
                12
        );

        addRecentProjects(content);

        // QUICK ACTIONS
        addMargin(
                content,
                sectionTitle("Quick Actions"),
                0,
                30,
                0,
                12
        );

        HorizontalScrollView quickScroll =
                new HorizontalScrollView(this);

        quickScroll.setHorizontalScrollBarEnabled(false);

        LinearLayout quickRow =
                horizontal();

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

        quickScroll.addView(quickRow);

        content.addView(quickScroll);

        // ENVIRONMENT
        addMargin(
                content,
                sectionTitle("Environment"),
                0,
                30,
                0,
                12
        );

        content.addView(
                statusCard(
                        "Android SDK",
                        "Ready",
                        GREEN
                )
        );

        content.addView(
                statusCard(
                        "Gradle",
                        "Ready",
                        GREEN
                )
        );

        content.addView(
                statusCard(
                        "Project Storage",
                        "Ready",
                        GREEN
                )
        );

        content.addView(
                statusCard(
                        "Code Editor",
                        "Ready",
                        GREEN
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
    // RECENT PROJECTS
    // =====================================================

    private void addRecentProjects(
            LinearLayout content
    ) {

        File root =
                new File(
                        getFilesDir(),
                        "projects"
                );

        if (!root.exists()) {
            root.mkdirs();
        }

        File[] projects =
                root.listFiles();

        if (projects == null ||
                projects.length == 0) {

            content.addView(
                    text(
                            "No projects yet. Create your first project.",
                            13,
                            MUTED
                    )
            );

            return;
        }

        for (File dir : projects) {

            if (!dir.isDirectory()) {
                continue;
            }

            String name = dir.getName();

            String pkg =
                    readText(
                            new File(
                                    dir,
                                    ".package"
                            )
                    ).trim();

            if (pkg.isEmpty()) {
                pkg = "Unknown package";
            }

            content.addView(
                    projectCard(
                            name,
                            pkg,
                            dir
                    )
            );
        }
    }

    // =====================================================
    // NEW PROJECT
    // =====================================================

    private void showNewProjectDialog() {

        LinearLayout box = vertical();

        box.setPadding(
                24,
                4,
                24,
                4
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
                16,
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
                16,
                0,
                0
        );

        android.widget.Spinner language =
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
                16,
                0,
                0
        );

        android.widget.Spinner sdk =
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
                        .setTitle("Create New Project")
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
                                        name.getText()
                                                .toString()
                                                .trim();

                                String packageName =
                                        pkg.getText()
                                                .toString()
                                                .trim();

                                if (!validProjectName(project)) {

                                    name.setError(
                                            "Use letters, numbers and _"
                                    );

                                    return;
                                }

                                if (!validPackage(packageName)) {

                                    pkg.setError(
                                            "Example: com.example.myapp"
                                    );

                                    return;
                                }

                                currentProjectName =
                                        project;

                                currentPackage =
                                        packageName;

                                if (createProjectFiles()) {

                                    dialog.dismiss();

                                    Toast.makeText(
                                            this,
                                            "Project created",
                                            Toast.LENGTH_SHORT
                                    ).show();

                                    buildWorkspace();

                                } else {

                                    Toast.makeText(
                                            this,
                                            "Project creation failed",
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            }
                    );
                }
        );

        dialog.show();
    }

    // =====================================================
    // CREATE PROJECT FILES
    // =====================================================

    private boolean createProjectFiles() {

        try {

            File projectsRoot =
                    new File(
                            getFilesDir(),
                            "projects"
                    );

            if (!projectsRoot.exists()) {
                projectsRoot.mkdirs();
            }

            projectDir =
                    new File(
                            projectsRoot,
                            currentProjectName
                    );

            if (!projectDir.exists()) {

                if (!projectDir.mkdirs()) {
                    return false;
                }
            }

            File app =
                    new File(
                            projectDir,
                            "app"
                    );

            File javaDir =
                    new File(
                            app,
                            "src/main/java/" +
                                    currentPackage.replace(
                                            ".",
                                            "/"
                                    )
                    );

            File layoutDir =
                    new File(
                            app,
                            "src/main/res/layout"
                    );

            File valuesDir =
                    new File(
                            app,
                            "src/main/res/values"
                    );

            if (!javaDir.exists()) {
                javaDir.mkdirs();
            }

            if (!layoutDir.exists()) {
                layoutDir.mkdirs();
            }

            if (!valuesDir.exists()) {
                valuesDir.mkdirs();
            }

            javaFile =
                    new File(
                            javaDir,
                            "MainActivity.java"
                    );

            manifestFile =
                    new File(
                            app,
                            "src/main/AndroidManifest.xml"
                    );

            xmlFile =
                    new File(
                            layoutDir,
                            "activity_main.xml"
                    );

            File stringsFile =
                    new File(
                            valuesDir,
                            "strings.xml"
                    );

            File appGradle =
                    new File(
                            app,
                            "build.gradle"
                    );

            File rootGradle =
                    new File(
                            projectDir,
                            "build.gradle"
                    );

            File settings =
                    new File(
                            projectDir,
                            "settings.gradle"
                    );

            File packageFile =
                    new File(
                            projectDir,
                            ".package"
                    );

            File projectFile =
                    new File(
                            projectDir,
                            ".project"
                    );

            writeText(
                    javaFile,
                    generateJava()
            );

            writeText(
                    manifestFile,
                    generateManifest()
            );

            writeText(
                    xmlFile,
                    generateXml()
            );

            writeText(
                    stringsFile,
                    generateStrings()
            );

            writeText(
                    appGradle,
                    generateAppGradle()
            );

            writeText(
                    rootGradle,
                    generateRootGradle()
            );

            writeText(
                    settings,
                    generateSettings()
            );

            writeText(
                    packageFile,
                    currentPackage
            );

            writeText(
                    projectFile,
                    currentProjectName
            );

            currentFile =
                    "MainActivity.java";

            return true;

        } catch (Exception e) {

            return false;
        }
    }

    // =====================================================
    // CODE GENERATORS
    // =====================================================

    private String generateJava() {

        return
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

                "    }\n\n" +

                "}\n";
    }

    private String generateManifest() {

        return
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

                "</manifest>\n";
    }

    private String generateXml() {

        return
                "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n\n" +

                "<LinearLayout\n" +
                "    xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
                "    android:layout_width=\"match_parent\"\n" +
                "    android:layout_height=\"match_parent\"\n" +
                "    android:orientation=\"vertical\"\n" +
                "    android:gravity=\"center\"\n" +
                "    android:padding=\"24dp\">\n\n" +

                "    <TextView\n" +
                "        android:layout_width=\"wrap_content\"\n" +
                "        android:layout_height=\"wrap_content\"\n" +
                "        android:text=\"Hello AlphaStudio\"\n" +
                "        android:textSize=\"24sp\" />\n\n" +

                "</LinearLayout>\n";
    }

    private String generateStrings() {

        return
                "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n\n" +

                "<resources>\n" +

                "    <string name=\"app_name\">" +
                currentProjectName +
                "</string>\n" +

                "</resources>\n";
    }

    private String generateAppGradle() {

        return
                "plugins {\n" +
                "    id 'com.android.application'\n" +
                "}\n\n" +

                "android {\n" +
                "    namespace '" +
                currentPackage +
                "'\n" +
                "    compileSdk 35\n\n" +

                "    defaultConfig {\n" +
                "        applicationId '" +
                currentPackage +
                "'\n" +
                "        minSdk 24\n" +
                "        targetSdk 35\n" +
                "        versionCode 1\n" +
                "        versionName '1.0'\n" +
                "    }\n" +

                "}\n";
    }

    private String generateRootGradle() {

        return
                "plugins {\n" +
                "    id 'com.android.application' version '8.7.3' apply false\n" +
                "}\n";
    }

    private String generateSettings() {

        return
                "pluginManagement {\n" +
                "    repositories {\n" +
                "        google()\n" +
                "        mavenCentral()\n" +
                "        gradlePluginPortal()\n" +
                "    }\n" +
                "}\n\n" +

                "dependencyResolutionManagement {\n" +
                "    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)\n" +
                "    repositories {\n" +
                "        google()\n" +
                "        mavenCentral()\n" +
                "    }\n" +
                "}\n\n" +

                "rootProject.name = '" +
                currentProjectName +
                "'\n" +

                "include ':app'\n";
    }

    // =====================================================
    // WORKSPACE
    // =====================================================

    private void buildWorkspace() {

        LinearLayout root = vertical();
        root.setBackgroundColor(BG);

        // HEADER
        LinearLayout header = horizontal();

        header.setGravity(
                Gravity.CENTER_VERTICAL
        );

        header.setPadding(
                6,
                6,
                6,
                6
        );

        header.setBackgroundColor(PANEL);

        TextView back =
                text("‹", 34, TEXT);

        back.setGravity(Gravity.CENTER);

        back.setOnClickListener(
                v -> {
                    saveCurrentFile();
                    buildDashboard();
                }
        );

        header.addView(
                back,
                new LinearLayout.LayoutParams(
                        45,
                        48
                )
        );

        LinearLayout projectInfo =
                vertical();

        projectInfo.setPadding(
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

        projectInfo.addView(project);

        projectInfo.addView(
                text(
                        currentPackage,
                        10,
                        MUTED
                )
        );

        header.addView(
                projectInfo,
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                )
        );

        TextView save =
                text("💾", 19, TEXT);

        save.setGravity(Gravity.CENTER);

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
                text("▶", 18, GREEN);

        run.setGravity(Gravity.CENTER);

        run.setOnClickListener(
                v -> buildProject()
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

        toolbarScroll.setHorizontalScrollBarEnabled(false);

        LinearLayout toolbar =
                horizontal();

        toolbar.setPadding(
                8,
                7,
                8,
                7
        );

        toolbar.addView(
                tool("⌕", "Search")
        );

        toolbar.addView(
                tool("↶", "Undo")
        );

        toolbar.addView(
                tool("↷", "Redo")
        );

        toolbar.addView(
                tool("💾", "Save")
        );

        toolbar.addView(
                tool("▶", "Build")
        );

        toolbarScroll.addView(toolbar);

        root.addView(toolbarScroll);

        // MAIN
        LinearLayout main =
                horizontal();

        // EXPLORER
        LinearLayout explorer =
                vertical();

        explorer.setPadding(
                8,
                12,
                8,
                12
        );

        explorer.setBackgroundColor(PANEL);

        TextView explorerTitle =
                text(
                        "PROJECT",
                        10,
                        MUTED
                );

        explorerTitle.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        explorer.addView(explorerTitle);

        explorer.addView(
                treeItem(
                        "📁 app",
                        0
                )
        );

        TextView manifest =
                treeItem(
                        "📄 AndroidManifest.xml",
                        1
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

        TextView java =
                treeItem(
                        "📄 MainActivity.java",
                        2
                );

        java.setOnClickListener(
                v -> openFile(
                        "MainActivity.java"
                )
        );

        explorer.addView(java);

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
                        190,
                        -1
                )
        );

        // EDITOR
        LinearLayout editorArea =
                vertical();

        editorArea.setBackgroundColor(BG);

        HorizontalScrollView tabsScroll =
                new HorizontalScrollView(this);

        tabsScroll.setHorizontalScrollBarEnabled(false);

        LinearLayout tabs =
                horizontal();

        tabs.setPadding(
                5,
                5,
                5,
                5
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

        editor =
                new EditText(this);

        editor.setGravity(
                Gravity.TOP | Gravity.START
        );

        editor.setTextSize(13);

        editor.setTextColor(
                Color.rgb(
                        225,
                        228,
                        235
                )
        );

        editor.setHintTextColor(MUTED);

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

        editor.setBackgroundColor(BG);

        loadCurrentFile();

        ScrollView editorScroll =
                new ScrollView(this);

        editorScroll.addView(editor);

        editorArea.addView(
                editorScroll,
                new LinearLayout.LayoutParams(
                        -1,
                        0,
                        1
                )
        );

        // STATUS
        LinearLayout status =
                horizontal();

        status.setGravity(
                Gravity.CENTER_VERTICAL
        );

        status.setPadding(
                12,
                7,
                12,
                7
        );

        TextView statusText =
                text(
                        "● Ready",
                        11,
                        GREEN
                );

        status.addView(
                statusText,
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                )
        );

        status.addView(
                text(
                        currentFile,
                        10,
                        MUTED
                )
        );

        editorArea.addView(status);

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
    // FILE OPERATIONS
    // =====================================================

    private void openFile(String file) {

        saveCurrentFile();

        currentFile = file;

        undoStack.clear();
        redoStack.clear();

        loadCurrentFile();

        Toast.makeText(
                this,
                file + " opened",
                Toast.LENGTH_SHORT
        ).show();
    }

    private File getCurrentFile() {

        if (currentFile.equals(
                "MainActivity.java"
        )) {
            return javaFile;
        }

        if (currentFile.equals(
                "AndroidManifest.xml"
        )) {
            return manifestFile;
        }

        return xmlFile;
    }

    private void loadCurrentFile() {

        if (editor == null) {
            return;
        }

        File file =
                getCurrentFile();

        if (file == null) {
            return;
        }

        String content =
                readText(file);

        editor.setText(content);

        editor.setSelection(
                editor.length()
        );
    }

    private void saveCurrentFile() {

        if (editor == null) {
            return;
        }

        File file =
                getCurrentFile();

        if (file == null) {
            return;
        }

        if (writeText(
                file,
                editor.getText().toString()
        )) {

            Toast.makeText(
                    this,
                    currentFile + " saved",
                    Toast.LENGTH_SHORT
            ).show();

        } else {

            Toast.makeText(
                    this,
                    "Save failed",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    // =====================================================
    // UNDO / REDO
    // =====================================================

    private void saveUndoState() {

        if (editor == null) {
            return;
        }

        String value =
                editor.getText().toString();

        if (undoStack.size() >= 30) {
            undoStack.remove(0);
        }

        undoStack.add(value);

        redoStack.clear();
    }

    private void undoEdit() {

        if (editor == null ||
                undoStack.isEmpty()) {

            Toast.makeText(
                    this,
                    "Nothing to undo",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        redoStack.add(
                editor.getText().toString()
        );

        String previous =
                undoStack.remove(
                        undoStack.size() - 1
                );

        editor.setText(previous);

        editor.setSelection(
                editor.length()
        );
    }

    private void redoEdit() {

        if (editor == null ||
                redoStack.isEmpty()) {

            Toast.makeText(
                    this,
                    "Nothing to redo",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        undoStack.add(
                editor.getText().toString()
        );

        String next =
                redoStack.remove(
                        redoStack.size() - 1
                );

        editor.setText(next);

        editor.setSelection(
                editor.length()
        );
    }

    // =====================================================
    // SEARCH
    // =====================================================

    private void showSearch() {

        EditText search =
                input(
                        "Search current file"
                );

        AlertDialog dialog =
                new AlertDialog.Builder(this)
                        .setTitle("Search")
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
                                        search.getText()
                                                .toString();

                                if (query.isEmpty()) {
                                    return;
                                }

                                String source =
                                        editor.getText()
                                                .toString();

                                int position =
                                        source.indexOf(
                                                query
                                        );

                                if (position >= 0) {

                                    editor.requestFocus();

                                    editor.setSelection(
                                            position,
                                            position +
                                                    query.length()
                                    );

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
    // BUILD
    // =====================================================

    private void buildProject() {

        saveCurrentFile();

        new AlertDialog.Builder(this)
                .setTitle("Build Project")
                .setMessage(
                        "Project files saved successfully.\n\n" +
                        "Build engine integration is the next stage."
                )
                .setPositiveButton(
                        "OK",
                        null
                )
                .show();
    }

    // =====================================================
    // TOOLBAR
    // =====================================================

    private TextView tool(
            String icon,
            String title
    ) {

        TextView t =
                text(
                        icon + " " + title,
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

        if (title.equals("Search")) {

            t.setOnClickListener(
                    v -> showSearch()
            );

        } else if (title.equals("Undo")) {

            t.setOnClickListener(
                    v -> undoEdit()
            );

        } else if (title.equals("Redo")) {

            t.setOnClickListener(
                    v -> redoEdit()
            );

        } else if (title.equals("Save")) {

            t.setOnClickListener(
                    v -> saveCurrentFile()
            );

        } else if (title.equals("Build")) {

            t.setOnClickListener(
                    v -> buildProject()
            );
        }

        return t;
    }

    // =====================================================
    // FILE TAB
    // =====================================================

    private TextView fileTab(
            String file
    ) {

        TextView tab =
                text(
                        file,
                        11,
                        TEXT
                );

        tab.setPadding(
                14,
                11,
                14,
                11
        );

        tab.setBackground(
                round(
                        8,
                        file.equals(currentFile)
                                ? PANEL2
                                : PANEL
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
                v -> openFile(file)
        );

        return tab;
    }

    // =====================================================
    // ACTION CARD
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

        TextView i =
                text(
                        icon,
                        27,
                        TEXT
                );

        i.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        card.addView(i);

        TextView t =
                text(
                        title,
                        17,
                        TEXT
                );

        t.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        addMargin(
                card,
                t,
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

                    if (title.equals(
                            "New Project"
                    )) {

                        showNewProjectDialog();

                    } else {

                        showOpenProjectDialog();
                    }
                }
        );

        return card;
    }

    // =====================================================
    // PROJECT CARD
    // =====================================================

    private View projectCard(
            String name,
            String pkg,
            File dir
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
                        "Android Application",
                        11,
                        MUTED
                )
        );

        card.setClickable(true);

        card.setOnClickListener(
                v -> openProject(dir)
        );

        return card;
    }

    // =====================================================
    // QUICK CARD
    // =====================================================

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

    // =====================================================
    // STATUS CARD
    // =====================================================

    private View statusCard(
            String name,
            String value,
            int color
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
                        color
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
                        color
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

    // =====================================================
    // OPEN PROJECT
    // =====================================================

    private void showOpenProjectDialog() {

        File root =
                new File(
                        getFilesDir(),
                        "projects"
                );

        if (!root.exists()) {
            root.mkdirs();
        }

        File[] dirs =
                root.listFiles();

        final ArrayList<String> names =
                new ArrayList<>();

        if (dirs != null) {

            for (File dir : dirs) {

                if (dir.isDirectory()) {
                    names.add(dir.getName());
                }
            }
        }

        if (names.isEmpty()) {

            new AlertDialog.Builder(this)
                    .setTitle("Open Project")
                    .setMessage(
                            "No saved projects found."
                    )
                    .setPositiveButton(
                            "OK",
                            null
                    )
                    .show();

            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Open Project")
                .setItems(
                        names.toArray(
                                new String[0]
                        ),
                        (dialog, which) -> {

                            File selected =
                                    new File(
                                            root,
                                            names.get(which)
                                    );

                            openProject(selected);
                        }
                )
                .setNegativeButton(
                        "Cancel",
                        null
                )
                .show();
    }

    private void openProject(File dir) {

        if (dir == null ||
                !dir.exists()) {

            Toast.makeText(
                    this,
                    "Project not found",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        currentProjectName =
                readText(
                        new File(
                                dir,
                                ".project"
                        )
                ).trim();

        currentPackage =
                readText(
                        new File(
                                dir,
                                ".package"
                        )
                ).trim();

        if (currentProjectName.isEmpty()) {
            currentProjectName =
                    dir.getName();
        }

        if (currentPackage.isEmpty()) {
            currentPackage =
                    "com.example.myapplication";
        }

        projectDir = dir;

        javaFile =
                findJavaFile(dir);

        manifestFile =
                new File(
                        dir,
                        "app/src/main/AndroidManifest.xml"
                );

        xmlFile =
                new File(
                        dir,
                        "app/src/main/res/layout/activity_main.xml"
                );

        if (javaFile == null) {

            Toast.makeText(
                    this,
                    "MainActivity.java not found",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        currentFile =
                "MainActivity.java";

        buildWorkspace();
    }

    private File findJavaFile(File dir) {

        File[] found =
                findFiles(
                        dir,
                        "MainActivity.java"
                );

        if (found.length > 0) {
            return found[0];
        }

        return null;
    }

    private File[] findFiles(
            File dir,
            String filename
    ) {

        ArrayList<File> result =
                new ArrayList<>();

        File[] files =
                dir.listFiles();

        if (files == null) {
            return new File[0];
        }

        for (File f : files) {

            if (f.isDirectory()) {

                File[] nested =
                        findFiles(
                                f,
                                filename
                        );

                for (File n : nested) {
                    result.add(n);
                }

            } else if (
                    f.getName().equals(filename)
            ) {

                result.add(f);
            }
        }

        return result.toArray(
                new File[0]
        );
    }

    // =====================================================
    // TREE
    // =====================================================

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

    // =====================================================
    // VALIDATION
    // =====================================================

    private boolean validProjectName(
            String value
    ) {

        return value.matches(
                "[A-Za-z][A-Za-z0-9_]*"
        );
    }

    private boolean validPackage(
            String value
    ) {

        return value.matches(
                "[a-zA-Z_][a-zA-Z0-9_]*" +
                "(\\.[a-zA-Z_][a-zA-Z0-9_]*)+"
        );
    }
}
