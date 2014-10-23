package com.lecz.clubdelosvencedores.general;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lecz.clubdelosvencedores.R;

import java.util.ArrayList;

/**
 * Created by Luis on 9/29/2014.
 */
public class HomeFragment_1 extends Fragment {
    private View rootView;
    private SharedPreferences settings;
    private ImageButton addButton;
    private TextView currentCigarette;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        Activity host = (Activity) rootView.getContext();

        addButton = (ImageButton) rootView.findViewById(R.id.add_cigarette);
        currentCigarette = (TextView) host.findViewById(R.id.textView4);

        settings = PreferenceManager.getDefaultSharedPreferences(rootView.getContext());
        int ret = settings.getInt("friend01", 0);
        ArrayList<String> phones = new ArrayList<String>();
        ContentResolver cr = host.getContentResolver();
        Cursor cursor = cr.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                new String[]{ret+""}, null);

        while (cursor.moveToNext())
        {
            phones.add(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
        }

        cursor.close();

        int as = settings.getInt("count", 1);
        currentCigarette.setText(as + " / 10");

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                settings = PreferenceManager.getDefaultSharedPreferences(rootView.getContext());
                int ret = settings.getInt("count", 1);

                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("count", ret + 1);
                editor.commit();

                currentCigarette.setText(String.valueOf(ret + 1)+ " / 10");
            }
        });

        return rootView;
    }

}