package com.example.ex5x;

//import android.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class FragA extends DialogFragment implements OnClickListener{
	ClickHandler listener;
	EditText num1, num2;
	Button plus, minus, mul, divide, clear;
	WatchText wt = new WatchText();
	MenuItem settings;


	@Override
	public void onAttach(@NonNull Context context) {
		try{
			this.listener = (ClickHandler)context;
		}catch(ClassCastException e){
			throw new ClassCastException("the class " +
					getActivity().getClass().getName() +
					" must implements the interface 'ClickHandler'");
		}
		super.onAttach(context);
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			setHasOptionsMenu(true);
		return inflater.inflate(R.layout.frag_a, container,false);
	}

	@Override
	public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
		menu.clear();
		inflater.inflate(R.menu.main, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}





	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		switch (item.getItemId())
		{
			case R.id.exit:
				showAlertDialog();
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}




	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		clear = (Button)view.findViewById(R.id.clr);
		clear.setOnClickListener(this);
		plus = (Button)view.findViewById(R.id.plus);
		plus.setOnClickListener(this);
		minus = (Button)view.findViewById(R.id.minus);
		minus.setOnClickListener(this);
		mul = (Button)view.findViewById(R.id.mul);
		mul.setOnClickListener(this);
		divide = (Button)view.findViewById(R.id.divide);
		divide.setOnClickListener(this);
		num1=(EditText)view.findViewById(R.id.num1);
		num2=(EditText)view.findViewById(R.id.num2);
		num1.addTextChangedListener(wt);
		num2.addTextChangedListener(wt);


		super.onViewCreated(view, savedInstanceState);
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
			num2.setText("");
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		//num1.removeTextChangedListener(wt);
		//num2.removeTextChangedListener(wt);
	}

	@Override
	public void onClick(View v) {
		String number1=num1.getText().toString(), number2=num2.getText().toString(), sign="";

		switch (v.getId())
		{
			case R.id.plus:
				sign="+";
				break;
			case R.id.minus:
				sign="-";
				break;
			case R.id.mul:
				sign="x";
				break;
			case R.id.divide:
				sign="/";
				break;
			default:
				num1.setText("");
				num2.setText("");

				plus.setEnabled(false);
				minus.setEnabled(false);
				mul.setEnabled(false);
				divide.setEnabled(false);
				break;
		}
//		Toast.makeText(getActivity(), "hello!!!", Toast.LENGTH_LONG).show();
		listener.OnClickEvent(number1, number2, sign);
	}

	public interface ClickHandler{
		public void OnClickEvent(String number1, String number2, String sign);
	}


	private class WatchText implements TextWatcher {

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {

		}

		@Override
		public void afterTextChanged(Editable s) {
			try {
				int operand1, operand2;

				operand1 = Integer.parseInt(num1.getText().toString());
				operand2 = Integer.parseInt(num2.getText().toString());

				plus.setEnabled(true);
				minus.setEnabled(true);
				mul.setEnabled(true);

				if(operand2 != 0)
					divide.setEnabled(true);
				else
					divide.setEnabled(false);
			}
			catch (NumberFormatException e){
				//Toast.makeText(getContext(),"Operand can not be empty!",Toast.LENGTH_SHORT).show();
				plus.setEnabled(false);
				minus.setEnabled(false);
				mul.setEnabled(false);
				divide.setEnabled(false);
			}
		}
	}
}

