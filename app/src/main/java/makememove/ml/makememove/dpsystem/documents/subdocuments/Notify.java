package makememove.ml.makememove.dpsystem.documents.subdocuments;

public class Notify {
    int id;
    int eventId;
    int user_id;
    int type;
    String message;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEvent_id() {
        return eventId;
    }

    public void setEvent_id(int event_id) {
        this.eventId = event_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
