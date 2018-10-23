
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
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import com.project.styx.CustAlrtMsgOptnListener.MessageCodes;

public class AddEditReview extends Activity {
	
	/** Define variables to use in the activity **/
	private EditText txtReview;
	
	private EditText txtReviewinfo1;
	
	private String strOrigReview = "";
	
	private String strOrigReviewinfo1 = "";
	
	public Integer OrigSpinnerreviewbook = 0;
	
	public Integer OrigSpinnerreviewauthor = 0;
	
	private Long mRowId;
	
	private MyAppDbSQL dbAppDbObj;
	
	private Spinner spinnerreviewauthor;
	
	private Cursor spinnerreviewauthorcursor;
	
	private SimpleCursorAdapter spinnerreviewauthoradapter;
	
	private Spinner spinnerreviewbook;
	
	private Cursor spinnerreviewbookcursor;
	
	private SimpleCursorAdapter spinnerreviewbookadapter;
	
	static final int DATE_DIALOG_ID = 1;
	
	private static MyDisplayAlertClass objDisplayAlertClass;
	
	private boolean blSaveNew;
	
	/** Declaration for must-required information **/
	/** * Event focus change listener Executes code when the focus is changed. Use
	 * it to get data when the user change the information from the edit text
	 * field */
	private OnFocusChangeListener txtReviewOnFocusChangeListener = new OnFocusChangeListener() {
		
		public void onFocusChange(View arg0, boolean arg1) {
			InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			mgr.hideSoftInputFromWindow(arg0.getWindowToken(), 0);
		}
	};
	
	private OnFocusChangeListener txtReviewinfo1OnFocusChangeListener = new OnFocusChangeListener() {
		
		public void onFocusChange(View arg0, boolean arg1) {
			InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			mgr.hideSoftInputFromWindow(arg0.getWindowToken(), 0);
		}
	};
	
	/** myAppCleanup method Sets object variables to null
	 * @return void */
	protected void myAppCleanup() {
		// Set object variables to null
		if (objDisplayAlertClass != null) {
			objDisplayAlertClass.cleanUpClassVars();
			objDisplayAlertClass = null;
		}
		if (this.spinnerreviewbookcursor != null) {
			this.spinnerreviewbookcursor.close();
			this.spinnerreviewbookcursor = null;
		}
		if (this.spinnerreviewbookadapter != null) {
			this.spinnerreviewbookadapter.getCursor().close();
			this.spinnerreviewbookadapter = null;
		}
		if (this.spinnerreviewauthorcursor != null) {
			this.spinnerreviewauthorcursor.close();
			this.spinnerreviewauthorcursor = null;
		}
		if (this.spinnerreviewauthoradapter != null) {
			this.spinnerreviewauthoradapter.getCursor().close();
			this.spinnerreviewauthoradapter = null;
		}
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			extras.clear();
			extras = null;
		}
	}// end myAppCleanup
	
	/** myCancelMethod: Cancel code Discards changes and returns to the calling
	 * screen with status 'RESULT_CANCELED' */
	private void myCancelMethod() {
		APPGlobalVars.SCR_PAUSE_CTL = "CANCEL";
		Bundle bundle = new Bundle();
		bundle.putBoolean("SHOW_FILTER", true);
		bundle.putBoolean("IS_SAVE_NEW", AddEditReview.this.blSaveNew);
		Intent mIntent = new Intent();
		mIntent.putExtras(bundle);
		setResult(RESULT_CANCELED, mIntent);
		// cleanup objects
		bundle = null;
		mIntent = null;
		finish();
	}
	
	/** mySaveDataMethod : Save method Validates the data in the "Required"
	 * fields. (Description, date, and amount) If the validation is successful,
	 * the bundles are populated, and then cleanup is performed, and then the
	 * program returns focus to the calling code. */
	private void mySaveDataMethod() {
		String strReview;
		String strReviewinfo1;
		// ///////////////////////
		int spinSpinnerreviewauthor;
		int spinSpinnerreviewbook;
		// ////////////////////////
		boolean boolSaveOk;
		boolSaveOk = true;
		// if any of the fields are blank, don't let save
		if (this.txtReview == null) {
			this.txtReview = (EditText) findViewById(R.id.txtReview);
		}
		try {
			/** Define the condition command for must filled field ***************************************/
			strReview = this.txtReview.getText().toString();
			if (strReview.contentEquals("")) {
				objDisplayAlertClass = new MyDisplayAlertClass(AddEditReview.this,
						new CustAlrtMsgOptnListener(MessageCodes.ALERT_TYPE_MSG),
						"Missing Required Review label ", "Please enter the review ");
				boolSaveOk = false;
				txtReview.requestFocus();
			}
			/******************************************************************************************/
			strReviewinfo1 = this.txtReviewinfo1.getText().toString();
			// ////////////////////////////////////////////////////////////
			spinSpinnerreviewauthor = (int) this.spinnerreviewauthor.getSelectedItemId();
			spinSpinnerreviewbook = (int) this.spinnerreviewbook.getSelectedItemId();
			// ////////////////////////////////////////////////////////////
			/** Save file command **/
			if (boolSaveOk == true) {
				APPGlobalVars.SCR_PAUSE_CTL = "SAVE";
				Bundle bundle = new Bundle();
				bundle.putString(MyAppDbAdapter.KEY_REVIEW, this.txtReview.getText().toString());
				bundle.putString(MyAppDbAdapter.KEY_REVIEW_INFO_1, strReviewinfo1);
				bundle.putInt(MyAppDbAdapter.KEY_REVIEW_BOOKID, spinSpinnerreviewbook);
				bundle.putInt(MyAppDbAdapter.KEY_REVIEW_AUTHORID, spinSpinnerreviewauthor);
				bundle.putBoolean("SHOW_FILTER", true);
				if (mRowId != null) {
					bundle.putLong(MyAppDbAdapter.KEY_ROWID, mRowId);
				}
				if (((!strOrigReview.equals(strReview)) && (!strOrigReview.equals("")))) {
					bundle.putString("OrigReview", strOrigReview);
				} else {
					bundle.putString("OrigReview", "");
				}// end if
				if (((!strOrigReviewinfo1.equals(strReviewinfo1)) && (!strOrigReviewinfo1.equals("")))) {
					bundle.putString("OrigReviewinfo1", strOrigReviewinfo1);
				} else {
					bundle.putString("OrigReviewinfo1", "");
				}// end if
					// ///////////////////////////////////////////////////////
				if (((!OrigSpinnerreviewbook.equals(spinSpinnerreviewbook)) && (!OrigSpinnerreviewbook
						.equals(0)))) {
					bundle.putInt("OrigSpinnerreviewbook", OrigSpinnerreviewbook);
				} else {
					bundle.putInt("OrigSpinnerreviewbook", 0);
				}// end if
				if (((!OrigSpinnerreviewauthor.equals(spinSpinnerreviewauthor)) && (!OrigSpinnerreviewauthor
						.equals(0)))) {
					bundle.putInt("OrigSpinnerreviewauthor", OrigSpinnerreviewauthor);
				} else {
					bundle.putInt("OrigSpinnerreviewauthor", 0);
				}// end if
					// ////////////////////////////////////////////////////////////
				bundle.putBoolean("IS_SAVE_NEW", AddEditReview.this.blSaveNew);
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
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(AddEditReview.this);
			errExcpError.addToLogFile(error, "AddEditReview.mySaveDataMethod", "");
			errExcpError = null;
		}// end try/catch (Exception error)
	}// end mySaveDataMethod;// end myCancelMethod
	
	/** onActivityResult method Called when an activity you launched exits, giving
	 * you the requestCode you started it with, the resultCode it returned, and
	 * any additional data from it. The resultCode will be RESULT_CANCELED if the
	 * activity explicitly returned that, didn't return any result, or crashed
	 * during its operation. You will receive this call immediately before
	 * onResume() when your activity is re-starting.
	 * @param int requestCode The integer request code originally supplied to
	 *        startActivityForResult(), allowing you to identify who this result
	 *        came from.
	 * @param int resultCode The integer result code returned by the child
	 *        activity through its setResult().
	 * @param Intent intent An Intent, which can return result data to the caller
	 *           (various data can be attached to Intent "extras"). requestCode
	 *           values: ACTIVITY_CREATE, ACTIVITY_EDIT resultCode values:
	 *           RESULT_OK, ACTIVITY_FINISH, RESULT_CANCELED
	 * @return void */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		try {
			AddEditReview.this.blSaveNew = false;
			txtReview = (EditText) findViewById(R.id.txtReview);
			txtReviewinfo1 = (EditText) findViewById(R.id.txtReviewinfo1);
			// ///////////////////////////////////////////////
			this.spinnerreviewbook = (Spinner) findViewById(R.id.spinnerreviewbook);
			this.spinnerreviewauthor = (Spinner) findViewById(R.id.spinnerreviewauthor);
			// ///////////////////////////////////////////////
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
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(AddEditReview.this);
			errExcpError.addToLogFile(error, "AddEditReview.onActivityResult", "");
			errExcpError = null;
		}// end try/catch (Exception error)
	}// end onActivityResult
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_edit_review);
		fillspinnerreviewbook();
		fillspinnerreviewauthor();
		try {
			try {
				/** This code will let the variables get data from a certain input
				 * edit text field */
				txtReview = (EditText) findViewById(R.id.txtReview);
				txtReviewinfo1 = (EditText) findViewById(R.id.txtReviewinfo1);
				this.spinnerreviewbook = (Spinner) findViewById(R.id.spinnerreviewbook);
				this.spinnerreviewauthor = (Spinner) findViewById(R.id.spinnerreviewauthor);
				mRowId = null;
				Bundle extras = getIntent().getExtras();
				if (extras != null) {
					String strReview = extras.getString(MyAppDbAdapter.KEY_REVIEW);
					String strReviewinfo1 = extras.getString(MyAppDbAdapter.KEY_REVIEW_INFO_1);
					int intSpinnerreviewbook = Integer.parseInt(extras
							.getString(MyAppDbAdapter.KEY_REVIEW_BOOKID));
					int intSpinnerreviewauthor = Integer.parseInt(extras
							.getString(MyAppDbAdapter.KEY_REVIEW_AUTHORID));
					mRowId = extras.getLong(MyAppDbAdapter.KEY_ROWID);
					/** This is for compulsory information field in the edit stage. In
					 * order to keep those field filled if the user unintentionally
					 * delete the field, an original information will be keep there **/
					if (strReview != null && !strReview.equals("") && !strReview.equals(" ")) {
						txtReview.setText(strReview);
						strOrigReview = strReview;
					}
					if (strReviewinfo1 != null && !strReviewinfo1.equals("")
							&& !strReviewinfo1.equals(" ")) {
						txtReviewinfo1.setText(strReviewinfo1);
						strOrigReviewinfo1 = strReviewinfo1;
					}
					Integer valuebook = Integer.valueOf(intSpinnerreviewbook);
					if (valuebook != null && intSpinnerreviewbook != 0) {
						OrigSpinnerreviewbook = intSpinnerreviewbook;
					}
					Integer valueauthor = Integer.valueOf(intSpinnerreviewauthor);
					if (valueauthor != null && intSpinnerreviewauthor != 0) {
						OrigSpinnerreviewauthor = intSpinnerreviewauthor;
					}
				}// end if
				/*** This code set event text focus change listeners */
				txtReview.setOnFocusChangeListener(txtReviewOnFocusChangeListener);
				txtReviewinfo1.setOnFocusChangeListener(txtReviewinfo1OnFocusChangeListener);
			}// end try statements
			catch (Exception error) {
				MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(this);
				errExcpError.addToLogFile(error, "AddEditReview.onCreate", "");
				errExcpError = null;
			}// end try/catch (Exception error)
		}
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(this);
			errExcpError.addToLogFile(error, "AddEditReview.onCreate", "opening database");
			errExcpError = null;
		}
	}// end onCreate
	
	private void fillspinnerreviewbook() {
		try {
			boolean dbOpenResult = this.dbAppDbObj.openDbAdapter();
			if (dbOpenResult) {
			
				spinnerreviewbookcursor = dbAppDbObj.fetchBook(0);
				this.startManagingCursor(this.spinnerreviewbookcursor);
				spinnerreviewbookcursor.moveToFirst();
				spinnerreviewbook = (Spinner) findViewById(R.id.spinnerreviewbook);
				String[] from = new String[] { MyAppDbAdapter.KEY_TITLE, MyAppDbAdapter.KEY_ROWID };
				int[] to = new int[] { android.R.id.text1 };
				spinnerreviewbookadapter = new SimpleCursorAdapter(AddEditReview.this,
						android.R.layout.simple_spinner_item, this.spinnerreviewbookcursor, from, to);
				spinnerreviewbook.setAdapter(this.spinnerreviewbookadapter);
				Bundle extras = getIntent().getExtras();
				int intSpinnerreview = Integer.parseInt(extras
						.getString(MyAppDbAdapter.KEY_REVIEW_BOOKID));
				for (int i = 0; i < spinnerreviewbook.getAdapter().getCount(); i++) {
					int id = (int) spinnerreviewbook.getItemIdAtPosition(i);
					if (id == intSpinnerreview) {
						spinnerreviewbook.setSelection(i);
					}
				}
			}
		}
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(AddEditReview.this);
			errExcpError.addToLogFile(error, "AddEditReview.fillspinnerreviewbook ", "");
			errExcpError = null;
		}// end try/catch (Exception error)
	}
	
	private void fillspinnerreviewauthor() {
		try {
			boolean dbOpenResult = this.dbAppDbObj.openDbAdapter();
			if (dbOpenResult) {
				spinnerreviewauthorcursor = dbAppDbObj.fetchAuthor(Author_List.intSortOption);
				this.startManagingCursor(this.spinnerreviewauthorcursor);
				spinnerreviewauthorcursor.moveToFirst();
				spinnerreviewauthor = (Spinner) findViewById(R.id.spinnerreviewauthor);
				String[] from = new String[] { MyAppDbAdapter.KEY_AUTHORFULLNAME,
						MyAppDbAdapter.KEY_ROWID };
				int[] to = new int[] { android.R.id.text1 };
				spinnerreviewauthoradapter = new SimpleCursorAdapter(AddEditReview.this,
						android.R.layout.simple_spinner_item, this.spinnerreviewauthorcursor, from, to);
				spinnerreviewauthor.setAdapter(this.spinnerreviewauthoradapter);
				Bundle extras = getIntent().getExtras();
				int intSpinnerreview = Integer.parseInt(extras.getString(MyAppDbAdapter.KEY_REVIEW_AUTHORID));
				for (int i = 0; i < spinnerreviewauthor.getAdapter().getCount(); i++) {
					int id = (int) spinnerreviewauthor.getItemIdAtPosition(i);
					if (id == intSpinnerreview) {
						spinnerreviewauthor.setSelection(i);
					}
				}
			}
		}
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(AddEditReview.this);
			errExcpError.addToLogFile(error, "AddEditReview.fillspinnerreviewauthor ", "");
			errExcpError = null;
		}// end try/catch (Exception error)
	}
	
	/** onCreateOptionsMenu method Initialize the contents of the Activity's
	 * standard options menu. You should place your menu items in to menu. This
	 * is only called once, the first time the options menu is displayed. To
	 * update the menu every time it is displayed, see
	 * onPrepareOptionsMenu(Menu).
	 * @param Menu menu: The options menu in which you place your items.
	 * @return Must return true for the menu to be displayed; if you return false
	 *         it will not be shown. */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.entry_menu, menu);
		return true;
	}
	
	/** onDestroy method Perform any final cleanup before an activity is
	 * destroyed. This can happen either because the activity is finishing
	 * (someone called finish() on it, or because the system is temporarily
	 * destroying this instance of the activity to save space. You can
	 * distinguish between these two scenarios with the isFinishing() method.
	 * Note: do not count on this method being called as a place for saving data!
	 * For example, if an activity is editing data in a content provider, those
	 * edits should be committed in either onPause() or
	 * onSaveInstanceState(Bundle), not here. This method is usually implemented
	 * to free resources like threads that are associated with an activity, so
	 * that a destroyed activity does not leave such things around while the rest
	 * of its application is still running. There are situations where the system
	 * will simply kill the activity's hosting process without calling this
	 * method (or any others) in it, so it should not be used to do things that
	 * are intended to remain around after the process goes away. Derived classes
	 * must call through to the super class's implementation of this method. If
	 * they do not, an exception will be thrown.
	 * @return void */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		// The user is going somewhere else, so make sure their current
		// changes are safely saved away in the provider. We don't need
		// to do this if only editing.
		myAppCleanup();
	}
	
	/** onKeyDown method Executes code depending on what keyCode is pressed.
	 * @param int keyCode
	 * @param KeyEvent event KeyEvent object
	 * @return true if the code completes execution, false otherwise */
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
	
	/** onKeyUp method Executes code depending on what keyCode is pressed.
	 * @param int keyCode
	 * @param KeyEvent event KeyEvent object
	 * @return true if the code completes execution, false otherwise */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		try {
			switch (keyCode) {
				case KeyEvent.KEYCODE_ENTER:
					if (txtReview.hasFocus()) {
						txtReviewinfo1.requestFocus();
						return true;
					}
				default:
					return false;
			}
		}// end try
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(AddEditReview.this);
			errExcpError.addToLogFile(error, "AddEditReview.onKeyUp", "");
			errExcpError = null;
			return false;
		}// end try/catch (Exception error)
	}// end onKeyUp
	
	/** onMenuItemSelected method onMenuItemSelected method Executes code per the
	 * menu item selected on the menu options that appears when the menu button
	 * is pressed
	 * @param int featureID: The panel that the menu is in.
	 * @param MenuItem item: The menu item that was selected.
	 * @return Return true to finish processing of selection, or false to perform
	 *         the normal menu handling (calling its Runnable or sending a
	 *         Message to its target Handler). */
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
			Intent intentQuitActivity = new Intent(AddEditReview.this, Review_List.class);
			intentQuitActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intentQuitActivity.putExtra("QUITTING", "TRUE");
			startActivity(intentQuitActivity);
		}
		return super.onMenuItemSelected(featureId, item);
	}// end onMenuItemSelected
	
	/** onPause method Called as part of the activity lifecycle when an activity
	 * is going into the background, but has not (yet) been killed. The
	 * counterpart to onResume(). This callback is mostly used for saving any
	 * persistent state the activity is editing, to present a "edit in place"
	 * model to the user and making sure nothing is lost if there are not enough
	 * resources to start the new activity without first killing this one. This
	 * is also a good place to do things like stop animations and other things
	 * that consume a noticeable mount of CPU in order to make the switch to the
	 * next activity as fast as possible, or to close resources that are
	 * exclusive access such as the camera. Checks the current state. If in
	 * "SAVE", "CANCEL", or "SAVEINSTANCE" states, then the "pause" code is
	 * bypassed. "SAVEINSTANCE" is used for when the screen orientation is
	 * changed.
	 * @return void */
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
	
	/** onRestoreInstanceState method This method is called after onStart() when
	 * the activity is being re-initialized from a previously saved state, given
	 * here in state. Most implementations will simply use onCreate(Bundle) to
	 * restore their state, but it is sometimes convenient to do it here after
	 * all of the initialization has been done or to allow subclasses to decide
	 * whether to use your default implementation. The default implementation of
	 * this method performs a restore of any view state that had previously been
	 * frozen by onSaveInstanceState(Bundle).
	 * @param Bundle savedInstanceState: the data most recently supplied in
	 *           onSaveInstanceState(Bundle).
	 * @return void */
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		// Restore UI state from the savedInstanceState.
		// This bundle has also been passed to onCreate.
		try {
			APPGlobalVars.SCR_PAUSE_CTL = "";
			txtReview = (EditText) findViewById(R.id.txtReview);
			txtReviewinfo1 = (EditText) findViewById(R.id.txtReviewinfo1);
			spinnerreviewbook = (Spinner) findViewById(R.id.spinnerreviewbook);
			spinnerreviewauthor = (Spinner) findViewById(R.id.spinnerreviewauthor);
			mRowId = null;
			if (savedInstanceState != null) {
				String strReview = savedInstanceState.getString(MyAppDbAdapter.KEY_REVIEW);
				String strReviewinfo1 = savedInstanceState.getString(MyAppDbAdapter.KEY_REVIEW_INFO_1);
				int intSpinnerreviewbook = savedInstanceState.getInt(MyAppDbAdapter.KEY_REVIEW_BOOKID);
				int intSpinnerreviewauthor = savedInstanceState
						.getInt(MyAppDbAdapter.KEY_REVIEW_AUTHORID);
				mRowId = savedInstanceState.getLong(MyAppDbAdapter.KEY_ROWID);
				if (strReview != null && !strReview.equals("") && !strReview.equals(" ")) {
					txtReview.setText(strReview);
					strOrigReview = strReview;
				}
				if (strReviewinfo1 != null && !strReviewinfo1.equals("") && !strReviewinfo1.equals(" ")) {
					txtReviewinfo1.setText(strReviewinfo1);
					strOrigReviewinfo1 = strReviewinfo1;
				}
				if (intSpinnerreviewbook != 0) {
					OrigSpinnerreviewbook = intSpinnerreviewbook;
				}
				if (intSpinnerreviewauthor != 0) {
					OrigSpinnerreviewauthor = intSpinnerreviewauthor;
				}
			}
		}// end try
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(AddEditReview.this);
			errExcpError.addToLogFile(error, "AddEditReview.onRestoreInstanceState", "");
			errExcpError = null;
		}// end try/catch (Exception error)
	}// end onRestoreInstanceState
	
	/** onResume: onResume: code for when the Activity resumes Called after
	 * onRestoreInstanceState(Bundle), onRestart(), or onPause(), for your
	 * activity to start interacting with the user. This is a good place to begin
	 * animations, open exclusive-access devices (such as the camera), etc. */
	@Override
	protected void onResume() {
		super.onResume();
		AddEditReview.this.blSaveNew = false;
		this.dbAppDbObj = new MyAppDbSQL(this);
		this.fillspinnerreviewbook();
		this.fillspinnerreviewauthor();
	}// end onResume
	
	/** onSaveInstanceState method This method is called before an activity may be
	 * killed so that when it comes back some time in the future it can restore
	 * its state. If called, this method will occur before onStop(). There are no
	 * guarantees about whether it will occur before or after onPause().
	 * @param Bundle savedInstanceState: Bundle in which to place your saved
	 *           state.
	 * @return void */
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		// Save UI state changes to the savedInstanceState.
		// This bundle will be passed to onCreate if the process is
		// killed and restarted.
		try {
			APPGlobalVars.SCR_PAUSE_CTL = "SAVEINSTANCE";
			savedInstanceState.putString(MyAppDbAdapter.KEY_REVIEW, txtReview.getText().toString());
			savedInstanceState.putString(MyAppDbAdapter.KEY_REVIEW_INFO_1, txtReviewinfo1.getText()
					.toString());
			savedInstanceState.putInt(	MyAppDbAdapter.KEY_REVIEW_BOOKID,
												(int) spinnerreviewbook.getSelectedItemId());
			savedInstanceState.putInt(	MyAppDbAdapter.KEY_REVIEW_AUTHORID,
												(int) spinnerreviewauthor.getSelectedItemId());
			if (mRowId != null) {
				savedInstanceState.putLong(MyAppDbAdapter.KEY_ROWID, mRowId);
			}
		}// end try
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(AddEditReview.this);
			errExcpError.addToLogFile(error, "AddEditReview.onSaveInstanceState", "");
			errExcpError = null;
		}// end try/catch (Exception error)
		super.onSaveInstanceState(savedInstanceState);
	}// end onSaveInstanceState
	
	/** onStop method Called when you are no longer visible to the user. You will
	 * next receive either onRestart(), onDestroy(), or nothing, depending on
	 * later user activity. Note that this method may never be called, in low
	 * memory situations where the system does not have enough memory to keep
	 * your activity's process running after its onPause() method is called.
	 * Derived classes must call through to the super class's implementation of
	 * this method. If they do not, an exception will be thrown. * @return void */
	@Override
	protected void onStop() {
		super.onStop();
		/* Set object variables to null */
		myAppCleanup();
	}// end onStop
}