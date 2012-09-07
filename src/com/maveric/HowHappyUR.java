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

	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.how_happy_u_r);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final LinearLayout default1 = (LinearLayout) findViewById(R.id.default1);
		final LinearLayout selected = (LinearLayout) findViewById(R.id.selected);
		ImageView normal = (ImageView) findViewById(R.id.normal);
		ImageView smile = (ImageView) findViewById(R.id.smile);
		ImageView sad = (ImageView) findViewById(R.id.sad);
		ImageView normal_sad = (ImageView) findViewById(R.id.normal_sad);
		ImageView very_smile = (ImageView) findViewById(R.id.very_smile);

		pref = new Apppref(getApplicationContext());
		if (pref.getHowHappyUR(getCurrentDate()) == 0) {
			default1.setVisibility(View.VISIBLE);
			selected.setVisibility(View.GONE);
		} else {
			default1.setVisibility(View.GONE);
			selected.setVisibility(View.VISIBLE);
			setImage(pref.getHowHappyUR(getCurrentDate()));
		}
		normal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pref.setHowHappyUR(getCurrentDate(), 1);
				setValues(default1, selected, 1);

			}
		});
		smile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pref.setHowHappyUR(getCurrentDate(), 2);
				setValues(default1, selected, 2);

			}
		});
		sad.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pref.setHowHappyUR(getCurrentDate(), 3);
				setValues(default1, selected, 3);

			}
		});
		normal_sad.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pref.setHowHappyUR(getCurrentDate(), 4);
				setValues(default1, selected, 4);

			}
		});
		very_smile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pref.setHowHappyUR(getCurrentDate(), 5);
				setValues(default1, selected, 5);

			}
		});
	}

	private void setValues(LinearLayout gone, LinearLayout visible, int value) {
		visible.setVisibility(View.VISIBLE);
		gone.setVisibility(View.GONE);
		setImage(value);
		if (pref.getHowHappyUR(getCurrentDate()) == 3
				&& pref.getHowHappyUR(prevDate(getCurrentDate())) == 3
				&& pref.getHowHappyUR(prevDate(prevDate(getCurrentDate()))) == 3) {
			Intent home = new Intent(context, metabolicQueries.class);
			startActivity(home);
		}
	}

	private void setImage(int value) {
		selectedImg = (ImageView) findViewById(R.id.selected_img);

		switch (value) {
		case 1:
			selectedImg.setBackgroundResource(R.drawable.normal);
			break;
		case 2:
			selectedImg.setBackgroundResource(R.drawable.smile);
			break;
		case 3:
			selectedImg.setBackgroundResource(R.drawable.sad);
			break;
		case 4:
			selectedImg.setBackgroundResource(R.drawable.normal_sad);
			break;
		case 5:
			selectedImg.setBackgroundResource(R.drawable.very_smile);
			break;

		default:
			break;
		}
	}
}
