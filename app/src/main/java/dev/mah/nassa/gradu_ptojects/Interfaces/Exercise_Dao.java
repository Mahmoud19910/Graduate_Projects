package dev.mah.nassa.gradu_ptojects.Interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import dev.mah.nassa.gradu_ptojects.Modles.Doctor;
import dev.mah.nassa.gradu_ptojects.Modles.Sports_Exercises;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

@Dao
public interface Exercise_Dao {
    @Insert
    Completable sports_ExercisesInsert(Sports_Exercises sports_Exercises);

    @Query("Select * from Sports_Exercises")
    Observable<List<Sports_Exercises>> getAllSports_Exercises();

    @Query("Select * from Sports_Exercises where id=:id")
    Observable<Sports_Exercises>  getSports_ExercisesById(String id);
}
