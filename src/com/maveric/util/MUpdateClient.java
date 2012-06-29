package com.maveric.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.maveric.Apppref;
import com.maveric.contentprovider.ExceriseProvider;
import com.maveric.database.model.ExceriseValue;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

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
		String foodUrl = "http://122.165.34.103/maveric/web/app_dev.php/m/excercise/list";
		WSclient wc = new WSclient(foodUrl, ctx);
		JSONArray ja = wc.getData();
		Log.i("mohan", "json res food" + ja);

	}

	private void checkNewExcercise() {

		String excerciseUrl = "http://122.165.34.103/maveric/web/app_dev.php/m/excercise/list/"
				+ appPref.getLastTime();

		Log.i("mohan", "exURL" + excerciseUrl);
		WSclient wc = new WSclient(excerciseUrl, ctx);
		JSONArray ja = wc.getData();
		try {
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
