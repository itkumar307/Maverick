package com.maveric;

import java.util.HashMap;
import java.util.List;

import com.maveric.R.string;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FoodListAdapter extends ArrayAdapter<String> {
	private final Context context;
	private HashMap<String, String[]> map;
	private String[] keys;

	public FoodListAdapter(Context context, HashMap<String, String[]> objects,
			String[] keys) {

		super(context, R.layout.diet_tracker_list_data, keys);

		this.context = context;
		map = objects;
		this.keys = keys;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.diet_tracker_list_data,
				parent, false);
		try {
			TextView foodItemTextView = (TextView) rowView
					.findViewById(R.id.diet_name);
			TextView foodServeTextView = (TextView) rowView
					.findViewById(R.id.diet_serve);
			TextView foodCalTextView = (TextView) rowView
					.findViewById(R.id.diet_colories);
			String[] fooditem = map.get(keys[position]);
			foodItemTextView.setText(keys[position]);
			foodServeTextView.setText(fooditem[1]+"serving");
			foodCalTextView.setText(fooditem[0]+"cal");
			Log.i("manikk", "food Adapter fooditem.size = " + fooditem.length
					+ " fooditem[0]" + fooditem[0]);
		} catch (Exception e) {
			Log.e("food Adapter", e.getMessage(), e);
		}

		return rowView;
	}
}
