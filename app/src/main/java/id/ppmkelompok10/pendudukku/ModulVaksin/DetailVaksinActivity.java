package id.ppmkelompok10.pendudukku.ModulVaksin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import id.ppmkelompok10.pendudukku.API.APIVaksin.APIVaksin;
import id.ppmkelompok10.pendudukku.API.RetroServer;
import id.ppmkelompok10.pendudukku.Model.ModelVaksin.ModelVaksin;
import id.ppmkelompok10.pendudukku.Model.ModelVaksin.ResponseModelDetailVaksin;
import id.ppmkelompok10.pendudukku.Model.ModelVaksin.ResponseModelVaksin;
import id.ppmkelompok10.pendudukku.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailVaksinActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private TextView tvTahapVaksin, tvTanggalPengajuan, tvTanggalPerkiraanSelesai, tvStatusPengajuan, tvKeterangan, tvTanggalWaktu, tvTempatVaksin, tvJenisVaksin, tvLokasiDiajukan, tvRiwayatPenyakit, tvTglSelesai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_vaksin);

        Intent intent = getIntent();
        int idVaksin = intent.getIntExtra("id_vaksin",0);

        //Deklarasi Text View
        tvTahapVaksin = findViewById(R.id.tv_tahap_vaksin);
        tvTanggalPengajuan = findViewById(R.id.tv_tanggal_pengajuan);
        tvTanggalPerkiraanSelesai = findViewById(R.id.tv_tanggal_perkiraan_selesai);
        tvStatusPengajuan = findViewById(R.id.tv_status_pengajuan);
        tvKeterangan = findViewById(R.id.tv_keterangan);
        tvTanggalWaktu = findViewById(R.id.tv_tanggal_waktu_vaksin);
        tvTempatVaksin = findViewById(R.id.tv_tempat_vaksin);
        tvJenisVaksin = findViewById(R.id.tv_jenis_vaksin);
        tvLokasiDiajukan = findViewById(R.id.tv_lokasi_diajukan);
        tvRiwayatPenyakit = findViewById(R.id.tv_riwayat_penyakit);
        tvTglSelesai = findViewById(R.id.tv_tgl_selesai);


        //Merubah Status Bar Menjadi Putih / Mode Light
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        APIVaksin apiVaksin = RetroServer.konekRetrofit().create(APIVaksin.class);
        Call<ResponseModelDetailVaksin> apiDetailVaksin = apiVaksin.apiDetailVaksin(""+idVaksin);

        apiDetailVaksin.enqueue(new Callback<ResponseModelDetailVaksin>() {
            @Override
            public void onResponse(Call<ResponseModelDetailVaksin> call, Response<ResponseModelDetailVaksin> response) {
                int code = response.body().getCode();
                String message = response.body().getMessage();
                ModelVaksin data = response.body().getData();
                if (code == 1){

                }
            }

            @Override
            public void onFailure(Call<ResponseModelDetailVaksin> call, Throwable t) {

            }
        });

        //Deklarasi Tombol Kembali
        btnBack = findViewById(R.id.btn_back);

        //Tombol Kembali
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }
}