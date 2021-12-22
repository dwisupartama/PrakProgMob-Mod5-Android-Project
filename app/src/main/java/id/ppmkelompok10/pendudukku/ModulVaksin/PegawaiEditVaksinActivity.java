package id.ppmkelompok10.pendudukku.ModulVaksin;

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

import id.ppmkelompok10.pendudukku.ModulKTP.PegawaiEditKTPActivity;
import id.ppmkelompok10.pendudukku.R;

public class PegawaiEditVaksinActivity extends AppCompatActivity {
    private ImageButton imbTanggalPerkiraanSelesai, imbTanggalVaksin, btnBack;
    private TextView tvTanggalPerkiraanSelesai, tvTanggalVaksin;
    private Spinner spStatusPengajuan;
    private LinearLayout lnTanggalPerkiraanSelesai, lnTanggalVaksin, lnWaktuVaksin, lnTempatVaksin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pegawai_edit_vaksin);

        //Merubah Status Bar Menjadi Putih / Mode Light
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //Deklarasi Image Button
        imbTanggalPerkiraanSelesai = findViewById(R.id.imb_tanggal_perkiraan_selesai);
        imbTanggalVaksin = findViewById(R.id.imb_tanggal_vaksin);

        //Deklarasi TextView Tanggal
        tvTanggalPerkiraanSelesai = findViewById(R.id.tv_tanggal_perkiraan_selesai);
        tvTanggalVaksin = findViewById(R.id.tv_tanggal_vaksin);

        //Deklarasi Tombol Back
        btnBack = findViewById(R.id.btn_back);

        //Deklarasi Spiner
        spStatusPengajuan = findViewById(R.id.sp_status_pengajuan);

        //Deklarasi Linear Layout
        lnTanggalPerkiraanSelesai = findViewById(R.id.ln_tanggal_perkiraan_selesai);
        lnTanggalVaksin = findViewById(R.id.ln_tanggal_vaksin);
        lnWaktuVaksin = findViewById(R.id.ln_waktu_vaksin);
        lnTempatVaksin = findViewById(R.id.ln_tempat_vaksin);

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

        //Tombol Tanggal Perkiraan Selesai
        imbTanggalPerkiraanSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        PegawaiEditVaksinActivity.this, R.style.datePickerDialogPrimary, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        tvTanggalPerkiraanSelesai.setText(""+dayOfMonth+"/"+month+"/"+year);
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
                        tvTanggalVaksin.setText(""+dayOfMonth+"/"+month+"/"+year);
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
                }else if(position == 3) {
                    lnTanggalPerkiraanSelesai.setVisibility(View.GONE);
                    lnTanggalVaksin.setVisibility(View.VISIBLE);
                    lnWaktuVaksin.setVisibility(View.VISIBLE);
                    lnTempatVaksin.setVisibility(View.VISIBLE);
                }else{
                    lnTanggalPerkiraanSelesai.setVisibility(View.GONE);
                    lnTanggalVaksin.setVisibility(View.GONE);
                    lnWaktuVaksin.setVisibility(View.GONE);
                    lnTempatVaksin.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}