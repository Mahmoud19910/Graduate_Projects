package dev.mah.nassa.gradu_ptojects.DataBase;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dev.mah.nassa.gradu_ptojects.Activityes.Home_Activity;
import dev.mah.nassa.gradu_ptojects.Adapters.DoctorAdapter_Horisintal;
import dev.mah.nassa.gradu_ptojects.Adapters.DoctorsAdapter;
import dev.mah.nassa.gradu_ptojects.Modles.Doctor;
import dev.mah.nassa.gradu_ptojects.Modles.Users_Chat;

public class RealTime_DataBase {

    // حفظ المستخدم في قاعدة بيانات الخاصة بالشات للمستخدمين
    public static void addUsersToRealTime(Context context , String id  , Users_Chat usersChat){
       DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("UsersChat");
       databaseReference.child(id).setValue(usersChat).addOnSuccessListener(new OnSuccessListener<Void>() {
           @Override
           public void onSuccess(Void unused) {
               Toast.makeText(context, "Success Add To RealTime", Toast.LENGTH_SHORT).show();
           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               Toast.makeText(context, "Failer Add To RealTime", Toast.LENGTH_SHORT).show();

           }
       });
    }

    // ميثود لتعديل قيمة الجلسة هل متصل أم لا
    public static void updateSession(String id , boolean isSesion , Context context) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("UsersChat");

        databaseReference.child(id).child("session").setValue(isSesion)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        // Update successful
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Update failed
                        Toast.makeText(context, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //جلب جميع الدكاترة من قاعدة البيانات
    public static void getAllDoctorsFromRealTime(Context context , DoctorAdapter_Horisintal adapter , OnSuccessListener onSuccessListener){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Admin");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adapter.clearAdapter();
                for(DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                    Doctor doctor = dataSnapshot1.getValue(Doctor.class);
                    onSuccessListener.onSuccess(doctor);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(context, error.getMessage().toString()+"", Toast.LENGTH_SHORT).show();
            }
        });
    }


    //جلب جميع الدكاترة من قاعدة البيانات
    public static void getAllDoctorsFromRealTime(Context context , DoctorsAdapter adapter , OnSuccessListener onSuccessListener){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Admin");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adapter.clearAdapter();
                for(DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                    Doctor doctor = dataSnapshot1.getValue(Doctor.class);
                    onSuccessListener.onSuccess(doctor);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(context, error.getMessage().toString()+"", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //  جلب جميع الدكاترة من قاعدة البيانات من دون أدابتر
    public static void getAllDoctorsFromRealTime(Context context ,  OnSuccessListener onSuccessListener){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Admin");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                    Doctor doctor = dataSnapshot1.getValue(Doctor.class);
                    onSuccessListener.onSuccess(doctor);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(context, error.getMessage().toString()+"", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
