package com.crime.crimebusters;

import java.io.IOException;
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
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;

public class SubmitReportActivity extends Activity {

	private String date;
	private String time;
	private String location;
	private String gps;
	private String report;
//s
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.submit_report_activity);
		Intent intent = getIntent();

		String panic_message = intent
				.getStringExtra(ReportIncidentActivity.description);

		TextView reportResult = (TextView) findViewById(R.id.submit_report_activity_result);
		reportResult.setText(panic_message, TextView.BufferType.EDITABLE);

		try {
			reportResult.setText(submitReport());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.submit_report, menu);
		return true;
	}

	public String submitReport() throws ClientProtocolException, IOException {
		String Report = "";
		try {
			Report = new submitHttpRequest().execute("1", "2", "3").get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Report:" + Report;
	}

	// Create a new HttpClient and Post Header
	/*
	 * HttpClient httpclient = new DefaultHttpClient(); HttpPost httppost = new
	 * HttpPost("http://www.sadkhin.net/test_post.php");
	 * 
	 * 
	 * List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	 * nameValuePairs.add(new BasicNameValuePair("id", "12345"));
	 * nameValuePairs.add(new BasicNameValuePair("stringdata",
	 * "AndDev is Cool!")); httppost.setEntity(new
	 * UrlEncodedFormEntity(nameValuePairs));
	 * 
	 * // Execute HTTP Post Request HttpResponse response =
	 * httpclient.execute(httppost); HttpEntity entity = response.getEntity();
	 * String responseString = EntityUtils.toString(entity, "UTF-8"); return ""
	 * +"Response String Returned";
	 */

	/*
	 * Button buttonReport = (Button) findViewById(R.id.button_submitReport);
	 * buttonReport.setText("Submitting Report");
	 * 
	 * EditText timeText = (EditText) findViewById(R.id.editText_time); String
	 * timeText_string = timeText.getText().toString();
	 * 
	 * EditText dateText = (EditText) findViewById(R.id.editText_date); String
	 * dateText_string = dateText.getText().toString();
	 * 
	 * EditText locationText = (EditText) findViewById(R.id.editText_location);
	 * String locationText_string = locationText.getText().toString();
	 * 
	 * EditText descriptionText = (EditText)
	 * findViewById(R.id.editText_description); String descriptionText_string =
	 * descriptionText.getText().toString();
	 * 
	 * 
	 * try { List<NameValuePair> nameValuePairs = new
	 * ArrayList<NameValuePair>(); nameValuePairs.add(new
	 * BasicNameValuePair("date", dateText_string)); nameValuePairs.add(new
	 * BasicNameValuePair("time", timeText_string)); nameValuePairs.add(new
	 * BasicNameValuePair("location", locationText_string));
	 * nameValuePairs.add(new BasicNameValuePair("description",
	 * descriptionText_string)); httppost.setEntity(new
	 * UrlEncodedFormEntity(nameValuePairs)); HttpResponse response =
	 * httpclient.execute(httppost); // EditText reponseText = //
	 * (EditText)findViewById(R.id.editText_status); // HttpEntity entity =
	 * response.getEntity(); // String responseString =
	 * EntityUtils.toString(entity, "UTF-8"); //
	 * reponseText.setText(responseString.toString());
	 * 
	 * // msgTextField.setText(""); //reset the message text field //
	 * Toast.makeText(getBaseContext(),"Sent",Toast.LENGTH_SHORT).show();
	 * 
	 * // buttonReport.setText("Report Submitted"); } catch
	 * (ClientProtocolException e) { e.printStackTrace(); } catch (IOException
	 * e) { e.printStackTrace(); }
	 */

	// }

}
