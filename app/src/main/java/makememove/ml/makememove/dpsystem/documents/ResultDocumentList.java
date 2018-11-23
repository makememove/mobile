package makememove.ml.makememove.dpsystem.documents;

import java.util.List;

import makememove.ml.makememove.dpsystem.documents.subdocuments.ResultDocument;

public class ResultDocumentList extends Document{
    List<ResultDocument> rankings;

    public ResultDocumentList(List<ResultDocument> rankings) {
        this.rankings = rankings;
    }

    public ResultDocumentList() {
    }

    @Override
    public void setData(Document document) {
        rankings=((ResultDocumentList) document).getRankings();
    }

    public List<ResultDocument> getRankings() {
        return rankings;
    }

    public void setRankings(List<ResultDocument> rankings) {
        this.rankings = rankings;
    }
}
