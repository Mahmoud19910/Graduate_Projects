package dev.mah.nassa.gradu_ptojects.Fragments;

import android.Manifest;

import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.github.anastr.speedviewlib.SpeedView;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import dev.mah.nassa.gradu_ptojects.Activityes.Home_Activity;
import dev.mah.nassa.gradu_ptojects.Constants.CustomDialog;
import dev.mah.nassa.gradu_ptojects.Constants.LanguageUtils;
import dev.mah.nassa.gradu_ptojects.Constants.SharedFunctions;
import dev.mah.nassa.gradu_ptojects.Constants.StopwatchTimer;
import dev.mah.nassa.gradu_ptojects.DataBase.FireStore_DataBase;
import dev.mah.nassa.gradu_ptojects.Interfaces.StartWalkingListener;
import dev.mah.nassa.gradu_ptojects.MVVM.ExersiseDetails_MVVM;
import dev.mah.nassa.gradu_ptojects.MVVM.Walking_MVVM;
import dev.mah.nassa.gradu_ptojects.Modles.Exercise_Details;
import dev.mah.nassa.gradu_ptojects.R;
import dev.mah.nassa.gradu_ptojects.databinding.FragmentStepsCountBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StepsCounter_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepsCounter_Fragment extends Fragment {

    private FragmentStepsCountBinding binding;
    private boolean isStart = false;
    private LinearLayout startBut , finishBut;
    private TextView timerTv, speedTv, distanceTv, caloriesTv, stepsTv , stopTv;
    private ImageView startIcon;
    private SpeedView awesomeSpeedometer;
    private StartWalkingListener listener;
    private Walking_MVVM walkingMvvm;
    private String timeAtMomment , caloriesBurned ;
    private ExersiseDetails_MVVM exersiseDetails_mvvm;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof StartWalkingListener) {
            listener = (StartWalkingListener) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = FragmentStepsCountBinding.inflate(inflater).getRoot();
        LanguageUtils.changeLanguage(getContext(), "en");

        // View Model تحديث البيانات وجلبها من الأكتيفيتي عن طريق
        walkingMvvm = ViewModelProviders.of(getActivity()).get(Walking_MVVM.class);

        // جلب الوقت
        walkingMvvm.getIme().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                timerTv.setText(s);
            }
        });

        // جلب بيانات السرعة و السعرات المحروقة و المسافة و الخطوات
        walkingMvvm.getAllData().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                SharedFunctions.saveCaloBurned(strings.get(2) , getContext()); // حفظ قيمة السعرات عندما لاتكون قيمتها صفر عند الوقوف عن المشي

                distanceTv.setText(strings.get(0) + " m");
                speedTv.setText(strings.get(1) + " m/min");

                caloriesTv.setText(SharedFunctions.loadCaloBurned(getContext()));
                caloriesBurned = strings.get(2);
                stepsTv.setText(strings.get(3));
                timeAtMomment = strings.get(4);

                awesomeSpeedometer.setWithTremble(false);
                awesomeSpeedometer.speedTo((float) Double.parseDouble(strings.get(1)));



            }
        });

        // جلب قيمة منطقية لزر التشغيل و الايقاف حتى يبقى على وضعه
        walkingMvvm.getIsStart().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean.booleanValue()){
                    isStart = true;
                    stopTv.setText("إيقاف");
                    startIcon.setImageDrawable(getActivity().getDrawable(R.drawable.pause_24));

                }else{
                    isStart = false;
                    stopTv.setText("بدء");
                    startIcon.setImageDrawable(getActivity().getDrawable(R.drawable.play_arrow_24));

                }
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        exersiseDetails_mvvm = ViewModelProviders.of(StepsCounter_Fragment.this).get(ExersiseDetails_MVVM.class);

        startBut = getActivity().findViewById(R.id.startBut);
        finishBut = getActivity().findViewById(R.id.finishBut);
        stopTv = getActivity().findViewById(R.id.stopTv);
        startIcon = getActivity().findViewById(R.id.startIcon);
        speedTv = getActivity().findViewById(R.id.tv_Speed);
        caloriesTv = getActivity().findViewById(R.id.tv_Calories);
        distanceTv = getActivity().findViewById(R.id.tv_Distance);
        stepsTv = getActivity().findViewById(R.id.steps);
        awesomeSpeedometer = getActivity().findViewById(R.id.awesomeSpeedome2);
        timerTv = getActivity().findViewById(R.id.tv_Time);


        startBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isStart) {
                    isStart = true;
                    stopTv.setText("إيقاف");
                    startIcon.setImageDrawable(getActivity().getDrawable(R.drawable.pause_24));
                    listener.startWalkingListener(true , false);

                } else {
                    isStart = false;
                    stopTv.setText("بدء");
                    startIcon.setImageDrawable(getActivity().getDrawable(R.drawable.play_arrow_24));
                    listener.startWalkingListener(false , false);

                }
            }
        });

        finishBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStart = true;
                stopTv.setText("بدء");
                Random random = new Random();
                startIcon.setImageDrawable(getActivity().getDrawable(R.drawable.play_arrow_24));
                if( !caloriesTv.getText().toString().isEmpty() && caloriesTv.getText().toString() != null && Double.parseDouble(caloriesTv.getText().toString()) > 0.0){
                    Exercise_Details exercise_details = new Exercise_Details(loadUid() , UUID.randomUUID().toString() ,  caloriesTv.getText().toString() , timeAtMomment ,  SharedFunctions.getDateAtTheMoment() , "رياضة المشي" ,String.valueOf(Home_Activity.timer.getTimeByHours()));
                    exersiseDetails_mvvm.insertExersiseDetails(exercise_details);
                    FireStore_DataBase.insertOrUpdateExerciseDetails(exercise_details , getContext());
                    CustomDialog dialog = new CustomDialog(getContext() , timerTv.getText().toString() , caloriesBurned);
                    dialog.show();
                    SharedFunctions.saveCaloBurned("0.00" , getContext()); //  حفظ قيمة 0 عند الانهاء
                }
                listener.startWalkingListener(false , true);

            }
        });




    }

    // جلب رقم المعرف للمستخد
    private String loadUid() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("saveUid", Context.MODE_PRIVATE);
        return sharedPreferences.getString("uid", "");
    }




}