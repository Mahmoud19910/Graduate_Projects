package dev.mah.nassa.gradu_ptojects.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import dev.mah.nassa.gradu_ptojects.Adapters.DoctorAdapter_Horisintal;
import dev.mah.nassa.gradu_ptojects.Adapters.DoctorsAdapter;
import dev.mah.nassa.gradu_ptojects.Constants.SharedFunctions;
import dev.mah.nassa.gradu_ptojects.DataBase.RealTime_DataBase;
import dev.mah.nassa.gradu_ptojects.MVVM.Doctors_MVVM;
import dev.mah.nassa.gradu_ptojects.MVVM.RealTime_MVVM;
import dev.mah.nassa.gradu_ptojects.Modles.Doctor;
import dev.mah.nassa.gradu_ptojects.R;
import dev.mah.nassa.gradu_ptojects.databinding.FragmentDoctorsBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Doctors_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Doctors_Fragment extends Fragment {

    private FragmentDoctorsBinding binding;
    private DoctorsAdapter doctorsAdapter;
    private RealTime_MVVM realTime_mvvm;
    private Doctors_MVVM doctors_mvvm;
    private DoctorAdapter_Horisintal doctorAdapter_horisintal;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDoctorsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        boolean inrenetConect = SharedFunctions.checkInternetConnection(getContext());
        realTime_mvvm = ViewModelProviders.of(Doctors_Fragment.this).get(RealTime_MVVM.class);
        doctors_mvvm = ViewModelProviders.of(Doctors_Fragment.this).get(Doctors_MVVM.class);

        //Adapter
        doctorAdapter_horisintal = new DoctorAdapter_Horisintal(getContext());
        binding.doctor2Recycler.setAdapter(doctorAdapter_horisintal);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.doctor2Recycler.setLayoutManager(layoutManager);


        //Adapter
        doctorsAdapter = new DoctorsAdapter(getContext());
        binding.doctorRecycler.setAdapter(doctorsAdapter);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext());
        layoutManager2.setOrientation(RecyclerView.VERTICAL);
        binding.doctorRecycler.setLayoutManager(layoutManager2);





        if (inrenetConect) {

            doctors_mvvm.deletAllDoctor();
            realTime_mvvm.getDoctors(new Doctors_Fragment()).observe(Doctors_Fragment.this, new Observer<Doctor>() {
                @Override
                public void onChanged(Doctor doctor) {
                    doctors_mvvm.insertDoctors(doctor);
                }
            });

            RealTime_DataBase.getAllDoctorsFromRealTime(getContext(), doctorsAdapter, new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {
                    Doctor doctor = (Doctor) o;
                    doctorsAdapter.addDoctor(doctor);
                }
            });


            RealTime_DataBase.getAllDoctorsFromRealTime(getContext(), doctorAdapter_horisintal, new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {
                    Doctor doctor = (Doctor) o;
                    doctorAdapter_horisintal.addDoctor(doctor);
                }
            });

        }else {

            SharedFunctions.customToaste(R.layout.internet_toast_shape , getContext());

            doctors_mvvm.getAllDoctors().observe(this, new Observer<List<Doctor>>() {
                @Override
                public void onChanged(List<Doctor> doctorList) {
                    //Adapter
                    doctorsAdapter = new DoctorsAdapter(getContext(), doctorList);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    layoutManager.setOrientation(RecyclerView.VERTICAL);
                    binding.doctorRecycler.setLayoutManager(layoutManager);
                    binding.doctorRecycler.setAdapter(doctorsAdapter);

                }
            });


            doctors_mvvm.getAllDoctors().observe(this, new Observer<List<Doctor>>() {
                @Override
                public void onChanged(List<Doctor> doctorList) {
                    //Adapter
                    doctorAdapter_horisintal = new DoctorAdapter_Horisintal(getContext(), doctorList);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    layoutManager.setOrientation(RecyclerView.HORIZONTAL);
                    binding.doctor2Recycler.setLayoutManager(layoutManager);
                    binding.doctor2Recycler.setAdapter(doctorAdapter_horisintal);

                }
            });


        }



    }


}