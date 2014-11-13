package com.lecz.clubdelosvencedores.general;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.lecz.clubdelosvencedores.AdviceActivity;
import com.lecz.clubdelosvencedores.Game.Game;
import com.lecz.clubdelosvencedores.PreGameActivity;
import com.lecz.clubdelosvencedores.R;
import com.lecz.clubdelosvencedores.VideosActivity;


/**
 *
 */
public class fragment_confirmation extends Fragment {
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_confirmation, container, false);

        ImageButton yes = (ImageButton) rootView.findViewById(R.id.confirmation_yes);
        ImageButton no = (ImageButton) rootView.findViewById(R.id.confirmation_no);
        toggleListConf();
        toggleListConf();
        yes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                SharedPreferences mPrefs = getActivity().getSharedPreferences("label", 0);
                Boolean show = mPrefs.getBoolean("show_again", true);
                Intent myIntent;
                if(show){
                    myIntent = new Intent(rootView.getContext(), PreGameActivity.class);
                }else{
                    myIntent = new Intent(rootView.getContext(), Game.class);
                }

                startActivity(myIntent);
            }
        });

        no.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                toggleListConf();

            }
        });
        return rootView;
    }

    private void toggleListConf() {
        Fragment f = getFragmentManager()
                .findFragmentByTag("fragment_confirmation");
        if (f != null) {
            getFragmentManager().popBackStack();
        } else {
            getFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_left,
                            R.anim.slide_right,
                            R.anim.slide_left,
                            R.anim.slide_right)
                    .add(R.id.confirmation_fragment, Fragment.instantiate(rootView.getContext(), fragment_confirmation.class.getName()),
                            "fragment_confirmation"
                    ).addToBackStack(null).commit();
        }
    }
}
