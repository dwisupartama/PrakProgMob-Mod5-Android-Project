package id.ppmkelompok10.pendudukku.ModulAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import id.ppmkelompok10.pendudukku.GetStartedActivity;
import id.ppmkelompok10.pendudukku.MainPegawaiActivity;
import id.ppmkelompok10.pendudukku.R;

public class LoginPegawaiActivity extends AppCompatActivity {
    private EditText etNIK, etPassword;
    private ImageButton imbShowHidePassword, btnBack;
    private TextView tvMasukPenduduk;
    private Button btnMasuk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pegawai);

        //Merubah Status Bar Menjadi Putih / Mode Light
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

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
                Intent mainPegawaiActivity = new Intent(LoginPegawaiActivity.this, MainPegawaiActivity.class);
                startActivity(mainPegawaiActivity);

                //Script Login Pegawai
            }
        });
    }
}