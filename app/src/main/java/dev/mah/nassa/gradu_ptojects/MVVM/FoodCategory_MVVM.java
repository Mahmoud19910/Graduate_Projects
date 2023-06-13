package dev.mah.nassa.gradu_ptojects.MVVM;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import dev.mah.nassa.gradu_ptojects.Constants.SharedFunctions;
import dev.mah.nassa.gradu_ptojects.DataBase.AppDatabese;
import dev.mah.nassa.gradu_ptojects.Modles.FoodCategory;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FoodCategory_MVVM extends AndroidViewModel {

    MutableLiveData<List<FoodCategory>> mutableLiveData = new MutableLiveData<>();
    MutableLiveData<FoodCategory> mutableLData = new MutableLiveData<>();

    Context context;
    AppDatabese appDatabese;

    public FoodCategory_MVVM(@androidx.annotation.NonNull Application application) {
        super(application);
        context=application.getApplicationContext();
        appDatabese=AppDatabese.getInstance(application.getApplicationContext());
    }


    public void insertFoodCategory( FoodCategory foodCategory) {
        appDatabese.foodCategory_dao().foodCategoryInsert(foodCategory)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
//                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });
    }

    public void updateFoodCategory( FoodCategory foodCategory) {
        appDatabese.foodCategory_dao().foodCategoryUpdate(foodCategory)
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

    public void deleteFoodCategory( FoodCategory foodCategory) {
        appDatabese.foodCategory_dao().foodCategoryDelete(foodCategory)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(context, "FoodCategoryDelete", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void deleteAllFoodCategory( Context context) {
        appDatabese.foodCategory_dao().deleteAllFoodCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(context, "deleteAllFoodCategory", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public MutableLiveData<List<FoodCategory>> getAllFoodCategory() {
        appDatabese.foodCategory_dao().getAllFoodCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<FoodCategory>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<FoodCategory> foodCategoryList) {
                        mutableLiveData.setValue(foodCategoryList);

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

    public void deleteByUidFoodCategory( String uid) {
        appDatabese.foodCategory_dao().deleteFoodCategoryUid(uid)
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

    public MutableLiveData<FoodCategory> getFoodCategoryByUid(String uid ) {
        appDatabese.foodCategory_dao().getFoodCategoryById(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FoodCategory>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull FoodCategory foodCategory) {
                        mutableLData.setValue(foodCategory);
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


}
