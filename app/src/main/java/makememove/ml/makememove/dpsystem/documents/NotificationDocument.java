package makememove.ml.makememove.dpsystem.documents;

import java.util.List;

import makememove.ml.makememove.dpsystem.documents.subdocuments.Notify;

public class NotificationDocument extends Document{
    List<Notify> notifications;

    public NotificationDocument() {
    }

    @Override
    public void setData(Document document) {
        notifications = ((NotificationDocument) document).getNotifications();
        sendNotification();
    }

    public NotificationDocument(List<Notify> notifications) {
        this.notifications = notifications;
    }

    public List<Notify> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notify> notifications) {
        this.notifications = notifications;
    }
}
