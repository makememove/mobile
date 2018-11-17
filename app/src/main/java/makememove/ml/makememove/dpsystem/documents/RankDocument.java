package makememove.ml.makememove.dpsystem.documents;

import makememove.ml.makememove.user.Sport;

public class RankDocument extends Document{
    private SportRankDocument sport;

    public RankDocument(){
        super();
        this.sport = new SportRankDocument();
    }

    @Override
    public void setData(Document document){
        sport = ((RankDocument) document).getSport();
        sendNotification();
    }


    public SportRankDocument getSport() {
        return sport;
    }

    public void setSport(SportRankDocument sport) {
        this.sport = sport;
    }
}
