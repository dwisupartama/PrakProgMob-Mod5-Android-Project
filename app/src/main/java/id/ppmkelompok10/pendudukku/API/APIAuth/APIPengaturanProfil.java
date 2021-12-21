package id.ppmkelompok10.pendudukku.API.APIAuth;

import id.ppmkelompok10.pendudukku.Model.ModelAuth.AccountModelAuth;
import id.ppmkelompok10.pendudukku.Model.ModelAuth.ResponseModelAuth;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIPengaturanProfil {
    @GET("ambilDataProfil/{nik}")
    Call<ResponseModelAuth> apiAmbilDataProfil(
            @Path(value = "nik", encoded = true) String nik
    );

    @FormUrlEncoded
    @POST("perbaharuiProfil")
    Call<ResponseModelAuth> apiPerbaharuiProfil(
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
}
