package com.maveric;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maveric.contentprovider.WorkoutProvider;
import com.maveric.database.model.WorkOutTrackerTable;
import com.maveric.util.MUpdateClient;

public class WorkSummeryActivity extends MavericBaseActiity {
	Cursor Wrktoday;
	TextView welcome, workout1200, workoutshow, food1200, foodshow;
	Apppref appPref;
	RelativeLayout foodTrack, workoutTrack;
	ProgressBar pbWork, pbDiet;

	Context context;

	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.welcomesummary);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		context = getApplicationContext();
		appPref = new Apppref(context);
		welcome = (TextView) findViewById(R.id.welcometext);

		pbWork = (ProgressBar) findViewById(R.id.progressbarworkout);
		pbDiet = (ProgressBar) findViewById(R.id.progressbardiet);

		workout1200 = (TextView) findViewById(R.id.workout_currentvalue);
		workoutshow = (TextView) findViewById(R.id.todayworkoutValue);
		food1200 = (TextView) findViewById(R.id.Diet_currentvalues);
		foodshow = (TextView) findViewById(R.id.todayeatenValue);

		foodTrack = (RelativeLayout) findViewById(R.id.tracfoodlayout);
		workoutTrack = (RelativeLayout) findViewById(R.id.trackworkoutlayout);

		/*
		 * check new updates
		 */
		try {
			MUpdateClient mc = new MUpdateClient();
			mc.insertNewDataIfAvailabe(context);
		} catch (Exception e) {
			Log.i("MavericHomeActivity", "Error in update app");

			foodTrack.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent home = new Intent(context,
							DietTrackerFoodSearch.class);
					home.putExtra("date", getCurrentDate());
					startActivity(home);
				}
			});

			workoutTrack.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent home = new Intent(context,
							WorkoutTrackerActivity.class);
					startActivity(home);

				}
			});
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (exitOnBackButton() && keyCode == KeyEvent.KEYCODE_BACK
				&& event.getRepeatCount() == 0) {
			displayExitAlert();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	protected boolean exitOnBackButton() {
		return true;
	}

	private void displayExitAlert() {
		AlertDialog dialog = new AlertDialog.Builder(WorkSummeryActivity.this)
				.create();
		dialog.setTitle(R.string.DIALOGMESSAGE);
		dialog.setButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				finish();
			}
		});
		dialog.setButton2("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				WorkSummeryActivity.this.closeOptionsMenu();
			}
		});
		dialog.show();
	}

	@Override
	public void onResume() {
		super.onResume();

		setValue();
	}

	private void setValue() {

		try {
			String value = "0";

			welcome.setText("WELCOME" + "  " + appPref.getUserNameOnly());
			Uri name = Uri.withAppendedPath(
					WorkoutProvider.WORKOUT_BY_DATE_SOMEVALUE_URI,
					getCurrentDate());

			Wrktoday = managedQuery(name, null, null, null, null);
			Wrktoday.moveToFirst();
			Log.i("kumar", "workcount" + Wrktoday.getCount());

			if (Wrktoday.getCount() > 0) {
				value = Wrktoday.getString(Wrktoday
						.getColumnIndex(WorkOutTrackerTable.Column.COUNT));
				Log.i("kumartotalcalories", "value" + value);

			} else {
				value = "0";
			}
			int cal = !TextUtils.isEmpty(value) ? Integer.parseInt(value) : 0;
			workout1200.setText(cal + " /5000 cal");
			workoutshow.setText(cal + " Cal");
			pbWork.setMax(5000);
			pbWork.setProgress(cal);
			pbWork.setIndeterminate(false);
			food1200.setText(getTotalFoodCalories() + " /5000 cal");
			foodshow.setText(getTotalFoodCalories() + " Cal");
			pbDiet.setMax(5000);
			pbDiet.setProgress(getTotalFoodCalories());
			pbDiet.setIndeterminate(false);
		} catch (Exception e) {
			Log.e("kumar", "setvalue" + e.getMessage(), e);
		}
	}
}