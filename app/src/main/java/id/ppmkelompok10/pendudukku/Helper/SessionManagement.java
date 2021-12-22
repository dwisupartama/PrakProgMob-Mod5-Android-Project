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

    public void saveSession(String nik, String statusAkses, String logedinTo){
        sharedEditor.putString("nik", nik);
        sharedEditor.putString("status_akses", statusAkses);
        sharedEditor.putString("loggedin_to", logedinTo);
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

    public String getStatusAkses(){
        return sharedPreferences.getString("status_akses", "");
    }

    public String getLoggedInTo(){
        return sharedPreferences.getString("loggedin_to", "");
    }

    public Boolean getStatusLogin(){
        return sharedPreferences.getBoolean("login", false);
    }
}
