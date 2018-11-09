package makememove.ml.makememove.dpsystem.documents;

import android.arch.persistence.room.Ignore;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

import makememove.ml.makememove.dpsystem.BaseView;
import makememove.ml.makememove.dpsystem.presenters.Presenter;

public abstract class Document {
    transient protected ArrayList<BaseView> views;
    transient protected Presenter presenter;

  public Presenter getPresenter(){
    return presenter;
  }
  public void setPresenter(Presenter presenter){this.presenter = presenter;}

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

    public void sendNotification(){
        for (BaseView view : views) {
            view.update();
        }
    }

    public  void getData(){}

    public void setData(Document document){

    }

    public  void service(){}
}
