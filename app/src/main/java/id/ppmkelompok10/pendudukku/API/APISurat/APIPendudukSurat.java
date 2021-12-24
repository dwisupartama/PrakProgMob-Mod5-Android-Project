package id.ppmkelompok10.pendudukku.API.APISurat;

import id.ppmkelompok10.pendudukku.Model.ModelKTP.ResponseMultiDataModelKTP;
import id.ppmkelompok10.pendudukku.Model.ModelKTP.ResponseSingleDataModelKTP;
import id.ppmkelompok10.pendudukku.Model.ModelSurat.ResponseMultiDataModelSurat;
import id.ppmkelompok10.pendudukku.Model.ModelSurat.ResponseSingleDataModelSurat;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIPendudukSurat {
    @GET("penduduk/daftarSurat/{nik}")
    Call<ResponseMultiDataModelSurat> apiDaftarSurat(
            @Path(value = "nik", encoded = true) String nik
    );

    @FormUrlEncoded
    @POST("penduduk/tambahSurat")
    Call<ResponseSingleDataModelSurat> apiTambahSurat(
            @Field("nik") String nik,
            @Field("jenis_surat") String jenis_surat,
            @Field("deskripsi_pengajuan") String deskripsi_pengajuan
    );

    @GET("penduduk/deleteSurat/{id}")
    Call<ResponseSingleDataModelSurat> apiDeleteSurat(
            @Path(value = "id", encoded = true) String id
    );

    @GET("detailDataSurat/{id}")
    Call<ResponseSingleDataModelSurat> apiDetailDataSurat(
            @Path(value = "id", encoded = true) String id
    );
}
