package dev.mah.nassa.gradu_ptojects;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
                if(checkInputs==true){
                    Intent goToVerifyAccount=new Intent(this , VerifyAccount_Activity.class);
                    goToVerifyAccount.putExtra("name" , binding.editName.getText().toString());
                    goToVerifyAccount.putExtra("phone" , binding.editPhone.getText().toString());
                    goToVerifyAccount.putExtra("pass" , binding.editPass.getText().toString());
                    startActivity(goToVerifyAccount);
                    finish();
                }
                break;

            case R.id.backSignUp_Bt:

                break;

            case R.id.gmail_But:


                break;
        }

    }




}