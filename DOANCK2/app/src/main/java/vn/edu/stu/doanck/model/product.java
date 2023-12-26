package vn.edu.stu.doanck.model;

import java.io.Serializable;

public class product implements Serializable {

    private Integer ma;
    private String ten;
    private Integer phanLoai;
    private byte[] hinh;
    private Integer soluong;
    private Integer gia;

    public product(Integer ma, String ten, Integer phanLoai, byte[] hinh, Integer soluong, Integer gia) {
        this.ma = ma;
        this.ten = ten;
        this.phanLoai = phanLoai;
        this.hinh = hinh;
        this.soluong = soluong;
        this.gia = gia;
    }

    public product() {
    }

    public Integer getMa() {
        return ma;
    }

    public void setMa(Integer ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Integer getPhanLoai() {
        return phanLoai;
    }

    public void setPhanLoai(Integer phanLoai) {
        this.phanLoai = phanLoai;
    }

    public byte[] getHinh() {
        return hinh;
    }

    public void setHinh(byte[] hinh) {this.hinh = hinh;}

    public Integer getSoluong() {
        return soluong;
    }

    public void setSoluong(Integer soluong) {
        this.soluong = soluong;
    }

    public Integer getGia() {
        return gia;
    }

    public void setGia(Integer gia) {
        this.gia = gia;
    }

    @Override
    public String toString() {
        return
                "ma=" + ma +
                ", ten='" + ten + '\'' +
                ", phanLoai=" + phanLoai +
                ", hinh='" + hinh + '\'' +
                ", soluong=" + soluong +
                ", gia=" + gia +
                '}';
    }
}
