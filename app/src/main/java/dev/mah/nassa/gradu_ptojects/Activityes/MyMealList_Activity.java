package dev.mah.nassa.gradu_ptojects.Activityes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

import dev.mah.nassa.gradu_ptojects.Adapters.MyMealList_Adapter;
import dev.mah.nassa.gradu_ptojects.Constants.SharedFunctions;
import dev.mah.nassa.gradu_ptojects.DataBase.FireStore_DataBase;
import dev.mah.nassa.gradu_ptojects.Modles.FoodCategory;
import dev.mah.nassa.gradu_ptojects.Modles.My_Meal_List;
import dev.mah.nassa.gradu_ptojects.databinding.ActivityMyMealListBinding;

public class MyMealList_Activity extends AppCompatActivity {

    private ActivityMyMealListBinding binding;
    private MyMealList_Adapter adapter;
    private  ArrayList<My_Meal_List> mealLists;
    private int sum =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyMealListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //الحصول على البيانات من الفاير بيز
        FireStore_DataBase.getAllData(getApplicationContext(), "MyMeal",loadUid(), new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {

                mealLists = (ArrayList<My_Meal_List>) o;

                // created for recycler view.
                adapter = new MyMealList_Adapter(mealLists, getBaseContext());
                binding.myMealListRvRecycler.setHasFixedSize(true);
                LinearLayoutManager layoutManager = new LinearLayoutManager(null);
                layoutManager.setReverseLayout(true);
                layoutManager.setStackFromEnd(true);
                binding.myMealListRvRecycler.setLayoutManager(layoutManager);
                binding.myMealListRvRecycler.setAdapter(adapter);

                //جمع عدد سعرات
                sum = 0;
                for (int i = 0 ; i<mealLists.size() ; i++){
                    int s = Integer.parseInt(mealLists.get(i).getCaloriesMeal());
                    sum+=s;
                }
                binding.myMealListTvSalary.setText("إجمالي السعرات الحرارية "+sum+" سعرة");
            }
        });

        //اذا لم يكن هناك بيانات في الاري سيتم طباعة 0 للسعرات
     if (mealLists == null){
        binding.myMealListTvSalary.setText("إجمالي السعرات الحرارية "+sum+" سعرة");
     }

      //عند ضغط سيظهر ديلوك لأضافة الوجبة
      binding.addFoodList.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              SharedFunctions.createFoodDialog(MyMealList_Activity.this, loadUid(), new OnSuccessListener() {
                  @Override
                  public void onSuccess(Object o) {
                      mealLists.add((My_Meal_List) o);
                      adapter.notifyDataSetChanged();
                  }
              });

          }
      });


      //Back
      binding.myMealIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // جلب رقم المعرف للمستخد
    private String loadUid() {
        SharedPreferences sharedPreferences = getSharedPreferences("saveUid", Context.MODE_PRIVATE);
        Toast.makeText(this, "load", Toast.LENGTH_SHORT).show();
        return sharedPreferences.getString("uid", "");
    }
}