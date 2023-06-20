package dev.mah.nassa.gradu_ptojects.Activityes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import dev.mah.nassa.gradu_ptojects.Adapters.ExerciseDetails_Adapter;
import dev.mah.nassa.gradu_ptojects.Constants.SharedFunctions;
import dev.mah.nassa.gradu_ptojects.DataBase.FireStore_DataBase;
import dev.mah.nassa.gradu_ptojects.MVVM.ExersiseDetails_MVVM;
import dev.mah.nassa.gradu_ptojects.Modles.Exercise_Details;
import dev.mah.nassa.gradu_ptojects.R;
import dev.mah.nassa.gradu_ptojects.ViewHolder.ExerciseDetails_ViewHolder;
import dev.mah.nassa.gradu_ptojects.databinding.ActivityActivitesStatsBinding;

public class ActivitesStats extends AppCompatActivity {

    private RecyclerView recyclerViewDetails;
    private ActivityActivitesStatsBinding binding;
    private ExersiseDetails_MVVM exersiseDetails_mvvm;
    private ExerciseDetails_Adapter exerciseDetails_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityActivitesStatsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        recyclerViewDetails = binding.recyclerDetails;
        exersiseDetails_mvvm = ViewModelProviders.of(ActivitesStats.this).get(ExersiseDetails_MVVM.class);

        binding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                exerciseDetails_adapter.getFilter().filter(newText);
                return true;
            }
        });

        boolean isConnected = SharedFunctions.checkInternetConnection(ActivitesStats.this);

        if(isConnected){

            FireStore_DataBase.getAllExerciseDetails_Lists(new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {
                    List<Exercise_Details> detailsList = (List<Exercise_Details>) o;

                    exerciseDetails_adapter = new ExerciseDetails_Adapter(R.layout.exercise_details_item_design , detailsList , ActivitesStats.this , binding.linearAppBar );
                    LinearLayoutManager layoutManager = new LinearLayoutManager(ActivitesStats.this);
                    layoutManager.setReverseLayout(true);
                    layoutManager.setStackFromEnd(true);
                    layoutManager.setOrientation(RecyclerView.VERTICAL);
                    recyclerViewDetails.setLayoutManager(layoutManager);
                    recyclerViewDetails.setAdapter(exerciseDetails_adapter);
                }
            });
        }else {
            exersiseDetails_mvvm.getAllExersiseDetails().observe(this, new Observer<List<Exercise_Details>>() {
                @Override
                public void onChanged(List<Exercise_Details> exercise_details) {
                    exerciseDetails_adapter = new ExerciseDetails_Adapter(R.layout.exercise_details_item_design , exercise_details , ActivitesStats.this , binding.linearAppBar );
                    LinearLayoutManager layoutManager = new LinearLayoutManager(ActivitesStats.this);
                    layoutManager.setReverseLayout(true);
                    layoutManager.setStackFromEnd(true);
                    layoutManager.setOrientation(RecyclerView.VERTICAL);
                    recyclerViewDetails.setLayoutManager(layoutManager);
                    recyclerViewDetails.setAdapter(exerciseDetails_adapter);
                }
            });
        }


        binding.backActivityExercices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    // جلب رقم المعرف للمستخد
    private String loadUid() {
        SharedPreferences sharedPreferences = getSharedPreferences("saveUid", Context.MODE_PRIVATE);
        return sharedPreferences.getString("uid", "");
    }
}