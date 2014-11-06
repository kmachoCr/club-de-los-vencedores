package com.lecz.clubdelosvencedores.objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

public class Notice implements Serializable{

    private String title;
    private String content;
    private String summary;
    private String link;
    private String url;
    private Bitmap image;
    private Date date;

    public Notice(String title, String content, String summary, String link, Date date, String url) {
        this.title = title;
        this.content = content;
        this.summary = summary;
        this.link = link;
        this.date = date;
        this.url = url;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
