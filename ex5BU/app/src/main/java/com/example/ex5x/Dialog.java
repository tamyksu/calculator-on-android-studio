package com.example.ex5x;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public  class Dialog extends DialogFragment implements SeekBar.OnSeekBarChangeListener {

	Button ok;
	//View view;
	HandlerDialog listener;
	Button cancel;
	SeekBar seekBar;
	HandlerDialog frag_b;
	TextView example;
	DataReporter listener1;
	HandlerDialog interfaceObj;
	String title="";
	String tag=" ";
	Context context;
	static int save_prog;




	public Dialog(){

	}
	public Dialog(String title){
		Bundle args = new Bundle();
		args.putString("title",title);
		setArguments(args);

	}


	public static Dialog newInstance(int num){

		Dialog dialogFragment = new Dialog();
		Bundle bundle = new Bundle();
		bundle.putInt("num", num);
		dialogFragment.setArguments(bundle);
		save_prog = num;
		return dialogFragment;

	}
	@Override
	public void onStart() {

		seekBar.setProgress(save_prog);
		this.seekBar.setOnSeekBarChangeListener(this);
		calcResultText();
		super.onStart();
	}

	@Override
	public void onAttach(@NonNull Context context) {
		try{
			this.listener1 = (DataReporter)context;
		}catch(ClassCastException e){
			throw new ClassCastException("the class " +
					getActivity().getClass().getName() +
					" must implements the interface 'DataReporter'");
		}

		super.onAttach(context);
	}

	public interface HandlerDialog{
		public void setResultDialog(int prog);
		public int getResultDialog();

	}

	public interface DataReporter{
		public void setProg(int progress);
		public int getProg();
	}
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
		try{

			listener1.setProg(progress);
			save_prog=progress;
			calcResultText();
		}
		catch(Exception e){
			Toast.makeText(getContext(),"seekBar Catch",Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		seekBar.setProgress(save_prog);
		calcResultText();
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {

	}



	private void calcResultText()
	{

		String str = "%.0f";
		int progress = seekBar.getProgress();

		String example_str="123";
		String exam_reminder = "";
		Double  num = 123.0;

		str = str.replace("0", String.valueOf(progress));

		if(progress > 0) {
			exam_reminder = String.format(str, num);

			example.setText("Example: " + exam_reminder);
		}
		else {

			example.setText("Example: " + example_str);
		}


	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.settings, null);

		return view;
	}

	@NonNull
	public  android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
		final AlertDialog builder = new AlertDialog.Builder(getContext()).create();
		View settings_layout = getActivity().getLayoutInflater().inflate(R.layout.settings, null);
		Bundle bundle = getArguments();

		builder.setView(settings_layout);
		example =(TextView) settings_layout.findViewById(R.id.example);
		this.seekBar = (SeekBar)settings_layout.findViewById(R.id.seekBar);
		ok = (Button) settings_layout.findViewById(R.id.ok);

		ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
			//	listener.setResultDialog(seekBar.getProgress());
				getTargetFragment().onActivityResult(save_prog, Activity.RESULT_OK, getActivity().getIntent());
				dismiss();

			}
		});
		cancel = (Button)settings_layout.findViewById(R.id.cancel);
		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

			builder.dismiss();


			}
		});
		return builder;
	}



}



