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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import id.ppmkelompok10.pendudukku.API.APIKTP.APIDeletePengajuan;
import id.ppmkelompok10.pendudukku.API.APIKTP.APIUpdatePengajuan;
import id.ppmkelompok10.pendudukku.API.RetroServer;
import id.ppmkelompok10.pendudukku.GetStartedActivity;
import id.ppmkelompok10.pendudukku.Helper.LoadingDialog;
import id.ppmkelompok10.pendudukku.MainPendudukActivity;
import id.ppmkelompok10.pendudukku.Model.ModelAuth.AccountModelAuth;
import id.ppmkelompok10.pendudukku.Model.ModelKTP.PengajuanKTP;
import id.ppmkelompok10.pendudukku.Model.ModelKTP.ResponseModelKTP;
import id.ppmkelompok10.pendudukku.ModulAuth.LoginPendudukActivity;
import id.ppmkelompok10.pendudukku.ModulPenduduk.PegawaiEditPendudukActivity;
import id.ppmkelompok10.pendudukku.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PegawaiEditKTPActivity extends AppCompatActivity {
    private ImageButton imbTanggal, btnBack;
    private TextView tvTanggal;
    private Spinner spStatusPengajuan;
    private LinearLayout lnTanggalPerkiraanSelesai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pegawai_edit_ktp_activity);

        //Merubah Status Bar Menjadi Putih / Mode Light
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //Deklarasi Spinner
        spStatusPengajuan = findViewById(R.id.sp_status_pengajuan);

        //Deklarasi Text View Tanggal Lahir
        tvTanggal = findViewById(R.id.tv_tanggal);

        //Deklarasi Tombol Kembali
        btnBack = findViewById(R.id.btn_back);

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
                        tvTanggal.setText(""+dayOfMonth+"/"+month+"/"+year);
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
    }

    protected void CallUpdateAPI(PengajuanKTP pengajuanKTP){
        LoadingDialog loading2 = new LoadingDialog(this);
        loading2.startLoadingDialog();
        APIUpdatePengajuan apiUpdatePengajuan = RetroServer.konekRetrofit().create(APIUpdatePengajuan.class);
        Call<ResponseModelKTP> getpengajuan = apiUpdatePengajuan.apiUpdate(pengajuanKTP.getId(),pengajuanKTP.getStatus_pengajuan(),pengajuanKTP.getKeterangan(),pengajuanKTP.getPerkiraan_selesai(),pengajuanKTP.getTanggal_selesai());

        getpengajuan.enqueue(new Callback<ResponseModelKTP>() {
            @Override
            public void onResponse(Call<ResponseModelKTP> call, Response<ResponseModelKTP> response) {
                int code = response.body().getCode();
                String message = response.body().getMessage();

                if(code == 0){
                    loading2.dismissLoading();
                    alertDialog(PegawaiEditKTPActivity.this, "Gagal Update", message);
                }else{
                    loading2.dismissLoading();
                    alertDialog(PegawaiEditKTPActivity.this, "Berhasil Update", message);
                }
            }

            @Override
            public void onFailure(Call<ResponseModelKTP> call, Throwable t) {
                Toast.makeText(PegawaiEditKTPActivity.this, "Error Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                loading2.dismissLoading();
            }
        });
    }

    public void alertDialog(Context context, String textTitle, String textMessage){
        //Btn Delete
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
}