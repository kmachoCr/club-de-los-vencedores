package com.lecz.clubdelosvencedores.objects;

import java.util.Date;

/**
 * Created by Luis on 9/29/2014.
 */
public class PlanDetail {
    private int id;
    private int number_day;
    private int total_cigarettes;
    private int used_cigarettes;
    private boolean approved;
    private boolean current;
    private boolean completed;
    private Long date;

    public PlanDetail() {
    }

    public PlanDetail(int number_day, int total_cigarrettes, int used_cigarrettes, boolean approved, boolean current, Long date, boolean completed) {
        this.number_day = number_day;
        this.total_cigarettes = total_cigarrettes;
        this.used_cigarettes = used_cigarrettes;
        this.approved = approved;
        this.current = current;
        this.date = date;
        this.completed = completed;
    }

    public int getNumber_day() {
        return number_day;
    }

    public void setNumber_day(int number_day) {
        this.number_day = number_day;
    }

    public int getTotal_cigarettes() {
        return total_cigarettes;
    }

    public void setTotal_cigarettes(int total_cigarrettes) {
        this.total_cigarettes = total_cigarrettes;
    }

    public int getUsed_cigarettes() {
        return used_cigarettes;
    }

    public void setUsed_cigarettes(int used_cigarrettes) {
        this.used_cigarettes = used_cigarrettes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}

