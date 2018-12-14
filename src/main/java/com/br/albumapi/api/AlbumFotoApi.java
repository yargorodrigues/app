package com.br.albumapi.api;

import com.br.albumapi.modelo.AlbumFoto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AlbumFotoApi {

    @GET("photos")
    Call<List<AlbumFoto>> get(@Query("albumId") int idAlbum);
}
