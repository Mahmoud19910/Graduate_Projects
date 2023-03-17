package dev.mah.nassa.gradu_ptojects;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import dev.mah.nassa.gradu_ptojects.FireBase_Authentication.Gmai_Auth;
import dev.mah.nassa.gradu_ptojects.Modles.SharedFunctions;
import dev.mah.nassa.gradu_ptojects.databinding.ActivitySignUpBinding;

public class SignUp_Activity extends AppCompatActivity implements View.OnClickListener {

    private ActivitySignUpBinding binding;
    boolean checkInputs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // انشاء مصادقة جوجل
        Gmai_Auth.createGoogleAuth(this);


        // وضع اتجاه النص و الواجهات من الاليمين الى اليسار لغة عربية
        binding.parentLayoutSignUp.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        binding.parentLayoutSignUp.setTextDirection(View.TEXT_DIRECTION_ANY_RTL);


        //Listeners
        binding.signUpBut.setOnClickListener(this);
        binding.backSignUpBt.setOnClickListener(this);
        binding.gmailBut.setOnClickListener(this);


// تشفير كلمة المرور
//        binding.eyePass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                binding.editPass.setTransformationMethod(new PasswordTransformationMethod());
//
//            }
//        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.signUp_But:

                checkInputs = SharedFunctions.
                        checkEnterdDataInSignUp(binding.editName, binding.editPhone, binding.editPass, binding.checkbox, this);
                if (checkInputs == true) {
                    Intent goToVerifyAccount = new Intent(this, VerifyAccount_Activity.class);
                    goToVerifyAccount.putExtra("name", binding.editName.getText().toString());
                    goToVerifyAccount.putExtra("phone", binding.editPhone.getText().toString());
                    goToVerifyAccount.putExtra("pass", binding.editPass.getText().toString());
                    startActivity(goToVerifyAccount);
                    finish();
                }
                break;

            case R.id.backSignUp_Bt:
                // تسجيل الخروج
                Gmai_Auth.onSignOut(this);
                break;

            case R.id.gmail_But:
                // تسجيل الدخول
                Gmai_Auth.onClickGoogleBut(this);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Gmai_Auth.onResultGoolgleAuth(this, requestCode, data);

    }
}