package com.project.styx;

/**Import needed element*/

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.project.styx.CustAlrtMsgOptnListener.MessageCodes;

/**
 * Class for displaying a list of data, activity screen and options.
 */
public class Genre_List extends ListActivity {
	/* Global Variable Declarations */
	private static final int ACTIVITY_CREATE = 0;
	private static final int ACTIVITY_EDIT = 1;
	private static final int ACTIVITY_FINISH = -2;
	int textlength = 0;
	private MyAppDbSQL dbAppDbObj;
	ListView av;
	private SimpleCursorAdapter listRegister;
	protected double dblAcctStartBal = 0.00;
	private boolean blContinue = false;
	private static final int DELETE_CONFIRMATION_MESSAGE = 1;
	private static final int CLEAR_ENTRIES_CONFIRMATION_MESSAGE = 2;
	private int intEntryListPosition = -1;
	private static MyDisplayAlertClass objDisplayAlertClass;
	private Cursor cursorTableQuery = null;
	private Cursor mEntryCursor;
	private boolean blSaveNew = false;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.genrelist);
		fillData();
		try {
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
				String strQuitStatus = extras.getString("QUITTING");
				if (strQuitStatus.equals("TRUE")) {
					// exit application
					APPGlobalVars.SCR_PAUSE_CTL = "QUIT";
					this.finish();
				}// end if (strQuitStatus.equals("TRUE"))
			}// end if (extras != null)
			extras = null;
		}// end try statements
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(this);
			errExcpError.addToLogFile(error, "Genre_List.onCreate", "");
			errExcpError = null;
		}// end try/catch (Exception error)
	}// end onCreate

	/**
	 * fillData: Get all of the rows from the database and create the item list
	 * 
	 * @return void
	 * 
	 */
	private void fillData() {
		// get a cursor of the entries number
		try {
			boolean dbOpenResult = this.dbAppDbObj.openDbAdapter();
			if (dbOpenResult) {
				/** Open the database and let mEntryCursor get the data */
				mEntryCursor = dbAppDbObj.fetchGenre();
				startManagingCursor(mEntryCursor);
				/**
				 * Create an array to specify the fields we want to display in
				 * the list and an array of the fields we want to bind those
				 * fields to
				 */
				String[] from = new String[] { MyAppDbAdapter.KEY_GENRE,
						MyAppDbAdapter.KEY_GENRE_INFO_1 };
				int[] to = new int[] { R.id.txtgenre };
				av = (ListView) findViewById(android.R.id.list);
				// Now create a simple cursor adapter and set it to display
				listRegister = new SimpleCursorAdapter(this,
						R.layout.genreentryrow, mEntryCursor, from, to);
				av.setAdapter(listRegister);
				av.setFastScrollEnabled(true);
				av.setTextFilterEnabled(true);

			} // end if
		}// end try
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					Genre_List.this);
			errExcpError.addToLogFile(error, "Genre_List.fillData", "");
			errExcpError = null;
		}// end catch (Exception error)
	}// end fillData

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
	 * @param menu
	 *            The options menu in which you place your items.
	 * 
	 * @return Must return true for the menu to be displayed; if you return
	 *         false it will not be shown.
	 * 
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.unsortlist_menu, menu);
		// inflater.inflate(R.menu.reconcile_menu, menu);
		return true;
	}// end onCreateOptionsMenu

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
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		try {
			int itemId = item.getItemId();
			{
				if (itemId == R.id.new_entry_menu_option) {
					Intent intentInsrt = new Intent(Genre_List.this,
							AddEditGenre.class);
					intentInsrt.putExtra("SHOW_SAVENEW", true);
					startActivityForResult(intentInsrt, ACTIVITY_CREATE);
				} else if (itemId == R.id.clear_screen_menu_option) {
					// clear the entries
					displayConfirmRequest(CLEAR_ENTRIES_CONFIRMATION_MESSAGE,
							item);
				} else if (itemId == R.id.export_list) {
					// export list
					try {
						if (this.dbAppDbObj != null) {
							boolean dbOpenResult = this.dbAppDbObj
									.openDbAdapter();
							if (dbOpenResult) {
								cursorTableQuery = this.dbAppDbObj
										.exportTableQuery(
												MyAppDbAdapter.MY_GENRE_DB_TABLE,
												MyAppDbAdapter.KEY_GENRE
														+ ","
														+ MyAppDbAdapter.KEY_GENRE_INFO_1
														+ " DESC");
								this.startManagingCursor(this.mEntryCursor);
								boolean dbCloseResult = this.dbAppDbObj
										.closeDbAdapter();
								if (!dbCloseResult)
									throw new Exception(
											"The database was not successfully closed.");
								DBTableExport objDBTableExport = new DBTableExport(
										Genre_List.this);
								objDBTableExport.exportAsCSVFile(
										cursorTableQuery,
										MyAppDbAdapter.MY_GENRE_DB_TABLE);
								Genre_List.objDisplayAlertClass = new MyDisplayAlertClass(
										Genre_List.this,
										new CustAlrtMsgOptnListener(
												MessageCodes.ALERT_TYPE_MSG),
										"Table Export", "Export Completed");
								objDBTableExport = null;
							}
						}
					}
					// end try
					catch (Exception error) {
						MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
								this);
						errExcpError.addToLogFile(error,
								"Genre_List.onMenuItemSelected",
								"attempting to export the "
										+ MyAppDbAdapter.MY_GENRE_DB_TABLE
										+ " table.");
						errExcpError = null;
						return false;
					}// end try/catch
				} else if (itemId == R.id.quit)
				// exit application
				{
					APPGlobalVars.SCR_PAUSE_CTL = "QUIT";
					this.finish();
					return true;
				}
				return super.onMenuItemSelected(featureId, item);
			}// end switch
		}// end try
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(this);
			errExcpError.addToLogFile(error, "Genre_List.onMenuItemSelected",
					"switch statement exception. ");
			errExcpError = null;
			return false;
		}// end try/catch
	}// end onMenuItemSelected

	/**
	 * onPrepareOptionsMenu method
	 * 
	 * Prepare the Screen's standard options menu to be displayed.
	 * 
	 * This is called right before the menu is shown, every time it is shown.
	 * 
	 * You can use this method to efficiently enable/disable items or otherwise
	 * dynamically modify the contents.
	 * 
	 * The default implementation updates the system menu items based on the
	 * activity's state.
	 * 
	 * Deriving classes should always call through to the base class
	 * implementation.
	 * 
	 * @param Menu
	 *            menu The options menu as last shown or first initialized by
	 *            onCreateOptionsMenu().
	 * 
	 * @return Must return true for the menu to be displayed; if it returns
	 *         false it will not be shown.
	 * 
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}// end onPrepareOptionsMenu

	/**
	 * onCreateContextMenu method
	 * 
	 * Called when a context menu for the view is about to be shown.
	 * 
	 * Unlike onCreateOptionsMenu(Menu), this will be called every time the
	 * context menu is about to be shown and should be populated for the view
	 * (or item inside the view for AdapterView subclasses, this can be found in
	 * the menuInfo)).
	 * 
	 * Use onContextItemSelected(android.view.MenuItem) to know when an item has
	 * been selected.
	 * 
	 * It is not safe to hold onto the context menu after this method returns.
	 * 
	 * Called when the context menu for this view is being built.
	 * 
	 * It is not safe to hold onto the menu after this method returns.
	 * 
	 * @param Menu
	 *            menu The context menu that is being built.
	 * @param View
	 *            v The view for which the context menu is being built
	 * @param ContextMenuInfo
	 *            menuInfo Extra information about the item for which the
	 *            context menu should be shown. This information will vary
	 *            depending on the class of v.
	 * 
	 * @return void
	 * 
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.listview_context_menu, menu);
	}// end onCreateContextMenu

	/**
	 * onContextItemSelected method
	 * 
	 * This hook is called whenever an item in a context menu is selected. The
	 * default implementation simply returns false to have the normal processing
	 * happen (calling the item's Runnable or sending a message to its Handler
	 * as appropriate).
	 * 
	 * You can use this method for any items for which you would like to do
	 * processing without those other facilities.
	 * 
	 * Use getMenuInfo() to get extra information set by the View that added
	 * this menu item.
	 * 
	 * Derived classes should call through to the base class for it to perform
	 * the default menu handling.
	 * 
	 * @param MenuItem
	 *            item The context menu item that was selected.
	 * 
	 * @return boolean Return false to allow normal context menu processing to
	 *         proceed, true to consume it here.
	 * 
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.delete_entry_context_menu_option) {
			displayConfirmRequest(DELETE_CONFIRMATION_MESSAGE, item);
			return true;
		}
		return super.onContextItemSelected(item);
	}// end onContextItemSelected

	/**
	 * onListItemClick method
	 * 
	 * This method will be called when an item in the list is selected.
	 * 
	 * Subclasses should override. Subclasses can call
	 * getListView().getItemAtPosition(position) if they need to access the data
	 * associated with the selected item.
	 * 
	 * @param ListView
	 *            l The ListView where the click happened.
	 * @param View
	 *            v The view that was clicked within the ListView.
	 * @param int position The position of the view in the list.
	 * @param long id The row id of the item that was clicked.
	 * 
	 * @return void
	 * 
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		try {
			if (this.mEntryCursor != null) {
				this.mEntryCursor = ((SimpleCursorAdapter) l.getAdapter())
						.getCursor();
				this.mEntryCursor.moveToPosition(position);
				Intent i = new Intent(this, AddEditGenre.class);
				i.putExtra(MyAppDbAdapter.KEY_ROWID, id);
				i.putExtra(
						MyAppDbAdapter.KEY_GENRE,
						this.mEntryCursor.getString(this.mEntryCursor
								.getColumnIndexOrThrow(MyAppDbAdapter.KEY_GENRE)));
				i.putExtra(
						MyAppDbAdapter.KEY_GENRE_INFO_1,
						this.mEntryCursor.getString(this.mEntryCursor
								.getColumnIndexOrThrow(MyAppDbAdapter.KEY_GENRE_INFO_1)));
				this.intEntryListPosition = position;
				i.putExtra("SHOW_SAVENEW", true);
				startActivityForResult(i, ACTIVITY_EDIT);
			}// end if (this.mEntryCursor != null)
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(this);
			errExcpError.addToLogFile(error, "Genre_List.onListItemClick", "");
			errExcpError = null;
		}// end try/catch
	}// end onListItemClick

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
			if (intent != null) {
				Bundle extras = intent.getExtras();
				if (extras != null) {
					this.blSaveNew = extras.getBoolean("IS_SAVE_NEW");
					if (this.dbAppDbObj == null) {
						// set database query class object
						this.dbAppDbObj = new MyAppDbSQL(this);
					}// end if (this.dbGenre == null)
					switch (requestCode) {
					case ACTIVITY_CREATE:
						if (resultCode == RESULT_OK && intent != null) {
							String txtGenre = extras
									.getString(MyAppDbAdapter.KEY_GENRE);
							String txtAddinfo = extras
									.getString(MyAppDbAdapter.KEY_GENRE_INFO_1);
							// save the data to the database table
							boolean dbOpenResult = this.dbAppDbObj
									.openDbAdapter();
							if (dbOpenResult) {
								boolean blIsSuccessful = this.dbAppDbObj
										.createGenreEntry(txtGenre, txtAddinfo);
								boolean dbCloseResult = this.dbAppDbObj
										.closeDbAdapter();
								if (!dbCloseResult)
									throw new Exception(
											"The database was not successfully closed.");
								if (blIsSuccessful == false) {
									Genre_List.objDisplayAlertClass = new MyDisplayAlertClass(
											Genre_List.this,
											new CustAlrtMsgOptnListener(
													MessageCodes.ALERT_TYPE_MSG),
											"Database Issue",
											"There was an issue, and the register entry data was not created.");
									break;
								} else {
									// bgh 08/26/2010 v1.03 - get the position
									// of the list entry
									// that was just created
									this.intEntryListPosition = this
											.getListView()
											.getSelectedItemPosition();
									break;
								}// end if (blIsSuccessful == false)
							}
						}// end if (resultCode == RESULT_OK && intent != null)
						else if (resultCode == ACTIVITY_FINISH) {
							APPGlobalVars.SCR_PAUSE_CTL = "QUIT";
							finish();
							break;
						} else if (resultCode == RESULT_CANCELED) {
							// bgh 08/26/2010 v1.03 - get the position of the
							// list entry
							this.intEntryListPosition = this.getListView()
									.getSelectedItemPosition();
							break;
						} else
							break;
					case ACTIVITY_EDIT:
						if (resultCode == RESULT_OK && intent != null) {
							Long rowId = extras
									.getLong(MyAppDbAdapter.KEY_ROWID);
							if (rowId != null) {
								String strGenre = extras
										.getString(MyAppDbAdapter.KEY_GENRE);
								String strAddinfo = extras
										.getString(MyAppDbAdapter.KEY_GENRE_INFO_1);
								String strOrigGenre = extras
										.getString("OrigGenre");
								String strOrigAddinfo = extras
										.getString("OrigAddinfo");
								// save the data to the database table
								boolean dbOpenResult = this.dbAppDbObj
										.openDbAdapter();
								if (dbOpenResult) {
									boolean blIsSuccessful = this.dbAppDbObj
											.updateGenreEntry(rowId, strGenre,
													strAddinfo, strOrigGenre,
													strOrigAddinfo);
									boolean dbCloseResult = this.dbAppDbObj
											.closeDbAdapter();
									if (!dbCloseResult)
										throw new Exception(
												"The database was not successfully closed.");
									if (blIsSuccessful == false) {
										Genre_List.objDisplayAlertClass = new MyDisplayAlertClass(
												Genre_List.this,
												new CustAlrtMsgOptnListener(
														MessageCodes.ALERT_TYPE_MSG),
												"Database Issue",
												"There was an issue, and the entry data was not updated.");
										break;
									} else {
										// bgh 08/26/2010 v1.03 - get the
										// position of the list entry
										this.intEntryListPosition = this
												.getListView()
												.getSelectedItemPosition();
										break;
									}// end if (blIsSuccessful == false)
								}
							}// end if (rowId != null)
							break;
						} else if (resultCode == ACTIVITY_FINISH) {
							APPGlobalVars.SCR_PAUSE_CTL = "QUIT";
							finish();
							break;
						} else if (resultCode == RESULT_CANCELED) {
							// bgh 08/26/2010 v1.03 - get the position of the
							// list entry
							this.intEntryListPosition = this.getListView()
									.getSelectedItemPosition();
							break;
						} else
							break;
					default:
						break;
					}// end switch
				}// end if (extras != null)
			}// end if (intent != null)
		}// end try
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					Genre_List.this);
			errExcpError.addToLogFile(error, "Genre_List.onActivityResult", "");
			errExcpError = null;
		}// end try/catch (Exception error)
	}// end onActivityResult

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
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			// set the 'no prompt' password flag
			// PasswordDialog.setPasswordNoPrompt();
			finish();
			return true;
		default:
			return false;
		}
	}// end onKeyDown

	/**
	 * myAppCleanup method
	 * 
	 * Sets object variables to null
	 * 
	 * @return void
	 * 
	 */
	protected void myAppCleanup() {
		try {
			/* Set object variables to null */
			if (Genre_List.objDisplayAlertClass != null) {
				Genre_List.objDisplayAlertClass.cleanUpClassVars();
				Genre_List.objDisplayAlertClass = null;
			}
			if (this.mEntryCursor != null) {
				this.mEntryCursor.close();
				this.mEntryCursor = null;
			}
			if (this.listRegister != null) {
				this.listRegister.getCursor().close();
				this.listRegister = null;
			}
			if (this.dbAppDbObj != null) {
				this.dbAppDbObj = null;
			}
		}// end try
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					Genre_List.this);
			errExcpError.addToLogFile(error, "Genre_List.myAppCleanup",
					"no prompt");
			errExcpError = null;
		}// end try/catch (Exception error)
	}// end myAppCleanup

	/**
	 * getSortOption method
	 * 
	 * Sets the sorting option for how the data is sorted
	 * 
	 * @return void
	 * 
	 */

	/**
	 * Called after onRestoreInstanceState(Bundle), onRestart(), or onPause(),
	 * for your activity to start interacting with the user.
	 * 
	 * This is a good place to begin animations, open exclusive-access devices
	 * (such as the camera), etc.
	 * 
	 * the data is refreshed
	 * 
	 */
	@Override
	protected void onResume() {
		/* query sorting options */
		super.onResume();
		try {
			if (this.blSaveNew == true) {
				// call the entry activity again
				this.blSaveNew = false;
				Intent intentInsrt = new Intent(Genre_List.this,
						AddEditGenre.class);
				intentInsrt.putExtra("SHOW_SAVENEW", true);
				startActivityForResult(intentInsrt, ACTIVITY_CREATE);
				return;
			}// end if (blSaveNew == true)
				// set database query class object
			this.dbAppDbObj = new MyAppDbSQL(this);

			this.fillData();
			this.registerForContextMenu(this.getListView());
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					Genre_List.this);
			errExcpError.addToLogFile(error, "Genre_List.resumeData", "");
			errExcpError = null;
		}// end try/catch (Exception error)
	}// end onResume

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
	 * Checks the current state. If in "QUIT" state, then the cleanup and finish
	 * code is executed.
	 * 
	 * "SAVEINSTANCE" is used for when the screen orientation is changed.
	 * 
	 * @return void
	 * 
	 */
	@Override
	protected void onPause() {
		// The user is going somewhere else, so make sure their current
		// changes are safely saved away in the provider. We don't need
		// to do this if only editing.
		if (APPGlobalVars.SCR_PAUSE_CTL != null
				&& APPGlobalVars.SCR_PAUSE_CTL.equals("QUIT")) {
			APPGlobalVars.SCR_PAUSE_CTL = "";
			finish();
		}
		super.onPause();
	}// end onPause

	/**
	 * displayConfirmRequest method
	 * 
	 * Prompts user to confirm an action.
	 * 
	 * Action types include: deleting an entry or clearing all entries.
	 * 
	 * @param int id Row ID of the entry to delete
	 * @param MenuItem
	 *            The context menu item that was selected.
	 * 
	 * @return void
	 * 
	 */
	private void displayConfirmRequest(int id, final MenuItem item) {
		try {
			this.setBlContinue(false);
			switch (id) {
			case DELETE_CONFIRMATION_MESSAGE:
				new AlertDialog.Builder(Genre_List.this)
						.setIcon(R.drawable.alert_dialog_icon)
						.setTitle(R.string.deleteEntryYesNo_title)
						.setMessage(R.string.deleteEntryYesNo_message)
						.setPositiveButton(R.string.alert_dialog_ok,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										/* User clicked OK, delete Entry */
										AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
												.getMenuInfo();
										try {
											boolean dbOpenResult = Genre_List.this.dbAppDbObj
													.openDbAdapter();
											if (dbOpenResult) {
												boolean blIsSuccessful = Genre_List.this.dbAppDbObj
														.deleteGenreEntry(info.id);
												boolean dbCloseResult = Genre_List.this.dbAppDbObj
														.closeDbAdapter();
												if (!dbCloseResult)
													throw new Exception(
															"The database was not successfully closed.");
												if (blIsSuccessful == false) {
													Genre_List.objDisplayAlertClass = new MyDisplayAlertClass(
															Genre_List.this,
															new CustAlrtMsgOptnListener(
																	MessageCodes.ALERT_TYPE_MSG),
															"Database Issue",
															"There was an issue, and the register entry data was not deleted.");
												}// end if (blIsSuccessful ==
													// false)
												Genre_List.this.fillData();
											}
										} catch (Exception error) {
											MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
													Genre_List.this);
											errExcpError
													.addToLogFile(
															error,
															"Genre_List.displayConfirmRequest.deleteGenreEntry",
															"");
											errExcpError = null;
										}// end try/catch (Exception error)
									}
								})
						.setNegativeButton(R.string.alert_dialog_cancel,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										/* User clicked Cancel */
										setBlContinue(false);
									}
								}).show();
				return;
			case CLEAR_ENTRIES_CONFIRMATION_MESSAGE:
				new AlertDialog.Builder(Genre_List.this)
						.setIcon(R.drawable.alert_dialog_icon)
						.setTitle(R.string.clearYesNo_title)
						.setMessage(R.string.clearYesNo_message)
						.setPositiveButton(R.string.alert_dialog_ok,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										/* User clicked OK, clear entries */
										try {
											boolean dbOpenResult = Genre_List.this.dbAppDbObj
													.openDbAdapter();
											if (dbOpenResult) {
												boolean blIsSuccessful = Genre_List.this.dbAppDbObj
														.deleteGenreEntries();
												boolean dbCloseResult = Genre_List.this.dbAppDbObj
														.closeDbAdapter();
												if (!dbCloseResult)
													throw new Exception(
															"The database was not successfully closed.");
												if (blIsSuccessful == false) {
													Genre_List.objDisplayAlertClass = new MyDisplayAlertClass(
															Genre_List.this,
															new CustAlrtMsgOptnListener(
																	MessageCodes.ALERT_TYPE_MSG),
															"Database Issue",
															"There was an issue, and the register entries were not deleted.");
												}
												Genre_List.this.fillData();
											}
										} catch (Exception error) {
											MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
													Genre_List.this);
											errExcpError
													.addToLogFile(
															error,
															"Genre_List.displayConfirmRequest.deleteGenreEntries",
															"");
											errExcpError = null;
										}// end try/catch (Exception error)
									}
								})
						.setNegativeButton(R.string.alert_dialog_cancel,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										/* User clicked Cancel */
										setBlContinue(false);
									}
								}).show();
				return;
			}// end switch
		}// end try
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					Genre_List.this);
			errExcpError.addToLogFile(error,
					"Genre_List.displayConfirmRequest", "");
			errExcpError = null;
		}// end try/catch (Exception error)
	}// end displayConfirmRequest

	/**
	 * @param blContinue
	 *            the blContinue to set
	 */
	protected boolean setBlContinue(boolean blContinue) {
		this.blContinue = blContinue;
		return blContinue;
	}

	/**
	 * @return the blContinue
	 */
	protected boolean isBlContinue() {
		return blContinue;
	}

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
		super.onSaveInstanceState(savedInstanceState);
		try {
			// bgh 08/26/2010 v1.03 - get the position of the list entry
			this.intEntryListPosition = this.getListView()
					.getSelectedItemPosition();
			savedInstanceState
					.putInt("LISTPOSITION", this.intEntryListPosition);
		}// end try
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					Genre_List.this);
			errExcpError.addToLogFile(error, "Genre_List.onSaveInstanceState",
					"no prompt");
			errExcpError = null;
		}// end try/catch (Exception error)
	}// end onSaveInstanceState

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
			if (savedInstanceState != null) {
				this.intEntryListPosition = savedInstanceState
						.getInt("LISTPOSITION");
			}// end if (savedInstanceState != null)
		}// end try
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					Genre_List.this);
			errExcpError.addToLogFile(error,
					"Genre_List.onRestoreInstanceState", "no prompt");
			errExcpError = null;
		}// end try/catch (Exception error)
	}// end onRestoreInstanceState

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
		// The user is going somewhere else, so make sure their current
		// changes are safely saved away in the provider. We don't need
		// to do this if only editing.
		super.onDestroy();
		this.myAppCleanup();
	}// end onDestroy

	/**
	 * setSelection method
	 * 
	 * Set the currently selected list item to the specified position with the
	 * adapter's data
	 * 
	 * @param int position The position value that the list will change focus
	 *        to.
	 * 
	 * @return void
	 * 
	 */
	@Override
	public void setSelection(int position) {
		super.setSelection(position);
	}// end setSelection

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
		this.myAppCleanup();
	}// end onStop
}// end Genre_List class
