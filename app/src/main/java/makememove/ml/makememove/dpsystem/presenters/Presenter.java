package makememove.ml.makememove.dpsystem.presenters;

import makememove.ml.makememove.datahandler.Encryptor;
import makememove.ml.makememove.dpsystem.api.RetrofitAPI;
import makememove.ml.makememove.dpsystem.documents.Document;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class Presenter{

    protected Retrofit retrofit ;
    protected RetrofitAPI api;
    protected Encryptor encryptor;
    protected Document document;

    protected Presenter(){
        retrofit = new Retrofit.Builder().baseUrl(RetrofitAPI.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        api = retrofit.create(RetrofitAPI.class);
        encryptor = new Encryptor();
    }
    public void loadDocument(String command){

    }

    public void saveDocument(String command){

    }

}
