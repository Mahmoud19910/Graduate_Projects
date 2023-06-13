package dev.mah.nassa.gradu_ptojects.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import dev.mah.nassa.gradu_ptojects.Interfaces.DoctorsDao;
import dev.mah.nassa.gradu_ptojects.Interfaces.ExerciseDetailsDao;
import dev.mah.nassa.gradu_ptojects.Interfaces.Exercise_Dao;
import dev.mah.nassa.gradu_ptojects.Interfaces.FoodCategory_Dao;
import dev.mah.nassa.gradu_ptojects.Interfaces.My_Meal_List_Dao;
import dev.mah.nassa.gradu_ptojects.Interfaces.UsersDao;
import dev.mah.nassa.gradu_ptojects.Interfaces.UsersHealthDao;
import dev.mah.nassa.gradu_ptojects.Modles.Doctor;
import dev.mah.nassa.gradu_ptojects.Modles.Exercise_Details;
import dev.mah.nassa.gradu_ptojects.Modles.FoodCategory;
import dev.mah.nassa.gradu_ptojects.Modles.My_Meal_List;
import dev.mah.nassa.gradu_ptojects.Modles.Sports_Exercises;
import dev.mah.nassa.gradu_ptojects.Modles.UsersInfo;
import dev.mah.nassa.gradu_ptojects.Modles.Users_Health_Info;

@Database(entities = {UsersInfo.class, Users_Health_Info.class , Exercise_Details.class , Doctor.class , FoodCategory.class , My_Meal_List.class , Sports_Exercises.class}, version = 15, exportSchema = false)
public abstract class AppDatabese extends RoomDatabase {
    private static AppDatabese instance;
    public abstract UsersDao usersDao();
    public abstract UsersHealthDao usersHealthDao();
    public abstract ExerciseDetailsDao exerciseDetailsDao();
    public abstract DoctorsDao doctorsDao();
    public abstract FoodCategory_Dao foodCategory_dao();
    public abstract My_Meal_List_Dao my_meal_list_dao();
    public abstract Exercise_Dao sports_exercises_dao();

    //singlton  تعود في كائن واحد من داتا بيز
    public static synchronized AppDatabese getInstance(Context context) {
        if(instance == null){
            instance=Room.databaseBuilder(context , AppDatabese.class , "User_data_base")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;

    }

}