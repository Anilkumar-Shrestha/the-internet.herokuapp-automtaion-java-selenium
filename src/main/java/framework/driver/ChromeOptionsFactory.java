package framework.driver;

import application.TestBase;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.HashMap;

import static framework.utility.loggerator.Logger.getLogger;

public class ChromeOptionsFactory extends TestBase {

    public static ChromeOptions defaultChromeOption(){
        ChromeOptions chromeOptions = defaultChromeOption(FileDownloadPath);
        chromeOptions.addArguments("--use-fake-ui-for-media-stream");
        return chromeOptions;
    }

    public static ChromeOptions defaultChromeOption(String fileDownloadPath){
        ChromeOptions chromeOptions = new ChromeOptions();
        HashMap<String, Integer> contentSettings = new HashMap<>();
        HashMap<String, Object> profile = new HashMap<>();
        HashMap<String, Object> pref = new HashMap<>();
        contentSettings.put("media_stream", 1);
        profile.put("managed_default_content_settings", contentSettings);
        pref.put("profile", profile);
        pref.put("profile.default_content_settings.popups", 0);
        pref.put("download.default_directory", fileDownloadPath);
        pref.put("download.prompt_for_download", false);
        pref.put("download.directory_upgrade", true);
        getLogger().debug("Chrome Download path set to "+fileDownloadPath);
        pref.put("safebrowsing.enabled", true);
        chromeOptions.setExperimentalOption("prefs", pref);

        chromeOptions.addArguments("window-size=1440,900"); //added for headless mode to size the window since maximize does not work.
        chromeOptions.addArguments("--use-fake-device-for-media-stream");  // Added for Headless mode to use fake media instead of live camera input.
//        chromeOptions.addArguments("--use-fake-ui-for-media-stream");  // Added for Headless mode to use avoids the need to grant camera/microphone permissions.
        chromeOptions.addArguments("--allow-running-insecure-content");
        chromeOptions.addArguments("--ignore-certificate-errors");
        chromeOptions.addArguments("-remote-allow-origins=*"); //https://groups.google.com/g/chromedriver-users/c/xL5-13_qGaA
//        chromeOptions.addArguments("--disable-gpu");
//        chromeOptions.addArguments("--remote-debugging-port=9222");
        if (setUp.getHeadlessMode().equalsIgnoreCase("true")) {
            chromeOptions.addArguments("--disable-dev-shm-usage");
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--headless=old");  // https://github.com/SeleniumHQ/selenium/issues/11637
            // https://www.selenium.dev/blog/2023/headless-is-going-away/

            // added if csp of valid user agent is required.
//            chromeOptions.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36");
//            chromeOptions.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36");
        }

        return chromeOptions;
    }



}
