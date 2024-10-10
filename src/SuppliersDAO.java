import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SuppliersDAO {
    public void insertSupplier(Suppliers supplier) throws SQLException {
        String sql = "INSERT INTO Suppliers (Account, PhoneNumber, ContactPerson, Email) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, supplier.getAccount());
            pstmt.setString(2, supplier.getPhoneNumber());
            pstmt.setString(3, supplier.getContactPerson());
            pstmt.setString(4, supplier.getEmail());
            pstmt.executeUpdate();
        }
    }

    public Suppliers getSupplier(String account) throws SQLException {
        String sql = "SELECT * FROM Suppliers WHERE Account = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, account);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Suppliers(
                        rs.getString("Account"),
                        rs.getString("PhoneNumber"),
                        rs.getString("ContactPerson"),
                        rs.getString("Email")
                );
            }
        }
        return null;
    }

    public void deleteSupplier(String account) throws SQLException {
        String sql = "DELETE FROM Suppliers WHERE Account = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, account);
            pstmt.executeUpdate();
        }
    }

    public void updateSupplier(Suppliers supplier) throws SQLException {
        String sql = "UPDATE Suppliers SET PhoneNumber=?, ContactPerson=?, Email=?, Account=? WHERE Account=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, supplier.getPhoneNumber());
            pstmt.setString(2, supplier.getContactPerson());
            pstmt.setString(3, supplier.getEmail());
            pstmt.setString(4, supplier.getAccount());
            pstmt.setString(5, supplier.getAccount());
            pstmt.executeUpdate();
        }
    }

    public List<Suppliers> searchSuppliersByStartingLetter(String letter) throws SQLException {
        List<Suppliers> suppliersList = new ArrayList<>();
        String sql = "SELECT * FROM Suppliers WHERE ContactPerson LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + letter + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Suppliers suppliers = new Suppliers(
                            rs.getString("Account"),
                            rs.getString("PhoneNumber"),
                            rs.getString("ContactPerson"),
                            rs.getString("Email")
                    );
                    suppliersList.add(suppliers);
                }
            }
        }
        return suppliersList;
    }
}