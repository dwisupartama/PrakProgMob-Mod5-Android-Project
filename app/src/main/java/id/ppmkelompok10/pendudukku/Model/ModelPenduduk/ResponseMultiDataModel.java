package id.ppmkelompok10.pendudukku.Model.ModelPenduduk;

import java.util.ArrayList;

public class ResponseMultiDataModel {
    private int code;
    private String message;
    private ArrayList<ModelDataPenduduk> data;

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

    public ArrayList<ModelDataPenduduk> getData() {
        return data;
    }

    public void setData(ArrayList<ModelDataPenduduk> data) {
        this.data = data;
    }
}
