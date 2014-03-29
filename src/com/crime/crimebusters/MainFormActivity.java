package com.crime.crimebusters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainFormActivity extends Activity {

	public static final String panic_message = "Panic Message";
	public static final String report_message = "Report Message";
	ReportSingleton reportSingleton = ReportSingleton.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_form);

		Intent intent = getIntent();
		String message = intent.getStringExtra(LoginActivity.EXTRA_MESSAGE);

		// Create the text view
		TextView textView = (TextView) findViewById(R.id.main_activity_header);
		// EditText editText = (EditText) findViewById(R.id.editText1);

		textView.setTextSize(30);

		textView.setText(textView.getText() + "\n" + message);

		// Set the text view as the activity layout
		// setContentView(textView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_form, menu);
		return true;
	}

	/** Called when the user clicks the Profile button */
	public void profile(View view) {
	//	Intent intent = new Intent(this, UpdateProfileActivity.class);
//		startActivity(intent);
	}
	
	
	/** Called when the user clicks the Incident button */
	public void incident(View view) {
		Intent intent = new Intent(this, ReportIncidentActivity.class);
		reportSingleton.setReportType(1); //high
		startActivity(intent);
	}

	/** Called when the user clicks the Report button */
	public void report(View view) {
		Intent intent = new Intent(this, ReportIncidentActivity.class);
		reportSingleton.setReportType(2); //low priority
		startActivity(intent);
	}
}
