package id.ppmkelompok10.pendudukku.ModulVaksin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import id.ppmkelompok10.pendudukku.API.APIVaksin.APIPegawaiVaksin;
import id.ppmkelompok10.pendudukku.API.APIVaksin.APIPendudukVaksin;
import id.ppmkelompok10.pendudukku.API.RetroServer;
import id.ppmkelompok10.pendudukku.Helper.LoadingDialog;
import id.ppmkelompok10.pendudukku.Model.ModelVaksin.ModelVaksin;
import id.ppmkelompok10.pendudukku.Model.ModelVaksin.ResponseSingleModelDataVaksin;
import id.ppmkelompok10.pendudukku.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PegawaiEditVaksinActivity extends AppCompatActivity {
    private ImageButton imbTanggalPerkiraanSelesai, imbTanggalVaksin, btnBack;
    private TextView tvTanggalPerkiraanSelesai, tvTanggalVaksin;
    private Spinner spStatusPengajuan;
    private Button btn_perbaharui;
    private LinearLayout lnTanggalPerkiraanSelesai, lnTanggalVaksin, lnWaktuVaksin, lnTempatVaksin, lnJenisVaksin;
    private EditText etWaktuVaksin, etTempatVaksin, etKeterangan, etJenisVaksin;
    String idVaksin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pegawai_edit_vaksin);

        //Merubah Status Bar Menjadi Putih / Mode Light
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        Intent intent = getIntent();
        idVaksin = String.valueOf(intent.getIntExtra("id_vaksin",0));

        //Deklarasi Image Button
        imbTanggalPerkiraanSelesai = findViewById(R.id.imb_tanggal_perkiraan_selesai);
        imbTanggalVaksin = findViewById(R.id.imb_tanggal_vaksin);

        //Deklarasi TextView Tanggal
        tvTanggalPerkiraanSelesai = findViewById(R.id.tv_tanggal_perkiraan_selesai);
        tvTanggalVaksin = findViewById(R.id.tv_tanggal_vaksin);

        etWaktuVaksin = findViewById(R.id.et_waktu_vaksin);
        etTempatVaksin = findViewById(R.id.et_tempat_vaksin);
        etKeterangan = findViewById(R.id.et_keterangan);
        etJenisVaksin = findViewById(R.id.et_jenis_vaksin);

        //Deklarasi Tombol Back
        btnBack = findViewById(R.id.btn_back);

        //Deklarasi tombol perbarui
        btn_perbaharui = findViewById(R.id.btn_perbaharui);

        //Deklarasi Spiner
        spStatusPengajuan = findViewById(R.id.sp_status_pengajuan);

        //Deklarasi Linear Layout
        lnTanggalPerkiraanSelesai = findViewById(R.id.ln_tanggal_perkiraan_selesai);
        lnTanggalVaksin = findViewById(R.id.ln_tanggal_vaksin);
        lnWaktuVaksin = findViewById(R.id.ln_waktu_vaksin);
        lnTempatVaksin = findViewById(R.id.ln_tempat_vaksin);
        lnJenisVaksin = findViewById(R.id.ln_jenis_vaksin);

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
                        PegawaiEditVaksinActivity.this, R.style.datePickerDialogPrimary, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        tvTanggalPerkiraanSelesai.setText(""+year+"-"+month+"-"+dayOfMonth);
                    }
                },year, month,day);
                datePickerDialog.show();
            }
        });

        //Tombol Tanggal Perkiraan Selesai
        imbTanggalVaksin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        PegawaiEditVaksinActivity.this, R.style.datePickerDialogPrimary, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        tvTanggalVaksin.setText(""+year+"-"+month+"-"+dayOfMonth);
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
                    lnTanggalVaksin.setVisibility(View.GONE);
                    lnWaktuVaksin.setVisibility(View.GONE);
                    lnTempatVaksin.setVisibility(View.GONE);
                    lnJenisVaksin.setVisibility(View.GONE);
                }else if(position == 3) {
                    lnTanggalPerkiraanSelesai.setVisibility(View.GONE);
                    lnTanggalVaksin.setVisibility(View.VISIBLE);
                    lnWaktuVaksin.setVisibility(View.VISIBLE);
                    lnTempatVaksin.setVisibility(View.VISIBLE);
                    lnJenisVaksin.setVisibility(View.VISIBLE);
                }else{
                    lnTanggalPerkiraanSelesai.setVisibility(View.GONE);
                    lnTanggalVaksin.setVisibility(View.GONE);
                    lnWaktuVaksin.setVisibility(View.GONE);
                    lnTempatVaksin.setVisibility(View.GONE);
                    lnJenisVaksin.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_perbaharui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validasiData();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setStatus();
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
                prosesPerbaharuiVaksin("");
            }
        }else if(spStatusPengajuan.getSelectedItem().toString().equals("Selesai di Proses")){
            if(tvTanggalVaksin.getText().equals("YYYY-MM-DD")){
                tvTanggalVaksin.setError("Tanggal Vaksin harus diisi !");
                tvTanggalVaksin.requestFocus();
            }else if(etWaktuVaksin.getText().toString().trim().length()==0){
                etWaktuVaksin.setError("Waktu Vaksin harus diisi !");
                etWaktuVaksin.requestFocus();
            }else if(etTempatVaksin.getText().toString().trim().length()==0){
                etTempatVaksin.setError("Tempat Vaksin harus diisi !");
                etTempatVaksin.requestFocus();
            }else if(etJenisVaksin.getText().toString().trim().length()==0){
                etJenisVaksin.setError("Jenis Vaksin harus diisi !");
                etJenisVaksin.requestFocus();
            }else {
                prosesPerbaharuiVaksin(date);
            }
        }else{
            prosesPerbaharuiVaksin("");
        }
    }

    private void prosesPerbaharuiVaksin(String date) {
        LoadingDialog loading = new LoadingDialog(this);
        loading.startLoadingDialog();

        String tanggalPerkiraanSelesai, tanggalVaksin, waktuVaksin, tempatVaksin, jenisVaksin;
        if(spStatusPengajuan.getSelectedItem().toString().equals("Sedang di Proses")){
            tanggalPerkiraanSelesai = tvTanggalPerkiraanSelesai.getText().toString().trim();
            tanggalVaksin = "";
            waktuVaksin = "";
            tempatVaksin = "";
            jenisVaksin = "";
        }else if(spStatusPengajuan.getSelectedItem().toString().equals("Selesai di Proses")){
            tanggalPerkiraanSelesai = "";
            tanggalVaksin = tvTanggalVaksin.getText().toString().trim();
            waktuVaksin = etWaktuVaksin.getText().toString().trim();
            tempatVaksin = etTempatVaksin.getText().toString().trim();
            jenisVaksin = etJenisVaksin.getText().toString().trim();
        }else{
            tanggalPerkiraanSelesai = "";
            tanggalVaksin = "";
            waktuVaksin = "";
            tempatVaksin = "";
            jenisVaksin = "";
        }

        String txtStatusPengajuan = spStatusPengajuan.getSelectedItem().toString();
        String keterangan = etKeterangan.getText().toString().trim();

        APIPegawaiVaksin apiPegawaiKTP = RetroServer.konekRetrofit().create(APIPegawaiVaksin.class);
        Call<ResponseSingleModelDataVaksin> apiUpdateVaksin = apiPegawaiKTP.apiUpdateVaksin(idVaksin, txtStatusPengajuan,keterangan, tanggalPerkiraanSelesai, date, tanggalVaksin, waktuVaksin, tempatVaksin, jenisVaksin);

        apiUpdateVaksin.enqueue(new Callback<ResponseSingleModelDataVaksin>() {
            @Override
            public void onResponse(Call<ResponseSingleModelDataVaksin> call, Response<ResponseSingleModelDataVaksin> response) {
                int code = response.body().getCode();
                String message = response.body().getMessage();

                if(code == 1){
                    alertDialogSuccess(PegawaiEditVaksinActivity.this, "Berhasil", message);
                }else{
                    alertDialogDanger(PegawaiEditVaksinActivity.this, "Gagal", message);
                }
                loading.dismissLoading();
            }

            @Override
            public void onFailure(Call<ResponseSingleModelDataVaksin> call, Throwable t) {
                loading.dismissLoading();
                Toast.makeText(PegawaiEditVaksinActivity.this, "Error Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setStatus(){
        LoadingDialog loading2 = new LoadingDialog(this);
        loading2.startLoadingDialog();

        APIPendudukVaksin apiPegawaiVaksin = RetroServer.konekRetrofit().create(APIPendudukVaksin.class);
        Call<ResponseSingleModelDataVaksin> apiDetailVaksin = apiPegawaiVaksin.apiDetailDataVaksin(idVaksin);

        apiDetailVaksin.enqueue(new Callback<ResponseSingleModelDataVaksin>() {
            @Override
            public void onResponse(Call<ResponseSingleModelDataVaksin> call, Response<ResponseSingleModelDataVaksin> response) {
                int code = response.body().getCode();
                String message = response.body().getMessage();
                ModelVaksin data = response.body().getData();

                String statusPengajuan = data.getStatus_pengajuan();
                if (statusPengajuan.equals("Menunggu Konfirmasi")) {
                    spStatusPengajuan.setSelection(1);
                }else if(statusPengajuan.equals("Sedang di Proses")) {
                    spStatusPengajuan.setSelection(2);
                    lnTanggalPerkiraanSelesai.setVisibility(View.VISIBLE);
                    tvTanggalPerkiraanSelesai.setText(data.getPerkiraan_selesai());
                }else if(statusPengajuan.equals("Selesai di Proses")) {
                    spStatusPengajuan.setSelection(3);
                    tvTanggalVaksin.setText(data.getTanggal_vaksin());
                    etWaktuVaksin.setText(data.getWaktu_vaksin());
                    etTempatVaksin.setText(data.getTempat_vaksin());
                }else if(statusPengajuan.equals("Pengajuan Gagal")) {
                    spStatusPengajuan.setSelection(4);
                }

                etKeterangan.setText(data.getKeterangan());
                loading2.dismissLoading();
            }

            @Override
            public void onFailure(Call<ResponseSingleModelDataVaksin> call, Throwable t) {
                Toast.makeText(PegawaiEditVaksinActivity.this, "Error Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                loading2.dismissLoading();
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