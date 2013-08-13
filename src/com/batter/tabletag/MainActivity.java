
package com.batter.tabletag;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;
//import android.view.Menu;
import android.content.Intent;

public class MainActivity extends Activity {
	
	private Button mOkButton;
	private Button mCancelButton;
	private EditText mInputView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        
        mOkButton = (Button)this.findViewById(R.id.button_ok);
        mCancelButton = (Button)this.findViewById(R.id.button_cancel);
        mInputView = (EditText)this.findViewById(R.id.editText);
        
        mOkButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent intent = new Intent("android.intent.action.VIEW");
				intent.setClass(MainActivity.this, RestaurantCoverActivity.class);
				MainActivity.this.startActivity(intent);
				MainActivity.this.finish();
			}
        	
        });
        
        mCancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				mInputView.setText("");
			}
        	
        });
        
        
        mInputView.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString().length() == 0) {
					mOkButton.setEnabled(false);
					mCancelButton.setEnabled(false);
				} else {
					mOkButton.setEnabled(true);
					mCancelButton.setEnabled(true);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start,
					int count, int before) {
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {			
			}
        	
        });
    }
}
