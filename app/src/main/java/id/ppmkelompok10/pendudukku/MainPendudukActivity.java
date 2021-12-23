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

import java.util.ArrayList;

import id.ppmkelompok10.pendudukku.API.APIAuth.APIPengaturanProfil;
import id.ppmkelompok10.pendudukku.API.APIKTP.APIGetAllPengajuan;
import id.ppmkelompok10.pendudukku.API.RetroServer;
import id.ppmkelompok10.pendudukku.Helper.LoadingDialog;
import id.ppmkelompok10.pendudukku.Helper.SessionManagement;
import id.ppmkelompok10.pendudukku.Model.ModelAuth.AccountModelAuth;
import id.ppmkelompok10.pendudukku.Model.ModelAuth.ResponseModelAuth;
import id.ppmkelompok10.pendudukku.Model.ModelKTP.PengajuanKTP;
import id.ppmkelompok10.pendudukku.Model.ModelKTP.PengajuanKTP_Data;
import id.ppmkelompok10.pendudukku.Model.ModelKTP.ResponseModelKTP;
import id.ppmkelompok10.pendudukku.ModulAuth.LoginPendudukActivity;
import id.ppmkelompok10.pendudukku.ModulAuth.PengaturanProfilActivity;
import id.ppmkelompok10.pendudukku.ModulKTP.PegawaiDaftarKTPActivity;
import id.ppmkelompok10.pendudukku.ModulKTP.PendudukDaftarKTPActivity;
import id.ppmkelompok10.pendudukku.ModulSurat.PendudukDaftarSuratActivity;
import id.ppmkelompok10.pendudukku.ModulVaksin.PendudukDaftarVaksinActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPendudukActivity extends AppCompatActivity {
    private ImageButton imbPengaturanProfil, imbLogout;
    private TextView tvNamaPengguna;
    private CardView cvMenuKTPku, cvMenuVaksinku, cvMenuSuratku;
    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_penduduk);
        session = new SessionManagement(this);

        //Merubah Status Bar Menjadi Putih / Mode Light
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //Deklarasi Tombol menu
        cvMenuKTPku = findViewById(R.id.cv_menu_ktpku);
        cvMenuVaksinku = findViewById(R.id.cv_menu_vaksinku);
        cvMenuSuratku = findViewById(R.id.cv_menu_suratku);

        //Deklarasi TextView
        tvNamaPengguna = findViewById(R.id.tv_nama_pengguna);

        showDataAkun(MainPendudukActivity.this);
        //ambil data api
        ambilDataAPI();
        //Button Pengaturan Profil
        imbPengaturanProfil = findViewById(R.id.img_pengaturan_profil);
        imbPengaturanProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pengaturanProfilActivity = new Intent(MainPendudukActivity.this, PengaturanProfilActivity.class);
                startActivity(pengaturanProfilActivity);
            }
        });

        imbLogout = findViewById(R.id.img_logout);
        imbLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogConfirm(MainPendudukActivity.this);
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

        cvMenuSuratku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pendudukDaftarSuratActivity = new Intent(MainPendudukActivity.this, PendudukDaftarSuratActivity.class);
                startActivity(pendudukDaftarSuratActivity);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showDataAkun(MainPendudukActivity.this);
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
                Intent loginPendudukActivity = new Intent(context, LoginPendudukActivity.class);
                startActivity(loginPendudukActivity);
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

                tvNamaPengguna.setText(dataAkun.getNama_lengkap());
                loading.dismissLoading();
            }

            @Override
            public void onFailure(Call<ResponseModelAuth> call, Throwable t) {
                Toast.makeText(context, "Error Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                loading.dismissLoading();
            }
        });
    }

    protected void ambilDataAPI(){
        LoadingDialog loading2 = new LoadingDialog(this);
        loading2.startLoadingDialog();
        //Ambil API
        ArrayList<PengajuanKTP> pengajuanKTPS = new ArrayList<>();
        session = new SessionManagement(this);
        String nik = session.getNIK();
        APIGetAllPengajuan apiGetAllPengajuan = RetroServer.konekRetrofit().create(APIGetAllPengajuan.class);
        Call<ResponseModelKTP> getpengajuan = apiGetAllPengajuan.apiAmbilPengajuan(nik);

        getpengajuan.enqueue(new Callback<ResponseModelKTP>() {
            @Override
            public void onResponse(Call<ResponseModelKTP> call, Response<ResponseModelKTP> response) {
                int code = response.body().getCode();
                String message = response.body().getMessage();
                ArrayList<PengajuanKTP> dataPengajuan = response.body().getData();
                for (PengajuanKTP item:dataPengajuan) {
                    pengajuanKTPS.add(item);
                }
                MainPendudukActivity.this.setPengajuanView(pengajuanKTPS);
                loading2.dismissLoading();
            }

            @Override
            public void onFailure(Call<ResponseModelKTP> call, Throwable t) {
                Toast.makeText(MainPendudukActivity.this, "Error Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                loading2.dismissLoading();
            }
        });
    }

    private void setPengajuanView(ArrayList<PengajuanKTP> pengajuanKTPS) {
        PengajuanKTP_Data.getInstance().setPengajuanData(pengajuanKTPS);
    }

}