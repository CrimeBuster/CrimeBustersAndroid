package com.crime.crimebusters;

import http_request.HttpRequestHandler;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class HTTPSubmitReportActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submit_report);
		Intent intent = getIntent();
	
		ReportSingleton r = ReportSingleton.getInstance(); 
		String name = r.getName();
		 
		TextView nameView = (TextView) findViewById(R.id.textView2);
		

		HttpRequestHandler h = new HttpRequestHandler();
		
		
		String nameReturn;
		try {
			nameReturn = h.execute("go").get();
			nameView.setText("" + nameReturn);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 nameView.setText("Interrupted execption");
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			 nameView.setText("Execution Exception");
			e.printStackTrace();
		}
		
		 
		 
	
		 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.httpsubmit_report, menu);
		return true;
	}

}
