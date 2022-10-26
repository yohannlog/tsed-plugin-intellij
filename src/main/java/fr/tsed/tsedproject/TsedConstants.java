package fr.tsed.tsedproject;

import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.util.Key;

public final class TsedConstants {
    
    public static WizardContext WIZARD_CONTEXT;

    public static final Key<String> WIZARD_PACKAGE_MANAGER = Key.create(TsedConstants.class.getPackageName() + ".packageManager");
    public static final Key<String> WIZARD_MY_PACKAGE_MANAGER = Key.create(TsedConstants.class.getPackageName() + ".myPackageManager");
    public static final Key<String> WIZARD_PACKAGE_ARCHITECTURE = Key.create(TsedConstants.class.getPackageName() + ".architecture");
    public static final Key<String> WIZARD_PACKAGE_STYLING = Key.create(TsedConstants.class.getPackageName() + ".styling");
    public static final Key<Boolean> WIZARD_PACKAGE_GRAPH = Key.create(TsedConstants.class.getPackageName() + ".graph");
    public static final Key<Boolean> WIZARD_PACKAGE_DATABASE = Key.create(TsedConstants.class.getPackageName() + ".database");
    public static final Key<Boolean> WIZARD_PACKAGE_PASSPORT = Key.create(TsedConstants.class.getPackageName() + ".passport");
    public static final Key<Boolean> WIZARD_PACKAGE_SOCKETIO = Key.create(TsedConstants.class.getPackageName() + ".socketio");
    public static final Key<Boolean> WIZARD_PACKAGE_SWAGGER = Key.create(TsedConstants.class.getPackageName() + ".swagger");
    public static final Key<Boolean> WIZARD_PACKAGE_OPENID = Key.create(TsedConstants.class.getPackageName() + ".openid");
    public static final Key<Boolean> WIZARD_PACKAGE_TESTING = Key.create(TsedConstants.class.getPackageName() + ".testing");
    public static final Key<Boolean> WIZARD_PACKAGE_LINTER = Key.create(TsedConstants.class.getPackageName() + ".linter");
    public static final Key<Boolean> WIZARD_PACKAGE_BUNDLER = Key.create(TsedConstants.class.getPackageName() + ".bundler");
    public static final Key<Boolean> WIZARD_PACKAGE_COMMANDS = Key.create(TsedConstants.class.getPackageName() + ".commands");
    public static final Key<String> WIZARD_PACKAGE_ORM_TYPE = Key.create(TsedConstants.class.getPackageName() + ".ormtype");
    public static final Key<String> WIZARD_PACKAGE_DATABASE_TYPE = Key.create(TsedConstants.class.getPackageName() + ".databasetype");
    public static final Key<String> WIZARD_PACKAGE_LINTER_TYPE = Key.create(TsedConstants.class.getPackageName() + ".lintertype");
    public static final Key<String> WIZARD_PACKAGE_ESLINT_EXTRA_TYPE = Key.create(TsedConstants.class.getPackageName() + ".eslintextratype");
    public final static Key<String> WIZARD_PACKAGE_TESTING_TYPE = Key.create(TsedConstants.class.getPackageName() + ".testingtype");
    public final static Key<String> WIZARD_PACKAGE_BUNDLER_TYPE = Key.create(TsedConstants.class.getPackageName() + ".bundlertype");
    
}
