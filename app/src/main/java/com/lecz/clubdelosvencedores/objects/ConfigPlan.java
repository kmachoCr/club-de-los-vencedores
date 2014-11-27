package com.lecz.clubdelosvencedores.objects;

import java.util.ArrayList;

/**
 * Created by Luis on 11/21/2014.
 */
public class ConfigPlan {
    private String number_cigarettes;
    private int id;
    private ArrayList<Integer> dayConfig = new ArrayList<Integer>();
    private String type_plan;

    public ConfigPlan() {
    }

    public ConfigPlan(String number_cigarettes, String type_plan, ArrayList<Integer> dayConfig) {
        this.number_cigarettes = number_cigarettes;
        this.type_plan = type_plan;
        this.dayConfig = dayConfig;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber_cigarettes() {
        return number_cigarettes;
    }

    public void setNumber_cigarettes(String number_cigarettes) {
        this.number_cigarettes = number_cigarettes;
    }

    public String getType_plan() {
        return type_plan;
    }

    public void setType_plan(String type_plan) {
        this.type_plan = type_plan;
    }

    public ArrayList<Integer> getDayConfig() {
        return dayConfig;
    }

    public void setDayConfig(ArrayList<Integer> dayConfig) {
        this.dayConfig = dayConfig;
    }

    public Integer getType_plan(int index) {
        return dayConfig.get(index);
    }
}
