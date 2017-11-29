package company.profile.app.android;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.app.Activity;
import android.content.Intent;

public class CompanyProfileApp extends Activity {
	
	Button btnAbout, btnContact, btnPortfolio;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.companyapp);
        
        btnAbout = (Button) findViewById(R.id.btnAbout);
        btnContact = (Button) findViewById(R.id.btnContact);
        btnPortfolio = (Button) findViewById(R.id.btnPort);
        
        btnAbout.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent about = new Intent(CompanyProfileApp.this, About.class);
				startActivity(about);
			}
		});
        
        btnContact.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent about = new Intent(CompanyProfileApp.this, Contact.class);
				startActivity(about);
			}
		});
        
        btnPortfolio.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent about = new Intent(CompanyProfileApp.this, Portfolio.class);
				startActivity(about);
			}
		});
                
    }
    
}
