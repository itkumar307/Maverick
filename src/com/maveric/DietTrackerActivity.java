package com.maveric;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maveric.contentprovider.FoodProvider;
import com.maveric.database.model.FoodTrackerTable;
import com.maveric.enums.foodTiming;

public class DietTrackerActivity extends MavericBaseActiity {

	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.dietviewcontainer);
	}

	private TextView breakFastTitle, lunchTitle, dinnerTitle, dateText;
	private ArrayList<String> breakFastKeyValues = new ArrayList<String>(),
			lunchKeyValues = new ArrayList<String>(),
			dinnerKeyValues = new ArrayList<String>();
	private HashMap<String, String[]> breakFastMap = new HashMap<String, String[]>(),
			lunchMap = new HashMap<String, String[]>(),
			dinnerMap = new HashMap<String, String[]>();
	private int breakfastCal = 0, lunchCal = 0, dinnerCal = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dateText = (TextView) findViewById(R.id.diet_tracker_date);
		breakFastTitle = (TextView) findViewById(R.id.breakfasttext);
		lunchTitle = (TextView) findViewById(R.id.lunchtext);
		dinnerTitle = (TextView) findViewById(R.id.dinnertext);
		dateText.setText(getCurrentDate());
		ImageView next = (ImageView) findViewById(R.id.date_next);
		ImageView prev = (ImageView) findViewById(R.id.date_prev);
		ImageView add = (ImageView) findViewById(R.id.add_diet);
		RelativeLayout breakFastLayout = (RelativeLayout) findViewById(R.id.breakfastlayout);
		RelativeLayout lunchLayout = (RelativeLayout) findViewById(R.id.lunchlayout);
		RelativeLayout dinnerLayout = (RelativeLayout) findViewById(R.id.dinnerlayout);
		
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dateText.setText(nextDate(dateText.getText().toString()));
				setValues();
			}
		});
		prev.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dateText.setText(prevDate(dateText.getText().toString()));
				setValues();
			}
		});
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				loding("Loding");
				Intent search = new Intent(context, DietTrackerFoodSearch.class);
				search.putExtra("date", dateText.getText().toString());
				startActivity(search);

			}
		});
		breakFastLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (breakFastMap.size() > 0)
					gotoResultActivity(breakFastMap, breakFastKeyValues,
							"Break fast");
				else
					toast("please add the food for breakfast list");
			}
		});
		lunchLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (lunchMap.size() > 0)
					gotoResultActivity(lunchMap, lunchKeyValues, "Lunch");
				else
					toast("please add the food for Lunch list");
			}
		});
		dinnerLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (dinnerMap.size() > 0)
					gotoResultActivity(dinnerMap, dinnerKeyValues, "Dinner");
				else
					toast("please add the food for Dinner list");
			}
		});

	}

	private void setValues() {
		resetAll();
		try {
			Uri foodListUri = Uri.withAppendedPath(
					FoodProvider.FOOD_BY_DATE_TIMING_URI, dateText.getText()
							.toString());
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
			breakFastTitle.setText(breakfastCal + "");
			lunchTitle.setText(lunchCal + "");
			dinnerTitle.setText(dinnerCal + "");
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

	private void gotoResultActivity(HashMap<String, String[]> map,
			ArrayList<String> list, String title) {
		loding("Loding");
		Intent result = new Intent(this, DietTrackerResultActivity.class);
		result.putExtra("map", map);
		result.putStringArrayListExtra("keys", list);
		result.putExtra("title", title);
		startActivity(result);
		overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
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
	@Override
	public void onResume() {
		super.onResume();
		setValues();
	}
}
