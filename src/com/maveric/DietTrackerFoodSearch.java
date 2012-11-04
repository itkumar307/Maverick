package com.maveric;

import java.util.HashMap;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.maveric.contentprovider.FoodProvider;
import com.maveric.database.model.FoodTable;
import com.maveric.database.model.FoodTrackerTable;

public class DietTrackerFoodSearch extends MavericListBaseActiity {

	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.diet_tracker_search);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final EditText searchBox = (EditText) findViewById(R.id.diet_search_editbox);
		// Button searchButton = (Button) findViewById(R.id.diet_search_button);
		Button loadFav = (Button) findViewById(R.id.load_fav);

		searchBox.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				String searchData = searchBox.getText().toString();

				if (!TextUtils.isEmpty(searchData)) {
					Uri searchList = Uri.withAppendedPath(
							FoodProvider.FOOD_LIST_BY_SEARCH_VALUE, searchBox
									.getText().toString());
					displayFoods(searchList, false);
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		// searchButton.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// InputMethodManager inputManager = (InputMethodManager)
		// getSystemService(Context.INPUT_METHOD_SERVICE);
		//
		// inputManager.hideSoftInputFromWindow(getCurrentFocus()
		// .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		//
		// if (!TextUtils.isEmpty(searchBox.getText().toString())) {
		// Uri searchList = Uri.withAppendedPath(
		// FoodProvider.FOOD_LIST_BY_SEARCH_VALUE, searchBox
		// .getText().toString());
		// displayFoods(searchList, false);
		// } else
		// toast("Please enter a food type to search for");
		// }
		// });
		loadFav.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				displayFoods(FoodProvider.FAV_FOOD_URI, true);
			}
		});
	}

	private void displayFoods(Uri uri, Boolean t) {

		final Cursor foodList = managedQuery(uri, null, null, null, null);
		TextView emp = (TextView) findViewById(R.id.empty);
		emp.setVisibility(View.GONE);
		if (foodList.getCount() == 0 && t) {
			emp.setText("No favourites entered");
			emp.setVisibility(View.VISIBLE);
		}
		// toast("No favourites entered");
		else if (foodList.getCount() == 0 && !t) {
			emp.setText("Pls contact admin by clicking request add food item link");
			emp.setVisibility(View.VISIBLE);
		}
		// toast("Pls contact admin by clicking request add food item link");
		Log.i("manikk", "search list count = " + foodList.getCount());
		// loding("Loading", 1000);
		ListView list = getListView();
		list.setAdapter(new SimpleCursorAdapter(DietTrackerFoodSearch.this,
				R.layout.data_select_input_cardatat, foodList,
				new String[] { FoodTable.Column.NAME },
				new int[] { R.id.titlename }));
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Log.i("manikk", "search item click ");
				try {
					HashMap<String, String> selectedFoodDetails = new HashMap<String, String>();
					selectedFoodDetails.put(FoodTrackerTable.Column.NAME,
							foodList.getString(foodList
									.getColumnIndex(FoodTable.Column.NAME)));
					selectedFoodDetails.put(FoodTrackerTable.Column.CALORIES,
							foodList.getString(foodList
									.getColumnIndex(FoodTable.Column.CALORIES)));
					selectedFoodDetails.put(FoodTrackerTable.Column.CARBOS,
							foodList.getString(foodList
									.getColumnIndex(FoodTable.Column.CARBOS)));
					selectedFoodDetails.put(FoodTrackerTable.Column.FAT,
							foodList.getString(foodList
									.getColumnIndex(FoodTable.Column.FAT)));
					selectedFoodDetails.put(FoodTrackerTable.Column.PROTIN,
							foodList.getString(foodList
									.getColumnIndex(FoodTable.Column.PROTIN)));
					selectedFoodDetails.put(
							FoodTrackerTable.Column.UNIT,
							foodList.getString(foodList
									.getColumnIndex(FoodTrackerTable.Column.UNIT)));
				String serve = foodList.getString(foodList
						.getColumnIndex(FoodTrackerTable.Column.SERVE));

				// String foodValue = foodList.getString(foodList
				// .getColumnIndex(FoodTable.Column.NAME));
				// int selectedCalories = foodList.getInt(foodList
				// .getColumnIndex(FoodTable.Column.CALORIES));
				// int selectedCorbos = foodList.getInt(foodList
				// .getColumnIndex(FoodTable.Column.CARBOS));
				// int selectedFat = foodList.getInt(foodList
				// .getColumnIndex(FoodTable.Column.FAT));
				// int selectedProtien = foodList.getInt(foodList
				// .getColumnIndex(FoodTable.Column.PROTIN));

				Intent foodAdd = new Intent(DietTrackerFoodSearch.this,
						DietTrackerAddActivity.class);
				foodAdd.putExtra("foodmap", selectedFoodDetails);
				foodAdd.putExtra("serve", serve);
				foodAdd.putExtra("date",
						getIntent().getExtras().getString("date"));
				startActivity(foodAdd);
				DietTrackerFoodSearch.this.finish();
				} catch (Exception e) {
					Log.e("manikk", e.getMessage(), e);
				}
			}
		});
	}
}
