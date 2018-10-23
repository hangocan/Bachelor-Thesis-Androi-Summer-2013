
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
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.project.styx.CustAlrtMsgOptnListener.MessageCodes;

public class AddEditBook extends Activity {
	
	static final int DATE_DIALOG_ID = 1;
	private static MyDisplayAlertClass objDisplayAlertClass;
	public Integer OrigSpinnereditor = 0;
	public Integer OrigSpinnerpublisher = 0;
	public Integer OrigSpinnerlanguage = 0;
	public Integer OrigSpinnerbookshelf = 0;
	/** Define variables to use in the activity **/
	private EditText txtBookTitle;
	private EditText txtBookSubtitle;
	private EditText txtBookIsbn;
	private EditText txtBookYear;
	private EditText txtBookEdition;
	private EditText txtBookCoverlink;
	private EditText txtBookCovertype;
	private EditText txtBookOwncode;
	private EditText txtBookPurchaseprice;
	private EditText txtBookCurrentprice;
	private EditText txtBookPagenumber;
	private EditText txtBookRecommendfor;
	private EditText txtBookLoanto;
	private EditText txtBookLoantophone;
	private EditText txtBookLoandate;
	private EditText txtBookPreloan;
	private EditText txtBookinfo1;
	private EditText txtBookinfo2;
	private EditText txtBookinfo3;
	private EditText txtBookinfo4;
	private EditText txtBookinfo5;
	private EditText txtBookDescription;
	private Long mRowId;
	/** Declaration for must-required information **/
	private String strOrigTitle = "";
	private String strOrigSubtitle = "";
	private String strOrigIsbn = "";
	private String strOrigYear = "";
	private String strOrigEdition = "";
	private String strOrigCoverlink = "";
	private String strOrigCovertype = "";
	private String strOrigOwncode = "";
	private String strOrigPurchaseprice = "";
	private String strOrigCurrentprice = "";
	private String strOrigPagenumber = "";
	private String strOrigRecommendfor = "";
	private String strOrigLoanto = "";
	private String strOrigLoantophone = "";
	private String strOrigLoandate = "";
	private String strOrigPreloan = "";
	private String strOrigDescription = "";
	private String strOrigBookinfo1 = "";
	private String strOrigBookinfo2 = "";
	private String strOrigBookinfo3 = "";
	private String strOrigBookinfo4 = "";
	private String strOrigBookinfo5 = "";
	private Spinner spinnereditor;
	private Cursor spinnereditorcursor;
	private SimpleCursorAdapter spinnereditoradapter;
	private Spinner spinnerpublisher;
	private Cursor spinnerpublishercursor;
	private SimpleCursorAdapter spinnerpublisheradapter;
	private Spinner spinnerlanguage;
	private Cursor spinnerlanguagecursor;
	private SimpleCursorAdapter spinnerlanguageadapter;
	private Spinner spinnerbookshelf;
	private Cursor spinnerbookshelfcursor;
	private SimpleCursorAdapter spinnerbookshelfadapter;
	private boolean blSaveNew;
	private MyAppDbSQL dbAppDbObj;
	/** * Event focus change listener Executes code when the focus is changed. Use
	 * it to get data when the user change the information from the edit text
	 * field */
	private OnFocusChangeListener txtBookTitleOnFocusChangeListener = new OnFocusChangeListener() {
		
		public void onFocusChange(View arg0, boolean arg1) {
			InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			mgr.hideSoftInputFromWindow(arg0.getWindowToken(), 0);
		}
	};
	private OnFocusChangeListener txtBookSubtitleOnFocusChangeListener = new OnFocusChangeListener() {
		
		public void onFocusChange(View arg0, boolean arg1) {
			InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			mgr.hideSoftInputFromWindow(arg0.getWindowToken(), 0);
		}
	};
	private OnFocusChangeListener txtBookIsbnOnFocusChangeListener = new OnFocusChangeListener() {
		
		public void onFocusChange(View arg0, boolean arg1) {
			InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			mgr.hideSoftInputFromWindow(arg0.getWindowToken(), 0);
		}
	};
	private OnFocusChangeListener txtBookYearOnFocusChangeListener = new OnFocusChangeListener() {
		
		public void onFocusChange(View arg0, boolean arg1) {
			InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			mgr.hideSoftInputFromWindow(arg0.getWindowToken(), 0);
		}
	};
	private OnFocusChangeListener txtBookEditionOnFocusChangeListener = new OnFocusChangeListener() {
		
		public void onFocusChange(View arg0, boolean arg1) {
			InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			mgr.hideSoftInputFromWindow(arg0.getWindowToken(), 0);
		}
	};
	private OnFocusChangeListener txtBookCoverlinkOnFocusChangeListener = new OnFocusChangeListener() {
		
		public void onFocusChange(View arg0, boolean arg1) {
			InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			mgr.hideSoftInputFromWindow(arg0.getWindowToken(), 0);
		}
	};
	private OnFocusChangeListener txtBookCovertypeOnFocusChangeListener = new OnFocusChangeListener() {
		
		public void onFocusChange(View arg0, boolean arg1) {
			InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			mgr.hideSoftInputFromWindow(arg0.getWindowToken(), 0);
		}
	};
	private OnFocusChangeListener txtBookOwncodeOnFocusChangeListener = new OnFocusChangeListener() {
		
		public void onFocusChange(View arg0, boolean arg1) {
			InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			mgr.hideSoftInputFromWindow(arg0.getWindowToken(), 0);
		}
	};
	private OnFocusChangeListener txtBookPurchasepriceOnFocusChangeListener = new OnFocusChangeListener() {
		
		public void onFocusChange(View arg0, boolean arg1) {
			InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			mgr.hideSoftInputFromWindow(arg0.getWindowToken(), 0);
		}
	};
	private OnFocusChangeListener txtBookCurrentpriceOnFocusChangeListener = new OnFocusChangeListener() {
		
		public void onFocusChange(View arg0, boolean arg1) {
			InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			mgr.hideSoftInputFromWindow(arg0.getWindowToken(), 0);
		}
	};
	private OnFocusChangeListener txtBookPagenumberOnFocusChangeListener = new OnFocusChangeListener() {
		
		public void onFocusChange(View arg0, boolean arg1) {
			InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			mgr.hideSoftInputFromWindow(arg0.getWindowToken(), 0);
		}
	};
	private OnFocusChangeListener txtBookRecommendforOnFocusChangeListener = new OnFocusChangeListener() {
		
		public void onFocusChange(View arg0, boolean arg1) {
			InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			mgr.hideSoftInputFromWindow(arg0.getWindowToken(), 0);
		}
	};
	private OnFocusChangeListener txtBookLoantoOnFocusChangeListener = new OnFocusChangeListener() {
		
		public void onFocusChange(View arg0, boolean arg1) {
			InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			mgr.hideSoftInputFromWindow(arg0.getWindowToken(), 0);
		}
	};
	private OnFocusChangeListener txtBookLoantophoneOnFocusChangeListener = new OnFocusChangeListener() {
		
		public void onFocusChange(View arg0, boolean arg1) {
			InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			mgr.hideSoftInputFromWindow(arg0.getWindowToken(), 0);
		}
	};
	private OnFocusChangeListener txtBookLoandateOnFocusChangeListener = new OnFocusChangeListener() {
		
		public void onFocusChange(View arg0, boolean arg1) {
			InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			mgr.hideSoftInputFromWindow(arg0.getWindowToken(), 0);
		}
	};
	private OnFocusChangeListener txtBookPreloanOnFocusChangeListener = new OnFocusChangeListener() {
		
		public void onFocusChange(View arg0, boolean arg1) {
			InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			mgr.hideSoftInputFromWindow(arg0.getWindowToken(), 0);
		}
	};
	private OnFocusChangeListener txtBookinfo1OnFocusChangeListener = new OnFocusChangeListener() {
		
		public void onFocusChange(View arg0, boolean arg1) {
			InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			mgr.hideSoftInputFromWindow(arg0.getWindowToken(), 0);
		}
	};
	private OnFocusChangeListener txtBookinfo2OnFocusChangeListener = new OnFocusChangeListener() {
		
		public void onFocusChange(View arg0, boolean arg1) {
			InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			mgr.hideSoftInputFromWindow(arg0.getWindowToken(), 0);
		}
	};
	private OnFocusChangeListener txtBookinfo3OnFocusChangeListener = new OnFocusChangeListener() {
		
		public void onFocusChange(View arg0, boolean arg1) {
			InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			mgr.hideSoftInputFromWindow(arg0.getWindowToken(), 0);
		}
	};
	private OnFocusChangeListener txtBookinfo4OnFocusChangeListener = new OnFocusChangeListener() {
		
		public void onFocusChange(View arg0, boolean arg1) {
			InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			mgr.hideSoftInputFromWindow(arg0.getWindowToken(), 0);
		}
	};
	private OnFocusChangeListener txtBookinfo5OnFocusChangeListener = new OnFocusChangeListener() {
		
		public void onFocusChange(View arg0, boolean arg1) {
			InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			mgr.hideSoftInputFromWindow(arg0.getWindowToken(), 0);
		}
	};
	private OnFocusChangeListener txtBookDescriptionOnFocusChangeListener = new OnFocusChangeListener() {
		
		public void onFocusChange(View arg0, boolean arg1) {
			InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			mgr.hideSoftInputFromWindow(arg0.getWindowToken(), 0);
		}
	};
	
	/** myAppCleanup method Sets object variables to null
	 * @return void */
	protected void myAppCleanup() {
		
		if (objDisplayAlertClass != null) {
			objDisplayAlertClass.cleanUpClassVars();
			objDisplayAlertClass = null;
		}
		if (this.spinnereditorcursor != null) {
			this.spinnereditorcursor.close();
			this.spinnereditorcursor = null;
		}
		if (this.spinnereditoradapter != null) {
			this.spinnereditoradapter.getCursor().close();
			this.spinnereditoradapter = null;
		}
		if (this.spinnerpublishercursor != null) {
			this.spinnerpublishercursor.close();
			this.spinnerpublishercursor = null;
		}
		if (this.spinnerpublisheradapter != null) {
			this.spinnerpublisheradapter.getCursor().close();
			this.spinnerpublisheradapter = null;
		}
		if (this.spinnerlanguagecursor != null) {
			this.spinnerlanguagecursor.close();
			this.spinnerlanguagecursor = null;
		}
		if (this.spinnerlanguageadapter != null) {
			this.spinnerlanguageadapter.getCursor().close();
			this.spinnerlanguageadapter = null;
		}
		if (this.spinnerbookshelfcursor != null) {
			this.spinnerbookshelfcursor.close();
			this.spinnerbookshelfcursor = null;
		}
		if (this.spinnerbookshelfadapter != null) {
			this.spinnerbookshelfadapter.getCursor().close();
			this.spinnerbookshelfadapter = null;
		}
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			extras.clear();
			extras = null;
		}
	}
	
	
	private void myCancelMethod() {
		APPGlobalVars.SCR_PAUSE_CTL = "CANCEL";
		Bundle bundle = new Bundle();
		bundle.putBoolean("SHOW_FILTER", true);
		bundle.putBoolean("IS_SAVE_NEW", AddEditBook.this.blSaveNew);
		Intent mIntent = new Intent();
		mIntent.putExtras(bundle);
		setResult(RESULT_CANCELED, mIntent);
	
		bundle = null;
		mIntent = null;
		finish();
	}
	
	
	private void mySaveDataMethod() {
		String strTitle;
		String strSubtitle;
		String strIsbn;
		String strYear;
		String strEdition;
		String strCoverlink;
		String strCovertype;
		String strOwncode;
		String strPurchaseprice;
		String strCurrentprice;
		String strPagenumber;
		String strRecommendfor;
	
		String strLoanto;
		String strLoantophone;
		String strLoandate;
		String strPreloan;
		String strBookinfo1;
		String strBookinfo2;
		String strBookinfo3;
		String strBookinfo4;
		String strBookinfo5;
		String strDescription;
		int spinSpinnereditor;
		int spinSpinnerpublisher;
		int spinSpinnerlanguage;
		int spinSpinnerbookshelf;
		boolean boolSaveOk;
		boolSaveOk = true;
		
		if (this.txtBookTitle == null) {
			this.txtBookTitle = (EditText) findViewById(R.id.txtBookTitle);
		}
		try {
			/** Define the condition command for must filled field ***************************************/
			strTitle = this.txtBookTitle.getText().toString();
			if (strTitle.contentEquals("")) {
				objDisplayAlertClass = new MyDisplayAlertClass(AddEditBook.this,
						new CustAlrtMsgOptnListener(MessageCodes.ALERT_TYPE_MSG),
						"Missing Required Title", "Please enter or select a title for this entry.");
				boolSaveOk = false;
				txtBookTitle.requestFocus();
			}
			/******************************************************************************************/
			strSubtitle = this.txtBookSubtitle.getText().toString();
			strIsbn = this.txtBookIsbn.getText().toString();
			strYear = this.txtBookYear.getText().toString();
			strEdition = this.txtBookEdition.getText().toString();
			strCoverlink = this.txtBookCoverlink.getText().toString();
			strCovertype = this.txtBookCovertype.getText().toString();
			strOwncode = this.txtBookOwncode.getText().toString();
			strPurchaseprice = this.txtBookPurchaseprice.getText().toString();
			strCurrentprice = this.txtBookCurrentprice.getText().toString();
			strPagenumber = this.txtBookPagenumber.getText().toString();
			strRecommendfor = this.txtBookRecommendfor.getText().toString();
			strLoanto = this.txtBookLoanto.getText().toString();
			strLoantophone = this.txtBookLoantophone.getText().toString();
			strLoandate = this.txtBookLoandate.getText().toString();
			strPreloan = this.txtBookPreloan.getText().toString();
			strBookinfo1 = this.txtBookinfo1.getText().toString();
			strBookinfo2 = this.txtBookinfo2.getText().toString();
			strBookinfo3 = this.txtBookinfo3.getText().toString();
			strBookinfo4 = this.txtBookinfo4.getText().toString();
			strBookinfo5 = this.txtBookinfo5.getText().toString();
			strDescription = this.txtBookDescription.getText().toString();
			spinSpinnereditor = (int) this.spinnereditor.getSelectedItemId();
			spinSpinnerpublisher = (int) this.spinnerpublisher.getSelectedItemId();
			spinSpinnerlanguage = (int) this.spinnerlanguage.getSelectedItemId();
			spinSpinnerbookshelf = (int) this.spinnerbookshelf.getSelectedItemId();
			/** Save file command **/
			if (boolSaveOk == true) {
				APPGlobalVars.SCR_PAUSE_CTL = "SAVE";
				Bundle bundle = new Bundle();
				bundle.putString(MyAppDbAdapter.KEY_TITLE, this.txtBookTitle.getText().toString());
				bundle.putString(MyAppDbAdapter.KEY_SUBTITLE, strSubtitle);
				bundle.putString(MyAppDbAdapter.KEY_ISBN, strIsbn);
				bundle.putString(MyAppDbAdapter.KEY_YEAR, strYear);
				bundle.putString(MyAppDbAdapter.KEY_EDITION, strEdition);
				bundle.putString(MyAppDbAdapter.KEY_COVER_LINK, strCoverlink);
				bundle.putString(MyAppDbAdapter.KEY_COVER_TYPE, strCovertype);
				bundle.putString(MyAppDbAdapter.KEY_OWN_CODE, strOwncode);
				bundle.putString(MyAppDbAdapter.KEY_PURCHASE_PRICE, strPurchaseprice);
				bundle.putString(MyAppDbAdapter.KEY_CURRENT_PRICE, strCurrentprice);
				bundle.putString(MyAppDbAdapter.KEY_PAGE_NUMBER, strPagenumber);
				bundle.putString(MyAppDbAdapter.KEY_RECOMMEND_FOR, strRecommendfor);
				bundle.putString(MyAppDbAdapter.KEY_LOAN_TO, strLoanto);
				bundle.putString(MyAppDbAdapter.KEY_LOAN_DATE, strLoandate);
				bundle.putString(MyAppDbAdapter.KEY_LOAN_TO_PHONE, strLoantophone);
				bundle.putString(MyAppDbAdapter.KEY_PRELOAN, strPreloan);
				bundle.putInt(MyAppDbAdapter.KEY_BOOK_EDITORID, spinSpinnereditor);
				bundle.putInt(MyAppDbAdapter.KEY_BOOK_PUBLISHERID, spinSpinnerpublisher);
				bundle.putInt(MyAppDbAdapter.KEY_BOOK_LANGUAGEID, spinSpinnerlanguage);
				bundle.putInt(MyAppDbAdapter.KEY_BOOK_SHELFID, spinSpinnerbookshelf);
				bundle.putString(MyAppDbAdapter.KEY_BOOK_INFO_1, strBookinfo1);
				bundle.putString(MyAppDbAdapter.KEY_BOOK_INFO_2, strBookinfo2);
				bundle.putString(MyAppDbAdapter.KEY_BOOK_INFO_3, strBookinfo3);
				bundle.putString(MyAppDbAdapter.KEY_BOOK_INFO_4, strBookinfo4);
				bundle.putString(MyAppDbAdapter.KEY_BOOK_INFO_5, strBookinfo5);
				bundle.putString(MyAppDbAdapter.KEY_DESCRIPTION, strDescription);
				bundle.putBoolean("SHOW_FILTER", true);
				if (mRowId != null) {
					bundle.putLong(MyAppDbAdapter.KEY_ROWID, mRowId);
				}
				if (((!strOrigTitle.equals(strTitle)) && (!strOrigTitle.equals("")))) {
					bundle.putString("OrigTitle", strOrigTitle);
				} else {
					bundle.putString("OrigTitle", "");
				}
				if (((!strOrigSubtitle.equals(strSubtitle)) && (!strOrigSubtitle.equals("")))) {
					bundle.putString("OrigSubtitle", strOrigSubtitle);
				} else {
					bundle.putString("OrigSubtitle", "");
				}
				if (((!strOrigIsbn.equals(strIsbn)) && (!strOrigIsbn.equals("")))) {
					bundle.putString("OrigIsbn", strOrigIsbn);
				} else {
					bundle.putString("OrigIsbn", "");
				}
				if (((!strOrigYear.equals(strYear)) && (!strOrigYear.equals("")))) {
					bundle.putString("OrigYear", strOrigYear);
				} else {
					bundle.putString("OrigYear", "");
				}
				if (((!strOrigEdition.equals(strEdition)) && (!strOrigEdition.equals("")))) {
					bundle.putString("OrigEdition", strOrigEdition);
				} else {
					bundle.putString("OrigEdition", "");
				}
				if (((!strOrigCoverlink.equals(strCoverlink)) && (!strOrigCoverlink.equals("")))) {
					bundle.putString("OrigCoverlink", strOrigCoverlink);
				} else {
					bundle.putString("OrigCoverlink", "");
				}
				if (((!strOrigCovertype.equals(strCovertype)) && (!strOrigCovertype.equals("")))) {
					bundle.putString("OrigCovertype", strOrigCovertype);
				} else {
					bundle.putString("OrigCovertype", "");
				}
				if (((!strOrigOwncode.equals(strOwncode)) && (!strOrigOwncode.equals("")))) {
					bundle.putString("OrigOwncode", strOrigOwncode);
				} else {
					bundle.putString("OrigOwncode", "");
				}
				if (((!strOrigPurchaseprice.equals(strPurchaseprice)) && (!strOrigPurchaseprice
						.equals("")))) {
					bundle.putString("OrigPurchaseprice", strOrigPurchaseprice);
				} else {
					bundle.putString("OrigPurchaseprice", "");
				}// 
				if (((!strOrigCurrentprice.equals(strCurrentprice)) && (!strOrigCurrentprice.equals("")))) {
					bundle.putString("OrigCurrentprice", strOrigCurrentprice);
				} else {
					bundle.putString("OrigCurrentprice", "");
				}// 
				if (((!strOrigRecommendfor.equals(strRecommendfor)) && (!strOrigRecommendfor.equals("")))) {
					bundle.putString("OrigRecommendfor", strOrigRecommendfor);
				} else {
					bundle.putString("OrigRecommendfor", "");
				}// 
				if (((!strOrigPagenumber.equals(strPagenumber)) && (!strOrigPagenumber.equals("")))) {
					bundle.putString("OrigPagenumber", strOrigPagenumber);
				} else {
					bundle.putString("OrigPagenumber", "");
				}// 
				if (((!strOrigLoanto.equals(strLoanto)) && (!strOrigLoanto.equals("")))) {
					bundle.putString("OrigLoanto", strOrigLoanto);
				} else {
					bundle.putString("OrigLoanto", "");
				}// 
				if (((!strOrigLoantophone.equals(strLoantophone)) && (!strOrigLoantophone.equals("")))) {
					bundle.putString("OrigLoantophone", strOrigLoantophone);
				} else {
					bundle.putString("OrigLoantophone", "");
				}// 
				if (((!strOrigLoandate.equals(strLoandate)) && (!strOrigLoandate.equals("")))) {
					bundle.putString("OrigLoandate", strOrigLoandate);
				} else {
					bundle.putString("OrigLoandate", "");
				}// 
				if (((!strOrigPreloan.equals(strPreloan)) && (!strOrigPreloan.equals("")))) {
					bundle.putString("OrigPreloan", strOrigPreloan);
				} else {
					bundle.putString("OrigPreloan", "");
				}// 
				if (((!strOrigDescription.equals(strDescription)) && (!strOrigDescription.equals("")))) {
					bundle.putString("OrigDescription", strOrigDescription);
				} else {
					bundle.putString("OrigDescription", "");
				}// 
				if (((!OrigSpinnereditor.equals(spinSpinnereditor)) && (!OrigSpinnereditor.equals(0)))) {
					bundle.putInt("OrigSpinnereditor", OrigSpinnereditor);
				} else {
					bundle.putInt("OrigSpinnereditor", 0);
				}// 
				if (((!OrigSpinnerpublisher.equals(spinSpinnerpublisher)) && (!OrigSpinnerpublisher
						.equals(0)))) {
					bundle.putInt("OrigSpinnerpublisher", OrigSpinnerpublisher);
				} else {
					bundle.putInt("OrigSpinnerpublisher", 0);
				}// 
				if (((!OrigSpinnerlanguage.equals(spinSpinnerlanguage)) && (!OrigSpinnerlanguage
						.equals(0)))) {
					bundle.putInt("OrigSpinnerlanguage", OrigSpinnerlanguage);
				} else {
					bundle.putInt("OrigSpinnerlanguage", 0);
				}// 
				if (((!OrigSpinnerbookshelf.equals(spinSpinnerbookshelf)) && (!OrigSpinnerbookshelf.equals(0)))) {
					bundle.putInt("OrigSpinnerbookshelf", OrigSpinnerbookshelf);
				} else {
					bundle.putInt("OrigSpinnerbookshelf", 0);
				}// 
				if (((!strOrigBookinfo1.equals(strBookinfo1)) && (!strOrigBookinfo1.equals("")))) {
					bundle.putString("OrigBookinfo1", strOrigBookinfo1);
				} else {
					bundle.putString("OrigBookinfo1", "");
				}// 
				if (((!strOrigBookinfo2.equals(strBookinfo2)) && (!strOrigBookinfo2.equals("")))) {
					bundle.putString("OrigBookinfo2", strOrigBookinfo2);
				} else {
					bundle.putString("OrigBookinfo2", "");
				}// 
				if (((!strOrigBookinfo3.equals(strBookinfo3)) && (!strOrigBookinfo3.equals("")))) {
					bundle.putString("OrigBookinfo3", strOrigBookinfo3);
				} else {
					bundle.putString("OrigBookinfo3", "");
				}// 
				if (((!strOrigBookinfo4.equals(strBookinfo4)) && (!strOrigBookinfo4.equals("")))) {
					bundle.putString("OrigBookinfo4", strOrigBookinfo4);
				} else {
					bundle.putString("OrigBookinfo4", "");
				}// 
				if (((!strOrigBookinfo5.equals(strBookinfo5)) && (!strOrigBookinfo5.equals("")))) {
					bundle.putString("OrigBookinfo5", strOrigBookinfo5);
				} else {
					bundle.putString("OrigBookinfo5", "");
				}// 
				bundle.putBoolean("IS_SAVE_NEW", AddEditBook.this.blSaveNew);
				Intent mIntent = new Intent();
				mIntent.putExtras(bundle);
				setResult(RESULT_OK, mIntent);
				// cleanup objects
				bundle = null;
				mIntent = null;
				finish();
			}//  boolSaveOk == true
		}// end try
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(AddEditBook.this);
			errExcpError.addToLogFile(error, "AddEditBook.mySaveDataMethod", "");
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
			AddEditBook.this.blSaveNew = false;
			txtBookTitle = (EditText) findViewById(R.id.txtBookTitle);
			txtBookSubtitle = (EditText) findViewById(R.id.txtBookSubtitle);
			txtBookIsbn = (EditText) findViewById(R.id.txtBookIsbn);
			txtBookYear = (EditText) findViewById(R.id.txtBookYear);
			txtBookEdition = (EditText) findViewById(R.id.txtBookEdition);
			txtBookCoverlink = (EditText) findViewById(R.id.txtBookCoverlink);
			txtBookCovertype = (EditText) findViewById(R.id.txtBookCovertype);
			txtBookOwncode = (EditText) findViewById(R.id.txtBookOwncode);
			txtBookPurchaseprice = (EditText) findViewById(R.id.txtBookPurchaseprice);
			txtBookCurrentprice = (EditText) findViewById(R.id.txtBookCurrentprice);
			txtBookPagenumber = (EditText) findViewById(R.id.txtBookPagenumber);
			txtBookRecommendfor = (EditText) findViewById(R.id.txtBookRecommendfor);
			txtBookLoanto = (EditText) findViewById(R.id.txtBookLoanto);
			txtBookLoantophone = (EditText) findViewById(R.id.txtBookLoantophone);
			txtBookLoandate = (EditText) findViewById(R.id.txtBookLoandate);
			txtBookPreloan = (EditText) findViewById(R.id.txtBookPreloan);
			this.spinnereditor = (Spinner) findViewById(R.id.spinnereditor);
			this.spinnerpublisher = (Spinner) findViewById(R.id.spinnerpublisher);
			this.spinnerlanguage = (Spinner) findViewById(R.id.spinnerlanguage);
			this.spinnerbookshelf = (Spinner) findViewById(R.id.spinnerbookshelf);
			txtBookinfo1 = (EditText) findViewById(R.id.txtBookinfo1);
			txtBookinfo2 = (EditText) findViewById(R.id.txtBookinfo2);
			txtBookinfo3 = (EditText) findViewById(R.id.txtBookinfo3);
			txtBookinfo4 = (EditText) findViewById(R.id.txtBookinfo4);
			txtBookinfo5 = (EditText) findViewById(R.id.txtBookinfo5);
			txtBookDescription = (EditText) findViewById(R.id.txtBookDescription);
			if (intent != null) {
				Bundle extras = intent.getExtras();
				if (extras != null) {
					switch (requestCode) {
						default:
							break;
					}// end switch
				}//  (extras != null)
			}//  (intent != null)
		}// end try
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(AddEditBook.this);
			errExcpError.addToLogFile(error, "AddEditBook.onActivityResult", "");
			errExcpError = null;
		}// end try/catch (Exception error)
	}// end onActivityResult

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_edit_book);
		fillspinnereditor();
		fillspinnerpublisher();
		fillspinnerlanguage();
		fillspinnerbookshelf();
		try {
			try {
				/** This code will let the variables get data from a certain input
				 * edit text field */
				txtBookTitle = (EditText) findViewById(R.id.txtBookTitle);
				txtBookSubtitle = (EditText) findViewById(R.id.txtBookSubtitle);
				txtBookIsbn = (EditText) findViewById(R.id.txtBookIsbn);
				txtBookYear = (EditText) findViewById(R.id.txtBookYear);
				txtBookEdition = (EditText) findViewById(R.id.txtBookEdition);
				txtBookCoverlink = (EditText) findViewById(R.id.txtBookCoverlink);
				txtBookCovertype = (EditText) findViewById(R.id.txtBookCovertype);
				txtBookOwncode = (EditText) findViewById(R.id.txtBookOwncode);
				txtBookPurchaseprice = (EditText) findViewById(R.id.txtBookPurchaseprice);
				txtBookCurrentprice = (EditText) findViewById(R.id.txtBookCurrentprice);
				txtBookPagenumber = (EditText) findViewById(R.id.txtBookPagenumber);
				txtBookRecommendfor = (EditText) findViewById(R.id.txtBookRecommendfor);
				txtBookLoanto = (EditText) findViewById(R.id.txtBookLoanto);
				txtBookLoantophone = (EditText) findViewById(R.id.txtBookLoantophone);
				txtBookLoandate = (EditText) findViewById(R.id.txtBookLoandate);
				txtBookPreloan = (EditText) findViewById(R.id.txtBookPreloan);
				this.spinnereditor = (Spinner) findViewById(R.id.spinnereditor);
				this.spinnerpublisher = (Spinner) findViewById(R.id.spinnerpublisher);
				this.spinnerlanguage = (Spinner) findViewById(R.id.spinnerlanguage);
				this.spinnerbookshelf = (Spinner) findViewById(R.id.spinnerbookshelf);
				txtBookinfo1 = (EditText) findViewById(R.id.txtBookinfo1);
				txtBookinfo2 = (EditText) findViewById(R.id.txtBookinfo2);
				txtBookinfo3 = (EditText) findViewById(R.id.txtBookinfo3);
				txtBookinfo4 = (EditText) findViewById(R.id.txtBookinfo4);
				txtBookinfo5 = (EditText) findViewById(R.id.txtBookinfo5);
				txtBookDescription = (EditText) findViewById(R.id.txtBookDescription);
				mRowId = null;
				Bundle extras = getIntent().getExtras();
				if (extras != null) {
					String strTitle = extras.getString(MyAppDbAdapter.KEY_TITLE);
					String strSubtitle = extras.getString(MyAppDbAdapter.KEY_SUBTITLE);
					String strIsbn = extras.getString(MyAppDbAdapter.KEY_ISBN);
					String strYear = extras.getString(MyAppDbAdapter.KEY_YEAR);
					String strEdition = extras.getString(MyAppDbAdapter.KEY_EDITION);
					String strCoverlink = extras.getString(MyAppDbAdapter.KEY_COVER_LINK);
					String strCovertype = extras.getString(MyAppDbAdapter.KEY_COVER_TYPE);
					String strOwncode = extras.getString(MyAppDbAdapter.KEY_OWN_CODE);
					String strPurchaseprice = extras.getString(MyAppDbAdapter.KEY_PURCHASE_PRICE);
					String strCurrentprice = extras.getString(MyAppDbAdapter.KEY_CURRENT_PRICE);
					String strPagenumber = extras.getString(MyAppDbAdapter.KEY_PAGE_NUMBER);
					String strRecommendfor = extras.getString(MyAppDbAdapter.KEY_RECOMMEND_FOR);
					String strLoanto = extras.getString(MyAppDbAdapter.KEY_LOAN_TO);
					String strLoantophone = extras.getString(MyAppDbAdapter.KEY_LOAN_TO_PHONE);
					String strLoandate = extras.getString(MyAppDbAdapter.KEY_LOAN_DATE);
					String strPreloan = extras.getString(MyAppDbAdapter.KEY_PRELOAN);
					String strDescription = extras.getString(MyAppDbAdapter.KEY_DESCRIPTION);
					int intSpinnereditor = Integer.parseInt(extras
							.getString(MyAppDbAdapter.KEY_BOOK_EDITORID));
					int intSpinnerpublisher = Integer.parseInt(extras
							.getString(MyAppDbAdapter.KEY_BOOK_PUBLISHERID));
					int intSpinnerlanguage = Integer.parseInt(extras
							.getString(MyAppDbAdapter.KEY_BOOK_LANGUAGEID));
					int intSpinnerbookshelf = Integer.parseInt(extras
							.getString(MyAppDbAdapter.KEY_BOOK_SHELFID));
					String strBookinfo1 = extras.getString(MyAppDbAdapter.KEY_BOOK_INFO_1);
					String strBookinfo2 = extras.getString(MyAppDbAdapter.KEY_BOOK_INFO_2);
					String strBookinfo3 = extras.getString(MyAppDbAdapter.KEY_BOOK_INFO_3);
					String strBookinfo4 = extras.getString(MyAppDbAdapter.KEY_BOOK_INFO_4);
					String strBookinfo5 = extras.getString(MyAppDbAdapter.KEY_BOOK_INFO_5);
					mRowId = extras.getLong(MyAppDbAdapter.KEY_ROWID);
					/** This is for compulsory information field in the edit stage. In
					 * order to keep those field filled if the user unintentionally
					 * delete the field, an original information will be keep there **/
					if (strTitle != null && !strTitle.equals("") && !strTitle.equals(" ")) {
						txtBookTitle.setText(strTitle);
						strOrigTitle = strTitle;
					}
					if (strSubtitle != null && !strSubtitle.equals("") && !strSubtitle.equals(" ")) {
						txtBookSubtitle.setText(strSubtitle);
						strOrigSubtitle = strSubtitle;
					}
					if (strIsbn != null && !strIsbn.equals("") && !strIsbn.equals(" ")) {
						txtBookIsbn.setText(strIsbn);
						strOrigIsbn = strIsbn;
					}
					if (strYear != null && !strYear.equals("") && !strYear.equals(" ")) {
						txtBookYear.setText(strYear);
						strOrigYear = strYear;
					}
					if (strOwncode != null && !strOwncode.equals("") && !strOwncode.equals(" ")) {
						txtBookOwncode.setText(strOwncode);
						strOrigOwncode = strOwncode;
					}
					if (strEdition != null && !strEdition.equals("") && !strEdition.equals(" ")) {
						txtBookEdition.setText(strEdition);
						strOrigEdition = strEdition;
					}
					if (strCoverlink != null && !strCoverlink.equals("") && !strCoverlink.equals(" ")) {
						txtBookCoverlink.setText(strCoverlink);
						strOrigCoverlink = strCoverlink;
					}
					if (strCovertype != null && !strCovertype.equals("") && !strCovertype.equals(" ")) {
						txtBookCovertype.setText(strCovertype);
						strOrigCovertype = strCovertype;
					}
					if (strPagenumber != null && !strPagenumber.equals("") && !strPagenumber.equals(" ")) {
						txtBookPagenumber.setText(strPagenumber);
						strOrigPagenumber = strPagenumber;
					}
					if (strRecommendfor != null && !strRecommendfor.equals("")
							&& !strRecommendfor.equals(" ")) {
						txtBookRecommendfor.setText(strRecommendfor);
						strOrigRecommendfor = strRecommendfor;
					}
					if (strPurchaseprice != null && !strPurchaseprice.equals("")
							&& !strPurchaseprice.equals(" ")) {
						txtBookPurchaseprice.setText(strPurchaseprice);
						strOrigPurchaseprice = strPurchaseprice;
					}
					if (strCurrentprice != null && !strCurrentprice.equals("")
							&& !strCurrentprice.equals(" ")) {
						txtBookCurrentprice.setText(strCurrentprice);
						strOrigCurrentprice = strCurrentprice;
					}
					if (strLoanto != null && !strLoanto.equals("") && !strLoanto.equals(" ")) {
						txtBookLoanto.setText(strLoanto);
						strOrigLoanto = strLoanto;
					}
					if (strLoantophone != null && !strLoantophone.equals("")
							&& !strLoantophone.equals(" ")) {
						txtBookLoantophone.setText(strLoantophone);
						strOrigLoantophone = strLoantophone;
					}
					if (strLoandate != null && !strLoandate.equals("") && !strLoandate.equals(" ")) {
						txtBookLoandate.setText(strLoandate);
						strOrigLoandate = strLoandate;
					}
					if (strPreloan != null && !strPreloan.equals("") && !strPreloan.equals(" ")) {
						txtBookPreloan.setText(strPreloan);
						strOrigPreloan = strPreloan;
					}
					if (strDescription != null && !strDescription.equals("")
							&& !strDescription.equals(" ")) {
						txtBookDescription.setText(strDescription);
						strOrigDescription = strDescription;
					}
					Integer valueeditor = Integer.valueOf(intSpinnereditor);
					if (valueeditor != null && intSpinnereditor != 0) {
						OrigSpinnereditor = intSpinnereditor;
					}
					Integer valuepublisher = Integer.valueOf(intSpinnerpublisher);
					if (valuepublisher != null && intSpinnerpublisher != 0) {
						OrigSpinnerpublisher = intSpinnerpublisher;
					}
					Integer valuelanguage = Integer.valueOf(intSpinnerlanguage);
					if (valuelanguage != null && intSpinnerlanguage != 0) {
						OrigSpinnerlanguage = intSpinnerlanguage;
					}
					Integer valueshelf = Integer.valueOf(intSpinnerbookshelf);
					if (valueshelf != null && intSpinnerbookshelf != 0) {
						OrigSpinnerbookshelf = intSpinnerbookshelf;
					}
					if (strBookinfo1 != null && !strBookinfo1.equals("") && !strBookinfo1.equals(" ")) {
						txtBookinfo1.setText(strBookinfo1);
						strOrigBookinfo1 = strBookinfo1;
					}
					if (strBookinfo2 != null && !strBookinfo2.equals("") && !strBookinfo2.equals(" ")) {
						txtBookinfo2.setText(strBookinfo2);
						strOrigBookinfo2 = strBookinfo2;
					}
					if (strBookinfo3 != null && !strBookinfo3.equals("") && !strBookinfo3.equals(" ")) {
						txtBookinfo3.setText(strBookinfo3);
						strOrigBookinfo3 = strBookinfo3;
					}
					if (strBookinfo4 != null && !strBookinfo4.equals("") && !strBookinfo4.equals(" ")) {
						txtBookinfo4.setText(strBookinfo4);
						strOrigBookinfo4 = strBookinfo4;
					}
					if (strBookinfo5 != null && !strBookinfo5.equals("") && !strBookinfo5.equals(" ")) {
						txtBookinfo5.setText(strBookinfo5);
						strOrigBookinfo5 = strBookinfo5;
					}
				}
				/*** This code set event text focus change listeners */
				txtBookTitle.setOnFocusChangeListener(txtBookTitleOnFocusChangeListener);
				txtBookSubtitle.setOnFocusChangeListener(txtBookSubtitleOnFocusChangeListener);
				txtBookIsbn.setOnFocusChangeListener(txtBookIsbnOnFocusChangeListener);
				txtBookYear.setOnFocusChangeListener(txtBookYearOnFocusChangeListener);
				txtBookEdition.setOnFocusChangeListener(txtBookEditionOnFocusChangeListener);
				txtBookCoverlink.setOnFocusChangeListener(txtBookCoverlinkOnFocusChangeListener);
				txtBookCovertype.setOnFocusChangeListener(txtBookCovertypeOnFocusChangeListener);
				txtBookOwncode.setOnFocusChangeListener(txtBookOwncodeOnFocusChangeListener);
				txtBookPurchaseprice.setOnFocusChangeListener(txtBookPurchasepriceOnFocusChangeListener);
				txtBookCurrentprice.setOnFocusChangeListener(txtBookCurrentpriceOnFocusChangeListener);
				txtBookPagenumber.setOnFocusChangeListener(txtBookPagenumberOnFocusChangeListener);
				txtBookRecommendfor.setOnFocusChangeListener(txtBookRecommendforOnFocusChangeListener);
				txtBookLoanto.setOnFocusChangeListener(txtBookLoantoOnFocusChangeListener);
				txtBookLoantophone.setOnFocusChangeListener(txtBookLoantophoneOnFocusChangeListener);
				txtBookLoandate.setOnFocusChangeListener(txtBookLoandateOnFocusChangeListener);
				txtBookPreloan.setOnFocusChangeListener(txtBookPreloanOnFocusChangeListener);
				txtBookinfo1.setOnFocusChangeListener(txtBookinfo1OnFocusChangeListener);
				txtBookinfo2.setOnFocusChangeListener(txtBookinfo2OnFocusChangeListener);
				txtBookinfo3.setOnFocusChangeListener(txtBookinfo3OnFocusChangeListener);
				txtBookinfo4.setOnFocusChangeListener(txtBookinfo4OnFocusChangeListener);
				txtBookinfo5.setOnFocusChangeListener(txtBookinfo5OnFocusChangeListener);
				txtBookDescription.setOnFocusChangeListener(txtBookDescriptionOnFocusChangeListener);
			}// end try statements
			catch (Exception error) {
				MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(this);
				errExcpError.addToLogFile(error, "AddEditBook.onCreate", "");
				errExcpError = null;
			}// end try/catch (Exception error)
		}
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(this);
			errExcpError.addToLogFile(error, "AddEditBook.onCreate", "opening database");
			errExcpError = null;
		}
	}// end onCreate

	private void fillspinnereditor() {
		try {
			boolean dbOpenResult = this.dbAppDbObj.openDbAdapter();
			if (dbOpenResult) {
				spinnereditorcursor = dbAppDbObj.fetchEditor(Editor_List.intSortOption);
				this.startManagingCursor(this.spinnereditorcursor);
				spinnereditorcursor.moveToFirst();
				spinnereditor = (Spinner) findViewById(R.id.spinnereditor);
				String[] from = new String[] { MyAppDbAdapter.KEY_EDITORFULLNAME,
						MyAppDbAdapter.KEY_ROWID };
				int[] to = new int[] { android.R.id.text1 };
				spinnereditoradapter = new SimpleCursorAdapter(AddEditBook.this,
						android.R.layout.simple_spinner_item, this.spinnereditorcursor, from, to);
				spinnereditor.setAdapter(this.spinnereditoradapter);
				Bundle extras = getIntent().getExtras();
				int intSpinnereditor = Integer.parseInt(extras
						.getString(MyAppDbAdapter.KEY_BOOK_EDITORID));
				for (int i = 0; i < spinnereditor.getAdapter().getCount(); i++) {
					int id = (int) spinnereditor.getItemIdAtPosition(i);
					if (id == intSpinnereditor) {
						spinnereditor.setSelection(i);
					}
				}
			}
		}
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(AddEditBook.this);
			errExcpError.addToLogFile(error, "AddEditRack.fillspinnereditor ", "");
			errExcpError = null;
		}// end try/catch (Exception error)
	}

	private void fillspinnerpublisher() {
		try {
			boolean dbOpenResult = this.dbAppDbObj.openDbAdapter();
			if (dbOpenResult) {
				spinnerpublishercursor = dbAppDbObj.fetchPublisher(Publisher_List.intSortOption);
				this.startManagingCursor(this.spinnerpublishercursor);
				spinnerpublishercursor.moveToFirst();
				spinnerpublisher = (Spinner) findViewById(R.id.spinnerpublisher);
				String[] from = new String[] { MyAppDbAdapter.KEY_PUBLISHER, MyAppDbAdapter.KEY_ROWID };
				int[] to = new int[] { android.R.id.text1 };
				spinnerpublisheradapter = new SimpleCursorAdapter(AddEditBook.this,
						android.R.layout.simple_spinner_item, this.spinnerpublishercursor, from, to);
				spinnerpublisher.setAdapter(this.spinnerpublisheradapter);
				Bundle extras = getIntent().getExtras();
				int intSpinnerpublisher = Integer.parseInt(extras
						.getString(MyAppDbAdapter.KEY_BOOK_PUBLISHERID));
				for (int i = 0; i < spinnerpublisher.getAdapter().getCount(); i++) {
					int id = (int) spinnerpublisher.getItemIdAtPosition(i);
					if (id == intSpinnerpublisher) {
						spinnerpublisher.setSelection(i);
					}
				}
			}
		}
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(AddEditBook.this);
			errExcpError.addToLogFile(error, "AddEditBook.fillspinnerpublisher ", "");
			errExcpError = null;
		}
	}

	private void fillspinnerlanguage() {
		try {
			boolean dbOpenResult = this.dbAppDbObj.openDbAdapter();
			if (dbOpenResult) {
				spinnerlanguagecursor = dbAppDbObj.fetchLanguage();
				this.startManagingCursor(this.spinnerlanguagecursor);
				spinnerlanguagecursor.moveToFirst();
				spinnerlanguage = (Spinner) findViewById(R.id.spinnerlanguage);
				String[] from = new String[] { MyAppDbAdapter.KEY_LANGUAGE, MyAppDbAdapter.KEY_ROWID };
				int[] to = new int[] { android.R.id.text1 };
				spinnerlanguageadapter = new SimpleCursorAdapter(AddEditBook.this,
						android.R.layout.simple_spinner_item, this.spinnerlanguagecursor, from, to);
				spinnerlanguage.setAdapter(this.spinnerlanguageadapter);
				Bundle extras = getIntent().getExtras();
				int intSpinnerlanguage = Integer.parseInt(extras
						.getString(MyAppDbAdapter.KEY_BOOK_LANGUAGEID));
				for (int i = 0; i < spinnerlanguage.getAdapter().getCount(); i++) {
					int id = (int) spinnerlanguage.getItemIdAtPosition(i);
					if (id == intSpinnerlanguage) {
						spinnerlanguage.setSelection(i);
					}
				}
			}
		}
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(AddEditBook.this);
			errExcpError.addToLogFile(error, "AddEditBook.fillspinnerlanguage ", "");
			errExcpError = null;
		}// end try/catch (Exception error)
	}

	private void fillspinnerbookshelf() {
		try {
			boolean dbOpenResult = this.dbAppDbObj.openDbAdapter();
			if (dbOpenResult) {
				spinnerbookshelfcursor = dbAppDbObj.fetchShelf();
				this.startManagingCursor(this.spinnerbookshelfcursor);
				spinnerbookshelfcursor.moveToFirst();
				spinnerbookshelf = (Spinner) findViewById(R.id.spinnerbookshelf);
				String[] from = new String[] { MyAppDbAdapter.KEY_SHELF_LABEL, MyAppDbAdapter.KEY_ROWID };
				int[] to = new int[] { android.R.id.text1 };
				spinnerbookshelfadapter = new SimpleCursorAdapter(AddEditBook.this,
						android.R.layout.simple_spinner_item, this.spinnerbookshelfcursor, from, to);
				spinnerbookshelf.setAdapter(this.spinnerbookshelfadapter);
				Bundle extras = getIntent().getExtras();
				int intSpinnerbookshelf = Integer.parseInt(extras
						.getString(MyAppDbAdapter.KEY_BOOK_SHELFID));
				for (int i = 0; i < spinnerbookshelf.getAdapter().getCount(); i++) {
					int id = (int) spinnerbookshelf.getItemIdAtPosition(i);
					if (id == intSpinnerbookshelf) {
						spinnerbookshelf.setSelection(i);
					}
				}
			}
		}
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(AddEditBook.this);
			errExcpError.addToLogFile(error, "AddEditShelf.fillspinnerbookshelf ", "");
			errExcpError = null;
		}// end try/catch (Exception error)
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.entry_menu, menu);
		return true;
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

	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		try {
			switch (keyCode) {
				case KeyEvent.KEYCODE_ENTER:
					if (txtBookTitle.hasFocus()) {
						txtBookSubtitle.requestFocus();
						return true;
					} else if (txtBookSubtitle.hasFocus()) {
						txtBookIsbn.requestFocus();
						return true;
					} else if (txtBookIsbn.hasFocus()) {
						txtBookYear.requestFocus();
						return true;
					} else if (txtBookYear.hasFocus()) {
						txtBookEdition.requestFocus();
						return true;
					} else if (txtBookEdition.hasFocus()) {
						txtBookCoverlink.requestFocus();
						return true;
					} else if (txtBookCoverlink.hasFocus()) {
						txtBookCovertype.requestFocus();
						return true;
					} else if (txtBookCovertype.hasFocus()) {
						txtBookOwncode.requestFocus();
						return true;
					} else if (txtBookOwncode.hasFocus()) {
						txtBookPurchaseprice.requestFocus();
						return true;
					} else if (txtBookPurchaseprice.hasFocus()) {
						txtBookCurrentprice.requestFocus();
						return true;
					} else if (txtBookCurrentprice.hasFocus()) {
						txtBookPagenumber.requestFocus();
						return true;
					} else if (txtBookPagenumber.hasFocus()) {
						txtBookRecommendfor.requestFocus();
						return true;
					} else if (txtBookRecommendfor.hasFocus()) {
						txtBookLoanto.requestFocus();
						return true;
					} else if (txtBookLoanto.hasFocus()) {
						txtBookLoantophone.requestFocus();
						return true;
					} else if (txtBookLoanto.hasFocus()) {
						txtBookLoantophone.requestFocus();
						return true;
					} else if (txtBookLoantophone.hasFocus()) {
						txtBookLoandate.requestFocus();
						return true;
					} else if (txtBookLoandate.hasFocus()) {
						txtBookPreloan.requestFocus();
						return true;
					} else if (txtBookPreloan.hasFocus()) {
						txtBookinfo1.requestFocus();
						return true;
					} else if (txtBookinfo1.hasFocus()) {
						txtBookinfo2.requestFocus();
						return true;
					} else if (txtBookinfo2.hasFocus()) {
						txtBookinfo3.requestFocus();
						return true;
					} else if (txtBookinfo3.hasFocus()) {
						txtBookinfo4.requestFocus();
						return true;
					} else if (txtBookinfo4.hasFocus()) {
						txtBookinfo5.requestFocus();
						return true;
					} else if (txtBookinfo5.hasFocus()) {
						txtBookDescription.requestFocus();
						return true;
					}
				default:
					return false;
			}
		}// end try
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(AddEditBook.this);
			errExcpError.addToLogFile(error, "AddEditBook.onKeyUp", "");
			errExcpError = null;
			return false;
		}// end try/catch (Exception error)
	}// end onKeyUp

	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.save_menu) {
		
			mySaveDataMethod();
		} else if (itemId == R.id.cancel_menu) {
			
			myCancelMethod();
		} else if (itemId == R.id.quit) {
		
			Intent intentQuitActivity = new Intent(AddEditBook.this, Book_List.class);
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
			txtBookTitle = (EditText) findViewById(R.id.txtBookTitle);
			txtBookSubtitle = (EditText) findViewById(R.id.txtBookSubtitle);
			txtBookIsbn = (EditText) findViewById(R.id.txtBookIsbn);
			txtBookYear = (EditText) findViewById(R.id.txtBookYear);
			txtBookEdition = (EditText) findViewById(R.id.txtBookEdition);
			txtBookCoverlink = (EditText) findViewById(R.id.txtBookCoverlink);
			txtBookCovertype = (EditText) findViewById(R.id.txtBookCovertype);
			txtBookOwncode = (EditText) findViewById(R.id.txtBookOwncode);
			txtBookPurchaseprice = (EditText) findViewById(R.id.txtBookPurchaseprice);
			txtBookCurrentprice = (EditText) findViewById(R.id.txtBookCurrentprice);
			txtBookPagenumber = (EditText) findViewById(R.id.txtBookPagenumber);
			txtBookRecommendfor = (EditText) findViewById(R.id.txtBookRecommendfor);
			txtBookLoanto = (EditText) findViewById(R.id.txtBookLoanto);
			txtBookLoantophone = (EditText) findViewById(R.id.txtBookLoantophone);
			txtBookLoandate = (EditText) findViewById(R.id.txtBookLoandate);
			txtBookPreloan = (EditText) findViewById(R.id.txtBookPreloan);
			this.spinnereditor = (Spinner) findViewById(R.id.spinnereditor);
			this.spinnerpublisher = (Spinner) findViewById(R.id.spinnerpublisher);
			this.spinnerlanguage = (Spinner) findViewById(R.id.spinnerlanguage);
			this.spinnerbookshelf = (Spinner) findViewById(R.id.spinnerbookshelf);
			txtBookinfo1 = (EditText) findViewById(R.id.txtBookinfo1);
			txtBookinfo2 = (EditText) findViewById(R.id.txtBookinfo2);
			txtBookinfo3 = (EditText) findViewById(R.id.txtBookinfo3);
			txtBookinfo4 = (EditText) findViewById(R.id.txtBookinfo4);
			txtBookinfo5 = (EditText) findViewById(R.id.txtBookinfo5);
			txtBookDescription = (EditText) findViewById(R.id.txtBookDescription);
			mRowId = null;
			if (savedInstanceState != null) {
				String strTitle = savedInstanceState.getString(MyAppDbAdapter.KEY_TITLE);
				String strSubtitle = savedInstanceState.getString(MyAppDbAdapter.KEY_SUBTITLE);
				String strIsbn = savedInstanceState.getString(MyAppDbAdapter.KEY_ISBN);
				String strYear = savedInstanceState.getString(MyAppDbAdapter.KEY_YEAR);
				String strEdition = savedInstanceState.getString(MyAppDbAdapter.KEY_EDITION);
				String strCoverlink = savedInstanceState.getString(MyAppDbAdapter.KEY_COVER_LINK);
				String strCovertype = savedInstanceState.getString(MyAppDbAdapter.KEY_COVER_TYPE);
				String strOwncode = savedInstanceState.getString(MyAppDbAdapter.KEY_OWN_CODE);
				String strPurchaseprice = savedInstanceState
						.getString(MyAppDbAdapter.KEY_PURCHASE_PRICE);
				String strCurrentprice = savedInstanceState.getString(MyAppDbAdapter.KEY_CURRENT_PRICE);
				String strPagenumber = savedInstanceState.getString(MyAppDbAdapter.KEY_PAGE_NUMBER);
				String strRecommendfor = savedInstanceState.getString(MyAppDbAdapter.KEY_RECOMMEND_FOR);
				String strLoanto = savedInstanceState.getString(MyAppDbAdapter.KEY_LOAN_TO);
				String strLoantophone = savedInstanceState.getString(MyAppDbAdapter.KEY_LOAN_TO_PHONE);
				String strLoandate = savedInstanceState.getString(MyAppDbAdapter.KEY_LOAN_DATE);
				String strPreloan = savedInstanceState.getString(MyAppDbAdapter.KEY_PRELOAN);
				String strDescription = savedInstanceState.getString(MyAppDbAdapter.KEY_DESCRIPTION);
				int intSpinnereditor = savedInstanceState.getInt(MyAppDbAdapter.KEY_BOOK_EDITORID);
				int intSpinnerpublisher = savedInstanceState
						.getInt(MyAppDbAdapter.KEY_BOOK_PUBLISHERID);
				int intSpinnerlanguage = savedInstanceState.getInt(MyAppDbAdapter.KEY_BOOK_LANGUAGEID);
				int intSpinnerbookshelf = savedInstanceState.getInt(MyAppDbAdapter.KEY_BOOK_SHELFID);
				String strBookinfo1 = savedInstanceState.getString(MyAppDbAdapter.KEY_BOOK_INFO_1);
				String strBookinfo2 = savedInstanceState.getString(MyAppDbAdapter.KEY_BOOK_INFO_2);
				String strBookinfo3 = savedInstanceState.getString(MyAppDbAdapter.KEY_BOOK_INFO_3);
				String strBookinfo4 = savedInstanceState.getString(MyAppDbAdapter.KEY_BOOK_INFO_4);
				String strBookinfo5 = savedInstanceState.getString(MyAppDbAdapter.KEY_BOOK_INFO_5);
				mRowId = savedInstanceState.getLong(MyAppDbAdapter.KEY_ROWID);
				if (strTitle != null && !strTitle.equals("") && !strTitle.equals(" ")) {
					txtBookTitle.setText(strTitle);
					strOrigTitle = strTitle;
				}
				if (strSubtitle != null && !strSubtitle.equals("") && !strSubtitle.equals(" ")) {
					txtBookSubtitle.setText(strSubtitle);
					strOrigSubtitle = strSubtitle;
				}
				if (strIsbn != null && !strIsbn.equals("") && !strIsbn.equals(" ")) {
					txtBookIsbn.setText(strIsbn);
					strOrigIsbn = strIsbn;
				}
				if (strYear != null && !strYear.equals("") && !strYear.equals(" ")) {
					txtBookYear.setText(strYear);
					strOrigYear = strYear;
				}
				if (strOwncode != null && !strOwncode.equals("") && !strOwncode.equals(" ")) {
					txtBookOwncode.setText(strOwncode);
					strOrigOwncode = strOwncode;
				}
				if (strEdition != null && !strEdition.equals("") && !strEdition.equals(" ")) {
					txtBookEdition.setText(strEdition);
					strOrigEdition = strEdition;
				}
				if (strCoverlink != null && !strCoverlink.equals("") && !strCoverlink.equals(" ")) {
					txtBookCoverlink.setText(strCoverlink);
					strOrigCoverlink = strCoverlink;
				}
				if (strCovertype != null && !strCovertype.equals("") && !strCovertype.equals(" ")) {
					txtBookCovertype.setText(strCovertype);
					strOrigCovertype = strCovertype;
				}
				if (strPagenumber != null && !strPagenumber.equals("") && !strPagenumber.equals(" ")) {
					txtBookPagenumber.setText(strPagenumber);
					strOrigPagenumber = strPagenumber;
				}
				if (strRecommendfor != null && !strRecommendfor.equals("")
						&& !strRecommendfor.equals(" ")) {
					txtBookRecommendfor.setText(strRecommendfor);
					strOrigRecommendfor = strRecommendfor;
				}
				if (strPurchaseprice != null && !strPurchaseprice.equals("")
						&& !strPurchaseprice.equals(" ")) {
					txtBookPurchaseprice.setText(strPurchaseprice);
					strOrigPurchaseprice = strPurchaseprice;
				}
				Integer valueeditor = Integer.valueOf(intSpinnereditor);
				if (valueeditor != null && intSpinnereditor != 0) {
					OrigSpinnereditor = intSpinnereditor;
				}
				Integer valuepublisher = Integer.valueOf(intSpinnerpublisher);
				if (valuepublisher != null && intSpinnerpublisher != 0) {
					OrigSpinnerpublisher = intSpinnerpublisher;
				}
				Integer valuelanguage = Integer.valueOf(intSpinnerlanguage);
				if (valuelanguage != null && intSpinnerlanguage != 0) {
					OrigSpinnerlanguage = intSpinnerlanguage;
				}
				Integer valueshelf = Integer.valueOf(intSpinnerbookshelf);
				if (valueshelf != null && intSpinnerbookshelf != 0) {
					OrigSpinnerbookshelf = intSpinnerbookshelf;
				}
				if (strCurrentprice != null && !strCurrentprice.equals("")
						&& !strCurrentprice.equals(" ")) {
					txtBookCurrentprice.setText(strCurrentprice);
					strOrigCurrentprice = strCurrentprice;
				}
				if (strLoanto != null && !strLoanto.equals("") && !strLoanto.equals(" ")) {
					txtBookLoanto.setText(strLoanto);
					strOrigLoanto = strLoanto;
				}
				if (strLoantophone != null && !strLoantophone.equals("") && !strLoantophone.equals(" ")) {
					txtBookLoantophone.setText(strLoantophone);
					strOrigLoantophone = strLoantophone;
				}
				if (strLoandate != null && !strLoandate.equals("") && !strLoandate.equals(" ")) {
					txtBookLoandate.setText(strLoandate);
					strOrigLoandate = strLoandate;
				}
				if (strPreloan != null && !strPreloan.equals("") && !strPreloan.equals(" ")) {
					txtBookPreloan.setText(strPreloan);
					strOrigPreloan = strPreloan;
				}
				if (strDescription != null && !strDescription.equals("") && !strDescription.equals(" ")) {
					txtBookDescription.setText(strDescription);
					strOrigDescription = strDescription;
				}
				if (strBookinfo1 != null && !strBookinfo1.equals("") && !strBookinfo1.equals(" ")) {
					txtBookinfo1.setText(strBookinfo1);
					strOrigBookinfo1 = strBookinfo1;
				}
				if (strBookinfo2 != null && !strBookinfo2.equals("") && !strBookinfo2.equals(" ")) {
					txtBookinfo2.setText(strBookinfo2);
					strOrigBookinfo2 = strBookinfo2;
				}
				if (strBookinfo3 != null && !strBookinfo3.equals("") && !strBookinfo3.equals(" ")) {
					txtBookinfo3.setText(strBookinfo3);
					strOrigBookinfo3 = strBookinfo3;
				}
				if (strBookinfo4 != null && !strBookinfo4.equals("") && !strBookinfo4.equals(" ")) {
					txtBookinfo4.setText(strBookinfo4);
					strOrigBookinfo4 = strBookinfo4;
				}
				if (strBookinfo5 != null && !strBookinfo5.equals("") && !strBookinfo5.equals(" ")) {
					txtBookinfo5.setText(strBookinfo5);
					strOrigBookinfo5 = strBookinfo5;
				}
			}
		}
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(AddEditBook.this);
			errExcpError.addToLogFile(error, "AddEditBook.onRestoreInstanceState", "");
			errExcpError = null;
		}
	}

	
	@Override
	protected void onResume() {
		super.onResume();
		AddEditBook.this.blSaveNew = false;
		this.dbAppDbObj = new MyAppDbSQL(this);
		this.fillspinnereditor();
		this.fillspinnerpublisher();
		this.fillspinnerbookshelf();
		this.fillspinnerlanguage();
	}// end onResume

	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		
		try {
			APPGlobalVars.SCR_PAUSE_CTL = "SAVEINSTANCE";
			savedInstanceState.putString(MyAppDbAdapter.KEY_TITLE, txtBookTitle.getText().toString());
			savedInstanceState
					.putString(MyAppDbAdapter.KEY_SUBTITLE, txtBookSubtitle.getText().toString());
			savedInstanceState.putString(MyAppDbAdapter.KEY_ISBN, txtBookIsbn.getText().toString());
			savedInstanceState.putString(MyAppDbAdapter.KEY_YEAR, txtBookYear.getText().toString());
			savedInstanceState.putString(MyAppDbAdapter.KEY_EDITION, txtBookEdition.getText().toString());
			savedInstanceState.putString(MyAppDbAdapter.KEY_COVER_LINK, txtBookCoverlink.getText()
					.toString());
			savedInstanceState.putString(MyAppDbAdapter.KEY_COVER_TYPE, txtBookCovertype.getText()
					.toString());
			savedInstanceState.putString(MyAppDbAdapter.KEY_OWN_CODE, txtBookOwncode.getText().toString());
			savedInstanceState.putString(MyAppDbAdapter.KEY_PURCHASE_PRICE, txtBookPurchaseprice.getText()
					.toString());
			savedInstanceState.putString(MyAppDbAdapter.KEY_CURRENT_PRICE, txtBookCurrentprice.getText()
					.toString());
			savedInstanceState.putString(MyAppDbAdapter.KEY_PAGE_NUMBER, txtBookPagenumber.getText()
					.toString());
			savedInstanceState.putString(MyAppDbAdapter.KEY_RECOMMEND_FOR, txtBookRecommendfor.getText()
					.toString());
			savedInstanceState.putString(MyAppDbAdapter.KEY_LOAN_TO, txtBookLoanto.getText().toString());
			savedInstanceState.putString(MyAppDbAdapter.KEY_LOAN_DATE, txtBookLoandate.getText()
					.toString());
			savedInstanceState.putString(MyAppDbAdapter.KEY_LOAN_TO_PHONE, txtBookLoantophone.getText()
					.toString());
			savedInstanceState.putString(MyAppDbAdapter.KEY_PRELOAN, txtBookPreloan.getText().toString());
			savedInstanceState.putInt(	MyAppDbAdapter.KEY_BOOK_EDITORID,
												(int) spinnereditor.getSelectedItemId());
			savedInstanceState.putInt(	MyAppDbAdapter.KEY_BOOK_PUBLISHERID,
												(int) spinnerpublisher.getSelectedItemId());
			savedInstanceState.putInt(	MyAppDbAdapter.KEY_BOOK_LANGUAGEID,
												(int) spinnerlanguage.getSelectedItemId());
			savedInstanceState.putInt(	MyAppDbAdapter.KEY_BOOK_SHELFID,
												(int) spinnerbookshelf.getSelectedItemId());
			savedInstanceState.putString(MyAppDbAdapter.KEY_BOOK_INFO_1, txtBookinfo1.getText()
					.toString());
			savedInstanceState.putString(MyAppDbAdapter.KEY_BOOK_INFO_2, txtBookinfo2.getText()
					.toString());
			savedInstanceState.putString(MyAppDbAdapter.KEY_BOOK_INFO_3, txtBookinfo3.getText()
					.toString());
			savedInstanceState.putString(MyAppDbAdapter.KEY_BOOK_INFO_4, txtBookinfo4.getText()
					.toString());
			savedInstanceState.putString(MyAppDbAdapter.KEY_BOOK_INFO_5, txtBookinfo5.getText()
					.toString());
			savedInstanceState.putString(MyAppDbAdapter.KEY_DESCRIPTION, txtBookDescription.getText()
					.toString());
			if (mRowId != null) {
				savedInstanceState.putLong(MyAppDbAdapter.KEY_ROWID, mRowId);
			}
		}// end try
		catch (Exception error) {
			MyErrorLog<Exception> errExcpError = new MyErrorLog<Exception>(AddEditBook.this);
			errExcpError.addToLogFile(error, "AddEditBook.onSaveInstanceState", "");
			errExcpError = null;
		}// end try/catch (Exception error)
		super.onSaveInstanceState(savedInstanceState);
	}// end onSaveInstanceState

	
	@Override
	protected void onStop() {
		super.onStop();
		/* Set object variables to null */
		myAppCleanup();
	}// end onStop
}// end AddEditBook