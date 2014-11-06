package com.lecz.clubdelosvencedores.general;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.lecz.clubdelosvencedores.R;
import com.lecz.clubdelosvencedores.objects.Notice;

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
	}

	private void recogerparametro() {
		title =  getIntent().getExtras().getString("title");
        date =  getIntent().getExtras().getString("date");
        content =  getIntent().getExtras().getString("content");
	}

	private void populateWebView() {
		WebView webview = (WebView) findViewById(R.id.articulo_Webview);
		webview.loadDataWithBaseURL(null, "<!DOCTYPE HTML>"
				+ populateHTML(R.raw.htmlnoticia), "text/html", "UTF-8", null);
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

}
