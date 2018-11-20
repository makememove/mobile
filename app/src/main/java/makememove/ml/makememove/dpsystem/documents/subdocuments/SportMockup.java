package makememove.ml.makememove.dpsystem.documents.subdocuments;

public class SportMockup {
    private int id;
    private String name;
    private int popularity;

    public SportMockup(int id, String name, int popularity) {
        this.id = id;
        this.name = name;
        this.popularity = popularity;
    }

    public SportMockup() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }
}
