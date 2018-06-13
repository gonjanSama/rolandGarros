package com.gonjansama.tennis.internal;

import com.gonjansama.tennis.Game;
import com.gonjansama.tennis.Player;
import com.gonjansama.tennis.Set;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GameImpl implements Game {
    private final Set set;
    private final Map<Player, GamePoint> score = new HashMap<>();

    public GameImpl(Set set) {
        score.put(set.getPlayer1(), GamePoint.ZERO);
        score.put(set.getPlayer2(), GamePoint.ZERO);
        this.set = set;
        this.set.setCurrentGame(this);
    }

    public GamePoint getPlayer1Score() {
        return score.get(set.getPlayer1());
    }

    public GamePoint getPlayer2Score() {
        return score.get(set.getPlayer2());
    }

    @Override
    public Player getPlayer(String name) {
        return set.getPlayer(name);
    }

    public Player getPlayer1() {
        return set.getPlayer1();
    }

    public Player getPlayer2() {
        return set.getPlayer2();
    }


    @Override
    public String getPlayerScore(Player player) {
        return score.get(getPlayer(player.getName())).getName();
    }

    public String getOpponentScore(Player player) {
        player = getPlayer(player.getName());
       return player.equals(set.getPlayer1()) ? getPlayer2Score().getName() : getPlayer1Score().getName();
    }

    public Player getOpponent(Player player) {
        return  set.getOpponent(player);
    }

    Map<Player, GamePoint> getScore() {
        return score;
    }

    @Override
    public Boolean isOnDeuce() {
        List<GamePoint> pointsTocheckDeuce = Arrays.asList(GamePoint.FOURTHY, GamePoint.ADVANTAGE);
        return pointsTocheckDeuce.contains(getPlayer1Score()) && getPlayer2Score().equals(GamePoint.FOURTHY) ||
                pointsTocheckDeuce.contains(getPlayer2Score()) && getPlayer1Score().equals(GamePoint.FOURTHY);
    }

    @Override
    public Optional<Player> getWinner() {
        return winBy(set.getPlayer1()) ? Optional.of(set.getPlayer1()) :
                winBy(set.getPlayer2()) ? Optional.of(set.getPlayer2()) : Optional.empty();
    }

    @Override
    public void addPoint(Player player) {
        if (!isInProgress()) {
            throw new IllegalStateException("Can not add point to player when the game is ended");
        }
        Map<Player, GamePoint> newScore = ScoringAlgorithm.calculateNextGameScore(getPlayer(player.getName()), this);
        score.putAll(newScore);
        set.updateScore();
    }

    public Boolean isValid() {
        return hasWinner().equals(true) ||
               isInProgress();
    }

    public Boolean isInProgress() {
        return isOnDeuce().equals(true) ||
                (getPlayer1Score().getWeight() <= GamePoint.FOURTHY.getWeight()
                        && getPlayer2Score().getWeight() <= GamePoint.FOURTHY.getWeight());
    }

    public Boolean hasWinner() {
        return  winBy(set.getPlayer1()) || winBy(set.getPlayer2());
    }

    private Boolean winBy(Player player) {
        return score.get(getPlayer(player.getName())).equals(GamePoint.WIN_POINT)
                && score.get(getOpponent(player)).getWeight() <= GamePoint.FOURTHY.getWeight();
    }
}
