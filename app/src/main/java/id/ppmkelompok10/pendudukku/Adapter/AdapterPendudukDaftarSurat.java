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

import id.ppmkelompok10.pendudukku.API.APISurat.APIPendudukSurat;
import id.ppmkelompok10.pendudukku.API.APIVaksin.APIPendudukVaksin;
import id.ppmkelompok10.pendudukku.API.RetroServer;
import id.ppmkelompok10.pendudukku.Helper.LoadingDialog;
import id.ppmkelompok10.pendudukku.Model.ModelSurat.ModelSurat;
import id.ppmkelompok10.pendudukku.Model.ModelSurat.ResponseSingleDataModelSurat;
import id.ppmkelompok10.pendudukku.Model.ModelVaksin.ResponseSingleModelDataVaksin;
import id.ppmkelompok10.pendudukku.ModulSurat.DetailSuratActivity;
import id.ppmkelompok10.pendudukku.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterPendudukDaftarSurat extends RecyclerView.Adapter<AdapterPendudukDaftarSurat.Holder> {
    private Context context;
    private ArrayList<ModelSurat> data;

    public AdapterPendudukDaftarSurat(Context context, ArrayList<ModelSurat> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public AdapterPendudukDaftarSurat.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_penduduk_daftar, parent, false);
        return new AdapterPendudukDaftarSurat.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPendudukDaftarSurat.Holder holder, @SuppressLint("RecyclerView") int position) {
        String jenisPengajuan = data.get(position).getJenis_surat();
        String tanggalPengajuan = data.get(position).getTanggal_pengajuan();
        String status = data.get(position).getStatus_pengajuan();

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
                Intent detailSuratActivity = new Intent(v.getContext(), DetailSuratActivity.class);
                detailSuratActivity.putExtra("id_surat",data.get(position).getId());
                context.startActivity(detailSuratActivity);
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

                        APIPendudukSurat apiPendudukSurat = RetroServer.konekRetrofit().create(APIPendudukSurat.class);
                        Call<ResponseSingleDataModelSurat> apiDeleteSurat = apiPendudukSurat.apiDeleteSurat(""+data.get(position).getId());

                        apiDeleteSurat.enqueue(new Callback<ResponseSingleDataModelSurat>() {
                            @Override
                            public void onResponse(Call<ResponseSingleDataModelSurat> call, Response<ResponseSingleDataModelSurat> response) {
                                data.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeRemoved(position, data.size());
                                loading2.dismissLoading();
                            }

                            @Override
                            public void onFailure(Call<ResponseSingleDataModelSurat> call, Throwable t) {
                                Toast.makeText(v.getContext(), "Error Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                                loading2.dismissLoading();
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
//        return 6;
        return data.size();
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
