package com.illinoiscrimebusters.crimebusters;
import java.io.File;
import java.io.IOException;

import com.crime.crimebusters.R;

import android.app.Activity;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnErrorListener;
import android.media.MediaRecorder.OnInfoListener;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoRecorderActivity extends Activity implements SurfaceHolder.Callback, OnInfoListener, OnErrorListener{
	
	private Button initBtn = null;
	private Button startBtn = null;
	private Button stopBtn = null;
	private Button playBtn = null;
	private Button stopPlayBtn = null;
	private TextView recordingMsg = null;
	private VideoView videoView = null;
	private SurfaceHolder holder = null;
	private Camera camera = null;
	private static final String TAG = "RecordVideo";
	private MediaRecorder recorder = null;
	private String outputFileName;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_recorder);
		
		//get references to UI elements
		initBtn = (Button) findViewById(R.id.initBtn);
		startBtn = (Button) findViewById(R.id.startBtn);
		stopBtn = (Button) findViewById(R.id.stopBtn);
		playBtn = (Button) findViewById(R.id.reviewBtn);
		stopPlayBtn = (Button) findViewById(R.id.stopReviewBtn);
		recordingMsg = (TextView) findViewById(R.id.recordingMsg);
		videoView = (VideoView)this.findViewById(R.id.videoView1);
	}
	
	public void buttonTapped(View view){
		switch(view.getId()){
		case R.id.initBtn:
			initRecorder();
			break;
		case R.id.startBtn:
			beginRecording();
			break;
		case R.id.stopBtn:
			stopRecording();
		case R.id.reviewBtn:
			playRecording();
		case R.id.stopReviewBtn:
			stopPlayback();
			break;
		}
	}

	private void stopPlayback() {
		videoView.stopPlayback();
		
	}

	private void playRecording() {
		MediaController mc = new MediaController(this);
		videoView.setMediaController(mc);
		videoView.setVideoPath(outputFileName);
		videoView.start();
		stopPlayBtn.setEnabled(true);
		
	}

	private void stopRecording() {
		if(recorder != null){
			recorder.setOnErrorListener(null);
			recorder.setOnInfoListener(null);
			try{
				recorder.stop();
			}catch(IllegalStateException e){
				//This can happen if the recorder has already stopped.
				Log.e(TAG, "Got IllegalStateException in stopRecording");
			}
			releaseRecorder();
			recordingMsg.setText("");
			releaseCamera();
			startBtn.setEnabled(false);
			stopBtn.setEnabled(false);
			playBtn.setEnabled(true);
		}
		
	}

	private void releaseCamera() {
		if(camera != null){
			try{
				camera.reconnect();
			}catch(IOException e){
				e.printStackTrace();
			}
			camera.release();
			camera = null;
		}
		
	}

	private void releaseRecorder() {
		if(recorder != null){
			recorder.release();
			recorder = null;
		}
		
	}

	private void beginRecording() {
		recorder.setOnInfoListener(this);
		recorder.setOnErrorListener(this);
		recorder.start();
		recordingMsg.setText("RECORDING");
		startBtn.setEnabled(false);
		stopBtn.setEnabled(true);
	}

	private void initRecorder() {
		if(recorder != null) return;
		
		outputFileName = Environment.getExternalStorageDirectory() + "/videooutput.mp4";
		
		File outFile = new File(outputFileName);
		if(outFile.exists())
			outFile.delete();
		
		try{
			camera.stopPreview();
			camera.unlock();
			recorder = new MediaRecorder();
			recorder.setCamera(camera);
			
			recorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
			recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
			recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
			recorder.setVideoSize(176,144);
			recorder.setVideoFrameRate(15);
			recorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
			recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			recorder.setMaxDuration(10000); //limit to 10 seconds
			recorder.setPreviewDisplay(holder.getSurface());
			recorder.setOutputFile(outputFileName);
			
			recorder.prepare();
			Log.v(TAG, "MediaRecorder initialized");
			initBtn.setEnabled(false);
			startBtn.setEnabled(true);
		}catch(Exception e){
			Log.v(TAG, "MediaRecorder failed to initialize");
			e.printStackTrace();
		}
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.v(TAG, "in surfaceCreated");
		
		try{
			camera.setPreviewDisplay(holder);
			camera.startPreview();
		}catch(IOException e){
			Log.v(TAG, "Could not start the preview");
			e.printStackTrace();
		}
		initBtn.setEnabled(true);
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void onResume(){
		Log.v(TAG, "in onResume");
		super.onResume();
		initBtn.setEnabled(false);
		startBtn.setEnabled(false);
		stopBtn.setEnabled(false);
		playBtn.setEnabled(false);
		stopPlayBtn.setEnabled(false);
		if(!initCamera())
			finish();
	}

	private boolean initCamera() {
		try{
			camera = Camera.open();
			Camera.Parameters camParams = camera.getParameters();
			camera.lock();
			holder = videoView.getHolder();
			holder.addCallback(this);
			holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}catch(RuntimeException re){
			Log.v(TAG, "Could not initialize the Camera");
			re.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public void onError(MediaRecorder mr, int what, int extra) {
		Log.i(TAG, "got a recording error");
		stopRecording();
		Toast.makeText(this, "Recording error has occurred. Stopping the recording", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onInfo(MediaRecorder mr, int what, int extra) {
		Log.i(TAG, "got a recording event");
		if(what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED){
			Log.i(TAG, "...max duration reached");
			stopRecording();
			Toast.makeText(this, "Recording limit has been reached. Stopping the recording", Toast.LENGTH_SHORT).show();
		}
	}

}
