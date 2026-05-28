package Model.User;

public interface InterfaceDAOUser {
    // Kontrak untuk mengecek login admin berdasarkan username & password
    public ModelUser loginAdmin(String username, String password);
}