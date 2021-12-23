package id.ppmkelompok10.pendudukku.API.APIKTP;

import id.ppmkelompok10.pendudukku.Model.ModelAuth.ResponseModelAuth;
import id.ppmkelompok10.pendudukku.Model.ModelKTP.ResponseModelKTP;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIGetAllPengajuan {
    @GET("/api/getPengajuanFor/{nik}")
    Call<ResponseModelKTP> apiAmbilPengajuan(
            @Path(value = "nik", encoded = true) String nik
    );
}
