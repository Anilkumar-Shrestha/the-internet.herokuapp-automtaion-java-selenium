package framework.utility.loggerator;

import org.slf4j.*;

public class Logger {

	public static org.slf4j.Logger getLogger() {
		StackTraceElement caller = new Throwable().fillInStackTrace().getStackTrace()[1];
		return LoggerFactory.getLogger(caller.getClassName() + "[" + caller.getMethodName() + "]");
	}

}
