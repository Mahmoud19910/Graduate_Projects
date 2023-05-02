package dev.mah.nassa.gradu_ptojects.Constants;

import android.app.Activity;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

public class StopwatchTimer {

    Timer timer;
    public TimerTask timerTask;
    public Double time = 0.0;


    // Constructor
    public StopwatchTimer(){
        timer = new Timer();

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
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return  Double.parseDouble(decimalFormat.format(hours));

    }

    public double getTimeByMinutes(){
        double min = time / 60.0;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return  Double.parseDouble(decimalFormat.format(min));

    }
}
