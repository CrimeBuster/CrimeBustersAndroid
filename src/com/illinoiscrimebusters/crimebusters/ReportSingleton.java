package com.illinoiscrimebusters.crimebusters;

import java.util.HashMap;

import com.crime.crimebusters.R;

import android.widget.ImageView;

public class ReportSingleton {
	private String url = "http://illinoiscrimebusters.com/services/PostReport.ashx";

//	private String  url ="http://sadkhin.net/crimebusters/test_post.php";
	private String username ="test";
	private int reportType = 0;
	private String imageLocation;
	
	
	private HashMap<String,String> report = new HashMap<String,String>();
	
	private int themeNumber = R.style.MyTheme;

    public int getThemeNumber() {
        return themeNumber;
    }

    public void setThemeNumber(int themeNumber) {
        this.themeNumber = themeNumber;
    }

    public int setTheme()
    {
	    if (themeNumber==R.style.MyTheme)
		{
			return R.drawable.c4;
			
		}
		
		else if (themeNumber==R.style.MyTheme2)
		{
			return R.drawable.b6;
			
		}
	    
		else if (themeNumber==R.style.MyTheme3)
		{
			return R.drawable.g1;
		}
	    return 0;
    }
	
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
