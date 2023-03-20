package dev.mah.nassa.gradu_ptojects.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import dev.mah.nassa.gradu_ptojects.Constants.PersonActivityArray;
import dev.mah.nassa.gradu_ptojects.Adapters.PersonActivity_Adapter;
import dev.mah.nassa.gradu_ptojects.R;
import dev.mah.nassa.gradu_ptojects.Interfaces.UsersInfoListener;
import dev.mah.nassa.gradu_ptojects.databinding.FragmentUsersInfo2Binding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentUsersInfo2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentUsersInfo2 extends Fragment {
    UsersInfoListener listener;
    FragmentUsersInfo2Binding binding;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof UsersInfoListener){
            listener= (UsersInfoListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // استدعاء ميثود عند الضغط على زر الرجوع
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                listener.getFragmentNumber(1);// ارسال قيمة الفراقمنت المراد الذهاب لها
                requireActivity().getSupportFragmentManager().popBackStack(); // الرجوع الى الخلف

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentUsersInfo2Binding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        PersonActivity_Adapter personActivity_adapter=new PersonActivity_Adapter(R.layout.arraylist_person_activity, getContext() , PersonActivityArray.getPersonActivityList());
        binding.listViewPersonInfo.setAdapter(personActivity_adapter);

        binding.listViewPersonInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String activityLevel=PersonActivityArray.getPersonActivityList().get(position);
                listener.getActivityLevel(position , activityLevel);
            }
        });


    }


}