package dev.mah.nassa.gradu_ptojects.Modles;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

@Dao
public interface UsersHealthDao {

    @Insert
    Completable usersHealthInsert (Users_Health_Info usersHealthInfo);
    @Update
    Completable  usersHealthUpdate (Users_Health_Info usersHealthInfo);
    @Delete
    Completable  usersHealthDelete (Users_Health_Info usersHealthInfo);

    @Query("delete From usersHealthTable")
    Completable deleteAllUsersHealth();

    @Query("Select * from usersHealthTable")
    Observable<List<Users_Health_Info>> getAllUsersHealth();

    @Query("Select * from usersHealthTable where id=:id")
    Observable<Users_Health_Info> getUsersHealthById(int id);

    @Query("delete From usersHealthTable Where id =:id")
    Completable deleteUsersHealthByUid (int id);
}
