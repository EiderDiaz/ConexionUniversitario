package com.example.eider.navigation_drawer.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.eider.navigation_drawer.Modelos.Usuario;
import com.example.eider.navigation_drawer.Other.AdminSQLiteOpenHelper;
import com.example.eider.navigation_drawer.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    LoginButton loginButton;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (AccessToken.getCurrentAccessToken() != null){
            goMainScreen();
        }
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email","public_profile");
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String userid = loginResult.getAccessToken().getUserId();
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        displayUserInfo(object);
                    }
                });
                Bundle bundle = new Bundle();
                bundle.putString("fields","first_name,last_name,email,id");
                graphRequest.setParameters(bundle);
                graphRequest.executeAsync();
                goMainScreen();
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "sesion cancelada", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, "error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void goMainScreen() {
            Intent intent = new Intent(this,SplashActivity.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP | intent.FLAG_ACTIVITY_CLEAR_TASK |intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void displayUserInfo(JSONObject object) {
        String firstname="",lastname="",email="",id="";
        try {
            firstname= object.getString("first_name");
            lastname=object.getString("last_name");
            email=object.getString("email");
            id=object.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getApplicationContext());
        admin.resetDataBase(getApplicationContext());
        admin.GuardarDatosSession(new Usuario(firstname,lastname,email,id),getApplicationContext());
        Toast.makeText(this, "nombre:"+firstname+"\napellido:"+lastname+"\nemail:"+email+"\nid:"+id, Toast.LENGTH_SHORT).show();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
