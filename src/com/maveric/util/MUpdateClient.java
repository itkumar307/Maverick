package com.maveric.util;

import org.json.JSONArray;

import android.content.Context;
import android.util.Log;

public class MUpdateClient {
	Context ctx;

	public void insertNewDataIfAvailabe(Context context) {
		this.ctx = context;

		/*
		 * Check New Food available
		 */

		checkNewFood();

		/*
		 * check New Exercise available
		 */

		checkNewExcercise();
	}

	private void checkNewFood() {
		String foodUrl = "http://122.165.34.103/maveric/web/app_dev.php/m/excercise/list";
		WSclient wc = new WSclient(foodUrl, ctx);
		JSONArray ja = wc.getData();
		Log.i("mohan","json res food"+ja);
		
	}

	private void checkNewExcercise() {

		String excerciseUrl = "http://122.165.34.103/maveric/web/app_dev.php/m/excercise/list";
		WSclient wc = new WSclient(excerciseUrl, ctx);
		JSONArray ja = wc.getData();
		Log.i("mohan","json res Excer"+ja);
	}
	
	

}
