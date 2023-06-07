package dev.mah.nassa.gradu_ptojects.MVVM;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Walking_MVVM extends AndroidViewModel {

    private MutableLiveData<String> time = new MutableLiveData<>();
    private MutableLiveData<Boolean> isStart = new MutableLiveData<>();
    private MutableLiveData<ArrayList<String>> walkingData = new MutableLiveData<>();

    public Walking_MVVM(@NonNull Application application) {
        super(application);
    }

    public void setTime(String timer){
        time.setValue(timer);
    }

    public MutableLiveData<String> getIme(){
        return  time;
    }

    public void setAllData(String distance , String speed , String caloriedBurnd , String steps , String timeAtTheMomment){
        ArrayList<String> list = new ArrayList<>();
        list.add(distance);
        list.add(speed);
        list.add(caloriedBurnd);
        list.add(steps);
        list.add(timeAtTheMomment);

        walkingData.setValue(list);
    }

    public MutableLiveData<ArrayList<String>> getAllData(){
        return walkingData;
    }

    public void setIsStart(boolean isRun){
        isStart.setValue(isRun);
    }
    public MutableLiveData<Boolean> getIsStart(){
        return isStart;
    }




}
