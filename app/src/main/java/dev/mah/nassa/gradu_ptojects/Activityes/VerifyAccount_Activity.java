package dev.mah.nassa.gradu_ptojects.Activityes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.PhoneAuthCredential;

import dev.mah.nassa.gradu_ptojects.FireBase_Authentication.PhoneAuth;
import dev.mah.nassa.gradu_ptojects.Interfaces.VerificationIdListener;
import dev.mah.nassa.gradu_ptojects.Constants.SharedFunctions;
import dev.mah.nassa.gradu_ptojects.R;
import dev.mah.nassa.gradu_ptojects.databinding.ActivityVerifyAccountBinding;

public class VerifyAccount_Activity extends AppCompatActivity implements View.OnClickListener , VerificationIdListener {

    private ActivityVerifyAccountBinding binding;
    private String phone , pass , name;
    private String storedVerificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityVerifyAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent i=getIntent();
        name=i.getStringExtra("name");
        pass=i.getStringExtra("pass");
        phone=i.getStringExtra("phone");
        saveData();



        Toast.makeText(this, phone+pass, Toast.LENGTH_SHORT).show();

        // وضع اتجاه النص و الواجهات من الاليمين الى اليسار لغة عربية
        binding.parentLayoutVerifyAccount.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        binding.parentLayoutVerifyAccount.setTextDirection(View.TEXT_DIRECTION_ANY_RTL);

        //Listeners
        binding.verifyButAccount.setOnClickListener(this);
        binding.resendButAccount.setOnClickListener(this);


        // ميثود ارسال رمز التحقق الى رقم الهاتف
        PhoneAuth.sendSmsCode(VerifyAccount_Activity.this,phone.toString());



        // استدعاء ميثود المؤقت التنازلي
        SharedFunctions.countDownTimerOnCreate(binding.resendButAccount , binding.progressVerifyAccount,binding.downTimerVerifyAccount);



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(VerifyAccount_Activity.this , SignUp_Activity.class));
        finish();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.verify_ButAccount:
                try {
                    PhoneAuthCredential credential= PhoneAuth.veryfiedAccount(storedVerificationId,binding.firstPinViewAccount.getText().toString());
                    PhoneAuth.signInWithPhoneAuthCredential(credential , VerifyAccount_Activity.this,name,phone,pass);
                }
                catch (Exception e){
                    Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.resend_ButAccount:
                // ميثود ارسال رمز التحقق الى رقم الهاتف مرة أخرى
                PhoneAuth.sendSmsCode(VerifyAccount_Activity.this,phone.toString());
                SharedFunctions.startTimerAndVisibleUi(binding.progressVerifyAccount,binding.downTimerVerifyAccount);
                break;

            case R.id.back_VerifyAccount:
                startActivity(new Intent(VerifyAccount_Activity.this , SignUp_Activity.class));
                finish();
                break;

        }
    }



    @Override
    public void getverificationId(String verificationId) {
        storedVerificationId=verificationId;
        Toast.makeText(this, verificationId+"ID", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
        Toast.makeText(this, phone+pass, Toast.LENGTH_SHORT).show();

    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name" , name);
        editor.putString("pass" , pass);
        editor.putString("phone" , phone);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        name= sharedPreferences.getString("name" , "");
        pass= sharedPreferences.getString("pass" , "");
        phone= sharedPreferences.getString("phone" , "");
    }



}