package id.ppmkelompok10.pendudukku.Model.ModelPenduduk;

public class ResponseSingleDataModel {
    private int code;
    private String message;
    private ModelDataPenduduk data;

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

    public ModelDataPenduduk getData() {
        return data;
    }

    public void setData(ModelDataPenduduk data) {
        this.data = data;
    }
}
