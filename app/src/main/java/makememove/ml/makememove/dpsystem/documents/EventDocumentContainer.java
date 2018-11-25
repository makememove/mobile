package makememove.ml.makememove.dpsystem.documents;

public class EventDocumentContainer extends Document{

    private EventDocument event;

    @Override
    public void setData(Document document) {
        event = (( EventDocumentContainer) document).getEvent();
        sendNotification();
    }

    public EventDocumentContainer(EventDocument event) {
        super();
        this.event = event;
    }

    public EventDocumentContainer() {
        super();
    }


    public EventDocument getEvent() {
        return event;
    }

    public void setEvent(EventDocument event) {
        this.event = event;
    }
}