package id.ppmkelompok10.pendudukku.Model.ModelAuth;

public class ResponseModelAuth {
    private int code;
    private String message;
    private AccountModelAuth data;

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

    public AccountModelAuth getData() {
        return data;
    }

    public void setData(AccountModelAuth data) {
        this.data = data;
    }
}
