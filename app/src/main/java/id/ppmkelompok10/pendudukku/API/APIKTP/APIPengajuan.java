package id.ppmkelompok10.pendudukku.API.APIKTP;

import id.ppmkelompok10.pendudukku.Model.ModelAuth.ResponseModelAuth;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIPengajuan {
    @FormUrlEncoded
    @POST("pengajuanKTP")
    Call<ResponseModelAuth> AjukanKTP(
            @Field("jenis_pengajuan") String jenis_pengajuan,
            @Field("nik") String nik,
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
}
