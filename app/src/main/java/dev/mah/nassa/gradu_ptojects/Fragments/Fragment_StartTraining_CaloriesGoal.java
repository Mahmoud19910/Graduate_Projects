package dev.mah.nassa.gradu_ptojects.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import dev.mah.nassa.gradu_ptojects.Activityes.ActivitesStats;
import dev.mah.nassa.gradu_ptojects.Constants.CustomDialog;
import dev.mah.nassa.gradu_ptojects.Constants.LanguageUtils;
import dev.mah.nassa.gradu_ptojects.Constants.PersonActivityArray;
import dev.mah.nassa.gradu_ptojects.Constants.SharedFunctions;
import dev.mah.nassa.gradu_ptojects.Constants.StopwatchTimer;
import dev.mah.nassa.gradu_ptojects.DataBase.FireStore_DataBase;
import dev.mah.nassa.gradu_ptojects.Interfaces.TimerListener;
import dev.mah.nassa.gradu_ptojects.MVVM.ExersiseDetails_MVVM;
import dev.mah.nassa.gradu_ptojects.MVVM.UsersViewModel;
import dev.mah.nassa.gradu_ptojects.Modles.Exercise_Details;
import dev.mah.nassa.gradu_ptojects.Modles.Sports_Exercises;
import dev.mah.nassa.gradu_ptojects.Modles.UsersInfo;
import dev.mah.nassa.gradu_ptojects.R;
import dev.mah.nassa.gradu_ptojects.databinding.FragmentStartTrainingCaloriesGoalBinding;

public class Fragment_StartTraining_CaloriesGoal extends Fragment implements View.OnClickListener, TimerListener {

    private FragmentStartTrainingCaloriesGoalBinding binding;
    private Sports_Exercises sports_exercises;
    private StopwatchTimer timer;
    boolean isStart = true;
    private String caloriNumber, uid, weight;
    private double caloriesBurned;
    private UsersViewModel usersViewModel;
    private ExersiseDetails_MVVM exercisedetails_mvvm;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStartTrainingCaloriesGoalBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        LanguageUtils.changeLanguage(getContext(), "en");

        Bundle bundle = getArguments();
        sports_exercises = (Sports_Exercises) bundle.get("obj");
        caloriNumber = (String) bundle.get("value");
        uid = (String) bundle.get("uid");
        timer = new StopwatchTimer(this);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Glide.with(getContext()).asGif().load(sports_exercises.getImageUrl()).into(binding.caloriStartImage);

        // Start Timer
        timer.startTimer();
        binding.progressVerifyAccount.setMax(Integer.parseInt(caloriNumber));

        //Listeners
        binding.finishBut.setOnClickListener(this);
        binding.stopBut.setOnClickListener(this);

        //Set Max Progressed
        binding.progressVerifyAccount.setMax(Integer.parseInt(caloriNumber));

        exercisedetails_mvvm = ViewModelProviders.of(Fragment_StartTraining_CaloriesGoal.this).get(ExersiseDetails_MVVM.class);
        usersViewModel = ViewModelProviders.of(Fragment_StartTraining_CaloriesGoal.this).get(UsersViewModel.class);

        // جلب وزن الشخص لحساب السعرات الحرارية
        usersViewModel.getUsersByUid(uid).observe(this, new Observer<UsersInfo>() {
            @Override
            public void onChanged(UsersInfo usersInfo) {
                weight = usersInfo.getWeight();
            }
        });


        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                SharedFunctions.finishTrainingDialog(caloriNumber, String.valueOf(caloriesBurned), getContext(), uid , sports_exercises, new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        if(((Boolean)o)==true){
                            // ايقاف المؤقت حفظ النشاط في قاعدة البيانات
                            timer.timerTask.cancel();

                        }else {
                            timer.timerTask.cancel();
                        }
                    }
                });
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.stopBut:
                if (isStart) {
                    timer.timerTask.cancel();
                    binding.stopTv.setText("بدء");
                    binding.stioIcon.setImageDrawable(getResources().getDrawable(R.drawable.play_arrow_24));
                    binding.stopBut.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                    isStart = false;

                } else {
                    binding.stopTv.setText("توقيف");
                    binding.stioIcon.setImageDrawable(getResources().getDrawable(R.drawable.pause_24));
                    binding.stopBut.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                    isStart = true;
                    timer.startTimer();
                }
                break;

            case R.id.finishBut:
                // Create an AlertDialog.Builder instance
                SharedFunctions.finishTrainingDialog(caloriNumber, String.valueOf(caloriesBurned),getContext() , uid ,  sports_exercises , new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {

                        if(((Boolean)o)==true){
                            // ايقاف المؤقت حفظ النشاط في قاعدة البيانات
                            timer.timerTask.cancel();
                            Random random = new Random();

                            Exercise_Details exercise_details = new Exercise_Details(uid , UUID.randomUUID().toString() , String.valueOf(caloriesBurned) ,  SharedFunctions.getTimeAtTheMoment() , SharedFunctions.getDateAtTheMoment()
                                    , sports_exercises.getName() , String.valueOf(timer.getTimeByHours()) );
                            FireStore_DataBase.insertOrUpdateExerciseDetails(exercise_details , getContext());
                            exercisedetails_mvvm.insertExersiseDetails(exercise_details);

                        }else {
                            timer.timerTask.cancel();
                        }
                    }
                });
                break;
        }
    }

    // Interface is used to transfear time to this fragment
    @Override
    public void getTimer(String time) {
        binding.timeCaloriTv.setText(time);
        if (weight != null && sports_exercises.getMetValue() != null) {
            caloriesBurned = (Double.parseDouble(weight) * Double.parseDouble(sports_exercises.getMetValue()) * timer.getTimeByHours());
            binding.caloriesCount.setText(String.format("%.2f", caloriesBurned) + "");
            binding.progressVerifyAccount.setProgress((int) caloriesBurned, true);
        }

        // انهاء النشاط عند الوصوصل الى الهدف من السعرات
        if(Double.parseDouble(caloriNumber) <= Double.parseDouble(binding.caloriesCount.getText().toString())){
            timer.timerTask.cancel();
            Random random = new Random();
            Exercise_Details exercise_details = new Exercise_Details(uid , UUID.randomUUID().toString() , String.valueOf(caloriesBurned) ,  SharedFunctions.getTimeAtTheMoment() , SharedFunctions.getDateAtTheMoment()
                    , sports_exercises.getName() , String.valueOf(timer.getTimeByHours()));
            exercisedetails_mvvm.insertExersiseDetails(exercise_details);
            FireStore_DataBase.insertOrUpdateExerciseDetails(exercise_details , getContext());

            CustomDialog dialog = new CustomDialog(getContext() , time , String.format("%.2f", caloriesBurned));
            dialog.show();
            // اغلاق الdialog عند الضغط على الزر
            dialog.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().startActivity(new Intent(getContext() , ActivitesStats.class));
                    dialog.dismiss();
                    getActivity().finish();

                }
            });


        }

    }


}