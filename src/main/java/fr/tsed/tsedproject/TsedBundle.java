package fr.tsed.tsedproject;

import com.intellij.DynamicBundle;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

import java.util.Arrays;

public final class TsedBundle extends DynamicBundle {
    @NotNull
    public static final TsedBundle INSTANCE;

    public TsedBundle() {
        super("messages.TsedBundle");
    }

    @NotNull
    public static final String message(@PropertyKey(resourceBundle = "messages.TsedBundle") @NotNull String key, @NotNull Object... params) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(params, "params");
        String var10000 = INSTANCE.getMessage(key, Arrays.copyOf(params, params.length));
        Intrinsics.checkNotNullExpressionValue(var10000, "getMessage(key, *params)");
        return var10000;
    }

    static {
        TsedBundle var0 = new TsedBundle();
        INSTANCE = var0;
    }
}