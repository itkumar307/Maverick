package com.maveric;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HowHappyUR extends MavericBaseActiity {
	private Apppref pref;
	private TextView selectedImg;
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
		
		LinearLayout angry = (LinearLayout) findViewById(R.id.angry);
		LinearLayout sad = (LinearLayout) findViewById(R.id.sad);
		LinearLayout neutral = (LinearLayout) findViewById(R.id.neutral);
		LinearLayout satisfied = (LinearLayout) findViewById(R.id.satisfied);
		LinearLayout happy = (LinearLayout) findViewById(R.id.happy);

		Bundle dateSelected = getIntent().getExtras();
		selectedDate = dateSelected.getString("date");

		pref = new Apppref(getApplicationContext());
		if (pref.getHowHappyUR(selectedDate) == 0) {
			setImage(0);
			default1.setVisibility(View.VISIBLE);
			selected.setVisibility(View.GONE);
		} else {
//			default1.setVisibility(View.GONE);
			selected.setVisibility(View.VISIBLE);
			setImage(pref.getHowHappyUR(selectedDate));
		}
		angry.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pref.setHowHappyUR(selectedDate, 1);
				setValues(default1, selected, 1);

			}
		});
		sad.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pref.setHowHappyUR(selectedDate, 2);
				setValues(default1, selected, 2);

			}
		});
		neutral.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pref.setHowHappyUR(selectedDate, 3);
				setValues(default1, selected, 3);

			}
		});
		satisfied.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pref.setHowHappyUR(selectedDate, 4);
				setValues(default1, selected, 4);

			}
		});
		happy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pref.setHowHappyUR(selectedDate, 5);
				setValues(default1, selected, 5);

			}
		});


	}

	private void setValues(LinearLayout gone, LinearLayout visible, int value) {
		visible.setVisibility(View.VISIBLE);
//		one.setVisibility(View.GONE);
		 setImage(value);
		// if (pref.getHowHappyUR(selectedDate) == 3
		// && pref.getHowHappyUR(prevDate(selectedDate)) == 3
		// && pref.getHowHappyUR(prevDate(prevDate(selectedDate))) == 3) {
		// Intent home = new Intent(context, metabolicQueries.class);
		// startActivity(home);
		// }
	}

	private void setImage(int value) {
		selectedImg = (TextView) findViewById(R.id.today);
		final ImageView red = (ImageView) findViewById(R.id.redc);
		final ImageView green = (ImageView) findViewById(R.id.greenstrike);
		final ImageView blue = (ImageView) findViewById(R.id.bluestrike);
		final ImageView yellow = (ImageView) findViewById(R.id.yellowstrike);
		final ImageView yellowc = (ImageView) findViewById(R.id.yellowc);
		ImageView[] img = {red, green, blue, yellow, yellowc};

		switch (value) {
		case 1:
			red.setBackgroundResource(R.drawable.dym);
			changeImg(img, red);
			selectedImg.setText("angry");
			break;
		case 2:
			blue.setBackgroundResource(R.drawable.dym);
			changeImg(img, blue);
			selectedImg.setText("sad");
			break;
		case 3:
			green.setBackgroundResource(R.drawable.dym);
			changeImg(img, green);
			selectedImg.setText("neutral");
			break;
		case 4:
			yellow.setBackgroundResource(R.drawable.dym);
			changeImg(img, yellow);
			selectedImg.setText("satisfied");
			break;
		case 5:
			yellowc.setBackgroundResource(R.drawable.dym);
			changeImg(img, yellowc);
			selectedImg.setText("happy");
			break;

		default:
			green.setBackgroundResource(R.drawable.dym);
			break;
		}
	}
	private void changeImg(ImageView[] imgs, ImageView img)
	{
		for (ImageView im : imgs )
		{
			if(!im.equals(img))
				im.setBackgroundResource(R.drawable.strike);
		}
	}
}
