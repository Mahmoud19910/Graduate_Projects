package dev.mah.nassa.gradu_ptojects.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dev.mah.nassa.gradu_ptojects.Activityes.ActivitesStats;
import dev.mah.nassa.gradu_ptojects.Activityes.Home_Activity;
import dev.mah.nassa.gradu_ptojects.Activityes.MyMealList_Activity;
import dev.mah.nassa.gradu_ptojects.Activityes.Profile_Activity;
import dev.mah.nassa.gradu_ptojects.Activityes.SignIn_Activity;
import dev.mah.nassa.gradu_ptojects.Constants.SharedFunctions;
import dev.mah.nassa.gradu_ptojects.FireBase_Authentication.Gmai_Auth;
import dev.mah.nassa.gradu_ptojects.R;
import dev.mah.nassa.gradu_ptojects.databinding.FragmentDrawer2Binding;


public class FragmentDrawer extends Fragment {

    FragmentDrawer2Binding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =FragmentDrawer2Binding.inflate(inflater , container , false).getRoot();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView signOut = getActivity().findViewById(R.id.signOut);
        TextView stats = getActivity().findViewById(R.id.statistics);
        TextView account = getActivity().findViewById(R.id.account);
        TextView food = getActivity().findViewById(R.id.myFoud);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                                Gmai_Auth.onSignOut(getContext());
                startActivity(new Intent(getContext(), SignIn_Activity.class));
                SharedFunctions.isSignIn(false, getContext());
                getActivity().finish();

            }
        });

        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext() , ActivitesStats.class));
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getContext(), Profile_Activity.class);
                getActivity().startActivity(intent);
            }
        });

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext() , MyMealList_Activity.class));
            }
        });
    }
}