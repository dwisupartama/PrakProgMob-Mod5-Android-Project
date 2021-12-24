package id.ppmkelompok10.pendudukku.ModulKTP;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import id.ppmkelompok10.pendudukku.API.APIKTP.APIPengajuanKTP;
import id.ppmkelompok10.pendudukku.API.RetroServer;
import id.ppmkelompok10.pendudukku.Adapter.AdapterPendudukDaftarKTP;
import id.ppmkelompok10.pendudukku.Helper.LoadingDialog;
import id.ppmkelompok10.pendudukku.Helper.SessionManagement;
import id.ppmkelompok10.pendudukku.Model.ModelKTP.PengajuanKTP;
import id.ppmkelompok10.pendudukku.Model.ModelKTP.ResponseMultiDataModelKTP;
import id.ppmkelompok10.pendudukku.Model.ModelPenduduk.ModelDataPenduduk;
import id.ppmkelompok10.pendudukku.ModulPenduduk.PegawaiDaftarPendudukActivity;
import id.ppmkelompok10.pendudukku.ModulSurat.PendudukDaftarSuratActivity;
import id.ppmkelompok10.pendudukku.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendudukDaftarKTPActivity extends AppCompatActivity{
    private ImageButton btnBack;
    private RecyclerView rvDaftarKTP;
    private Button btnTambah;
    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penduduk_daftar_ktp_activity);

        //Merubah Status Bar Menjadi Putih / Mode Light
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        session = new SessionManagement(this);

        //Deklarasi Tombol Kembali
        btnBack = findViewById(R.id.btn_back);

        //Deklatasi Tombol Tambah Pengajuan
        btnTambah = findViewById(R.id.btn_tambah);

        //Deklarasi Recycler View
        rvDaftarKTP = findViewById(R.id.rv_daftar_ktp);

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
                Intent pendudukTambahKTPActivity = new Intent(PendudukDaftarKTPActivity.this, PendudukTambahKTPActivity.class);
                startActivity(pendudukTambahKTPActivity);
            }
        });
    }

    public void tampilDaftar(){
        LoadingDialog loading2 = new LoadingDialog(this);
        loading2.startLoadingDialog();
        String nik = session.getNIK();

        APIPengajuanKTP apiPengajuanKTP = RetroServer.konekRetrofit().create(APIPengajuanKTP.class);
        Call<ResponseMultiDataModelKTP> apiGetPengajuanFor = apiPengajuanKTP.apiGetPengajuanFor(nik);

        apiGetPengajuanFor.enqueue(new Callback<ResponseMultiDataModelKTP>() {
            @Override
            public void onResponse(Call<ResponseMultiDataModelKTP> call, Response<ResponseMultiDataModelKTP> response) {
                int code = response.body().getCode();
                String message = response.body().getMessage();
                ArrayList<PengajuanKTP> data = response.body().getData();

                if(code == 1){
                    loading2.dismissLoading();
                    AdapterPendudukDaftarKTP adapterPendudukDaftarKTP = new AdapterPendudukDaftarKTP(PendudukDaftarKTPActivity.this, data);
                    rvDaftarKTP.setAdapter(adapterPendudukDaftarKTP);
                }

            }

            @Override
            public void onFailure(Call<ResponseMultiDataModelKTP> call, Throwable t) {
                Toast.makeText(PendudukDaftarKTPActivity.this, "Error Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                loading2.dismissLoading();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        tampilDaftar();
    }

}