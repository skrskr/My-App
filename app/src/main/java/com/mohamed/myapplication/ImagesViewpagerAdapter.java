package com.mohamed.myapplication;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ImagesViewpagerAdapter extends PagerAdapter {
    ArrayList<String> image_url;
    Context mContext;
    LayoutInflater layoutInflater;


    public ImagesViewpagerAdapter(ArrayList<String> image_url, Context mContext) {
        this.image_url = image_url;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return image_url.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int p) {
        layoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.image_layout, container, false);
        final ImageView imageView = view.findViewById(R.id.imageview);
        Glide.with(mContext).load(image_url.get(p)).into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }
}
