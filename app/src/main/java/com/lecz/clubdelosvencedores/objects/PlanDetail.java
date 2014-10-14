package com.lecz.clubdelosvencedores.objects;

/**
 * Created by Luis on 9/29/2014.
 */
public class PlanDetail {
    private int number_day;
    private int total_cigarettes;
    private int used_cigarettes;
    private boolean approved;
    private boolean current;

    public PlanDetail() {
    }

    public PlanDetail(int number_day, int total_cigarrettes, int used_cigarrettes, boolean approved, boolean current) {
        this.number_day = number_day;
        this.total_cigarettes = total_cigarrettes;
        this.used_cigarettes = used_cigarrettes;
        this.approved = approved;
        this.current = current;
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
}

