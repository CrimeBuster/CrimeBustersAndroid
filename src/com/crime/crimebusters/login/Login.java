package com.crime.crimebusters.login;

import java.util.concurrent.ExecutionException;
import org.json.JSONException;
import org.json.JSONObject;

import com.crime.crimebusters.util.RequestMethod;
import com.crime.crimebusters.util.RestClient;

import android.os.AsyncTask;
import android.widget.Button;

public class Login {
	private Button loginButton;
	private String _validateCredentialsService = 
			"http://illinoiscrimebusters.com/Services/ValidateUser.ashx";
	
	public Login(Button loginButton) {
		this.loginButton = loginButton;
	}
	
	public Login() {

	}

	public String validateCredentials(String userName, String password) throws InterruptedException, ExecutionException {
		AsyncTask<String, Void, String> task = 
				new ValidateCredentialsTask().execute(userName, password);		
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(task.get());
			return jsonObject.getString("result");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	private class ValidateCredentialsTask extends AsyncTask<String, Void, String> {
		protected String doInBackground(String... params) {
			RestClient client = new RestClient(_validateCredentialsService);
			client.AddParam("userName", params[0]);
			client.AddParam("password", params[1]);

			try {
			    client.Execute(RequestMethod.GET);
			} catch (Exception e) {
			    e.printStackTrace();
			}

			String response = client.getResponse();	
			
			return response;
		}

		protected void onPostExecute(String result) {
			loginButton.setText("Log in");
		}
	}
}
