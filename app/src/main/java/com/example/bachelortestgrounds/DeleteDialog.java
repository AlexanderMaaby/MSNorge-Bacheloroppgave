package com.example.bachelortestgrounds;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.view.Window;

public class DeleteDialog extends Activity{
	
	public static final String PREFS_NAME = "Usersettings";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    // TODO Auto-generated method stub
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    showDialog();
	}

	/**
	 * @throws NotFoundException
	 */
	private void showDialog() throws NotFoundException {
	    new AlertDialog.Builder(this)
	            .setTitle(getResources().getString(R.string.deleteuserdata))
	            .setMessage(
	                    getResources().getString(R.string.deleteuserinfo))
	            .setIcon(
	                    getResources().getDrawable(
	                            android.R.drawable.ic_dialog_alert))
	            .setPositiveButton(
	                    getResources().getString(R.string.positiveyesbutton),
	                    new DialogInterface.OnClickListener() {

	                        @Override
	                        public void onClick(DialogInterface dialog, int which) {
	                        		
	                        		deleteAllData();
	                  		  		Intent playIntent = new Intent(getApplicationContext(), SettingsActivity.class);
	                  		  		startActivity(playIntent);
	                        }
	                    })
	            .setNegativeButton(
	                    getResources().getString(R.string.negativenobutton),
	                    new DialogInterface.OnClickListener() {

	                        @Override
	                        public void onClick(DialogInterface dialog,
	                                int which) {
                  		  		Intent playIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                  		  		startActivity(playIntent);
	                        }
	                    }).show();
	}
	private void deleteAllData() {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.clear().commit();
	}
}
