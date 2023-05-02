package dev.mah.nassa.gradu_ptojects.Activityes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.PhoneAuthCredential;

import dev.mah.nassa.gradu_ptojects.Constants.SharedFunctions;
import dev.mah.nassa.gradu_ptojects.DataBase.FireStore_DataBase;
import dev.mah.nassa.gradu_ptojects.FireBase_Authentication.PhoneAuth;
import dev.mah.nassa.gradu_ptojects.Interfaces.VerificationIdListener;
import dev.mah.nassa.gradu_ptojects.MVVM.UsersViewModel;
import dev.mah.nassa.gradu_ptojects.databinding.ActivityVerifyForgetPassBinding;

public class VerifyForgetPass_Activity extends AppCompatActivity implements VerificationIdListener {

    private ActivityVerifyForgetPassBinding binding;
    private String newPass;
    private String verifyId, phone;
    private UsersViewModel usersViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyForgetPassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        newPass = intent.getStringExtra("newPass").toString();
        phone = intent.getStringExtra("phone").toString();

        // ارسال الرمز الى الرقم
        PhoneAuth.sendSmsCode(VerifyForgetPass_Activity.this, phone);

        usersViewModel= ViewModelProviders.of(VerifyForgetPass_Activity.this).get(UsersViewModel.class);


        // Timer
        SharedFunctions.countDownTimerOnCreate(binding.reSendButForget, binding.progressVerifyAccount, binding.downTimerVerifyAccount);

        binding.parentLayoutVerifyForgetPass.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        binding.parentLayoutVerifyForgetPass.setTextDirection(View.TEXT_DIRECTION_ANY_RTL);

        //Listeners
        // Verify Button
        binding.verifyButForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.firstPinView.getText().toString().isEmpty()) {
                    SharedFunctions.showProgressBar(VerifyForgetPass_Activity.this);
                    Toast.makeText(VerifyForgetPass_Activity.this, verifyId + "", Toast.LENGTH_SHORT).show();
                    PhoneAuthCredential credential = PhoneAuth.veryfiedAccount(verifyId, binding.firstPinView.getText().toString());
                    PhoneAuth.verifyNewPassWithPhoneAuthCredential(credential, VerifyForgetPass_Activity.this, new OnSuccessListener<Boolean>() {
                        @Override
                        public void onSuccess(Boolean aBoolean) {
                            if (aBoolean.booleanValue()) {
                                FireStore_DataBase.updatePass(newPass, phone, VerifyForgetPass_Activity.this);
                                usersViewModel.updatePass(newPass, phone);
                            }
                        }
                    });

                } else {
                    SharedFunctions.dismissDialog();
                    Toast.makeText(VerifyForgetPass_Activity.this, "يرجى ادخال رمز التأكيد", Toast.LENGTH_SHORT).show();
                }


            }
        });

        // Resend Buttton
        binding.reSendButForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedFunctions.startTimerAndVisibleUi(binding.progressVerifyAccount, binding.downTimerVerifyAccount);
            }
        });

    }


    @Override
    public void getverificationId(String verificationId) {
        verifyId = verificationId;
        Toast.makeText(this, "veri" + verificationId, Toast.LENGTH_SHORT).show();
    }

}