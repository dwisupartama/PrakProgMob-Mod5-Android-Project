package id.ppmkelompok10.pendudukku.ModulVaksin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import id.ppmkelompok10.pendudukku.R;

public class PendudukTambahVaksinActivity extends AppCompatActivity {
    private Spinner spTahapVaksin, spDaerahVaksin;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penduduk_tambah_vaksin);

        //Merubah Status Bar Menjadi Putih / Mode Light
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //Deklarasi Spinner
        spTahapVaksin = findViewById(R.id.sp_tahap_vaksin);
        spDaerahVaksin = findViewById(R.id.sp_daerah_vaksin);

        //Deklatasi Tombol Kembali
        btnBack = findViewById(R.id.btn_back);

        //Konfigurasi Spinner Status Tahap Vaksin
        ArrayAdapter<String> adapterTahapVaksin = new ArrayAdapter<>(
                this,
                R.layout.custom_spinner,
                getResources().getStringArray(R.array.list_tahap_vaksin)
        );
        adapterTahapVaksin.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spTahapVaksin.setAdapter(adapterTahapVaksin);

        //Konfigurasi Spinner Status Daerah Vaksin
        ArrayAdapter<String> adapterDaerahVaksin = new ArrayAdapter<>(
                this,
                R.layout.custom_spinner,
                getResources().getStringArray(R.array.list_daerah_vaksin)
        );
        adapterDaerahVaksin.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spDaerahVaksin.setAdapter(adapterDaerahVaksin);

        //Tombol Kembali
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}