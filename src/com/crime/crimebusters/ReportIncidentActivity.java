package com.crime.crimebusters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Menu;
import android.view.View;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class ReportIncidentActivity extends Activity implements
		LocationListener {

	private String reportType = "default";
	private ReportSingleton reportSingleton = ReportSingleton.getInstance();

	// GPS
	private TextView latituteField;
	private TextView longitudeField;
	private LocationManager locationManager;
	private String provider;
	private ImageView iv;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
	    
	       if (data != null)
	       {         
	           Bitmap photo = (Bitmap) data.getExtras().get("data"); 
	           iv.setImageBitmap(photo);
	       }
	}  
	
	
	
	
	public void takePicture(View v){
	 	 Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
	 	 startActivityForResult(intent, 0);
	}
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_incident);

		Intent intent = getIntent();
		String panic_message = intent
				.getStringExtra(MainFormActivity.panic_message);
		String report_message = intent
				.getStringExtra(MainFormActivity.report_message);

		latituteField = (TextView) findViewById(R.id.locationGPS_lat);
		longitudeField = (TextView) findViewById(R.id.locationGPS_long);
		iv = (ImageView) findViewById(R.id.imageView1); 

		Button btn = (Button) findViewById(R.id.button1);

		// GPS
		// Get the location manager
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// Define the criteria how to select the locatioin provider -> use
		// default
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(provider);

		// Initialize the location fields
		if (location != null) {
			System.out.println("Provider " + provider + " has been selected.");
			onLocationChanged(location);
		} else {
			latituteField.setText("Location not available");
			longitudeField.setText("Location not available");
		}

		// Create the text view
		TextView textView = (TextView) findViewById(R.id.activity_report_header);
		// EditText editText = (EditText) findViewById(R.id.editText1);

		// Skip this part and read from singleton class
		if (panic_message != null) {
			textView.setText(panic_message);
		} else if (report_message != null) {
			textView.setText(report_message);

		}
		// Tue Feb 09 2010 13:06:20 GMT-0600 (CST)
		// 2010-02-09 %20 18:06:20

		reportSingleton.setReportType("1");
		// Set the text view as the activity layout
		// setContentView(textView);
		Time now = new Time();
		now.setToNow();
		String format = "";
		String time = now.format(format);
		time = "2010-02-09 %20 18:06:20";

		((EditText) findViewById(R.id.editText_currentTime)).setText(time);

		// ((EditText)
		// findViewById(R.id.editText_currentTime)).setText(now.format(format));
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

	/* Request updates at startup */
	@Override
	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(provider, 400, 1, this);
	}

	/* Remove the locationlistener updates when Activity is paused */
	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		Toast.makeText(this, "Enabled new provider " + provider,
				Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onProviderDisabled(String provider) {
		Toast.makeText(this, "Disabled provider " + provider,
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onLocationChanged(Location location) {
		float lat = (float) (location.getLatitude());
		float lng = (float) (location.getLongitude());
		latituteField.setText(String.valueOf(lat));
		longitudeField.setText(String.valueOf(lng));

	}

	public void populateReport() {
		String[] params = { "userName", "reportTypeId", "message", "latitude",
				"longitude", "resourceURL", "timeStamp" };

		// EditText e = (EditText) findViewById(R.id.message);

		// reportSingleton.setKey("userName", ((EditText)
		// findViewById(R.id.message)).getText().toString() );
		// reportSingleton.setKey("reportTypeId", ((EditText)
		// findViewById(R.id.editText1)).getText().toString());
		reportSingleton.setKey("message",
				((EditText) findViewById(R.id.message)).getText().toString());

		// String gps = ((EditText)
		// findViewById(R.id.locationGPS)).getText().toString();
		String gps = "gps";
		String latitude = "half";
		String longitude = "half2";

		reportSingleton.setKey("latitude", latitude);
		reportSingleton.setKey("longitude", longitude);

		String location = ((EditText) findViewById(R.id.location)).getText()
				.toString();

		reportSingleton.setKey("resourceURL", "NoneYet");
		// ToDo image or something?

		reportSingleton.setKey("timeStamp",
				((EditText) findViewById(R.id.editText_currentTime)).getText()
						.toString());

	}

}
