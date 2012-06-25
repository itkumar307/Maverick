package com.maveric;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public abstract class MavericBaseActiity extends Activity {
	protected Context context;
	protected TextView member, diet, workOut, metaBolic, inter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this.getApplicationContext();
		setContentToLayout();
		member = (TextView) findViewById(R.id.member_zone);
		diet = (TextView) findViewById(R.id.Diet_tracker);
		workOut = (TextView) findViewById(R.id.workout_tracker);
		metaBolic = (TextView) findViewById(R.id.metapolic_typing);
		inter = (TextView) findViewById(R.id.intract);

		member.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent singup = new Intent(context,
						LoginActivity.class);
				startActivity(singup);
			}
		});
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
						"This menu not yet associated with any page !", 1500)
						.show();
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

	protected abstract void setContentToLayout();

}
