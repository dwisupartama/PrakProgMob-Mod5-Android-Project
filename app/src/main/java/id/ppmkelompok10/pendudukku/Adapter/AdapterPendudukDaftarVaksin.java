package id.ppmkelompok10.pendudukku.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import id.ppmkelompok10.pendudukku.ModulKTP.DetailKTPActivity;
import id.ppmkelompok10.pendudukku.ModulVaksin.DetailVaksinActivity;
import id.ppmkelompok10.pendudukku.R;

public class AdapterPendudukDaftarVaksin extends RecyclerView.Adapter<AdapterPendudukDaftarVaksin.Holder> {
    private Context context;

    public AdapterPendudukDaftarVaksin(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterPendudukDaftarVaksin.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_penduduk_daftar, parent, false);
        return new AdapterPendudukDaftarVaksin.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPendudukDaftarVaksin.Holder holder, int position) {
        String jenisPengajuan = "Vaksin Pertama";
        String tanggalPengajuan = "12 Desember 2021";
//        String status = "Menunggu Konfirmasi";
//        String status = "Sedang di Proses";
//        String status = "Selesai di Proses";
        String status = "Pengajuan Gagal";

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

        //Set Margin List Terakhir
        if (position == (getItemCount()-1)){
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.cvList.getLayoutParams();
            params.bottomMargin = 60;
        }

        holder.imbDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailVaksinActivity = new Intent(v.getContext(), DetailVaksinActivity.class);
                context.startActivity(detailVaksinActivity);
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
//                        databaseHelper.deletePenduduk(idDetail);
                        alertDialog.dismiss();
//                        finish();
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
        return 6;
        //return arrayList.size();
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