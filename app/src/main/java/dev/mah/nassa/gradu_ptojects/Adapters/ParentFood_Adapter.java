package dev.mah.nassa.gradu_ptojects.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.logging.ConsoleHandler;

import dev.mah.nassa.gradu_ptojects.Modles.FoodCategory;
import dev.mah.nassa.gradu_ptojects.R;

public class ParentFood_Adapter extends RecyclerView.Adapter<ParentFood_Adapter.MyViewHolder> {
    private ArrayList<FoodCategory> foodCategoryArrayList;
    private Context context;
    private OnClickListener onClickListener;
    private ArrayList <String> ss;


    public ParentFood_Adapter(ArrayList<FoodCategory> foodCategoryArrayList, Context context, OnClickListener onClickListener) {
        this.foodCategoryArrayList = foodCategoryArrayList;
        this.context = context;
        this.onClickListener = onClickListener;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parint_food_recyclerview_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {


        ArrayList<FoodCategory> horizontalFoodList = new ArrayList<>();

        if(position == ss.size()-1){
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) holder.constraintLayout.getLayoutParams();
            layoutParams.bottomMargin = 130; // Change the bottom margin value as desired
            holder.constraintLayout.setLayoutParams(layoutParams);
        }

        if(ss != null ){
            if (position < ss.size()) {

                holder.foodName.setText(ss.get(position));
                YoYo.with(Techniques.ZoomIn).duration(1000).playOn(holder.foodName);
                YoYo.with(Techniques.ZoomIn).duration(1000).playOn(holder.childRecyclerView);

                for(FoodCategory foodCategory : foodCategoryArrayList){

                    if(foodCategory.getDepartmentName().equals(ss.get(position))){
                        horizontalFoodList.add(foodCategory);
                    }
                }
                ChildFood_Adapter childRecyclerViewAdapter = new ChildFood_Adapter(horizontalFoodList , holder.childRecyclerView.getContext());
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
                holder.childRecyclerView.setLayoutManager(layoutManager);
                holder.childRecyclerView.setHasFixedSize(true);
                holder.childRecyclerView.setAdapter(childRecyclerViewAdapter);

            }
        }

        //عن ضغط على اسم القسم سيتم تعبئة السنر بالاري وارسالها
        holder.foodName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(horizontalFoodList);
            }
        });
    }

    @Override
    public int getItemCount() {
        ArrayList <String> timp = new ArrayList();
        LinkedHashSet<String> result;
        for (int i = 0; i <foodCategoryArrayList.size(); i++) {

            timp.add(foodCategoryArrayList.get(i).getDepartmentName());
            result = new LinkedHashSet<>(timp);
            ss = new ArrayList(result);

        }
            return ss.size();
    }

    //MyViewHolder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView foodName;
        public RecyclerView childRecyclerView;
        public ConstraintLayout constraintLayout;

        public MyViewHolder(View itemView) {
            super(itemView);

            foodName = itemView.findViewById(R.id.parint_foood_name);
            childRecyclerView = itemView.findViewById(R.id.Child_RV);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
        }
    }

    //Listener
    public interface OnClickListener {
        void onClick (ArrayList<FoodCategory> list);
    }
}

