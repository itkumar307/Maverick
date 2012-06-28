package com.maveric.enums;

public enum ExceriseType {
	UNKNOWN(0, "Unknown"), NEW(5000, "WalkingExcerise"), EXCELLENT(10000,
			"RunningExcerise"), GOOD(30000, "PushUp");

	Integer value;
	String excerise;

	ExceriseType(int value, String excerise) {
		this.value = value;
		this.excerise = excerise;
	}

	public Integer getValue() {
		return value;
	}

	public String getMessage() {
		return excerise;
	}

	public static String[] exceriseMessages() {
		String[] types = { ExceriseType.UNKNOWN.getMessage(),
				ExceriseType.NEW.getMessage(),
				ExceriseType.EXCELLENT.getMessage(),
				ExceriseType.GOOD.getMessage() };
		return types;
	}

	public static ExceriseType getExceriseByMsg(String message) {
		for (ExceriseType s : ExceriseType.values()) {
			if (s.getMessage().equalsIgnoreCase(message)) {
				return s;
			}
		}
		return ExceriseType.UNKNOWN;
	}

	public static ExceriseType getExceriseByValue(Integer value) {
		for (ExceriseType s : ExceriseType.values()) {
			if (s.getValue().equals(value)) {
				return s;
			}
		}
		return ExceriseType.UNKNOWN;
	}
}
