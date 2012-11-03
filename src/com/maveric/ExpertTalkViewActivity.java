package com.maveric;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

public class ExpertTalkViewActivity extends MavericBaseActiity {

	Context ctx;
	TextView msg;

	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.experttakeview);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ctx = getApplicationContext();
		String dataString = getIntent().getExtras().getString("description");

		msg = (TextView) findViewById(R.id.experttextview);

		msg.setText(dataString);
	}
}
