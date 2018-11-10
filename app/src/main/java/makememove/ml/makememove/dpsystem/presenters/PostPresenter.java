package makememove.ml.makememove.dpsystem.presenters;

import android.util.Log;

import makememove.ml.makememove.dpsystem.documents.Document;
import makememove.ml.makememove.dpsystem.documents.SportListDocument;
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
        Log.d("A token: ","A token: "+pos+"\n");
        Call call = api.unpostPreferredUserSport(token,pos);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call call, Response response) {
        if(response.isSuccessful()){
            Log.d("Successful","Successful");
        }
        else
            Log.d("Not Successful","Not Successful");
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        System.out.printf("Failure occured in PostPresenter class!");
    }

}
