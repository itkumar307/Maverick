package com.maveric;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

public class metabolicQueries extends MavericBaseActiity {
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
		// final RadioGroup optionGroup = (RadioGroup)
		// findViewById(R.id.selected_option);
		optionA = (RadioButton) findViewById(R.id.ratio_button_1);
		optionB = (RadioButton) findViewById(R.id.ratio_button_2);
		result = new int[14];
		submit = (Button) findViewById(R.id.submit);
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
		question.setText(getResources().getStringArray(
				R.array.metabolic_questions)[questionNo]);
		Log.i("manikk", "showQuestionWithOptions");

		optionA.setText(getResources().getStringArray(R.array.OPTION_A)[questionNo]);
		optionB.setText(getResources().getStringArray(R.array.OPTION_B)[questionNo]);
		if (questionNo == 13)
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
			if (questionNo < 13) {
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
			if (questionNo < 13) {
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
		queriesLayout.setVisibility(View.GONE);
		introLayout.setVisibility(View.GONE);
		TextView resultText = (TextView) findViewById(R.id.result);
		int optionA = 0, optionB = 0;
		for (Integer i : result) {
			if (i == 0)
				optionA++;
			else
				optionB++;
		}
		resultText.setVisibility(View.VISIBLE);
		questionNo = 0;
		if (optionA > optionB || optionA == 3)
			resultText.setText("You are a Protein Type");
		else if (optionA < optionB || optionB == 3)
			resultText.setText("You are a Mixed Type");
		else if (optionA == optionB)
			resultText.setText("You are a Carbo Type");
	}
}
