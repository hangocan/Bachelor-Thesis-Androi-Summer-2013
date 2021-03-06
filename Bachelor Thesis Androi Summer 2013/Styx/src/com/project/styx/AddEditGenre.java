package com.project.styx;

/** Importing Android add-on elements*/
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.project.styx.CustAlrtMsgOptnListener.MessageCodes;

public class AddEditGenre extends Activity {
	/** Define variables to use in the activity **/
	private EditText txtGenre;
	private EditText txtGenreinfo1;
	private Integer bRowId;
	private MyAppDbSQL dbAppDbObj;
	private ListView listbook;
	private Cursor mBookCursor;
	private SimpleCursorAdapter bookRegister;
	static final int DATE_DIALOG_ID = 1;
	private Long mRowId;
	/** Declaration for must-required information **/
	private String strOrigGenre = "";
	private String strOrigGenreinfo1 = "";
	private static MyDisplayAlertClass objDisplayAlertClass;
	private boolean blSaveNew;
	/**
	 * * Event focus change listener Executes code when the focus is changed.
	 * Use it to get data when the user change the information from the edit
	 * text field
	 */
	private OnFocusChangeListener txtGenreOnFocusChangeListener = new OnFocusChangeListener() {
		public void onFocusChange(View arg0, boolean arg1) {
			InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			mgr.hideSoftInputFromWindow(arg0.getWindowToken(), 0);
		}
	};
	private OnFocusChangeListener txtGenreinfo1OnFocusChangeListener = new OnFocusChangeListener() {
		public void onFocusChange(View arg0, boolean arg1) {
			InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			mgr.hideSoftInputFromWindow(arg0.getWindowToken(), 0);
		}
	};

	/**
	 * myAppCleanup method
	 * 
	 * Sets object variables to null
	 * 
	 * @return void
	 * 
	 */
	protected void myAppCleanup() {
		// Set object variables to null
		if (objDisplayAlertClass != null) {
			objDisplayAlertClass.cleanUpClassVars();
			objDisplayAlertClass = null;
		}
	}// end myAppCleanup

	/**
	 * myCancelMethod: Cancel code
	 * 
	 * Discards changes and returns to the calling screen with status
	 * 'RESULT_CANCELED'
	 * 
	 */
	private void myCancelMethod() {
		APPGlobalVars.SCR_PAUSE_CTL = "CANCEL";
		Bundle bundle = new Bundle();
		bundle.putBoolean("SHOW_FILTER", true);
		bundle.putBoolean("IS_SAVE_NEW", AddEditGenre.this.blSaveNew);
		Intent mIntent = new Intent();
		mIntent.putExtras(bundle);
		setResult(RESULT_CANCELED, mIntent);
		// cleanup objects
		bundle = null;
		mIntent = null;
		finish();
	}

	/**
	 * mySaveDataMethod : Save method
	 * 
	 * Validates the data in the "Required" fields. (Description, date, and
	 * amount)
	 * 
	 * If the validation is successful, the bundles are populated, and then
	 * cleanup is performed, and then the program returns focus to the calling
	 * code.
	 * 
	 */
	private void mySaveDataMethod() {
		String strGenre;
		String strGenreinfo1;

		boolean boolSaveOk;
		boolSaveOk = true;
		// if any of the fields are blank, don't let save
		if (this.txtGenre == null) {
			this.txtGenre = (EditText) findViewById(R.id.txtGenre);
		}
		try {
			/** Define the condition command for must filled field ***************************************/
			strGenre = this.txtGenre.getText().toString();
			if (strGenre.contentEquals("")) {
				objDisplayAlertClass = new MyDisplayAlertClass(
						AddEditGenre.this, new CustAlrtMsgOptnListener(
								MessageCodes.ALERT_TYPE_MSG),
						"Missing Required Genre ", "Please enter  the genre ");
				boolSaveOk = false;
				txtGenre.requestFocus();
			}
			/******************************************************************************************/
			strGenreinfo1 = this.txtGenreinfo1.getText().toString();

			/** Save file command **/
			if (boolSaveOk == true) {
				APPGlobalVars.SCR_PAUSE_CTL = "SAVE";
				Bundle bundle = new Bundle();
				bundle.putString(MyAppDbAdapter.KEY_GENRE, this.txtGenre
						.getText().toString());
				bundle.putString(MyAppDbAdapter.KEY_GENRE_INFO_1, strGenreinfo1);

				bundle.putBoolean("SHOW_FILTER", true);
				if (mRowId != null) {
					bundle.putLong(MyAppDbAdapter.KEY_ROWID, mRowId);
				}
				if (((!strOrigGenre.equals(strGenre)) && (!strOrigGenre
						.equals("")))) {
					bundle.putString("OrigGenre", strOrigGenre);
				} else {
					bundle.putString("OrigGenre", "");
				}// end if
				if (((!strOrigGenre.equals(strGenreinfo1)) && (!strOrigGenreinfo1
						.equals("")))) {
					bundle.putString("OrigGenreinfo1", strOrigGenreinfo1);
				} else {
					bundle.putString("OrigGenreinfo1", "");
				}// end if
				bundle.putBoolean("IS_SAVE_NEW", AddEditGenre.this.blSaveNew);
				Intent mIntent = new Intent();
				mIntent.putExtras(bundle);
				setResult(RESULT_OK, mIntent);
				// cleanup objects
				bundle = null;
				mIntent = null;
				finish();
			}// end if boolSaveOk == true
		}// end try
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					AddEditGenre.this);
			errExcpError.addToLogFile(error, "AddEditGenre.mySaveDataMethod",
					"");
			errExcpError = null;
		}// end try/catch (Exception error)
	}// end mySaveDataMethod;// end myCancelMethod

	
	
	
	
	/**
	 * onActivityResult method
	 * 
	 * Called when an activity you launched exits, giving you the requestCode
	 * you started it with, the resultCode it returned, and any additional data
	 * from it.
	 * 
	 * The resultCode will be RESULT_CANCELED if the activity explicitly
	 * returned that, didn't return any result, or crashed during its operation.
	 * 
	 * You will receive this call immediately before onResume() when your
	 * activity is re-starting.
	 * 
	 * @param int requestCode The integer request code originally supplied to
	 *        startActivityForResult(), allowing you to identify who this result
	 *        came from.
	 * @param int resultCode The integer result code returned by the child
	 *        activity through its setResult().
	 * @param Intent
	 *            intent An Intent, which can return result data to the caller
	 *            (various data can be attached to Intent "extras").
	 * 
	 *            requestCode values: ACTIVITY_CREATE, ACTIVITY_EDIT
	 * 
	 *            resultCode values: RESULT_OK, ACTIVITY_FINISH, RESULT_CANCELED
	 * 
	 * @return void
	 * 
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		try {
			AddEditGenre.this.blSaveNew = false;
			txtGenre = (EditText) findViewById(R.id.txtGenre);
			txtGenreinfo1 = (EditText) findViewById(R.id.txtGenreinfo1);

			if (intent != null) {
				Bundle extras = intent.getExtras();
				if (extras != null) {
					switch (requestCode) {
					default:
						break;
					}// end switch
				}// end if (extras != null)
			}// end if (intent != null)
		}// end try
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					AddEditGenre.this);
			errExcpError.addToLogFile(error, "AddEditGenre.onActivityResult",
					"");
			errExcpError = null;
		}// end try/catch (Exception error)
	}// end onActivityResult

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_edit_genre);
		fillBooklist();
		try {
			try {
				/**
				 * This code will let the variables get data from a certain
				 * input edit text field
				 */
				txtGenre = (EditText) findViewById(R.id.txtGenre);
				txtGenreinfo1 = (EditText) findViewById(R.id.txtGenreinfo1);
				this.listbook = (ListView) findViewById(R.id.listbookongenre);
				
			
				mRowId = null;
				bRowId = 0;
				Bundle extras = getIntent().getExtras();
				if (extras != null) {
					String strGenre = extras
							.getString(MyAppDbAdapter.KEY_GENRE);
					String strGenreinfo1 = extras
							.getString(MyAppDbAdapter.KEY_GENRE_INFO_1);
					
					mRowId = extras.getLong(MyAppDbAdapter.KEY_ROWID);
						 bRowId = (int) extras.getLong(MyAppDbAdapter.KEY_ROWID);
					/**
					 * This is for compulsory information field in the edit
					 * stage. In order to keep those field filled if the user
					 * unintentionally delete the field, an original information
					 * will be keep there
					 **/
					if (strGenre != null && !strGenre.equals("")
							&& !strGenre.equals(" ")) {
						txtGenre.setText(strGenre);
						strOrigGenre = strGenre;
					}
					if (strGenreinfo1 != null && !strGenreinfo1.equals("")
							&& !strGenreinfo1.equals(" ")) {
						txtGenreinfo1.setText(strGenreinfo1);
						strOrigGenreinfo1 = strGenreinfo1;
					}
				}// end if
				/*** This code set event text focus change listeners */
				txtGenre.setOnFocusChangeListener(txtGenreOnFocusChangeListener);
				txtGenreinfo1
						.setOnFocusChangeListener(txtGenreinfo1OnFocusChangeListener);

				/**
				 * txtGenrePublisher = txtGenreGenre = txtGenreShelf =
				 * txtGenreLanguage =
				 */
			}// end try statements
			catch (Exception error) {
				MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
						this);
				errExcpError.addToLogFile(error, "AddEditGenre.onCreate", "");
				errExcpError = null;
			}// end try/catch (Exception error)
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(this);
			errExcpError.addToLogFile(error, "AddEditGenre.onCreate",
					"opening database");
			errExcpError = null;
		}
	}// end onCreate

	/**
	 * onCreateOptionsMenu method
	 * 
	 * Initialize the contents of the Activity's standard options menu. You
	 * should place your menu items in to menu.
	 * 
	 * This is only called once, the first time the options menu is displayed.
	 * 
	 * To update the menu every time it is displayed, see
	 * onPrepareOptionsMenu(Menu).
	 * 
	 * @param Menu
	 *            menu: The options menu in which you place your items.
	 * 
	 * @return Must return true for the menu to be displayed; if you return
	 *         false it will not be shown.
	 * 
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.entry_menu, menu);
		return true;
	}
	private void fillBooklist() {
		try {
			boolean dbOpenResult = this.dbAppDbObj.openDbAdapter();
			if (dbOpenResult) {
				/** Open the database and let mGenreCursor get the data */
				this.mBookCursor = this.dbAppDbObj.fetchBookonGenre(bRowId);
				this.startManagingCursor(this.mBookCursor);
				String[] from = new String[] { MyAppDbAdapter.KEY_TITLE };
				int[] to = new int[] { R.id.Booktitle };
				listbook = (ListView) findViewById(R.id.listbookongenre);
				this.bookRegister = new SimpleCursorAdapter(this,
						R.layout.bookentryrow, mBookCursor, from, to);
				listbook.setAdapter(bookRegister);
				listbook.setFastScrollEnabled(true);
				listbook.setTextFilterEnabled(false);
			
			}
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					AddEditGenre.this);
			errExcpError.addToLogFile(error, "AddEditGenre.fillbook", "");
			errExcpError = null;
		}// end catch (Exception error)
	}// end fillGenre
	/**
	 * onDestroy method
	 * 
	 * Perform any final cleanup before an activity is destroyed.
	 * 
	 * This can happen either because the activity is finishing (someone called
	 * finish() on it, or because the system is temporarily destroying this
	 * instance of the activity to save space.
	 * 
	 * You can distinguish between these two scenarios with the isFinishing()
	 * method.
	 * 
	 * Note: do not count on this method being called as a place for saving
	 * data! For example, if an activity is editing data in a content provider,
	 * those edits should be committed in either onPause() or
	 * onSaveInstanceState(Bundle), not here.
	 * 
	 * This method is usually implemented to free resources like threads that
	 * are associated with an activity, so that a destroyed activity does not
	 * leave such things around while the rest of its application is still
	 * running.
	 * 
	 * There are situations where the system will simply kill the activity's
	 * hosting process without calling this method (or any others) in it, so it
	 * should not be used to do things that are intended to remain around after
	 * the process goes away.
	 * 
	 * Derived classes must call through to the super class's implementation of
	 * this method. If they do not, an exception will be thrown.
	 * 
	 * @return void
	 * 
	 */
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		// The user is going somewhere else, so make sure their current
		// changes are safely saved away in the provider. We don't need
		// to do this if only editing.
		myAppCleanup();
	}

	/**
	 * onKeyDown method
	 * 
	 * Executes code depending on what keyCode is pressed.
	 * 
	 * @param int keyCode
	 * @param KeyEvent
	 *            event KeyEvent object
	 * 
	 * @return true if the code completes execution, false otherwise
	 * 
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			Bundle bundle = new Bundle();
			bundle.putBoolean("SHOW_FILTER", true);
			Intent mIntent = new Intent();
			mIntent.putExtras(bundle);
			setResult(RESULT_CANCELED, mIntent);
			// cleanup objects
			bundle = null;
			mIntent = null;
			finish();
			return true;
		default:
			return false;
		}
	}// end onKeyDown

	/**
	 * onKeyUp method
	 * 
	 * Executes code depending on what keyCode is pressed.
	 * 
	 * @param int keyCode
	 * @param KeyEvent
	 *            event KeyEvent object
	 * 
	 * @return true if the code completes execution, false otherwise
	 * 
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		try {
			switch (keyCode) {
			case KeyEvent.KEYCODE_ENTER:
				if (txtGenre.hasFocus()) {
					// sends focus to txtGenreGenre (user pressed "Next")
					txtGenreinfo1.requestFocus();
					return true;
				}
			default:
				return false;
			}
		}// end try
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					AddEditGenre.this);
			errExcpError.addToLogFile(error, "AddEditGenre.onKeyUp", "");
			errExcpError = null;
			return false;
		}// end try/catch (Exception error)
	}// end onKeyUp

	/**
	 * onMenuItemSelected method
	 * 
	 * onMenuItemSelected method Executes code per the menu item selected on the
	 * menu options that appears when the menu button is pressed
	 * 
	 * @param int featureID: The panel that the menu is in.
	 * @param MenuItem
	 *            item: The menu item that was selected.
	 * 
	 * @return Return true to finish processing of selection, or false to
	 *         perform the normal menu handling (calling its Runnable or sending
	 *         a Message to its target Handler).
	 * 
	 */
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.save_menu) {
			/* save entry */
			// call save method code
			mySaveDataMethod();
		} else if (itemId == R.id.cancel_menu) {
			// call save method code
			myCancelMethod();
		} else if (itemId == R.id.quit) {
			// application is exiting
			Intent intentQuitActivity = new Intent(AddEditGenre.this,
					Genre_List.class);
			intentQuitActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intentQuitActivity.putExtra("QUITTING", "TRUE");
			startActivity(intentQuitActivity);
		}
		return super.onMenuItemSelected(featureId, item);
	}// end onMenuItemSelected

	/**
	 * onPause method
	 * 
	 * Called as part of the activity lifecycle when an activity is going into
	 * the background, but has not (yet) been killed. The counterpart to
	 * onResume().
	 * 
	 * This callback is mostly used for saving any persistent state the activity
	 * is editing, to present a "edit in place" model to the user and making
	 * sure nothing is lost if there are not enough resources to start the new
	 * activity without first killing this one.
	 * 
	 * This is also a good place to do things like stop animations and other
	 * things that consume a noticeable mount of CPU in order to make the switch
	 * to the next activity as fast as possible, or to close resources that are
	 * exclusive access such as the camera.
	 * 
	 * Checks the current state. If in "SAVE", "CANCEL", or "SAVEINSTANCE"
	 * states, then the "pause" code is bypassed.
	 * 
	 * "SAVEINSTANCE" is used for when the screen orientation is changed.
	 * 
	 * @return void
	 * 
	 */
	@Override
	protected void onPause() {
		super.onPause();
		// The user is going somewhere else, so make sure their current
		// changes are safely saved away in the provider. We don't need
		// to do this if only editing.
		if (APPGlobalVars.SCR_PAUSE_CTL != null
				&& ((APPGlobalVars.SCR_PAUSE_CTL.equals("SAVE")) || (APPGlobalVars.SCR_PAUSE_CTL
						.equals("CANCEL")))) {
			APPGlobalVars.SCR_PAUSE_CTL = "";
			finish();
		} else {
			APPGlobalVars.SCR_PAUSE_CTL = "";
		}
	}// end onPause

	/**
	 * onRestoreInstanceState method
	 * 
	 * This method is called after onStart() when the activity is being
	 * re-initialized from a previously saved state, given here in state.
	 * 
	 * Most implementations will simply use onCreate(Bundle) to restore their
	 * state, but it is sometimes convenient to do it here after all of the
	 * initialization has been done or to allow subclasses to decide whether to
	 * use your default implementation.
	 * 
	 * The default implementation of this method performs a restore of any view
	 * state that had previously been frozen by onSaveInstanceState(Bundle).
	 * 
	 * @param Bundle
	 *            savedInstanceState: the data most recently supplied in
	 *            onSaveInstanceState(Bundle).
	 * 
	 * @return void
	 * 
	 */
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		// Restore UI state from the savedInstanceState.
		// This bundle has also been passed to onCreate.
		try {
			APPGlobalVars.SCR_PAUSE_CTL = "";
			txtGenre = (EditText) findViewById(R.id.txtGenre);
			txtGenreinfo1 = (EditText) findViewById(R.id.txtGenreinfo1);

			mRowId = null;
			if (savedInstanceState != null) {
				String strGenre = savedInstanceState
						.getString(MyAppDbAdapter.KEY_GENRE);
				String strGenreinfo1 = savedInstanceState
						.getString(MyAppDbAdapter.KEY_GENRE_INFO_1);
				mRowId = savedInstanceState.getLong(MyAppDbAdapter.KEY_ROWID);
				if (strGenre != null && !strGenre.equals("")
						&& !strGenre.equals(" ")) {
					txtGenre.setText(strGenre);
					strOrigGenre = strGenre;
				}
				if (strGenreinfo1 != null && !strGenreinfo1.equals("")
						&& !strGenreinfo1.equals(" ")) {
					txtGenreinfo1.setText(strGenreinfo1);
					strOrigGenreinfo1 = strGenreinfo1;
				}
			}
		}// end try
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					AddEditGenre.this);
			errExcpError.addToLogFile(error,
					"AddEditGenre.onRestoreInstanceState", "");
			errExcpError = null;
		}// end try/catch (Exception error)
	}// end onRestoreInstanceState

	/**
	 * onResume: onResume: code for when the Activity resumes
	 * 
	 * Called after onRestoreInstanceState(Bundle), onRestart(), or onPause(),
	 * for your activity to start interacting with the user.
	 * 
	 * This is a good place to begin animations, open exclusive-access devices
	 * (such as the camera), etc.
	 * 
	 */
	@Override
	protected void onResume() {
		super.onResume();
		AddEditGenre.this.blSaveNew = false;
		this.dbAppDbObj = new MyAppDbSQL(this);
		fillBooklist();
	}// end onResume

	/**
	 * onSaveInstanceState method
	 * 
	 * This method is called before an activity may be killed so that when it
	 * comes back some time in the future it can restore its state.
	 * 
	 * If called, this method will occur before onStop(). There are no
	 * guarantees about whether it will occur before or after onPause().
	 * 
	 * @param Bundle
	 *            savedInstanceState: Bundle in which to place your saved state.
	 * 
	 * @return void
	 * 
	 */
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		// Save UI state changes to the savedInstanceState.
		// This bundle will be passed to onCreate if the process is
		// killed and restarted.
		try {
			APPGlobalVars.SCR_PAUSE_CTL = "SAVEINSTANCE";
			savedInstanceState.putString(MyAppDbAdapter.KEY_GENRE, txtGenre
					.getText().toString());
			savedInstanceState.putString(MyAppDbAdapter.KEY_GENRE_INFO_1,
					txtGenreinfo1.getText().toString());

			if (mRowId != null) {
				savedInstanceState.putLong(MyAppDbAdapter.KEY_ROWID, mRowId);
			}
		}// end try
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					AddEditGenre.this);
			errExcpError.addToLogFile(error,
					"AddEditGenre.onSaveInstanceState", "");
			errExcpError = null;
		}// end try/catch (Exception error)
		super.onSaveInstanceState(savedInstanceState);
	}// end onSaveInstanceState

	/**
	 * onStop method
	 * 
	 * Called when you are no longer visible to the user. You will next receive
	 * either onRestart(), onDestroy(), or nothing, depending on later user
	 * activity. Note that this method may never be called, in low memory
	 * situations where the system does not have enough memory to keep your
	 * activity's process running after its onPause() method is called. Derived
	 * classes must call through to the super class's implementation of this
	 * method. If they do not, an exception will be thrown.
	 * 
	 * * @return void
	 * 
	 */
	@Override
	protected void onStop() {
		super.onStop();
		/* Set object variables to null */
		myAppCleanup();
	}// end onStop
}// end AddEditGenre