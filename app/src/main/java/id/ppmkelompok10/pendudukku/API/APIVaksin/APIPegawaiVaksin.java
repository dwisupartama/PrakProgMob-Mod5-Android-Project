package id.ppmkelompok10.pendudukku.API.APIVaksin;

import id.ppmkelompok10.pendudukku.Model.ModelVaksin.ResponseMultiDataModelVaksin;
import id.ppmkelompok10.pendudukku.Model.ModelVaksin.ResponseSingleModelDataVaksin;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIPegawaiVaksin {

    @FormUrlEncoded
    @POST("pegawai/updateVaksin")
    Call<ResponseSingleModelDataVaksin> apiUpdateVaksin(
            @Field("id") String id,
            @Field("status_pengajuan") String status_pengajuan,
            @Field("keterangan") String keterangan,
            @Field("perkiraan_selesai") String perkiraan_selesai,
            @Field("tanggal_selesai") String tanggal_selesai,
            @Field("tanggal_vaksin") String tanggal_vaksin,
            @Field("waktu_vaksin") String waktu_vaksin,
            @Field("tempat_vaksin") String tempat_vaksin,
            @Field("jenis_vaksin") String jenis_vaksin
    );

    @GET("pegawai/daftarVaksinPegawai")
    Call<ResponseMultiDataModelVaksin> apiDaftarVaksinPegawai();


}
