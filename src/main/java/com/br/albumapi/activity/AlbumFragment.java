package com.br.albumapi.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.br.albumapi.R;
import com.br.albumapi.api.AlbumApi;
import com.br.albumapi.api.RetrofitInicializador;
import com.br.albumapi.modelo.Album;

import java.io.IOException;

public class AlbumFragment extends DialogFragment {

    public OnSaveAlbumListener mListener;
    private EditText edtUsuario;
    private EditText edtTitulo;
    private Button btnSalvar;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_albuns, container, false);
        edtUsuario = view.findViewById(R.id.edt_usuario);
        edtTitulo = view.findViewById(R.id.edt_titulo);
        btnSalvar = view.findViewById(R.id.btn_salvar);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = ProgressDialog.show(getActivity(), "Aguarde", "Salvando album...");
                new AlbumPostAsync().execute();
            }
        });
        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnSaveAlbumListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString());
        }
    }

    public interface OnSaveAlbumListener {
        void onAlbumSavo(Album album);

        void onAlbumErro(String erro);
    }

    class AlbumPostAsync extends AsyncTask<Void, Void, Album> {

        @Override
        protected Album doInBackground(Void... voids) {

            Album album = new Album();
            Album albumResult = new Album();
            try {

                AlbumApi albumApi = RetrofitInicializador.getClient().create(AlbumApi.class);

                album.setTitle(edtTitulo.getText().toString());
                album.setUserId(Integer.parseInt(edtUsuario.getText().toString()));

                albumResult = albumApi.post(album).execute().body();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return albumResult;
        }

        @Override
        protected void onPostExecute(Album albums) {
            progressDialog.dismiss();
            if (albums != null) {
                mListener.onAlbumSavo(albums);
                dismiss();
            } else
                mListener.onAlbumErro("Houve um erro ao salvar o álbum");

            super.onPostExecute(albums);
        }
    }
}
