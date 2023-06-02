package dev.mah.nassa.gradu_ptojects.Services_Firebase;


import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class CloudMessaging {


    // Users Chat ميثود لجلب رقم الجهاز المراد ارسال له الاشعار وتنفذ عند التسجيل و حفظها في
    public static void getToken(Context context , OnSuccessListener listener){
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String s) {
                listener.onSuccess(s);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Failer Get Token", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
