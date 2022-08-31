package fr.tsed.tsedproject.wizards;

import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.module.ModuleType;
import org.jetbrains.annotations.Nullable;

public class DemoModuleBuilder extends ModuleBuilder {
    @Override
    public ModuleType<?> getModuleType() {
        return DemoModuleType.getInstance();
    }

    @Override
    public String getPresentableName() {
        return "Tsed";
    }

    @Nullable
    @Override
    public ModuleWizardStep getCustomOptionsStep(WizardContext context, Disposable parentDisposable) {
        return new DemoModuleWizardStep();
    }
}
