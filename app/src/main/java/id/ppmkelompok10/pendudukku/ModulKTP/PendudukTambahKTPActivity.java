package id.ppmkelompok10.pendudukku.ModulKTP;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import id.ppmkelompok10.pendudukku.API.APIAuth.APIPengaturanProfil;
import id.ppmkelompok10.pendudukku.API.APIKTP.APIPengajuanKTP;
import id.ppmkelompok10.pendudukku.API.RetroServer;
import id.ppmkelompok10.pendudukku.Helper.LoadingDialog;
import id.ppmkelompok10.pendudukku.Helper.SessionManagement;
import id.ppmkelompok10.pendudukku.Model.ModelAuth.AccountModelAuth;
import id.ppmkelompok10.pendudukku.Model.ModelAuth.ResponseModelAuth;
import id.ppmkelompok10.pendudukku.Model.ModelKTP.ResponseSingleDataModelKTP;
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

        session = new SessionManagement(this);

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
//        ambilDataAPI();

        setDataForm();

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
                validasiData();
            }
        });

    }

    public void validasiData() {
        if(spJenisPengajuanKTP.getSelectedItem().toString().equals("Pilih Jenis Pengajuan KTP")){
            TextView textStatusAkses = (TextView) spJenisPengajuanKTP.getSelectedView();
            textStatusAkses.setError("Jenis Pengajuan KTP harus dipilih !");
            spJenisPengajuanKTP.requestFocus();
        }else if(!checkData.isChecked()){
            checkData.setError("Ini harus dicentang !");
            checkData.requestFocus();
        }else{
            prosesSimpanPengajuanKTP();
        }
    }

    public void prosesSimpanPengajuanKTP() {
        LoadingDialog loading = new LoadingDialog(this);
        loading.startLoadingDialog();

        String txtJenisPengajuan = spJenisPengajuanKTP.getSelectedItem().toString();
        String txtNIK = tv_nik.getText().toString();
        String txtNamaLengkap = tv_nama.getText().toString();
        String txtTempatLahir = tv_tmptLahir.getText().toString();
        String txtTanggalLahir = tv_tgl_lahir.getText().toString();
        String txtJenisKelamin = tv_JenisKelamin.getText().toString();
        String txtGolonganDarah = tv_GolDar.getText().toString();
        String txtAlamat = tv_Alamat.getText().toString();
        String txtAgama = tv_Agama.getText().toString();
        String txtStatusPerkawinan = tv_perkawinan.getText().toString();
        String txtPekerjaan = tv_pekerjaan.getText().toString();

        APIPengajuanKTP apiPengajuan = RetroServer.konekRetrofit().create(APIPengajuanKTP.class);
        Call<ResponseSingleDataModelKTP> apiPengajuanKTP = apiPengajuan.apiPengajuanKTP(
                txtJenisPengajuan,
                txtNIK,
                txtNamaLengkap,
                txtTempatLahir,
                txtTanggalLahir,
                txtJenisKelamin,
                txtGolonganDarah,
                txtAlamat,
                txtAgama,
                txtStatusPerkawinan,
                txtPekerjaan
        );

        apiPengajuanKTP.enqueue(new Callback<ResponseSingleDataModelKTP>() {
            @Override
            public void onResponse(Call<ResponseSingleDataModelKTP> call, Response<ResponseSingleDataModelKTP> response) {
                int code = response.body().getCode();
                String message = response.body().getMessage();

                if(code == 1){
                    alertDialogSuccess(PendudukTambahKTPActivity.this, "Berhasil", message);
                }else{
                    alertDialogDanger(PendudukTambahKTPActivity.this, "Gagal", message);
                }
            }

            @Override
            public void onFailure(Call<ResponseSingleDataModelKTP> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setDataForm();
    }

    public void alertDialogDanger(Context context, String textTitle, String textMessage){
        AlertDialog.Builder builderDialog;
        AlertDialog alertDialog;

        builderDialog = new AlertDialog.Builder(context);
        View layoutView = LayoutInflater.from(context).inflate(R.layout.dialog_danger, null);

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
            }
        });
    }

    public void alertDialogSuccess(Context context, String textTitle, String textMessage){
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
                finish();
            }
        });
    }

    public void setDataForm(){
        LoadingDialog loading2 = new LoadingDialog(this);
        loading2.startLoadingDialog();

        String nik = session.getNIK();

        APIPengaturanProfil apiPengaturanProfil = RetroServer.konekRetrofit().create(APIPengaturanProfil.class);
        Call<ResponseModelAuth> apiAmbilDataProfil = apiPengaturanProfil.apiAmbilDataProfil(nik);

        apiAmbilDataProfil.enqueue(new Callback<ResponseModelAuth>() {
            @Override
            public void onResponse(Call<ResponseModelAuth> call, Response<ResponseModelAuth> response) {
                int code = response.body().getCode();
                String message = response.body().getMessage();
                AccountModelAuth data = response.body().getData();

                tv_nik.setText(data.getNik());
                tv_nama.setText(data.getNama_lengkap());
                tv_tmptLahir.setText(data.getTempat_lahir());
                tv_tgl_lahir.setText(data.getTanggal_lahir());
                tv_JenisKelamin.setText(data.getJenis_kelamin());
                tv_GolDar.setText(data.getGolongan_darah());
                tv_Alamat.setText(data.getAlamat());
                tv_Agama.setText(data.getAgama());
                tv_perkawinan.setText(data.getStatus_perkawinan());
                tv_pekerjaan.setText(data.getPekerjaan());
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