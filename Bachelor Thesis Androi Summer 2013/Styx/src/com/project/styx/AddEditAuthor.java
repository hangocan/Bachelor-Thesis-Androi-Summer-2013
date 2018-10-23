
package com.project.styx;

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
public class AddEditAuthor extends Activity {
	
	private EditText txtAuthorFullname;
	private EditText txtAuthorHomepage;
	private EditText txtAuthorEmail;
	static final int DATE_DIALOG_ID = 1;
	private Long mRowId;
	private String strOrigFullname = "";
	private String strOrigHomepage = "";
	private String strOrigEmail = "";
	private Integer bRowId;
	private MyAppDbSQL dbAppDbObj;
	private ListView listbook;
	private Cursor mBookCursor;
	private SimpleCursorAdapter bookRegister;
	private static MyDisplayAlertClass objDisplayAlertClass;
	private boolean blSaveNew;
	private OnFocusChangeListener txtAuthorFullnameOnFocusChangeListener = new OnFocusChangeListener() {
		
		public void onFocusChange(View arg0, boolean arg1) {
			InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			mgr.hideSoftInputFromWindow(arg0.getWindowToken(), 0);
		}
	};
	private OnFocusChangeListener txtAuthorHomepageOnFocusChangeListener = new OnFocusChangeListener() {
		
		public void onFocusChange(View arg0, boolean arg1) {
			InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			mgr.hideSoftInputFromWindow(arg0.getWindowToken(), 0);
		}
	};
	private OnFocusChangeListener txtAuthorEmailOnFocusChangeListener = new OnFocusChangeListener() {
		
		public void onFocusChange(View arg0, boolean arg1) {
			InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			mgr.hideSoftInputFromWindow(arg0.getWindowToken(), 0);
		}
	};
	
	protected void myAppCleanup() {

		if (objDisplayAlertClass != null) {
			objDisplayAlertClass.cleanUpClassVars();
			objDisplayAlertClass = null;
		}
	}
	
	private void myCancelMethod() {
		APPGlobalVars.SCR_PAUSE_CTL = "CANCEL";
		Bundle bundle = new Bundle();
		bundle.putBoolean("SHOW_FILTER", true);
		bundle.putBoolean("IS_SAVE_NEW", AddEditAuthor.this.blSaveNew);
		Intent mIntent = new Intent();
		mIntent.putExtras(bundle);
		setResult(RESULT_CANCELED, mIntent);
		bundle = null;
		mIntent = null;
		finish();
	}
	
	private void mySaveDataMethod() {
		String strFullname;
		String strHomepage;
		String strEmail;
		boolean boolSaveOk;
		boolSaveOk = true;

		if (this.txtAuthorFullname == null) {
			this.txtAuthorFullname = (EditText) findViewById(R.id.txtAuthorFullname);
		}
		try {
			/** Define the condition command for must filled field ***************************************/
			strFullname = this.txtAuthorFullname.getText().toString();
			if (strFullname.contentEquals("")) {
				objDisplayAlertClass = new MyDisplayAlertClass(AddEditAuthor.this,
						new CustAlrtMsgOptnListener(MessageCodes.ALERT_TYPE_MSG),
						"Missing required author fullname",
						"Please enter author fullname");
				boolSaveOk = false;
				txtAuthorFullname.requestFocus();
			}
			/******************************************************************************************/
			strHomepage = this.txtAuthorHomepage.getText().toString();
			strEmail = this.txtAuthorEmail.getText().toString();
			/** Save file command **/
			if (boolSaveOk == true) {
				APPGlobalVars.SCR_PAUSE_CTL = "SAVE";
				Bundle bundle = new Bundle();
				bundle.putString(	MyAppDbAdapter.KEY_AUTHORFULLNAME,
										txtAuthorFullname.getText().toString());
				bundle.putString(MyAppDbAdapter.KEY_AUTHORHOMEPAGE, strHomepage);
				bundle.putString(MyAppDbAdapter.KEY_AUTHOREMAIL, strEmail);
				bundle.putBoolean("SHOW_FILTER", true);
				if (mRowId != null) {
					bundle.putLong(MyAppDbAdapter.KEY_ROWID, mRowId);
				}
				if (((!strOrigFullname.equals(strFullname)) && (!strOrigFullname
						.equals("")))) {
					bundle.putString("OrigFullname", strOrigFullname);
				} else {
					bundle.putString("OrigFullname", "");
				}
				if (((!strOrigHomepage.equals(strHomepage)) && (!strOrigHomepage
						.equals("")))) {
					bundle.putString("OrigHomepage", strOrigHomepage);
				} else {
					bundle.putString("OrigHomepage", "");
				}
				if (((!strOrigEmail.equals(strEmail)) && (!strOrigEmail.equals("")))) {
					bundle.putString("OrigEmail", strOrigEmail);
				} else {
					bundle.putString("OrigEmail", "");
				}
				bundle.putBoolean("IS_SAVE_NEW", AddEditAuthor.this.blSaveNew);
				Intent mIntent = new Intent();
				mIntent.putExtras(bundle);
				setResult(RESULT_OK, mIntent);
				bundle = null;
				mIntent = null;
				finish();
			}
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					AddEditAuthor.this);
			errExcpError.addToLogFile(error, "AddEditAuthor.mySaveDataMethod", "");
			errExcpError = null;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
												Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		try {
			AddEditAuthor.this.blSaveNew = false;
			txtAuthorFullname = (EditText) findViewById(R.id.txtAuthorFullname);
			txtAuthorHomepage = (EditText) findViewById(R.id.txtAuthorHomepage);
			txtAuthorEmail = (EditText) findViewById(R.id.txtAuthorEmail);
			if (intent != null) {
				Bundle extras = intent.getExtras();
				if (extras != null) {
					switch (requestCode) {
						default:
							break;
					}
				}
			}
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					AddEditAuthor.this);
			errExcpError.addToLogFile(error, "AddEditAuthor.onActivityResult", "");
			errExcpError = null;
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_edit_author);
		fillBooklist();
		try {
			try {
				/** This code will let the variables get data from a certain
				 * input edit text field */
				txtAuthorFullname = (EditText) findViewById(R.id.txtAuthorFullname);
				txtAuthorHomepage = (EditText) findViewById(R.id.txtAuthorHomepage);
				txtAuthorEmail = (EditText) findViewById(R.id.txtAuthorEmail);
				mRowId = null;
				Bundle extras = getIntent().getExtras();
				if (extras != null) {
					String strFullname = extras
							.getString(MyAppDbAdapter.KEY_AUTHORFULLNAME);
					String strHomepage = extras
							.getString(MyAppDbAdapter.KEY_AUTHORHOMEPAGE);
					String strEmail = extras
							.getString(MyAppDbAdapter.KEY_AUTHOREMAIL);
					mRowId = extras.getLong(MyAppDbAdapter.KEY_ROWID);
					bRowId = (int) extras.getLong(MyAppDbAdapter.KEY_ROWID);
					/** This is for compulsory information field in the edit
					 * stage. In order to keep those field filled if the user
					 * unintentionally delete the field, an original information
					 * will be keep there **/
					if (strFullname != null && !strFullname.equals("")
							&& !strFullname.equals(" ")) {
						txtAuthorFullname.setText(strFullname);
						strOrigFullname = strFullname;
					}
					if (strHomepage != null && !strHomepage.equals("")
							&& !strHomepage.equals(" ")) {
						txtAuthorHomepage.setText(strHomepage);
						strOrigHomepage = strHomepage;
					}
					if (strEmail != null && !strEmail.equals("")
							&& !strEmail.equals(" ")) {
						txtAuthorEmail.setText(strEmail);
						strOrigEmail = strEmail;
					}
				}
				/*** This code set event text focus change listeners */
				txtAuthorFullname
						.setOnFocusChangeListener(txtAuthorFullnameOnFocusChangeListener);
				txtAuthorHomepage
						.setOnFocusChangeListener(txtAuthorHomepageOnFocusChangeListener);
				txtAuthorEmail
						.setOnFocusChangeListener(txtAuthorEmailOnFocusChangeListener);
			} catch (Exception error) {
				MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(this);
				errExcpError.addToLogFile(error, "AddEditAuthor.onCreate", "");
				errExcpError = null;
			}
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(this);
			errExcpError.addToLogFile(	error, "AddEditAuthor.onCreate",
												"opening database");
			errExcpError = null;
		}
	}
	
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
				/** Open the database and let mAuthorCursor get the data */
				this.mBookCursor = this.dbAppDbObj.fetchBookonauthor(bRowId);
				this.startManagingCursor(this.mBookCursor);
				String[] from = new String[] { MyAppDbAdapter.KEY_TITLE };
				int[] to = new int[] { R.id.Booktitle };
				listbook = (ListView) findViewById(R.id.listbookonauthor);
				this.bookRegister = new SimpleCursorAdapter(this,
						R.layout.bookentryrow, mBookCursor, from, to);
				listbook.setAdapter(bookRegister);
				listbook.setFastScrollEnabled(true);
				listbook.setTextFilterEnabled(false);
			}
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					AddEditAuthor.this);
			errExcpError.addToLogFile(error, "AddEditAuthor.fillbook", "");
			errExcpError = null;
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		myAppCleanup();
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				Bundle bundle = new Bundle();
				bundle.putBoolean("SHOW_FILTER", true);
				Intent mIntent = new Intent();
				mIntent.putExtras(bundle);
				setResult(RESULT_CANCELED, mIntent);

				bundle = null;
				mIntent = null;
				finish();
				return true;
			default:
				return false;
		}
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		try {
			switch (keyCode) {
				case KeyEvent.KEYCODE_ENTER:
					if (txtAuthorEmail.hasFocus()) {
						txtAuthorHomepage.requestFocus();
						return true;
					} else if (txtAuthorHomepage.hasFocus()) {
						txtAuthorFullname.requestFocus();
						return true;
					}
				default:
					return false;
			}
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					AddEditAuthor.this);
			errExcpError.addToLogFile(error, "AddEditAuthor.onKeyUp", "");
			errExcpError = null;
			return false;
		}
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.save_menu) {
			mySaveDataMethod();
		} else if (itemId == R.id.cancel_menu) {
			myCancelMethod();
		} else if (itemId == R.id.quit) {
			Intent intentQuitActivity = new Intent(AddEditAuthor.this,
					Author_List.class);
			intentQuitActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intentQuitActivity.putExtra("QUITTING", "TRUE");
			startActivity(intentQuitActivity);
		}
		return super.onMenuItemSelected(featureId, item);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if (APPGlobalVars.SCR_PAUSE_CTL != null
				&& ((APPGlobalVars.SCR_PAUSE_CTL.equals("SAVE")) || (APPGlobalVars.SCR_PAUSE_CTL
						.equals("CANCEL")))) {
			APPGlobalVars.SCR_PAUSE_CTL = "";
			finish();
		} else {
			APPGlobalVars.SCR_PAUSE_CTL = "";
		}
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		try {
			APPGlobalVars.SCR_PAUSE_CTL = "";
			txtAuthorFullname = (EditText) findViewById(R.id.txtAuthorFullname);
			txtAuthorHomepage = (EditText) findViewById(R.id.txtAuthorHomepage);
			txtAuthorEmail = (EditText) findViewById(R.id.txtAuthorEmail);
			mRowId = null;
			if (savedInstanceState != null) {
				String strFullname = savedInstanceState
						.getString(MyAppDbAdapter.KEY_AUTHORFULLNAME);
				String strHomepage = savedInstanceState
						.getString(MyAppDbAdapter.KEY_AUTHORHOMEPAGE);
				String strEmail = savedInstanceState
						.getString(MyAppDbAdapter.KEY_AUTHOREMAIL);
				mRowId = savedInstanceState.getLong(MyAppDbAdapter.KEY_ROWID);
				if (strFullname != null && !strFullname.equals("")
						&& !strFullname.equals(" ")) {
					txtAuthorFullname.setText(strFullname);
					strOrigFullname = strFullname;
				}
				if (strHomepage != null && !strHomepage.equals("")
						&& !strHomepage.equals(" ")) {
					txtAuthorHomepage.setText(strHomepage);
					strOrigHomepage = strHomepage;
				}
				if (strEmail != null && !strEmail.equals("")
						&& !strEmail.equals(" ")) {
					txtAuthorEmail.setText(strEmail);
					strOrigEmail = strEmail;
				}
			}
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					AddEditAuthor.this);
			errExcpError.addToLogFile(	error,
												"AddEditAuthor.onRestoreInstanceState", "");
			errExcpError = null;
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		AddEditAuthor.this.blSaveNew = false;
		this.dbAppDbObj = new MyAppDbSQL(this);
		fillBooklist();
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		try {
			APPGlobalVars.SCR_PAUSE_CTL = "SAVEINSTANCE";
			savedInstanceState.putString(	MyAppDbAdapter.KEY_AUTHORFULLNAME,
													txtAuthorFullname.getText().toString());
			savedInstanceState.putString(	MyAppDbAdapter.KEY_AUTHORHOMEPAGE,
													txtAuthorHomepage.getText().toString());
			savedInstanceState.putString(	MyAppDbAdapter.KEY_AUTHOREMAIL,
													txtAuthorEmail.getText().toString());
			if (mRowId != null) {
				savedInstanceState.putLong(MyAppDbAdapter.KEY_ROWID, mRowId);
			}
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					AddEditAuthor.this);
			errExcpError.addToLogFile(	error, "AddEditAuthor.onSaveInstanceState",
												"");
			errExcpError = null;
		}
		super.onSaveInstanceState(savedInstanceState);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		myAppCleanup();
	}
}