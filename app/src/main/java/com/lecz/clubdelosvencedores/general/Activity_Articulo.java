package com.lecz.clubdelosvencedores.general;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.lecz.clubdelosvencedores.R;
import com.lecz.clubdelosvencedores.UpdateInfoActivity;
import com.lecz.clubdelosvencedores.UpdatePlanActivity;
import com.lecz.clubdelosvencedores.objects.Notice;
import com.lecz.clubdelosvencedores.register.ActivityFriends;
import com.lecz.clubdelosvencedores.register.RegisterActivityFive;
import com.lecz.clubdelosvencedores.register.RegisterActivityTwo;

public class Activity_Articulo extends Activity {

	String title;
    String date;
    String content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_articulo);
		recogerparametro();
		populateWebView();
        getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	private void recogerparametro() {
		title =  getIntent().getExtras().getString("title");
        date =  getIntent().getExtras().getString("date");
        content =  getIntent().getExtras().getString("content");
	}

	private void populateWebView() {
		WebView webview = (WebView) findViewById(R.id.articulo_Webview);

		webview.loadDataWithBaseURL(null, "<!DOCTYPE HTML>" + populateHTML(R.raw.htmlnoticia) + "<script></script>", "text/html", "UTF-8", null);
        webview.setWebChromeClient(new WebChromeClient());
        webview.getSettings().setJavaScriptEnabled(true);
	}

	private String populateHTML(int resourceID) {
		String html;
		html = readTextFromResource(resourceID);
		html = html.replace("_TITLE_", title);
		html = html.replace("_PUBDATE_", "" + date);
		html = html.replace("_CONTENT_", content);
		return html;
	}

	private String readTextFromResource(int resourceID) {
		InputStream raw = getResources().openRawResource(resourceID);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		int i;
		try {
			i = raw.read();
			while (i != -1) {
				stream.write(i);
				i = raw.read();
			}
			raw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stream.toString();
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.intern, menu);
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

        }
        return super.onOptionsItemSelected(item);
    }


}
