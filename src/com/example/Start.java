package com.example;

import android.app.Activity;
import android.os.Bundle;

public class Start extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		MyView myView = new MyView(this);
		setContentView(myView);
	}
}