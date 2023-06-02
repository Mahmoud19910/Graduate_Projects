package dev.mah.nassa.gradu_ptojects.Modles;

public class Notifications {
    private String id;
    private String token;
    private String title;
    private String message;

    public Notifications() {
    }

    public Notifications(String id, String token, String title, String message) {
        this.id = id;
        this.token = token;
        this.title = title;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
