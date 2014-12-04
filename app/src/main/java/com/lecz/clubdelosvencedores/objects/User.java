package com.lecz.clubdelosvencedores.objects;

import java.util.Date;

/**
 * Created by Luis on 9/29/2014.
 */
public class User {
    private int days_with_smoking;
    private int id;
    private String name;
    private int age;
    private boolean genre;
    private boolean smoking;
    private Double days_without_smoking;
    private int cigarettes_per_day;
    private Double days_without_smoking_count;
    private int plan_type;
    private int cigarettes_no_smoked;
    private int money_saved;
    private Long last_cigarette;

    public User(){}



    public User(String name, int age, boolean genre, Date register_date, int plan_type) {
        this.name = name;
        this.age = age;
        this.genre = genre;
        this.days_without_smoking = 0.0;
        this.days_without_smoking_count = 0.0;
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
    public int getDays_with_smoking() {
        return days_with_smoking;
    }

    public void setDays_with_smoking(int days_with_smoking) {
        this.days_with_smoking = days_with_smoking;
    }

    public Long getLast_cigarette() {
        return last_cigarette;
    }

    public void setLast_cigarette(Long last_cigarette) {
        this.last_cigarette = last_cigarette;
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

    public Double getDays_without_smoking() {
        return days_without_smoking;
    }

    public void setDays_without_smoking(Double days_without_smoking) {
        this.days_without_smoking = days_without_smoking;
    }

    public Double getDays_without_smoking_count() {
        return days_without_smoking_count;
    }

    public void setDays_without_smoking_count(Double days_without_smoking_count) {
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
