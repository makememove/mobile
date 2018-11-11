package makememove.ml.makememove.dpsystem.documents;

import java.util.ArrayList;
import java.util.List;

import makememove.ml.makememove.dpsystem.presenters.Presenter;
import makememove.ml.makememove.user.Sport;

public class SportListDocument extends Document {
    private List<Sport> sports;

    public SportListDocument(){
        super();
        this.sports = new ArrayList<Sport>();
    }
    public SportListDocument(List<Sport> sports) {
        super();
        this.sports = sports;
    }

    @Override
    public void setData(Document document){
        sports = ((SportListDocument) document).getSports();
        sendNotification();
    }

    public List<Sport> getSports() {
        return sports;
    }

    public void setSports(List<Sport> sports) {
        this.sports = sports;
    }
}
