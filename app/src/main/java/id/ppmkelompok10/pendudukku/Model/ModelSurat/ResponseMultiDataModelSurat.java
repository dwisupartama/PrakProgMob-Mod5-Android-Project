package id.ppmkelompok10.pendudukku.Model.ModelSurat;

import java.util.ArrayList;

public class ResponseMultiDataModelSurat {
    private int code;
    private String message;
    private ArrayList<ModelSurat> data;

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

    public ArrayList<ModelSurat> getData() {
        return data;
    }

    public void setData(ArrayList<ModelSurat> data) {
        this.data = data;
    }
}
