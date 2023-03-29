package dev.mah.nassa.gradu_ptojects.Modles;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UsersViewModel extends ViewModel {

    MutableLiveData<List<UsersInfo>> mutableLiveData =new MutableLiveData<>();
    UsersInfo usersInfoUid =new UsersInfo();
    UsersInfo getusersInfoUid =new UsersInfo();
    public void insertUsers(UsersDatabese usersDatabese , UsersInfo usersInfo,Context context){
        usersDatabese.usersDao().UsersInsert(usersInfo)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(context, "Insert", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
    public void updateUsers(UsersDatabese usersDatabese , UsersInfo usersInfo , Context context){
        usersDatabese.usersDao().UsersUpdate(usersInfo)
                .subscribeOn(Schedulers.computation())
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
    public void deleteUsers (UsersDatabese usersDatabese , UsersInfo usersInfo , Context context){
        usersDatabese.usersDao().UsersDelete(usersInfo)
                .subscribeOn(Schedulers.computation())
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
    public void deleteAllUsers(UsersDatabese usersDatabese ,  Context context){
        usersDatabese.usersDao().deleteAllUsers()
                .subscribeOn(Schedulers.computation())
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
    public MutableLiveData<List<UsersInfo>> getAllUsers(UsersDatabese usersDatabese){
        usersDatabese.usersDao().getAllUsers()
                .subscribeOn(Schedulers.computation())
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

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        return mutableLiveData;
    }
    public void deleteByUidUsers(UsersDatabese usersDatabese , String uid , Context context ){
        usersDatabese.usersDao().deleteByUid(uid)
                .subscribeOn(Schedulers.computation())
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
    public UsersInfo getUsersByUid(UsersDatabese usersDatabese ,String uid, Context context){
        usersDatabese.usersDao().getUsersById(uid)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UsersInfo>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        
                    }

                    @Override
                    public void onNext(@NonNull UsersInfo usersInfo) {
                        usersInfoUid = usersInfo;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return usersInfoUid;
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
    public UsersInfo getAllByUidUserId (UsersDatabese usersDatabese , String uid ,Context context){
        usersDatabese.usersDao().getAllByUidUserId(uid)
                .subscribeOn(Schedulers.computation())
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
}
