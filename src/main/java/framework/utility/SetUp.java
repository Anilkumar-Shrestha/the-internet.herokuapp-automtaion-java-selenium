package framework.utility;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.openqa.selenium.WebDriver;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SetUp {

	public static WebDriver driver;

	protected String BrowserType;
	protected String BrowserDriverVersion;
	protected String HeadlessMode;
	protected String remoteRun;
	protected String remoteUrl;
	protected String  AppUrl;
	protected String  AppApiUrl;
	protected String  ApiVersion;
	protected String  excludeScreenshot;



	public String toString() {
		return
				"BrowserType: '" + this.BrowserType +
				"', HeadlessMode: '" + this.HeadlessMode +
				"', remoteRun: '" + this.remoteRun +
				"', AppUrl: '" + this.AppUrl +
				"', AppApiUrl: '" + this.AppApiUrl + "'";
	}

}
