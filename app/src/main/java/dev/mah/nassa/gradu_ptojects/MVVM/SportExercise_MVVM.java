package dev.mah.nassa.gradu_ptojects.MVVM;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import dev.mah.nassa.gradu_ptojects.DataBase.AppDatabese;
import dev.mah.nassa.gradu_ptojects.Modles.Doctor;
import dev.mah.nassa.gradu_ptojects.Modles.Sports_Exercises;
import dev.mah.nassa.gradu_ptojects.Modles.UsersInfo;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SportExercise_MVVM extends AndroidViewModel {
    MutableLiveData<List<Sports_Exercises>> mutableLiveDataSports_Exercises = new MutableLiveData<>();
    MutableLiveData<Sports_Exercises> mutableLData = new MutableLiveData<>();

    private Context context;
    private AppDatabese appDatabese;
    public SportExercise_MVVM(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        appDatabese = AppDatabese.getInstance(context);
    }

    public void insertSportExercise(Sports_Exercises sports_Exercises){
        appDatabese.sports_exercises_dao().sports_ExercisesInsert(sports_Exercises)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(context, "Success Insert", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }

    public MutableLiveData<List<Sports_Exercises>> getAllSports_Exercises(){
        appDatabese.sports_exercises_dao().getAllSports_Exercises()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Sports_Exercises>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<Sports_Exercises> sports_Exercises) {
                        mutableLiveDataSports_Exercises.setValue(sports_Exercises);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
//                        Toast.makeText(context, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return mutableLiveDataSports_Exercises;
    }

    public MutableLiveData<Sports_Exercises> getSports_ExercisesByUid(String id ) {
        appDatabese.sports_exercises_dao().getSports_ExercisesById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Sports_Exercises>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull Sports_Exercises sports_Exercises) {
                        mutableLData.setValue(sports_Exercises);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return mutableLData;
    }



}
