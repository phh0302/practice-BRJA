-- =========================================================
-- HeroGame Database
-- Java Programming 2 - Set01 Practical Paper
-- =========================================================

DROP DATABASE IF EXISTS HeroGame;
CREATE DATABASE HeroGame;
USE HeroGame;

-- ---------------------------------------------------------
-- Table: National
-- ---------------------------------------------------------
CREATE TABLE National (
    NationalId   INT AUTO_INCREMENT PRIMARY KEY,
    NationalName VARCHAR(100) NOT NULL UNIQUE
);

-- ---------------------------------------------------------
-- Table: Player
-- ---------------------------------------------------------
CREATE TABLE Player (
    PlayerId   INT AUTO_INCREMENT PRIMARY KEY,
    NationalId INT NOT NULL,
    PlayerName VARCHAR(100) NOT NULL,
    HighScore  INT NOT NULL DEFAULT 0,
    Level      INT NOT NULL DEFAULT 1,
    CONSTRAINT fk_player_national
        FOREIGN KEY (NationalId) REFERENCES National(NationalId)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

-- ---------------------------------------------------------
-- Sample data (matches Table 1 in the question)
-- ---------------------------------------------------------
INSERT INTO National (NationalName) VALUES
    ('Vietnam'),
    ('USA'),
    ('Japan');

INSERT INTO Player (NationalId, PlayerName, HighScore, Level) VALUES
    (1, 'Player 1', 100,  2),
    (2, 'Player 2', 1050, 10),
    (3, 'Player 3', 200,  5);
