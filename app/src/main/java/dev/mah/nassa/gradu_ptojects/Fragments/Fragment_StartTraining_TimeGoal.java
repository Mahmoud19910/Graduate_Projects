package dev.mah.nassa.gradu_ptojects.Fragments;

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

import java.text.DecimalFormat;
import java.util.Random;
import java.util.UUID;

import dev.mah.nassa.gradu_ptojects.Constants.CustomDialog;
import dev.mah.nassa.gradu_ptojects.Constants.LanguageUtils;
import dev.mah.nassa.gradu_ptojects.Constants.SharedFunctions;
import dev.mah.nassa.gradu_ptojects.Constants.StopwatchTimer;
import dev.mah.nassa.gradu_ptojects.Constants.Vital_Equations;
import dev.mah.nassa.gradu_ptojects.DataBase.FireStore_DataBase;
import dev.mah.nassa.gradu_ptojects.Interfaces.TimerListener;
import dev.mah.nassa.gradu_ptojects.MVVM.ExersiseDetails_MVVM;
import dev.mah.nassa.gradu_ptojects.MVVM.UsersViewModel;
import dev.mah.nassa.gradu_ptojects.Modles.Exercise_Details;
import dev.mah.nassa.gradu_ptojects.Modles.Sports_Exercises;
import dev.mah.nassa.gradu_ptojects.Modles.UsersInfo;
import dev.mah.nassa.gradu_ptojects.R;
import dev.mah.nassa.gradu_ptojects.databinding.FragmentStartTrainingTimeGoalBinding;


public class Fragment_StartTraining_TimeGoal extends Fragment implements TimerListener, View.OnClickListener {

    private FragmentStartTrainingTimeGoalBinding binding;
    private Sports_Exercises sports_exercises;
    private String uid, timeGoal;
    private StopwatchTimer timer;
    private TimerListener listener;
    private UsersViewModel usersViewModel;
    private String weight;
    private boolean isStart = true;
    private double caloriesBurned;
    private int progressedValue = 0;
    private ExersiseDetails_MVVM exercisedetails_mvvm;
    private Random random;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStartTrainingTimeGoalBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        LanguageUtils.changeLanguage(getContext(), "en");

        Bundle bundle = getArguments();
        sports_exercises = (Sports_Exercises) bundle.get("obj");
        timeGoal = (String) bundle.get("value");
        uid = (String) bundle.get("uid");
        timer = new StopwatchTimer(this);//Instance Of Timer
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Glide.with(getContext())
                .asGif()
                .load(sports_exercises.getImageUrl())
                .into(binding.timeStartImage);

        // تشغيل المؤقت عند البداية
        timer.startTimer();

        //progress bar وضع قيمة
        binding.progressVerifyAccount.setMax(((int) (Double.parseDouble(timeGoal) * 10000)));
        Toast.makeText(getContext(), ((int) (Double.parseDouble(timeGoal) * 10000)) + "max", Toast.LENGTH_SHORT).show();

        //UsersViewModel
        usersViewModel = ViewModelProviders.of(Fragment_StartTraining_TimeGoal.this).get(UsersViewModel.class);
        exercisedetails_mvvm = ViewModelProviders.of(Fragment_StartTraining_TimeGoal.this).get(ExersiseDetails_MVVM.class);

        usersViewModel.getUsersByUid(uid).observe(this, new Observer<UsersInfo>() {
            @Override
            public void onChanged(UsersInfo usersInfo) {
                weight = usersInfo.getWeight(); // جلب الوزن لحساب السعرات الحرارية أثناء التمرين
            }
        });

        //Listeners
        binding.finishBut.setOnClickListener(this);
        binding.stopBut.setOnClickListener(this);

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                SharedFunctions.finishTrainingDialog(timeGoal, String.valueOf(caloriesBurned), getContext(), uid , sports_exercises, new OnSuccessListener() {
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
                SharedFunctions.finishTrainingDialog(timeGoal, String.valueOf(timer.getTimeByHours()), getContext(), uid , sports_exercises, new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {

                        if (((Boolean) o) == true) {
                            // ايقاف المؤقت حفظ النشاط في قاعدة البيانات
                            timer.timerTask.cancel();
                             random = new Random();

                            Exercise_Details exercise_details = new Exercise_Details(uid , UUID.randomUUID().toString() , String.format("%.2f", caloriesBurned) ,  SharedFunctions.getTimeAtTheMoment() , SharedFunctions.getDateAtTheMoment()
                                    , sports_exercises.getName() , String.valueOf(timer.getTimeByHours()));
                            FireStore_DataBase.insertOrUpdateExerciseDetails(exercise_details, getContext());
                            exercisedetails_mvvm.insertExersiseDetails(exercise_details);

                        } else {
                            timer.timerTask.cancel();
                        }
                    }
                });
                break;
        }
    }


    // Interface is used to transfear time to this fragment to calculate calories burned
    @Override
    public void getTimer(String time) {
        binding.timerTimeGoal.setText(time);
        if (weight != null && sports_exercises.getMetValue() != null) {
            caloriesBurned = Vital_Equations.calculateCaloriesBurnd(Double.parseDouble(weight), Double.parseDouble(sports_exercises.getMetValue()), timer);
            binding.caloriesTimeGoalTv.setText(String.format("%.2f" , caloriesBurned)+" ");
            binding.progressVerifyAccount.setProgress(progressedValue += 3, true);

        }
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        double timrHourse = Double.parseDouble(decimalFormat.format(Double.parseDouble(timeGoal)));

//        Toast.makeText(getContext(), timer.getTimeByHours()+"by hourse", Toast.LENGTH_SHORT).show();
//        Toast.makeText(getContext(), timrHourse+"goal", Toast.LENGTH_SHORT).show();
        // عند الوصول الى الهدف يتم انهاء النشاط
        if (timer.getTimeByHours() == timrHourse) {
            timer.timerTask.cancel();

            Exercise_Details exercise_details = new Exercise_Details(uid , UUID.randomUUID().toString() , String.valueOf(caloriesBurned) ,  SharedFunctions.getTimeAtTheMoment() , SharedFunctions.getDateAtTheMoment()
                    , sports_exercises.getName() , String.valueOf(timer.getTimeByHours()) );
            exercisedetails_mvvm.insertExersiseDetails(exercise_details);
            FireStore_DataBase.insertOrUpdateExerciseDetails(exercise_details, getContext());
            CustomDialog dialog = new CustomDialog(getContext(), time, String.format("%.2f", caloriesBurned));
            dialog.show();

        }

    }


}