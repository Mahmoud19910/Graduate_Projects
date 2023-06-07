package dev.mah.nassa.gradu_ptojects.Modles;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // عند وصول الساعة الى الوقت الذي تم تحديده من قبل المستخدم
        // تشغيل نغمة تذكير بتناول الدواء
        Toast.makeText(context, "وقت الدواء", Toast.LENGTH_LONG).show();
    }
}
