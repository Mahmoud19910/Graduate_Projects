package dev.mah.nassa.gradu_ptojects.Activityes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import java.util.List;

import dev.mah.nassa.gradu_ptojects.Fragments.Doctors_Fragment;
import dev.mah.nassa.gradu_ptojects.Fragments.FoodCategory_Fragment;
import dev.mah.nassa.gradu_ptojects.Fragments.Home_Fragment;
import dev.mah.nassa.gradu_ptojects.Fragments.StepsCounter_Fragment;
import dev.mah.nassa.gradu_ptojects.Fragments.Training_Fragment;
import dev.mah.nassa.gradu_ptojects.Modles.UsersInfo;
import dev.mah.nassa.gradu_ptojects.Modles.UsersViewModel;
import dev.mah.nassa.gradu_ptojects.R;
import dev.mah.nassa.gradu_ptojects.databinding.ActivityHomeBinding;

public class Home_Activity extends AppCompatActivity {

    UsersViewModel usersViewModel;
    ActivityHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.parentLayoutHome.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        binding.parentLayoutHome.setTextDirection(View.TEXT_DIRECTION_ANY_RTL);

       FragmentTransaction fragmentTransaction =  getSupportFragmentManager().beginTransaction();
       fragmentTransaction.replace(R.id.frame , new Home_Fragment());
       fragmentTransaction.commit();
        MeowBottomNavigation bottomNavigation = findViewById(R.id.navigateBar);

        bottomNavigation.show(3,false);
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.article_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.walk_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_baseline_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.gymnastics_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(5, R.drawable.ic_baseline_health_and_safety_24));

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                switch (item.getId()){
                    case 1:
                        replace(new FoodCategory_Fragment());

                        break;
                    case 2:
                        replace(new StepsCounter_Fragment());

                        break;
                    case 3:
                        replace(new Home_Fragment());

                        break;
                    case 4:
                        replace(new Training_Fragment());

                        break;
                    case 5:
                        replace(new Doctors_Fragment());

                        break;

                }
            }
        });

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                // your codes
            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                // your codes
            }
        });

        usersViewModel= ViewModelProviders.of(this).get(UsersViewModel.class);


        usersViewModel.getAllUsers().observe(Home_Activity.this, new Observer<List<UsersInfo>>() {
            @Override
            public void onChanged(List<UsersInfo> usersInfos) {
                for(UsersInfo usersInfo : usersInfos){
                    Toast.makeText(Home_Activity.this, usersInfo.getPass(), Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    private void replace(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.commit();
    }

}