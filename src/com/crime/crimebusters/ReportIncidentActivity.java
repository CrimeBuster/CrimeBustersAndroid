package com.crime.crimebusters;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ReportIncidentActivity extends Activity {

	private String reportType = "default";
	private ReportSingleton reportSingleton = ReportSingleton.getInstance();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_incident);

		Intent intent = getIntent();
		String panic_message = intent
				.getStringExtra(MainFormActivity.panic_message);
		String report_message = intent
				.getStringExtra(MainFormActivity.report_message);

		// Create the text view
		TextView textView = (TextView) findViewById(R.id.activity_report_header);
		// EditText editText = (EditText) findViewById(R.id.editText1);
		
		//Skip this part and read from singleton class
		if (panic_message != null) {
			textView.setText(panic_message);
		} else if (report_message != null) {
			textView.setText(report_message);

		}
	//	Tue Feb 09 2010 13:06:20 GMT-0600 (CST)
	//	2010-02-09 %20 18:06:20
		
		reportSingleton.setReportType("1");
		// Set the text view as the activity layout
		// setContentView(textView);
		Time now = new Time();
		now.setToNow();
		String format = "";
		String time = now.format(format);
		time = "2010-02-09 %20 18:06:20";
		
		( (EditText) findViewById(R.id.editText_currentTime) ).setText(time);
	
		//((EditText) findViewById(R.id.editText_currentTime)).setText(now.format(format));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.report_incident, menu);
		return true;
	}

	public void submitReport(View view) {
		populateReport();
		Intent intent = new Intent(this, HTTPSubmitReportActivity.class);
		startActivity(intent);
	}
	
	public void populateReport(){
			String[] params = {"userName","reportTypeId","message","latitude","longitude","resourceURL","timeStamp"};
			
		//	EditText e = (EditText) findViewById(R.id.message);
			
		//	reportSingleton.setKey("userName", ((EditText) findViewById(R.id.message)).getText().toString() );
		//	reportSingleton.setKey("reportTypeId", ((EditText) findViewById(R.id.editText1)).getText().toString());
			reportSingleton.setKey("message", ((EditText) findViewById(R.id.message)).getText().toString());
			
			String gps =  ((EditText) findViewById(R.id.locationGPS)).getText().toString();
			String latitude = "half";
			String longitude = "half2";

			reportSingleton.setKey("latitude",latitude);
			reportSingleton.setKey("longitude", longitude);
			
			
			String location = ((EditText) findViewById(R.id.location)).getText().toString();
			
			
			reportSingleton.setKey("resourceURL", "NoneYet");
			//ToDo image or something?
			
			reportSingleton.setKey("timeStamp", ((EditText) findViewById(R.id.editText_currentTime)).getText().toString());
	
			
			
			
	}

	
	
	

}
