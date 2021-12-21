package id.ppmkelompok10.pendudukku;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import id.ppmkelompok10.pendudukku.Helper.SessionManagement;
import id.ppmkelompok10.pendudukku.ModulAuth.LoginPegawaiActivity;
import id.ppmkelompok10.pendudukku.ModulAuth.LoginPendudukActivity;

public class GetStartedActivity extends AppCompatActivity {

    private Button btnGetStarted;
    private TextView tvMasukPegawai;

    public static Activity getStarterActivity;

    @Override
    protected void onStart() {
        super.onStart();
        SessionManagement session = new SessionManagement(this);
        if(session.getStatusLogin()){
            if(session.getLoggedInTo().equals("Pegawai")){
                Intent mainPegawaiActivity = new Intent(this, MainPegawaiActivity.class);
                startActivity(mainPegawaiActivity);
                finish();
            }else if(session.getLoggedInTo().equals("Penduduk")){
                Intent mainPendudukActivity = new Intent(this, MainPendudukActivity.class);
                startActivity(mainPendudukActivity);
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);
        getStarterActivity = this;

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        btnGetStarted = findViewById(R.id.btn_ayo_mulai);
        tvMasukPegawai = findViewById(R.id.tv_masuk_pegawai);

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginPendudukActivity = new Intent(GetStartedActivity.this, LoginPendudukActivity.class);
                startActivity(loginPendudukActivity);
            }
        });

        tvMasukPegawai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginPegawaiActivity = new Intent(GetStartedActivity.this, LoginPegawaiActivity.class);
                startActivity(loginPegawaiActivity);
            }
        });
    }
}