package dev.mah.nassa.gradu_ptojects.Activityes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

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
import java.util.List;

import dev.mah.nassa.gradu_ptojects.Constants.SharedFunctions;
import dev.mah.nassa.gradu_ptojects.DataBase.FireStore_DataBase;
import dev.mah.nassa.gradu_ptojects.DataBase.RealTime_DataBase;
import dev.mah.nassa.gradu_ptojects.FireBase_Authentication.Gmai_Auth;
import dev.mah.nassa.gradu_ptojects.Interfaces.Gmail_Acc_Info_Listener;
import dev.mah.nassa.gradu_ptojects.Modles.UsersInfo;
import dev.mah.nassa.gradu_ptojects.MVVM.UsersViewModel;
import dev.mah.nassa.gradu_ptojects.Modles.Users_Chat;
import dev.mah.nassa.gradu_ptojects.R;
import dev.mah.nassa.gradu_ptojects.Services_Firebase.CloudMessaging;
import dev.mah.nassa.gradu_ptojects.databinding.ActivitySignInBinding;

public class SignIn_Activity extends AppCompatActivity implements View.OnClickListener , Gmail_Acc_Info_Listener {

    private ActivitySignInBinding binding;
    private SharedPreferences preferences;
    private boolean isFindEmail=false; // لمعرفة هل تم التسجيل من قبل لتنفيذ عملية الدخول مباشرة
    private UsersViewModel usersViewModel;
    private boolean showPass = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // انشاء مصادقة جوجل
        Gmai_Auth.createGoogleAuth(this);

        usersViewModel= ViewModelProviders.of(SignIn_Activity.this).get(UsersViewModel.class);

        // وضع اتجاه النص و الواجهات من الاليمين الى اليسار لغة عربية
        binding.parentLayoutSignIn.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        binding.parentLayoutSignIn.setTextDirection(View.TEXT_DIRECTION_ANY_RTL);

        binding.forgetPassButInSignIn.setOnClickListener(this);
        binding.signInBut.setOnClickListener(this);
        binding.gmailButSignIn.setOnClickListener(this);
        binding.backSignInBt.setOnClickListener(this);
        binding.signUpButInSignIn.setOnClickListener(this);
        binding.eyePass.setOnClickListener(this);
        binding.editPassSignIn.setTransformationMethod(new PasswordTransformationMethod());



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.forgetPassBut_InSignIn:

                startActivity(new Intent(getApplicationContext() , Forget_Activity.class));
                finish();
                break;

            case R.id.signIn_But:
               boolean isChecked =  SharedFunctions.checkEnterdDataInSignIn(binding.editPassSignIn , binding.editPhoneSignIn , SignIn_Activity.this);
              boolean internetCheck =  SharedFunctions.checkInternetConnection(SignIn_Activity.this);
               if(isChecked && internetCheck){
                   SharedFunctions.showProgressBar(SignIn_Activity.this);
                           if(SharedFunctions.checkInternetConnection(SignIn_Activity.this)){
                               FireStore_DataBase.signInMethods(binding.editPhoneSignIn.getText().toString(), binding.editPassSignIn.getText().toString(), SignIn_Activity.this, new OnSuccessListener<Boolean>() {
                                   @Override
                                   public void onSuccess(Boolean aBoolean) {
                                       if(aBoolean){
                                           SharedFunctions.isSignIn(true , SignIn_Activity.this);
                                       }else{
                                           SharedFunctions.dismissDialog();
                                       }
                                   }
                               });

                           }else {
                               usersViewModel.getAllUsers().observe(SignIn_Activity.this, new Observer<List<UsersInfo>>() {
                                   @Override
                                   public void onChanged(List<UsersInfo> usersInfos) {
                                       for(UsersInfo users : usersInfos){
                                           if(users.getPhone().equals(binding.editPhoneSignIn.getText().toString()) && users.getPass().equals(binding.editPassSignIn.getText().toString())){

                                               saveUid(users.getUid());
                                               //لجلب البيانات الخاصة بكل مستخدم uid ارسال فيمة
                                               Intent intent = new Intent(SignIn_Activity.this  , Home_Activity.class);
                                               intent.putExtra("uid" , users.getUid());
                                               startActivity(intent);
                                               finish();
                                               SharedFunctions.isSignIn(true , SignIn_Activity.this);
                                           }else {
                                               SharedFunctions.dismissDialog();
                                               Toast.makeText(SignIn_Activity.this, "يرجى التأكد من صحة البيانات", Toast.LENGTH_SHORT).show();
                                           }

                                       }
                                   }
                               });
                           }
               }else {
                   Toast.makeText(this, "يرجى التأكد من اتصالك بالشبكة !!", Toast.LENGTH_SHORT).show();
                   SharedFunctions.dismissDialog();
               }


                break;

            case R.id.gmail_ButSignIn:
                SharedFunctions.showProgressBar(SignIn_Activity.this);
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

            case R.id.eyePass:
                if (showPass == false) {
                    showPass = true;
                    binding.editPassSignIn.setTransformationMethod(null);
                    binding.eyePass.setImageResource(R.drawable.lock_open_24);

                } else {
                    showPass = false;
                    binding.editPassSignIn.setTransformationMethod(new PasswordTransformationMethod());
                    binding.eyePass.setImageResource(R.drawable.lock_24);

                }
                break;

        }

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
        if(photoUri!=null){
            photo=photoUri.toString();

        }else {
            photo="";
        }

//        //لارسال الاشعارات و تخزينها في قاعدة البيانات Token جلب
//        CloudMessaging.getToken(SignIn_Activity.this, new OnSuccessListener() {
//            @Override
//            public void onSuccess(Object o) {
//               if(photo!=null){
//                   // حفظ في المستخدم ريال تايم
//                   Users_Chat usersChat = new Users_Chat(id , name , photo , "" , true , o.toString());
//                   RealTime_DataBase.addUsersToRealTime(SignIn_Activity.this , id , usersChat );
//               }else {
//                   // حفظ في المستخدم ريال تايم
//                   Users_Chat usersChat = new Users_Chat(id , name , photo , "" , true , o.toString());
//                   RealTime_DataBase.addUsersToRealTime(SignIn_Activity.this , id , usersChat );
//               }
//            }
//        });


        FireStore_DataBase.getAllUsersInfo(new OnSuccessListener<ArrayList<UsersInfo>>() {
            @Override
            public void onSuccess(ArrayList<UsersInfo> usersInfos) {
                for(UsersInfo data : usersInfos){
                    if(data.getEmail().equalsIgnoreCase(email)){

                        Intent intent =new Intent(SignIn_Activity.this , Home_Activity.class);
                        intent.putExtra("uid" , data.getUid()) ;
                        startActivity(intent);
                        finish();
                        SharedFunctions.isSignIn(true , SignIn_Activity.this);
                        isFindEmail=true;
                        break;
                    }
                }

                if(!isFindEmail){
                    Intent intent = new Intent(SignIn_Activity.this , UsersInformation_Activity.class);
                    intent.putExtra("name" , name);
                    intent.putExtra("photo" ,photo);
                    intent.putExtra("email" , email);
                    intent.putExtra("uid" , id);

                    startActivity(intent);
                    SharedFunctions.isSignIn(true , SignIn_Activity.this);
                }

            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignIn_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    // حفظ رقم المعرف للمستخد
    private void saveUid(String uid) {
        if (uid != null) {
            SharedPreferences sharedPreferences = getSharedPreferences("saveUid", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("uid", uid);
            editor.apply();
        }

    }


}