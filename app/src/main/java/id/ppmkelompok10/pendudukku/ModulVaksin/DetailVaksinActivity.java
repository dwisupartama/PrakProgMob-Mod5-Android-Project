package id.ppmkelompok10.pendudukku.ModulVaksin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import id.ppmkelompok10.pendudukku.API.APIVaksin.APIPegawaiVaksin;
import id.ppmkelompok10.pendudukku.API.APIVaksin.APIPendudukVaksin;
import id.ppmkelompok10.pendudukku.API.RetroServer;
import id.ppmkelompok10.pendudukku.Helper.LoadingDialog;
import id.ppmkelompok10.pendudukku.Model.ModelVaksin.ModelVaksin;
import id.ppmkelompok10.pendudukku.Model.ModelVaksin.ResponseMultiDataModelVaksin;
import id.ppmkelompok10.pendudukku.Model.ModelVaksin.ResponseSingleModelDataVaksin;
import id.ppmkelompok10.pendudukku.ModulKTP.DetailKTPActivity;
import id.ppmkelompok10.pendudukku.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailVaksinActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private TextView tvTahapVaksin, tvTanggalPengajuan, tvTanggalPerkiraanSelesai, tvStatusPengajuan, tvKeterangan, tvTanggalWaktu, tvTempatVaksin, tvJenisVaksin, tvLokasiDiajukan, tvRiwayatPenyakit, tvLabelPerkiraanSelesai;
    private LinearLayout lnBackgroundStatus;
    String idVaksin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_vaksin);

        //Merubah Status Bar Menjadi Putih / Mode Light
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        Intent intent = getIntent();
        idVaksin = String.valueOf(intent.getIntExtra("id_vaksin",0));

        //Deklarasi Text View
        tvTahapVaksin = findViewById(R.id.tv_tahap_vaksin);
        tvTanggalPengajuan = findViewById(R.id.tv_tanggal_pengajuan);
        tvLabelPerkiraanSelesai = findViewById(R.id.tv_label_perkiraan_selesai);
        tvTanggalPerkiraanSelesai = findViewById(R.id.tv_tanggal_perkiraan_selesai);
        lnBackgroundStatus = findViewById(R.id.ln_bg_status);
        tvStatusPengajuan = findViewById(R.id.tv_status);
        tvKeterangan = findViewById(R.id.tv_keterangan);
        tvTanggalWaktu = findViewById(R.id.tv_tanggal_waktu_vaksin);
        tvTempatVaksin = findViewById(R.id.tv_tempat_vaksin);
        tvJenisVaksin = findViewById(R.id.tv_jenis_vaksin);
        tvLokasiDiajukan = findViewById(R.id.tv_lokasi_diajukan);
        tvRiwayatPenyakit = findViewById(R.id.tv_riwayat_penyakit);

        tampilDataDetail();

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

    @Override
    protected void onResume() {
        super.onResume();
        tampilDataDetail();
    }

    public void tampilDataDetail(){
        LoadingDialog loading2 = new LoadingDialog(this);
        loading2.startLoadingDialog();

        APIPendudukVaksin apiPegawaiVaksin = RetroServer.konekRetrofit().create(APIPendudukVaksin.class);
        Call<ResponseSingleModelDataVaksin> apiDetailVaksin = apiPegawaiVaksin.apiDetailDataVaksin(idVaksin);

        apiDetailVaksin.enqueue(new Callback<ResponseSingleModelDataVaksin>() {
            @Override
            public void onResponse(Call<ResponseSingleModelDataVaksin> call, Response<ResponseSingleModelDataVaksin> response) {
                int code = response.body().getCode();
                String message = response.body().getMessage();
                ModelVaksin data = response.body().getData();

                if (code == 1){
                    tvTahapVaksin.setText(data.getTahap_vaksin());
                    tvTanggalPengajuan.setText(data.getTanggal_pengajuan());

                    if(data.getStatus_pengajuan().equals("Menunggu Konfirmasi")){
                        tvLabelPerkiraanSelesai.setVisibility(View.GONE);
                        tvTanggalPerkiraanSelesai.setVisibility(View.GONE);
                        lnBackgroundStatus.setBackground(ContextCompat.getDrawable(DetailVaksinActivity.this,R.drawable.bg_status_blue));
                        tvStatusPengajuan.setTextColor(ContextCompat.getColor(DetailVaksinActivity.this, R.color.BlueColorPrimary));
                    }else if(data.getStatus_pengajuan().equals("Sedang di Proses")){
                        tvLabelPerkiraanSelesai.setVisibility(View.VISIBLE);
                        tvLabelPerkiraanSelesai.setText("Perkiraan Selesai");
                        tvTanggalPerkiraanSelesai.setVisibility(View.VISIBLE);
                        tvTanggalPerkiraanSelesai.setText(data.getPerkiraan_selesai());
                        lnBackgroundStatus.setBackground(ContextCompat.getDrawable(DetailVaksinActivity.this,R.drawable.bg_status_purple));
                        tvStatusPengajuan.setTextColor(ContextCompat.getColor(DetailVaksinActivity.this, R.color.PrimaryColorVariant));
                    }else if(data.getStatus_pengajuan().equals("Selesai di Proses")){
                        tvLabelPerkiraanSelesai.setVisibility(View.VISIBLE);
                        tvLabelPerkiraanSelesai.setText("Tanggal Selesai di Proses");
                        tvTanggalPerkiraanSelesai.setVisibility(View.VISIBLE);
                        tvTanggalPerkiraanSelesai.setText(data.getTanggal_selesai());
                        lnBackgroundStatus.setBackground(ContextCompat.getDrawable(DetailVaksinActivity.this,R.drawable.bg_status_green));
                        tvStatusPengajuan.setTextColor(ContextCompat.getColor(DetailVaksinActivity.this, R.color.GreenColorPrimary));
                    }else if(data.getStatus_pengajuan().equals("Pengajuan Gagal")){
                        tvLabelPerkiraanSelesai.setVisibility(View.GONE);
                        tvTanggalPerkiraanSelesai.setVisibility(View.GONE);
                        lnBackgroundStatus.setBackground(ContextCompat.getDrawable(DetailVaksinActivity.this,R.drawable.bg_status_red));
                        tvStatusPengajuan.setTextColor(ContextCompat.getColor(DetailVaksinActivity.this, R.color.RedColorPrimary));
                    }
                    lnBackgroundStatus.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                    tvStatusPengajuan.setText(data.getStatus_pengajuan());
                    if(data.getKeterangan() != null){
                        tvKeterangan.setText(data.getKeterangan());
                    }else{
                        tvKeterangan.setText("-");
                    }

                    if((data.getTanggal_vaksin() != null) || (data.getWaktu_vaksin() != null)){
                        tvTanggalWaktu.setText(data.getTanggal_vaksin()+" "+data.getWaktu_vaksin());
                    }else{
                        tvTanggalWaktu.setText("-");
                    }

                    if(data.getTempat_vaksin() != null){
                        tvTempatVaksin.setText(data.getTempat_vaksin());
                    }else{
                        tvTempatVaksin.setText("-");
                    }

                    if(data.getJenis_vaksin() != null){
                        tvJenisVaksin.setText(data.getJenis_vaksin());
                    }else{
                        tvJenisVaksin.setText("-");
                    }

                    if(data.getRiwayat_penyakit() != null){
                        tvRiwayatPenyakit.setText(data.getRiwayat_penyakit());
                    }else{
                        tvRiwayatPenyakit.setText("-");
                    }

                    tvLokasiDiajukan.setText(data.getDaerah_vaksin_diajukan());
                }
                loading2.dismissLoading();
            }

            @Override
            public void onFailure(Call<ResponseSingleModelDataVaksin> call, Throwable t) {
                Toast.makeText(DetailVaksinActivity.this, "Error Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                loading2.dismissLoading();
            }
        });
    }
}