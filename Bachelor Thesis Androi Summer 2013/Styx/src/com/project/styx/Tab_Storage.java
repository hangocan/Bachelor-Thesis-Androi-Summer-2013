
package com.project.styx;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
public class Tab_Storage extends TabActivity {
	
	/** Called when the activity is first created. */
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tablist);
		TabHost tabHost = getTabHost();
		TabSpec shelfpec = tabHost.newTabSpec("Shelf");
		Intent shelfIntent = new Intent(this, Shelf_List.class);
		shelfpec.setIndicator("Shelf");
		shelfpec.setContent(shelfIntent);
		TabSpec rackpec = tabHost.newTabSpec("Rack");
		Intent rackIntent = new Intent(this, Rack_List.class);
		rackpec.setIndicator("Rack");
		rackpec.setContent(rackIntent);
		TabSpec roompec = tabHost.newTabSpec("Room");
		Intent roomIntent = new Intent(this, Room_List.class);
		roompec.setIndicator("Room");
		roompec.setContent(roomIntent);
		TabSpec storagepec = tabHost.newTabSpec("Storage");
		Intent storageIntent = new Intent(this, Storage_List.class);
		storagepec.setIndicator("Storage");
		storagepec.setContent(storageIntent);
		// Adding all TabSpec to TabHost
		tabHost.addTab(storagepec); // Adding Editor tab
		tabHost.addTab(roompec); // Adding authors tab
		tabHost.addTab(rackpec); // Adding book tab
		tabHost.addTab(shelfpec); // Adding Editor tab
	}
}