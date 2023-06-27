package dev.mah.nassa.gradu_ptojects.Activityes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import dev.mah.nassa.gradu_ptojects.Constants.LanguageUtils;
import dev.mah.nassa.gradu_ptojects.Constants.PersonActivityArray;
import dev.mah.nassa.gradu_ptojects.Constants.Vital_Equations;
import dev.mah.nassa.gradu_ptojects.DataBase.FireStore_DataBase;
import dev.mah.nassa.gradu_ptojects.DataBase.RealTime_DataBase;
import dev.mah.nassa.gradu_ptojects.Interfaces.UsersInfoListener;
import dev.mah.nassa.gradu_ptojects.DataBase.AppDatabese;
import dev.mah.nassa.gradu_ptojects.MVVM.UsersHealthInfoViewModel;
import dev.mah.nassa.gradu_ptojects.Modles.UsersInfo;
import dev.mah.nassa.gradu_ptojects.MVVM.UsersViewModel;
import dev.mah.nassa.gradu_ptojects.Modles.Users_Chat;
import dev.mah.nassa.gradu_ptojects.Modles.Users_Health_Info;
import dev.mah.nassa.gradu_ptojects.R;
import dev.mah.nassa.gradu_ptojects.Services_Firebase.CloudMessaging;
import dev.mah.nassa.gradu_ptojects.databinding.ActivityUsersInformationBinding;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UsersInformation_Activity extends AppCompatActivity implements UsersInfoListener {
    private ActivityUsersInformationBinding binding;
    private String phone, pass, name, eage, uid, length, weight, activityLevel, gender, email, photo;
    private String illness;
    private Long alarmTime;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private UsersHealthInfoViewModel usersHealthInfoViewModel;
    private UsersViewModel usersViewModel;
    private boolean isFind = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsersInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        LanguageUtils.changeLanguage(UsersInformation_Activity.this , "en");

        usersHealthInfoViewModel = ViewModelProviders.of(this).get(UsersHealthInfoViewModel.class);
        usersViewModel = ViewModelProviders.of(this).get(UsersViewModel.class);

        Intent i = getIntent();
        name = i.getStringExtra("name");
        pass = i.getStringExtra("pass");
        phone = i.getStringExtra("phone");
        uid = i.getStringExtra("uid");
        photo = i.getStringExtra("photo");
        email = i.getStringExtra("email");



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
        switch (fragNumber) {
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
    public void getInfoUsers(String gender, String length, String weight, String eage, String illness, Long alarmTime) {
        this.eage = eage;
        this.gender = gender;
        this.length = length;
        this.weight = weight;
        this.illness = illness;
        this.alarmTime = alarmTime;
    }

    // بعد اختيار المستخدم مستوى النشاط نقوم بارسال مستوى
    // النشاط وعليه يتم حساب السعرات الحرارية و كمية المياه و حفظ البيانات المستخدم و البيانات الصحية
    @Override
    public void getActivityLevel(int activityIndex, String activityLevel) {

        Observable.fromCallable(() -> {
                    UsersInfo usersInfo = new UsersInfo(uid, name, phone, pass, eage, length, weight, activityLevel, gender, photo, email);
                    ArrayList<UsersInfo> usersInfos = new ArrayList<>(); // Placeholder for your actual data retrieval from Firestore
                    boolean isFind = false;

                    for (UsersInfo users : usersInfos) {
                        if (users.getEmail().equals(usersInfo.getEmail()) && users.getPhone().equals(usersInfo.getPhone())) {
                            isFind = true;
                            break;
                        }
                    }

                    if (!isFind) {
                        FireStore_DataBase.insertUsersInfo(usersInfo, UsersInformation_Activity.this);
                        usersViewModel.insertUsers(usersInfo);

                        // حفظ المستخدم للدردشة
                        CloudMessaging.getToken(UsersInformation_Activity.this, new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
//                                Toast.makeText(UsersInformation_Activity.this, "Name  :"+ name  +"\n phone  :"  + phone +"\nToken  :"+o.toString(), Toast.LENGTH_SHORT).show();
                                Users_Chat usersChat  = new Users_Chat(uid , name , photo , " " , true , o.toString());
                                RealTime_DataBase.addUsersToRealTime(UsersInformation_Activity.this , uid , usersChat);



                            }
                        });

                        if (!illness.isEmpty()) {
                            String levelActivity = PersonActivityArray.getPersonActivityList().get(activityIndex);
                            double caloriesRequirement = Vital_Equations.caloriDailyRequirment(UsersInformation_Activity.this, eage, length, levelActivity, weight, gender);
                            double waterQuantity = Vital_Equations.waterQuantity(weight);
                            Users_Health_Info usersHealthInfo = new Users_Health_Info(uid, caloriesRequirement, waterQuantity, true, alarmTime, 0, 0);
                            usersHealthInfoViewModel.insertUsersHealth(usersHealthInfo);
                            FireStore_DataBase.insertUsersHealthInfo(usersHealthInfo, UsersInformation_Activity.this);
                        }
                    } else {
                        throw new RuntimeException("رقم الهاتف أو البريد الاكتروني مستخدم بالفعل");
                    }

                    // Return a non-null value
                    return true;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> {
                            // Handle success
                        },
                        error -> {
                            Toast.makeText(UsersInformation_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(UsersInformation_Activity.this, SignUp_Activity.class));
                            finish();
                        }
                );
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
        editor.putString("name", name);
        editor.putString("pass", pass);
        editor.putString("phone", phone);
        editor.putString("gender", gender);
        editor.putString("length", length);
        editor.putString("weight", weight);
        editor.putString("eage", eage);
        editor.putString("uid", uid);
        editor.putString("photo", photo);
        editor.putString("email", email);
        editor.apply();
    }


    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        name = sharedPreferences.getString("name", "");
        pass = sharedPreferences.getString("pass", "");
        phone = sharedPreferences.getString("phone", "");
        gender = sharedPreferences.getString("gender", "");
        length = sharedPreferences.getString("length", "");
        weight = sharedPreferences.getString("weight", "");
        eage = sharedPreferences.getString("eage", "");
        uid = sharedPreferences.getString("uid", "");
        photo = sharedPreferences.getString("photo", "");
        email = sharedPreferences.getString("email", "");
    }

}