package id.ppmkelompok10.pendudukku.ModulKTP;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import id.ppmkelompok10.pendudukku.Adapter.AdapterPegawaiDaftarKTP;
import id.ppmkelompok10.pendudukku.Adapter.AdapterPendudukDaftarKTP;
import id.ppmkelompok10.pendudukku.R;

public class PendudukDaftarKTPActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private RecyclerView rvDaftarKTP;
    private Button btnTambah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penduduk_daftar_ktp_activity);

        //Merubah Status Bar Menjadi Putih / Mode Light
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

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
        AdapterPendudukDaftarKTP adapterPendudukDaftarKTP = new AdapterPendudukDaftarKTP(PendudukDaftarKTPActivity.this);
        rvDaftarKTP.setAdapter(adapterPendudukDaftarKTP);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tampilDaftar();
    }
}