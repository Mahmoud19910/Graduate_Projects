package dev.mah.nassa.gradu_ptojects.Modles;

import java.io.Serializable;

public class Sports_Exercises implements Serializable {
    private String id;
    private String name;
    private String descriotion;
    private String imageUrl;

    public Sports_Exercises(String id, String name, String descriotion, String imageUrl) {
        this.id = id;
        this.name = name;
        this.descriotion = descriotion;
        this.imageUrl = imageUrl;
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
}
