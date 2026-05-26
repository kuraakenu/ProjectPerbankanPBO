package Model.Rekening;

public class ModelRekening {
    private Integer id;
    private String noRek;
    private String jenis;
    private String pin; // Atribut baru untuk enkapsulasi PIN Nasabah
    private int idNasabah;
    private double saldo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNoRek() {
        return noRek;
    }

    public void setNoRek(String noRek) {
        this.noRek = noRek;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    // Getter dan Setter Baru untuk PIN
    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        // Validasi dasar: PIN perbankan umumnya harus 6 digit angka
        if (pin != null && pin.length() == 6) {
            this.pin = pin;
        } else {
            this.pin = "123456"; // Default jika input tidak valid
        }
    }

    public int getIdNasabah() {
        return idNasabah;
    }

    public void setIdNasabah(int idNasabah) {
        this.idNasabah = idNasabah;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}