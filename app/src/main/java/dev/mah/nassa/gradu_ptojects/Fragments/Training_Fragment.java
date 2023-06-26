package dev.mah.nassa.gradu_ptojects.Fragments;

import android.graphics.Shader;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import dev.mah.nassa.gradu_ptojects.Adapters.Exercices_Adapter;
import dev.mah.nassa.gradu_ptojects.Constants.SharedFunctions;
import dev.mah.nassa.gradu_ptojects.MVVM.FireStore_MVVM;
import dev.mah.nassa.gradu_ptojects.MVVM.SportExercise_MVVM;
import dev.mah.nassa.gradu_ptojects.Modles.Sports_Exercises;
import dev.mah.nassa.gradu_ptojects.R;
import dev.mah.nassa.gradu_ptojects.databinding.FragmentTrainingBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Training_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Training_Fragment extends Fragment {

    private FireStore_MVVM fireStore_mvvm;
    private FragmentTrainingBinding binding;
    private String uid;
    private SportExercise_MVVM sportExerciseMvvm;
    public Training_Fragment(String uid){
        this.uid=uid;
    }
    public Training_Fragment( ){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentTrainingBinding.inflate(inflater, container, false);
        fireStore_mvvm= ViewModelProviders.of(Training_Fragment.this).get(FireStore_MVVM.class);
        sportExerciseMvvm =ViewModelProviders.of(Training_Fragment.this).get(SportExercise_MVVM.class);





        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.recyclerExercices.setVisibility(View.INVISIBLE);
        binding.progress.setVisibility(View.VISIBLE);


        boolean isConnect = SharedFunctions.checkInternetConnection(getContext());
//        if(isConnect){
//            fireStore_mvvm.getAllSportsExercises(new OnSuccessListener<List<Sports_Exercises>>() {
//                @Override
//                public void onSuccess(List<Sports_Exercises> sports_exercises) {
//                    binding.recyclerExercices.setVisibility(View.VISIBLE);
//                    binding.progress.setVisibility(View.INVISIBLE);
//                    Exercices_Adapter exercices_adapter  = new Exercices_Adapter(R.layout.exercices_item_design , getContext() , sports_exercises,uid);
//                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//                    linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
//                    binding.recyclerExercices.setLayoutManager(linearLayoutManager);
//                    binding.recyclerExercices.setAdapter(exercices_adapter);
//                }
//            });
//        }else {
            binding.recyclerExercices.setVisibility(View.VISIBLE);
            binding.progress.setVisibility(View.INVISIBLE);

            sportExerciseMvvm.getAllSports_Exercises().observe(Training_Fragment.this, new Observer<List<Sports_Exercises>>() {
                @Override
                public void onChanged(List<Sports_Exercises> sports_exercises) {
                    Exercices_Adapter exercices_adapter  = new Exercices_Adapter(R.layout.exercices_item_design , getContext() , sports_exercises,uid);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
                    binding.recyclerExercices.setLayoutManager(linearLayoutManager);
                    binding.recyclerExercices.setAdapter(exercices_adapter);
                }
            });
//        }

    }
}