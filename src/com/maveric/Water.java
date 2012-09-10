package com.maveric;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Water extends MavericBaseActiity {

	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.water_consum);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final LinearLayout beforeFill = (LinearLayout) findViewById(R.id.befor_fill);
		final LinearLayout aftFill = (LinearLayout) findViewById(R.id.after_fill);
		Button submit = (Button) findViewById(R.id.enter);
		Button edit = (Button) findViewById(R.id.water_edit);
		final EditText editText = (EditText) findViewById(R.id.water_editbox);
		final TextView water_result = (TextView) findViewById(R.id.water_result);
		final Apppref app = new Apppref(context);
		if (TextUtils.isEmpty(app.getWaterConsume(getCurrentDate())))
			goneVisible(aftFill, beforeFill);
		else {
			goneVisible(beforeFill, aftFill);
			setResultText(Integer
					.valueOf(app.getWaterConsume(getCurrentDate())));
			water_result.setText("Today you have consumed"
					+ app.getWaterConsume(getCurrentDate())
					+ " liters of water");
		}
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				goneVisible(beforeFill, aftFill);
				app.setWaterConsume(getCurrentDate(), editText.getText()
						.toString());
				setResultText(Integer.valueOf(app
						.getWaterConsume(getCurrentDate())));
				water_result.setText("Today your water consume is "
						+ app.getWaterConsume(getCurrentDate()) + " liter");

			}
		});
		edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				goneVisible(aftFill, beforeFill);
				editText.setText(app.getWaterConsume(getCurrentDate()));
			}
		});
	}

	private void goneVisible(LinearLayout gone, LinearLayout visible) {
		gone.setVisibility(View.GONE);
		visible.setVisibility(View.VISIBLE);
	}

	private void setResultText(int value) {
		TextView result = (TextView) findViewById(R.id.result_text);
		if (value < 3) {
			result.setText("Need to improve your water intake. Recommended water intake is 3 liters per day");
		} else if (value >= 3)
			result.setText("Good. Keep it up");
	}
}
