package dev.mah.nassa.gradu_ptojects.Fragments;

import static androidx.core.content.PermissionChecker.checkSelfPermission;

import android.Manifest;
import androidx.core.content.ContextCompat;
import android.content.pm.PackageManager;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.github.anastr.speedviewlib.SpeedView;

import dev.mah.nassa.gradu_ptojects.Constants.StopwatchTimer;
import dev.mah.nassa.gradu_ptojects.R;
import dev.mah.nassa.gradu_ptojects.databinding.FragmentStepsCountBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StepsCounter_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepsCounter_Fragment extends Fragment implements SensorEventListener {

   private FragmentStepsCountBinding binding;
   private SensorManager sensorManager;
   private StopwatchTimer timer;
    private boolean running = false;
    private float totalSteps = 0f;
    private long previousTime = 0;
    private float previousTotalSteps = 0f;
    private float averageStepLengthMeters = 0.7f; // average step length in meters
    private double spped;
    private  float distance;
    private  int currentSteps;
    private static final int PERMISSION_REQUEST_ACTIVITY_RECOGNITION = 0;
    private boolean isStart=false;
    private boolean isPlay=false;
    private Button startBut;
    private TextView timerTv , speedTv , distanceTv , caloriesTv , stepsTv;
    private SpeedView awesomeSpeedometer;









    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
         return FragmentStepsCountBinding.inflate(inflater).getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       startBut = getActivity().findViewById(R.id.startBut);
       speedTv = getActivity().findViewById(R.id.tv_Speed);
       caloriesTv = getActivity().findViewById(R.id.tv_Calories);
       distanceTv = getActivity().findViewById(R.id.tv_Distance);
       stepsTv = getActivity().findViewById(R.id.steps);
        awesomeSpeedometer=getActivity().findViewById(R.id.awesomeSpeedome2);
        timerTv = getActivity().findViewById(R.id.tv_Time);


        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        timer = new StopwatchTimer();
        resetSteps();

        startBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isStart){
                    isStart=true;
                    startTracking();
                    startBut.setText("إيقاف");
                    startBut.setBackgroundColor(getResources().getColor(R.color.red));
                    startBut.setBackground(getResources().getDrawable(R.drawable.stop_button));

                }else{
                    isStart=false;
                    stopTracking();
                    startBut.setText("بدء");
                    startBut.setBackgroundColor(getResources().getColor(R.color.blue));
                    startBut.setBackground(getResources().getDrawable(R.drawable.default_button));
                    double metaValue = calculateMETABOLICEQUIVALENTS(convertSpeesToMilesPerHourse(spped));
                    caloriesTv.setText((metaValue*70*timer.getTimeByHours())+"");
                }
            }
        });



    }


    @Override
    public void onResume() {
        super.onResume();
        // Request permission for physical activity recognition
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, PERMISSION_REQUEST_ACTIVITY_RECOGNITION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Handle permission request result for physical activity recognition
        if (requestCode == PERMISSION_REQUEST_ACTIVITY_RECOGNITION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Permission granted for activity recognition", Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(this, "Permission denied for activity recognition", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void startTracking() {
        timer.startTimer(timerTv);
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
        Toast.makeText(getContext(), timer.getTimeByHours() + "", Toast.LENGTH_LONG).show();
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
            stepsTv.setText(Integer.toString(currentSteps));
            distanceTv.setText(String.format("%.2f", distance) + " m");
            speedTv.setText(String.format("%.2f", spped) + " m/min");
            awesomeSpeedometer.setWithTremble(false);
            awesomeSpeedometer.speedTo((float) spped);

        }
    }

    // تحويل السرعة من متر في الدقيقة الى ميل في الساعة
    public double convertSpeesToMilesPerHourse(double speed) {

        return (speed * 60) / 1609.34;

    }


    // ميثود حساب معدل الأيض
    public double calculateMETABOLICEQUIVALENTS(double speedPerHours) {

        if (speedPerHours >= 2 && speedPerHours <= 3.4) {
            return 2.0;
        } else if (speedPerHours >= 3.5 && speedPerHours <= 4.9) {
            return 4.5;
        } else if (speedPerHours >= 5 && speedPerHours <= 6.9) {
            return 11.5;
        } else if (speedPerHours >= 7 && speedPerHours <= 10) {
            return 16.0;
        } else {
            return 0;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void resetSteps() {
        stepsTv.setOnClickListener(view -> Toast.makeText(getContext(), "Long tap to reset steps", Toast.LENGTH_SHORT).show());

        stepsTv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                previousTotalSteps = totalSteps;
                stepsTv.setText("0");
                distanceTv.setText("0.00 m");
                speedTv.setText("0.00 m/s");
                return false;
            }
        });
    }
}