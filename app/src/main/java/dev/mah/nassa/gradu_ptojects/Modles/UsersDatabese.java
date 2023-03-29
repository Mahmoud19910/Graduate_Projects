package dev.mah.nassa.gradu_ptojects.Modles;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {UsersInfo.class, Users_Health_Info.class}, version = 1, exportSchema = false)
public abstract class UsersDatabese extends RoomDatabase {
    private static UsersDatabese instance;
    public abstract UsersDao usersDao();
    public abstract UsersHealthDao usersHealthDao();
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    //singlton  تعود في كائن واحد من داتا بيز
    public static synchronized UsersDatabese getInstance(Context context) {
        if(instance == null){
            instance=Room.databaseBuilder(context , UsersDatabese.class , "User_data_base")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;

    }
    private static Callback callback = new Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDataAsyncTask(instance).execute();
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

    //لأظافة بيانات في داتا بيز
    private static class PopulateDataAsyncTask extends AsyncTask<Void,Void,Void> {
        private UsersDao usersDao;
        PopulateDataAsyncTask(UsersDatabese db){
            usersDao = db.usersDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            //هان بتقدر تظيف بشكل مباشر في داتا بيز فقط انشأ اوبجكت وعبي بياناتو
            //            wordsDao.insert(new Words("book","sami","asd"));
            return null;
        }
    }
}
