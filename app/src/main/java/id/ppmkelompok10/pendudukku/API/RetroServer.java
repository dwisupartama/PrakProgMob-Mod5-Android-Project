package id.ppmkelompok10.pendudukku.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroServer {
    private static final String baseURl = "http://172.16.57.143:8000/api/";
    private static Retrofit retro;

    public static Retrofit konekRetrofit(){
        if(retro == null){
            retro = new Retrofit.Builder()
                    .baseUrl(baseURl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retro;
    }
}
