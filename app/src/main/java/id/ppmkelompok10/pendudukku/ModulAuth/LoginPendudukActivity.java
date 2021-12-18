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
import android.widget.Toast;

import id.ppmkelompok10.pendudukku.MainPegawaiActivity;
import id.ppmkelompok10.pendudukku.MainPendudukActivity;
import id.ppmkelompok10.pendudukku.R;

public class LoginPendudukActivity extends AppCompatActivity {
    private EditText etNIK, etPassword;
    private ImageButton imbShowHidePassword, btnBack;
    private TextView tvMasukPegawai;
    private Button btnMasuk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_penduduk);

        //Merubah Status Bar Menjadi Putih / Mode Light
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //Deklarasi Input NIK dan Password
        etNIK = findViewById(R.id.et_nik);
        etPassword = findViewById(R.id.et_password);

        //Deklarasi Tombol Kembali
        btnBack = findViewById(R.id.btn_back);

        //Deklarasi Tombol Masuk Sebagai Penduduk
        tvMasukPegawai = findViewById(R.id.tv_masuk_pegawai);

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
        tvMasukPegawai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginPegawaiActivity = new Intent(LoginPendudukActivity.this, LoginPegawaiActivity.class);
                startActivity(loginPegawaiActivity);
                finish();
            }
        });

        //Tombol Masuk / Login Pegawai
        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainPendudukActivity = new Intent(LoginPendudukActivity.this, MainPendudukActivity.class);
                startActivity(mainPendudukActivity);

                //Script Login Pegawai
            }
        });
    }
}