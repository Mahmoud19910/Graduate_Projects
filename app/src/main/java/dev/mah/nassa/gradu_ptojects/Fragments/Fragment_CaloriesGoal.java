package dev.mah.nassa.gradu_ptojects.Fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.rishabhharit.roundedimageview.RoundedImageView;

import de.hdodenhof.circleimageview.CircleImageView;
import dev.mah.nassa.gradu_ptojects.Constants.LanguageUtils;
import dev.mah.nassa.gradu_ptojects.Constants.SharedFunctions;
import dev.mah.nassa.gradu_ptojects.Modles.Sports_Exercises;
import dev.mah.nassa.gradu_ptojects.R;
import dev.mah.nassa.gradu_ptojects.databinding.FragmentCaloriesGoalBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_CaloriesGoal#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_CaloriesGoal extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private  FragmentCaloriesGoalBinding binding;
    private Sports_Exercises sports_exercises;
    private int indexFrag;
    private String uid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCaloriesGoalBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        LanguageUtils.changeLanguage(getContext(), "en");
        Bundle bundle = getArguments();
        sports_exercises = (Sports_Exercises) bundle.getSerializable("obj");
        indexFrag = bundle.getInt("indexFrag");
        uid = bundle.getString("uid");

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RoundedImageView circleImageView = binding.trainingImage;
        Glide.with(getContext())
                .load(sports_exercises.getImageUrl())
                .into(circleImageView);

        binding.startBut.setOnClickListener(this);
        binding.increaseCaloriesBut.setOnClickListener(this);
        binding.decreaseCaloriesBut.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startBut:
                SharedFunctions.dialogStartTraining(getContext(), indexFrag, sports_exercises, binding.caloriesTv.getText().toString()  ,uid);
                SharedFunctions.countDownTimer.start();
                break;

            case R.id.increaseCaloriesBut:
                SharedFunctions.caloriesIncrease(binding.caloriesTv);
                break;

            case R.id.decreaseCaloriesBut:
                SharedFunctions.caloriesDecrease(binding.caloriesTv);
                break;
        }
    }
}