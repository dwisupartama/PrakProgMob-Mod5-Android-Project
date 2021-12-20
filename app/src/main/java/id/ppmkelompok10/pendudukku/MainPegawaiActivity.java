package id.ppmkelompok10.pendudukku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import id.ppmkelompok10.pendudukku.ModulAuth.PengaturanProfilActivity;
import id.ppmkelompok10.pendudukku.ModulKTP.PegawaiDaftarKTPActivity;
import id.ppmkelompok10.pendudukku.ModulPenduduk.PegawaiDaftarPendudukActivity;
import id.ppmkelompok10.pendudukku.ModulSurat.PegawaiDaftarSuratActivity;
import id.ppmkelompok10.pendudukku.ModulSurat.PendudukDaftarSuratActivity;
import id.ppmkelompok10.pendudukku.ModulVaksin.PegawaiDaftarVaksinActivity;

public class MainPegawaiActivity extends AppCompatActivity {
    private ImageButton imbPengaturanProfil;
    private CardView cvMenuPendudukku, cvMenuKTPku, cvMenuVaksinku, cvMenuSuratku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pegawai);

        //Merubah Status Bar Menjadi Putih / Mode Light
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //Deklarasi Tombol menu
        cvMenuPendudukku = findViewById(R.id.cv_menu_pendudukku);
        cvMenuKTPku = findViewById(R.id.cv_menu_ktpku);
        cvMenuVaksinku = findViewById(R.id.cv_menu_vaksinku);
        cvMenuSuratku = findViewById(R.id.cv_menu_suratku);

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

        cvMenuKTPku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pegawaiDaftarKTPActivity = new Intent(MainPegawaiActivity.this, PegawaiDaftarKTPActivity.class);
                startActivity(pegawaiDaftarKTPActivity);
            }
        });

        cvMenuVaksinku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pegawaiDaftarVaksinActivity = new Intent(MainPegawaiActivity.this, PegawaiDaftarVaksinActivity.class);
                startActivity(pegawaiDaftarVaksinActivity);
            }
        });

        cvMenuSuratku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pegawaiDaftarSuratActivity = new Intent(MainPegawaiActivity.this, PegawaiDaftarSuratActivity.class);
                startActivity(pegawaiDaftarSuratActivity);
            }
        });
    }
}