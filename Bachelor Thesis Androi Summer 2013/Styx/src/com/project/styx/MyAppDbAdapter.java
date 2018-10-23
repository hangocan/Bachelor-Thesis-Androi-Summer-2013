
package com.project.styx;

import java.io.File;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.project.styx.CustAlrtMsgOptnListener.MessageCodes;


public class MyAppDbAdapter extends SQLiteOpenHelper {
	
	// database info
	private static final String MY_DATABASE_NAME = "LibraryDB.db";
	
	private static final String MY_INTERNAL_DATABASE_FOLDER = "Styx";
	
	private static final String DATABASE_PATH_EXTERNAL = Environment.getExternalStorageDirectory()
			.toString() + File.separator + "Styx";
	
	private String dbPathToUse = DATABASE_PATH_EXTERNAL;
	
	// change this if the database structure gets changes and needs to be
	// updated.
	private static final int DATABASE_VERSION = 2;
	
	protected static final String KEY_ROWID = "_id";
	
	// Table Book reference
	protected static final String MY_BOOK_DB_TABLE = "book_table";
	
	protected static final String KEY_BOOKID = "bookid";
	
	protected static final String KEY_TITLE = "title";
	
	protected static final String KEY_SUBTITLE = "subtitle";
	
	protected static final String KEY_YEAR = "year";
	
	protected static final String KEY_EDITION = "edition";
	
	protected static final String KEY_ISBN = "isbn";
	
	protected static final String KEY_COVER_LINK = "coverlink";
	
	protected static final String KEY_COVER_TYPE = "covertype";
	
	protected static final String KEY_OWN_CODE = "owncode";
	
	protected static final String KEY_PURCHASE_PRICE = "purchaseprice";
	
	protected static final String KEY_CURRENT_PRICE = "currentprice";
	
	protected static final String KEY_PAGE_NUMBER = "pagenumber";
	
	protected static final String KEY_RECOMMEND_FOR = "recommendfor";
	
	protected static final String KEY_LOAN_TO = "loanto";
	
	protected static final String KEY_LOAN_TO_PHONE = "loantophone";
	
	protected static final String KEY_LOAN_DATE = "loandate";
	
	protected static final String KEY_PRELOAN = "preloan";
	
	protected static final String KEY_BOOK_INFO_1 = "bookinfo1";
	
	protected static final String KEY_BOOK_INFO_2 = "bookinfo2";
	
	protected static final String KEY_BOOK_INFO_3 = "bookinfo3";
	
	protected static final String KEY_BOOK_INFO_4 = "bookinfo4";
	
	protected static final String KEY_BOOK_INFO_5 = "bookinfo5";
	
	protected static final String KEY_DESCRIPTION = "description";
	
	protected static final String KEY_BOOK_LANGUAGEID = "booklanguageid";// ID
	
	protected static final String KEY_BOOK_PUBLISHERID = "bookpublisherid";// ID
	
	protected static final String KEY_BOOK_EDITORID = "bookeditorid";// ID
	
	protected static final String KEY_BOOK_SHELFID = "bookshelfid"; // ID
	
	// Table Author Reference
	protected static final String MY_AUTHOR_DB_TABLE = "author_table";
	
	protected static final String KEY_AUTHORID = "authorid";
	
	protected static final String KEY_AUTHORFULLNAME = "authorfullname";
	
	protected static final String KEY_AUTHORHOMEPAGE = "authorhomepage";
	
	protected static final String KEY_AUTHOREMAIL = "authoremail";
	
	// Table Book Author
	protected static final String MY_BOOK_AUTHOR_DB_TABLE = "book_author_table";
	
	protected static final String KEY_BAID = "baid";
	
	protected static final String KEY_BA_AUTHORID = "baauthorid";
	
	protected static final String KEY_BA_BOOKID = "babookid";
	
	// Table Review
	protected static final String MY_REVIEW_DB_TABLE = "review_table";
	
	protected static final String KEY_REVIEW = "review";
	
	protected static final String KEY_REVIEWID = "reviewid";
	
	protected static final String KEY_REVIEW_INFO_1 = "reviewinfo1";
	
	protected static final String KEY_REVIEW_AUTHORID = "reviewauthorid";
	
	protected static final String KEY_REVIEW_BOOKID = "reviewbookid";
	
	// Table Editor
	protected static final String MY_EDITOR_DB_TABLE = "editor_table";
	
	protected static final String KEY_EDITORID = "editorid";
	
	protected static final String KEY_EDITORFULLNAME = "editorfullname";
	
	protected static final String KEY_EDITORHOMEPAGE = "editorhomepage";
	
	protected static final String KEY_EDITOREMAIL = "editoremail";
	
	// Table Publisher
	protected static final String MY_PUBLISHER_DB_TABLE = "publisher_table";
	
	protected static final String KEY_PUBLISHERID = "publisherid";
	
	protected static final String KEY_PUBLISHER = "publisher";
	
	protected static final String KEY_PUBLISHERADDRESS = "publisheraddress";
	
	protected static final String KEY_PUBLISHERHOMEPAGE = "publisherhomepage";
	
	protected static final String KEY_PUBLISHEREMAIL = "publisheremail";
	
	protected static final String KEY_PUBLISHER_INFO_1 = "publisherinfo1";
	
	// Table language
	protected static final String MY_LANGUAGE_DB_TABLE = "language_table";
	
	protected static final String KEY_LANGUAGE = "language";
	
	protected static final String KEY_LANGUAGEID = "languageid";
	
	protected static final String KEY_LANGUAGE_INFO_1 = "languageinfo1";
	
	// Table Genre
	protected static final String MY_GENRE_DB_TABLE = "genre_table";
	
	protected static final String KEY_GENRE = "genre";
	
	protected static final String KEY_GENREID = "genreid";
	
	protected static final String KEY_GENRE_INFO_1 = "genreinfo1";
	
	// Table Book - Genre
	protected static final String MY_BOOK_GENRE_DB_TABLE = "book_genre_table";
	
	protected static final String KEY_BG_ID = "bgeid";
	
	protected static final String KEY_BG_GENREID = "bggenreid";
	
	protected static final String KEY_BG_BOOKID = "bgbookid";
	
	// Table Bookseller
	protected static final String MY_BOOKSELLER_DB_TABLE = "bookseller_table";
	
	protected static final String KEY_BOOKSELLERID = "booksellerid";
	
	protected static final String KEY_BOOKSELLER = "bookseller";
	
	protected static final String KEY_BOOKSELLERADDRESS = "bookselleraddress";
	
	protected static final String KEY_BOOKSELLERHOMEPAGE = "booksellerhomepage";
	
	protected static final String KEY_BOOKSELLEREMAIL = "bookselleremail";
	
	protected static final String KEY_EMAIL_NEWS = "emailnews";
	
	protected static final String KEY_MAIL_NEWS = "mailnews";
	
	protected static final String KEY_BOOKSELLER_INFO_1 = "booksellerinfo1";
	
	// Table Book- Bookseller
	protected static final String MY_BOOK_BOOKSELLER_DB_TABLE = "book_bookseller_table";
	
	protected static final String KEY_BSID = "bsid";
	
	protected static final String KEY_BS_BOOKSELLERID = "bsbooksellerid";
	
	protected static final String KEY_BS_BOOKID = "bsbookid";
	
	// Table Shelf
	protected static final String MY_SHELF_DB_TABLE = "shelf_table";
	
	protected static final String KEY_SHELFID = "shelfid";
	
	protected static final String KEY_SHELF_RACKID = "shelfrackid";
	
	protected static final String KEY_SHELF_LABEL = "shelflabel";
	
	protected static final String KEY_SHELF_INFO_1 = "shelfinfo1";
	
	// Table Rack
	protected static final String MY_RACK_DB_TABLE = "rack_table";
	
	protected static final String KEY_RACKID = "rackid";
	
	protected static final String KEY_RACK_ROOMID = "rackroomid";
	
	protected static final String KEY_RACK_LABEL = "racklabel";
	
	protected static final String KEY_RACK_INFO_1 = "rackinfo1";
	
	// Table Room
	protected static final String MY_ROOM_DB_TABLE = "room_table";
	
	protected static final String KEY_ROOMID = "roomid";
	
	protected static final String KEY_ROOM_STORAGEID = "roomstorageid";
	
	protected static final String KEY_ROOM_NUMBER = "roomnumber";
	
	protected static final String KEY_ROOM_INFO_1 = "roominfo1";
	
	// Table storage
	protected static final String MY_STORAGE_DB_TABLE = "storage_table";
	
	protected static final String KEY_STORAGEID = "storageid";
	
	protected static final String KEY_STORAGE = "storage";
	
	protected static final String KEY_STORAGEADDRESS = "storageaddress";
	
	protected static final String KEY_STORAGE_INFO_1 = "storageinfo1";
	
	// Table apps preference reference
	protected static final String MY_PREFS_DB_TABLE = "myappprefs";
	
	protected static final String KEY_PREFNAME = "prefname";
	
	protected static final String KEY_PREFVALUE = "prefvalue";
	
	protected static final String KEY_PREFDESCR = "prefdescr";
	
	private static final String TAG = "MyAppDbAdapter";
	
	private Context mCtx;
	
	private SQLiteDatabase mDb;
	
	protected MyAppDbAdapter objBookDbAdapterRef;
	
	private MyDisplayAlertClass objDisplayAlertClass;
	
	/* DATABASE CREATE STATEMENTS */
	/** Template Database table creation sql statement */
	private static final String MY_BOOK_DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ MY_BOOK_DB_TABLE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_TITLE
			+ " TEXT NOT NULL DEFAULT '', " + KEY_SUBTITLE + " TEXT '', " + KEY_ISBN + " TEXT '', "
			+ KEY_YEAR + " TEXT '', " + KEY_EDITION + " TEXT '', " + KEY_COVER_LINK + " TEXT '', "
			+ KEY_COVER_TYPE + " TEXT '', " + KEY_OWN_CODE + " TEXT '', " + KEY_PURCHASE_PRICE
			+ " TEXT '', " + KEY_CURRENT_PRICE + " TEXT '', " + KEY_PAGE_NUMBER + " TEXT '', "
			+ KEY_RECOMMEND_FOR + " TEXT '', " + KEY_LOAN_TO + " TEXT '', " + KEY_LOAN_TO_PHONE
			+ " TEXT '', " + KEY_LOAN_DATE + " TEXT '', " + KEY_PRELOAN + " TEXT '', "
			+ KEY_BOOK_EDITORID + " INTEGER ''," + KEY_BOOK_PUBLISHERID + " INTEGER '',"
			+ KEY_BOOK_LANGUAGEID + " INTEGER ''," + KEY_BOOK_SHELFID + " INTEGER '',"
			+ KEY_BOOK_INFO_1 + " TEXT '', " + KEY_BOOK_INFO_2 + " TEXT '', " + KEY_BOOK_INFO_3
			+ " TEXT '', " + KEY_BOOK_INFO_4 + " TEXT '', " + KEY_BOOK_INFO_5 + " TEXT '', "
			+ KEY_DESCRIPTION + " TEXT);";
	
	private static final String MY_AUTHOR_DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ MY_AUTHOR_DB_TABLE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_AUTHORFULLNAME
			+ " TEXT NOT NULL DEFAULT '', " + KEY_AUTHORHOMEPAGE + " TEXT '', " + KEY_AUTHOREMAIL
			+ " TEXT);";
	
	private static final String MY_BOOK_AUTHOR_DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ MY_BOOK_AUTHOR_DB_TABLE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_BA_BOOKID
			+ " INTEGER ' '," + KEY_BA_AUTHORID + " INTEGER);";
	
	private static final String MY_REVIEW_DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ MY_REVIEW_DB_TABLE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_REVIEW_BOOKID
			+ " INTEGER ' '," + KEY_REVIEW_AUTHORID + " INTEGER ' '," + KEY_REVIEW + " TEXT '', "
			+ KEY_REVIEW_INFO_1 + " TEXT);";
	
	private static final String MY_EDITOR_DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ MY_EDITOR_DB_TABLE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_EDITORFULLNAME
			+ " TEXT NOT NULL DEFAULT '', " + KEY_EDITORHOMEPAGE + " TEXT '', " + KEY_EDITOREMAIL
			+ " TEXT);";
	
	private static final String MY_PUBLISHER_DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ MY_PUBLISHER_DB_TABLE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_PUBLISHER
			+ " TEXT NOT NULL DEFAULT '', " + KEY_PUBLISHERHOMEPAGE + " TEXT '', "
			+ KEY_PUBLISHERADDRESS + " TEXT '', " + KEY_PUBLISHER_INFO_1 + " TEXT '', "
			+ KEY_PUBLISHEREMAIL + " TEXT);";
	
	private static final String MY_LANGUAGE_DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ MY_LANGUAGE_DB_TABLE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_LANGUAGE
			+ " TEXT NOT NULL DEFAULT ''," + KEY_LANGUAGE_INFO_1 + " TEXT);";
	
	private static final String MY_GENRE_DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ MY_GENRE_DB_TABLE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_GENRE
			+ " TEXT NOT NULL DEFAULT ''," + KEY_GENRE_INFO_1 + " TEXT);";
	
	private static final String MY_BOOK_GENRE_DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ MY_BOOK_GENRE_DB_TABLE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_BG_BOOKID
			+ " INTEGER ' '," + KEY_BG_GENREID + " INTEGER);";
	
	private static final String MY_BOOKSELLER_DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ MY_BOOKSELLER_DB_TABLE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_BOOKSELLER
			+ " TEXT NOT NULL DEFAULT '', " + KEY_BOOKSELLERADDRESS + " TEXT '', " + KEY_EMAIL_NEWS
			+ " TEXT '', " + KEY_MAIL_NEWS + " TEXT '', " + KEY_BOOKSELLERHOMEPAGE + " TEXT '', "
			+ KEY_BOOKSELLEREMAIL + " TEXT '', " + KEY_BOOKSELLER_INFO_1 + " TEXT);";
	
	private static final String MY_BOOK_BOOKSELLER_DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ MY_BOOK_BOOKSELLER_DB_TABLE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_BS_BOOKID
			+ " INTEGER ' '," + KEY_BS_BOOKSELLERID + " INTEGER);";
	
	private static final String MY_SHELF_DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ MY_SHELF_DB_TABLE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_SHELF_LABEL
			+ " TEXT NOT NULL DEFAULT ''," + KEY_SHELF_RACKID + " INTEGER ''," + KEY_SHELF_INFO_1
			+ " TEXT);";
	
	private static final String MY_RACK_DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ MY_RACK_DB_TABLE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_RACK_LABEL
			+ " TEXT NOT NULL DEFAULT ''," + KEY_RACK_ROOMID + " INTEGER ''," + KEY_RACK_INFO_1
			+ " TEXT);";
	
	private static final String MY_ROOM_DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ MY_ROOM_DB_TABLE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_ROOM_NUMBER
			+ " TEXT NOT NULL DEFAULT''," + KEY_ROOM_STORAGEID + " INTEGER ''," + KEY_ROOM_INFO_1
			+ " TEXT);";
	
	private static final String MY_STORAGE_DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ MY_STORAGE_DB_TABLE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_STORAGE
			+ " TEXT NOT NULL DEFAULT ''," + KEY_STORAGEADDRESS + " TEXT ''," + KEY_STORAGE_INFO_1
			+ " TEXT);";
	
	/** Application Preferences Database table creation sql statement */
	private static final String MY_PREFS_DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ MY_PREFS_DB_TABLE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ "prefname TEXT NOT NULL DEFAULT '' COLLATE NOCASE, "
			+ "prefvalue TEXT NOT NULL DEFAULT '' COLLATE NOCASE, " + "prefdescr TEXT);";
	
	// public static final String DEFAULT_SORT_ORDER = MyAppDbAdapter.KEY_BOOKID+
	// " ASC";
	/** Constructor - takes the context to allow the database to be opened/created
	 * @param ctx the Context within which to work */
	MyAppDbAdapter(Context context) {
		super(context, MyAppDbAdapter.MY_DATABASE_NAME, null, MyAppDbAdapter.DATABASE_VERSION);
		try {
			this.mCtx = context;
			this.objBookDbAdapterRef = this;
			// check for existence of the SD card
			if (android.os.Environment.getExternalStorageState()
					.equals(android.os.Environment.MEDIA_MOUNTED)
					&& !(Environment.getExternalStorageState()
							.equals(Environment.MEDIA_MOUNTED_READ_ONLY))) {
				this.dbPathToUse = DATABASE_PATH_EXTERNAL + File.separator;
			}// end if
			else {
				this.dbPathToUse = context.getDatabasePath(MyAppDbAdapter.MY_INTERNAL_DATABASE_FOLDER)
						.getPath();
			}// end if sd card exists
				// file might not be created yet, use built path instead
			File myAppDBDir = new File(this.dbPathToUse);
			File myAppDB = null;
			// if the directory does not yet exist, create it.
			if (!myAppDBDir.exists() && !myAppDBDir.isDirectory()) {
				boolean blMkDirRslt = myAppDBDir.mkdirs();
				if (blMkDirRslt != true) {
					throw new Exception("Application data directory could not be created at "
							+ this.dbPathToUse);
				} else {
					myAppDB = new File(this.dbPathToUse + MyAppDbAdapter.MY_DATABASE_NAME);
				}
			} else {
				if (myAppDBDir.exists() && myAppDBDir.isDirectory()) {
					myAppDB = new File(this.dbPathToUse + MyAppDbAdapter.MY_DATABASE_NAME);
				}// end if (!myAppDBDir.exists() &&...
			}// end if (!myAppDBDir.exists() &&...
			if (myAppDB != null) {
				this.mDb = SQLiteDatabase.openDatabase(this.dbPathToUse + File.separator
						+ MyAppDbAdapter.MY_DATABASE_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY
						| SQLiteDatabase.OPEN_READWRITE);
				if (this.mDb != null) {
					int version = this.mDb.getVersion();
					if (version != MyAppDbAdapter.DATABASE_VERSION) {
						this.mDb.beginTransaction();
						try {
							if (version == 0) {
								onCreate(this.mDb);
							} else {
								onUpgrade(this.mDb, version, MyAppDbAdapter.DATABASE_VERSION);
							}
							this.mDb.setVersion(MyAppDbAdapter.DATABASE_VERSION);
							this.mDb.setTransactionSuccessful();
						}// end try statements
						catch (SQLException error) {
							MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(this.mCtx);
							errExcpError.addToLogFile(	error, "MyAppDbAdapter.getWritableDatabase",
																"attempting to create database tables");
							errExcpError = null;
						}// end try/catch (Exception error
						catch (Exception error) {
							MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(this.mCtx);
							errExcpError.addToLogFile(	error, "CheckRegDBAdapter.getWritableDatabase",
																"attempting to create database tables");
							errExcpError = null;
						}// end try/catch (Exception error)
						finally {
							this.mDb.endTransaction();
						}
					}// end if (version != CheckRegDbAdapter.DATABASE_VERSION)
				}// end if (this.mDb != null)
			}
		}
		catch (SQLiteException error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(this.mCtx);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.MyAppDbAdapter",
												"SQLiteException - Class constructor");
			errExcpError = null;
		}// end try/catch (Exception error)
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(this.mCtx);
			errExcpError.addToLogFile(error, "MyAppDbAdapter.MyAppDbAdapter", "Class constructor");
			errExcpError = null;
		} finally {
			DBUtil.safeCloseDataBase(this.mDb);
		}// end try/catch (Exception error)
	}// end constructor
	
	/** public void onCreate Executes SQLite commands to create tables in the
	 * database.
	 * @param: SQLiteDatabase object
	 * @return: void
	 * @throws SQLException if the SQL scripts encounter issues */
	@Override
	public void onCreate(SQLiteDatabase db) throws SQLException {
		try {
			// Log that getWriteableDatabase was called recursively
			MyErrorLog<String> errExcpError = new MyErrorLog<String>(this.mCtx);
			errExcpError.addToLogFile(	"creating the database tables", "MyAppDbAdapter.onCreate",
												"no prompt");
			errExcpError = null;
			// create the database tables
			db.execSQL(MY_BOOK_DATABASE_CREATE);
			db.execSQL(MY_AUTHOR_DATABASE_CREATE);
			db.execSQL(MY_BOOK_AUTHOR_DATABASE_CREATE);
			db.execSQL(MY_EDITOR_DATABASE_CREATE);
			db.execSQL(MY_REVIEW_DATABASE_CREATE);
			db.execSQL(MY_PUBLISHER_DATABASE_CREATE);
			db.execSQL(MY_LANGUAGE_DATABASE_CREATE);
			db.execSQL(MY_GENRE_DATABASE_CREATE);
			db.execSQL(MY_BOOK_GENRE_DATABASE_CREATE);
			db.execSQL(MY_BOOKSELLER_DATABASE_CREATE);
			db.execSQL(MY_BOOK_BOOKSELLER_DATABASE_CREATE);
			db.execSQL(MY_SHELF_DATABASE_CREATE);
			db.execSQL(MY_RACK_DATABASE_CREATE);
			db.execSQL(MY_ROOM_DATABASE_CREATE);
			db.execSQL(MY_STORAGE_DATABASE_CREATE);
			db.execSQL(MY_PREFS_DATABASE_CREATE);
		}
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(this.mCtx);
			errExcpError.addToLogFile(error, "DatabaseHelper.onCreate", "database Table creates");
			errExcpError = null;
		}// end try/catch (SQLException error)
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(this.mCtx);
			errExcpError.addToLogFile(error, "DatabaseHelper.onCreate", "creating Table creates");
			errExcpError = null;
		}// end try/catch (Exception error)
	}// end onCreate
	
	/** Handles the logging and SQL scripts for upgrade actions When creating an
	 * upgrade, make sure the code here reflects what needs to be done. For
	 * example, if the table structure has been changed
	 * @return void
	 * @param database
	 * @param oldVersion
	 * @param newVersion
	 * @throws SQLException if the SQL scripts encounter issues */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) throws SQLException {
		try {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ".");
			if (this.objBookDbAdapterRef.objDisplayAlertClass != null) {
				this.objBookDbAdapterRef.objDisplayAlertClass.cleanUpClassVars();
				this.objBookDbAdapterRef.objDisplayAlertClass = null;
			}// end if (objDisplayAlertClass != null)
			this.objBookDbAdapterRef.objDisplayAlertClass = new MyDisplayAlertClass(this.mCtx,
					new CustAlrtMsgOptnListener(MessageCodes.ALERT_TYPE_MSG), "Upgrading Database",
					"Upgrading database from version " + oldVersion + " to " + newVersion
							+ ".  Existing Data will be preserved.");
			if (oldVersion == 1 && newVersion > 1) {
				// execute code to handle database changes
			}// end initial create
		}
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(this.mCtx);
			errExcpError.addToLogFile(error, "DatabaseHelper.onUpgrade", "upgrading the database");
			errExcpError = null;
		}// end try/catch (SQLException error)
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(this.mCtx);
			errExcpError.addToLogFile(error, "DatabaseHelper.onUpgrade", "upgrading the database");
			errExcpError = null;
		}// end try/catch (Exception error)
	}// end onUpgrade
	
	/** Create and/or open a database that will be used for reading and writing.
	 * Once opened successfully, the database is cached, so you can call this
	 * method every time you need to write to the database. Make sure to call
	 * close() when you no longer need it. Errors such as bad permissions or a
	 * full disk may cause this operation to fail, but future attempts may
	 * succeed if the problem is fixed. Returns a read/write database object
	 * valid until close() is called Throws SQLiteException if the database
	 * cannot be opened for writing */
	@Override
	public SQLiteDatabase getWritableDatabase() throws SQLiteException {
		try {
			this.mDb = SQLiteDatabase.openDatabase(this.dbPathToUse + File.separator
					+ MyAppDbAdapter.MY_DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE
					| SQLiteDatabase.CREATE_IF_NECESSARY);
			return this.mDb;
		}
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(this.mCtx);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.getWritableDatabase",
												"SQLException - main try/catch");
			errExcpError = null;
			return null;
		}// end try/catch (SQLException error)
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(this.mCtx);
			errExcpError.addToLogFile(error, "MyAppDbAdapter.getWritableDatabase", "main try/catch");
			errExcpError = null;
			return null;
		}// end try/catch (Exception error)
	}// end getWriteableDatabase
	
	/** Create and/or open a database. This will be the same object returned by
	 * getWritableDatabase() unless some problem, such as a full disk, requires
	 * the database to be opened read-only. In that case, a read-only database
	 * object will be returned. If the problem is fixed, a future call to
	 * getWritableDatabase() may succeed, in which case the read-only database
	 * object will be closed and the read/write object will be returned in the
	 * future. Returns a database object valid until getWritableDatabase() or
	 * close() is called. Throws SQLiteException if the database cannot be opened */
	@Override
	public SQLiteDatabase getReadableDatabase() throws SQLiteException {
		try {
			this.mDb = SQLiteDatabase.openDatabase(this.dbPathToUse + File.separator
					+ MyAppDbAdapter.MY_DATABASE_NAME, null, SQLiteDatabase.OPEN_READONLY
					| SQLiteDatabase.CREATE_IF_NECESSARY);
			return this.mDb;
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(this.mCtx);
			errExcpError.addToLogFile(error, "MyAppDbAdapter.getReadableDatabase", "main try/catch");
			errExcpError = null;
			return null;
		}// end try/catch (SQLException error)
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(this.mCtx);
			errExcpError.addToLogFile(error, "MyAppDbAdapter.getReadableDatabase", "main try/catch");
			errExcpError = null;
			return null;
		}// end try/catch (Exception error)
	}// end getReadableDatabase
	
	/** Close the database.
	 * @return void
	 * @param none */
	public void close() {
		if (this.mDb != null && this.mDb.isOpen()) {
			DBUtil.safeCloseDataBase(this.mDb);
			this.mDb = null;
		}
	}// end close()
	
	/** boolean dbIsOpen
	 * @return true if the DB is currently open (has not been closed) */
	protected boolean dbIsOpen() {
		// check if the database object is open
		if (this.mDb != null && this.mDb.isOpen())
			return true;
		else return false;
	}// end dbIsOpen
}// end MyAppDbAdapter class
