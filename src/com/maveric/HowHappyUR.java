package com.maveric;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class HowHappyUR extends MavericBaseActiity {
	private Apppref pref;
	private ImageView selectedImg;
	String selectedDate;

	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.how_happy_u_r);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final LinearLayout default1 = (LinearLayout) findViewById(R.id.default1);
		final LinearLayout selected = (LinearLayout) findViewById(R.id.selected);
		ImageView red = (ImageView) findViewById(R.id.red);
		ImageView green = (ImageView) findViewById(R.id.green);
		ImageView blue = (ImageView) findViewById(R.id.blue);
		ImageView yellow = (ImageView) findViewById(R.id.yellow);

		Bundle dateSelected = getIntent().getExtras();
		selectedDate = dateSelected.getString("date");

		pref = new Apppref(getApplicationContext());
		if (pref.getHowHappyUR(selectedDate) == 0) {
			default1.setVisibility(View.VISIBLE);
			selected.setVisibility(View.GONE);
		} else {
			default1.setVisibility(View.GONE);
			selected.setVisibility(View.VISIBLE);
			setImage(pref.getHowHappyUR(selectedDate));
		}
		red.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pref.setHowHappyUR(selectedDate, 1);
				setValues(default1, selected, 1);

			}
		});
		green.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pref.setHowHappyUR(selectedDate, 2);
				setValues(default1, selected, 2);

			}
		});
		blue.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pref.setHowHappyUR(selectedDate, 3);
				setValues(default1, selected, 3);

			}
		});
		yellow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pref.setHowHappyUR(selectedDate, 4);
				setValues(default1, selected, 4);

			}
		});

	}

	private void setValues(LinearLayout gone, LinearLayout visible, int value) {
		visible.setVisibility(View.VISIBLE);
		gone.setVisibility(View.GONE);
		setImage(value);
//		if (pref.getHowHappyUR(selectedDate) == 3
//				&& pref.getHowHappyUR(prevDate(selectedDate)) == 3
//				&& pref.getHowHappyUR(prevDate(prevDate(selectedDate))) == 3) {
//			Intent home = new Intent(context, metabolicQueries.class);
//			startActivity(home);
//		}
	}

	private void setImage(int value) {
		selectedImg = (ImageView) findViewById(R.id.selected_img);

		switch (value) {
		case 1:
			selectedImg.setBackgroundResource(R.drawable.redface);
			break;
		case 2:
			selectedImg.setBackgroundResource(R.drawable.greenface);
			break;
		case 3:
			selectedImg.setBackgroundResource(R.drawable.blueface);
			break;
		case 4:
			selectedImg.setBackgroundResource(R.drawable.yellowface);
			break;

		default:
			break;
		}
	}
}
