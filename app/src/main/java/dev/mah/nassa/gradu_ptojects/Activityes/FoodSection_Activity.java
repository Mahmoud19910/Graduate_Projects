package dev.mah.nassa.gradu_ptojects.Activityes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import dev.mah.nassa.gradu_ptojects.Adapters.FoodSection_Adapter;
import dev.mah.nassa.gradu_ptojects.Adapters.ParentFood_Adapter;
import dev.mah.nassa.gradu_ptojects.Modles.FoodCategory;
import dev.mah.nassa.gradu_ptojects.databinding.ActivityFoodSectionBinding;

public class FoodSection_Activity extends AppCompatActivity {
    private ActivityFoodSectionBinding binding;
    private FoodSection_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFoodSectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        ArrayList<FoodCategory> arrayList =(ArrayList<FoodCategory>) intent.getSerializableExtra("food");

        //الحصول على عنوان صفحة Title
        binding.foodSectionTvTitle.setText(arrayList.get(0).getDepartmentName());

        adapter = new FoodSection_Adapter(arrayList,getBaseContext());
        binding.foodSectionRecyclerView.setHasFixedSize(true);
        binding.foodSectionRecyclerView.setLayoutManager(new LinearLayoutManager(null));
        binding.foodSectionRecyclerView.setAdapter(adapter);

        //button Back
        binding.foodSectionIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}