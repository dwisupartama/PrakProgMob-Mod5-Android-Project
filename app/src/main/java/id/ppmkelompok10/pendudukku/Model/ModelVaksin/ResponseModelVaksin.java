package id.ppmkelompok10.pendudukku.Model.ModelVaksin;

import java.util.ArrayList;
import java.util.List;

import id.ppmkelompok10.pendudukku.Model.ModelVaksin.ResponseModelVaksin;

public class ResponseModelVaksin {
    private int code;
    private String message;
    private ArrayList<ModelVaksin> data = new ArrayList<>();

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

    public ArrayList<ModelVaksin> getData() {
        return data;
    }

    public void setData(ArrayList<ModelVaksin> data) {
        this.data = data;
    }
}
