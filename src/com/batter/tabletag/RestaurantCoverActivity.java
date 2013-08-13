package com.batter.tabletag;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class RestaurantCoverActivity extends Activity {
	
	private Handler mHander = new Handler() {
		
		public void handleMessage (Message msg) {
			Intent localIntent = new Intent();
			localIntent.setClass(RestaurantCoverActivity.this, DishMenuActivity.class);
		    startActivity(localIntent);
		    RestaurantCoverActivity.this.finish();
		}
	};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.restaurant_cover);
        TextView txt = (TextView) findViewById(R.id.welcome_tips);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/DroidSansMono.ttf");
        txt.setTypeface(font);        
    }

    public void onResume()
    {
      super.onResume();
      mHander.sendMessageDelayed(mHander.obtainMessage(), 3000L);
    }

}
