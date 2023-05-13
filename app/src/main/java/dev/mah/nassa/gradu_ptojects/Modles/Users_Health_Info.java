package dev.mah.nassa.gradu_ptojects.Modles;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "usersHealthTable")
public class Users_Health_Info {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String userId;
    private double caloriesNumber;
    private double waterDrink;
    private boolean illness;
    private Long medicineTime;
   private double burnedCaloriesNumber;
   private double caloriesGained;


    public Users_Health_Info() {
    }

    public Users_Health_Info(String userId, double caloriesNumber, double waterDrink, boolean illness, Long medicineTime , double burnedCaloriesNumber , double caloriesGained ) {
        this.userId = userId;
        this.caloriesNumber = caloriesNumber;
        this.waterDrink = waterDrink;
        this.illness = illness;
        this.medicineTime = medicineTime;
        this.setBurnedCaloriesNumber(burnedCaloriesNumber);
        this.setCaloriesGained(caloriesGained);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getCaloriesNumber() {
        return caloriesNumber;
    }

    public void setCaloriesNumber(double caloriesNumber) {
        this.caloriesNumber = caloriesNumber;
    }

    public double getWaterDrink() {
        return waterDrink;
    }

    public void setWaterDrink(double waterDrink) {
        this.waterDrink = waterDrink;
    }

    public boolean isIllness() {
        return illness;
    }

    public void setIllness(boolean illness) {
        this.illness = illness;
    }

    public Long getMedicineTime() {
        return medicineTime;
    }

    public void setMedicineTime(Long medicineTime) {
        this.medicineTime = medicineTime;
    }

    public double getBurnedCaloriesNumber() {
        return burnedCaloriesNumber;
    }

    public void setBurnedCaloriesNumber(double burnedCaloriesNumber) {
        this.burnedCaloriesNumber = burnedCaloriesNumber;
    }

    public double getCaloriesGained() {
        return caloriesGained;
    }

    public void setCaloriesGained(double caloriesGained) {
        this.caloriesGained = caloriesGained;
    }
}