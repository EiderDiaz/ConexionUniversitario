package com.example.eider.navigation_drawer.Activity;

import android.content.Context;
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
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;

public class SplashActivity extends AppCompatActivity {
    private TextView tv;
    private ImageView iv;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (AccessToken.getCurrentAccessToken() == null){
            goLoginScreen();
        }
        
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

    private void goLoginScreen() {

        Intent intent = new Intent(this,LoginActivity.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP | intent.FLAG_ACTIVITY_CLEAR_TASK |intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

}
