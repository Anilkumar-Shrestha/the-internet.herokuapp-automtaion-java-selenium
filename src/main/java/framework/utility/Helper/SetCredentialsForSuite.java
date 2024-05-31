package framework.utility.Helper;

import framework.utility.PropertiesManager;
import org.json.simple.parser.ParseException;

import static framework.utility.Helper.PasswordEncryptionDecryption.getDecryptString;
import static framework.utility.loggerator.Logger.getLogger;


public class SetCredentialsForSuite {
    public static String basicAuthUserName;
    public static String basicAuthUserPassword;


    public static void setCredentialsFromLocalFile() throws ParseException {
        getLogger().info("Retrieving credentials from local user credentials properties file");
        basicAuthUserName = PropertiesManager.getProperty("BasicAuthUserName");
        basicAuthUserPassword = getDecryptString(PropertiesManager.getProperty("BasicAuthUserPassword"));

    }

}
