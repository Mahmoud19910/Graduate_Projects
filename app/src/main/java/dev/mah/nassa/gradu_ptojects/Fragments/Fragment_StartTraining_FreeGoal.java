package dev.mah.nassa.gradu_ptojects.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

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

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import dev.mah.nassa.gradu_ptojects.Activityes.ActivitesStats;
import dev.mah.nassa.gradu_ptojects.Activityes.Exercices_Activity;
import dev.mah.nassa.gradu_ptojects.Constants.CustomDialog;
import dev.mah.nassa.gradu_ptojects.Constants.LanguageUtils;
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
import dev.mah.nassa.gradu_ptojects.databinding.FragmentStartTrainingFreeGoalBinding;

public class Fragment_StartTraining_FreeGoal extends Fragment implements View.OnClickListener ,  TimerListener {


    private FragmentStartTrainingFreeGoalBinding binding;
    private Sports_Exercises sports_exercises;
    private StopwatchTimer timer;
    private String timeGoal, uid;
    boolean isStart = true;
    private String weight;
    private double caloriesBurned;
    private UsersViewModel usersViewModel;
    private ExersiseDetails_MVVM exercisedetails_mvvm;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStartTrainingFreeGoalBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        LanguageUtils.changeLanguage(getContext(), "en");

        Bundle bundle = getArguments();
        sports_exercises = (Sports_Exercises) bundle.get("obj");
        timeGoal = (String) bundle.get("value"); // نوع الهدف حر لا نحتاج قيمة الهدف
        uid = (String) bundle.get("uid");
        timer = new StopwatchTimer(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        binding.progressVerifyAccount.setMax(100);
        binding.progressVerifyAccount.setProgress(100);

        // Start Timer
        timer.startTimer();

        //Listeners
        binding.finishBut.setOnClickListener(this);
        binding.stopBut.setOnClickListener(this);

        Glide.with(getContext())
                .asGif()
                .load(sports_exercises.getImageUrl())
                .into(binding.freeGoalStartImage);

        usersViewModel = ViewModelProviders.of(Fragment_StartTraining_FreeGoal.this).get(UsersViewModel.class);
        exercisedetails_mvvm = ViewModelProviders.of(Fragment_StartTraining_FreeGoal.this).get(ExersiseDetails_MVVM.class);

        // جلب وزن الشخص لحساب السعرات الحرارية
        usersViewModel.getUsersByUid(uid).observe(this, new Observer<UsersInfo>() {
            @Override
            public void onChanged(UsersInfo usersInfo) {
                weight = usersInfo.getWeight();
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

                if(caloriesBurned != 0){
                    timer.timerTask.cancel();
                    Random random = new Random();

                    Exercise_Details exercise_details = new Exercise_Details(uid , UUID.randomUUID().toString() , String.valueOf(caloriesBurned) ,  SharedFunctions.getTimeAtTheMoment() , SharedFunctions.getDateAtTheMoment()
                            , sports_exercises.getName(), String.valueOf(timer.getTimeByHours()) );

                    exercisedetails_mvvm.insertExersiseDetails(exercise_details);

                    Executor executor = Executors.newSingleThreadExecutor();
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            FireStore_DataBase.insertOrUpdateExerciseDetails(exercise_details , getContext());
                        }
                    });

                    CustomDialog dialog = new CustomDialog(getContext() , timeGoal , String.format("%.2f", caloriesBurned));
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
                }else {
                    Toast.makeText(getContext(), "! للأسف لم تقم بأي نشاط ", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getContext(), Exercices_Activity.class);
                    intent.putExtra("obj", sports_exercises);
                   getActivity().startActivity(intent);
                    getActivity().finish();
                }
                break;
        }

        // Interface is used to transfear time to this fragment

    }

    @Override
    public void getTimer(String time) {
        timeGoal = time;
        binding.timeTvFreeGoal.setText(time);
        if (weight != null && sports_exercises.getMetValue() != null) {
            caloriesBurned = (Double.parseDouble(weight) * Double.parseDouble(sports_exercises.getMetValue()) * timer.getTimeByHours());
            binding.caloriesCountFreeGoal.setText(String.format("%.2f" , caloriesBurned) + "");
        }else {

        }
    }
}
