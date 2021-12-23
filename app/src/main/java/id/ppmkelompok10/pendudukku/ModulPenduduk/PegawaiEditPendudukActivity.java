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
import id.ppmkelompok10.pendudukku.Model.ModelAuth.AccountModelAuth;
import id.ppmkelompok10.pendudukku.Model.ModelAuth.ResponseModelAuth;
import id.ppmkelompok10.pendudukku.Model.ModelPenduduk.ModelDataPenduduk;
import id.ppmkelompok10.pendudukku.Model.ModelPenduduk.ResponseSingleDataModel;
import id.ppmkelompok10.pendudukku.ModulAuth.PengaturanProfilActivity;
import id.ppmkelompok10.pendudukku.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PegawaiEditPendudukActivity extends AppCompatActivity {
    private ImageButton imbTanggal, btnBack;
    private TextView tvTanggalLahir;
    private Spinner spAgama, spStatusPerkawinan, spStatusAkses;
    private Button btnPerbaharui, btnResetPassword;
    private EditText etNIK, etNamaLengkap, etTempatLahir, etAlamat, etPekerjaan;
    RadioGroup rgJK, rgGoldar;
    RadioButton rbJK, rbGoldar, rbJKL, rbJKP, rbGoldarA, rbGoldarB, rbGoldarAB, rbGoldarO;
    private LinearLayout lnStatusAkses;
    SessionManagement session;
    String nik;

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
        setContentView(R.layout.activity_pegawai_edit_penduduk);

        Intent pegawaiEditPendudukActivity = getIntent();
        nik = pegawaiEditPendudukActivity.getStringExtra("id_penduduk");

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

        btnPerbaharui = findViewById(R.id.btn_perbaharui);
        btnResetPassword = findViewById(R.id.btn_reset_password);

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

        ambilData();

        //Tombol Tanggal Lahir
        imbTanggal = findViewById(R.id.imb_tanggal);
        imbTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        PegawaiEditPendudukActivity.this, R.style.datePickerDialogPrimary, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        tvTanggalLahir.setText(""+dayOfMonth+"/"+month+"/"+year);
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

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadingDialog loading2 = new LoadingDialog(PegawaiEditPendudukActivity.this);
                loading2.startLoadingDialog();

                APIPenduduk apiPenduduk = RetroServer.konekRetrofit().create(APIPenduduk.class);
                Call<ResponseSingleDataModel> apiResetPassword = apiPenduduk.resetPasswordPenduduk(nik);

                apiResetPassword.enqueue(new Callback<ResponseSingleDataModel>() {
                    @Override
                    public void onResponse(Call<ResponseSingleDataModel> call, Response<ResponseSingleDataModel> response) {
                        int code = response.body().getCode();
                        String message = response.body().getMessage();
                        loading2.dismissLoading();
                        if(code == 0){
                            alertDialogDanger(PegawaiEditPendudukActivity.this, "Gagal", message);
                        }else{
                            alertDialogSuccess(PegawaiEditPendudukActivity.this, "Berhasil", message);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseSingleDataModel> call, Throwable t) {
                        loading2.dismissLoading();
                        Toast.makeText(PegawaiEditPendudukActivity.this, "Error Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnPerbaharui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validasiData();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ambilData();
    }

    public void ambilData(){
        LoadingDialog loading2 = new LoadingDialog(this);
        loading2.startLoadingDialog();

        APIPenduduk apiPenduduk = RetroServer.konekRetrofit().create(APIPenduduk.class);
        Call<ResponseSingleDataModel> apiEditPenduduk = apiPenduduk.editPenduduk(nik);

        apiEditPenduduk.enqueue(new Callback<ResponseSingleDataModel>() {
            @Override
            public void onResponse(Call<ResponseSingleDataModel> call, Response<ResponseSingleDataModel> response) {
                int code = response.body().getCode();
                String message = response.body().getMessage();
                ModelDataPenduduk dataPenduduk = response.body().getData();

                String dataStatusAkses = dataPenduduk.getStatus_akses();
                if (dataStatusAkses.equals("Penduduk")) {
                    spStatusAkses.setSelection(1);
                }else if(dataStatusAkses.equals("Pegawai")) {
                    spStatusAkses.setSelection(2);
                }else if(dataStatusAkses.equals("Admin")) {
                    spStatusAkses.setSelection(3);
                }

                etNIK.setText(dataPenduduk.getNik());
                etNamaLengkap.setText(dataPenduduk.getNama_lengkap());
                etTempatLahir.setText(dataPenduduk.getTempat_lahir());
                tvTanggalLahir.setText(dataPenduduk.getTanggal_lahir());

                String dataJenisKelamin = dataPenduduk.getJenis_kelamin();
                if(dataJenisKelamin.equals("Laki - Laki")){
                    rbJKL.setChecked(true);
                }else if(dataJenisKelamin.equals("Perempuan")){
                    rbJKP.setChecked(true);
                }

                String dataGolonganDarah = dataPenduduk.getGolongan_darah();
                if(dataGolonganDarah.equals("A")){
                    rbGoldarA.setChecked(true);
                }else if(dataGolonganDarah.equals("B")){
                    rbGoldarB.setChecked(true);
                }else if(dataGolonganDarah.equals("AB")){
                    rbGoldarAB.setChecked(true);
                }else if(dataGolonganDarah.equals("O")){
                    rbGoldarO.setChecked(true);
                }

                etAlamat.setText(dataPenduduk.getAlamat());

                String dataAgama = dataPenduduk.getAgama();
                if (dataAgama.equals("Hindu")) {
                    spAgama.setSelection(1);
                }else if(dataAgama.equals("Islam")) {
                    spAgama.setSelection(2);
                }else if(dataAgama.equals("Kristen Katolik")) {
                    spAgama.setSelection(3);
                }else if(dataAgama.equals("Kristen Protestan")) {
                    spAgama.setSelection(4);
                }else if(dataAgama.equals("Budha")) {
                    spAgama.setSelection(5);
                }else if(dataAgama.equals("Kong Hu Chu")) {
                    spAgama.setSelection(6);
                }

                String dataStatusPerkawinan = dataPenduduk.getStatus_perkawinan();
                if (dataStatusPerkawinan.equals("Kawin")) {
                    spStatusPerkawinan.setSelection(1);
                }else if(dataStatusPerkawinan.equals("Belum Kawin")) {
                    spStatusPerkawinan.setSelection(2);
                }else if(dataStatusPerkawinan.equals("Cerai Mati")) {
                    spStatusPerkawinan.setSelection(3);
                }else if(dataStatusPerkawinan.equals("Cerai Hidup")) {
                    spStatusPerkawinan.setSelection(4);
                }

                etPekerjaan.setText(dataPenduduk.getPekerjaan());
                loading2.dismissLoading();
            }

            @Override
            public void onFailure(Call<ResponseSingleDataModel> call, Throwable t) {
                Toast.makeText(PegawaiEditPendudukActivity.this, "Error Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                loading2.dismissLoading();
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
            }else{
                prosesPerbaharuiPenduduk();
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
            }else{
                prosesPerbaharuiPenduduk();
            }
        }
    }

    public void prosesPerbaharuiPenduduk() {
        LoadingDialog loading2 = new LoadingDialog(this);
        loading2.startLoadingDialog();

        String uNIK = nik;

        String uStatusAkses;
        if(session.getStatusAkses().equals("Admin")){
            uStatusAkses = spStatusAkses.getSelectedItem().toString();
        }else{
            uStatusAkses = "Penduduk";
        }

        String uNewNik = etNIK.getText().toString().trim();
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
        Call<ResponseSingleDataModel> apiUpdatePenduduk = apiPenduduk.updatePenduduk(
                uNIK,
                uStatusAkses,
                uNewNik,
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

        apiUpdatePenduduk.enqueue(new Callback<ResponseSingleDataModel>() {
            @Override
            public void onResponse(Call<ResponseSingleDataModel> call, Response<ResponseSingleDataModel> response) {
                int code = response.body().getCode();
                String message = response.body().getMessage();
                loading2.dismissLoading();
                if(code == 0){
                    alertDialogDanger(PegawaiEditPendudukActivity.this, "Gagal", message);
                }else{
                    alertDialogSuccess(PegawaiEditPendudukActivity.this, "Berhasil", message);
                }
            }

            @Override
            public void onFailure(Call<ResponseSingleDataModel> call, Throwable t) {
                loading2.dismissLoading();
                Toast.makeText(PegawaiEditPendudukActivity.this, "Error Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
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