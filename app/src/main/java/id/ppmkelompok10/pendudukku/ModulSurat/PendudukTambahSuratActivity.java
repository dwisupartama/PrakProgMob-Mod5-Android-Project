package id.ppmkelompok10.pendudukku.ModulSurat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import id.ppmkelompok10.pendudukku.API.APIKTP.APIPengajuanKTP;
import id.ppmkelompok10.pendudukku.API.APISurat.APIPendudukSurat;
import id.ppmkelompok10.pendudukku.API.RetroServer;
import id.ppmkelompok10.pendudukku.Helper.LoadingDialog;
import id.ppmkelompok10.pendudukku.Helper.SessionManagement;
import id.ppmkelompok10.pendudukku.Model.ModelKTP.ResponseSingleDataModelKTP;
import id.ppmkelompok10.pendudukku.Model.ModelSurat.ResponseMultiDataModelSurat;
import id.ppmkelompok10.pendudukku.Model.ModelSurat.ResponseSingleDataModelSurat;
import id.ppmkelompok10.pendudukku.ModulKTP.PendudukTambahKTPActivity;
import id.ppmkelompok10.pendudukku.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendudukTambahSuratActivity extends AppCompatActivity {
    private EditText etJenisSurat, etDeskripsiSurat;
    private ImageButton btnBack;
    private Button btnTambah;
    private CheckBox checkData;
    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penduduk_tambah_surat);

        //Merubah Status Bar Menjadi Putih / Mode Light
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        session = new SessionManagement(this);

        etJenisSurat = findViewById(R.id.et_jenis_surat);
        etDeskripsiSurat = findViewById(R.id.et_deskripsi_surat);

        //Deklarasi Tombol Kembali
        btnBack = findViewById(R.id.btn_back);

        //Deklarasi Tombol Ajukan Pendaftaran Vaksin
        btnTambah = findViewById(R.id.btn_tambah);

        checkData = findViewById(R.id.cb_check_data);

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

    public void validasiData() {
        if(etJenisSurat.getText().toString().trim().length()==0){
            etJenisSurat.setError("Jenis Surat harus diisi!");
            etJenisSurat.requestFocus();
        }else if(etDeskripsiSurat.getText().toString().trim().length()==0){
            etDeskripsiSurat.setError("Deskripsi Surat harus diisi!");
            etDeskripsiSurat.requestFocus();
        }else if(!checkData.isChecked()){
            checkData.setError("Sebelum melanjutkan, harus dicentang");
            checkData.requestFocus();
        }else{
            prosesTambahSurat();
        }
    }

    public void prosesTambahSurat() {
        LoadingDialog loading = new LoadingDialog(this);
        loading.startLoadingDialog();

        String txtjenisSurat = etJenisSurat.getText().toString();
        String txtDeskripsiSurat = etDeskripsiSurat.getText().toString();

        APIPendudukSurat apiPendudukSurat = RetroServer.konekRetrofit().create(APIPendudukSurat.class);
        Call<ResponseSingleDataModelSurat> apiDaftarSurat = apiPendudukSurat.apiTambahSurat(session.getNIK(), txtjenisSurat, txtDeskripsiSurat);

        apiDaftarSurat.enqueue(new Callback<ResponseSingleDataModelSurat>() {
            @Override
            public void onResponse(Call<ResponseSingleDataModelSurat> call, Response<ResponseSingleDataModelSurat> response) {
                int code = response.body().getCode();
                String message = response.body().getMessage();

                if(code == 1){
                    alertDialogSuccess(PendudukTambahSuratActivity.this, "Berhasil", message);
                }else{
                    alertDialogDanger(PendudukTambahSuratActivity.this, "Gagal", message);
                }
            }

            @Override
            public void onFailure(Call<ResponseSingleDataModelSurat> call, Throwable t) {

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