package com.maveric;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.maveric.database.model.WorkOutTrackerTable;

public class WorkoutAdapter extends SimpleCursorAdapter {
	private int viewlayout;
	private LayoutInflater mInflater;

	public WorkoutAdapter(Context context, Cursor cursor, String[] from,
			int[] to) {

		super(context, R.layout.worktrackerlistdata, cursor, from, to);
		try {
			this.viewlayout = R.layout.worktrackerlistdata;
			mInflater = LayoutInflater.from(context);

		} catch (Exception e) {
			Log.e("kumar" + this.getClass(),
					"Error in set title adapter" + e.getMessage());
		}
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		try {

			String exceriseType = cursor
					.getString(cursor
							.getColumnIndex(WorkOutTrackerTable.Column.SELECT_EXCERISE));
//			int calories = cursor.getInt(cursor
//					.getColumnIndex(WorkOutTrackerTable.Column.CALORIES));
			int workoutTime = cursor.getInt(cursor
					.getColumnIndex(WorkOutTrackerTable.Column.WORKOUT));
			TextView exceriseTypeValue = (TextView) view
					.findViewById(R.id.listexcerisetype);
			exceriseTypeValue.setText(exceriseType);

			TextView workoutValue = (TextView) view
					.findViewById(R.id.listworkouttime);
			workoutValue.setText("Min:" + workoutTime);

//			TextView caloriesValue = (TextView) view
//					.findViewById(R.id.listcalories);
			//caloriesValue.setText("cal:" + calories);

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
