package com.example.firebase;

import android.os.Parcel;
import android.os.Parcelable;

public class SinhVienRVModal implements Parcelable {
    private String svName;
    private String gioiTinh;
    private String phone;
    private String email;
    private String svID;

    public SinhVienRVModal(){}

    public SinhVienRVModal(String svName, String gioiTinh, String phone, String email, String svID) {
        this.svName = svName;
        this.gioiTinh = gioiTinh;
        this.phone = phone;
        this.email = email;
        this.svID = svID;
    }

    protected SinhVienRVModal(Parcel in) {
        svName = in.readString();
        gioiTinh = in.readString();
        phone = in.readString();
        email = in.readString();
        svID = in.readString();
    }

    public static final Creator<SinhVienRVModal> CREATOR = new Creator<SinhVienRVModal>() {
        @Override
        public SinhVienRVModal createFromParcel(Parcel in) {
            return new SinhVienRVModal(in);
        }

        @Override
        public SinhVienRVModal[] newArray(int size) {
            return new SinhVienRVModal[size];
        }
    };

    public String getSvName() {
        return svName;
    }

    public void setSvName(String svName) {
        this.svName = svName;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSvID() {
        return svID;
    }

    public void setSvID(String svID) {
        this.svID = svID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(svName);
        parcel.writeString(gioiTinh);
        parcel.writeString(phone);
        parcel.writeString(email);
        parcel.writeString(svID);
    }
}
