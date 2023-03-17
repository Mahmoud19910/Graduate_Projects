package dev.mah.nassa.gradu_ptojects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import dev.mah.nassa.gradu_ptojects.Constants.PagerView_Comp;
import dev.mah.nassa.gradu_ptojects.databinding.ActivityUnBoardingBinding;

public class UnBoarding_Activity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    ActivityUnBoardingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUnBoardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.parentLayoutUnBoarding.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        binding.parentLayoutUnBoarding.setTextDirection(View.TEXT_DIRECTION_ANY_RTL);

        PagerAdapter pagerAdapter = new PagerAdapter(this, PagerView_Comp.getImageList());
        binding.pagerImage.setAdapter(pagerAdapter);
        binding.pagerImage.addOnPageChangeListener(this);
        binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(UnBoarding_Activity.this, SignUp_Activity.class);
                startActivity(ii);
            }
        });


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                binding.indicator1.setBackground(getDrawable(R.drawable.indicator_pagerfillcolor));
                binding.indicator2.setBackground(getDrawable(R.drawable.indicator_outline));
                binding.indicator3.setBackground(getDrawable(R.drawable.indicator_outline));
                binding.titleUnboarding.setText("حقق إنجازاً");
                binding.descriptionUnboarding.setText("يتيج الطبيق لمستخدميه حساب خطوات المشي أو الركض والمسافة المقطوعة و السرعة وبناءً على ذلك تحسب عدد السعرات الحرارية المفقودة بعد كل تمرين.");

                break;

            case 1:
                binding.indicator2.setBackground(getDrawable(R.drawable.indicator_pagerfillcolor));
                binding.indicator1.setBackground(getDrawable(R.drawable.indicator_outline));
                binding.indicator3.setBackground(getDrawable(R.drawable.indicator_outline));
                binding.titleUnboarding.setText("اشرب الماء بانتظام وحافظ على رشاقة وصحة جسدك");
                binding.descriptionUnboarding.setText("يتيج الطبيق لمستخدميه معرفة كمية الماء التي يجب شربها خلال اليوم للتمتع بصحة جيدة ورشاقة عالية ");

                break;

            case 2:
                binding.indicator3.setBackground(getDrawable(R.drawable.indicator_pagerfillcolor));
                binding.indicator2.setBackground(getDrawable(R.drawable.indicator_outline));
                binding.indicator1.setBackground(getDrawable(R.drawable.indicator_outline));
                binding.titleUnboarding.setText("اعتن بنفسك ");
                binding.descriptionUnboarding.setText("يضم التطبيق العديد من الوجبات الغذائية الصحية ومجموعة من التمارين المنزلية ");
                break;
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}