package com.maveric.enums;

import com.maveric.DashBoardActivity;
import com.maveric.DietTrackerActivity;
import com.maveric.WorkoutTrackerActivity;
import com.maveric.WorkoutTrackerViewerActivity;

public enum calender {
	CAlENDAR(0, DashBoardActivity.class), DIET_TRACKER(1,
			DietTrackerActivity.class), WORK_OUT_TRACKER(2,
			WorkoutTrackerViewerActivity.class);
	Integer value;
	Class<?> activity;

	calender(int value, Class<?> activity) {
		this.value = value;
		this.activity = activity;
	}

	public Integer getValue() {
		return this.value;
	}

	public Class<?> getActivity() {
		return this.activity;
	}

	public static calender getCalenderByValue(int v) {
		for (calender s : calender.values()) {
			if (s.getValue().equals(v)) {
				return s;
			}
		}
		return calender.CAlENDAR;
	}
}
