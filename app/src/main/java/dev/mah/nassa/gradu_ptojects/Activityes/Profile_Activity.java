package dev.mah.nassa.gradu_ptojects.Activityes;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnSuccessListener;

import dev.mah.nassa.gradu_ptojects.DataBase.FireStore_DataBase;
import dev.mah.nassa.gradu_ptojects.Modles.UsersInfo;
import dev.mah.nassa.gradu_ptojects.databinding.ActivityProfileBinding;

public class Profile_Activity extends AppCompatActivity {
    private ActivityProfileBinding binding;
    String[] nameSpinner = {"مستوى نشاط"};
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
                binding.profileIdProgressBar.setVisibility(View.GONE);
                UsersInfo usersInfo = (UsersInfo) o;
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

                //أظهار تصميم بعد الحصول على البيانات
                binding.profileLayout.setVisibility(View.VISIBLE);
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
            }
        });
    }

    // جلب رقم المعرف للمستخد
    private String loadUid(){
        SharedPreferences sharedPreferences = getSharedPreferences("saveUid" , Context.MODE_PRIVATE);
        Toast.makeText(this, "load", Toast.LENGTH_SHORT).show();
        return sharedPreferences.getString("uid" , "");
    }

}