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
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.platform.ProjectGeneratorPeer;
import com.intellij.ui.CheckBoxList;
import com.intellij.ui.CheckboxTree;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBComboBoxLabel;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.layout.ButtonSelectorKt;
import com.intellij.ui.layout.ButtonSelectorToolbar;
import com.intellij.ui.layout.RowBuilder;
import com.intellij.ui.tabs.JBTabs;
import fr.tsed.tsedproject.TsedBundle;
import fr.tsed.tsedproject.TsedIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;

public class TsedCliProjectGenerator extends NpmPackageProjectGenerator {

    private String packageName = "@tsed/cli";
    private String executable = "tsed";
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
        //log
        JBCheckBox defaultSetupCheckbox = new JBCheckBox("test",true);
        System.out.println("ddddddddddddd");
        return new TsedCLIProjectGeneratorPeer();
    }

    @Override
    protected String @NotNull [] generatorArgs(@NotNull Project project, @NotNull VirtualFile baseDir) {
        String projectName = project.getName();
        return new String[]{initCommand, "--directory", ".", "--package-manager", "npm", projectName};
    }

    private class TsedCLIProjectGeneratorPeer extends NpmPackageGeneratorPeer {

        private JCheckBox       myUseDefaults;

        private JComboBox<String>       myPackageManager;
        private JCheckBox myUseDefaults1;
        private JCheckBox myUseDefaults2;

        @Override
        protected JPanel createPanel() {
            System.out.println("sssssssssss");
            final JPanel panel = super.createPanel();

            myUseDefaults = new JCheckBox("test", true);
            panel.add(myUseDefaults);

            myUseDefaults1 = new JCheckBox("test", true);
            panel.add(myUseDefaults1);
            myUseDefaults2 = new JCheckBox("tests", true);
            panel.add(myUseDefaults2);

            myPackageManager = new ComboBox<>();
            myPackageManager.addItem("express");
            myPackageManager.addItem("koa.js");

            return panel;
        }

        @Override
        public void buildUI(@NotNull SettingsStep settingsStep) {
            super.buildUI(settingsStep);
            settingsStep.addSettingsComponent(myUseDefaults);
            settingsStep.addSettingsField("Choose: ", myPackageManager);
            settingsStep.addSettingsField("Choose a framework", myUseDefaults);
        }

        @Override
        protected @NotNull NodePackageField createAndInitPackageField(@NotNull NodeJsInterpreterField interpreterField) {
            return super.createAndInitPackageField(interpreterField);
        }

        @Override
        public @NotNull Settings getSettings() {
            return super.getSettings();
        }

        @Override
        public @Nullable ValidationInfo validate() {
            return super.validate();
        }
    }

    private static class TsedCLIProjectSettings extends Settings {
        TsedCLIProjectSettings(@NotNull Settings settings) {
            super(settings.myInterpreterRef, settings.myPackage);
        }
    }
}


