package dev.mah.nassa.gradu_ptojects.Activityes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.io.Serializable;

import dev.mah.nassa.gradu_ptojects.Fragments.Fragment_CaloriesGoal;
import dev.mah.nassa.gradu_ptojects.Modles.Sports_Exercises;
import dev.mah.nassa.gradu_ptojects.R;
import dev.mah.nassa.gradu_ptojects.databinding.ActivityExercicesBinding;
import dev.mah.nassa.gradu_ptojects.databinding.FragmentTrainingBinding;

public class Exercices_Activity extends AppCompatActivity {

    ActivityExercicesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExercicesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        Sports_Exercises sports_exercises = (Sports_Exercises) intent.getSerializableExtra("obj");

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle= new Bundle();
        bundle.putSerializable("obj" , sports_exercises);
       Fragment_CaloriesGoal fragment_caloriesGoal =  new Fragment_CaloriesGoal();
       fragment_caloriesGoal.setArguments(bundle);
        transaction.replace(R.id.framExercices,fragment_caloriesGoal);
        transaction.commit();

        binding.titleExercices.setText(sports_exercises.getName());
    }
}