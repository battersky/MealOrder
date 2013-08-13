package com.batter.tabletag.info;

import com.batter.tabletag.constant.Constant;
import com.batter.tabletag.ui.DishNumberWidget;

public class DishInfo implements Info, DishNumberWidget.onNumberChangeListener {
	
	public interface InfoChangeListener {
		void onInfoChange(float priceChange, int selectedDishCount);
	}
	
	public int mResId = 0;
	public float mPrice = 0;
	public String mName;
	public int mCount = 0;
	public DishCategory mCategory;
	public DishDescription mDescription;
	private InfoChangeListener mInfoChangeListener = null;

	@Override
	public int getType() {
		return Constant.VIEW_ITEM_TYPE_DISH;
	}
	@Override
	public void onNumberChanged(int num) {
		if (mCount == 0 && num > 0) {
			mCategory.mDishCount ++;
		} else if (mCount + num == 0) {
			mCategory.mDishCount --;
		}
		mCount += num;
		
		if (mInfoChangeListener != null) {
			mInfoChangeListener.onInfoChange(num * mPrice, num);
		}
	}
	
	public void setInfoChangeListener(InfoChangeListener listener) {
		mInfoChangeListener = listener;
	}
}
