package com.maveric;

import android.os.Bundle;

public class WorkoutTrackerActivity  extends MavericBaseActiity {
	
	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.workout_tracker);

	}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}