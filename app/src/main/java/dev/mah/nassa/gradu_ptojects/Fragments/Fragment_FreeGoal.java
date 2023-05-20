package dev.mah.nassa.gradu_ptojects.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rishabhharit.roundedimageview.RoundedImageView;

import dev.mah.nassa.gradu_ptojects.Constants.LanguageUtils;
import dev.mah.nassa.gradu_ptojects.Constants.SharedFunctions;
import dev.mah.nassa.gradu_ptojects.Modles.Sports_Exercises;
import dev.mah.nassa.gradu_ptojects.R;
import dev.mah.nassa.gradu_ptojects.databinding.FragmentFreeGoalBinding;


public class Fragment_FreeGoal extends Fragment implements View.OnClickListener {

    private FragmentFreeGoalBinding binding;
    private Sports_Exercises sports_exercises;
    private int indexFrag;
    private String uid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFreeGoalBinding.inflate(inflater, container, false); // Assign the inflated binding object to 'binding'
        View view = binding.getRoot();
        LanguageUtils.changeLanguage(getContext(), "en");
        Bundle bundle = getArguments();
        sports_exercises = (Sports_Exercises) bundle.getSerializable("obj");
        indexFrag =  bundle.getInt("indexFrag");
        uid = bundle.getString("uid");

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RoundedImageView freeGoalImage = binding.freeGoalImage;

        Glide.with(getContext())
                .asGif() // Enable support for GIF type
                .load(sports_exercises.getImageUrl())
                .into(freeGoalImage);

        binding.freeGoalStartBut.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.freeGoalStartBut:
                SharedFunctions.dialogStartTraining(getContext() , indexFrag , sports_exercises , null , uid);
                SharedFunctions.countDownTimer.start();
                break;
        }
    }
}