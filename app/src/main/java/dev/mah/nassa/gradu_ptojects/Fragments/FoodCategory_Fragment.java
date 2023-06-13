package dev.mah.nassa.gradu_ptojects.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import dev.mah.nassa.gradu_ptojects.Activityes.FoodSection_Activity;
import dev.mah.nassa.gradu_ptojects.Adapters.ParentFood_Adapter;
import dev.mah.nassa.gradu_ptojects.Constants.SharedFunctions;
import dev.mah.nassa.gradu_ptojects.DataBase.FireStore_DataBase;
import dev.mah.nassa.gradu_ptojects.MVVM.FoodCategory_MVVM;
import dev.mah.nassa.gradu_ptojects.Modles.FoodCategory;
import dev.mah.nassa.gradu_ptojects.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FoodCategory_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoodCategory_Fragment extends Fragment{
    private ParentFood_Adapter ParentAdapter;
    private RecyclerView.LayoutManager parentLayoutManager;
    private ArrayList<FoodCategory>foodCategories;
    private FoodCategory_MVVM foodCategoryMvvm;
    private boolean internetConnection;
    private RecyclerView parentFoodRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;

    public FoodCategory_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food_category_, container, false);
         parentFoodRecyclerView = view.findViewById(R.id.FragmentFood_parent_recyclerView);
         progressBar = view.findViewById(R.id.progress);
       internetConnection =  SharedFunctions.checkInternetConnection(getContext());
         swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        if(internetConnection){

            FireStore_DataBase.getAllDatas(getContext(), "Food_Category", new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {

                    progressBar.setVisibility(View.INVISIBLE);

                    foodCategories = (ArrayList<FoodCategory>) o;
                    parentFoodRecyclerView.setHasFixedSize(true);
                    parentLayoutManager = new LinearLayoutManager(getContext());
                    parentFoodRecyclerView.setLayoutManager(parentLayoutManager);
                    parentFoodRecyclerView.setAdapter(ParentAdapter);
                    ParentAdapter = new ParentFood_Adapter(foodCategories, getContext(),list1 -> {
                        Intent intent = new Intent(getContext(), FoodSection_Activity.class);
                        intent.putExtra("food", list1);
                        getContext().startActivity(intent);
                    });
                }
            });

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeRefreshLayout.setRefreshing(false);
                    FireStore_DataBase.getAllDatas(getContext(), "Food_Category", new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {

                            foodCategories = (ArrayList<FoodCategory>) o;
                            parentFoodRecyclerView.setHasFixedSize(true);
                            parentLayoutManager = new LinearLayoutManager(getContext());
                            parentFoodRecyclerView.setLayoutManager(parentLayoutManager);
                            ParentAdapter = new ParentFood_Adapter(foodCategories, getContext(),list1 -> {
                                Intent intent = new Intent(getContext(), FoodSection_Activity.class);
                                intent.putExtra("food", list1);
                                getContext().startActivity(intent);
                            });

                            parentFoodRecyclerView.setAdapter(ParentAdapter);

                        }
                    });

                }
            });
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        foodCategoryMvvm = ViewModelProviders.of(FoodCategory_Fragment.this).get(FoodCategory_MVVM.class);

        if(!internetConnection){
            foodCategoryMvvm.getAllFoodCategory().observe(FoodCategory_Fragment.this, new Observer<List<FoodCategory>>() {
                @Override
                public void onChanged(List<FoodCategory> foodCategoryList) {
                    progressBar.setVisibility(View.INVISIBLE);
                    parentFoodRecyclerView.setHasFixedSize(true);
                    parentLayoutManager = new LinearLayoutManager(getContext());
                    parentFoodRecyclerView.setLayoutManager(parentLayoutManager);
                    ParentAdapter = new ParentFood_Adapter((ArrayList<FoodCategory>) foodCategoryList, getContext(), list1 -> {
                        Intent intent = new Intent(getContext(), FoodSection_Activity.class);
                        intent.putExtra("food", list1);
                        getContext().startActivity(intent);
                    });
                    parentFoodRecyclerView.setAdapter(ParentAdapter);

                }
            });

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeRefreshLayout.setRefreshing(false);
                    foodCategoryMvvm.getAllFoodCategory().observe(FoodCategory_Fragment.this, new Observer<List<FoodCategory>>() {
                        @Override
                        public void onChanged(List<FoodCategory> foodCategoryList) {
                            parentFoodRecyclerView.setHasFixedSize(true);
                            parentLayoutManager = new LinearLayoutManager(getContext());
                            parentFoodRecyclerView.setLayoutManager(parentLayoutManager);
                            ParentAdapter = new ParentFood_Adapter((ArrayList<FoodCategory>) foodCategoryList, getContext(), list1 -> {
                                Intent intent = new Intent(getContext(), FoodSection_Activity.class);
                                intent.putExtra("food", list1);
                                getContext().startActivity(intent);
                            });
                            parentFoodRecyclerView.setAdapter(ParentAdapter);

                        }
                    });

                }
            });
        }
    }
}