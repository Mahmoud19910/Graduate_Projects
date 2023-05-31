package dev.mah.nassa.gradu_ptojects.Fragments;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.anastr.speedviewlib.ProgressiveGauge;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dev.mah.nassa.gradu_ptojects.Activityes.ActivitesStats;
import dev.mah.nassa.gradu_ptojects.Activityes.Home_Activity;
import dev.mah.nassa.gradu_ptojects.Constants.LanguageUtils;
import dev.mah.nassa.gradu_ptojects.Constants.PersonActivityArray;
import dev.mah.nassa.gradu_ptojects.Constants.SharedFunctions;
import dev.mah.nassa.gradu_ptojects.Constants.Vital_Equations;
import dev.mah.nassa.gradu_ptojects.MVVM.ExersiseDetails_MVVM;
import dev.mah.nassa.gradu_ptojects.MVVM.UsersHealthInfoViewModel;
import dev.mah.nassa.gradu_ptojects.MVVM.UsersViewModel;
import dev.mah.nassa.gradu_ptojects.Modles.Exercise_Details;
import dev.mah.nassa.gradu_ptojects.Modles.UsersInfo;
import dev.mah.nassa.gradu_ptojects.Modles.Users_Health_Info;
import dev.mah.nassa.gradu_ptojects.R;
import dev.mah.nassa.gradu_ptojects.databinding.ActivityActivitesStatsBinding;
import dev.mah.nassa.gradu_ptojects.databinding.FragmentHomeBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home_Fragment extends Fragment {

    private FragmentHomeBinding binding;
    private ProgressiveGauge progressiveGauge;
    private String uid;
    private UsersViewModel usersViewModel;
    private UsersInfo usersInfo;
    private UsersHealthInfoViewModel usersHealthInfoViewModel;
    private ExersiseDetails_MVVM exersiseDetails_mvvm;
    private double totalCaloBurned;


    public Home_Fragment() {
    }

    public Home_Fragment(String uid) {
        this.uid = uid;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater);

        LanguageUtils.changeLanguage(requireContext(), "en");
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        usersViewModel = ViewModelProviders.of(Home_Fragment.this).get(UsersViewModel.class);
        usersHealthInfoViewModel = ViewModelProviders.of(Home_Fragment.this).get(UsersHealthInfoViewModel.class);
        exersiseDetails_mvvm = ViewModelProviders.of(Home_Fragment.this).get(ExersiseDetails_MVVM.class);

        progressiveGauge = binding.awesomeSpeedomete;
        binding.caloeiLayout.setVisibility(View.INVISIBLE);
        binding.weightLayout.setVisibility(View.INVISIBLE);
        binding.watertLayout.setVisibility(View.INVISIBLE);

       CountDownTimer countDownTimer = new CountDownTimer(1000000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                int timer = (int) (millisUntilFinished / 1000);

                switch (timer){
                    case 996:
                        binding.caloeiLayout.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.ZoomInDown).duration(700).repeat(0).playOn(binding.caloeiLayout);
                        break;
                    case 994:
                        binding.weightLayout.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.ZoomInUp).duration(700).repeat(0).playOn(binding.weightLayout);

                        break;
                    case 992:
                        binding.watertLayout.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.SlideInLeft).duration(700).repeat(0).playOn(binding.watertLayout);
                        break;
                }
            }

            @Override
            public void onFinish() {

            }

        };
        countDownTimer.start();

        usersViewModel.getUsersByUid(uid).observe(this, new Observer<UsersInfo>() {
            @Override
            public void onChanged(UsersInfo usersInfo) {
                progressiveGauge.speedTo((float) Integer.parseInt(usersInfo.getWeight()));
                binding.w.setText(Vital_Equations.calculateFreeBodyMass(usersInfo.getLength(), usersInfo.getWeight()) + "");
            }
        });


        //  جلب بيانات المستخدم
        usersHealthInfoViewModel.getUsersHealthById(uid).observe(this, new Observer<Users_Health_Info>() {
            @Override
            public void onChanged(Users_Health_Info users_health_info) {

                Toast.makeText(getContext(), users_health_info.getUserId() + "Home Uid", Toast.LENGTH_LONG).show();
                binding.calorDailyRequirment.setText(String.format("kca " + "%.2f", users_health_info.getCaloriesNumber()) + "  ");
                binding.waterQuan.setText(String.format("لتر " + "%.2f", users_health_info.getWaterDrink()) + "  ");
                binding.caloriesGained.setText(String.format("kca " + "%.2f", users_health_info.getCaloriesGained()) + "  ");


            }
        });


        // جلب السعرات المحروقة  خلال اليوم
        exersiseDetails_mvvm.getExerciseByDateAndUid(uid, SharedFunctions.getDateAtTheMoment()).observe(this, new Observer<List<Exercise_Details>>() {
            @Override
            public void onChanged(List<Exercise_Details> exercise_details) {

                for (Exercise_Details details : exercise_details) {
                    double calo = Double.parseDouble(details.getCaloriesBurned());
                    totalCaloBurned += calo;
                }

                binding.burnedCalories.setText(String.format("%.2f", totalCaloBurned) + "سعرة");
            }
        });


    }
}