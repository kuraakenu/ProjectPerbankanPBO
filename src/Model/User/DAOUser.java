package Model.User;

import Model.Connector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOUser implements InterfaceDAOUser {
    
    private Connection connection;

    public DAOUser() {
        // Mengambil koneksi database terpusat dari kelas Connector
        this.connection = Connector.Connect();
    }

    @Override
    public ModelUser loginAdmin(String username, String password) {
        ModelUser user = null;
        // Menggunakan Prepared Statement untuk mencegah SQL Injection (Keamanan Bank!)
        String query = "SELECT * FROM user WHERE username = ? AND password = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new ModelUser();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error pada loginAdmin: " + e.getMessage());
        }
        
        return user; // Mengembalikan objek user jika ketemu, atau null jika gagal login
    }
}