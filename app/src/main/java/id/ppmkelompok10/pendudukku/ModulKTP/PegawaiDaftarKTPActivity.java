package id.ppmkelompok10.pendudukku.ModulKTP;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import id.ppmkelompok10.pendudukku.API.APIKTP.APIPegawaiKTP;
import id.ppmkelompok10.pendudukku.API.APIKTP.APIPengajuanKTP;
import id.ppmkelompok10.pendudukku.API.RetroServer;
import id.ppmkelompok10.pendudukku.Adapter.AdapterPegawaiDaftarKTP;
import id.ppmkelompok10.pendudukku.Adapter.AdapterPendudukDaftarKTP;
import id.ppmkelompok10.pendudukku.Helper.KeyboardUtils;
import id.ppmkelompok10.pendudukku.Helper.LoadingDialog;
import id.ppmkelompok10.pendudukku.Model.ModelKTP.PengajuanKTP;
import id.ppmkelompok10.pendudukku.Model.ModelKTP.ResponseMultiDataModelKTP;
import id.ppmkelompok10.pendudukku.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PegawaiDaftarKTPActivity extends AppCompatActivity{
    private ImageButton btnBack;
    private RecyclerView rvDaftarKTP;
    private EditText etSearch;
    private TextView tvTitle, tvSubtitle;
    public ArrayList<PengajuanKTP> pengajuanlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pegawai_daftar_ktp_activity);

        //Merubah Status Bar Menjadi Putih / Mode Light
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //Deklarasi Tombol Kembali
        btnBack = findViewById(R.id.btn_back);

        //Deklarasi Input Search
        etSearch = findViewById(R.id.et_search);

        //Deklarasi Title dan Subtitle
        tvTitle = findViewById(R.id.tv_title);
        tvSubtitle = findViewById(R.id.tv_subtitle);

        //Deklarasi Recycler View
        rvDaftarKTP = findViewById(R.id.rv_daftar_ktp);

        //Pemanggilan Data Daftar
//        ambilDataAPI();
        tampilDaftar();

        //Tombol Kembali
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        KeyboardUtils.addKeyboardToggleListener(this, new KeyboardUtils.SoftKeyboardToggleListener() {
            @Override
            public void onToggleSoftKeyboard(boolean isVisible) {
                if(isVisible){
                    tvTitle.setVisibility(View.GONE);
                    tvSubtitle.setVisibility(View.GONE);
                }else{
                    tvTitle.setVisibility(View.VISIBLE);
                    tvSubtitle.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void tampilDaftar(){
        LoadingDialog loading2 = new LoadingDialog(this);
        loading2.startLoadingDialog();

        APIPegawaiKTP apiPegawaiKTP = RetroServer.konekRetrofit().create(APIPegawaiKTP.class);
        Call<ResponseMultiDataModelKTP> apiAllPengajuan = apiPegawaiKTP.apiAllPengajuan();

        apiAllPengajuan.enqueue(new Callback<ResponseMultiDataModelKTP>() {
            @Override
            public void onResponse(Call<ResponseMultiDataModelKTP> call, Response<ResponseMultiDataModelKTP> response) {
                int code = response.body().getCode();
                String message = response.body().getMessage();
                ArrayList<PengajuanKTP> data = response.body().getData();

                if(code == 1){
                    loading2.dismissLoading();
                    AdapterPegawaiDaftarKTP adapterPegawaiDaftarKTP = new AdapterPegawaiDaftarKTP(PegawaiDaftarKTPActivity.this, data);
                    rvDaftarKTP.setAdapter(adapterPegawaiDaftarKTP);
                }
            }

            @Override
            public void onFailure(Call<ResponseMultiDataModelKTP> call, Throwable t) {
                Toast.makeText(PegawaiDaftarKTPActivity.this, "Error Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                loading2.dismissLoading();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        tampilDaftar();
    }
}