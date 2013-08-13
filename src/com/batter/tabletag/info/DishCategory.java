package com.batter.tabletag.info;

import com.batter.tabletag.constant.Constant;

public class DishCategory implements Info {
	public String mCategoryName;
	public int mDishCount = 0;

	@Override
	public int getType() {
		return Constant.VIEW_ITEM_TYPE_CATEGORY;
	}
}
