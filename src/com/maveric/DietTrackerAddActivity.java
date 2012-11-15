package com.maveric;

import java.util.HashMap;
import java.util.Map.Entry;

import android.content.ContentValues;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.maveric.contentprovider.FoodProvider;
import com.maveric.database.model.FoodTrackerTable;
import com.maveric.enums.foodTiming;

public class DietTrackerAddActivity extends MavericBaseActiity {

	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.diet_tracker_add);
	}

	private Spinner foodTiming1;
	private EditText serving_count;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView selectedFood = (TextView) findViewById(R.id.diet_tracker_selected_food);
		foodTiming1 = (Spinner) findViewById(R.id.foodTiming);
		serving_count = (EditText) findViewById(R.id.serving_editbox);
		Button savingFoodItems = (Button) findViewById(R.id.save_food);
		Button cancel = (Button) findViewById(R.id.cancel_food);
		Bundle bundle = getIntent().getExtras();
		TextView unit = (TextView) findViewById(R.id.unit);
		@SuppressWarnings("unchecked")
		final HashMap<String, String> selectedFoodDetails = (HashMap<String, String>) bundle
				.getSerializable("foodmap");
		foodTypeAdapter();
		selectedFood.setText(selectedFoodDetails
				.get(FoodTrackerTable.Column.NAME));
		unit.setText(selectedFoodDetails.get(FoodTrackerTable.Column.UNIT));
		savingFoodItems.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				addFood(selectedFoodDetails);

			}
		});
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DietTrackerAddActivity.this.finish();

			}
		});

	}

	private void foodTypeAdapter() {

		ArrayAdapter<CharSequence> foodType = new ArrayAdapter<CharSequence>(
				this, android.R.layout.simple_spinner_item);
		foodType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		for (String f : foodTiming.getMessages())
			foodType.add(f);
		foodTiming1.setAdapter(foodType);

	}

	private void addFood(HashMap<String, String> selectedFoodDetails) {
		loding("Saving", 1000);
		CheckBox diet_tracker_add_as_fav = (CheckBox) findViewById(R.id.diet_tracker_add_as_fav);
		ContentValues values = new ContentValues();
		for (Entry<String, String> foods : selectedFoodDetails.entrySet()) {
			values.put(foods.getKey(), foods.getValue());
		}
		if (!TextUtils.isEmpty(serving_count.getText().toString())) {
			String serves = ""
					+ (Integer.valueOf(serving_count.getText().toString()) / Integer
							.valueOf(getIntent().getExtras().getString("serve")));
			values.put(FoodTrackerTable.Column.SERVE, serves);
			values.put(FoodTrackerTable.Column.USERSERVE, serving_count
					.getText().toString());
			values.put(FoodTrackerTable.Column.FAV_STATE,
					diet_tracker_add_as_fav.isChecked() ? 1 : 0);
			values.put(FoodTrackerTable.Column.FOOD_TYPE, foodTiming
					.valueByMsg(foodTiming1.getSelectedItem().toString()));
			values.put(FoodTrackerTable.Column.DATE, getIntent().getExtras()
					.getString("date"));
			getContentResolver().insert(FoodProvider.INSERT_FOOD_DETAILS_URI,
					values);
			this.finish();
		} else
			toast("Please fill your serving");
	}
}
