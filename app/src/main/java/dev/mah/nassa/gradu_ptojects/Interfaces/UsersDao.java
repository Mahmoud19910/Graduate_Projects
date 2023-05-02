package dev.mah.nassa.gradu_ptojects.Interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import dev.mah.nassa.gradu_ptojects.Modles.UsersInfo;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
@Dao
public interface UsersDao {
    @Insert
    Completable usersInsert (UsersInfo usersInfo);
    @Update
    Completable  usersUpdate (UsersInfo usersInfo);
    @Delete
    Completable  usersDelete (UsersInfo usersInfo);
    @Query("delete From usersTable")
    Completable deleteAllUsers();

    @Query("UPDATE usersTable SET pass = :newPass WHERE phone = :phoneNumber")
    Completable updatePassword(String newPass, String phoneNumber);

    @Query("Select * from usersTable")
    Observable<List<UsersInfo>> getAllUsers();
    @Query("Select * from usersTable where uid=:uid")
    Observable<UsersInfo>  getUsersById(String uid);
    @Query("delete From usersTable Where uid =:uid")
    Completable deleteByUid (String uid);

    @Query("Select * from usersTable Where uid =:uid")
    Observable<UsersInfo> getAllByUidUserId (String uid);
}