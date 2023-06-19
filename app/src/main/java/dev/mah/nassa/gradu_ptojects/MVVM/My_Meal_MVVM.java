package dev.mah.nassa.gradu_ptojects.MVVM;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import dev.mah.nassa.gradu_ptojects.Constants.SharedFunctions;
import dev.mah.nassa.gradu_ptojects.DataBase.AppDatabese;
import dev.mah.nassa.gradu_ptojects.Modles.FoodCategory;
import dev.mah.nassa.gradu_ptojects.Modles.My_Meal_List;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class My_Meal_MVVM extends AndroidViewModel {

    MutableLiveData<List<My_Meal_List>> mutableLiveData = new MutableLiveData<>();
    MutableLiveData<List<My_Meal_List>> liveData = new MutableLiveData<>();
    MutableLiveData<My_Meal_List> mutableLData = new MutableLiveData<>();
    MutableLiveData<String> mutableLiveDataString=new MutableLiveData<>();

    Context context;
    AppDatabese appDatabese;
    // initialize variables

    // create set text method
    public void setTitle(String s)
    {
        // set value
        mutableLiveDataString.setValue(s);
    }

    // create get text method
    public MutableLiveData<String> getTitle()
    {
        return mutableLiveDataString;
    }
    public My_Meal_MVVM(@androidx.annotation.NonNull Application application) {
        super(application);
        context=application.getApplicationContext();
        appDatabese=AppDatabese.getInstance(application.getApplicationContext());
    }


    public void insertMy_Meal_List( My_Meal_List my_meal_list) {
        appDatabese.my_meal_list_dao().my_Meal_ListInsert(my_meal_list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
//                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });
    }

    public void updateMy_Meal_List( My_Meal_List my_meal_list) {
        appDatabese.my_meal_list_dao().my_Meal_ListUpdate(my_meal_list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(context, "Update", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void deleteMy_Meal_List( My_Meal_List my_meal_list) {
        appDatabese.my_meal_list_dao().my_Meal_ListDelete(my_meal_list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(context, "My_Meal_ListDelete", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void deleteAllMy_Meal_List( Context context) {
        appDatabese.my_meal_list_dao().deleteAllMy_Meal_List()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(context, "deleteAllMy_Meal_List", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public MutableLiveData<List<My_Meal_List>> getAllMy_Meal_List() {
        appDatabese.my_meal_list_dao().getAllMy_Meal_List()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<My_Meal_List>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<My_Meal_List> my_meal_lists) {
                        mutableLiveData.setValue(my_meal_lists);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        SharedFunctions.dismissDialog();

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        return mutableLiveData;
    }

    public void deleteByUidMy_Meal_List( String uid) {
        appDatabese.my_meal_list_dao().deleteMy_Meal_ListUid(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }

    public MutableLiveData<My_Meal_List> getMy_Meal_ListByUid(String uid ) {
        appDatabese.my_meal_list_dao().getMy_Meal_ListById(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<My_Meal_List>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull My_Meal_List my_meal_list) {
                        mutableLData.setValue(my_meal_list);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return mutableLData;
    }


    public MutableLiveData<List<My_Meal_List>> getMy_Meal_ListByUidAndDate(String uid , String date) {
        appDatabese.my_meal_list_dao().getMy_Meal_ListByIdAndDate(uid , date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<My_Meal_List>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<My_Meal_List> my_meal_lists) {
                        liveData.setValue(my_meal_lists);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        return liveData;
    }

}
