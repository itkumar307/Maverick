package com.maveric;

import com.maveric.enums.calender;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class DashBoardActivity extends MavericBaseActiity {

	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.dashboard);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RelativeLayout myProfile = (RelativeLayout) findViewById(R.id.my_profile);
		RelativeLayout workout = (RelativeLayout) findViewById(R.id.workout);
		RelativeLayout calentar = (RelativeLayout) findViewById(R.id.calentar);
		RelativeLayout expertZone = (RelativeLayout) findViewById(R.id.exper_zone);
		RelativeLayout dietTracker = (RelativeLayout) findViewById(R.id.diet_tracker);
		RelativeLayout workoutTracker = (RelativeLayout) findViewById(R.id.workoutTracker);

		myProfile.setOnClickListener(new OnClickListener() {

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
		workout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		calentar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent calenders = new Intent(context,
						CalendarViewActivity.class);
				calenders.putExtra("class", calender.CAlENDAR.getValue());
				startActivity(calenders);

			}
		});
		expertZone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		dietTracker.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				gotoDietTracker();

			}
		});
		workoutTracker.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				gotoWorkoutTracker();

			}
		});
	}
}
