package com.maveric;

import android.os.Bundle;
import android.widget.TextView;

public class MavericHomeActivity extends MavericBaseActiity {
	
	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.home);

	}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView bmiText = (TextView) findViewById(R.id.calories_born_answer);
        TextView waterReq =(TextView) findViewById(R.id.diet_answer);
        Apppref appPref = new Apppref(context);
        bmiText.setText(appPref.getRecWeight()+" kg");
        waterReq.setText(appPref.getRecWater()+" L");
    }
}