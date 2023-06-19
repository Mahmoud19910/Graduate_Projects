package dev.mah.nassa.gradu_ptojects.Activityes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import dev.mah.nassa.gradu_ptojects.Adapters.MyMealList_Adapter;
import dev.mah.nassa.gradu_ptojects.Constants.SharedFunctions;
import dev.mah.nassa.gradu_ptojects.DataBase.FireStore_DataBase;
import dev.mah.nassa.gradu_ptojects.MVVM.My_Meal_MVVM;
import dev.mah.nassa.gradu_ptojects.Modles.FoodCategory;
import dev.mah.nassa.gradu_ptojects.Modles.My_Meal_List;
import dev.mah.nassa.gradu_ptojects.R;
import dev.mah.nassa.gradu_ptojects.databinding.ActivityMyMealListBinding;

public class MyMealList_Activity extends AppCompatActivity {

    private ActivityMyMealListBinding binding;
    private MyMealList_Adapter adapter;
    private  ArrayList<My_Meal_List> mealLists=new ArrayList<>();
    private int sum =0;
    private My_Meal_MVVM my_meal_mvvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyMealListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.myMealListTvSalary.setText("إجمالي السعرات الحرارية "+sum+" سعرة");
        my_meal_mvvm = ViewModelProviders.of(MyMealList_Activity.this).get(My_Meal_MVVM.class);
       boolean isConnect =  SharedFunctions.checkInternetConnection(MyMealList_Activity.this);

        //search
        binding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });

        //استدعاء ميثود العرض في الادابتر
        showAdapterRev();

       if(isConnect){
           //الحصول على البيانات من الفاير بيز
           FireStore_DataBase.getAllData(getApplicationContext(), "MyMeal",loadUid(), new OnSuccessListener() {
               @Override
               public void onSuccess(Object o) {

                   mealLists = (ArrayList<My_Meal_List>) o;
                   //عرض الأدابتر
                   showAdapterRev();

                   //جمع عدد سعرات
                   sum = 0;
                   for (int i = 0 ; i<mealLists.size() ; i++){
                       int s = Integer.parseInt(mealLists.get(i).getCaloriesMeal());
                       sum+=s;
                   }

                   binding.myMealListTvSalary.setText("إجمالي السعرات الحرارية "+sum+" سعرة");
               }
           });
       }else {
           my_meal_mvvm.getAllMy_Meal_List().observe(MyMealList_Activity.this, new Observer<List<My_Meal_List>>() {
               @Override
               public void onChanged(List<My_Meal_List> my_meal_lists) {

                   // created for recycler view.
                   mealLists = (ArrayList<My_Meal_List>) my_meal_lists;
                   showAdapterRev();

                   //جمع عدد سعرات
                   sum = 0;
                   for (int i = 0 ; i<my_meal_lists.size() ; i++){
                       int s = Integer.parseInt(my_meal_lists.get(i).getCaloriesMeal());
                       sum+=s;
                   }
                   binding.myMealListTvSalary.setText("إجمالي السعرات الحرارية "+sum+" سعرة");
               }
           });
       }


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
                      if(SharedFunctions.checkInternetConnection(MyMealList_Activity.this)){
                          mealLists.add((My_Meal_List) o);
                          showAdapterRev();
                          //جمع عدد سعرات
                          sum = 0;
                          for (int i = 0 ; i<mealLists.size() ; i++){
                              int s = Integer.parseInt(mealLists.get(i).getCaloriesMeal());
                              sum+=s;
                          }
                          binding.myMealListTvSalary.setText("إجمالي السعرات الحرارية "+sum+" سعرة");
                      }else {
                          my_meal_mvvm.insertMy_Meal_List((My_Meal_List) o);

                      }

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

      //search Listener
      binding.search.setOnSearchClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              binding.myMealTvTitle.setVisibility(View.GONE);
          }
      });
        //search Listener
        binding.search.setOnCloseListener(new SearchView.OnCloseListener() {
          @Override
          public boolean onClose() {

              binding.myMealTvTitle.setVisibility(View.VISIBLE);
              return false;
          }
      });

    }
    //ميثود لععرض البيانات في الادابتر
     private void showAdapterRev(){
      adapter = new MyMealList_Adapter(MyMealList_Activity.this,mealLists,binding.tvEmpty,binding.toolbar);
      binding.myMealListRvRecycler.setHasFixedSize(true);
      LinearLayoutManager layoutManager = new LinearLayoutManager(null);
      layoutManager.setReverseLayout(true);
      layoutManager.setStackFromEnd(true);
      binding.myMealListRvRecycler.setLayoutManager(layoutManager);
      binding.myMealListRvRecycler.setAdapter(adapter);
  }
    // جلب رقم المعرف للمستخد
    private String loadUid() {
        SharedPreferences sharedPreferences = getSharedPreferences("saveUid", Context.MODE_PRIVATE);
        return sharedPreferences.getString("uid", "");
    }
}