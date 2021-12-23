package id.ppmkelompok10.pendudukku.ModulPenduduk;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import id.ppmkelompok10.pendudukku.API.APIAuth.APIPengaturanProfil;
import id.ppmkelompok10.pendudukku.API.APIPenduduk.APIPenduduk;
import id.ppmkelompok10.pendudukku.API.RetroServer;
import id.ppmkelompok10.pendudukku.Helper.LoadingDialog;
import id.ppmkelompok10.pendudukku.Helper.SessionManagement;
import id.ppmkelompok10.pendudukku.Model.ModelAuth.ResponseModelAuth;
import id.ppmkelompok10.pendudukku.Model.ModelPenduduk.ResponseSingleDataModel;
import id.ppmkelompok10.pendudukku.ModulAuth.PengaturanProfilActivity;
import id.ppmkelompok10.pendudukku.ModulAuth.UbahPasswordActivity;
import id.ppmkelompok10.pendudukku.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PegawaiTambahPendudukActivity extends AppCompatActivity {
    private ImageButton imbTanggal, btnBack;
    private TextView tvTanggalLahir;
    private Spinner spAgama, spStatusPerkawinan, spStatusAkses;
    private Button btnTambah;
    private EditText etNIK, etNamaLengkap, etTempatLahir, etAlamat, etPekerjaan;
    private CheckBox cbCheckData;
    RadioGroup rgJK, rgGoldar;
    RadioButton rbJK, rbGoldar, rbJKL, rbJKP, rbGoldarA, rbGoldarB, rbGoldarAB, rbGoldarO;
    private LinearLayout lnStatusAkses;
    SessionManagement session;

    @Override
    protected void onStart() {
        super.onStart();
        session = new SessionManagement(this);

        if(session.getStatusAkses().equals("Admin")){
            lnStatusAkses.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pegawai_tambah_penduduk);

        session = new SessionManagement(this);

        //Merubah Status Bar Menjadi Putih / Mode Light
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        lnStatusAkses = findViewById(R.id.ln_status_akses);

        etNIK = findViewById(R.id.et_nik);
        etNamaLengkap = findViewById(R.id.et_nama_lengkap);
        etTempatLahir = findViewById(R.id.et_tempat_lahir);

        tvTanggalLahir = findViewById(R.id.tv_tanggal_lahir);
        imbTanggal = findViewById(R.id.imb_tanggal);

        rgJK = (RadioGroup) findViewById(R.id.group_jk);
        rbJKL = (RadioButton) findViewById(R.id.jk_lakilaki);
        rbJKP = (RadioButton) findViewById(R.id.jk_perempuan);

        rgGoldar = (RadioGroup) findViewById(R.id.group_golongan_darah);
        rbGoldarA = (RadioButton) findViewById(R.id.golongan_darah_a);
        rbGoldarB = (RadioButton) findViewById(R.id.golongan_darah_b);
        rbGoldarAB = (RadioButton) findViewById(R.id.golongan_darah_ab);
        rbGoldarO = (RadioButton) findViewById(R.id.golongan_darah_o);

        etAlamat = findViewById(R.id.et_alamat);

        //Deklarasi Spinner
        spStatusAkses = findViewById(R.id.sp_status_akses);
        spAgama = findViewById(R.id.sp_agama);
        spStatusPerkawinan = findViewById(R.id.sp_status_perkawinan);

        etPekerjaan = findViewById(R.id.et_pekerjaan);

        cbCheckData = findViewById(R.id.cb_check_data);

        btnTambah = findViewById(R.id.btn_tambah);

        //Deklarasi Tombol Kembali
        btnBack = findViewById(R.id.btn_back);

        //Konfigurasi Spinner Status Akses
        ArrayAdapter<String> adapterStatusAkses = new ArrayAdapter<>(
                this,
                R.layout.custom_spinner,
                getResources().getStringArray(R.array.list_status_akses)
        );
        adapterStatusAkses.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spStatusAkses.setAdapter(adapterStatusAkses);

        //Konfigurasi Spinner Agama
        ArrayAdapter<String> adapterAgama = new ArrayAdapter<>(
                this,
                R.layout.custom_spinner,
                getResources().getStringArray(R.array.list_agama)
        );
        adapterAgama.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spAgama.setAdapter(adapterAgama);

        //Konfigurasi Spinner Status Perkawinan
        ArrayAdapter<String> adapterStatusPerkawinan = new ArrayAdapter<>(
                this,
                R.layout.custom_spinner,
                getResources().getStringArray(R.array.list_status_perkawinan)
        );
        adapterStatusPerkawinan.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spStatusPerkawinan.setAdapter(adapterStatusPerkawinan);

        //Deklarasi Pengambilan Tanggal Untuk Tombol Tanggal Lahir
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        //Tombol Tanggal Lahir
        imbTanggal = findViewById(R.id.imb_tanggal);
        imbTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        PegawaiTambahPendudukActivity.this, R.style.datePickerDialogPrimary, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        tvTanggalLahir.setText(""+year+"/"+month+"/"+dayOfMonth);
                    }
                },year, month,day);
                datePickerDialog.show();
            }
        });

        //Tombol Kembali
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(PegawaiTambahPendudukActivity.this, "test", Toast.LENGTH_SHORT).show();
                validasiData();
            }
        });
    }

    @SuppressLint("ResourceType")
    public void validasiData() {
        if(session.getStatusAkses().equals("Admin")){
            if(spStatusAkses.getSelectedItem().toString().equals("Pilih Status Akses")){
                TextView textStatusAkses = (TextView) spStatusAkses.getSelectedView();
                textStatusAkses.setError("Status Akses harus dipilih !");
                spStatusAkses.requestFocus();
            }else if(etNIK.getText().toString().trim().length()==0){
                etNIK.setError("NIK harus diisi !");
                etNIK.requestFocus();
            }else if(etNamaLengkap.getText().toString().trim().length()==0){
                etNamaLengkap.setError("Nama Lengkap harus diisi !");
                etNamaLengkap.requestFocus();
            }else if(etTempatLahir.getText().toString().trim().length()==0){
                etTempatLahir.setError("Tempat Lahir harus diisi !");
                etTempatLahir.requestFocus();
            }else if(tvTanggalLahir.getText().toString().trim().equals("YYYY-MM-DD")){
                tvTanggalLahir.setError("Tanggal Lahir harus diisi !");
                tvTanggalLahir.requestFocus();
            }else if(rgJK.getCheckedRadioButtonId()<=0){
                rbJK = findViewById(R.id.jk_perempuan);
                rbJK.setError("Jenis Kelamin harus diisi !");
                rbJK.requestFocus();
            }else if(rgGoldar.getCheckedRadioButtonId()<=0){
                rbGoldar = findViewById(R.id.golongan_darah_o);
                rbGoldar.setError("Golongan Darah harus diisi !");
                rbGoldar.requestFocus();
            }else if(etAlamat.getText().toString().trim().length()==0){
                etAlamat.setError("Alamat harus diisi !");
                etAlamat.requestFocus();
            }else if(spAgama.getSelectedItem().toString().equals("Pilih Agama")){
                TextView textAgama = (TextView) spAgama.getSelectedView();
                textAgama.setError("Agama harus dipilih !");
                spAgama.requestFocus();
            }else if(spStatusPerkawinan.getSelectedItem().toString().equals("Pilih Status Perkawinan")){
                TextView textStatusPerkawinan = (TextView) spStatusPerkawinan.getSelectedView();
                textStatusPerkawinan.setError("Agama harus dipilih !");
                spAgama.requestFocus();
            }else if(etPekerjaan.getText().toString().trim().length()==0){
                etPekerjaan.setError("Pekerjaan harus diisi !");
                etPekerjaan.requestFocus();
            }else if(!cbCheckData.isChecked()){
                cbCheckData.setError("Ini harus dicentang !");
                cbCheckData.requestFocus();
            }else{
                prosesTambahPenduduk();
            }
        }else{
            if(etNIK.getText().toString().trim().length()==0){
                etNIK.setError("NIK harus diisi !");
                etNIK.requestFocus();
            }else if(etNamaLengkap.getText().toString().trim().length()==0){
                etNamaLengkap.setError("Nama Lengkap harus diisi !");
                etNamaLengkap.requestFocus();
            }else if(etTempatLahir.getText().toString().trim().length()==0){
                etTempatLahir.setError("Tempat Lahir harus diisi !");
                etTempatLahir.requestFocus();
            }else if(tvTanggalLahir.getText().toString().trim().equals("YYYY-MM-DD")){
                tvTanggalLahir.setError("Tanggal Lahir harus diisi !");
                tvTanggalLahir.requestFocus();
            }else if(rgJK.getCheckedRadioButtonId()<=0){
                rbJK = findViewById(R.id.jk_perempuan);
                rbJK.setError("Jenis Kelamin harus diisi !");
                rbJK.requestFocus();
            }else if(rgGoldar.getCheckedRadioButtonId()<=0){
                rbGoldar = findViewById(R.id.golongan_darah_o);
                rbGoldar.setError("Golongan Darah harus diisi !");
                rbGoldar.requestFocus();
            }else if(etAlamat.getText().toString().trim().length()==0){
                etAlamat.setError("Alamat harus diisi !");
                etAlamat.requestFocus();
            }else if(spAgama.getSelectedItem().toString().equals("Pilih Agama")){
                TextView textAgama = (TextView) spAgama.getSelectedView();
                textAgama.setError("Agama harus dipilih !");
                spAgama.requestFocus();
            }else if(spStatusPerkawinan.getSelectedItem().toString().equals("Pilih Status Perkawinan")){
                TextView textStatusPerkawinan = (TextView) spStatusPerkawinan.getSelectedView();
                textStatusPerkawinan.setError("Agama harus dipilih !");
                spAgama.requestFocus();
            }else if(etPekerjaan.getText().toString().trim().length()==0){
                etPekerjaan.setError("Pekerjaan harus diisi !");
                etPekerjaan.requestFocus();
            }else if(!cbCheckData.isChecked()){
                cbCheckData.setError("Ini harus dicentang !");
                cbCheckData.requestFocus();
            }else{
                prosesTambahPenduduk();
            }
        }
    }

    public void prosesTambahPenduduk() {
        LoadingDialog loading2 = new LoadingDialog(this);
        loading2.startLoadingDialog();

        String uStatusAkses;
        if(session.getStatusAkses().equals("Admin")){
            uStatusAkses = spStatusAkses.getSelectedItem().toString();
        }else{
            uStatusAkses = "Penduduk";
        }

        String uNIK = etNIK.getText().toString().trim();
        String uNamaLengkap = etNamaLengkap.getText().toString().trim();
        String uTempatLahir = etTempatLahir.getText().toString().trim();
        String uTanggalLahir = tvTanggalLahir.getText().toString().trim();

        int selectedJK = rgJK.getCheckedRadioButtonId();
        rbJK = findViewById(selectedJK);
        String uJenisKelamin = rbJK.getText().toString();

        int selectedGoldar = rgGoldar.getCheckedRadioButtonId();
        rbGoldar = findViewById(selectedGoldar);
        String uGolonganDarah = rbGoldar.getText().toString();

        String uAlamat = etAlamat.getText().toString().trim();
        String uAgama = spAgama.getSelectedItem().toString();
        String uStatusPerkawinan = spStatusPerkawinan.getSelectedItem().toString();
        String uPekerjaan = etPekerjaan.getText().toString();

        APIPenduduk apiPenduduk = RetroServer.konekRetrofit().create(APIPenduduk.class);
        Call<ResponseSingleDataModel> apiTambahPenduduk = apiPenduduk.addPenduduk(
                uNIK,
                uStatusAkses,
                uNamaLengkap,
                uTempatLahir,
                uTanggalLahir,
                uJenisKelamin,
                uGolonganDarah,
                uAlamat,
                uAgama,
                uStatusPerkawinan,
                uPekerjaan
        );

        apiTambahPenduduk.enqueue(new Callback<ResponseSingleDataModel>() {
            @Override
            public void onResponse(Call<ResponseSingleDataModel> call, Response<ResponseSingleDataModel> response) {
                int code = response.body().getCode();
                String message = response.body().getMessage();
                loading2.dismissLoading();
                if(code == 0){
                    alertDialogDanger(PegawaiTambahPendudukActivity.this, "Gagal", message);
                }else{
                    alertDialogSuccess(PegawaiTambahPendudukActivity.this, "Berhasil", message);
                }
            }

            @Override
            public void onFailure(Call<ResponseSingleDataModel> call, Throwable t) {
                loading2.dismissLoading();
                Toast.makeText(PegawaiTambahPendudukActivity.this, "Error Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
}