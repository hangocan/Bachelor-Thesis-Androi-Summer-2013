
package com.project.styx;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
public class Report extends Activity {
	
	private MyAppDbSQL dbAppDbObj;
	private Cursor mBookCursor;
	private Cursor mRentCursor;
	private Cursor mAuthorCursor;
	private Cursor mEditorCursor;
	private Button screenshot;
	private String strCustDate;
	private TextView booktotal;
	private TextView bookrent;
	private TextView authortotal;
	private TextView editortotal;
	private TextView date;
	private int count;
	private int countrent;
	private int countauthor;
	private int counteditor;
	private String dateStr;
	private String EXPORT_FILEDIRECTORY;
	String strExportPathToUse = EXPORT_FILEDIRECTORY;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report);
		todaydate();
		fillBook();
		fillRent();
		fillAuthor();
		fillEditor();
		this.date = (TextView) findViewById(R.id.todaydate);
		this.booktotal = (TextView) findViewById(R.id.booktotalnumber);
		this.bookrent = (TextView) findViewById(R.id.bookrentnumber);
		this.authortotal = (TextView) findViewById(R.id.authornumber);
		this.editortotal = (TextView) findViewById(R.id.editornumber);
		this.screenshot = (Button) findViewById(R.id.screenshot);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		dateStr = dateFormat.format(date);
		EXPORT_FILEDIRECTORY = Environment.getExternalStorageDirectory()
				.toString() + "/Styx/";
		screenshot.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Bitmap bitmap = takeScreenshot();
				saveBitmap(bitmap);
			}
		});
	}
	
	private void todaydate() {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(
				getString(R.string.DATE_FORMAT_ISO8601));
		Date today = new Date();
		dateFormatter = new SimpleDateFormat(
				getString(R.string.DATE_FORMAT_ENTRY_UPDATE));
		strCustDate = dateFormatter.format(today);
		dateFormatter = null;
		this.date = (TextView) findViewById(R.id.todaydate);
		this.date.setText(strCustDate);
	}
	
	private int fillBook() {
		try {
			boolean dbOpenResult = this.dbAppDbObj.openDbAdapter();
			if (dbOpenResult) {
				/** Open the database and let mBookCursor get the data */
				this.mBookCursor = this.dbAppDbObj.fetchBook(0);
				this.startManagingCursor(this.mBookCursor);
				if (mBookCursor != null) {
					mBookCursor.moveToFirst();
					mBookCursor.moveToNext();
					count = mBookCursor.getCount();
					this.booktotal.setText(String.valueOf(count));
					mBookCursor.close();
					return count;
				}
			}
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					Report.this);
			errExcpError.addToLogFile(error, "Report.fillBook", "");
			errExcpError = null;
		}
		return count;
	}// end fillBook
	
	private int fillAuthor() {
		try {
			boolean dbOpenResult = this.dbAppDbObj.openDbAdapter();
			if (dbOpenResult) {
				this.mAuthorCursor = this.dbAppDbObj.fetchAuthor(0);
				this.startManagingCursor(this.mAuthorCursor);
				if (mAuthorCursor != null) {
					mAuthorCursor.moveToFirst();
					mAuthorCursor.moveToNext();
					countauthor = mAuthorCursor.getCount();
					this.authortotal.setText(String.valueOf(countauthor));
					mAuthorCursor.close();
					return countauthor;
				}
			}
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					Report.this);
			errExcpError.addToLogFile(error, "Report.fillAuthor", "");
			errExcpError = null;
		}
		return count;
	}// end fillAuthor
	
	private int fillEditor() {
		try {
			boolean dbOpenResult = this.dbAppDbObj.openDbAdapter();
			if (dbOpenResult) {
				/** Open the database and let mEditorCursor get the data */
				this.mEditorCursor = this.dbAppDbObj.fetchEditor(0);
				this.startManagingCursor(this.mEditorCursor);
				if (mEditorCursor != null) {
					mEditorCursor.moveToFirst();
					mEditorCursor.moveToNext();
					counteditor = mEditorCursor.getCount();
					this.editortotal.setText(String.valueOf(counteditor));
					mEditorCursor.close();
					return count;
				}
			}
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					Report.this);
			errExcpError.addToLogFile(error, "Report.fillEditor", "");
			errExcpError = null;
		}
		return count;
	}// end fillEditor
	
	public Bitmap takeScreenshot() {
		View rootView = findViewById(android.R.id.content).getRootView();
		rootView.setDrawingCacheEnabled(true);
		return rootView.getDrawingCache();
	}
	
	public void saveBitmap(Bitmap bitmap) {
		File imagePath = new File(EXPORT_FILEDIRECTORY + "/report" + dateStr
				+ ".png");
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(imagePath);
			bitmap.compress(CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			Log.e("GREC", e.getMessage(), e);
		} catch (IOException e) {
			Log.e("GREC", e.getMessage(), e);
		}
	}
	
	private int fillRent() {
		try {
			boolean dbOpenResult = this.dbAppDbObj.openDbAdapter();
			if (dbOpenResult) {
				/** Open the database and let mRentCursor get the data */
				this.mRentCursor = this.dbAppDbObj.fetchRent();
				this.startManagingCursor(this.mRentCursor);
				if (mRentCursor != null) {
					mRentCursor.moveToFirst();
					mRentCursor.moveToNext();
					countrent = mRentCursor.getCount();
					this.bookrent.setText(String.valueOf(countrent));
					mRentCursor.close();
					return countrent;
				}
			}
		} catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(
					Report.this);
			errExcpError.addToLogFile(error, "Report.fillRent", "");
			errExcpError = null;
		}
		return count;
	}// end fillRent
	
	protected void onResume() {
		super.onResume();
		this.dbAppDbObj = new MyAppDbSQL(this);
		fillRent();
		fillBook();
		todaydate();
		fillAuthor();
		fillEditor();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.report, menu);
		return true;
	}
}
