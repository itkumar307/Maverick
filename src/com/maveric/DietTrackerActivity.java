package com.maveric;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DietTrackerActivity extends MavericBaseActiity {

	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.dietviewcontainer);
	}

	private TextView breakFastTitle, lunchTitle, dinnerTitle, dateText;

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
		Button user_food = (Button) findViewById(R.id.user_food);
		user_food.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isNetworkAvailable()) {
					Intent prof = new Intent(context, Webview.class);
					prof.putExtra("url", getString(R.string.FOOD_API));
					prof.putExtra("title", "Diet");
					startActivity(prof);
				} else
					toast(getString(R.string.NO_INTERNET_CONNECTION));

			}
		});
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dateText.setText(nextDate(dateText.getText().toString()));
				setTextValues();
			}
		});
		prev.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dateText.setText(prevDate(dateText.getText().toString()));
				setTextValues();
			}
		});
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				loding("Loding", 1000);
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

	private void gotoResultActivity(HashMap<String, String[]> map,
			ArrayList<String> list, String title) {
		loding("Loding", 1000);
		Intent result = new Intent(this, DietTrackerResultActivity.class);
		result.putExtra("map", map);
		result.putStringArrayListExtra("keys", list);
		result.putExtra("title", title);
		startActivity(result);
		overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
	}

	@Override
	public void onResume() {
		super.onResume();
		setTextValues();

	}

	private void setTextValues() {
		setValues(dateText.getText().toString());
		breakFastTitle.setText(breakfastCal + "");
		lunchTitle.setText(lunchCal + "");
		dinnerTitle.setText(dinnerCal + "");
	}
}
