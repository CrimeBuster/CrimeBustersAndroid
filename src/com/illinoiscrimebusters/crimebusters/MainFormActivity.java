package com.illinoiscrimebusters.crimebusters;

import com.crime.crimebusters.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainFormActivity extends Activity {

	public static final String PANIC_MESSAGE = "Panic Message";
	public static final String REPORT_MESSAGE = "Report Message";
	ReportSingleton _reportSingleton = ReportSingleton.getInstance();
	private String _userName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_form);
		
		int theme = _reportSingleton.setTheme();
		getWindow().setBackgroundDrawableResource(theme);

		TextView textView = (TextView) findViewById(R.id.main_activity_header);
		textView.setTextSize(24);

		_userName = getIntent().getStringExtra("userName");
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
		_reportSingleton.setReportType(1); //high
		startActivity(intent);
	}

	/** Called when the user clicks the Report button */
	public void report(View view) {
		Intent intent = new Intent(this, ReportIncidentActivity.class);
		_reportSingleton.setReportType(2); //low priority
		startActivity(intent);
	}
}