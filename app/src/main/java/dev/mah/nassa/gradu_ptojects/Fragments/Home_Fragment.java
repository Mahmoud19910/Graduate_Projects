package dev.mah.nassa.gradu_ptojects.Fragments;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import com.github.anastr.speedviewlib.ProgressiveGauge;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dev.mah.nassa.gradu_ptojects.Activityes.Home_Activity;
import dev.mah.nassa.gradu_ptojects.Constants.LanguageUtils;
import dev.mah.nassa.gradu_ptojects.Constants.PersonActivityArray;
import dev.mah.nassa.gradu_ptojects.Constants.Vital_Equations;
import dev.mah.nassa.gradu_ptojects.MVVM.UsersHealthInfoViewModel;
import dev.mah.nassa.gradu_ptojects.MVVM.UsersViewModel;
import dev.mah.nassa.gradu_ptojects.Modles.UsersInfo;
import dev.mah.nassa.gradu_ptojects.Modles.Users_Health_Info;
import dev.mah.nassa.gradu_ptojects.R;
import dev.mah.nassa.gradu_ptojects.databinding.FragmentHomeBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home_Fragment extends Fragment {

  private FragmentHomeBinding binding;
   private ProgressiveGauge progressiveGauge;
   private String uid;
   private UsersViewModel usersViewModel;
   private UsersInfo usersInfo;
   UsersHealthInfoViewModel usersHealthInfoViewModel;

    public Home_Fragment(){
    }
   public Home_Fragment(String uid){
       this.uid=uid;
   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(inflater);

        LanguageUtils.changeLanguage(requireContext(), "en");
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        usersViewModel=ViewModelProviders.of(Home_Fragment.this).get(UsersViewModel.class);
        usersHealthInfoViewModel=ViewModelProviders.of(Home_Fragment.this).get(UsersHealthInfoViewModel.class);
        progressiveGauge= binding.awesomeSpeedomete;

    usersViewModel.getUsersByUid(uid).observe(this, new Observer<UsersInfo>() {
        @Override
        public void onChanged(UsersInfo usersInfo) {
            progressiveGauge.speedTo((float) Integer.parseInt(usersInfo.getWeight()));
           binding.w.setText(Vital_Equations.calculateFreeBodyMass(usersInfo.getLength() , usersInfo.getWeight())+"");
        }
    });



        usersHealthInfoViewModel.getUsersHealthById(uid).observe(this, new Observer<Users_Health_Info>() {
            @Override
            public void onChanged(Users_Health_Info users_health_info) {

                Toast.makeText(getContext(), users_health_info.getUserId()+ "Home Uid", Toast.LENGTH_LONG).show();
                binding.calorDailyRequirment.setText(String.format("kca "+"%.2f" , users_health_info.getCaloriesNumber())+"  ");
                binding.waterQuan.setText(String.format("لتر "+"%.2f" , users_health_info.getWaterDrink())+ "  ");
                binding.burnedCalories.setText(String.format("kca "+"%.2f" , users_health_info.getBurnedCaloriesNumber())+ "  ");
                binding.caloriesGained.setText(String.format("kca "+"%.2f" , users_health_info.getCaloriesGained())+ "  ");


            }
        });









    }
}