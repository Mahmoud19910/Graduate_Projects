package dev.mah.nassa.gradu_ptojects.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import dev.mah.nassa.gradu_ptojects.Constants.LanguageUtils;
import dev.mah.nassa.gradu_ptojects.Constants.SharedFunctions;
import dev.mah.nassa.gradu_ptojects.Modles.Sports_Exercises;
import dev.mah.nassa.gradu_ptojects.R;
import dev.mah.nassa.gradu_ptojects.databinding.FragmentTimeGoalBinding;


public class Fragment_TimeGoal extends Fragment implements View.OnClickListener {

    private FragmentTimeGoalBinding binding;
    private Sports_Exercises sports_exercises;
    private int indexFrag;
    private String uid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTimeGoalBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        LanguageUtils.changeLanguage(getContext(), "en");
        Bundle bundle = getArguments();
        sports_exercises = (Sports_Exercises) bundle.getSerializable("obj");
        indexFrag = bundle.getInt("indexFrag");
        uid = bundle.getString("uid");

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Glide.with(getContext())
                .asGif()
                .load(sports_exercises.getImageUrl())
                .into(binding.timeGoalImage);

        binding.timeGoalBut.setOnClickListener(this);
        binding.increaseTimeBut.setOnClickListener(this);
        binding.decreaseTimeBut.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.timeGoalBut:
                //(StratTraining_Activity) أخذ قيمة الساهة و الدقيقة لتحويلهما الى
                double timeByHourse = (Integer.parseInt(binding.hourseTv.getText().toString()) + (Double.parseDouble(binding.minutesTv.getText().toString()) / 60));
                SharedFunctions.dialogStartTraining(getContext(), indexFrag, sports_exercises, String.valueOf(timeByHourse), uid);
                SharedFunctions.countDownTimer.start();
                break;
            case R.id.decreaseTimeBut:
                SharedFunctions.timeDecrease(binding.hourseTv, binding.minutesTv);
                break;

            case R.id.increaseTimeBut:
                SharedFunctions.timeIncrease(binding.hourseTv, binding.minutesTv);
                break;

        }
    }
}