package com.maveric;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.maveric.contentprovider.WorkoutProvider;
import com.maveric.database.model.WorkOutTrackerTable;
import com.maveric.obj.type.MaverickDataOrganize;

public class WorkoutTrackerViewerActivity extends MavericListBaseActiity {

	TextView previous;
	TextView next;
	TextView date;
	MaverickDataOrganize maverickData;
	Context ctx;
	Button close;
	Cursor cursorDate;
	Cursor cursorDetails;;
	TextView addWorkoutData;
	String nowDate;

	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.workoutviewcontainer);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ctx = getApplicationContext();

		Bundle dateSelected = getIntent().getExtras();

		nowDate = dateSelected.getString("date");

		maverickData = new MaverickDataOrganize(ctx);

		previous = (TextView) findViewById(R.id.prev_date);
		next = (TextView) findViewById(R.id.next_date);
		date = (TextView) findViewById(R.id.date);
		addWorkoutData = (TextView) findViewById(R.id.add_workout_data);
		TextView user_exercise = (TextView) findViewById(R.id.user_excercise);

		user_exercise.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isNetworkAvailable()) {
					Intent prof = new Intent(context, Webview.class);
					prof.putExtra("url", getString(R.string.HTTP)
							+ getString(R.string.HTTP_DOMAIN)
							+ getString(R.string.HTTP_SUB)
							+ getString(R.string.HTTP_WORKOUT));
					prof.putExtra("title", "Workout");
					startActivity(prof);
				} else
					toast(getString(R.string.NO_INTERNET_CONNECTION));

			}
		});

		previous.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				try {
					String previousDate = prevDate(date.getText().toString());
					date.setText(previousDate);
					listViewRefresh(previousDate);
				} catch (Exception e) {
					Log.e("kumar" + this.getClass(),
							"previous" + e.getMessage(), e);
				}

			}
		});

		/*
		 * select workoutdata from new or favourite item
		 */
		addWorkoutData.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				loding("Loading", 1000);
				Intent addData = new Intent(context,
						WorkoutTrackerActivity.class);
				addData.putExtra("date", date.getText().toString());
				startActivity(addData);

			}
		});

		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				try {
					String nextDate = nextDate(date.getText().toString());
					date.setText(nextDate);
					listViewRefresh(nextDate);

				} catch (Exception e) {
					Log.e("kumar" + this.getClass(), "next" + e.getMessage(), e);

				}

			}
		});

	}

	protected void listViewRefresh(String presentDate) {
		try {
			Log.i("kumar" + this.getClass(), "date" + presentDate);
			Uri name = Uri.withAppendedPath(
					WorkoutProvider.WORKOUT_BY_DATE_URI, presentDate);

			cursorDetails = managedQuery(name, null, null, null, null);

			Log.i("kumar" + this.getClass(), "count of cursor list view"
					+ cursorDetails.getCount());
			// cursorDetails.moveToFirst();

			// if (cursorDetails.getCount() > 0) {
			ListAdapter adapter = new WorkoutAdapter(this, cursorDetails,
					new String[] { WorkOutTrackerTable.Column.DATE },
					new int[] { R.id.listexcerisetype });
			setListAdapter(adapter);
			// } else {

			// }
		} catch (Exception e1) {
			Log.e("kumar" + this.getClass(), "cursor error" + e1.getMessage(),
					e1);

		}
	}

	@Override
	public void onResume() {
		super.onResume();
		try {
			date.setText(nowDate);
			listViewRefresh(nowDate);
		} catch (Exception e1) {
			Log.e("kumar" + this.getClass(),
					"cursor error load date" + e1.getMessage(), e1);
		}
	}
}
