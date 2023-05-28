package dev.mah.nassa.gradu_ptojects.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

import dev.mah.nassa.gradu_ptojects.Activityes.DoctorsActivity;
import dev.mah.nassa.gradu_ptojects.Modles.Doctor;
import dev.mah.nassa.gradu_ptojects.R;

public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.ViewHolderDoctor> {

    private Context context;
    private List<Doctor> doctorList;
    public  static Doctor docto;

    public DoctorsAdapter(Context context) {
        this.context = context;
        doctorList = new ArrayList<>();
    }

    public DoctorsAdapter(Context context , List<Doctor> doctorList) {
        this.context = context;
        this.doctorList = doctorList;
    }

    public void addDoctor(Doctor doctor){
        doctorList.add(doctor);
        notifyDataSetChanged();
    }

    public void clearAdapter(){
        doctorList.clear();
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolderDoctor onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.doctor_item_design, parent, false);
        return new ViewHolderDoctor(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDoctor holder, int position) {

        Doctor doctor = doctorList.get(position);

        if(doctor.getImage()==null){
            holder.doctorPhoto.setImageDrawable(context.getDrawable(R.drawable.user));
        }else {
            Glide.with(context).load(doctor.getImage()).into(holder.doctorPhoto);
        }

        holder.nameDoctor.setText(doctor.getName());
        holder.specialization.setText(doctor.getSpecialization());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                docto = doctor;
                context.startActivity(new Intent(context , DoctorsActivity.class));
            }
        });



    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public class ViewHolderDoctor extends RecyclerView.ViewHolder {
        CircularImageView doctorPhoto;
        TextView nameDoctor, specialization;

        public ViewHolderDoctor(@NonNull View itemView) {
            super(itemView);
            doctorPhoto = itemView.findViewById(R.id.doctorPhoto);
            nameDoctor = itemView.findViewById(R.id.nameDoctor);
            specialization = itemView.findViewById(R.id.specialization);
        }
    }

    public static Doctor TransferToActivit(){
        return docto;
    }


}

