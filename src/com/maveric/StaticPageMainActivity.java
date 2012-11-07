package com.maveric;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class StaticPageMainActivity extends MavericBaseActiity {

	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.static_page_activity);

	}

	TextView whyMaverick;
	TextView knowYour;
	TextView stressManagement;
	TextView strength;
	TextView warmup;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		whyMaverick = (TextView) findViewById(R.id.whymaverickact);
		stressManagement = (TextView) findViewById(R.id.stressmanagementact);
		knowYour = (TextView) findViewById(R.id.knowyouract);
		strength = (TextView) findViewById(R.id.strengthact);
		warmup = (TextView) findViewById(R.id.warmupact);

		TextView gifImage = (TextView) findViewById(R.id.gif);
		TextView askOurExpert = (TextView) findViewById(R.id.ask_our_expert);
		TextView expertTalk = (TextView) findViewById(R.id.expert_talk);

		gifImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent gif = new Intent(context,
						ExceriseImageShowActivity.class);
				startActivity(gif);

			}
		});
		askOurExpert.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				callUrl(getString(R.string.HTTP)
						+ getString(R.string.HTTP_DOMAIN)
						+ getString(R.string.HTTP_SUB)
						+ getString(R.string.HTTP_USER_ASK), "Ask our expert");

			}
		});
		expertTalk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				callUrl(getString(R.string.HTTP)
						+ getString(R.string.HTTP_DOMAIN)
						+ getString(R.string.HTTP_SUB)
						+ getString(R.string.HTTP_USER_TALK), "Expert talk");

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

	private void callUrl(String url, String title) {
		if (isNetworkAvailable()) {
			Intent prof = new Intent(context, Webview.class);
			prof.putExtra("url", url);
			prof.putExtra("title", title);
			startActivity(prof);
		} else
			toast(getString(R.string.NO_INTERNET_CONNECTION));
	}

}