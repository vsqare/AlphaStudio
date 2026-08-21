package com.alphastudio;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    // =========================================================
    // COLORS
    // =========================================================

    private static final int BG =
            Color.rgb(15, 16, 20);

    private static final int PANEL =
            Color.rgb(24, 25, 30);

    private static final int PANEL2 =
            Color.rgb(34, 35, 42);

    private static final int PANEL3 =
            Color.rgb(43, 44, 52);

    private static final int TEXT =
            Color.rgb(245, 245, 247);

    private static final int MUTED =
            Color.rgb(150, 154, 166);

    private static final int ACCENT =
            Color.rgb(82, 120, 255);

    private static final int GREEN =
            Color.rgb(70, 190, 125);

    private static final int RED =
            Color.rgb(235, 90, 90);

    // =========================================================
    // PROJECT
    // =========================================================

    private File projectsRoot;
    private File projectDir;

    private String currentProjectName =
            "MyApplication";

    private String currentPackage =
            "com.example.myapplication";

    private File currentFile;

    private EditText editor;

    private TextView currentFileLabel;
    private TextView statusLabel;

    // =========================================================
    // EDIT HISTORY
    // =========================================================

    private final ArrayList<String> undoStack =
            new ArrayList<>();

    private final ArrayList<String> redoStack =
            new ArrayList<>();

    // =========================================================
    // EXPANDED FOLDERS
    // =========================================================

    private final Map<String, Boolean> expanded =
            new HashMap<>();

    // =========================================================
    // ACTIVITY
    // =========================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(BG);
        getWindow().setNavigationBarColor(BG);

        projectsRoot =
                new File(
                        getFilesDir(),
                        "projects"
                );

        if (!projectsRoot.exists()) {
            projectsRoot.mkdirs();
        }

        buildDashboard();
    }

    // =========================================================
    // DASHBOARD
    // =========================================================

    private void buildDashboard() {

        LinearLayout root =
                vertical();

        root.setBackgroundColor(BG);

        // HEADER
        LinearLayout header =
                horizontal();

        header.setGravity(
                Gravity.CENTER_VERTICAL
        );

        header.setPadding(
                18,
                14,
                12,
                14
        );

        header.setBackgroundColor(PANEL);

        TextView logo =
                text(
                        "A",
                        22,
                        Color.WHITE
                );

        logo.setGravity(Gravity.CENTER);

        logo.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        logo.setBackground(
                round(
                        13,
                        ACCENT
                )
        );

        header.addView(
                logo,
                new LinearLayout.LayoutParams(
                        46,
                        46
                )
        );

        LinearLayout titleBox =
                vertical();

        titleBox.setPadding(
                13,
                0,
                0,
                0
        );

        TextView title =
                text(
                        "AlphaStudio",
                        20,
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

        header.addView(
                titleBox,
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                )
        );

        TextView menu =
                text(
                        "⋮",
                        28,
                        TEXT
                );

        menu.setGravity(Gravity.CENTER);

        header.addView(
                menu,
                new LinearLayout.LayoutParams(
                        42,
                        46
                )
        );

        root.addView(header);

        // CONTENT
        ScrollView scroll =
                new ScrollView(this);

        LinearLayout content =
                vertical();

        content.setPadding(
                20,
                24,
                20,
                30
        );

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
                        "Build Android projects directly from your phone.",
                        14,
                        MUTED
                ),
                0,
                6,
                0,
                22
        );

        // ACTIONS
        LinearLayout actions =
                horizontal();

        View newProject =
                actionCard(
                        "＋",
                        "New Project",
                        "Create Android app",
                        ACCENT
                );

        View openProject =
                actionCard(
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

        a.setMargins(
                0,
                0,
                6,
                0
        );

        LinearLayout.LayoutParams b =
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                );

        b.setMargins(
                6,
                0,
                0,
                0
        );

        actions.addView(
                newProject,
                a
        );

        actions.addView(
                openProject,
                b
        );

        content.addView(actions);

        addMargin(
                content,
                sectionTitle(
                        "Recent Projects"
                ),
                0,
                30,
                0,
                12
        );

        addRecentProjects(content);

        addMargin(
                content,
                sectionTitle(
                        "Quick Actions"
                ),
                0,
                30,
                0,
                12
        );

        LinearLayout quick =
                horizontal();

        quick.addView(
                quickCard(
                        "＋",
                        "New File"
                )
        );

        quick.addView(
                quickCard(
                        "▣",
                        "Projects"
                )
        );

        quick.addView(
                quickCard(
                        "⌕",
                        "Search"
                )
        );

        HorizontalScrollView quickScroll =
                new HorizontalScrollView(this);

        quickScroll.setHorizontalScrollBarEnabled(
                false
        );

        quickScroll.addView(quick);

        content.addView(quickScroll);

        addMargin(
                content,
                sectionTitle(
                        "Environment"
                ),
                0,
                30,
                0,
                12
        );

        content.addView(
                statusCard(
                        "Android SDK",
                        "Ready"
                )
        );

        content.addView(
                statusCard(
                        "Gradle",
                        "Ready"
                )
        );

        content.addView(
                statusCard(
                        "Project Storage",
                        "Ready"
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

    // =========================================================
    // RECENT PROJECTS
    // =========================================================

    private void addRecentProjects(
            LinearLayout content
    ) {

        File[] list =
                projectsRoot.listFiles();

        if (list == null ||
                list.length == 0) {

            content.addView(
                    text(
                            "No projects yet",
                            13,
                            MUTED
                    )
            );

            return;
        }

        for (File f : list) {

            if (!f.isDirectory()) {
                continue;
            }

            String name =
                    f.getName();

            String pkg =
                    readText(
                            new File(
                                    f,
                                    ".package"
                            )
                    ).trim();

            if (pkg.isEmpty()) {
                pkg = "Unknown package";
            }

            content.addView(
                    projectCard(
                            name,
                            pkg
                    )
            );
        }
    }

    // =========================================================
    // NEW PROJECT
    // =========================================================

    private void showNewProjectDialog() {

        LinearLayout box =
                vertical();

        box.setPadding(
                25,
                5,
                25,
                5
        );

        box.addView(
                label("Project Name")
        );

        EditText name =
                input(
                        "MyApplication"
                );

        box.addView(name);

        addMargin(
                box,
                label("Package Name"),
                0,
                15,
                0,
                0
        );

        EditText pkg =
                input(
                        "com.example.myapplication"
                );

        box.addView(pkg);

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
                                        name.getText()
                                                .toString()
                                                .trim();

                                String packageName =
                                        pkg.getText()
                                                .toString()
                                                .trim();

                                if (!validProjectName(
                                        project
                                )) {

                                    name.setError(
                                            "Invalid project name"
                                    );

                                    return;
                                }

                                if (!validPackage(
                                        packageName
                                )) {

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

                                    openProject(
                                            projectDir
                                    );

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

    // =========================================================
    // CREATE PROJECT FILES
    // =========================================================

    private boolean createProjectFiles() {

        try {

            projectDir =
                    new File(
                            projectsRoot,
                            currentProjectName
                    );

            if (projectDir.exists()) {

                Toast.makeText(
                        this,
                        "Project already exists",
                        Toast.LENGTH_SHORT
                ).show();

                return false;
            }

            if (!projectDir.mkdirs()) {
                return false;
            }

            File javaDir =
                    new File(
                            projectDir,
                            "app/src/main/java/" +
                                    currentPackage.replace(
                                            ".",
                                            "/"
                                    )
                    );

            File layoutDir =
                    new File(
                            projectDir,
                            "app/src/main/res/layout"
                    );

            File valuesDir =
                    new File(
                            projectDir,
                            "app/src/main/res/values"
                    );

            if (!javaDir.mkdirs()) {
                return false;
            }

            if (!layoutDir.mkdirs()) {
                return false;
            }

            if (!valuesDir.mkdirs()) {
                return false;
            }

            File java =
                    new File(
                            javaDir,
                            "MainActivity.java"
                    );

            File manifest =
                    new File(
                            projectDir,
                            "app/src/main/AndroidManifest.xml"
                    );

            File xml =
                    new File(
                            layoutDir,
                            "activity_main.xml"
                    );

            File strings =
                    new File(
                            valuesDir,
                            "strings.xml"
                    );

            File appGradle =
                    new File(
                            projectDir,
                            "app/build.gradle"
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

            writeText(
                    java,
                    generateJava()
            );

            writeText(
                    manifest,
                    generateManifest()
            );

            writeText(
                    xml,
                    generateXml()
            );

            writeText(
                    strings,
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
                    new File(
                            projectDir,
                            ".project"
                    ),
                    currentProjectName
            );

            writeText(
                    new File(
                            projectDir,
                            ".package"
                    ),
                    currentPackage
            );

            return true;

        } catch (Exception e) {

            return false;
        }
    }

    // =========================================================
    // GENERATED JAVA
    // =========================================================

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

    // =========================================================
    // MANIFEST
    // =========================================================

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

    // =========================================================
    // XML
    // =========================================================

    private String generateXml() {

        return
                "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n\n" +

                "<LinearLayout\n" +

                "    xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +

                "    android:layout_width=\"match_parent\"\n" +

                "    android:layout_height=\"match_parent\"\n" +

                "    android:gravity=\"center\"\n" +

                "    android:orientation=\"vertical\"\n" +

                "    android:padding=\"24dp\">\n\n" +

                "    <TextView\n" +

                "        android:layout_width=\"wrap_content\"\n" +

                "        android:layout_height=\"wrap_content\"\n" +

                "        android:text=\"Hello AlphaStudio\"\n" +

                "        android:textSize=\"24sp\" />\n\n" +

                "</LinearLayout>\n";
    }

    // =========================================================
    // STRINGS
    // =========================================================

    private String generateStrings() {

        return
                "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n\n" +

                "<resources>\n" +

                "    <string name=\"app_name\">" +
                currentProjectName +
                "</string>\n" +

                "</resources>\n";
    }

    // =========================================================
    // APP GRADLE
    // =========================================================

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

    // =========================================================
    // ROOT GRADLE
    // =========================================================

    private String generateRootGradle() {

        return
                "plugins {\n" +

                "    id 'com.android.application' version '8.7.3' apply false\n" +

                "}\n";
    }

    // =========================================================
    // SETTINGS
    // =========================================================

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
                "'\n\n" +

                "include ':app'\n";
    }

    // =========================================================
    // OPEN PROJECT
    // =========================================================

    private void openProject(
            File dir
    ) {

        if (dir == null ||
                !dir.exists()) {

            Toast.makeText(
                    this,
                    "Project not found",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        projectDir = dir;

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

        currentFile = null;

        buildWorkspace();
    }

    // =========================================================
    // OPEN PROJECT DIALOG
    // =========================================================

    private void showOpenProjectDialog() {

        File[] dirs =
                projectsRoot.listFiles();

        final ArrayList<String> names =
                new ArrayList<>();

        if (dirs != null) {

            for (File f : dirs) {

                if (f.isDirectory()) {
                    names.add(f.getName());
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

        Collections.sort(names);

        new AlertDialog.Builder(this)
                .setTitle("Open Project")
                .setItems(
                        names.toArray(
                                new String[0]
                        ),
                        (dialog, which) -> {

                            openProject(
                                    new File(
                                            projectsRoot,
                                            names.get(which)
                                    )
                            );
                        }
                )
                .setNegativeButton(
                        "Cancel",
                        null
                )
                .show();
    }

    // =========================================================
    // WORKSPACE
    // =========================================================

    private void buildWorkspace() {

        LinearLayout root =
                vertical();

        root.setBackgroundColor(BG);

        // HEADER
        LinearLayout header =
                horizontal();

        header.setGravity(
                Gravity.CENTER_VERTICAL
        );

        header.setPadding(
                5,
                5,
                5,
                5
        );

        header.setBackgroundColor(PANEL);

        TextView back =
                text(
                        "‹",
                        35,
                        TEXT
                );

        back.setGravity(
                Gravity.CENTER
        );

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

        TextView newFile =
                headerButton(
                        "＋"
                );

        newFile.setOnClickListener(
                v -> showNewFileDialog()
        );

        header.addView(
                newFile
        );

        TextView newFolder =
                headerButton(
                        "📁"
                );

        newFolder.setOnClickListener(
                v -> showNewFolderDialog()
        );

        header.addView(
                newFolder
        );

        TextView save =
                headerButton(
                        "💾"
                );

        save.setOnClickListener(
                v -> saveCurrentFile()
        );

        header.addView(save);

        root.addView(header);

        // TOOLBAR
        HorizontalScrollView toolScroll =
                new HorizontalScrollView(this);

        toolScroll.setHorizontalScrollBarEnabled(
                false
        );

        LinearLayout tools =
                horizontal();

        tools.setPadding(
                7,
                7,
                7,
                7
        );

        tools.addView(
                toolButton(
                        "⌕",
                        "Search"
                )
        );

        tools.addView(
                toolButton(
                        "↶",
                        "Undo"
                )
        );

        tools.addView(
                toolButton(
                        "↷",
                        "Redo"
                )
        );

        tools.addView(
                toolButton(
                        "💾",
                        "Save"
                )
        );

        tools.addView(
                toolButton(
                        "↻",
                        "Refresh"
                )
        );

        tools.addView(
                toolButton(
                        "⋮",
                        "More"
                )
        );

        toolScroll.addView(tools);

        root.addView(toolScroll);

        // MAIN
        LinearLayout main =
                horizontal();

        // EXPLORER
        LinearLayout explorer =
                vertical();

        explorer.setBackgroundColor(PANEL);

        TextView explorerTitle =
                text(
                        "  PROJECT EXPLORER",
                        10,
                        MUTED
                );

        explorerTitle.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        explorer.addView(
                explorerTitle
        );

        ScrollView treeScroll =
                new ScrollView(this);

        LinearLayout tree =
                vertical();

        addProjectTree(
                tree,
                projectDir,
                0
        );

        treeScroll.addView(tree);

        explorer.addView(
                treeScroll,
                new LinearLayout.LayoutParams(
                        -1,
                        0,
                        1
                )
        );

        main.addView(
                explorer,
                new LinearLayout.LayoutParams(
                        215,
                        -1
                )
        );

        // EDITOR
        LinearLayout editorArea =
                vertical();

        editorArea.setBackgroundColor(BG);

        currentFileLabel =
                text(
                        "No file selected",
                        12,
                        TEXT
                );

        currentFileLabel.setPadding(
                14,
                11,
                14,
                11
        );

        currentFileLabel.setBackgroundColor(
                PANEL2
        );

        editorArea.addView(
                currentFileLabel
        );

        editor =
                new EditText(this);

        editor.setGravity(
                Gravity.TOP |
                Gravity.START
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
                14,
                14,
                40
        );

        editor.setBackgroundColor(BG);

        if (currentFile != null) {
            loadCurrentFile();
        } else {
            editor.setHint(
                    "Select a file from Project Explorer"
            );
        }

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

        statusLabel =
                text(
                        "● Ready",
                        11,
                        GREEN
                );

        statusLabel.setPadding(
                12,
                7,
                12,
                7
        );

        statusLabel.setBackgroundColor(
                PANEL
        );

        editorArea.addView(
                statusLabel
        );

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

    // =========================================================
    // PROJECT TREE
    // =========================================================

    private void addProjectTree(
            LinearLayout parent,
            File dir,
            int level
    ) {

        if (dir == null ||
                !dir.exists()) {
            return;
        }

        File[] files =
                dir.listFiles();

        if (files == null) {
            return;
        }

        ArrayList<File> folders =
                new ArrayList<>();

        ArrayList<File> normalFiles =
                new ArrayList<>();

        for (File f : files) {

            if (f.isDirectory()) {
                folders.add(f);
            } else {
                normalFiles.add(f);
            }
        }

        Comparator<File> comparator =
                (a, b) ->
                        a.getName()
                                .compareToIgnoreCase(
                                        b.getName()
                                );

        Collections.sort(
                folders,
                comparator
        );

        Collections.sort(
                normalFiles,
                comparator
        );

        for (File folder : folders) {

            if (folder.getName().equals(".git")) {
                continue;
            }

            addFolderItem(
                    parent,
                    folder,
                    level
            );
        }

        for (File file : normalFiles) {

            if (file.getName().equals(".project") ||
                    file.getName().equals(".package")) {
                continue;
            }

            addFileItem(
                    parent,
                    file,
                    level
            );
        }
    }

    // =========================================================
    // FOLDER ITEM
    // =========================================================

    private void addFolderItem(
            LinearLayout parent,
            File folder,
            int level
    ) {

        boolean isExpanded =
                expanded.containsKey(
                        folder.getAbsolutePath()
                ) &&
                Boolean.TRUE.equals(
                        expanded.get(
                                folder.getAbsolutePath()
                        )
                );

        TextView item =
                text(
                        (isExpanded ? "▾ " : "▸ ") +
                                "📁 " +
                                folder.getName(),
                        12,
                        TEXT
                );

        item.setPadding(
                10 + level * 14,
                10,
                5,
                10
        );

        item.setBackgroundColor(
                isExpanded
                        ? PANEL2
                        : PANEL
        );

        item.setOnClickListener(
                v -> {

                    expanded.put(
                            folder.getAbsolutePath(),
                            !isExpanded
                    );

                    buildWorkspace();
                }
        );

        item.setOnLongClickListener(
                v -> {

                    showFileMenu(folder);
                    return true;
                }
        );

        parent.addView(item);

        if (isExpanded) {

            addProjectTree(
                    parent,
                    folder,
                    level + 1
            );
        }
    }

    // =========================================================
    // FILE ITEM
    // =========================================================

    private void addFileItem(
            LinearLayout parent,
            File file,
            int level
    ) {

        String icon =
                getFileIcon(
                        file.getName()
                );

        TextView item =
                text(
                        icon +
                                " " +
                                file.getName(),
                        12,
                        TEXT
                );

        item.setPadding(
                10 + level * 14,
                9,
                5,
                9
        );

        if (currentFile != null &&
                currentFile.equals(file)) {

            item.setTextColor(
                    Color.WHITE
            );

            item.setBackgroundColor(
                    PANEL3
            );
        }

        item.setOnClickListener(
                v -> openFile(file)
        );

        item.setOnLongClickListener(
                v -> {

                    showFileMenu(file);
                    return true;
                }
        );

        parent.addView(item);
    }

    // =========================================================
    // OPEN FILE
    // =========================================================

    private void openFile(
            File file
    ) {

        if (file == null ||
                !file.isFile()) {
            return;
        }

        if (currentFile != null &&
                editor != null) {

            saveCurrentFile();
        }

        currentFile = file;

        undoStack.clear();
        redoStack.clear();

        loadCurrentFile();

        buildWorkspace();
    }

    // =========================================================
    // LOAD FILE
    // =========================================================

    private void loadCurrentFile() {

        if (editor == null ||
                currentFile == null) {
            return;
        }

        String content =
                readText(currentFile);

        editor.setText(content);

        editor.setSelection(
                editor.length()
        );

        if (currentFileLabel != null) {

            currentFileLabel.setText(
                    "📄 " +
                    currentFile.getName()
            );
        }

        if (statusLabel != null) {

            statusLabel.setText(
                    "● Editing  " +
                    currentFile.getName()
            );
        }
    }

    // =========================================================
    // SAVE
    // =========================================================

    private void saveCurrentFile() {

        if (editor == null ||
                currentFile == null) {
            return;
        }

        if (writeText(
                currentFile,
                editor.getText()
                        .toString()
        )) {

            setStatus(
                    "● Saved",
                    GREEN
            );

            Toast.makeText(
                    this,
                    currentFile.getName() +
                            " saved",
                    Toast.LENGTH_SHORT
            ).show();

        } else {

            setStatus(
                    "● Save failed",
                    RED
            );
        }
    }

    // =========================================================
    // NEW FILE
    // =========================================================

    private void showNewFileDialog() {

        if (projectDir == null) {
            return;
        }

        LinearLayout box =
                vertical();

        box.setPadding(
                22,
                5,
                22,
                5
        );

        box.addView(
                label(
                        "File name"
                )
        );

        EditText name =
                input(
                        "Example.java"
                );

        box.addView(name);

        TextView info =
                text(
                        "File will be created inside the project root.",
                        11,
                        MUTED
                );

        addMargin(
                box,
                info,
                0,
                7,
                0,
                0
        );

        AlertDialog dialog =
                new AlertDialog.Builder(this)
                        .setTitle(
                                "New File"
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

                    dialog.getButton(
                            AlertDialog.BUTTON_POSITIVE
                    ).setOnClickListener(
                            v -> {

                                String filename =
                                        name.getText()
                                                .toString()
                                                .trim();

                                if (!validFileName(
                                        filename
                                )) {

                                    name.setError(
                                            "Invalid file name"
                                    );

                                    return;
                                }

                                File file =
                                        new File(
                                                projectDir,
                                                filename
                                        );

                                if (file.exists()) {

                                    name.setError(
                                            "File already exists"
                                    );

                                    return;
                                }

                                if (writeText(
                                        file,
                                        defaultFileContent(
                                                filename
                                        )
                                )) {

                                    dialog.dismiss();

                                    openFile(file);

                                    Toast.makeText(
                                            this,
                                            "File created",
                                            Toast.LENGTH_SHORT
                                    ).show();

                                } else {

                                    Toast.makeText(
                                            this,
                                            "Could not create file",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }
                            }
                    );
                }
        );

        dialog.show();
    }

    // =========================================================
    // NEW FOLDER
    // =========================================================

    private void showNewFolderDialog() {

        if (projectDir == null) {
            return;
        }

        EditText input =
                input(
                        "Folder name"
                );

        LinearLayout box =
                vertical();

        box.setPadding(
                22,
                5,
                22,
                5
        );

        box.addView(input);

        AlertDialog dialog =
                new AlertDialog.Builder(this)
                        .setTitle(
                                "New Folder"
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

                    dialog.getButton(
                            AlertDialog.BUTTON_POSITIVE
                    ).setOnClickListener(
                            v -> {

                                String name =
                                        input.getText()
                                                .toString()
                                                .trim();

                                if (!validFolderName(
                                        name
                                )) {

                                    input.setError(
                                            "Invalid folder name"
                                    );

                                    return;
                                }

                                File folder =
                                        new File(
                                                projectDir,
                                                name
                                        );

                                if (folder.exists()) {

                                    input.setError(
                                            "Already exists"
                                    );

                                    return;
                                }

                                if (folder.mkdirs()) {

                                    dialog.dismiss();

                                    expanded.put(
                                            projectDir
                                                    .getAbsolutePath(),
                                            true
                                    );

                                    buildWorkspace();

                                    Toast.makeText(
                                            this,
                                            "Folder created",
                                            Toast.LENGTH_SHORT
                                    ).show();

                                } else {

                                    Toast.makeText(
                                            this,
                                            "Folder creation failed",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }
                            }
                    );
                }
        );

        dialog.show();
    }

    // =========================================================
    // FILE MENU
    // =========================================================

    private void showFileMenu(
            File file
    ) {

        String[] options = {

                "Open",

                "Rename",

                "Delete",

                "Copy Path"
        };

        new AlertDialog.Builder(this)
                .setTitle(
                        file.getName()
                )
                .setItems(
                        options,
                        (dialog, which) -> {

                            if (which == 0) {

                                if (file.isFile()) {
                                    openFile(file);
                                }

                            } else if (which == 1) {

                                showRenameDialog(file);

                            } else if (which == 2) {

                                showDeleteDialog(file);

                            } else {

                                Toast.makeText(
                                        this,
                                        file.getAbsolutePath(),
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        }
                )
                .show();
    }

    // =========================================================
    // RENAME
    // =========================================================

    private void showRenameDialog(
            File oldFile
    ) {

        EditText input =
                input(
                        oldFile.getName()
                );

        LinearLayout box =
                vertical();

        box.setPadding(
                22,
                5,
                22,
                5
        );

        box.addView(input);

        AlertDialog dialog =
                new AlertDialog.Builder(this)
                        .setTitle(
                                "Rename"
                        )
                        .setView(box)
                        .setNegativeButton(
                                "Cancel",
                                null
                        )
                        .setPositiveButton(
                                "Rename",
                                null
                        )
                        .create();

        dialog.setOnShowListener(
                d -> {

                    dialog.getButton(
                            AlertDialog.BUTTON_POSITIVE
                    ).setOnClickListener(
                            v -> {

                                String newName =
                                        input.getText()
                                                .toString()
                                                .trim();

                                if (!validFileName(
                                        newName
                                )) {

                                    input.setError(
                                            "Invalid name"
                                    );

                                    return;
                                }

                                File newFile =
                                        new File(
                                                oldFile.getParentFile(),
                                                newName
                                        );

                                if (newFile.exists()) {

                                    input.setError(
                                            "Already exists"
                                    );

                                    return;
                                }

                                if (oldFile.renameTo(
                                        newFile
                                )) {

                                    if (currentFile != null &&
                                            currentFile.equals(
                                                    oldFile
                                            )) {

                                        currentFile =
                                                newFile;
                                    }

                                    dialog.dismiss();

                                    buildWorkspace();

                                    Toast.makeText(
                                            this,
                                            "Renamed",
                                            Toast.LENGTH_SHORT
                                    ).show();

                                } else {

                                    Toast.makeText(
                                            this,
                                            "Rename failed",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }
                            }
                    );
                }
        );

        dialog.show();
    }

    // =========================================================
    // DELETE
    // =========================================================

    private void showDeleteDialog(
            File file
    ) {

        new AlertDialog.Builder(this)
                .setTitle(
                        "Delete"
                )
                .setMessage(
                        "Delete \"" +
                                file.getName() +
                                "\"?"
                )
                .setNegativeButton(
                        "Cancel",
                        null
                )
                .setPositiveButton(
                        "Delete",
                        (dialog, which) -> {

                            if (deleteRecursive(file)) {

                                if (currentFile != null &&
                                        currentFile.equals(
                                                file
                                        )) {

                                    currentFile = null;
                                }

                                buildWorkspace();

                                Toast.makeText(
                                        this,
                                        "Deleted",
                                        Toast.LENGTH_SHORT
                                ).show();

                            } else {

                                Toast.makeText(
                                        this,
                                        "Delete failed",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        }
                )
                .show();
    }

    // =========================================================
    // DELETE RECURSIVE
    // =========================================================

    private boolean deleteRecursive(
            File file
    ) {

        if (file == null ||
                !file.exists()) {
            return false;
        }

        if (file.isDirectory()) {

            File[] children =
                    file.listFiles();

            if (children != null) {

                for (File child : children) {

                    if (!deleteRecursive(
                            child
                    )) {
                        return false;
                    }
                }
            }
        }

        return file.delete();
    }

    // =========================================================
    // SEARCH
    // =========================================================

    private void showSearch() {

        if (editor == null ||
                currentFile == null) {

            Toast.makeText(
                    this,
                    "Open a file first",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        EditText search =
                input(
                        "Search text"
                );

        LinearLayout box =
                vertical();

        box.setPadding(
                22,
                5,
                22,
                5
        );

        box.addView(search);

        AlertDialog dialog =
                new AlertDialog.Builder(this)
                        .setTitle(
                                "Search"
                        )
                        .setView(box)
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

                    dialog.getButton(
                            AlertDialog.BUTTON_POSITIVE
                    ).setOnClickListener(
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

                                    dialog.dismiss();

                                } else {

                                    Toast.makeText(
                                            this,
                                            "Text not found",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }
                            }
                    );
                }
        );

        dialog.show();
    }

    // =========================================================
    // UNDO
    // =========================================================

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
                editor.getText()
                        .toString()
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

    // =========================================================
    // REDO
    // =========================================================

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
                editor.getText()
                        .toString()
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

    // =========================================================
    // TOOL BUTTON
    // =========================================================

    private TextView toolButton(
            String icon,
            String name
    ) {

        TextView t =
                text(
                        icon + " " + name,
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

        if (name.equals("Search")) {

            t.setOnClickListener(
                    v -> showSearch()
            );

        } else if (name.equals("Undo")) {

            t.setOnClickListener(
                    v -> undoEdit()
            );

        } else if (name.equals("Redo")) {

            t.setOnClickListener(
                    v -> redoEdit()
            );

        } else if (name.equals("Save")) {

            t.setOnClickListener(
                    v -> saveCurrentFile()
            );

        } else if (name.equals("Refresh")) {

            t.setOnClickListener(
                    v -> buildWorkspace()
            );

        } else {

            t.setOnClickListener(
                    v -> showMoreMenu()
            );
        }

        return t;
    }

    // =========================================================
    // MORE MENU
    // =========================================================

    private void showMoreMenu() {

        String[] items = {

                "Project Information",

                "New File",

                "New Folder",

                "Refresh Explorer"
        };

        new AlertDialog.Builder(this)
                .setTitle(
                        "AlphaStudio"
                )
                .setItems(
                        items,
                        (dialog, which) -> {

                            if (which == 0) {

                                showProjectInfo();

                            } else if (which == 1) {

                                showNewFileDialog();

                            } else if (which == 2) {

                                showNewFolderDialog();

                            } else {

                                buildWorkspace();
                            }
                        }
                )
                .show();
    }

    // =========================================================
    // PROJECT INFO
    // =========================================================

    private void showProjectInfo() {

        int files =
                countFiles(
                        projectDir
                );

        new AlertDialog.Builder(this)
                .setTitle(
                        currentProjectName
                )
                .setMessage(
                        "Package: " +
                                currentPackage +
                                "\n\nFiles: " +
                                files +
                                "\n\nLocation:\n" +
                                projectDir
                                        .getAbsolutePath()
                )
                .setPositiveButton(
                        "OK",
                        null
                )
                .show();
    }

    // =========================================================
    // COUNT FILES
    // =========================================================

    private int countFiles(
            File dir
    ) {

        if (dir == null ||
                !dir.exists()) {
            return 0;
        }

        int count = 0;

        File[] files =
                dir.listFiles();

        if (files == null) {
            return 0;
        }

        for (File f : files) {

            if (f.isDirectory()) {

                count += countFiles(f);

            } else {

                count++;
            }
        }

        return count;
    }

    // =========================================================
    // DEFAULT FILE CONTENT
    // =========================================================

    private String defaultFileContent(
            String filename
    ) {

        if (filename.endsWith(".java")) {

            return
                    "package " +
                    currentPackage +
                    ";\n\n" +

                    "public class " +
                    filename.substring(
                            0,
                            filename.length() - 5
                    ) +
                    " {\n\n" +

                    "}\n";
        }

        if (filename.endsWith(".xml")) {

            return
                    "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n\n" +
                    "<LinearLayout\n" +
                    "    xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
                    "    android:layout_width=\"match_parent\"\n" +
                    "    android:layout_height=\"match_parent\"\n" +
                    "    android:orientation=\"vertical\">\n\n" +
                    "</LinearLayout>\n";
        }

        if (filename.endsWith(".gradle")) {

            return
                    "plugins {\n" +
                    "}\n";
        }

        if (filename.endsWith(".json")) {

            return
                    "{\n\n}\n";
        }

        return "";
    }

    // =========================================================
    // VALIDATION
    // =========================================================

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

    private boolean validFileName(
            String value
    ) {

        if (value.isEmpty()) {
            return false;
        }

        if (value.contains("/") ||
                value.contains("\\") ||
                value.contains(":")) {
            return false;
        }

        return !value.equals(".") &&
                !value.equals("..");
    }

    private boolean validFolderName(
            String value
    ) {

        return validFileName(value);
    }

    // =========================================================
    // FILE ICON
    // =========================================================

    private String getFileIcon(
            String name
    ) {

        String lower =
                name.toLowerCase();

        if (lower.endsWith(".java")) {
            return "☕";
        }

        if (lower.endsWith(".xml")) {
            return "◈";
        }

        if (lower.endsWith(".gradle")) {
            return "⚙";
        }

        if (lower.endsWith(".json")) {
            return "{}";
        }

        if (lower.endsWith(".kt")) {
            return "K";
        }

        if (lower.endsWith(".png") ||
                lower.endsWith(".jpg") ||
                lower.endsWith(".jpeg")) {
            return "▧";
        }

        if (lower.endsWith(".txt")) {
            return "▤";
        }

        return "📄";
    }

    // =========================================================
    // HEADER BUTTON
    // =========================================================

    private TextView headerButton(
            String value
    ) {

        TextView t =
                text(
                        value,
                        18,
                        TEXT
                );

        t.setGravity(
                Gravity.CENTER
        );

        t.setBackground(
                round(
                        9,
                        PANEL2
                )
        );

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        43,
                        43
                );

        p.setMargins(
                3,
                0,
                3,
                0
        );

        t.setLayoutParams(p);

        return t;
    }

    // =========================================================
    // DASHBOARD ACTION CARD
    // =========================================================

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

    // =========================================================
    // PROJECT CARD
    // =========================================================

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
                        "▣ " + name,
                        16,
                        TEXT
                );

        n.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        card.addView(n);

        card.addView(
                text(
                        pkg,
                        12,
                        MUTED
                )
        );

        card.addView(
                text(
                        "Android Application",
                        11,
                        MUTED
                )
        );

        card.setOnClickListener(
                v -> openProject(
                        new File(
                                projectsRoot,
                                name
                        )
                )
        );

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

    // =========================================================
    // QUICK CARD
    // =========================================================

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

        t.setOnClickListener(
                v -> {

                    if (title.equals(
                            "New File"
                    )) {

                        if (projectDir != null) {
                            showNewFileDialog();
                        } else {
                            showOpenProjectDialog();
                        }

                    } else {

                        showOpenProjectDialog();
                    }
                }
        );

        return t;
    }

    // =========================================================
    // STATUS CARD
    // =========================================================

    private View statusCard(
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

        TextView status =
                text(
                        value,
                        13,
                        GREEN
                );

        status.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        card.addView(status);

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

    // =========================================================
    // SECTION TITLE
    // =========================================================

    private TextView sectionTitle(
            String value
    ) {

        TextView t =
                text(
                        value,
                        20,
                        TEXT
                );

        t.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        return t;
    }

    // =========================================================
    // LABEL
    // =========================================================

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

    // =========================================================
    // INPUT
    // =========================================================

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

    // =========================================================
    // TEXT
    // =========================================================

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

    // =========================================================
    // LAYOUT HELPERS
    // =========================================================

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

    // =========================================================
    // BACKGROUND
    // =========================================================

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

    // =========================================================
    // MARGIN
    // =========================================================

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

    // =========================================================
    // STATUS
    // =========================================================

    private void setStatus(
            String value,
            int color
    ) {

        if (statusLabel != null) {

            statusLabel.setText(value);

            statusLabel.setTextColor(
                    color
            );
        }
    }

    // =========================================================
    // WRITE FILE
    // =========================================================

    private boolean writeText(
            File file,
            String content
    ) {

        try {

            File parent =
                    file.getParentFile();

            if (parent != null &&
                    !parent.exists()) {

                parent.mkdirs();
            }

            FileWriter writer =
                    new FileWriter(file);

            writer.write(content);

            writer.flush();

            writer.close();

            return true;

        } catch (IOException e) {

            return false;
        }
    }

    // =========================================================
    // READ FILE
    // =========================================================

    private String readText(
            File file
    ) {

        if (file == null ||
                !file.exists()) {

            return "";
        }

        StringBuilder result =
                new StringBuilder();

        try {

            BufferedReader reader =
                    new BufferedReader(
                            new FileReader(file)
                    );

            String line;

            while (
                    (line =
                            reader.readLine()) != null
            ) {

                result.append(line);

                result.append("\n");
            }

            reader.close();

        } catch (IOException e) {

            return "";
        }

        return result.toString();
    }
}
