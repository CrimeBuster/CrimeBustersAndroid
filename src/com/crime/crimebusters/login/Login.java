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
	private final String VALIDATE_CREDENTIALS_SERVICE = 
			"http://illinoiscrimebusters.com/Services/ValidateUser.ashx";
	private final String CREATE_USER_SERVICE = 
			"http://illinoiscrimebusters.com/Services/CreateUser.ashx";
	
	
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
	
	public String createUser(String userName, String password, String firstName, String lastName, String email) throws InterruptedException, ExecutionException {
		AsyncTask<String, Void, String> task = 
				new CreateUserTask().execute(userName, password, firstName, lastName, email);		
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
			RestClient client = new RestClient(VALIDATE_CREDENTIALS_SERVICE);
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
	
	private class CreateUserTask extends AsyncTask<String, Void, String> {
		protected String doInBackground(String... params) {
			RestClient client = new RestClient(CREATE_USER_SERVICE);
			client.AddParam("userName", params[0]);
			client.AddParam("password", params[1]);
			client.AddParam("firstName", params[2]);
			client.AddParam("lastName", params[3]);
			client.AddParam("email", params[4]);

			try {
			    client.Execute(RequestMethod.POST);
			} catch (Exception e) {
			    e.printStackTrace();
			}

			String response = client.getResponse();	
			
			return response;
		}

		protected void onPostExecute(String result) {
			//loginButton.setText("Log in");
		}
	}
}
