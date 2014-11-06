package com.lecz.clubdelosvencedores.general;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lecz.clubdelosvencedores.R;

import java.util.ArrayList;

/**
 * Created by Luis on 11/3/2014.
 */
public class description_achievement extends Fragment {
    private View rootView;
    private TextView title;
    private TextView description;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView =  inflater.inflate(R.layout.achievement_detail, container, false);
        title = (TextView) rootView.findViewById(R.id.title_achievement);
        description = (TextView) rootView.findViewById(R.id.description_achievement);

        SharedPreferences mPrefs = getActivity().getSharedPreferences("label", 0);
        String ttitle = mPrefs.getString("title", "as");
        String tdescription = mPrefs.getString("description", "as");

        title.setText(ttitle);
        description.setText(tdescription);

        return rootView;
    }



}
