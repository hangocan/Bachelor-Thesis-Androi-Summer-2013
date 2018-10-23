
package com.project.styx;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
/** Application Database Actions class. Executes the database actions such as
 * insert, update, delete, etc.
 * @constructor context */
public class MyAppDbSQL {
	
	private MyAppDbAdapter dbAdapterObj;
	private SQLiteDatabase sqliteDBObj;
	private Context ctxContext;
	
	/** MyAppDbSQL Class Constructor */
	protected MyAppDbSQL(Context ctxContext) {
		try {
			this.ctxContext = ctxContext;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.MyAppDbSQL",
										"exception thrown in the class constructor");
			errExcpError = null;
		}
	}// end constructor
	
	/** Database Query methods */
	/** listBookOrderByOpts
	 * @param int intOrderByOpt - the OrderBy Option to return
	 * @return String */
	protected String listBookOrderByOpts(int intBookOrderByOpt) {
		switch (intBookOrderByOpt) {
			case 0:
				return MyAppDbAdapter.KEY_TITLE + " ASC";
			case 1:
				return MyAppDbAdapter.KEY_TITLE + " DESC";
			default:
				return MyAppDbAdapter.KEY_ROWID + " ASC";
		}// end switch
	}// end listBookOrderByOpts
	
	protected String listAuthorOrderByOpts(int intAuthorOrderByOpt) {
		switch (intAuthorOrderByOpt) {
			case 0:
				return MyAppDbAdapter.KEY_AUTHORFULLNAME + " ASC";
			case 1:
				return MyAppDbAdapter.KEY_AUTHORFULLNAME + " DESC";
			default:
				return MyAppDbAdapter.KEY_ROWID + " ASC";
		}// end switch
	}// end listBookOrderByOpts
	
	protected String listEditorOrderByOpts(int intEditorOrderByOpt) {
		switch (intEditorOrderByOpt) {
			case 0:
				return MyAppDbAdapter.KEY_EDITORFULLNAME + " ASC";
			case 1:
				return MyAppDbAdapter.KEY_EDITORFULLNAME + " DESC";
			default:
				return MyAppDbAdapter.KEY_ROWID + " ASC";
		}// end switch
	}// end listBookOrderByOpts
	
	protected String listBooksellerOrderByOpts(int intBooksellerOrderByOpt) {
		switch (intBooksellerOrderByOpt) {
			case 0:
				return MyAppDbAdapter.KEY_BOOKSELLER + " ASC";
			case 1:
				return MyAppDbAdapter.KEY_BOOKSELLER + " DESC";
			default:
				return MyAppDbAdapter.KEY_ROWID + " ASC";
		}// end switch
	}// end listBookOrderByOpts
	
	protected String listPublisherOrderByOpts(int intPublisherOrderByOpt) {
		switch (intPublisherOrderByOpt) {
			case 0:
				return MyAppDbAdapter.KEY_PUBLISHER + " ASC";
			case 1:
				return MyAppDbAdapter.KEY_PUBLISHER + " DESC";
			default:
				return MyAppDbAdapter.KEY_ROWID + " ASC";
		}// end switch
	}// end listBookOrderByOpts
	
	/********************************************
	 *******************************************/
	protected boolean openDbAdapter() throws Exception {
		boolean isOpen = false;
		try {
			if (this.dbAdapterObj == null || !(this.dbAdapterObj.dbIsOpen())) {
				this.dbAdapterObj = new MyAppDbAdapter(ctxContext);
				if (this.dbAdapterObj != null) this.sqliteDBObj = this.dbAdapterObj
						.getReadableDatabase();
			}// end if (this.dbBook == null || ...
			if (this.dbAdapterObj.dbIsOpen() && this.sqliteDBObj != null) {
				isOpen = true;
			}
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbSQL.openDbAdapter",
												"exception thrown opening the db adapter");
			errExcpError = null;
			isOpen = false;
		}
		return isOpen;
	}
	
	protected boolean closeDbAdapter() throws Exception {
		boolean isClosed = false;
		try {
			// clean up DB objects
			if (this.sqliteDBObj != null) {
				this.sqliteDBObj.close();
				this.sqliteDBObj = null;
			}
			if (this.dbAdapterObj != null) {
				this.dbAdapterObj.close();
				this.dbAdapterObj = null;
			}
			isClosed = true;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbSQL.closeDbAdapter",
												"exception thrown closing the db adapter");
			errExcpError = null;
			isClosed = false;
		}
		return isClosed;
	}
	
	/*****************************************************************
	 *****************************************************************/
	/** fetch list: Return a Cursor over the list of entries in the database
	 * @param intSortOpt
	 * the selected sorting option
	 * @return Cursor containing filtered query results
	 * @throws SQLException
	 * if entry could not be found/retrieved */
	protected Cursor fetchBook(int intSortOpt) throws SQLException {
		String strOrderBy = "";
		Cursor mBookCursor = null;
		try {
			strOrderBy = listBookOrderByOpts(intSortOpt);
			mBookCursor = this.sqliteDBObj
					.query(	MyAppDbAdapter.MY_BOOK_DB_TABLE, new String[] {
										MyAppDbAdapter.KEY_ROWID,
										MyAppDbAdapter.KEY_TITLE,
										MyAppDbAdapter.KEY_SUBTITLE, // Subtitle
										MyAppDbAdapter.KEY_ISBN, // ISBN
										MyAppDbAdapter.KEY_YEAR, // Year
										MyAppDbAdapter.KEY_EDITION, // Edition
										MyAppDbAdapter.KEY_COVER_LINK, // Coverlink
										MyAppDbAdapter.KEY_COVER_TYPE, // Covertype
										MyAppDbAdapter.KEY_OWN_CODE, // Owncode
										MyAppDbAdapter.KEY_PURCHASE_PRICE,
										MyAppDbAdapter.KEY_CURRENT_PRICE,
										MyAppDbAdapter.KEY_PAGE_NUMBER, // Pagenumber
										MyAppDbAdapter.KEY_RECOMMEND_FOR,
										MyAppDbAdapter.KEY_LOAN_TO, // Loanto
										MyAppDbAdapter.KEY_LOAN_DATE, // Loandate
										MyAppDbAdapter.KEY_LOAN_TO_PHONE,
										MyAppDbAdapter.KEY_PRELOAN, // Preloan
										MyAppDbAdapter.KEY_BOOK_EDITORID,
										MyAppDbAdapter.KEY_BOOK_LANGUAGEID,
										MyAppDbAdapter.KEY_BOOK_PUBLISHERID,
										MyAppDbAdapter.KEY_BOOK_SHELFID,
										MyAppDbAdapter.KEY_BOOK_INFO_1, // Bookinfo1
										MyAppDbAdapter.KEY_BOOK_INFO_2, // Bookinfo2
										MyAppDbAdapter.KEY_BOOK_INFO_3, // Bookinfo3
										MyAppDbAdapter.KEY_BOOK_INFO_4, // Bookinfo4
										MyAppDbAdapter.KEY_BOOK_INFO_5, // Bookinfo5
										MyAppDbAdapter.KEY_DESCRIPTION, }, null, null,
								null, null,
								strOrderBy);
			if (mBookCursor != null) {
				mBookCursor.moveToFirst();
				return mBookCursor;
			} else {
				return null;
			}// end if (mBookCursor != null)
		} catch (SQLiteException error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchBook",
										"SQLiteException - exception thrown fetching book");
			errExcpError = null;
			return null;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchBook",
										"exception - exception thrown fetching book");
			errExcpError = null;
			return null;
		}
	}// end fetchBook(int intSortOpt)
	
	protected Cursor fetchBookEntry(long rowId) throws SQLException {
		Cursor mBookCursor = null;
		try {
			mBookCursor = this.sqliteDBObj
					.query(	true, MyAppDbAdapter.MY_BOOK_DB_TABLE, new String[] {
										MyAppDbAdapter.KEY_ROWID,
										MyAppDbAdapter.KEY_TITLE,
										MyAppDbAdapter.KEY_SUBTITLE, // Subtitle
										MyAppDbAdapter.KEY_ISBN, // ISBN
										MyAppDbAdapter.KEY_YEAR, // Year
										MyAppDbAdapter.KEY_EDITION, // Edition
										MyAppDbAdapter.KEY_COVER_LINK, // Coverlink
										MyAppDbAdapter.KEY_COVER_TYPE, // Covertype
										MyAppDbAdapter.KEY_OWN_CODE, // Owncode
										MyAppDbAdapter.KEY_PURCHASE_PRICE,
										MyAppDbAdapter.KEY_CURRENT_PRICE,
										MyAppDbAdapter.KEY_PAGE_NUMBER, // Pagenumber
										MyAppDbAdapter.KEY_RECOMMEND_FOR,
										MyAppDbAdapter.KEY_LOAN_TO, // Loanto
										MyAppDbAdapter.KEY_LOAN_DATE, // Loandate
										MyAppDbAdapter.KEY_LOAN_TO_PHONE,
										MyAppDbAdapter.KEY_PRELOAN, // Preloan
										MyAppDbAdapter.KEY_BOOK_EDITORID,
										MyAppDbAdapter.KEY_BOOK_LANGUAGEID,
										MyAppDbAdapter.KEY_BOOK_PUBLISHERID,
										MyAppDbAdapter.KEY_BOOK_SHELFID,
										MyAppDbAdapter.KEY_BOOK_INFO_1, // Bookinfo1
										MyAppDbAdapter.KEY_BOOK_INFO_2, // Bookinfo2
										MyAppDbAdapter.KEY_BOOK_INFO_3, // Bookinfo3
										MyAppDbAdapter.KEY_BOOK_INFO_4, // Bookinfo4
										MyAppDbAdapter.KEY_BOOK_INFO_5, // Bookinfo5
										MyAppDbAdapter.KEY_DESCRIPTION, },
								MyAppDbAdapter.KEY_ROWID + "=" + rowId, null, null,
								null, null, null);
			if (mBookCursor != null) {
				mBookCursor.moveToFirst();
				return mBookCursor;
			} else {
				return null;
			}// end if (mBookCursor != null)
		} catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.fetchBookEntry",
												"BookEntry query");
			errExcpError = null;
			return null;
		}// end try/catch (SQLException error)
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.fetchBookEntry",
												"BookEntry query");
			errExcpError = null;
			return null;
		}
	}// end fetchListEntry(long rowId)
	
	protected Cursor fetchBookByName(String inputText) throws SQLException {
		Cursor mCursor = null;
		try {
			if (inputText == null || inputText.length() == 0) {
				mCursor = this.sqliteDBObj
						.query(	MyAppDbAdapter.MY_BOOK_DB_TABLE, new String[] {
											MyAppDbAdapter.KEY_ROWID,
											MyAppDbAdapter.KEY_TITLE,
											MyAppDbAdapter.KEY_SUBTITLE, // Subtitle
											MyAppDbAdapter.KEY_ISBN, // ISBN
											MyAppDbAdapter.KEY_YEAR, // Year
											MyAppDbAdapter.KEY_EDITION, // Edition
											MyAppDbAdapter.KEY_COVER_LINK, // Coverlink
											MyAppDbAdapter.KEY_COVER_TYPE, // Covertype
											MyAppDbAdapter.KEY_OWN_CODE, // Owncode
											MyAppDbAdapter.KEY_PURCHASE_PRICE,
											MyAppDbAdapter.KEY_CURRENT_PRICE,
											MyAppDbAdapter.KEY_PAGE_NUMBER, // Pagenumber
											MyAppDbAdapter.KEY_RECOMMEND_FOR,
											MyAppDbAdapter.KEY_LOAN_TO, // Loanto
											MyAppDbAdapter.KEY_LOAN_DATE, // Loandate
											MyAppDbAdapter.KEY_LOAN_TO_PHONE,
											MyAppDbAdapter.KEY_PRELOAN, // Preloan
											MyAppDbAdapter.KEY_BOOK_EDITORID,
											MyAppDbAdapter.KEY_BOOK_LANGUAGEID,
											MyAppDbAdapter.KEY_BOOK_PUBLISHERID,
											MyAppDbAdapter.KEY_BOOK_SHELFID,
											MyAppDbAdapter.KEY_BOOK_INFO_1, // Bookinfo1
											MyAppDbAdapter.KEY_BOOK_INFO_2, // Bookinfo2
											MyAppDbAdapter.KEY_BOOK_INFO_3, // Bookinfo3
											MyAppDbAdapter.KEY_BOOK_INFO_4, // Bookinfo4
											MyAppDbAdapter.KEY_BOOK_INFO_5, // Bookinfo5
											MyAppDbAdapter.KEY_DESCRIPTION, }, null, null,
									null,
									null, null);
			} else {
				mCursor = this.sqliteDBObj
						.query(	true, MyAppDbAdapter.MY_BOOK_DB_TABLE, new String[] {
											MyAppDbAdapter.KEY_ROWID,
											MyAppDbAdapter.KEY_TITLE,
											MyAppDbAdapter.KEY_SUBTITLE, // Subtitle
											MyAppDbAdapter.KEY_ISBN, // ISBN
											MyAppDbAdapter.KEY_YEAR, // Year
											MyAppDbAdapter.KEY_EDITION, // Edition
											MyAppDbAdapter.KEY_COVER_LINK, // Coverlink
											MyAppDbAdapter.KEY_COVER_TYPE, // Covertype
											MyAppDbAdapter.KEY_OWN_CODE, // Owncode
											MyAppDbAdapter.KEY_PURCHASE_PRICE,
											MyAppDbAdapter.KEY_CURRENT_PRICE,
											MyAppDbAdapter.KEY_PAGE_NUMBER, // Pagenumber
											MyAppDbAdapter.KEY_RECOMMEND_FOR,
											MyAppDbAdapter.KEY_LOAN_TO, // Loanto
											MyAppDbAdapter.KEY_LOAN_DATE, // Loandate
											MyAppDbAdapter.KEY_LOAN_TO_PHONE,
											MyAppDbAdapter.KEY_PRELOAN, // Preloan
											MyAppDbAdapter.KEY_BOOK_EDITORID,
											MyAppDbAdapter.KEY_BOOK_LANGUAGEID,
											MyAppDbAdapter.KEY_BOOK_PUBLISHERID,
											MyAppDbAdapter.KEY_BOOK_SHELFID,
											MyAppDbAdapter.KEY_BOOK_INFO_1, // Bookinfo1
											MyAppDbAdapter.KEY_BOOK_INFO_2, // Bookinfo2
											MyAppDbAdapter.KEY_BOOK_INFO_3, // Bookinfo3
											MyAppDbAdapter.KEY_BOOK_INFO_4, // Bookinfo4
											MyAppDbAdapter.KEY_BOOK_INFO_5, // Bookinfo5
											MyAppDbAdapter.KEY_DESCRIPTION, },
									MyAppDbAdapter.KEY_TITLE + " LIKE '%" + inputText
											+ "%'", null, null, null, null, null);
			}
			if (mCursor != null) {
				mCursor.moveToFirst();
				return mCursor;
			} else {
				return null;
			}
		} catch (SQLiteException error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchBookByName",
										"SQLiteException - exception thrown fetching book");
			errExcpError = null;
			return null;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchBookByName",
										"exception - exception thrown fetching book");
			errExcpError = null;
			return null;
		}
	}
	
	protected Cursor fetchBookonGenre(int bggid) throws SQLException {
		Cursor mGenreCursor = null;
		try {
			mGenreCursor = this.sqliteDBObj
					.rawQuery(	"SELECT * FROM book_table, book_genre_table "
											+ "WHERE book_table.rowid = book_genre_table.bgbookid AND book_genre_table.bggenreid = "
											+ bggid + " "
											+ "GROUP BY book_genre_table.bgbookid", null);
			if (mGenreCursor != null) {
				mGenreCursor.moveToFirst();
				return mGenreCursor;
			} else {
				return null;
			}
		} catch (SQLiteException error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchBookbyGenre",
										"SQLiteException - exception thrown fetching book");
			errExcpError = null;
			return null;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchBookbyGenre",
										"exception - exception thrown fetching book");
			errExcpError = null;
			return null;
		}
	}
	
	protected Cursor fetchBookonbookseller(int bsbid) throws SQLException {
		Cursor mbooksellerCursor = null;
		try {
			mbooksellerCursor = this.sqliteDBObj
					.rawQuery(	"SELECT * FROM book_table, book_bookseller_table "
											+ "WHERE book_table.rowid = book_bookseller_table.bsbookid AND book_bookseller_table.bsbooksellerid = "
											+ bsbid + " "
											+ "GROUP BY book_bookseller_table.bsbookid",
									null);
			if (mbooksellerCursor != null) {
				mbooksellerCursor.moveToFirst();
				return mbooksellerCursor;
			} else {
				return null;
			}
		} catch (SQLiteException error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchBookbybookseller",
										"SQLiteException - exception thrown fetching book");
			errExcpError = null;
			return null;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchBookbybookseller",
										"exception - exception thrown fetching book");
			errExcpError = null;
			return null;
		}
	}
	
	protected Cursor fetchBookonauthor(int babid) throws SQLException {
		Cursor mauthorCursor = null;
		try {
			mauthorCursor = this.sqliteDBObj
					.rawQuery(	"SELECT * FROM book_table, book_author_table "
											+ "WHERE book_table.rowid = book_author_table.babookid AND book_author_table.baauthorid = "
											+ babid + " "
											+ "GROUP BY book_author_table.babookid", null);
			if (mauthorCursor != null) {
				mauthorCursor.moveToFirst();
				return mauthorCursor;
			} else {
				return null;
			}
		} catch (SQLiteException error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchBookbyauthor",
										"SQLiteException - exception thrown fetching book");
			errExcpError = null;
			return null;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchBookbyauthor",
										"exception - exception thrown fetching book");
			errExcpError = null;
			return null;
		}
	}
	
	protected Cursor fetchBookoneditor(int bebid) throws SQLException {
		Cursor meditorCursor = null;
		try {
			meditorCursor = this.sqliteDBObj
					.rawQuery(	"SELECT * FROM book_table, editor_table "
											+ "WHERE editor_table.rowid = book_table.bookeditorid  AND editor_table.rowid = "
											+ bebid, null);
			if (meditorCursor != null) {
				meditorCursor.moveToFirst();
				return meditorCursor;
			} else {
				return null;
			}
		} catch (SQLiteException error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchBookbyeditor",
										"SQLiteException - exception thrown fetching book");
			errExcpError = null;
			return null;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchBookbyeditor",
										"exception - exception thrown fetching book");
			errExcpError = null;
			return null;
		}
	}
	
	protected Cursor fetchBookonpublisher(int bpbid) throws SQLException {
		Cursor mpublisherCursor = null;
		try {
			mpublisherCursor = this.sqliteDBObj
					.rawQuery(	"SELECT * FROM book_table, publisher_table "
											+ "WHERE publisher_table.rowid = book_table.bookpublisherid AND publisher_table.rowid = "
											+ bpbid, null);
			if (mpublisherCursor != null) {
				mpublisherCursor.moveToFirst();
				return mpublisherCursor;
			} else {
				return null;
			}
		} catch (SQLiteException error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchBookbypublisher",
										"SQLiteException - exception thrown fetching book");
			errExcpError = null;
			return null;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchBookbypublisher",
										"exception - exception thrown fetching book");
			errExcpError = null;
			return null;
		}
	}
	
	protected Cursor fetchBookonlanguage(int blbid) throws SQLException {
		Cursor mlanguageCursor = null;
		try {
			mlanguageCursor = this.sqliteDBObj
					.rawQuery(	"SELECT * FROM book_table, language_table "
											+ "WHERE language_table.rowid = book_table.booklanguageid  AND language_table.rowid = "
											+ blbid, null);
			if (mlanguageCursor != null) {
				mlanguageCursor.moveToFirst();
				return mlanguageCursor;
			} else {
				return null;
			}
		} catch (SQLiteException error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchBookbylanguage",
										"SQLiteException - exception thrown fetching book");
			errExcpError = null;
			return null;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchBookbylanguage",
										"exception - exception thrown fetching book");
			errExcpError = null;
			return null;
		}
	}
	
	protected Cursor fetchBookonshelf(int sbid) throws SQLException {
		Cursor mshelfCursor = null;
		try {
			mshelfCursor = this.sqliteDBObj
					.rawQuery(	"SELECT * FROM book_table, shelf_table "
											+ "WHERE shelf_table.rowid = book_table.bookshelfid AND shelf_table.rowid = "
											+ sbid, null);
			if (mshelfCursor != null) {
				mshelfCursor.moveToFirst();
				return mshelfCursor;
			} else {
				return null;
			}
		} catch (SQLiteException error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchBookbyshelf",
										"SQLiteException - exception thrown fetching book");
			errExcpError = null;
			return null;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchBookbyshelf",
										"exception - exception thrown fetching book");
			errExcpError = null;
			return null;
		}
	}
	
	protected Cursor fetchAuthor(int intSortOpt) throws SQLException {
		String strOrderBy = "";
		Cursor mAuthorCursor = null;
		try {
			strOrderBy = listAuthorOrderByOpts(intSortOpt);
			mAuthorCursor = this.sqliteDBObj
					.query(	MyAppDbAdapter.MY_AUTHOR_DB_TABLE, new String[] {
										MyAppDbAdapter.KEY_ROWID,
										MyAppDbAdapter.KEY_AUTHORFULLNAME,
										MyAppDbAdapter.KEY_AUTHORHOMEPAGE,
										MyAppDbAdapter.KEY_AUTHOREMAIL, }, null, null,
								null, null,
								strOrderBy);
			if (mAuthorCursor != null) {
				mAuthorCursor.moveToFirst();
				return mAuthorCursor;
			} else {
				return null;
			}// end if (mAuthorCursor != null)
		} catch (SQLiteException error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchAuthor",
										"SQLiteException - exception thrown fetching author");
			errExcpError = null;
			return null;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchAuthor",
										"exception - exception thrown fetching author");
			errExcpError = null;
			return null;
		}
	}
	
	protected Cursor fetchAuthorEntry(long rowId) throws SQLException {
		Cursor mCursor = null;
		try {
			mCursor = this.sqliteDBObj
					.query(true, MyAppDbAdapter.MY_AUTHOR_DB_TABLE, new String[] {
							MyAppDbAdapter.KEY_ROWID,
							MyAppDbAdapter.KEY_AUTHORFULLNAME,
							MyAppDbAdapter.KEY_AUTHORHOMEPAGE,
							MyAppDbAdapter.KEY_AUTHOREMAIL }, MyAppDbAdapter.KEY_ROWID
							+ "=" + rowId, null, null, null, null, null);
			if (mCursor != null) {
				mCursor.moveToFirst();
				return mCursor;
			} else {
				return null;
			}// end if (mCursor != null)
		} catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.fetchListEntry",
												"ListEntry query");
			errExcpError = null;
			return null;
		}// end try/catch (SQLException error)
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.fetchListEntry",
												"ListEntry query");
			errExcpError = null;
			return null;
		}
	}// end fetchListEntry(long rowId)
	
	protected Cursor fetchAuthorByName(String inputText) throws SQLException {
		Cursor mAuthorCursor = null;
		try {
			if (inputText == null || inputText.length() == 0) {
				mAuthorCursor = this.sqliteDBObj
						.query(	MyAppDbAdapter.MY_AUTHOR_DB_TABLE, new String[] {
											MyAppDbAdapter.KEY_ROWID,
											MyAppDbAdapter.KEY_AUTHORFULLNAME,
											MyAppDbAdapter.KEY_AUTHORHOMEPAGE,
											MyAppDbAdapter.KEY_AUTHOREMAIL, }, null, null,
									null,
									null, null);
			} else {
				mAuthorCursor = this.sqliteDBObj
						.query(	true, MyAppDbAdapter.MY_AUTHOR_DB_TABLE,
									new String[] { MyAppDbAdapter.KEY_ROWID,
											MyAppDbAdapter.KEY_AUTHORFULLNAME,
											MyAppDbAdapter.KEY_AUTHORHOMEPAGE,
											MyAppDbAdapter.KEY_AUTHOREMAIL, },
									MyAppDbAdapter.KEY_AUTHORFULLNAME + " LIKE '%"
											+ inputText + "%'", null, null, null, null,
									null);
			}
			if (mAuthorCursor != null) {
				mAuthorCursor.moveToFirst();
				return mAuthorCursor;
			} else {
				return null;
			}
		} catch (SQLiteException error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchAuthorByName",
										"SQLiteException - exception thrown fetching author");
			errExcpError = null;
			return null;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchAuthorByName",
										"exception - exception thrown fetching author");
			errExcpError = null;
			return null;
		}
	}
	
	protected Cursor fetchBookAuthor(int babid) throws SQLException {
		Cursor mAuthorCursor = null;
		try {
			mAuthorCursor = this.sqliteDBObj
					.rawQuery(	"SELECT * FROM author_table, book_author_table "
											+ "WHERE author_table.rowid = book_author_table.baauthorid AND book_author_table.babookid = "
											+ babid + " "
											+ "GROUP BY book_author_table.baauthorid",
									null);
			if (mAuthorCursor != null) {
				mAuthorCursor.moveToFirst();
				return mAuthorCursor;
			} else {
				return null;
			}
		} catch (SQLiteException error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchAuthorByName",
										"SQLiteException - exception thrown fetching author");
			errExcpError = null;
			return null;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchAuthorByName",
										"exception - exception thrown fetching author");
			errExcpError = null;
			return null;
		}
	}
	
	protected Cursor fetchBookAuthorEntry(long rowId) throws SQLException {
		Cursor mCursor = null;
		try {
			mCursor = this.sqliteDBObj
					.query(	true, MyAppDbAdapter.MY_BOOK_AUTHOR_DB_TABLE,
								new String[] { MyAppDbAdapter.KEY_ROWID,
										MyAppDbAdapter.KEY_BA_AUTHORID,
										MyAppDbAdapter.KEY_BA_BOOKID },
								MyAppDbAdapter.KEY_ROWID + "=" + rowId, null, null,
								null, null, null);
			if (mCursor != null) {
				mCursor.moveToFirst();
				return mCursor;
			} else {
				return null;
			}// end if (mCursor != null)
		} catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.fetchListEntry",
												"ListEntry query");
			errExcpError = null;
			return null;
		}// end try/catch (SQLException error)
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.fetchListEntry",
												"ListEntry query");
			errExcpError = null;
			return null;
		}
	}// end fetchListEntry(long rowId)
	
	protected Cursor fetchBookseller(int intSortOpt) throws SQLException {
		String strOrderBy = "";
		Cursor mBookCursor = null;
		try {
			strOrderBy = listBooksellerOrderByOpts(intSortOpt);
			mBookCursor = this.sqliteDBObj
					.query(	MyAppDbAdapter.MY_BOOKSELLER_DB_TABLE, new String[] {
										MyAppDbAdapter.KEY_ROWID,
										MyAppDbAdapter.KEY_BOOKSELLER,
										MyAppDbAdapter.KEY_BOOKSELLERADDRESS,
										MyAppDbAdapter.KEY_BOOKSELLERHOMEPAGE,
										MyAppDbAdapter.KEY_EMAIL_NEWS,
										MyAppDbAdapter.KEY_MAIL_NEWS,
										MyAppDbAdapter.KEY_BOOKSELLEREMAIL,
										MyAppDbAdapter.KEY_BOOKSELLER_INFO_1, }, null,
								null, null,
								null, strOrderBy);
			if (mBookCursor != null) {
				mBookCursor.moveToFirst();
				return mBookCursor;
			} else {
				return null;
			}// end if (mBookCursor != null)
		} catch (SQLiteException error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchBookseller",
										"SQLiteException - exception thrown fetching bookseller");
			errExcpError = null;
			return null;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchBookseller",
										"exception - exception thrown fetching bookseller");
			errExcpError = null;
			return null;
		}
	}
	
	protected Cursor fetchBooksellerEntry(long rowId) throws SQLException {
		Cursor mCursor = null;
		try {
			mCursor = this.sqliteDBObj
					.query(	true, MyAppDbAdapter.MY_BOOKSELLER_DB_TABLE,
								new String[] { MyAppDbAdapter.KEY_ROWID,
										MyAppDbAdapter.KEY_BOOKSELLER,
										MyAppDbAdapter.KEY_BOOKSELLERADDRESS,
										MyAppDbAdapter.KEY_BOOKSELLERHOMEPAGE,
										MyAppDbAdapter.KEY_EMAIL_NEWS,
										MyAppDbAdapter.KEY_MAIL_NEWS,
										MyAppDbAdapter.KEY_BOOKSELLEREMAIL,
										MyAppDbAdapter.KEY_BOOKSELLER_INFO_1 },
								MyAppDbAdapter.KEY_ROWID + "=" + rowId, null, null,
								null, null, null);
			if (mCursor != null) {
				mCursor.moveToFirst();
				return mCursor;
			} else {
				return null;
			}// end if (mCursor != null)
		} catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.fetchListEntry",
												"ListEntry query");
			errExcpError = null;
			return null;
		}// end try/catch (SQLException error)
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.fetchListEntry",
												"ListEntry query");
			errExcpError = null;
			return null;
		}
	}// end fetchListEntry(long rowId)
	
	protected Cursor fetchBooksellerByName(String inputText) throws SQLException {
		Cursor mCursor = null;
		try {
			if (inputText == null || inputText.length() == 0) {
				mCursor = this.sqliteDBObj
						.query(	MyAppDbAdapter.MY_BOOKSELLER_DB_TABLE, new String[] {
											MyAppDbAdapter.KEY_ROWID,
											MyAppDbAdapter.KEY_BOOKSELLER,
											MyAppDbAdapter.KEY_BOOKSELLERADDRESS,
											MyAppDbAdapter.KEY_BOOKSELLERHOMEPAGE,
											MyAppDbAdapter.KEY_EMAIL_NEWS,
											MyAppDbAdapter.KEY_MAIL_NEWS,
											MyAppDbAdapter.KEY_BOOKSELLEREMAIL,
											MyAppDbAdapter.KEY_BOOKSELLER_INFO_1 }, null,
									null,
									null, null, null);
			} else {
				mCursor = this.sqliteDBObj
						.query(	true, MyAppDbAdapter.MY_BOOKSELLER_DB_TABLE,
									new String[] { MyAppDbAdapter.KEY_ROWID,
											MyAppDbAdapter.KEY_BOOKSELLER,
											MyAppDbAdapter.KEY_BOOKSELLERADDRESS,
											MyAppDbAdapter.KEY_BOOKSELLERHOMEPAGE,
											MyAppDbAdapter.KEY_EMAIL_NEWS,
											MyAppDbAdapter.KEY_MAIL_NEWS,
											MyAppDbAdapter.KEY_BOOKSELLEREMAIL,
											MyAppDbAdapter.KEY_BOOKSELLER_INFO_1 },
									MyAppDbAdapter.KEY_BOOKSELLER + " LIKE '%"
											+ inputText + "%'", null, null, null, null,
									null);
			}
			if (mCursor != null) {
				mCursor.moveToFirst();
				return mCursor;
			} else {
				return null;
			}
		} catch (SQLiteException error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchBooksellerByName",
										"SQLiteException - exception thrown fetching bookseller");
			errExcpError = null;
			return null;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchBooksellerByName",
										"exception - exception thrown fetching bookseller");
			errExcpError = null;
			return null;
		}
	}
	
	protected Cursor fetchBookBookseller(int bsbid) throws SQLException {
		Cursor mBooksellerCursor = null;
		try {
			mBooksellerCursor = this.sqliteDBObj
					.rawQuery(	"SELECT * FROM bookseller_table, book_bookseller_table "
											+ "WHERE bookseller_table.rowid = book_bookseller_table.bsbooksellerid AND book_bookseller_table.bsbookid = "
											+ bsbid
											+ " "
											+ "GROUP BY book_bookseller_table.bsbooksellerid",
									null);
			if (mBooksellerCursor != null) {
				mBooksellerCursor.moveToFirst();
				return mBooksellerCursor;
			} else {
				return null;
			}
		} catch (SQLiteException error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchBooksellerByName",
										"SQLiteException - exception thrown fetching bookseller");
			errExcpError = null;
			return null;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchBookBookseller",
										"exception - exception thrown fetching bookseller");
			errExcpError = null;
			return null;
		}
	}
	
	protected Cursor fetchBookBooksellerEntry(long rowId) throws SQLException {
		Cursor mCursor = null;
		try {
			mCursor = this.sqliteDBObj
					.query(	true, MyAppDbAdapter.MY_BOOK_BOOKSELLER_DB_TABLE,
								new String[] { MyAppDbAdapter.KEY_ROWID,
										MyAppDbAdapter.KEY_BS_BOOKSELLERID,
										MyAppDbAdapter.KEY_BS_BOOKID },
								MyAppDbAdapter.KEY_ROWID + "=" + rowId, null, null,
								null, null, null);
			if (mCursor != null) {
				mCursor.moveToFirst();
				return mCursor;
			} else {
				return null;
			}// end if (mCursor != null)
		} catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.fetchListEntry",
												"ListEntry query");
			errExcpError = null;
			return null;
		}// end try/catch (SQLException error)
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.fetchListEntry",
												"ListEntry query");
			errExcpError = null;
			return null;
		}
	}// end fetchListEntry(long rowId)
	
	protected Cursor fetchEditor(int intSortOpt) throws SQLException {
		String strOrderBy = "";
		Cursor mBookCursor = null;
		try {
			strOrderBy = listEditorOrderByOpts(intSortOpt);
			mBookCursor = this.sqliteDBObj
					.query(	MyAppDbAdapter.MY_EDITOR_DB_TABLE, new String[] {
										MyAppDbAdapter.KEY_ROWID,
										MyAppDbAdapter.KEY_EDITORFULLNAME,
										MyAppDbAdapter.KEY_EDITORHOMEPAGE,
										MyAppDbAdapter.KEY_EDITOREMAIL, }, null, null,
								null, null,
								strOrderBy);
			if (mBookCursor != null) {
				mBookCursor.moveToFirst();
				return mBookCursor;
			} else {
				return null;
			}// end if (mBookCursor != null)
		} catch (SQLiteException error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchEditor",
										"SQLiteException - exception thrown fetching editor");
			errExcpError = null;
			return null;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchEditor",
										"exception - exception thrown fetching editor");
			errExcpError = null;
			return null;
		}
	}
	
	protected Cursor fetchEditorEntry(long rowId) throws SQLException {
		Cursor mCursor = null;
		try {
			mCursor = this.sqliteDBObj
					.query(	true, MyAppDbAdapter.MY_EDITOR_DB_TABLE, new String[] {
										MyAppDbAdapter.KEY_ROWID,
										MyAppDbAdapter.KEY_EDITORFULLNAME,
										MyAppDbAdapter.KEY_EDITORHOMEPAGE, // Subtitle
										MyAppDbAdapter.KEY_EDITOREMAIL, },
								MyAppDbAdapter.KEY_ROWID + "=" + rowId, null, null,
								null, null, null);
			if (mCursor != null) {
				mCursor.moveToFirst();
				return mCursor;
			} else {
				return null;
			}// end if (mCursor != null)
		} catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.fetchListEntry",
												"ListEntry query");
			errExcpError = null;
			return null;
		}// end try/catch (SQLException error)
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.fetchListEntry",
												"ListEntry query");
			errExcpError = null;
			return null;
		}
	}// end fetchListEntry(long rowId)
	
	protected Cursor fetchEditorByName(String inputText) throws SQLException {
		Cursor mCursor = null;
		try {
			if (inputText == null || inputText.length() == 0) {
				mCursor = this.sqliteDBObj
						.query(	MyAppDbAdapter.MY_EDITOR_DB_TABLE, new String[] {
											MyAppDbAdapter.KEY_ROWID,
											MyAppDbAdapter.KEY_EDITORFULLNAME,
											MyAppDbAdapter.KEY_EDITORHOMEPAGE,
											MyAppDbAdapter.KEY_EDITOREMAIL, }, null, null,
									null,
									null, null);
			} else {
				mCursor = this.sqliteDBObj
						.query(	true, MyAppDbAdapter.MY_EDITOR_DB_TABLE,
									new String[] { MyAppDbAdapter.KEY_ROWID,
											MyAppDbAdapter.KEY_EDITORFULLNAME,
											MyAppDbAdapter.KEY_EDITORHOMEPAGE,
											MyAppDbAdapter.KEY_EDITOREMAIL, },
									MyAppDbAdapter.KEY_EDITORFULLNAME + " LIKE '%"
											+ inputText + "%'", null, null, null, null,
									null);
			}
			if (mCursor != null) {
				mCursor.moveToFirst();
				return mCursor;
			} else {
				return null;
			}
		} catch (SQLiteException error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchEditorByName",
										"SQLiteException - exception thrown fetching editor");
			errExcpError = null;
			return null;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchEditorByName",
										"exception - exception thrown fetching editor");
			errExcpError = null;
			return null;
		}
	}
	
	protected Cursor fetchPublisher(int intSortOpt) throws SQLException {
		String strOrderBy = "";
		Cursor mBookCursor = null;
		try {
			strOrderBy = listPublisherOrderByOpts(intSortOpt);
			mBookCursor = this.sqliteDBObj
					.query(	MyAppDbAdapter.MY_PUBLISHER_DB_TABLE, new String[] {
										MyAppDbAdapter.KEY_ROWID,
										MyAppDbAdapter.KEY_PUBLISHER,
										MyAppDbAdapter.KEY_PUBLISHERADDRESS,
										MyAppDbAdapter.KEY_PUBLISHERHOMEPAGE,
										MyAppDbAdapter.KEY_PUBLISHEREMAIL,
										MyAppDbAdapter.KEY_PUBLISHER_INFO_1, }, null,
								null, null,
								null, strOrderBy);
			if (mBookCursor != null) {
				mBookCursor.moveToFirst();
				return mBookCursor;
			} else {
				return null;
			}// end if (mBookCursor != null)
		} catch (SQLiteException error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchPublisher",
										"SQLiteException - exception thrown fetching editor");
			errExcpError = null;
			return null;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchPublisher",
										"exception - exception thrown fetching editor");
			errExcpError = null;
			return null;
		}
	}
	
	protected Cursor fetchPublisherEntry(long rowId) throws SQLException {
		Cursor mCursor = null;
		try {
			mCursor = this.sqliteDBObj
					.query(	true, MyAppDbAdapter.MY_PUBLISHER_DB_TABLE,
								new String[] {
										MyAppDbAdapter.KEY_ROWID,
										MyAppDbAdapter.KEY_PUBLISHER,
										MyAppDbAdapter.KEY_PUBLISHERADDRESS,
										MyAppDbAdapter.KEY_PUBLISHERHOMEPAGE, // Subtitle
										MyAppDbAdapter.KEY_PUBLISHEREMAIL,
										MyAppDbAdapter.KEY_PUBLISHER_INFO_1, },
								MyAppDbAdapter.KEY_ROWID + "=" + rowId, null, null,
								null, null, null);
			if (mCursor != null) {
				mCursor.moveToFirst();
				return mCursor;
			} else {
				return null;
			}// end if (mCursor != null)
		} catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.fetchListEntry",
												"ListEntry query");
			errExcpError = null;
			return null;
		}// end try/catch (SQLException error)
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.fetchListEntry",
												"ListEntry query");
			errExcpError = null;
			return null;
		}
	}// end fetchListEntry(long rowId)
	
	protected Cursor fetchPublisherByName(String inputText) throws SQLException {
		Cursor mCursor = null;
		try {
			if (inputText == null || inputText.length() == 0) {
				mCursor = this.sqliteDBObj
						.query(	MyAppDbAdapter.MY_PUBLISHER_DB_TABLE, new String[] {
											MyAppDbAdapter.KEY_ROWID,
											MyAppDbAdapter.KEY_PUBLISHER,
											MyAppDbAdapter.KEY_PUBLISHERADDRESS,
											MyAppDbAdapter.KEY_PUBLISHERHOMEPAGE,
											MyAppDbAdapter.KEY_PUBLISHEREMAIL,
											MyAppDbAdapter.KEY_PUBLISHER_INFO_1, }, null,
									null,
									null, null, null);
			} else {
				mCursor = this.sqliteDBObj
						.query(	true, MyAppDbAdapter.MY_PUBLISHER_DB_TABLE,
									new String[] { MyAppDbAdapter.KEY_ROWID,
											MyAppDbAdapter.KEY_PUBLISHER,
											MyAppDbAdapter.KEY_PUBLISHERADDRESS,
											MyAppDbAdapter.KEY_PUBLISHERHOMEPAGE,
											MyAppDbAdapter.KEY_PUBLISHEREMAIL,
											MyAppDbAdapter.KEY_PUBLISHER_INFO_1, },
									MyAppDbAdapter.KEY_PUBLISHER + " LIKE '%"
											+ inputText + "%'", null, null, null, null,
									null);
			}
			if (mCursor != null) {
				mCursor.moveToFirst();
				return mCursor;
			} else {
				return null;
			}
		} catch (SQLiteException error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchPublisherByName",
										"SQLiteException - exception thrown fetching publisher");
			errExcpError = null;
			return null;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchPublisherByName",
										"exception - exception thrown fetching publisher");
			errExcpError = null;
			return null;
		}
	}
	
	protected Cursor fetchLanguage() throws SQLException {
		Cursor mLanguageCursor = null;
		try {
			mLanguageCursor = this.sqliteDBObj
					.query(	MyAppDbAdapter.MY_LANGUAGE_DB_TABLE, new String[] {
										MyAppDbAdapter.KEY_ROWID,
										MyAppDbAdapter.KEY_LANGUAGE,
										MyAppDbAdapter.KEY_LANGUAGE_INFO_1, }, null,
								null, null,
								null, MyAppDbAdapter.KEY_ROWID + " ASC");
			if (mLanguageCursor != null) {
				mLanguageCursor.moveToFirst();
				return mLanguageCursor;
			} else {
				return null;
			}// end if (mLanguageCursor != null)
		} catch (SQLiteException error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchLanguage",
										"SQLiteException - exception thrown fetching language");
			errExcpError = null;
			return null;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchLanguage",
										"exception - exception thrown fetching language");
			errExcpError = null;
			return null;
		}
	}
	
	protected Cursor fetchLanguageEntry(long rowId) throws SQLException {
		Cursor mCursor = null;
		try {
			mCursor = this.sqliteDBObj
					.query(	true, MyAppDbAdapter.MY_LANGUAGE_DB_TABLE,
								new String[] { MyAppDbAdapter.KEY_ROWID,
										MyAppDbAdapter.KEY_LANGUAGE,
										MyAppDbAdapter.KEY_LANGUAGE_INFO_1, // Subtitle
								}, MyAppDbAdapter.KEY_ROWID + "=" + rowId, null, null,
								null,
								null, null);
			if (mCursor != null) {
				mCursor.moveToFirst();
				return mCursor;
			} else {
				return null;
			}// end if (mCursor != null)
		} catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.fetchListEntry",
												"ListEntry query");
			errExcpError = null;
			return null;
		}// end try/catch (SQLException error)
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.fetchListEntry",
												"ListEntry query");
			errExcpError = null;
			return null;
		}
	}// end fetchListEntry(long rowId)
	
	protected Cursor fetchStorage() throws SQLException {
		Cursor mStorageCursor = null;
		try {
			mStorageCursor = this.sqliteDBObj
					.query(	MyAppDbAdapter.MY_STORAGE_DB_TABLE, new String[] {
										MyAppDbAdapter.KEY_ROWID,
										MyAppDbAdapter.KEY_STORAGE,
										MyAppDbAdapter.KEY_STORAGEADDRESS,
										MyAppDbAdapter.KEY_STORAGE_INFO_1, }, null, null,
								null,
								null, MyAppDbAdapter.KEY_ROWID + " ASC");
			if (mStorageCursor != null) {
				mStorageCursor.moveToFirst();
				return mStorageCursor;
			} else {
				return null;
			}// end if (mStorageCursor != null)
		} catch (SQLiteException error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchStorage",
										"SQLiteException - exception thrown fetching language");
			errExcpError = null;
			return null;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchStorage",
										"exception - exception thrown fetching language");
			errExcpError = null;
			return null;
		}
	}
	
	protected Cursor fetchStorageEntry(long rowId) throws SQLException {
		Cursor mCursor = null;
		try {
			mCursor = this.sqliteDBObj
					.query(	true, MyAppDbAdapter.MY_STORAGE_DB_TABLE, new String[] {
										MyAppDbAdapter.KEY_ROWID,
										MyAppDbAdapter.KEY_STORAGE,
										MyAppDbAdapter.KEY_STORAGEADDRESS,
										MyAppDbAdapter.KEY_STORAGE_INFO_1, // Subtitle
								}, MyAppDbAdapter.KEY_ROWID + "=" + rowId, null, null,
								null,
								null, null);
			if (mCursor != null) {
				mCursor.moveToFirst();
				return mCursor;
			} else {
				return null;
			}// end if (mCursor != null)
		} catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.fetchListEntry",
												"ListEntry query");
			errExcpError = null;
			return null;
		}// end try/catch (SQLException error)
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.fetchListEntry",
												"ListEntry query");
			errExcpError = null;
			return null;
		}
	}// end fetchListEntry(long rowId)
	
	protected Cursor fetchRack() throws SQLException {
		Cursor mRackCursor = null;
		try {
			mRackCursor = this.sqliteDBObj
					.query(	MyAppDbAdapter.MY_RACK_DB_TABLE, new String[] {
										MyAppDbAdapter.KEY_ROWID,
										MyAppDbAdapter.KEY_RACK_LABEL,
										MyAppDbAdapter.KEY_RACK_ROOMID,
										MyAppDbAdapter.KEY_RACK_INFO_1 }, null, null,
								null, null,
								MyAppDbAdapter.KEY_RACK_LABEL + " ASC");
			if (mRackCursor != null) {
				mRackCursor.moveToFirst();
				return mRackCursor;
			} else {
				return null;
			}// end if (mRackCursor != null)
		} catch (SQLiteException error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchRack",
										"SQLiteException - exception thrown fetching rack");
			errExcpError = null;
			return null;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchRack",
										"exception - exception thrown fetching rack");
			errExcpError = null;
			return null;
		}
	}
	
	protected Cursor fetchRackEntry(long rowId) throws SQLException {
		Cursor mCursor = null;
		try {
			mCursor = this.sqliteDBObj
					.query(	true, MyAppDbAdapter.MY_RACK_DB_TABLE, new String[] {
										MyAppDbAdapter.KEY_ROWID,
										MyAppDbAdapter.KEY_RACK_LABEL,
										MyAppDbAdapter.KEY_RACK_INFO_1,
										MyAppDbAdapter.KEY_RACK_ROOMID, },
								MyAppDbAdapter.KEY_ROWID + "=" + rowId, null, null,
								null, null, null);
			if (mCursor != null) {
				mCursor.moveToFirst();
				return mCursor;
			} else {
				return null;
			}// end if (mCursor != null)
		} catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.fetchListEntry",
												"ListEntry query");
			errExcpError = null;
			return null;
		}// end try/catch (SQLException error)
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.fetchListEntry",
												"ListEntry query");
			errExcpError = null;
			return null;
		}
	}// end fetchListEntry(long rowId)
	
	protected Cursor fetchRoom() throws SQLException {
		// String strOrderBy = "";
		Cursor mBookCursor = null;
		try {
			// strOrderBy = listRoomOrderByOpts(intSortOpt);
			mBookCursor = this.sqliteDBObj
					.query(	MyAppDbAdapter.MY_ROOM_DB_TABLE, new String[] {
										MyAppDbAdapter.KEY_ROWID,
										MyAppDbAdapter.KEY_ROOM_NUMBER,
										MyAppDbAdapter.KEY_ROOM_STORAGEID,
										MyAppDbAdapter.KEY_ROOM_INFO_1, }, null, null,
								null, null,
								MyAppDbAdapter.KEY_ROOM_NUMBER + " ASC");
			if (mBookCursor != null) {
				mBookCursor.moveToFirst();
				return mBookCursor;
			} else {
				return null;
			}// end if (mBookCursor != null)
		} catch (SQLiteException error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchRoom",
										"SQLiteException - exception thrown fetching room");
			errExcpError = null;
			return null;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchRoom",
										"exception - exception thrown fetching room");
			errExcpError = null;
			return null;
		}
	}
	
	protected Cursor fetchRoomEntry(long rowId) throws SQLException {
		Cursor mCursor = null;
		try {
			mCursor = this.sqliteDBObj
					.query(	true, MyAppDbAdapter.MY_ROOM_DB_TABLE, new String[] {
										MyAppDbAdapter.KEY_ROWID,
										MyAppDbAdapter.KEY_ROOM_NUMBER,
										MyAppDbAdapter.KEY_ROOM_INFO_1,
										MyAppDbAdapter.KEY_ROOM_STORAGEID, },
								MyAppDbAdapter.KEY_ROWID + "=" + rowId, null, null,
								null, null, null);
			if (mCursor != null) {
				mCursor.moveToFirst();
				return mCursor;
			} else {
				return null;
			}// end if (mCursor != null)
		} catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.fetchListEntry",
												"ListEntry query");
			errExcpError = null;
			return null;
		}// end try/catch (SQLException error)
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.fetchListEntry",
												"ListEntry query");
			errExcpError = null;
			return null;
		}
	}// end fetchListEntry(long rowId)
	
	protected Cursor fetchShelf() throws SQLException {
		Cursor mShelfCursor = null;
		try {
			mShelfCursor = this.sqliteDBObj
					.query(	MyAppDbAdapter.MY_SHELF_DB_TABLE, new String[] {
										MyAppDbAdapter.KEY_ROWID,
										MyAppDbAdapter.KEY_SHELF_LABEL,
										MyAppDbAdapter.KEY_SHELF_RACKID,
										MyAppDbAdapter.KEY_SHELF_INFO_1, }, null, null,
								null,
								null, MyAppDbAdapter.KEY_SHELF_LABEL + " ASC");
			if (mShelfCursor != null) {
				mShelfCursor.moveToFirst();
				return mShelfCursor;
			} else {
				return null;
			}// end if (mShelfCursor != null)
		} catch (SQLiteException error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchShelf",
										"SQLiteException - exception thrown fetching shelf");
			errExcpError = null;
			return null;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchShelf",
										"exception - exception thrown fetching shelf");
			errExcpError = null;
			return null;
		}
	}
	
	protected Cursor fetchShelfEntry(long rowId) throws SQLException {
		Cursor mCursor = null;
		try {
			mCursor = this.sqliteDBObj
					.query(	true, MyAppDbAdapter.MY_SHELF_DB_TABLE, new String[] {
										MyAppDbAdapter.KEY_ROWID,
										MyAppDbAdapter.KEY_SHELF_LABEL,
										MyAppDbAdapter.KEY_SHELF_RACKID,
										MyAppDbAdapter.KEY_SHELF_INFO_1, },
								MyAppDbAdapter.KEY_ROWID + "=" + rowId, null, null,
								null, null, null);
			if (mCursor != null) {
				mCursor.moveToFirst();
				return mCursor;
			} else {
				return null;
			}// end if (mCursor != null)
		} catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.fetchListEntry",
												"ListEntry query");
			errExcpError = null;
			return null;
		}// end try/catch (SQLException error)
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.fetchListEntry",
												"ListEntry query");
			errExcpError = null;
			return null;
		}
	}// end fetchListEntry(long rowId)
	
	protected Cursor fetchGenre() throws SQLException {
		Cursor mBookCursor = null;
		try {
			mBookCursor = this.sqliteDBObj
					.query(	MyAppDbAdapter.MY_GENRE_DB_TABLE, new String[] {
										MyAppDbAdapter.KEY_ROWID,
										MyAppDbAdapter.KEY_GENRE,
										MyAppDbAdapter.KEY_GENRE_INFO_1, }, null, null,
								null,
								null, MyAppDbAdapter.KEY_ROWID + " ASC");
			if (mBookCursor != null) {
				mBookCursor.moveToFirst();
				return mBookCursor;
			} else {
				return null;
			}// end if (mBookCursor != null)
		} catch (SQLiteException error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchGenre",
										"SQLiteException - exception thrown fetching genre");
			errExcpError = null;
			return null;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchGenre",
										"exception - exception thrown fetching genre");
			errExcpError = null;
			return null;
		}
	}
	
	protected Cursor fetchBookGenre(int babid) throws SQLException {
		Cursor mGenreCursor = null;
		try {
			mGenreCursor = this.sqliteDBObj
					.rawQuery(	"SELECT * FROM genre_table, book_genre_table "
											+ "WHERE genre_table.rowid = book_genre_table.bggenreid AND book_genre_table.bgbookid = "
											+ babid + " "
											+ "GROUP BY book_genre_table.bggenreid", null);
			if (mGenreCursor != null) {
				mGenreCursor.moveToFirst();
				return mGenreCursor;
			} else {
				return null;
			}
		} catch (SQLiteException error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchGenreByName",
										"SQLiteException - exception thrown fetching genre");
			errExcpError = null;
			return null;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchGenreByName",
										"exception - exception thrown fetching genre");
			errExcpError = null;
			return null;
		}
	}
	
	protected Cursor fetchGenreEntry(long rowId) throws SQLException {
		Cursor mCursor = null;
		try {
			mCursor = this.sqliteDBObj
					.query(	true, MyAppDbAdapter.MY_GENRE_DB_TABLE, new String[] {
										MyAppDbAdapter.KEY_ROWID,
										MyAppDbAdapter.KEY_GENRE,
										MyAppDbAdapter.KEY_GENRE_INFO_1, },
								MyAppDbAdapter.KEY_ROWID + "=" + rowId, null, null,
								null, null, null);
			if (mCursor != null) {
				mCursor.moveToFirst();
				return mCursor;
			} else {
				return null;
			}// end if (mCursor != null)
		} catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.fetchListEntry",
												"ListEntry query");
			errExcpError = null;
			return null;
		}// end try/catch (SQLException error)
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.fetchListEntry",
												"ListEntry query");
			errExcpError = null;
			return null;
		}
	}// end fetchListEntry(long rowId)
	
	protected Cursor fetchBookGenreEntry(long rowId) throws SQLException {
		Cursor mCursor = null;
		try {
			mCursor = this.sqliteDBObj
					.query(	true, MyAppDbAdapter.MY_BOOK_GENRE_DB_TABLE,
								new String[] { MyAppDbAdapter.KEY_ROWID,
										MyAppDbAdapter.KEY_BG_GENREID,
										MyAppDbAdapter.KEY_BG_BOOKID },
								MyAppDbAdapter.KEY_ROWID + "=" + rowId, null, null,
								null, null, null);
			if (mCursor != null) {
				mCursor.moveToFirst();
				return mCursor;
			} else {
				return null;
			}// end if (mCursor != null)
		} catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.fetchListEntry",
												"ListEntry query");
			errExcpError = null;
			return null;
		}// end try/catch (SQLException error)
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.fetchListEntry",
												"ListEntry query");
			errExcpError = null;
			return null;
		}
	}// end fetchListEntry(long rowI
	
	protected Cursor fetchReview() throws SQLException {
		Cursor mBookCursor = null;
		try {
			mBookCursor = this.sqliteDBObj
					.query(	MyAppDbAdapter.MY_REVIEW_DB_TABLE, new String[] {
										MyAppDbAdapter.KEY_ROWID,
										MyAppDbAdapter.KEY_REVIEW,
										MyAppDbAdapter.KEY_REVIEW_BOOKID,
										MyAppDbAdapter.KEY_REVIEW_AUTHORID,
										MyAppDbAdapter.KEY_REVIEW_INFO_1 }, null, null,
								null,
								null, MyAppDbAdapter.KEY_ROWID + " ASC");
			if (mBookCursor != null) {
				mBookCursor.moveToFirst();
				return mBookCursor;
			} else {
				return null;
			}// end if (mBookCursor != null)
		} catch (SQLiteException error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchReview",
										"SQLiteException - exception thrown fetching review");
			errExcpError = null;
			return null;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchReview",
										"exception - exception thrown fetching review");
			errExcpError = null;
			return null;
		}
	}
	
	protected Cursor fetchRent() throws SQLException {
		Cursor mRentCursor = null;
		try {
			mRentCursor = this.sqliteDBObj
					.rawQuery(	"SELECT * FROM book_table "
											+ "WHERE book_table.loanto IS NOT NULL AND book_table.loanto != '' OR book_table.loandate IS NOT NULL AND book_table.loandate != '' OR book_table.loantophone IS NOT NULL AND book_table.loantophone !=''   "
											+ "GROUP BY book_table.rowid", null);
			if (mRentCursor != null) {
				mRentCursor.moveToFirst();
				return mRentCursor;
			} else {
				return null;
			}
		} catch (SQLiteException error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchRent",
										"SQLiteException - exception thrown fetching Rent");
			errExcpError = null;
			return null;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					ctxContext);
			errExcpError
					.addToLogFile(	error, "MyAppDbSQL.fetchRent",
										"exception - exception thrown fetching Rent");
			errExcpError = null;
			return null;
		}
	}
	
	protected Cursor fetchReviewEntry(long rowId) throws SQLException {
		Cursor mCursor = null;
		try {
			mCursor = this.sqliteDBObj
					.query(	true, MyAppDbAdapter.MY_REVIEW_DB_TABLE, new String[] {
										MyAppDbAdapter.KEY_ROWID,
										MyAppDbAdapter.KEY_REVIEW,
										MyAppDbAdapter.KEY_REVIEW_BOOKID,
										MyAppDbAdapter.KEY_REVIEW_AUTHORID,
										MyAppDbAdapter.KEY_REVIEW_INFO_1 },
								MyAppDbAdapter.KEY_ROWID + "=" + rowId, null, null,
								null, null, null);
			if (mCursor != null) {
				mCursor.moveToFirst();
				return mCursor;
			} else {
				return null;
			}// end if (mCursor != null)
		} catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.fetchListEntry",
												"ListEntry query");
			errExcpError = null;
			return null;
		}// end try/catch (SQLException error)
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.fetchListEntry",
												"ListEntry query");
			errExcpError = null;
			return null;
		}
	}// end fetchListEntry(long rowId)
	
	/***************************************************************** exportTableQuery: Return a Cursor for a specific table in the database
	 * @param strTableName
	 * the specified table
	 * @param strOrderBy
	 * the selected ordering option
	 * @return Cursor containing filtered query results
	 * @throws SQLException
	 * if entry could not be found/retrieved */
	protected Cursor exportTableQuery(String strTableName, String strOrderBy) {
		Cursor mBookCursor = null;
		try {
			// get everything from the table
			String strSQL = "SELECT * FROM " + strTableName + " ORDER BY "
					+ strOrderBy;
			mBookCursor = this.sqliteDBObj.rawQuery(strSQL, null);
			if (mBookCursor != null) {
				mBookCursor.moveToFirst();
				return mBookCursor;
			} else {
				return null;
			}// end if (mBookCursor != null
		} catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.exportTableQuery",
												"creating export temporary Cursor");
			errExcpError = null;
			return null;
		}// end try/catch (SQLException error)
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.exportTableQuery",
												"creating export temporary Cursor");
			errExcpError = null;
			return null;
		}
	}// end exportTableQuery
	
	/*****************************************************************
	 *****************************************************************/
	/** createEntry: Creates a new entry in the database table
	 * @return True if the database insert succeeds
	 * @throws SQLException
	 * if entry could not be found/retrieved */
	protected Boolean createBookEntry(String txtBookTitle,
													String txtBookSubtitle,
													String txtBookIsbn, String txtBookYear,
													String txtBookEdition,
													String txtBookCoverlink,
													String txtBookCovertype,
													String txtBookOwncode,
													String txtBookPurchaseprice,
													String txtBookCurrentprice,
													String txtBookPagenumber,
													String txtBookRecommendfor,
													String txtBookLoanto,
													String txtBookLoantophone,
													String txtBookLoandate,
													String txtBookPreloan,
													int spinSpinnereditor,
													int spinSpinnerpublisher,
													int spinSpinnerlanguage,
													int spinSpinnerbookshelf,
													String txtBookinfo1,
													String txtBookinfo2,
													String txtBookinfo3,
													String txtBookinfo4,
													String txtBookinfo5,
													String txtBookDescription) {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			ContentValues initialValues = new ContentValues();
			initialValues.put(MyAppDbAdapter.KEY_TITLE, txtBookTitle);
			initialValues.put(MyAppDbAdapter.KEY_SUBTITLE, txtBookSubtitle);
			initialValues.put(MyAppDbAdapter.KEY_ISBN, txtBookIsbn);
			initialValues.put(MyAppDbAdapter.KEY_YEAR, txtBookYear);
			initialValues.put(MyAppDbAdapter.KEY_EDITION, txtBookEdition);
			initialValues.put(MyAppDbAdapter.KEY_COVER_LINK, txtBookCoverlink);
			initialValues.put(MyAppDbAdapter.KEY_COVER_TYPE, txtBookCovertype);
			initialValues.put(MyAppDbAdapter.KEY_OWN_CODE, txtBookOwncode);
			initialValues.put(MyAppDbAdapter.KEY_PURCHASE_PRICE,
									txtBookPurchaseprice);
			initialValues.put(MyAppDbAdapter.KEY_CURRENT_PRICE,
									txtBookCurrentprice);
			initialValues.put(MyAppDbAdapter.KEY_PAGE_NUMBER, txtBookPagenumber);
			initialValues.put(MyAppDbAdapter.KEY_RECOMMEND_FOR,
									txtBookRecommendfor);
			initialValues.put(MyAppDbAdapter.KEY_LOAN_TO, txtBookLoanto);
			initialValues.put(MyAppDbAdapter.KEY_LOAN_DATE, txtBookLoandate);
			initialValues
					.put(MyAppDbAdapter.KEY_LOAN_TO_PHONE, txtBookLoantophone);
			initialValues.put(MyAppDbAdapter.KEY_PRELOAN, txtBookPreloan);
			initialValues.put(MyAppDbAdapter.KEY_BOOK_EDITORID, spinSpinnereditor);
			initialValues.put(MyAppDbAdapter.KEY_BOOK_PUBLISHERID,
									spinSpinnerpublisher);
			initialValues.put(MyAppDbAdapter.KEY_BOOK_LANGUAGEID,
									spinSpinnerlanguage);
			initialValues.put(MyAppDbAdapter.KEY_BOOK_SHELFID,
									spinSpinnerbookshelf);
			initialValues.put(MyAppDbAdapter.KEY_BOOK_INFO_1, txtBookinfo1);
			initialValues.put(MyAppDbAdapter.KEY_BOOK_INFO_2, txtBookinfo2);
			initialValues.put(MyAppDbAdapter.KEY_BOOK_INFO_3, txtBookinfo3);
			initialValues.put(MyAppDbAdapter.KEY_BOOK_INFO_4, txtBookinfo4);
			initialValues.put(MyAppDbAdapter.KEY_BOOK_INFO_5, txtBookinfo5);
			initialValues.put(MyAppDbAdapter.KEY_DESCRIPTION, txtBookDescription);
			blIsSuccessful = (this.sqliteDBObj
					.insert(MyAppDbAdapter.MY_BOOK_DB_TABLE, null, initialValues) != -1);
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.createBookEntry",
												"creating book entry");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.createBookEntry",
												"creating book entry");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	protected Boolean createAuthorEntry(String txtAuthorfullname,
													String txtAuthorhomepage,
													String txtAuthoremail) {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			ContentValues initialValues = new ContentValues();
			initialValues
					.put(MyAppDbAdapter.KEY_AUTHORFULLNAME, txtAuthorfullname);
			initialValues
					.put(MyAppDbAdapter.KEY_AUTHORHOMEPAGE, txtAuthorhomepage);
			initialValues.put(MyAppDbAdapter.KEY_AUTHOREMAIL, txtAuthoremail);
			blIsSuccessful = (this.sqliteDBObj
					.insert(MyAppDbAdapter.MY_AUTHOR_DB_TABLE, null, initialValues) != -1);
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.createAuthorEntry",
												"creating author entry");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.createAuthorEntry",
												"creating author entry");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	protected Boolean createAuthorInstantEntry(String newauthor) {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			ContentValues initialValues = new ContentValues();
			initialValues.put(MyAppDbAdapter.KEY_AUTHORFULLNAME, newauthor);
			blIsSuccessful = (this.sqliteDBObj
					.insert(MyAppDbAdapter.MY_AUTHOR_DB_TABLE, null, initialValues) != -1);
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.createAuthorEntry",
												"creating author entry");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.createAuthorEntry",
												"creating author instant entry");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	protected Boolean createGenreInstantEntry(String newgenre) {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			ContentValues initialValues = new ContentValues();
			initialValues.put(MyAppDbAdapter.KEY_GENRE, newgenre);
			blIsSuccessful = (this.sqliteDBObj
					.insert(MyAppDbAdapter.MY_GENRE_DB_TABLE, null, initialValues) != -1);
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.createGenreEntry",
												"creating genre entry");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.createGenreEntry",
												"creating genre instant entry");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	protected Boolean createBooksellerEntry(String txtBookseller,
															String txtBooksellerAddress,
															String txtBooksellerHomepage,
															String txtEmailnews,
															String txtMailnews,
															String txtBookselleremail,
															String txtBooksellerinfo1) {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			ContentValues initialValues = new ContentValues();
			initialValues.put(MyAppDbAdapter.KEY_BOOKSELLER, txtBookseller);
			initialValues.put(MyAppDbAdapter.KEY_BOOKSELLERADDRESS,
									txtBooksellerAddress);
			initialValues.put(MyAppDbAdapter.KEY_BOOKSELLERHOMEPAGE,
									txtBooksellerHomepage);
			initialValues.put(MyAppDbAdapter.KEY_EMAIL_NEWS, txtEmailnews);
			initialValues.put(MyAppDbAdapter.KEY_MAIL_NEWS, txtMailnews);
			initialValues.put(MyAppDbAdapter.KEY_BOOKSELLEREMAIL,
									txtBookselleremail);
			initialValues.put(MyAppDbAdapter.KEY_BOOKSELLER_INFO_1,
									txtBooksellerinfo1);
			blIsSuccessful = (this.sqliteDBObj
					.insert(	MyAppDbAdapter.MY_BOOKSELLER_DB_TABLE, null,
								initialValues) != -1);
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error,
												"MyAppDbAdapter.createBooksellerEntry",
												"creating genre entry");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error,
												"MyAppDbAdapter.createBooksellerEntry",
												"creating genre entry");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	protected Boolean createBooksellerInstantEntry(String newbookseller) {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			ContentValues initialValues = new ContentValues();
			initialValues.put(MyAppDbAdapter.KEY_BOOKSELLER, newbookseller);
			blIsSuccessful = (this.sqliteDBObj
					.insert(	MyAppDbAdapter.MY_BOOKSELLER_DB_TABLE, null,
								initialValues) != -1);
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error,
												"MyAppDbAdapter.createBooksellerEntry",
												"creating bookseller entry");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error,
												"MyAppDbAdapter.createBooksellerEntry",
												"creating bookseller instant entry");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	protected Boolean createEditorEntry(String txtEditorfullname,
													String txtEditorhomepage,
													String txtEditoremail) {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			ContentValues initialValues = new ContentValues();
			initialValues
					.put(MyAppDbAdapter.KEY_EDITORFULLNAME, txtEditorfullname);
			initialValues
					.put(MyAppDbAdapter.KEY_EDITORHOMEPAGE, txtEditorhomepage);
			initialValues.put(MyAppDbAdapter.KEY_EDITOREMAIL, txtEditoremail);
			blIsSuccessful = (this.sqliteDBObj
					.insert(MyAppDbAdapter.MY_EDITOR_DB_TABLE, null, initialValues) != -1);
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.createEditorEntry",
												"creating editor entry");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.createEditorEntry",
												"creating editor entry");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	protected Boolean createPublisherEntry(String txtPublisher,
														String txtPublisheraddress,
														String txtPublisherhomepage,
														String txtPublisheremail,
														String txtPublisherinfo1) {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			ContentValues initialValues = new ContentValues();
			initialValues.put(MyAppDbAdapter.KEY_PUBLISHER, txtPublisher);
			initialValues.put(MyAppDbAdapter.KEY_PUBLISHERADDRESS,
									txtPublisheraddress);
			initialValues.put(MyAppDbAdapter.KEY_PUBLISHERHOMEPAGE,
									txtPublisherhomepage);
			initialValues
					.put(MyAppDbAdapter.KEY_PUBLISHEREMAIL, txtPublisheremail);
			initialValues.put(MyAppDbAdapter.KEY_PUBLISHER_INFO_1,
									txtPublisherinfo1);
			blIsSuccessful = (this.sqliteDBObj
					.insert(	MyAppDbAdapter.MY_PUBLISHER_DB_TABLE, null,
								initialValues) != -1);
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error,
												"MyAppDbAdapter.createPublisherEntry",
												"creating publisher entry");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error,
												"MyAppDbAdapter.createPublisherEntry",
												"creating publisher entry");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	protected Boolean createGenreEntry(String txtGenre, String txtGenreinfo1) {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			ContentValues initialValues = new ContentValues();
			initialValues.put(MyAppDbAdapter.KEY_GENRE, txtGenre);
			initialValues.put(MyAppDbAdapter.KEY_GENRE_INFO_1, txtGenreinfo1);
			blIsSuccessful = (this.sqliteDBObj
					.insert(MyAppDbAdapter.MY_GENRE_DB_TABLE, null, initialValues) != -1);
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.createGenreEntry",
												"creating genre entry");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.createGenreEntry",
												"creating genre entry");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	protected Boolean createLanguageEntry(String txtLanguage,
														String txtLanguageinfo1) {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			ContentValues initialValues = new ContentValues();
			initialValues.put(MyAppDbAdapter.KEY_LANGUAGE, txtLanguage);
			initialValues
					.put(MyAppDbAdapter.KEY_LANGUAGE_INFO_1, txtLanguageinfo1);
			blIsSuccessful = (this.sqliteDBObj
					.insert(MyAppDbAdapter.MY_LANGUAGE_DB_TABLE, null, initialValues) != -1);
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error,
												"MyAppDbAdapter.createLanguageEntry",
												"creating language entry");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error,
												"MyAppDbAdapter.createLanguageEntry",
												"creating language entry");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	protected Boolean createRackEntry(String txtRacklabel, String txtRackinfo1,
													int spinSpinnerrack) {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			ContentValues initialValues = new ContentValues();
			initialValues.put(MyAppDbAdapter.KEY_RACK_LABEL, txtRacklabel);
			initialValues.put(MyAppDbAdapter.KEY_RACK_INFO_1, txtRackinfo1);
			initialValues.put(MyAppDbAdapter.KEY_RACK_ROOMID, spinSpinnerrack);
			blIsSuccessful = (this.sqliteDBObj
					.insert(MyAppDbAdapter.MY_RACK_DB_TABLE, null, initialValues) != -1);
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.createRackEntry",
												"creating rack entry");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.createRackEntry",
												"creating rack entry");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	protected Boolean createRoomEntry(String txtRoom, String txtRoominfo1,
													int spinSpinnerroom) {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			ContentValues initialValues = new ContentValues();
			initialValues.put(MyAppDbAdapter.KEY_ROOM_NUMBER, txtRoom);
			initialValues.put(MyAppDbAdapter.KEY_ROOM_INFO_1, txtRoominfo1);
			initialValues.put(MyAppDbAdapter.KEY_ROOM_STORAGEID, spinSpinnerroom);
			blIsSuccessful = (this.sqliteDBObj
					.insert(MyAppDbAdapter.MY_ROOM_DB_TABLE, null, initialValues) != -1);
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.createRoomEntry",
												"creating room entry");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.createRoomEntry",
												"creating room entry");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	protected Boolean createShelfEntry(String txtShelflabel,
													String txtShelfinfo1,
													int spinSpinnershelf) {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			ContentValues initialValues = new ContentValues();
			initialValues.put(MyAppDbAdapter.KEY_SHELF_LABEL, txtShelflabel);
			initialValues.put(MyAppDbAdapter.KEY_SHELF_INFO_1, txtShelfinfo1);
			initialValues.put(MyAppDbAdapter.KEY_SHELF_RACKID, spinSpinnershelf);
			blIsSuccessful = (this.sqliteDBObj
					.insert(MyAppDbAdapter.MY_SHELF_DB_TABLE, null, initialValues) != -1);
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.createShelfEntry",
												"creating shelf entry");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.createShelfEntry",
												"creating shelf entry");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	protected Boolean createStorageEntry(String txtStorage,
														String txtStorageaddress,
														String txtStorageinfo1) {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			ContentValues initialValues = new ContentValues();
			initialValues.put(MyAppDbAdapter.KEY_STORAGE, txtStorage);
			initialValues
					.put(MyAppDbAdapter.KEY_STORAGEADDRESS, txtStorageaddress);
			initialValues.put(MyAppDbAdapter.KEY_STORAGE_INFO_1, txtStorageinfo1);
			blIsSuccessful = (this.sqliteDBObj
					.insert(MyAppDbAdapter.MY_STORAGE_DB_TABLE, null, initialValues) != -1);
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.createStorageEntry",
												"creating storage entry");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.createStorageEntry",
												"creating storage entry");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	protected Boolean createReviewEntry(String txtReview, String txtReviewinfo1,
													int spinSpinnerreviewauthor,
													int spinSpinnerreviewbook) {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			ContentValues initialValues = new ContentValues();
			initialValues.put(MyAppDbAdapter.KEY_REVIEW, txtReview);
			initialValues.put(MyAppDbAdapter.KEY_REVIEW_INFO_1, txtReviewinfo1);
			initialValues.put(MyAppDbAdapter.KEY_REVIEW_AUTHORID,
									spinSpinnerreviewauthor);
			initialValues.put(MyAppDbAdapter.KEY_REVIEW_BOOKID,
									spinSpinnerreviewbook);
			blIsSuccessful = (this.sqliteDBObj
					.insert(MyAppDbAdapter.MY_REVIEW_DB_TABLE, null, initialValues) != -1);
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.createReviewEntry",
												"creating review entry");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.createReviewEntry",
												"creating review entry");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	protected Boolean createBookAuthorEntry(int bRowId, int aRowId) {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			ContentValues initialValues = new ContentValues();
			initialValues.put(MyAppDbAdapter.KEY_BA_AUTHORID, aRowId);
			initialValues.put(MyAppDbAdapter.KEY_BA_BOOKID, bRowId);
			blIsSuccessful = (this.sqliteDBObj
					.insert(	MyAppDbAdapter.MY_BOOK_AUTHOR_DB_TABLE, null,
								initialValues) != -1);
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.createReviewEntry",
												"creating review entry");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.createReviewEntry",
												"creating review entry");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	protected Boolean createBookGenreEntry(int bRowId, int aRowId) {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			ContentValues initialValues = new ContentValues();
			initialValues.put(MyAppDbAdapter.KEY_BG_GENREID, aRowId);
			initialValues.put(MyAppDbAdapter.KEY_BG_BOOKID, bRowId);
			blIsSuccessful = (this.sqliteDBObj
					.insert(	MyAppDbAdapter.MY_BOOK_GENRE_DB_TABLE, null,
								initialValues) != -1);
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.createGenreEntry",
												"creating genre entry");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.createGenreEntry",
												"creating genre entry");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	protected Boolean createBookBooksellerEntry(int bRowId, int aRowId) {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			ContentValues initialValues = new ContentValues();
			initialValues.put(MyAppDbAdapter.KEY_BS_BOOKSELLERID, aRowId);
			initialValues.put(MyAppDbAdapter.KEY_BS_BOOKID, bRowId);
			blIsSuccessful = (this.sqliteDBObj
					.insert(	MyAppDbAdapter.MY_BOOK_BOOKSELLER_DB_TABLE, null,
								initialValues) != -1);
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError
					.addToLogFile(	error,
										"MyAppDbAdapter.createBooktoBooksellerEntry",
										"creating review entry");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError
					.addToLogFile(	error,
										"MyAppDbAdapter.createBooktoBooksellerEntry",
										"creating review entry");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	/***************************************************************** updateEntry: Updates an existing entry in the database table
	 * @return True if the database update succeeds
	 * @throws SQLException
	 * if entry could not be found/retrieved */
	protected boolean updateBookEntry(Long rowId, String strBookTitle,
													String strBookSubtitle,
													String strBookIsbn, String strBookYear,
													String strBookEdition,
													String strBookCoverlink,
													String strBookCovertype,
													String strBookOwncode,
													String strBookPurchaseprice,
													String strBookCurrentprice,
													String strBookPagenumber,
													String strBookRecommendfor,
													String strBookLoanto,
													String strBookLoantophone,
													String strBookLoandate,
													String strBookPreloan,
													int spinSpinnereditor,
													int spinSpinnerpublisher,
													int spinSpinnerlanguage,
													int spinSpinnerbookshelf,
													String strBookinfo1,
													String strBookinfo2,
													String strBookinfo3,
													String strBookinfo4,
													String strBookinfo5,
													String strBookDescription,
													String strOrigBookTitle,
													String strOrigBookSubtitle,
													String strOrigBookIsbn,
													String strOrigBookYear,
													String strOrigBookEdition,
													String strOrigBookCoverlink,
													String strOrigBookCovertype,
													String strOrigBookOwncode,
													String strOrigBookPurchaseprice,
													String strOrigBookCurrentprice,
													String strOrigBookPagenumber,
													String strOrigBookRecommendfor,
													String strOrigBookLoanto,
													String strOrigBookLoantophone,
													String strOrigBookLoandate,
													String strOrigBookPreloan,
													int spinOrigSpinnereditor,
													int spinOrigSpinnerpublisher,
													int spinOrigSpinnerlanguage,
													int spinOrigSpinnerbookshelf,
													String strOrigBookinfo1,
													String strOrigBookinfo2,
													String strOrigBookinfo3,
													String strOrigBookinfo4,
													String strOrigBookinfo5,
													String strOrigBookDescription) {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			ContentValues args = new ContentValues();
			args.put(MyAppDbAdapter.KEY_TITLE, strBookTitle);
			args.put(MyAppDbAdapter.KEY_SUBTITLE, strBookSubtitle);
			args.put(MyAppDbAdapter.KEY_ISBN, strBookIsbn);
			args.put(MyAppDbAdapter.KEY_YEAR, strBookYear);
			args.put(MyAppDbAdapter.KEY_EDITION, strBookEdition);
			args.put(MyAppDbAdapter.KEY_COVER_LINK, strBookCoverlink);
			args.put(MyAppDbAdapter.KEY_COVER_TYPE, strBookCovertype);
			args.put(MyAppDbAdapter.KEY_OWN_CODE, strBookOwncode);
			args.put(MyAppDbAdapter.KEY_PURCHASE_PRICE, strBookPurchaseprice);
			args.put(MyAppDbAdapter.KEY_CURRENT_PRICE, strBookCurrentprice);
			args.put(MyAppDbAdapter.KEY_PAGE_NUMBER, strBookPagenumber);
			args.put(MyAppDbAdapter.KEY_RECOMMEND_FOR, strBookRecommendfor);
			args.put(MyAppDbAdapter.KEY_LOAN_TO, strBookLoanto);
			args.put(MyAppDbAdapter.KEY_LOAN_DATE, strBookLoandate);
			args.put(MyAppDbAdapter.KEY_LOAN_TO_PHONE, strBookLoantophone);
			args.put(MyAppDbAdapter.KEY_PRELOAN, strBookPreloan);
			args.put(MyAppDbAdapter.KEY_DESCRIPTION, strBookDescription);
			args.put(MyAppDbAdapter.KEY_BOOK_EDITORID, spinSpinnereditor);
			args.put(MyAppDbAdapter.KEY_BOOK_PUBLISHERID, spinSpinnerpublisher);
			args.put(MyAppDbAdapter.KEY_BOOK_LANGUAGEID, spinSpinnerlanguage);
			args.put(MyAppDbAdapter.KEY_BOOK_SHELFID, spinSpinnerbookshelf);
			args.put(MyAppDbAdapter.KEY_BOOK_INFO_1, strBookinfo1);
			args.put(MyAppDbAdapter.KEY_BOOK_INFO_2, strBookinfo2);
			args.put(MyAppDbAdapter.KEY_BOOK_INFO_3, strBookinfo3);
			args.put(MyAppDbAdapter.KEY_BOOK_INFO_4, strBookinfo4);
			args.put(MyAppDbAdapter.KEY_BOOK_INFO_5, strBookinfo5);
			blIsSuccessful = (this.sqliteDBObj
					.update(	MyAppDbAdapter.MY_BOOK_DB_TABLE, args,
								MyAppDbAdapter.KEY_ROWID + "=" + rowId, null) != -1);
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.updateBookEntry",
												"updating book entry information");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.updateBookEntry",
												"updating book entry information");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	protected boolean updateAuthorEntry(Long rowId, String strAuthorfullname,
													String strAuthorhomepage,
													String strAuthoremail,
													String strOrigAuthorfullname,
													String strOrigAuthorhomepage,
													String strOrigAuthoremail) {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			ContentValues args = new ContentValues();
			args.put(MyAppDbAdapter.KEY_AUTHORFULLNAME, strAuthorfullname);
			args.put(MyAppDbAdapter.KEY_AUTHORHOMEPAGE, strAuthorhomepage);
			args.put(MyAppDbAdapter.KEY_AUTHOREMAIL, strAuthoremail);
			blIsSuccessful = (this.sqliteDBObj
					.update(	MyAppDbAdapter.MY_AUTHOR_DB_TABLE, args,
								MyAppDbAdapter.KEY_ROWID + "=" + rowId, null) != -1);
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.updateAuthorEntry",
												"updating book entry information");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.updateAuthorEntry",
												"updating book entry information");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}// end updateAuthorEntry
	
	protected boolean updateBooksellerEntry(Long rowId, String strBookseller,
															String strBookselleraddress,
															String strBooksellerhomepage,
															String strBookselleremail,
															String strEmailnews,
															String strMailnews,
															String strBooksellerinfo1,
															String strOrigBookseller,
															String strOrigBookselleraddress,
															String strOrigBooksellerhomepage,
															String strOrigBookselleremail,
															String strOrigEmailnews,
															String strOrigMailnews,
															String strOrigBooksellerinfo1) {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			ContentValues args = new ContentValues();
			args.put(MyAppDbAdapter.KEY_BOOKSELLER, strBookseller);
			args.put(MyAppDbAdapter.KEY_BOOKSELLERHOMEPAGE, strBooksellerhomepage);
			args.put(MyAppDbAdapter.KEY_BOOKSELLERADDRESS, strBookselleraddress);
			args.put(MyAppDbAdapter.KEY_EMAIL_NEWS, strEmailnews);
			args.put(MyAppDbAdapter.KEY_MAIL_NEWS, strMailnews);
			args.put(MyAppDbAdapter.KEY_BOOKSELLEREMAIL, strBookselleremail);
			args.put(MyAppDbAdapter.KEY_BOOKSELLER_INFO_1, strBooksellerinfo1);
			blIsSuccessful = (this.sqliteDBObj
					.update(	MyAppDbAdapter.MY_BOOKSELLER_DB_TABLE, args,
								MyAppDbAdapter.KEY_ROWID + "=" + rowId, null) != -1);
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error,
												"MyAppDbAdapter.updateBooksellerEntry",
												"updating genre entry information");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error,
												"MyAppDbAdapter.updateBooksellerEntry",
												"updating genre entry information");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}// end updateBooksellerEntry
	
	protected boolean updateEditorEntry(Long rowId, String strEditorfullname,
													String strEditorhomepage,
													String strEditoremail,
													String strOrigEditorfullname,
													String strOrigEditorhomepage,
													String strOrigEditoremail) {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			ContentValues args = new ContentValues();
			args.put(MyAppDbAdapter.KEY_EDITORFULLNAME, strEditorfullname);
			args.put(MyAppDbAdapter.KEY_EDITORHOMEPAGE, strEditorhomepage);
			args.put(MyAppDbAdapter.KEY_EDITOREMAIL, strEditoremail);
			blIsSuccessful = (this.sqliteDBObj
					.update(	MyAppDbAdapter.MY_EDITOR_DB_TABLE, args,
								MyAppDbAdapter.KEY_ROWID + "=" + rowId, null) != -1);
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.updateEditorEntry",
												"updating book entry information");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.updateEditorEntry",
												"updating book entry information");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}// end updateEditorEntry
	
	protected boolean updatePublisherEntry(Long rowId, String strPublisher,
														String strPublisheraddress,
														String strPublisherhomepage,
														String strPublisheremail,
														String strPublisherinfo1,
														String strOrigPublisher,
														String strOrigPublisheraddress,
														String strOrigPublisherhomepage,
														String strOrigPublisheremail,
														String strOrigPublisherinfo1) {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			ContentValues args = new ContentValues();
			args.put(MyAppDbAdapter.KEY_PUBLISHER, strPublisher);
			args.put(MyAppDbAdapter.KEY_PUBLISHERHOMEPAGE, strPublisherhomepage);
			args.put(MyAppDbAdapter.KEY_PUBLISHEREMAIL, strPublisheremail);
			args.put(MyAppDbAdapter.KEY_PUBLISHERADDRESS, strPublisheraddress);
			args.put(MyAppDbAdapter.KEY_PUBLISHER_INFO_1, strPublisherinfo1);
			blIsSuccessful = (this.sqliteDBObj
					.update(	MyAppDbAdapter.MY_PUBLISHER_DB_TABLE, args,
								MyAppDbAdapter.KEY_ROWID + "=" + rowId, null) != -1);
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error,
												"MyAppDbAdapter.updatePublisherEntry",
												"updating book entry information");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error,
												"MyAppDbAdapter.updatePublisherEntry",
												"updating book entry information");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}// end updatePublisherEntry
	
	protected boolean updateLanguageEntry(Long rowId, String strLanguage,
														String strLanguageinfo1,
														String strOrigLanguage,
														String strOrigLanguageinfo1) {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			ContentValues args = new ContentValues();
			args.put(MyAppDbAdapter.KEY_LANGUAGE, strLanguage);
			args.put(MyAppDbAdapter.KEY_LANGUAGE_INFO_1, strLanguageinfo1);
			blIsSuccessful = (this.sqliteDBObj
					.update(	MyAppDbAdapter.MY_LANGUAGE_DB_TABLE, args,
								MyAppDbAdapter.KEY_ROWID + "=" + rowId, null) != -1);
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error,
												"MyAppDbAdapter.updateLanguageEntry",
												"updating language entry information");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error,
												"MyAppDbAdapter.updateLanguageEntry",
												"updating language entry information");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}// end updateLanguageEntry
	
	protected boolean updateGenreEntry(Long rowId, String strGenre,
													String strGenreinfo1,
													String strOrigGenre,
													String strOrigGenreinfo1) {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			ContentValues args = new ContentValues();
			args.put(MyAppDbAdapter.KEY_GENRE, strGenre);
			args.put(MyAppDbAdapter.KEY_GENRE_INFO_1, strGenreinfo1);
			blIsSuccessful = (this.sqliteDBObj
					.update(	MyAppDbAdapter.MY_GENRE_DB_TABLE, args,
								MyAppDbAdapter.KEY_ROWID + "=" + rowId, null) != -1);
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.updateGenreEntry",
												"updating genre entry information");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.updateGenreEntry",
												"updating genre entry information");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}// end updateGenreEntry
	
	protected boolean updateReviewEntry(Long rowId, String strReview,
													String strReviewinfo1,
													int spinSpinnerreviewauthor,
													int spinSpinnerreviewbook,
													String strOrigReview,
													String strOrigReviewinfo1,
													int spinOrigSpinnerreviewauthor,
													int spinOrigSpinnerreviewbook) {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			ContentValues args = new ContentValues();
			args.put(MyAppDbAdapter.KEY_REVIEW, strReview);
			args.put(MyAppDbAdapter.KEY_REVIEW_INFO_1, strReviewinfo1);
			args.put(MyAppDbAdapter.KEY_REVIEW_AUTHORID, spinSpinnerreviewauthor);
			args.put(MyAppDbAdapter.KEY_REVIEW_BOOKID, spinSpinnerreviewbook);
			blIsSuccessful = (this.sqliteDBObj
					.update(	MyAppDbAdapter.MY_REVIEW_DB_TABLE, args,
								MyAppDbAdapter.KEY_ROWID + "=" + rowId, null) != -1);
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.updateReviewEntry",
												"updating review entry information");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.updateReviewEntry",
												"updating review entry information");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}// end updateReviewEntry
	
	protected boolean updateRackEntry(Long rowId, String strRacklabel,
													String strRackinfo1,
													int spinSpinnerrack,
													String strOrigRacklabel,
													String strOrigRackinfo1,
													int spinOrigSpinnerrack) {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			ContentValues args = new ContentValues();
			args.put(MyAppDbAdapter.KEY_RACK_LABEL, strRacklabel);
			args.put(MyAppDbAdapter.KEY_RACK_INFO_1, strRackinfo1);
			args.put(MyAppDbAdapter.KEY_RACK_ROOMID, spinSpinnerrack);
			blIsSuccessful = (this.sqliteDBObj
					.update(	MyAppDbAdapter.MY_RACK_DB_TABLE, args,
								MyAppDbAdapter.KEY_ROWID + "=" + rowId, null) != -1);
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.updateRackEntry",
												"updating rack entry information");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.updateRackEntry",
												"updating rack entry information");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}// end updateRackEntry
	
	protected boolean updateStorageEntry(Long rowId, String strStorage,
														String strStorageaddress,
														String strStorageinfo1,
														String strOrigStorage,
														String strOrigStorageaddress,
														String strOrigStorageinfo1) {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			ContentValues args = new ContentValues();
			args.put(MyAppDbAdapter.KEY_STORAGE, strStorage);
			args.put(MyAppDbAdapter.KEY_STORAGEADDRESS, strStorageaddress);
			args.put(MyAppDbAdapter.KEY_STORAGE_INFO_1, strStorageinfo1);
			blIsSuccessful = (this.sqliteDBObj
					.update(	MyAppDbAdapter.MY_STORAGE_DB_TABLE, args,
								MyAppDbAdapter.KEY_ROWID + "=" + rowId, null) != -1);
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.updateStorageEntry",
												"updating storage entry information");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.updateStorageEntry",
												"updating storage entry information");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}// end updateStorageEntry
	
	protected boolean updateShelfEntry(Long rowId, String strShelflabel,
													String strShelfinfo1,
													int spinSpinnershelf,
													String strOrigShelflabel,
													String strOrigShelfinfo1,
													int spinOrigSpinnershelf) {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			ContentValues args = new ContentValues();
			args.put(MyAppDbAdapter.KEY_SHELF_LABEL, strShelflabel);
			args.put(MyAppDbAdapter.KEY_SHELF_INFO_1, strShelfinfo1);
			args.put(MyAppDbAdapter.KEY_SHELF_RACKID, spinSpinnershelf);
			blIsSuccessful = (this.sqliteDBObj
					.update(	MyAppDbAdapter.MY_SHELF_DB_TABLE, args,
								MyAppDbAdapter.KEY_ROWID + "=" + rowId, null) != -1);
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.updateShelfEntry",
												"updating shelf entry information");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.updateShelfEntry",
												"updating shelf entry information");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}// end updateShelfEntry
	
	protected boolean updateRoomEntry(Long rowId, String strRoomnumber,
													String strRoominfo1,
													int spinSpinnerroom,
													String strOrigRoomnumber,
													String strOrigRoominfo1,
													int spinOrigSpinnerroom) {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			ContentValues args = new ContentValues();
			args.put(MyAppDbAdapter.KEY_ROOM_NUMBER, strRoomnumber);
			args.put(MyAppDbAdapter.KEY_ROOM_INFO_1, strRoominfo1);
			args.put(MyAppDbAdapter.KEY_ROOM_STORAGEID, spinSpinnerroom);
			blIsSuccessful = (this.sqliteDBObj
					.update(	MyAppDbAdapter.MY_ROOM_DB_TABLE, args,
								MyAppDbAdapter.KEY_ROWID + "=" + rowId, null) != -1);
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.updateRoomEntry",
												"updating room entry information");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.updateRoomEntry",
												"updating room entry information");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}// end updateRoomEntry
	
	/***************************************************************** Delete the entries
	 * @return true if deleted, false otherwise
	 * @throws SQLException
	 * if the SQL scripts encounter issues */
	protected boolean deleteBookEntries() throws SQLException {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			blIsSuccessful = this.sqliteDBObj
					.delete(MyAppDbAdapter.MY_BOOK_DB_TABLE, "1", null) > 0;
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.deleteBookEntries",
												"deleting book entries");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.deleteBookEntries",
												"deleting book entries");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	protected boolean deleteAuthorEntries() throws SQLException {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			blIsSuccessful = this.sqliteDBObj
					.delete(MyAppDbAdapter.MY_AUTHOR_DB_TABLE, "1", null) > 0;
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error,
												"MyAppDbAdapter.deleteAuthorEntries",
												"deleting author entries");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error,
												"MyAppDbAdapter.deleteAuthorEntries",
												"deleting author entries");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	protected boolean deleteEditorEntries() throws SQLException {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			blIsSuccessful = this.sqliteDBObj
					.delete(MyAppDbAdapter.MY_EDITOR_DB_TABLE, "1", null) > 0;
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error,
												"MyAppDbAdapter.deleteEditorEntries",
												"deleting editor entries");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error,
												"MyAppDbAdapter.deleteEditorEntries",
												"deleting editor entries");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	protected boolean deleteBooksellerEntries() throws SQLException {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			blIsSuccessful = this.sqliteDBObj
					.delete(MyAppDbAdapter.MY_BOOKSELLER_DB_TABLE, "1", null) > 0;
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error,
												"MyAppDbAdapter.deleteBooksellerEntries",
												"deleting genre entries");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error,
												"MyAppDbAdapter.deleteBooksellerEntries",
												"deleting genre entries");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	protected boolean deleteGenreEntries() throws SQLException {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			blIsSuccessful = this.sqliteDBObj
					.delete(MyAppDbAdapter.MY_GENRE_DB_TABLE, "1", null) > 0;
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.deleteGenreEntries",
												"deleting genre entries");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.deleteGenreEntries",
												"deleting genre entries");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	protected boolean deleteLanguageEntries() throws SQLException {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			blIsSuccessful = this.sqliteDBObj
					.delete(MyAppDbAdapter.MY_LANGUAGE_DB_TABLE, "1", null) > 0;
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error,
												"MyAppDbAdapter.deleteLanguageEntries",
												"deleting language entries");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error,
												"MyAppDbAdapter.deleteLanguageEntries",
												"deleting language entries");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	protected boolean deletePublisherEntries() throws SQLException {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			blIsSuccessful = this.sqliteDBObj
					.delete(MyAppDbAdapter.MY_PUBLISHER_DB_TABLE, "1", null) > 0;
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error,
												"MyAppDbAdapter.deletePublisherEntries",
												"deleting publisher entries");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error,
												"MyAppDbAdapter.deletePublisherEntries",
												"deleting publisher entries");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	protected boolean deleteStorageEntries() throws SQLException {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			blIsSuccessful = this.sqliteDBObj
					.delete(MyAppDbAdapter.MY_STORAGE_DB_TABLE, "1", null) > 0;
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error,
												"MyAppDbAdapter.deleteStorageEntries",
												"deleting storage entries");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error,
												"MyAppDbAdapter.deleteStorageEntries",
												"deleting storage entries");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	protected boolean deleteRackEntries() throws SQLException {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			blIsSuccessful = this.sqliteDBObj
					.delete(MyAppDbAdapter.MY_RACK_DB_TABLE, "1", null) > 0;
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.deleteRackEntries",
												"deleting rack entries");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.deleteRackEntries",
												"deleting rack entries");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	protected boolean deleteShelfEntries() throws SQLException {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			blIsSuccessful = this.sqliteDBObj
					.delete(MyAppDbAdapter.MY_GENRE_DB_TABLE, "1", null) > 0;
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.deleteShelfEntries",
												"deleting shelf entries");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.deleteShelfEntries",
												"deleting shelf entries");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	protected boolean deleteRoomEntries() throws SQLException {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			blIsSuccessful = this.sqliteDBObj
					.delete(MyAppDbAdapter.MY_ROOM_DB_TABLE, "1", null) > 0;
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.deleteRoomEntries",
												"deleting room entries");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.deleteRoomEntries",
												"deleting room entries");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	protected boolean deleteReviewEntries() throws SQLException {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			blIsSuccessful = this.sqliteDBObj
					.delete(MyAppDbAdapter.MY_REVIEW_DB_TABLE, "1", null) > 0;
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error,
												"MyAppDbAdapter.deleteReviewEntries",
												"deleting review entries");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error,
												"MyAppDbAdapter.deleteReviewEntries",
												"deleting review entries");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	/***************************************************************** Delete the Book Entry per the given rowID
	 * @param rowId
	 * rowId of the Book Entry in the Book table to be deleted
	 * @return true if the delete is successful, false otherwise.
	 * @throws SQLException
	 * if the SQL scripts encounter issues ******************************************************************/
	protected boolean deleteBookEntry(long rowId) throws SQLException {
		// delete a single Book entry in the Book table
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			Cursor crsrListRowData;
			crsrListRowData = fetchBookEntry(rowId);
			if (crsrListRowData.getCount() >= 0) {
				blIsSuccessful = this.sqliteDBObj
						.delete(	MyAppDbAdapter.MY_BOOK_DB_TABLE,
									MyAppDbAdapter.KEY_ROWID + "=" + rowId, null) > 0;
			} else {
				blIsSuccessful = true;
			}
			crsrListRowData.close();
			crsrListRowData = null;
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.deleteBookkEntry",
												"deleting book entry");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.deleteBookEntry",
												"deleting book entry");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}// end deleteBookEntry(long rowId)
	
	protected boolean deleteAuthorEntry(long rowId) throws SQLException {
		// delete a single Author entry in the Author table
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			Cursor crsrListRowData;
			crsrListRowData = fetchAuthorEntry(rowId);
			if (crsrListRowData.getCount() >= 0) {
				blIsSuccessful = this.sqliteDBObj
						.delete(	MyAppDbAdapter.MY_AUTHOR_DB_TABLE,
									MyAppDbAdapter.KEY_ROWID + "=" + rowId, null) > 0;
			} else {
				blIsSuccessful = true;
			}
			crsrListRowData.close();
			crsrListRowData = null;
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.deleteAuthorkEntry",
												"deleting author entry");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.deleteAuthorEntry",
												"deleting author entry");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	protected boolean deleteBookAuthorEntry(long rowId) throws SQLException {
		// delete a single Author entry in the Author table
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			Cursor crsrListRowData;
			crsrListRowData = fetchBookAuthorEntry(rowId);
			if (crsrListRowData.getCount() >= 0) {
				blIsSuccessful = this.sqliteDBObj
						.delete(	MyAppDbAdapter.MY_BOOK_AUTHOR_DB_TABLE,
									MyAppDbAdapter.KEY_ROWID + "=" + rowId, null) > 0;
			} else {
				blIsSuccessful = true;
			}
			crsrListRowData.close();
			crsrListRowData = null;
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.deleteAuthorkEntry",
												"deleting author entry");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.deleteAuthorEntry",
												"deleting author entry");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	protected boolean deleteBookGenreEntry(long rowId) throws SQLException {
		// delete a single Genre entry in the Genre table
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			Cursor crsrListRowData;
			crsrListRowData = fetchBookGenreEntry(rowId);
			if (crsrListRowData.getCount() >= 0) {
				blIsSuccessful = this.sqliteDBObj
						.delete(	MyAppDbAdapter.MY_BOOK_GENRE_DB_TABLE,
									MyAppDbAdapter.KEY_ROWID + "=" + rowId, null) > 0;
			} else {
				blIsSuccessful = true;
			}
			crsrListRowData.close();
			crsrListRowData = null;
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.deleteGenrekEntry",
												"deleting genre entry");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.deleteGenreEntry",
												"deleting genre entry");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	protected boolean deleteEditorEntry(long rowId) throws SQLException {
		// delete a single Editor entry in the Editor table
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			Cursor crsrListRowData;
			crsrListRowData = fetchEditorEntry(rowId);
			if (crsrListRowData.getCount() >= 0) {
				blIsSuccessful = this.sqliteDBObj
						.delete(	MyAppDbAdapter.MY_EDITOR_DB_TABLE,
									MyAppDbAdapter.KEY_ROWID + "=" + rowId, null) > 0;
			} else {
				blIsSuccessful = true;
			}
			crsrListRowData.close();
			crsrListRowData = null;
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.deleteEditorEntry",
												"deleting Editor entry");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.deleteEditorEntry",
												"deleting Editor entry");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}// end deleteEditorEntry(long rowId)
	
	protected boolean deleteBooksellerEntry(long rowId) throws SQLException {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			Cursor crsrListRowData;
			crsrListRowData = fetchBooksellerEntry(rowId);
			if (crsrListRowData.getCount() >= 0) {
				blIsSuccessful = this.sqliteDBObj
						.delete(	MyAppDbAdapter.MY_BOOKSELLER_DB_TABLE,
									MyAppDbAdapter.KEY_ROWID + "=" + rowId, null) > 0;
			} else {
				blIsSuccessful = true;
			}
			crsrListRowData.close();
			crsrListRowData = null;
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error,
												"MyAppDbAdapter.deleteBooksellerEntry",
												"deleting Bookseller entry");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error,
												"MyAppDbAdapter.deleteBooksellerEntry",
												"deleting Bookseller entry");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	protected boolean deleteBookBooksellerEntry(long rowId) throws SQLException {
		// delete a single Bookseller entry in the Bookseller table
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			Cursor crsrListRowData;
			crsrListRowData = fetchBookBooksellerEntry(rowId);
			if (crsrListRowData.getCount() >= 0) {
				blIsSuccessful = this.sqliteDBObj
						.delete(	MyAppDbAdapter.MY_BOOK_BOOKSELLER_DB_TABLE,
									MyAppDbAdapter.KEY_ROWID + "=" + rowId, null) > 0;
			} else {
				blIsSuccessful = true;
			}
			crsrListRowData.close();
			crsrListRowData = null;
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error,
												"MyAppDbAdapter.deleteBooksellerkEntry",
												"deleting author entry");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error,
												"MyAppDbAdapter.deleteBooksellerEntry",
												"deleting author entry");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	protected boolean deleteGenreEntry(long rowId) throws SQLException {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			Cursor crsrListRowData;
			crsrListRowData = fetchGenreEntry(rowId);
			if (crsrListRowData.getCount() >= 0) {
				blIsSuccessful = this.sqliteDBObj
						.delete(	MyAppDbAdapter.MY_GENRE_DB_TABLE,
									MyAppDbAdapter.KEY_ROWID + "=" + rowId, null) > 0;
			} else {
				blIsSuccessful = true;
			}
			crsrListRowData.close();
			crsrListRowData = null;
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.deleteGenreEntry",
												"deleting Genre entry");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.deleteGenreEntry",
												"deleting Genre entry");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	protected boolean deleteLanguageEntry(long rowId) throws SQLException {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			Cursor crsrListRowData;
			crsrListRowData = fetchLanguageEntry(rowId);
			if (crsrListRowData.getCount() >= 0) {
				blIsSuccessful = this.sqliteDBObj
						.delete(	MyAppDbAdapter.MY_LANGUAGE_DB_TABLE,
									MyAppDbAdapter.KEY_ROWID + "=" + rowId, null) > 0;
			} else {
				blIsSuccessful = true;
			}
			crsrListRowData.close();
			crsrListRowData = null;
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error,
												"MyAppDbAdapter.deleteLanguageEntry",
												"deleting Language entry");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error,
												"MyAppDbAdapter.deleteLanguageEntry",
												"deleting Language entry");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	protected boolean deletePublisherEntry(long rowId) throws SQLException {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			Cursor crsrListRowData;
			crsrListRowData = fetchPublisherEntry(rowId);
			if (crsrListRowData.getCount() >= 0) {
				blIsSuccessful = this.sqliteDBObj
						.delete(	MyAppDbAdapter.MY_PUBLISHER_DB_TABLE,
									MyAppDbAdapter.KEY_ROWID + "=" + rowId, null) > 0;
			} else {
				blIsSuccessful = true;
			}
			crsrListRowData.close();
			crsrListRowData = null;
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error,
												"MyAppDbAdapter.deletePublisherEntry",
												"deleting Publisher entry");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error,
												"MyAppDbAdapter.deletePublisherEntry",
												"deleting Publisher entry");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	protected boolean deleteStorageEntry(long rowId) throws SQLException {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			Cursor crsrListRowData;
			crsrListRowData = fetchStorageEntry(rowId);
			if (crsrListRowData.getCount() >= 0) {
				blIsSuccessful = this.sqliteDBObj
						.delete(	MyAppDbAdapter.MY_STORAGE_DB_TABLE,
									MyAppDbAdapter.KEY_ROWID + "=" + rowId, null) > 0;
			} else {
				blIsSuccessful = true;
			}
			crsrListRowData.close();
			crsrListRowData = null;
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.deleteStorageEntry",
												"deleting Storage entry");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.deleteStorageEntry",
												"deleting Storage entry");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	protected boolean deleteRackEntry(long rowId) throws SQLException {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			Cursor crsrListRowData;
			crsrListRowData = fetchRackEntry(rowId);
			if (crsrListRowData.getCount() >= 0) {
				blIsSuccessful = this.sqliteDBObj
						.delete(	MyAppDbAdapter.MY_RACK_DB_TABLE,
									MyAppDbAdapter.KEY_ROWID + "=" + rowId, null) > 0;
			} else {
				blIsSuccessful = true;
			}
			crsrListRowData.close();
			crsrListRowData = null;
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.deleteRackEntry",
												"deleting Rack entry");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.deleteRackEntry",
												"deleting Rack entry");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	protected boolean deleteShelfEntry(long rowId) throws SQLException {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			Cursor crsrListRowData;
			crsrListRowData = fetchShelfEntry(rowId);
			if (crsrListRowData.getCount() >= 0) {
				blIsSuccessful = this.sqliteDBObj
						.delete(	MyAppDbAdapter.MY_SHELF_DB_TABLE,
									MyAppDbAdapter.KEY_ROWID + "=" + rowId, null) > 0;
			} else {
				blIsSuccessful = true;
			}
			crsrListRowData.close();
			crsrListRowData = null;
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.deleteShelfEntry",
												"deleting Shelf entry");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.deleteShelfEntry",
												"deleting Shelf entry");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	protected boolean deleteRoomEntry(long rowId) throws SQLException {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			Cursor crsrListRowData;
			crsrListRowData = fetchRoomEntry(rowId);
			if (crsrListRowData.getCount() >= 0) {
				blIsSuccessful = this.sqliteDBObj
						.delete(	MyAppDbAdapter.MY_ROOM_DB_TABLE,
									MyAppDbAdapter.KEY_ROWID + "=" + rowId, null) > 0;
			} else {
				blIsSuccessful = true;
			}
			crsrListRowData.close();
			crsrListRowData = null;
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.deleteRoomEntry",
												"deleting Room entry");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.deleteRoomEntry",
												"deleting Room entry");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
	
	protected boolean deleteReviewEntry(long rowId) throws SQLException {
		boolean blIsSuccessful = false;
		this.sqliteDBObj.beginTransaction();
		try {
			Cursor crsrListRowData;
			crsrListRowData = fetchReviewEntry(rowId);
			if (crsrListRowData.getCount() >= 0) {
				blIsSuccessful = this.sqliteDBObj
						.delete(	MyAppDbAdapter.MY_REVIEW_DB_TABLE,
									MyAppDbAdapter.KEY_ROWID + "=" + rowId, null) > 0;
			} else {
				blIsSuccessful = true;
			}
			crsrListRowData.close();
			crsrListRowData = null;
			if (blIsSuccessful == true) {
				this.sqliteDBObj.setTransactionSuccessful();
			}
		}// end try
		catch (SQLException error) {
			MyErrorLog<SQLException> errExcpError = new MyErrorLog<SQLException>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.deleteReviewEntry",
												"deleting Review entry");
			errExcpError = null;
			blIsSuccessful = false;
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					this.ctxContext);
			errExcpError.addToLogFile(	error, "MyAppDbAdapter.deleteReviewEntry",
												"deleting Review entry");
			errExcpError = null;
			blIsSuccessful = false;
		} finally {
			this.sqliteDBObj.endTransaction();
		}
		return blIsSuccessful;
	}
}// end MyAppDbSQL class
