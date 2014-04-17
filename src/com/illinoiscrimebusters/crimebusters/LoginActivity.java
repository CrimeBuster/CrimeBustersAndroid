package com.illinoiscrimebusters.crimebusters;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.crime.crimebusters.R;
import com.illinoiscrimebusters.login.Login;

/**
 * Activity for the Login page.
 * @author Chris
 *
 */
public class LoginActivity extends Activity {

	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	
	private ReportSingleton reportSingleton = ReportSingleton.getInstance();
	private SharedPreferences preference;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		int theme = reportSingleton.setTheme();
		getWindow().setBackgroundDrawableResource(theme);
		
		setContentView(R.layout.activity_login);   
	}
	
	/**
	 * If the user has been authenticated before, 
	 * redirect the user to the Main Form
	 */
	protected void onResume() {
		super.onResume();
		preference = PreferenceManager.getDefaultSharedPreferences(this);
		boolean isAuthenticated = preference.getBoolean("isAuthenticated", false);
		
		if (isAuthenticated) {
			String userName = preference.getString("userName", "");
			redirectToMainForm(userName);
			LoginActivity.this.finish();
		}
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	/** 
	 * Event handler for the login button 
	 * @throws ExecutionException 
	 * @throws InterruptedException */
	public void loginUser(View view) throws InterruptedException, ExecutionException {	
		ProgressDialog dialog = 
				ProgressDialog.show(this, "Logging in", "Please wait...", true);
		
		EditText editUserName = (EditText) findViewById(R.id.email);
		String userName = editUserName.getText().toString();
		
		EditText editPassword = (EditText) findViewById(R.id.password);
		String password = editPassword.getText().toString();
		
		if (!validateFields(userName, password)) {
			Toast.makeText(this, 
					"Username and password required.", 
					Toast.LENGTH_LONG).show();
			dialog.dismiss();
			return;
		}
		
		Login login = new Login();
		String loginStatus = login.validateCredentials(userName, password);
	
		if (loginStatus.equals("success")) {			
			SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
			pref.edit().putBoolean("isAuthenticated", true).putString("userName", userName).commit();
			
			redirectToMainForm(userName);
		} else {
			Toast.makeText(getApplicationContext(), "Login failed! " + 
					loginStatus, Toast.LENGTH_LONG).show();
			dialog.dismiss();
		}
	}
	
	/**
	 * Validates the userName and password before sending a web service call.
	 * @param userName userName of the user.
	 * @param password password of the user
	 * @return
	 */
	private boolean validateFields(String userName, String password) {
		if (isFieldEmpty(userName, password)) {
			return false;
		}
		
		return true;
	}

	/**
	 * Validates for empty fields.
	 * @param userName userName of the user
	 * @param password password of the user
	 * @return true if one field is empty
	 */
	private boolean isFieldEmpty(String userName, String password) {
		return userName.equals("") || password.equals("");
	}

	/**
	 * Starts the create account activity
	 * @param view
	 */
	public void createAccount(View view) {
		startActivity(new Intent(this, RegisterUserActivity.class));
	}	

	/**
	 * Redirects the user to the MainFormActivity 
	 * if the user has been authenticated previously.
	 * @param userName userName of the user.
	 */
	private void redirectToMainForm(String userName) {
		Intent intent = new Intent(this, MainFormActivity.class);
		intent.putExtra(EXTRA_MESSAGE, userName);		
		startActivity(intent);
	}
}
