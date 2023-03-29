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
public interface UsersDao {
    @Insert
    Completable UsersInsert (UsersInfo usersInfo);
    @Update
    Completable  UsersUpdate (UsersInfo usersInfo);
    @Delete
    Completable  UsersDelete (UsersInfo usersInfo);
    @Query("delete From usersTable")
    Completable deleteAllUsers();
    @Query("Select * from usersTable")
    Observable<List<UsersInfo>> getAllUsers();
    @Query("Select * from usersTable where uid=:uid")
    Observable<UsersInfo>  getUsersById(String uid);
    @Query("delete From usersTable Where uid =:uid")
    Completable deleteByUid (String uid);

    @Query("Select *from usersTable,usersHealthTable  Where uid =:uid  and userId =:uid")
    Observable<UsersInfo> getAllByUidUserId (String uid);
}
