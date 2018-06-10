package com.gonjansama.tennis.internal;

import com.gonjansama.tennis.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SetImpl implements com.gonjansama.tennis.Set {
    private final java.util.List<GameImpl> games = new ArrayList<>();
    private final MatchImpl match;
    private final Map<Player, Integer> score = new HashMap<>();
    private GameImpl currentGame;
    private Map<Player, Integer> tieBreakScore;
    private Boolean tieBreakScoreInitialized = false;
     

    public SetImpl(MatchImpl match) {
        this.match = match;
        score.put(match.getPlayer1(), 0);
        score.put(match.getPlayer2(), 0);
        currentGame = new GameImpl(this);
        games.add(currentGame);
    }

    @Override
    public Player getPlayer1() {
        return match.getPlayer1();
    }

    @Override
    public Player getPlayer2() {
        return match.getPlayer2();
    }

    @Override
    public Integer getPlayerScore(Player player) {
        return score.get(getPlayer(player.getName()));
    }

    public void setPlayerScore(Player player, Integer playerScore) {
        Integer opponentScore = getOpponentScore(player);
        if (!ScoringAlgorithm.isValidSetScore(opponentScore, playerScore)) {
            throw new IllegalArgumentException("Can not set player score with : " + playerScore + " when his opponent have : " + opponentScore);
        }
        score.put(getPlayer(player.getName()), playerScore);
        if (isOnTieBreak()) {
            if (!tieBreakScoreInitialized) {
              initializedTieBreakScore();
            }
        }
    }

    private void initializedTieBreakScore() {
        tieBreakScore = new HashMap<>();
        tieBreakScore.put(match.getPlayer1(), 0);
        tieBreakScore.put(match.getPlayer2(), 0);
        tieBreakScoreInitialized = true;
    }

    public void setPlayerTieBreakScore(Player player, Integer playerScore) {
        if (!isOnTieBreak()) throw new IllegalStateException("Can not set tiebreak score for a player when the set is not on tiebreak");
        Integer opponentScore = getOpponentTieBreakScore(player);
        if (!ScoringAlgorithm.isValidTieBreakScore(opponentScore, playerScore)) {
            throw new IllegalArgumentException("Can not set player tiebreak score with : " + playerScore + " when his opponent have : " + opponentScore);
        }
        tieBreakScore.put(getPlayer(player.getName()), playerScore);
    }

    @Override
    public Integer getPlayerTieBreakScore(Player player) {
        if (!isOnTieBreak()) throw new IllegalStateException("Can not get tiebreak score for a player when the set is not on tiebreak");
        return tieBreakScore.get(getPlayer(player.getName()));
    }

    @Override
    public GameImpl createGame() {
        if (currentGame != null) {
            throw new IllegalStateException("A Game is already launched for this set");
        }
        if (!isInProgress()) {
            throw new IllegalStateException("Can not create game for a finished set");
        }
        currentGame = new GameImpl(this);
        games.add(currentGame);
        return currentGame;
    }

    @Override
    public GameImpl getCurrentGame() {
        if (!isInProgress()) {
            throw new IllegalStateException("Can not get current game for a finished set");
        }
        return currentGame;
    }

    public void setCurrentGame(GameImpl game) {
        if (currentGame != null) throw new IllegalStateException("A Game is already launched for this set");
        currentGame = game;
        games.add(game);
    }

    public Integer getOpponentScore(Player player) {
        player = getPlayer(player.getName());
        return player.equals(match.getPlayer1()) ? getPlayerScore(match.getPlayer2()) : getPlayerScore(match.getPlayer1());
    }

    public Integer getOpponentTieBreakScore(Player player) {
        player = getPlayer(player.getName());
        return player.equals(match.getPlayer1()) ? getPlayerTieBreakScore(match.getPlayer2()) : getPlayerTieBreakScore(match.getPlayer1());
    }

    public Player getOpponent(Player player) {
        player = getPlayer(player.getName());
        return player.equals(match.getPlayer1()) ? match.getPlayer2() : match.getPlayer1();
    }

    public Integer getPlayer1Score() {
        return score.get(match.getPlayer1());
    }

    public Integer getPlayer2Score() {
        return score.get(match.getPlayer2());
    }

    @Override
    public Player getPlayer(String name) {
        return match.getPlayer(name);
    }

    public Set<GameImpl> getGames() {
        return new HashSet<>(games);
    }

    void updateScore() {
        if (currentGame.hasWinner()) {
            Player gameWinner = currentGame.getWinner();
            Map<String, Integer> updatedScores = ScoringAlgorithm.calculatePlayerSetScore(gameWinner, this);
            if (!isOnTieBreak()) {
                setPlayerScore(gameWinner, updatedScores.get("setScore"));
            } else {
                setPlayerTieBreakScore(gameWinner, updatedScores.get("tiebreakScore"));
            }
            currentGame = null;
        }
    }

    public Boolean hasWinner() {
        return winBy(match.getPlayer1()) || winBy(match.getPlayer2());
    }

    public Boolean isValid() {
        return hasWinner().equals(true) || isInProgress().equals(true);
    }

    public Boolean isInProgress() {
        return (!hasWinner() && getPlayer1Score() <= 6 && getPlayer2Score() <= 6) ||
               (isOnTieBreak() && Math.max(getPlayerTieBreakScore(match.getPlayer1()), getPlayerTieBreakScore(match.getPlayer2())) < 7) ||
               (isOnTieBreak()
                   && Math.max(getPlayerTieBreakScore(match.getPlayer1()), getPlayerTieBreakScore(match.getPlayer2())) >= 7
                   && (Math.abs(getPlayerTieBreakScore(match.getPlayer1()) - getPlayerTieBreakScore(match.getPlayer2()))) < 2);
    }

    private Boolean winBy(Player player) {
        return (getPlayerScore(player).equals(7) && (getOpponentScore(player) >= 5 && getOpponentScore(player) < 7)) ||
                (getPlayerScore(player).equals(6) && (getOpponentScore(player) <= 4)) ||
                (isOnTieBreak()
                    && Integer.valueOf(getPlayerTieBreakScore(player) - getOpponentTieBreakScore(player)).equals(2)
                    && getPlayerTieBreakScore(player) > 7) ||
                (isOnTieBreak()
                        && (getPlayerTieBreakScore(player) - getOpponentTieBreakScore(player)) >= 2
                        && getPlayerTieBreakScore(player) == 7);
    }

    @Override
    public Player getWinner() {
        if (isInProgress()) throw new IllegalStateException("Can not get winner for a set in progress");
        return winBy(match.getPlayer1()) ? match.getPlayer1() : winBy(match.getPlayer2()) ? match.getPlayer2() : null;
    }

    @Override
    public Boolean isOnTieBreak() {
        return getPlayer1Score().equals(6) && getPlayer2Score().equals(6);
    }
}
