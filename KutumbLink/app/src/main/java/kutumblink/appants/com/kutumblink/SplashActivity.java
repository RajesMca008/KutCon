package kutumblink.appants.com.kutumblink;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends Activity {

    private static int SPLASH_TIME_OUT = 1500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        startMainActivity();
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
