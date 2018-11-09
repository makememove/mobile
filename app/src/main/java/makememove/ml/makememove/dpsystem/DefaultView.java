package makememove.ml.makememove.dpsystem;

public interface DefaultView extends BaseView {
    public void sendNotification();
    public BaseView getResult();
    public void service();
    public void update();
}
