package id.ppmkelompok10.pendudukku.ModulVaksin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import id.ppmkelompok10.pendudukku.API.APIVaksin.APIVaksin;
import id.ppmkelompok10.pendudukku.API.RetroServer;
import id.ppmkelompok10.pendudukku.Adapter.AdapterPendudukDaftarKTP;
import id.ppmkelompok10.pendudukku.Adapter.AdapterPendudukDaftarVaksin;
import id.ppmkelompok10.pendudukku.Helper.SessionManagement;
import id.ppmkelompok10.pendudukku.Model.ModelVaksin.ModelVaksin;
import id.ppmkelompok10.pendudukku.Model.ModelVaksin.ResponseModelVaksin;
import id.ppmkelompok10.pendudukku.ModulKTP.PendudukDaftarKTPActivity;
import id.ppmkelompok10.pendudukku.ModulKTP.PendudukTambahKTPActivity;
import id.ppmkelompok10.pendudukku.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendudukDaftarVaksinActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private RecyclerView rvDaftarVaksin;
    private Button btnTambah;
    private SessionManagement session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penduduk_daftar_vaksin);
        
        session = new SessionManagement(this);

        //Merubah Status Bar Menjadi Putih / Mode Light
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //Deklarasi Tombol Kembali
        btnBack = findViewById(R.id.btn_back);

        //Deklatasi Tombol Tambah Pengajuan
        btnTambah = findViewById(R.id.btn_tambah);

        //Deklarasi Recycler View
        rvDaftarVaksin = findViewById(R.id.rv_daftar_vaksin);

        //Pemanggilan Data Daftar
        tampilDaftar();

        //Tombol Kembali
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Tombol Tambah Pengajuan
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pendudukTambahVaksinActivity = new Intent(PendudukDaftarVaksinActivity.this, PendudukTambahVaksinActivity.class);
                startActivity(pendudukTambahVaksinActivity);
            }
        });
    }

    public void tampilDaftar(){
        APIVaksin daftarVaksin = RetroServer.konekRetrofit().create(APIVaksin.class);
        Call<ResponseModelVaksin> apiDaftarVaksin = daftarVaksin.apiDaftarVaksin(session.getNIK()); 
        
        apiDaftarVaksin.enqueue(new Callback<ResponseModelVaksin>() {
            @Override
            public void onResponse(Call<ResponseModelVaksin> call, Response<ResponseModelVaksin> response) {
                int code = response.body().getCode();
                String message = response.body().getMessage();
                ArrayList<ModelVaksin> data = response.body().getData();

                if (code == 1){
                    AdapterPendudukDaftarVaksin adapterPendudukDaftarVaksin = new AdapterPendudukDaftarVaksin(PendudukDaftarVaksinActivity.this,data);
                    rvDaftarVaksin.setAdapter(adapterPendudukDaftarVaksin);
                }
            }

            @Override
            public void onFailure(Call<ResponseModelVaksin> call, Throwable t) {

            }
        });



    }

    @Override
    protected void onResume(){
        super.onResume();
        tampilDaftar();
    }
}