package makememove.ml.makememove.dpsystem.presenters;

import android.util.Log;

import java.io.IOException;

import makememove.ml.makememove.dpsystem.documents.AuthInputDocument;
import makememove.ml.makememove.dpsystem.documents.Document;
import makememove.ml.makememove.dpsystem.documents.EventDocument;
import makememove.ml.makememove.dpsystem.documents.subdocuments.FinishedRank;
import makememove.ml.makememove.dpsystem.documents.subdocuments.Team;
import makememove.ml.makememove.user.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostPresenter extends Presenter implements Callback {

    public PostPresenter(){
        super();
    }
    public PostPresenter(Document doc){
        this.document = doc;
    }

    public void setDocument(Document doc){
        this.document = doc;
    }

    public void postPreferredSport(String token, int pos){
        Call call = api.postPreferredUserSport(token,pos);
        call.enqueue(this);
    }

    public void unpostPreferredSport(String token, int pos){
        Call call = api.unpostPreferredUserSport(token,pos);
        call.enqueue(this);
    }
    public void postEvent(String token, EventDocument doc){
        Call call = api.createEvent(token,doc);
        Log.d("visibility",Integer.toString(doc.getPublished()));
        call.enqueue(this);
    }
    public void denyFriendRequest(String token, int position){
        Call call = api.denyFriendRequest(token,position);
        call.enqueue(this);
    }
    public void acceptFriendRequest(String token, int position){
        Call call = api.acceptFriendRequest(token,position);
        call.enqueue(this);
    }
    public void deleteFriend(String token, int position){
        Call call = api.deleteFriend(token, position);
        call.enqueue(this);
    }

    public void deleteNotification(String token,int notid){
        Call call = api.deleteNotification(token,notid);
        call.enqueue(this);
    }

    public void createTeam(String token, Team team){
        Call call = api.createTeam(token,team);
        call.enqueue(this);
    }

    public void sendFriendRequest(String token, int userId){
        Call call = api.sendFriendRequest(token,userId);
        call.enqueue(this);
    }

    public void joinTeam(String token, int teamId){
        Call call = api.joinTeam(token,teamId);
        call.enqueue(this);
    }

    public void leaveTeam(String token, int teamId){
        Call call = api.leaveTeam(token,teamId);
        call.enqueue(this);
    }

    public void modifyProfile(String token, String gender, String birthday){
        User user = User.getInstance();
        Call call = api.modifyProfile(token,user.getFirstName(),user.getLastName(),gender,birthday);
        call.enqueue(this);
    }

    public void modifyEvent(String token, int eventId, EventDocument doc){
        Call call = api.modifyEvent(token,eventId, doc);
        call.enqueue(this);
    }

    public void closeEvent(String token, int eventId, FinishedRank ranks){
        Call call = api.closeEvent(token,eventId,ranks);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call call, Response response) {
        if(response.isSuccessful()){
            if(document!=null) {
                document.setData((AuthInputDocument) response.body());
                Log.d("refresh inc","refresh inc");
            }
            Log.d("Successful","Successful");
        }
        else {
            try {
                Log.d("Not Successful", "Not Successful" + response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        System.out.printf("Failure occured in PostPresenter class!");
    }

}
