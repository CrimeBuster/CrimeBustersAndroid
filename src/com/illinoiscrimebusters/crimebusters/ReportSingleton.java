package com.illinoiscrimebusters.crimebusters;

import java.io.BufferedOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.crime.crimebusters.R;

import android.widget.ImageView;

public class ReportSingleton {
	private String url = "http://illinoiscrimebusters.com/services/PostReport.ashx";

	// private String url ="http://sadkhin.net/crimebusters/test_post.php";
	private String username = "test";
	private int reportType = 0;
	private String imageLocation;
	private String image1;
	private String image2;
	private String image3;
	private String audioPath;
	private String videoPath;

	private String audioPathDisplay;
	private String videoPathDisplay;

	public String getAudioPathDisplay() {
		return audioPathDisplay;
	}

	public void setAudioPathDisplay(String audioPathDisplay) {
		this.audioPathDisplay = audioPathDisplay;
	}

	public String getVideoPathDisplay() {
		return videoPathDisplay;
	}

	public void setVideoPathDisplay(String videoPathDisplay) {
		this.videoPathDisplay = videoPathDisplay;
	}

	private String temp_desc;
	private String temp_location;

	private String whichButton;

	public void clearImages() {
		image1 = null;
		image2 = null;
		image3 = null;

	}

	public void clearAudioVideoPaths() {

		audioPath = null;
		videoPath = null;
		audioPathDisplay = null;
		videoPathDisplay = null;
	}

	private BufferedOutputStream bos;
	private BufferedOutputStream audBos;

	public BufferedOutputStream getBos() {
		return bos;
	}

	public void setBos(BufferedOutputStream bos) {
		this.bos = bos;
	}

	public BufferedOutputStream getAudBos() {
		return audBos;
	}

	public void setAudBos(BufferedOutputStream audBos) {
		this.audBos = audBos;
	}

	private boolean iv1Done = false;
	private boolean iv2Done = false;
	private boolean iv3Done = false;

	public boolean isIv1Done() {
		return iv1Done;
	}

	public void setIv1Done(boolean iv1Done) {
		this.iv1Done = iv1Done;
	}

	public boolean isIv2Done() {
		return iv2Done;
	}

	public void setIv2Done(boolean iv2Done) {
		this.iv2Done = iv2Done;
	}

	public boolean isIv3Done() {
		return iv3Done;
	}

	public void setIv3Done(boolean iv3Done) {
		this.iv3Done = iv3Done;
	}

	private String language;

	public String getLanguage() {
		if (language == null) {
			language = "English";
		}
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public int position;

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	private HashMap<String, String> report = new HashMap<String, String>();

	private int themeNumber;

	public int getThemeNumber() {
		return themeNumber;
	}

	public void setThemeNumber(int themeNumber) {
		this.themeNumber = themeNumber;
	}

	public int setTheme() {

		int th = 0;
		if (themeNumber == 0) {
			themeNumber = R.style.MyTheme;
		}

		if (themeNumber == R.style.MyTheme) {
			th = R.drawable.c8;

		}

		else if (themeNumber == R.style.MyTheme2) {
			th = R.drawable.b6;

		}

		else if (themeNumber == R.style.MyTheme3) {
			th = R.drawable.orange;
		}

		return th;
	}

	public String returnKey(String key) {
		return report.get(key);

	}

	public void setKey(String key, String value) {
		report.put(key, value);
	}

	public String getName() {
		return username;

	}

	public HashMap<String, String> copyReport() {
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
	 * @param reportType
	 *            the reportType to set
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

	public void setName(String username2) {
		username = username2;

	}

	public String getAudioPath() {
		return audioPath;
	}

	public void setAudioPath(String audioPath) {
		this.audioPath = audioPath;
	}

	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}

	public String getImage1() {
		return image1;
	}

	public void setImage1(String image1) {
		this.image1 = image1;
	}

	public String getImage2() {
		return image2;
	}

	public void setImage2(String image2) {
		this.image2 = image2;
	}

	public String getImage3() {
		return image3;
	}

	public void setImage3(String image3) {
		this.image3 = image3;
	}

	public String getWhichButton() {
		return whichButton;
	}

	public void setWhichButton(String i) {
		this.whichButton = i;
	}

	public String getTemp_desc() {
		return temp_desc;
	}

	public void setTemp_desc(String temp_desc) {
		this.temp_desc = temp_desc;
	}

	public String getTemp_location() {
		return temp_location;
	}

	public void setTemp_location(String temp_location) {
		this.temp_location = temp_location;
	}

}
