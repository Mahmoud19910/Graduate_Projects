package dev.mah.nassa.gradu_ptojects.MVVM;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import dev.mah.nassa.gradu_ptojects.Constants.SharedFunctions;
import dev.mah.nassa.gradu_ptojects.DataBase.AppDatabese;
import dev.mah.nassa.gradu_ptojects.Modles.UsersInfo;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UsersViewModel extends AndroidViewModel {

    MutableLiveData<List<UsersInfo>> mutableLiveData = new MutableLiveData<>();
    MutableLiveData<UsersInfo> mutableLData = new MutableLiveData<>();

    UsersInfo usersInfoUid = new UsersInfo();
    UsersInfo getusersInfoUid = new UsersInfo();
    Context context;
    AppDatabese appDatabese;

    public UsersViewModel(@androidx.annotation.NonNull Application application) {
        super(application);
        context=application.getApplicationContext();
        appDatabese=AppDatabese.getInstance(application.getApplicationContext());
    }


    public void insertUsers( UsersInfo usersInfo) {
        appDatabese.usersDao().usersInsert(usersInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(context, "Insert Users Success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                        System.out.println("E R R O R  :"+e.getMessage());
                        Log.e("TAG", "Error occurred: " + e.getMessage());

                    }
                });
    }

    public void updateUsers( UsersInfo usersInfo) {
        appDatabese.usersDao().usersUpdate(usersInfo)
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

    public void deleteUsers( UsersInfo usersInfo) {
        appDatabese.usersDao().usersDelete(usersInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(context, "UsersDelete", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void deleteAllUsers( Context context) {
        appDatabese.usersDao().deleteAllUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(context, "deleteAllUsers", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public MutableLiveData<List<UsersInfo>> getAllUsers() {
        appDatabese.usersDao().getAllUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<UsersInfo>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<UsersInfo> usersInfos) {
                        mutableLiveData.setValue(usersInfos);

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

    public void deleteByUidUsers( String uid) {
        appDatabese.usersDao().deleteByUid(uid)
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

    public MutableLiveData<UsersInfo> getUsersByUid(String uid ) {
        appDatabese.usersDao().getUsersById(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UsersInfo>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull UsersInfo usersInfo) {
                       mutableLData.setValue(usersInfo);
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

    //    public UsersInfo getAllByUidUserId (UsersDatabese usersDatabese , String uid ,Context context){
//            usersDatabese.usersDao().getAllByUidUserId(uid)
//                    .subscribeOn(Schedulers.computation())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Consumer<UsersInfo>() {
//                        @Override
//                        public void accept(UsersInfo usersInfo) throws Throwable {
//             getusersInfoUid = usersInfo;
//                        }
//                    });
//            return getusersInfoUid;
//    }
    public UsersInfo getAllByUidUserId( String uid) {
        appDatabese.usersDao().getAllByUidUserId(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UsersInfo>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull UsersInfo usersInfo) {
                        getusersInfoUid = usersInfo;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return getusersInfoUid;
    }

    public void updatePass(String newPass , String phone){
        appDatabese.usersDao().updatePassword(newPass , phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(context, "Success Update", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(context, e.getMessage().toString() , Toast.LENGTH_SHORT).show();

                    }
                });

    }
}