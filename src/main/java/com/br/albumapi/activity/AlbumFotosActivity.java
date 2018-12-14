package com.br.albumapi.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.br.albumapi.adaptadores.AdaptadorAlbumFotos;
import com.br.albumapi.R;
import com.br.albumapi.api.AlbumFotoApi;
import com.br.albumapi.api.RetrofitInicializador;
import com.br.albumapi.modelo.AlbumFoto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AlbumFotosActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private AdaptadorAlbumFotos adaptadorAlbum;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albuns_fotos);

        progressBar = findViewById(R.id.progresso);
        mRecyclerView = findViewById(R.id.listview);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        new AlbumAsync().execute(getIntent().getIntExtra("albumid", 0));
    }

    class AlbumAsync extends AsyncTask<Integer, Void, List<AlbumFoto>> {

        @Override
        protected List<AlbumFoto> doInBackground(Integer... album) {

            List<AlbumFoto> albumList = new ArrayList<>();
            if (album.length == 0)
                return albumList;

            try {
                AlbumFotoApi albumApi = RetrofitInicializador.getClient().create(AlbumFotoApi.class);
                albumList = albumApi.get(album[0]).execute().body();


            } catch (IOException e) {
                e.printStackTrace();
            }
            return albumList;
        }

        @Override
        protected void onPostExecute(List<AlbumFoto> albums) {
            adaptadorAlbum = new AdaptadorAlbumFotos(albums);
            mRecyclerView.setAdapter(adaptadorAlbum);
            progressBar.setVisibility(View.GONE);
            super.onPostExecute(albums);
        }
    }
}
