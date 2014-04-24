package com.illinoiscrimebusters.crimebusters;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import com.crime.crimebusters.R;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Activity for the Media page.
 * @author Khushbu
 *
 */
public class MediaActivity extends Activity  {	
	private ReportSingleton _reportSingleton = ReportSingleton.getInstance();
	private ImageView iv1;
	private ImageView iv2;
	private ImageView iv3;
	
	//Audio
	 private MediaRecorder myAudioRecorder;
	 private String outputFile = null;
	 private Button start,stop,play;

	 //Video
	 private static final int VIDEO_CAPTURE = 101;
     private Uri fileUri;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		int theme = _reportSingleton.setTheme();
		getWindow().setBackgroundDrawableResource(theme);
		
		String lang = _reportSingleton.getLanguage();
		if (lang!=null)
		{
			if (lang.equalsIgnoreCase("English"))
				changeLocale("en");
			
			if (lang.equalsIgnoreCase("French"))
				changeLocale("fr");
			
			if (lang.equalsIgnoreCase("Spanish"))
				changeLocale("es");
			
		}
		setContentView(R.layout.activity_media);
		
		if (_reportSingleton.getReportType() == 1) {
			setContentView(R.layout.activity_high_priority_incident);
			Intent intent = getIntent();
			

		} else {
			setContentView(R.layout.activity_media);
			Intent intent = getIntent();
			iv1 = (ImageView) findViewById(R.id.imageView1);
			iv2 = (ImageView) findViewById(R.id.imageView2);
			iv3 = (ImageView) findViewById(R.id.imageView3);
		}

		  // Audio
		  start = (Button)findViewById(R.id.button1);
	      stop = (Button)findViewById(R.id.button2);
	      play = (Button)findViewById(R.id.button3);
	      
	      stop.setEnabled(false);
	      play.setEnabled(false);
	      outputFile = Environment.getExternalStorageDirectory().
	      getAbsolutePath() + "/myrecording.3gp";;

	      myAudioRecorder = new MediaRecorder();
	      myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
	      myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
	      myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
	      myAudioRecorder.setOutputFile(outputFile);
	      
	      // Video
	      Button recBtn = 
	                (Button) findViewById(R.id.recordButton);
			
			if (!hasCamera())
				recBtn.setEnabled(false);
	}
	
	//Video
	private boolean hasCamera() {
	    if (getPackageManager().hasSystemFeature(
                       PackageManager.FEATURE_CAMERA_ANY)){
	        return true;
	    } else {
	        return false;
	    }
	}
	
	//Video
	public void startRecording(View view)
	{
	    File mediaFile = new
        File(Environment.getExternalStorageDirectory().getAbsolutePath() 
              + "/myvideo.mp4");	
	
	    Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
	    fileUri = Uri.fromFile(mediaFile);
		
 	    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
	    startActivityForResult(intent, VIDEO_CAPTURE);
	}
	
	//Audio
	 public void start(View view){
	      try {
	         myAudioRecorder.prepare();
	         myAudioRecorder.start();
	      } catch (IllegalStateException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      } catch (IOException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }
	      start.setEnabled(false);
	      stop.setEnabled(true);
	      Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();

	   }

	 //Audio
	   public void stop(View view){
	      myAudioRecorder.stop();
	      myAudioRecorder.release();
	      myAudioRecorder  = null;
	      stop.setEnabled(false);
	      play.setEnabled(true);
	      Toast.makeText(getApplicationContext(), "Audio recorded successfully",
	      Toast.LENGTH_LONG).show();
	   }
	
	   //Audio
	   public void play(View view) throws IllegalArgumentException,   
	   SecurityException, IllegalStateException, IOException{
	   
	   MediaPlayer m = new MediaPlayer();
	   m.setDataSource(outputFile);
	   m.prepare();
	   m.start();
	   Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_LONG).show();

	   }
	/**
	 * Event handler for the change language button
	 * @param language
	 */
	private void changeLocale(String language) {
		
		Configuration config = getResources().getConfiguration();
		
		// Creating an instance of Locale for French language
        Locale locale = new Locale(language);
 
        // Setting locale of the configuration to French language
        config.locale = locale;
 
        // Updating the application configuration
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
 
        // Setting the title for the activity, after configuration change
        setTitle(R.string.app_name);
        
	}

	/**
	 * @param v
	 */
	public void returnToSubmit(View v) {
		Intent intent = new Intent(this, ReportIncidentActivity.class);
		startActivity(intent);
	}
	/**
	 * @param v
	 */
	public void takePicture(View v) {
		Intent intent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, 0);		
	}
	
	
//	/**
//	 * @param v
//	 */
//	public void takePicture1(View v) {
//		Intent intent = new Intent(
//				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//		startActivityForResult(intent, 0);		
//	}
//	
//	/**
//	 * @param v
//	 */
//	public void takePicture2(View v) {
//		Intent intent = new Intent(
//				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//		startActivityForResult(intent, 0);		
//	}
//	
//	/**
//	 * @param v
//	 */
//	public void takePicture3(View v) {
//		Intent intent = new Intent(
//				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//		startActivityForResult(intent, 0);		
//	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		//Video
		  if (requestCode == VIDEO_CAPTURE) {
		        if (resultCode == RESULT_OK) {
		             Toast.makeText(this, "Video has been saved to:\n" +
		                data.getData(), Toast.LENGTH_LONG).show();
		        } else if (resultCode == RESULT_CANCELED) {
		        	Toast.makeText(this, "Video recording cancelled.", 
	                      Toast.LENGTH_LONG).show();
		        } else {
		        	Toast.makeText(this, "Failed to record video", 
	                      Toast.LENGTH_LONG).show();
		        }
		    }

		  else if (data != null) {
			Bitmap photo = (Bitmap) data.getExtras().get("data");
			
			if (!_reportSingleton.isIv1Done())
			{
				iv1.setImageBitmap(photo);
				SaveImage savefile = new SaveImage();
				savefile.SavePic(this, photo);
				_reportSingleton.setIv1Done(true);
			}
			else if (!_reportSingleton.isIv2Done())
			{
				iv2.setImageBitmap(photo);
				SaveImage savefile = new SaveImage();
				savefile.SavePic(this, photo);
				_reportSingleton.setIv2Done(true);
				
			}
			else if (!_reportSingleton.isIv3Done())
			{
				iv3.setImageBitmap(photo);
				SaveImage savefile = new SaveImage();
				savefile.SavePic(this, photo);
				_reportSingleton.setIv3Done(true);
				
			}
		}
		
	}
	
	/**
	 * If the user has been authenticated before, 
	 * redirect the user to the Main Form
	 */
	protected void onResume() {
		super.onResume();
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.media, menu);
		return true;
	}

}