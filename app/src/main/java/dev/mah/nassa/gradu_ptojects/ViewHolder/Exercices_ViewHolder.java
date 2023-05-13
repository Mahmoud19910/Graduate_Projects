package dev.mah.nassa.gradu_ptojects.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.awt.font.TextAttribute;

import dev.mah.nassa.gradu_ptojects.R;

public class Exercices_ViewHolder extends RecyclerView.ViewHolder {

   public TextView nameTraining;
    public ImageView urlImage;
    public Exercices_ViewHolder(@NonNull View itemView) {
        super(itemView);
        nameTraining = itemView.findViewById(R.id.nameTraining);
        urlImage = itemView.findViewById(R.id.imageTraining);
    }
}
