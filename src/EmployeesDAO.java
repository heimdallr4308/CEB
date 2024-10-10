import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeesDAO {
    public void insertEmployee(Employees employee) throws SQLException {
        String sql = "INSERT INTO Employees (EmployeeName, DateOfBirth, PositionName) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, employee.getEmployeeName());
            pstmt.setDate(2, employee.getDateOfBirth());
            pstmt.setString(3, employee.getPositionName());
            pstmt.executeUpdate();
        }
    }

    public Employees getEmployees(String employeeName) throws SQLException {
        String sql = "SELECT * FROM Employees WHERE EmployeeName = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, employeeName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Employees(
                        rs.getString("EmployeeName"),
                        rs.getDate("DateOfBirth"),
                        rs.getString("PositionName")
                );
            }
        }
        return null;
    }

    public void deleteEmployee(String employeeName) throws SQLException {
        String sql = "DELETE FROM Employees WHERE EmployeeName = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, employeeName);
            pstmt.executeUpdate();
        }
    }

    public void updateEmployee(Employees employee) throws SQLException {
        String sql = "UPDATE Employees SET DateOfBirth=?, PositionName=? WHERE EmployeeName=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, employee.getDateOfBirth());
            pstmt.setString(2, employee.getPositionName());
            pstmt.setString(3, employee.getEmployeeName());
            pstmt.executeUpdate();
        }
    }

    public List<Employees> searchEmployeesByLetter(String letter) throws SQLException {
        List<Employees> employeesList = new ArrayList<>();
        String sql = "SELECT * FROM Employees WHERE EmployeeName LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + letter + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Employees employee = new Employees(
                            rs.getString("EmployeeName"),
                            rs.getDate("DateOfBirth"),
                            rs.getString("PositionName")
                    );
                    employeesList.add(employee);
                }
            }
        }
        return employeesList;
    }
}