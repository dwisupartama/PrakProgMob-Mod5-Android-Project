package id.ppmkelompok10.pendudukku.ModulKTP;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import id.ppmkelompok10.pendudukku.Adapter.AdapterPegawaiDaftarKTP;
import id.ppmkelompok10.pendudukku.Helper.KeyboardUtils;
import id.ppmkelompok10.pendudukku.R;

public class PegawaiDaftarKTPActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private RecyclerView rvDaftarKTP;
    private EditText etSearch;
    private TextView tvTitle, tvSubtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pegawai_daftar_ktp_activity);

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
        AdapterPegawaiDaftarKTP adapterPegawaiDaftarKTP = new AdapterPegawaiDaftarKTP(PegawaiDaftarKTPActivity.this);
        rvDaftarKTP.setAdapter(adapterPegawaiDaftarKTP);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tampilDaftar();
    }
}