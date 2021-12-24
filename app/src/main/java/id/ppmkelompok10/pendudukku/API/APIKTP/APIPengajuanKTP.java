package id.ppmkelompok10.pendudukku.API.APIKTP;

import java.math.BigInteger;

import id.ppmkelompok10.pendudukku.Model.ModelAuth.ResponseModelAuth;
import id.ppmkelompok10.pendudukku.Model.ModelKTP.ResponseMultiDataModelKTP;
import id.ppmkelompok10.pendudukku.Model.ModelKTP.ResponseSingleDataModelKTP;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIPengajuanKTP {
    @GET("penduduk/getPengajuanFor/{nik}")
    Call<ResponseMultiDataModelKTP> apiGetPengajuanFor(
            @Path(value = "nik", encoded = true) String nik
    );

    @FormUrlEncoded
    @POST("penduduk/pengajuanKTP")
    Call<ResponseSingleDataModelKTP> apiPengajuanKTP(
            @Field("jenis_pengajuan") String jenis_pengajuan,
            @Field("nik") String nik,
            @Field("nama_lengkap") String nama_lengkap,
            @Field("tempat_lahir") String tempat_lahir,
            @Field("tanggal_lahir") String tanggal_lahir,
            @Field("jenis_kelamin") String jenis_kelamin,
            @Field("golongan_darah") String golongan_darah,
            @Field("alamat") String alamat,
            @Field("agama") String agama,
            @Field("status_perkawinan") String status_perkawinan,
            @Field("pekerjaan") String pekerjaan
    );

    @GET("penduduk/deletePengajuan/{id}")
    Call<ResponseSingleDataModelKTP> apiDeletePengajuan(
            @Path(value = "id", encoded = true) String id
    );

    @GET("detailPengajuan/{id}")
    Call<ResponseSingleDataModelKTP> apiDetailPengajuan(
            @Path(value = "id", encoded = true) String id
    );
}
