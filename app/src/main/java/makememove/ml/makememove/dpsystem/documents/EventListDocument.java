package makememove.ml.makememove.dpsystem.documents;

import java.util.ArrayList;
import java.util.List;

import makememove.ml.makememove.user.Sport;

public class EventListDocument extends Document {
    private List<EventDocument> events;

    public EventListDocument(){
        super();
        this.events = new ArrayList<EventDocument>();
    }
    public EventListDocument(List<EventDocument> events) {
        super();
        this.events = events;
    }

    @Override
    public void setData(Document document){
        events = ((EventListDocument) document).getEvents();
        sendNotification();
    }

    public List<EventDocument> getEvents() {
        return events;
    }

    public void setEvents(List<EventDocument> events) {
        this.events = events;
    }
}
