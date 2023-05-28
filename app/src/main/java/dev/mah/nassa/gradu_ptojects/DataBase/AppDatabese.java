package dev.mah.nassa.gradu_ptojects.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import dev.mah.nassa.gradu_ptojects.Interfaces.DoctorsDao;
import dev.mah.nassa.gradu_ptojects.Interfaces.ExerciseDetailsDao;
import dev.mah.nassa.gradu_ptojects.Interfaces.UsersDao;
import dev.mah.nassa.gradu_ptojects.Interfaces.UsersHealthDao;
import dev.mah.nassa.gradu_ptojects.Modles.Doctor;
import dev.mah.nassa.gradu_ptojects.Modles.Exercise_Details;
import dev.mah.nassa.gradu_ptojects.Modles.UsersInfo;
import dev.mah.nassa.gradu_ptojects.Modles.Users_Health_Info;

@Database(entities = {UsersInfo.class, Users_Health_Info.class , Exercise_Details.class , Doctor.class}, version = 10, exportSchema = false)
public abstract class AppDatabese extends RoomDatabase {
    private static AppDatabese instance;
    public abstract UsersDao usersDao();
    public abstract UsersHealthDao usersHealthDao();
    public abstract ExerciseDetailsDao exerciseDetailsDao();
    public abstract DoctorsDao doctorsDao();

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