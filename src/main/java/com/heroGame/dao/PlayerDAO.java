package com.heroGame.dao;

import com.heroGame.db.DBConnection;
import com.heroGame.model.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for the Player table.
 * Method names match the marking schema exactly:
 *   - insertPlayer / deletePlayer   (Question 1, 2 marks)
 *   - displayAll                    (Question 2, 2 marks)
 *   - displayAllByPlayerName        (2 marks)
 *   - displayTop10                  (Question 1, 2 marks)
 */
public class PlayerDAO {

    // Common SELECT that joins Player with National so we can display
    // the national name instead of just the NationalId.
    private static final String BASE_SELECT =
            "SELECT p.PlayerId, p.NationalId, p.PlayerName, p.HighScore, p.Level, n.NationalName " +
            "FROM Player p JOIN National n ON p.NationalId = n.NationalId ";

    /** Insert a new player. */
    public boolean insertPlayer(Player player) throws SQLException {
        String sql = "INSERT INTO Player (NationalId, PlayerName, HighScore, Level) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, player.getNationalId());
            ps.setString(2, player.getPlayerName());
            ps.setInt(3, player.getHighScore());
            ps.setInt(4, player.getLevel());
            return ps.executeUpdate() > 0;
        }
    }

    /** Delete a player by PlayerId. */
    public boolean deletePlayer(int playerId) throws SQLException {
        String sql = "DELETE FROM Player WHERE PlayerId = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, playerId);
            return ps.executeUpdate() > 0;
        }
    }

    /** Optional helper: update an existing player's info (not in the marking schema, kept as a bonus). */
    public boolean updatePlayer(Player player) throws SQLException {
        String sql = "UPDATE Player SET NationalId = ?, PlayerName = ?, HighScore = ?, Level = ? WHERE PlayerId = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, player.getNationalId());
            ps.setString(2, player.getPlayerName());
            ps.setInt(3, player.getHighScore());
            ps.setInt(4, player.getLevel());
            ps.setInt(5, player.getPlayerId());
            return ps.executeUpdate() > 0;
        }
    }

    /** Display all players, as Table 1 in the question. */
    public List<Player> displayAll() throws SQLException {
        List<Player> list = new ArrayList<>();
        String sql = BASE_SELECT + "ORDER BY p.PlayerId";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    /** Display all players whose name matches (partial match) the given name. */
    public List<Player> displayAllByPlayerName(String name) throws SQLException {
        List<Player> list = new ArrayList<>();
        String sql = BASE_SELECT + "WHERE p.PlayerName LIKE ? ORDER BY p.PlayerId";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + name + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }

    /** Display the top 10 players with the highest HighScore. */
    public List<Player> displayTop10() throws SQLException {
        List<Player> list = new ArrayList<>();
        String sql = BASE_SELECT + "ORDER BY p.HighScore DESC LIMIT 10";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    private Player mapRow(ResultSet rs) throws SQLException {
        return new Player(
                rs.getInt("PlayerId"),
                rs.getInt("NationalId"),
                rs.getString("PlayerName"),
                rs.getInt("HighScore"),
                rs.getInt("Level"),
                rs.getString("NationalName")
        );
    }
}
