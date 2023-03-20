package dev.mah.nassa.gradu_ptojects.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import dev.mah.nassa.gradu_ptojects.R;

public class PagerAdapter extends androidx.viewpager.widget.PagerAdapter{
    List<Integer> imageArray;
    Context context;
    public PagerAdapter(Context context, ArrayList<Integer> imageArray) {
        this.context = context;
        this.imageArray = imageArray;

    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_pager, null);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView imageView = view.findViewById(R.id.photo_Pager);
        imageView.setImageResource(imageArray.get(position));
        container.addView(view);
        return view;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return imageArray.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }
}

