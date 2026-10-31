package com.heroGame.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Entity class mapped to the Player table.
 * Setters, getters and a no-arg + full constructor as required by the
 * marking schema ("Create Entity Player, National with setter, getter,
 * constructor").
 */
public class Player {

    private final IntegerProperty playerId;
    private final IntegerProperty nationalId;
    private final StringProperty playerName;
    private final IntegerProperty highScore;
    private final IntegerProperty level;

    // Extra, display-only field: not a DB column, filled in by the DAO join
    private final StringProperty nationalName;

    public Player() {
        this(0, 0, "", 0, 1, "");
    }

    public Player(int playerId, int nationalId, String playerName, int highScore, int level) {
        this(playerId, nationalId, playerName, highScore, level, "");
    }

    public Player(int playerId, int nationalId, String playerName, int highScore, int level, String nationalName) {
        this.playerId = new SimpleIntegerProperty(playerId);
        this.nationalId = new SimpleIntegerProperty(nationalId);
        this.playerName = new SimpleStringProperty(playerName);
        this.highScore = new SimpleIntegerProperty(highScore);
        this.level = new SimpleIntegerProperty(level);
        this.nationalName = new SimpleStringProperty(nationalName);
    }

    public int getPlayerId() {
        return playerId.get();
    }

    public void setPlayerId(int playerId) {
        this.playerId.set(playerId);
    }

    public IntegerProperty playerIdProperty() {
        return playerId;
    }

    public int getNationalId() {
        return nationalId.get();
    }

    public void setNationalId(int nationalId) {
        this.nationalId.set(nationalId);
    }

    public IntegerProperty nationalIdProperty() {
        return nationalId;
    }

    public String getPlayerName() {
        return playerName.get();
    }

    public void setPlayerName(String playerName) {
        this.playerName.set(playerName);
    }

    public StringProperty playerNameProperty() {
        return playerName;
    }

    public int getHighScore() {
        return highScore.get();
    }

    public void setHighScore(int highScore) {
        this.highScore.set(highScore);
    }

    public IntegerProperty highScoreProperty() {
        return highScore;
    }

    public int getLevel() {
        return level.get();
    }

    public void setLevel(int level) {
        this.level.set(level);
    }

    public IntegerProperty levelProperty() {
        return level;
    }

    public String getNationalName() {
        return nationalName.get();
    }

    public void setNationalName(String nationalName) {
        this.nationalName.set(nationalName);
    }

    public StringProperty nationalNameProperty() {
        return nationalName;
    }
}
