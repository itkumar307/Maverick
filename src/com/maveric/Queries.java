package com.maveric;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Queries extends MavericBaseActiity {

	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.queries);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Button metabolic = (Button) findViewById(R.id.metapolic_queries1);
		Button workin = (Button) findViewById(R.id.workin_queries);
		final Apppref app = new Apppref(context);
		metabolic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!app.isQueriesAlreadyAnswerd())
					metabolicQueries();
				else {
					Intent metabolicChart = new Intent(context,
							MetobolicChartActivity.class);
					startActivity(metabolicChart);
				}
				Queries.this.finish();
			}
		});
		workin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!app.isWorkQueriesAlreadyAnswerd())
					workinQueries();
				else {
					Intent workin = new Intent(context, WorkinChart.class);
					startActivity(workin);
				}
				Queries.this.finish();
			}
		});
	}

	private void workinQueries() {
		Intent Workin_workoutIntent= new Intent(context, Workin_workout.class);
		startActivity(Workin_workoutIntent);
	}
}
