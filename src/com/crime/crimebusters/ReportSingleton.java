package com.crime.crimebusters;

import java.util.HashMap;

import android.widget.ImageView;

public class ReportSingleton {
	private String url = "http://illinoiscrimebusters.com/services/PostReport.ashx";
	private String username ="test";
	private int reportType = 0;
	private String imageLocation;
	
	
	private HashMap<String,String> report = new HashMap<String,String>();
	
	
	public String returnKey(String key){
		return report.get(key);
		
	}
	public void setKey(String key, String value){
		report.put(key, value);
	}
	
	public String getName(){
		return username;
		
	}
	
	
	public HashMap<String, String> copyReport(){
		return this.report;
	}
	
	
	

	// http://stackoverflow.com/questions/2139134/how-to-send-an-object-from-one-android-activity-to-another-using-intents//
	// keyword horse//

	private static ReportSingleton instance = null;

	protected ReportSingleton() {
		// Exists only to defeat instantiation.
	}

	public static ReportSingleton getInstance() {
		if (instance == null) {
			instance = new ReportSingleton();
		}
		return instance;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @return the reportType
	 */
	public int getReportType() {
		return reportType;
	}
	/**
	 * @param reportType the reportType to set
	 */
	public void setReportType(int reportType) {
		this.reportType = reportType;
	}
	public String getImageLocation() {
		return imageLocation;
	}
	public void setImageLocation(String imageLocation) {
		this.imageLocation = imageLocation;
	}

	

}
