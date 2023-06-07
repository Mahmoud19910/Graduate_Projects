package dev.mah.nassa.gradu_ptojects.Activityes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.Serializable;

import dev.mah.nassa.gradu_ptojects.Fragments.Fragment_CaloriesGoal;
import dev.mah.nassa.gradu_ptojects.Fragments.Fragment_FreeGoal;
import dev.mah.nassa.gradu_ptojects.Fragments.Fragment_TimeGoal;
import dev.mah.nassa.gradu_ptojects.Modles.Sports_Exercises;
import dev.mah.nassa.gradu_ptojects.R;
import dev.mah.nassa.gradu_ptojects.databinding.ActivityExercicesBinding;
import dev.mah.nassa.gradu_ptojects.databinding.FragmentTrainingBinding;

public class Exercices_Activity extends AppCompatActivity implements View.OnClickListener  {

   private ActivityExercicesBinding binding;
    private Sports_Exercises sports_exercises;
    private int fragmentIndex=1;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExercicesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
       sports_exercises = (Sports_Exercises) intent.getSerializableExtra("obj");
       uid=intent.getStringExtra("uid");
       fragmentIndex = intent.getIntExtra("fragmentIndex" , 1);


        // Set Default Fragment
        switch (fragmentIndex){
            case 0:
                replace(new Fragment_TimeGoal() , sports_exercises , 0 , uid);
                break;
            case 1:
                replace(new Fragment_CaloriesGoal(), sports_exercises , 1 , uid);
                break;
            case 2:
                replace(new Fragment_FreeGoal() , sports_exercises , 2 , uid);
                break;

        }
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        Bundle bundle= new Bundle();
//        bundle.putSerializable("obj" , sports_exercises);
//        bundle.putInt("indexFrag" , fragmentIndex);
//        bundle.putString("uid" , uid);
//
//        Fragment_CaloriesGoal fragment_caloriesGoal =  new Fragment_CaloriesGoal();
//       fragment_caloriesGoal.setArguments(bundle);
//        transaction.replace(R.id.framExercices,fragment_caloriesGoal);
//        transaction.commit();
//        binding.caloriesGoalIcon.setColorFilter(getResources().getColor(R.color.red));
//        binding.caloriesGoalText.setTextColor(getColor(R.color.black));

        binding.titleExercices.setText(sports_exercises.getName());

        //Listeners
        binding.freeGoalLayout.setOnClickListener(this);
        binding.caloriesGoalLayout.setOnClickListener(this);
        binding.timeGoalLayout.setOnClickListener(this);
        binding.backActivityExercices.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.timeGoal_Layout:
                this.fragmentIndex=0;
                replace(new Fragment_TimeGoal() , sports_exercises , fragmentIndex , uid);

                break;
            case R.id.caloriesGoal_Layout:
                this.fragmentIndex=1;
                replace(new Fragment_CaloriesGoal() , sports_exercises , fragmentIndex , uid);
                break;
            case R.id.freeGoal_Layout:
                this.fragmentIndex=2;
                replace(new Fragment_FreeGoal() , sports_exercises,fragmentIndex , uid);
                break;

            case R.id.backActivityExercices:
                startActivity(new Intent(Exercices_Activity.this , Home_Activity.class));
                finish();
                break;

        }
    }


    // Method to replace layout On Navigation
    private void replace(Fragment fragment_caloriesGoal , Sports_Exercises sports_exercises , int fragmentIndex , String uid) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle= new Bundle();
        bundle.putSerializable("obj" , sports_exercises);
        bundle.putInt("indexFrag" , fragmentIndex);
        bundle.putString("uid" , uid);
        fragment_caloriesGoal.setArguments(bundle);
        transaction.replace(R.id.framExercices,fragment_caloriesGoal);
        transaction.commit();
        switch (fragmentIndex){
            case 0:
                binding.timeGoalIcon.setColorFilter(getResources().getColor(R.color.red));
                binding.timeGoalText.setTextColor(getColor(R.color.black));

                binding.caloriesGoalIcon.setColorFilter(getResources().getColor(R.color.grey));
                binding.caloriesGoalText.setTextColor(getColor(R.color.grey));

                binding.freeGoalIcon.setColorFilter(getResources().getColor(R.color.grey));
                binding.freeGoalText.setTextColor(getColor(R.color.grey));
                break;

            case 1:
                binding.caloriesGoalIcon.setColorFilter(getResources().getColor(R.color.red));
                binding.caloriesGoalText.setTextColor(getColor(R.color.black));

                binding.timeGoalIcon.setColorFilter(getResources().getColor(R.color.grey));
                binding.timeGoalText.setTextColor(getColor(R.color.grey));

                binding.freeGoalIcon.setColorFilter(getResources().getColor(R.color.grey));
                binding.freeGoalText.setTextColor(getColor(R.color.grey));
                break;
            case 2:
                binding.freeGoalIcon.setColorFilter(getResources().getColor(R.color.red));
                binding.freeGoalText.setTextColor(getColor(R.color.black));

                binding.caloriesGoalIcon.setColorFilter(getResources().getColor(R.color.grey));
                binding.caloriesGoalText.setTextColor(getColor(R.color.grey));

                binding.timeGoalIcon.setColorFilter(getResources().getColor(R.color.grey));
                binding.timeGoalText.setTextColor(getColor(R.color.grey));
                break;

        }
    }
}