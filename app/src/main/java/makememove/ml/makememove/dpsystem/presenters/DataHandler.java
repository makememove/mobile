package makememove.ml.makememove.dpsystem.presenters;

import makememove.ml.makememove.dpsystem.documents.UserDocument;
import makememove.ml.makememove.dpsystem.documents.AuthInputDocument;
import makememove.ml.makememove.dpsystem.documents.TokenDocument;
import makememove.ml.makememove.dpsystem.documents.SportListDocument;
import makememove.ml.makememove.user.User;
import retrofit2.Call;
import retrofit2.Callback;

public class DataHandler extends Presenter{
    private static DataHandler single_instance = null;

    private DataHandler()
    {
        super();
    }

    public static DataHandler getInstance()
    {
        if (single_instance == null)
            single_instance = new DataHandler();

        return single_instance;
    }


    //login presenter

    public void login(String email, String username, String password, retrofit2.Callback callback){
        Call<TokenDocument> call=api.login(new AuthInputDocument(username,email,encryptor.encode(password)));
        call.enqueue(callback);
    }

    public void signup(String email, String username, String password, retrofit2.Callback callback){
        Call<TokenDocument> call=api.signup(new AuthInputDocument(username,email,encryptor.encode(password)));
        call.enqueue(callback);
    }

    //lp end

    //datapresenter

    public void setUserData(retrofit2.Callback callback){
        Call<UserDocument> call = api.getUserDetails(User.getInstance().getToken());
        call.enqueue(callback);
    }

    //dp end
}
