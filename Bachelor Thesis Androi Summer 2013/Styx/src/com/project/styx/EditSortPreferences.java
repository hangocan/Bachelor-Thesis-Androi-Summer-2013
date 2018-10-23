package com.project.styx;

import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
public class EditSortPreferences extends PreferenceActivity {
	
	String strPrefs_Key = "";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
				int intPrefs_File = extras.getInt("PREFERENCE_FILE");
				strPrefs_Key = extras.getString("PREFERENCE_KEY");
				if (intPrefs_File < 0 || intPrefs_File > 0) {
					addPreferencesFromResource(intPrefs_File);
					if (strPrefs_Key != null && !strPrefs_Key.equals("")
							&& !strPrefs_Key.equals(" ")) {
						ListPreference lpSortOptList = (ListPreference) findPreference(strPrefs_Key);
						if (lpSortOptList != null) {
							setSortSummary(lpSortOptList.getEntry());
						}// end if(lpSortOptList != null)
					}// end if (strPrefs_Key != null &&...
					Preference customPref = (Preference) findPreference(strPrefs_Key);
					customPref
							.setOnPreferenceChangeListener(myOnPreferenceChangeListener);
				}// end if (intPrefs_File < 0 || intPrefs_File > 0)
			}// end if (extras != null)
			else {
				finish();
			}// end if/else
		}// end try
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(this);
			errExcpError.addToLogFile(	error, "EditSortPreferences.onCreate",
												"addPreferencesFromResource");
			errExcpError = null;
		}// end try/catch (Exception error)
	}// end onCreate
	private OnPreferenceChangeListener myOnPreferenceChangeListener = new OnPreferenceChangeListener() {
		
		@Override
		public boolean onPreferenceChange(Preference preference, Object newValue) {
			setSortSummary(getSortSummary(newValue));
			/* finish(); */
			return true;
		}// end onPreferenceChange
	};// end setOnPreferenceChangeListener
	
	/** getSortSummary: set the summary message which displays the selected
	 * sorting
	 * option
	 * @return CharSequence */
	private CharSequence getSortSummary(Object objValue) {
		/* display the selected sorting option */
		CharSequence charSeqSelectedValue = "";
		ListPreference etSortOptions = (ListPreference) findPreference(strPrefs_Key);
		int intPrefKeyIndex = -1;
		if (etSortOptions != null) {
			if (objValue != null) {
				// the ListPreference list is 0-based.
				intPrefKeyIndex = Integer.parseInt((String) objValue);
			} else {
				intPrefKeyIndex = Integer.parseInt(etSortOptions.getValue());
			}
			charSeqSelectedValue = etSortOptions.getEntries()[intPrefKeyIndex];
			intPrefKeyIndex = -1;
			etSortOptions = null;
		}// end if etSortOptions != null)
		return charSeqSelectedValue;
	}// end getSortSummary
	
	/** setSortSummary: set the summary message which displays the selected
	 * sorting
	 * option
	 * @return void */
	private void setSortSummary(CharSequence charSeqValue) {
		/* display the selected sorting option */
		ListPreference etSortOptions = (ListPreference) findPreference(strPrefs_Key);
		if (etSortOptions != null && !charSeqValue.equals("")) {
			refreshSummary(etSortOptions, "Selected Option: " + charSeqValue);
			etSortOptions = null;
			return;
		}// end if etSortOptions != null)
	}// end setSortSummary
	
	/** refreshSummary: refreshes the summary for a preference
	 * @return void */
	private void refreshSummary(Object objPref, String strSummary) {
		((Preference) objPref).setSummary(strSummary);
		return;
	}// end refreshSummary
	
	/** onResume: Cancel button Listener
	 * Called after onRestoreInstanceState(Bundle), onRestart(), or onPause(),
	 * for
	 * your activity to start interacting with the user.
	 * This is a good place to begin animations, open exclusive-access devices
	 * (such as the camera), etc.
	 * Per the conditional check results, cleanup is performed and then the
	 * program returns focus to the calling code. */
	@Override
	protected void onResume() {
		try {
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
				String strQuitStatus = extras.getString("QUITTING");
				if (strQuitStatus != null && strQuitStatus.equals("TRUE")) {
					// exit application
					finish();
				}
			}// end if (extras != null)
		}// end try
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(this);
			errExcpError.addToLogFile(error, "EditSortPreferences.onResume", " ");
			errExcpError = null;
		}// end try/catch (Exception error)
		super.onResume();
	}// end onResume
	
	@Override
	protected void onPause() {
		// The user is going somewhere else, so make sure their current
		// changes are safely saved away in the provider. We don't need
		// to do this if only editing.
		try {
			if (APPGlobalVars.SCR_PAUSE_CTL != null
					&& APPGlobalVars.SCR_PAUSE_CTL.equals("QUIT")) {
				APPGlobalVars.SCR_PAUSE_CTL = "";
				finish();
			}// end if (APPGlobalVars.SCR_PAUSE_CTL != null &&...
		}// end try
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(this);
			errExcpError.addToLogFile(error, "EditSortPreferences.onPause", " ");
			errExcpError = null;
		}// end try/catch (Exception error)
		super.onPause();
	}// end onPause
	
	@Override
	protected void onDestroy() {
		// The user is going somewhere else, so make sure their current
		// changes are safely saved away in the provider. We don't need
		// to do this if only editing.
		myAppAcctCleanup();
		super.onDestroy();
	}// end onDestroy
	
	protected static void myAppAcctCleanup() {
		/* Set object variables to null */
	}// end myAppAcctCleanup
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				/* perform cleanup */
				finish();
				return true;
			default:
				return false;
		}
	}// end onKeyDown
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.preference_menu, menu);
		return true;
	}// end onCreateOptionsMenu
}// end EditSortPreferences class
