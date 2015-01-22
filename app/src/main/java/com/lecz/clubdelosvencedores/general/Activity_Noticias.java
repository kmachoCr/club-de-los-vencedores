package com.lecz.clubdelosvencedores.general;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.lecz.clubdelosvencedores.UpdateInfoActivity;
import com.lecz.clubdelosvencedores.UpdatePlanActivity;
import com.lecz.clubdelosvencedores.objects.Notice;

import com.lecz.clubdelosvencedores.R;
import com.lecz.clubdelosvencedores.register.ActivityFriends;
import com.lecz.clubdelosvencedores.register.RegisterActivityFive;
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
        getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	private void inicializarListView() {
        View footer = getLayoutInflater().inflate(R.layout.footer_list, null);
		lista = (ListView) findViewById(R.id.noticias_listview);
        Array_Noticias.get(0).getImage();
        adapter = new NoticeAdapter(Activity_Noticias.this, Array_Noticias);
        lista.addFooterView(footer);
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
        }else{
            Log.i("Online:","No online");
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
			XMLParser parser = new XMLParser(feedUrl, getBaseContext(), Array_Noticias.size());
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

        switch (id) {
            case R.id.gotoUpdateInfo:
                Intent intent = new Intent(getApplicationContext(), UpdateInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.gotoUpdateFriends:
                Intent intents = new Intent(getApplicationContext(), ActivityFriends.class);
                startActivity(intents);
                break;
            case R.id.gotoRestartPlan:
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

                builder.setMessage("Está seguro que desea reiniciar el plan de fumado?").setIcon(R.drawable.pulmones)
                        .setTitle("Reiniciar plan?");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intents = new Intent(getApplicationContext(), UpdatePlanActivity.class);
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
