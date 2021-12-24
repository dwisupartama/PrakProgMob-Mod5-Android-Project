package id.ppmkelompok10.pendudukku.ModulSurat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import id.ppmkelompok10.pendudukku.API.APISurat.APIPendudukSurat;
import id.ppmkelompok10.pendudukku.API.APIVaksin.APIPendudukVaksin;
import id.ppmkelompok10.pendudukku.API.RetroServer;
import id.ppmkelompok10.pendudukku.Helper.LoadingDialog;
import id.ppmkelompok10.pendudukku.Model.ModelSurat.ModelSurat;
import id.ppmkelompok10.pendudukku.Model.ModelSurat.ResponseMultiDataModelSurat;
import id.ppmkelompok10.pendudukku.Model.ModelSurat.ResponseSingleDataModelSurat;
import id.ppmkelompok10.pendudukku.Model.ModelVaksin.ModelVaksin;
import id.ppmkelompok10.pendudukku.Model.ModelVaksin.ResponseSingleModelDataVaksin;
import id.ppmkelompok10.pendudukku.ModulVaksin.DetailVaksinActivity;
import id.ppmkelompok10.pendudukku.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailSuratActivity extends AppCompatActivity {
    private TextView tvJenisSurat, tvTanggalPengajuan, tvLabelTanggalPerkiraanSelesai, tvTanggalPerkiraanSelesai, tvStatus, tvKeterangan, tvDeskripsiPengajuan, tvLabelDownload;
    private LinearLayout lnBgStatus;
    private Button btnDownload;
    private ImageButton btnBack;
    String idSurat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_surat);

        //Merubah Status Bar Menjadi Putih / Mode Light
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        Intent intent = getIntent();
        idSurat = String.valueOf(intent.getIntExtra("id_surat",0));

        tvJenisSurat = findViewById(R.id.tv_jenis_surat);
        tvTanggalPengajuan = findViewById(R.id.tv_tanggal_pengajuan);
        tvLabelTanggalPerkiraanSelesai = findViewById(R.id.tv_label_tanggal_perkiraan_selesai);
        tvTanggalPerkiraanSelesai = findViewById(R.id.tv_tanggal_perkiraan_selesai);
        tvStatus = findViewById(R.id.tv_status);
        tvKeterangan = findViewById(R.id.tv_keterangan);
        tvDeskripsiPengajuan = findViewById(R.id.tv_deskripsi_pengajuan);
        tvLabelDownload = findViewById(R.id.tv_label_download);

        lnBgStatus = findViewById(R.id.ln_bg_status);

        //Deklarasi Tombol Kembali
        btnBack = findViewById(R.id.btn_back);
        btnDownload = findViewById(R.id.btn_download);

        tampilDataDetail();

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

        APIPendudukSurat apiPendudukSurat = RetroServer.konekRetrofit().create(APIPendudukSurat.class);
        Call<ResponseSingleDataModelSurat> apiDetailDataSurat = apiPendudukSurat.apiDetailDataSurat(idSurat);

        apiDetailDataSurat.enqueue(new Callback<ResponseSingleDataModelSurat>() {
            @Override
            public void onResponse(Call<ResponseSingleDataModelSurat> call, Response<ResponseSingleDataModelSurat> response) {
                int code = response.body().getCode();
                String message = response.body().getMessage();
                ModelSurat data = response.body().getData();

                if (code == 1){
                    tvJenisSurat.setText(data.getJenis_surat());
                    tvTanggalPengajuan.setText(data.getTanggal_pengajuan());

                    if(data.getStatus_pengajuan().equals("Menunggu Konfirmasi")){
                        tvLabelTanggalPerkiraanSelesai.setVisibility(View.GONE);
                        tvTanggalPerkiraanSelesai.setVisibility(View.GONE);
                        lnBgStatus.setBackground(ContextCompat.getDrawable(DetailSuratActivity.this,R.drawable.bg_status_blue));
                        tvStatus.setTextColor(ContextCompat.getColor(DetailSuratActivity.this, R.color.BlueColorPrimary));
                        tvLabelDownload.setVisibility(View.GONE);
                        btnDownload.setVisibility(View.GONE);
                    }else if(data.getStatus_pengajuan().equals("Sedang di Proses")){
                        tvLabelTanggalPerkiraanSelesai.setVisibility(View.VISIBLE);
                        tvLabelTanggalPerkiraanSelesai.setText("Perkiraan Selesai");
                        tvTanggalPerkiraanSelesai.setVisibility(View.VISIBLE);
                        tvTanggalPerkiraanSelesai.setText(data.getPerkiraan_selesai());
                        lnBgStatus.setBackground(ContextCompat.getDrawable(DetailSuratActivity.this,R.drawable.bg_status_purple));
                        tvStatus.setTextColor(ContextCompat.getColor(DetailSuratActivity.this, R.color.PrimaryColorVariant));
                        tvLabelDownload.setVisibility(View.GONE);
                        btnDownload.setVisibility(View.GONE);
                    }else if(data.getStatus_pengajuan().equals("Selesai di Proses")){
                        tvLabelTanggalPerkiraanSelesai.setVisibility(View.VISIBLE);
                        tvLabelTanggalPerkiraanSelesai.setText("Tanggal Selesai di Proses");
                        tvTanggalPerkiraanSelesai.setVisibility(View.VISIBLE);
                        tvTanggalPerkiraanSelesai.setText(data.getTanggal_selesai());
                        lnBgStatus.setBackground(ContextCompat.getDrawable(DetailSuratActivity.this,R.drawable.bg_status_green));
                        tvStatus.setTextColor(ContextCompat.getColor(DetailSuratActivity.this, R.color.GreenColorPrimary));
                        tvLabelDownload.setVisibility(View.VISIBLE);
                        btnDownload.setVisibility(View.VISIBLE);
                    }else if(data.getStatus_pengajuan().equals("Pengajuan Gagal")){
                        tvLabelTanggalPerkiraanSelesai.setVisibility(View.GONE);
                        tvTanggalPerkiraanSelesai.setVisibility(View.GONE);
                        lnBgStatus.setBackground(ContextCompat.getDrawable(DetailSuratActivity.this,R.drawable.bg_status_red));
                        tvStatus.setTextColor(ContextCompat.getColor(DetailSuratActivity.this, R.color.RedColorPrimary));
                        tvLabelDownload.setVisibility(View.GONE);
                        btnDownload.setVisibility(View.GONE);
                    }
                    lnBgStatus.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                    tvStatus.setText(data.getStatus_pengajuan());
                    if(data.getKeterangan() != null){
                        tvKeterangan.setText(data.getKeterangan());
                    }else{
                        tvKeterangan.setText("-");
                    }

                    tvDeskripsiPengajuan.setText(data.getDeskripsi_pengajuan());
                    loading2.dismissLoading();
                }
            }

            @Override
            public void onFailure(Call<ResponseSingleDataModelSurat> call, Throwable t) {
                Toast.makeText(DetailSuratActivity.this, "Error Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                loading2.dismissLoading();
            }
        });

    }
}