package dev.mah.nassa.gradu_ptojects.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import dev.mah.nassa.gradu_ptojects.R;

public class ExerciseDetails_ViewHolder extends RecyclerView.ViewHolder {

   public TextView dateTv , date , nameExercise , exerciseDuration , time ,calories;
   public TableLayout tableLayout;
  public LinearLayoutCompat linearLayout;
  public ImageView iconDrop;
    public ExerciseDetails_ViewHolder(@NonNull View itemView) {
        super(itemView);
        dateTv = itemView.findViewById(R.id.dateTv);
        date = itemView.findViewById(R.id.date);
        nameExercise = itemView.findViewById(R.id.exerciseName);
        exerciseDuration = itemView.findViewById(R.id.exerciseDuration);
        time = itemView.findViewById(R.id.time);
        calories = itemView.findViewById(R.id.caloriBurnedDetails);
        tableLayout = itemView.findViewById(R.id.tableDetails);
        linearLayout=itemView.findViewById(R.id.linear);
        iconDrop=itemView.findViewById(R.id.dropIcon);
    }
}
