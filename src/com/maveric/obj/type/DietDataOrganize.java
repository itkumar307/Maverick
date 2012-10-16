package com.maveric.obj.type;

import android.content.Context;

public class DietDataOrganize {

	private Context ctx;
	private String dinner1 = "a", dinner2 = "a", dinner3 = "a";
	private String breakfast1 = "a", breakfast2 = "a", breakfast3 = "a";
	private String lunch1 = "a", lunch2 = "a", lunch3 = "a";
	private String snacks1 = "a", snacks2 = "a", snacks3 = "a";
	int breakfastCount1 = 9, breakfastCount2 = 9, breakfastCount3 = 9;
	int dinnerCount1 = 9, dinnerCount2 = 9, dinnerCount3 = 9;
	int lunchCount1 = 9, lunchCount2 = 9, lunchCount3 = 9;
	int snacksCount1 = 9, snacksCount2 = 9, snacksCount3 = 9;

	public DietDataOrganize(Context ctx) {
		this.ctx = ctx;
	}

	/*
	 * start with breakfast food
	 */
	public void setBreakFast1(String food) {
		this.breakfast1 = food;
	}

	public String getBreakFast1() {
		return breakfast1;
	}

	public void setBreakFast2(String food) {
		this.breakfast2 = food;
	}

	public String getBreakFast2() {
		return breakfast2;
	}

	public void setBreakFast3(String food) {
		this.breakfast3 = food;
	}

	public String getBreakFast3() {
		return breakfast3;
	}

	/*
	 * breakfastCount
	 */
	public void setBreakFastCount1(int count) {
		this.breakfastCount1 = count;
	}

	public int getBreakFastCount1() {
		return breakfastCount1;
	}

	public void setBreakFastCount2(int count) {
		this.breakfastCount2 = count;
	}

	public int getBreakFastCount2() {
		return breakfastCount2;
	}

	public void setBreakFastCount3(int count) {
		this.breakfastCount3 = count;
	}

	public int getBreakFastCount3() {
		return breakfastCount3;
	}

	/*
	 * Lunch food
	 */
	public void setLunch1(String food) {
		this.lunch1 = food;
	}

	public String getLunch1() {
		return lunch1;
	}

	public void setLunch2(String food) {
		this.lunch2 = food;
	}

	public String getLunch2() {
		return lunch2;
	}

	public void setLunch3(String food) {
		this.lunch3 = food;
	}

	public String getLunch3() {
		return lunch3;
	}

	/*
	 * Lunch count
	 */

	public void setLunchCount1(int count) {
		this.lunchCount1 = count;
	}

	public int getLunchCount1() {
		return lunchCount1;
	}

	public void setLunchCount2(int count) {
		this.lunchCount2 = count;
	}

	public int getLunchCount2() {
		return lunchCount2;
	}

	public void setLunchCount3(int count) {
		this.lunchCount3 = count;
	}

	public int getLunchCount3() {
		return lunchCount3;
	}

	/*
	 * dinner food
	 */

	public void setDinner1(String food) {
		this.dinner1 = food;
	}

	public String getDinner1() {
		return dinner1;
	}

	public void setDinner2(String food) {
		this.dinner2 = food;
	}

	public String getDinner2() {
		return dinner2;
	}

	public void setDinner3(String food) {
		this.dinner3 = food;
	}

	public String getDinner3() {
		return dinner3;
	}

	/*
	 * Dinner count
	 */
	public void setDinnerCount1(int food) {
		this.dinnerCount1 = food;
	}

	public int getDinnerCount1() {
		return dinnerCount1;
	}

	public void setDinnerCount2(int food) {
		this.dinnerCount2 = food;
	}

	public int getDinnerCount2() {
		return dinnerCount2;
	}

	public void setDinnerCount3(int food) {
		this.dinnerCount3 = food;
	}

	public int getDinnerCount3() {
		return dinnerCount3;
	}

	/*
	 * snacks food
	 */
	public void setSnacks1(String food) {
		this.snacks1 = food;
	}

	public String getSnacks1() {
		return snacks1;
	}

	public void setSnacks2(String food) {
		this.snacks2 = food;
	}

	public String getSnacks2() {
		return snacks2;
	}

	public void setSnacks3(String food) {
		this.snacks3 = food;
	}

	public String getSnacks3() {
		return snacks3;
	}

	/*
	 * snacks count
	 */
	public void setSnacksCount1(int food) {
		this.snacksCount1 = food;
	}

	public int getSnacksCount1() {
		return snacksCount1;
	}

	public void setSnacksCount2(int food) {
		this.snacksCount2 = food;
	}

	public int getSnacksCount2() {
		return snacksCount2;
	}

	public void setSnacksCount3(int food) {
		this.snacksCount3 = food;
	}

	public int getSnacksCount3() {
		return snacksCount3;
	}

}
