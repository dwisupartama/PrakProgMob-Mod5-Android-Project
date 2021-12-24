package id.ppmkelompok10.pendudukku.ModulVaksin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import id.ppmkelompok10.pendudukku.API.APIVaksin.APIPegawaiVaksin;
import id.ppmkelompok10.pendudukku.API.APIVaksin.APIPendudukVaksin;
import id.ppmkelompok10.pendudukku.API.RetroServer;
import id.ppmkelompok10.pendudukku.Adapter.AdapterPendudukDaftarVaksin;
import id.ppmkelompok10.pendudukku.Helper.LoadingDialog;
import id.ppmkelompok10.pendudukku.Helper.SessionManagement;
import id.ppmkelompok10.pendudukku.Model.ModelVaksin.ModelVaksin;
import id.ppmkelompok10.pendudukku.Model.ModelVaksin.ResponseMultiDataModelVaksin;
import id.ppmkelompok10.pendudukku.Model.ModelVaksin.ResponseSingleModelDataVaksin;
import id.ppmkelompok10.pendudukku.ModulPenduduk.PegawaiDaftarPendudukActivity;
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
        LoadingDialog loading2 = new LoadingDialog(this);
        loading2.startLoadingDialog();

        APIPendudukVaksin apiPendudukVaksin = RetroServer.konekRetrofit().create(APIPendudukVaksin.class);
        Call<ResponseMultiDataModelVaksin> apiDaftarVaksin = apiPendudukVaksin.apiDaftarVaksin(session.getNIK());

        apiDaftarVaksin.enqueue(new Callback<ResponseMultiDataModelVaksin>() {
            @Override
            public void onResponse(Call<ResponseMultiDataModelVaksin> call, Response<ResponseMultiDataModelVaksin> response) {
                int code = response.body().getCode();
                String message = response.body().getMessage();
                ArrayList<ModelVaksin> data = response.body().getData();

                if (code == 1){
                    AdapterPendudukDaftarVaksin adapterPendudukDaftarVaksin = new AdapterPendudukDaftarVaksin(PendudukDaftarVaksinActivity.this, data);
                    rvDaftarVaksin.setAdapter(adapterPendudukDaftarVaksin);
                }
                loading2.dismissLoading();
            }

            @Override
            public void onFailure(Call<ResponseMultiDataModelVaksin> call, Throwable t) {
                Toast.makeText(PendudukDaftarVaksinActivity.this, "Error Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                loading2.dismissLoading();
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        tampilDaftar();
    }
}