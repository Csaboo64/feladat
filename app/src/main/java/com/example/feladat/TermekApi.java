package com.example.feladat;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface TermekApi {
    @GET("/termekek")
    Call<List<Termek>> getTermekek();

    @POST("/termekek")
    Call<Void> createTermek(@Body Termek termek);

    @DELETE("/termekek/{id}")
    Call<Void> deleteTermek(@Path("id") int id);
}

