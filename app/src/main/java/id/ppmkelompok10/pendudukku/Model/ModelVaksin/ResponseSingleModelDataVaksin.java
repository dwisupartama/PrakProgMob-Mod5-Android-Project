package id.ppmkelompok10.pendudukku.Model.ModelVaksin;

import java.util.ArrayList;

public class ResponseSingleModelDataVaksin {
    private int code;
    private String message;
    private ModelVaksin data;

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

    public ModelVaksin getData() {
        return data;
    }

    public void setData(ModelVaksin data) {
        this.data = data;
    }
}
