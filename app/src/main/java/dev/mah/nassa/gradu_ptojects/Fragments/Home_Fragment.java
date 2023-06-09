package dev.mah.nassa.gradu_ptojects.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.CountDownTimer;
import android.os.Handler;
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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dev.mah.nassa.gradu_ptojects.Activityes.ActivitesStats;
import dev.mah.nassa.gradu_ptojects.Activityes.Exercices_Activity;
import dev.mah.nassa.gradu_ptojects.Activityes.FoodSection_Activity;
import dev.mah.nassa.gradu_ptojects.Activityes.Home_Activity;
import dev.mah.nassa.gradu_ptojects.Adapters.OfferMealHomeHZ_Adapter;
import dev.mah.nassa.gradu_ptojects.Adapters.ParentFood_Adapter;
import dev.mah.nassa.gradu_ptojects.Constants.LanguageUtils;
import dev.mah.nassa.gradu_ptojects.Constants.PersonActivityArray;
import dev.mah.nassa.gradu_ptojects.Constants.SharedFunctions;
import dev.mah.nassa.gradu_ptojects.Constants.Vital_Equations;
import dev.mah.nassa.gradu_ptojects.DataBase.FireStore_DataBase;
import dev.mah.nassa.gradu_ptojects.Interfaces.SetStepsCountInActivity;
import dev.mah.nassa.gradu_ptojects.MVVM.ExersiseDetails_MVVM;
import dev.mah.nassa.gradu_ptojects.MVVM.FoodCategory_MVVM;
import dev.mah.nassa.gradu_ptojects.MVVM.My_Meal_MVVM;
import dev.mah.nassa.gradu_ptojects.MVVM.UsersHealthInfoViewModel;
import dev.mah.nassa.gradu_ptojects.MVVM.UsersViewModel;
import dev.mah.nassa.gradu_ptojects.Modles.Exercise_Details;
import dev.mah.nassa.gradu_ptojects.Modles.FoodCategory;
import dev.mah.nassa.gradu_ptojects.Modles.My_Meal_List;
import dev.mah.nassa.gradu_ptojects.Modles.Sports_Exercises;
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
public class Home_Fragment extends Fragment implements View.OnClickListener {

    private FragmentHomeBinding binding;
    private ProgressiveGauge progressiveGauge;
    private String uid;
    private UsersViewModel usersViewModel;
    private UsersInfo usersInfo;
    private UsersHealthInfoViewModel usersHealthInfoViewModel;
    private ExersiseDetails_MVVM exersiseDetails_mvvm;
    private double totalCaloBurned;
    private double totalCaloEarend;
    private Sports_Exercises sports_exercises;
    private SetStepsCountInActivity stepsCountInActivityListener;
    private ArrayList<FoodCategory>foodCategories;
    private Handler sHandler = new Handler();
    private FoodCategory_MVVM foodCategoryMvvm;
    private My_Meal_MVVM my_meal_mvvm;

    public Home_Fragment() {
    }

    public Home_Fragment(String uid) {
        this.uid = uid;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof SetStepsCountInActivity ){
            stepsCountInActivityListener = (SetStepsCountInActivity) context;
        }
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

        LanguageUtils.changeLanguage(getContext() , "en");
         sports_exercises  = new Sports_Exercises("M4zajfIsrYEKivgGO4vO" , "تمارين منزلية عامة " ,
                "لايوجد " , "https://firebasestorage.googleapis.com/v0/b/graduate-project-c9979.appspot.com/o/exercises%2F3bfbb6ae-db1a-4ccb-b4e0-d41de16057c2?alt=media&token=dc9e4888-0426-480e-bd04-2c56f36e70d5" ,
                "3.8");

        usersViewModel = ViewModelProviders.of(Home_Fragment.this).get(UsersViewModel.class);
        usersHealthInfoViewModel = ViewModelProviders.of(Home_Fragment.this).get(UsersHealthInfoViewModel.class);
        exersiseDetails_mvvm = ViewModelProviders.of(Home_Fragment.this).get(ExersiseDetails_MVVM.class);
        foodCategoryMvvm = ViewModelProviders.of(Home_Fragment.this).get(FoodCategory_MVVM.class);
        my_meal_mvvm = ViewModelProviders.of(Home_Fragment.this).get(My_Meal_MVVM.class);

        progressiveGauge = binding.awesomeSpeedomete;
        binding.caloeiLayout.setVisibility(View.INVISIBLE);
        binding.weightLayout.setVisibility(View.INVISIBLE);
        binding.watertLayout.setVisibility(View.INVISIBLE);
        binding.cardGoals.setVisibility(View.INVISIBLE);
        binding.progress.setVisibility(View.VISIBLE);
        binding.caloriLayout.setVisibility(View.INVISIBLE);
        binding.weightLay.setVisibility(View.INVISIBLE);

        // Listeners
        binding.timeBut.setOnClickListener(this);
        binding.freeBut.setOnClickListener(this);
        binding.caloriBut.setOnClickListener(this);
        binding.stepsBut.setOnClickListener(this);
        binding.startBurned.setOnClickListener(this);


       CountDownTimer countDownTimer = new CountDownTimer(1000000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                int timer = (int) (millisUntilFinished / 1000);

                switch (timer){
                    case 996:
                        binding.progress.setVisibility(View.INVISIBLE);

                        binding.caloeiLayout.setVisibility(View.VISIBLE);
                        binding.caloriLayout.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.BounceInLeft).duration(1000).repeat(0).playOn(binding.caloeiLayout);
                        YoYo.with(Techniques.BounceInLeft).duration(1000).repeat(0).playOn(binding.caloriLayout);

                        binding.cardGoals.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.RollIn).duration(700).repeat(0).playOn(binding.cardGoals);



                        binding.weightLayout.setVisibility(View.VISIBLE);
                        binding.weightLay.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.ZoomInUp).duration(900).repeat(0).playOn(binding.weightLayout);
                        YoYo.with(Techniques.ZoomInUp).duration(900).repeat(0).playOn(binding.weightLay);

                        binding.watertLayout.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.SlideInLeft).duration(900).repeat(0).playOn(binding.watertLayout);
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
              String freeBodyMass =   Vital_Equations.calculateFreeBodyMass(usersInfo.getLength(), usersInfo.getWeight());
                binding.w.setText(freeBodyMass + "");

                if(freeBodyMass.equals("وزنك ضمن الحد الطبيعي حافظ على هذا المستوى")){
                    binding.weightPerfect.setText(usersInfo.getWeight() +"كجم");
                }else {
                    double weightPerfect = Vital_Equations.calculatePerfectWeight(usersInfo.getLength());
                    binding.weightPerfect.setText(String.format("%.2f" , weightPerfect)+" كجم ");
                }
            }
        });


        //  جلب بيانات المستخدم
        usersHealthInfoViewModel.getUsersHealthById(uid).observe(this, new Observer<Users_Health_Info>() {
            @Override
            public void onChanged(Users_Health_Info users_health_info) {

                binding.calorDailyRequirment.setText(String.format("%.2f"+"  سعرة ", users_health_info.getCaloriesNumber()) + "  ");
                binding.waterQuan.setText(String.format("لتر " + "%.2f", users_health_info.getWaterDrink()) + "  ");
                binding.caloriesGained.setText(String.format("%.2f"+"سعرة ", users_health_info.getCaloriesGained()) + "  ");
                binding.caloriNeedToLooseWeight.setText(String.format("%.2f"+"  سعرة ", (users_health_info.getCaloriesNumber()-1000))+"");
                binding.caloriNeedToIncreaseWeight.setText(String.format( "%.2f"+"  سعرة ", (users_health_info.getCaloriesNumber()+1000))+"");


            }
        });


        // جلب السعرات المحروقة  خلال اليوم
        exersiseDetails_mvvm.getExerciseByDateAndUid(uid, SharedFunctions.getDateAtTheMoment()).observe(this, new Observer<List<Exercise_Details>>() {
            @Override
            public void onChanged(List<Exercise_Details> exercise_details) {

                for (Exercise_Details details : exercise_details) {
                    if (details.getCaloriesBurned() != null || !details.getCaloriesBurned().isEmpty()){
                        double calo = Double.parseDouble(details.getCaloriesBurned());
                        totalCaloBurned += calo;
                    }

                }

                binding.burnedCalories.setText(String.format("%.2f", totalCaloBurned) + "سعرة");
            }
        });

        my_meal_mvvm.getMy_Meal_ListByUidAndDate(uid , SharedFunctions.getDateAtTheMoment()).observe(Home_Fragment.this, new Observer<List<My_Meal_List>>() {
            @Override
            public void onChanged(List<My_Meal_List> my_meal_lists) {

                for(My_Meal_List mealList : my_meal_lists){

                    if(!mealList.getCaloriesMeal().isEmpty() || mealList.getCaloriesMeal()!=null){
                        NumberFormat numberFormat = DecimalFormat.getInstance(Locale.ENGLISH); // Use English locale to parse decimal numbers
                        try {
                            double cal = numberFormat.parse(mealList.getCaloriesMeal()).doubleValue();
                            totalCaloEarend += cal;
                        } catch (ParseException e) {
                            // Handle the ParseException, e.g., log an error or show an error message
                        }
                    }
                }

                binding.caloriesGained.setText(String.format("%.2f", totalCaloEarend) + "سعرة");
            }
        });

        // جلب الوجبات لعرضها بشكل أفقي
        foodCategoryMvvm.getAllFoodCategory().observe(Home_Fragment.this, new Observer<List<FoodCategory>>() {
            @Override
            public void onChanged(List<FoodCategory> foodCategoryList) {
                foodCategories = (ArrayList<FoodCategory>) foodCategoryList;
                binding.fragmentHomeViewPager2.setAdapter(new OfferMealHomeHZ_Adapter(getContext() , foodCategories , binding.fragmentHomeViewPager2));
                binding.fragmentHomeViewPager2.setOffscreenPageLimit(3);
                binding.fragmentHomeViewPager2.setCurrentItem(1,false);
                binding.fragmentHomeViewPager2.setClipChildren(false);
                binding.fragmentHomeViewPager2.setClipToPadding(false);
                binding.fragmentHomeViewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
                CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
                compositePageTransformer.addTransformer(new MarginPageTransformer(40));
                compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
                    @Override
                    public void transformPage(@NonNull View page, float position) {
                        float r = 1 - Math.abs(position);
                        page.setScaleY(0.85f + r * 0.15f);

                    }
                });

                binding.fragmentHomeViewPager2.setPageTransformer(compositePageTransformer);
                binding.fragmentHomeViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        sHandler.removeCallbacks(runnable);
                        sHandler.postDelayed(runnable , 2000);
                    }
                });
            }
        });

    }

    //بناء على نوع الهدف  Fragment حتى يتم ضبط وضع  Exercices_Activity يتم ارسال القيم الى
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.caloriBut:
                Intent intent = new Intent(getContext() , Exercices_Activity.class);
                intent.putExtra("obj" , sports_exercises);
                intent.putExtra("uid" , uid);
                intent.putExtra("fragmentIndex" , 1);
                getActivity().startActivity(intent);
                break;
            case R.id.timeBut:
                Intent intent1 = new Intent(getContext() , Exercices_Activity.class);
                intent1.putExtra("obj" , sports_exercises);
                intent1.putExtra("uid" , uid);
                intent1.putExtra("fragmentIndex" , 0);
                getActivity().startActivity(intent1);
                break;
            case R.id.freeBut:
                Intent intent2 = new Intent(getContext() , Exercices_Activity.class);
                intent2.putExtra("obj" , sports_exercises);
                intent2.putExtra("uid" , uid);
                intent2.putExtra("fragmentIndex" , 2);
                getActivity().startActivity(intent2);
                break;

            case R.id.stepsBut:
                stepsCountInActivityListener.setStepsListener(2);
                break;

            case R.id.startBurned:
                Intent intent3 = new Intent(getContext() , Exercices_Activity.class);
                intent3.putExtra("obj" , sports_exercises);
                intent3.putExtra("uid" , uid);
                intent3.putExtra("fragmentIndex" , 1);
                getActivity().startActivity(intent3);
                break;
        }
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            binding.fragmentHomeViewPager2.setCurrentItem(binding.fragmentHomeViewPager2.getCurrentItem() + 1);
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        sHandler.postDelayed(runnable , 2000);
    }

    @Override
    public void onResume() {
        super.onResume();
        sHandler.postDelayed(runnable , 2000);
    }
}
