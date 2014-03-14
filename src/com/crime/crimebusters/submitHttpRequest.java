package com.crime.crimebusters;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;

public class submitHttpRequest extends AsyncTask<String, String, String> {

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		// Do some prepartations over here, before the task starts to execute
		// Like freeze the button and/or show a progress bar

	}

	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
	//	if(arg0){
			try {
				return "doInBackground " + submitReport( "a", "b", "c");
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		//}
			return "failure doInBackground";
	   
		
		
	}
	public String submitReport(String a, String b, String c) throws ClientProtocolException, IOException {
	    // Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://www.sadkhin.net/test_post.php");


	       List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	      nameValuePairs.add(new BasicNameValuePair("id", "12345"));
	       nameValuePairs.add(new BasicNameValuePair("stringdata", "AndDev is Cool!"));
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	        // Execute HTTP Post Request
	       HttpResponse response = httpclient.execute(httppost);
	       HttpEntity entity = response.getEntity();
	        String responseString = EntityUtils.toString(entity, "UTF-8");
			return "" +"doInBackground String Returned" + c + b + a;
	}
	
	
	

	protected void onPostExecute(String result) {
		// Do modifications you want after everything is finished
		// Like re-enable the button, and/or hide a progressbar
		// And of course do what you want with your result got from http-req
		
		
		
	}

}