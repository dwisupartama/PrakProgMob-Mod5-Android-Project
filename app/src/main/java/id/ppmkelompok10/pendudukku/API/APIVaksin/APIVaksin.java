package id.ppmkelompok10.pendudukku.API.APIVaksin;

import id.ppmkelompok10.pendudukku.Model.ModelAuth.ResponseModelAuth;
import id.ppmkelompok10.pendudukku.Model.ModelVaksin.ModelVaksin;
import id.ppmkelompok10.pendudukku.Model.ModelVaksin.ResponseModelDetailVaksin;
import id.ppmkelompok10.pendudukku.Model.ModelVaksin.ResponseModelVaksin;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIVaksin {

    @FormUrlEncoded
    @POST("tambahVaksin")
    Call<ResponseModelVaksin> apiTambahVaksin(
            @Field("nik") String nik,
            @Field("tahap_vaksin") String tahap_vaksin,
            @Field("daerah_vaksin_diajukan") String daerah_vaksin_diajukan,
            @Field("riwayat_penyakit") String riwayat_penyakit
    );

    @GET("daftarVaksin/{nik}")
    Call<ResponseModelVaksin> apiDaftarVaksin(
            @Path(value = "nik", encoded = true) String nik
    );

    @GET("daftarVaksinPegawai")
    Call<ResponseModelVaksin> apiDaftarVaksinPegawai(
    );

    @GET("detailDataVaksin/{id}")
    Call<ResponseModelDetailVaksin> apiDetailVaksin(
            @Path(value = "id", encoded = true) String id
    );
}
