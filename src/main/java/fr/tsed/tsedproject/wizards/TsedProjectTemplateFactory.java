package fr.tsed.tsedproject.wizards;

import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.lang.javascript.boilerplate.JavaScriptNewTemplatesFactoryBase;
import com.intellij.platform.ProjectTemplate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TsedProjectTemplateFactory extends JavaScriptNewTemplatesFactoryBase {
    @NotNull
    @Override
    public ProjectTemplate[] createTemplates(@Nullable WizardContext context) {
        return new ProjectTemplate[]{(ProjectTemplate)(new TsedCliProjectGenerator())};
    }
}
