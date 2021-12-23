package id.ppmkelompok10.pendudukku.ModulPenduduk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import id.ppmkelompok10.pendudukku.API.APIAuth.APIPengaturanProfil;
import id.ppmkelompok10.pendudukku.API.APIPenduduk.APIPenduduk;
import id.ppmkelompok10.pendudukku.API.RetroServer;
import id.ppmkelompok10.pendudukku.Adapter.AdapterPegawaiDaftarPenduduk;
import id.ppmkelompok10.pendudukku.Helper.KeyboardUtils;
import id.ppmkelompok10.pendudukku.Helper.LoadingDialog;
import id.ppmkelompok10.pendudukku.Model.ModelAuth.ResponseModelAuth;
import id.ppmkelompok10.pendudukku.Model.ModelPenduduk.ModelDataPenduduk;
import id.ppmkelompok10.pendudukku.Model.ModelPenduduk.ResponseMultiDataModel;
import id.ppmkelompok10.pendudukku.ModulAuth.PengaturanProfilActivity;
import id.ppmkelompok10.pendudukku.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PegawaiDaftarPendudukActivity extends AppCompatActivity{
    private ImageButton btnBack;
    private RecyclerView rvDaftarPenduduk;
    private Button btnTambah;
    private EditText etSearch;
    private TextView tvTitle, tvSubtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pegawai_daftar_penduduk);

        //Merubah Status Bar Menjadi Putih / Mode Light
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //Deklarasi Tombol Kembali
        btnBack = findViewById(R.id.btn_back);

        //Deklarasi Button Tambah
        btnTambah = findViewById(R.id.btn_tambah);

        //Deklarasi Input Search
        etSearch = findViewById(R.id.et_search);

        //Deklarasi Title dan Subtitle
        tvTitle = findViewById(R.id.tv_title);
        tvSubtitle = findViewById(R.id.tv_subtitle);

        //Deklarasi Recycler View
        rvDaftarPenduduk = findViewById(R.id.rv_daftar_penduduk);

        //Pemanggilan Data Daftar
        tampilDaftar();

        etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i == KeyEvent.KEYCODE_ENTER){
                    if(etSearch.getText().toString().trim().equals("")){
                        tampilDaftar();
                    }else{
                        searchDaftar(etSearch.getText().toString().trim());
                    }
//                    Toast.makeText(PegawaiDaftarPendudukActivity.this, "Enter Press", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });


        //Tombol Kembali
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pegawaiTambahPendudukActivity = new Intent(PegawaiDaftarPendudukActivity.this, PegawaiTambahPendudukActivity.class);
                startActivity(pegawaiTambahPendudukActivity);
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

    public void searchDaftar(String key){
        LoadingDialog loading2 = new LoadingDialog(this);
        loading2.startLoadingDialog();
        APIPenduduk apiPenduduk = RetroServer.konekRetrofit().create(APIPenduduk.class);
        Call<ResponseMultiDataModel> apiSearchPenduduk = apiPenduduk.searchPenduduk(key);

        apiSearchPenduduk.enqueue(new Callback<ResponseMultiDataModel>() {
            @Override
            public void onResponse(Call<ResponseMultiDataModel> call, Response<ResponseMultiDataModel> response) {
                int code = response.body().getCode();
                String message = response.body().getMessage();
                ArrayList<ModelDataPenduduk> data = response.body().getData();
                if (code==1){
                    loading2.dismissLoading();
                    AdapterPegawaiDaftarPenduduk adapterPegawaiDaftarPenduduk = new AdapterPegawaiDaftarPenduduk(PegawaiDaftarPendudukActivity.this,data);
                    rvDaftarPenduduk.setAdapter(adapterPegawaiDaftarPenduduk);
                }
            }

            @Override
            public void onFailure(Call<ResponseMultiDataModel> call, Throwable t) {
                Toast.makeText(PegawaiDaftarPendudukActivity.this, "Error Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                loading2.dismissLoading();
            }
        });

//        apiGetPenduduk.enqueue(new Callback<ResponseMultiDataModel>() {
//            @Override
//            public void onResponse(Call<ResponseMultiDataModel> call, Response<ResponseMultiDataModel> response) {
//                int code=response.body().getCode();
//                String message=response.body().getMessage();
//                ArrayList<ModelDataPenduduk> data = response.body().getData();
//                if (code==1){
//                    loading2.dismissLoading();
//                    AdapterPegawaiDaftarPenduduk adapterPegawaiDaftarPenduduk = new AdapterPegawaiDaftarPenduduk(PegawaiDaftarPendudukActivity.this,data);
//                    rvDaftarPenduduk.setAdapter(adapterPegawaiDaftarPenduduk);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseMultiDataModel> call, Throwable t) {
//                Toast.makeText(PegawaiDaftarPendudukActivity.this, "Error Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
//                loading2.dismissLoading();
//            }
//        });
    }

    public void tampilDaftar(){
        LoadingDialog loading2 = new LoadingDialog(this);
        loading2.startLoadingDialog();
        APIPenduduk apiPenduduk = RetroServer.konekRetrofit().create(APIPenduduk.class);
        Call<ResponseMultiDataModel> apiGetPenduduk = apiPenduduk.getPenduduk();
        apiGetPenduduk.enqueue(new Callback<ResponseMultiDataModel>() {
            @Override
            public void onResponse(Call<ResponseMultiDataModel> call, Response<ResponseMultiDataModel> response) {
                int code=response.body().getCode();
                String message=response.body().getMessage();
                ArrayList<ModelDataPenduduk> data = response.body().getData();
                if (code==1){
                    loading2.dismissLoading();
                    AdapterPegawaiDaftarPenduduk adapterPegawaiDaftarPenduduk = new AdapterPegawaiDaftarPenduduk(PegawaiDaftarPendudukActivity.this,data);
                    rvDaftarPenduduk.setAdapter(adapterPegawaiDaftarPenduduk);
                }
            }

            @Override
            public void onFailure(Call<ResponseMultiDataModel> call, Throwable t) {
                Toast.makeText(PegawaiDaftarPendudukActivity.this, "Error Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
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