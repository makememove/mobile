package makememove.ml.makememove.dpsystem.documents.subdocuments;

public class ResultDocument {
    private int teamId;
    private String place;
    private String teamName;

    public ResultDocument(int teamId, String place, String teamName) {
        this.teamId = teamId;
        this.place = place;
        this.teamName = teamName;
    }

    public ResultDocument() {
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}

