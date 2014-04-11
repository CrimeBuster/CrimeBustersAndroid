package com.crime.crimebusters;

import java.util.concurrent.ExecutionException;

import com.crime.crimebusters.login.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Activity that register a user to our application.
 * @author Chris
 *
 */
public class RegisterUserActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_user);
		int theme= ((MyApplication) this.getApplication()).setTheme();
		getWindow().setBackgroundDrawableResource(theme);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register_user, menu);
		return true;
	}

	/**
	 * Event handler for the Create User button
	 * @param view The object that throws the event.
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public void createUser(View view) throws InterruptedException, ExecutionException {
		EditText editUserName = (EditText)findViewById(R.id.createUser_userName);
		String userName = editUserName.getText().toString();
		
		EditText editPassword = (EditText)findViewById(R.id.createUser_password);
		String password = editPassword.getText().toString();
		
		EditText editFirstName = (EditText)findViewById(R.id.createUser_firstName);
		String firstName = editFirstName.getText().toString();
		
		EditText editLastName = (EditText)findViewById(R.id.createUser_lastName);
		String lastName = editLastName.getText().toString();
		
		EditText editEmail = (EditText)findViewById(R.id.createUser_email);
		String email = editEmail.getText().toString();
		
		if (!validateFields(userName, password, firstName, lastName, email)) {
			Toast.makeText(this, 
					"All fields are required. Please ensure that the email address is a valid Illinois address.", 
					Toast.LENGTH_LONG).show();	
			return;
		}
		
		Button buttonCreateUser = (Button) findViewById(R.id.createUser_button);
		buttonCreateUser.setText("Creating User...");
		
		Login login = new Login(buttonCreateUser);
		String createUserStatus = login
				.createUser(userName, password, firstName, lastName, email);
		
		if (createUserStatus.equals("Success")) {
			Toast.makeText(this, "Successfully created user! We have sent you an email to verify your account before you can log in.", Toast.LENGTH_LONG).show();
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		} else {
			Toast.makeText(this, "Create user failed! " + createUserStatus, Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * Validates the fields before sending a web service call.
	 * @param userName UserName of the account
	 * @param password Password of the account 
	 * @param firstName First name of the user
	 * @param lastName Last name of the user
	 * @param email a valid University of Illinois email.
	 * @return true if validation is successful.
	 */
	private Boolean validateFields(String userName, String password, String firstName, String lastName, String email) {
		
		if (isFieldEmpty(userName, password, firstName, lastName, email)
				|| isNotIllinoisEmail(email)) {
			return false;
		}
		
		return true;
	}

	/**
	 * Validates if the email is a University of Illinois email address.
	 * @param email email address of the account
	 * @return true if not an illinois email address.
	 */
	private boolean isNotIllinoisEmail(String email) {
		return !email.substring(email.indexOf("@")).equals("@illinois.edu") &&
				!email.substring(email.indexOf("@")).equals("@uiuc.com");
	}

	/**
	 * Validates for empty fields.
	 * @param userName UserName of the account
	 * @param password Password of the account 
	 * @param firstName First name of the user
	 * @param lastName Last name of the user
	 * @param email a valid University of Illinois email.
	 * @return true if one field is empty.
	 */
	private boolean isFieldEmpty(String userName, String password,
			String firstName, String lastName, String email) {
		return userName.equals("") || password.equals("") || firstName.equals("") 
				|| lastName.equals("") || email.equals("");
	}
}
