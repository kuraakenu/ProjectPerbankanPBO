package Controller;

import Model.Rekening.DAORekening;
import Model.Rekening.ModelRekening;
import Model.Transaksi.DAOTransaksi;
import Model.Transaksi.ModelTransaksi;
import Model.Transaksi.ModelTableTransaksi;
import java.util.List;
import javax.swing.JOptionPane;

public class TransaksiController {

    private DAORekening daoRekening;
    private DAOTransaksi daoTransaksi;

    public TransaksiController() {
        this.daoRekening = new DAORekening();
        this.daoTransaksi = new DAOTransaksi();
    }

    /**
     * FUNGSI TRANSFER ANTAR NASABAH (Menerapkan Multithreading & Error Handling)
     */
    public void prosesTransfer(ModelRekening akunLogin, String noRekTujuan, double nominal, ModelTableTransaksi tableModel) {
        
        // 1. VALIDASI ERROR HANDLING AWAL (Diperiksa instan sebelum masuk Thread)
        if (noRekTujuan.trim().isEmpty() || nominal <= 0) {
            JOptionPane.showMessageDialog(null, "Input tidak valid! Periksa kembali nomor rekening dan nominal.", "Gagal", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (akunLogin.getNoRek().equals(noRekTujuan)) {
            JOptionPane.showMessageDialog(null, "Tidak bisa mentransfer ke nomor rekening Anda sendiri!", "Gagal", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (akunLogin.getSaldo() < nominal) {
            JOptionPane.showMessageDialog(null, "Transfer Gagal! Saldo Anda tidak mencukupi.\nSaldo saat ini: Rp " + akunLogin.getSaldo(), "Saldo Kurang", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2. PENERAPAN MULTITHREADING (Proses berat ke database dilempar ke Background Thread)
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Simulasi delay jaringan perbankan (opsional, agar efek loading di UI terasa mulus)
                    Thread.sleep(500); 

                    // Cek apakah rekening tujuan ada di database
                    ModelRekening rekTujuan = daoRekening.cariRekening(noRekTujuan);
                    if (rekTujuan == null) {
                        JOptionPane.showMessageDialog(null, "Error: Nomor rekening tujuan '" + noRekTujuan + "' tidak ditemukan!", "Rekening Tidak Ada", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Bungkus data ke dalam ModelTransaksi
                    ModelTransaksi trx = new ModelTransaksi();
                    trx.setJenis("Transfer");
                    trx.setJumlah(nominal);
                    trx.setRekAsal(akunLogin.getId()); // ID primary key rekening asal
                    trx.setRekTujuan(rekTujuan.getId()); // ID primary key rekening tujuan

                    // Kirim ke DAO untuk dieksekusi (menggunakan mekanisme commit/rollback)
                    boolean sukses = daoTransaksi.tambahTransaksi(trx);

                    if (sukses) {
                        // Update saldo objek lokal yang sedang login agar sinkron dengan database
                        akunLogin.setSaldo(akunLogin.getSaldo() - nominal);
                        
                        JOptionPane.showMessageDialog(null, "Transfer Sukses!\nKe Rekening: " + noRekTujuan + "\nNominal: Rp " + nominal, "Sukses", JOptionPane.INFORMATION_MESSAGE);
                        
                        // Refresh tabel riwayat transaksi milik nasabah di UI secara real-time
                        if (tableModel != null) {
                            List<ModelTransaksi> listBaru = daoTransaksi.getTransaksiByRekening(akunLogin.getId());
                            tableModel.setList(listBaru);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Terjadi kesalahan sistem di database. Transaksi dibatalkan!", "Sistem Error", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (InterruptedException e) {
                    System.out.println("Thread Transaksi Terganggu: " + e.getMessage());
                }
            }
        }).start(); // Jalankan thread transaksi
    }

    /**
     * FUNGSI SETOR TUNAI
     */
    public void prosesSetorTunai(ModelRekening akunLogin, double nominal, ModelTableTransaksi tableModel) {
        if (nominal <= 0) {
            JOptionPane.showMessageDialog(null, "Nominal setoran harus lebih dari 0!", "Input Salah", JOptionPane.WARNING_MESSAGE);
            return;
        }

        new Thread(() -> {
            ModelTransaksi trx = new ModelTransaksi();
            trx.setJenis("Setor Tunai");
            trx.setJumlah(nominal);
            trx.setRekAsal(0); // 0 menandakan tidak ada rekening asal (setor manual)
            trx.setRekTujuan(akunLogin.getId());

            if (daoTransaksi.tambahTransaksi(trx)) {
                akunLogin.setSaldo(akunLogin.getSaldo() + nominal);
                JOptionPane.showMessageDialog(null, "Setor Tunai Berhasil!\nSaldo Anda bertambah: Rp " + nominal, "Sukses", JOptionPane.INFORMATION_MESSAGE);
                if (tableModel != null) {
                    tableModel.setList(daoTransaksi.getTransaksiByRekening(akunLogin.getId()));
                }
            }
        }).start();
    }

    /**
     * FUNGSI TARIK TUNAI
     */
    public void prosesTarikTunai(ModelRekening akunLogin, double nominal, ModelTableTransaksi tableModel) {
        if (nominal <= 0) {
            JOptionPane.showMessageDialog(null, "Nominal penarikan harus lebih dari 0!", "Input Salah", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (akunLogin.getSaldo() < nominal) {
            JOptionPane.showMessageDialog(null, "Tarik Tunai Gagal! Saldo Anda tidak mencukupi.", "Saldo Kurang", JOptionPane.ERROR_MESSAGE);
            return;
        }

        new Thread(() -> {
            ModelTransaksi trx = new ModelTransaksi();
            trx.setJenis("Tarik Tunai");
            trx.setJumlah(nominal);
            trx.setRekAsal(akunLogin.getId());
            trx.setRekTujuan(0); // 0 menandakan tidak ada rekening tujuan (diambil tunai)

            if (daoTransaksi.tambahTransaksi(trx)) {
                akunLogin.setSaldo(akunLogin.getSaldo() - nominal);
                JOptionPane.showMessageDialog(null, "Tarik Tunai Berhasil!\nUang ditarik: Rp " + nominal, "Sukses", JOptionPane.INFORMATION_MESSAGE);
                if (tableModel != null) {
                    tableModel.setList(daoTransaksi.getTransaksiByRekening(akunLogin.getId()));
                }
            }
        }).start();
    }
}