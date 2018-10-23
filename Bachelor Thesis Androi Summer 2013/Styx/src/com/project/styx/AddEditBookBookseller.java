
package com.project.styx;

/** Importing Android add-on elements */
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
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
public class AddEditBookBookseller extends Activity {
	
	/** Define variables to use in the activity **/
	private String newbookseller;
	private Integer bRowId;
	private int aRowId;
	EditText instantbookseller;
	private MyAppDbSQL dbAppDbObj;
	private ListView listbookseller;
	private Cursor mBookBooksellerCursor;
	private SimpleCursorAdapter booksellerbookRegister;
	private Spinner spinnerbsname;
	private TextView bsbook;
	private Cursor spinnerbsnamecursor;
	private Button btaddbookseller;
	private static final int DELETE_CONFIRMATION_MESSAGE = 1;
	private SimpleCursorAdapter spinnerbsnameadapter;
	protected double dblAcctStartBal = 0.00;
	private boolean blContinue = false;
	static final int DATE_DIALOG_ID = 1;
	private String bstitle;
	private static MyDisplayAlertClass objDisplayAlertClass;
	private boolean blSaveNew;
	private Button btnewbookseller;
	
	/** Declaration for must-required information **/
	/** * Event focus change listener Executes code when the focus is changed.
	 * Use it to get data when the user change the information from the edit
	 * text field */
	/** myAppCleanup method Sets object variables to null
	 * @return void */
	protected void myAppCleanup() {
		if (objDisplayAlertClass != null) {
			objDisplayAlertClass.cleanUpClassVars();
			objDisplayAlertClass = null;
		}
		if (this.spinnerbsnamecursor != null) {
			this.spinnerbsnamecursor.close();
			this.spinnerbsnamecursor = null;
		}
		if (this.spinnerbsnameadapter != null) {
			this.spinnerbsnameadapter.getCursor().close();
			this.spinnerbsnameadapter = null;
		}
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			extras.clear();
			extras = null;
		}
	}// end myAppCleanup
	
	/** myCancelMethod: Cancel code Discards changes and returns to the calling
	 * screen with status 'RESULT_CANCELED' */
	/** onActivityResult method Called when an activity you launched exits,
	 * giving you the requestCode you started it with, the resultCode it
	 * returned, and any additional data from it. The resultCode will be
	 * RESULT_CANCELED if the activity explicitly returned that, didn't return
	 * any result, or crashed during its operation. You will receive this call
	 * immediately before onResume() when your activity is re-starting.
	 * @param int requestCode The integer request code originally supplied to
	 * startActivityForResult(), allowing you to identify who this result
	 * came from.
	 * @param int resultCode The integer result code returned by the child
	 * activity through its setResult().
	 * @param Intent
	 * intent An Intent, which can return result data to the caller
	 * (various data can be attached to Intent "extras"). requestCode
	 * values: ACTIVITY_CREATE, ACTIVITY_EDIT resultCode values:
	 * RESULT_OK, ACTIVITY_FINISH, RESULT_CANCELED
	 * @return void */
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
												Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		try {
			AddEditBookBookseller.this.blSaveNew = false;
			this.spinnerbsname = (Spinner) findViewById(R.id.spinnerbsname);
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
					AddEditBookBookseller.this);
			errExcpError.addToLogFile(	error,
												"AddEditBookBookseller.onActivityResult",
												"");
			errExcpError = null;
		}// end try/catch (Exception error)
	}// end onActivityResult
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_edit_book_bookseller);
		fillspinnerbsname();
		fillBooksellerlist();
		try {
			try {
				this.spinnerbsname = (Spinner) findViewById(R.id.spinnerbsname);
				this.bsbook = (TextView) findViewById(R.id.bsbook);
				this.btaddbookseller = (Button) findViewById(R.id.btaddbookseller);
				this.btnewbookseller = (Button) findViewById(R.id.btnewbookseller);
				this.listbookseller = (ListView) findViewById(R.id.listbookseller);
				bRowId = 0;
				Bundle extras = getIntent().getExtras();
				if (extras != null) {
					bRowId = (int) extras.getLong(MyAppDbAdapter.KEY_ROWID);
					bstitle = extras.getString(MyAppDbAdapter.KEY_TITLE);
				}
				/*** This code set event text focus change listeners */
				this.bsbook.setText(bstitle);
				btaddbookseller.setOnClickListener(new View.OnClickListener() {
					
					private String x;
					
					@Override
					public void onClick(View v) {
						dbAppDbObj.createBookBooksellerEntry(bRowId, aRowId);
						x = "The bookseller has been register!";
						Toast.makeText(getApplicationContext(), x, Toast.LENGTH_SHORT)
								.show();
						fillBooksellerlist();
					}
				});
				btnewbookseller.setOnClickListener(new View.OnClickListener() {
					
					private TextView instantbookseller;
					private String y;
					
					@Override
					public void onClick(View v) {
						this.instantbookseller = (EditText) findViewById(R.id.instantbookseller);
						newbookseller = this.instantbookseller.getText().toString();
						if (this.instantbookseller != null
								&& !newbookseller.contentEquals("")) {
							dbAppDbObj.createAuthorInstantEntry(newbookseller);
							y = "New bookseller has been added! Please update later";
							Toast.makeText(getApplicationContext(), y,
												Toast.LENGTH_SHORT).show();
							instantbookseller.setText("");
							InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
							imm.hideSoftInputFromWindow(instantbookseller
									.getWindowToken(), 0);
						} else {
							y = "New bookseller cannot be add ! Please input the bookseller name !";
							Toast.makeText(getApplicationContext(), y,
												Toast.LENGTH_SHORT).show();
						}
						fillspinnerbsname();
					}
				});
				registerForContextMenu(listbookseller);
			} catch (Exception error) {
				MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(this);
				errExcpError.addToLogFile(	error, "AddEditBookBookseller.onCreate",
													"");
				errExcpError = null;
			}
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(this);
			errExcpError.addToLogFile(	error, "AddEditBookBookseller.onCreate",
												"opening database");
			errExcpError = null;
		}
	}// end onCreate
	
	@SuppressWarnings("deprecation")
	private void fillspinnerbsname() {
		try {
			boolean dbOpenResult = this.dbAppDbObj.openDbAdapter();
			if (dbOpenResult) {
				spinnerbsnamecursor = dbAppDbObj.fetchBookseller(0);
				this.startManagingCursor(this.spinnerbsnamecursor);
				spinnerbsnamecursor.moveToFirst();
				spinnerbsname = (Spinner) findViewById(R.id.spinnerbsname);
				String[] from = new String[] { MyAppDbAdapter.KEY_BOOKSELLER,
						MyAppDbAdapter.KEY_ROWID };
				int[] to = new int[] { android.R.id.text1 };
				spinnerbsnameadapter = new SimpleCursorAdapter(
						AddEditBookBookseller.this,
						android.R.layout.simple_spinner_item,
						this.spinnerbsnamecursor, from, to);
				spinnerbsname
						.setAdapter(this.spinnerbsnameadapter);
				spinnerbsnameadapter.notifyDataSetChanged();
				spinnerbsname
						.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
							
							public void onItemSelected(AdapterView<?> adapterView,
																View view, int i, long l) {
								aRowId = (int) adapterView.getItemIdAtPosition(i);
								// Your code here
							}
							
							public void onNothingSelected(AdapterView<?> adapterView) {
								aRowId = (int) 0;
								return;
							}
						});
			}
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					AddEditBookBookseller.this);
			errExcpError
					.addToLogFile(	error,
										"AddEditBookBookseller.fillspinnerbs",
										"");
			errExcpError = null;
		}
	}
	
	@SuppressWarnings("deprecation")
	private void fillBooksellerlist() {
		try {
			boolean dbOpenResult = this.dbAppDbObj.openDbAdapter();
			if (dbOpenResult) {
				this.mBookBooksellerCursor = this.dbAppDbObj
						.fetchBookBookseller(bRowId);
				this.startManagingCursor(this.mBookBooksellerCursor);
				String[] from = new String[] { MyAppDbAdapter.KEY_BOOKSELLER };
				// and an array of the fields we want to bind those fields to
				int[] to = new int[] { R.id.bookseller };
				listbookseller = (ListView) findViewById(R.id.listbookseller);
				this.booksellerbookRegister = new SimpleCursorAdapter(this,
						R.layout.booksellerentryrow, mBookBooksellerCursor, from, to);
				listbookseller.setAdapter(booksellerbookRegister);
				listbookseller.setFastScrollEnabled(true);
				listbookseller.setTextFilterEnabled(false);
			}
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					AddEditBookBookseller.this);
			errExcpError.addToLogFile(	error,
												"AddEditBookBookseller.fillBookseller", "");
			errExcpError = null;
		}// end catch (Exception error)
	}// end fillBookseller
	
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
					new AlertDialog.Builder(AddEditBookBookseller.this)
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
																	boolean dbOpenResult = AddEditBookBookseller.this.dbAppDbObj
																			.openDbAdapter();
																	if (dbOpenResult) {
																		boolean blIsSuccessful = AddEditBookBookseller.this.dbAppDbObj
																				.deleteBookBooksellerEntry(info.id);
																		boolean dbCloseResult = AddEditBookBookseller.this.dbAppDbObj
																				.closeDbAdapter();
																		if (!dbCloseResult) throw new Exception(
																				"The database was not successfully closed.");
																		if (blIsSuccessful == false) {
																			AddEditBookBookseller.objDisplayAlertClass = new MyDisplayAlertClass(
																					AddEditBookBookseller.this,
																					new CustAlrtMsgOptnListener(
																							MessageCodes.ALERT_TYPE_MSG),
																					"Database Issue",
																					"There was an issue, and the register entry data was not deleted.");
																		}// end if (blIsSuccessful
																			// ==
																			// false)
																		AddEditBookBookseller.this
																				.fillBooksellerlist();
																	}
																} catch (Exception error) {
																	MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
																			AddEditBookBookseller.this);
																	errExcpError
																			.addToLogFile(	error,
																								"AddEditBookBookseller.displayConfirmRequest.deleteBooksellerEntry",
																								"");
																	errExcpError = null;
																}// end try/catch (Exception
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
		}// end try
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					AddEditBookBookseller.this);
			errExcpError
					.addToLogFile(	error,
										"AddEditBookAddEditBookBookseller.displayConfirmRequest",
										"");
			errExcpError = null;
		}// end try/catch (Exception error)
	}// end displayConfirmRequest
	
	protected boolean setBlContinue(boolean blContinue) {
		this.blContinue = blContinue;
		return blContinue;
	}
	
	/** @return the blContinue */
	protected boolean isBlContinue() {
		return blContinue;
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
				// cleanup objects
				bundle = null;
				mIntent = null;
				finish();
				return true;
			default:
				return false;
		}
	}// end onKeyDown
	
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
		// Restore UI state from the savedInstanceState.
		// This bundle has also been passed to onCreate.
		try {
			APPGlobalVars.SCR_PAUSE_CTL = "";
			spinnerbsname = (Spinner) findViewById(R.id.spinnerbsname);
			aRowId = 0;
			if (savedInstanceState != null) {}
		}// end try
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					AddEditBookBookseller.this);
			errExcpError
					.addToLogFile(	error,
										"AddEditBookBookseller.onRestoreInstanceState",
										"");
			errExcpError = null;
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		AddEditBookBookseller.this.blSaveNew = false;
		this.dbAppDbObj = new MyAppDbSQL(this);
		this.fillBooksellerlist();
		this.fillspinnerbsname();
	}// end onResume
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		try {
			APPGlobalVars.SCR_PAUSE_CTL = "SAVEINSTANCE";
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					AddEditBookBookseller.this);
			errExcpError
					.addToLogFile(	error,
										"AddEditBookBookseller.onSaveInstanceState", "");
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