package id.ppmkelompok10.pendudukku.ModulKTP;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import id.ppmkelompok10.pendudukku.API.APIAuth.APILogin;
import id.ppmkelompok10.pendudukku.API.APIAuth.APIPengaturanProfil;
import id.ppmkelompok10.pendudukku.API.APIKTP.APIPengajuan;
import id.ppmkelompok10.pendudukku.API.RetroServer;
import id.ppmkelompok10.pendudukku.GetStartedActivity;
import id.ppmkelompok10.pendudukku.Helper.LoadingDialog;
import id.ppmkelompok10.pendudukku.Helper.SessionManagement;
import id.ppmkelompok10.pendudukku.MainPendudukActivity;
import id.ppmkelompok10.pendudukku.Model.ModelAuth.AccountModelAuth;
import id.ppmkelompok10.pendudukku.Model.ModelAuth.ResponseModelAuth;
import id.ppmkelompok10.pendudukku.ModulAuth.LoginPendudukActivity;
import id.ppmkelompok10.pendudukku.ModulAuth.PengaturanProfilActivity;
import id.ppmkelompok10.pendudukku.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class PendudukTambahKTPActivity extends AppCompatActivity {
    private Spinner spJenisPengajuanKTP;
    private ImageButton btnBack;
    private Button btnAjukan;
    private TextView tv_nik,tv_nama,tv_tmptLahir,tv_tgl_lahir,tv_JenisKelamin,tv_GolDar,tv_Alamat,tv_Agama,tv_perkawinan,tv_pekerjaan;
    private CheckBox checkData;
    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penduduk_tambah_ktp_activity);

        //Merubah Status Bar Menjadi Putih / Mode Light
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //deklarasi textview data
        tv_nik = findViewById(R.id.tv_nik);
        tv_nama = findViewById(R.id.tv_nama);
        tv_tmptLahir = findViewById(R.id.tv_tempat_lahir);
        tv_tgl_lahir = findViewById(R.id.tv_tgl_lahir);
        tv_JenisKelamin = findViewById(R.id.tv_jenis_kelamin);
        tv_GolDar = findViewById(R.id.tv_golongan_darah);
        tv_Alamat = findViewById(R.id.tv_alamat);
        tv_Agama = findViewById(R.id.tv_agama);
        tv_perkawinan = findViewById(R.id.tv_perkawinan);
        tv_pekerjaan = findViewById(R.id.tv_pekerjaan);
        checkData = findViewById(R.id.cb_check_data);

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

        // Ambil data penduduk
        ambilDataAPI();

        //Tombol Kembali
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Deklatasi Tombol ajukan ktp
        btnAjukan = findViewById(R.id.btn_ajukan);
        btnAjukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkData.isChecked()){
                    BuatPengajuan();
//                    Toast.makeText(PendudukTambahKTPActivity.this, ""+spJenisPengajuanKTP.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(PendudukTambahKTPActivity.this, "Silahkan Centang Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void BuatPengajuan() {
        LoadingDialog loading = new LoadingDialog(this);
        loading.startLoadingDialog();
        //ambil Data
        String __jenis_Pengajuan = spJenisPengajuanKTP.getSelectedItem().toString();
        String __nik = tv_nik.getText().toString();
        String __nama = tv_nama.getText().toString();
        String __tmptLahir = tv_tmptLahir.getText().toString();
        String __tglLahir = tv_tgl_lahir.getText().toString();
        String __jK = tv_JenisKelamin.getText().toString();
        String __golDar = tv_GolDar.getText().toString();
        String __alamat = tv_Alamat.getText().toString();
        String __agama = tv_Agama.getText().toString();
        String __perkawinan = tv_perkawinan.getText().toString();
        String __pekerjaan = tv_pekerjaan.getText().toString();

        APIPengajuan apiPengajuan = RetroServer.konekRetrofit().create(APIPengajuan.class);
        Call<ResponseModelAuth> ajukan = apiPengajuan.AjukanKTP(__jenis_Pengajuan, __nik,__nama,__tmptLahir,__tglLahir,__jK,__golDar,__alamat,__agama,__perkawinan,__pekerjaan);
        ajukan.enqueue(new Callback<ResponseModelAuth>() {
            @Override
            public void onResponse(Call<ResponseModelAuth> call, Response<ResponseModelAuth> response) {
                Log.d("Penduduk", "onResponse: "+response);
                int code = response.body().getCode();
                String message = response.body().getMessage();

                if(code == 0){
                    loading.dismissLoading();
                    alertDialog(PendudukTambahKTPActivity.this, "Gagal", message);
                }
                else{
                    loading.dismissLoading();
                    alertDialog(PendudukTambahKTPActivity.this, "Berhasil", message);
                }
            }

            @Override
            public void onFailure(Call<ResponseModelAuth> call, Throwable t) {
                Toast.makeText(PendudukTambahKTPActivity.this, "Error Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                loading.dismissLoading();
            }
        });
    }

    public void alertDialog(Context context, String textTitle, String textMessage){
        //Btn Delete
        AlertDialog.Builder builderDialog;
        AlertDialog alertDialog;

        builderDialog = new AlertDialog.Builder(context);
        View layoutView = LayoutInflater.from(context).inflate(R.layout.dialog_success, null);

        Button buttonPrimary = layoutView.findViewById(R.id.btn_primary);

        TextView title = layoutView.findViewById(R.id.title);
        TextView subtitle = layoutView.findViewById(R.id.subtitle);

        title.setText(textTitle);
        subtitle.setText(textMessage);
        buttonPrimary.setText("Oke");

        builderDialog.setView(layoutView);
        alertDialog = builderDialog.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationZoom;
        alertDialog.show();

        buttonPrimary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    public void ambilDataAPI(){
        LoadingDialog loading2 = new LoadingDialog(this);
        loading2.startLoadingDialog();
        //Ambil API
        session = new SessionManagement(this);
        String nik = session.getNIK();
        APIPengaturanProfil apiPengaturanProfil = RetroServer.konekRetrofit().create(APIPengaturanProfil.class);
        Call<ResponseModelAuth> apiAmbilDataProfil = apiPengaturanProfil.apiAmbilDataProfil(nik);

        apiAmbilDataProfil.enqueue(new Callback<ResponseModelAuth>() {
            @Override
            public void onResponse(Call<ResponseModelAuth> call, Response<ResponseModelAuth> response) {
                int code = response.body().getCode();
                String message = response.body().getMessage();
                AccountModelAuth dataAkun = response.body().getData();

//                Toast.makeText(PengaturanProfiltActivity.this, "NIK : "+dataAkun.getNik(), Toast.LENGTH_SHORT).show();
//                int dataNIK = dataAkun.getNik();
                tv_nik.setText(""+dataAkun.getNik());
                tv_nama.setText(dataAkun.getNama_lengkap());
                tv_tmptLahir.setText(dataAkun.getTempat_lahir());
                tv_tgl_lahir.setText(dataAkun.getTanggal_lahir());
                tv_JenisKelamin.setText(dataAkun.getJenis_kelamin());
                tv_GolDar.setText(dataAkun.getGolongan_darah());
                tv_Alamat.setText(dataAkun.getAlamat());
                tv_Agama.setText(dataAkun.getAgama());
                tv_perkawinan.setText(dataAkun.getStatus_perkawinan());
                tv_pekerjaan.setText(dataAkun.getPekerjaan());
                loading2.dismissLoading();
            }

            @Override
            public void onFailure(Call<ResponseModelAuth> call, Throwable t) {
                Toast.makeText(PendudukTambahKTPActivity.this, "Error Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                loading2.dismissLoading();
            }
        });
    }
}