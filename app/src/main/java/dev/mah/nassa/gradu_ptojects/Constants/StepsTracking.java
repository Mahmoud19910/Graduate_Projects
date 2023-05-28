package dev.mah.nassa.gradu_ptojects.Constants;

import android.app.Application;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.lifecycle.ViewModelStoreOwner;

import com.google.common.base.Stopwatch;

import dev.mah.nassa.gradu_ptojects.MVVM.Walking_MVVM;

public class StepsTracking implements SensorEventListener {
    private StopwatchTimer timer;
    private Walking_MVVM walkingMvvm;
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
    private String timeAtMomment;

    public StepsTracking(Context context){
        timeAtMomment = SharedFunctions.getTimeAtTheMoment();
        timer=new StopwatchTimer();
        walkingMvvm = new ViewModelProvider((ViewModelStoreOwner) context.getApplicationContext(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(((Application) context.getApplicationContext())))
                .get(Walking_MVVM.class);
        sensorManager=(SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

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
            double metaValue = calculateMETABOLICEQUIVALENTS(convertSpeesToMilesPerHourse(spped));

            walkingMvvm.setAllData(String.format("%.2f",distance),String.format("%.2f", spped) ,String.format("%.2f", (metaValue*70*timer.getTimeByHours())) , Integer.toString(currentSteps) , timeAtMomment);


        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

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
//        Toast.makeText(getApplicationContext(), timer.getTimeByHours() + "", Toast.LENGTH_LONG).show();
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



}
