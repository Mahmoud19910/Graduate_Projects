package dev.mah.nassa.gradu_ptojects.Activityes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import java.security.PrivateKey;
import java.util.List;

import dev.mah.nassa.gradu_ptojects.Constants.SharedFunctions;
import dev.mah.nassa.gradu_ptojects.FireBase_Authentication.Gmai_Auth;
import dev.mah.nassa.gradu_ptojects.Fragments.Doctors_Fragment;
import dev.mah.nassa.gradu_ptojects.Fragments.FoodCategory_Fragment;
import dev.mah.nassa.gradu_ptojects.Fragments.Home_Fragment;
import dev.mah.nassa.gradu_ptojects.Fragments.StepsCounter_Fragment;
import dev.mah.nassa.gradu_ptojects.Fragments.Training_Fragment;
import dev.mah.nassa.gradu_ptojects.Modles.UsersInfo;
import dev.mah.nassa.gradu_ptojects.MVVM.UsersViewModel;
import dev.mah.nassa.gradu_ptojects.R;
import dev.mah.nassa.gradu_ptojects.databinding.ActivityHomeBinding;

public class Home_Activity extends AppCompatActivity implements View.OnClickListener {

    private UsersViewModel usersViewModel;
    private ActivityHomeBinding binding;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        usersViewModel=ViewModelProviders.of(Home_Activity.this).get(UsersViewModel.class);

        binding.parentLayoutHome.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        binding.parentLayoutHome.setTextDirection(View.TEXT_DIRECTION_ANY_RTL);

      Intent intent = getIntent();
     uid = intent.getStringExtra("uid");
     // Shared Prefrences
     saveUid(uid);
        usersViewModel.getUsersByUid(loadUid()).observe(this, new Observer<UsersInfo>() {
            @Override
            public void onChanged(UsersInfo usersInfo) {
                binding.userNmae.setText(usersInfo.getName());
            }
        });

        // Listener
        binding.drawer.setOnClickListener(this);

        // Set Main Layout (Home Activity)
       FragmentTransaction fragmentTransaction =  getSupportFragmentManager().beginTransaction();
       fragmentTransaction.replace(R.id.frame , new Home_Fragment(loadUid()));
       fragmentTransaction.commit();


        MeowBottomNavigation bottomNavigation = findViewById(R.id.navigateBar);

        // set default layout
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

                        Home_Fragment home_fragment = new Home_Fragment(loadUid());
                        replace(home_fragment);

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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.drawer:
                Gmai_Auth.onSignOut(Home_Activity.this);
                startActivity(new Intent(Home_Activity.this , SignIn_Activity.class));
                SharedFunctions.isSignIn(false , Home_Activity.this);
                finish();
                break;
        }
    }


    // Method to replace layout On Navigation
    private void replace(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void saveUid(String uid){
        if(uid!=null){
            SharedPreferences sharedPreferences = getSharedPreferences("saveUid"  , Context.MODE_PRIVATE);
            SharedPreferences.Editor editor =sharedPreferences.edit();
            editor.putString("uid" , uid);
            Toast.makeText(this, "save", Toast.LENGTH_SHORT).show();
            editor.apply();
        }

    }

    private String loadUid(){
        SharedPreferences sharedPreferences = getSharedPreferences("saveUid" , Context.MODE_PRIVATE);
        Toast.makeText(this, "load", Toast.LENGTH_SHORT).show();
     return sharedPreferences.getString("uid" , "");
    }

}