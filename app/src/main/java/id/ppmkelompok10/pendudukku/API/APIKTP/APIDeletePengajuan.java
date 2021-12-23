package id.ppmkelompok10.pendudukku.API.APIKTP;

import id.ppmkelompok10.pendudukku.Model.ModelKTP.ResponseModelKTP;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIDeletePengajuan {
    @GET("/api/deletePengajuan/{id}")
    Call<ResponseModelKTP> apiDelete(
            @Path(value = "id", encoded = true) Long id
    );
}
