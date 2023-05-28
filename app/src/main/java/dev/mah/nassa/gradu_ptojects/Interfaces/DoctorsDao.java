package dev.mah.nassa.gradu_ptojects.Interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import dev.mah.nassa.gradu_ptojects.Modles.Doctor;
import dev.mah.nassa.gradu_ptojects.Modles.UsersInfo;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

@Dao
public interface DoctorsDao {
    @Insert
    Completable doctorInsert(Doctor doctor);
    @Update
    Completable  doctorUpdate (Doctor doctor);

    @Delete
    Completable  doctorDelete (Doctor doctor);

    @Query("delete From Doctor")
    Completable deleteAllDoctor();


    @Query("Select * from Doctor")
    Observable<List<Doctor>> getAllDoctor();

    @Query("Select * from Doctor where id=:id")
    Observable<Doctor>  getDoctorById(String id);

    @Query("delete From Doctor Where id =:id")
    Completable deleteById (String id);

}
