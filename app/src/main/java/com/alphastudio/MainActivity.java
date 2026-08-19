package com.alphastudio;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    // =========================
    // COLORS
    // =========================

    private static final int BG =
            Color.rgb(18, 18, 20);

    private static final int PANEL =
            Color.rgb(27, 27, 31);

    private static final int PANEL_2 =
            Color.rgb(35, 35, 41);

    private static final int TEXT =
            Color.rgb(245, 245, 247);

    private static final int MUTED =
            Color.rgb(155, 155, 165);

    private static final int ACCENT =
            Color.rgb(82, 120, 255);

    private static final int GREEN =
            Color.rgb(70, 190, 125);


    // =========================
    // ACTIVITY
    // =========================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(BG);
        getWindow().setNavigationBarColor(BG);

        buildDashboard();
    }


    // =========================
    // DASHBOARD
    // =========================

    private void buildDashboard() {

        LinearLayout root =
                new LinearLayout(this);

        root.setOrientation(
                LinearLayout.VERTICAL
        );

        root.setBackgroundColor(BG);


        // =========================
        // TOP BAR
        // =========================

        LinearLayout topBar =
                new LinearLayout(this);

        topBar.setOrientation(
                LinearLayout.HORIZONTAL
        );

        topBar.setGravity(
                Gravity.CENTER_VERTICAL
        );

        topBar.setPadding(
                18,
                14,
                12,
                14
        );

        topBar.setBackgroundColor(PANEL);


        // Logo

        TextView logo =
                createText(
                        "A",
                        21,
                        Color.WHITE
                );

        logo.setGravity(
                Gravity.CENTER
        );

        logo.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        logo.setBackground(
                round(
                        12,
                        ACCENT
                )
        );

        topBar.addView(
                logo,
                new LinearLayout.LayoutParams(
                        46,
                        46
                )
        );


        // Title

        LinearLayout titleContainer =
                new LinearLayout(this);

        titleContainer.setOrientation(
                LinearLayout.VERTICAL
        );

        titleContainer.setPadding(
                13,
                0,
                0,
                0
        );


        TextView appTitle =
                createText(
                        "AlphaStudio",
                        19,
                        TEXT
                );

        appTitle.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );


        TextView appSubtitle =
                createText(
                        "Android IDE",
                        12,
                        MUTED
                );


        titleContainer.addView(
                appTitle
        );

        titleContainer.addView(
                appSubtitle
        );


        LinearLayout.LayoutParams titleParams =
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                );


        topBar.addView(
                titleContainer,
                titleParams
        );


        // Menu

        TextView menu =
                createText(
                        "⋮",
                        28,
                        TEXT
                );

        menu.setGravity(
                Gravity.CENTER
        );


        topBar.addView(
                menu,
                new LinearLayout.LayoutParams(
                        42,
                        46
                )
        );


        root.addView(
                topBar,
                new LinearLayout.LayoutParams(
                        -1,
                        -2
                )
        );


        // =========================
        // SCROLL
        // =========================

        ScrollView scrollView =
                new ScrollView(this);

        scrollView.setFillViewport(
                true
        );

        scrollView.setBackgroundColor(
                BG
        );


        LinearLayout content =
                new LinearLayout(this);

        content.setOrientation(
                LinearLayout.VERTICAL
        );

        content.setPadding(
                20,
                24,
                20,
                30
        );


        // =========================
        // WELCOME
        // =========================

        TextView welcome =
                createText(
                        "Welcome back",
                        28,
                        TEXT
                );

        welcome.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );


        content.addView(
                welcome
        );


        TextView description =
                createText(
                        "Build professional Android apps directly from your phone.",
                        14,
                        MUTED
                );


        addMargin(
                content,
                description,
                0,
                6,
                0,
                22
        );


        // =========================
        // PROJECT BUTTONS
        // =========================

        LinearLayout projectRow =
                new LinearLayout(this);

        projectRow.setOrientation(
                LinearLayout.HORIZONTAL
        );


        View newProject =
                createActionCard(
                        "＋",
                        "New Project",
                        "Create Android app",
                        ACCENT
                );


        View openProject =
                createActionCard(
                        "↗",
                        "Open Project",
                        "Open existing project",
                        PANEL_2
                );


        LinearLayout.LayoutParams leftCard =
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                );

        leftCard.setMargins(
                0,
                0,
                6,
                0
        );


        LinearLayout.LayoutParams rightCard =
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                );

        rightCard.setMargins(
                6,
                0,
                0,
                0
        );


        projectRow.addView(
                newProject,
                leftCard
        );


        projectRow.addView(
                openProject,
                rightCard
        );


        content.addView(
                projectRow
        );


        // =========================
        // RECENT PROJECTS
        // =========================

        TextView recentTitle =
                createText(
                        "Recent Projects",
                        20,
                        TEXT
                );

        recentTitle.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );


        addMargin(
                content,
                recentTitle,
                0,
                30,
                0,
                12
        );


        content.addView(
                createProjectCard(
                        "AlphaStudio",
                        "com.alphastudio",
                        "Android App"
                )
        );


        View firstProject =
                createProjectCard(
                        "My First App",
                        "com.example.myapp",
                        "Android App"
                );


        addMargin(
                content,
                firstProject,
                0,
                8,
                0,
                0
        );


        // =========================
        // QUICK ACTIONS
        // =========================

        TextView quickTitle =
                createText(
                        "Quick Actions",
                        20,
                        TEXT
                );

        quickTitle.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );


        addMargin(
                content,
                quickTitle,
                0,
                30,
                0,
                12
        );


        HorizontalScrollView horizontalScroll =
                new HorizontalScrollView(this);

        horizontalScroll.setHorizontalScrollBarEnabled(
                false
        );


        LinearLayout quickRow =
                new LinearLayout(this);

        quickRow.setOrientation(
                LinearLayout.HORIZONTAL
        );


        quickRow.addView(
                createQuickCard(
                        "▣",
                        "Build APK"
                )
        );


        quickRow.addView(
                createQuickCard(
                        "⌘",
                        "GitHub"
                )
        );


        quickRow.addView(
                createQuickCard(
                        "⚙",
                        "SDK Manager"
                )
        );


        quickRow.addView(
                createQuickCard(
                        "▤",
                        "Terminal"
                )
        );


        horizontalScroll.addView(
                quickRow
        );


        content.addView(
                horizontalScroll
        );


        // =========================
        // ENVIRONMENT
        // =========================

        TextView environmentTitle =
                createText(
                        "Environment",
                        20,
                        TEXT
                );

        environmentTitle.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );


        addMargin(
                content,
                environmentTitle,
                0,
                30,
                0,
                12
        );


        content.addView(
                createStatusCard(
                        "Android SDK",
                        "Ready"
                )
        );


        content.addView(
                createStatusCard(
                        "Gradle",
                        "Ready"
                )
        );


        content.addView(
                createStatusCard(
                        "GitHub",
                        "Connected"
                )
        );


        // =========================
        // FINISH
        // =========================

        scrollView.addView(
                content
        );


        root.addView(
                scrollView,
                new LinearLayout.LayoutParams(
                        -1,
                        0,
                        1
                )
        );


        setContentView(
                root
        );
    }


    // =========================
    // ACTION CARD
    // =========================

    private View createActionCard(
            String icon,
            String title,
            String subtitle,
            int backgroundColor
    ) {

        LinearLayout card =
                new LinearLayout(this);

        card.setOrientation(
                LinearLayout.VERTICAL
        );

        card.setPadding(
                18,
                18,
                18,
                18
        );

        card.setBackground(
                round(
                        16,
                        backgroundColor
                )
        );


        TextView iconView =
                createText(
                        icon,
                        27,
                        TEXT
                );

        iconView.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );


        card.addView(
                iconView
        );


        TextView titleView =
                createText(
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


        TextView subtitleView =
                createText(
                        subtitle,
                        12,
                        Color.rgb(
                                205,
                                205,
                                215
                        )
                );


        card.addView(
                subtitleView
        );


        card.setClickable(
                true
        );


        card.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(
                            View v
                    ) {

                        if (
                                title.equals(
                                        "New Project"
                                )
                        ) {

                            showNewProjectDialog();

                        } else if (
                                title.equals(
                                        "Open Project"
                                )
                        ) {

                            Toast.makeText(
                                    MainActivity.this,
                                    "Open Project will be added next",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
                }
        );


        return card;
    }


    // =========================
    // NEW PROJECT DIALOG
    // =========================

    private void showNewProjectDialog() {

        LinearLayout layout =
                new LinearLayout(this);

        layout.setOrientation(
                LinearLayout.VERTICAL
        );

        layout.setPadding(
                35,
                5,
                35,
                5
        );


        // PROJECT NAME

        TextView projectLabel =
                createText(
                        "Project Name",
                        13,
                        TEXT
                );

        projectLabel.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );


        layout.addView(
                projectLabel
        );


        EditText projectName =
                new EditText(this);

        projectName.setHint(
                "MyApplication"
        );

        projectName.setSingleLine(
                true
        );

        projectName.setTextColor(
                TEXT
        );

        projectName.setHintTextColor(
                MUTED
        );


        layout.addView(
                projectName,
                new LinearLayout.LayoutParams(
                        -1,
                        -2
                )
        );


        // PACKAGE NAME

        TextView packageLabel =
                createText(
                        "Package Name",
                        13,
                        TEXT
                );

        packageLabel.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );


        LinearLayout.LayoutParams packageLabelParams =
                new LinearLayout.LayoutParams(
                        -1,
                        -2
                );

        packageLabelParams.setMargins(
                0,
                18,
                0,
                0
        );


        layout.addView(
                packageLabel,
                packageLabelParams
        );


        EditText packageName =
                new EditText(this);

        packageName.setHint(
                "com.example.myapplication"
        );

        packageName.setSingleLine(
                true
        );

        packageName.setTextColor(
                TEXT
        );

        packageName.setHintTextColor(
                MUTED
        );


        layout.addView(
                packageName
        );


        // LANGUAGE

        TextView languageLabel =
                createText(
                        "Language",
                        13,
                        TEXT
                );

        languageLabel.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );


        LinearLayout.LayoutParams languageLabelParams =
                new LinearLayout.LayoutParams(
                        -1,
                        -2
                );

        languageLabelParams.setMargins(
                0,
                18,
                0,
                0
        );


        layout.addView(
                languageLabel,
                languageLabelParams
        );


        Spinner language =
                new Spinner(this);


        String[] languages = {
                "Java",
                "Kotlin"
        };


        ArrayAdapter<String> languageAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        languages
                );


        languageAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );


        language.setAdapter(
                languageAdapter
        );


        layout.addView(
                language
        );


        // MINIMUM SDK

        TextView sdkLabel =
                createText(
                        "Minimum SDK",
                        13,
                        TEXT
                );

        sdkLabel.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );


        LinearLayout.LayoutParams sdkLabelParams =
                new LinearLayout.LayoutParams(
                        -1,
                        -2
                );

        sdkLabelParams.setMargins(
                0,
                18,
                0,
                0
        );


        layout.addView(
                sdkLabel,
                sdkLabelParams
        );


        Spinner sdk =
                new Spinner(this);


        String[] sdks = {
                "Android 7.0 (API 24)",
                "Android 8.0 (API 26)",
                "Android 10 (API 29)",
                "Android 12 (API 31)",
                "Android 15 (API 35)"
        };


        ArrayAdapter<String> sdkAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        sdks
                );


        sdkAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );


        sdk.setAdapter(
                sdkAdapter
        );


        layout.addView(
                sdk
        );


        // DIALOG

        AlertDialog dialog =
                new AlertDialog.Builder(this)
                        .setTitle(
                                "Create New Project"
                        )
                        .setView(
                                layout
                        )
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
                new android.content.DialogInterface.OnShowListener() {

                    @Override
                    public void onShow(
                            android.content.DialogInterface d
                    ) {

                        Button createButton =
                                dialog.getButton(
                                        AlertDialog.BUTTON_POSITIVE
                                );


                        createButton.setOnClickListener(
                                new View.OnClickListener() {

                                    @Override
                                    public void onClick(
                                            View v
                                    ) {

                                        String name =
                                                projectName
                                                        .getText()
                                                        .toString()
                                                        .trim();


                                        String pkg =
                                                packageName
                                                        .getText()
                                                        .toString()
                                                        .trim();


                                        if (
                                                name.isEmpty()
                                        ) {

                                            projectName.setError(
                                                    "Enter project name"
                                            );

                                            return;
                                        }


                                        if (
                                                pkg.isEmpty()
                                        ) {

                                            packageName.setError(
                                                    "Enter package name"
                                            );

                                            return;
                                        }


                                        Toast.makeText(
                                                MainActivity.this,
                                                "Project created: " +
                                                        name,
                                                Toast.LENGTH_LONG
                                        ).show();


                                        dialog.dismiss();
                                    }
                                }
                        );
                    }
                }
        );


        dialog.show();
    }


    // =========================
    // PROJECT CARD
    // =========================

    private View createProjectCard(
            String name,
            String packageName,
            String type
    ) {

        LinearLayout card =
                new LinearLayout(this);

        card.setOrientation(
                LinearLayout.VERTICAL
        );

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


        TextView nameView =
                createText(
                        "▣  " + name,
                        16,
                        TEXT
                );

        nameView.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );


        card.addView(
                nameView
        );


        TextView packageView =
                createText(
                        packageName,
                        12,
                        MUTED
                );


        addMargin(
                card,
                packageView,
                0,
                5,
                0,
                2
        );


        TextView typeView =
                createText(
                        "Android App  •  " + type,
                        11,
                        MUTED
                );


        card.addView(
                typeView
        );


        return card;
    }


    // =========================
    // QUICK CARD
    // =========================

    private View createQuickCard(
            String icon,
            String title
    ) {

        TextView card =
                createText(
                        icon +
                                "\n\n" +
                                title,
                        14,
                        TEXT
                );


        card.setGravity(
                Gravity.CENTER
        );


        card.setPadding(
                15,
                15,
                15,
                15
        );


        card.setBackground(
                round(
                        14,
                        PANEL
                )
        );


        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        125,
                        105
                );


        params.setMargins(
                0,
                0,
                12,
                0
        );


        card.setLayoutParams(
                params
        );


        return card;
    }


    // =========================
    // STATUS CARD
    // =========================

    private View createStatusCard(
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


        TextView dot =
                createText(
                        "●",
                        14,
                        GREEN
                );


        card.addView(
                dot,
                new LinearLayout.LayoutParams(
                        25,
                        -2
                )
        );


        TextView nameView =
                createText(
                        name,
                        14,
                        TEXT
                );


        LinearLayout.LayoutParams nameParams =
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                );


        card.addView(
                nameView,
                nameParams
        );


        TextView statusView =
                createText(
                        status,
                        13,
                        GREEN
                );


        statusView.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );


        card.addView(
                statusView
        );


        LinearLayout.LayoutParams cardParams =
                new LinearLayout.LayoutParams(
                        -1,
                        -2
                );


        cardParams.setMargins(
                0,
                0,
                0,
                8
        );


        card.setLayoutParams(
                cardParams
        );


        return card;
    }


    // =========================
    // TEXT
    // =========================

    private TextView createText(
            String value,
            float size,
            int color
    ) {

        TextView text =
                new TextView(this);

        text.setText(
                value
        );

        text.setTextSize(
                size
        );

        text.setTextColor(
                color
        );

        return text;
    }


    // =========================
    // BACKGROUND
    // =========================

    private GradientDrawable round(
            int radius,
            int color
    ) {

        GradientDrawable drawable =
                new GradientDrawable();

        drawable.setColor(
                color
        );

        drawable.setCornerRadius(
                radius
        );

        return drawable;
    }


    // =========================
    // MARGIN
    // =========================

    private void addMargin(
            LinearLayout parent,
            View view,
            int left,
            int top,
            int right,
            int bottom
    ) {

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        -1,
                        -2
                );


        params.setMargins(
                left,
                top,
                right,
                bottom
        );


        parent.addView(
                view,
                params
        );
    }
}
