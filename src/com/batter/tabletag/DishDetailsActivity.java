package com.batter.tabletag;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DishDetailsActivity extends Activity {

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();
        overridePendingTransition(R.anim.activity_detail_in, R.anim.activity_detail_hold);
        
        this.setContentView(R.layout.activity_dish_detail);
        TextView categoryView = (TextView)this.findViewById(R.id.CategoryView);
        TextView dishNameView = (TextView)this.findViewById(R.id.DishNameView);
        TextView dishCharactorView = (TextView)this.findViewById(R.id.DishCharactorView);
        TextView dishTastView = (TextView)this.findViewById(R.id.DishTastView);
        TextView dishOriginView = (TextView)this.findViewById(R.id.DishOriginView);
        TextView dishRecomendationView = (TextView)this.findViewById(R.id.DishRecomendationView);
        
        String categoryName = intent.getStringExtra("Category");
        if (categoryName != null) {
        	categoryView.setText(categoryName);
        	categoryView.setEnabled(true);
        }
        
        String dishName = intent.getStringExtra("DishName");
        if (dishName != null) {
        	dishNameView.setText(dishName);
        	dishNameView.setEnabled(true);
        }
        
        String dishCharactor = intent.getStringExtra("Charactor");
        if (dishCharactor != null) {
        	dishCharactorView.setText(
        			getResources().getString(R.string.prefix_dish_detail_charactor) + dishCharactor);
        	dishCharactorView.setEnabled(true);
        }
        
        String dishTast = intent.getStringExtra("Tast");
        if (dishTast != null) {
        	dishTastView.setText(
        			getResources().getString(R.string.prefix_dish_detail_tast) + dishTast);
        	dishTastView.setEnabled(true);
        }

        String dishOrigin = intent.getStringExtra("Origin");
        if (dishOrigin != null) {
        	dishOriginView.setText(
        			getResources().getString(R.string.prefix_dish_detail_origin) + dishOrigin);
        	dishOriginView.setEnabled(true);
        }
        
        String dishRecomendation = intent.getStringExtra("Recomendation");
        if (dishRecomendation != null) {
        	dishRecomendationView.setText(
        			getResources().getString(R.string.prefix_dish_detail_recomendation) + dishRecomendation);
        	dishRecomendationView.setEnabled(true);
        }
	}
	
	@Override
    protected void onPause() {
		// Whenever this activity is paused (i.e. looses focus because another activity is started etc)
		// Override how this activity is animated out of view
		// The new activity is kept still and this activity is pushed out to the left
        overridePendingTransition(R.anim.activity_detail_hold, R.anim.activity_detail_out);
        super.onPause();
    }
}
