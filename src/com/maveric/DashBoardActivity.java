package com.maveric;

import com.maveric.enums.calender;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
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
		RelativeLayout quiz = (RelativeLayout) findViewById(R.id.quiz);
		RelativeLayout calentar = (RelativeLayout) findViewById(R.id.calentar);
		RelativeLayout expertZone = (RelativeLayout) findViewById(R.id.exper_zone);
		RelativeLayout dietTracker = (RelativeLayout) findViewById(R.id.diet_tracker);
		RelativeLayout workoutTracker = (RelativeLayout) findViewById(R.id.workoutTracker);
		RelativeLayout staticPage = (RelativeLayout) findViewById(R.id.more);

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
		quiz.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent queries = new Intent(context, Queries.class);
				startActivity(queries);

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
				Intent expertZone = new Intent(context,
						ExportZoneActivity.class);
				startActivity(expertZone);

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
		staticPage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent singup = new Intent(context,
						StaticPageMainActivity.class);
				startActivity(singup);

			}
		});
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
		AlertDialog dialog = new AlertDialog.Builder(DashBoardActivity.this)
				.create();
		dialog.setTitle(R.string.DIALOGMESSAGE);
		dialog.setButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				finish();
			}
		});
		dialog.setButton2("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				DashBoardActivity.this.closeOptionsMenu();
			}
		});
		dialog.show();
	}
}
