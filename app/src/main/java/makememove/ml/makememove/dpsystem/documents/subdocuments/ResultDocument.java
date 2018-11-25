package makememove.ml.makememove.dpsystem.documents.subdocuments;

public class ResultDocument {
    private int teamId;
    private int place;
    private String teamName;

    public ResultDocument(int teamId, int place, String teamName){
        this.teamId = teamId;
        this.place = place;
        this.teamName = teamName;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }


    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}

