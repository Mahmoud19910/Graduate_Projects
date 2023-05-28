package dev.mah.nassa.gradu_ptojects.Activityes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;

import dev.mah.nassa.gradu_ptojects.Adapters.DoctorsAdapter;
import dev.mah.nassa.gradu_ptojects.Modles.Doctor;
import dev.mah.nassa.gradu_ptojects.R;
import dev.mah.nassa.gradu_ptojects.databinding.ActivityDoctorsBinding;

public class DoctorsActivity extends AppCompatActivity  {

    private ActivityDoctorsBinding binding;
    private String specialization , name , id , phoneNumber , cv , adress ,photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Doctor doctor = DoctorsAdapter.TransferToActivit();


                if (doctor.getImage()== null) {
                    binding.doctorImage.setImageDrawable(getDrawable(R.drawable.user));
                } else {
                    Glide.with(DoctorsActivity.this).load(doctor.getImage()).into(binding.doctorImage);
                }
                binding.tvCv.setText(doctor.getDescription());
                binding.tvName.setText("الاسم كاملاً : " + doctor.getName());
                binding.tvSpecialization.setText("التخصص : " + doctor.getSpecialization());

                // بدء الدردشة
                binding.startChat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DoctorsActivity.this , Chat_Activity.class);
                        intent.putExtra("doctor" , doctor);
                        startActivity(intent);
                    }
                });

                binding.profileIvBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       Intent intent =  new Intent(DoctorsActivity.this , Home_Activity.class);
                       intent.putExtra("fromDoctorFragment" , true);
                        startActivity(intent);
                    }
                });






    }


}