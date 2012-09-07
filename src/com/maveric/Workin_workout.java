package com.maveric;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

public class Workin_workout extends MavericBaseActiity {
	int questionNo;
	int[] result;
	RadioButton optionA, optionB;
	Button submit;

	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.metabolic_queries);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Button start = (Button) findViewById(R.id.start);
		TextView intro = (TextView) findViewById(R.id.intro);
		optionA = (RadioButton) findViewById(R.id.ratio_button_1);
		optionB = (RadioButton) findViewById(R.id.ratio_button_2);
		result = new int[7];
		submit = (Button) findViewById(R.id.submit);
		intro.setText(getString(R.string.INTRO_WORKIN_QUES));
		start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				LinearLayout queriesLayout = (LinearLayout) findViewById(R.id.queries_layout);
				LinearLayout introLayout = (LinearLayout) findViewById(R.id.intro_layout);
				queriesLayout.setVisibility(View.VISIBLE);
				introLayout.setVisibility(View.GONE);
				questionNo = 0;
				showQuestionWithOptions();
			}
		});
		optionA.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				selectedOptionStored(R.id.ratio_button_1);

			}
		});
		optionB.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				selectedOptionStored(R.id.ratio_button_2);
				// break;
			}
		});
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (optionB.isChecked() || optionA.isChecked())
					showResult();
				else
					toast("select any one option");

			}
		});

	}

	private void showQuestionWithOptions() {
		TextView question = (TextView) findViewById(R.id.question);
		question.setText(getResources()
				.getStringArray(R.array.workin_questions)[questionNo]);
		Log.i("manikk", "showQuestionWithOptions");

		optionA.setText("yes");
		optionB.setText("No");
		if (questionNo == 6)
			submit.setVisibility(View.VISIBLE);
		else
			submit.setVisibility(View.GONE);
	}

	private void selectedOptionStored(int selectedOption) {
		Log.i("manikk", "selectedOptionStored");
		switch (selectedOption) {
		case R.id.ratio_button_1:
			optionB.setChecked(false);
			Log.i("manikk", "R.id.ratio_button_1");
			result[questionNo] = 0;
			if (questionNo < 6) {
				questionNo++;
				optionA.setChecked(false);
				optionB.setChecked(false);
			}
			showQuestionWithOptions();
			break;
		case R.id.ratio_button_2:
			optionA.setChecked(false);
			Log.i("manikk", "R.id.ratio_button_2");
			result[questionNo] = 1;
			if (questionNo < 6) {
				optionA.setChecked(false);
				optionB.setChecked(false);
				questionNo++;
			}
			showQuestionWithOptions();
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		try {
			if (exitOnBackButton() && keyCode == KeyEvent.KEYCODE_BACK
					&& event.getRepeatCount() == 0) {
				optionA.setChecked(false);
				optionB.setChecked(false);
				if (questionNo > 0) {
					questionNo--;
					showQuestionWithOptions();
					if (result[questionNo] == 0) {
						Log.i("manikk", "questionNoif = " + questionNo
								+ "result" + result[questionNo]);
						optionB.setChecked(false);
						optionA.setChecked(true);
					} else {
						optionA.setChecked(false);
						optionB.setChecked(true);
						Log.i("manikk", "questionNoelse = " + questionNo
								+ "result" + result[questionNo]);
					}
					Log.i("manikk", "questionNo = " + questionNo + "result"
							+ result[questionNo]);

				} else
					this.finish();
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

	private void showResult() {
		LinearLayout queriesLayout = (LinearLayout) findViewById(R.id.queries_layout);
		LinearLayout introLayout = (LinearLayout) findViewById(R.id.intro_layout);
		LinearLayout image_disc = (LinearLayout) findViewById(R.id.image_disc);
		queriesLayout.setVisibility(View.GONE);
		introLayout.setVisibility(View.GONE);
		TextView resultText = (TextView) findViewById(R.id.result);
		ImageView image = (ImageView) findViewById(R.id.image);
		int optionA = 0, optionB = 0;
		Apppref appref = new Apppref(context);
		appref.setIsWorkQueriesAlreadyAnswerd(true);
		for (Integer i : result) {
			if (i == 0)
				optionA++;
			else
				optionB++;
		}
		resultText.setVisibility(View.VISIBLE);
		image.setVisibility(View.GONE);
		image_disc.setVisibility(View.GONE);
		questionNo = 0;
		Log.i("manikk", "workin_workout = " + optionA);
		if (optionA >= 4) {
			resultText.setText(getString(R.string.YES_4));
			appref.setLastWorkQueriesResult(getString(R.string.YES_4));
			// image.setImageResource(R.drawable.m2);
		} else if (optionA == 3) {
			resultText.setText(getString(R.string.YES_3));
			appref.setLastWorkQueriesResult(getString(R.string.YES_3));
			// image.setImageResource(R.drawable.m1);
		} else if (optionA == 2) {
			resultText.setText(getString(R.string.YES_2));
			appref.setLastWorkQueriesResult(getString(R.string.YES_2));
			// image.setImageResource(R.drawable.m3);
		} else if (optionA == 1) {
			resultText.setText(getString(R.string.YES_1));
			appref.setLastWorkQueriesResult(getString(R.string.YES_1));
			// image.setImageResource(R.drawable.m3);
		}
	}
}