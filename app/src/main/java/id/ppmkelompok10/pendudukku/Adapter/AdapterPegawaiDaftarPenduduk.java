package id.ppmkelompok10.pendudukku.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import id.ppmkelompok10.pendudukku.ModulPenduduk.PegawaiEditPendudukActivity;
import id.ppmkelompok10.pendudukku.R;

public class AdapterPegawaiDaftarPenduduk extends RecyclerView.Adapter<AdapterPegawaiDaftarPenduduk.Holder> {
    private Context context;

    public AdapterPegawaiDaftarPenduduk(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterPegawaiDaftarPenduduk.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_pegawai_daftar_wtdelete, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPegawaiDaftarPenduduk.Holder holder, int position) {
        String nik = "5103061410010003";
        String namaLengkap = "I Kadek Dwi Supartama";
        // Max : 16
        String noKK = "51032829467263";

        if(namaLengkap.length() > 18){
            namaLengkap = namaLengkap.substring(0,18)+"...";
        }

        holder.tvNIK.setText(nik);
        holder.tvNamaLengkap.setText(namaLengkap);
        holder.tvNoKK.setText(noKK);

        //Set Margin List Terakhir
        if (position == (getItemCount()-1)){
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.cvList.getLayoutParams();
            params.bottomMargin = 60;
        }

        //Tombol Edit List
        holder.imbEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Btn Edit
                Intent pegawaiEditPendudukActivity = new Intent(context, PegawaiEditPendudukActivity.class);
                context.startActivity(pegawaiEditPendudukActivity);
            }
        });

        //Tombol Delete List
        holder.imbDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Btn Delete
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
        TextView tvNIK, tvNamaLengkap, tvNoKK;
        ImageButton imbEdit, imbDelete;
        CardView cvList;

        public Holder(@NonNull View itemView) {
            super(itemView);

            tvNIK = itemView.findViewById(R.id.tv_nik);
            tvNamaLengkap = itemView.findViewById(R.id.tv_nama_lengkap);
            tvNoKK = itemView.findViewById(R.id.tv_no_kk);

            imbEdit = itemView.findViewById(R.id.imb_edit);
            imbDelete = itemView.findViewById(R.id.imb_delete);

            cvList = itemView.findViewById(R.id.cv_list);
        }
    }
}
