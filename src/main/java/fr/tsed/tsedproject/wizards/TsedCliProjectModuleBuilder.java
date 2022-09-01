package fr.tsed.tsedproject.wizards;

import com.intellij.ide.util.projectWizard.WebProjectTemplate;
import com.intellij.ide.util.projectWizard.WebTemplateNewProjectWizard;
import com.intellij.ide.wizard.GeneratorNewProjectWizard;
import com.intellij.ide.wizard.GeneratorNewProjectWizardBuilderAdapter;

public class TsedCliProjectModuleBuilder extends GeneratorNewProjectWizardBuilderAdapter {
    public TsedCliProjectModuleBuilder() {
        super((new WebTemplateNewProjectWizard((new TsedCliProjectGenerator()))));
    }
}