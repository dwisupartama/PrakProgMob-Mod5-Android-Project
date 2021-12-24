package id.ppmkelompok10.pendudukku.ModulSurat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import id.ppmkelompok10.pendudukku.API.APISurat.APIPendudukSurat;
import id.ppmkelompok10.pendudukku.API.APIVaksin.APIPendudukVaksin;
import id.ppmkelompok10.pendudukku.API.RetroServer;
import id.ppmkelompok10.pendudukku.Adapter.AdapterPendudukDaftarSurat;
import id.ppmkelompok10.pendudukku.Adapter.AdapterPendudukDaftarVaksin;
import id.ppmkelompok10.pendudukku.Helper.LoadingDialog;
import id.ppmkelompok10.pendudukku.Helper.SessionManagement;
import id.ppmkelompok10.pendudukku.Model.ModelKTP.ResponseMultiDataModelKTP;
import id.ppmkelompok10.pendudukku.Model.ModelSurat.ModelSurat;
import id.ppmkelompok10.pendudukku.Model.ModelSurat.ResponseMultiDataModelSurat;
import id.ppmkelompok10.pendudukku.Model.ModelVaksin.ModelVaksin;
import id.ppmkelompok10.pendudukku.Model.ModelVaksin.ResponseMultiDataModelVaksin;
import id.ppmkelompok10.pendudukku.ModulVaksin.PendudukDaftarVaksinActivity;
import id.ppmkelompok10.pendudukku.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendudukDaftarSuratActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private RecyclerView rvDaftarSurat;
    private Button btnTambah;
    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penduduk_daftar_surat);

        //Merubah Status Bar Menjadi Putih / Mode Light
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        session = new SessionManagement(this);

        //Deklarasi Tombol Kembali
        btnBack = findViewById(R.id.btn_back);

        //Deklarasi Recycler View
        rvDaftarSurat = findViewById(R.id.rv_daftar_surat);

        //Deklarasi Tombol Tambah
        btnTambah = findViewById(R.id.btn_tambah);

        //Pemanggilan Data Daftar
        tampilDaftar();

        //Tombol Kembali
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pendudukTambahSuratActivity = new Intent(PendudukDaftarSuratActivity.this, PendudukTambahSuratActivity.class);
                startActivity(pendudukTambahSuratActivity);
            }
        });
    }



    public void tampilDaftar(){
        LoadingDialog loading2 = new LoadingDialog(this);
        loading2.startLoadingDialog();

        APIPendudukSurat apiPendudukSurat = RetroServer.konekRetrofit().create(APIPendudukSurat.class);
        Call<ResponseMultiDataModelSurat> apiDaftarSurat = apiPendudukSurat.apiDaftarSurat(session.getNIK());

        apiDaftarSurat.enqueue(new Callback<ResponseMultiDataModelSurat>() {
            @Override
            public void onResponse(Call<ResponseMultiDataModelSurat> call, Response<ResponseMultiDataModelSurat> response) {
                int code = response.body().getCode();
                String message = response.body().getMessage();
                ArrayList<ModelSurat> data = response.body().getData();

                if (code == 1){
                    AdapterPendudukDaftarSurat adapterPendudukDaftarSurat = new AdapterPendudukDaftarSurat(PendudukDaftarSuratActivity.this, data);
                    rvDaftarSurat.setAdapter(adapterPendudukDaftarSurat);
                }
                loading2.dismissLoading();
            }

            @Override
            public void onFailure(Call<ResponseMultiDataModelSurat> call, Throwable t) {
                Toast.makeText(PendudukDaftarSuratActivity.this, "Error Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                loading2.dismissLoading();
            }
        });
//        apiDaftarVaksin.enqueue(new Callback<ResponseMultiDataModelVaksin>() {
//            @Override
//            public void onResponse(Call<ResponseMultiDataModelVaksin> call, Response<ResponseMultiDataModelVaksin> response) {
//                int code = response.body().getCode();
//                String message = response.body().getMessage();
//                ArrayList<ModelVaksin> data = response.body().getData();
//
//                if (code == 1){
//                    AdapterPendudukDaftarVaksin adapterPendudukDaftarVaksin = new AdapterPendudukDaftarVaksin(PendudukDaftarVaksinActivity.this, data);
//                    rvDaftarVaksin.setAdapter(adapterPendudukDaftarVaksin);
//                }
//                loading2.dismissLoading();
//            }
//
//            @Override
//            public void onFailure(Call<ResponseMultiDataModelVaksin> call, Throwable t) {
//                Toast.makeText(PendudukDaftarVaksinActivity.this, "Error Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
//                loading2.dismissLoading();
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        tampilDaftar();
    }
}