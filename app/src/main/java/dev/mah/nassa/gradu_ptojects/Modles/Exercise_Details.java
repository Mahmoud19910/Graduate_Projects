package dev.mah.nassa.gradu_ptojects.Modles;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Exercise_Details {
    @PrimaryKey(autoGenerate = true)
    private
    int id;
    private String uid;
    private String caloriesBurned;
    private String exerciseTime;
    private String date;
    private String exerciseName;
    private String exerciseDuration;

    public Exercise_Details() {
    }

    public Exercise_Details(String uid, String caloriesBurned, String exerciseTime, String date, String exerciseName, String exerciseDuration) {
        this.uid = uid;
        this.caloriesBurned = caloriesBurned;
        this.exerciseTime = exerciseTime;
        this.date = date;
        this.exerciseName = exerciseName;
        this.exerciseDuration = exerciseDuration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(String caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public String getExerciseTime() {
        return exerciseTime;
    }

    public void setExerciseTime(String exerciseTime) {
        this.exerciseTime = exerciseTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getExerciseDuration() {
        return exerciseDuration;
    }

    public void setExerciseDuration(String exerciseDuration) {
        this.exerciseDuration = exerciseDuration;
    }
}
