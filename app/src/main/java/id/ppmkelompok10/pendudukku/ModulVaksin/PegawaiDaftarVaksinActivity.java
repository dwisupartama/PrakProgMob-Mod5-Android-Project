package id.ppmkelompok10.pendudukku.ModulVaksin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import id.ppmkelompok10.pendudukku.Adapter.AdapterPegawaiDaftarVaksin;
import id.ppmkelompok10.pendudukku.Helper.KeyboardUtils;
import id.ppmkelompok10.pendudukku.R;

public class PegawaiDaftarVaksinActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private RecyclerView rvDaftarVaksin;
    private EditText etSearch;
    private TextView tvTitle, tvSubtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pegawai_daftar_vaksin);

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
        AdapterPegawaiDaftarVaksin adapterPegawaiDaftarVaksin = new AdapterPegawaiDaftarVaksin(PegawaiDaftarVaksinActivity.this);
        rvDaftarVaksin.setAdapter(adapterPegawaiDaftarVaksin);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tampilDaftar();
    }
}