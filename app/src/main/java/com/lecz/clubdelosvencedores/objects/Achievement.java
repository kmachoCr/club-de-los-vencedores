package com.lecz.clubdelosvencedores.objects;

import android.net.Uri;

/**
 * Created by Arte-2 on 02/10/2014.
 */
public class Achievement {
    private int id;
    private String title;
    private String type;
    private Long amount;
    private boolean completed;

    public Achievement() {
    }

    public Achievement(String title, String type, Long amount, boolean completed) {
        this.title = title;
        this.type = type;
        this.amount = amount;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
