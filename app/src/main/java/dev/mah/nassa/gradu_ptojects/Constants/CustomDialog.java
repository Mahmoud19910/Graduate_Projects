package dev.mah.nassa.gradu_ptojects.Constants;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import dev.mah.nassa.gradu_ptojects.Activityes.ActivitesStats;
import dev.mah.nassa.gradu_ptojects.Activityes.Exercices_Activity;
import dev.mah.nassa.gradu_ptojects.Activityes.StartTraining_Activity;
import dev.mah.nassa.gradu_ptojects.Modles.Sports_Exercises;
import dev.mah.nassa.gradu_ptojects.R;

public class CustomDialog extends Dialog {
    Context context;
    String time , caloriesBurned;
    public CustomDialog(Context context , String time , String caloriesBurned ) {
        super(context);
        this.context=context;
        this.time=time;
        this.caloriesBurned=caloriesBurned;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goal_dialog);

        LinearLayout  layout =  findViewById(R.id.details);
        ImageView imageView = findViewById(R.id.imageTraining);

        Glide.with(context)
                .load("https://i.gifer.com/7LDn.gif")
                .into(imageView);


       layout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Toast.makeText(context, "Navigate To Details Activity", Toast.LENGTH_SHORT).show();
               ((Activity)context).startActivity(new Intent(context , ActivitesStats.class));
               ((Activity)context).finish();


           }
       });
       TextView caloriTv = findViewById(R.id.caloriBurnd);
       caloriTv.setText(caloriesBurned+"سعرة");
        TextView achivmentTime = findViewById(R.id.achivmentTime);
        achivmentTime.setText(time);
        // Set the dialog size
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }
}
