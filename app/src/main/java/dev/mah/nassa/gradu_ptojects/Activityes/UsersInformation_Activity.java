package dev.mah.nassa.gradu_ptojects.Activityes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import dev.mah.nassa.gradu_ptojects.DataBase.FireStore_DataBase;
import dev.mah.nassa.gradu_ptojects.Interfaces.UsersInfoListener;
import dev.mah.nassa.gradu_ptojects.Modles.UsersInfo;
import dev.mah.nassa.gradu_ptojects.Modles.Users_Health_Info;
import dev.mah.nassa.gradu_ptojects.R;
import dev.mah.nassa.gradu_ptojects.databinding.ActivityUsersInformationBinding;

public class UsersInformation_Activity extends AppCompatActivity implements UsersInfoListener {
    private ActivityUsersInformationBinding binding;
    private String phone , pass , name , eage , uid , length , weight , activityLevel,gender,email,photo;
    private boolean illness;
    private Long alarmTime;
    private FirebaseFirestore firestore=FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUsersInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent i=getIntent();
        name=i.getStringExtra("name");
        pass=i.getStringExtra("pass");
        phone=i.getStringExtra("phone");
        uid=i.getStringExtra("uid");
        photo= i.getStringExtra("photo");
        email=i.getStringExtra("email");



        Toast.makeText(this, "PHOME NUMBER  ="+phone, Toast.LENGTH_SHORT).show();

        // وضع اتجاه النص و الواجهات من الاليمين الى اليسار لغة عربية
        binding.parentUsersInfo.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        binding.parentUsersInfo.setTextDirection(View.TEXT_DIRECTION_ANY_RTL);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Toast.makeText(this, "لم يتبقى سوى خطوة واحدة للتسجيل !!", Toast.LENGTH_SHORT).show();
    }

    // ميثود خاصة بالمؤشر
    @Override
    public void getFragmentNumber(int fragNumber) {
        switch (fragNumber){
            case 1:
                binding.indicatUserInfo1.setBackground(getDrawable(R.drawable.usersinfo_indicators));
                binding.indicatUserInfo2.setBackground(getDrawable(R.drawable.usersinfo_inducators_ouline));
                break;

            case 2:
                binding.indicatUserInfo2.setBackground(getDrawable(R.drawable.usersinfo_indicators));
                binding.indicatUserInfo1.setBackground(getDrawable(R.drawable.usersinfo_indicators));
                break;


        }
    }

    // fragment جلب بيانات المستخدم من
    @Override
    public void getInfoUsers(String gender, String length, String weight, String eage, boolean illness, Long alarmTime) {
        this.eage=eage;
        this.gender=gender;
        this.length=length;
        this.weight=weight;
        this.illness=illness;
        this.alarmTime=alarmTime;
    }

    // مستوى نشاط المستخدم (Interfacece)
    @Override
    public void getActivityLevel(int activityIndex , String activityLeve) {
        UsersInfo usersInfo = new UsersInfo(uid,name,phone,pass,eage,length,weight,activityLeve,gender,photo,email);
        FireStore_DataBase.insertUsersInfo( usersInfo,UsersInformation_Activity.this); // Fire Store حفظ بيانات المستخدم على
        if(illness ==true || illness ==false){
            Users_Health_Info usersHealthInfo=new Users_Health_Info(0,uid,232.5 , 3,illness, alarmTime);
            FireStore_DataBase.insertUsersHealthInfo(usersHealthInfo, UsersInformation_Activity.this);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        saveData(); //shared نخزنها في  fragment عند استلام البينات من
        loadData();
        Toast.makeText(this, gender, Toast.LENGTH_SHORT).show();

    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name" , name);
        editor.putString("pass" , pass);
        editor.putString("phone" , phone);
        editor.putString("gender" , gender);
        editor.putString("length" , length);
        editor.putString("weight" , weight);
        editor.putString("eage" , eage);
        editor.putString("uid",uid);
        editor.putString("photo",photo);
        editor.putString("email",email);
        editor.apply();
    }


    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        name= sharedPreferences.getString("name" , "");
        pass= sharedPreferences.getString("pass" , "");
        phone= sharedPreferences.getString("phone" , "");
        gender= sharedPreferences.getString("gender" , "");
        length= sharedPreferences.getString("length" , "");
        weight= sharedPreferences.getString("weight" , "");
        eage= sharedPreferences.getString("eage" , "");
        uid=sharedPreferences.getString("uid" , "");
        photo=sharedPreferences.getString("photo" , "");
        email=sharedPreferences.getString("email" , "");
    }

}