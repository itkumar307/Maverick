package com.maveric;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WorkinChart extends MavericBaseActiity {
	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.metobolic_chart);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Button gotoWorkQueries = (Button) findViewById(R.id.matabolic_queries);
		Apppref app = new Apppref(context);
		gotoWorkQueries.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent Workin_workoutIntent = new Intent(context,
						Workin_workout.class);
				startActivity(Workin_workoutIntent);
				WorkinChart.this.finish();

			}
		});
		TextView lastQueriesAnswer = (TextView) findViewById(R.id.lastQueriesAnswer);
		LinearLayout matabolic_detail = (LinearLayout) findViewById(R.id.matabolic_detail);
		matabolic_detail.setVisibility(View.GONE);
		lastQueriesAnswer.setText(app.getLastWorkQueriesResult());
	}
}
