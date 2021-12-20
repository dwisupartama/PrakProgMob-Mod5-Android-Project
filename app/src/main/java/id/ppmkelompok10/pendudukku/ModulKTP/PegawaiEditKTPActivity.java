package id.ppmkelompok10.pendudukku.ModulKTP;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

import id.ppmkelompok10.pendudukku.ModulPenduduk.PegawaiEditPendudukActivity;
import id.ppmkelompok10.pendudukku.R;

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
}