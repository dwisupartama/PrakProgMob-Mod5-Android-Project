package id.ppmkelompok10.pendudukku.Model.ModelVaksin;

import java.util.ArrayList;

public class ResponseMultiDataModelVaksin {
    private int code;
    private String message;
    private ArrayList<ModelVaksin> data;

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
