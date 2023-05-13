package dev.mah.nassa.gradu_ptojects.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import dev.mah.nassa.gradu_ptojects.Activityes.Exercices_Activity;
import dev.mah.nassa.gradu_ptojects.Modles.Sports_Exercises;
import dev.mah.nassa.gradu_ptojects.ViewHolder.Exercices_ViewHolder;

public class Exercices_Adapter extends RecyclerView.Adapter<Exercices_ViewHolder> {
    private int layout;
    private Context context;
    private List<Sports_Exercises> arrayList;

    public Exercices_Adapter(int layout, Context context, List<Sports_Exercises> arrayList) {
        this.layout = layout;
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Exercices_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent , false);
        return new Exercices_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Exercices_ViewHolder holder, int position) {
        holder.nameTraining.setText(arrayList.get(position).getName());
        Glide.with(context)
                        .load(arrayList.get(position).getImageUrl())
                .into(holder.urlImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context , Exercices_Activity.class);
                intent.putExtra("obj" , arrayList.get(position));
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
