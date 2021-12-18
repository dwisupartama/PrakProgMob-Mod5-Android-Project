package id.ppmkelompok10.pendudukku.ModulAuth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

import id.ppmkelompok10.pendudukku.R;

public class PengaturanProfilActivity extends AppCompatActivity {
    private ImageButton imbTanggal, btnBack;
    private TextView tvTanggalLahir;
    private Spinner spAgama, spStatusPerkawinan;
    private Button btnUbahPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan_profil);

        //Merubah Status Bar Menjadi Putih / Mode Light
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //Deklarasi Spinner
        spAgama = findViewById(R.id.sp_agama);
        spStatusPerkawinan = findViewById(R.id.sp_status_perkawinan);

        //Deklarasi Text View Tanggal Lahir
        tvTanggalLahir = findViewById(R.id.tv_tanggal_lahir);

        //Deklarasi Tombol Kembali
        btnBack = findViewById(R.id.btn_back);

        //Deklarasi Tombol Ubah Password
        btnUbahPassword = findViewById(R.id.btn_ubah_password);

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
                        PengaturanProfilActivity.this, R.style.datePickerDialogPrimary, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        tvTanggalLahir.setText(""+dayOfMonth+"/"+month+"/"+year);
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
    }
}