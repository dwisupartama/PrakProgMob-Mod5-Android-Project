package id.ppmkelompok10.pendudukku.API.APIKTP;

import java.util.Date;

import id.ppmkelompok10.pendudukku.Model.ModelKTP.ResponseMultiDataModelKTP;
import id.ppmkelompok10.pendudukku.Model.ModelKTP.ResponseSingleDataModelKTP;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIPegawaiKTP {
    @FormUrlEncoded
    @POST("pegawai/updatePengajuan")
    Call<ResponseSingleDataModelKTP> apiUpdatePengajuan(
        @Field("id") String id,
        @Field("status_pengajuan") String status,
        @Field("keterangan") String keterangan,
        @Field("perkiraan_selesai") Date perkiraan_selesai,
        @Field("tanggal_selesai") Date tanggal_selesai
    );

    @GET("pegawai/allPengajuan")
    Call<ResponseMultiDataModelKTP> apiAllPengajuan();
}
