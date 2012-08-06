package com.maveric;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.maveric.contentprovider.FoodProvider;
import com.maveric.database.model.FoodTrackerTable;
import com.maveric.enums.foodTiming;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public abstract class MavericBaseActiity extends Activity {
	protected Context context;
	protected TextView diet, workOut, metaBolic, inter;
	private int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
	private ProgressDialog progressDialog;

	protected ArrayList<String> breakFastKeyValues = new ArrayList<String>(),
			lunchKeyValues = new ArrayList<String>(),
			dinnerKeyValues = new ArrayList<String>();
	protected HashMap<String, String[]> breakFastMap = new HashMap<String, String[]>(),
			lunchMap = new HashMap<String, String[]>(),
			dinnerMap = new HashMap<String, String[]>();
	protected int breakfastCal = 0, lunchCal = 0, dinnerCal = 0;
	protected Button home;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this.getApplicationContext();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentToLayout();
		diet = (TextView) findViewById(R.id.Diet_tracker);
		workOut = (TextView) findViewById(R.id.workout_tracker);
		metaBolic = (TextView) findViewById(R.id.metapolic_typing);
		inter = (TextView) findViewById(R.id.intract);
		home = (Button) findViewById(R.id.home_button);
		/* login Activity not use for nw via menu */
		// member.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// Intent singup = new Intent(context, LoginActivity.class);
		// startActivity(singup);
		// }
		// });
		if (diet != null) {
			diet.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent singup = new Intent(context,
							DietTrackerActivity.class);
					startActivity(singup);
				}
			});
			workOut.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent singup = new Intent(context,
							WorkoutTrackerViewerActivity.class);
					startActivity(singup);
				}
			});
			metaBolic.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Log.i("manikk",
							"new Apppref(context).isQueriesAlreadyAnswerd() ="
									+ new Apppref(context)
											.isQueriesAlreadyAnswerd());
					if (!new Apppref(context).isQueriesAlreadyAnswerd())
						metabolicQueries();
					else {
						Intent metabolicChart = new Intent(context,
								MetobolicChartActivity.class);
						startActivity(metabolicChart);
					}
				}
			});
			inter.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent singup = new Intent(context,
							StaticPageMainActivity.class);
					startActivity(singup);
				}
			});
		}
		if (home != null)
			home.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					loding("Loding");
					Intent home = new Intent(context, WorkSummeryActivity.class)
							.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(home);
				}
			});
	}

	protected abstract void setContentToLayout();

	protected void toast(String text) {
		Toast.makeText(context, text, Toast.LENGTH_LONG).show();
	}

	protected float getBmi(float weight, float height) {
		float mheight = height / 100;

		return (float) (weight / (mheight * mheight));
	}

	protected float getRecWater(float weight) {
		return (float) (2.20462262 * 0.03 * weight);
	}

	protected float recWeight(float weight, float height) {
		float mheight = height / 100;
		return (float) (getBmi(weight, height) * mheight * mheight);
	}

	protected boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null;
	}

	protected void metabolicQueries() {
		Intent singup = new Intent(context, metabolicQueries.class);
		startActivity(singup);
	}

	protected String getCurrentDate() {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		return format.format(c.getTime());
	}

	private String getStringFromDate(Long date) {
		return new SimpleDateFormat("dd-MM-yyyy").format(date);
	}

	protected String prevDate(String date) {
		return getStringFromDate(getDateFromString(date).getTime()
				- MILLIS_IN_DAY);
	}

	private Date getDateFromString(String date) {
		try {
			return (Date) new SimpleDateFormat("dd-MM-yyyy").parse(date);
		} catch (ParseException e) {
			Log.e("manikk", e.getMessage());
			return null;
		}
	}

	protected String nextDate(String date) {
		return getStringFromDate(getDateFromString(date).getTime()
				+ MILLIS_IN_DAY);
	}

	protected void loding(String title) {
		progressDialog = ProgressDialog.show(MavericBaseActiity.this, title
				+ "...", "your request is Processing");

		new Thread() {
			public void run() {
				try {
					sleep(1000);
				} catch (Exception e) {
					if (progressDialog != null) {
						progressDialog.dismiss();
					}
				}
				progressDialog.dismiss();
			}
		}.start();
	}

	protected void onDestroy() {
		super.onDestroy();
		Log.i("manikk", "Base Destroy");
		if (progressDialog != null)
			progressDialog.dismiss();
	}

	protected void setValues(String date) {
		resetAll();
		try {
			Uri foodListUri = Uri.withAppendedPath(
					FoodProvider.FOOD_BY_DATE_TIMING_URI, date);
			Cursor foodList = managedQuery(foodListUri, null, null, null, null);
			if (foodList.moveToFirst()) {
				do {

					String name = foodList.getString(foodList
							.getColumnIndex(FoodTrackerTable.Column.NAME));
					int food_type = Integer
							.valueOf(foodList.getString(foodList
									.getColumnIndex(FoodTrackerTable.Column.FOOD_TYPE)));
					String calories = foodList.getString(foodList
							.getColumnIndex(FoodTrackerTable.Column.CALORIES));
					String serving = foodList.getString(foodList
							.getColumnIndex(FoodTrackerTable.Column.SERVE));

					Log.i("manikk",
							"name = "
									+ name
									+ " cal = "
									+ calories
									+ " foodtype = "
									+ food_type
									+ " fav = "
									+ foodList.getString(foodList
											.getColumnIndex(FoodTrackerTable.Column.FAV_STATE)));
					if (food_type == foodTiming.BREAKFAST.getValue()) {
						breakFastKeyValues.add(name);
						breakFastMap.put(name,
								new String[] { calories, serving });
						breakfastCal += Integer.valueOf(calories);
					} else if (food_type == foodTiming.LUNCH.getValue()) {
						lunchKeyValues.add(name);
						lunchMap.put(name, new String[] { calories, serving });
						lunchCal += Integer.valueOf(calories);
					} else if (food_type == foodTiming.DINNER.getValue()) {
						dinnerKeyValues.add(name);
						dinnerMap.put(name, new String[] { calories, serving });
						dinnerCal += Integer.valueOf(calories);
					}

				} while (foodList.moveToNext());

			}
			Log.i("manikk", "breakFastKeyValues = " + breakFastKeyValues.size()
					+ " lunchKeyValues = " + lunchKeyValues.size()
					+ " dinnerKeyValues = " + dinnerKeyValues.size());
			Log.i("manikk", "breakFastMap = " + breakFastMap.size()
					+ " lunchMap = " + lunchMap.size() + " dinnerMap = "
					+ dinnerMap.size());
			Log.i("manikk", "breakfastCal = " + breakfastCal + " lunchCal = "
					+ lunchCal + " dinnerCal = " + dinnerCal);
		} catch (Exception e) {
			Log.e("DietTrackerActivity", e.getMessage(), e);
		}
	}

	private void resetAll() {
		lunchKeyValues.clear();
		breakFastKeyValues.clear();
		dinnerKeyValues.clear();
		breakFastMap.clear();
		lunchMap.clear();
		dinnerMap.clear();
		breakfastCal = 0;
		lunchCal = 0;
		dinnerCal = 0;
	}

	protected int getTotalFoodCalories() {
		setValues(getCurrentDate());
		return breakfastCal + dinnerCal + lunchCal;
	}
}
