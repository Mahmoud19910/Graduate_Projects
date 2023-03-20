package dev.mah.nassa.gradu_ptojects.Activityes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import dev.mah.nassa.gradu_ptojects.FireBase_Authentication.Gmai_Auth;
import dev.mah.nassa.gradu_ptojects.R;

public class Home_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button signOut=findViewById(R.id.signOut);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gmai_Auth.onSignOut(Home_Activity.this);
            }
        });
    }
}