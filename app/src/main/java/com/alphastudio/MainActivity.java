package com.alphastudio;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.ArrayList;

public class MainActivity extends Activity {

    private static final int BG = Color.rgb(16,17,20);
    private static final int PANEL = Color.rgb(25,26,31);
    private static final int PANEL2 = Color.rgb(35,36,43);
    private static final int TEXT = Color.rgb(245,245,247);
    private static final int MUTED = Color.rgb(155,158,170);
    private static final int ACCENT = Color.rgb(82,120,255);
    private static final int GREEN = Color.rgb(70,190,125);
    private static final int RED = Color.rgb(235,90,90);

    private String currentProjectName = "MyApplication";
    private String currentPackage = "com.example.myapplication";
    private String currentFile = "MainActivity.java";

    private File projectDir, javaFile, manifestFile, xmlFile;
    private EditText editor;
    private static final int EXPORT_REQUEST = 4201;
    private File pendingExportZip;

    private final ArrayList<String> undoStack = new ArrayList<>();
    private final ArrayList<String> redoStack = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(BG);
        getWindow().setNavigationBarColor(BG);
        buildDashboard();
    }

    private void buildDashboard() {
        LinearLayout root = vertical();
        root.setBackgroundColor(BG);

        LinearLayout top = horizontal();
        top.setGravity(Gravity.CENTER_VERTICAL);
        top.setPadding(18,14,12,14);
        top.setBackgroundColor(PANEL);

        TextView logo = text("A",21,Color.WHITE);
        logo.setGravity(Gravity.CENTER);
        logo.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        logo.setBackground(round(12,ACCENT));
        top.addView(logo,new LinearLayout.LayoutParams(46,46));

        LinearLayout titleBox = vertical();
        titleBox.setPadding(13,0,0,0);
        TextView title = text("AlphaStudio",19,TEXT);
        title.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        titleBox.addView(title);
        titleBox.addView(text("Professional Android IDE",12,MUTED));
        top.addView(titleBox,new LinearLayout.LayoutParams(0,-2,1));

        TextView menu = text("⋮",28,TEXT);
        menu.setGravity(Gravity.CENTER);
        top.addView(menu,new LinearLayout.LayoutParams(42,46));
        root.addView(top);

        ScrollView scroll = new ScrollView(this);
        LinearLayout content = vertical();
        content.setPadding(20,24,20,30);

        TextView welcome = text("Welcome back",28,TEXT);
        welcome.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        content.addView(welcome);

        addMargin(content,text("Build and manage Android projects directly from your phone.",14,MUTED),0,6,0,22);

        LinearLayout actions = horizontal();
        View newProject = actionCard("＋","New Project","Create Android app",ACCENT);
        View openProject = actionCard("↗","Open Project","Open saved project",PANEL2);

        LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(0,-2,1);
        p1.setMargins(0,0,6,0);
        LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(0,-2,1);
        p2.setMargins(6,0,0,0);
        actions.addView(newProject,p1);
        actions.addView(openProject,p2);
        content.addView(actions);

        addMargin(content,sectionTitle("Recent Projects"),0,30,0,12);
        addRecentProjects(content);

        addMargin(content,sectionTitle("Quick Actions"),0,30,0,12);
        HorizontalScrollView quickScroll = new HorizontalScrollView(this);
        quickScroll.setHorizontalScrollBarEnabled(false);
        LinearLayout quickRow = horizontal();

        TextView build = quickCard("▣","Build APK");
        build.setOnClickListener(v -> {
            Toast.makeText(this,"Open a project first, then use Build.",Toast.LENGTH_SHORT).show();
        });
        quickRow.addView(build);

        TextView github = quickCard("⌘","GitHub");
        github.setOnClickListener(v -> Toast.makeText(this,"GitHub workflow is managed from the repository.",Toast.LENGTH_SHORT).show());
        quickRow.addView(github);

        TextView sdk = quickCard("⚙","SDK Manager");
        sdk.setOnClickListener(v -> Toast.makeText(this,"Android SDK environment is configured by the build system.",Toast.LENGTH_SHORT).show());
        quickRow.addView(sdk);

        TextView terminal = quickCard("▤","Terminal");
        terminal.setOnClickListener(v -> Toast.makeText(this,"Use your Android terminal for Gradle commands.",Toast.LENGTH_SHORT).show());
        quickRow.addView(terminal);

        quickScroll.addView(quickRow);
        content.addView(quickScroll);

        addMargin(content,sectionTitle("Environment"),0,30,0,12);
        content.addView(statusCard("Android SDK","Ready"));
        content.addView(statusCard("Gradle","Ready"));
        content.addView(statusCard("Project Storage","Ready"));

        scroll.addView(content);
        root.addView(scroll,new LinearLayout.LayoutParams(-1,0,1));
        setContentView(root);
    }

    private void addRecentProjects(LinearLayout content) {
        File root = new File(getFilesDir(),"projects");
        if (!root.exists()) root.mkdirs();
        File[] projects = root.listFiles();

        if (projects == null || projects.length == 0) {
            content.addView(text("No projects yet",13,MUTED));
            return;
        }

        for (File dir : projects) {
            if (!dir.isDirectory()) continue;
            String name = dir.getName();
            String pkg = readText(new File(dir,".package")).trim();
            if (pkg.isEmpty()) pkg = "Unknown package";
            content.addView(projectCard(name,pkg));
        }
    }

    private void showNewProjectDialog() {
        LinearLayout box = vertical();
        box.setPadding(25,5,25,5);

        box.addView(label("Project Name"));
        EditText name = input("MyApplication");
        box.addView(name);

        addMargin(box,label("Package Name"),0,16,0,0);
        EditText pkg = input("com.example.myapplication");
        box.addView(pkg);

        addMargin(box,label("Language"),0,16,0,0);
        Spinner language = spinner(new String[]{"Java","Kotlin"});
        box.addView(language);

        addMargin(box,label("Minimum SDK"),0,16,0,0);
        Spinner sdk = spinner(new String[]{
                "Android 7.0 (API 24)",
                "Android 8.0 (API 26)",
                "Android 10 (API 29)",
                "Android 12 (API 31)",
                "Android 15 (API 35)"
        });
        box.addView(sdk);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Create New Project")
                .setView(box)
                .setNegativeButton("Cancel",null)
                .setPositiveButton("Create",null)
                .create();

        dialog.setOnShowListener(d -> {
            Button create = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            create.setOnClickListener(v -> {
                String project = name.getText().toString().trim();
                String packageName = pkg.getText().toString().trim();

                if (!validProjectName(project)) {
                    name.setError("Use letters, numbers or _");
                    return;
                }
                if (!validPackage(packageName)) {
                    pkg.setError("Example: com.example.myapp");
                    return;
                }

                currentProjectName = project;
                currentPackage = packageName;

                if (createProjectFiles()) {
                    dialog.dismiss();
                    Toast.makeText(this,"Project created",Toast.LENGTH_SHORT).show();
                    buildWorkspace();
                } else {
                    Toast.makeText(this,"Project creation failed",Toast.LENGTH_LONG).show();
                }
            });
        });
        dialog.show();
    }

    private boolean createProjectFiles() {
        try {
            File projectsRoot = new File(getFilesDir(),"projects");
            if (!projectsRoot.exists() && !projectsRoot.mkdirs()) return false;

            projectDir = new File(projectsRoot,currentProjectName);
            if (!projectDir.exists() && !projectDir.mkdirs()) return false;

            File app = new File(projectDir,"app");
            File src = new File(app,"src/main/java/"+currentPackage.replace(".","/"));
            File res = new File(app,"src/main/res/layout");
            File values = new File(app,"src/main/res/values");

            if (!src.mkdirs() && !src.exists()) return false;
            if (!res.mkdirs() && !res.exists()) return false;
            if (!values.mkdirs() && !values.exists()) return false;

            javaFile = new File(src,"MainActivity.java");
            manifestFile = new File(app,"src/main/AndroidManifest.xml");
            xmlFile = new File(res,"activity_main.xml");

            writeText(javaFile,generateJava());
            writeText(manifestFile,generateManifest());
            writeText(xmlFile,generateXml());
            writeText(new File(values,"strings.xml"),generateStrings());
            writeText(new File(app,"build.gradle"),generateAppGradle());
            writeText(new File(projectDir,"build.gradle"),generateRootGradle());
            writeText(new File(projectDir,"settings.gradle"),generateSettings());
            writeText(new File(projectDir,".package"),currentPackage);
            writeText(new File(projectDir,".project"),currentProjectName);

            currentFile = "MainActivity.java";
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String generateJava() {
        return "package "+currentPackage+";\\n\\n"+
                "import android.app.Activity;\\n"+
                "import android.os.Bundle;\\n\\n"+
                "public class MainActivity extends Activity {\\n\\n"+
                "    @Override\\n"+
                "    protected void onCreate(Bundle savedInstanceState) {\\n"+
                "        super.onCreate(savedInstanceState);\\n"+
                "        setContentView(R.layout.activity_main);\\n"+
                "    }\\n\\n"+
                "}\\n";
    }

    private String generateManifest() {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\\n"+
                "<manifest xmlns:android=\"http://schemas.android.com/apk/res/android\">\\n"+
                "    <application android:allowBackup=\"true\" android:label=\""+currentProjectName+"\" android:theme=\"@android:style/Theme.Material.NoActionBar\">\\n"+
                "        <activity android:name=\".MainActivity\" android:exported=\"true\">\\n"+
                "            <intent-filter>\\n"+
                "                <action android:name=\"android.intent.action.MAIN\" />\\n"+
                "                <category android:name=\"android.intent.category.LAUNCHER\" />\\n"+
                "            </intent-filter>\\n"+
                "        </activity>\\n"+
                "    </application>\\n"+
                "</manifest>\\n";
    }

    private String generateXml() {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\\n"+
                "<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\" android:layout_width=\"match_parent\" android:layout_height=\"match_parent\" android:orientation=\"vertical\" android:gravity=\"center\" android:padding=\"24dp\">\\n"+
                "    <TextView android:layout_width=\"wrap_content\" android:layout_height=\"wrap_content\" android:text=\"Hello AlphaStudio\" android:textSize=\"24sp\" />\\n"+
                "</LinearLayout>\\n";
    }

    private String generateStrings() {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\\n<resources>\\n<string name=\"app_name\">"+currentProjectName+"</string>\\n</resources>\\n";
    }

    private String generateAppGradle() {
        return "plugins {\\n"+
                "    id 'com.android.application'\\n"+
                "}\\n\\n"+
                "android {\\n"+
                "    namespace '"+currentPackage+"'\\n"+
                "    compileSdk 35\\n"+
                "    defaultConfig {\\n"+
                "        applicationId '"+currentPackage+"'\\n"+
                "        minSdk 24\\n"+
                "        targetSdk 35\\n"+
                "        versionCode 1\\n"+
                "        versionName '1.0'\\n"+
                "    }\\n"+
                "}\\n";
    }

    private String generateRootGradle() {
        return "plugins {\\n    id 'com.android.application' version '8.7.3' apply false\\n}\\n";
    }

    private String generateSettings() {
        return "pluginManagement {\\n"+
                "    repositories { google(); mavenCentral(); gradlePluginPortal() }\\n"+
                "}\\n"+
                "dependencyResolutionManagement {\\n"+
                "    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)\\n"+
                "    repositories { google(); mavenCentral() }\\n"+
                "}\\n"+
                "rootProject.name = '"+currentProjectName+"'\\n"+
                "include ':app'\\n";
    }

    private void buildWorkspace() {
        LinearLayout root = vertical();
        root.setBackgroundColor(BG);

        LinearLayout header = horizontal();
        header.setGravity(Gravity.CENTER_VERTICAL);
        header.setPadding(6,6,6,6);
        header.setBackgroundColor(PANEL);

        TextView back = text("‹",34,TEXT);
        back.setGravity(Gravity.CENTER);
        back.setOnClickListener(v -> { saveCurrentFile(); buildDashboard(); });
        header.addView(back,new LinearLayout.LayoutParams(45,48));

        LinearLayout projectInfo = vertical();
        projectInfo.setPadding(8,0,0,0);
        TextView project = text(currentProjectName,17,TEXT);
        project.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        projectInfo.addView(project);
        projectInfo.addView(text(currentPackage,10,MUTED));
        header.addView(projectInfo,new LinearLayout.LayoutParams(0,-2,1));

        TextView save = text("SAVE",10,TEXT);
        save.setGravity(Gravity.CENTER);
        save.setOnClickListener(v -> saveCurrentFile());
        header.addView(save,new LinearLayout.LayoutParams(55,48));

        TextView run = text("▶",18,GREEN);
        run.setGravity(Gravity.CENTER);
        run.setOnClickListener(v -> buildProject());
        header.addView(run,new LinearLayout.LayoutParams(45,48));
        root.addView(header);

        HorizontalScrollView toolbarScroll = new HorizontalScrollView(this);
        toolbarScroll.setHorizontalScrollBarEnabled(false);
        LinearLayout toolbar = horizontal();
        toolbar.setPadding(8,7,8,7);
        toolbar.addView(tool("⌕","Search"));
        toolbar.addView(tool("↶","Undo"));
        toolbar.addView(tool("↷","Redo"));
        toolbar.addView(tool("SAVE","Save"));
        toolbar.addView(tool("▶","Build"));
        toolbar.addView(tool("⇧","Export"));
        toolbarScroll.addView(toolbar);
        root.addView(toolbarScroll);

        LinearLayout main = horizontal();

        LinearLayout explorer = vertical();
        explorer.setPadding(8,12,8,12);
        explorer.setBackgroundColor(PANEL);
        TextView explorerTitle = text("PROJECT",10,MUTED);
        explorerTitle.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        explorer.addView(explorerTitle);

        TextView manifest = treeItem("📄 AndroidManifest.xml",1);
        manifest.setOnClickListener(v -> openFile("AndroidManifest.xml"));
        explorer.addView(manifest);

        TextView java = treeItem("📄 MainActivity.java",1);
        java.setOnClickListener(v -> openFile("MainActivity.java"));
        explorer.addView(java);

        TextView xml = treeItem("📄 activity_main.xml",1);
        xml.setOnClickListener(v -> openFile("activity_main.xml"));
        explorer.addView(xml);

        main.addView(explorer,new LinearLayout.LayoutParams(190,-1));

        LinearLayout editorArea = vertical();
        editorArea.setBackgroundColor(BG);

        HorizontalScrollView tabsScroll = new HorizontalScrollView(this);
        tabsScroll.setHorizontalScrollBarEnabled(false);
        LinearLayout tabs = horizontal();
        tabs.setPadding(5,5,5,5);
        tabs.addView(fileTab("MainActivity.java"));
        tabs.addView(fileTab("AndroidManifest.xml"));
        tabs.addView(fileTab("activity_main.xml"));
        tabsScroll.addView(tabs);
        editorArea.addView(tabsScroll);

        editor = new EditText(this);
        editor.setGravity(Gravity.TOP|Gravity.START);
        editor.setTextSize(13);
        editor.setTextColor(Color.rgb(225,228,235));
        editor.setHintTextColor(MUTED);
        editor.setTypeface(Typeface.MONOSPACE);
        editor.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_MULTI_LINE|InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        editor.setPadding(14,12,14,40);
        editor.setBackgroundColor(BG);
        loadCurrentFile();

        ScrollView editorScroll = new ScrollView(this);
        editorScroll.addView(editor);
        editorArea.addView(editorScroll,new LinearLayout.LayoutParams(-1,0,1));

        LinearLayout status = horizontal();
        status.setGravity(Gravity.CENTER_VERTICAL);
        status.setPadding(12,7,12,7);
        status.addView(text("● Ready",11,GREEN),new LinearLayout.LayoutParams(0,-2,1));
        status.addView(text(currentFile,10,MUTED));
        editorArea.addView(status);

        main.addView(editorArea,new LinearLayout.LayoutParams(0,-1,1));
        root.addView(main,new LinearLayout.LayoutParams(-1,0,1));
        setContentView(root);
    }

    private void openFile(String file) {
        saveCurrentFile();
        currentFile = file;
        undoStack.clear();
        redoStack.clear();
        buildWorkspace();
    }

    private void loadCurrentFile() {
        if (editor == null) return;
        File file = getCurrentFile();
        editor.setText(readText(file));
        editor.setSelection(editor.length());
    }

    private File getCurrentFile() {
        if ("MainActivity.java".equals(currentFile)) return javaFile;
        if ("AndroidManifest.xml".equals(currentFile)) return manifestFile;
        return xmlFile;
    }

    private void saveCurrentFile() {
        if (editor == null) return;
        File file = getCurrentFile();
        if (file == null) return;
        if (writeText(file,editor.getText().toString()))
            Toast.makeText(this,currentFile+" saved",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"Save failed",Toast.LENGTH_SHORT).show();
    }

    private void saveUndoState() {
        if (editor == null) return;
        if (undoStack.size() >= 30) undoStack.remove(0);
        undoStack.add(editor.getText().toString());
        redoStack.clear();
    }

    private void undoEdit() {
        if (editor == null || undoStack.isEmpty()) {
            Toast.makeText(this,"Nothing to undo",Toast.LENGTH_SHORT).show();
            return;
        }
        redoStack.add(editor.getText().toString());
        editor.setText(undoStack.remove(undoStack.size()-1));
        editor.setSelection(editor.length());
    }

    private void redoEdit() {
        if (editor == null || redoStack.isEmpty()) {
            Toast.makeText(this,"Nothing to redo",Toast.LENGTH_SHORT).show();
            return;
        }
        undoStack.add(editor.getText().toString());
        editor.setText(redoStack.remove(redoStack.size()-1));
        editor.setSelection(editor.length());
    }

    private void showSearch() {
        EditText search = input("Search current file");
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Search")
                .setView(search)
                .setNegativeButton("Cancel",null)
                .setPositiveButton("Find",null)
                .create();

        dialog.setOnShowListener(d -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                String query = search.getText().toString();
                if (query.isEmpty()) return;
                String source = editor.getText().toString();
                int position = source.indexOf(query);
                if (position >= 0) {
                    editor.requestFocus();
                    editor.setSelection(position,position+query.length());
                    dialog.dismiss();
                } else {
                    Toast.makeText(this,"Not found",Toast.LENGTH_SHORT).show();
                }
            });
        });
        dialog.show();
    }

    private void buildProject() {
        saveCurrentFile();
        new AlertDialog.Builder(this)
                .setTitle("Build")
                .setMessage("Project files are saved. Use the GitHub Actions Gradle workflow to compile the APK.")
                .setPositiveButton("OK",null)
                .show();
    }

    // =====================================================
    // PROJECT EXPORT
    // =====================================================

    private void exportProject() {
        saveCurrentFile();

        if (projectDir == null || !projectDir.exists()) {
            Toast.makeText(this, "Open a project first", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            File exportDir = new File(getCacheDir(), "exports");
            if (!exportDir.exists() && !exportDir.mkdirs()) {
                throw new IOException("Cannot create export folder");
            }

            pendingExportZip = new File(
                    exportDir,
                    safeFileName(currentProjectName) + ".zip"
            );

            if (pendingExportZip.exists() && !pendingExportZip.delete()) {
                throw new IOException("Cannot replace old export");
            }

            zipDirectory(projectDir, pendingExportZip);

            Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("application/zip");
            intent.putExtra(
                    Intent.EXTRA_TITLE,
                    safeFileName(currentProjectName) + ".zip"
            );
            startActivityForResult(intent, EXPORT_REQUEST);

        } catch (Exception e) {
            Toast.makeText(
                    this,
                    "Export failed: " + e.getMessage(),
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    private String safeFileName(String value) {
        if (value == null || value.trim().isEmpty()) {
            return "AlphaStudioProject";
        }

        return value.trim().replaceAll("[^A-Za-z0-9._-]", "_");
    }

    private void zipDirectory(File sourceDir, File zipFile) throws IOException {
        FileOutputStream fos = new FileOutputStream(zipFile);
        ZipOutputStream zos = new ZipOutputStream(fos);

        try {
            String rootName = sourceDir.getName();
            zipRecursive(sourceDir, rootName + "/", zos);
        } finally {
            try {
                zos.close();
            } catch (IOException ignored) {
            }
        }
    }

    private void zipRecursive(
            File file,
            String zipPath,
            ZipOutputStream zos
    ) throws IOException {

        if (file.isDirectory()) {
            File[] children = file.listFiles();

            if (children == null) {
                return;
            }

            if (children.length == 0) {
                zos.putNextEntry(new ZipEntry(zipPath));
                zos.closeEntry();
                return;
            }

            for (File child : children) {
                zipRecursive(
                        child,
                        zipPath + child.getName() +
                                (child.isDirectory() ? "/" : ""),
                        zos
                );
            }

            return;
        }

        FileInputStream input = new FileInputStream(file);

        try {
            ZipEntry entry = new ZipEntry(zipPath);
            zos.putNextEntry(entry);

            byte[] buffer = new byte[8192];
            int count;

            while ((count = input.read(buffer)) != -1) {
                zos.write(buffer, 0, count);
            }

            zos.closeEntry();

        } finally {
            input.close();
        }
    }

    @Override
    protected void onActivityResult(
            int requestCode,
            int resultCode,
            Intent data
    ) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode != EXPORT_REQUEST) {
            return;
        }

        if (resultCode != RESULT_OK ||
                data == null ||
                data.getData() == null) {

            pendingExportZip = null;
            Toast.makeText(
                    this,
                    "Export cancelled",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        if (pendingExportZip == null ||
                !pendingExportZip.exists()) {

            Toast.makeText(
                    this,
                    "Export file not found",
                    Toast.LENGTH_LONG
            ).show();
            return;
        }

        Uri destination = data.getData();

        try {
            OutputStream output =
                    getContentResolver().openOutputStream(destination);

            if (output == null) {
                throw new IOException("Cannot open destination");
            }

            FileInputStream input =
                    new FileInputStream(pendingExportZip);

            try {
                byte[] buffer = new byte[8192];
                int count;

                while ((count = input.read(buffer)) != -1) {
                    output.write(buffer, 0, count);
                }

                output.flush();

            } finally {
                input.close();
                output.close();
            }

            Toast.makeText(
                    this,
                    "Project exported successfully",
                    Toast.LENGTH_LONG
            ).show();

        } catch (Exception e) {

            Toast.makeText(
                    this,
                    "Export failed: " + e.getMessage(),
                    Toast.LENGTH_LONG
            ).show();

        } finally {
            pendingExportZip = null;
        }
    }

    private TextView tool(String icon,String title) {
        TextView t = text(icon+"  "+title,12,TEXT);
        t.setPadding(14,10,14,10);
        t.setBackground(round(10,PANEL));
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-2,-2);
        p.setMargins(0,0,8,0);
        t.setLayoutParams(p);

        if ("Search".equals(title)) t.setOnClickListener(v -> showSearch());
        else if ("Undo".equals(title)) t.setOnClickListener(v -> undoEdit());
        else if ("Redo".equals(title)) t.setOnClickListener(v -> redoEdit());
        else if ("Save".equals(title)) t.setOnClickListener(v -> saveCurrentFile());
        else if ("Build".equals(title)) t.setOnClickListener(v -> buildProject());
        else if ("Export".equals(title)) t.setOnClickListener(v -> exportProject());
        return t;
    }

    private TextView fileTab(String file) {
        TextView tab = text(file,11,TEXT);
        tab.setPadding(14,11,14,11);
        tab.setBackground(round(8,file.equals(currentFile)?PANEL2:PANEL));
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-2,-2);
        p.setMargins(3,0,3,0);
        tab.setLayoutParams(p);
        tab.setOnClickListener(v -> openFile(file));
        return tab;
    }

    private View actionCard(String icon,String title,String subtitle,int color) {
        LinearLayout card = vertical();
        card.setPadding(18,18,18,18);
        card.setBackground(round(16,color));

        TextView i = text(icon,27,TEXT);
        i.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        card.addView(i);

        TextView t = text(title,17,TEXT);
        t.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        addMargin(card,t,0,12,0,3);
        card.addView(text(subtitle,12,TEXT));

        card.setClickable(true);
        card.setOnClickListener(v -> {
            if ("New Project".equals(title)) showNewProjectDialog();
            else showOpenProjectDialog();
        });
        return card;
    }

    private View projectCard(String name,String pkg) {
        LinearLayout card = vertical();
        card.setPadding(18,16,18,16);
        card.setBackground(round(14,PANEL));

        TextView n = text("▣ "+name,16,TEXT);
        n.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        card.addView(n);

        addMargin(card,text(pkg,12,MUTED),0,5,0,2);
        card.addView(text("Android Application",11,MUTED));

        card.setClickable(true);
        card.setOnClickListener(v -> openProject(new File(new File(getFilesDir(),"projects"),name)));

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1,-2);
        p.setMargins(0,0,0,8);
        card.setLayoutParams(p);
        return card;
    }

    private TextView quickCard(String icon,String title) {
        TextView t = text(icon+"\n\n"+title,14,TEXT);
        t.setGravity(Gravity.CENTER);
        t.setPadding(15,15,15,15);
        t.setBackground(round(14,PANEL));
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(125,105);
        p.setMargins(0,0,12,0);
        t.setLayoutParams(p);
        return t;
    }

    private View statusCard(String name,String value) {
        LinearLayout card = horizontal();
        card.setGravity(Gravity.CENTER_VERTICAL);
        card.setPadding(16,15,16,15);
        card.setBackground(round(12,PANEL));

        card.addView(text("●",14,GREEN),new LinearLayout.LayoutParams(25,-2));
        card.addView(text(name,14,TEXT),new LinearLayout.LayoutParams(0,-2,1));

        TextView s = text(value,13,GREEN);
        s.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        card.addView(s);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1,-2);
        p.setMargins(0,0,0,8);
        card.setLayoutParams(p);
        return card;
    }

    private void showOpenProjectDialog() {
        File root = new File(getFilesDir(),"projects");
        if (!root.exists()) root.mkdirs();
        File[] dirs = root.listFiles();
        final ArrayList<String> names = new ArrayList<>();

        if (dirs != null) for (File dir : dirs) if (dir.isDirectory()) names.add(dir.getName());

        if (names.isEmpty()) {
            new AlertDialog.Builder(this).setTitle("Open Project")
                    .setMessage("No saved projects found.")
                    .setPositiveButton("OK",null).show();
            return;
        }

        new AlertDialog.Builder(this).setTitle("Open Project")
                .setItems(names.toArray(new String[0]),(dialog,which) ->
                        openProject(new File(root,names.get(which))))
                .setNegativeButton("Cancel",null).show();
    }

    private void openProject(File dir) {
        if (dir == null || !dir.exists()) {
            Toast.makeText(this,"Project not found",Toast.LENGTH_SHORT).show();
            return;
        }

        currentProjectName = readText(new File(dir,".project")).trim();
        currentPackage = readText(new File(dir,".package")).trim();
        if (currentProjectName.isEmpty()) currentProjectName = dir.getName();
        if (currentPackage.isEmpty()) currentPackage = "com.example.myapplication";

        projectDir = dir;
        javaFile = findJavaFile(dir);
        manifestFile = new File(dir,"app/src/main/AndroidManifest.xml");
        xmlFile = new File(dir,"app/src/main/res/layout/activity_main.xml");

        if (javaFile == null) {
            Toast.makeText(this,"MainActivity.java not found",Toast.LENGTH_SHORT).show();
            return;
        }

        currentFile = "MainActivity.java";
        buildWorkspace();
    }

    private File findJavaFile(File dir) {
        File[] found = findFiles(dir,"MainActivity.java");
        return found.length > 0 ? found[0] : null;
    }

    private File[] findFiles(File dir,String filename) {
        ArrayList<File> result = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files == null) return new File[0];

        for (File f : files) {
            if (f.isDirectory()) {
                for (File n : findFiles(f,filename)) result.add(n);
            } else if (filename.equals(f.getName())) {
                result.add(f);
            }
        }
        return result.toArray(new File[0]);
    }

    private TextView treeItem(String value,int level) {
        TextView t = text(value,12,TEXT);
        t.setPadding(8+level*12,9,4,9);
        return t;
    }

    private boolean validProjectName(String value) {
        return value.matches("[A-Za-z][A-Za-z0-9_]*");
    }

    private boolean validPackage(String value) {
        return value.matches("[a-zA-Z_][a-zA-Z0-9_]*(\\.[a-zA-Z_][a-zA-Z0-9_]*)+");
    }

    private boolean writeText(File file,String content) {
        try {
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) parent.mkdirs();
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.flush();
            writer.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private String readText(File file) {
        if (file == null || !file.exists()) return "";
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) result.append(line).append("\n");
            reader.close();
        } catch (IOException e) {
            return "";
        }
        return result.toString();
    }

    private TextView sectionTitle(String value) {
        TextView t = text(value,20,TEXT);
        t.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        return t;
    }

    private TextView label(String value) {
        TextView t = text(value,13,TEXT);
        t.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,values);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        return s;
    }

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

    private TextView text(String value,float size,int color) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);
        return t;
    }

    private GradientDrawable round(int radius,int color) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(radius);
        return d;
    }

    private void addMargin(LinearLayout parent,View view,int left,int top,int right,int bottom) {
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1,-2);
        p.setMargins(left,top,right,bottom);
        parent.addView(view,p);
    }
}
