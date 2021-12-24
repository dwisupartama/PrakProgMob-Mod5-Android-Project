package id.ppmkelompok10.pendudukku.ModulVaksin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import id.ppmkelompok10.pendudukku.API.APIVaksin.APIPegawaiVaksin;
import id.ppmkelompok10.pendudukku.API.APIVaksin.APIPendudukVaksin;
import id.ppmkelompok10.pendudukku.API.RetroServer;
import id.ppmkelompok10.pendudukku.Helper.SessionManagement;
import id.ppmkelompok10.pendudukku.Model.ModelVaksin.ResponseSingleModelDataVaksin;
import id.ppmkelompok10.pendudukku.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendudukTambahVaksinActivity extends AppCompatActivity {
    private Spinner spTahapVaksin, spDaerahVaksin;
    private ImageButton btnBack;
    private Button btnTambah;
    private EditText etRiwayatPenyakit;
    private CheckBox cbCheckData;
    SessionManagement session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penduduk_tambah_vaksin);

        session = new SessionManagement(this);

        //Merubah Status Bar Menjadi Putih / Mode Light
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //Deklarasi Spinner
        spTahapVaksin = findViewById(R.id.sp_tahap_vaksin);
        spDaerahVaksin = findViewById(R.id.sp_daerah_vaksin);

        //Deklarasi Tombol Kembali
        btnBack = findViewById(R.id.btn_back);

        //Deklarasi Tombol Ajukan Pendaftaran Vaksin
        btnTambah = findViewById(R.id.btn_masuk);

        //Deklarasi Edit Text
        etRiwayatPenyakit = findViewById(R.id.et_riwayat_penyakit);

        //Deklarasi Checkbox
        cbCheckData = findViewById(R.id.cb_check_data);

        //Konfigurasi Spinner Status Tahap Vaksin
        ArrayAdapter<String> adapterTahapVaksin = new ArrayAdapter<>(
                this,
                R.layout.custom_spinner,
                getResources().getStringArray(R.array.list_tahap_vaksin)
        );
        adapterTahapVaksin.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spTahapVaksin.setAdapter(adapterTahapVaksin);

        //Konfigurasi Spinner Status Daerah Vaksin
        ArrayAdapter<String> adapterDaerahVaksin = new ArrayAdapter<>(
                this,
                R.layout.custom_spinner,
                getResources().getStringArray(R.array.list_daerah_vaksin)
        );
        adapterDaerahVaksin.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spDaerahVaksin.setAdapter(adapterDaerahVaksin);

        //Tombol Kembali
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Tombol Tambah
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {validasiData();}
        });
    }

    @SuppressLint("ResourceType")
    public void validasiData() {
        if(spTahapVaksin.getSelectedItem().toString().equals("Pilih Tahap Vaksin")){
            TextView textTahapVaksin = (TextView) spTahapVaksin.getSelectedView();
            textTahapVaksin.setError("Tahap vaksin harus dipilih !");
            spTahapVaksin.requestFocus();
        }else if(spDaerahVaksin.getSelectedItem().toString().equals("Pilih Lokasi Daerah Vaksin")){
            TextView textDaerahVaksin = (TextView) spDaerahVaksin.getSelectedView();
            textDaerahVaksin.setError("Daerah Vaksin harus dipilih !");
            spDaerahVaksin.requestFocus();
        }else if(!cbCheckData.isChecked()){
            cbCheckData.setError("Sebelum melanjutkan, harus dicentang");
            cbCheckData.requestFocus();
        }else{
            prosesTambahVaksin();
        }
    }

    public void prosesTambahVaksin() {
        String dataNik = session.getNIK();
        String uTahapVaksin = spTahapVaksin.getSelectedItem().toString();
        String uDaerahVaksin = spDaerahVaksin.getSelectedItem().toString();
        String uRiwayatPenyakit = etRiwayatPenyakit.getText().toString().trim();

        APIPendudukVaksin apiPendudukVaksin = RetroServer.konekRetrofit().create(APIPendudukVaksin.class);
        Call<ResponseSingleModelDataVaksin> apiTambahVaksin = apiPendudukVaksin.apiTambahVaksin(
                dataNik,
                uTahapVaksin,
                uDaerahVaksin,
                uRiwayatPenyakit
        );

        apiTambahVaksin.enqueue(new Callback<ResponseSingleModelDataVaksin>() {
            @Override
            public void onResponse(Call<ResponseSingleModelDataVaksin> call, Response<ResponseSingleModelDataVaksin> response) {
                int code = response.body().getCode();
                String message = response.body().getMessage();

                if(code == 0){
                    alertDialogDanger(PendudukTambahVaksinActivity.this, "Gagal", message);
                }else{
                    alertDialogSuccess(PendudukTambahVaksinActivity.this, "Berhasil", message);
                }
            }

            @Override
            public void onFailure(Call<ResponseSingleModelDataVaksin> call, Throwable t) {
                Toast.makeText(PendudukTambahVaksinActivity.this, "Error Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
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
