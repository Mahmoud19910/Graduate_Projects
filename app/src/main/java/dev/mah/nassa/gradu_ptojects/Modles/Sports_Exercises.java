package dev.mah.nassa.gradu_ptojects.Modles;

import androidx.annotation.NonNull;
import androidx.room.DeleteTable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Sports_Exercises implements Serializable {
    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private String descriotion;
    private String imageUrl;
    private String metValue;

    public Sports_Exercises(String id, String name, String descriotion, String imageUrl , String metValue) {
        this.id = id;
        this.name = name;
        this.descriotion = descriotion;
        this.imageUrl = imageUrl;
        this.setMetValue(metValue);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriotion() {
        return descriotion;
    }

    public void setDescriotion(String descriotion) {
        this.descriotion = descriotion;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMetValue() {
        return metValue;
    }

    public void setMetValue(String metValue) {
        this.metValue = metValue;
    }
}
