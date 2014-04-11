package com.crime.crimebusters;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class UpdateProfileActivity extends Activity{
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_profile);
		int theme= ((MyApplication) this.getApplication()).setTheme();
		getWindow().setBackgroundDrawableResource(theme);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update_profile, menu);
		return true;
	}

	/**
	 * Event handler for the Change Theme button
	 * @param view The object that throws the event.
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public void changeTheme(View view) throws InterruptedException, ExecutionException {
		
		int theme= ((MyApplication) this.getApplication()).getThemeNumber();
		
		if (theme==R.style.MyTheme)
		{
			getWindow().setBackgroundDrawableResource(R.drawable.b6);
			((MyApplication) this.getApplication()).setThemeNumber(R.style.MyTheme2);
		}
		
		else if (theme==R.style.MyTheme2)
		{
			getWindow().setBackgroundDrawableResource(R.drawable.g1);
			((MyApplication) this.getApplication()).setThemeNumber(R.style.MyTheme3);
		}
		
		else if (theme==R.style.MyTheme3)
		{
			getWindow().setBackgroundDrawableResource(R.drawable.c4);
			((MyApplication) this.getApplication()).setThemeNumber(R.style.MyTheme);
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
	 * Event handler for the Update User button
	 * @param view The object that throws the event.
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public void updateUser(View view) throws InterruptedException, ExecutionException {
	
	}
}
