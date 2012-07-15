package com.maveric;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MetobolicChartActivity extends MavericBaseActiity {
	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.metobolic_chart);

	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Button gotoMetobolicQueries = (Button)findViewById(R.id.matabolic_queries);
		gotoMetobolicQueries.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				metabolicQueries();
				
			}
		});
		TextView lastQueriesAnswer =(TextView) findViewById(R.id.lastQueriesAnswer);
		lastQueriesAnswer.setText(new Apppref(context).getLastMetabolicQueriesResult()+" "+"is");
	}
}
