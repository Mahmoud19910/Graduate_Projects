package dev.mah.nassa.gradu_ptojects.Constants;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import dev.mah.nassa.gradu_ptojects.Activityes.FoodSection_Activity;
import dev.mah.nassa.gradu_ptojects.Activityes.Home_Activity;
import dev.mah.nassa.gradu_ptojects.Adapters.ParentFood_Adapter;
import dev.mah.nassa.gradu_ptojects.DataBase.FireStore_DataBase;
import dev.mah.nassa.gradu_ptojects.DataBase.RealTime_DataBase;
import dev.mah.nassa.gradu_ptojects.Fragments.Training_Fragment;
import dev.mah.nassa.gradu_ptojects.MVVM.Doctors_MVVM;
import dev.mah.nassa.gradu_ptojects.MVVM.ExersiseDetails_MVVM;
import dev.mah.nassa.gradu_ptojects.MVVM.FireStore_MVVM;
import dev.mah.nassa.gradu_ptojects.MVVM.FoodCategory_MVVM;
import dev.mah.nassa.gradu_ptojects.MVVM.My_Meal_MVVM;
import dev.mah.nassa.gradu_ptojects.MVVM.SportExercise_MVVM;
import dev.mah.nassa.gradu_ptojects.MVVM.UsersHealthInfoViewModel;
import dev.mah.nassa.gradu_ptojects.MVVM.UsersViewModel;
import dev.mah.nassa.gradu_ptojects.Modles.Doctor;
import dev.mah.nassa.gradu_ptojects.Modles.Exercise_Details;
import dev.mah.nassa.gradu_ptojects.Modles.FoodCategory;
import dev.mah.nassa.gradu_ptojects.Modles.My_Meal_List;
import dev.mah.nassa.gradu_ptojects.Modles.Sports_Exercises;
import dev.mah.nassa.gradu_ptojects.Modles.UsersInfo;
import dev.mah.nassa.gradu_ptojects.Modles.Users_Health_Info;

public class CheckRoomIsEmpty {
    private Context context;
    private FoodCategory_MVVM foodCategoryMvvm;
    private UsersHealthInfoViewModel usersHealthInfoViewModel;
    private UsersViewModel usersViewModel;
    private My_Meal_MVVM my_meal_mvvm;
    private Doctors_MVVM doctors_mvvm;
    private ExersiseDetails_MVVM exersiseDetails_mvvm;
    private SportExercise_MVVM sportExerciseMvvm;
    private FireStore_MVVM fireStore_mvvm;

    private List<Sports_Exercises> listSportLocal;

    public CheckRoomIsEmpty(Context context) {
        this.context = context;
        foodCategoryMvvm = ViewModelProviders.of((Home_Activity) context).get(FoodCategory_MVVM.class);
        usersHealthInfoViewModel = ViewModelProviders.of((Home_Activity) context).get(UsersHealthInfoViewModel.class);
        usersViewModel = ViewModelProviders.of((Home_Activity) context).get(UsersViewModel.class);
        my_meal_mvvm = ViewModelProviders.of((Home_Activity) context).get(My_Meal_MVVM.class);
        doctors_mvvm = ViewModelProviders.of((Home_Activity) context).get(Doctors_MVVM.class);
        exersiseDetails_mvvm = ViewModelProviders.of((Home_Activity) context).get(ExersiseDetails_MVVM.class);
        sportExerciseMvvm = ViewModelProviders.of((Home_Activity) context).get(SportExercise_MVVM.class);
        fireStore_mvvm= ViewModelProviders.of((Home_Activity)context).get(FireStore_MVVM.class);


    }

//    public void checkFoodCategoryBase() {
//        foodCategoryMvvm.getAllFoodCategory().observe((Home_Activity) context, new Observer<List<FoodCategory>>() {
//            @Override
//            public void onChanged(List<FoodCategory> foodCategoryList) {
//                if (foodCategoryList.isEmpty() || foodCategoryList == null) {
//
//
//                    FireStore_DataBase.getAllDatas(context, "Food_Category", new OnSuccessListener() {
//                        @Override
//                        public void onSuccess(Object o) {
//                            ArrayList<FoodCategory> foodCategories = (ArrayList<FoodCategory>) o;
//                            for (FoodCategory foodCategory : foodCategories) {
//                                foodCategoryMvvm.insertFoodCategory(foodCategory);
//
//                            }
//
//                        }
//                    });
//
//
//                }
//            }
//        });
//    }

    public void checkFoodCategoryBase() {
        foodCategoryMvvm.getAllFoodCategory().observe((Home_Activity) context, new Observer<List<FoodCategory>>() {
            @Override
            public void onChanged(List<FoodCategory> foodCategoryList) {
                if (foodCategoryList.isEmpty() || foodCategoryList == null) {

                    fireStore_mvvm.getAllFood(new OnSuccessListener<ArrayList<FoodCategory>>() {
                        @Override
                        public void onSuccess(ArrayList<FoodCategory> foodCategories) {
                            for (FoodCategory foodCategory : foodCategories) {
                                foodCategoryMvvm.insertFoodCategory(foodCategory);

                            }
                        }
                    });



                }
            }
        });
    }

    public void checkUsersHealthBase(String uid) {

        usersHealthInfoViewModel.getAllUsersHealth().observe((Home_Activity) context, new Observer<List<Users_Health_Info>>() {
            @Override
            public void onChanged(List<Users_Health_Info> users_health_infos) {
                if (users_health_infos == null || users_health_infos.isEmpty()) {
                    FireStore_DataBase.getUsersHealthInfo(context, new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            Users_Health_Info usersHealthInfo = (Users_Health_Info) o;
                            if (usersHealthInfo.getUserId().equals(uid)) {
                                usersHealthInfoViewModel.insertUsersHealth(usersHealthInfo);
                            }
                        }
                    });
                }
            }
        });


    }

    public void checkUsersInfoBase(String uid) {

        usersViewModel.getAllUsers().observe((Home_Activity) context, new Observer<List<UsersInfo>>() {
            @Override
            public void onChanged(List<UsersInfo> usersInfos) {

                if (usersInfos.isEmpty() || usersInfos == null) {

                    FireStore_DataBase.getAllUsersInfo(new OnSuccessListener<ArrayList<UsersInfo>>() {
                        @Override
                        public void onSuccess(ArrayList<UsersInfo> usersInfos) {
                            for (UsersInfo usersInfo : usersInfos) {
                                if (usersInfo.getUid().equals(uid)) {
                                    usersViewModel.insertUsers(usersInfo);
                                }
                            }


                        }
                    }, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });


                }
            }
        });


    }

    public void checkMyMealBase(String uid) {

        my_meal_mvvm.getMy_Meal_ListByUid(uid).observe((Home_Activity) context, new Observer<My_Meal_List>() {
            @Override
            public void onChanged(My_Meal_List my_meal_list) {
                if (my_meal_list == null) {

                    FireStore_DataBase.getAllData(context, "MyMeal", uid, new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            ArrayList<My_Meal_List> mealLists = (ArrayList<My_Meal_List>) o;

                            for (My_Meal_List mealList : mealLists) {

                                if (mealList.getUid().equals(uid)) {
                                    my_meal_mvvm.insertMy_Meal_List(mealList);

                                }
                            }

                        }
                    });

                }
            }
        });

    }

    public void checkDoctorsBase() {
        doctors_mvvm.getAllDoctors().observe((Home_Activity) context, new Observer<List<Doctor>>() {
            @Override
            public void onChanged(List<Doctor> doctorList) {

                if (doctorList.isEmpty() || doctorList == null) {

                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            RealTime_DataBase.getAllDoctorsFromRealTime(context, new OnSuccessListener() {
                                @Override
                                public void onSuccess(Object o) {
                                    doctors_mvvm.insertDoctors((Doctor) o);
                                }
                            });
                        }
                    });
                    thread.start();


                }
            }
        });
    }


    public void checkExerxciseDetailsBase(String uid){
        exersiseDetails_mvvm.getAllExersiseDetails().observe((Home_Activity) context, new Observer<List<Exercise_Details>>() {
            @Override
            public void onChanged(List<Exercise_Details> exercise_details) {

                if(exercise_details.isEmpty() || exercise_details == null){

                    FireStore_DataBase.getAllExerciseDetails(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                           Exercise_Details exerciseDetails = (Exercise_Details) o;
                            if(exerciseDetails.getUid().equals(uid)){
                                exersiseDetails_mvvm.insertExersiseDetails(exerciseDetails);
                            }
                        }
                    });
                }
            }
        });
    }





    public void checkSportExerciseBase() {
        sportExerciseMvvm.getAllSports_Exercises().observe((Home_Activity) context, new Observer<List<Sports_Exercises>>() {
            @Override
            public void onChanged(List<Sports_Exercises> sports_exercises) {

                listSportLocal = sports_exercises;
                    fireStore_mvvm.getAllSportsExercises(new OnSuccessListener<List<Sports_Exercises>>() {
                        @Override
                        public void onSuccess(List<Sports_Exercises> sports_exercises) {

                            if(sports_exercises.size() != listSportLocal.size()){
                                sportExerciseMvvm.deleteAllExercise(context);
                                for(Sports_Exercises sports_exercises1 : sports_exercises){
                                    sportExerciseMvvm.insertSportExercise(sports_exercises1);
                                }
                            }

                        }
                    });

            }
        });
    }


}
