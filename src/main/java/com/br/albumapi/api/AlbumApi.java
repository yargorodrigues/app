package com.br.albumapi.api;

import com.br.albumapi.modelo.Album;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AlbumApi {

    @GET("albums")
    Call<List<Album>> get();

    @POST("albums")
    Call<Album> post(@Body Album album);
}
