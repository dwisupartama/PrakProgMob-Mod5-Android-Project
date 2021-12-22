package id.ppmkelompok10.pendudukku.ModulAuth;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
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
import id.ppmkelompok10.pendudukku.API.APIAuth.APIPengaturanProfil;
import id.ppmkelompok10.pendudukku.API.RetroServer;
import id.ppmkelompok10.pendudukku.Helper.LoadingDialog;
import id.ppmkelompok10.pendudukku.Helper.SessionManagement;
import id.ppmkelompok10.pendudukku.Model.ModelAuth.ResponseModelAuth;
import id.ppmkelompok10.pendudukku.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahPasswordActivity extends AppCompatActivity {
    private EditText etPasswordLama, etPasswordBaru, etUlangPasswordBaru;
    private ImageButton imbShowHidePasswordLama, imbShowHidePasswordBaru, imbShowHidePasswordUlang, btnBack;
    private Button btnUbahPassword;

    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_password);

        //Merubah Status Bar Menjadi Putih / Mode Light
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        session = new SessionManagement(this);

        //Deklarasi Tombol Kembali dan Tombol Ubah Pasword
        btnBack = findViewById(R.id.btn_back);
        btnUbahPassword = findViewById(R.id.btn_ubah_password);

        //Deklarasi Hide Password Inputan
        etPasswordLama = findViewById(R.id.et_password_lama);
        etPasswordBaru = findViewById(R.id.et_password_baru);
        etUlangPasswordBaru = findViewById(R.id.et_password_ulang);

        imbShowHidePasswordLama = findViewById(R.id.imb_show_hide_password_lama);
        imbShowHidePasswordBaru = findViewById(R.id.imb_show_hide_password_baru);
        imbShowHidePasswordUlang = findViewById(R.id.imb_show_hide_password_ulang);

        //Show Hide Password Lama
        imbShowHidePasswordLama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etPasswordLama.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    imbShowHidePasswordLama.setImageResource(R.drawable.ic_eye_close);
                    etPasswordLama.setInputType( InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }else {
                    imbShowHidePasswordLama.setImageResource(R.drawable.ic_eye);
                    etPasswordLama.setInputType( InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD );
                }
                etPasswordLama.setSelection(etPasswordLama.getText().length());
                etPasswordLama.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.poppins_medium));
            }
        });

        //Show Hide Password Baru
        imbShowHidePasswordBaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etPasswordBaru.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    imbShowHidePasswordBaru.setImageResource(R.drawable.ic_eye_close);
                    etPasswordBaru.setInputType( InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }else {
                    imbShowHidePasswordBaru.setImageResource(R.drawable.ic_eye);
                    etPasswordBaru.setInputType( InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD );
                }
                etPasswordBaru.setSelection(etPasswordBaru.getText().length());
                etPasswordBaru.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.poppins_medium));
            }
        });

        //Show Hide Password Ulang
        imbShowHidePasswordUlang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etUlangPasswordBaru.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    imbShowHidePasswordUlang.setImageResource(R.drawable.ic_eye_close);
                    etUlangPasswordBaru.setInputType( InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }else {
                    imbShowHidePasswordUlang.setImageResource(R.drawable.ic_eye);
                    etUlangPasswordBaru.setInputType( InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD );
                }
                etUlangPasswordBaru.setSelection(etUlangPasswordBaru.getText().length());
                etUlangPasswordBaru.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.poppins_medium));
            }
        });

        //Tombol Kembali
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnUbahPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passwordLama = etPasswordLama.getText().toString().trim();
                String passwordBaru = etPasswordBaru.getText().toString().trim();
                String retypePassword = etUlangPasswordBaru.getText().toString().trim();
                //Jalankan Ubah Password
                validasiUbahPassword(passwordLama, passwordBaru, retypePassword);
            }
        });


    }

    public void validasiUbahPassword(String passwordLama,String passwordBaru,String retypePassword) {
        if(passwordLama.length()==0){
            etPasswordLama.setError("Password Lama harus diisi!");
            etPasswordLama.requestFocus();
        }else if(passwordBaru.length()==0){
            etPasswordBaru.setError("Password Baru harus diisi!");
            etPasswordBaru.requestFocus();
        }else if(retypePassword.length()==0){
            etUlangPasswordBaru.setError("Ulang Password Baru harus diisi!");
            etUlangPasswordBaru.requestFocus();
        }else if(!passwordBaru.equals(retypePassword)){
            etUlangPasswordBaru.setError("Ulang Password tidak valid!");
            etUlangPasswordBaru.requestFocus();
        }else{
            prosesUbahPassword(passwordLama, passwordBaru);
        }
    }

    public void prosesUbahPassword(String passwordLama, String passwordBaru) {
        LoadingDialog loading = new LoadingDialog(this);
        loading.startLoadingDialog();

        String nik = session.getNIK();

        APIPengaturanProfil apiPengaturanProfil = RetroServer.konekRetrofit().create(APIPengaturanProfil.class);
        Call<ResponseModelAuth> ubahPassword = apiPengaturanProfil.apiUbahPassword(nik, passwordLama, passwordBaru);

        ubahPassword.enqueue(new Callback<ResponseModelAuth>() {
            @Override
            public void onResponse(Call<ResponseModelAuth> call, Response<ResponseModelAuth> response) {
                int code = response.body().getCode();
                String message = response.body().getMessage();

                loading.dismissLoading();

                if(code == 0){
                    alertDialogDanger(UbahPasswordActivity.this, "Gagal", message);
                }else{
                    alertDialogSuccess(UbahPasswordActivity.this, "Berhasil", message);
                }
            }

            @Override
            public void onFailure(Call<ResponseModelAuth> call, Throwable t) {
                Toast.makeText(UbahPasswordActivity.this, "Error Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                loading.dismissLoading();
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