package com.project.styx;


import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class Tab_Book extends TabActivity {
	/** Called when the activity is first created. */
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tablist);

		
		TabHost tabHost = getTabHost();

		

		// Tab for book
		TabSpec bookpec = tabHost.newTabSpec("Book");
		bookpec.setIndicator("Book");
		Intent bookIntent = new Intent(this, Book_List.class);
		bookpec.setContent(bookIntent);

		TabSpec reviewpec = tabHost.newTabSpec("Review");
		Intent booksellerIntent = new Intent(this, Review_List.class);
		reviewpec.setIndicator("Review");
		reviewpec.setContent(booksellerIntent);
		
		TabSpec bookauthorpec = tabHost.newTabSpec("Bookauthor");
		bookauthorpec.setIndicator("Book Author");
		Intent bookauthorIntent = new Intent(this, BookAuthor_List.class);
		bookauthorpec.setContent(bookauthorIntent);

		TabSpec bookbooksellerpec = tabHost.newTabSpec("Bookbookseller");
		bookbooksellerpec.setIndicator("Book Bookseller");
		Intent bookbooksellerIntent = new Intent(this, BookBookseller_List.class);
		bookbooksellerpec.setContent(bookbooksellerIntent);
		
		TabSpec bookgenrepec = tabHost.newTabSpec("Bookgenre");
		bookgenrepec.setIndicator("Book Genre");
		Intent bookgenreIntent = new Intent(this, BookGenre_List.class);
		bookgenrepec.setContent(bookgenreIntent);
		// Adding all TabSpec to TabHost
	
		tabHost.addTab(bookpec); // Adding book tab
		tabHost.addTab(reviewpec);
		tabHost.addTab(bookauthorpec);
		tabHost.addTab(bookgenrepec);
		tabHost.addTab(bookbooksellerpec);
	}
}