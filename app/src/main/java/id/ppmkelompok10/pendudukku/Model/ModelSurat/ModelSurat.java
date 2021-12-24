package id.ppmkelompok10.pendudukku.Model.ModelSurat;

public class ModelSurat {
    private int id;
    private String nik, jenis_surat, tanggal_pengajuan, status_pengajuan, keterangan, perkiraan_selesai, tanggal_selesai, deskripsi_pengajuan, file_surat, nama_lengkap, created_at, updated_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getJenis_surat() {
        return jenis_surat;
    }

    public void setJenis_surat(String jenis_surat) {
        this.jenis_surat = jenis_surat;
    }

    public String getTanggal_pengajuan() {
        return tanggal_pengajuan;
    }

    public void setTanggal_pengajuan(String tanggal_pengajuan) {
        this.tanggal_pengajuan = tanggal_pengajuan;
    }

    public String getStatus_pengajuan() {
        return status_pengajuan;
    }

    public void setStatus_pengajuan(String status_pengajuan) {
        this.status_pengajuan = status_pengajuan;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getPerkiraan_selesai() {
        return perkiraan_selesai;
    }

    public void setPerkiraan_selesai(String perkiraan_selesai) {
        this.perkiraan_selesai = perkiraan_selesai;
    }

    public String getTanggal_selesai() {
        return tanggal_selesai;
    }

    public void setTanggal_selesai(String tanggal_selesai) {
        this.tanggal_selesai = tanggal_selesai;
    }

    public String getDeskripsi_pengajuan() {
        return deskripsi_pengajuan;
    }

    public void setDeskripsi_pengajuan(String deskripsi_pengajuan) {
        this.deskripsi_pengajuan = deskripsi_pengajuan;
    }

    public String getFile_surat() {
        return file_surat;
    }

    public void setFile_surat(String file_surat) {
        this.file_surat = file_surat;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
