package com.crime.crimebusters;

import java.util.HashMap;

public class ReportSingleton {
	private String url = "http://sadkhin.net/crimebusters/test_post.php?";
	private String username ="test";
	private String reportType ="1";
	
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
	public String getReportType() {
		return reportType;
	}
	/**
	 * @param reportType the reportType to set
	 */
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	

}
