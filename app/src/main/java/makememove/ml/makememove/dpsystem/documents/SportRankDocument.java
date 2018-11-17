package makememove.ml.makememove.dpsystem.documents;

import java.util.List;

import makememove.ml.makememove.dpsystem.documents.subdocuments.UserRank;

public class SportRankDocument {
    private List<UserRank> ranklist;


    public List<UserRank> getRanklist() {
        return ranklist;
    }

    public void setRanklist(List<UserRank> ranklist) {
        this.ranklist = ranklist;
    }
}
