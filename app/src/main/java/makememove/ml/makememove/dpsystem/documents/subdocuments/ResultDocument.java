package makememove.ml.makememove.dpsystem.documents.subdocuments;

public class ResultDocument {
    private int teamId;
    private int place;
    private String teamName;

    public ResultDocument(){

    }
    public ResultDocument(int id, int place, String teamName){
        this.teamId = id;
        this.place = place;
        this.teamName = teamName;
    }
    public ResultDocument(int id, String teamName){
        this.teamId = id;
        this.teamName = teamName;
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

    public int getId() {
        return teamId;
    }

    public void setId(int id) {
        this.teamId = id;
    }
}

