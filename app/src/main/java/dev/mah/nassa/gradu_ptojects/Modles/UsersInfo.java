package dev.mah.nassa.gradu_ptojects.Modles;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "usersTable")
public class UsersInfo implements Serializable{
    @PrimaryKey
    @NonNull
    private String uid ;
    private String name ;
    private String phone ;
    private String pass;
    @NonNull
    private String eage ;
    @NonNull
    private String length ;
    @NonNull
    private String weight;
    private String activityLevel ;
    private String gender ;
    private String photo ;
    private String email;
    private int itemSpn;

    public int getItemSpn() {
        return itemSpn;
    }

    public void setItemSpn(int itemSpn) {
        this.itemSpn = itemSpn;
    }

    public UsersInfo() {
    }


    public UsersInfo(String uid, String name, String phone, String pass, String eage, String length, String weight, String activityLevel, String gender, String photo, String email) {
        this.uid = uid;
        this.name = name;
        this.phone = phone;
        this.pass = pass;
        this.eage = eage;
        this.length = length;
        this.weight = weight;
        this.activityLevel = activityLevel;
        this.gender = gender;
        this.photo = photo;
        this.email = email;
    }
    public UsersInfo(String uid, String name, String phone,  String eage, String length, String weight, String email) {
        this.uid = uid;
        this.name = name;
        this.phone = phone;
        this.eage = eage;
        this.length = length;
        this.weight = weight;
        this.email = email;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEage() {
        return eage;
    }

    public void setEage(String eage) {
        this.eage = eage;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(String activityLevel) {
        this.activityLevel = activityLevel;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}