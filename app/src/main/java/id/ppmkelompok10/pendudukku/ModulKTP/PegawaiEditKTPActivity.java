package id.ppmkelompok10.pendudukku.ModulKTP;

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

import id.ppmkelompok10.pendudukku.API.APIKTP.APIPegawaiKTP;
import id.ppmkelompok10.pendudukku.API.APIKTP.APIPengajuanKTP;
import id.ppmkelompok10.pendudukku.API.RetroServer;
import id.ppmkelompok10.pendudukku.Helper.LoadingDialog;
import id.ppmkelompok10.pendudukku.Helper.SessionManagement;
import id.ppmkelompok10.pendudukku.Model.ModelKTP.PengajuanKTP;
import id.ppmkelompok10.pendudukku.Model.ModelKTP.ResponseSingleDataModelKTP;
import id.ppmkelompok10.pendudukku.ModulPenduduk.PegawaiTambahPendudukActivity;
import id.ppmkelompok10.pendudukku.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PegawaiEditKTPActivity extends AppCompatActivity {
    private ImageButton imbTanggal, btnBack;
    private Button btn_perbaharui;
    private TextView tvTanggal;
    private Spinner spStatusPengajuan;
    private LinearLayout lnTanggalPerkiraanSelesai;
    EditText et_keterangan;
    SessionManagement session;
    String idPengajuanKTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pegawai_edit_ktp_activity);

        //Merubah Status Bar Menjadi Putih / Mode Light
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //Get Data ID Pengajuan
        Intent detailPengajuanKTP = getIntent();
        idPengajuanKTP = String.valueOf(detailPengajuanKTP.getIntExtra("id_pengajuan",0));

        //Deklarasi Spinner
        spStatusPengajuan = findViewById(R.id.sp_status_pengajuan);

        //Deklarasi Text View Tanggal Lahir
        tvTanggal = findViewById(R.id.tv_tanggal);

        //Deklarasi EditText Keterangan
        et_keterangan = findViewById(R.id.et_keterangan);

        //Deklarasi Tombol Kembali
        btnBack = findViewById(R.id.btn_back);

        //Deklarasi tombol perbarui
        btn_perbaharui = findViewById(R.id.btn_perbaharui);

        //Deklarasi Layout Tanggal Pengajuan;
        lnTanggalPerkiraanSelesai = findViewById(R.id.ln_tanggal_perkiraan_selesai);

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

        //Tombol Tanggal
        imbTanggal = findViewById(R.id.imb_tanggal);
        imbTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        PegawaiEditKTPActivity.this, R.style.datePickerDialogPrimary, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        tvTanggal.setText(""+year+"-"+month+"-"+dayOfMonth);
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
                }else{
                    lnTanggalPerkiraanSelesai.setVisibility(View.GONE);
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

    public void validasiData() {
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

//        Toast.makeText(PegawaiEditKTPActivity.this, "Date : "+date, Toast.LENGTH_SHORT).show();
        if(spStatusPengajuan.getSelectedItem().toString().equals("Pilih Status Pengajuan")){
            TextView textStatusAkses = (TextView) spStatusPengajuan.getSelectedView();
            textStatusAkses.setError("Status Pengajuan harus dipilih !");
            spStatusPengajuan.requestFocus();
        }else if(spStatusPengajuan.getSelectedItem().toString().equals("Sedang di Proses")){
            if(tvTanggal.getText().equals("YYYY-MM-DD")){
                tvTanggal.setError("Tanggal Perkiraan Selesai harus diisi !");
                tvTanggal.requestFocus();
            }else{
                prosesPerbaharuiKTP("");
            }
        }else if(spStatusPengajuan.getSelectedItem().toString().equals("Selesai di Proses")){
            if(et_keterangan.getText().toString().trim().length()==0){
                et_keterangan.setError("Keterangan harus diisi !");
                et_keterangan.requestFocus();
            }else {
                prosesPerbaharuiKTP(date);
            }
        }else{
            prosesPerbaharuiKTP("");
        }
    }

    public void prosesPerbaharuiKTP(String date) {
        LoadingDialog loading = new LoadingDialog(this);
        loading.startLoadingDialog();

        String tanggalPerkiraanSelesai;
        if(spStatusPengajuan.getSelectedItem().toString().equals("Sedang di Proses")){
            tanggalPerkiraanSelesai = tvTanggal.getText().toString().trim();
        }else{
            tanggalPerkiraanSelesai = "";
        }
        String txtStatusPengajuan = spStatusPengajuan.getSelectedItem().toString();
        String keterangan = et_keterangan.getText().toString().trim();

        APIPegawaiKTP apiPengajuan = RetroServer.konekRetrofit().create(APIPegawaiKTP.class);
        Call<ResponseSingleDataModelKTP> apiPengajuanKTP = apiPengajuan.apiUpdatePengajuan(idPengajuanKTP, txtStatusPengajuan, keterangan, tanggalPerkiraanSelesai, date);

        apiPengajuanKTP.enqueue(new Callback<ResponseSingleDataModelKTP>() {
            @Override
            public void onResponse(Call<ResponseSingleDataModelKTP> call, Response<ResponseSingleDataModelKTP> response) {
                int code = response.body().getCode();
                String message = response.body().getMessage();

                if(code == 1){
                    alertDialogSuccess(PegawaiEditKTPActivity.this, "Berhasil", message);
                }else{
                    alertDialogDanger(PegawaiEditKTPActivity.this, "Gagal", message);
                }
                loading.dismissLoading();
            }

            @Override
            public void onFailure(Call<ResponseSingleDataModelKTP> call, Throwable t) {
                loading.dismissLoading();
                Toast.makeText(PegawaiEditKTPActivity.this, "Error Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setStatus();
    }

    public void setStatus(){
        LoadingDialog loading2 = new LoadingDialog(this);
        loading2.startLoadingDialog();

        APIPengajuanKTP apiPengajuanKTP = RetroServer.konekRetrofit().create(APIPengajuanKTP.class);
        Call<ResponseSingleDataModelKTP> apiDetailPengajuan = apiPengajuanKTP.apiDetailPengajuan(idPengajuanKTP);

        apiDetailPengajuan.enqueue(new Callback<ResponseSingleDataModelKTP>() {
            @Override
            public void onResponse(Call<ResponseSingleDataModelKTP> call, Response<ResponseSingleDataModelKTP> response) {
                int code = response.body().getCode();
                String message = response.body().getMessage();
                PengajuanKTP data = response.body().getData();

                String statusPengajuan = data.getStatus_pengajuan();
                if (statusPengajuan.equals("Menunggu Konfirmasi")) {
                    spStatusPengajuan.setSelection(1);
                }else if(statusPengajuan.equals("Sedang di Proses")) {
                    spStatusPengajuan.setSelection(2);
                    lnTanggalPerkiraanSelesai.setVisibility(View.VISIBLE);
                    tvTanggal.setText(data.getPerkiraan_selesai());
                }else if(statusPengajuan.equals("Selesai di Proses")) {
                    spStatusPengajuan.setSelection(3);
                }else if(statusPengajuan.equals("Pengajuan Gagal")) {
                    spStatusPengajuan.setSelection(4);
                }
                loading2.dismissLoading();
            }

            @Override
            public void onFailure(Call<ResponseSingleDataModelKTP> call, Throwable t) {
                Toast.makeText(PegawaiEditKTPActivity.this, "Error Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
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