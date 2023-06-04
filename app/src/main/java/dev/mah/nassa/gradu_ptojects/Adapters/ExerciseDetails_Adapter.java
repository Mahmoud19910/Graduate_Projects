package dev.mah.nassa.gradu_ptojects.Adapters;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.mah.nassa.gradu_ptojects.Modles.Exercise_Details;
import dev.mah.nassa.gradu_ptojects.ViewHolder.ExerciseDetails_ViewHolder;

public class ExerciseDetails_Adapter extends RecyclerView.Adapter<ExerciseDetails_ViewHolder> {

    private int layout;
    private List<Exercise_Details> detailsList;
    private Context context;
    private boolean dropDown=false;

    public ExerciseDetails_Adapter(int layout, List<Exercise_Details> detailsList, Context context) {
        this.layout = layout;
        this.detailsList = detailsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ExerciseDetails_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view =  LayoutInflater.from(context).inflate(layout , parent , false);
      ExerciseDetails_ViewHolder exerciseDetails_viewHolder = new ExerciseDetails_ViewHolder(view);
        return exerciseDetails_viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseDetails_ViewHolder holder, int position) {


                holder.exerciseDuration.setText(detailsList.get(position).getExerciseDuration() + "ساعة");
                holder.nameExercise.setText(detailsList.get(position).getExerciseName() );
                holder.time.setText(detailsList.get(position).getExerciseTime() );
                holder.dateTv.setText(detailsList.get(position).getDate() );
                holder.date.setText(detailsList.get(position).getDate() );
                holder.calories.setText(String.format("%.2f", Double.parseDouble(detailsList.get(position).getCaloriesBurned())) + "سعرة حرارية");

                ViewGroup.LayoutParams layoutParams =  holder.tableLayout.getLayoutParams();
                if((position+1) != detailsList.size()){
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
        return detailsList.size();
    }
}


