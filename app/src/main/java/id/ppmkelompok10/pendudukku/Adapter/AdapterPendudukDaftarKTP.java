package id.ppmkelompok10.pendudukku.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.ppmkelompok10.pendudukku.API.APIAuth.APIPengaturanProfil;
import id.ppmkelompok10.pendudukku.API.APIKTP.APIPengajuanKTP;
import id.ppmkelompok10.pendudukku.API.APIPenduduk.APIPenduduk;
import id.ppmkelompok10.pendudukku.API.RetroServer;
import id.ppmkelompok10.pendudukku.Helper.LoadingDialog;
import id.ppmkelompok10.pendudukku.Model.ModelAuth.ResponseModelAuth;
import id.ppmkelompok10.pendudukku.Model.ModelKTP.PengajuanKTP;
import id.ppmkelompok10.pendudukku.Model.ModelKTP.ResponseSingleDataModelKTP;
import id.ppmkelompok10.pendudukku.Model.ModelPenduduk.ResponseSingleDataModel;
import id.ppmkelompok10.pendudukku.ModulKTP.DetailKTPActivity;
import id.ppmkelompok10.pendudukku.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterPendudukDaftarKTP extends RecyclerView.Adapter<AdapterPendudukDaftarKTP.Holder> {
    private Context context;
    private ArrayList<PengajuanKTP> pengajuanAll;

    public AdapterPendudukDaftarKTP(Context context, ArrayList<PengajuanKTP> pengajuanAll) {
        this.context = context;
        this.pengajuanAll = pengajuanAll;
    }

    @NonNull
    @Override
    public AdapterPendudukDaftarKTP.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_penduduk_daftar, parent, false);
        return new AdapterPendudukDaftarKTP.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPendudukDaftarKTP.Holder holder, @SuppressLint("RecyclerView") int position) {
        String jenisPengajuan = pengajuanAll.get(position).getJenis_pengajuan();
        String tanggalPengajuan = pengajuanAll.get(position).getTanggal_pengajuan().toString();
        String status = pengajuanAll.get(position).getStatus_pengajuan();

        holder.tvJenisPengajuan.setText(jenisPengajuan);
        holder.tvTanggal.setText(tanggalPengajuan);
        holder.tvStatus.setText(status);

        if(status.equals("Menunggu Konfirmasi")){
            holder.lnBgStatus.setBackground(ContextCompat.getDrawable(context,R.drawable.bg_status_blue));
            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.BlueColorPrimary));
        }else if(status.equals("Sedang di Proses")){
            holder.lnBgStatus.setBackground(ContextCompat.getDrawable(context,R.drawable.bg_status_purple));
            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.PrimaryColorVariant));
        }else if(status.equals("Selesai di Proses")){
            holder.lnBgStatus.setBackground(ContextCompat.getDrawable(context,R.drawable.bg_status_green));
            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.GreenColorPrimary));
        }else if(status.equals("Pengajuan Gagal")){
            holder.lnBgStatus.setBackground(ContextCompat.getDrawable(context,R.drawable.bg_status_red));
            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.RedColorPrimary));
        }

        if(status.equals("Menunggu Konfirmasi")){
            holder.imbDelete.setVisibility(View.VISIBLE);
        }else{
            holder.imbDelete.setVisibility(View.GONE);
        }

        //Set Margin List Terakhir
        if (position == (getItemCount()-1)){
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.cvList.getLayoutParams();
            params.bottomMargin = 60;
        }

        holder.imbDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailPengajuanKTP = new Intent(v.getContext(), DetailKTPActivity.class);
                detailPengajuanKTP.putExtra("id_pengajuan", pengajuanAll.get(position).getId());
                context.startActivity(detailPengajuanKTP);
            }
        });

        holder.imbDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builderDialog;
                AlertDialog alertDialog;

                builderDialog = new AlertDialog.Builder(v.getContext());
                View layoutView = LayoutInflater.from(context).inflate(R.layout.dialog_confirm_danger, null);

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
                        alertDialog.dismiss();
                        LoadingDialog loading2 = new LoadingDialog((Activity) v.getContext());
                        loading2.startLoadingDialog();

                        APIPengajuanKTP apiPengajuan = RetroServer.konekRetrofit().create(APIPengajuanKTP.class);
                        Call<ResponseSingleDataModelKTP> apiDeletePengajuan = apiPengajuan.apiDeletePengajuan(""+pengajuanAll.get(position).getId());

                        apiDeletePengajuan.enqueue(new Callback<ResponseSingleDataModelKTP>() {
                            @Override
                            public void onResponse(Call<ResponseSingleDataModelKTP> call, Response<ResponseSingleDataModelKTP> response) {
                                pengajuanAll.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeRemoved(position, pengajuanAll.size());
                                loading2.dismissLoading();
                            }

                            @Override
                            public void onFailure(Call<ResponseSingleDataModelKTP> call, Throwable t) {
                                loading2.dismissLoading();
                                Toast.makeText(v.getContext(), "Error Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                buttonKembali.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return pengajuanAll.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView tvJenisPengajuan, tvTanggal, tvStatus;
        LinearLayout lnBgStatus;
        ImageButton imbDetail, imbDelete;
        CardView cvList;

        public Holder(@NonNull View itemView) {
            super(itemView);

            tvJenisPengajuan = itemView.findViewById(R.id.tv_jenis_pengajuan);
            tvTanggal = itemView.findViewById(R.id.tv_tanggal);
            tvStatus = itemView.findViewById(R.id.tv_status);

            lnBgStatus = itemView.findViewById(R.id.ln_bg_status);

            imbDetail = itemView.findViewById(R.id.imb_detail);
            imbDelete = itemView.findViewById(R.id.imb_delete);

            cvList = itemView.findViewById(R.id.cv_list);
        }
    }
}
