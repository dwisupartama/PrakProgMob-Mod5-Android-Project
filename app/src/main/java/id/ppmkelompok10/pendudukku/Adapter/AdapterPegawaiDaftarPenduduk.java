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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.ppmkelompok10.pendudukku.API.APIPenduduk.APIPenduduk;
import id.ppmkelompok10.pendudukku.API.RetroServer;
import id.ppmkelompok10.pendudukku.Helper.LoadingDialog;
import id.ppmkelompok10.pendudukku.Model.ModelPenduduk.ModelDataPenduduk;
import id.ppmkelompok10.pendudukku.Model.ModelPenduduk.ResponseSingleDataModel;
import id.ppmkelompok10.pendudukku.ModulPenduduk.PegawaiEditPendudukActivity;
import id.ppmkelompok10.pendudukku.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterPegawaiDaftarPenduduk extends RecyclerView.Adapter<AdapterPegawaiDaftarPenduduk.Holder> {
    private Context context;
    private ArrayList<ModelDataPenduduk> dataPenduduk;

    public AdapterPegawaiDaftarPenduduk(Context context, ArrayList<ModelDataPenduduk> dataPenduduk) {
        this.context = context;
        this.dataPenduduk = dataPenduduk;
    }

    @NonNull
    @Override
    public AdapterPegawaiDaftarPenduduk.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_pegawai_daftar_wtdelete, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPegawaiDaftarPenduduk.Holder holder, @SuppressLint("RecyclerView") int position) {
        String nik = dataPenduduk.get(position).getNik();
        String namaLengkap = dataPenduduk.get(position).getNama_lengkap();
        // Max : 16
        String tanggalLahir =dataPenduduk.get(position).getTanggal_lahir() ;

        if(namaLengkap.length() > 18){
            namaLengkap = namaLengkap.substring(0,18)+"...";
        }

        holder.tvNIK.setText(nik);
        holder.tvNamaLengkap.setText(namaLengkap);
        holder.tvNoKK.setText(tanggalLahir);

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
                pegawaiEditPendudukActivity.putExtra("id_penduduk", dataPenduduk.get(position).getNik());
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
                        alertDialog.dismiss();

                        LoadingDialog loading2 = new LoadingDialog((Activity) v.getContext());
                        loading2.startLoadingDialog();

                        APIPenduduk apiPenduduk = RetroServer.konekRetrofit().create(APIPenduduk.class);
                        Call<ResponseSingleDataModel> apiDeletePenduduk = apiPenduduk.deletePenduduk(nik);

                        apiDeletePenduduk.enqueue(new Callback<ResponseSingleDataModel>() {
                            @Override
                            public void onResponse(Call<ResponseSingleDataModel> call, Response<ResponseSingleDataModel> response) {
                                dataPenduduk.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeRemoved(position, dataPenduduk.size());
                                loading2.dismissLoading();
                            }

                            @Override
                            public void onFailure(Call<ResponseSingleDataModel> call, Throwable t) {
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
//        return 6;
        return dataPenduduk.size();
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
