package com.alphastudio;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {

    // =========================
    // COLORS
    // =========================

    private static final int BG = Color.rgb(18, 18, 20);
    private static final int PANEL = Color.rgb(27, 27, 31);
    private static final int PANEL_2 = Color.rgb(35, 35, 41);

    private static final int TEXT = Color.rgb(245, 245, 247);
    private static final int MUTED = Color.rgb(155, 155, 165);

    private static final int ACCENT = Color.rgb(82, 120, 255);
    private static final int GREEN = Color.rgb(70, 190, 125);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(BG);
        getWindow().setNavigationBarColor(BG);

        createDashboard();
    }

    // =========================
    // DASHBOARD
    // =========================

    private void createDashboard() {

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(BG);

        // ---------------------------------
        // TOP BAR
        // ---------------------------------

        LinearLayout topBar = new LinearLayout(this);
        topBar.setOrientation(LinearLayout.HORIZONTAL);
        topBar.setGravity(Gravity.CENTER_VERTICAL);
        topBar.setPadding(18, 14, 14, 14);
        topBar.setBackgroundColor(PANEL);

        TextView logo = createText("A", 22, TEXT);
        logo.setGravity(Gravity.CENTER);
        logo.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        logo.setBackground(createRounded(12, ACCENT));

        topBar.addView(
                logo,
                new LinearLayout.LayoutParams(46, 46)
        );

        LinearLayout titleContainer = new LinearLayout(this);
        titleContainer.setOrientation(LinearLayout.VERTICAL);
        titleContainer.setPadding(13, 0, 0, 0);

        TextView appName = createText(
                "AlphaStudio",
                19,
                TEXT
        );

        appName.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        TextView appSubtitle = createText(
                "Android IDE",
                12,
                MUTED
        );

        titleContainer.addView(appName);
        titleContainer.addView(appSubtitle);

        LinearLayout.LayoutParams titleParams =
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                );

        topBar.addView(titleContainer, titleParams);

        TextView menu = createText(
                "⋮",
                28,
                TEXT
        );

        menu.setGravity(Gravity.CENTER);

        topBar.addView(
                menu,
                new LinearLayout.LayoutParams(42, 46)
        );

        root.addView(topBar);

        // ---------------------------------
        // SCROLL CONTENT
        // ---------------------------------

        ScrollView scrollView = new ScrollView(this);

        scrollView.setFillViewport(true);
        scrollView.setVerticalScrollBarEnabled(false);

        LinearLayout content = new LinearLayout(this);

        content.setOrientation(
                LinearLayout.VERTICAL
        );

        content.setPadding(
                20,
                25,
                20,
                30
        );

        // ---------------------------------
        // WELCOME
        // ---------------------------------

        TextView welcome = createText(
                "Welcome back",
                28,
                TEXT
        );

        welcome.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        content.addView(welcome);

        TextView description = createText(
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

        // ---------------------------------
        // NEW / OPEN PROJECT
        // ---------------------------------

        LinearLayout projectRow =
                new LinearLayout(this);

        projectRow.setOrientation(
                LinearLayout.HORIZONTAL
        );

        TextView newProject = createActionCard(
                "＋",
                "New Project",
                "Create Android app",
                ACCENT
        );

        TextView openProject = createActionCard(
                "↗",
                "Open Project",
                "Open existing project",
                PANEL_2
        );

        LinearLayout.LayoutParams leftParams =
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                );

        leftParams.setMargins(0, 0, 6, 0);

        LinearLayout.LayoutParams rightParams =
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                );

        rightParams.setMargins(6, 0, 0, 0);

        projectRow.addView(
                newProject,
                leftParams
        );

        projectRow.addView(
                openProject,
                rightParams
        );

        content.addView(projectRow);

        // ---------------------------------
        // RECENT PROJECTS
        // ---------------------------------

        TextView recentTitle = createText(
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

        TextView project1 = createProjectCard(
                "▣  AlphaStudio",
                "com.alphastudio  •  Android App"
        );

        content.addView(project1);

        TextView project2 = createProjectCard(
                "▣  My First App",
                "com.example.myapp  •  Android App"
        );

        addMargin(
                content,
                project2,
                0,
                8,
                0,
                0
        );

        // ---------------------------------
        // QUICK ACTIONS
        // ---------------------------------

        TextView quickTitle = createText(
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
                createQuickCard("▣", "Build APK")
        );

        quickRow.addView(
                createQuickCard("⌘", "GitHub")
        );

        quickRow.addView(
                createQuickCard("⚙", "SDK Manager")
        );

        quickRow.addView(
                createQuickCard("▤", "Terminal")
        );

        horizontalScroll.addView(quickRow);

        content.addView(horizontalScroll);

        // ---------------------------------
        // ENVIRONMENT
        // ---------------------------------

        TextView environmentTitle = createText(
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
                        "●",
                        "Android SDK",
                        "Ready"
                )
        );

        content.addView(
                createStatusCard(
                        "●",
                        "Gradle",
                        "Ready"
                )
        );

        content.addView(
                createStatusCard(
                        "●",
                        "GitHub",
                        "Connected"
                )
        );

        // ---------------------------------
        // FINISH
        // ---------------------------------

        scrollView.addView(content);

        root.addView(
                scrollView,
                new LinearLayout.LayoutParams(
                        -1,
                        0,
                        1
                )
        );

        setContentView(root);
    }

    // ==================================================
    // ACTION CARD
    // ==================================================

    private TextView createActionCard(
            String icon,
            String title,
            String subtitle,
            int backgroundColor
    ) {

        TextView card = createText(
                icon + "\n\n" +
                title + "\n" +
                subtitle,
                14,
                TEXT
        );

        card.setPadding(
                18,
                18,
                18,
                18
        );

        card.setBackground(
                createRounded(
                        16,
                        backgroundColor
                )
        );

        card.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        // New Project / Open Project
                        // functionality will be added here.

                    }
                }
        );

        return card;
    }

    // ==================================================
    // PROJECT CARD
    // ==================================================

    private TextView createProjectCard(
            String title,
            String subtitle
    ) {

        TextView card = createText(
                title + "\n" + subtitle,
                14,
                TEXT
        );

        card.setPadding(
                18,
                17,
                18,
                17
        );

        card.setBackground(
                createRounded(
                        14,
                        PANEL
                )
        );

        card.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        // Project editor will open here.

                    }
                }
        );

        return card;
    }

    // ==================================================
    // QUICK ACTION CARD
    // ==================================================

    private TextView createQuickCard(
            String icon,
            String title
    ) {

        TextView card = createText(
                icon + "\n\n" + title,
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
                createRounded(
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

        card.setLayoutParams(params);

        card.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        // Quick action functionality
                        // will be added later.

                    }
                }
        );

        return card;
    }

    // ==================================================
    // STATUS CARD
    // ==================================================

    private TextView createStatusCard(
            String icon,
            String name,
            String status
    ) {

        TextView card = createText(
                icon + "   " +
                name +
                "                         " +
                status,
                14,
                TEXT
        );

        card.setPadding(
                16,
                17,
                16,
                17
        );

        card.setBackground(
                createRounded(
                        12,
                        PANEL
                )
        );

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        -1,
                        -2
                );

        params.setMargins(
                0,
                0,
                0,
                8
        );

        card.setLayoutParams(params);

        return card;
    }

    // ==================================================
    // TEXT HELPER
    // ==================================================

    private TextView createText(
            String text,
            float size,
            int color
    ) {

        TextView view = new TextView(this);

        view.setText(text);
        view.setTextSize(size);
        view.setTextColor(color);

        return view;
    }

    // ==================================================
    // BACKGROUND HELPER
    // ==================================================

    private GradientDrawable createRounded(
            int radius,
            int color
    ) {

        GradientDrawable drawable =
                new GradientDrawable();

        drawable.setColor(color);

        drawable.setCornerRadius(
                radius
        );

        return drawable;
    }

    // ==================================================
    // MARGIN HELPER
    // ==================================================

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
