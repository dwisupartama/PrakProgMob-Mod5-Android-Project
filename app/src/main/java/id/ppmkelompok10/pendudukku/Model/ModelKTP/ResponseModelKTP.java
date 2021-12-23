package id.ppmkelompok10.pendudukku.Model.ModelKTP;

import java.util.ArrayList;

public class ResponseModelKTP {
    private int code;
    private String message;
    private ArrayList<PengajuanKTP> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<PengajuanKTP> getData() {
        return data;
    }

    public void setData(ArrayList<PengajuanKTP> data) {
        this.data = data;
    }
}
