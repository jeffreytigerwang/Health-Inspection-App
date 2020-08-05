package com.example.restaurantlist.UI;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurantlist.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.Timer;
import java.util.TimerTask;

//Loading screen into main program
public class welcomeActivity extends AppCompatActivity {
    ProgressBar progressBar;
    int count=0;

    private static final String TAG="welcomeActivity";
    private static final int ERROR_DIALOG_REQUEST=9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        //hide hideNavigationBar, let it full screen.
        hideNavigationBar();



        if(isServicesOK()){
            init();
        }


    }

    public boolean isServicesOK(){
        Log.d(TAG,"isServicesOK: checking google services version");
        int availalve = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(welcomeActivity.this);
        if(availalve== ConnectionResult.SUCCESS){
            Log.d(TAG,"isServicesOK: Google Play Services is working");
            return true;
        }
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(availalve)){
            Log.d(TAG,"isServicesOK: an error occured but we can fix it");
            Dialog dialog =GoogleApiAvailability.getInstance().getErrorDialog(welcomeActivity.this,availalve,ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else {
            Toast.makeText(this,"You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void init(){
        //set up the ProgressBar
        setupprog();

    }

    private void setupprog() {
        progressBar=findViewById(R.id.pb);

        final Timer time = new Timer();
        TimerTask timerTask= new TimerTask() {
            @Override
            public void run() {

                count++;
                progressBar.setProgress(count);

                if(count == 100)
                {

                    time.cancel();
                    Intent intent= new Intent(welcomeActivity.this, DownloadDataActivity.class);
                    startActivity(intent);
                    finish();
                }


            }

        };

        time.schedule(timerTask,0,15);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onResume() {
        super.onResume();
        hideNavigationBar();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void hideNavigationBar() {
        //Code found at [https://www.youtube.com/watch?v=cMVbpbaDwTo]
        this.getWindow().getDecorView()
                .setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                );
    }


}
