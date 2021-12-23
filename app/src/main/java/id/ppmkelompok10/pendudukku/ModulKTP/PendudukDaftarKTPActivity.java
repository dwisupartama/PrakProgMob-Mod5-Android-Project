package id.ppmkelompok10.pendudukku.ModulKTP;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import id.ppmkelompok10.pendudukku.API.APIKTP.APIDeletePengajuan;
import id.ppmkelompok10.pendudukku.API.APIKTP.APIGetAllPengajuan;
import id.ppmkelompok10.pendudukku.API.RetroServer;
import id.ppmkelompok10.pendudukku.Adapter.AdapterPendudukDaftarKTP;
import id.ppmkelompok10.pendudukku.Helper.LoadingDialog;
import id.ppmkelompok10.pendudukku.Helper.SessionManagement;
import id.ppmkelompok10.pendudukku.Model.ModelKTP.PengajuanKTP;
import id.ppmkelompok10.pendudukku.Model.ModelKTP.PengajuanKTP_Data;
import id.ppmkelompok10.pendudukku.Model.ModelKTP.ResponseModelKTP;
import id.ppmkelompok10.pendudukku.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendudukDaftarKTPActivity extends AppCompatActivity implements AdapterPendudukDaftarKTP.lihatDataPengajuan, AdapterPendudukDaftarKTP.hapusPengajuan {
    private ImageButton btnBack;
    private RecyclerView rvDaftarKTP;
    private Button btnTambah;
    public ArrayList<PengajuanKTP> pengajuanlist;
    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penduduk_daftar_ktp_activity);

        //Merubah Status Bar Menjadi Putih / Mode Light
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //Deklarasi Tombol Kembali
        btnBack = findViewById(R.id.btn_back);

        //Deklatasi Tombol Tambah Pengajuan
        btnTambah = findViewById(R.id.btn_tambah);

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

        //Tombol Tambah Pengajuan
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pendudukTambahKTPActivity = new Intent(PendudukDaftarKTPActivity.this, PendudukTambahKTPActivity.class);
                tambahPengajuan.launch(pendudukTambahKTPActivity);
            }
        });
    }

    public void tampilDaftar(ArrayList<PengajuanKTP> pengajuanKTP){
        pengajuanlist = pengajuanKTP;
        AdapterPendudukDaftarKTP adapterPendudukDaftarKTP = new AdapterPendudukDaftarKTP(PendudukDaftarKTPActivity.this,pengajuanlist,this::lihat,this::hapus);
        rvDaftarKTP.setAdapter(adapterPendudukDaftarKTP);
        rvDaftarKTP.invalidate();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void lihat(int position) {
        Intent detailKTPActivity = new Intent(PendudukDaftarKTPActivity.this, DetailKTPActivity.class);
        detailKTPActivity.putExtra("pengajuan",pengajuanlist.get(position));
        startActivity(detailKTPActivity);
    }

    ActivityResultLauncher<Intent> tambahPengajuan = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    ambilDataAPI();
                }
            });

    public void ambilDataAPI(){
        LoadingDialog loading2 = new LoadingDialog(this);
        loading2.startLoadingDialog();
        //Ambil API
        ArrayList<PengajuanKTP> pengajuanKTPS = new ArrayList<>();
        session = new SessionManagement(this);
        String nik = session.getNIK();
        APIGetAllPengajuan apiGetAllPengajuan = RetroServer.konekRetrofit().create(APIGetAllPengajuan.class);
        Call<ResponseModelKTP> getpengajuan = apiGetAllPengajuan.apiAmbilPengajuan(nik);

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
                Toast.makeText(PendudukDaftarKTPActivity.this, "Error Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                loading2.dismissLoading();
            }
        });
    }

    @Override
    public void hapus(int position) {
        AlertDialog.Builder builderDialog;
        AlertDialog alertDialog;

        builderDialog = new AlertDialog.Builder(PendudukDaftarKTPActivity.this);
        View layoutView = LayoutInflater.from(PendudukDaftarKTPActivity.this).inflate(R.layout.dialog_confirm_danger, null);

        Button buttonHapus = layoutView.findViewById(R.id.btn_primary);
        Button buttonKembali = layoutView.findViewById(R.id.btn_secondary);

        TextView title = layoutView.findViewById(R.id.title);
        TextView subtitle = layoutView.findViewById(R.id.subtitle);

        title.setText("Hapus Data");
        subtitle.setText("Apakah anda yakin ?");
        buttonHapus.setText("Ya, Hapus");
        buttonKembali.setText("Kembali");

        builderDialog.setView(layoutView);
        alertDialog = builderDialog.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationZoom;
        alertDialog.show();

        buttonHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                        databaseHelper.deletePenduduk(idDetail);
                CallHapusAPI(pengajuanlist.get(position).getId());
                alertDialog.dismiss();
            }
        });

        buttonKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    protected void CallHapusAPI(Long id){
        LoadingDialog loading2 = new LoadingDialog(this);
        loading2.startLoadingDialog();
        APIDeletePengajuan apiDeletePengajuan = RetroServer.konekRetrofit().create(APIDeletePengajuan.class);
        Call<ResponseModelKTP> getpengajuan = apiDeletePengajuan.apiDelete(id);

        getpengajuan.enqueue(new Callback<ResponseModelKTP>() {
            @Override
            public void onResponse(Call<ResponseModelKTP> call, Response<ResponseModelKTP> response) {
                int code = response.body().getCode();
                String message = response.body().getMessage();
                loading2.dismissLoading();
                ambilDataAPI();
            }

            @Override
            public void onFailure(Call<ResponseModelKTP> call, Throwable t) {
                Toast.makeText(PendudukDaftarKTPActivity.this, "Error Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                loading2.dismissLoading();
            }
        });

    }
}