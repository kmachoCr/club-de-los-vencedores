package com.lecz.clubdelosvencedores.Game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;
import com.lecz.clubdelosvencedores.R;

import java.util.Random;

public class BlockAdapter extends BaseAdapter {
    private Block[] blocks = new Block[25];
    private Context mContext;
    LayoutInflater inflater;
    Resources resources;
    int size_gv;

    public BlockAdapter(Context c, Resources resources, int size) {
        this.mContext = c;
        this.resources = resources;
        this.size_gv = size;
    }

    public int getCount() {
        return blocks.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView img;

        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.gridblock, parent, false);

        img = (ImageView) itemView.findViewById(R.id.imageView);
        itemView.setBackgroundColor(blocks[position].getColor());
        img.setMaxHeight(20);
        if(blocks[position].isTarget()){
            SVG pulmones = SVGParser.getSVGFromResource(resources, R.raw.icn_juego_pulmones);
            img.setImageDrawable(pulmones.createPictureDrawable());
        }


        return itemView;
    }

    public Block[] getBlocks(){
        return blocks;
    }

    public Block[] setBlocks(int number_target){
        for (int i = 0; i < blocks.length; i++){
            Block block = new Block();

            int min = 0;
            int max = colours.length - 1;

            Random ra = new Random();
            int i1 = ra.nextInt(max - min + 1) + min;

            block.setTarget(false);
            block.setColor(Color.parseColor(colours[i1]));

            blocks[i] = block;
        }

        for (int i = 0; i < number_target; i++){
            int min = 0;
            int max = 23;
            Random r = new Random();
            int i1 = r.nextInt(max - min + 1) + min;
            blocks[i1].setTarget(true);
            blocks[i1].setColor(Color.parseColor("#F46E25"));


            blocks[i1].setImage(R.raw.icn_juego_pulmones);
        }

        return blocks;
    }

    private String[] colours = {"#ADC259", "#FBB03B", "#5ACAC0"};
}