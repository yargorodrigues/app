package com.br.albumapi.adaptadores;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.br.albumapi.R;
import com.br.albumapi.modelo.AlbumFoto;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.List;

public class AdaptadorAlbumFotos extends RecyclerView.Adapter<AdaptadorAlbumFotos.AlbumViewHolder> {

    private List<AlbumFoto> albumList;

    public AdaptadorAlbumFotos(List<AlbumFoto> albums) {
        albumList = albums;
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.linha_album_fotos, parent, false);

        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder albumViewHolder, int i) {

        AlbumFoto album = albumList.get(i);
        albumViewHolder.txtId.setText(Integer.toString(album.getId()));
        albumViewHolder.txtTitulo.setText(album.getTitle());
        Picasso.get().load(album.getThumbnailUrl()).into(albumViewHolder.imgAlbum);

    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    class AlbumViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtTitulo;
        public TextView txtId;
        public ImageView imgAlbum;

        public AlbumViewHolder(View v) {
            super(v);
            txtId = v.findViewById(R.id.txt_id);
            txtTitulo = v.findViewById(R.id.txt_titulo);
            imgAlbum = v.findViewById(R.id.img_album);

        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
