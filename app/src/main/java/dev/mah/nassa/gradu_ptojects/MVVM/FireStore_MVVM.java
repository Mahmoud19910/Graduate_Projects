package dev.mah.nassa.gradu_ptojects.MVVM;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import dev.mah.nassa.gradu_ptojects.DataBase.FireStore_DataBase;
import dev.mah.nassa.gradu_ptojects.Modles.Sports_Exercises;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FireStore_MVVM extends AndroidViewModel {


    static FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    Context context;
    public FireStore_MVVM(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }


    public void getAllSportsExercises(OnSuccessListener<List<Sports_Exercises>> onSuccessListener){

        FireStore_DataBase.getAllExercices(context)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Sports_Exercises>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<Sports_Exercises> sports_exercises) {
                        onSuccessListener.onSuccess(sports_exercises);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }



}
