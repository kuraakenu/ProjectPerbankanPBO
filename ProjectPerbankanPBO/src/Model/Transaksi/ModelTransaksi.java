/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Transaksi;

public class ModelTransaksi {
    private Integer id;
    private String jenis, tanggal;
    private double jumlah;
    private int rekAsal, rekTujuan;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public double getJumlah() {
        return jumlah;
    }

    public void setJumlah(double jumlah) {
        this.jumlah = jumlah;
    }

    public int getRekAsal() {
        return rekAsal;
    }

    public void setRekAsal(int rekAsal) {
        this.rekAsal = rekAsal;
    }

    public int getRekTujuan() {
        return rekTujuan;
    }

    public void setRekTujuan(int rekTujuan) {
        this.rekTujuan = rekTujuan;
    }
    
}
