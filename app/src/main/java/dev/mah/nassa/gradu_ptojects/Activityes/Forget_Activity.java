package dev.mah.nassa.gradu_ptojects.Activityes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

import dev.mah.nassa.gradu_ptojects.Constants.SharedFunctions;
import dev.mah.nassa.gradu_ptojects.R;
import dev.mah.nassa.gradu_ptojects.databinding.ActivityForgetBinding;

public class Forget_Activity extends AppCompatActivity  {

    private boolean showPass = false;
    private String verifuId;
    ActivityForgetBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityForgetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.parentLayoutForget.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        binding.parentLayoutForget.setTextDirection(View.TEXT_DIRECTION_ANY_RTL);


        binding.verifyButForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = SharedFunctions.checkEnterdNewPass(binding.editPass , binding.editNumber , Forget_Activity.this);
                if(isChecked){
                    SharedFunctions.showProgressBar(Forget_Activity.this);
                    String phone = binding.editNumber.getText().toString();
                    Intent intent =new Intent(Forget_Activity.this , VerifyForgetPass_Activity.class);
                    intent.putExtra("newPass",binding.editPass.getText().toString());
                    intent.putExtra("phone",binding.editNumber.getText().toString());
                    startActivity(intent);
                    finish();


                }


            }
        });

        binding.backSignInBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext() , SignUp_Activity.class));
                finish();
            }
        });

        binding.eyePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(showPass==false){
                    showPass=true;
                    binding.editPass.setTransformationMethod(null);
                    binding.eyePass.setImageResource(R.drawable.lock_open_24);
                }else {
                    showPass=false;
                    binding.editPass.setTransformationMethod(new PasswordTransformationMethod());
                    binding.eyePass.setImageResource(R.drawable.lock_24);
                }



            }
        });


    }

}
