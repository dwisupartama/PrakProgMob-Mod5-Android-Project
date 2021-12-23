package id.ppmkelompok10.pendudukku.API.APIKTP;

import java.util.Date;

import id.ppmkelompok10.pendudukku.Model.ModelKTP.ResponseModelKTP;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIPegawaiKTP {
    @FormUrlEncoded
    @POST("/api/updatePengajuan")
    Call<ResponseModelKTP> apiUpdate(
        @Field("id") Long id,
        @Field("status_pengajuan") String status,
        @Field("keterangan") String keterangan,
        @Field("perkiraan_selesai") Date perkiraan_selesai,
        @Field("tanggal_selesai") Date tanggal_selesai
    );

    @GET("/api/allPengajuan")
    Call<ResponseModelKTP> apiGetAll();
}
