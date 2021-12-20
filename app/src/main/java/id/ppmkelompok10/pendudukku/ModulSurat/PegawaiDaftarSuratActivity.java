package id.ppmkelompok10.pendudukku.ModulSurat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import id.ppmkelompok10.pendudukku.Adapter.AdapterPegawaiDaftarSurat;
import id.ppmkelompok10.pendudukku.Adapter.AdapterPegawaiDaftarVaksin;
import id.ppmkelompok10.pendudukku.KeyboardUtils;
import id.ppmkelompok10.pendudukku.ModulVaksin.PegawaiDaftarVaksinActivity;
import id.ppmkelompok10.pendudukku.R;

public class PegawaiDaftarSuratActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private RecyclerView rvDaftarSurat;
    private EditText etSearch;
    private TextView tvTitle, tvSubtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pegawai_daftar_surat);

        //Merubah Status Bar Menjadi Putih / Mode Light
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //Deklarasi Tombol Kembali
        btnBack = findViewById(R.id.btn_back);

        //Deklarasi Input Search
        etSearch = findViewById(R.id.et_search);

        //Deklarasi Title dan Subtitle
        tvTitle = findViewById(R.id.tv_title);
        tvSubtitle = findViewById(R.id.tv_subtitle);

        //Deklarasi Recycler View
        rvDaftarSurat = findViewById(R.id.rv_daftar_surat);

        //Pemanggilan Data Daftar
        tampilDaftar();

        //Tombol Kembali
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        KeyboardUtils.addKeyboardToggleListener(this, new KeyboardUtils.SoftKeyboardToggleListener() {
            @Override
            public void onToggleSoftKeyboard(boolean isVisible) {
                if(isVisible){
                    tvTitle.setVisibility(View.GONE);
                    tvSubtitle.setVisibility(View.GONE);
                }else{
                    tvTitle.setVisibility(View.VISIBLE);
                    tvSubtitle.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void tampilDaftar(){
        AdapterPegawaiDaftarSurat adapterPegawaiDaftarSurat = new AdapterPegawaiDaftarSurat(PegawaiDaftarSuratActivity.this);
        rvDaftarSurat.setAdapter(adapterPegawaiDaftarSurat);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tampilDaftar();
    }
}