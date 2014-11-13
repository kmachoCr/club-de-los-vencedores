package com.lecz.clubdelosvencedores.objects;

/**
 * Created by Luis on 11/11/2014.
 */
public class Advice {
    private int id;
    private String type;
    private String body;
    private boolean cat_genre;
    private boolean motiv_money;
    private boolean motiv_aesthetic;
    private boolean motiv_family;
    private boolean motiv_health;


    public Advice() {
    }


    public Advice(int id, boolean motiv_health, boolean motiv_family, boolean motiv_aesthetic, boolean motiv_money, boolean cat_genre, String body, String type) {
        this.id = id;
        this.motiv_health = motiv_health;
        this.motiv_family = motiv_family;
        this.motiv_aesthetic = motiv_aesthetic;
        this.motiv_money = motiv_money;
        this.cat_genre = cat_genre;
        this.body = body;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setCat_genre(boolean cat_genre) {
        this.cat_genre = cat_genre;
    }

    public boolean isMotiv_money() {
        return motiv_money;
    }

    public void setMotiv_money(boolean motiv_money) {
        this.motiv_money = motiv_money;
    }

    public boolean isMotiv_health() {
        return motiv_health;
    }

    public void setMotiv_health(boolean motiv_health) {
        this.motiv_health = motiv_health;
    }

    public boolean isMotiv_family() {
        return motiv_family;
    }

    public void setMotiv_family(boolean motiv_family) {
        this.motiv_family = motiv_family;
    }

    public boolean isMotiv_aesthetic() {
        return motiv_aesthetic;
    }

    public void setMotiv_aesthetic(boolean motiv_aesthetic) {
        this.motiv_aesthetic = motiv_aesthetic;
    }

    public boolean isCat_genre() {
        return cat_genre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
