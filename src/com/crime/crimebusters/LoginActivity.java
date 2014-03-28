package com.crime.crimebusters;

import java.util.concurrent.ExecutionException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.crime.crimebusters.login.Login;

@SuppressLint("ShowToast")
public class LoginActivity extends Activity {

	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	/** Called when the user clicks the Send button 
	 * @throws ExecutionException 
	 * @throws InterruptedException */
	public void sendMessage(View view) throws InterruptedException, ExecutionException {
		Button buttonLogin = (Button) findViewById(R.id.sign_in_button);
		buttonLogin.setText("Logging in...");
		
		EditText editUserName = (EditText) findViewById(R.id.email);
		String userName = editUserName.getText().toString();
		
		EditText editPassword = (EditText) findViewById(R.id.password);
		String password = editPassword.getText().toString();
		
		Login login = new Login(buttonLogin);
		String loginStatus = login.validateCredentials(userName, password);
	
		if (loginStatus.equals("success")) {
			Intent intent = new Intent(this, MainFormActivity.class);
			intent.putExtra(EXTRA_MESSAGE, userName);
			startActivity(intent);
		} else {
			Toast.makeText(getApplicationContext(), "Login failed! " + loginStatus, Toast.LENGTH_LONG).show();
		}
	}
	
	/**
	 * Starts the create account activity
	 * @param view
	 */
	public void createAccount(View view) {
		startActivity(new Intent(this, RegisterUserActivity.class));
	}	
}
