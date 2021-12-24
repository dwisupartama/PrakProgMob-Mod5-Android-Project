package id.ppmkelompok10.pendudukku.Model.ModelSurat;

import java.util.ArrayList;

public class ResponseSingleDataModelSurat {
    private int code;
    private String message;
    private ModelSurat data;

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

    public ModelSurat getData() {
        return data;
    }

    public void setData(ModelSurat data) {
        this.data = data;
    }
}
