package dev.mah.nassa.gradu_ptojects.MVVM;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import dev.mah.nassa.gradu_ptojects.DataBase.AppDatabese;
import dev.mah.nassa.gradu_ptojects.Interfaces.ExerciseDetailsDao;
import dev.mah.nassa.gradu_ptojects.Modles.Exercise_Details;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ExersiseDetails_MVVM extends AndroidViewModel {

    public ExerciseDetailsDao exerciseDetailsDao;
    Context context;
    MutableLiveData<List<Exercise_Details>> mutableLiveDataExercie = new MutableLiveData<>();

    public ExersiseDetails_MVVM(@NonNull Application application) {
        super(application);
        context=application.getApplicationContext();
     exerciseDetailsDao = AppDatabese.getInstance(application.getApplicationContext()).exerciseDetailsDao();
    }

    public MutableLiveData<List<Exercise_Details>> getAllExersiseDetails(){
        exerciseDetailsDao.getAllExercise_Details()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Exercise_Details>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<Exercise_Details> exercise_details) {

                        mutableLiveDataExercie.setValue(exercise_details);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Toast.makeText(context, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return mutableLiveDataExercie;
    }

    public void insertExersiseDetails(Exercise_Details exercise_details){
        exerciseDetailsDao.exersuseDetailsInsert(exercise_details)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(context, "Succes Add Exercise", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Toast.makeText(context, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void deleteAllExerciseDetails(){
        exerciseDetailsDao.deleteAllExersuseDetails()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(context, "Delete All Details", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }
}
