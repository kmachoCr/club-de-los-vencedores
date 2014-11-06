package com.lecz.clubdelosvencedores.Game;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lecz.clubdelosvencedores.R;

import java.util.Random;

public class BlockAdapter extends BaseAdapter {
    private Block[] blocks = new Block[28];
    private Context mContext;
    LayoutInflater inflater;
    public BlockAdapter(Context c) {
        mContext = c;
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
        img.setImageResource(blocks[position].getImage());

        return itemView;
    }

    public Block[] getBlocks(){
        return blocks;
    }

    public Block[] setBlocks(int number_target){
        for (int i = 0; i < blocks.length; i++){
            Block block = new Block();

            int min = 0;
            int max = 7;

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
            blocks[i1].setColor(Color.RED);
            blocks[i1].setImage(R.drawable.minus_test);
        }

        return blocks;
    }

    private String[] colours = {"#11aa00","#999999","#0066cc","#e6b121","#99FFFF","#3399FF","#8ad0e8", "#339955", "#99FFcc", "#aa66cc", "#9944aa"};
}