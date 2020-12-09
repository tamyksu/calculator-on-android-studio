package com.example.ex5x;

//import android.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class FragB extends DialogFragment implements  Dialog.HandlerDialog {
	TextView result;
	public static final int DIALOG_FRAGMENT = 1;
	static String result_string="";
	private static String num1, num2, sign;
	static int progress_dialog=0;


	@Override
	public void onActivityResult(int prog, int resultCode, @Nullable Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			progress_dialog=prog;
			calcResultText();

		}
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		switch (item.getItemId())
		{
			case R.id.exit:
				showAlertDialog();
				return true;
			case R.id.settings:
				DialogFragment dialogFrag = Dialog.newInstance(progress_dialog);

				dialogFrag.setTargetFragment(this, DIALOG_FRAGMENT);

				dialogFrag.show(getFragmentManager().beginTransaction(), "dialog");

				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	@Override
	public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
		menu.clear();
		inflater.inflate(R.menu.menu_frag_b, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setHasOptionsMenu(true);

		return inflater.inflate(R.layout.frag_b, container,false);
	}

	@Override
	public void onStart() {
		calcResultText();
		super.onStart();
	}

	@Override
	public void setInitialSavedState(@Nullable SavedState state) {

		super.setInitialSavedState(state);

	}

	@Override
	public void onResume() {
		calcResultText();
		super.onResume();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		this.result= (TextView) view.findViewById(R.id.textView1);
		calcResultText();
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		calcResultText();
		if (savedInstanceState != null) {
		}
	}

	public void showAlertDialog(){

		AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
		alert.setTitle("Closing the application");
		alert.setMessage("Are you sure? ");
		alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				System.exit(0);

			}
		});
		alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {

			}
		});
		alert.create().show();
	}








	@Override
	public void onSaveInstanceState(@NonNull Bundle outState) {
		calcResultText();
		super.onSaveInstanceState(outState);
	}


	//the activity informs fragB about new click in fragA
	public void onNewClick(String num1, String num2, String sign){

		this.num1 = num1;
		this.num2 = num2;
		this.sign = sign;
		switch (sign)
		{
			case "+": result_string = String.valueOf(Integer.parseInt(num1) + Integer.parseInt(num2));
				break;
			case "-": result_string = String.valueOf(Integer.parseInt(num1) - Integer.parseInt(num2));
				break;
			case "x": result_string = String.valueOf(Integer.parseInt(num1) * Integer.parseInt(num2));
				break;
			case "/": result_string = String.valueOf(Double.parseDouble(num1) / Double.parseDouble(num2));
				break;
			default:
				this.result.setText("");
				return;
		}

		calcResultText();
	}

	private void calcResultText()
	{
		if(result_string == ""||sign =="") {
			return;
		}
		Double doubleResult = Double.parseDouble(result_string);
		String str = "%.0f";
		str = str.replace("0", String.valueOf(progress_dialog));

		if(progress_dialog > 0) {

			str = String.format(str, doubleResult);
		}
		else {
			str = String.format("%d", doubleResult.intValue());
		}
		result.setText(num1 + " " + sign + " " + num2 + " = " + str);

	}


	@Override
	public void setResultDialog(int prog) {
		this.progress_dialog=prog;
		Toast.makeText(getContext(),"setResultDialog ",Toast.LENGTH_SHORT).show();
	}

	@Override
	public int getResultDialog() {
		Toast.makeText(getContext(),"getResultDialog ",Toast.LENGTH_SHORT).show();
		return this.progress_dialog;
	}


}
