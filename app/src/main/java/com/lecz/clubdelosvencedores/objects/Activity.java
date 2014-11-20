package com.lecz.clubdelosvencedores.objects;

/**
 * Created by Luis on 11/16/2014.
 */
public class Activity {
    private int id, image;
    private Long date;
    private String content, type, title;

    public Activity() {
    }

    public Activity(int image, Long date, String content, String type, String title) {
        this.image = image;
        this.date = date;
        this.content = content;
        this.type = type;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
