package id.ppmkelompok10.pendudukku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import id.ppmkelompok10.pendudukku.ModulAuth.PengaturanProfilActivity;
import id.ppmkelompok10.pendudukku.ModulKTP.PegawaiDaftarKTPActivity;
import id.ppmkelompok10.pendudukku.ModulKTP.PendudukDaftarKTPActivity;
import id.ppmkelompok10.pendudukku.ModulVaksin.PendudukDaftarVaksinActivity;

public class MainPendudukActivity extends AppCompatActivity {
    private ImageButton imbPengaturanProfil;
    private CardView cvMenuKTPku, cvMenuVaksinku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_penduduk);

        //Merubah Status Bar Menjadi Putih / Mode Light
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //Deklarasi Tombol menu
        cvMenuKTPku = findViewById(R.id.cv_menu_ktpku);

        //Deklarasi Tombol menu
        cvMenuVaksinku = findViewById(R.id.cv_menu_vaksinku);

        //Button Pengaturan Profil
        imbPengaturanProfil = findViewById(R.id.img_pengaturan_profil);
        imbPengaturanProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pengaturanProfilActivity = new Intent(MainPendudukActivity.this, PengaturanProfilActivity.class);
                startActivity(pengaturanProfilActivity);
            }
        });

        cvMenuKTPku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pendudukDaftarKTPActivity = new Intent(MainPendudukActivity.this, PendudukDaftarKTPActivity.class);
                startActivity(pendudukDaftarKTPActivity);
            }
        });

        cvMenuVaksinku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pendudukDaftarVaksinActivity = new Intent(MainPendudukActivity.this, PendudukDaftarVaksinActivity.class);
                startActivity(pendudukDaftarVaksinActivity);
            }
        });
    }
}