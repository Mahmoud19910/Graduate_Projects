package dev.mah.nassa.gradu_ptojects.DataBase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import dev.mah.nassa.gradu_ptojects.Activityes.Home_Activity;
import dev.mah.nassa.gradu_ptojects.Activityes.UsersInformation_Activity;
import dev.mah.nassa.gradu_ptojects.Modles.UsersInfo;
import dev.mah.nassa.gradu_ptojects.Modles.Users_Health_Info;

public class FireStore_DataBase {
    static FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    //UsersInfo(Collection)
    public static void insertUsersInfo(UsersInfo usersInfo, Context context) {
        Toast.makeText(context, "PHOME NUMBER  =" + usersInfo.getPhone(), Toast.LENGTH_SHORT).show();

        Map<String, Object> mapArray = new HashMap<>();
        mapArray.put("uid", usersInfo.getUid());
        mapArray.put("name", usersInfo.getName());
        mapArray.put("phone", usersInfo.getPhone());
        mapArray.put("pass", usersInfo.getPass());
        mapArray.put("eage", usersInfo.getEage());
        mapArray.put("length", usersInfo.getLength());
        mapArray.put("weight", usersInfo.getWeight());
        mapArray.put("activityLevel", usersInfo.getActivityLeve());
        mapArray.put("gender", usersInfo.getGender());
        mapArray.put("photo", usersInfo.getPhoto());
        mapArray.put("email", usersInfo.getEmail());

        firestore.collection("UsersInfo").add(mapArray).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    //عند اضافة البيانات بنجاح يتم الانتقال الى واجهة الرئيسية
                    context.startActivity(new Intent(context, Home_Activity.class));
                    ((Activity) context).finish();
                } else {
                    Toast.makeText(context, "Added Failer", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //UsersHealthInfo(Collection)
    public static void insertUsersHealthInfo(Users_Health_Info usersHealthInfo, Context context) {
        Map<String , Object> mapUsersHealth=new HashMap<>();
        mapUsersHealth.put("id" , usersHealthInfo.getId());
        mapUsersHealth.put("userIdid" , usersHealthInfo.getUserId());
        mapUsersHealth.put("caloriesNumber" , usersHealthInfo.getCaloriesNumber());
        mapUsersHealth.put("waterDrink" , usersHealthInfo.getWaterDrink());
        mapUsersHealth.put("illness" , usersHealthInfo.isIllness());
        mapUsersHealth.put("medicineTime" , usersHealthInfo.getMedicineTime());
        firestore.collection("UsersHealthInfo").add(mapUsersHealth).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if(task.isSuccessful()){
                    Toast.makeText(context, "Added Health", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Fail Health", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


    // Check Sign In
    public static void signInMethods(String phone , String pass , Context context){
        firestore.collection("UsersInfo").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
               QuerySnapshot querySnapshot= task.getResult();
               for(DocumentSnapshot data : querySnapshot.getDocuments()){
                   if(data.get("phone").toString().equalsIgnoreCase(phone) && data.get("pass").toString().equalsIgnoreCase(pass)){
                       context.startActivity(new Intent(context ,Home_Activity.class));
                       ((Activity)context).finish();
                   }else {
                       Toast.makeText(context, "فشل التسجيل يرجى التحقق من صحة البيانات!!", Toast.LENGTH_SHORT).show();
                   }
               }
            }
        });
    }


}
