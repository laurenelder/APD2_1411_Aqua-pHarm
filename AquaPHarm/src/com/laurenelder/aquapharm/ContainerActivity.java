package com.laurenelder.aquapharm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ContainerActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_container);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
        getMenuInflater().inflate(R.menu.main, menu);
        
 //       menu.findItem(R.id.action_about).s
        menu.findItem(R.id.action_item_one).setEnabled(true).setVisible(true);
        
        menu.findItem(R.id.action_item_one).setIcon(R.drawable.ic_action_edit);
        menu.findItem(R.id.action_item_two).setIcon(R.drawable.ic_action_discard);
        
        return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
        int id = item.getItemId();
        if (id == R.id.action_item_one) {
        	return true;
        }
        if (id == R.id.action_item_two) {
        	return true;
        }
		
		return super.onOptionsItemSelected(item);
	}

}
