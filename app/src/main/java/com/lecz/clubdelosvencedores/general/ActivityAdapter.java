package com.lecz.clubdelosvencedores.general;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lecz.clubdelosvencedores.R;
import com.lecz.clubdelosvencedores.objects.Activity;
import com.lecz.clubdelosvencedores.objects.Notice;

import java.util.ArrayList;

public class ActivityAdapter extends ArrayAdapter<Object> {
    // Declare Variables
    Context context;
    LayoutInflater inflater;
    private ArrayList<Activity> activities;

    public ActivityAdapter(Context context, ArrayList<Activity> activities ) {
        super(context, R.layout.viewpager_item);
        this.context = context;
        this.activities = activities;
    }

    @Override
    public int getCount() {
        return activities.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView title;
        TextView content;
        ImageView image;
        RelativeLayout contentMain;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.viewpager_item, parent, false);

        title = (TextView) itemView.findViewById(R.id.title);
        content = (TextView) itemView.findViewById(R.id.content);
        image = (ImageView) itemView.findViewById(R.id.image);
        contentMain = (RelativeLayout) itemView.findViewById(R.id.contentmain);

        //title.setText(activities.get(position).getTitle());
        content.setText(activities.get(position).getContent());
        if(activities.get(position).getType().equals("logro")){
            contentMain.setBackgroundResource(R.drawable.logro_fondo);
        }else{
            contentMain.setBackgroundResource(R.drawable.consejo_fondo);
        }

        image.setImageResource(activities.get(position).getImage());

        return itemView;
    }

}