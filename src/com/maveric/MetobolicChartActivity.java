package com.maveric;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MetobolicChartActivity extends MavericBaseActiity {
	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.metobolic_chart);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Button gotoMetobolicQueries = (Button) findViewById(R.id.matabolic_queries);
		Apppref app = new Apppref(context);
		gotoMetobolicQueries.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				metabolicQueries();
				MetobolicChartActivity.this.finish();
			}
		});
		TextView lastQueriesAnswer = (TextView) findViewById(R.id.lastQueriesAnswer);

		if ("You are a Protein Type".equalsIgnoreCase(app
				.getLastMetabolicQueriesResult())) {
			lastQueriesAnswer.setText("You belong to PROTIEN TYPE category");
			RelativeLayout pro = (RelativeLayout) findViewById(R.id.protine_type_image);
			pro.setVisibility(View.VISIBLE);
		} else if ("You are a Mixed Type".equalsIgnoreCase(app
				.getLastMetabolicQueriesResult())) {
			lastQueriesAnswer.setText("You belong to MIXED TYPE category");
			RelativeLayout mixed = (RelativeLayout) findViewById(R.id.mixed_type_image);
			mixed.setVisibility(View.VISIBLE);
		} else if ("You are a Carbo Type".equalsIgnoreCase(app
				.getLastMetabolicQueriesResult())) {
			lastQueriesAnswer.setText("You belong to CARBO TYPE category");
			RelativeLayout mixed = (RelativeLayout) findViewById(R.id.carbo_type_image);
			mixed.setVisibility(View.VISIBLE);
		}

	}
}
