package dev.mah.nassa.gradu_ptojects;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import dev.mah.nassa.gradu_ptojects.FireBase_Authentication.Gmai_Auth;
import dev.mah.nassa.gradu_ptojects.databinding.ActivitySignInBinding;

public class SignIn_Activity extends AppCompatActivity implements View.OnClickListener {

    ActivitySignInBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // انشاء مصادقة جوجل
        Gmai_Auth.createGoogleAuth(this);

        // وضع اتجاه النص و الواجهات من الاليمين الى اليسار لغة عربية
        binding.parentLayoutSignIn.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        binding.parentLayoutSignIn.setTextDirection(View.TEXT_DIRECTION_ANY_RTL);

        binding.forgetPassButInSignIn.setOnClickListener(this);
        binding.signInBut.setOnClickListener(this);
        binding.gmailButSignIn.setOnClickListener(this);
        binding.backSignInBt.setOnClickListener(this);
        binding.signUpButInSignIn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.forgetPassBut_InSignIn:

                startActivity(new Intent(getApplicationContext() , Forget_Activity.class));
                finish();
                break;

            case R.id.signIn_But:
                startActivity(new Intent(getApplicationContext() , UsersInformation_Activity.class));
                finish();
                break;

            case R.id.gmail_ButSignIn:
                Gmai_Auth.onClickGoogleBut(this);
                break;
            case R.id.backSignIn_Bt:
                Gmai_Auth.onSignOut(this);
                startActivity(new Intent(getApplicationContext() , SignUp_Activity.class));
                finish();
                break;

            case R.id.signUpBut_InSignIn:
                startActivity(new Intent(getApplicationContext() , SignUp_Activity.class));
                finish();
                break;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Gmai_Auth.onResultGoolgleAuth(this, requestCode, data);

    }
}