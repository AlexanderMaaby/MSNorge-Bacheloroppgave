package com.example.bachelortestgrounds;

import java.util.Locale;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.DisplayMetrics;

public class SettingsFragment extends PreferenceFragment {
	
	private Locale myLocale;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
        setUpdatePreferences();
    }
    
    private void setLocale(String lang) {
    	myLocale = new Locale(lang); 
    	Resources res = getResources(); 
    	DisplayMetrics dm = res.getDisplayMetrics(); 
    	Configuration conf = res.getConfiguration(); 
    	conf.locale = myLocale; 
    	res.updateConfiguration(conf, dm); 
    	//Intent refresh = new Intent(this, SettingsActivity.class); 
    	//startActivity(refresh); 
    }
    
    private void setUpdatePreferences() {
        //get the specified preferences using the key declared in preferences.xml
        ListPreference dataPrefConType = (ListPreference) findPreference("language_preference");

        //get the description from the selected item
        dataPrefConType.setSummary( dataPrefConType.getEntry() );

        //when the user choose other item the description changes too with the selected item
        dataPrefConType.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                preference.setSummary(o.toString());
                if (preference.getSummary().toString().equals("English")) {
                	setLocale("en");
                }
                else if (preference.getSummary().toString().equals("Norwegian")) {
                	setLocale("no");
                }
                return true;
            }
        });
    }
}