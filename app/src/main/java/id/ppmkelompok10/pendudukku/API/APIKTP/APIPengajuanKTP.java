package id.ppmkelompok10.pendudukku.API.APIKTP;

import java.math.BigInteger;

import id.ppmkelompok10.pendudukku.Model.ModelAuth.ResponseModelAuth;
import id.ppmkelompok10.pendudukku.Model.ModelKTP.ResponseModelKTP;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIPengajuanKTP {
    @FormUrlEncoded
    @POST("pengajuanKTP")
    Call<ResponseModelAuth> AjukanKTP(
            @Field("jenis_pengajuan") String jenis_pengajuan,
            @Field("nik") BigInteger id,
            @Field("nama") String nama,
            @Field("tempat_lahir") String tempat_lahir,
            @Field("tanggal_lahir") String tanggal_lahir,
            @Field("jenis_kelamin") String jenis_kelamin,
            @Field("golongan_darah") String golongan_darah,
            @Field("alamat") String alamat,
            @Field("agama") String agama,
            @Field("status_perkawinan") String status_perkawinan,
            @Field("pekerjaan") String pekerjaan
    );

    @GET("/api/deletePengajuan/{id}")
    Call<ResponseModelKTP> apiDelete(
            @Path(value = "id", encoded = true) Long id
    );

    @GET("/api/getPengajuanFor/{nik}")
    Call<ResponseModelKTP> apiAmbilPengajuan(
            @Path(value = "nik", encoded = true) String nik
    );
}
