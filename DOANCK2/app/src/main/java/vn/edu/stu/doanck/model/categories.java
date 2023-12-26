package vn.edu.stu.doanck.model;

import java.io.Serializable;

public class categories implements Serializable {
    private Integer maCate;
    private String tenLoai;

    public categories(Integer maCate, String tenLoai) {
        this.maCate = maCate;
        this.tenLoai = tenLoai;
    }

    public categories() {
    }

    public Integer getMaCate() {
        return maCate;
    }

    public void setMaCate(Integer maCate) {
        this.maCate = maCate;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    @Override
    public String toString() {
        return
                "Ma loai=" + maCate +
                "\n Ten loai='" + tenLoai;
    }

}
