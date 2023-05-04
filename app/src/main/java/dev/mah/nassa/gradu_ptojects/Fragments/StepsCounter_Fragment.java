package dev.mah.nassa.gradu_ptojects.Fragments;

import android.Manifest;

import androidx.core.content.ContextCompat;

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
import android.widget.TextView;
import android.widget.Toast;


import com.github.anastr.speedviewlib.SpeedView;

import java.util.ArrayList;

import dev.mah.nassa.gradu_ptojects.Constants.StopwatchTimer;
import dev.mah.nassa.gradu_ptojects.Interfaces.StartWalkingListener;
import dev.mah.nassa.gradu_ptojects.MVVM.Walking_MVVM;
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
    private Button startBut;
    private TextView timerTv, speedTv, distanceTv, caloriesTv, stepsTv;
    private SpeedView awesomeSpeedometer;
    private StartWalkingListener listener;
    private Walking_MVVM walkingMvvm;


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

                distanceTv.setText(strings.get(0) + " m");
                speedTv.setText(strings.get(1) + " m/min");
                caloriesTv.setText(strings.get(2));
                stepsTv.setText(strings.get(3));
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
                    startBut.setText("إيقاف");
                    startBut.setBackgroundColor(getResources().getColor(R.color.red));
                    startBut.setBackground(getResources().getDrawable(R.drawable.stop_button));
                }else{
                    isStart = false;
                    startBut.setText("بدء");
                    startBut.setBackground(getResources().getDrawable(R.drawable.default_button));
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        startBut = getActivity().findViewById(R.id.startBut);
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
                    startBut.setText("إيقاف");
                    startBut.setBackground(getResources().getDrawable(R.drawable.stop_button));
                    listener.startWalkingListener(true);

                } else {
                    isStart = false;
                    startBut.setText("بدء");
                    startBut.setBackground(getResources().getDrawable(R.drawable.default_button));
                    listener.startWalkingListener(false);

                }
            }
        });


    }


}