package com.br.albumapi.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.br.albumapi.R;
import com.br.albumapi.activity.AlbumFotosActivity;
import com.br.albumapi.modelo.Album;

import java.util.List;

public class AdaptadorAlbum extends RecyclerView.Adapter<AdaptadorAlbum.AlbumViewHolder> {

    private List<Album> albumList;
    private Context context;

    public AdaptadorAlbum(List<Album> albums) {
        albumList = albums;
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_fotos, parent, false);
        context = parent.getContext();
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder albumViewHolder, int i) {

        final Album album = albumList.get(i);
        albumViewHolder.txtId.setText(Integer.toString(album.getId()));
        albumViewHolder.txtTitulo.setText(album.getTitle());
        albumViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AlbumFotosActivity.class);
                intent.putExtra("albumid", album.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    class AlbumViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtTitulo;
        public TextView txtId;

        public AlbumViewHolder(View v) {
            super(v);
            txtId = v.findViewById(R.id.txt_id);
            txtTitulo = v.findViewById(R.id.txt_titulo);

        }
    }


}
