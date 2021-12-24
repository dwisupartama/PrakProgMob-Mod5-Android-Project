package id.ppmkelompok10.pendudukku.Model.ModelKTP;

import java.util.ArrayList;

public class ResponseSingleDataModelKTP {
    private int code;
    private String message;
    private PengajuanKTP data;

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

    public PengajuanKTP getData() {
        return data;
    }

    public void setData(PengajuanKTP data) {
        this.data = data;
    }
}
