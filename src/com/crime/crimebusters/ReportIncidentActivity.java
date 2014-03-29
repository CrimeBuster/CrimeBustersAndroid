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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ReportIncidentActivity extends Activity implements
		LocationListener {

	private ReportSingleton reportSingleton = ReportSingleton.getInstance();

	// GPS
	private TextView latituteField;
	private TextView longitudeField;
	private LocationManager locationManager;
	private String provider;
	private ImageView iv;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (data != null) {
			Bitmap photo = (Bitmap) data.getExtras().get("data");
			iv.setImageBitmap(photo);
		}
	}

	/**
	 * @param v
	 */
	public void takePicture(View v) {
		Intent intent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, 0);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (reportSingleton.getReportType() == 1) {
			setContentView(R.layout.activity_high_priority_incident);
			Intent intent = getIntent();
			iv = (ImageView) findViewById(R.id.imageView1);

		} else {
			setContentView(R.layout.activity_report_incident);
			Intent intent = getIntent();

		}

		GPS();
		time();

	}

	private void time() {
		Time now = new Time();
		now.setToNow();
		String format = "%m/%d/%y %H:%M:%S";
		String time = now.format(format);

		((EditText) findViewById(R.id.editText_currentTime)).setText(time);

	}

	// GPS
	// Get the location manager
	// Define the criteria how to select the locatioin provider -> use
	// default
	// Initialize the location fields
	public void GPS() {
		latituteField = (TextView) findViewById(R.id.locationGPS_lat);
		longitudeField = (TextView) findViewById(R.id.locationGPS_long);

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(provider);
		if (location != null) {
			System.out.println("Provider " + provider + " has been selected.");
			onLocationChanged(location);
		} else {
			latituteField.setText("LAT: N/A");
			longitudeField.setText("LONG: N/A");
		}

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

		reportSingleton.setKey("message",
				((EditText) findViewById(R.id.message)).getText().toString());

		String location = "";
		String gps = "";
		String latitude = "";
		String longitude = "";
		String message = "";
		String timestamp = "";
	

		// GET

		location = ((EditText) findViewById(R.id.location)).getText()
				.toString();
		timestamp = ((EditText) findViewById(R.id.editText_currentTime))
				.getText().toString();
		latitude = ((EditText) findViewById(R.id.locationGPS_lat))
				.getText().toString();
		longitude = ((EditText) findViewById(R.id.locationGPS_long))
				.getText().toString();
		message=((EditText) findViewById(R.id.message))
				.getText().toString();
				
				

		reportSingleton.setKey("desc", message);
		reportSingleton.setKey("lat", latitude);
		reportSingleton.setKey("lng", longitude);
		reportSingleton.setKey("timeStamp", timestamp);
	

		int reportType = reportSingleton.getReportType();
		if (reportType != 1) {
			reportSingleton.setImageLocation("somehowgetthis");
		}
		else{
			
			
		}

	}
}
