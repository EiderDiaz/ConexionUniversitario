package com.example.eider.navigation_drawer.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.eider.navigation_drawer.R;

public class SplashActivity extends AppCompatActivity {
    private TextView tv;
    private ImageView iv;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tv = (TextView)findViewById(R.id.splash_text1);
        iv= (ImageView)findViewById(R.id.splashlogolux);
        pb= (ProgressBar)findViewById(R.id.spalshProgressbar);

        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.mytransition);
        tv.startAnimation(myanim);
        iv.startAnimation(myanim);
        pb.startAnimation(myanim);

        final Intent intent=  new Intent(this,MainActivity.class);
        Thread thread = new Thread(){
            public  void  run(){
                try {
                    sleep(5000);
                }catch (InterruptedException e){
                    Toast.makeText(SplashActivity.this, "error :C"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                finally {

                    startActivity(intent);
                    finish();
                } // final finally

            } // final void run

        }; //final thread
        thread.start();
    }
}
