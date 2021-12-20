package id.ppmkelompok10.pendudukku.ModulVaksin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import id.ppmkelompok10.pendudukku.Adapter.AdapterPendudukDaftarKTP;
import id.ppmkelompok10.pendudukku.Adapter.AdapterPendudukDaftarVaksin;
import id.ppmkelompok10.pendudukku.ModulKTP.PendudukDaftarKTPActivity;
import id.ppmkelompok10.pendudukku.ModulKTP.PendudukTambahKTPActivity;
import id.ppmkelompok10.pendudukku.R;

public class PendudukDaftarVaksinActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private RecyclerView rvDaftarVaksin;
    private Button btnTambah;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penduduk_daftar_vaksin);

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
        AdapterPendudukDaftarVaksin adapterPendudukDaftarVaksin = new AdapterPendudukDaftarVaksin(PendudukDaftarVaksinActivity.this);
        rvDaftarVaksin.setAdapter(adapterPendudukDaftarVaksin);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tampilDaftar();
    }
}