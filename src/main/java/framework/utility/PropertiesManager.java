package framework.utility;

import org.testng.annotations.Optional;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Stream;

import static framework.utility.loggerator.Logger.getLogger;

public class PropertiesManager {

	private static final String PATH_TO_RESOURCES_DIR = "src/main/resources";
	private static final Properties props;

	static {
		props = new Properties();
		loadProperties();
	}

	private static void loadProperties() {
		Stream.of(Objects.requireNonNull(new File(PATH_TO_RESOURCES_DIR).listFiles()))
				.filter(f -> f.getName().contains(".properties"))
				.toList()
				.forEach(file -> {
					getLogger().debug("Loading property file {}", file.getName());
					try (InputStream in = new FileInputStream(file.getAbsolutePath())) {
						props.load(in);
					} catch (IOException e) {
						getLogger().error("Properties not loaded", e.getCause());
					}
				});
	}

	public synchronized static String getProperty(String key) {
		String property = props.getProperty(key);
		getLogger().debug("Return value {} for property key {}", property, key);
		return property;
	}

	public synchronized static String getProperty(String key, @Optional String defaultValue) {
		String property = props.getProperty(key);
		getLogger().debug("Return value "+property+" for property key "+ key);
		return (property == null) ? defaultValue : property;
	}


	public synchronized static String getAppUrl(){
		return props.getProperty("AppUrl");
	}

	public synchronized static String getAppApiUrl(){
		return props.getProperty("AppApiUrl");
	}

}
