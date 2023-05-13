package dev.mah.nassa.gradu_ptojects.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import de.hdodenhof.circleimageview.CircleImageView;
import dev.mah.nassa.gradu_ptojects.Modles.Sports_Exercises;
import dev.mah.nassa.gradu_ptojects.R;
import dev.mah.nassa.gradu_ptojects.databinding.FragmentCaloriesGoalBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_CaloriesGoal#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_CaloriesGoal extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  FragmentCaloriesGoalBinding binding;
    Sports_Exercises sports_exercises;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCaloriesGoalBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        Bundle bundle = getArguments();
        sports_exercises = (Sports_Exercises) bundle.getSerializable("obj");

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Toast.makeText(getContext(), sports_exercises.getImageUrl(), Toast.LENGTH_SHORT).show();
        CircularImageView circleImageView = binding.trainingImage;
        Glide.with(getContext())
                .asGif() // Enable support for GIF type
                .load(sports_exercises.getImageUrl())
                .into(circleImageView);
    }
}