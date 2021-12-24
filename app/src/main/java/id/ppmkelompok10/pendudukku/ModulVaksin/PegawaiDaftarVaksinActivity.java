package id.ppmkelompok10.pendudukku.ModulVaksin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import id.ppmkelompok10.pendudukku.API.APIVaksin.APIPegawaiVaksin;
import id.ppmkelompok10.pendudukku.API.APIVaksin.APIPendudukVaksin;
import id.ppmkelompok10.pendudukku.API.RetroServer;
import id.ppmkelompok10.pendudukku.Adapter.AdapterPegawaiDaftarVaksin;
import id.ppmkelompok10.pendudukku.Adapter.AdapterPendudukDaftarVaksin;
import id.ppmkelompok10.pendudukku.Helper.KeyboardUtils;
import id.ppmkelompok10.pendudukku.Helper.LoadingDialog;
import id.ppmkelompok10.pendudukku.Helper.SessionManagement;
import id.ppmkelompok10.pendudukku.Model.ModelVaksin.ModelVaksin;
import id.ppmkelompok10.pendudukku.Model.ModelVaksin.ResponseMultiDataModelVaksin;
import id.ppmkelompok10.pendudukku.Model.ModelVaksin.ResponseSingleModelDataVaksin;
import id.ppmkelompok10.pendudukku.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PegawaiDaftarVaksinActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private RecyclerView rvDaftarVaksin;
    private EditText etSearch;
    private TextView tvTitle, tvSubtitle;
    private SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pegawai_daftar_vaksin);

        session = new SessionManagement(this);

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
        rvDaftarVaksin = findViewById(R.id.rv_daftar_vaksin);

        //Pemanggilan Data Daftar
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

        APIPegawaiVaksin apiPegawaiVaksin = RetroServer.konekRetrofit().create(APIPegawaiVaksin.class);
        Call<ResponseMultiDataModelVaksin> apiDaftarVaksinPegawai = apiPegawaiVaksin.apiDaftarVaksinPegawai();

        apiDaftarVaksinPegawai.enqueue(new Callback<ResponseMultiDataModelVaksin>() {
            @Override
            public void onResponse(Call<ResponseMultiDataModelVaksin> call, Response<ResponseMultiDataModelVaksin> response) {
                int code = response.body().getCode();
                String message = response.body().getMessage();
                ArrayList<ModelVaksin> data = response.body().getData();

                if (code == 1){
                    AdapterPegawaiDaftarVaksin adapterPegawaiDaftarVaksin = new AdapterPegawaiDaftarVaksin(PegawaiDaftarVaksinActivity.this, data);
                    rvDaftarVaksin.setAdapter(adapterPegawaiDaftarVaksin);
                }
                loading2.dismissLoading();
            }

            @Override
            public void onFailure(Call<ResponseMultiDataModelVaksin> call, Throwable t) {
                Toast.makeText(PegawaiDaftarVaksinActivity.this, "Error Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
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