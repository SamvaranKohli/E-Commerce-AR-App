package com.example.furniture;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class ImageAdapter extends PagerAdapter {

    Context mcontext;
    int[] mImageIds = new int[] {R.drawable.ic_launcher_background, R.drawable.ic_launcher_foreground};

    ArrayList<Bitmap> mImageViews = new ArrayList<>();

    ImageAdapter(Context context, ArrayList<Bitmap> views)
    {
        mcontext = context;
        mImageViews = views;
    }

    @Override
    public int getCount() {
        return mImageViews.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        ImageView imageView = new ImageView(mcontext);
        //Picasso.get().load(mImageViews[position]).fit().centerCrop().into(imageView);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageBitmap(mImageViews.get(position));

        container.addView(imageView);
        return imageView;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView) object);
    }
}

