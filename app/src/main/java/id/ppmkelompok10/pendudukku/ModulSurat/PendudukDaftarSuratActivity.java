package id.ppmkelompok10.pendudukku.ModulSurat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import id.ppmkelompok10.pendudukku.Adapter.AdapterPendudukDaftarSurat;
import id.ppmkelompok10.pendudukku.R;

public class PendudukDaftarSuratActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private RecyclerView rvDaftarSurat;
    private Button btnTambah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penduduk_daftar_surat);

        //Merubah Status Bar Menjadi Putih / Mode Light
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

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
        AdapterPendudukDaftarSurat adapterPendudukDaftarSurat = new AdapterPendudukDaftarSurat(PendudukDaftarSuratActivity.this);
        rvDaftarSurat.setAdapter(adapterPendudukDaftarSurat);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tampilDaftar();
    }
}