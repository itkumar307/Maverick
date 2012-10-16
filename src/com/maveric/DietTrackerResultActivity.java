package com.maveric;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.widget.TextView;

public class DietTrackerResultActivity extends MavericListBaseActiity {

	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.diet_tracker_result);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView titleText = (TextView) findViewById(R.id.title);
		Bundle bundle = getIntent().getExtras();
		@SuppressWarnings("unchecked")
		HashMap<String, String[]> map = (HashMap<String, String[]>) bundle
				.getSerializable("map");
		ArrayList<String> keys = bundle.getStringArrayList("keys");
		titleText.setText(bundle.getString("title"));
		setListAdapter(new FoodListAdapter(context, map,
				keys.toArray(new String[keys.size()])));
	}
}
