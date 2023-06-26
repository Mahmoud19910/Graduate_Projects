package dev.mah.nassa.gradu_ptojects.Activityes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

import dev.mah.nassa.gradu_ptojects.Constants.LanguageUtils;
import dev.mah.nassa.gradu_ptojects.Constants.PersonActivityArray;
import dev.mah.nassa.gradu_ptojects.Constants.SharedFunctions;
import dev.mah.nassa.gradu_ptojects.DataBase.FireStore_DataBase;
import dev.mah.nassa.gradu_ptojects.MVVM.UsersViewModel;
import dev.mah.nassa.gradu_ptojects.Modles.UsersInfo;
import dev.mah.nassa.gradu_ptojects.R;
import dev.mah.nassa.gradu_ptojects.databinding.ActivityProfileBinding;

public class Profile_Activity extends AppCompatActivity {
    private ActivityProfileBinding binding;
    private UsersInfo usersInfo,usersInfoEdite;
    private String uid;
    private UsersViewModel usersViewModel;

    boolean isRequestCode = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        usersViewModel = ViewModelProviders.of(this).get(UsersViewModel.class);
        setContentView(binding.getRoot());

        LanguageUtils.changeLanguage(Profile_Activity.this , "en");


        //اظهار ال ProgressBar
        binding.profileIdProgressBar.setVisibility(View.VISIBLE);

        //اخفاء تصميم
        binding.profileLayout.setVisibility(View.INVISIBLE);

        //الحصول على بيانات المستخدم من FireStore
        //Show data User
       boolean internetConection =  SharedFunctions.checkInternetConnection(getBaseContext());

       if(internetConection){
           FireStore_DataBase.getUsersById(loadUid(), this, new OnSuccessListener() {
               @Override
               public void onSuccess(Object o) {

                   //ProgressBar اخفاء ال
                   binding.profileIdProgressBar.setVisibility(View.GONE);


                   usersInfo = (UsersInfo) o;
                   if(usersInfo.getPhoto()==null || usersInfo.getPhoto().isEmpty()){
                       binding.profileIvImage.setImageDrawable(getDrawable(R.drawable.user));
                   }else {
                       Glide.with(getBaseContext())
                               .load(usersInfo.getPhoto())
                               .diskCacheStrategy(DiskCacheStrategy.ALL)
                               .into(binding.profileIvImage);
                   }
                   binding.profileTvName.setText(usersInfo.getName());
                   binding.profileTvAge.setText(usersInfo.getEage());
                   binding.profileTvHeight.setText(usersInfo.getLength());
                   binding.profileTvWeight.setText(usersInfo.getWeight());
                   binding.profileTvEmail.setText(usersInfo.getEmail());
                   binding.profileTvPhone.setText(usersInfo.getPhone());
                   binding.profileTvActivityLeve.setText(usersInfo.getActivityLevel().replaceAll("\n"," "));
                   binding.profileTvGender.setText(usersInfo.getGender());
                   //أظهار تصميم بعد الحصول على البيانات
                   binding.profileLayout.setVisibility(View.VISIBLE);
                   isRequestCode =false;
               }

           });
       }else {

           usersViewModel.getUsersByUid(loadUid()).observe(Profile_Activity.this, new Observer<UsersInfo>() {
               @Override
               public void onChanged(UsersInfo usersInfo2) {

                   usersInfo = usersInfo2;
                   //ProgressBar اخفاء ال
                   binding.profileIdProgressBar.setVisibility(View.GONE);

                   if(usersInfo.getPhoto()==null || usersInfo.getPhoto().isEmpty()){
                       binding.profileIvImage.setImageDrawable(getDrawable(R.drawable.user));
                   }else {
                       Glide.with(getBaseContext())
                               .load(usersInfo.getPhoto())
                               .diskCacheStrategy(DiskCacheStrategy.ALL)
                               .into(binding.profileIvImage);
                   }
                   binding.profileTvName.setText(usersInfo.getName());
                   binding.profileTvAge.setText(usersInfo.getEage());
                   binding.profileTvHeight.setText(usersInfo.getLength());
                   binding.profileTvWeight.setText(usersInfo.getWeight());
                   binding.profileTvEmail.setText(usersInfo.getEmail());
                   binding.profileTvPhone.setText(usersInfo.getPhone());
                   binding.profileTvActivityLeve.setText(usersInfo.getActivityLevel().replaceAll("\n"," "));
                   binding.profileTvGender.setText(usersInfo.getGender());
                   //أظهار تصميم بعد الحصول على البيانات
                   binding.profileLayout.setVisibility(View.VISIBLE);
                   isRequestCode =false;

               }
           });
       }


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
                int position= itemSpin(usersInfo.getActivityLevel() , PersonActivityArray.getPersonActivityList());
                Intent intent = new Intent(getBaseContext() , ProfileEdite_Activity.class);

                //تحقق من قيمة السبنر اذا تم تعديلها سيجلب الاندكس جديد ويتم تمريره للأبجكت
                if (isRequestCode == false){
                    usersInfo.setItemSpn(position);
                }else {
                    int positionUpdate= itemSpin(usersInfoEdite.getActivityLevel() , PersonActivityArray.getPersonActivityList());
                    usersInfo.setItemSpn(positionUpdate);
                    usersInfo.setPhoto(usersInfoEdite.getPhoto());
                    usersInfo.setName(usersInfoEdite.getName());
                    usersInfo.setWeight(usersInfoEdite.getWeight());
                    usersInfo.setLength(usersInfoEdite.getLength());
                    usersInfo.setEage(usersInfoEdite.getEage());
                    usersInfo.setPhoto(usersInfoEdite.getPhoto());
                    usersInfo.setEmail(usersInfoEdite.getEmail());
                    usersInfo.setPhone(usersInfoEdite.getPhone());
                    usersInfo.setGender(usersInfoEdite.getGender());
                }

                intent.putExtra("profile" ,usersInfo);
                startActivityForResult(intent,1);
            }
        });
    }


    // جلب رقم المعرف للمستخد
    private String loadUid(){
        SharedPreferences sharedPreferences = getSharedPreferences("saveUid" , Context.MODE_PRIVATE);
        return sharedPreferences.getString("uid" , "");
    }

    //ميثود الحصول على مكان الاندكس في السبنر
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
            binding.profileTvActivityLeve.setText(usersInfoEdite.getActivityLevel().replaceAll("\n"," "));
            binding.profileTvGender.setText(usersInfoEdite.getGender());
            isRequestCode = true;
        }
    }

}