package dev.mah.nassa.gradu_ptojects.MVVM;
import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import dev.mah.nassa.gradu_ptojects.DataBase.AppDatabese;
import dev.mah.nassa.gradu_ptojects.Modles.Users_Health_Info;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UsersHealthInfoViewModel extends AndroidViewModel {

    MutableLiveData<List<Users_Health_Info>> mutableLiveData = new MutableLiveData<>();
    MutableLiveData<Users_Health_Info> usersHealthInfoMutableLiveData = new MutableLiveData<>();
    Users_Health_Info usersHealthInfo = new Users_Health_Info();
    Context context;
    AppDatabese appDatabese;

    public UsersHealthInfoViewModel(@NonNull Application application) {
        super(application);
        context=application.getApplicationContext();
        appDatabese= AppDatabese.getInstance(application.getApplicationContext());
    }

    public void insertUsersHealth(Users_Health_Info usersHealthInfo) {
        appDatabese.usersHealthDao().usersHealthInsert(usersHealthInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(context, "Insert Health Success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        System.out.println("eRROR"+e.getMessage().toString());
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });
    }

    public void updateUsersHealth( Users_Health_Info usersHealthInfo) {
        appDatabese.usersHealthDao().usersHealthUpdate(usersHealthInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(context, "Update", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }

    public void deleteUsersHealth(Users_Health_Info usersHealthInfo) {
        appDatabese.usersHealthDao().usersHealthDelete(usersHealthInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(context, "delete", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }

    public void deleteAllUsersHealth() {
        appDatabese.usersHealthDao().deleteAllUsersHealth()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(context, "deleteAllUsersHealth", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }

    public MutableLiveData<List<Users_Health_Info>> getAllUsersHealth() {
        appDatabese.usersHealthDao().getAllUsersHealth()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Users_Health_Info>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<Users_Health_Info> usersHealthInfos) {
                        mutableLiveData.setValue(usersHealthInfos);

                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        return mutableLiveData;
    }

    public MutableLiveData<Users_Health_Info> getUsersHealthById( String uid) {
        appDatabese.usersHealthDao().getUsersHealthById(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Users_Health_Info>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull Users_Health_Info usersHealthInfo1) {
                        usersHealthInfoMutableLiveData.setValue(usersHealthInfo1);

                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return usersHealthInfoMutableLiveData;
    }

    public void deleteUsersHealthById( String uid ){
        appDatabese.usersHealthDao().deleteUsersHealthByUid(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }

    public void updateCalories(String uid , double burnedCalories ){
        appDatabese.usersHealthDao().updateCaloriesById(uid , burnedCalories)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(context, "Success Update Calories", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }
}
