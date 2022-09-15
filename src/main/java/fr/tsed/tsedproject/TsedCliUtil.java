package fr.tsed.tsedproject;

import com.intellij.javascript.nodejs.CompletionModuleInfo;
import com.intellij.javascript.nodejs.NodeModuleSearchUtil;
import com.intellij.javascript.nodejs.NodePackageVersion;
import com.intellij.javascript.nodejs.NodePackageVersionUtil;
import com.intellij.javascript.nodejs.packageJson.notification.PackageJsonGetDependenciesAction;
import com.intellij.lang.javascript.buildTools.npm.PackageJsonUtil;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.text.SemVer;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class TsedCliUtil {
    private static final String NOTIFICATION_GROUP_ID = "Tsed CLI";

    @NonNls
    private static final List<String> ANGULAR_JSON_NAMES = ContainerUtil.newArrayList(
            "package.json");

    public static @Nullable VirtualFile findCliJson(@Nullable VirtualFile dir) {
        if (dir == null || !dir.isValid()) return null;
        VirtualFile file = dir.findChild("package.json");
        if (file != null && file.isValid()) return file;
        return null;
    }

    public static @Nullable VirtualFile findTsedCliFolder(@Nullable Project project, @Nullable VirtualFile file) {
        @Nullable VirtualFile current = file;
        while (current != null) {
            if (current.isDirectory() && findCliJson(current) != null) return current;
            current = current.getParent();
        }
        if (findCliJson(project.getBaseDir()) != null) return project.getBaseDir();
        return null;
    }

    public static boolean hasTsedCliPackageInstalled(@NotNull Project project, @NotNull VirtualFile cli) {
        return findTsedCliModuleInfo(cli) != null;
    }

    private static @Nullable CompletionModuleInfo findTsedCliModuleInfo(@NotNull VirtualFile cli) {
        List<CompletionModuleInfo> modules = new ArrayList<>();
        NodeModuleSearchUtil.findModulesWithName(modules, "@tsed/cli", cli, null);
        CompletionModuleInfo moduleInfo = ContainerUtil.getFirstItem(modules);
        //todo search globally
        return moduleInfo != null && moduleInfo.getVirtualFile() != null ? moduleInfo : null;
    }

    public static void notifyTsedCliNotInstalled(@NotNull Project project, @NotNull VirtualFile cliFolder, @NotNull @Nls String message) {
        VirtualFile packageJson = PackageJsonUtil.findChildPackageJsonFile(cliFolder);
        if (packageJson == null) {
            System.out.println("packageJson is null");
        }
        Notification notification = NotificationGroupManager.getInstance().getNotificationGroup(NOTIFICATION_GROUP_ID).createNotification(
                message,
                TsedBundle.message("tsed.notify.cli.required-package-not-installed", packageJson != null ? packageJson.getPath() : cliFolder.getPath()),
                NotificationType.WARNING);
        if (packageJson != null) {
            notification.addAction(new PackageJsonGetDependenciesAction(project, packageJson, notification));
        }
        notification.notify(project);
    }

    public static @Nullable SemVer getTsedCliPackageVersion(@NotNull VirtualFile cliFolder) {
            CompletionModuleInfo moduleInfo = findTsedCliModuleInfo(cliFolder);
        if (moduleInfo == null) {
            System.out.println("moduleInfo is null");
            return null;
        }
        NodePackageVersion nodePackageVersion = NodePackageVersionUtil.getPackageVersion(moduleInfo.getVirtualFile().getPath());
        return nodePackageVersion != null ? nodePackageVersion.getSemVer() : null;
    }
}
