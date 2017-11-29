package company.profile.app.android;

import java.io.IOException;
import java.util.ArrayList;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.database.SQLException;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

public class Product extends Activity{
	ListView listProduct;
	ProductAdapter eaListProduct;
	static String[] title, desc, image, category;
	static int [] id;
	
	Button back;
	Dialog dialog;
	
	DataBaseHelperCompany myDbHelperCompany;
	ArrayList<ArrayList<Object>> data;
	
	@Override
	protected void onResume(){
		super.onResume();
		new CheckListData().execute();
		eaListProduct.notifyDataSetChanged();
		eaListProduct.notifyDataSetInvalidated();
	    listProduct.invalidateViews();
	}
	
	 public class ProductAdapter extends BaseAdapter {
	  	  private LayoutInflater mInflater;

	        public ProductAdapter(Context c) {
	            mInflater = LayoutInflater.from(c);
	        }

	        public int getCount() {
	              return title.length;
	        }

	        public Object getItem(int position) {
	              return position;
	        }

	        public long getItemId(int position) {
	              return position;
	        }

	        public View getView(int position, View convertView, ViewGroup parent) {
	        	
	             ViewHolder holder;
	              if (convertView == null) {
	            	convertView = mInflater.inflate(R.layout.row_product, null);
	  				holder = new ViewHolder();
	  				holder.txtTitleProduct = (TextView) convertView.findViewById(R.id.txtTitleProduct);
	  				holder.txtCatProduct = (TextView) convertView.findViewById(R.id.txtCatProduct);
	  				holder.ImageProduct = (ImageView) convertView.findViewById(R.id.imgProduct);
	                convertView.setTag(holder);
	            	
	              } else{
	            	  holder = (ViewHolder) convertView.getTag();
	              }
	              holder.txtTitleProduct.setText(title[position]);
	              holder.txtCatProduct.setText(category[position]);
	  			  
	  			  String uri = image[position];

	  		      int imageResource = getResources().getIdentifier(uri, null, getPackageName());
	  		      Drawable image = getResources().getDrawable(imageResource);
	  		      holder.ImageProduct.setImageDrawable(image);
	  		      holder.ImageProduct.setScaleType(ImageView.ScaleType.FIT_XY);
				  
	  			  //holder.ImageProduct.setImageResource(R.drawable.product1);
	  			
	  			  return convertView;
	        }
	        
	        public class ViewHolder {
	        	TextView txtTitleProduct;
	  			TextView txtCatProduct;
	  			ImageView ImageProduct;
	  			
	  		}
	  }
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product);
        listProduct = (ListView) findViewById(R.id.listProduct);
        
      //Check Database
      	myDbHelperCompany= new DataBaseHelperCompany(this);
      	try {
      			myDbHelperCompany.createDataBase();
      		}catch(IOException ioe){
      			throw new Error("Unable to create database");
      		}
      	try{
      			myDbHelperCompany.openDataBase();
      		}catch(SQLException sqle){
      			throw sqle;
      		}
      	
      	eaListProduct = new ProductAdapter(this);
        
        listProduct.setFastScrollEnabled(true);  
        
        /*ColorDrawable divcolor = new ColorDrawable(Color.DKGRAY);
        listProduct.setDivider(divcolor);
        listProduct.setDividerHeight(4);
        */
        
        listProduct.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view,
    		        int position, long idn) {
        		 dialog = new Dialog(Product.this);
                 dialog.setContentView(R.layout.dialog_product);
                 dialog.setTitle("Item Descriptions");
                 dialog.setCancelable(true);
  
                 //set up text
                 TextView text_title = (TextView) dialog.findViewById(R.id.txt_title_product);
                 text_title.setText(title[position]);
                 
               //set up text
                 TextView text_cat = (TextView) dialog.findViewById(R.id.txt_cat_product);
                 text_cat.setText(category[position]);
                 
                 //set up text
                 TextView text_desc = (TextView) dialog.findViewById(R.id.txt_desc_product);
                 text_desc.setText(desc[position]);
  
                 //set up image view
                 ImageView img = (ImageView) dialog.findViewById(R.id.ImageProduct);
                 String uri = image[position];
	  		     int imageResource = getResources().getIdentifier(uri, null, getPackageName());
	  		     Drawable image = getResources().getDrawable(imageResource);
	  		     img.setImageDrawable(image);
	  		     img.setScaleType(ImageView.ScaleType.FIT_XY);
                 
                 //set up button
                 Button button = (Button) dialog.findViewById(R.id.btnClose);
                 button.setOnClickListener(new OnClickListener() {
                     public void onClick(View v) {
                         dialog.dismiss();
                     }
                 });
                 //now that the dialog is set up, it's time to show it    
                 dialog.show();
        	}
        });
        
        back = (Button) findViewById(R.id.btnBack);
        
        back.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	@Override
	public void onConfigurationChanged(final Configuration newConfig)
	{
	    // Ignore orientation change to keep activity from restarting
	    super.onConfigurationChanged(newConfig);
	}
	
	//this asyntask for display all data
	public class CheckListData extends AsyncTask<Void, Void, Void> {
		ProgressDialog dialog;
		
		@Override
		 protected void onPreExecute() {
		  // TODO Auto-generated method stub
			 dialog= ProgressDialog.show(Product.this, "", 
	                 "Read data..., please wait...", true);
		  	
		 }

		 @Override
		 protected Void doInBackground(Void... params) {
		  // TODO Auto-generated method stub
			 getAllProduct();
		  return null;
		 }

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			listProduct.setAdapter(eaListProduct);
			dialog.dismiss();			
		}
	}
	
	private void getAllProduct(){
		data = myDbHelperCompany.getAllDataProduct();
		
		id = new int[data.size()];
		title = new String[data.size()];
		desc = new String[data.size()];
		image = new String[data.size()];
		category = new String[data.size()];
		
		for(int position = 0; position < data.size(); position++){
			ArrayList<Object> row = data.get(position);
			
			id[position] = Integer.parseInt(row.get(0).toString());
			title[position] = row.get(1).toString();
			desc[position] = row.get(2).toString();
			image[position] = row.get(3).toString();
			category[position] = row.get(4).toString();
		}
	}
}
