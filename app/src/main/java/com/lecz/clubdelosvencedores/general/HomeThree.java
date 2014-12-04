package com.lecz.clubdelosvencedores.general;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lecz.clubdelosvencedores.DatabaseManagers.NoticeDataSource;
import com.lecz.clubdelosvencedores.R;
import com.lecz.clubdelosvencedores.objects.Notice;
import com.lecz.clubdelosvencedores.utilities.XMLParser;

import java.text.DateFormat;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 *
 */
public class HomeThree extends Fragment {
    View rootView;
    public ArrayList<Notice> Array_Noticias = new ArrayList<Notice>();
    private NoticeAdapter adapter;
    private String URL = "http://www.clubdelosvencedores.com/feed/?fsk=54591cdb1e8cd";
    private ListView lista;
    private Button load_more;
    private ProgressBar loadmorepb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home_three, container, false);

        NoticeDataSource nds = new NoticeDataSource(rootView.getContext());
        nds.open();
        Array_Noticias = nds.getNotices();

        if(Array_Noticias.size() > 0){
            inicializarListView();
        }else{
            rellenarNoticias();
        }
        nds.close();


        return rootView;


    }

    private void inicializarListView() {
        View footer = getActivity().getLayoutInflater().inflate(R.layout.footer_list, null);
        lista = (ListView) rootView.findViewById(R.id.noticias_listview);
        adapter = new NoticeAdapter(rootView.getContext(), Array_Noticias);
        lista.addFooterView(footer);
        lista.setAdapter(adapter);

        load_more = (Button) footer.findViewById(R.id.loadmore);
        loadmorepb = (ProgressBar) footer.findViewById(R.id.loadmorepb);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Intent intent = new Intent(rootView.getContext(), Activity_Articulo.class);
                intent.putExtra("title", Array_Noticias.get(arg2).getTitle());
                intent.putExtra("date", DateFormat.getDateInstance().format(Array_Noticias.get(arg2).getDate()));
                intent.putExtra("content", Array_Noticias.get(arg2).getContent());
                startActivity(intent);
            }
        });

        load_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadMOreNotice();
            }
        });


    }

    private void actualizarListView() {
        lista = (ListView) rootView.findViewById(R.id.noticias_listview);
        adapter.notifyDataSetChanged();

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Intent intent = new Intent(rootView.getContext(), Activity_Articulo.class);
                intent.putExtra("title", Array_Noticias.get(arg2).getTitle());
                intent.putExtra("date", DateFormat.getDateInstance().format(Array_Noticias.get(arg2).getDate()));
                intent.putExtra("content", Array_Noticias.get(arg2).getContent());
                startActivity(intent);
            }
        });
    }

    private void rellenarNoticias() {

        if (isOnline()) {
            Log.i("isOnline", "online");
            new DescargarNoticias(rootView.getContext(), URL).execute();
        }else{
            Log.i("isOnline", "offline");
            NoticeDataSource nds = new NoticeDataSource(rootView.getContext());
            nds.open();
            Array_Noticias = nds.getNotices();
            nds.close();
        }
    }

    private void loadMOreNotice() {

        if (isOnline()) {
            loadmorepb.setVisibility(View.VISIBLE);
            load_more.setVisibility(View.INVISIBLE);
            new DescargarNoticias(rootView.getContext(), URL).execute();
        }else{
            Toast toast = Toast.makeText(rootView.getContext(), "No hay conexión a internet", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) rootView.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        Log.i("Status","Llego");
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

    private class DescargarNoticias extends AsyncTask<String, Void, Boolean> {

        private String feedUrl;
        private Context ctx;

        public DescargarNoticias(Context c, String url) {
            this.feedUrl = url;
            this.ctx = c;
        }

        @Override
        protected Boolean doInBackground(final String... args) {
            XMLParser parser = new XMLParser(feedUrl, rootView.getContext(), Array_Noticias.size());
            Array_Noticias = parser.parse();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                try {
                    if(adapter == null){
                        inicializarListView();
                    }else{
                        int scrollX = adapter.getData().size();
                        adapter.getData().clear();
                        adapter = new NoticeAdapter(rootView.getContext(), Array_Noticias);
                        adapter.notifyDataSetChanged();
                        lista.setAdapter(adapter);
                        lista.setSelection(scrollX - 1);
                        loadmorepb.setVisibility(View.INVISIBLE);
                        load_more.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(ctx, "Error en la lectura", Toast.LENGTH_LONG)
                        .show();
            }
        }

    }

}

