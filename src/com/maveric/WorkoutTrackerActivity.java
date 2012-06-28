package com.maveric;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.maveric.contentprovider.ExceriseProvider;
import com.maveric.contentprovider.WorkoutProvider;
import com.maveric.database.model.ExceriseValue;
import com.maveric.database.model.WorkOutTrackerTable;
import com.maveric.obj.type.MaverickDataOrganize;

public class WorkoutTrackerActivity extends MavericBaseActiity {

	RelativeLayout exceriseSelect;
	RelativeLayout exceriseTime;
	MaverickDataOrganize maverickData;
	Context ctx;
	Button previousLog;
	Button saveWorkoutData;
	Cursor workoutInfo;
	int width;
	TextView exceriseText;
	TextView workoutText;

	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.workoutcontainer);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Display display = getWindowManager().getDefaultDisplay();
		width = display.getWidth();

		ctx = getApplicationContext();

		maverickData = new MaverickDataOrganize(ctx);

		exceriseSelect = (RelativeLayout) findViewById(R.id.selectexercisevalue);
		exceriseTime = (RelativeLayout) findViewById(R.id.selectworkoutvalue);

		previousLog = (Button) findViewById(R.id.workout_date_logs);

		saveWorkoutData = (Button) findViewById(R.id.workoutsave);

		exceriseText = (TextView) findViewById(R.id.selected_exercise_text);

		workoutText = (TextView) findViewById(R.id.selectworkouttext);

		// try {
		// workoutInfo = managedQuery(WorkoutProvider.WORKOUT_URI, null, null,
		// null, null);
		//
		// workoutInfo.moveToFirst();
		//
		// Log.i("kumar",
		// "date details"
		// + workoutInfo.getString(workoutInfo
		// .getColumnIndex(WorkOutTrackerTable.Column.DATE)));
		// } catch (Exception e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }

		exceriseSelect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				exceriseTypeInput();

			}
		});
		exceriseTime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				workTimeInput();

			}
		});

		saveWorkoutData.setOnClickListener(new OnClickListener() {
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

	private void exceriseTypeInput() {
		try {
			final Cursor exceriseCursor = managedQuery(
					ExceriseProvider.EXCERISETYPE_URI, null, null, null, null);
			if (exceriseCursor.getCount() > 0) {
				final Dialog listDialog = new Dialog(
						WorkoutTrackerActivity.this, R.style.PauseDialog);

				listDialog
						.setTitle(getString(R.string.EXCERISE_TYPE_DIALOG_HEAD));
				WindowManager.LayoutParams transparentView = listDialog
						.getWindow().getAttributes();
				transparentView.alpha = 0.7f;

				listDialog.getWindow().setAttributes(transparentView);

				LayoutInflater inflater = (LayoutInflater) WorkoutTrackerActivity.this
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = inflater.inflate(R.layout.alertlist, null, false);
				listDialog.setContentView(view);
				LayoutParams params = listDialog.getWindow().getAttributes();
				params.width = (int) (width / 1.5);
				listDialog.getWindow().setAttributes(
						(android.view.WindowManager.LayoutParams) params);
				listDialog.setCanceledOnTouchOutside(true);

				ListView list = (ListView) listDialog
						.findViewById(R.id.alertlist);
				list.setAdapter(new SimpleCursorAdapter(
						WorkoutTrackerActivity.this,
						R.layout.data_select_input_cardatat, exceriseCursor,
						new String[] { ExceriseValue.Column.EXCERISE_TYPE },
						new int[] { R.id.titlename }));
				listDialog.show();
				list.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> adapter, View view,
							int id, long position) {
						String exceriseTypeValue = exceriseCursor.getString(exceriseCursor
								.getColumnIndex(ExceriseValue.Column.EXCERISE_TYPE));

						maverickData.setExceriseType(exceriseTypeValue);
						Log.i("kumar:" + this.getClass(),
								"Input of ExceriseTypedata:"
										+ exceriseTypeValue);
						exceriseText.setText(exceriseTypeValue);
						listDialog.dismiss();
						overridePendingTransition(R.anim.prev_slidein,
								R.anim.prev_slideout);
					}
				});
			} else {
				// TODO
			}

		} catch (Exception e) {
			Log.e("kumar" + this.getClass(), "excerise input function failed."
					+ e.getMessage(),e);
		}

	}

	private void workTimeInput() {
		try {
			final Integer[] arrayInput = new Integer[24];
			int listPosition = 0;
			for (int i = 1; i < 25; i++) {
				arrayInput[listPosition] = i;
				listPosition++;
			}
			final Dialog listDialog = new Dialog(WorkoutTrackerActivity.this,
					R.style.PauseDialog);
			listDialog.setTitle(R.string.CALORIES_DIALOG_HEAD);
			WindowManager.LayoutParams transparentView = listDialog.getWindow()
					.getAttributes();
			transparentView.alpha = 0.7f;
			listDialog.getWindow().setAttributes(transparentView);

			LayoutInflater inflater = (LayoutInflater) WorkoutTrackerActivity.this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.alertlist, null, false);
			listDialog.setContentView(view);
			LayoutParams params = listDialog.getWindow().getAttributes();
			params.width = width / 2;

			listDialog.setCanceledOnTouchOutside(true);

			ListView list = (ListView) listDialog.findViewById(R.id.alertlist);

			list.setAdapter(new ArrayAdapter<Integer>(
					WorkoutTrackerActivity.this,
					R.layout.data_select_input_cardatat, arrayInput));
			listDialog.show();
			listDialog.getWindow().setAttributes(
					(android.view.WindowManager.LayoutParams) params);

			list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> adapter, View view,
						int id, long position) {
					try {
						int arrayValue = arrayInput[id];

						maverickData.setExceriseTimeWorking(arrayValue);

						Log.i("kumar:" + this.getClass(),
								"Input of ExceriseworkoutTime:" + arrayValue);
						workoutText.setText("" + arrayValue);
						listDialog.dismiss();
						overridePendingTransition(R.anim.prev_slidein,
								R.anim.prev_slideout);
					} catch (Exception e) {
						Log.e("kumar" + this.getClass(),
								"excerise input function failed."
										+ e.getMessage());
					}
				}

			});
		} catch (Exception e) {
			Log.e("kumar" + this.getClass(),
					"selectthevaluefromdialogue failed." + e.getMessage());
		}

	}
}
