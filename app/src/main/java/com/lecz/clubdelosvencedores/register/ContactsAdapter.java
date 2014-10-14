package com.lecz.clubdelosvencedores.register;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lecz.clubdelosvencedores.R;
import com.lecz.clubdelosvencedores.objects.Contact;

import java.util.ArrayList;

/**
 * Created by Luis on 8/27/2014.
 */
public class ContactsAdapter extends ArrayAdapter<Object> {
    Context context;
    private ArrayList<Contact> arrayList;

    public ContactsAdapter(Context context, ArrayList<Contact> array) {
        super(context, R.layout.item_contact);
        this.arrayList = array;
        this.context = context;
    }

    @Override
    public int getCount(){
        return arrayList.size();
    }

    public static class Placeholder{
        TextView contact_name;
        TextView contact_number;
        ImageView checkmark;

        public static Placeholder generate(View convertView){
            Placeholder placeholder = new Placeholder();
            placeholder.contact_name = (TextView) convertView.findViewById(R.id.contact_name);
            placeholder.contact_number = (TextView) convertView.findViewById(R.id.contact_number);
            placeholder.checkmark = (ImageView) convertView.findViewById(R.id.checkmark);

            return placeholder;
        }
    }
    public View getView(int position, View convertView, ViewGroup parent){

        Placeholder placeholder;
        if(convertView == null){
            convertView = View.inflate(context, R.layout.item_contact, null);
            placeholder = Placeholder.generate(convertView);
            convertView.setTag(placeholder);
        }else{
            placeholder = (Placeholder) convertView.getTag();
        }
        //Uri u = arrayList.get(position).getPhoto();
        placeholder.contact_name.setText(arrayList.get(position).getName());
        placeholder.contact_number.setText(arrayList.get(position).getPhone());
        if(arrayList.get(position).isSelected()) {
            placeholder.checkmark.setVisibility(View.VISIBLE);
        }else{
            placeholder.checkmark.setVisibility(View.INVISIBLE);
        }


//        if (u != null) {
//            placeholder.contact_image.setImageURI(u);
//        } else {
//            placeholder.contact_image.setImageResource(R.drawable.ic_launcher);
//        }

        return  (convertView);

    }
}
