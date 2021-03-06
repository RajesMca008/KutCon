package kutumblink.appants.com.kutumblink;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;

import java.util.ArrayList;

import kutumblink.appants.com.kutumblink.model.ContactsDo;
import kutumblink.appants.com.kutumblink.utils.DatabaseHandler;

public class SplashActivity extends Activity {

    private static int SPLASH_TIME_OUT = 1500;
    public static ArrayList<ContactsDo> arr_contacts = new ArrayList<ContactsDo>();
    DatabaseHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

      dbHandler = new DatabaseHandler(getApplicationContext());

        startMainActivity();
    }




    private String retrieveContactNumbers(long contactId) {
       String data="";
        Cursor cursor = getApplicationContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[] { contactId + "" }, null);
        if (cursor.getCount() >= 1) {
            while (cursor.moveToNext()) {
                // store the numbers in an array
                String str = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                if (str != null && str.trim().length() > 0) {
                  //  phoneNum.add(str);
                    data+=str;
                }
            }
        }
        cursor.close();

        return data;
    }

    private void startMainActivity(){
        /****** Create Thread that will sleep for 3 seconds *************/
        Thread background = new Thread() {
            public void run() {
                try {
                    // Thread will sleep for 3 seconds
                    sleep(1*1000);
                    // After 3 seconds redirect to another intent
                    Intent i=new Intent(SplashActivity.this,kutumblink.appants.com.kutumblink.HomeActivity.class);
                    startActivity(i);
                    //Remove activity
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        // start thread
        background.start();
    }
}
