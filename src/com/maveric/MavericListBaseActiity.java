package com.maveric;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public abstract class MavericListBaseActiity extends ListActivity {
	protected Context context;
	protected TextView diet, workOut, metaBolic, inter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this.getApplicationContext();
		setContentToLayout();
		diet = (TextView) findViewById(R.id.Diet_tracker);
		workOut = (TextView) findViewById(R.id.workout_tracker);
		metaBolic = (TextView) findViewById(R.id.metapolic_typing);
		inter = (TextView) findViewById(R.id.intract);

		/* login Activity not use for nw via menu */
		// member.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// Intent singup = new Intent(context, LoginActivity.class);
		// startActivity(singup);
		// }
		// });
		if (diet != null) {
			diet.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent singup = new Intent(context,
							DietTrackerActivity.class);
					startActivity(singup);
				}
			});
			workOut.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent singup = new Intent(context,
							WorkoutTrackerActivity.class);
					startActivity(singup);
				}
			});
			metaBolic.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Toast.makeText(context,
							"This menu not yet associated with any page !",
							1500).show();
				}
			});
			inter.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent singup = new Intent(context,
							MavericHomeActivity.class);
					startActivity(singup);
				}
			});
		}
	}

	protected abstract void setContentToLayout();

	protected void toast(String text) {
		Toast.makeText(context, text, Toast.LENGTH_LONG).show();
	}

	protected float getBmi(float weight, float height) {
		float mheight = height / 100;

		return (float) (weight / (mheight * mheight));
	}

	protected float getRecWater(float weight) {
		return (float) (2.20462262 * 0.03 * weight);
	}

	protected float recWeight(float weight, float height) {
		float mheight = height / 100;
		return (float) (getBmi(weight, height) * mheight * mheight);
	}

	protected boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null;
	}
}
