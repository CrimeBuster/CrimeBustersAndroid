package http_request;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.crime.crimebusters.ReportSingleton;

import android.os.AsyncTask;

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

public class HttpRequestHandler extends
		AsyncTask<String, Void, String> {

	private ReportSingleton reportSingleton =  ReportSingleton.getInstance();
	@Override
	protected String doInBackground(String... args) {
		
	
		
	//	DefaultHttpClient client
		HttpGet httpGet = new HttpGet(
				"http://illinoiscrimebusters.com/Services/PostReport.ashx?" +
				"userName=chris.ababan" +
				"&reportTypeId=1" +
				"&latitude=40.106869" +
				 "&longitude=-88.223755" +
				 "&message=" + reportSingleton.returnKey("message") +
				"&resourceUrl=testResourceUrl" +
				"&timeStamp=2010-02-09%2018:06:20");
		

		
		String status=" AND THE HTTP WAS SUCCESSFULL";
		
		
		DefaultHttpClient httpClient  = new DefaultHttpClient();
		HttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpEntity entity = response.getEntity();
		String responseString = "Nothing happened";
		try {
			responseString = EntityUtils.toString(entity, "UTF-8");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return responseString;
		
	}
	/*@Override
	protected void onPostExecute(String result) {
		// textView.setText(result);
		   // TODO: check this.exception 
        // TODO: check this.exception 
        // TODO: do something with the feed
        super.onPostExecute(result);
        response("ONPOSTEXECUTE" + result);
		
	}
	
	private String response(String response){
		return response;
		
	}

	*/
	
}

/*
 * 
 * public class HttpRequest extends AsyncTask<URL, String, Long> {
 * 
 * public boolean isConnected() { ConnectivityManager connectivityManager =
 * null; // NetworkInfo wifiNetwork = //
 * connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI); //
 * NetworkInfo mobileNetwork = connectivityManager //
 * .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
 * 
 * NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
 * 
 * return true;
 * 
 * 
 * // if (activeNetwork.isConnected()) { return true; } else { return // false;
 * // Connection unavailable }
 * 
 * }
 * 
 * protected Long doInBackground(URL... urls) { if (!isConnected()) { return
 * (long) 0.0; } else {
 * 
 * }
 * 
 * int count = urls.length; long totalSize = 0; for (int i = 0; i < count; i++)
 * { // // totalSize += Downloader.downloadFile(urls[i]); //
 * publishProgress((int) ((i / (float) count) * 100)); // Escape early if
 * cancel() is called if (isCancelled()) break; } return totalSize; }
 * 
 * protected void onProgressUpdate(String... progress) { //
 * setProgressPercent(progress[0]); }
 * 
 * protected void onPostExecute(String result) { // showDialog("Downloaded " +
 * result + " bytes"); }
 * 
 * 
 * }
 */