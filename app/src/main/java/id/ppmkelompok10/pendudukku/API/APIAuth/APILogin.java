package id.ppmkelompok10.pendudukku.API.APIAuth;

import id.ppmkelompok10.pendudukku.Model.ModelAuth.ResponseModelAuth;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APILogin {
    @FormUrlEncoded
    @POST("loginPenduduk")
    Call<ResponseModelAuth> apiLoginPenduduk(
            @Field("nik") String nik,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("loginPegawai")
    Call<ResponseModelAuth> apiLoginPegawai(
            @Field("nik") String nik,
            @Field("password") String password
    );
}
