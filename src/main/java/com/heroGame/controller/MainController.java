package com.heroGame.controller;

import com.heroGame.dao.NationalDAO;
import com.heroGame.dao.PlayerDAO;
import com.heroGame.model.National;
import com.heroGame.model.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MainController {

    @FXML private TableView<Player> playerTable;
    @FXML private TableColumn<Player, Integer> colPlayerId;
    @FXML private TableColumn<Player, String> colPlayerName;
    @FXML private TableColumn<Player, Integer> colHighScore;
    @FXML private TableColumn<Player, Integer> colLevel;
    @FXML private TableColumn<Player, String> colNational;

    @FXML private TextField txtPlayerName;
    @FXML private TextField txtHighScore;
    @FXML private TextField txtLevel;
    @FXML private ComboBox<National> cmbNational;

    @FXML private TextField txtNewNational;
    @FXML private TextField txtSearchName;

    @FXML private Label lblStatus;

    private final PlayerDAO playerDAO = new PlayerDAO();
    private final NationalDAO nationalDAO = new NationalDAO();

    private final ObservableList<Player> playerData = FXCollections.observableArrayList();
    private final ObservableList<National> nationalData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colPlayerId.setCellValueFactory(new PropertyValueFactory<>("playerId"));
        colPlayerName.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        colHighScore.setCellValueFactory(new PropertyValueFactory<>("highScore"));
        colLevel.setCellValueFactory(new PropertyValueFactory<>("level"));
        colNational.setCellValueFactory(new PropertyValueFactory<>("nationalName"));
        playerTable.setItems(playerData);

        // Fill the form when a row is selected
        playerTable.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV != null) {
                txtPlayerName.setText(newV.getPlayerName());
                txtHighScore.setText(String.valueOf(newV.getHighScore()));
                txtLevel.setText(String.valueOf(newV.getLevel()));
                for (National n : nationalData) {
                    if (n.getNationalId() == newV.getNationalId()) {
                        cmbNational.getSelectionModel().select(n);
                        break;
                    }
                }
            }
        });

        loadNationals();
        loadAllPlayers();
    }

    // ---------------------------------------------------------------
    // Players
    // ---------------------------------------------------------------

    @FXML
    private void onAddPlayer() {
        try {
            String name = txtPlayerName.getText().trim();
            National national = cmbNational.getSelectionModel().getSelectedItem();

            if (name.isEmpty() || national == null) {
                showStatus("Please enter a player name and select a national.");
                return;
            }

            int highScore = parseIntOrZero(txtHighScore.getText());
            int level = parseIntOrZero(txtLevel.getText());
            if (level <= 0) level = 1;

            Player player = new Player(0, national.getNationalId(), name, highScore, level);
            playerDAO.insertPlayer(player);
            showStatus("Player added successfully.");
            onClearForm();
            loadAllPlayers();
        } catch (SQLException e) {
            showStatus("Error adding player: " + e.getMessage());
        }
    }

    @FXML
    private void onRemovePlayer() {
        Player selected = playerTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showStatus("Select a player in the table first.");
            return;
        }
        try {
            playerDAO.deletePlayer(selected.getPlayerId());
            showStatus("Player removed.");
            loadAllPlayers();
        } catch (SQLException e) {
            showStatus("Error removing player: " + e.getMessage());
        }
    }

    @FXML
    private void onClearForm() {
        txtPlayerName.clear();
        txtHighScore.clear();
        txtLevel.clear();
        cmbNational.getSelectionModel().clearSelection();
        playerTable.getSelectionModel().clearSelection();
    }

    @FXML
    private void onShowAll() {
        loadAllPlayers();
    }

    @FXML
    private void onFindByName() {
        String keyword = txtSearchName.getText().trim();
        if (keyword.isEmpty()) {
            loadAllPlayers();
            return;
        }
        try {
            List<Player> result = playerDAO.displayAllByPlayerName(keyword);
            playerData.setAll(result);
            showStatus(result.isEmpty() ? "No player found." : result.size() + " player(s) found.");
        } catch (SQLException e) {
            showStatus("Error searching player: " + e.getMessage());
        }
    }

    @FXML
    private void onTop10() {
        try {
            List<Player> result = playerDAO.displayTop10();
            playerData.setAll(result);
            showStatus("Showing top " + result.size() + " players by High Score.");
        } catch (SQLException e) {
            showStatus("Error loading top players: " + e.getMessage());
        }
    }

    private void loadAllPlayers() {
        try {
            playerData.setAll(playerDAO.displayAll());
            showStatus("");
        } catch (SQLException e) {
            showStatus("Error loading players: " + e.getMessage());
        }
    }

    // ---------------------------------------------------------------
    // Nationals
    // ---------------------------------------------------------------

    @FXML
    private void onAddNational() {
        String name = txtNewNational.getText().trim();
        if (name.isEmpty()) {
            showStatus("Enter a national name first.");
            return;
        }
        try {
            nationalDAO.add(name);
            txtNewNational.clear();
            loadNationals();
            showStatus("National added.");
        } catch (SQLException e) {
            showStatus("Error adding national: " + e.getMessage());
        }
    }

    @FXML
    private void onRemoveNational() {
        National selected = cmbNational.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showStatus("Select a national from the combo box first.");
            return;
        }
        try {
            nationalDAO.remove(selected.getNationalId());
            loadNationals();
            showStatus("National removed.");
        } catch (SQLException e) {
            showStatus("Cannot remove national (it may still be used by a player): " + e.getMessage());
        }
    }

    private void loadNationals() {
        try {
            nationalData.setAll(nationalDAO.getAll());
            cmbNational.setItems(nationalData);
        } catch (SQLException e) {
            showStatus("Error loading nationals: " + e.getMessage());
        }
    }

    // ---------------------------------------------------------------
    // Helpers
    // ---------------------------------------------------------------

    private int parseIntOrZero(String text) {
        try {
            return Integer.parseInt(text.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    private void showStatus(String message) {
        lblStatus.setText(message);
    }
}
