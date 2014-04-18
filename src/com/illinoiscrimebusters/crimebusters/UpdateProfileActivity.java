package com.illinoiscrimebusters.crimebusters;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import com.crime.crimebusters.R;
import com.illinoiscrimebusters.login.Login;
import com.illinoiscrimebusters.user.User;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class UpdateProfileActivity extends Activity{
	private String _userName;
	
	private ReportSingleton reportSingleton = ReportSingleton.getInstance();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_profile);
		_userName = getIntent().getStringExtra("userName");
		
		// TODO: Chris: Not a good code. For testing.
		// Need to refactor.
		if (_userName == null) {
			_userName = "crime.buster"; 
		}
		
		initializeFields();
		
		int theme= reportSingleton.setTheme();
		getWindow().setBackgroundDrawableResource(theme);
	}

	/**
	 * Event handler for the Change Theme button
	 * @param view The object that throws the event.
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public void changeTheme(View view) throws InterruptedException, ExecutionException {
		
		int theme= reportSingleton.getThemeNumber();
		
		if (theme==R.style.MyTheme)
		{
			getWindow().setBackgroundDrawableResource(R.drawable.b6);
			reportSingleton.setThemeNumber(R.style.MyTheme2);
		}
		
		else if (theme==R.style.MyTheme2)
		{
			getWindow().setBackgroundDrawableResource(R.drawable.g1);
			reportSingleton.setThemeNumber(R.style.MyTheme3);
		}
		
		else if (theme==R.style.MyTheme3)
		{
			getWindow().setBackgroundDrawableResource(R.drawable.c4);
			reportSingleton.setThemeNumber(R.style.MyTheme);
		}
		
		Intent intent = new Intent(this, MainFormActivity.class);
		startActivity(intent);
	}
	
	/**
	 * Event handler for the Change Language button
	 * @param view The object that throws the event.
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public void changeLanguage(View view) throws InterruptedException, ExecutionException {

        Configuration config = getResources().getConfiguration();
		
        if ("en".equalsIgnoreCase(config.locale.getLanguage()))
        {
        	changeLocale("fr", config);
        }
        else if ("fr".equalsIgnoreCase(config.locale.getLanguage()))
        {
        	changeLocale("en", config);
        }
        
        Intent intent = new Intent(this, MainFormActivity.class);
		startActivity(intent);
	}
	
	private void changeLocale(String name, Configuration config) {
		// Creating an instance of Locale for French language
        Locale locale = new Locale(name);
 
        // Setting locale of the configuration to French language
        config.locale = locale;
 
        // Updating the application configuration
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
 
        // Setting the title for the activity, after configuration change
        setTitle(R.string.app_name);
	}

	/**
	 * Initialize fields based from the data retrieved from the web service.
	 */
	private void initializeFields() {
		User user = new User(_userName);
		try {
			user.getUserProfile();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		EditText editFirstName = (EditText) findViewById(R.id.updateProfile_firstName);
		editFirstName.setText(user.getFirstName());
		
		EditText editLastName = (EditText) findViewById(R.id.updateProfile_lastName);
		editLastName.setText(user.getLastName());
		
		EditText editPhoneNumber = (EditText) findViewById(R.id.updateProfile_phoneNumber);
		editPhoneNumber.setText(user.getPhoneNumber());
		
		EditText editAddress = (EditText) findViewById(R.id.updateProfile_address);
		editAddress.setText(user.getPhoneNumber());
		
		EditText editZipCode = (EditText) findViewById(R.id.updateProfile_zipCode);
		editZipCode.setText(user.getZipCode());
		
		RadioGroup radioGender = (RadioGroup) findViewById(R.id.updateProfile_gender);

		RadioButton selectedRadioButton = (RadioButton)radioGender.findViewById(
				user.getGender().equals("M") ? R.id.male : R.id.female
		);
		selectedRadioButton.setChecked(true);
	}
	
	/**
	 * Event handler for the Update User button
	 * @param view The object that throws the event.
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public void onUpdateProfile(View view) throws InterruptedException, ExecutionException {
		EditText editFirstName = (EditText) findViewById(R.id.updateProfile_firstName);
		String firstName = editFirstName.getText().toString();
		
		EditText editLastName = (EditText) findViewById(R.id.updateProfile_lastName);
		String lastName = editLastName.getText().toString();
		
		EditText editPhoneNumber = (EditText) findViewById(R.id.updateProfile_phoneNumber);
		String phoneNumber = editPhoneNumber.getText().toString();
		
		EditText editAddress = (EditText) findViewById(R.id.updateProfile_address);
		String address = editAddress.getText().toString();
		
		EditText editZipCode = (EditText) findViewById(R.id.updateProfile_zipCode);
		String zipCode = editZipCode.getText().toString();
		
		RadioGroup radioGender = (RadioGroup) findViewById(R.id.updateProfile_gender);
		int checkedGenderId = radioGender.getCheckedRadioButtonId();
		String gender = checkedGenderId == R.id.male ? "M" : "F";
		
		if (!validateFields(firstName, lastName, checkedGenderId)) {
			Toast.makeText(this, 
					"First, last name and gender fields are required.", 
					Toast.LENGTH_LONG).show();	
			return;
		}
		
		Button buttonUpdateProfile = (Button) findViewById(R.id.updateProfile_button);
		buttonUpdateProfile.setText("Updating Profile...");
		
		User user = new User(_userName, firstName, lastName, gender, phoneNumber, address, zipCode, buttonUpdateProfile);
		String updateStatus = user.updateProfile();
		
		if (updateStatus.equals("success")) {
			Toast.makeText(this, "Successfully updated user profile", Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, "Error in updating user profile. Error Details: " + updateStatus, Toast.LENGTH_LONG).show();
		}
	}
	
	/**
	 * Validates the firstName, lastName and gender before calling the web service
	 * @param firstName First name of the user
	 * @param lastName Last name of the user.
	 * @param selectedGenderId ID of the selected gender
	 * @return true if validation succeeds.
	 */
	private boolean validateFields(String firstName, String lastName, int selectedGenderId) {
		if (isFieldEmpty(firstName, lastName) || selectedGenderId == -1) {
			return false;
		}
		return true;
	}
	
	/**
	 * Validates empty fields
	 * @param firstName First name of the user
	 * @param lastName Last name of the user.
	 * @return true if both fields are non empty
	 */
	private boolean isFieldEmpty(String firstName, String lastName) {
		return firstName.equals("") || lastName.equals("");
	}

	/**
	 * Gets the username for UI testing
	 * @return 
	 */
	public String getUserName() {
		return _userName;
	}
	
	/**
	 * Sets the userName for UI testing
	 * @param userName
	 */
	public void setUserName(String userName) {
		_userName = userName;
	}
	
	public void onLogoutClick(View view) {		
		new AlertDialog.Builder(this)
	    .setTitle("Logout")
	    .setMessage("Are you sure you want to log out?")
	    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	    		Login login = new Login();
	    		login.logOut(UpdateProfileActivity.this);
	    		Intent intent = new Intent(getBaseContext(), LoginActivity.class);
	    		startActivity(intent);
	        }
	     })
	    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	           dialog.cancel();
	        }
	     })
	    .setIcon(android.R.drawable.ic_dialog_alert).show();
	}
}
