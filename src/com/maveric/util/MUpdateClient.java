package com.maveric.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.maveric.Apppref;
import com.maveric.contentprovider.ExceriseProvider;
import com.maveric.database.model.ExceriseValue;
import com.maveric.database.model.FoodTable;

public class MUpdateClient {
	Context ctx;
	private Apppref appPref;

	public void insertNewDataIfAvailabe(Context context) {
		this.ctx = context;
		appPref = new Apppref(ctx);
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

		try {
			String foodUrl = "http://122.165.34.103/maveric/web/app_dev.php/m/food/list/"
					+ appPref.getLastTime1();
			Log.i("mohan", "FoodURL" + foodUrl);

			WSclient wc = new WSclient(foodUrl, ctx);
			JSONArray ja = wc.getData();
			Log.i("mohan", "Food Response" + ja);

			if (ja.length() > 0) {
				String time1 = null;
				for (int i = 0; i < ja.length(); i++) {
					JSONObject jo = (JSONObject) ja.get(i);
					ContentValues values = new ContentValues();
					values.put(FoodTable.Column.NAME, jo.getString("name"));
					values.put(FoodTable.Column.CALORIES,
							jo.getString("calories"));
					values.put(FoodTable.Column.FAT, jo.getString("fat"));
					values.put(FoodTable.Column.CARBOS, jo.getString("carbos"));
					values.put(FoodTable.Column.PROTIN, jo.getString("protin"));
					values.put(FoodTable.Column.UPDATED,
							jo.getString(ExceriseValue.Column.UPDATED));
					values.put(FoodTable.Column.CREATED,
							jo.getString(ExceriseValue.Column.CREATED));
					time1 = jo.getString(ExceriseValue.Column.UPDATED);

					Log.i("mohan", "food values" + values);
//					ctx.getContentResolver().insert(ExceriseProvider.FOOD_URI,
//							values);

				}
				appPref.setLastTime1(time1);
			}
		} catch (JSONException e) {
			Log.i("mohan", "error" + e.getMessage());
		}

	}

	private void checkNewExcercise() {

		try {
			String excerciseUrl = "http://122.165.34.103/maveric/web/app_dev.php/m/excercise/list/"
					+ appPref.getLastTime();

			Log.i("mohan", "exURL" + excerciseUrl);
			WSclient wc = new WSclient(excerciseUrl, ctx);
			JSONArray ja = wc.getData();
			if (ja.length() > 0) {
				String time = null;
				for (int i = 0; i < ja.length(); i++) {
					JSONObject jo = (JSONObject) ja.get(i);
					ContentValues values = new ContentValues();
					values.put(ExceriseValue.Column.EXCERISE_TYPE,
							jo.getString("name"));
					values.put(ExceriseValue.Column.CALORIES,
							jo.getString("calories"));
					values.put(ExceriseValue.Column.UPDATED,
							jo.getString(ExceriseValue.Column.UPDATED));
					values.put(ExceriseValue.Column.CREATED,
							jo.getString(ExceriseValue.Column.CREATED));
					time = jo.getString(ExceriseValue.Column.UPDATED);
					ctx.getContentResolver().insert(
							ExceriseProvider.EXCERISETYPE_URI, values);

				}
				appPref.setLastTime(time);
			}
		} catch (JSONException e) {
			Log.i("mohan", "error" + e.getMessage());
		}

	}

}
