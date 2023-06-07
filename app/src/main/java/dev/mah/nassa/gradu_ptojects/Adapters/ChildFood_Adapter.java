package dev.mah.nassa.gradu_ptojects.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import dev.mah.nassa.gradu_ptojects.Constants.SharedFunctions;
import dev.mah.nassa.gradu_ptojects.Modles.FoodCategory;
import dev.mah.nassa.gradu_ptojects.R;

public class ChildFood_Adapter extends RecyclerView.Adapter<ChildFood_Adapter.MyViewHolder>{
    private ArrayList <FoodCategory> foodCategoryArrayList ;
    private Context context;

    public ChildFood_Adapter(ArrayList<FoodCategory> foodCategoryArrayList, Context context) {
        this.foodCategoryArrayList = foodCategoryArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_food_recyclerview_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        FoodCategory foodCategory = foodCategoryArrayList.get(position);
        Glide.with(context)
                .load(foodCategory.getFilePath())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.foodImages);
        holder.foodName.setText(foodCategory.getNameMeal());

        holder.foodImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedFunctions.createFoodDialog(holder.itemView.getContext() ,loadUid(), foodCategoryArrayList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodCategoryArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView foodImages;
        public TextView foodName;

        public MyViewHolder(View itemView) {
            super(itemView);
            foodImages = itemView.findViewById(R.id.child_Food_iv_image);
            foodName = itemView.findViewById(R.id.child_Food_tv_name);
        }
    }


    // جلب رقم المعرف للمستخد
    private String loadUid(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("saveUid" , Context.MODE_PRIVATE);
        return sharedPreferences.getString("uid" , "");
    }
}
