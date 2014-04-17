package http_request;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

import com.crime.crimebusters.ReportSingleton;

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
	private String responseString = "";

	
	
	
	/**
	 * @return the success of failure of the post result of the report
	 */
	private String submitReport() {

		HashMap<String, String> report = reportSingleton.copyReport();
		int reportType = reportSingleton.getReportType();
		String reportTypeString = String.valueOf(reportType);

	

		ArrayList<NameValuePair> parameters = new ArrayList<NameValuePair>();
	
		for (Map.Entry<String, String> entry : report.entrySet()) {
			parameters.add(new BasicNameValuePair(entry.getKey(), entry
					.getValue()));
		}
		
		parameters.add(new BasicNameValuePair("reportTypeId", reportTypeString));
		parameters.add(new BasicNameValuePair("userName", "boris.sadkhin"));

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
