package com.maveric;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class LoginOnlyActivity extends MavericBaseActiity {

	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.loginonlycontainer);

	}

	private LinearLayout signInLayout, signUpLayout;
	private Button signUp, signIn, ok, cancel;
	private Apppref appPref;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		signUp = (Button) findViewById(R.id.signin_signup);
		signIn = (Button) findViewById(R.id.signin_signin);
		ok = (Button) findViewById(R.id.signup_ok);
		cancel = (Button) findViewById(R.id.signup_cancel);

		final EditText userNameIn = (EditText) findViewById(R.id.signin_user_name);
		final EditText passWordIn = (EditText) findViewById(R.id.signin_password);
		final EditText userNameUp = (EditText) findViewById(R.id.signup_user_name);
		final EditText passWordUp = (EditText) findViewById(R.id.signup_password);
		final EditText conformPwdUp = (EditText) findViewById(R.id.signup_confrm_pwd);
		final EditText emailIdUp = (EditText) findViewById(R.id.signup_email_id);

		signInLayout = (LinearLayout) findViewById(R.id.signinlayout);
		signUpLayout = (LinearLayout) findViewById(R.id.signuplayout);

		appPref = new Apppref(context);
		if (appPref.getSignInOnly())
			gotoHomeActivity();

		signIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String userNameString = userNameIn.getText().toString();
				String passWordString = passWordIn.getText().toString();

				if (userNameString.length() > 0 && passWordString.length() > 0) {

					if (appPref.getUserNameOnly().equalsIgnoreCase(
							userNameString)
							&& appPref.getPasswordonly().equalsIgnoreCase(
									passWordString)) {
						appPref.setSignInOnly(true);
						gotoHomeActivity();
					} else {
						toast("Hey Your userName and password didnt match");
						userNameIn.setText("");
						passWordIn.setText("");
					}
				} else {
					toast("Hey Please fill all fields");
				}
			}
		});

		signUp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				signInLayout.setVisibility(View.GONE);
				signUpLayout.setVisibility(View.VISIBLE);
			}
		});

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				signUpLayout.setVisibility(View.GONE);
				signInLayout.setVisibility(View.VISIBLE);

			}
		});

		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String userNameString = userNameUp.getText().toString();
				String emailIdString = emailIdUp.getText().toString();
				String passWordString = passWordUp.getText().toString();
				String conformPassString = conformPwdUp.getText().toString();

				if (isAllFilled(userNameString, emailIdString, passWordString,
						conformPassString)) {
					if (!checkPassWord(passWordString, conformPassString)) {
						toast("Hey Password is didnot match please five once again");
						passWordUp.setText(" ");
						conformPwdUp.setText(" ");
						return;
					}
					appPref.setUserNameOnly(userNameString);
					appPref.setPasswordonly(passWordString);
					
					gotoHomeActivity();

//					signUpLayout.setVisibility(View.GONE);
//					signInLayout.setVisibility(View.VISIBLE);

				} else {
					toast("Hey Please fill all fields");
				}

			}
		});

	}

	protected boolean checkPassWord(String passWordString,
			String conformPassString) {
		if (passWordString.equalsIgnoreCase(conformPassString)) {
			return true;
		}
		return false;
	}

	private Boolean isAllFilled(String first, String second, String third,
			String Fourth) {
		if (first.length() > 0 && second.length() > 0 && third.length() > 0
				&& Fourth.length() > 0)
			return true;
		else
			return false;
	}

	private void gotoHomeActivity() {
		Intent home = new Intent(context, MavericHomeActivity.class);
		startActivity(home);
		this.finish();
	}
}
