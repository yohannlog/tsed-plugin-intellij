package fr.tsed.tsedproject;

import com.intellij.openapi.util.IconLoader;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

@SuppressWarnings("unused")
public final class TsedIcons {
    @NotNull
    public static final Icon      FileType;
    @NotNull
    public static final Icon      ProjectGenerator;
    @NotNull
    public static final TsedIcons INSTANCE;

    static {
        TsedIcons var0 = new TsedIcons();
        INSTANCE = var0;
        Icon var10000 = IconLoader.getIcon("/icons/tsed.svg", var0.getClass());
        Intrinsics.checkNotNullExpressionValue(var10000, "IconLoader.getIcon(\"/icons/tsed.svg\", javaClass)");
        FileType = var10000;
        var10000 = IconLoader.getIcon("/icons/tsed.svg", var0.getClass());
        Intrinsics.checkNotNullExpressionValue(var10000, "IconLoader.getIcon(\"/icons/tsed.svg\", javaClass)");
        ProjectGenerator = var10000;
    }
}