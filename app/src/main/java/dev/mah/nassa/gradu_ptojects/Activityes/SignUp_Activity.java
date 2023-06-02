package dev.mah.nassa.gradu_ptojects.Activityes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

import dev.mah.nassa.gradu_ptojects.DataBase.FireStore_DataBase;
import dev.mah.nassa.gradu_ptojects.DataBase.RealTime_DataBase;
import dev.mah.nassa.gradu_ptojects.FireBase_Authentication.Gmai_Auth;
import dev.mah.nassa.gradu_ptojects.Interfaces.Gmail_Acc_Info_Listener;
import dev.mah.nassa.gradu_ptojects.Constants.SharedFunctions;
import dev.mah.nassa.gradu_ptojects.Modles.UsersInfo;
import dev.mah.nassa.gradu_ptojects.Modles.Users_Chat;
import dev.mah.nassa.gradu_ptojects.R;
import dev.mah.nassa.gradu_ptojects.Services_Firebase.CloudMessaging;
import dev.mah.nassa.gradu_ptojects.databinding.ActivitySignUpBinding;

public class SignUp_Activity extends AppCompatActivity implements View.OnClickListener, Gmail_Acc_Info_Listener {

    private ActivitySignUpBinding binding;
    private boolean checkInputs;
    private boolean showPass = false;
    private boolean isFindEmail=false; // لمعرفة هل تم التسجيل من قبل لتنفيذ عملية الدخول مباشرة


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
        binding.signInButInSignUp.setOnClickListener(this);
        binding.eyePass.setOnClickListener(this);
        binding.editPass.setTransformationMethod(new PasswordTransformationMethod());


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.signUp_But:

                checkInputs = SharedFunctions.checkEnterdDataInSignUp(binding.editName, binding.editPhone, binding.editPass, binding.checkbox, this);
                boolean internetCheck = SharedFunctions.checkInternetConnection(SignUp_Activity.this);
               String phoneCheck =  binding.editPhone.getText().toString();
                if (checkInputs == true && internetCheck == true && phoneCheck.contains("+972") || phoneCheck.contains("+970") && phoneCheck.length() == 13) {
                    SharedFunctions.showProgressBar(SignUp_Activity.this);
                    Intent goToVerifyAccount = new Intent(this, VerifyAccount_Activity.class);
                    goToVerifyAccount.putExtra("name", binding.editName.getText().toString());
                    goToVerifyAccount.putExtra("phone", binding.editPhone.getText().toString());
                    goToVerifyAccount.putExtra("pass", binding.editPass.getText().toString());
                    startActivity(goToVerifyAccount);
                    finish();
                }
                else
                if(!internetCheck){
                    Toast.makeText(this, "يرجى التأكد من اتصالك بالشبكة !!", Toast.LENGTH_SHORT).show();
                    SharedFunctions.dismissDialog();
                }
                else
                    if(!checkInputs){
                        Toast.makeText(this, "يرجى التأكد من ادخال كافة الحقول  !!", Toast.LENGTH_SHORT).show();
                    }
                    else
                        if(!phoneCheck.contains("+972") || !phoneCheck.contains("+970") || phoneCheck.length() > 13){
                            Toast.makeText(this, "الرقم المدخل خاطئ  !!", Toast.LENGTH_SHORT).show();

                        }

                break;

            case R.id.backSignUp_Bt:
                startActivity(new Intent(getApplicationContext(), SignIn_Activity.class));
                finish();
                break;

            case R.id.gmail_But:
                SharedFunctions.showProgressBar(SignUp_Activity.this);
                // تسجيل الدخول
                Gmai_Auth.onClickGoogleBut(this);
                break;

            case R.id.signInBut_InSignUp:
                startActivity(new Intent(getApplicationContext(), SignIn_Activity.class));
                finish();
                break;
            case R.id.eyePass:
                if (showPass == false) {
                    showPass = true;
                    binding.editPass.setTransformationMethod(null);
                    binding.eyePass.setImageResource(R.drawable.lock_open_24);

                } else {
                    showPass = false;
                    binding.editPass.setTransformationMethod(new PasswordTransformationMethod());
                    binding.eyePass.setImageResource(R.drawable.lock_24);

                }
                break;
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), SignIn_Activity.class));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Gmai_Auth.onResultGoolgleAuth(this, requestCode, data);

    }

    @Override
    public void getGmailInfoListener(String name, Uri photoUri, String email, String id) {

        saveUid(id);
        String photo;

        if (photoUri != null) {
            photo = photoUri.toString();

        } else {
            photo = "";
        }


        //لارسال الاشعارات و تخزينها في قاعدة البيانات Token جلب
        CloudMessaging.getToken(SignUp_Activity.this, new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
               if(photo!=null){
                   // حفظ في المستخدم ريال تايم
                   Users_Chat usersChat = new Users_Chat(id , name , photo , "" , true , o.toString());
                   RealTime_DataBase.addUsersToRealTime(SignUp_Activity.this , id , usersChat );
               } else {
                   // حفظ في المستخدم ريال تايم
                   Users_Chat usersChat = new Users_Chat(id , name , photo , "" , true , o.toString());
                   RealTime_DataBase.addUsersToRealTime(SignUp_Activity.this , id , usersChat );
               }
            }
        });



        // جلب البيانات لمعرفة أن المستخدم مسجل من قبل لتسجيل الدخول مباشرة
        FireStore_DataBase.getAllUsersInfo(new OnSuccessListener<ArrayList<UsersInfo>>() {
            @Override
            public void onSuccess(ArrayList<UsersInfo> usersInfos) {
                for(UsersInfo data : usersInfos){
                    if(data.getEmail().equalsIgnoreCase(email)){

                        startActivity(new Intent(SignUp_Activity.this , Home_Activity.class));
                        finish();
                        SharedFunctions.isSignIn(true , SignUp_Activity.this);
                        isFindEmail=true;
                        break;
                    }
                }

                if(!isFindEmail){
                    Intent intent = new Intent(SignUp_Activity.this, UsersInformation_Activity.class);
                    intent.putExtra("name", name);
                    intent.putExtra("photo", photo);
                    intent.putExtra("email", email);
                    intent.putExtra("uid", id);

                    startActivity(intent);
                    SharedFunctions.isSignIn(true , SignUp_Activity.this);
                }

            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUp_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    // حفظ رقم المعرف للمستخد
    private void saveUid(String uid) {
        if (uid != null) {
            SharedPreferences sharedPreferences = getSharedPreferences("saveUid", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("uid", uid);
            Toast.makeText(this, "save", Toast.LENGTH_SHORT).show();
            editor.apply();
        }

    }

}