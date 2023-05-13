package dev.mah.nassa.gradu_ptojects.Interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import dev.mah.nassa.gradu_ptojects.Modles.Users_Health_Info;
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

    @Query("Select * from usersHealthTable where userId=:uid")
    Observable<Users_Health_Info> getUsersHealthById(String uid);

    @Query("delete From usersHealthTable Where userId =:uid")
    Completable deleteUsersHealthByUid (String uid);

    @Query("UPDATE usersHealthTable SET burnedCaloriesNumber = :burnedCal WHERE userId = :uid")
    Completable updateCaloriesById(String uid, double  burnedCal);
}