
package com.project.styx;

/** Import needed element */
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import com.project.styx.CustAlrtMsgOptnListener.MessageCodes;
/** Class for displaying a list of data, activity screen and options. */
public class BookBookseller_List extends ListActivity {
	
	/* Global Variable Declarations */
	private static final int intDefaultSortOption = -1;
	private static int intSortOption = BookBookseller_List.intDefaultSortOption;
	private static final int ACTIVITY_CREATE = 0;
	private static final int ACTIVITY_EDIT = 1;
	
	int textlength = 0;
	ListView booklist;
	private MyAppDbSQL dbAppDbObj;
	private Cursor mEntryCursor;
	private SimpleCursorAdapter bookRegister;
	protected double dblAcctStartBal = 0.00;
	private boolean blContinue = false;
	private static final int DELETE_CONFIRMATION_MESSAGE = 1;
	private static final int CLEAR_ENTRIES_CONFIRMATION_MESSAGE = 2;
	private int intEntryListPosition = -1;
	private static MyDisplayAlertClass objDisplayAlertClass;
	private Cursor cursorTableQuery = null;
	private boolean blSaveNew = false;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.booklist);
		fillData();
		try {
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
				String strQuitStatus = extras.getString("QUITTING");
				if (strQuitStatus.equals("TRUE")) {
					APPGlobalVars.SCR_PAUSE_CTL = "QUIT";
					this.finish();
				}
			}
			extras = null;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(this);
			errExcpError.addToLogFile(error, "BookBookseller_List.onCreate", "");
			errExcpError = null;
		}
	}
	
	/** fillBook: Get all of the rows from the database and create the item list
	 * @return void */
	private void fillData() {
		try {
			boolean dbOpenResult = this.dbAppDbObj.openDbAdapter();
			if (dbOpenResult) {
				/** Open the database and let mEntryCursor get the data */
				mEntryCursor = dbAppDbObj
						.fetchBook(BookBookseller_List.intSortOption);
				startManagingCursor(mEntryCursor);
				/** Create an array to specify the fields we want to display in
				 * the list and an array of the fields we want to bind those
				 * fields to */
				String[] from = new String[] { MyAppDbAdapter.KEY_TITLE };
				int[] to = new int[] { R.id.Booktitle };
				booklist = (ListView) findViewById(android.R.id.list);
				bookRegister = new SimpleCursorAdapter(this, R.layout.bookentryrow,
						mEntryCursor, from, to);
				bookRegister.setFilterQueryProvider(new FilterQueryProvider() {
					
					public Cursor runQuery(CharSequence constraint) {
						String partialItemName = null;
						if (constraint != null) {
							partialItemName = constraint.toString();
						}
						return dbAppDbObj.fetchBookByName(partialItemName);
					}
				});
				booklist.setAdapter(bookRegister);
				booklist.setFastScrollEnabled(true);
				booklist.setTextFilterEnabled(true);
				/** This part of the code is for searching function First, get
				 * the word input by the user */
				EditText Search = (EditText) findViewById(R.id.Search);
				/** Get the event so that the input word is send to filter */
				Search.addTextChangedListener(new TextWatcher() {
					
					public void afterTextChanged(Editable s) {
					}
					
					public void onTextChanged(CharSequence s, int start, int before,
														int count) {
						if (bookRegister != null) {
							bookRegister.getFilter().filter(s.toString());
							booklist.setAdapter(bookRegister);
						}
					}
					
					public void beforeTextChanged(CharSequence s, int start,
															int count, int after) {
					}
				});
				/** Let the adapter change according to the filter word */
			}
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					BookBookseller_List.this);
			errExcpError.addToLogFile(error, "BookBookseller_List.fillData", "");
			errExcpError = null;
		}
	}
	
	/** onCreateOptionsMenu method
	 * Initialize the contents of the Activity's standard options menu. You
	 * should place your menu items in to menu.
	 * This is only called once, the first time the options menu is displayed.
	 * To update the menu every time it is displayed, see
	 * onPrepareOptionsMenu(Menu).
	 * @param menu
	 * The options menu in which you place your items.
	 * @return Must return true for the menu to be displayed; if you return
	 * false it will not be shown. */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.sortexportlist_menu, menu);
		return true;
	}
	
	/** onMenuItemSelected method
	 * onMenuItemSelected method Executes code per the menu item selected on the
	 * menu options that appears when the menu button is pressed
	 * @param int featureID: The panel that the menu is in.
	 * @param MenuItem
	 * item: The menu item that was selected.
	 * @return Return true to finish processing of selection, or false to
	 * perform the normal menu handling (calling its Runnable or sending
	 * a Message to its target Handler). */
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		try {
			int itemId = item.getItemId();
			{
				if (itemId == R.id.sorting_options) {
					Intent prefsIntent = new Intent(this, EditSortPreferences.class);
					prefsIntent.putExtra("PREFERENCE_FILE", R.xml.book_view_prefs);
					prefsIntent.putExtra("PREFERENCE_KEY",
												getString(R.string.BookSortOpt));
					startActivity(prefsIntent);
				} else if (itemId == R.id.export_list) {
					try {
						if (this.dbAppDbObj != null) {
							boolean dbOpenResult = this.dbAppDbObj.openDbAdapter();
							if (dbOpenResult) {
								cursorTableQuery = this.dbAppDbObj
										.exportTableQuery(MyAppDbAdapter.MY_BOOK_BOOKSELLER_DB_TABLE,
																MyAppDbAdapter.KEY_BS_BOOKID
																		+ ","
																		+ MyAppDbAdapter.KEY_BS_BOOKSELLERID
																		+ " DESC");
								this.startManagingCursor(this.mEntryCursor);
								boolean dbCloseResult = this.dbAppDbObj
										.closeDbAdapter();
								if (!dbCloseResult) throw new Exception(
										"The database was not successfully closed.");
								DBTableExport objDBTableExport = new DBTableExport(
										BookBookseller_List.this);
								objDBTableExport
										.exportAsCSVFile(	cursorTableQuery,
																MyAppDbAdapter.MY_BOOK_BOOKSELLER_DB_TABLE);
								BookBookseller_List.objDisplayAlertClass = new MyDisplayAlertClass(
										BookBookseller_List.this,
										new CustAlrtMsgOptnListener(
												MessageCodes.ALERT_TYPE_MSG),
										"Table Export", "Export Completed");
								objDBTableExport = null;
							}
						}
					} catch (Exception error) {
						MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
								this);
						errExcpError
								.addToLogFile(	error,
													"BookBookseller_List.onMenuItemSelected",
													"attempting to export the "
															+ MyAppDbAdapter.MY_BOOK_BOOKSELLER_DB_TABLE
															+ " table.");
						errExcpError = null;
						return false;
					}
				} else if (itemId == R.id.quit) {
					APPGlobalVars.SCR_PAUSE_CTL = "QUIT";
					this.finish();
					return true;
				}
				return super.onMenuItemSelected(featureId, item);
			}
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(this);
			errExcpError.addToLogFile(	error,
												"BookBookseller_List.onMenuItemSelected",
												"switch statement exception. ");
			errExcpError = null;
			return false;
		}
	}
	
	/** onPrepareOptionsMenu method
	 * Prepare the Screen's standard options menu to be displayed.
	 * This is called right before the menu is shown, every time it is shown.
	 * You can use this method to efficiently enable/disable items or otherwise
	 * dynamically modify the contents.
	 * The default implementation updates the system menu items based on the
	 * activity's state.
	 * Deriving classes should always call through to the base class
	 * implementation.
	 * @param Menu
	 * menu The options menu as last shown or first initialized by
	 * onCreateOptionsMenu().
	 * @return Must return true for the menu to be displayed; if it returns
	 * false it will not be shown. */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}
	
	/** onCreateContextMenu method
	 * Called when a context menu for the view is about to be shown.
	 * Unlike onCreateOptionsMenu(Menu), this will be called every time the
	 * context menu is about to be shown and should be populated for the view
	 * (or item inside the view for AdapterView subclasses, this can be found in
	 * the menuInfo)).
	 * Use onContextItemSelected(android.view.MenuItem) to know when an item has
	 * been selected.
	 * It is not safe to hold onto the context menu after this method returns.
	 * Called when the context menu for this view is being built.
	 * It is not safe to hold onto the menu after this method returns.
	 * @param Menu
	 * menu The context menu that is being built.
	 * @param View
	 * v The view for which the context menu is being built
	 * @param ContextMenuInfo
	 * menuInfo Extra information about the item for which the
	 * context menu should be shown. This information will vary
	 * depending on the class of v.
	 * @return void */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
												ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.listview_context_menu, menu);
	}
	
	/** onContextItemSelected method
	 * This hook is called whenever an item in a context menu is selected. The
	 * default implementation simply returns false to have the normal processing
	 * happen (calling the item's Runnable or sending a message to its Handler
	 * as appropriate).
	 * You can use this method for any items for which you would like to do
	 * processing without those other facilities.
	 * Use getMenuInfo() to get extra information set by the View that added
	 * this menu item.
	 * Derived classes should call through to the base class for it to perform
	 * the default menu handling.
	 * @param MenuItem
	 * item The context menu item that was selected.
	 * @return boolean Return false to allow normal context menu processing to
	 * proceed, true to consume it here. */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.delete_entry_context_menu_option) {
			displayConfirmRequest(DELETE_CONFIRMATION_MESSAGE, item);
			return true;
		}
		return super.onContextItemSelected(item);
	}
	
	/** onListItemClick method
	 * This method will be called when an item in the list is selected.
	 * Subclasses should override. Subclasses can call
	 * getListView().getItemAtPosition(position) if they need to access the data
	 * associated with the selected item.
	 * @param ListView
	 * l The ListView where the click happened.
	 * @param View
	 * v The view that was clicked within the ListView.
	 * @param int position The position of the view in the list.
	 * @param long id The row id of the item that was clicked.
	 * @return void */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		try {
			if (this.mEntryCursor != null) {
				this.mEntryCursor = ((SimpleCursorAdapter) l.getAdapter())
						.getCursor();
				this.mEntryCursor.moveToPosition(position);
				Intent i = new Intent(this, AddEditBookBookseller.class);
				i.putExtra(MyAppDbAdapter.KEY_ROWID, id);
				i.putExtra(MyAppDbAdapter.KEY_TITLE, this.mEntryCursor
						.getString(this.mEntryCursor
								.getColumnIndexOrThrow(MyAppDbAdapter.KEY_TITLE)));
				this.intEntryListPosition = position;
				i.putExtra("SHOW_SAVENEW", true);
				startActivityForResult(i, ACTIVITY_EDIT);
			}
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(this);
			errExcpError.addToLogFile(	error,
												"BookBookseller_List.onListItemClick", "");
			errExcpError = null;
		}
	}
	
	/** onActivityResult method
	 * Called when an activity you launched exits, giving you the requestCode
	 * you started it with, the resultCode it returned, and any additional data
	 * from it.
	 * The resultCode will be RESULT_CANCELED if the activity explicitly
	 * returned that, didn't return any result, or crashed during its operation.
	 * You will receive this call immediately before onResume() when your
	 * activity is re-starting.
	 * @param int requestCode The integer request code originally supplied to
	 * startActivityForResult(), allowing you to identify who this result
	 * came from.
	 * @param int resultCode The integer result code returned by the child
	 * activity through its setResult().
	 * @param Intent
	 * intent An Intent, which can return result data to the caller
	 * (various data can be attached to Intent "extras").
	 * requestCode values: ACTIVITY_CREATE, ACTIVITY_EDIT
	 * resultCode values: RESULT_OK, ACTIVITY_FINISH, RESULT_CANCELED
	 * @return void */
	/** onKeyDown method
	 * Executes code depending on what keyCode is pressed.
	 * @param int keyCode
	 * @param KeyEvent
	 * event KeyEvent object
	 * @return true if the code completes execution, false otherwise */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				finish();
				return true;
			default:
				return false;
		}
	}
	
	/** myAppCleanup method
	 * Sets object variables to null
	 * @return void */
	protected void myAppCleanup() {
		try {
			/* Set object variables to null */
			if (BookBookseller_List.objDisplayAlertClass != null) {
				BookBookseller_List.objDisplayAlertClass.cleanUpClassVars();
				BookBookseller_List.objDisplayAlertClass = null;
			}
			if (this.mEntryCursor != null) {
				this.mEntryCursor.close();
				this.mEntryCursor = null;
			}
			if (this.bookRegister != null) {
				this.bookRegister.getCursor().close();
				this.bookRegister = null;
			}
			if (this.dbAppDbObj != null) {
				this.dbAppDbObj = null;
			}
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					BookBookseller_List.this);
			errExcpError.addToLogFile(	error, "BookBookseller_List.myAppCleanup",
												"no prompt");
			errExcpError = null;
		}
	}
	
	/** getSortOption method
	 * Sets the sorting option for how the data is sorted
	 * @return void */
	protected static void getSortOption(Context ctxContext) {
		try {
			GetSortOptions objGetSortOptions = new GetSortOptions(ctxContext,
					"BookBookseller_List", "BookSortOpt");
			BookBookseller_List.intSortOption = objGetSortOptions
					.getIntSortOption("");
			objGetSortOptions = null;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "BookBookseller_List.getSortOption",
										"An Error Occured, Sort Option set to default, sorted by rowID");
			errExcpError = null;
			BookBookseller_List.intSortOption = BookBookseller_List.intDefaultSortOption;
		}
	}
	
	/** Called after onRestoreInstanceState(Bundle), onRestart(), or onPause(),
	 * for your activity to start interacting with the user.
	 * This is a good place to begin animations, open exclusive-access devices
	 * (such as the camera), etc.
	 * the data is refreshed */
	@Override
	protected void onResume() {
		/* query sorting options */
		super.onResume();
		try {
			if (this.blSaveNew == true) {
				this.blSaveNew = false;
				Intent intentInsrt = new Intent(BookBookseller_List.this,
						AddEditBookBookseller.class);
				intentInsrt.putExtra("SHOW_SAVENEW", true);
				startActivityForResult(intentInsrt, ACTIVITY_CREATE);
				return;
			}
			this.dbAppDbObj = new MyAppDbSQL(this);
			BookBookseller_List.getSortOption(BookBookseller_List.this);
			this.fillData();
			this.registerForContextMenu(this.getListView());
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					BookBookseller_List.this);
			errExcpError.addToLogFile(error, "BookBookseller_List.resumeData", "");
			errExcpError = null;
		}
	}
	
	/** onPause method
	 * Called as part of the activity lifecycle when an activity is going into
	 * the background, but has not (yet) been killed. The counterpart to
	 * onResume().
	 * This callback is mostly used for saving any persistent state the activity
	 * is editing, to present a "edit in place" model to the user and making
	 * sure nothing is lost if there are not enough resources to start the new
	 * activity without first killing this one.
	 * This is also a good place to do things like stop animations and other
	 * things that consume a noticeable mount of CPU in order to make the switch
	 * to the next activity as fast as possible, or to close resources that are
	 * exclusive access such as the camera.
	 * Checks the current state. If in "QUIT" state, then the cleanup and finish
	 * code is executed.
	 * "SAVEINSTANCE" is used for when the screen orientation is changed.
	 * @return void */
	@Override
	protected void onPause() {
		if (APPGlobalVars.SCR_PAUSE_CTL != null
				&& APPGlobalVars.SCR_PAUSE_CTL.equals("QUIT")) {
			APPGlobalVars.SCR_PAUSE_CTL = "";
			finish();
		}
		super.onPause();
	}
	
	/** displayConfirmRequest method
	 * Prompts user to confirm an action.
	 * Action types include: deleting an entry or clearing all entries.
	 * @param int id Row ID of the entry to delete
	 * @param MenuItem
	 * The context menu item that was selected.
	 * @return void */
	private void displayConfirmRequest(int id, final MenuItem item) {
		try {
			this.setBlContinue(false);
			switch (id) {
				case DELETE_CONFIRMATION_MESSAGE:
					new AlertDialog.Builder(BookBookseller_List.this)
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
																	boolean dbOpenResult = BookBookseller_List.this.dbAppDbObj
																			.openDbAdapter();
																	if (dbOpenResult) {
																		boolean blIsSuccessful = BookBookseller_List.this.dbAppDbObj
																				.deleteBookEntry(info.id);
																		boolean dbCloseResult = BookBookseller_List.this.dbAppDbObj
																				.closeDbAdapter();
																		if (!dbCloseResult) throw new Exception(
																				"The database was not successfully closed.");
																		if (blIsSuccessful == false) {
																			BookBookseller_List.objDisplayAlertClass = new MyDisplayAlertClass(
																					BookBookseller_List.this,
																					new CustAlrtMsgOptnListener(
																							MessageCodes.ALERT_TYPE_MSG),
																					"Database Issue",
																					"There was an issue, and the register entry data was not deleted.");
																		}
																		BookBookseller_List.this
																				.fillData();
																	}
																} catch (Exception error) {
																	MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
																			BookBookseller_List.this);
																	errExcpError
																			.addToLogFile(	error,
																								"BookBookseller_List.displayConfirmRequest.deleteBookEntry",
																								"");
																	errExcpError = null;
																}
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
				case CLEAR_ENTRIES_CONFIRMATION_MESSAGE:
					new AlertDialog.Builder(BookBookseller_List.this)
							.setIcon(R.drawable.alert_dialog_icon)
							.setTitle(R.string.clearYesNo_title)
							.setMessage(R.string.clearYesNo_message)
							.setPositiveButton(	R.string.alert_dialog_ok,
														new DialogInterface.OnClickListener() {
															
															public void onClick(	DialogInterface dialog,
																						int whichButton) {
																/* User clicked OK, clear
																 * entries */
																try {
																	boolean dbOpenResult = BookBookseller_List.this.dbAppDbObj
																			.openDbAdapter();
																	if (dbOpenResult) {
																		boolean blIsSuccessful = BookBookseller_List.this.dbAppDbObj
																				.deleteBookEntries();
																		boolean dbCloseResult = BookBookseller_List.this.dbAppDbObj
																				.closeDbAdapter();
																		if (!dbCloseResult) throw new Exception(
																				"The database was not successfully closed.");
																		if (blIsSuccessful == false) {
																			BookBookseller_List.objDisplayAlertClass = new MyDisplayAlertClass(
																					BookBookseller_List.this,
																					new CustAlrtMsgOptnListener(
																							MessageCodes.ALERT_TYPE_MSG),
																					"Database Issue",
																					"There was an issue, and the register entries were not deleted.");
																		}
																		BookBookseller_List.this
																				.fillData();
																	}
																} catch (Exception error) {
																	MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
																			BookBookseller_List.this);
																	errExcpError
																			.addToLogFile(	error,
																								"BookBookseller_List.displayConfirmRequest.deleteBookEntries",
																								"");
																	errExcpError = null;
																}
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
					BookBookseller_List.this);
			errExcpError
					.addToLogFile(	error,
										"BookBookseller_List.displayConfirmRequest", "");
			errExcpError = null;
		}
	}
	
	/** @param blContinue
	 * the blContinue to set */
	protected boolean setBlContinue(boolean blContinue) {
		this.blContinue = blContinue;
		return blContinue;
	}
	
	/** @return the blContinue */
	protected boolean isBlContinue() {
		return blContinue;
	}
	
	/** onSaveInstanceState method
	 * This method is called before an activity may be killed so that when it
	 * comes back some time in the future it can restore its state.
	 * If called, this method will occur before onStop(). There are no
	 * guarantees about whether it will occur before or after onPause().
	 * @param Bundle
	 * savedInstanceState: Bundle in which to place your saved state.
	 * @return void */
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		try {
			this.intEntryListPosition = this.getListView()
					.getSelectedItemPosition();
			savedInstanceState.putInt("LISTPOSITION", this.intEntryListPosition);
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					BookBookseller_List.this);
			errExcpError.addToLogFile(	error,
												"BookBookseller_List.onSaveInstanceState",
												"no prompt");
			errExcpError = null;
		}
	}
	
	/** onRestoreInstanceState method
	 * This method is called after onStart() when the activity is being
	 * re-initialized from a previously saved state, given here in state.
	 * Most implementations will simply use onCreate(Bundle) to restore their
	 * state, but it is sometimes convenient to do it here after all of the
	 * initialization has been done or to allow subclasses to decide whether to
	 * use your default implementation.
	 * The default implementation of this method performs a restore of any view
	 * state that had previously been frozen by onSaveInstanceState(Bundle).
	 * @param Bundle
	 * savedInstanceState: the data most recently supplied in
	 * onSaveInstanceState(Bundle).
	 * @return void */
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		try {
			APPGlobalVars.SCR_PAUSE_CTL = "";
			if (savedInstanceState != null) {
				this.intEntryListPosition = savedInstanceState
						.getInt("LISTPOSITION");
			}
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					BookBookseller_List.this);
			errExcpError
					.addToLogFile(	error,
										"BookBookseller_List.onRestoreInstanceState",
										"no prompt");
			errExcpError = null;
		}
	}
	
	/** onDestroy method
	 * Perform any final cleanup before an activity is destroyed.
	 * This can happen either because the activity is finishing (someone called
	 * finish() on it, or because the system is temporarily destroying this
	 * instance of the activity to save space.
	 * You can distinguish between these two scenarios with the isFinishing()
	 * method.
	 * Note: do not count on this method being called as a place for saving
	 * data! For example, if an activity is editing data in a content provider,
	 * those edits should be committed in either onPause() or
	 * onSaveInstanceState(Bundle), not here.
	 * This method is usually implemented to free resources like threads that
	 * are associated with an activity, so that a destroyed activity does not
	 * leave such things around while the rest of its application is still
	 * running.
	 * There are situations where the system will simply kill the activity's
	 * hosting process without calling this method (or any others) in it, so it
	 * should not be used to do things that are intended to remain around after
	 * the process goes away.
	 * Derived classes must call through to the super class's implementation of
	 * this method. If they do not, an exception will be thrown.
	 * @return void */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.myAppCleanup();
	}
	
	/** setSelection method
	 * Set the currently selected list item to the specified position with the
	 * adapter's data
	 * @param int position The position value that the list will change focus
	 * to.
	 * @return void */
	@Override
	public void setSelection(int position) {
		super.setSelection(position);
	}
	
	/** onStop method
	 * Called when you are no longer visible to the user. You will next receive
	 * either onRestart(), onDestroy(), or nothing, depending on later user
	 * activity. Note that this method may never be called, in low memory
	 * situations where the system does not have enough memory to keep your
	 * activity's process running after its onPause() method is called. Derived
	 * classes must call through to the super class's implementation of this
	 * method. If they do not, an exception will be thrown.
	 * * @return void */
	@Override
	protected void onStop() {
		super.onStop();
		/* Set object variables to null */
		this.myAppCleanup();
	}
}
