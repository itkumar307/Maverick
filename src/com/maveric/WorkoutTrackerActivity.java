package com.maveric;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.maveric.contentprovider.WorkoutProvider;
import com.maveric.database.model.WorkOutTrackerTable;
import com.maveric.enums.ExceriseType;
import com.maveric.obj.type.MaverickDataOrganize;

public class WorkoutTrackerActivity extends MavericBaseActiity {

	Spinner exceriseSelect;
	Spinner exceriseTime;
	MaverickDataOrganize maverickData;
	Context ctx;
	Button addFavourite;
	Cursor workoutInfo;

	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.workoutcontainer);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ctx = getApplicationContext();

		maverickData = new MaverickDataOrganize(ctx);

		exceriseSelect = (Spinner) findViewById(R.id.selectexercisevalue);
		exceriseTime = (Spinner) findViewById(R.id.selectworkoutvalue);

		addFavourite = (Button) findViewById(R.id.favourite);

		try {
			workoutInfo = managedQuery(WorkoutProvider.WORKOUT_URI, null, null,
					null, null);
			
			workoutInfo.moveToFirst();

			Log.i("kumar",
					"date details"
							+ workoutInfo.getString(workoutInfo
									.getColumnIndex(WorkOutTrackerTable.Column.DATE)));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		exeriseTypeAdapter();
		exeriseWorkoutAdapter();

		exceriseSelect.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				String exceriseData = exceriseSelect.getSelectedItem()
						.toString();
				maverickData.setExceriseType(exceriseData);
				Log.i("kumar:" + this.getClass(), "Input of FExceriseTypedata:"
						+ exceriseData);

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});

		exceriseTime.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				String exceriseTimeData = exceriseTime.getSelectedItem()
						.toString();
				maverickData.setExceriseTimeWorking(Integer
						.parseInt(exceriseTimeData));
				Log.i("kumar:" + this.getClass(),
						"Input of ExceriseworkoutTime:" + exceriseTimeData);

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});

		addFavourite.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				try {
					ContentValues values = new ContentValues();
					values.put(WorkOutTrackerTable.Column.SELECT_EXCERISE,
							maverickData.getExceriseType());
					values.put(WorkOutTrackerTable.Column.WORKOUT,
							maverickData.getExceriseTimeWorking());

					getContentResolver().insert(
							WorkoutProvider.INSERT_WORKOUT_DETAILS_URI, values);
				} catch (Exception e) {
					Log.i("kumar:" + this.getClass(),
							"error in sve data into workout table"
									+ e.getMessage());
				}
			}
		});
	}

	private void exeriseTypeAdapter() {
		ArrayAdapter<CharSequence> exceriseType = new ArrayAdapter<CharSequence>(
				this, android.R.layout.simple_spinner_item);
		exceriseType
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		for (String msg : ExceriseType.exceriseMessages()) {
			exceriseType.add(msg);
		}
		exceriseSelect.setAdapter(exceriseType);

	}

	private void exeriseWorkoutAdapter() {
		ArrayAdapter<Integer> exceriseTimeAdapter = new ArrayAdapter<Integer>(
				this, android.R.layout.simple_spinner_item);
		exceriseTimeAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		for (int i = 1; i < 25; i++) {
			exceriseTimeAdapter.add(i);
		}

		exceriseTime.setAdapter(exceriseTimeAdapter);

	}

}