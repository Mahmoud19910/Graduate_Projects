package dev.mah.nassa.gradu_ptojects.MVVM;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import dev.mah.nassa.gradu_ptojects.Adapters.DoctorsAdapter;
import dev.mah.nassa.gradu_ptojects.DataBase.RealTime_DataBase;
import dev.mah.nassa.gradu_ptojects.Fragments.Doctors_Fragment;
import dev.mah.nassa.gradu_ptojects.Modles.Doctor;

public class RealTime_MVVM extends AndroidViewModel {
    MutableLiveData<Doctor> doctorMutableData = new MutableLiveData<>();
    private Context context;



    public RealTime_MVVM(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }


    public MutableLiveData<Doctor> getDoctors( Fragment fragment){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                RealTime_DataBase.getAllDoctorsFromRealTime(context,  new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Doctor  doctor = (Doctor) o;
                        doctorMutableData.setValue(doctor);
                    }
                });
            }
        });

        thread.start();

        return doctorMutableData;
    }


}
