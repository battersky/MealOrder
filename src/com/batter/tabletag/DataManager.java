package com.batter.tabletag;

import java.util.ArrayList;
import java.util.List;

import com.batter.tabletag.constant.Constant;
import com.batter.tabletag.info.DishCategory;
import com.batter.tabletag.info.DishDescription;
import com.batter.tabletag.info.DishInfo;
import com.batter.tabletag.info.DishInfo.InfoChangeListener;
import com.batter.tabletag.info.Info;

public class DataManager implements InfoChangeListener {
	
	public interface DishInfoChangeListener {
		void onDishInfoChange(float priceChange, int selectedDishCount);
	}
	
	
	public enum Mode {
		MENU_MODE,
		ORDER_MODE
	}
	
	protected List<Info> mItems = new ArrayList<Info>();
	protected List<Info> mOrderedItems = new ArrayList<Info>();
	
	protected float mTotalPrice = 0;
	
	protected int mSelectedDishCount = 0;
	
	protected DishInfoChangeListener mDishInfoChangeListener = null;
	
	public List<Info> getData(Mode mode) {
		
		if (mode == Mode.MENU_MODE) {
			if (mItems.size() == 0) {
				createFakeData();
			}
			return mItems;
		} else {
			mOrderedItems.clear();
			mOrderedItems.addAll(mItems);
			maipulation();
			return mOrderedItems;
		}		
	}
	
	public float getTotalPrice() {
		return mTotalPrice;
	}
	
	public int getSelectedDishCount() {
		return mSelectedDishCount;
	}
	
	public void setDishInfoChangeListener(DishInfoChangeListener listener) {
		mDishInfoChangeListener = listener;
	}
	
	public int getDataTypeCount() {
		return 2;
	}
	
	private void maipulation() {
		int i = 0;
		while(i < mOrderedItems.size()) {
			Info info = mOrderedItems.get(i);
			if (info.getType() == Constant.VIEW_ITEM_TYPE_DISH) {
				DishInfo dishInfo = (DishInfo)info;
				if (dishInfo.mCount == 0) {
					mOrderedItems.remove(i);
					continue;
				}
			} else {
				DishCategory dishCategory = (DishCategory)info;
				if (dishCategory.mDishCount == 0) {
					mOrderedItems.remove(i);
					continue;
				}
			}
			i++;
		}
	}
	
	private void createFakeData() {
		
		DishCategory cateInfo = new DishCategory();
		cateInfo.mCategoryName = "Starter";
		mItems.add(cateInfo);
		
		DishInfo dishInfo = new DishInfo();
		dishInfo.mPrice = 14;
		dishInfo.mName = "Jumbo Shrimp Cocktail";
		dishInfo.mResId = R.drawable.item_one;
		dishInfo.mCategory = cateInfo;
		DishDescription dishDescription = new DishDescription();
		dishDescription.mCharactor = "Homemade";
		dishDescription.mTaste = "It's fresh taste and made by chicken";
		dishDescription.mOrigin = "from China";
		dishDescription.mRecomendation = "good to have together with red wine";
		dishInfo.mDescription = dishDescription;
		dishInfo.setInfoChangeListener(this);
		mItems.add(dishInfo);
		
		dishInfo = new DishInfo();
		dishInfo.mPrice = 13;
		dishInfo.mName = "Coconut Shrimp ";
		dishInfo.mResId = R.drawable.item_two;
		dishInfo.mCategory = cateInfo;
		dishDescription = new DishDescription();
		dishDescription.mCharactor = "Homemade";
		dishDescription.mTaste = "It's fresh taste and made by chicken";
		dishDescription.mOrigin = "from China";
		dishDescription.mRecomendation = "good to have together with red wine";
		dishInfo.mDescription = dishDescription;
		dishInfo.setInfoChangeListener(this);
		mItems.add(dishInfo);
		
		dishInfo = new DishInfo();
		dishInfo.mPrice = 12;
		dishInfo.mName = "Chef’s Daily Fresh Oysters";
		dishInfo.mResId = R.drawable.item_three;
		dishInfo.mCategory = cateInfo;
		dishDescription = new DishDescription();
		dishDescription.mCharactor = "Homemade";
		dishDescription.mTaste = "It's fresh taste and made by chicken";
		dishDescription.mOrigin = "from China";
		dishDescription.mRecomendation = "good to have together with red wine";
		dishInfo.mDescription = dishDescription;
		dishInfo.setInfoChangeListener(this);
		mItems.add(dishInfo);
		
		cateInfo = new DishCategory();
		cateInfo.mCategoryName = "Soups & Salads";
		mItems.add(cateInfo);
		
		dishInfo = new DishInfo();
		dishInfo.mPrice = 3;
		dishInfo.mName = "New England Clam Chowder";
		dishInfo.mResId = R.drawable.item_five;
		dishInfo.mCategory = cateInfo;
		dishDescription = new DishDescription();
		dishDescription.mCharactor = "Homemade";
		dishDescription.mTaste = "It's fresh taste and made by chicken";
		dishDescription.mOrigin = "from China";
		dishDescription.mRecomendation = "good to have together with red wine";
		dishInfo.mDescription = dishDescription;
		dishInfo.setInfoChangeListener(this);
		mItems.add(dishInfo);
		
		dishInfo = new DishInfo();
		dishInfo.mPrice = 3;
		dishInfo.mName = "Lobster Bisque";
		dishInfo.mResId = R.drawable.item_four;
		dishInfo.mCategory = cateInfo;
		dishDescription = new DishDescription();
		dishDescription.mCharactor = "Homemade";
		dishDescription.mTaste = "It's fresh taste and made by chicken";
		dishDescription.mOrigin = "from China";
		dishDescription.mRecomendation = "good to have together with red wine";
		dishInfo.mDescription = dishDescription;
		dishInfo.setInfoChangeListener(this);
		mItems.add(dishInfo);
		
		cateInfo = new DishCategory();
		cateInfo.mCategoryName = "Sandwiches";
		mItems.add(cateInfo);
		
		dishInfo = new DishInfo();
		dishInfo.mPrice = 10;
		dishInfo.mCategory = cateInfo;
		dishInfo.mName = "Chicken Sandwich";
		dishInfo.mResId = R.drawable.item_six;
		dishDescription = new DishDescription();
		dishDescription.mCharactor = "Homemade";
		dishDescription.mTaste = "It's fresh taste and made by chicken";
		dishDescription.mOrigin = "from China";
		dishDescription.mRecomendation = "good to have together with red wine";
		dishInfo.mDescription = dishDescription;
		dishInfo.setInfoChangeListener(this);
		mItems.add(dishInfo);
		
	}

	@Override
	public void onInfoChange(float priceChange, int selectedDishCount) {
		mTotalPrice += priceChange;
		this.mSelectedDishCount += selectedDishCount;
		if (mDishInfoChangeListener != null) {
			mDishInfoChangeListener.onDishInfoChange(priceChange, selectedDishCount);
		}
		
	}
}
