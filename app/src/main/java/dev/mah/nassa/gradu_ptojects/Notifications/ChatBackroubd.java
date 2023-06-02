package dev.mah.nassa.gradu_ptojects.Notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import dev.mah.nassa.gradu_ptojects.Activityes.MainActivity;
import dev.mah.nassa.gradu_ptojects.Modles.Notifications;
import dev.mah.nassa.gradu_ptojects.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatBackroubd extends Service {
    public int countNotification = 0;
    private static final String CHANNEL_ID = "ForegroundServiceChannel";
    private DatabaseReference databaseReference;
    private APIService apiService;



    @Override
    public void onCreate() {
        super.onCreate();

        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
            Notification notification = createNotification();
            startForeground(1, notification);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        databaseReference = FirebaseDatabase.getInstance().getReference("Notifications").child(loadUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                   Notifications notifications =(Notifications) dataSnapshot.getValue(Notifications.class);
                    sendNotifications(notifications.getToken() , notifications.getTitle() , notifications.getMessage());
                }

                // بعد ارسال الاشعار نقوم بحذف الاشعارات من قاعدة البيانات
                databaseReference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ChatBackroubd.this, "dleted", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ChatBackroubd.this, e.getMessage().toString()+"", Toast.LENGTH_SHORT).show();
                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        return START_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // Method to send FCM notification
    private void sendNotifications(String userToken , String title , String message) {

        Data data = new Data(title , message);
        NotificationSender notificationSender = new NotificationSender(data , userToken);
        apiService.sendNotification(notificationSender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {

                if(response.code() == 200){
                    if(response.body().success != 1){
                        Toast.makeText(getApplicationContext(), "Filed", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

            }
        });

    }


    // جلب رقم المعرف للمستخد
    private String loadUid() {
        SharedPreferences sharedPreferences = getSharedPreferences("saveUid", Context.MODE_PRIVATE);
        Toast.makeText(this, "load", Toast.LENGTH_SHORT).show();
        return sharedPreferences.getString("uid", "");
    }

    // Create the notification channel (required for Android Oreo and above)
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "CaloMate";
            String description = "صحة و لياقة";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    // Create the foreground service notification
    private Notification createNotification() {
        Intent notificationIntent = new Intent(this, MainActivity.class); // Replace with your main activity class
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("CaloMate")
                .setContentText("صحة عامة و لياقة بدنية ")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent);

        return builder.build();
    }

}
