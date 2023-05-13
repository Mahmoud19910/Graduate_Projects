package dev.mah.nassa.gradu_ptojects.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dev.mah.nassa.gradu_ptojects.Activityes.SignIn_Activity;
import dev.mah.nassa.gradu_ptojects.Constants.SharedFunctions;
import dev.mah.nassa.gradu_ptojects.FireBase_Authentication.Gmai_Auth;
import dev.mah.nassa.gradu_ptojects.R;
import dev.mah.nassa.gradu_ptojects.databinding.FragmentDrawerBinding;

public class DrawerFragment extends Fragment {
    FragmentDrawerBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = FragmentDrawerBinding.inflate(inflater,container,false).getRoot();
        return inflater.inflate(R.layout.fragment_drawer, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gmai_Auth.onSignOut(getContext());
                startActivity(new Intent(getContext(), SignIn_Activity.class));
                SharedFunctions.isSignIn(false, getContext());
                getActivity().finish();
            }
        });

    }
}