package com.example.bachelortestgrounds;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements OnClickListener {

	public static final String PREFS_NAME = "Usersettings";
	private boolean isNewUser = true;
	private boolean hasWeeklyGoal = false;
	private int weeklyKmGoal;
	private long weeklyProgress;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startBtn = (Button)findViewById(R.id.startwalk);
        startBtn.setOnClickListener(this);
        Button settingsBtn = (Button)findViewById(R.id.settingsbtn);
        settingsBtn.setOnClickListener(this);
        Button statsBtn = (Button)findViewById(R.id.statsbtn);
        statsBtn.setOnClickListener(this);
        
        fetchPreferences();
    }
    
    public void onClick(View view) {
   	 	if (view.getId() == R.id.startwalk) {
   	 		if (hasWeeklyGoal) {
   	 		Intent playIntent = new Intent(this, WalkActivity.class);
   	 		this.startActivity(playIntent);
   	 		}
   	 		else {
   	 			buildGoalDialog();
   	 		}
   	 	}
   	 	if (view.getId() == R.id.settingsbtn) {
   	 		Intent settingsIntent = new Intent(this, SettingsActivity.class);
	 		this.startActivity(settingsIntent);
   	 	}
   	 	if (view.getId() == R.id.statsbtn) {
   	 	
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
    
    public void fetchPreferences() {
    	SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
    	isNewUser = prefs.getBoolean("newUser", true);
    	hasWeeklyGoal = prefs.getBoolean("weeklybool", false);
    	weeklyProgress = prefs.getLong("weeklyProgress", 0);
    	if (hasWeeklyGoal) {
    		weeklyKmGoal = prefs.getInt("weeklygoal", 1);
    		TextView progress = (TextView)findViewById(R.id.progressmsg);
    		progress.setText(getResources().getString(R.string.weeklytxt) + " " + weeklyProgress + "/" + weeklyKmGoal + getResources().getString(R.string.km));
    	}
    }
    
    private void buildGoalDialog() {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setIcon(R.drawable.slider2);
    	builder.setTitle("Set your weekly goal");
    	builder.setMessage("Du må sette ditt ukentlige mål før du går ut på tur.");
    	
    	LinearLayout linear = new LinearLayout(this); 
    	final SeekBar seek = new SeekBar(this);
    	seek.setMax(14);
    	final TextView text=new TextView(this); 
        text.setText(R.string.weeklyplaceholder); 
        text.setPadding(10, 10, 10, 10); 
        
        linear.setOrientation(LinearLayout.HORIZONTAL);
        linear.addView(seek); 
        linear.addView(text); 
    	
    	builder.setView(linear);
    	
    	seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				text.setText("Ukens mål er satt til: " + (seekBar.getProgress() + 1) + "km");
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				
				
			}
		});
    	
    	builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	int weekgoal = seek.getProgress() + 1;
    			SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    			SharedPreferences.Editor editor = settings.edit();
    			editor.putInt("weeklygoal", weekgoal);
    			editor.putBoolean("weeklybool", true);
    			editor.commit();
    			//Toast.makeText(getApplicationContext(), R.string.weeklygoalset + weekgoal + R.string.km, Toast.LENGTH_LONG).show();
    			fetchPreferences();
            }
        });

    	builder.setNegativeButton("Avbryt", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

    	AlertDialog dialog = builder.create();
    	dialog.show();
    }
}
