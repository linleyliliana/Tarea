package com.example.apirestful;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MainActivity2 extends AppCompatActivity {


    public TextView txtLista;

    public interface LugaresTuris {
        @GET("lugar_turistico/json_getlistadoGrid")
        Call<LugarResponse> getLugares();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        txtLista = findViewById(R.id.txtLista);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://uealecpeterson.net/turismo/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        LugaresTuris api = retrofit.create(LugaresTuris.class);

        Call<LugarResponse> call = api.getLugares();

        call.enqueue(new Callback<LugarResponse>() {
            @Override
            public void onResponse(Call<LugarResponse> call, Response<LugarResponse> response) {
                if (response.isSuccessful()) {
                    LugarResponse lugarResponse = response.body();
                    if (lugarResponse != null) {
                        mostrarNombLugares(lugarResponse.getData());
                    } else {
                        Toast.makeText(MainActivity2.this,
                                "Nulo", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity2.this,
                            "Sin éxito: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LugarResponse> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
                Toast.makeText(MainActivity2.this,
                        "Error de consultar API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarNombLugares(List<Turismo> lugares) {
        StringBuilder nombres = new StringBuilder();
        for (Turismo lugar : lugares) {
            nombres.append("Categoría: "+lugar.getCate()+
                    "  Nombre: "+ lugar.getNombrelug()+
                    "   Teléfono :"+ lugar.getTel()).append("\n\n");
        }
        txtLista.setText(nombres.toString());
    }

    public class LugarResponse {
        private List<Turismo> data;
        public List<Turismo> getData() {
            return data;
        }
        public void setData(List<Turismo> data) {
            this.data = data;
        }
    }

    public class Turismo {
        private String categoria;
        private String nombre_lugar;
        private String telefono;


        public String getCate() {
            return categoria;
        }
        public void setCate(String categoria) {
            this.categoria = categoria;
        }

        public String getNombrelug() {
            return nombre_lugar;
        }
        public void setNombrelug(String nombrelug) {
            this.nombre_lugar = nombrelug;
        }

        public String getTel() {
            return telefono;
        }
        public void setTel(String tel) {
            this.telefono = tel;
        }
    }
}