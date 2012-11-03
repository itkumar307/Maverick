package com.maveric;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class ExportZoneActivity extends TabActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.expert_talk);
		Button home = (Button) findViewById(R.id.home_button);
		if (home != null)
			home.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					Intent home = new Intent(ExportZoneActivity.this,
							DashBoardActivity.class)
							.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

					startActivity(home);
				}
			});
		TabHost th = (TabHost) findViewById(android.R.id.tabhost);
		TabSpec expertTag = th.newTabSpec("tab_id_1");
		TabSpec askQuestion = th.newTabSpec("tab_id_2");

		try {
			expertTag.setIndicator("EXPERT TALK").setContent(
					new Intent(this, ExpertTalkActivity.class));

			/*
			 * ask question
			 */

			askQuestion
					.setIndicator("ASK A QUESTION")
					.setContent(
							new Intent(this, Webview.class)
									.putExtra("askquestion", "askquestion")
									.putExtra("title", "Ask our expert")
									.putExtra(
											"url",
											getString(R.string.HTTP)
													+ getString(R.string.HTTP_DOMAIN)
													+ getString(R.string.HTTP_SUB)
													+ getString(R.string.HTTP_USER_ASK)));
			th.addTab(expertTag);
			th.addTab(askQuestion);
		} catch (Exception e) {
			Log.e("kumar", "tab error" + e.getMessage());
		}

	}
}
