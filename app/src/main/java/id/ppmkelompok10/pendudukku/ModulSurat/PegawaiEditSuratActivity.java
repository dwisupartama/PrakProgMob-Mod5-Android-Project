package id.ppmkelompok10.pendudukku.ModulSurat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import id.ppmkelompok10.pendudukku.API.APIKTP.APIPengajuanKTP;
import id.ppmkelompok10.pendudukku.API.APISurat.APIPegawaiSurat;
import id.ppmkelompok10.pendudukku.API.APISurat.APIPendudukSurat;
import id.ppmkelompok10.pendudukku.API.RetroServer;
import id.ppmkelompok10.pendudukku.Helper.LoadingDialog;
import id.ppmkelompok10.pendudukku.Model.ModelKTP.PengajuanKTP;
import id.ppmkelompok10.pendudukku.Model.ModelKTP.ResponseSingleDataModelKTP;
import id.ppmkelompok10.pendudukku.Model.ModelSurat.ModelSurat;
import id.ppmkelompok10.pendudukku.Model.ModelSurat.ResponseMultiDataModelSurat;
import id.ppmkelompok10.pendudukku.Model.ModelSurat.ResponseSingleDataModelSurat;
import id.ppmkelompok10.pendudukku.ModulKTP.PegawaiEditKTPActivity;
import id.ppmkelompok10.pendudukku.ModulVaksin.PegawaiEditVaksinActivity;
import id.ppmkelompok10.pendudukku.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PegawaiEditSuratActivity extends AppCompatActivity {
    private ImageButton imbTanggalPerkiraanSelesai, btnBack;
    private TextView tvTanggalPerkiraanSelesai, txtNamaFile;
    private Spinner spStatusPengajuan;
    private LinearLayout lnTanggalPerkiraanSelesai, lnSuratJadi;
    private Button btnUploadSurat, btnPerbaharui;
    private EditText etKeterangan;
    String idSurat;
    private int REQ_PDF = 21;
    private String encodedPDF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pegawai_edit_surat);

        //Merubah Status Bar Menjadi Putih / Mode Light
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        Intent intent = getIntent();
        idSurat = String.valueOf(intent.getIntExtra("id_surat",0));

        //Deklarasi Image Button
        imbTanggalPerkiraanSelesai = findViewById(R.id.imb_tanggal_perkiraan_selesai);

        //Deklarasi TextView Tanggal
        tvTanggalPerkiraanSelesai = findViewById(R.id.tv_tanggal_perkiraan_selesai);
        txtNamaFile = findViewById(R.id.txt_nama_file);

        //Deklarasi Tombol Back
        btnBack = findViewById(R.id.btn_back);
        btnUploadSurat = findViewById(R.id.btn_upload);
        btnPerbaharui = findViewById(R.id.btn_perbaharui);

        //Deklarasi Spiner
        spStatusPengajuan = findViewById(R.id.sp_status_pengajuan);

        etKeterangan = findViewById(R.id.et_keterangan);

        //Deklarasi Linear Layout
        lnTanggalPerkiraanSelesai = findViewById(R.id.ln_tanggal_perkiraan_selesai);
        lnSuratJadi = findViewById(R.id.ln_surat_jadi);

        //Konfigurasi Spinner Status Akses
        ArrayAdapter<String> adapterStatusPengajuan = new ArrayAdapter<>(
                this,
                R.layout.custom_spinner,
                getResources().getStringArray(R.array.list_status_pengajuan)
        );
        adapterStatusPengajuan.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spStatusPengajuan.setAdapter(adapterStatusPengajuan);

        //Deklarasi Pengambilan Tanggal Untuk Tombol Tanggal Lahir
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        setStatus();

        //Tombol Tanggal Perkiraan Selesai
        imbTanggalPerkiraanSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        PegawaiEditSuratActivity.this, R.style.datePickerDialogPrimary, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        tvTanggalPerkiraanSelesai.setText(""+year+"-"+month+"-"+dayOfMonth);
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

        spStatusPengajuan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 2){
                    lnTanggalPerkiraanSelesai.setVisibility(View.VISIBLE);
                }else if(position == 3) {
                    lnTanggalPerkiraanSelesai.setVisibility(View.GONE);
                    lnSuratJadi.setVisibility(View.VISIBLE);
                }else{
                    lnTanggalPerkiraanSelesai.setVisibility(View.GONE);
                    lnSuratJadi.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnUploadSurat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("application/pdf");
                chooseFile = Intent.createChooser(chooseFile, "Pilih File PDF");
                startActivityForResult(chooseFile, REQ_PDF);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_PDF && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();

            try {
                InputStream inputStream = PegawaiEditSuratActivity.this.getContentResolver().openInputStream(path);
                byte[] pdfInBytes = new byte[inputStream.available()];
                inputStream.read(pdfInBytes);
                encodedPDF = Base64.encodeToString(pdfInBytes, Base64.DEFAULT);

                txtNamaFile.setText("File PDF telah dipilih");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setStatus(){
        LoadingDialog loading2 = new LoadingDialog(this);
        loading2.startLoadingDialog();

        APIPendudukSurat apiPendudukSurat = RetroServer.konekRetrofit().create(APIPendudukSurat.class);
        Call<ResponseSingleDataModelSurat> apiDetailDataSurat = apiPendudukSurat.apiDetailDataSurat(idSurat);

        apiDetailDataSurat.enqueue(new Callback<ResponseSingleDataModelSurat>() {
            @Override
            public void onResponse(Call<ResponseSingleDataModelSurat> call, Response<ResponseSingleDataModelSurat> response) {
                int code = response.body().getCode();
                String message = response.body().getMessage();
                ModelSurat data = response.body().getData();

                String statusPengajuan = data.getStatus_pengajuan();
                if (statusPengajuan.equals("Menunggu Konfirmasi")) {
                    spStatusPengajuan.setSelection(1);
                    lnSuratJadi.setVisibility(View.GONE);
                }else if(statusPengajuan.equals("Sedang di Proses")) {
                    spStatusPengajuan.setSelection(2);
                    lnTanggalPerkiraanSelesai.setVisibility(View.VISIBLE);
                    tvTanggalPerkiraanSelesai.setText(data.getPerkiraan_selesai());
                    lnSuratJadi.setVisibility(View.GONE);
                }else if(statusPengajuan.equals("Selesai di Proses")) {
                    spStatusPengajuan.setSelection(3);
                    lnSuratJadi.setVisibility(View.VISIBLE);
                    txtNamaFile.setText("Pilih Untuk Merubah File");
                }else if(statusPengajuan.equals("Pengajuan Gagal")) {
                    spStatusPengajuan.setSelection(4);
                }
                loading2.dismissLoading();
            }

            @Override
            public void onFailure(Call<ResponseSingleDataModelSurat> call, Throwable t) {
                Toast.makeText(PegawaiEditSuratActivity.this, "Error Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                loading2.dismissLoading();
            }
        });
    }

    public void validasiData() {
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

//        Toast.makeText(PegawaiEditKTPActivity.this, "Date : "+date, Toast.LENGTH_SHORT).show();
        if(spStatusPengajuan.getSelectedItem().toString().equals("Pilih Status Pengajuan")){
            TextView textStatusAkses = (TextView) spStatusPengajuan.getSelectedView();
            textStatusAkses.setError("Status Pengajuan harus dipilih !");
            spStatusPengajuan.requestFocus();
        }else if(spStatusPengajuan.getSelectedItem().toString().equals("Sedang di Proses")){
            if(tvTanggalPerkiraanSelesai.getText().equals("YYYY-MM-DD")){
                tvTanggalPerkiraanSelesai.setError("Tanggal Perkiraan Selesai harus diisi !");
                tvTanggalPerkiraanSelesai.requestFocus();
            }else{
                prosesPerbaharuiSurat("");
            }
        }else if(spStatusPengajuan.getSelectedItem().toString().equals("Selesai di Proses")){
            if(txtNamaFile.getText().toString().trim().equals("Tidak Ada File")){
                txtNamaFile.setError("File Surat harus diisi !");
                txtNamaFile.requestFocus();
            }else {
                prosesPerbaharuiSurat(date);
            }
        }else{
            prosesPerbaharuiSurat("");
        }
    }

    public void prosesPerbaharuiSurat(String date) {
        LoadingDialog loading = new LoadingDialog(this);
        loading.startLoadingDialog();

        String tanggalPerkiraanSelesai, file;
        if(spStatusPengajuan.getSelectedItem().toString().equals("Sedang di Proses")){
            tanggalPerkiraanSelesai = tvTanggalPerkiraanSelesai.getText().toString().trim();
        }else{
            tanggalPerkiraanSelesai = "";
        }

        if(txtNamaFile.getText().toString().trim().equals("Pilih Untuk Merubah File") || txtNamaFile.getText().toString().trim().equals("Tidak Ada File")){
            file = "";
        }else{
            //File Ketika Isi
            file = encodedPDF;
        }

        String txtStatusPengajuan = spStatusPengajuan.getSelectedItem().toString();
        String txtKeterangan = etKeterangan.getText().toString().trim();

        APIPegawaiSurat apiPegawaiSurat = RetroServer.konekRetrofit().create(APIPegawaiSurat.class);
        Call<ResponseSingleDataModelSurat> apiDaftarSurat = apiPegawaiSurat.apiUpdateSurat(idSurat, txtStatusPengajuan, txtKeterangan, tanggalPerkiraanSelesai, date, file);

        apiDaftarSurat.enqueue(new Callback<ResponseSingleDataModelSurat>() {
            @Override
            public void onResponse(Call<ResponseSingleDataModelSurat> call, Response<ResponseSingleDataModelSurat> response) {
                int code = response.body().getCode();
                String message = response.body().getMessage();

                if(code == 1){
                    alertDialogSuccess(PegawaiEditSuratActivity.this, "Berhasil", message);
                }else{
                    alertDialogDanger(PegawaiEditSuratActivity.this, "Gagal", message);
                }
                loading.dismissLoading();
            }

            @Override
            public void onFailure(Call<ResponseSingleDataModelSurat> call, Throwable t) {
                loading.dismissLoading();
                Toast.makeText(PegawaiEditSuratActivity.this, "Error Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
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