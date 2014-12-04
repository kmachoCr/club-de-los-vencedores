package com.lecz.clubdelosvencedores;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.lecz.clubdelosvencedores.objects.Video;
import com.lecz.clubdelosvencedores.register.RegisterActivityFive;
import com.lecz.clubdelosvencedores.register.RegisterActivityTwo;
import com.lecz.clubdelosvencedores.utilities.GetYouTubeUserVideosTask;
import com.lecz.clubdelosvencedores.utilities.Library;
import com.lecz.clubdelosvencedores.utilities.VideosListView;

import java.util.ArrayList;
import java.util.List;


public class VideosActivity extends Activity {
    // A reference to our list that will hold the video details
    private VideosListView listView;
    private List<Video> list;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        listView = (VideosListView) findViewById(R.id.videosListView);
        new Thread(new GetYouTubeUserVideosTask(responseHandler, "iafacr")).start();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(getApplication(), VideoPlayer.class);

                myIntent.putExtra("url", list.get(position).getUrl());
                startActivity(myIntent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        switch (id) {
            case R.id.gotoUpdateInfo:
                Intent intent = new Intent(getApplicationContext(), UpdateInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.gotoUpdateFriends:
                Intent intents = new Intent(getApplicationContext(), RegisterActivityFive.class);
                startActivity(intents);
                break;
            case R.id.gotoRestartPlan:
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

                builder.setMessage("Está seguro que desea reiniciar el plan de fumado?").setIcon(R.drawable.ic_launcher)
                        .setTitle("Reiniciar plan?");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intents = new Intent(getApplicationContext(), RegisterActivityTwo.class);
                        startActivity(intents);
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // This is the XML onClick listener to retreive a users video feed
    public void getUserYouTubeFeed(View v){
        // We start a new task that does its work on its own thread
        // We pass in a handler that will be called when the task has finished
        // We also pass in the name of the user we are searching YouTube for

    }

    // This is the handler that receives the response when the YouTube task has finished
    Handler responseHandler = new Handler() {
        public void handleMessage(Message msg) {
            populateListWithVideos(msg);
        };
    };

    /**
     * This method retrieves the Library of videos from the task and passes them to our ListView
     * @param msg
     */
    private void populateListWithVideos(Message msg) {
        // Retreive the videos are task found from the data bundle sent back
        Library lib = (Library) msg.getData().get(GetYouTubeUserVideosTask.LIBRARY);
        // Because we have created a custom ListView we don't have to worry about setting the adapter in the activity
        // we can just call our custom method with the list of items we want to display
        list = lib.getVideos();
        listView.setVideos(lib.getVideos());
    }

    @Override
    protected void onStop() {
        // Make sure we null our handler when the activity has stopped
        // because who cares if we get a callback once the activity has stopped? not me!
        responseHandler = null;
        super.onStop();
    }
}
