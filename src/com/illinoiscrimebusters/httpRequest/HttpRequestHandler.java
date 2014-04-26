package com.illinoiscrimebusters.httpRequest;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.content.Context;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.illinoiscrimebusters.crimebusters.ReportSingleton;

//AsyncTask <TypeOfVarArgParams , ProgressValue , ResultValue> .\\
/*
 * http://www.vogella.com/tutorials/AndroidBackgroundProcessing/article.html#concurrency_asynchtask
 * 
 * To use AsyncTask you must subclass it. AsyncTask uses generics and varargs. 
 * The parameters are the following AsyncTask <TypeOfVarArgParams , ProgressValue , ResultValue> .
 An AsyncTask is started via the execute() method.

 The execute() method calls the doInBackground() and the onPostExecute() method.

 TypeOfVarArgParams is passed into the doInBackground() method as input,
 ProgressValue is used for progress information and ResultValue must be returned from doInBackground() method and is passed to onPostExecute() as a parameter.

 The doInBackground() method contains the coding instruction which should be performed in a background thread.
 This method runs automatically in a separate Thread.

 The onPostExecute() method synchronizes itself again with the user interface thread and allows it to be updated.
 This method is called by the framework once the doInBackground() method finishes.
 */

public class HttpRequestHandler extends AsyncTask<String, Void, String> {

	private ReportSingleton reportSingleton = ReportSingleton.getInstance();
	private String responseString =  "";

	
	
	
	private String submitReport2(){
			 HttpClient client = new DefaultHttpClient();
		    HttpPost post = new HttpPost("url" + "uploadFile");
		    MultipartEntityBuilder multipartEntity = MultipartEntityBuilder.create(); 
		return "";
		
	}
	
	private String submitReport() {
		// Get Data
		HashMap<String, String> report = reportSingleton.copyReport();
		// Get Report type
		int reportType = reportSingleton.getReportType();
		String username = reportSingleton.getName();
		username = "crime.buster";
		String reportTypeString = String.valueOf(reportType);
		String url = reportSingleton.getUrl();
		
		
	//	activity.getApplicationContext();
	//	_preference = activity.getSharedPreferences("cbPreference", Context.MODE_PRIVATE);
	///	_preference.edit().clear().commit();
		
		
		
	//	SharedPreferences _preference ; 
	//	_preference = getSharedPreferences("cbPreference", MODE_PRIVATE);
		
	//	username =_preference.getString("userName","crime.buster");
	//
		
		MultipartEntityBuilder multipartEntity = MultipartEntityBuilder
				.create(); 
		multipartEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

	/*	if (reportSingleton.getImage1() != null) {
			multipartEntity.addPart("photo1", new FileBody(new File(
					reportSingleton.getImage1())));

		}
		if (reportSingleton.getImage1() != null) {
			multipartEntity.addPart("photo2", new FileBody(new File(
					reportSingleton.getImage2())));

		}
		if (reportSingleton.getImage1() != null) {
			multipartEntity.addPart("photo3", new FileBody(new File(
					reportSingleton.getImage3())));

		}
*/
		//
//		if(reportSingleton.getImageLocation() != null){
	//		multipartEntity.addPart("photo1", new FileBody(new File(reportSingleton.getImageLocation() )));
	//	}
		if(reportSingleton.getImage1() != null){
			multipartEntity.addPart("photo1", new FileBody(new File(reportSingleton.getImage1() )));
		}
		if(reportSingleton.getImage2() != null){
			multipartEntity.addPart("photo2", new FileBody(new File(reportSingleton.getImage2() )));
		}
		if(reportSingleton.getImage3() != null){
			multipartEntity.addPart("photo3", new FileBody(new File(reportSingleton.getImage3() )));
		}
		
		
		if(reportSingleton.getAudioPath() != null){
			multipartEntity.addPart("audio", new FileBody(new File(reportSingleton.getAudioPath() )));
		}
		if(reportSingleton.getVideoPath() != null){
			multipartEntity.addPart("video", new FileBody(new File(reportSingleton.getVideoPath() )));
		}
		
		
		
		for (String name : report.keySet()) {
		
			
			multipartEntity.addTextBody(name, report.get(name) );
		}
		
	
		
		multipartEntity.addTextBody("reportTypeId", reportTypeString);
		multipartEntity.addTextBody("userName", username);

		HttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		post.setEntity(multipartEntity.build());
		HttpEntity entity = null;
		HttpResponse response = null;
		try {
			response = httpClient.execute(post);
			entity = response.getEntity();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		String returnString = "";
		try {
			returnString =  EntityUtils.toString(entity, "UTF-8");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnString;

	}
	
	
	/**
	 * @return the success of failure of the post result of the report
	 */
	private String submitReport22() {

		HashMap<String, String> report = reportSingleton.copyReport();
		int reportType = reportSingleton.getReportType();
		String reportTypeString = String.valueOf(reportType);

		

		ArrayList<NameValuePair> parameters = new ArrayList<NameValuePair>();
	
		for (Map.Entry<String, String> entry : report.entrySet()) {
			parameters.add(new BasicNameValuePair(entry.getKey(), entry
					.getValue()));
		}
		
		parameters.add(new BasicNameValuePair("reportTypeId", reportTypeString));
		parameters.add(new BasicNameValuePair("userName", reportSingleton.getName()));

		// Make it into a post
		UrlEncodedFormEntity post;
		try {
			//Encode it
			post = new UrlEncodedFormEntity(parameters, "UTF-8");
		
			//Do the post
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(reportSingleton.getUrl());
			httppost.setEntity(post);

			
			
			//Get response
			HttpResponse response = httpClient.execute(httppost);
			HttpEntity entity = response.getEntity();

			if (response.getEntity().getContentLength() != 0) {
			  responseString += EntityUtils.toString(entity, "UTF-8");
				
			
				
			} else {
				responseString += "failure2";

			}

		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseString;

	}

	@Override
	protected String doInBackground(String... args) {

		return submitReport();

	}
}
