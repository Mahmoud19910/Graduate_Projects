package dev.mah.nassa.gradu_ptojects.Constants;

import android.app.Activity;
import android.content.Context;
import android.provider.ContactsContract;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import dev.mah.nassa.gradu_ptojects.Interfaces.TimerListener;

public class StopwatchTimer {

    Timer timer;
    public TimerTask timerTask;
    public Double time = 0.0;
    public TimerListener listener;


    // Constructor
    public StopwatchTimer(){
        timer = new Timer();
    }
    // Constructor
    public StopwatchTimer(TimerListener listener){
        this.listener=listener;
        timer = new Timer();
    }

    // تشغيل المؤقت
    public void startTimer() {
        Activity activity = new Activity();
        timerTask = new TimerTask() {
            @Override
            public void run()
            {
                time++;
                activity.runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        listener.getTimer(getTimerText());


                    }
                });
            }

        };
        timer.scheduleAtFixedRate(timerTask, 0 ,1000);
    }


    // تشغيل المؤقت
    public void startTimer(TextView textView) {
        Activity activity = new Activity();
        timerTask = new TimerTask() {
            @Override
            public void run()
            {
                time++;
                activity.runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        textView.setText(getTimerText());

                    }
                });
            }

        };
        timer.scheduleAtFixedRate(timerTask, 0 ,1000);
    }

    public void startTimer(OnSuccessListener listener) {
        Activity activity = new Activity();
        timerTask = new TimerTask() {
            @Override
            public void run()
            {
                time++;
                activity.runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        listener.onSuccess(getTimerText());
                    }
                });
            }

        };
        timer.scheduleAtFixedRate(timerTask, 0 ,1000);
    }


    public String getTimerText() {
        int rounded = (int) Math.round(time);

        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = ((rounded % 86400) / 3600);

        return formatTime(seconds, minutes, hours);
    }

    private String formatTime(int seconds, int minutes, int hours)
    {
        return String.format("%02d",hours) + " : " + String.format("%02d",minutes) + " : " + String.format("%02d",seconds);
    }

    public double getTimeByHours(){
        double hours = time / 3600.0;
        DecimalFormat decimalFormat = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.ENGLISH));
        return Double.parseDouble(decimalFormat.format(hours));

    }

    public double getTimeByMinutes(){
        double min = time / 60.0;
        DecimalFormat decimalFormat = new DecimalFormat("#.##" , new DecimalFormatSymbols(Locale.ENGLISH));
        return  Double.parseDouble(decimalFormat.format(min));

    }

    public void finishTimer() {
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
            time = 0.0;
        }
    }
}
