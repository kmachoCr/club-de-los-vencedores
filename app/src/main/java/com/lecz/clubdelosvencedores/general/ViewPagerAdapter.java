package com.lecz.clubdelosvencedores.general;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lecz.clubdelosvencedores.R;

public class ViewPagerAdapter extends PagerAdapter {
    // Declare Variables
    Context context;
    String[] rank;
    String[] country;
    String[] population;
    int[] flag;
    LayoutInflater inflater;

    public ViewPagerAdapter(Context context, String[] rank, String[] country,
                            String[] population, int[] flag) {
        this.context = context;
        this.rank = rank;
        this.country = country;
        this.population = population;
        this.flag = flag;
    }

    @Override
    public int getCount() {
        return rank.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        // Declare Variables
        TextView txtconsejo;
        ImageView imgimagen;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.viewpager_item, container,
                false);

        // Locate the TextViews in viewpager_item.xml
        txtconsejo = (TextView) itemView.findViewById(R.id.consejo);

        // Capture position and set to the TextViews
        txtconsejo.setText(rank[position]);

        // Locate the ImageView in viewpager_item.xml
        imgimagen = (ImageView) itemView.findViewById(R.id.imagen);
        // Capture position and set to the ImageView
        imgimagen.setImageResource(flag[position]);

        // Add viewpager_item.xml to ViewPager
        ((ViewPager) container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((RelativeLayout) object);

    }
}