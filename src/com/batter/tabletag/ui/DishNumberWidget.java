package com.batter.tabletag.ui;

import java.util.ArrayList;
import java.util.List;

import com.batter.tabletag.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class DishNumberWidget extends RelativeLayout {
	
	public interface onNumberChangeListener {
		public void onNumberChanged(int num);
	}
	
	private Button mNumberButton;
	private Button mDecreaseButton;
	private Button mIncreaseButton;
	private int mCount = 0;
	private List<onNumberChangeListener> mListenerList = new ArrayList<onNumberChangeListener>();

	public DishNumberWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater)context.getSystemService
			      (Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.dish_ordered_number_widget, null);
		if (view != null) {
			this.addView(view);
		}
		
	}
	
	public void addNumberChangeListener(onNumberChangeListener listener) {
		if (mListenerList != null) {
			mListenerList.add(listener);
		}
	}
	
	public int getCount() {
		return mCount;
	}
	
	public void setCount(int number) {
		mCount = number;
		expand(number);
	}
	
	public void expand(int number) {
		if (number > 0) {
			mDecreaseButton.setVisibility(View.VISIBLE);
			mNumberButton.setVisibility(View.VISIBLE);
			mNumberButton.setText(Integer.toString(mCount));
		}
		
	}
	
	public void Collapse() {
		mNumberButton.setVisibility(View.INVISIBLE);
		mDecreaseButton.setVisibility(View.INVISIBLE);
	}
	
	protected void onFinishInflate () {
		super.onFinishInflate();
		mIncreaseButton = (Button)this.findViewById(R.id.BtnIncreaseButton);
		mDecreaseButton = (Button)this.findViewById(R.id.BtnDecreaseButton);
		mNumberButton = (Button)this.findViewById(R.id.BtnNumberButton);
		
		if (mIncreaseButton != null) {
			mIncreaseButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					mCount ++;
					expand(mCount);
					
					if (mListenerList.size() != 0) {
						for (int i = 0; i < mListenerList.size(); i++) {
							mListenerList.get(i).onNumberChanged(1);
						}
					}					
				}
				
			});
		}
		
		if (mDecreaseButton != null) {
			mDecreaseButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					mCount --;
					if (mCount > 0) {						
						if (mNumberButton != null) {
							mNumberButton.setText(Integer.toString(mCount));
						}
					} else {
						Collapse();
					}
					
					if (mListenerList.size() != 0) {
						for (int i = 0; i < mListenerList.size(); i++) {
							mListenerList.get(i).onNumberChanged(-1);
						}
					}
				}
				
			});
		}
	}

}
