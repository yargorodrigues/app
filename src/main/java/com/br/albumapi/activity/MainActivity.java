package com.br.albumapi.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.br.albumapi.R;
import com.br.albumapi.adaptadores.AdaptadorAlbum;
import com.br.albumapi.api.AlbumApi;
import com.br.albumapi.api.RetrofitInicializador;
import com.br.albumapi.modelo.Album;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AlbumFragment.OnSaveAlbumListener {

    private RecyclerView mRecyclerView;
    private AdaptadorAlbum adaptadorAlbum;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar progressBar;
    private List<Album> albumList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progresso);
        mRecyclerView = findViewById(R.id.listview);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        albumList = new ArrayList<>();
        new AlbumAsync().execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_album, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_add) {
            AlbumFragment albumFragment = new AlbumFragment();
            albumFragment.mListener = this;
            albumFragment.show(getSupportFragmentManager().beginTransaction(), "addfrag");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAlbumSavo(Album album) {
        Toast.makeText(this, "Álbum salvo com sucesso!", Toast.LENGTH_LONG).show();
        albumList.add(album);
        adaptadorAlbum.notifyDataSetChanged();
    }

    @Override
    public void onAlbumErro(String erro) {

        new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage(erro)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    class AlbumAsync extends AsyncTask<Void, Void, List<Album>> {

        @Override
        protected List<Album> doInBackground(Void... voids) {

            List<Album> albumList = new ArrayList<>();
            try {

                AlbumApi albumApi = RetrofitInicializador.getClient().create(AlbumApi.class);
                albumList = albumApi.get().execute().body();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return albumList;
        }

        @Override
        protected void onPostExecute(List<Album> albums) {
            albumList = albums;
            adaptadorAlbum = new AdaptadorAlbum(albums);
            mRecyclerView.setAdapter(adaptadorAlbum);
            progressBar.setVisibility(View.GONE);
            super.onPostExecute(albums);
        }
    }


}
