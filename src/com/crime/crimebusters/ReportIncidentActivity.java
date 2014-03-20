package com.crime.crimebusters;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class ReportIncidentActivity extends Activity implements LocationListener{
	  
	  private TextView latituteField;
	  private TextView longitudeField;
	  private LocationManager locationManager;
	  private String provider;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.activity_report_incident);
		 
		 latituteField = (TextView) findViewById(R.id.longText);
		 longitudeField = (TextView) findViewById(R.id.latiText);

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


		Intent intent = getIntent();
		String  panic_message= intent
				.getStringExtra(MainFormActivity.panic_message);
		String report_message = intent
				.getStringExtra(MainFormActivity.report_message);

		// Create the text view
		TextView textView = (TextView) findViewById(R.id.activity_report_header);
		// EditText editText = (EditText) findViewById(R.id.editText1);


	
		if (panic_message != null) {
			textView.setText(panic_message);
		}
		else if(report_message != null){
			textView.setText(report_message);	
				
		}

		// Set the text view as the activity layout
		//setContentView(textView);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.report_incident, menu);
		return true;
	}

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


}
