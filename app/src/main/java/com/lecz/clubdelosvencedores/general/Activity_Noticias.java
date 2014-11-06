package com.lecz.clubdelosvencedores.general;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import com.lecz.clubdelosvencedores.objects.Notice;

import com.lecz.clubdelosvencedores.R;
import com.lecz.clubdelosvencedores.utilities.XMLParser;

public class Activity_Noticias extends Activity {

	public ArrayList<Notice> Array_Noticias = new ArrayList<Notice>();
	private NoticeAdapter adapter;

	private String URL = "http://www.clubdelosvencedores.com/feed/?fsk=54591cdb1e8cd";

	ListView lista;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_noticias);
        rellenarNoticias();
        Log.i("llego", "llego");

	}

	private void inicializarListView() {
		lista = (ListView) findViewById(R.id.noticias_listview);

        adapter = new NoticeAdapter(this, Array_Noticias);
		lista.setAdapter(adapter);

		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(Activity_Noticias.this, Activity_Articulo.class);
				intent.putExtra("title", Array_Noticias.get(arg2).getTitle());
                intent.putExtra("date", DateFormat.getDateInstance().format(Array_Noticias.get(arg2).getDate()));
                intent.putExtra("content", Array_Noticias.get(arg2).getContent());
				startActivity(intent);		
			}
		});
	}

	private void rellenarNoticias() {
		if (isOnline()) {
			new DescargarNoticias(getBaseContext(), URL).execute();
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
			XMLParser parser = new XMLParser(feedUrl, getBaseContext());
			Array_Noticias = parser.parse();
			return true;
		}

		@Override
		protected void onPostExecute(Boolean success) {
			if (success) {
				try {
					inicializarListView();
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				Toast.makeText(ctx, "Error en la lectura", Toast.LENGTH_LONG)
						.show();
			}
		}

	}

	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getApplication()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnected()) {
			return true;
		}
		return false;
	}
}
