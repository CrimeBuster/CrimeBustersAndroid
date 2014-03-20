package com.crime.crimebusters;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

public class ReportIncidentActivity extends Activity implements LocationListener {

	private TextView latituteField;
	private TextView longitudeField;
	private LocationManager locationManager;
    private String provider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_incident);

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
		String panic_message = intent
				.getStringExtra(MainFormActivity.panic_message);
		String report_message = intent
				.getStringExtra(MainFormActivity.report_message);

		String report_type = report_message.length() > 0 ? "report" : "panic";

		// Create the text view
		TextView textView = (TextView) findViewById(R.id.activity_report_header);
		// EditText editText = (EditText) findViewById(R.id.editText1);

		Time now = new Time();
		now.setToNow();
		String time = now.format("%H:%M:%S");
		String date = now.format("%d.%m.%Y");

		if (panic_message != null) {
			textView.setText(panic_message);
		} else if (report_message != null) {
			textView.setText(report_message);

		}
		EditText timeText = (EditText) findViewById(R.id.editText_time);
		timeText.setText(time, TextView.BufferType.EDITABLE);

		EditText dateText = (EditText) findViewById(R.id.editText_date);
		dateText.setText(date, TextView.BufferType.EDITABLE);

		// Set the text view as the activity layout
		// setContentView(textView);

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.report_incident, menu);
		return true;
	}

	public String getRest(ArrayList<String> elements, String request) {
		String msg = null;
		for (String s : elements) {

		}
		return msg;
	}
	
	public void submitReport(View view) throws ClientProtocolException, IOException {
	    // Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://www.sadkhin.net/test_post.php");


	       List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("id", "12345"));
	        nameValuePairs.add(new BasicNameValuePair("stringdata", "AndDev is Cool!"));
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	        // Execute HTTP Post Request
	        HttpResponse response = httpclient.execute(httppost);
	        
	 
	    /*
		Button buttonReport = (Button) findViewById(R.id.button_submitReport);
		buttonReport.setText("Submitting Report");

		EditText timeText = (EditText) findViewById(R.id.editText_time);
		String timeText_string = timeText.getText().toString();

		EditText dateText = (EditText) findViewById(R.id.editText_date);
		String dateText_string = dateText.getText().toString();

		EditText locationText = (EditText) findViewById(R.id.editText_location);
		String locationText_string = locationText.getText().toString();

		EditText descriptionText = (EditText) findViewById(R.id.editText_description);
		String descriptionText_string = descriptionText.getText().toString();


		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("date", dateText_string));
			nameValuePairs.add(new BasicNameValuePair("time", timeText_string));
			nameValuePairs.add(new BasicNameValuePair("location",
					locationText_string));
			nameValuePairs.add(new BasicNameValuePair("description",
					descriptionText_string));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			// EditText reponseText =
			// (EditText)findViewById(R.id.editText_status);
			// HttpEntity entity = response.getEntity();
			// String responseString = EntityUtils.toString(entity, "UTF-8");
			// reponseText.setText(responseString.toString());

			// msgTextField.setText(""); //reset the message text field
			// Toast.makeText(getBaseContext(),"Sent",Toast.LENGTH_SHORT).show();

		//	buttonReport.setText("Report Submitted");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    */  
	} 
	
	/**
	 * Called when the user clicks the Submit Report button
	 * 
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public void submitReport2(View view) throws InterruptedException,
			ExecutionException {
		Button buttonReport = (Button) findViewById(R.id.button_submitReport);
		buttonReport.setText("Submitting Report");

		EditText timeText = (EditText) findViewById(R.id.editText_time);
		String timeText_string = timeText.getText().toString();

		EditText dateText = (EditText) findViewById(R.id.editText_date);
		String dateText_string = dateText.getText().toString();

		EditText locationText = (EditText) findViewById(R.id.editText_location);
		String locationText_string = locationText.getText().toString();

		EditText descriptionText = (EditText) findViewById(R.id.editText_description);
		String descriptionText_string = descriptionText.getText().toString();

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://www.sadkhin.net/test_post.php");

		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("date", dateText_string));
			nameValuePairs.add(new BasicNameValuePair("time", timeText_string));
			nameValuePairs.add(new BasicNameValuePair("location",
					locationText_string));
			nameValuePairs.add(new BasicNameValuePair("description",
					descriptionText_string));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			// EditText reponseText =
			// (EditText)findViewById(R.id.editText_status);
			// HttpEntity entity = response.getEntity();
			// String responseString = EntityUtils.toString(entity, "UTF-8");
			// reponseText.setText(responseString.toString());

			// msgTextField.setText(""); //reset the message text field
			// Toast.makeText(getBaseContext(),"Sent",Toast.LENGTH_SHORT).show();

		//	buttonReport.setText("Report Submitted");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// } else {
		// display message if text field is empty
		// buttonReport.setText("Not Submitted");
		// Toast.makeText(getBaseContext(),"All fields are required",Toast.LENGTH_SHORT).show();
	}

}
