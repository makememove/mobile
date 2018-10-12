package makememove.ml.makememove.retrofitexample;

public class Event {
    private String id;
    private String title;

    public Event(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }


}

        /*
        Retrofit retrofit = new Retrofit.Builder().baseUrl(API.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        API api = retrofit.create(API.class);

        Call<List<Event>> call = api.getEvent();

        call.enqueue(new Callback<List<Event>>() {

            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                List<Event> events = response.body();
                String list="";
                for(Event e: events){
                    Log.d("id",e.getId());
                    Log.d("title",e.getTitle());
                    list = list + e.getId() +" - " + e.getTitle() + "\n";
                }
                tv1.setText(list);
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });*/
