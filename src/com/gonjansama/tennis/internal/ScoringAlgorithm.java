package com.gonjansama.tennis.internal;

import com.gonjansama.tennis.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class ScoringAlgorithm {

    static Map<String, Integer> calculatePlayerSetScore(Player gameWinner, SetImpl set) {
        Map<String, Integer> result = new HashMap<>();
        if (!set.isInProgress()) {
            throw new IllegalArgumentException("Can not calculate Player Score for a finished Set");
        }
        if (!set.isOnTieBreak()) {
            Integer playerScore = set.getPlayerScore(gameWinner);
            result.put("setScore", playerScore + 1);
        } else {
            Integer playerTieBreakScore = set.getPlayerTieBreakScore(gameWinner);
            result.put("tiebreakScore", playerTieBreakScore + 1);
        }
        return result;
    }

    static Boolean isValidSetScore(Integer player1Score, Integer player2Score) {
        return (player1Score >= 0 && player2Score >= 0) &&
                (Math.max(player1Score, player2Score) <= 7) && !(player1Score.equals(7) && player2Score.equals(7));
    }

    static Boolean isValidTieBreakScore(Integer player1Score, Integer player2Score) {
        List<Integer> allowDiffsOnTieBreak = Stream.of(1, 2).collect(Collectors.toList());
        return player1Score >= 0 && player2Score >= 0 &&
                (Math.max(player1Score, player2Score) < 7 || (Math.max(player1Score, player2Score) >= 7
                        && allowDiffsOnTieBreak.contains(Math.abs(player1Score - player2Score))));
    }

    static Map<Player, GamePoint> calculateNextGameScore(Player scorer, GameImpl game) {
        Map<Player, GamePoint> gameScore = game.getScore();
        if (!game.isInProgress()) {
            throw new IllegalArgumentException("Can not calculate Player Point for a finished Game");
        }
        if (!game.isOnDeuce()) {
            gameScore.put(scorer, calculatePlayerNextPoint(scorer, game));
        } else {
            gameScore = calculateGameScoreOnDeuce(scorer, game);
        }
        return gameScore;
    }

    private static List<GamePointTransition> transitions() {
        GamePointTransition zeroToFifteen = new GamePointTransition(GamePoint.ZERO, GamePoint.FIFTEEN);
        GamePointTransition fifteenToThirthy = new GamePointTransition(GamePoint.FIFTEEN, GamePoint.THIRTHY);
        GamePointTransition thirhtyToFourthy = new GamePointTransition(GamePoint.THIRTHY, GamePoint.FOURTHY);
        GamePointTransition fourthyToSucces = new GamePointTransition(GamePoint.FOURTHY, GamePoint.WIN_POINT);
        return Stream.of(zeroToFifteen, fifteenToThirthy, thirhtyToFourthy, fourthyToSucces).collect(Collectors.toList());
    }

    private static GamePoint calculatePlayerNextPoint(Player scorer, GameImpl game) {
        GamePoint scorerScore = game.getScore().get(scorer);
        for (GamePointTransition transition : transitions()) {
            boolean gamePointMatch = transition.getFrom().equals(scorerScore);
            if (gamePointMatch) {
                return transition.getTo();
            }
        }
        throw new IllegalArgumentException("Can not calculate Player Point, the game is on deuce or has an invalid score");
    }

    private static Map<Player, GamePoint> calculateGameScoreOnDeuce(Player scorer, GameImpl game) {
        Map<Player, GamePoint> score = game.getScore();
        if (game.getPlayerScore(scorer).equals(GamePoint.FOURTHY.getName())
                && game.getOpponentScore(scorer).equals(GamePoint.FOURTHY.getName())) {
            score.put(scorer, GamePoint.ADVANTAGE);
            return score;
        }

        if (game.getPlayerScore(scorer).equals(GamePoint.ADVANTAGE.getName()) &&
                game.getOpponentScore(scorer).equals(GamePoint.FOURTHY.getName())) {
            score.put(scorer, GamePoint.WIN_POINT);
            return score;
        }

        if (game.getPlayerScore(scorer).equals(GamePoint.FOURTHY.getName()) &&
                game.getOpponentScore(scorer).equals(GamePoint.ADVANTAGE.getName())) {
            score.put(scorer, GamePoint.FOURTHY);
            score.put(game.getOpponent(scorer), GamePoint.FOURTHY);
            return score;
        }
        throw new IllegalArgumentException("Can not calculate Game Score, the game is not in deuce");
    }
}
