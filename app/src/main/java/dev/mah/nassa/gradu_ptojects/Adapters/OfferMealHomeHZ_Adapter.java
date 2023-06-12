package dev.mah.nassa.gradu_ptojects.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import dev.mah.nassa.gradu_ptojects.Modles.FoodCategory;
import dev.mah.nassa.gradu_ptojects.R;

public class OfferMealHomeHZ_Adapter extends RecyclerView.Adapter<OfferMealHomeHZ_Adapter.ViewHolder>{
    Context context;
    ArrayList<FoodCategory> list;
    ViewPager2 viewPager2;

    public OfferMealHomeHZ_Adapter(Context context, ArrayList<FoodCategory> list, ViewPager2 viewPager2) {
        this.context = context;
        this.list = list;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_meals_items , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodCategory mealHomeHZ =list.get(position);
        Glide.with(context)
                .load(mealHomeHZ.getFilePath())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);
        holder.calories.setText(mealHomeHZ.getCaloriesMeal());
        holder.name.setText(mealHomeHZ.getNameMeal());

        if (position == list.size() - 2){
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView name,calories;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.offerMealsItems_Iv_image);
            name = itemView.findViewById(R.id.offerMealsItems_tv_name);
            calories = itemView.findViewById(R.id.offerMealsItems_tv_caloriesMeal);

        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            list.addAll(list);
            notifyDataSetChanged();
        }
    };
}
