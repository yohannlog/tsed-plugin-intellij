package fr.tsed.tsedproject.wizards;

import com.intellij.execution.filters.Filter;
import com.intellij.ide.util.projectWizard.SettingsStep;
import com.intellij.javascript.nodejs.interpreter.NodeJsInterpreterField;
import com.intellij.javascript.nodejs.util.NodePackageField;
import com.intellij.lang.javascript.boilerplate.NpmPackageProjectGenerator;
import com.intellij.lang.javascript.boilerplate.NpxPackageDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.platform.ProjectGeneratorPeer;
import com.intellij.ui.CheckboxTree;
import fr.tsed.tsedproject.TsedBundle;
import fr.tsed.tsedproject.TsedIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TsedCliProjectGenerator extends NpmPackageProjectGenerator {

    private String packageName = "@tsed/cli";
    private String executable  = "tsed";
    private String initCommand = "init";

    @Override
    protected Filter @NotNull [] filters(@NotNull Project project, @NotNull VirtualFile virtualFile) {
        return new Filter[0];
    }

    @Override
    protected void customizeModule(@NotNull VirtualFile virtualFile, ContentEntry contentEntry) {

    }


    @Override
    protected @NotNull String packageName() {
        return packageName;
    }

    @Override
    protected @NotNull String presentablePackageName() {
        return TsedBundle.message("tsed.project.generator.presentable.package.name");
    }

    @Override
    public String getDescription() {
        return TsedBundle.message("tsed.project.generator.description");
    }

    @Override
    public @NotNull @NlsContexts.Label String getName() {
        return TsedBundle.message("tsed.project.generator.name");
    }

    @Override
    public Icon getIcon() {
        return TsedIcons.ProjectGenerator;
    }

    @Override
    protected @NotNull List<NpxPackageDescriptor.NpxCommand> getNpxCommands() {
        NpxPackageDescriptor.NpxCommand initCommand = new NpxPackageDescriptor.NpxCommand(this.packageName, this.executable);
        return List.of(initCommand);
    }

    @Override
    public @NotNull ProjectGeneratorPeer<Settings> createPeer() {
        return new TsedCLIProjectGeneratorPeer();
    }

    @Override
    protected String @NotNull [] generatorArgs(@NotNull Project project, @NotNull VirtualFile baseDir, @NotNull Settings settings) {
        TsedCLIProjectSettings cliProjectSettings = (TsedCLIProjectSettings) settings;
        String projectName = project.getName();

        List<String> parameters = new ArrayList<>();
        parameters.add("init");

        parameters.add("--packageManager=" + cliProjectSettings.packageManager);

        parameters.add("--project-name=" + projectName);

        parameters.add("--features=" + getFeatures(cliProjectSettings));

        if (Objects.equals(cliProjectSettings.architectureType, "Ts.ED")) {
            parameters.add("--arch=default");
        } else {
            parameters.add("--arch=" + cliProjectSettings.architectureType);
        }

        if (Objects.equals(cliProjectSettings.stylingType, "Ts.ED")) {
            parameters.add("--convention=default");
        } else {
            parameters.add("--convention=" + cliProjectSettings.stylingType);
        }
        parameters.add("--platform=" + cliProjectSettings.myPackageManager);

        parameters.add("--skip-prompt");

        parameters.add(".");

        String[] args = new String[parameters.size()];
        for (int i = 0; i < parameters.size(); i++) {
            System.out.println(parameters.get(i));
            args[i] = parameters.get(i).toLowerCase();
        }


        return args;
    }

    public String getFeatures(@NotNull TsedCLIProjectSettings settings) {
        List<String> features = new ArrayList<>();
        if (settings.useTypeGraph) {
            features.add("graphql");
        }
        if (settings.useDatabase) {
            features.add(settings.ormType);
            if (settings.ormType.equals("typeorm")) {
                features.add("typeorm:" + settings.databaseType);
            }
        }
        if (settings.useSwagger) {
            features.add("swagger");
        }

        if (settings.usePassport) {
            features.add("passportjs");
        }

        if (settings.useSocketio) {
            features.add("socketio");
        }

        if (settings.useOpenId) {
            features.add("oidc");
        }

        if (settings.useTesting) {
            features.add("testing");
            if (settings.testingType.equals("Jest")) {
                features.add("jest");
            } else {
                features.add("mocha");
                features.add("chai");
                features.add("sinon");
            }
        }

        if (settings.useLinter) {
            features.add("linter");
            features.add("eslint");
            if (settings.eslintExtra.equals("Prettier")) {
                features.add("prettier");
            } else if (settings.eslintExtra.equals("On Commit")) {
                features.add("standard");
            }
        }


        return String.join(",", features).toLowerCase();
    }

    private class TsedCLIProjectGeneratorPeer extends NpmPackageGeneratorPeer {

        /**
         * PACKAGE MANAGER
         */
        private JComboBox<String> packageManager;

        private JComboBox<String> myPackageManager;
        private JComboBox<String> architectureType;
        private JComboBox<String> stylingType;

        private CheckboxTree features;
        private JCheckBox    useTypeGraph;
        private JCheckBox    useDatabase;
        private JCheckBox    usePassport;
        private JCheckBox    useSocketio;
        private JCheckBox    useSwagger;
        private JCheckBox    useOpenId;
        private JCheckBox    useTesting;
        private JCheckBox    useLinter;
        private JCheckBox    useBundlers;
        private JCheckBox    useCommands;

        /**
         * DATABASE PART
         */
        private JComboBox<String> ormType;
        private JComboBox<String> databaseType;

        /**
         * ESLint part
         */
        private JComboBox<String> linterType;
        private JComboBox<String> eslintExtra;

        /**
         * TESTING PART
         */
        private JComboBox<String> testingType;

        /**
         * BLUNDER PART
         */
        private JComboBox<String> bundlerType;

        @Override
        protected JPanel createPanel() {
            System.out.println("sssssssssss");
            final JPanel panel = super.createPanel();

            packageManager = new ComboBox<>(new String[]{"npm", "yarn", "pnpm (experimental)"});

            myPackageManager = new ComboBox<>();
            myPackageManager.addItem("express");
            myPackageManager.addItem("koa.js");

            architectureType = new ComboBox<>();
            architectureType.addItem("Ts.ED");
            architectureType.addItem("feature");

            stylingType = new ComboBox<>();
            stylingType.addItem("Ts.ED");
            stylingType.addItem("Angular");

            useTypeGraph = new JCheckBox("TypeGraphQL");
            useDatabase = new JCheckBox("Database");
            useDatabase.addActionListener(e -> {
                ormType.setEnabled(useDatabase.isSelected());
            });
            usePassport = new JCheckBox("Passport.js");
            useSocketio = new JCheckBox("Socket.io");
            useSwagger = new JCheckBox("Swagger");
            useOpenId = new JCheckBox("OpenID Connect Provider");
            useTesting = new JCheckBox("Testing");
            useTesting.addActionListener(e -> {
                testingType.setEnabled(useTesting.isSelected());
            });
            useLinter = new JCheckBox("Linter");
            useLinter.addActionListener(e -> {
                linterType.setEnabled(useLinter.isSelected());
                eslintExtra.setEnabled(useLinter.isSelected());
            });
            useBundlers = new JCheckBox("Bundlers");
            useBundlers.addActionListener(e -> {
                bundlerType.setEnabled(useBundlers.isSelected());
            });
            useCommands = new JCheckBox("Commands");

            ormType = new ComboBox<>();
            ormType.setEnabled(false);
            ormType.addItem("Prisma");
            ormType.addItem("Mongoose");
            ormType.addItem("TypeORM");
            ormType.addActionListener(e -> {
                databaseType.setEnabled(Objects.equals(ormType.getSelectedItem(), "TypeORM"));
            });

            databaseType = new ComboBox<>();
            databaseType.setEnabled(false);
            databaseType.addItem("MySQL");
            databaseType.addItem("MariaDB");
            databaseType.addItem("PostgreSQL");
            databaseType.addItem("CockRoachDB");
            databaseType.addItem("SQLite");
            databaseType.addItem("Better SQLite3");
            databaseType.addItem("Cordova");
            databaseType.addItem("NativeScript");
            databaseType.addItem("Oracle");
            databaseType.addItem("MsSQL");
            databaseType.addItem("MongoDB");
            databaseType.addItem("SQL.js");
            databaseType.addItem("ReactNative");
            databaseType.addItem("Expo");

            features = new CheckboxTree();
            features.add("typegraphql", useTypeGraph);
            features.add("database", useDatabase);
            features.add("passportjs", usePassport);
            features.add("socketio", useSocketio);
            features.add("swagger", useSwagger);
            features.add("openid", useOpenId);
            features.add("testing", useTesting);
            features.add("linter", useLinter);
            features.add("bundler", useBundlers);
            features.add("commands", useCommands);

            linterType = new ComboBox<>();
            linterType.setEnabled(false);
            linterType.addItem("ESLint");

            eslintExtra = new ComboBox<>();
            eslintExtra.setEnabled(false);
            eslintExtra.addItem("None");
            eslintExtra.addItem("Prettier");
            eslintExtra.addItem("On Commit");
            eslintExtra.setSelectedIndex(0);

            testingType = new ComboBox<>();
            testingType.setEnabled(false);
            testingType.addItem("Jest");
            testingType.addItem("Mocha + Chai + Sinon");

            bundlerType = new ComboBox<>();
            bundlerType.setEnabled(false);
            bundlerType.addItem("Babel");
            bundlerType.addItem("Webpack");

            return panel;
        }

        @Override
        public void buildUI(@NotNull SettingsStep settingsStep) {
            super.buildUI(settingsStep);

            settingsStep.addSettingsField("Package Manager:", packageManager);
            settingsStep.addSettingsField("Choose platform: ", myPackageManager);
            settingsStep.addSettingsField("Choose an architecture: ", architectureType);
            settingsStep.addSettingsField("Choose a styling: ", stylingType);

            settingsStep.addSettingsField("Choose features: ", features);
            settingsStep.addSettingsComponent(useTypeGraph);
            settingsStep.addSettingsComponent(useDatabase);
            settingsStep.addSettingsComponent(usePassport);
            settingsStep.addSettingsComponent(useSocketio);
            settingsStep.addSettingsComponent(useSwagger);
            settingsStep.addSettingsComponent(useOpenId);
            settingsStep.addSettingsComponent(useTesting);
            settingsStep.addSettingsComponent(useLinter);
            settingsStep.addSettingsComponent(useBundlers);
            settingsStep.addSettingsComponent(useCommands);

            settingsStep.addSettingsField("Choose an ORM manager: ", ormType);
            settingsStep.addSettingsField("Choose a database: ", databaseType);
            settingsStep.addSettingsField("Choose a linter: ", linterType);
            settingsStep.addSettingsField("Choose extra linter tools", eslintExtra);
            settingsStep.addSettingsField("Choose unit framework", testingType);
            settingsStep.addSettingsField("Choose a bundler", bundlerType);
        }

        @Override
        protected @NotNull NodePackageField createAndInitPackageField(@NotNull NodeJsInterpreterField interpreterField) {
            return super.createAndInitPackageField(interpreterField);
        }

        @Override
        public @NotNull Settings getSettings() {
            return new TsedCLIProjectSettings(super.getSettings(), myPackageManager.getSelectedItem().toString(),
                    packageManager.getSelectedItem().toString(), architectureType.getSelectedItem().toString(),
                    stylingType.getSelectedItem().toString(), useTypeGraph.isSelected(),
                    ormType.getSelectedItem().toString(),
                    useDatabase.isSelected(),databaseType.getSelectedItem().toString(), usePassport.isSelected(), useSocketio.isSelected(),
                    useSwagger.isSelected(), useOpenId.isSelected(), useTesting.isSelected(), testingType.getSelectedItem().toString(),
                    useLinter.isSelected(), eslintExtra.getSelectedItem().toString(), useBundlers.isSelected(), useCommands.isSelected());
        }

        @Override
        public @Nullable ValidationInfo validate() {
            return super.validate();
        }
    }

    private static class TsedCLIProjectSettings extends Settings {

        public final String myPackageManager;
        public final String packageManager;
        public final String architectureType;
        public final String stylingType;

        public final boolean useTypeGraph;

        public final String ormType;
        public final boolean useDatabase;
        public final String databaseType;
        public final boolean usePassport;
        public final boolean useSocketio;
        public final boolean useSwagger;
        public final boolean useOpenId;
        public final boolean useTesting;
        public final String testingType;
        public final boolean useLinter;
        public final String eslintExtra;
        public final boolean useBundlers;
        public final boolean useCommands;

        TsedCLIProjectSettings(@NotNull Settings settings, String myPackageManager,
                               String packageManager, String architectureType,
                               String stylingType, boolean useTypeGraph,
                               String ormType,
                               boolean useDatabase, String databaseType, boolean usePassport,
                               boolean useSocketio, boolean useSwagger,
                               boolean useOpenId, boolean useTesting, String testingType,
                               boolean useLinter, String eslintExtra ,boolean useBundlers,
                               boolean useCommands) {
            super(settings.myInterpreterRef, settings.myPackage);
            this.myPackageManager = myPackageManager;
            this.packageManager = packageManager;
            this.architectureType = architectureType;
            this.stylingType = stylingType;
            this.useTypeGraph = useTypeGraph;
            this.ormType = ormType;
            this.useDatabase = useDatabase;
            this.databaseType = databaseType;
            this.usePassport = usePassport;
            this.useSocketio = useSocketio;
            this.useSwagger = useSwagger;
            this.useOpenId = useOpenId;
            this.useTesting = useTesting;
            this.testingType = testingType;
            this.useLinter = useLinter;
            this.eslintExtra = eslintExtra;
            this.useBundlers = useBundlers;
            this.useCommands = useCommands;
        }
    }
}


