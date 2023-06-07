package dev.mah.nassa.gradu_ptojects.Modles;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Doctor implements Serializable {
    @PrimaryKey
    @NonNull
    private String id;
    private String image;
    private String name;
    private String number;
    private String specialization;
    private String address;
    private String description;
    private boolean isSesion;
    private String token;
    public Doctor() {
    }

    public Doctor(String id, String image, String name, String number, String specialization, String address, String description , boolean isSesion , String token) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.number = number;
        this.setSpecialization(specialization);
        this.address = address;
        this.description = description;
        this.setSesion(isSesion);
        this.setToken(token);
    }

    public Doctor(String image, String name, String number, String specialization, String address, String description) {

        this.image = image;
        this.name = name;
        this.number = number;
        this.specialization = specialization;
        this.address = address;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }



    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSesion() {
        return isSesion;
    }

    public void setSesion(boolean sesion) {
        isSesion = sesion;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
