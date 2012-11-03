package com.maveric;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maveric.contentprovider.WorkoutProvider;
import com.maveric.database.model.WorkOutTrackerTable;

public class WorkSummeryActivity extends MavericBaseActiity {
	Cursor Wrktoday;
	TextView welcome, workout1200, food1200, foodshow;
	Apppref appPref;
	RelativeLayout foodTrack, workoutTrack;
	ProgressBar pbWork, pbDiet;
	String selectedDate;
	Context context;

	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.welcomesummary);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle dateSelected = getIntent().getExtras();
		selectedDate = dateSelected.getString("date");
		context = getApplicationContext();
		appPref = new Apppref(context);
		welcome = (TextView) findViewById(R.id.welcometext);

		pbWork = (ProgressBar) findViewById(R.id.progressbarworkout);
		pbDiet = (ProgressBar) findViewById(R.id.progressbardiet);

		workout1200 = (TextView) findViewById(R.id.workout_currentvalue);

		food1200 = (TextView) findViewById(R.id.Diet_currentvalues);
		foodshow = (TextView) findViewById(R.id.todayeatenValue);

		foodTrack = (RelativeLayout) findViewById(R.id.tracfoodlayout);
		workoutTrack = (RelativeLayout) findViewById(R.id.trackworkoutlayout);

		TextView profile = (TextView) findViewById(R.id.profile);
		TextView water_link = (TextView) findViewById(R.id.water);
		
		howHappyUR.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i("manikk", "howHappyUR");
				Intent home = new Intent(context, HowHappyUR.class);
				home.putExtra("date", selectedDate);
				startActivity(home);

			}
		});
		profile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isNetworkAvailable()) {
					Intent prof = new Intent(context, Webview.class);
					Log.i("manikk", "profile = " + getString(R.string.HTTP)
							+ getString(R.string.HTTP_DOMAIN)
							+ getString(R.string.HTTP_SUB)
							+ getString(R.string.HTTP_PROFILE));
					prof.putExtra("url", getString(R.string.HTTP)
							+ getString(R.string.HTTP_DOMAIN)
							+ getString(R.string.HTTP_SUB)
							+ getString(R.string.HTTP_PROFILE));
					prof.putExtra("title", "profile");
					startActivity(prof);
				} else
					toast(getString(R.string.NO_INTERNET_CONNECTION));

			}
		});
		// RelativeLayout howHappyUR = (RelativeLayout)
		// findViewById(R.id.how_happy_u_r);
		try {
			if (water_link != null)
				water_link.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent wat = new Intent(WorkSummeryActivity.this,
								Water.class);
						wat.putExtra("date", selectedDate);
						startActivity(wat);

					}
				});
		} catch (Exception e) {
			// TODO: handle exception
		}

		foodTrack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Log.i("manikk", "foodTrack");
				Intent home = new Intent(context, DietTrackerFoodSearch.class);
				home.putExtra("date", selectedDate);
				startActivity(home);
			}
		});

		workoutTrack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Log.i("manikk", "workoutTrack");
				Intent home = new Intent(context, WorkoutTrackerActivity.class);
				home.putExtra("date", selectedDate);
				startActivity(home);

			}
		});

		/*
		 * check new updates
		 */
		try {
			// MUpdateClient mc = new MUpdateClient();
			// mc.insertNewDataIfAvailabe(context);
		} catch (Exception e) {
			Log.i("MavericHomeActivity", "Error in update app");

		}
	}

	

	@Override
	public void onResume() {
		super.onResume();

		setValue();
		setImage(appPref.getHowHappyUR(selectedDate));
	}

	private void setValue() {

		try {
			int value = 0;

			welcome.setText("Welcome" + "  " + appPref.getUserNameOnly());
			Uri name = Uri.withAppendedPath(
					WorkoutProvider.WORKOUT_BY_DATE_SOMEVALUE_URI,
					selectedDate);

			Wrktoday = managedQuery(name, null, null, null, null);
			Wrktoday.moveToFirst();
			Log.i("kumar", "workcount" + Wrktoday.getCount());

			if (Wrktoday.getCount() > 0) {
				value = Wrktoday.getInt(Wrktoday
						.getColumnIndex(WorkOutTrackerTable.Column.WORKOUT));
				Log.i("kumartotalcalories", "value" + value);

			} else {
				value = 0;
			}

			// int cal = !TextUtils.isEmpty(value) ? Integer.parseInt(value) :
			// 0;
			workout1200.setText(value + " /300 Mins");
			pbWork.setMax(300);
			pbWork.setProgress(value);
			pbWork.setIndeterminate(false);
			food1200.setText(getTotalFoodCalories(selectedDate) + " /5000 cal");
			foodshow.setText(getTotalFoodCalories(selectedDate) + " Cal");
			pbDiet.setMax(5000);
			pbDiet.setProgress(getTotalFoodCalories(selectedDate));
			pbDiet.setIndeterminate(false);
		} catch (Exception e) {
			Log.e("kumar", "setvalue" + e.getMessage(), e);
		}
	}

	private void setImage(int value) {
		Log.i("manikk", "WorkSummeryActivity img= " + value);
		ImageView selectedImg = (ImageView) findViewById(R.id.how_happy_img);

		switch (value) {
		case 1:
			selectedImg.setBackgroundResource(R.drawable.redface);
			break;
		case 2:
			Log.i("manikk", "WorkSummeryActivity case= " + value);
			selectedImg.setBackgroundResource(R.drawable.greenface);
			break;
		case 3:
			selectedImg.setBackgroundResource(R.drawable.blueface);
			break;
		case 4:
			selectedImg.setBackgroundResource(R.drawable.yellowface);
			break;

		default:
			selectedImg.setBackgroundResource(R.drawable.yellowface);
			break;
		}
	}
}