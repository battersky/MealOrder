package com.batter.tabletag;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DishMenuActivity extends SherlockActivity implements DishAdapter.DataChangeListener {
	
	
	private class DataLoadTask extends AsyncTask<DishAdapter.Mode, Void, Void> {
		
		private DishAdapter mDishAdapter;
		private Context mContext;
		private ProgressDialog mDialog;
		
		public DataLoadTask(DishAdapter adapter, Context context) {
			mDishAdapter = adapter;
			mContext = context;
		}
		
		protected void onPreExecute () {
			mDialog = ProgressDialog.show(mContext, "", "loading menu");
		}

		protected Void doInBackground(
				com.batter.tabletag.DishAdapter.Mode... modes) {
			mDishAdapter.setMode(modes[0]);
			return null;
		}

		protected void onPostExecute(Void result) {
			mDishAdapter.notifyDataSetChanged();
			mDialog.dismiss();			
		}
		
	}
	
	private enum Mode {
		MEMU_MODE,
		ORDER_MODE
	}
	
	private ListView mListView;	
	private DishAdapter mAdapter;
	
	private TextView mOrderView;
	private TextView mSelectedDishCountView;
	private TextView mPriceView;
	
	private Mode mMode = Mode.MEMU_MODE;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mListView = (ListView)this.findViewById(R.id.MenuList);
        mAdapter = new DishAdapter(this);
        
        mListView.setAdapter(mAdapter);
        mAdapter.setDataChangeListener(this);
        
        ActionBar actionBar = getSupportActionBar();
        
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setCustomView(R.layout.activity_dish_header);
        
        
        RelativeLayout headerView = (RelativeLayout)actionBar.getCustomView();
        if (headerView != null) {
        	mOrderView = (TextView)headerView.findViewById(R.id.OrderView);
        	if (mOrderView != null) {
        		mOrderView.setOnClickListener(new OnClickListener() {

    				@Override
    				public void onClick(View view) {
    					if (mMode == Mode.ORDER_MODE) {
    						DishMenuActivity.this.finish();
    					} else {
    						setMode(Mode.ORDER_MODE);
    					}
    					
    				}            		
            	});
        	}
        	mSelectedDishCountView = (TextView)headerView.findViewById(R.id.DishCount);
        	mPriceView = (TextView)headerView.findViewById(R.id.PriceView);
        }
        
        setMode(Mode.MEMU_MODE);
    }
	
	@Override
	public void onBackPressed () {
		if (mMode == Mode.ORDER_MODE) {
			setMode(Mode.MEMU_MODE);
		} else {
			super.onBackPressed();
		}
	}
	
	public void setMode(Mode mode) {
		mMode = mode;
    	
    	if (mode == Mode.MEMU_MODE) {    		
    		mOrderView.setText(R.string.key_order_note);
    		int count = mAdapter.getSelectedDishCount();
    		if (count > 0) {
    			mSelectedDishCountView.setVisibility(View.VISIBLE);
    			mSelectedDishCountView.setText(Integer.toString(count));
    			mOrderView.setEnabled(true);
    		} else {
    			mSelectedDishCountView.setVisibility(View.INVISIBLE);
    			mOrderView.setEnabled(false);
    		}
    		
    	} else {
    		mOrderView.setText(R.string.key_close_note);
    		mSelectedDishCountView.setVisibility(View.INVISIBLE);
    	}

		new DataLoadTask(this.mAdapter, this).execute(
				mode == Mode.MEMU_MODE ? DishAdapter.Mode.MENU_MODE : DishAdapter.Mode.ORDER_MODE);
	}

	@Override
	public void onDataChange() {
		mPriceView.setText(String.format(getString(R.string.total_price), Float.toString(mAdapter.getTotalPrice())));
		int count = mAdapter.getSelectedDishCount();
		if (count > 0) {
			mSelectedDishCountView.setVisibility(View.VISIBLE);
			mSelectedDishCountView.setText(Integer.toString(count));
			mOrderView.setEnabled(true);
		} else {
			mSelectedDishCountView.setVisibility(View.INVISIBLE);
			mOrderView.setEnabled(false);
		}
	}
}
