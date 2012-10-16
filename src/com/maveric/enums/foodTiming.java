package com.maveric.enums;

public enum foodTiming {
	BREAKFAST(1, "Breakfast"), LUNCH(2, "Lunch"), DINNER(
			3, "Dinner");
	int value;
	String message;

	foodTiming(int value, String msg) {
		this.value = value;
		this.message = msg;
	}

	public int getValue() {
		return this.value;
	}

	public String getMsg() {
		return message;
	}

	public static String[] getMessages() {
		String[] msgs = { BREAKFAST.getMsg(), LUNCH.getMsg(),
				DINNER.getMsg() };
		return msgs;
	}

	public static int valueByMsg(String msg) {
		for (foodTiming f : foodTiming.values()) {
			if (f.getMsg().equalsIgnoreCase(msg)) {
				return f.getValue();
			}

		}
		return BREAKFAST.getValue();
	}
}
