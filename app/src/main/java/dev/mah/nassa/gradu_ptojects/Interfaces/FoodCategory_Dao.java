package dev.mah.nassa.gradu_ptojects.Interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import dev.mah.nassa.gradu_ptojects.Modles.FoodCategory;
import dev.mah.nassa.gradu_ptojects.Modles.UsersInfo;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

@Dao
public interface FoodCategory_Dao {
    @Insert
    Completable foodCategoryInsert (FoodCategory foodCategory);
    @Update
    Completable  foodCategoryUpdate (FoodCategory foodCategory);
    @Delete
    Completable  foodCategoryDelete (FoodCategory foodCategory);
    @Query("delete From FoodCategory")
    Completable deleteAllFoodCategory();


    @Query("Select * from FoodCategory")
    Observable<List<FoodCategory>> getAllFoodCategory();
    @Query("Select * from FoodCategory where uid=:uid")
    Observable<FoodCategory>  getFoodCategoryById(String uid);
    @Query("delete From FoodCategory Where uid =:uid")
    Completable deleteFoodCategoryUid (String uid);

    @Query("Select * from FoodCategory Where uid =:uid")
    Observable<FoodCategory> getAllByUidFoodCategory (String uid);
}
