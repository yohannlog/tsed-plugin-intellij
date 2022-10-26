package fr.tsed.tsedproject.wizards;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WebProjectTemplate;
import com.intellij.ide.util.projectWizard.WebTemplateNewProjectWizard;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.ide.wizard.CommitStepException;
import com.intellij.ide.wizard.GeneratorNewProjectWizard;
import com.intellij.ide.wizard.GeneratorNewProjectWizardBuilderAdapter;
import com.intellij.ide.wizard.NewProjectWizardStep;
import com.intellij.lang.javascript.boilerplate.NpmPackageProjectGenerator;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.CheckboxTree;
import com.intellij.ui.JBColor;
import com.intellij.ui.layout.RowBuilder;
import fr.tsed.tsedproject.TsedConstants;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.Objects;

public class TsedCliProjectModuleBuilder extends GeneratorNewProjectWizardBuilderAdapter {
    public TsedCliProjectModuleBuilder() {
        super((new WebTemplateNewProjectWizard((new TsedCliProjectGenerator()))));
    }

    @Override
    public ModuleWizardStep[] createFinishingSteps(@NotNull WizardContext wizardContext, @NotNull ModulesProvider modulesProvider) {
        TsedConstants.WIZARD_CONTEXT = wizardContext;
        return super.createFinishingSteps(wizardContext, modulesProvider);
    }

    @Override
    public ModuleWizardStep[] createWizardSteps(@NotNull WizardContext wizardContext, @NotNull ModulesProvider modulesProvider) {
        return new ModuleWizardStep[]{new ModuleWizardStep() {

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
            public JComponent getComponent() {
                JPanel panel = new JPanel();

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

                JLabel packageManagerLabel = new JLabel("Package manager: ");
                JLabel myPackageManagerLabel = new JLabel("Choose platform: ");
                JLabel architectureTypeLabel = new JLabel("Choose an architecture: ");
                JLabel stylingTypeLabel = new JLabel("Choose a styling: ");
                JLabel ormTypeLabel = new JLabel("Choose an ORM manager:  ");
                JLabel databaseTypeLabel = new JLabel("Choose a database:  ");
                JLabel featuresLabel = new JLabel("Choose features:  ");
                JLabel linterTypeLabel = new JLabel("Choose a linter:  ");
                JLabel eslintExtraLabel = new JLabel("Choose extra linter tools:  ");
                JLabel testingTypeLabel = new JLabel("Choose unit framework:  ");
                JLabel bundlerTypeLabel = new JLabel("Choose a bundler:  ");
                
                JLabel featuresTitle = new JLabel("Features to select :");


                GridBagLayout gridbag = new GridBagLayout();
                GridBagConstraints c = new GridBagConstraints();
                //take all height
                c.fill = GridBagConstraints.VERTICAL;
                panel.setLayout(gridbag);
                gridbag.setConstraints(panel, c);
  
                panel.setBackground(JBColor.background());
                GridBagConstraints left = new GridBagConstraints();
                left.anchor = GridBagConstraints.EAST;
                GridBagConstraints right = new GridBagConstraints();
                right.weightx = 2.0;
                right.fill = GridBagConstraints.HORIZONTAL;
                right.gridwidth = GridBagConstraints.REMAINDER;
                
                panel.add(packageManagerLabel, left);
                panel.add(packageManager, right);
                
                panel.add(myPackageManagerLabel, left);
                panel.add(myPackageManager, right);
                
                panel.add(architectureTypeLabel, left);
                panel.add(architectureType, right);
                
                panel.add(stylingTypeLabel, left);
                panel.add(stylingType, right);
                
                panel.add(featuresTitle, left);
                panel.add(new JLabel(""), right);

                panel.add(useTypeGraph, right);
                panel.add(useDatabase, right);
                panel.add(usePassport, right);
                panel.add(useSocketio, right);
                panel.add(useSwagger, right);
                panel.add(useOpenId, right);
                panel.add(useTesting, right);
                panel.add(useLinter, right);
                panel.add(useBundlers, right);
                panel.add(useCommands, right);
                
                
                panel.add(ormTypeLabel, left);
                panel.add(ormType, right);
                
                panel.add(databaseTypeLabel, left);
                panel.add(databaseType, right);
                
                panel.add(linterTypeLabel, left);
                panel.add(linterType, right);
                
                panel.add(eslintExtraLabel, left);
                panel.add(eslintExtra, right);
                
                panel.add(testingTypeLabel, left);
                panel.add(testingType, right);
                
                panel.add(bundlerTypeLabel, left);
                panel.add(bundlerType, right);
                
                panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                
                return panel;
            }
// wizardContext.putUserData(TsedConstants.WIZARD_PACKAGE_
            @Override
            public void updateDataModel() {
                wizardContext.putUserData(TsedConstants.WIZARD_PACKAGE_MANAGER, Objects.requireNonNull(packageManager.getSelectedItem()).toString());
                wizardContext.putUserData(TsedConstants.WIZARD_MY_PACKAGE_MANAGER, Objects.requireNonNull(myPackageManager.getSelectedItem()).toString());
                wizardContext.putUserData(TsedConstants.WIZARD_PACKAGE_ARCHITECTURE, Objects.requireNonNull(architectureType.getSelectedItem()).toString());
                wizardContext.putUserData(TsedConstants.WIZARD_PACKAGE_STYLING, Objects.requireNonNull(stylingType.getSelectedItem()).toString());
                wizardContext.putUserData(TsedConstants.WIZARD_PACKAGE_GRAPH, useTypeGraph.isSelected());
                wizardContext.putUserData(TsedConstants.WIZARD_PACKAGE_DATABASE, useDatabase.isSelected());
                wizardContext.putUserData(TsedConstants.WIZARD_PACKAGE_PASSPORT, usePassport.isSelected());
                wizardContext.putUserData(TsedConstants.WIZARD_PACKAGE_SOCKETIO, useSocketio.isSelected());
                wizardContext.putUserData(TsedConstants.WIZARD_PACKAGE_SWAGGER, useSwagger.isSelected());
                wizardContext.putUserData(TsedConstants.WIZARD_PACKAGE_OPENID, useOpenId.isSelected());
                wizardContext.putUserData(TsedConstants.WIZARD_PACKAGE_TESTING, useTesting.isSelected());
                wizardContext.putUserData(TsedConstants.WIZARD_PACKAGE_LINTER, useLinter.isSelected());
                wizardContext.putUserData(TsedConstants.WIZARD_PACKAGE_BUNDLER, useBundlers.isSelected());
                wizardContext.putUserData(TsedConstants.WIZARD_PACKAGE_COMMANDS, useCommands.isSelected());
                wizardContext.putUserData(TsedConstants.WIZARD_PACKAGE_ORM_TYPE, Objects.requireNonNull(ormType.getSelectedItem()).toString());
                wizardContext.putUserData(TsedConstants.WIZARD_PACKAGE_DATABASE_TYPE, Objects.requireNonNull(databaseType.getSelectedItem()).toString());
                wizardContext.putUserData(TsedConstants.WIZARD_PACKAGE_LINTER_TYPE, Objects.requireNonNull(linterType.getSelectedItem()).toString());
                wizardContext.putUserData(TsedConstants.WIZARD_PACKAGE_ESLINT_EXTRA_TYPE, Objects.requireNonNull(eslintExtra.getSelectedItem()).toString());
                wizardContext.putUserData(TsedConstants.WIZARD_PACKAGE_TESTING_TYPE, Objects.requireNonNull(testingType.getSelectedItem()).toString());
                wizardContext.putUserData(TsedConstants.WIZARD_PACKAGE_BUNDLER_TYPE, Objects.requireNonNull(bundlerType.getSelectedItem()).toString());
            }

            @Override
            public void disposeUIResources() {
                System.out.println("disposeUIResources");
                super.disposeUIResources();
            }

            @Override
            public void onWizardFinished() throws CommitStepException {
                System.out.println("onWizardFinished");
                super.onWizardFinished();
            }

            @Override
            public void onStepLeaving() {
                System.out.println("onStepLeaving");
                super.onStepLeaving();
            }

            @Override
            public JComponent getPreferredFocusedComponent() {
                System.out.println("getPreferredFocusedComponent");
                return super.getPreferredFocusedComponent();
            }

            @Override
            public void updateStep() {
                System.out.println("updateStep");
                super.updateStep();
            }
        }};
    }
}
