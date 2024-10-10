import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CurrencyDAO {
    public void insertCurrency(Currency currency) throws SQLException {
        String sql = "INSERT INTO Currency (CurrencyName, BuyRate, SellRate, YearOfIssue, Account) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, currency.getCurrencyName());
            pstmt.setBigDecimal(2, currency.getBuyRate());
            pstmt.setBigDecimal(3, currency.getSellRate());
            pstmt.setInt(4, currency.getYearOfIssue());
            pstmt.setString(5, currency.getAccount());
            pstmt.executeUpdate();
        }
    }

    public Currency getCurrency(String currencyName) throws SQLException {
        String sql = "SELECT * FROM Currency CurrencyName = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, currencyName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Currency(
                        rs.getString("CurrencyName"),
                        rs.getBigDecimal("BuyRate"),
                        rs.getBigDecimal("SellRate"),
                        rs.getInt("YearOfIssue"),
                        rs.getString("Account")
                );
            }
        }
        return null;
    }

    public void deleteCurrency(String currencyName) throws SQLException {
        String sql = "DELETE FROM Currency WHERE CurrencyName = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, currencyName);
            pstmt.executeUpdate();
        }
    }

    public void updateCurrency(Currency currency) throws SQLException {
        String sql = "UPDATE Currency SET BuyRate=?, SellRate=?, YearOfIssue=?, Account=? WHERE CurrencyName=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBigDecimal(1, currency.getBuyRate());
            pstmt.setBigDecimal(2, currency.getSellRate());
            pstmt.setInt(3, currency.getYearOfIssue());
            pstmt.setString(4, currency.getAccount());
            pstmt.setString(5, currency.getCurrencyName());
            pstmt.executeUpdate();
        }
    }

    public List<Currency> getTopCurrencies() throws SQLException {
        List<Currency> currencies = new ArrayList<>();
        String sql = "SELECT * FROM Currency LIMIT 5";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Currency currency = new Currency(
                        rs.getString("CurrencyName"),
                        rs.getBigDecimal("BuyRate"),
                        rs.getBigDecimal("SellRate"),
                        rs.getInt("YearOfIssue"),
                        rs.getString("Account")
                );
                currencies.add(currency);
            }
        }
        return currencies;
    }

    public List<Currency> getCurrenciesContainingLetter(String letter) throws SQLException {
        List<Currency> currencies = new ArrayList<>();
        String sql = "SELECT CurrencyName, BuyRate, SellRate FROM Currency WHERE CurrencyName LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + letter + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Currency currency = new Currency(
                            rs.getString("CurrencyName"),
                            rs.getBigDecimal("BuyRate"),
                            rs.getBigDecimal("SellRate"),
                            0,
                            null
                    );
                    currencies.add(currency);
                }
            }
        }
        return currencies;
    }
}