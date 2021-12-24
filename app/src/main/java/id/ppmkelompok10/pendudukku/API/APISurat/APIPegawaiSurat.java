package id.ppmkelompok10.pendudukku.API.APISurat;

import id.ppmkelompok10.pendudukku.Model.ModelKTP.ResponseMultiDataModelKTP;
import id.ppmkelompok10.pendudukku.Model.ModelKTP.ResponseSingleDataModelKTP;
import id.ppmkelompok10.pendudukku.Model.ModelSurat.ResponseMultiDataModelSurat;
import id.ppmkelompok10.pendudukku.Model.ModelSurat.ResponseSingleDataModelSurat;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIPegawaiSurat {
    @FormUrlEncoded
    @POST("pegawai/updateSurat")
    Call<ResponseSingleDataModelSurat> apiUpdateSurat(
            @Field("id") String id,
            @Field("status_pengajuan") String status,
            @Field("keterangan") String keterangan,
            @Field("perkiraan_selesai") String perkiraan_selesai,
            @Field("tanggal_selesai") String tanggal_selesai,
            @Field("file") String file
            );

    @GET("pegawai/daftarSuratPegawai")
    Call<ResponseMultiDataModelSurat> apiDaftarSuratPegawai();
}
