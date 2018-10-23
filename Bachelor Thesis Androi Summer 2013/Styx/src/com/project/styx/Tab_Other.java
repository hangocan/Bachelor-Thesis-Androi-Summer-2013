package com.project.styx;


import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class Tab_Other extends TabActivity {
	/** Called when the activity is first created. */
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tablist);

		
		TabHost tabHost = getTabHost();

		// Tab for authors
		TabSpec authorpec = tabHost.newTabSpec("Author");
		Intent authorsIntent = new Intent(this, Author_List.class);
		authorpec.setIndicator("Author");
		authorpec.setContent(authorsIntent);

	

		// Tab for Editor
		TabSpec editorpec = tabHost.newTabSpec("Editor");
		Intent editorIntent = new Intent(this, Editor_List.class);
		editorpec.setIndicator("Editor");
		editorpec.setContent(editorIntent);

		TabSpec genrepec = tabHost.newTabSpec("Genre");
		Intent genreIntent = new Intent(this, Genre_List.class);
		genrepec.setIndicator("Genre");
		genrepec.setContent(genreIntent);

		TabSpec languagepec = tabHost.newTabSpec("Language");
		Intent languageIntent = new Intent(this, Language_List.class);
		languagepec.setIndicator("Language");
		languagepec.setContent(languageIntent);

		TabSpec publisherpec = tabHost.newTabSpec("Publisher");
		Intent publisherIntent = new Intent(this, Publisher_List.class);
		publisherpec.setIndicator("Publisher");
		publisherpec.setContent(publisherIntent);

		TabSpec booksellerpec = tabHost.newTabSpec("Bookseller");
		Intent booksellerIntent = new Intent(this, Bookseller_List.class);
		booksellerpec.setIndicator("Bookseller");
		booksellerpec.setContent(booksellerIntent);

	

		// Adding all TabSpec to TabHost
		tabHost.addTab(authorpec); // Adding authors tab
		tabHost.addTab(editorpec); // Adding Editor tab
		tabHost.addTab(booksellerpec); // Adding authors tab
		tabHost.addTab(publisherpec); // Adding authors tab
		tabHost.addTab(genrepec); // Adding book tab
		tabHost.addTab(languagepec); // Adding Editor tab
	
	}
}