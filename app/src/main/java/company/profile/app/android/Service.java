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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

public class Service extends Activity{
	ListView listService;
	ServiceAdapter eaListService;
	static String[] title, desc, image, category;
	static int [] id;
	Dialog dialog;
	
	Button back;
	
	DataBaseHelperCompany myDbHelperCompany;
	ArrayList<ArrayList<Object>> data;
	
	@Override
	protected void onResume(){
		super.onResume();
		new CheckListData().execute();
		eaListService.notifyDataSetChanged();
		eaListService.notifyDataSetInvalidated();
	    listService.invalidateViews();
	}
	
	 public class ServiceAdapter extends BaseAdapter {
	  	  private LayoutInflater mInflater;

	        public ServiceAdapter(Context c) {
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
	            	convertView = mInflater.inflate(R.layout.row_service, null);
	  				holder = new ViewHolder();
	  				holder.txtTitleService = (TextView) convertView.findViewById(R.id.txtTitleService);
	  				holder.txtCatService = (TextView) convertView.findViewById(R.id.txtCatService);
	  				holder.ImageService = (ImageView) convertView.findViewById(R.id.imgService);
	                convertView.setTag(holder);
	            	
	              } else{
	            	  holder = (ViewHolder) convertView.getTag();
	              }
	              holder.txtTitleService.setText(title[position]);
	              holder.txtCatService.setText(category[position]);
	  			  
	  			  String uri = image[position];

	  		      int imageResource = getResources().getIdentifier(uri, null, getPackageName());
	  		      Drawable image = getResources().getDrawable(imageResource);
	  		      holder.ImageService.setImageDrawable(image);
	  		      holder.ImageService.setScaleType(ImageView.ScaleType.FIT_XY);
				  
	  			  //holder.ImageService.setImageResource(R.drawable.product1);
	  			
	  			  return convertView;
	        }
	        
	        public class ViewHolder {
	        	TextView txtTitleService;
	  			TextView txtCatService;
	  			ImageView ImageService;
	  			
	  		}
	  }
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service);
        listService = (ListView) findViewById(R.id.listService);
        
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
      	
      	eaListService = new ServiceAdapter(this);
        
        listService.setFastScrollEnabled(true);  
        
        /*ColorDrawable divcolor = new ColorDrawable(Color.DKGRAY);
        listService.setDivider(divcolor);
        listService.setDividerHeight(4);
        */
        
        listService.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view,
    		        int position, long idn) {
        		 dialog = new Dialog(Service.this);
                 dialog.setContentView(R.layout.dialog_service);
                 dialog.setTitle("Item Descriptions");
                 dialog.setCancelable(true);
  
                 //set up text
                 TextView text_title = (TextView) dialog.findViewById(R.id.txt_title_service);
                 text_title.setText(title[position]);
                 
               //set up text
                 TextView text_cat = (TextView) dialog.findViewById(R.id.txt_cat_service);
                 text_cat.setText(category[position]);
                 
                 //set up text
                 TextView text_desc = (TextView) dialog.findViewById(R.id.txt_desc_service);
                 text_desc.setText(desc[position]);
  
                 //set up image view
                 ImageView img = (ImageView) dialog.findViewById(R.id.ImageService);
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
			 dialog= ProgressDialog.show(Service.this, "", 
	                 "Read data..., please wait...", true);
		  	
		 }

		 @Override
		 protected Void doInBackground(Void... params) {
		  // TODO Auto-generated method stub
			 getAllService();
		  return null;
		 }

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			listService.setAdapter(eaListService);
			dialog.dismiss();			
		}
	}
	
	private void getAllService(){
		data = myDbHelperCompany.getAllDataService();
		
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
