package com.project.styx;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ManageData extends Activity {

	Button btmanagebook; 
	Button btmanageother ;
	Button btmanagestorage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manage_data);
		this.btmanagebook = (Button) findViewById(R.id.btmanagebook);
		this.btmanageother = (Button) findViewById(R.id.btmanageother);
		this.btmanagestorage = (Button) findViewById(R.id.btmanagestorage);
		
		btmanagebook.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ManageData.this, Tab_Book.class);
				startActivity(intent);
			}
		});
		btmanageother.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ManageData.this, Tab_Other.class);
				startActivity(intent);
			}
		});
		btmanagestorage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ManageData.this, Tab_Storage.class);
				startActivity(intent);
			}
		});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.manage_data, menu);
		return true;
	}

}
