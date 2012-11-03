package com.maveric;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class ExportZoneActivity extends TabActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expert_talk);
		TabHost th = (TabHost) findViewById(android.R.id.tabhost);
		TabSpec expertTag = th.newTabSpec("experttalk");
		TabSpec askQuestion = th.newTabSpec("askquestion");

		expertTag.setIndicator("EXPERT TALK").setContent(
				new Intent(this, ExpertTalkActivity.class));

		/*
		 * ask question
		 */

		askQuestion.setIndicator("ASK A QUESTION").setContent(
				new Intent(this, ExpertTalkActivity.class)
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

	}
}
