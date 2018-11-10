package makememove.ml.makememove.dpsystem.presenters;

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

    @Override
    public void onResponse(Call call, Response response) {
        if(response.isSuccessful()){

        }
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        System.out.printf("Failure occured in PostPresenter class!");
    }

}
