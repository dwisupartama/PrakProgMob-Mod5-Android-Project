package id.ppmkelompok10.pendudukku.Model.ModelKTP;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class PengajuanKTP implements Parcelable {
    private long id;
    private String jenis_pengajuan;
    private Date tanggal_pengajuan;
    private String status_pengajuan;
    private String keterangan;
    private Date perkiraan_selesai;
    private Date tanggal_selesai;
    private long nik;
    private String nama_lengkap;
    private String tempat_lahir;
    private Date tanggal_lahir;
    private String jenis_kelamin;
    private String golongan_darah;
    private String alamat;
    private String agama;
    private String status_perkawinan;
    private String pekerjaan;
    private Date created_at;
    private Date updated_at;

    public PengajuanKTP(long id, String jenis_pengajuan, Date tanggal_pengajuan, String status_pengajuan, long nik, String nama_lengkap, String tempat_lahir, Date tanggal_lahir, String jenis_kelamin, String golongan_darah, String alamat, String agama, String status_perkawinan, String pekerjaan, Date created_at, Date updated_at) {
        this.id = id;
        this.jenis_pengajuan = jenis_pengajuan;
        this.tanggal_pengajuan = tanggal_pengajuan;
        this.status_pengajuan = status_pengajuan;
        this.nik = nik;
        this.nama_lengkap = nama_lengkap;
        this.tempat_lahir = tempat_lahir;
        this.tanggal_lahir = tanggal_lahir;
        this.jenis_kelamin = jenis_kelamin;
        this.golongan_darah = golongan_darah;
        this.alamat = alamat;
        this.agama = agama;
        this.status_perkawinan = status_perkawinan;
        this.pekerjaan = pekerjaan;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public PengajuanKTP(long id, String jenis_pengajuan, Date tanggal_pengajuan, String status_pengajuan, String keterangan, Date perkiraan_selesai, Date tanggal_selesai, long nik, String nama_lengkap, String tempat_lahir, Date tanggal_lahir, String jenis_kelamin, String golongan_darah, String alamat, String agama, String status_perkawinan, String pekerjaan, Date created_at, Date updated_at) {
        this.id = id;
        this.jenis_pengajuan = jenis_pengajuan;
        this.tanggal_pengajuan = tanggal_pengajuan;
        this.status_pengajuan = status_pengajuan;
        this.keterangan = keterangan;
        this.perkiraan_selesai = perkiraan_selesai;
        this.tanggal_selesai = tanggal_selesai;
        this.nik = nik;
        this.nama_lengkap = nama_lengkap;
        this.tempat_lahir = tempat_lahir;
        this.tanggal_lahir = tanggal_lahir;
        this.jenis_kelamin = jenis_kelamin;
        this.golongan_darah = golongan_darah;
        this.alamat = alamat;
        this.agama = agama;
        this.status_perkawinan = status_perkawinan;
        this.pekerjaan = pekerjaan;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    protected PengajuanKTP(Parcel in) {
        id = in.readLong();
        jenis_pengajuan = in.readString();
        tanggal_pengajuan = new Date(in.readLong());
        status_pengajuan = in.readString();
        keterangan = in.readString();
        nik = in.readLong();
        nama_lengkap = in.readString();
        tempat_lahir = in.readString();
        tanggal_lahir = new Date(in.readLong());
        jenis_kelamin = in.readString();
        golongan_darah = in.readString();
        alamat = in.readString();
        agama = in.readString();
        status_perkawinan = in.readString();
        pekerjaan = in.readString();
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(jenis_pengajuan);
        parcel.writeLong(tanggal_pengajuan.getTime());
        parcel.writeString(status_pengajuan);
        parcel.writeString(keterangan);
        parcel.writeLong(nik);
        parcel.writeString(nama_lengkap);
        parcel.writeString(tempat_lahir);
        parcel.writeLong(tanggal_lahir.getTime());
        parcel.writeString(jenis_kelamin);
        parcel.writeString(golongan_darah);
        parcel.writeString(alamat);
        parcel.writeString(agama);
        parcel.writeString(status_perkawinan);
        parcel.writeString(pekerjaan);
    }

    public static final Creator<PengajuanKTP> CREATOR = new Creator<PengajuanKTP>() {
        @Override
        public PengajuanKTP createFromParcel(Parcel in) {
            return new PengajuanKTP(in);
        }

        @Override
        public PengajuanKTP[] newArray(int size) {
            return new PengajuanKTP[size];
        }
    };

    public long getId() {
        return id;
    }

    public String getJenis_pengajuan() {
        return jenis_pengajuan;
    }

    public Date getTanggal_pengajuan() {
        return tanggal_pengajuan;
    }

    public String getStatus_pengajuan() {
        return status_pengajuan;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public Date getPerkiraan_selesai() {
        return perkiraan_selesai;
    }

    public Date getTanggal_selesai() {
        return tanggal_selesai;
    }

    public long getNik() {
        return nik;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public String getTempat_lahir() {
        return tempat_lahir;
    }

    public Date getTanggal_lahir() {
        return tanggal_lahir;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public String getGolongan_darah() {
        return golongan_darah;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getAgama() {
        return agama;
    }

    public String getStatus_perkawinan() {
        return status_perkawinan;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
