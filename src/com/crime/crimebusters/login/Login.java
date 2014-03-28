package com.crime.crimebusters.login;

import java.util.concurrent.ExecutionException;
import org.json.JSONException;
import org.json.JSONObject;

import com.crime.crimebusters.util.RequestMethod;
import com.crime.crimebusters.util.RestClient;

import android.os.AsyncTask;
import android.widget.Button;

/**
 * Contains the business logic for the Login module.
 * @author Chris
 *
 */
public class Login {
	private Button actionButton;
	private final String VALIDATE_CREDENTIALS_SERVICE = 
			"http://illinoiscrimebusters.com/Services/ValidateUser.ashx";
	private final String CREATE_USER_SERVICE = 
			"http://illinoiscrimebusters.com/Services/CreateUser.ashx";
	
	public Login(Button loginButton) {
		this.actionButton = loginButton;
	}
	
	public Login() {

	}

	/**
	 * Validates the user against the VALIDATE_CREDENTIALS_SERVICE
	 * @param userName user name of the user
	 * @param password password
	 * @return status of the validation.
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
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
	
	/**
	 * Creates a user against CREATE_USER_SERVICE service
	 * @param userName username of the new user
	 * @param password password of the new user
	 * @param firstName first name f the new user
	 * @param lastName last name of the new user
	 * @param email UIUC email address of the user
	 * @return status of user creation.
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
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
	
	/**
	 * Validates the user credentials asynchronously.
	 * @author Chris
	 *
	 */
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

			return client.getResponse();	
		}

		protected void onPostExecute(String result) {
			actionButton.setText("Log in");
		}
	}
	
	/**
	 * Creates the user asynchronously
	 * @author Chris
	 *
	 */
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

			return client.getResponse();	
		}

		protected void onPostExecute(String result) {
			actionButton.setText("Create User");
		}
	}
}
