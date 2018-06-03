package com.gonjansama.rolandgarros;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameServiceImpl implements GameService {

    private List<GamePointTransition> gamePointTransitions;

    public GameServiceImpl() {
        gamePointTransitions = transitions();
    }

    public Game createGame(String player1, String player2) {
        return new Game(new Player(player1), new Player(player2));
    }

    public void addPointToPlayer1(Game game) {
        addPointToPlayer(game, game.getPlayer1());
    }

    public void addPointToPlayer2(Game game) {
        addPointToPlayer(game, game.getPlayer2());
    }

    private void addPointToPlayer(Game game, Player player) {
        Map<Player, GamePoint> score = game.getScore();
        score.put(player, getNextPoint(player, game));
    }

    private GamePoint getNextPoint(Player player, Game game) {
        GamePoint playerScore = game.getScore().get(player);
        for (GamePointTransition transition : gamePointTransitions) {
            boolean gamePointMatch = transition.getFrom().equals(playerScore);
            boolean ruleMatch = transition.getRule().equals(extractApplyingRule(game));
            if (gamePointMatch && ruleMatch) {
                return transition.getTo();
            }
        }
        return null;
    }

    private Rule extractApplyingRule(Game game) {
        Rule[] rules = {RuleFactory.create(StandardRule.class)};
        return Arrays.stream(rules).filter(rule -> rule.matches(game)).findFirst().orElse(null);
    }


    private List<GamePointTransition> transitions() {
        GamePointTransition zeroToFifteen = new GamePointTransition(GamePoint.ZERO, GamePoint.FIFTEEN, RuleFactory.create(StandardRule.class));
        GamePointTransition fifteenToThirthy = new GamePointTransition(GamePoint.FIFTEEN, GamePoint.THIRTHY, RuleFactory.create(StandardRule.class));
        GamePointTransition thirhtyToFourthy = new GamePointTransition(GamePoint.THIRTHY, GamePoint.FOURTHY, RuleFactory.create(StandardRule.class));
        GamePointTransition fourthyToSucces = new GamePointTransition(GamePoint.FOURTHY, GamePoint.WIN_POINT, RuleFactory.create(StandardRule.class));
        return Stream.of(zeroToFifteen, fifteenToThirthy, thirhtyToFourthy, fourthyToSucces).collect(Collectors.toList());
    }
}
