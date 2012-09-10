package com.maveric;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class StaticPageMainActivity extends MavericBaseActiity {

	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.static_page_activity);

	}

	ImageView whyMaverick;
	ImageView knowYour;
	ImageView stressManagement;
	ImageView strength;
	ImageView warmup;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		whyMaverick = (ImageView) findViewById(R.id.whymaverickact);
		stressManagement = (ImageView) findViewById(R.id.stressmanagementact);
		knowYour = (ImageView) findViewById(R.id.knowyouract);
		strength = (ImageView) findViewById(R.id.strengthact);
		warmup = (ImageView) findViewById(R.id.warmupact);

		TextView gifImage = (TextView) findViewById(R.id.gif);

		gifImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent singup = new Intent(context,
						ExceriseImageShowActivity.class);
				startActivity(singup);

			}
		});

		whyMaverick.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent s = new Intent(StaticPageMainActivity.this,
						StaticWhyMaverick.class);
				startActivity(s);

			}
		});
		strength.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent s = new Intent(StaticPageMainActivity.this,
						StaticStrengthBasic.class);
				startActivity(s);

			}
		});
		stressManagement.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent s = new Intent(StaticPageMainActivity.this,
						StaticStressManagement.class);
				startActivity(s);

			}
		});
		knowYour.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent s = new Intent(StaticPageMainActivity.this,
						StaticKnowYourBasic.class);
				startActivity(s);

			}
		});
		warmup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent s = new Intent(StaticPageMainActivity.this,
						StaticWarmUp.class);
				startActivity(s);

			}
		});

	}

}