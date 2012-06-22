package com.maveric;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class LoginActivity extends MavericBaseActiity {

	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.login_sreen);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RelativeLayout loginWithEmailNext = (RelativeLayout) findViewById(R.id.next);
		RelativeLayout loginWithCurrentDetailNext = (RelativeLayout) findViewById(R.id.current_detail_next);
		RelativeLayout loginWithTargetDetailNext = (RelativeLayout) findViewById(R.id.target_detail_next);
		RelativeLayout loginWithMavericIdNext = (RelativeLayout) findViewById(R.id.mavric_id_next);

		final LinearLayout loginWithEmail = (LinearLayout) findViewById(R.id.login_with_email);
		LinearLayout loginWithCurrentDetail = (LinearLayout) findViewById(R.id.login_with_current_detail);
		final LinearLayout loginWithTargetDetail = (LinearLayout) findViewById(R.id.login_with_target_detail);
		final LinearLayout loginWithMavericId = (LinearLayout) findViewById(R.id.login_with_maveric_id);

		final LinearLayout[] logins = { loginWithCurrentDetail, loginWithEmail,
				loginWithMavericId, loginWithTargetDetail };

		loginWithEmailNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO
			}
		});
		loginWithCurrentDetailNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setLoginScreen(logins, loginWithTargetDetail);
			}
		});
		loginWithTargetDetailNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setLoginScreen(logins, loginWithMavericId);
			}
		});
		loginWithMavericIdNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setLoginScreen(logins, loginWithEmail);
			}
		});
	}

	private void setLoginScreen(LinearLayout[] logins, LinearLayout visibleLogin) {
		for (LinearLayout login : logins) {
			if (login.equals(visibleLogin))
				login.setVisibility(View.VISIBLE);
			else
				login.setVisibility(View.GONE);

		}

	}
}
