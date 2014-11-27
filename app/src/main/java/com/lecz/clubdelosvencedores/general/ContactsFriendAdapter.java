package com.lecz.clubdelosvencedores.general;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;
import com.lecz.clubdelosvencedores.R;
import com.lecz.clubdelosvencedores.objects.Contact;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Luis on 8/27/2014.
 */
public class ContactsFriendAdapter extends ArrayAdapter<Object> {
    Context context;
    private ArrayList<Contact> arrayList;
    Resources resources;

    public ContactsFriendAdapter(Context context, ArrayList<Contact> array, Resources resources) {
        super(context, R.layout.item_contact);
        this.arrayList = array;
        this.resources = resources;
        this.context = context;
    }

    @Override
    public int getCount(){
        return arrayList.size();
    }

    public static class Placeholder{
        TextView contact_name;
        TextView contact_number;
        ImageButton button_call;

        public static Placeholder generate(View convertView){
            Placeholder placeholder = new Placeholder();
            placeholder.contact_name = (TextView) convertView.findViewById(R.id.contact_name);
            placeholder.contact_number = (TextView) convertView.findViewById(R.id.contact_number);
            placeholder.button_call = (ImageButton) convertView.findViewById(R.id.button_call);

            return placeholder;
        }
    }
    public View getView(final int position, View convertView, ViewGroup parent){

        Placeholder placeholder;
        if(convertView == null){
            convertView = View.inflate(context, R.layout.item_contactfriend, null);
            placeholder = Placeholder.generate(convertView);
            convertView.setTag(placeholder);
        }else{
            placeholder = (Placeholder) convertView.getTag();
        }
        //Uri u = arrayList.get(position).getPhoto();
        placeholder.contact_name.setText(arrayList.get(position).getName());
        placeholder.contact_number.setText(arrayList.get(position).getPhone());


        SVG svg = SVGParser.getSVGFromResource(resources, R.raw.icn_contactos);
        placeholder.button_call.setImageDrawable(svg.createPictureDrawable());
        placeholder.button_call.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent phoneCallIntent = new Intent(Intent.ACTION_CALL);
                phoneCallIntent.setData(Uri.parse("tel:" + arrayList.get(position).getPhone()));
                phoneCallIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(phoneCallIntent);
            }
        });

        return convertView;
    }


}
