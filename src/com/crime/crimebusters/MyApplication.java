package com.crime.crimebusters;

import android.app.Application;

public class MyApplication extends Application {

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

}


