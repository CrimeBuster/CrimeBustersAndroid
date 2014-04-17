package com.crime.crimebusters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainFormActivity extends Activity {

	public static final String panic_message = "Panic Message";
	public static final String report_message = "Report Message";
	ReportSingleton reportSingleton = ReportSingleton.getInstance();
	private String _userName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_form);
		
		int theme = reportSingleton.setTheme();
		getWindow().setBackgroundDrawableResource(theme);

		TextView textView = (TextView) findViewById(R.id.main_activity_header);
		textView.setTextSize(24);

		_userName = getIntent().getStringExtra(LoginActivity.EXTRA_MESSAGE);
		if (_userName != null) {
			textView.setText(textView.getText() + " " + _userName);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_form, menu);
		return true;
	}

	/** Called when the user clicks the Profile button */
	public void profile(View view) {
		Intent intent = new Intent(this, UpdateProfileActivity.class);
		intent.putExtra("userName", _userName);
		startActivity(intent);
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
