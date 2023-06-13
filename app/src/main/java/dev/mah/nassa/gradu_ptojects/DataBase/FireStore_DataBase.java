package dev.mah.nassa.gradu_ptojects.DataBase;

import static dev.mah.nassa.gradu_ptojects.Activityes.Home_Activity.uid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
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
import dev.mah.nassa.gradu_ptojects.Modles.Exercise_Details;
import dev.mah.nassa.gradu_ptojects.Modles.FoodCategory;
import dev.mah.nassa.gradu_ptojects.Modles.My_Meal_List;
import dev.mah.nassa.gradu_ptojects.Modles.Sports_Exercises;
import dev.mah.nassa.gradu_ptojects.Modles.UsersInfo;
import dev.mah.nassa.gradu_ptojects.Modles.Users_Health_Info;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;

public class FireStore_DataBase {
    static FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    public static boolean isFindUser=false;

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
        mapArray.put("activityLevel", usersInfo.getActivityLevel());
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
        mapUsersHealth.put("userId" , usersHealthInfo.getUserId());
        mapUsersHealth.put("caloriesNumber" , usersHealthInfo.getCaloriesNumber());
        mapUsersHealth.put("waterDrink" , usersHealthInfo.getWaterDrink());
        mapUsersHealth.put("illness" , usersHealthInfo.isIllness());
        mapUsersHealth.put("medicineTime" , usersHealthInfo.getMedicineTime());
        mapUsersHealth.put("burnedCaloriesNumber" , usersHealthInfo.getBurnedCaloriesNumber());
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

    public static void getUsersHealthInfo(Context context , OnSuccessListener onSuccessListener) {
        firestore.collection("UsersHealthInfo").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot documentSnapshot : snapshotList){
                   Users_Health_Info usersHealthInfo = documentSnapshot.toObject(Users_Health_Info.class);
                   onSuccessListener.onSuccess(usersHealthInfo);
                }
            }
        });
    }

        //Food Meal(Collection)
    public static void insertMeal(My_Meal_List mealList, Context context) {
        firestore.collection("MyMeal").add(mealList).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
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
    //Food Meal(Collection)
    public static void insertMeal(FoodCategory foodCategory, Context context) {
        firestore.collection("MyMeal").add(foodCategory).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
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
        firestore.collection("UsersInfo").whereEqualTo("uid" , uid)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    // Iterate over the documents in the query result
                    for (QueryDocumentSnapshot document : querySnapshot) {
                        // Access the data of each document
                        String uid2 =  document.getString("uid");
                        String name =  document.getString("name");
                        String phone =  document.getString("phone");
                        String pass =  document.getString("pass");
                        String eage =  document.getString("eage");
                        String length =  document.getString("length");
                        String weight =  document.getString("weight");
                        String activityLevel =  document.getString("activityLevel");
                        String gender =  document.getString("gender");
                        String photo =  document.getString("photo");
                        String email =  document.getString("email");
                        UsersInfo usersInfo =   new UsersInfo(uid2 , name , phone , pass , eage , length , weight,activityLevel,gender,photo,email);
                        onSuccessListener.onSuccess(usersInfo);
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle any errors that occurred during the query
                    System.out.println("Error getting documents: " + e.getMessage());
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
                            String metValue =(String) documentSnapshot.getData().get("metValue");
                            list.add(new Sports_Exercises(id , name , description , imGif,metValue));
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

    public static void update(String collection , String id , UsersInfo usersInfo){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection(collection);

// Create a query to find documents with name equal to "Mahmoud"
        Query query = collectionRef.whereEqualTo("uid", id);

// Execute the query
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        // Update each matching document
                        document.getReference().set(usersInfo)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            // Document updated successfully
                                        } else {
                                            // Error updating document
                                        }
                                    }
                                });
                    }
                } else {
                    // Error getting documents
                }
            }
        });
    }

    public static void insertOrUpdateExerciseDetails(Exercise_Details exercise_details , Context context){
        firestore.collection("ExerciseDetails").add(exercise_details).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
               task.addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       Toast.makeText(context, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                   }
               });
            }
        });

    }

    public static void getAllExerciseDetails(OnSuccessListener onSuccessListener){
        firestore.collection("ExerciseDetails").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

               List<DocumentSnapshot> snapshotList =  queryDocumentSnapshots.getDocuments();
               for(DocumentSnapshot documentSnapshot : snapshotList){

                   Exercise_Details exerciseDetails = documentSnapshot.toObject(Exercise_Details.class);
                   onSuccessListener.onSuccess(exerciseDetails);
               }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    //inside this method we are passing our updated values
    //تحتاج الميثود ثلاث مدخلات
    //1 - اسم الكولكشن الموجود في الفاير ستور
    //2- ال id الخاص بالاوبجكت
    //الاوبجكت بعد تعديله
    public static void updateObject(String collection,String uid,Object object) {

            CollectionReference collectionRef = firestore.collection(collection);

        Query query;
            // Create a query to find documents with uid equal to "uid"
        if(collection.equals("UsersHealthInfo")){
             query= collectionRef.whereEqualTo("userId", uid);
        }else {
            query = collectionRef.whereEqualTo("uid", uid);

        }

            // Execute the query
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            // Update each matching document
                            document.getReference().set(object)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                // Document updated successfully
                                                Log.d("FirestoreUpdate", "Document updated successfully!");
                                            } else {
                                                // Error updating document
                                                Log.e("FirestoreUpdate", "Error updating document", task.getException());
                                            }
                                        }
                                    });
                        }
                    } else {
                        // Error getting documents
                        Log.e("FirestoreQuery", "Error getting documents", task.getException());
                    }
                }
            });
        }

    public static void getAllData (Context context, String collection ,String uid ,OnSuccessListener onSuccessListener){

        ArrayList<My_Meal_List> foodCategories = new ArrayList<>();
        firestore.collection(collection).whereEqualTo("uid" , uid).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {

                                My_Meal_List c = d.toObject(My_Meal_List.class);

                                c.setId(d.getId());

                                foodCategories.add(c);
                                onSuccessListener.onSuccess(foodCategories);
                            }
                        } else {
                            // if the snapshot is empty we are displaying a toast message.
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // if we do not get any data or any error we are displaying
                        // a toast message that we do not get any data
                    }
                });

    }
    //FoodCategory_Fragment food
    public static void getAllDatas (Context context, String collection ,OnSuccessListener onSuccessListener){

        ArrayList<FoodCategory> foodCategories = new ArrayList<>();
        firestore.collection(collection).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // after getting the data we are calling on success method
                        // and inside this method we are checking if the received
                        // query snapshot is empty or not.
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // if the snapshot is not empty we are
                            // hiding our progress bar and adding
                            // our data in a list.
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                // after getting this list we are passing
                                // that list to our object class.
                                FoodCategory c = d.toObject(FoodCategory.class);

                                // below is the updated line of code which we have to
                                // add to pass the document id inside our modal class.
                                // we are setting our document id with d.getId() method
                                c.setId(d.getId());

                                // and we will pass this object class
                                // inside our arraylist which we have
                                // created for recycler view.
                                foodCategories.add(c);
                                onSuccessListener.onSuccess(foodCategories);
                            }
                        } else {
                            // if the snapshot is empty we are displaying a toast message.
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // if we do not get any data or any error we are displaying
                        // a toast message that we do not get any data
                    }
                });

    }


}


