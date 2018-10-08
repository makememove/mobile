package makememove.ml.makememove.retrofitexample;

public class Description {
    private String id;
    private String title;
    private String description;
    private String time;

    public Description(String id, String title, String description, String time) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }


}
