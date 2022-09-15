package fr.tsed.tsedproject;

import com.google.common.collect.Lists;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.filters.Filter;
import com.intellij.icons.AllIcons;
import com.intellij.javascript.nodejs.CompletionModuleInfo;
import com.intellij.javascript.nodejs.NodeModuleSearchUtil;
import com.intellij.javascript.nodejs.interpreter.NodeJsInterpreter;
import com.intellij.javascript.nodejs.interpreter.NodeJsInterpreterManager;
import com.intellij.javascript.nodejs.util.NodePackage;
import com.intellij.lang.javascript.JavaScriptBundle;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.ComponentPopupBuilder;
import com.intellij.openapi.ui.popup.IconButton;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.ColoredListCellRenderer;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.ui.components.JBList;
import com.intellij.ui.speedSearch.ListWithFilter;
import com.intellij.util.Function;
import com.intellij.util.text.SemVer;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UIUtil;
import fr.tsed.tsedproject.wizards.TsedCliProjectGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TsedCliGenerateAction extends DumbAwareAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        @Nullable Project project = e.getProject();
        @Nullable VirtualFile file = e.getData(PlatformDataKeys.VIRTUAL_FILE);
        @Nullable FileEditor editor = e.getData(PlatformDataKeys.FILE_EDITOR);
        VirtualFile cli = TsedCliUtil.findTsedCliFolder(project, file);
        SemVer cliVersion = TsedCliUtil.getTsedCliPackageVersion(cli);

        if (cliVersion == null) {
            System.out.println("Cli version is null");
            TsedCliUtil.notifyTsedCliNotInstalled(project, cli, "tsed.action.ng-generate.cant-generate-code");
            return;
        }

        JBList<String> list = new JBList<>();
        list.add(new JButton("test"));
        list.setCellRenderer(new ColoredListCellRenderer<String>() {
            @Override
            protected void customizeCellRenderer(@NotNull JList<? extends String> list, String value, int index, boolean selected, boolean hasFocus) {
                if (!selected && index % 2 == 0) {

                    setBackground(UIUtil.getDecoratedRowColor());
                }
            }
        });

        DefaultActionGroup actionGroup = new DefaultActionGroup();

        ActionToolbar toolbar = ActionManager.getInstance().createActionToolbar("TsedCliGenerateAction", actionGroup, true);
        toolbar.setReservePlaceAutoPopupIcon(false);
        toolbar.setMinimumButtonSize(new Dimension(22, 22));
        JComponent toolbarComponent = toolbar.getComponent();
        toolbarComponent.setOpaque(false);

        JScrollPane scroll = ScrollPaneFactory.createScrollPane(list, true);
        scroll.setBorder(JBUI.Borders.empty());

        JComponent pane = ListWithFilter.wrap(list, scroll, new Function<String, String>() {
            @Override
            public String fun(String s) {
                return s;
            }
        });

        ComponentPopupBuilder builder = JBPopupFactory.getInstance()
                .createComponentPopupBuilder(pane, list)
                .setMayBeParent(true)
                .setRequestFocus(true)
                .setFocusable(true)
                .setFocusOwners(new Component[]{list})
                .setLocateWithinScreenBounds(true)
                .setCancelOnOtherWindowOpen(true)
                .setMovable(true)
                .setResizable(true)
                .setTitle("TODO")
                .setSettingButtons(toolbarComponent)
                .setCancelOnWindowDeactivation(true)
                .setCancelOnClickOutside(true)
                .setDimensionServiceKey(project, "fr.tsed.tsedproject.generate", true)
                .setMinSize(new Dimension(JBUI.scale(350), JBUI.scale(350)))
                .setCancelButton(new IconButton("TODO CLOSE", AllIcons.Actions.Close, AllIcons.Actions.CloseHovered));
        JBPopup popup = builder.createPopup();
        popup.showCenteredInCurrentWindow(project);
    }

    private void runGenerator(@NotNull Project project, @NotNull VirtualFile cli, @NotNull String generatorName, @Nullable VirtualFile workingDir) throws ExecutionException {
        NodeJsInterpreter interpreter = NodeJsInterpreterManager.getInstance(project).getInterpreter();
        if (interpreter == null) {
            return;
        }

        List<CompletionModuleInfo> modules = new ArrayList<>();
        NodeModuleSearchUtil.findModulesWithName(modules, "@tsed/cli", cli, null);

        CompletionModuleInfo module = modules.stream().findFirst().orElse(null);

        Filter[] filters = new Filter[0];
        TsedCliProjectGenerator.generate(interpreter, new NodePackage(module.getVirtualFile().getPath()), new Function<NodePackage, String>() {
            @Override
            public String fun(NodePackage nodePackage) {
                return nodePackage.findBinFile("tsed", null).getAbsolutePath();
            }
        }, cli, VfsUtilCore.virtualToIoFile(workingDir == null ? cli : workingDir), project, null, JavaScriptBundle.message("generate.0", cli.getName()), filters, "generate", "test", "");
    }
}
