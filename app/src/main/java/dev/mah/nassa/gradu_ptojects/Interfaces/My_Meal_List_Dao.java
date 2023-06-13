package dev.mah.nassa.gradu_ptojects.Interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import dev.mah.nassa.gradu_ptojects.Modles.FoodCategory;
import dev.mah.nassa.gradu_ptojects.Modles.My_Meal_List;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
@Dao
public interface My_Meal_List_Dao {

    @Insert
    Completable my_Meal_ListInsert (My_Meal_List my_Meal_List);
    @Update
    Completable  my_Meal_ListUpdate (My_Meal_List my_Meal_List);
    @Delete
    Completable  my_Meal_ListDelete (My_Meal_List my_Meal_List);
    @Query("delete From My_Meal_List")
    Completable deleteAllMy_Meal_List();


    @Query("Select * from My_Meal_List")
    Observable<List<My_Meal_List>> getAllMy_Meal_List();

    @Query("Select * from My_Meal_List where uid=:uid AND date=:date")
    Observable<List<My_Meal_List>>  getMy_Meal_ListByIdAndDate(String uid , String date);

    @Query("Select * from My_Meal_List where uid=:uid")
    Observable<My_Meal_List>  getMy_Meal_ListById(String uid);

    @Query("delete From My_Meal_List Where uid =:uid")
    Completable deleteMy_Meal_ListUid (String uid);

    @Query("Select * from My_Meal_List Where uid =:uid")
    Observable<My_Meal_List> getAllByUidMy_Meal_List (String uid);
}
