package makememove.ml.makememove.eventsystem;

import java.util.ArrayList;

public abstract class Document {
    private ArrayList<BaseView> views;

    protected ArrayList<BaseView> getViews() {
        if(views==null)
            views = new ArrayList<BaseView>();
        return views;
    }

    public void attach(BaseView view){
        if(view==null)
            views = new ArrayList<BaseView>();
        views.add(view);
    }

    public void detach(BaseView view){
        if(view==null)
            return;
        views.remove(view);
    }

    public abstract void sendNotification();

    public abstract void getData();

    public abstract void service();
}
