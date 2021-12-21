package id.ppmkelompok10.pendudukku.ModulAuth;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import id.ppmkelompok10.pendudukku.API.APIAuth.APILogin;
import id.ppmkelompok10.pendudukku.API.RetroServer;
import id.ppmkelompok10.pendudukku.GetStartedActivity;
import id.ppmkelompok10.pendudukku.Helper.SessionManagement;
import id.ppmkelompok10.pendudukku.MainPegawaiActivity;
import id.ppmkelompok10.pendudukku.MainPendudukActivity;
import id.ppmkelompok10.pendudukku.Model.ModelAuth.DataModelLoginAuth;
import id.ppmkelompok10.pendudukku.Model.ModelAuth.ResponseModelAuth;
import id.ppmkelompok10.pendudukku.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPegawaiActivity extends AppCompatActivity {
    private EditText etNIK, etPassword;
    private ImageButton imbShowHidePassword, btnBack;
    private TextView tvMasukPenduduk;
    private Button btnMasuk;
    private SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pegawai);

        //Merubah Status Bar Menjadi Putih / Mode Light
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //Buka Session
        session = new SessionManagement(this);

        //Deklarasi Input NIK dan Password
        etNIK = findViewById(R.id.et_nik);
        etPassword = findViewById(R.id.et_password);

        //Deklarasi Tombol Kembali
        btnBack = findViewById(R.id.btn_back);

        //Deklarasi Tombol Masuk Sebagai Penduduk
        tvMasukPenduduk = findViewById(R.id.tv_masuk_penduduk);

        //Deklarasi Tombol Masuk
        btnMasuk = findViewById(R.id.btn_masuk);

        //Auto Focus ke Input NIK
        etNIK.requestFocus();

        //Tombol Hide dan Show Password
        imbShowHidePassword = findViewById(R.id.imb_show_hide_password);
        imbShowHidePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    imbShowHidePassword.setImageResource(R.drawable.ic_eye_close);
                    etPassword.setInputType( InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }else {
                    imbShowHidePassword.setImageResource(R.drawable.ic_eye);
                    etPassword.setInputType( InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD );
                }
                etPassword.setSelection(etPassword.getText().length());
                etPassword.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.poppins_medium));
            }
        });

        //Tombol Kembali
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Tombol Masuk Sebagai Penduduk
        tvMasukPenduduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginPendudukActivity = new Intent(LoginPegawaiActivity.this, LoginPendudukActivity.class);
                startActivity(loginPendudukActivity);
                finish();
            }
        });

        //Tombol Masuk / Login Pegawai
        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Script Login Pegawai
                String nik = etNIK.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if(nik.equals("")){
                    etNIK.setError("NIK harus diisi !");
                    etNIK.requestFocus();
                }else if(password.equals("")){
                    etPassword.setError("Password harus diisi !");
                    etPassword.requestFocus();
                }else{
                    loginPenduduk(nik, password);
                }
            }
        });
    }

    public void loginPenduduk(String nik, String password){
        APILogin apiLogin = RetroServer.konekRetrofit().create(APILogin.class);
        Call<ResponseModelAuth> loginPenduduk = apiLogin.apiLoginPegawai(nik, password);


        loginPenduduk.enqueue(new Callback<ResponseModelAuth>() {
            @Override
            public void onResponse(Call<ResponseModelAuth> call, Response<ResponseModelAuth> response) {
                int code = response.body().getCode();
                String message = response.body().getMessage();

                if(code == 0){
                    alertDialogDanger(LoginPegawaiActivity.this, "Gagal Masuk", message);
                }else{
                    DataModelLoginAuth dataLogin = response.body().getData();
                    session.saveSession(String.valueOf(dataLogin.getNik()), dataLogin.getNama_lengkap(), dataLogin.getStatus_akses(), "Pegawai");
                    alertDialogSuccess(LoginPegawaiActivity.this, "Berhasil Masuk", message);
                }
            }

            @Override
            public void onFailure(Call<ResponseModelAuth> call, Throwable t) {
                Toast.makeText(LoginPegawaiActivity.this, "Error Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void alertDialogDanger(Context context, String textTitle, String textMessage){
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
        buttonPrimary.setText("Oke, Masuk");

        builderDialog.setView(layoutView);
        alertDialog = builderDialog.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationZoom;
        alertDialog.show();

        buttonPrimary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

                Intent mainPegawaiActivity = new Intent(LoginPegawaiActivity.this, MainPegawaiActivity.class);
                startActivity(mainPegawaiActivity);
                GetStartedActivity.getStarterActivity.finish();
                finish();
            }
        });
    }
}