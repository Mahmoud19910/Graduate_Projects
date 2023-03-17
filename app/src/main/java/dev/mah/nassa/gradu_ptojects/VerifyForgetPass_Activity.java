package dev.mah.nassa.gradu_ptojects;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import dev.mah.nassa.gradu_ptojects.databinding.ActivityVerifyForgetPassBinding;

public class VerifyForgetPass_Activity extends AppCompatActivity {

    ActivityVerifyForgetPassBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityVerifyForgetPassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.parentLayoutVerifyForgetPass.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        binding.parentLayoutVerifyForgetPass.setTextDirection(View.TEXT_DIRECTION_ANY_RTL);
    }
}