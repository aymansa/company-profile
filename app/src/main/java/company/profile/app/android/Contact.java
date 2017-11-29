package company.profile.app.android;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.content.Intent;

public class Contact extends Activity {

	Button back, official, tel, fb, twit, you, in, loc, email;
	
	String link_official="http://codecanyon.net/user/xcl0ude/portfolio";
	String link_tel="01207396507";
	String link_email="aymansamir464@yahoo.com";
	String link_fb="http://m.facebook.com/profile.php?id=100004480951613";
	String link_twit="http://mobile.twitter.com/";
	String link_you="http://m.youtube.com/";
	String link_in="https://touch.www.linkedin.com/#login/ayman-samir-32908a12b/";
	String link_map ="https://maps.google.co.id/maps?ie=UTF8&f=d&daddr=Pasar+Rawa+Kalong,+Jalan+Setia+Mekar&geocode=CRV8vVSnh9asFV7AoP8d8VNhBiEd_B8HXX2V5Q&gl=ID&hl=en";
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact);
        
        back = (Button) findViewById(R.id.btnBack);
        official = (Button) findViewById(R.id.btnWeb);
        tel = (Button) findViewById(R.id.btnContact);
        fb = (Button) findViewById(R.id.btnFB);
        twit = (Button) findViewById(R.id.btnTwitter);
        you = (Button) findViewById(R.id.btnYoutube);
        in = (Button) findViewById(R.id.btnLinkedin);
        loc = (Button) findViewById(R.id.btnLoc);
        email = (Button) findViewById(R.id.btnEmail);
        
        
        back.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
        
        email.setOnClickListener(new View.OnClickListener() {
	
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				   Intent intent = new Intent(Intent.ACTION_SEND); 
				   intent.setType("plain/text");
				   intent.putExtra(Intent.EXTRA_EMAIL, link_email);  
				   startActivity(Intent.createChooser(intent, "Choice App to send email:"));
			}
		});
        
        official.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_VIEW);
		        i.setData(Uri.parse(link_official));
		        startActivity(i);
			}
		});
        
        fb.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_VIEW);
		        i.setData(Uri.parse(link_fb));
		        startActivity(i);
			}
		});
        
        twit.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_VIEW);
		        i.setData(Uri.parse(link_twit));
		        startActivity(i);
			}
		});
        
        you.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_VIEW);
		        i.setData(Uri.parse(link_you));
		        startActivity(i);
			}
		});
        
        in.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_VIEW);
		        i.setData(Uri.parse(link_in));
		        startActivity(i);
			}
		});
        
        tel.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_CALL);
		        intent.setData(Uri.parse("tel:"+link_tel));
		        startActivity(intent);
			}
		});
        
        loc.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_VIEW);
		        i.setData(Uri.parse(link_map));
		        startActivity(i);
			}
		});

    }
    
}
