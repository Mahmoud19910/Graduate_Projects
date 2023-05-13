package dev.mah.nassa.gradu_ptojects.DataBase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.mah.nassa.gradu_ptojects.Activityes.Home_Activity;
import dev.mah.nassa.gradu_ptojects.Constants.SharedFunctions;
import dev.mah.nassa.gradu_ptojects.Modles.Sports_Exercises;
import dev.mah.nassa.gradu_ptojects.Modles.UsersInfo;
import dev.mah.nassa.gradu_ptojects.Modles.Users_Health_Info;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;

public class FireStore_DataBase {
    static FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    public static boolean isFindUser=false;
    public static boolean isFind=false;


    //UsersInfo(Collection)
    public static void insertUsersInfo(UsersInfo usersInfo, Context context) {

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
                    //لجلب البيانات الخاصة بكل مستخدم uid ارسال فيمة
                    //عند اضافة البيانات بنجاح يتم الانتقال الى واجهة الرئيسية
                    Intent intent = new Intent(context , Home_Activity.class);
                    intent.putExtra("uid" ,usersInfo.getUid());
                    context.startActivity(intent);
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
        mapUsersHealth.put("burnedCalories" , usersHealthInfo.getBurnedCaloriesNumber());
        mapUsersHealth.put("caloriesGained" , usersHealthInfo.getCaloriesGained());

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

    // Get All Data(Users Info)
    public static void getAllUsersInfo(OnSuccessListener<ArrayList<UsersInfo>> onsuccess , OnFailureListener onFailureListener){
        ArrayList<UsersInfo> usersInfoArrayList =new ArrayList<>();
        firestore.collection("UsersInfo").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
               List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
               for(DocumentSnapshot documentSnapshot : snapshotList){
                   Map<String , Object> map = documentSnapshot.getData();
                   String uid = (String) map.get("uid");
                   String name = (String) map.get("name");
                   String phone =(String) map.get("phone");
                   String pass = (String)map.get("pass");
                   String eage = (String)map.get("eage");
                   String length =(String) map.get("length");
                   String weight =(String) map.get("weight");
                   String activityLevel = (String)map.get("activityLevel");
                   String gender = (String)map.get("gender");
                   String photo = (String)map.get("photo");
                   String email = (String)map.get("email");
                   usersInfoArrayList.add(new UsersInfo(uid , name , phone , pass , eage , length , weight,activityLevel,gender,photo,email));

               }
                onsuccess.onSuccess(usersInfoArrayList);
            }
        })
                .addOnFailureListener(onFailureListener);
    }

    public static void getUsersById(String uid , Context context , OnSuccessListener onSuccessListener){
        firestore.collection("UsersInfo")
                .whereEqualTo("uid",uid)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            if(task.getResult()!=null){
                                List<DocumentSnapshot> snapshotList = task.getResult().getDocuments();
                                for(DocumentSnapshot documentSnapshot : snapshotList){
                                  UsersInfo usersInfo =   documentSnapshot.toObject(UsersInfo.class);
                                  if(usersInfo!=null){
                                      onSuccessListener.onSuccess(usersInfo);
                                  }
                                }

                            }

                        }else{
                            Toast.makeText(context, task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    // Check Sign In
    public static void signInMethods(String phone , String pass , Context context , OnSuccessListener<Boolean> onSuccessListener){
        firestore.collection("UsersInfo").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
               QuerySnapshot querySnapshot= task.getResult();
               for(DocumentSnapshot data : querySnapshot.getDocuments()){
                   if(data.get("phone").toString().equalsIgnoreCase(phone) && data.get("pass").toString().equalsIgnoreCase(pass)){

                       Intent intent = new Intent(context , Home_Activity.class);
                       intent.putExtra("uid" , data.get("uid").toString());
                       context.startActivity(intent);
                       ((Activity)context).finish();
                       isFindUser=true;
                      onSuccessListener.onSuccess(isFindUser);
                   }
            }
               if(!isFindUser){
                   SharedFunctions.dismissDialog();
                   Toast.makeText(context, "فشل التسجيل يرجى التحقق من صحة البيانات!!", Toast.LENGTH_SHORT).show();
               }
            }
        });

    }

    public static void updatePass(String newPass , String phoneNum , Context context){

        Query query = firestore.collection("UsersInfo").whereEqualTo("phone",phoneNum);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    QuerySnapshot querySnapshot = task.getResult();
                    for(QueryDocumentSnapshot document : querySnapshot){
                        document.getReference().update("pass" , newPass);

                    }

                }else{
                    Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    // Get All Sports Exercices
    public static Observable<List<Sports_Exercises>> getAllExercices(Context context){
        return Observable.create(new ObservableOnSubscribe<List<Sports_Exercises>>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<List<Sports_Exercises>> emitter) throws Throwable {

                firestore.collection("exercises").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> snapshotList =  queryDocumentSnapshots.getDocuments();
                        ArrayList<Sports_Exercises> list = new ArrayList<>();

                        for(DocumentSnapshot documentSnapshot : snapshotList){
                            String description =(String) documentSnapshot.getData().get("descriptionMeal");
                            String name = (String) documentSnapshot.getData().get("exerciseName");
                            String id = (String) documentSnapshot.getData().get("id");
                            String imGif =(String) documentSnapshot.getData().get("imGif");
                            list.add(new Sports_Exercises(id , name , description , imGif));
                        }
                        emitter.onNext(list);
                        emitter.onComplete();
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Failer", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
    }


}
