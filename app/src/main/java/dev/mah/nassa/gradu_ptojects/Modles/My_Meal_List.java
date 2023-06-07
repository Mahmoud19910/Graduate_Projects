package dev.mah.nassa.gradu_ptojects.Modles;

import java.io.Serializable;

public class My_Meal_List implements Serializable {
    private String uid ;
    private String id;
    private String nameMeal,caloriesMeal,time , date , weightMeal;

    public My_Meal_List(String uid, String id, String nameMeal, String caloriesMeal, String time, String date, String weightMeal) {
        this.uid = uid;
        this.id = id;
        this.nameMeal = nameMeal;
        this.caloriesMeal = caloriesMeal;
        this.time = time;
        this.date = date;
        this.weightMeal = weightMeal;
    }

    public My_Meal_List() {
    }

    public String getWeight() {
        return weightMeal;
    }

    public void setWeight(String weight) {
        this.weightMeal = weight;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameMeal() {
        return nameMeal;
    }

    public void setNameMeal(String nameMeal) {
        this.nameMeal = nameMeal;
    }

    public String getCaloriesMeal() {
        return caloriesMeal;
    }

    public void setCaloriesMeal(String caloriesMeal) {
        this.caloriesMeal = caloriesMeal;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
