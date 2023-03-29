package dev.mah.nassa.gradu_ptojects.Modles;
import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UsersHealthInfoViewModel extends ViewModel {
    MutableLiveData<List<Users_Health_Info>> mutableLiveData = new MutableLiveData<>();
    Users_Health_Info usersHealthInfo = new Users_Health_Info();

    public void insertUsersHealth(UsersDatabese usersDatabese, Users_Health_Info usersHealthInfo, Context context) {
        usersDatabese.usersHealthDao().usersHealthInsert(usersHealthInfo)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(context, "Insert", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }

    public void updateUsersHealth(UsersDatabese usersDatabese, Users_Health_Info usersHealthInfo, Context context) {
        usersDatabese.usersHealthDao().usersHealthUpdate(usersHealthInfo)
                .subscribeOn(Schedulers.computation())
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

    public void deleteUsersHealth(UsersDatabese usersDatabese, Users_Health_Info usersHealthInfo, Context context) {
        usersDatabese.usersHealthDao().usersHealthDelete(usersHealthInfo)
                .subscribeOn(Schedulers.computation())
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

    public void deleteAllUsersHealth(UsersDatabese usersDatabese, Context context) {
        usersDatabese.usersHealthDao().deleteAllUsersHealth()
                .subscribeOn(Schedulers.computation())
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

    public MutableLiveData<List<Users_Health_Info>> getAllUsersHealth(UsersDatabese usersDatabese) {
        usersDatabese.usersHealthDao().getAllUsersHealth()
                .subscribeOn(Schedulers.computation())
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

    public Users_Health_Info getUsersHealthById(UsersDatabese usersDatabese, int id, Context context) {
        usersDatabese.usersHealthDao().getUsersHealthById(id)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Users_Health_Info>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull Users_Health_Info usersHealthInfo1) {
                        usersHealthInfo = usersHealthInfo1;
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return usersHealthInfo;
    }

    public void deleteUsersHealthByUid(UsersDatabese usersDatabese , int id, Context context ){
        usersDatabese.usersHealthDao().deleteUsersHealthByUid(id)
                .subscribeOn(Schedulers.computation())
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
}

