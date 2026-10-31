package com.heroGame.dao;

import com.heroGame.db.DBConnection;
import com.heroGame.model.National;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for the National table.
 * Covers: add new National, remove a National, list all National.
 */
public class NationalDAO {

    public List<National> getAll() throws SQLException {
        List<National> list = new ArrayList<>();
        String sql = "SELECT NationalId, NationalName FROM National ORDER BY NationalName";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new National(rs.getInt("NationalId"), rs.getString("NationalName")));
            }
        }
        return list;
    }

    public boolean add(String nationalName) throws SQLException {
        String sql = "INSERT INTO National (NationalName) VALUES (?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nationalName);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean remove(int nationalId) throws SQLException {
        String sql = "DELETE FROM National WHERE NationalId = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nationalId);
            return ps.executeUpdate() > 0;
        }
    }
}
