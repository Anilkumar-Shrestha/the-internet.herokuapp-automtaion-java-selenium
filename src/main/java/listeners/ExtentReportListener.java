package listeners;

import framework.utility.reporter.CustomExtentReport;
import org.testng.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static framework.utility.loggerator.Logger.getLogger;

public class ExtentReportListener implements ITestListener, ISuiteListener, IAlterSuiteListener {


	@Override
	public void onTestStart(ITestResult iTestResult) {
		String methodDescription = iTestResult.getMethod().getDescription();
		String methodName;
		methodName = Objects.equals(methodDescription, "") ?iTestResult.getMethod().getMethodName():methodDescription;
		String className = iTestResult.getTestClass().getRealClass().getSimpleName();
		String finalTestName = className+"."+methodName.toUpperCase();
//        String finalTestName = iTestResult.getTestContext().getName()+"."+iTestResult.getTestClass().getRealClass().getSimpleName()+"."+methodName.toUpperCase();
		getLogger().info("Extent Report Listener:: Executing Test :- " +  finalTestName + " ---------");
		CustomExtentReport.startTest(className,finalTestName);

	}

	@Override
	public void onTestSuccess(ITestResult iTestResult) {
		getLogger().info("Test " + iTestResult.getMethod().getDescription() + " finish. SUCCESS");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		String methodName= result.getMethod().getMethodName();
		getLogger().error("Test Case Failed is {}", result.getMethod().getDescription());
		Throwable errorMessage = new Throwable( result.getThrowable());
		getLogger().error("Test Case Failed is ", errorMessage);
		CustomExtentReport.stopFailedTest( methodName,errorMessage);
	}

	@Override
	public void onTestSkipped(ITestResult iTestResult) {
		String descriptionName= iTestResult.getMethod().getDescription()+" ";
		List <String> groupDependentName= Arrays.asList(iTestResult.getMethod().getMethodsDependedUpon());
		List<String> groupDependentNames = groupDependentName.stream()
				.map(string -> {
					int dotIndex = string.lastIndexOf(".");
					if (dotIndex >= 0 && dotIndex < string.length() - 1) {
						return string.substring(dotIndex + 1);
					}
					return "";
				})
				.collect(Collectors.toList());
		getLogger().warn ("Test `" + descriptionName + "` finish. SKIPPED. Depends upon method: "+groupDependentNames, iTestResult.getThrowable());
		CustomExtentReport.stopSkippedTest(descriptionName, groupDependentNames);
	}


	@Override
	public void onFinish(ISuite suite) {
		getLogger().info("Extent Report Listener:: ==> On Finish");
		CustomExtentReport.finishRun();
	}

}
