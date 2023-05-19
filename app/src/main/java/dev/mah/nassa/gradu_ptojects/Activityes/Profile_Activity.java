package dev.mah.nassa.gradu_ptojects.Activityes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

import dev.mah.nassa.gradu_ptojects.Constants.PersonActivityArray;
import dev.mah.nassa.gradu_ptojects.DataBase.FireStore_DataBase;
import dev.mah.nassa.gradu_ptojects.Modles.UsersInfo;
import dev.mah.nassa.gradu_ptojects.databinding.ActivityProfileBinding;

public class Profile_Activity extends AppCompatActivity {
    private ActivityProfileBinding binding;
    private UsersInfo usersInfo,usersInfoEdite;

    boolean isRequestCode = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //اظهار ال ProgressBar
        binding.profileIdProgressBar.setVisibility(View.VISIBLE);

        //اخفاء تصميم
        binding.profileLayout.setVisibility(View.INVISIBLE);


        //الحصول على بيانات المستخدم من FireStore
        //Show data User
        FireStore_DataBase.getUsersById(loadUid(), this, new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {

                //ProgressBar اخفاء ال
                binding.profileIdProgressBar.setVisibility(View.GONE);

                usersInfo = (UsersInfo) o;

                Glide.with(getBaseContext())
                        .load(usersInfo.getPhoto())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.profileIvImage);
                binding.profileTvName.setText(usersInfo.getName());
                binding.profileTvAge.setText(usersInfo.getEage());
                binding.profileTvHeight.setText(usersInfo.getLength());
                binding.profileTvWeight.setText(usersInfo.getWeight());
                binding.profileTvEmail.setText(usersInfo.getEmail());
                binding.profileTvPhone.setText(usersInfo.getPhone());
                binding.profileTvActivityLeve.setText(usersInfo.getActivityLeve());
                binding.profileTvGender.setText(usersInfo.getGender());
                //أظهار تصميم بعد الحصول على البيانات
                binding.profileLayout.setVisibility(View.VISIBLE);
                isRequestCode =false;
            }

        });

        //رجوع لصفحة home
        binding.profileIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //تعديل على بيانات المستخدم
        binding.profileIvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Profile_Activity.this, "Edit", Toast.LENGTH_SHORT).show();

                int i= itemSpin(usersInfo.getActivityLeve() , PersonActivityArray.getPersonActivityList());
                Intent intent = new Intent(getBaseContext() , ProfileEdite_Activity.class);

                if (isRequestCode == false){
                    usersInfo.setItemSpn(i);
                }else {
                    int iIs= itemSpin(usersInfoEdite.getActivityLeve() , PersonActivityArray.getPersonActivityList());
                    usersInfo.setItemSpn(iIs);}


                intent.putExtra("profile" ,usersInfo);
                startActivityForResult(intent,1);
            }
        });
    }

    // جلب رقم المعرف للمستخد
    private String loadUid(){
        SharedPreferences sharedPreferences = getSharedPreferences("saveUid" , Context.MODE_PRIVATE);
        Toast.makeText(this, "load", Toast.LENGTH_SHORT).show();
        return sharedPreferences.getString("uid" , "");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {

            usersInfoEdite = (UsersInfo) data.getSerializableExtra("profile");
            Glide.with(getBaseContext())
                    .load(usersInfoEdite.getPhoto())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.profileIvImage);
            binding.profileTvName.setText(usersInfoEdite.getName());
            binding.profileTvAge.setText(usersInfoEdite.getEage());
            binding.profileTvHeight.setText(usersInfoEdite.getLength());
            binding.profileTvWeight.setText(usersInfoEdite.getWeight());
            binding.profileTvEmail.setText(usersInfoEdite.getEmail());
            binding.profileTvPhone.setText(usersInfoEdite.getPhone());
            binding.profileTvActivityLeve.setText(usersInfoEdite.getActivityLeve());
            binding.profileTvGender.setText(usersInfoEdite.getGender());
            isRequestCode = true;
        }
    }

    int itemSpin(String activityLeve1 , ArrayList<String> arrayList){

        String lave = activityLeve1.replaceAll("\\s+","");
        String arrayName;
        int i;
        for ( i = 0; i < PersonActivityArray.getPersonActivityList().size() ; i++)
        {
            arrayName = arrayList.get(i).replaceAll("\\s+","");
            if (lave.equalsIgnoreCase(arrayName)){
                break;
            }
        }
        return i;
    }
}