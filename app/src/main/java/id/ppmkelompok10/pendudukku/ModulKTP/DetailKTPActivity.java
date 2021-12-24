package id.ppmkelompok10.pendudukku.ModulKTP;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import id.ppmkelompok10.pendudukku.API.APIKTP.APIPengajuanKTP;
import id.ppmkelompok10.pendudukku.API.RetroServer;
import id.ppmkelompok10.pendudukku.Helper.LoadingDialog;
import id.ppmkelompok10.pendudukku.Model.ModelKTP.PengajuanKTP;
import id.ppmkelompok10.pendudukku.Model.ModelKTP.ResponseMultiDataModelKTP;
import id.ppmkelompok10.pendudukku.Model.ModelKTP.ResponseSingleDataModelKTP;
import id.ppmkelompok10.pendudukku.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailKTPActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private TextView tv_nik,tv_nama,tv_tmptLahir,tv_tgl_lahir,tv_JenisKelamin,tv_GolDar,tv_Alamat,tv_Agama,tv_perkawinan,tv_pekerjaan;
    private TextView tv_jenis_pembuatan,tv_tgl_pengajuan,tv_tgl_perkiraan,tv_status,tv_keterangan, tv_label_perkiraan_selesai;
    private LinearLayout tv_background;
    String idPengajuanKTP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ktp_activity);

        //Merubah Status Bar Menjadi Putih / Mode Light
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //Get Data ID Pengajuan
        Intent detailPengajuanKTP = getIntent();
        idPengajuanKTP = String.valueOf(detailPengajuanKTP.getIntExtra("id_pengajuan",0));

        //deklarasi textview data
        tv_jenis_pembuatan = findViewById(R.id.tv_jenis_pembuatan);
        tv_tgl_pengajuan = findViewById(R.id.tv_tanggal_pengajuan);
        tv_label_perkiraan_selesai = findViewById(R.id.tv_label_perkiraan_selesai);
        tv_tgl_perkiraan = findViewById(R.id.tv_tanggal_perkiraan_selesai);
        tv_background = findViewById(R.id.ln_bg_status);
        tv_status = findViewById(R.id.tv_status);
        tv_keterangan = findViewById(R.id.tv_keterangan);
        tv_nik = findViewById(R.id.tv_nik);
        tv_nama = findViewById(R.id.tv_nama);
        tv_tmptLahir = findViewById(R.id.tv_tempat_lahir);
        tv_tgl_lahir = findViewById(R.id.tv_tgl_lahir);
        tv_JenisKelamin = findViewById(R.id.tv_jenis_kelamin);
        tv_GolDar = findViewById(R.id.tv_golongan_darah);
        tv_Alamat = findViewById(R.id.tv_alamat);
        tv_Agama = findViewById(R.id.tv_agama);
        tv_perkawinan = findViewById(R.id.tv_perkawinan);
        tv_pekerjaan = findViewById(R.id.tv_pekerjaan);

        //Deklarasi Tombol Kembali
        btnBack = findViewById(R.id.btn_back);

        tampilDetail();

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
        tampilDetail();
    }

    public void tampilDetail(){
        LoadingDialog loading2 = new LoadingDialog(this);
        loading2.startLoadingDialog();

        APIPengajuanKTP apiPengajuanKTP = RetroServer.konekRetrofit().create(APIPengajuanKTP.class);
        Call<ResponseSingleDataModelKTP> apiDetailPengajuan = apiPengajuanKTP.apiDetailPengajuan(idPengajuanKTP);

        apiDetailPengajuan.enqueue(new Callback<ResponseSingleDataModelKTP>() {
            @Override
            public void onResponse(Call<ResponseSingleDataModelKTP> call, Response<ResponseSingleDataModelKTP> response) {
                int code = response.body().getCode();
                String message = response.body().getMessage();
                PengajuanKTP data = response.body().getData();

                tv_nik.setText(data.getNik());
                tv_jenis_pembuatan.setText(data.getJenis_pengajuan());
                tv_tgl_pengajuan.setText(data.getTanggal_pengajuan());

                if(data.getStatus_pengajuan().equals("Menunggu Konfirmasi")){
                    tv_label_perkiraan_selesai.setVisibility(View.GONE);
                    tv_tgl_perkiraan.setVisibility(View.GONE);
                    tv_background.setBackground(ContextCompat.getDrawable(DetailKTPActivity.this,R.drawable.bg_status_blue));
                    tv_status.setTextColor(ContextCompat.getColor(DetailKTPActivity.this, R.color.BlueColorPrimary));
                }else if(data.getStatus_pengajuan().equals("Sedang di Proses")){
                    tv_label_perkiraan_selesai.setVisibility(View.VISIBLE);
                    tv_label_perkiraan_selesai.setText("Perkiraan Selesai");
                    tv_tgl_perkiraan.setVisibility(View.VISIBLE);
                    tv_tgl_perkiraan.setText(data.getPerkiraan_selesai());
                    tv_background.setBackground(ContextCompat.getDrawable(DetailKTPActivity.this,R.drawable.bg_status_purple));
                    tv_status.setTextColor(ContextCompat.getColor(DetailKTPActivity.this, R.color.PrimaryColorVariant));
                }else if(data.getStatus_pengajuan().equals("Selesai di Proses")){
                    tv_label_perkiraan_selesai.setVisibility(View.VISIBLE);
                    tv_label_perkiraan_selesai.setText("Tanggal Selesai di Proses");
                    tv_tgl_perkiraan.setVisibility(View.VISIBLE);
                    tv_tgl_perkiraan.setText(data.getTanggal_selesai());
                    tv_background.setBackground(ContextCompat.getDrawable(DetailKTPActivity.this,R.drawable.bg_status_green));
                    tv_status.setTextColor(ContextCompat.getColor(DetailKTPActivity.this, R.color.GreenColorPrimary));
                }else if(data.getStatus_pengajuan().equals("Pengajuan Gagal")){
                    tv_label_perkiraan_selesai.setVisibility(View.GONE);
                    tv_tgl_perkiraan.setVisibility(View.GONE);
                    tv_background.setBackground(ContextCompat.getDrawable(DetailKTPActivity.this,R.drawable.bg_status_red));
                    tv_status.setTextColor(ContextCompat.getColor(DetailKTPActivity.this, R.color.RedColorPrimary));
                }
                tv_background.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                tv_status.setText(data.getStatus_pengajuan());
                if(data.getKeterangan() != null){
                    tv_keterangan.setText(data.getKeterangan());
                }else{
                    tv_keterangan.setText("-");
                }
                tv_nama.setText(data.getNama_lengkap());
                tv_tmptLahir.setText(data.getTempat_lahir());
                tv_JenisKelamin.setText(data.getJenis_kelamin());
                tv_GolDar.setText(data.getGolongan_darah());
                tv_Alamat.setText(data.getAlamat());
                tv_Agama.setText(data.getAgama());
                tv_perkawinan.setText(data.getStatus_perkawinan());
                tv_pekerjaan.setText(data.getPekerjaan());
                loading2.dismissLoading();
            }

            @Override
            public void onFailure(Call<ResponseSingleDataModelKTP> call, Throwable t) {
                Toast.makeText(DetailKTPActivity.this, "Error Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                loading2.dismissLoading();
            }
        });
    }

}