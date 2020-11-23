package com.example.bachelortestgrounds;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class WalkActivity extends Activity implements OnClickListener{
	
	public static final String PREFS_NAME = "Usersettings";
	private int timerint = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);
        
        Button startBtn = (Button)findViewById(R.id.walk);
        startBtn.setOnClickListener(this);
        Button stopBtn = (Button)findViewById(R.id.stop);
        stopBtn.setOnClickListener(this);
        stopBtn.setEnabled(false);
        Button calcBtn = (Button)findViewById(R.id.calcdistance);
        calcBtn.setOnClickListener(this);
        
    }
    public void onClick(View view) {
    	 if (view.getId() == R.id.walk) {
    		 flipButtons(true);
    		 timer.start();
    		 GPSTracker gps = new GPSTracker(WalkActivity.this);
             // check if GPS enabled    
             if(gps.canGetLocation()){
                  
                 double latitude = gps.getLatitude();
                 double longitude = gps.getLongitude();
                  
                 // \n is for new line
                 Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                 
                 TextView txt = (TextView)findViewById(R.id.long1);
                 TextView txt2 = (TextView)findViewById(R.id.lat1);
                 String test = String.valueOf(longitude);
                 txt.setText(test);
                 test = String.valueOf(latitude);
                 txt2.setText(test);
                 	
             }else{
                 // can't get location
                 // GPS or Network is not enabled
                 // Ask user to enable GPS/network in settings
                 gps.showSettingsAlert();
             }
             gps.stopUsingGPS();
    	}
    	 if (view.getId() == R.id.stop) {
    		 flipButtons(false);
    		 timer.cancel();
    		 timerint = 0;
    		 updateTimer();
    		 GPSTracker gps = new GPSTracker(WalkActivity.this);
             // check if GPS enabled    
             if(gps.canGetLocation()){
                  
                 double latitude = gps.getLatitude();
                 double longitude = gps.getLongitude();
                  
                 // \n is for new line
                 Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                 
                 TextView txt = (TextView)findViewById(R.id.long2);
                 TextView txt2 = (TextView)findViewById(R.id.lat2);
                 String test = String.valueOf(longitude);
                 txt.setText(test);
                 test = String.valueOf(latitude);
                 txt2.setText(test);
                 	
             }else{
                 // can't get location
                 // GPS or Network is not enabled
                 // Ask user to enable GPS/network in settings
                 gps.showSettingsAlert();
             }
             gps.stopUsingGPS();
    	}
    	 if (view.getId() == R.id.calcdistance) {
 
    		 TextView txt = (TextView)findViewById(R.id.long1);
             TextView txt2 = (TextView)findViewById(R.id.lat1);
    		 TextView txt3 = (TextView)findViewById(R.id.long2);
             TextView txt4 = (TextView)findViewById(R.id.lat2);
             String test = String.valueOf(txt.getText());
             Double longitude = Double.valueOf(test);
             test = String.valueOf(txt2.getText());
             Double latitude = Double.valueOf(test);
             test = String.valueOf(txt3.getText());
             Double longitude2 = Double.valueOf(test);
             test = String.valueOf(txt4.getText());
             Double latitude2 = Double.valueOf(test);
             
            double test2 = distanceBetweenTwoLocationsInKm(latitude, longitude, latitude2, longitude2);

    		 TextView txt5 = (TextView)findViewById(R.id.thedistance);
    		 txt5.setText(String.valueOf(test2));
    		 
    		 if (test2 > 0.1) {
    			 SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
    			 long x = prefs.getLong("weeklyProgress", 0);
    			 SharedPreferences.Editor editor = prefs.edit();
    			 x += test2;
    			 editor.putLong("weeklyProgress", x);
    			 editor.commit();
    		 }
    	 }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public static Double distanceBetweenTwoLocationsInKm(Double latitudeOne, Double longitudeOne, Double latitudeTwo, Double longitudeTwo) {
        if (latitudeOne == null || latitudeTwo == null || longitudeOne == null || longitudeTwo == null) {
            return null;
        }

        Double earthRadius = 6371.0;
        Double diffBetweenLatitudeRadians = Math.toRadians(latitudeTwo - latitudeOne);
        Double diffBetweenLongitudeRadians = Math.toRadians(longitudeTwo - longitudeOne);
        Double latitudeOneInRadians = Math.toRadians(latitudeOne);
        Double latitudeTwoInRadians = Math.toRadians(latitudeTwo);
        Double a = Math.sin(diffBetweenLatitudeRadians / 2) * Math.sin(diffBetweenLatitudeRadians / 2) + Math.cos(latitudeOneInRadians) * Math.cos(latitudeTwoInRadians) * Math.sin(diffBetweenLongitudeRadians / 2)
                * Math.sin(diffBetweenLongitudeRadians / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return (earthRadius * c);
    }
    
    public void flipButtons(boolean start) {
		 Button startBtn = (Button)findViewById(R.id.walk);
		 Button stopBtn = (Button)findViewById(R.id.stop);
		 if (start) {
			 startBtn.setEnabled(false);
			 stopBtn.setEnabled(true);
		 }
		 else {
			 startBtn.setEnabled(true);
			 stopBtn.setEnabled(false);
		 }
    }
    
    public void updateTimer() {
    	TextView cd=(TextView)findViewById(R.id.timer);
    	cd.setText("Time walked: " + timerint);
    }
    
    public CountDownTimer timer = new CountDownTimer(200000000, 1000) {
    	
        public void onTick(long millisUntilFinished) {

            timerint +=1 ;
            updateTimer();
 
        }
        public void onFinish() {
            
        }
     };
}
