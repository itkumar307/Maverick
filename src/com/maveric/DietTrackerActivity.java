package com.maveric;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.maveric.contentprovider.FoodProvider;
import com.maveric.database.model.FoodTable;
import com.maveric.database.model.FoodTrackerTable;
import com.maveric.enums.foodTiming;

public class DietTrackerActivity extends MavericBaseActiity {

	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.dietviewcontainer);
	}

	private TextView breakFastTitle, lunchTitle, dinnerTitle, dateText;
	private ListView list2, list1, list3;
	private List<String> keyValues = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dateText = (TextView) findViewById(R.id.diet_tracker_date);
		breakFastTitle = (TextView) findViewById(R.id.breakfast_textview);
		lunchTitle = (TextView) findViewById(R.id.lunch_textview);
		dinnerTitle = (TextView) findViewById(R.id.dinner_textview);
		Log.i("manikk", "date = " + getCurrentDate());
		dateText.setText(getCurrentDate());
		ImageView next = (ImageView) findViewById(R.id.date_next);
		ImageView prev = (ImageView) findViewById(R.id.date_prev);
		ImageView add = (ImageView) findViewById(R.id.add_diet);
		breakFastTitle.setText("morning");
		lunchTitle.setText("morning");
		dinnerTitle.setText("morning");
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dateText.setText(nextDate(dateText.getText().toString()));

			}
		});
		prev.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dateText.setText(prevDate(dateText.getText().toString()));

			}
		});
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent search = new Intent(context, DietTrackerFoodSearch.class);
				startActivity(search);

			}
		});
		setBreakFastList();
		setLunchList();
		setDinnerList();
	}

	private void setBreakFastList() {
		list1 = (ListView) findViewById(R.id.listView1);
		list1.setAdapter(getAdapter(foodTiming.BREAKFAST));
	}

	private void setLunchList() {
		list2 = (ListView) findViewById(R.id.listView2);
		list2.setAdapter(getAdapter(foodTiming.LUNCH));
	}

	private void setDinnerList() {
		list3 = (ListView) findViewById(R.id.listView3);
		list3.setAdapter(getAdapter(foodTiming.BREAKFAST));
	}

	private FoodListAdapter getAdapter(foodTiming timing) {
		return new FoodListAdapter(context, getMap(timing), keyValues);
	}

	private HashMap<String, String[]> getMap(foodTiming timing) {
		keyValues.clear();
		Uri foodListUri = Uri.withAppendedPath(
				FoodProvider.FOOD_BY_DATE_TIMING_URI, dateText.getText()
						.toString() + "/" + timing.getValue());
		HashMap<String, String[]> foodDetails = new HashMap<String, String[]>();
		//foodDetails.clear();
		Cursor foodList = managedQuery(foodListUri, null, null, null, null);
		Log.i("manikk", "get count for insert values = " + foodList.getCount());
		if (foodList.getCount() > 0) {
			foodList.moveToFirst();
			do {
				Log.i("manikk", "123");
				String[] setofString = {
						foodList.getString(foodList
								.getColumnIndex(FoodTrackerTable.Column.SERVE)),
						foodList.getString(foodList
								.getColumnIndex(FoodTrackerTable.Column.CARBOS)) };
				foodDetails.put(foodList.getString(foodList
						.getColumnIndex(FoodTable.Column.NAME)), setofString);
				keyValues.add(foodList.getString(foodList
						.getColumnIndex(FoodTable.Column.NAME)));
			} while (foodList.moveToNext());
		} 
		Log.i("manikk", "map size = "+foodDetails.size()+" keyvalues size1 = "+keyValues.size());
		return foodDetails;
	}
}
