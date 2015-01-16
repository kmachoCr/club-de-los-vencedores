package com.lecz.clubdelosvencedores.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.Log;

import com.lecz.clubdelosvencedores.DatabaseManagers.NoticeDataSource;
import com.lecz.clubdelosvencedores.R;
import com.lecz.clubdelosvencedores.general.Activity_Noticias;
import com.lecz.clubdelosvencedores.objects.Notice;

public class XMLParser {
	private URL url;
	Context contextor;
    NoticeDataSource nds;
    int size;
    ArrayList<Notice> dbNotices;
	
	public XMLParser(String url,Context contexto, int size) {
		contextor=contexto;
        this.size = size;
        nds = new NoticeDataSource(contexto);
        nds.open();
        this.dbNotices = nds.getNotices();
        nds.close();
        try {
			this.url = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Notice> parse() {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		ArrayList<Notice> noticias = new ArrayList<Notice>();
		DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy kk:mm:ss Z", Locale.ENGLISH);
		Notice noticia;

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document dom = builder.parse(this.url.openConnection().getInputStream());
			Element root = dom.getDocumentElement();
			NodeList items = root.getElementsByTagName("item");
            Log.i("Size", dbNotices.size()+"");
            int totaldb = dbNotices.size();

            if(items.getLength() > totaldb){
                int dif = items.getLength() - dbNotices.size();

                size = totaldb + 5;

                for (int i = 0; i < (items.getLength() < size? items.getLength(): size); i++){
                    noticia = new Notice();
                    Node item = items.item(i);
                    NodeList properties = item.getChildNodes();
                    for (int j=0;j<properties.getLength();j++){
                        Node property = properties.item(j);
                        String name = property.getNodeName();
                        if (name.equalsIgnoreCase("title")){
                            noticia.setTitle(property.getFirstChild().getNodeValue());
                        }else if (name.equalsIgnoreCase("description")){
                            int delimiter = (property.getFirstChild().getNodeValue()).indexOf(" ] ");
                            String html = property.getFirstChild().getNodeValue().substring(delimiter + 1);
                            noticia.setSummary(String.valueOf(stripHtml(html)));


                        }else if (name.equalsIgnoreCase("content:encoded")){
                            int delimiter = (property.getFirstChild().getNodeValue()).indexOf(" ] ");
                            String html = property.getFirstChild().getNodeValue().substring(delimiter + 1);
                            noticia.setContent(html);

                            int startdelimiterImage = property.getFirstChild().getNodeValue().indexOf("src=");
                            if(startdelimiterImage!=-1){

                                String urlpart = property.getFirstChild().getNodeValue().substring(startdelimiterImage+5);
                                int enddelimiterImage = urlpart.indexOf("\"");
                                noticia.setUrl(property.getFirstChild().getNodeValue().substring(startdelimiterImage + 5, startdelimiterImage + 5 + enddelimiterImage));
                                nds.open();
                                if(nds.getNotice(noticia.getTitle()) == null){
                                    nds.close();
                                    if(getBitmapFromURL(property.getFirstChild().getNodeValue().substring(startdelimiterImage + 5, startdelimiterImage + 5 + enddelimiterImage)) != null){
                                        noticia.setImage(getBitmapFromURL(property.getFirstChild().getNodeValue().substring(startdelimiterImage + 5, startdelimiterImage + 5 + enddelimiterImage)));
                                    }else{
                                        Drawable d = contextor.getResources().getDrawable(R.drawable.checkmark);
                                        noticia.setImage(((BitmapDrawable) d).getBitmap());
                                    }
                                }

                            }
                        }else if (name.equalsIgnoreCase("link")){
                            noticia.setLink(property.getFirstChild().getNodeValue());
                        }else if(name.equalsIgnoreCase("pubDate")){
                            noticia.setDate(formatter.parse(""+property.getFirstChild().getNodeValue()).getTime());
                        }
                    }
                    noticias.add(noticia);
                    nds.open();
                    if(nds.getNotice(noticia.getTitle()) == null){
                        nds.createNotice(noticia);
                    }

                    nds.close();
                    Log.i("Parsher", "notcia:"+i);
                }
            }else{
                return dbNotices;
            }
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 

		return noticias;
	}


    public ArrayList<Notice> update() {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        ArrayList<Notice> noticias = new ArrayList<Notice>();
        DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy kk:mm:ss Z", Locale.ENGLISH);
        Notice noticia;
        size += 5;
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document dom = builder.parse(this.url.openConnection().getInputStream());
            Element root = dom.getDocumentElement();
            NodeList items = root.getElementsByTagName("item");
            Log.i("Size", dbNotices.size()+"");
            if(items.getLength() > dbNotices.size()){
                for (int i = 0; i < (items.getLength() < size? items.getLength(): size); i++){
                    noticia = new Notice();
                    Node item = items.item(i);
                    NodeList properties = item.getChildNodes();
                    for (int j=0;j<properties.getLength();j++){
                        Node property = properties.item(j);
                        String name = property.getNodeName();
                        if (name.equalsIgnoreCase("title")){
                            noticia.setTitle(property.getFirstChild().getNodeValue());
                        }else if (name.equalsIgnoreCase("description")){
                            int delimiter = (property.getFirstChild().getNodeValue()).indexOf(" ] ");
                            String html = property.getFirstChild().getNodeValue().substring(delimiter + 1);
                            noticia.setSummary(String.valueOf(stripHtml(html)));


                        }else if (name.equalsIgnoreCase("content:encoded")){
                            int delimiter = (property.getFirstChild().getNodeValue()).indexOf(" ] ");
                            String html = property.getFirstChild().getNodeValue().substring(delimiter + 1);
                            noticia.setContent(html);


                            int startdelimiterImage = property.getFirstChild().getNodeValue().indexOf("src=");
                            if(startdelimiterImage!=-1){

                                String urlpart = property.getFirstChild().getNodeValue().substring(startdelimiterImage+5);
                                int enddelimiterImage = urlpart.indexOf("\"");
                                noticia.setUrl(property.getFirstChild().getNodeValue().substring(startdelimiterImage + 5, startdelimiterImage + 5 + enddelimiterImage));
                                noticia.setImage(getBitmapFromURL(property.getFirstChild().getNodeValue().substring(startdelimiterImage + 5, startdelimiterImage + 5 + enddelimiterImage)));
                            }

                        }else if (name.equalsIgnoreCase("link")){
                            noticia.setLink(property.getFirstChild().getNodeValue());
                        }else if(name.equalsIgnoreCase("pubDate")){
                            noticia.setDate(formatter.parse(""+property.getFirstChild().getNodeValue()).getTime());
                        }
                    }
                    noticias.add(noticia);
                    nds.open();
                    nds.createNotice(noticia);
                    nds.close();
                    Log.i("Parsher", noticia.getUrl());
                }
            }else{
                return dbNotices;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return noticias;
    }

    public Bitmap getBitmapFromURL(String src) {
        try {
            Log.i("url",src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            connection.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
            connection.connect();

            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }

    public CharSequence stripHtml(String s) {
        return Html.fromHtml(s).toString().replace('\n', (char) 32)
                .replace((char) 160, (char) 32).replace((char) 65532, (char) 32).trim();
    }
	
}
