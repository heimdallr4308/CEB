import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionsDAO {
    public void insertTransaction(Transactions transactions) throws SQLException {
        String sql = "INSERT INTO Transactions (TransactionCode, EmployeeName, CurrencyName, TransactionDate, TransactionTime) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, transactions.getTransactionCode());
            pstmt.setString(2, transactions.getEmployeeName());
            pstmt.setString(3, transactions.getCurrencyName());
            pstmt.setDate(4, transactions.getTransactionDate());
            pstmt.setTime(5, transactions.getTransactionTime());
            pstmt.executeUpdate();
        }
    }

    public Transactions getTransaction(int transactionCode) throws SQLException {
        String sql = "SELECT * FROM Transactions WHERE TransactionCode = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, transactionCode);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Transactions(
                        rs.getInt("TransactionCode"),
                        rs.getString("EmployeeName"),
                        rs.getString("CurrencyName"),
                        rs.getDate("TransactionDate"),
                        rs.getTime("TransactionTime")
                );
            }
        }
        return null;
    }

    public void deleteTransaction(int transactionCode) throws SQLException {
        String sql = "DELETE FROM Transactions WHERE TransactionCode = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, transactionCode);
            pstmt.executeUpdate();
        }
    }

    public void updateTransaction(Transactions transaction) throws SQLException {
        String sql = "UPDATE Transactions SET EmployeeName=?, CurrencyName=?, TransactionDate=?, TransactionTime=? WHERE TransactionCode=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, transaction.getEmployeeName());
            pstmt.setString(2, transaction.getCurrencyName());
            pstmt.setDate(3, transaction.getTransactionDate());
            pstmt.setTime(4, transaction.getTransactionTime());
            pstmt.setInt(5, transaction.getTransactionCode());
            pstmt.executeUpdate();
        }
    }

    public List<Transactions> getTransactionsStartingWith(String letter) throws SQLException {
        String sql = "SELECT TransactionCode, TransactionDate, TransactionTime FROM transactions WHERE TransactionCode LIKE ?";
        List<Transactions> transactionsList = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + letter + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Transactions transaction = new Transactions(
                        rs.getInt("TransactionCode"),
                        null,
                        null,
                        rs.getDate("TransactionDate"),
                        rs.getTime("TransactionTime")
                );
                transactionsList.add(transaction);
            }
        }
        return transactionsList;
    }
}