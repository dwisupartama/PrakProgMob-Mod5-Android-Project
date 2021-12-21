package id.ppmkelompok10.pendudukku.ModulAuth;

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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import id.ppmkelompok10.pendudukku.API.APIAuth.APILogin;
import id.ppmkelompok10.pendudukku.API.APIAuth.APIPengaturanProfil;
import id.ppmkelompok10.pendudukku.API.RetroServer;
import id.ppmkelompok10.pendudukku.GetStartedActivity;
import id.ppmkelompok10.pendudukku.Helper.SessionManagement;
import id.ppmkelompok10.pendudukku.MainPendudukActivity;
import id.ppmkelompok10.pendudukku.Model.ModelAuth.AccountModelAuth;
import id.ppmkelompok10.pendudukku.Model.ModelAuth.ResponseModelAuth;
import id.ppmkelompok10.pendudukku.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengaturanProfilActivity extends AppCompatActivity {
    private ImageButton imbTanggal, btnBack;
    private TextView tvTanggalLahir, tvNIK;
    private Spinner spAgama, spStatusPerkawinan;
    private Button btnUbahPassword, btnPerbaharui;
    private EditText etNamaLengkap, etTempatLahir, etAlamat, etPekerjaan;
    RadioGroup rgJK, rgGoldar;
    RadioButton rbJK, rbGoldar, rbJKL, rbJKP, rbGoldarA, rbGoldarB, rbGoldarAB, rbGoldarO;
    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan_profil);

        session = new SessionManagement(this);

        //Merubah Status Bar Menjadi Putih / Mode Light
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        tvNIK = findViewById(R.id.tv_nik);

        btnUbahPassword = findViewById(R.id.btn_ubah_password);

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

        spAgama = findViewById(R.id.sp_agama);
        spStatusPerkawinan = findViewById(R.id.sp_status_perkawinan);

        etPekerjaan = findViewById(R.id.et_pekerjaan);

        btnBack = findViewById(R.id.btn_back);
        btnPerbaharui = findViewById(R.id.btn_perbaharui);

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

        ambilDataAPI();

        //Tombol Tanggal Lahir
        imbTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        PengaturanProfilActivity.this, R.style.datePickerDialogPrimary, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        tvTanggalLahir.setText(""+year+"-"+month+"-"+dayOfMonth);
                    }
                },year, month,day);
                datePickerDialog.show();
            }
        });

        //Tombol Ubah Password
        btnUbahPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ubahPasswordActivity = new Intent(PengaturanProfilActivity.this, UbahPasswordActivity.class);
                startActivity(ubahPasswordActivity);
            }
        });

        //Tombol Kembali
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnPerbaharui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validasiData();
            }
        });
    }

    @SuppressLint("ResourceType")
    public void validasiData() {
        if(etNamaLengkap.getText().toString().trim().length()==0){
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
            prosesPerbaharuiProfil();
        }
    }

    public void prosesPerbaharuiProfil() {
        String dataNik = session.getNIK();
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

        APIPengaturanProfil apiPengaturanProfil = RetroServer.konekRetrofit().create(APIPengaturanProfil.class);
        Call<ResponseModelAuth> apiPerbaharuiProfil = apiPengaturanProfil.apiPerbaharuiProfil(
                dataNik,
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

        apiPerbaharuiProfil.enqueue(new Callback<ResponseModelAuth>() {
            @Override
            public void onResponse(Call<ResponseModelAuth> call, Response<ResponseModelAuth> response) {
                int code = response.body().getCode();
                String message = response.body().getMessage();

                if(code == 0){
                    alertDialogDanger(PengaturanProfilActivity.this, "Gagal", message);
                }else{
                    alertDialogSuccess(PengaturanProfilActivity.this, "Berhasil", message);
                }
            }

            @Override
            public void onFailure(Call<ResponseModelAuth> call, Throwable t) {
                Toast.makeText(PengaturanProfilActivity.this, "Error Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ambilDataAPI();
    }

    public void ambilDataAPI(){
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

//                Toast.makeText(PengaturanProfiltActivity.this, "NIK : "+dataAkun.getNik(), Toast.LENGTH_SHORT).show();
//                int dataNIK = dataAkun.getNik();
                tvNIK.setText(""+dataAkun.getNik());
                etNamaLengkap.setText(dataAkun.getNama_lengkap());
                etTempatLahir.setText(dataAkun.getTempat_lahir());
                tvTanggalLahir.setText(dataAkun.getTanggal_lahir());

                String dataJenisKelamin = dataAkun.getJenis_kelamin();
                if(dataJenisKelamin.equals("Laki - Laki")){
                    rbJKL.setChecked(true);
                }else if(dataJenisKelamin.equals("Perempuan")){
                    rbJKP.setChecked(true);
                }

                String dataGolonganDarah = dataAkun.getGolongan_darah();
                if(dataGolonganDarah.equals("A")){
                    rbGoldarA.setChecked(true);
                }else if(dataGolonganDarah.equals("B")){
                    rbGoldarB.setChecked(true);
                }else if(dataGolonganDarah.equals("AB")){
                    rbGoldarAB.setChecked(true);
                }else if(dataGolonganDarah.equals("O")){
                    rbGoldarO.setChecked(true);
                }

                etAlamat.setText(dataAkun.getAlamat());

                String dataAgama = dataAkun.getAgama();
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

                String dataStatusPerkawinan = dataAkun.getStatus_perkawinan();
                if (dataStatusPerkawinan.equals("Kawin")) {
                    spStatusPerkawinan.setSelection(1);
                }else if(dataStatusPerkawinan.equals("Belum Kawin")) {
                    spStatusPerkawinan.setSelection(2);
                }else if(dataStatusPerkawinan.equals("Cerai Mati")) {
                    spStatusPerkawinan.setSelection(3);
                }else if(dataStatusPerkawinan.equals("Cerai Hidup")) {
                    spStatusPerkawinan.setSelection(4);
                }

                etPekerjaan.setText(dataAkun.getPekerjaan());
            }

            @Override
            public void onFailure(Call<ResponseModelAuth> call, Throwable t) {
                Toast.makeText(PengaturanProfilActivity.this, "Error Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
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

                ambilDataAPI();
            }
        });
    }
}