package com.lecz.clubdelosvencedores.objects;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by Arte-2 on 02/10/2014.
 */
public class Contact {
    private int id;
    private String name;
    private String phone;
    private Uri photo;
    private boolean selected;

    public Contact() {}

    public Contact(int id, String name, String phone, boolean selected) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Uri getPhoto() {
        return photo;
    }

    public void setPhoto(Uri photo) {
        this.photo = photo;
    }
}
