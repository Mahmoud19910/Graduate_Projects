package dev.mah.nassa.gradu_ptojects.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dev.mah.nassa.gradu_ptojects.Modles.Exercise_Details;
import dev.mah.nassa.gradu_ptojects.Modles.FoodCategory;
import dev.mah.nassa.gradu_ptojects.Modles.My_Meal_List;
import dev.mah.nassa.gradu_ptojects.R;

public class MyMealList_Adapter extends RecyclerView.Adapter<MyMealList_Adapter.MyViewHolder>{

    private ArrayList<My_Meal_List>list;
    private Context context;
    private boolean dropDown=false;

    public MyMealList_Adapter(ArrayList<My_Meal_List> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_meal_list_recyclerview_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        My_Meal_List meal = list.get(position);
        holder.mealName.setText(meal.getNameMeal());
        holder.mealCalories.setText(meal.getCaloriesMeal());
        holder.mealWeightMeal.setText(meal.getWeight());
        holder.mealDATE.setText(meal.getDate());
        holder.mealTime.setText(meal.getTime());


        ViewGroup.LayoutParams layoutParams =  holder.tableLayout.getLayoutParams();
        if((position+1) != list.size()){
            layoutParams.height=0;
            holder.tableLayout.setLayoutParams(layoutParams);
            holder.iconDrop.setRotation(360);
        }else {
            layoutParams.height=450;
            holder.tableLayout.setLayoutParams(layoutParams);
            holder.iconDrop.setRotation(270);
        }

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dropDown==false){
                    layoutParams.height=0;
                    holder.iconDrop.setRotation(360);
                    holder.tableLayout.setLayoutParams(layoutParams);
                    dropDown=true;
                }else {
                    dropDown=false;
                    layoutParams.height=450;
                    holder.iconDrop.setRotation(270);
                    holder.tableLayout.setLayoutParams(layoutParams);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView mealName,mealCalories,mealWeightMeal,mealTime,mealDATE;
        public TableLayout tableLayout;
        public LinearLayoutCompat linearLayout;
        public ImageView iconDrop;
        public MyViewHolder(View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.myMealList_RV_tv_name);
            mealCalories = itemView.findViewById(R.id.myMealList_RV_tv_calories);
            mealWeightMeal = itemView.findViewById(R.id.myMealList_RV_tv_weightMeal);
            mealDATE = itemView.findViewById(R.id.myMealList_RV_dateTv);
            mealTime = itemView.findViewById(R.id.myMealList_RV_tv_time);
            tableLayout = itemView.findViewById(R.id.myMealList_RV_tb_tableDetails);
            linearLayout = itemView.findViewById(R.id.meal_linear);
            iconDrop = itemView.findViewById(R.id.myMealList_RV_im_dropIcon);
        }
    }
}
