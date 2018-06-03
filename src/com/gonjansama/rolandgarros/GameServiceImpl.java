package com.gonjansama.rolandgarros;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        GamePoint player1Score = score.get(player);
        score.put(player, getNextPoint(player1Score));
    }

    private GamePoint getNextPoint(GamePoint current) {
        Optional<GamePointTransition> transitionOptional =
                gamePointTransitions.stream().filter(transition -> transition.getFrom().equals(current)).findFirst();
        return transitionOptional.map(GamePointTransition::getTo).orElse(null);
    }


    private List<GamePointTransition> transitions() {
        GamePointTransition zeroToFifteen = new GamePointTransition(GamePoint.ZERO, GamePoint.FIFTEEN);
        GamePointTransition fifteenToThirthy = new GamePointTransition(GamePoint.FIFTEEN, GamePoint.THIRTHY);
        GamePointTransition thirhtyToFourthy = new GamePointTransition(GamePoint.THIRTHY, GamePoint.FOURTHY);
        GamePointTransition fourthyToSucces = new GamePointTransition(GamePoint.FOURTHY, GamePoint.WIN_POINT);
        return Stream.of(zeroToFifteen, fifteenToThirthy, thirhtyToFourthy, fourthyToSucces).collect(Collectors.toList());
    }
}
