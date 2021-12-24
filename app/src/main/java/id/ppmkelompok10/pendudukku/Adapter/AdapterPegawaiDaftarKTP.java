package id.ppmkelompok10.pendudukku.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.ppmkelompok10.pendudukku.Model.ModelKTP.PengajuanKTP;
import id.ppmkelompok10.pendudukku.R;

public class AdapterPegawaiDaftarKTP extends RecyclerView.Adapter<AdapterPegawaiDaftarKTP.Holder> {
    private Context context;
    private ArrayList<PengajuanKTP> pengajuanAll;

    public AdapterPegawaiDaftarKTP(Context context, ArrayList<PengajuanKTP> pengajuanAll) {
        this.context = context;
        this.pengajuanAll = pengajuanAll;
    }

    @NonNull
    @Override
    public AdapterPegawaiDaftarKTP.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_pegawai_daftar_wtdetail, parent, false);
        return new AdapterPegawaiDaftarKTP.Holder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull AdapterPegawaiDaftarKTP.Holder holder, int position) {
        String jenisPengajuan = pengajuanAll.get(position).getJenis_pengajuan();
        String namaLengkap = pengajuanAll.get(position).getNama_lengkap();
        String nik = String.valueOf(pengajuanAll.get(position).getNik());
        String status = pengajuanAll.get(position).getStatus_pengajuan();

        if(namaLengkap.length() > 18){
            namaLengkap = namaLengkap.substring(0,18)+"...";
        }

        holder.tvJenisPengajuan.setText(jenisPengajuan);
        holder.tvNamaLengkap.setText(namaLengkap);
        holder.tvNIK.setText(nik);
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
        TextView tvJenisPengajuan, tvNamaLengkap, tvNIK, tvStatus;
        LinearLayout lnBgStatus;
        ImageButton imbDetail, imbEdit;
        CardView cvList;

        public Holder(@NonNull View itemView) {
            super(itemView);

            tvJenisPengajuan = itemView.findViewById(R.id.tv_jenis_pengajuan);
            tvNamaLengkap = itemView.findViewById(R.id.tv_nama_lengkap);
            tvNIK = itemView.findViewById(R.id.tv_nik);
            tvStatus = itemView.findViewById(R.id.tv_status);

            lnBgStatus = itemView.findViewById(R.id.ln_bg_status);

            imbDetail = itemView.findViewById(R.id.imb_detail);
            imbEdit = itemView.findViewById(R.id.imb_edit);

//            imbDetail.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    lihatDataPengajuan.lihat(getAbsoluteAdapterPosition());
//                }
//            });
//
//            imbEdit.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    editPengajuan.edit(getAbsoluteAdapterPosition());
//                }
//            });

            cvList = itemView.findViewById(R.id.cv_list);
        }
    }

//    public interface EditPengajuan{
//        void edit(int position);
//    }
}
