package com.lecz.clubdelosvencedores.objects;

import java.util.Date;

/**
 * Created by Luis on 9/29/2014.
 */
public class User {
    private int id;
    private String name;
    private int age;
    private boolean genre;
    private boolean smoking;
    private int days_without_smoking;
    private int cigarettes_per_day;
    private int days_without_smoking_count;
    private int plan_type;
    private int cigarettes_no_smoked;
    private int money_saved;

    public User(){}

    public User(String name, int age, boolean genre, Date register_date, int plan_type) {
        this.name = name;
        this.age = age;
        this.genre = genre;
        this.days_without_smoking = 0;
        this.days_without_smoking_count = 0;
        this.plan_type = plan_type;
        this.cigarettes_no_smoked = 0;
        this.money_saved = 0;
    }

    public int getCigarettes_per_day() {
        return cigarettes_per_day;
    }

    public void setCigarettes_per_day(int cigarettes_per_day) {
        this.cigarettes_per_day = cigarettes_per_day;
    }

    public boolean getSmoking() {
        return smoking;
    }

    public void setSmoking(boolean smoking) {
        this.smoking = smoking;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean getGenre() {
        return genre;
    }

    public void setGenre(boolean genre) {
        this.genre = genre;
    }

    public int getDays_without_smoking() {
        return days_without_smoking;
    }

    public void setDays_without_smoking(int days_without_smoking) {
        this.days_without_smoking = days_without_smoking;
    }

    public int getDays_without_smoking_count() {
        return days_without_smoking_count;
    }

    public void setDays_without_smoking_count(int days_without_smoking_count) {
        this.days_without_smoking_count = days_without_smoking_count;
    }

    public int getPlan_type() {
        return plan_type;
    }

    public void setPlan_type(int plan_type) {
        this.plan_type = plan_type;
    }

    public int getCigarettes_no_smoked() {
        return cigarettes_no_smoked;
    }

    public void setCigarettes_no_smoked(int cigarettes_no_smoked) {
        this.cigarettes_no_smoked = cigarettes_no_smoked;
    }

    public int getMoney_saved() {
        return money_saved;
    }

    public void setMoney_saved(int money_saved) {
        this.money_saved = money_saved;
    }
}
