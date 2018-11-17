package makememove.ml.makememove.dpsystem.documents;

import java.util.ArrayList;
import java.util.List;

import makememove.ml.makememove.dpsystem.documents.subdocuments.Team;

public class TeamDocument extends Document {

    private InnerDocument event;
    @Override
    public void setData(Document document) {
        event = ((TeamDocument) document).getEvent();
        sendNotification();
    }

    public InnerDocument getEvent() {
        return event;
    }

    public void setEvent(InnerDocument event) {
        this.event = event;
    }



    public class InnerDocument extends Document {
        private List<Team> teams;

        public InnerDocument() {
            super();
            this.teams = new ArrayList<Team>();
        }

        @Override
        public void setData(Document document) {
            teams = ((InnerDocument) document).getTeams();
            sendNotification();
        }

        public List<Team> getTeams() {
            return teams;
        }

        public void setTeams(List<Team> teams) {
            this.teams = teams;
        }
    }
}
