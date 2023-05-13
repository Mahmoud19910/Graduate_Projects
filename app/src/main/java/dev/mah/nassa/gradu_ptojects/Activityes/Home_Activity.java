package dev.mah.nassa.gradu_ptojects.Activityes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.gms.tasks.OnSuccessListener;

import dev.mah.nassa.gradu_ptojects.Constants.SharedFunctions;
import dev.mah.nassa.gradu_ptojects.Constants.StepsTracking;
import dev.mah.nassa.gradu_ptojects.Constants.StopwatchTimer;
import dev.mah.nassa.gradu_ptojects.Constants.Vital_Equations;
import dev.mah.nassa.gradu_ptojects.FireBase_Authentication.Gmai_Auth;
import dev.mah.nassa.gradu_ptojects.Fragments.Doctors_Fragment;
import dev.mah.nassa.gradu_ptojects.Fragments.FoodCategory_Fragment;
import dev.mah.nassa.gradu_ptojects.Fragments.Home_Fragment;
import dev.mah.nassa.gradu_ptojects.Fragments.StepsCounter_Fragment;
import dev.mah.nassa.gradu_ptojects.Fragments.Training_Fragment;
import dev.mah.nassa.gradu_ptojects.Interfaces.StartWalkingListener;
import dev.mah.nassa.gradu_ptojects.MVVM.UsersHealthInfoViewModel;
import dev.mah.nassa.gradu_ptojects.MVVM.Walking_MVVM;
import dev.mah.nassa.gradu_ptojects.Modles.UsersInfo;
import dev.mah.nassa.gradu_ptojects.MVVM.UsersViewModel;
import dev.mah.nassa.gradu_ptojects.R;
import dev.mah.nassa.gradu_ptojects.databinding.ActivityHomeBinding;

public class Home_Activity extends AppCompatActivity implements View.OnClickListener , StartWalkingListener , SensorEventListener {

    private UsersViewModel usersViewModel;
    private Walking_MVVM walkingMvvm;
    private UsersHealthInfoViewModel usersHealthInfoViewModel;

    private ActivityHomeBinding binding;
    private String uid;
    private StopwatchTimer timer;
    private SensorManager sensorManager;
    private boolean running = false;
    private float totalSteps = 0f;
    private long previousTime = 0;
    private float previousTotalSteps = 0f;
    private float averageStepLengthMeters = 0.7f; // average step length in meters
    private double spped;
    private  float distance;
    private  int currentSteps;
    private boolean isPlay=false;
    private String weight;

    private static final int PERMISSION_REQUEST_ACTIVITY_RECOGNITION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        usersViewModel=ViewModelProviders.of(Home_Activity.this).get(UsersViewModel.class);
        // (Steps Fragment) يستخدم لحفظ و ارسال القيم بشكل أوتوماتيكي الى  View Model
        walkingMvvm = ViewModelProviders.of(Home_Activity.this).get(Walking_MVVM.class);
        usersHealthInfoViewModel=ViewModelProviders.of(Home_Activity.this).get(UsersHealthInfoViewModel.class);
        timer = new StopwatchTimer();

        binding.parentLayoutHome.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        binding.parentLayoutHome.setTextDirection(View.TEXT_DIRECTION_ANY_RTL);

      Intent intent = getIntent();
     uid = intent.getStringExtra("uid");

     // Shared Prefrences
     saveUid(uid);

        usersViewModel.getUsersByUid(loadUid()).observe(this, new Observer<UsersInfo>() {
            @Override
            public void onChanged(UsersInfo usersInfo) {
                binding.userNmae.setText(usersInfo.getName());
                weight=usersInfo.getWeight();
                Toast.makeText(Home_Activity.this, usersInfo.getName()+"name", Toast.LENGTH_SHORT).show();

            }
        });

        // Listener
        binding.drawer.setOnClickListener(this);

        // Set Main Layout (Home Activity)
       FragmentTransaction fragmentTransaction =  getSupportFragmentManager().beginTransaction();
       fragmentTransaction.replace(R.id.frame , new Home_Fragment(loadUid()));
       fragmentTransaction.commit();


        MeowBottomNavigation bottomNavigation = findViewById(R.id.navigateBar);

        // set default layout
        bottomNavigation.show(3,false);
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.article_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.walk_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_baseline_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.gymnastics_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(5, R.drawable.ic_baseline_health_and_safety_24));

        // الانتقال بين fragments
        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                switch (item.getId()){
                    case 1:
                        replace(new FoodCategory_Fragment());

                        break;
                    case 2:
                       replace(new StepsCounter_Fragment());
                        break;
                    case 3:

                        Home_Fragment home_fragment = new Home_Fragment(loadUid());
                        replace(home_fragment);

                        break;
                    case 4:
                        replace(new Training_Fragment());

                        break;
                    case 5:
                        replace(new Doctors_Fragment());

                        break;

                }
            }
        });

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                // your codes
            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                // your codes
            }
        });


        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        timer = new StopwatchTimer();

    }

    @Override
    public void onResume() {
        super.onResume();
        // Request permission for physical activity recognition
        if (ContextCompat.checkSelfPermission(Home_Activity.this, Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, PERMISSION_REQUEST_ACTIVITY_RECOGNITION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Handle permission request result for physical activity recognition
        if (requestCode == PERMISSION_REQUEST_ACTIVITY_RECOGNITION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "Permission granted for activity recognition", Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(this, "Permission denied for activity recognition", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void startTracking() {

        // Enable tracking and start sensor listener
        running = true;
        Sensor stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if (stepSensor == null) {
//            Toast.makeText(getContext(), "No sensor detected on this device", Toast.LENGTH_SHORT).show();
        } else {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI);
        }

    }


    public void stopTracking() {
        // Disable tracking and stop sensor listener
        running = false;
        sensorManager.unregisterListener(this);
        timer.timerTask.cancel();
        Toast.makeText(getApplicationContext(), timer.getTimeByHours() + "", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {


        long currentTime = System.currentTimeMillis();

        if (running) {
            totalSteps = event.values[0];

            // شرط عند التشغيل لأول مرة يقوم بتصفير الخطوات
            if(isPlay==false){
                isPlay=true;
                previousTotalSteps = totalSteps;
            }
            currentSteps = (int) (totalSteps - previousTotalSteps);
            distance = currentSteps * averageStepLengthMeters;
            long deltaTime = currentTime - previousTime;


            double minTime = timer.getTimeByMinutes();
            spped = distance / minTime;
            double metaValue = Vital_Equations.calculateMETABOLICEQUIVALENTS(Vital_Equations.convertSpeesToMilesPerHourse(spped));
            double caloriesBurnd = Vital_Equations.calculateCaloriesBurnd(Double.parseDouble(weight),metaValue,timer);
            walkingMvvm.setAllData(String.format("%.2f",distance),String.format("%.2f", spped) ,String.format("%.2f", caloriesBurnd) , Integer.toString(currentSteps));
            usersHealthInfoViewModel.updateCalories(loadUid() , caloriesBurnd);



        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }






    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.drawer:
                DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
                drawerLayout.openDrawer(GravityCompat.START);

                break;
        }
    }


    // Method to replace layout On Navigation
    private void replace(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.commit();
    }

    // حفظ رقم المعرف للمستخد
    private void saveUid(String uid){
        if(uid!=null){
            SharedPreferences sharedPreferences = getSharedPreferences("saveUid"  , Context.MODE_PRIVATE);
            SharedPreferences.Editor editor =sharedPreferences.edit();
            editor.putString("uid" , uid);
            Toast.makeText(this, "save", Toast.LENGTH_SHORT).show();
            editor.apply();
        }

    }

    // جلب رقم المعرف للمستخد
    private String loadUid(){
        SharedPreferences sharedPreferences = getSharedPreferences("saveUid" , Context.MODE_PRIVATE);
        Toast.makeText(this, "load", Toast.LENGTH_SHORT).show();
     return sharedPreferences.getString("uid" , "");
    }


    // Implementation Wallking Interface

    @Override
    public void startWalkingListener(boolean isStart) {
        if(isStart){ // بدء المشي
            timer.startTimer(new OnSuccessListener() { // بدء المؤقت
                @Override
                public void onSuccess(Object o) {
                    walkingMvvm.setTime(o.toString());
                }
            });
            startTracking();

            //fragment ليبقى الزر على وضعه عند الرحوع الى  true ارسال قيمة
            walkingMvvm.setIsStart(true);
        }else {
          stopTracking();
            walkingMvvm.setIsStart(false);
        }
    }
}