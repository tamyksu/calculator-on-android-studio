package com.example.ex5x;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements FragA.ClickHandler, Dialog.DataReporter{

	static int progress=0;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState != null ) {
		}

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override//function of frag A
	public void OnClickEvent(String number1, String number2, String sign) {
		FragB fragB;
	    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			fragB = (FragB) getSupportFragmentManager().findFragmentById(R.id.fragB);
		}
		else //I am in portrait
		{
			fragB = new FragB();
			getSupportFragmentManager().beginTransaction().
					add(R.id.fragContainer, fragB).//add on top of the static fragment
					addToBackStack("BBB").//cause the back button scrolling through the loaded fragments
					commit();
			getSupportFragmentManager().executePendingTransactions();
		 }
		fragB.onNewClick(number1, number2, sign);
	}


	@Override//function of frag B
	public void setProg(int progress)
	{
		this.progress=progress;
	}
	@Override//function of frag B
	public int getProg()
	{
		return this.progress;
	}

}
