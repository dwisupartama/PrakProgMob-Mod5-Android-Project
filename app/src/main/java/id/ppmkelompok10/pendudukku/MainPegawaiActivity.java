package id.ppmkelompok10.pendudukku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import id.ppmkelompok10.pendudukku.ModulAuth.LoginPendudukActivity;
import id.ppmkelompok10.pendudukku.ModulAuth.PengaturanProfilActivity;
import id.ppmkelompok10.pendudukku.ModulPenduduk.PegawaiDaftarPendudukActivity;

public class MainPegawaiActivity extends AppCompatActivity {
    private ImageButton imbPengaturanProfil;
    private CardView cvMenuPendudukku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pegawai);

        //Merubah Status Bar Menjadi Putih / Mode Light
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //Deklarasi Tombol menu
        cvMenuPendudukku = findViewById(R.id.cv_menu_pendudukku);

        //Button Pengaturan Profil
        imbPengaturanProfil = findViewById(R.id.img_pengaturan_profil);
        imbPengaturanProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pengaturanProfilActivity = new Intent(MainPegawaiActivity.this, PengaturanProfilActivity.class);
                startActivity(pengaturanProfilActivity);
            }
        });

        cvMenuPendudukku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pegawaiDaftarPendudukActivity = new Intent(MainPegawaiActivity.this, PegawaiDaftarPendudukActivity.class);
                startActivity(pegawaiDaftarPendudukActivity);
            }
        });
    }
}