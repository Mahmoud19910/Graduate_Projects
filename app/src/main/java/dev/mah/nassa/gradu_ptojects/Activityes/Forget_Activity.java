package dev.mah.nassa.gradu_ptojects.Activityes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import dev.mah.nassa.gradu_ptojects.databinding.ActivityForgetBinding;

public class Forget_Activity extends AppCompatActivity {

    ActivityForgetBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityForgetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.parentLayoutForget.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        binding.parentLayoutForget.setTextDirection(View.TEXT_DIRECTION_ANY_RTL);

        binding.verifyButForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext() , VerifyForgetPass_Activity.class));
            }
        });
    }
}
