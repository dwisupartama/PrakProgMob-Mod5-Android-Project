package id.ppmkelompok10.pendudukku.ModulAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import id.ppmkelompok10.pendudukku.R;

public class UbahPasswordActivity extends AppCompatActivity {
    private EditText etPasswordLama, etPasswordBaru, etUlangPasswordBaru;
    private ImageButton imbShowHidePasswordLama, imbShowHidePasswordBaru, imbShowHidePasswordUlang, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_password);

        //Merubah Status Bar Menjadi Putih / Mode Light
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //Deklarasi Tombol Kembali
        btnBack = findViewById(R.id.btn_back);

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
    }
}