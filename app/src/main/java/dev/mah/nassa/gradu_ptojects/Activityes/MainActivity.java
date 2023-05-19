package dev.mah.nassa.gradu_ptojects.Activityes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import dev.mah.nassa.gradu_ptojects.FireBase_Authentication.Gmai_Auth;
import dev.mah.nassa.gradu_ptojects.R;

public class MainActivity extends AppCompatActivity {
    public  FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences=getSharedPreferences("isLoged" , Context.MODE_PRIVATE);
       boolean isSignIn = sharedPreferences.getBoolean("isSignIn",false);
        Toast.makeText(this, isSignIn+"", Toast.LENGTH_SHORT).show();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
               if(firebaseAuth.getCurrentUser()!=null || isSignIn == true){
                   startActivity(new Intent(getApplicationContext() , Home_Activity.class));
                   finish();
               }else{
                   startActivity(new Intent(getApplicationContext() , UnBoarding_Activity.class));
                   finish();
               }

            }
        }, 2000);
    }
}