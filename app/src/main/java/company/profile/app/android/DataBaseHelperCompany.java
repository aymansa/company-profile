package company.profile.app.android;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelperCompany extends SQLiteOpenHelper{
	 
    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/company.profile.app.android/databases/";
    
 
    private final static String DB_NAME = "db_company";
	public final int DB_VERSION = 1;
    private SQLiteDatabase myDataBase; 
  
    private final Context myContext;
    
    private final String TABLE_NAME_PRODUCT = "tbl_product";
	private final String TABLE_ROW_ID_PRODUCT = "id";
	private final String TITLE_PRODUCT = "title_product";
	private final String CATEGORY_PRODUCT = "category_product";
	private final String DESC_PRODUCT = "desc_product";
	private final String IMAGE_PRODUCT = "image_product";
	
	private final String TABLE_NAME_SERVICE = "tbl_service";
	private final String TABLE_ROW_ID_SERVICE = "id";
	private final String TITLE_SERVICE = "title_service";
	private final String CATEGORY_SERVICE = "category_service";
	private final String DESC_SERVICE = "desc_service";
	private final String IMAGE_SERVICE = "image_service";
	
	boolean ada = false;
    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DataBaseHelperCompany(Context context) {
 
    	super(context, DB_NAME, null, 1);
        this.myContext = context;
    }	
 
  /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException{
 
    	boolean dbExist = checkDataBase();
    	SQLiteDatabase db_Read = null;
 
    	if(dbExist){
    		//Toast.makeText(myContext, "Create Database \n " +
    		//		"Database", Toast.LENGTH_SHORT).show();
    		//do nothing - database already exist
    	}else{
 
    		//By calling this method and empty database will be created into the default system path
               //of your application so we are gonna be able to overwrite that database with our database.
    		db_Read = this.getReadableDatabase();
    		db_Read.close();
 
        	try {
        		//Toast.makeText(myContext, "No Database \n" +
        		//		"Call method Copy Database", Toast.LENGTH_SHORT).show();
    			copyDataBase();
    		} catch (IOException e) {
        		throw new Error("Error copying database");
        	}
    	}
 
    }
 
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
 
    	SQLiteDatabase checkDB = null;
 
    	try{
    		String myPath = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    		//Toast.makeText(myContext, "Database", Toast.LENGTH_SHORT).show();
    	}catch(SQLiteException e){
    		//Toast.makeText(myContext, "No Database", Toast.LENGTH_SHORT).show();
    		//database does't exist yet.
    	}
    	if(checkDB != null){
    		checkDB.close();
    	}
 
    	return checkDB != null ? true : false;
    }
 
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{
    	//Toast.makeText(myContext, "Copy database", Toast.LENGTH_SHORT);
    	
    	//Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(DB_NAME);
 
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DB_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
    	
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
 
    }
 
    public void openDataBase() throws SQLException{
    	//Toast.makeText(myContext, "Database Open", Toast.LENGTH_SHORT);

    	//Open the database
        String myPath = DB_PATH + DB_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }
 
    @Override
	public synchronized void close() {
 
    	    if(myDataBase != null)
    		    myDataBase.close();
 
    	    super.close();
 
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
 
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
	}
        // Add your public helper methods to access and get content from the database.
       // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
       // to you to create adapters for your views.
	
	
	public ArrayList<ArrayList<Object>> getAllDataProduct()
	{
		// create an ArrayList that will hold all of the data collected from
		// the database.
		ArrayList<ArrayList<Object>> dataArrays = new ArrayList<ArrayList<Object>>();
 
		// this is a database call that creates a "cursor" object.
		// the cursor object store the information collected from the
		// database and is used to iterate through the data.
		Cursor cursor = null;
		try
		{
			// ask the database object to create the cursor.
			cursor = myDataBase.query(
					TABLE_NAME_PRODUCT,
					new String[]{TABLE_ROW_ID_PRODUCT, TITLE_PRODUCT, DESC_PRODUCT, IMAGE_PRODUCT, CATEGORY_PRODUCT},
					null,null, null, null, null
			);
			
			// move the cursor's pointer to position zero.
			cursor.moveToLast();
			// if there is data after the current cursor position, add it
			// to the ArrayList.
			if (!cursor.isBeforeFirst())
			{
				do
				{
					ArrayList<Object> dataList = new ArrayList<Object>();
 
					dataList.add(cursor.getLong(0));
					dataList.add(cursor.getString(1));
					dataList.add(cursor.getString(2));
					dataList.add(cursor.getString(3));
					dataList.add(cursor.getString(4));
					dataArrays.add(dataList);
				}
				// move the cursor's pointer up one position.
				while (cursor.moveToPrevious());
			}
			cursor.close();
		}
		catch (SQLException e)
		{
			Log.e("DB Error", e.toString());
			e.printStackTrace();
		}
 
		// return the ArrayList that holds the data collected from
		// the database.
		return dataArrays;
	}
	
	public ArrayList<ArrayList<Object>> getAllDataService()
	{
		// create an ArrayList that will hold all of the data collected from
		// the database.
		ArrayList<ArrayList<Object>> dataArrays = new ArrayList<ArrayList<Object>>();
 
		// this is a database call that creates a "cursor" object.
		// the cursor object store the information collected from the
		// database and is used to iterate through the data.
		Cursor cursor = null;
		try
		{
			// ask the database object to create the cursor.
			cursor = myDataBase.query(
					TABLE_NAME_SERVICE,
					new String[]{TABLE_ROW_ID_SERVICE, TITLE_SERVICE, DESC_SERVICE, IMAGE_SERVICE, CATEGORY_SERVICE},
					null,null, null, null, null
			);
			
			// move the cursor's pointer to position zero.
			cursor.moveToLast();
			// if there is data after the current cursor position, add it
			// to the ArrayList.
			if (!cursor.isBeforeFirst())
			{
				do
				{
					ArrayList<Object> dataList = new ArrayList<Object>();
 
					dataList.add(cursor.getLong(0));
					dataList.add(cursor.getString(1));
					dataList.add(cursor.getString(2));
					dataList.add(cursor.getString(3));
					dataList.add(cursor.getString(4));
					dataArrays.add(dataList);
				}
				// move the cursor's pointer up one position.
				while (cursor.moveToPrevious());
			}
			cursor.close();
		}
		catch (SQLException e)
		{
			Log.e("DB Error", e.toString());
			e.printStackTrace();
		}
 
		// return the ArrayList that holds the data collected from
		// the database.
		return dataArrays;
	}
	
}