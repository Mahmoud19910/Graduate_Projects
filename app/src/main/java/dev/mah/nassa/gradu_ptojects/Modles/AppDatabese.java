package dev.mah.nassa.gradu_ptojects.Modles;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {UsersInfo.class, Users_Health_Info.class}, version = 3, exportSchema = false)
public abstract class AppDatabese extends RoomDatabase {
    private static AppDatabese instance;
    public abstract UsersDao usersDao();
    public abstract UsersHealthDao usersHealthDao();

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