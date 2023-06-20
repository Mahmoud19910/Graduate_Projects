package dev.mah.nassa.gradu_ptojects.Adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dev.mah.nassa.gradu_ptojects.Activityes.FoodSection_Activity;
import dev.mah.nassa.gradu_ptojects.Constants.SharedFunctions;
import dev.mah.nassa.gradu_ptojects.Modles.Exercise_Details;
import dev.mah.nassa.gradu_ptojects.Modles.FoodCategory;
import dev.mah.nassa.gradu_ptojects.R;

public class FoodSection_Adapter extends RecyclerView.Adapter<FoodSection_Adapter.MyViewHolder>{
    private ArrayList<FoodCategory> foodCategoryArrayList;
    private Context context;
    private ArrayList<FoodCategory> copy_foodCategoryArrayList;


    public FoodSection_Adapter(ArrayList<FoodCategory> foodCategoryArrayList, Context context) {
        this.foodCategoryArrayList = foodCategoryArrayList;
        this.context = context;
        copy_foodCategoryArrayList=foodCategoryArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_section_recyclerview_items, parent, false);
        return new FoodSection_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        FoodCategory foodCategory = foodCategoryArrayList.get(position);
        Glide.with(context)
                .load(foodCategory.getFilePath())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.foodSectionImages);
        holder.foodSectionName.setText(foodCategory.getNameMeal());
        holder.foodSectionCalories.setText(foodCategory.getCaloriesMeal());
        holder.foodSectionSize.setText(foodCategory.getWeightMeal());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          SharedFunctions.createFoodDialog("FoodSection" , holder.itemView.getContext() ,loadUid(), foodCategoryArrayList.get(position));
            }
        });
        holder.foodSectionAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            SharedFunctions.createFoodDialog("FoodSection" , holder.itemView.getContext() ,loadUid(), foodCategoryArrayList.get(position));

            }
        });
    }

    //ميثود البحث لعمل فلترة اثناء ستخدام البحث
    public Filter getFilter() {

        Filter myFilter=new Filter() {
            FilterResults fr=new FilterResults();
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                ArrayList<FoodCategory> temp=new ArrayList<FoodCategory>();
                for(int i=0;i<copy_foodCategoryArrayList.size();i++){
                    FoodCategory c=copy_foodCategoryArrayList.get(i);
                    String stno=c.getDate()+"";
                    if(c.getNameMeal().toLowerCase().contains(charSequence.toString().toLowerCase())  ){
                        temp.add(c);
                    }
                }
                fr.values=temp;
                fr.count=temp.size();

                return fr;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults fr) {
                foodCategoryArrayList = (ArrayList<FoodCategory>) fr.values;
                notifyDataSetChanged();
            }
        };

        return myFilter;
    }



    @Override
    public int getItemCount() {
        return foodCategoryArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView foodSectionImages,foodSectionAdd;
        public TextView foodSectionName,foodSectionCalories,foodSectionSize;

        public MyViewHolder(View itemView) {
            super(itemView);
            foodSectionImages = itemView.findViewById(R.id.foodSection_iv_images);
            foodSectionAdd = itemView.findViewById(R.id.foodSection_iv_add);
            foodSectionName = itemView.findViewById(R.id.foodSection_tv_names);
            foodSectionCalories = itemView.findViewById(R.id.foodSection_tv_calories);
            foodSectionSize = itemView.findViewById(R.id.foodSection_tv_size);
        }
    }
    // جلب رقم المعرف للمستخد
    private String loadUid(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("saveUid" , Context.MODE_PRIVATE);
        return sharedPreferences.getString("uid" , "");
    }


}
