package com.lecz.clubdelosvencedores.general;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.lecz.clubdelosvencedores.AdviceActivity;
import com.lecz.clubdelosvencedores.DatabaseManagers.ContactFriendSource;
import com.lecz.clubdelosvencedores.Game.Game;
import com.lecz.clubdelosvencedores.PreGameActivity;
import com.lecz.clubdelosvencedores.R;
import com.lecz.clubdelosvencedores.VideosActivity;
import com.lecz.clubdelosvencedores.objects.Contact;

import java.util.ArrayList;


/**
 *
 */
public class fragment_slide extends Fragment {
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_slide, container, false);

        ImageButton gotoGame = (ImageButton) rootView.findViewById(R.id.gotoGame);
        ImageButton gotoCall = (ImageButton) rootView.findViewById(R.id.gotoCall);
        ImageButton gotoVideo = (ImageButton) rootView.findViewById(R.id.gotoVideo);
        ImageButton gotoConsejo = (ImageButton) rootView.findViewById(R.id.gotoConsejo);



        gotoGame.setOnClickListener(new View.OnClickListener() {

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

        gotoCall.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ContactFriendSource cds = new ContactFriendSource(rootView.getContext());
                cds.open();
                ArrayList<Contact> list = cds.getContacts();
                cds.close();
                if(list.size() > 0){
                    Intent myIntent = new Intent(rootView.getContext(), CallFiendsActivity.class);
                    startActivity(myIntent);
                }else{
                    Toast toast = Toast.makeText(rootView.getContext(), "Primero debes agregar algunos amigos", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });

        gotoVideo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(isOnline()){
                    Intent myIntent = new Intent(rootView.getContext(), VideosActivity.class);
                    startActivity(myIntent);
                }else{
                    Toast toast = Toast.makeText(rootView.getContext(), "No hay conexión a internet", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });

        gotoConsejo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(rootView.getContext(), AdviceActivity.class);
                startActivity(myIntent);
            }
        });
        return rootView;
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) rootView.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        Log.i("Status", "Llego");
        // test for connection
        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            Log.i("Status","Online");
            return true;
        } else {
            Log.i("Status","Offline");
            return false;
        }
    }
}
