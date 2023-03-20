package dev.mah.nassa.gradu_ptojects.Modles;

public class Users_Health_Info {
private int id;
private String userId;
private double caloriesNumber;
private int waterDrink;
private boolean illness;
private Long medicineTime;

    public Users_Health_Info(int id, String userId, double caloriesNumber, int waterDrink, boolean illness, Long medicineTime) {
        this.id = id;
        this.userId = userId;
        this.caloriesNumber = caloriesNumber;
        this.waterDrink = waterDrink;
        this.illness = illness;
        this.medicineTime = medicineTime;
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

    public int getWaterDrink() {
        return waterDrink;
    }

    public void setWaterDrink(int waterDrink) {
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
}
