package com.maveric;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public abstract class MavericBaseActiity extends Activity {
	protected Context context;
	protected TextView diet, workOut, metaBolic, inter;
	private int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this.getApplicationContext();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
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
							WorkoutTrackerViewerActivity.class);
					startActivity(singup);
				}
			});
			metaBolic.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Log.i("manikk",
							"new Apppref(context).isQueriesAlreadyAnswerd() ="
									+ new Apppref(context)
											.isQueriesAlreadyAnswerd());
					if (!new Apppref(context).isQueriesAlreadyAnswerd())
						metabolicQueries();
					else {
						Intent metabolicChart = new Intent(context,
								MetobolicChartActivity.class);
						startActivity(metabolicChart);
					}
				}
			});
			inter.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent singup = new Intent(context,
							StaticPageMainActivity.class);
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

	protected void metabolicQueries() {
		Intent singup = new Intent(context, metabolicQueries.class);
		startActivity(singup);
	}
	protected String getCurrentDate() {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat(
				"dd-MM-yyyy");
		return format.format(c.getTime());
	}

	private String getStringFromDate(Long date) {
		return new SimpleDateFormat("dd/MM/yy").format(date);
	}

	protected String prevDate(String date) {
		return getStringFromDate(getDateFromString(date).getTime()
				- MILLIS_IN_DAY);
	}

	private Date getDateFromString(String date) {
		try {
			return (Date) new SimpleDateFormat("dd/MM/yy").parse(date);
		} catch (ParseException e) {
			Log.e("manikk", e.getMessage());
			return null;
		}
	}

	protected String nextDate(String date) {
		return getStringFromDate(getDateFromString(date).getTime()
				+ MILLIS_IN_DAY);
	}
}
