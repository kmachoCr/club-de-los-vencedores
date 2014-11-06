package com.lecz.clubdelosvencedores.general;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lecz.clubdelosvencedores.Game.Game;
import com.lecz.clubdelosvencedores.R;
import com.lecz.clubdelosvencedores.VideosActivity;


/**
 *
 */
public class fragment_slide extends Fragment {
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_slide, container, false);

        Button gotoGame = (Button) rootView.findViewById(R.id.gotoGame);
        Button gotoCall = (Button) rootView.findViewById(R.id.gotoCall);
        Button gotoVideo = (Button) rootView.findViewById(R.id.gotoVideo);
        Button gotoConsejo = (Button) rootView.findViewById(R.id.gotoConsejo);

        gotoGame.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(rootView.getContext(), Game.class);
                startActivity(myIntent);
            }
        });

        gotoCall.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(rootView.getContext(), CallFiendsActivity.class);
                startActivity(myIntent);
            }
        });

        gotoVideo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(rootView.getContext(), VideosActivity.class);
                startActivity(myIntent);
            }
        });

        gotoConsejo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(rootView.getContext(), Activity_Noticias.class);
                startActivity(myIntent);
            }
        });
        return rootView;
    }
}
