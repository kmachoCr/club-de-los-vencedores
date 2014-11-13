package com.lecz.clubdelosvencedores.objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.lecz.clubdelosvencedores.LocalService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

public class Notice implements Serializable{

    private int id;
    private String title;
    private String content;
    private String summary;
    private String link;
    private String url;
    private Bitmap image;
    private Long date;

    public Notice(String title, String content, String summary, String link, Long date, String url) {
        this.title = title;
        this.content = content;
        this.summary = summary;
        this.link = link;
        this.date = date;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Notice() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Bitmap getImage() {
        return this.image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public byte[] convertBitmapToByteArray(Bitmap bitmap) {
        if(bitmap != null){
            Bitmap photo = bitmap;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] bArray = bos.toByteArray();

            return bArray;
        }else{
            Log.i("Bitmap", "null");
            return null;
        }



    }

    public Bitmap convertByteArrayToBitmap(byte[] photo) {
        byte[] imgbytes = Base64.decode(photo, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imgbytes, 0,
                imgbytes.length);

        return bitmap;
    }


}
