package com.maveric;

import java.util.HashMap;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

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
		Button searchButton = (Button) findViewById(R.id.diet_search_button);
		searchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				displayFoods(searchBox.getText().toString());

			}
		});
	}

	private void displayFoods(String searchFood) {
		Uri searchList = Uri.withAppendedPath(
				FoodProvider.FOOD_LIST_BY_SEARCH_VALUE, searchFood);
		final Cursor foodList = managedQuery(searchList, null, null, null, null);
		Log.i("manikk","search list count = "+foodList.getCount());
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
				HashMap<String, String> selectedFoodDetails = new HashMap<String, String>();
				selectedFoodDetails.put(FoodTrackerTable.Column.NAME, foodList.getString(foodList
						.getColumnIndex(FoodTable.Column.NAME)));
				selectedFoodDetails.put(FoodTrackerTable.Column.CALORIES, foodList.getString(foodList
						.getColumnIndex(FoodTable.Column.CALORIES)));
				selectedFoodDetails.put(FoodTrackerTable.Column.CARBOS, foodList.getString(foodList
						.getColumnIndex(FoodTable.Column.CARBOS)));
				selectedFoodDetails.put(FoodTrackerTable.Column.FAT, foodList.getString(foodList
						.getColumnIndex(FoodTable.Column.FAT)));
				selectedFoodDetails.put(FoodTrackerTable.Column.PROTIN, foodList.getString(foodList
						.getColumnIndex(FoodTable.Column.PROTIN)));
				
//				String foodValue = foodList.getString(foodList
//						.getColumnIndex(FoodTable.Column.NAME));
//				int selectedCalories = foodList.getInt(foodList
//						.getColumnIndex(FoodTable.Column.CALORIES));
//				int selectedCorbos = foodList.getInt(foodList
//						.getColumnIndex(FoodTable.Column.CARBOS));
//				int selectedFat = foodList.getInt(foodList
//						.getColumnIndex(FoodTable.Column.FAT));
//				int selectedProtien = foodList.getInt(foodList
//						.getColumnIndex(FoodTable.Column.PROTIN));
				
				Intent foodAdd = new Intent(DietTrackerFoodSearch.this,DietTrackerAddActivity.class);
				foodAdd.putExtra("foodmap", selectedFoodDetails);
				startActivity(foodAdd);

			}
		});
	}
}
