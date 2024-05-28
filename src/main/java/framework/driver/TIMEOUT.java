package framework.driver;

import java.time.Duration;

public enum TIMEOUT {
	TEN_SEC(10),
	FIVE_SEC(5),
	THREE_SEC(3),
	ONE_SEC(1);

	private final int time;

	TIMEOUT(int time) {
		this.time = time;
	}

	public int getTimeInSeconds() {
		return time;
	}

	public long getTimeInMillis() {
		return getTimeInSeconds() * 1000L;
	}

	public Duration getDurationInSeconds() {
		return Duration.ofSeconds(this.time);
	}
}
