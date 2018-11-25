package makememove.ml.makememove.dpsystem.documents.subdocuments;

import java.util.List;

import javax.xml.transform.Result;

import makememove.ml.makememove.dpsystem.documents.Document;
import makememove.ml.makememove.dpsystem.documents.RankDocument;

public class FinishedRank extends Document {
    private List<ResultDocument> rankings;

    public FinishedRank(){
        super();
    }

    public List<ResultDocument> getRankings() {
        return rankings;
    }

    public void setRankings(List<ResultDocument> rankings) {
        this.rankings = rankings;
    }

    @Override
    public void setData(Document document) {
        this.rankings = ((FinishedRank) document).getRankings();
        sendNotification();
    }
}
