package com.example.geniuscredit1;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class GSTapi {
    public static final String url ="https://appyflow.in/api/";

    public static anuj anu= null;
    public static anuj getservice(){
        if (anu==null){
            Retrofit retro = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            anu = retro.create(anuj.class);


        }
        return anu;

    }

    public interface anuj{
        @GET("verifyGST")
        Call<GSTJson> getdata(@Query("gstNo") String gstnumber, @Query("key_secret") String webkey);


    }
}
