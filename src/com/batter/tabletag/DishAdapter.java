package com.batter.tabletag;

import java.util.ArrayList;
import java.util.List;

import com.batter.tabletag.DataManager.DishInfoChangeListener;
import com.batter.tabletag.constant.Constant;
import com.batter.tabletag.info.DishCategory;
import com.batter.tabletag.info.DishInfo;
import com.batter.tabletag.info.Info;
import com.batter.tabletag.ui.DishNumberWidget;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DishAdapter extends BaseAdapter implements DishInfoChangeListener {
	
	public interface DataChangeListener {
		public void onDataChange();
	}
	
	public enum Mode {
		MENU_MODE,
		ORDER_MODE
	}	
	
	DataManager mDataManager;	
	
	protected List<Info> mItems = new ArrayList<Info>();
	
	private Mode mMode = Mode.MENU_MODE;
	
	protected Context mContext;
	
	private DataChangeListener mDataChangeListener = null;
	
	public DishAdapter(Context context) {
		super();
        mContext = context;
        mDataManager = new DataManager();
        mDataManager.setDishInfoChangeListener(this);
    }

	@Override
	public int getCount() {
		return mItems.size();
	}
	
	public int getSelectedDishCount() {
		return mDataManager.getSelectedDishCount();
	}
	
	public float getTotalPrice() {
		return mDataManager.getTotalPrice();
	}

	@Override
	public Object getItem(int position) {		
		return mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
	
	public void setMode(Mode mode) {
		mMode = mode;
	    mItems = mDataManager.getData(mode == Mode.MENU_MODE ? 
				DataManager.Mode.MENU_MODE : DataManager.Mode.ORDER_MODE);
	}
	
	@Override
	public int getViewTypeCount () {
		return mDataManager.getDataTypeCount();
	}

	@Override	
	public int getItemViewType (int position) {
		Info item = mItems.get(position);
		return item.getType();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Info item = (Info)getItem(position);
		
		LayoutInflater inflater = LayoutInflater.from(mContext);
		if (item.getType() == Constant.VIEW_ITEM_TYPE_CATEGORY) {
			return createCategoryView(inflater, item);
		} else {
			return createDishView(inflater, item);
		}
	}
	
	private View createCategoryView(LayoutInflater inflater, Info info) {
		View view = inflater.inflate(R.layout.dish_category, null);
		DishCategory categoryInfo = (DishCategory)info;
		if (view != null && categoryInfo != null) {
			TextView categoryText = (TextView)view.findViewById(R.id.category_name);
			categoryText.setText(categoryInfo.mCategoryName);
		}
		
		return view;
	}
	
	private View createDishView(LayoutInflater inflater, Info info) {
		View view = inflater.inflate(R.layout.choose_menu_item, null);
		DishInfo dishInfo = (DishInfo)info;
		if (view != null && dishInfo != null) {
			ImageView disImage = (ImageView)view.findViewById(R.id.ImageCai);
			TextView dishNameView = (TextView)view.findViewById(R.id.TextCaiName);
			TextView dishPriceView = (TextView)view.findViewById(R.id.DishPrice);
			DishNumberWidget numberWidget = (DishNumberWidget)view.findViewById(R.id.NumberWidget);
			
			disImage.setImageResource(dishInfo.mResId);
			disImage.setTag(dishInfo);
			disImage.setOnClickListener(new OnClickListener() {
				

				@Override
				public void onClick(View view) {
					
					DishInfo info = (DishInfo)view.getTag();
					
					Intent intent = new Intent("");
					intent.setClass(mContext, DishDetailsActivity.class);
					intent.putExtra("Category", info.mCategory.mCategoryName);
					intent.putExtra("DishName", info.mName);
					intent.putExtra("Tast", info.mDescription.mTaste);
					intent.putExtra("Origin", info.mDescription.mOrigin);
					intent.putExtra("Recomendation", info.mDescription.mRecomendation);
					intent.putExtra("Charactor", info.mDescription.mCharactor);
					mContext.startActivity(intent);
				}
				
			});
			
			
			dishNameView.setText(dishInfo.mName);
			dishPriceView.setText(Float.toString(dishInfo.mPrice));
			numberWidget.setCount(dishInfo.mCount);
			numberWidget.addNumberChangeListener(dishInfo);	
		}
		
		return view;
	}
	
	public void setDataChangeListener(DataChangeListener listener) {
		mDataChangeListener = listener;
	}

	@Override
	public void onDishInfoChange(float priceChange, int selectedDishCount) {
		if (mDataChangeListener != null) {
			mDataChangeListener.onDataChange();
		}
		
	}
}
