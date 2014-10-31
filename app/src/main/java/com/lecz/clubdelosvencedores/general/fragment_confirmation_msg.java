package com.lecz.clubdelosvencedores.general;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lecz.clubdelosvencedores.R;


/**
 *
 */
public class fragment_confirmation_msg extends Fragment {
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_confirmation_msg, container, false);

        return rootView;
    }
}
