package dev.mah.nassa.gradu_ptojects.Fragments;

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

import dev.mah.nassa.gradu_ptojects.Constants.PersonActivityArray;
import dev.mah.nassa.gradu_ptojects.Constants.Vital_Equations;
import dev.mah.nassa.gradu_ptojects.MVVM.UsersViewModel;
import dev.mah.nassa.gradu_ptojects.Modles.UsersInfo;
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
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        usersViewModel=ViewModelProviders.of(Home_Fragment.this).get(UsersViewModel.class);
        progressiveGauge= binding.awesomeSpeedomete;

    usersViewModel.getUsersByUid(uid).observe(this, new Observer<UsersInfo>() {
        @Override
        public void onChanged(UsersInfo usersInfo) {
            progressiveGauge.speedTo((float) Integer.parseInt(usersInfo.getWeight()));
            binding.waterQuan.setText(Vital_Equations.waterQuantity(usersInfo.getWeight())+"لتر");
           double caloriDailyRequirment =  Vital_Equations.caloriDailyRequirment(usersInfo.getEage() , usersInfo.getLength() , usersInfo.getActivityLeve()  ,usersInfo.getWeight() , usersInfo.getGender());
           binding.calorDailyRequirment.setText("kca  "+caloriDailyRequirment+"");
           binding.w.setText(Vital_Equations.calculateFreeBodyMass(usersInfo.getLength() , usersInfo.getWeight())+"");
        }
    });








    }
}