package com.lecz.clubdelosvencedores.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
import android.text.Html;
import android.util.Log;

import com.lecz.clubdelosvencedores.general.Activity_Noticias;
import com.lecz.clubdelosvencedores.objects.Notice;

public class XMLParser {
	private URL url;
	Context contextor;
	
	public XMLParser(String url,Context contexto) {
		contextor=contexto;
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
			for (int i=0;i<items.getLength();i++){
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
						noticia.setDate(formatter.parse(""+property.getFirstChild().getNodeValue()));
					}
				}
				noticias.add(noticia);
				Log.i("Parsher", "notcia:"+i);
			}

			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 

		return noticias;
	}


    public Bitmap getBitmapFromURL(String src) {
        try {

            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
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
