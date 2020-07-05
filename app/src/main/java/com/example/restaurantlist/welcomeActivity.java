package com.example.restaurantlist;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class welcomeActivity extends AppCompatActivity {
    ProgressBar progressBar;
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hide hideNavigationBar, let it full screen.
        hideNavigationBar();

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
                    Intent intent= new Intent(welcomeActivity.this,MenuActivity.class);
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
