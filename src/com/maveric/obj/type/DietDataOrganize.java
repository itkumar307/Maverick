package com.maveric.obj.type;

import android.content.Context;

public class DietDataOrganize {

	private Context ctx;
	private String dinner;
	private String breakfast;
	private String lunch;
	private String snacks;

	public DietDataOrganize(Context ctx) {
		this.ctx = ctx;
	}

	public void setBreakFast(String food) {
		this.breakfast = food;
	}

	public String getBreakFast() {
		return breakfast;
	}

	public void setLunch(String food) {
		this.lunch = food;
	}

	public String getLunch() {
		return lunch;
	}

	public void setDinner(String food) {
		this.dinner = food;
	}

	public String getDinner() {
		return dinner;
	}

	public void setSnacks(String food) {
		this.snacks = food;
	}

	public String getSnacks() {
		return snacks;
	}

}
