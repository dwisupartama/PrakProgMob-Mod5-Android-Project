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

import id.ppmkelompok10.pendudukku.Model.ModelKTP.PengajuanKTP;
import id.ppmkelompok10.pendudukku.R;

public class DetailKTPActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private TextView tv_nik,tv_nama,tv_tmptLahir,tv_tgl_lahir,tv_JenisKelamin,tv_GolDar,tv_Alamat,tv_Agama,tv_perkawinan,tv_pekerjaan;
    private TextView tv_jenis_pembuatan,tv_tgl_pengajuan,tv_tgl_perkiraan,tv_status,tv_keterangan;
    private LinearLayout tv_background;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ktp_activity);

        //Merubah Status Bar Menjadi Putih / Mode Light
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //deklarasi textview data
        tv_jenis_pembuatan = findViewById(R.id.tv_jenis_pembuatan);
        tv_tgl_pengajuan = findViewById(R.id.tv_tanggal_pengajuan);
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

        // get data yang dikirim
        Intent getPengajuan = getIntent();
        PengajuanKTP pengajuan = getPengajuan.getParcelableExtra("pengajuan");
        Log.d("Penlah", "lihattgl: "+pengajuan.getTanggal_lahir());
        setItem(pengajuan);

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

    private void setItem(PengajuanKTP pengajuanKTP){
        tv_nik.setText(String.valueOf(pengajuanKTP.getNik()));
        if(pengajuanKTP.getJenis_pengajuan() != null){
            tv_jenis_pembuatan.setText(pengajuanKTP.getJenis_pengajuan());
        }
        if(pengajuanKTP.getTanggal_pengajuan() != null){
            tv_tgl_pengajuan.setText(pengajuanKTP.getTanggal_pengajuan().toString());
        }
        if(pengajuanKTP.getPerkiraan_selesai() != null){
            tv_tgl_perkiraan.setText(pengajuanKTP.getPerkiraan_selesai().toString());
        }else{
            tv_tgl_perkiraan.setText("-");
        }
        if(pengajuanKTP.getStatus_pengajuan() != null){
            tv_status.setText(pengajuanKTP.getStatus_pengajuan());
            setStatus(pengajuanKTP.getStatus_pengajuan());
        }
        if(pengajuanKTP.getKeterangan() != null){
            tv_keterangan.setText(pengajuanKTP.getKeterangan());
        }
        if(pengajuanKTP.getTanggal_lahir() != null){
            tv_tgl_lahir.setText(pengajuanKTP.getTanggal_lahir().toString());
        }
        tv_nama.setText(pengajuanKTP.getNama_lengkap());
        tv_tmptLahir.setText(pengajuanKTP.getTempat_lahir());
        tv_JenisKelamin.setText(pengajuanKTP.getJenis_kelamin());
        tv_GolDar.setText(pengajuanKTP.getGolongan_darah());
        tv_Alamat.setText(pengajuanKTP.getAlamat());
        tv_Agama.setText(pengajuanKTP.getAgama());
        tv_perkawinan.setText(pengajuanKTP.getStatus_perkawinan());
        tv_pekerjaan.setText(pengajuanKTP.getPekerjaan());
    }

    private void setStatus(String status){
        if(status.equals("Menunggu Konfirmasi")){
            tv_background.setBackground(ContextCompat.getDrawable(this,R.drawable.bg_status_blue));
            tv_status.setTextColor(ContextCompat.getColor(this, R.color.BlueColorPrimary));
        }else if(status.equals("Sedang di Proses")){
            tv_background.setBackground(ContextCompat.getDrawable(this,R.drawable.bg_status_purple));
            tv_status.setTextColor(ContextCompat.getColor(this, R.color.PrimaryColorVariant));
        }else if(status.equals("Selesai di Proses")){
            tv_background.setBackground(ContextCompat.getDrawable(this,R.drawable.bg_status_green));
            tv_status.setTextColor(ContextCompat.getColor(this, R.color.GreenColorPrimary));
        }else if(status.equals("Pengajuan Gagal")){
            tv_background.setBackground(ContextCompat.getDrawable(this,R.drawable.bg_status_red));
            tv_status.setTextColor(ContextCompat.getColor(this, R.color.RedColorPrimary));
        }
    }
}