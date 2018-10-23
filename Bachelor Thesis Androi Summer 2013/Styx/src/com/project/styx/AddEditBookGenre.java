
package com.project.styx;

/** Importing Android add-on elements */
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import com.project.styx.CustAlrtMsgOptnListener.MessageCodes;
public class AddEditBookGenre extends Activity implements TextWatcher {
	
	/** Define variables to use in the activity **/
	private String newgenre;
	private Integer bRowId;
	private int aRowId;
	EditText instantgenre;
	private MyAppDbSQL dbAppDbObj;
	private ListView listgenre;
	private Cursor mGenreBookCursor;
	private SimpleCursorAdapter genrebookRegister;
	private Spinner spinnerbggenre;
	private TextView bgbook;
	private Cursor spinnerbggenrecursor;
	private Button btaddgenre;
	private static final int DELETE_CONFIRMATION_MESSAGE = 1;
	private SimpleCursorAdapter spinnerbggenreadapter;
	protected double dblAcctStartBal = 0.00;
	private boolean blContinue = false;
	static final int DATE_DIALOG_ID = 1;
	private String bgtitle;
	private static MyDisplayAlertClass objDisplayAlertClass;
	private boolean blSaveNew;
	private Button btnewgenre;
	
	protected void myAppCleanup() {
		if (objDisplayAlertClass != null) {
			objDisplayAlertClass.cleanUpClassVars();
			objDisplayAlertClass = null;
		}
		if (this.spinnerbggenrecursor != null) {
			this.spinnerbggenrecursor.close();
			this.spinnerbggenrecursor = null;
		}
		if (this.spinnerbggenreadapter != null) {
			this.spinnerbggenreadapter.getCursor().close();
			this.spinnerbggenreadapter = null;
		}
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			extras.clear();
			extras = null;
		}
	}// end myAppCleanup
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
												Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		try {
			AddEditBookGenre.this.blSaveNew = false;
			this.spinnerbggenre = (Spinner) findViewById(R.id.spinnerbggenre);
			if (intent != null) {
				Bundle extras = intent.getExtras();
				if (extras != null) {
					switch (requestCode) {
						default:
							break;
					}// end switch
				}
			}
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					AddEditBookGenre.this);
			errExcpError.addToLogFile(	error, "AddEditBookGenre.onActivityResult",
												"");
			errExcpError = null;
		}
	}// end onActivityResult
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_edit_book_genre);
		fillspinnerbggenre();
		fillGenrelist();
		try {
			try {
				/** This code will let the variables get data from a certain
				 * input edit text field */
				this.spinnerbggenre = (Spinner) findViewById(R.id.spinnerbggenre);
				this.bgbook = (TextView) findViewById(R.id.bgbook);
				this.btaddgenre = (Button) findViewById(R.id.btaddgenre);
				this.btnewgenre = (Button) findViewById(R.id.btnewgenre);
				this.listgenre = (ListView) findViewById(R.id.listgenre);
				this.instantgenre = (EditText) findViewById(R.id.instantgenre);
				bRowId = 0;
				Bundle extras = getIntent().getExtras();
				if (extras != null) {
					bRowId = (int) extras.getLong(MyAppDbAdapter.KEY_ROWID);
					bgtitle = extras.getString(MyAppDbAdapter.KEY_TITLE);
				}
				
				this.bgbook.setText(bgtitle);
				btaddgenre.setOnClickListener(new View.OnClickListener() {
					
					private String x;
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dbAppDbObj.createBookGenreEntry(bRowId, aRowId);
						x = "The genre has been register!";
						Toast.makeText(getApplicationContext(), x, Toast.LENGTH_SHORT)
								.show();
						btaddgenre.setEnabled(false);
						fillGenrelist();
					}
				});
				this.btnewgenre.setOnClickListener(new View.OnClickListener() {
					
					private TextView instantgenre;
					private String y;
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						this.instantgenre = (EditText) findViewById(R.id.instantgenre);
						newgenre = this.instantgenre.getText().toString();
						if (this.instantgenre != null && !newgenre.contentEquals("")) {
							dbAppDbObj.createGenreInstantEntry(newgenre);
							y = "New genre has been added! Please update later";
							Toast.makeText(getApplicationContext(), y,
												Toast.LENGTH_SHORT).show();
							instantgenre.setText("");
							InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
							imm.hideSoftInputFromWindow(	instantgenre
																			.getWindowToken(),
																	0);
						} else {
							y = "New genre cannot be add ! Please input the genre name !";
							Toast.makeText(getApplicationContext(), y,
												Toast.LENGTH_SHORT).show();
						}
						fillspinnerbggenre();
					}
				});
				registerForContextMenu(listgenre);
			} catch (Exception error) {
				MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(this);
				errExcpError.addToLogFile(error, "AddEditBookGenre.onCreate", "");
				errExcpError = null;
			}
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(this);
			errExcpError.addToLogFile(	error, "AddEditBookGenre.onCreate",
												"opening database");
			errExcpError = null;
		}
	}// end onCreate
	
	private void fillspinnerbggenre() {
		try {
			boolean dbOpenResult = this.dbAppDbObj.openDbAdapter();
			if (dbOpenResult) {
				spinnerbggenrecursor = dbAppDbObj.fetchGenre();
				this.startManagingCursor(this.spinnerbggenrecursor);
				spinnerbggenrecursor.moveToFirst();
				spinnerbggenre = (Spinner) findViewById(R.id.spinnerbggenre);
				String[] from = new String[] { MyAppDbAdapter.KEY_GENRE,
						MyAppDbAdapter.KEY_ROWID };
				int[] to = new int[] { android.R.id.text1 };
				spinnerbggenreadapter = new SimpleCursorAdapter(
						AddEditBookGenre.this, android.R.layout.simple_spinner_item,
						this.spinnerbggenrecursor, from, to);
				spinnerbggenre.setAdapter(this.spinnerbggenreadapter);
				spinnerbggenreadapter.notifyDataSetChanged();
				spinnerbggenre
						.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
							
							public void onItemSelected(AdapterView<?> adapterView,
																View view, int i, long l) {
								aRowId = (int) adapterView.getItemIdAtPosition(i);
								btaddgenre.setEnabled(true);
							}
							
							public void onNothingSelected(AdapterView<?> adapterView) {
								aRowId = (int) 0;
								return;
							}
						});
			}
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					AddEditBookGenre.this);
			errExcpError.addToLogFile(	error,
												"AddEditBookGenre.fillspinnerbggenre ", "");
			errExcpError = null;
		}
	}
	
	private void fillGenrelist() {
		try {
			boolean dbOpenResult = this.dbAppDbObj.openDbAdapter();
			if (dbOpenResult) {
				/** Open the database and let mGenreCursor get the data */
				this.mGenreBookCursor = this.dbAppDbObj.fetchBookGenre(bRowId);
				this.startManagingCursor(this.mGenreBookCursor);
				String[] from = new String[] { MyAppDbAdapter.KEY_GENRE };
				int[] to = new int[] { R.id.txtgenre };
				listgenre = (ListView) findViewById(R.id.listgenre);
				this.genrebookRegister = new SimpleCursorAdapter(this,
						R.layout.genreentryrow, mGenreBookCursor, from, to);
				listgenre.setAdapter(genrebookRegister);
				listgenre.setFastScrollEnabled(true);
				listgenre.setTextFilterEnabled(false);
			}
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					AddEditBookGenre.this);
			errExcpError.addToLogFile(error, "AddEditBookGenre.fillGenre", "");
			errExcpError = null;
		}// end catch (Exception error)
	}// end fillGenre
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}// end onPrepareOptionsMenu
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
												ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.listview_context_menu, menu);
	}// end onCreateContextMenu
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.delete_entry_context_menu_option) {
			displayConfirmRequest(DELETE_CONFIRMATION_MESSAGE, item);
			return true;
		}
		return super.onContextItemSelected(item);
	}// end onContextItemSelected
	
	private void displayConfirmRequest(int id, final MenuItem item) {
		try {
			this.setBlContinue(false);
			switch (id) {
				case DELETE_CONFIRMATION_MESSAGE:
					new AlertDialog.Builder(AddEditBookGenre.this)
							.setIcon(R.drawable.alert_dialog_icon)
							.setTitle(R.string.deleteEntryYesNo_title)
							.setMessage(R.string.deleteEntryYesNo_message)
							.setPositiveButton(	R.string.alert_dialog_ok,
														new DialogInterface.OnClickListener() {
															
															public void onClick(	DialogInterface dialog,
																						int whichButton) {
																/* User clicked OK, delete Entry */
																AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
																		.getMenuInfo();
																try {
																	boolean dbOpenResult = AddEditBookGenre.this.dbAppDbObj
																			.openDbAdapter();
																	if (dbOpenResult) {
																		boolean blIsSuccessful = AddEditBookGenre.this.dbAppDbObj
																				.deleteBookGenreEntry(info.id);
																		boolean dbCloseResult = AddEditBookGenre.this.dbAppDbObj
																				.closeDbAdapter();
																		if (!dbCloseResult) throw new Exception(
																				"The database was not successfully closed.");
																		if (blIsSuccessful == false) {
																			AddEditBookGenre.objDisplayAlertClass = new MyDisplayAlertClass(
																					AddEditBookGenre.this,
																					new CustAlrtMsgOptnListener(
																							MessageCodes.ALERT_TYPE_MSG),
																					"Database Issue",
																					"There was an issue, and the register entry data was not deleted.");
																		}
																		AddEditBookGenre.this
																				.fillGenrelist();
																	}
																} catch (Exception error) {
																	MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
																			AddEditBookGenre.this);
																	errExcpError
																			.addToLogFile(	error,
																								"AddEditBookGenre.displayConfirmRequest.deleteGenreEntry",
																								"");
																	errExcpError = null;
																}
																// error)
															}
														})
							.setNegativeButton(	R.string.alert_dialog_cancel,
														new DialogInterface.OnClickListener() {
															
															public void onClick(	DialogInterface dialog,
																						int whichButton) {
																/* User clicked Cancel */
																setBlContinue(false);
															}
														}).show();
					return;
			}
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					AddEditBookGenre.this);
			errExcpError
					.addToLogFile(	error,
										"AddEditBookAddEditBookGenre.displayConfirmRequest",
										"");
			errExcpError = null;
		}
	}// end displayConfirmRequest
	
	protected boolean setBlContinue(boolean blContinue) {
		this.blContinue = blContinue;
		return blContinue;
	}
	
	/** @return the blContinue */
	protected boolean isBlContinue() {
		return blContinue;
	}
	
	/** onDestroy method Perform any final cleanup before an activity is
	 * destroyed. This can happen either because the activity is finishing
	 * (someone called finish() on it, or because the system is temporarily
	 * destroying this instance of the activity to save space. You can
	 * distinguish between these two scenarios with the isFinishing() method.
	 * Note: do not count on this method being called as a place for saving
	 * data! For example, if an activity is editing data in a content provider,
	 * those edits should be committed in either onPause() or
	 * onSaveInstanceState(Bundle), not here. This method is usually implemented
	 * to free resources like threads that are associated with an activity, so
	 * that a destroyed activity does not leave such things around while the
	 * rest of its application is still running. There are situations where the
	 * system will simply kill the activity's hosting process without calling
	 * this method (or any others) in it, so it should not be used to do things
	 * that are intended to remain around after the process goes away. Derived
	 * classes must call through to the super class's implementation of this
	 * method. If they do not, an exception will be thrown.
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
	 * @param KeyEvent
	 * event KeyEvent object
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
	 * @param KeyEvent
	 * event KeyEvent object
	 * @return true if the code completes execution, false otherwise */
	@Override
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
	 * @param Bundle
	 * savedInstanceState: the data most recently supplied in
	 * onSaveInstanceState(Bundle).
	 * @return void */
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		// Restore UI state from the savedInstanceState.
		// This bundle has also been passed to onCreate.
		try {
			APPGlobalVars.SCR_PAUSE_CTL = "";
			spinnerbggenre = (Spinner) findViewById(R.id.spinnerbggenre);
			aRowId = 0;
			if (savedInstanceState != null) {}
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					AddEditBookGenre.this);
			errExcpError.addToLogFile(	error,
												"AddEditBookGenre.onRestoreInstanceState",
												"");
			errExcpError = null;
		}
	}// end onRestoreInstanceState
	
	/** onResume: onResume: code for when the Activity resumes Called after
	 * onRestoreInstanceState(Bundle), onRestart(), or onPause(), for your
	 * activity to start interacting with the user. This is a good place to
	 * begin animations, open exclusive-access devices (such as the camera),
	 * etc. */
	@Override
	protected void onResume() {
		super.onResume();
		AddEditBookGenre.this.blSaveNew = false;
		this.dbAppDbObj = new MyAppDbSQL(this);
		this.fillGenrelist();
		this.fillspinnerbggenre();
	}// end onResume
	
	/** onSaveInstanceState method This method is called before an activity may
	 * be killed so that when it comes back some time in the future it can
	 * restore its state. If called, this method will occur before onStop().
	 * There are no guarantees about whether it will occur before or after
	 * onPause().
	 * @param Bundle
	 * savedInstanceState: Bundle in which to place your saved state.
	 * @return void */
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		// Save UI state changes to the savedInstanceState.
		// This bundle will be passed to onCreate if the process is
		// killed and restarted.
		try {
			APPGlobalVars.SCR_PAUSE_CTL = "SAVEINSTANCE";
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					AddEditBookGenre.this);
			errExcpError.addToLogFile(	error,
												"AddEditBookGenre.onSaveInstanceState", "");
			errExcpError = null;
		}
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
	
	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
	}
}