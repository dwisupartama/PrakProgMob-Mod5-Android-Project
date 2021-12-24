package id.ppmkelompok10.pendudukku.API.APIVaksin;

import id.ppmkelompok10.pendudukku.Model.ModelKTP.ResponseSingleDataModelKTP;
import id.ppmkelompok10.pendudukku.Model.ModelVaksin.ResponseMultiDataModelVaksin;
import id.ppmkelompok10.pendudukku.Model.ModelVaksin.ResponseSingleModelDataVaksin;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIPendudukVaksin {
    @FormUrlEncoded
    @POST("penduduk/tambahVaksin")
    Call<ResponseSingleModelDataVaksin> apiTambahVaksin(
            @Field("nik") String nik,
            @Field("tahap_vaksin") String tahap_vaksin,
            @Field("daerah_vaksin_diajukan") String daerah_vaksin_diajukan,
            @Field("riwayat_penyakit") String riwayat_penyakit
    );

    @GET("penduduk/daftarVaksin/{nik}")
    Call<ResponseMultiDataModelVaksin> apiDaftarVaksin(
            @Path(value = "nik", encoded = true) String nik
    );

    @GET("penduduk/deleteVaksin/{id}")
    Call<ResponseSingleModelDataVaksin> apiDeleteVaksin(
            @Path(value = "id", encoded = true) String id
    );

    @GET("detailDataVaksin/{id}")
    Call<ResponseSingleModelDataVaksin> apiDetailDataVaksin(
            @Path(value = "id", encoded = true) String id
    );
}
