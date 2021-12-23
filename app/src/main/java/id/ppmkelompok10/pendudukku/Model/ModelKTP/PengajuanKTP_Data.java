package id.ppmkelompok10.pendudukku.Model.ModelKTP;

import java.util.ArrayList;

public class PengajuanKTP_Data {
    private ArrayList<PengajuanKTP> pengajuanData;

    public ArrayList<PengajuanKTP> getdata() {
        return pengajuanData;
    }

    public void setPengajuanData(ArrayList<PengajuanKTP> pengajuanData) {
        this.pengajuanData = pengajuanData;
    }

    private static PengajuanKTP_Data single_instance = null;
    public static PengajuanKTP_Data getInstance() {
        if (single_instance == null)
            single_instance = new PengajuanKTP_Data();

        return single_instance;
    }
}
