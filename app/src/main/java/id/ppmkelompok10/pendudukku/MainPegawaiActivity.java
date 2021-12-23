package id.ppmkelompok10.pendudukku;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import id.ppmkelompok10.pendudukku.API.APIAuth.APIPengaturanProfil;
import id.ppmkelompok10.pendudukku.API.RetroServer;
import id.ppmkelompok10.pendudukku.Helper.LoadingDialog;
import id.ppmkelompok10.pendudukku.Helper.SessionManagement;
import id.ppmkelompok10.pendudukku.Model.ModelAuth.AccountModelAuth;
import id.ppmkelompok10.pendudukku.Model.ModelAuth.ResponseModelAuth;
import id.ppmkelompok10.pendudukku.ModulAuth.LoginPegawaiActivity;
import id.ppmkelompok10.pendudukku.ModulAuth.LoginPendudukActivity;
import id.ppmkelompok10.pendudukku.ModulAuth.PengaturanProfilActivity;
import id.ppmkelompok10.pendudukku.ModulKTP.PegawaiDaftarKTPActivity;
import id.ppmkelompok10.pendudukku.ModulPenduduk.PegawaiDaftarPendudukActivity;
import id.ppmkelompok10.pendudukku.ModulSurat.PegawaiDaftarSuratActivity;
import id.ppmkelompok10.pendudukku.ModulSurat.PendudukDaftarSuratActivity;
import id.ppmkelompok10.pendudukku.ModulVaksin.PegawaiDaftarVaksinActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPegawaiActivity extends AppCompatActivity {
    private ImageButton imbPengaturanProfil, imbLogout;
    private TextView tvNamaPengguna;
    private CardView cvMenuPendudukku, cvMenuKTPku, cvMenuVaksinku, cvMenuSuratku;
    SessionManagement session;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pegawai);

        session = new SessionManagement(this);

        //Merubah Status Bar Menjadi Putih / Mode Light
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //Deklarasi Tombol menu
        cvMenuPendudukku = findViewById(R.id.cv_menu_pendudukku);
        cvMenuKTPku = findViewById(R.id.cv_menu_ktpku);
        cvMenuVaksinku = findViewById(R.id.cv_menu_vaksinku);
        cvMenuSuratku = findViewById(R.id.cv_menu_suratku);

        //Deklarasi TextView
        tvNamaPengguna = findViewById(R.id.tv_nama_pengguna);

        showDataAkun(MainPegawaiActivity.this);

        //Button Pengaturan Profil
        imbPengaturanProfil = findViewById(R.id.img_pengaturan_profil);
        imbPengaturanProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pengaturanProfilActivity = new Intent(MainPegawaiActivity.this, PengaturanProfilActivity.class);
                startActivity(pengaturanProfilActivity);
            }
        });

        imbLogout = findViewById(R.id.img_logout);
        imbLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogConfirm(MainPegawaiActivity.this);
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

    public void alertDialogConfirm(Context context){
        AlertDialog.Builder builderDialog;
        AlertDialog alertDialog;

        builderDialog = new AlertDialog.Builder(context);
        View layoutView = LayoutInflater.from(context).inflate(R.layout.dialog_confirm_danger, null);

        Button buttonKeluar = layoutView.findViewById(R.id.btn_primary);
        Button buttonKembali = layoutView.findViewById(R.id.btn_secondary);

        TextView title = layoutView.findViewById(R.id.title);
        TextView subtitle = layoutView.findViewById(R.id.subtitle);

        title.setText("Keluar");
        subtitle.setText("Apakah anda yakin ingin keluar?");
        buttonKeluar.setText("Ya, Keluar");
        buttonKembali.setText("Kembali");

        builderDialog.setView(layoutView);
        alertDialog = builderDialog.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationZoom;
        alertDialog.show();

        buttonKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.removeSession();
                alertDialog.dismiss();
                Intent loginPegawaiActivity = new Intent(context, LoginPegawaiActivity.class);
                startActivity(loginPegawaiActivity);
                finish();
            }
        });

        buttonKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showDataAkun(MainPegawaiActivity.this);
    }

    public void showDataAkun(Context context){
        LoadingDialog loading = new LoadingDialog(this);
        loading.startLoadingDialog();
        //Ambil API
        String nik = session.getNIK();
        APIPengaturanProfil apiPengaturanProfil = RetroServer.konekRetrofit().create(APIPengaturanProfil.class);
        Call<ResponseModelAuth> apiAmbilDataProfil = apiPengaturanProfil.apiAmbilDataProfil(nik);

        apiAmbilDataProfil.enqueue(new Callback<ResponseModelAuth>() {
            @Override
            public void onResponse(Call<ResponseModelAuth> call, Response<ResponseModelAuth> response) {
                int code = response.body().getCode();
                String message = response.body().getMessage();
                AccountModelAuth dataAkun = response.body().getData();

                loading.dismissLoading();


                String namaLengkap = dataAkun.getNama_lengkap();
                if(namaLengkap.length() > 11){
                    namaLengkap = namaLengkap.substring(0,11)+"...";
                }
                tvNamaPengguna.setText(namaLengkap);
            }

            @Override
            public void onFailure(Call<ResponseModelAuth> call, Throwable t) {
                loading.dismissLoading();
                Toast.makeText(context, "Error Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}