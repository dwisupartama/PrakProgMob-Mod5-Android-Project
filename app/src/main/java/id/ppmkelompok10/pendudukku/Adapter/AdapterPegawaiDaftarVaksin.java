package id.ppmkelompok10.pendudukku.Adapter;

import android.content.Context;
import android.content.Intent;
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

import id.ppmkelompok10.pendudukku.ModulKTP.DetailKTPActivity;
import id.ppmkelompok10.pendudukku.ModulKTP.PegawaiEditKTPActivity;
import id.ppmkelompok10.pendudukku.ModulVaksin.DetailVaksinActivity;
import id.ppmkelompok10.pendudukku.ModulVaksin.PegawaiEditVaksinActivity;
import id.ppmkelompok10.pendudukku.R;

public class AdapterPegawaiDaftarVaksin extends RecyclerView.Adapter<AdapterPegawaiDaftarVaksin.Holder> {
    private Context context;

    public AdapterPegawaiDaftarVaksin(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterPegawaiDaftarVaksin.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_pegawai_daftar_wtdetail, parent, false);
        return new AdapterPegawaiDaftarVaksin.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPegawaiDaftarVaksin.Holder holder, int position) {
        String jenisPengajuan = "Vaksin Pertama";
        String namaLengkap = "I Kadek Dwi Supartama";
        String nik = "5103061410010003";
        String status = "Menunggu Konfirmasi";
//        String status = "Sedang di Proses";
//        String status = "Selesai di Proses";
//        String status = "Pengajuan Gagal";

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

        holder.imbDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailVaksinActivity = new Intent(v.getContext(), DetailVaksinActivity.class);
                context.startActivity(detailVaksinActivity);
            }
        });

        holder.imbEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pegawaiEditVaksinActivity = new Intent(v.getContext(), PegawaiEditVaksinActivity.class);
                context.startActivity(pegawaiEditVaksinActivity);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 6;
        //return arrayList.size();
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

            cvList = itemView.findViewById(R.id.cv_list);
        }
    }
}
