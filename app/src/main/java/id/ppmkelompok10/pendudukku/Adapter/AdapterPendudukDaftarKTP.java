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

import java.util.ArrayList;

import id.ppmkelompok10.pendudukku.Model.ModelKTP.PengajuanKTP;
import id.ppmkelompok10.pendudukku.ModulKTP.DetailKTPActivity;
import id.ppmkelompok10.pendudukku.ModulKTP.PegawaiEditKTPActivity;
import id.ppmkelompok10.pendudukku.R;

public class AdapterPendudukDaftarKTP extends RecyclerView.Adapter<AdapterPendudukDaftarKTP.Holder> {
    private Context context;
    private ArrayList<PengajuanKTP> pengajuanAll;
    private lihatDataPengajuan lihatDataPengajuan;
    private hapusPengajuan hapusPengajuan;

    public AdapterPendudukDaftarKTP(Context context,ArrayList<PengajuanKTP> pengajuanAll,lihatDataPengajuan lihatDataPengajuan,hapusPengajuan hapusPengajuan) {
        this.context = context;
        this.pengajuanAll = pengajuanAll;
        this.lihatDataPengajuan = lihatDataPengajuan;
        this.hapusPengajuan = hapusPengajuan;
    }

    @NonNull
    @Override
    public AdapterPendudukDaftarKTP.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_penduduk_daftar, parent, false);
        return new AdapterPendudukDaftarKTP.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPendudukDaftarKTP.Holder holder, int position) {
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

        //Set Margin List Terakhir
        if (position == (getItemCount()-1)){
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.cvList.getLayoutParams();
            params.bottomMargin = 60;
        }
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

            imbDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lihatDataPengajuan.lihat(getAbsoluteAdapterPosition());
                }
            });

            imbDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hapusPengajuan.hapus(getAbsoluteAdapterPosition());
                }
            });
        }
    }
    public interface lihatDataPengajuan{
        void lihat(int position);
    }
    public interface hapusPengajuan{
        void hapus(int position);
    }
}
