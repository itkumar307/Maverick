package com.maveric;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class LoginActivity extends MavericBaseActiity {

	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.login_sreen);

	}

	LinearLayout loginWithEmail, loginWithCurrentDetail, loginWithTargetDetail;
	Button signUp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		signUp = (Button) findViewById(R.id.sign_up);
		RelativeLayout loginWithCurrentDetailNext = (RelativeLayout) findViewById(R.id.current_detail_next);
		RelativeLayout loginWithTargetDetailNext = (RelativeLayout) findViewById(R.id.target_detail_next);
		final EditText currentHeight = (EditText) findViewById(R.id.login_height);
		final EditText currentWeight = (EditText) findViewById(R.id.login_weight);
		final EditText currentHip = (EditText) findViewById(R.id.login_hip);
		final EditText currentWaist = (EditText) findViewById(R.id.login_waist);
		final EditText targetHeight = (EditText) findViewById(R.id.TARGET_HEIGHT);
		final EditText targettWeight = (EditText) findViewById(R.id.TARGET_WEIGHT);
		final EditText targetHip = (EditText) findViewById(R.id.TARGET_HIP);
		final EditText currentBmi = (EditText) findViewById(R.id.CURRENT_BMI);
		final EditText userName = (EditText) findViewById(R.id.user_name);
		final EditText passWord = (EditText) findViewById(R.id.password);
		final EditText conformPwd = (EditText) findViewById(R.id.confrm_pwd);
		final EditText emailId = (EditText) findViewById(R.id.email_id);

		loginWithEmail = (LinearLayout) findViewById(R.id.login_with_email);
		loginWithCurrentDetail = (LinearLayout) findViewById(R.id.login_with_current_detail);
		loginWithTargetDetail = (LinearLayout) findViewById(R.id.login_with_target_detail);

		loginWithCurrentDetail.setVisibility(View.VISIBLE);

		signUp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				signUp.setBackgroundColor(R.color.signup_pressed);
				signUp.setClickable(false);
				if (isAllFilled(userName, conformPwd, emailId, passWord)) {
					Intent home = new Intent(context, MavericHomeActivity.class);
					startActivity(home);
				} else
					toast(getString(R.string.REQUIRE_FIELD_TOAST));
			}
		});
		loginWithCurrentDetailNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isAllFilled(currentHeight, currentWeight, currentHip,
						currentWaist))
					setLoginScreen(loginWithTargetDetail);
				else
					toast(getString(R.string.REQUIRE_FIELD_TOAST));
			}
		});
		loginWithTargetDetailNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isAllFilled(targetHeight, targettWeight, targetHip,
						currentBmi))
					setLoginScreen(loginWithEmail);
				else
					toast(getString(R.string.REQUIRE_FIELD_TOAST));
			}
		});
	}

	private void setLoginScreen(LinearLayout visibleLogin) {
		LinearLayout[] logins = { loginWithCurrentDetail, loginWithEmail,
				loginWithTargetDetail };

		for (LinearLayout login : logins) {
			if (login.equals(visibleLogin))
				login.setVisibility(View.VISIBLE);
			else
				login.setVisibility(View.GONE);

		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		try {
			if (exitOnBackButton() && keyCode == KeyEvent.KEYCODE_BACK
					&& event.getRepeatCount() == 0) {
				signUp.setClickable(true);
				if (loginWithTargetDetail.getVisibility() == View.VISIBLE)
					setLoginScreen(loginWithCurrentDetail);
				else if (loginWithEmail.getVisibility() == View.VISIBLE)
					setLoginScreen(loginWithTargetDetail);
				else if (loginWithCurrentDetail.getVisibility() == View.VISIBLE)
					finish();

				return false;
			}
			return super.onKeyDown(keyCode, event);
		}

		catch (Exception e) {
		}
		return false;
	}

	protected boolean exitOnBackButton() {
		return true;
	}

	private Boolean isAllFilled(EditText first, EditText second,
			EditText third, EditText Fourth) {
		if (first.getText().length() > 0 && second.getText().length() > 0
				&& third.getText().length() > 0
				&& Fourth.getText().length() > 0)
			return true;
		else
			return false;
	}
}
