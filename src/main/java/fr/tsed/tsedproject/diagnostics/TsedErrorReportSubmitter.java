package fr.tsed.tsedproject.diagnostics;

import com.intellij.openapi.diagnostic.ErrorReportSubmitter;
import com.intellij.openapi.util.NlsActions;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class TsedErrorReportSubmitter extends ErrorReportSubmitter {

    private Map<String, String> packageAbbreviations = new HashMap<>();
    @Override
    public @NlsActions.ActionText @NotNull String getReportActionText() {
        return null;
    }
}
