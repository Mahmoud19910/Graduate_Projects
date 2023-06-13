package dev.mah.nassa.gradu_ptojects.Modles;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class FoodCategory implements Serializable {
        private String uid ;
        @PrimaryKey
        @NonNull
        private String id;
        private String filePath , departmentName,nameMeal,caloriesMeal,time , date ,weightMeal;

    public FoodCategory() {
    }

    public FoodCategory(String uid, String id, String filePath, String departmentName, String nameMeal, String caloriesMeal, String time, String date, String weightMeal) {
        this.uid = uid;
        this.id = id;
        this.filePath = filePath;
        this.departmentName = departmentName;
        this.nameMeal = nameMeal;
        this.caloriesMeal = caloriesMeal;
        this.time = time;
        this.date = date;
        this.weightMeal = weightMeal;
    }

    public String getWeightMeal() {
        return weightMeal;
    }

    public void setWeightMeal(String weightMeal) {
        this.weightMeal = weightMeal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    // getter method for our id
          public String getId() {
        return id;
          }
        // setter method for our id
        public void setId(String id) {
        this.id = id;
          }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
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


    }


