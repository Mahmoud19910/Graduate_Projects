package dev.mah.nassa.gradu_ptojects.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import dev.mah.nassa.gradu_ptojects.Modles.SharedFunctions;
import dev.mah.nassa.gradu_ptojects.R;
import dev.mah.nassa.gradu_ptojects.UsersInfoListener;
import dev.mah.nassa.gradu_ptojects.databinding.FragmentUsersInfo1Binding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentUsersInfo1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentUsersInfo1 extends Fragment {

    private FragmentUsersInfo1Binding binding;
    private UsersInfoListener listener;
    private String gender , length , weight , eage;
    private boolean checkInput=true;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof UsersInfoListener){
            listener= (UsersInfoListener) context;
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentUsersInfo1Binding.inflate(inflater);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // عند الضغط على زر التالي يقوم النترفيس بارسال قيمة الفراقمنت المتوجه له الى أكتيفيتي حتى يتم تغييير المؤشر
        binding.nextBtUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInput= SharedFunctions.checkEnterdDataInUserInfo(binding.editEage,binding.editLength,binding.editWeight,binding.usersInfoRadiBut,getContext());
                if(checkInput==true){
                    listener.getFragmentNumber(2);
                    FragmentManager fragmentManager=getFragmentManager();
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.framUsersInfo,new FragmentUsersInfo2());
                    fragmentTransaction.addToBackStack("stack");
                    fragmentTransaction.commit();
                    listener.getInfoUsers(gender,length,weight,eage);
                }

            }
        });


        binding.usersInfoRadiBut.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.male:
                        gender=binding.male.getText().toString();
                        break;

                    case R.id.female:
                        gender=binding.female.getText().toString();
                        break;
                }
            }
        });


    }
}