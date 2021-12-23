package id.ppmkelompok10.pendudukku.ModulKTP;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import id.ppmkelompok10.pendudukku.API.APIKTP.APIPegawaiKTP;
import id.ppmkelompok10.pendudukku.API.RetroServer;
import id.ppmkelompok10.pendudukku.Adapter.AdapterPegawaiDaftarKTP;
import id.ppmkelompok10.pendudukku.Adapter.AdapterPendudukDaftarKTP;
import id.ppmkelompok10.pendudukku.Helper.KeyboardUtils;
import id.ppmkelompok10.pendudukku.Helper.LoadingDialog;
import id.ppmkelompok10.pendudukku.Model.ModelKTP.PengajuanKTP;
import id.ppmkelompok10.pendudukku.Model.ModelKTP.PengajuanKTP_Data;
import id.ppmkelompok10.pendudukku.Model.ModelKTP.ResponseModelKTP;
import id.ppmkelompok10.pendudukku.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PegawaiDaftarKTPActivity extends AppCompatActivity implements AdapterPendudukDaftarKTP.lihatDataPengajuan,AdapterPegawaiDaftarKTP.EditPengajuan {
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
        ambilDataAPI();

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

    public void tampilDaftar(ArrayList<PengajuanKTP> pengajuanKTP){
        pengajuanlist = pengajuanKTP;
        AdapterPegawaiDaftarKTP adapterPendudukDaftarKTP = new AdapterPegawaiDaftarKTP(PegawaiDaftarKTPActivity.this,pengajuanlist,this::lihat,this::edit);
        rvDaftarKTP.setAdapter(adapterPendudukDaftarKTP);
        rvDaftarKTP.invalidate();
    }

    public void ambilDataAPI(){
        LoadingDialog loading2 = new LoadingDialog(this);
        loading2.startLoadingDialog();
        //Ambil API
        ArrayList<PengajuanKTP> pengajuanKTPS = new ArrayList<>();
        APIPegawaiKTP apiGetAllPengajuan = RetroServer.konekRetrofit().create(APIPegawaiKTP.class);
        Call<ResponseModelKTP> getpengajuan = apiGetAllPengajuan.apiGetAll();

        getpengajuan.enqueue(new Callback<ResponseModelKTP>() {
            @Override
            public void onResponse(Call<ResponseModelKTP> call, Response<ResponseModelKTP> response) {
                ArrayList<PengajuanKTP> dataPengajuan = response.body().getData();
                for (PengajuanKTP item:dataPengajuan) {
                    pengajuanKTPS.add(item);
                }
                PengajuanKTP_Data.getInstance().setPengajuanData(pengajuanKTPS);
                tampilDaftar(pengajuanKTPS);
                loading2.dismissLoading();
            }

            @Override
            public void onFailure(Call<ResponseModelKTP> call, Throwable t) {
                Log.d("Daftar", "onFailure: "+t.getMessage());
                Toast.makeText(PegawaiDaftarKTPActivity.this, "Error Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                loading2.dismissLoading();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void edit(int position) {
        Intent pegawaiEditKTPActivity = new Intent(PegawaiDaftarKTPActivity.this, PegawaiEditKTPActivity.class);
        pegawaiEditKTPActivity.putExtra("pengajuan",pengajuanlist.get(position));
        startActivity(pegawaiEditKTPActivity);
    }

    @Override
    public void lihat(int position) {
        Intent detailKTPActivity = new Intent(PegawaiDaftarKTPActivity.this, DetailKTPActivity.class);
        detailKTPActivity.putExtra("pengajuan",pengajuanlist.get(position));
        startActivity(detailKTPActivity);
    }
}