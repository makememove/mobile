package makememove.ml.makememove.dpsystem.documents;

import java.util.List;

import makememove.ml.makememove.dpsystem.presenters.Presenter;
import makememove.ml.makememove.user.Sport;

public class SportListDocument extends Document {
    private List<Sport> sports;

    public SportListDocument(List<Sport> sports) {
        this.sports = sports;
    }

    public List<Sport> getSports() {
        return sports;
    }

    public void setSports(List<Sport> sports) {
        this.sports = sports;
    }



}
