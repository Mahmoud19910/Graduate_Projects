package dev.mah.nassa.gradu_ptojects.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

import dev.mah.nassa.gradu_ptojects.Activityes.Chat_Activity;
import dev.mah.nassa.gradu_ptojects.Activityes.DoctorsActivity;
import dev.mah.nassa.gradu_ptojects.Constants.SharedFunctions;
import dev.mah.nassa.gradu_ptojects.Modles.Doctor;
import dev.mah.nassa.gradu_ptojects.R;

public class DoctorAdapter_Horisintal extends RecyclerView.Adapter<DoctorAdapter_Horisintal.ViewHolderDoctor> {

    private Context context;
    private List<Doctor> doctorList;
    public  Doctor docto;

    public DoctorAdapter_Horisintal(Context context) {
        this.context = context;
        doctorList = new ArrayList<>();
    }

    public DoctorAdapter_Horisintal(Context context , List<Doctor> doctorList) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.doctor_item_design2, parent, false);

        return new ViewHolderDoctor(view);
    }


    @Override
    public void onBindViewHolder(@NonNull DoctorAdapter_Horisintal.ViewHolderDoctor holder, int position) {

        Doctor doctor = doctorList.get(position);

        if(!SharedFunctions.checkInternetConnection(context)){
            holder.onLine.setVisibility(View.INVISIBLE);
            Glide.with(context).load(doctor.getImage()).into(holder.doctorPhoto);
            holder.nameDoctor.setText(doctor.getName());
        }else {

            Glide.with(context).load(doctor.getImage()).into(holder.doctorPhoto);
            holder.nameDoctor.setText(doctor.getName());
            if(doctor.isSesion()){
                holder.onLine.setImageDrawable(context.getDrawable(R.drawable.online));
            }else {
                holder.onLine.setImageDrawable(context.getDrawable(R.drawable.ofline));

            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , Chat_Activity.class);
                intent.putExtra("doctor" , doctor);
                context.startActivity(intent);
            }
        });





    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public class ViewHolderDoctor extends RecyclerView.ViewHolder {

        public CircularImageView doctorPhoto;
        public TextView nameDoctor;
        public ImageView onLine;

        public ViewHolderDoctor(@NonNull View itemView) {
            super(itemView);
            doctorPhoto = itemView.findViewById(R.id.doctorImage);
            nameDoctor = itemView.findViewById(R.id.doctorNmae);
            onLine = itemView.findViewById(R.id.onLine);
        }
    }



}
