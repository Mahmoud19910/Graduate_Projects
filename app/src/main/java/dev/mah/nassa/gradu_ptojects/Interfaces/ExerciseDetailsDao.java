package dev.mah.nassa.gradu_ptojects.Interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import dev.mah.nassa.gradu_ptojects.Modles.Exercise_Details;
import dev.mah.nassa.gradu_ptojects.Modles.Users_Health_Info;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

@Dao
public interface ExerciseDetailsDao {

    @Insert
    Completable exersuseDetailsInsert (Exercise_Details exersuseDetails);
    @Update
    Completable  exersuseDetailsUpdate (Exercise_Details exersuseDetails);
    @Delete
    Completable  exersuseDetailsDelete (Exercise_Details exersuseDetails);

    @Query("delete From Exercise_Details")
    Completable deleteAllExersuseDetails();

    @Query("Select * from Exercise_Details")
    Observable<List<Exercise_Details>> getAllExercise_Details();

    @Query("Select * from Exercise_Details where uid=:uid")
    Observable<Exercise_Details> getExercise_DetailsById(String uid);

    @Query("delete From Exercise_Details Where uid =:uid")
    Completable deleteExercise_DetailsByUid (String uid);


}