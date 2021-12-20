package id.ppmkelompok10.pendudukku.Helper;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedEditor;
    String SHARED_PREF_NAME = "session";

    public SessionManagement(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        sharedEditor = sharedPreferences.edit();
        sharedEditor.apply();
    }

    public void saveSession(String nik, String namaLengkap){
        sharedEditor.putString("nik", nik);
        sharedEditor.putString("nama_lengkap", namaLengkap);
        sharedEditor.putBoolean("login", true);
        sharedEditor.apply();
    }

    public void removeSession(){
        sharedEditor.clear();
        sharedEditor.apply();
    }

    public String getNIK(){
        return sharedPreferences.getString("nik", "");
    }

    public String getNamaLengkap(){
        return sharedPreferences.getString("nama_lengkap", "");
    }

    public Boolean getStatusLogin(){
        return sharedPreferences.getBoolean("login", false);
    }
}
