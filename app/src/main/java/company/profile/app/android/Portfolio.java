package company.profile.app.android;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

public class Portfolio extends TabActivity implements OnTabChangeListener{

	TabHost tabHost;
	Resources res;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.portfolio);
		
		res = getResources();
		tabHost = getTabHost();
		TabHost.TabSpec specProduct;
		TabHost.TabSpec specServices;
		
		tabHost.setOnTabChangedListener(this);		
		specProduct = tabHost.newTabSpec("tab1").
					setIndicator("",getResources().
					getDrawable(R.drawable.product)).setContent(new Intent().setClass(this, Product.class));
		tabHost.addTab(specProduct);

		specServices = tabHost.newTabSpec("tab2").
				setIndicator("",getResources().
				getDrawable(R.drawable.service)).setContent(new Intent().setClass(this, Service.class));
		tabHost.addTab(specServices);

		for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
        {
			tabHost.getTabWidget().getChildAt(i).setBackgroundDrawable(this.getResources().getDrawable(R.drawable.tab_default));

        }
		tabHost.setCurrentTab(0);
		tabHost.getTabWidget().getChildAt(0).setBackgroundDrawable(this.getResources().getDrawable(R.drawable.tab_active));

		
	}
	
		public void onTabChanged(String tabId) {
			
			for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
	        {
	        	tabHost.getTabWidget().getChildAt(i).setBackgroundDrawable(this.getResources().getDrawable(R.drawable.tab_default));
	        } 
					
			tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundDrawable(this.getResources().getDrawable(R.drawable.tab_active));
		}
}