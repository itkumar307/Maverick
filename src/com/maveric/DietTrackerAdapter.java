package com.maveric;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.maveric.database.model.FoodTrackerTable;

public class DietTrackerAdapter extends SimpleCursorAdapter {
	private int viewlayout;
	private LayoutInflater mInflater;

	public DietTrackerAdapter(Context context, Cursor cursor, String[] from,
			int[] to) {

		super(context, R.layout.diettrackerlistdata, cursor, from, to);
		try {
			this.viewlayout = R.layout.diettrackerlistdata;
			mInflater = LayoutInflater.from(context);

		} catch (Exception e) {
			Log.e("kumar" + this.getClass(),
					"Error in set title adapter" + e.getMessage());
		}
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		try {

			String breakfast = cursor.getString(cursor
					.getColumnIndex(FoodTrackerTable.Column.FOOD_BREAKFAST));
			String lunch = cursor.getString(cursor
					.getColumnIndex(FoodTrackerTable.Column.FOOD_LUNCH));
			String dinner = cursor.getString(cursor
					.getColumnIndex(FoodTrackerTable.Column.FOOD_DINNER));
			String snacks = cursor.getString(cursor
					.getColumnIndex(FoodTrackerTable.Column.FOOD_SNACK));

			TextView breakfastValue = (TextView) view
					.findViewById(R.id.breakfastlist);
			breakfastValue.setText(breakfast);

			TextView lunchValue = (TextView) view.findViewById(R.id.lunchlist);
			lunchValue.setText(lunch);

			TextView dinnerValue = (TextView) view
					.findViewById(R.id.dinnerlist);
			dinnerValue.setText(dinner);

			TextView snacksValue = (TextView) view.findViewById(R.id.snacklist);
			snacksValue.setText(snacks);

		} catch (Exception e) {
			Log.e("kumar" + this.getClass(),
					"Error bind view " + e.getMessage(), e);
		}
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View v;
		v = mInflater.inflate(viewlayout, null);
		return v;
	}
}
