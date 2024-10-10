import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PositionDAO {
    public void insertPosition(Position position) throws SQLException {
        String sql = "INSERT INTO Position (PositionName, PositionStatus) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, position.getPositionName());
            pstmt.setString(2, position.getPositionStatus());
            pstmt.executeUpdate();
        }
    }

    public Position getPosition(String positionName) throws SQLException {
        String sql = "SELECT * FROM `Position` WHERE PositionName = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, positionName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Position(
                        rs.getString("PositionName"),
                        rs.getString("PositionStatus")
                );
            }
        }
        return null;
    }

    public void deletePosition(String positionName) throws SQLException {
        String sql = "DELETE FROM Position WHERE PositionName = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, positionName);
            statement.executeUpdate();
        }
    }

    public void updatePosition(Position position) throws SQLException {
        String sql = "UPDATE Position SET PositionStatus=? WHERE PositionName=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, position.getPositionStatus());
            pstmt.setString(2, position.getPositionName());
            pstmt.executeUpdate();
        }
    }

    public List<Position> getPositionsStartingWith(String letter) throws SQLException {
        String sql = "SELECT PositionName, PositionStatus FROM Position WHERE PositionName LIKE ?";
        List<Position> positions = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + letter + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                positions.add(new Position(
                        rs.getString("PositionName"),
                        rs.getString("PositionStatus")
                ));
            }
        }
        return positions;
    }
}