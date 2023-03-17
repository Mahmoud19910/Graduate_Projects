package dev.mah.nassa.gradu_ptojects;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import dev.mah.nassa.gradu_ptojects.databinding.ActivitySignInBinding;

public class SignIn_Activity extends AppCompatActivity {

    ActivitySignInBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.parentLayoutSignIn.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        binding.parentLayoutSignIn.setTextDirection(View.TEXT_DIRECTION_ANY_RTL);

        binding.forgetPassButInSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext() , Forget_Activity.class));
            }
        });

        binding.signInBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext() , UsersInformation_Activity.class));
            }
        });
    }

}