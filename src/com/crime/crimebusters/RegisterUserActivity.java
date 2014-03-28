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


public class RegisterUserActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_user);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register_user, menu);
		return true;
	}

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
			Toast.makeText(this, "Login failed! " + createUserStatus, Toast.LENGTH_LONG).show();
		}
	}
}
