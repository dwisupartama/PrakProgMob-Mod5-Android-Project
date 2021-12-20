package id.ppmkelompok10.pendudukku.ModulKTP;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import id.ppmkelompok10.pendudukku.R;

public class PendudukTambahKTPActivity extends AppCompatActivity {
    private Spinner spJenisPengajuanKTP;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penduduk_tambah_ktp_activity);

        //Merubah Status Bar Menjadi Putih / Mode Light
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //Deklarasi Spinner
        spJenisPengajuanKTP = findViewById(R.id.sp_jenis_pengajuan_ktp);

        //Deklatasi Tombol Kembali
        btnBack = findViewById(R.id.btn_back);

        //Konfigurasi Spinner Status Akses
        ArrayAdapter<String> adapterJenisPengajuanKTP = new ArrayAdapter<>(
                this,
                R.layout.custom_spinner,
                getResources().getStringArray(R.array.list_jenis_pengajuan_ktp)
        );
        adapterJenisPengajuanKTP.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spJenisPengajuanKTP.setAdapter(adapterJenisPengajuanKTP);

        //Tombol Kembali
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}