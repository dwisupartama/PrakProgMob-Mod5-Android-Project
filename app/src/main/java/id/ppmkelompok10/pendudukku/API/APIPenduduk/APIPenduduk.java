package id.ppmkelompok10.pendudukku.API.APIPenduduk;

import id.ppmkelompok10.pendudukku.Model.ModelAuth.ResponseModelAuth;
import id.ppmkelompok10.pendudukku.Model.ModelPenduduk.ResponseMultiDataModel;
import id.ppmkelompok10.pendudukku.Model.ModelPenduduk.ResponseSingleDataModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIPenduduk {
    @GET("getPenduduk")
    Call<ResponseMultiDataModel> getPenduduk();

    @FormUrlEncoded
    @POST("addPenduduk")
    Call<ResponseSingleDataModel> addPenduduk(
            @Field("nik") String nik,
            @Field("status_akses") String status_akses,
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

    @GET("editPenduduk/{nik}")
    Call<ResponseSingleDataModel> editPenduduk(
            @Path(value = "nik", encoded = true) String nik
    );

    @FormUrlEncoded
    @POST("updatePenduduk")
    Call<ResponseSingleDataModel> updatePenduduk(
            @Field("nik") String nik,
            @Field("status_akses") String status_akses,
            @Field("new_nik") String new_nik,
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

    @GET("resetPasswordPenduduk/{nik}")
    Call<ResponseSingleDataModel> resetPasswordPenduduk(
            @Path(value = "nik", encoded = true) String nik
    );

    @GET("deletePenduduk/{nik}")
    Call<ResponseSingleDataModel> deletePenduduk(
            @Path(value = "nik", encoded = true) String nik
    );

    @GET("searchPenduduk/{key}")
    Call<ResponseMultiDataModel> searchPenduduk(
            @Path(value = "key", encoded = true) String key
    );
}
