package com.lecz.clubdelosvencedores.general;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.lecz.clubdelosvencedores.objects.Notice;
import com.lecz.clubdelosvencedores.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NoticeAdapter extends ArrayAdapter<Object> {
	Context context;
	private ArrayList<Notice> noticias;


	public NoticeAdapter(Context context, ArrayList<Notice> noticias) {
		super(context, R.layout.item_contact);
		this.context = context;
		this.noticias = noticias;

	}

	@Override
	public int getCount() {
		return noticias.size();
	}

	private static class PlaceHolder {

		TextView title;
		TextView time;
		TextView content;
		
		ImageView picture;

		public static PlaceHolder generate(View convertView) {
			PlaceHolder placeHolder = new PlaceHolder();
			placeHolder.title = (TextView) convertView
					.findViewById(R.id.noticia_textview_title);
			placeHolder.time = (TextView) convertView
					.findViewById(R.id.noticia_textview_time);
			placeHolder.content = (TextView) convertView
					.findViewById(R.id.noticia_textview_content);
			
			placeHolder.picture = (ImageView) convertView
					.findViewById(R.id.noticia_imageView);
			return placeHolder;
		}

	}

	public View getView(int position, View convertView, ViewGroup parent) {
		PlaceHolder placeHolder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_noticias, null);
			placeHolder = PlaceHolder.generate(convertView);
			convertView.setTag(placeHolder);
		} else {
			placeHolder = (PlaceHolder) convertView.getTag();
		}
		placeHolder.title.setText(noticias.get(position).getTitle());
		placeHolder.time.setText(""
				+ noticias.get(position).getDate().getDate() + "/"
				+ noticias.get(position).getDate().getMonth());
		placeHolder.content.setText(noticias.get(position).getSummary());

        placeHolder.picture.setImageBitmap(noticias.get(position).getImage());
		return (convertView);
	}



    public byte[] convertBitmapToByteArray(Context context, Bitmap bitmap) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream(bitmap.getWidth() * bitmap.getHeight());
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, buffer);
        return buffer.toByteArray();
    }


}
