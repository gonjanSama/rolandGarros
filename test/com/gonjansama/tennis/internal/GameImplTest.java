package com.gonjansama.tennis.internal;

import com.gonjansama.tennis.Player;
import org.junit.Assert;
import org.junit.Test;
import java.util.Map;

public class GameImplTest {
    private GameImpl game;
    private Player nadal = new Player("nadal", 1);
    private Player federer = new Player("federer", 2);
    private SetImpl set = new MatchImpl(nadal, federer).getCurrentSet();

    @Test
    public void should_return_0_point_for_each_player_when_the_game_start() {
        // Given
        game = set.getCurrentGame();
        
        // When
        Map<Player, GamePoint> score = game.getScore();
        
        // Then
        score.forEach((player, playerScore) -> Assert.assertEquals(GamePoint.ZERO, playerScore));
    }

    @Test
    public void should_return_15_point_for_a_player_with_0_point_when_this_player_win_a_point() {
        // Given
        game = set.getCurrentGame();

        // When
        game.addPoint(nadal);

        // Then
        Assert.assertEquals(GamePoint.FIFTEEN.getName(), game.getPlayerScore(nadal));
    }

    @Test
    public void should_return_30_point_for_a_player_with_15_point_when_this_player_win_a_point() {
        // Given
        game = set.getCurrentGame();

        // When
        game.getScore().put(nadal, GamePoint.FIFTEEN);
        game.addPoint(nadal);

        //Then
        Assert.assertEquals(GamePoint.THIRTHY.getName(), game.getPlayerScore(nadal));
    }

    @Test
    public void should_return_40_point_for_a_player_with_30_point_when_this_player_win_a_point() {
        // Given
        game = set.getCurrentGame();

        // When
        game.getScore().put(nadal, GamePoint.THIRTHY);
        game.addPoint(nadal);

        //Then
        Assert.assertEquals(GamePoint.FOURTHY.getName(), game.getPlayerScore(nadal));
    }

    @Test
    public void should_return_win_point_for_a_player_with_40_point_when_this_player_win_next_point() {
        // Given
        game = set.getCurrentGame();

        // When
        game.getScore().put(federer, GamePoint.FOURTHY);
        game.addPoint(federer);

        //Then
        Assert.assertEquals(GamePoint.WIN_POINT.getName(), game.getPlayerScore(federer));
        Assert.assertEquals(game.getWinner(), federer);
    }

    @Test
    public void should_activate_the_deuce_rule_when_two_players_have_40_point() {
        // Given
        game = set.getCurrentGame();

        // When
        game.getScore().put(nadal, GamePoint.FOURTHY);
        game.getScore().put(federer, GamePoint.FOURTHY);

        //Then
        Assert.assertTrue(game.isOnDeuce());
    }

    @Test
    public void should_return_advantage_for_player_when_score_is_deuce() {
        // Given
        game = set.getCurrentGame();

        // When
        game.getScore().put(nadal, GamePoint.FOURTHY);
        game.getScore().put(federer, GamePoint.FOURTHY);
        game.addPoint(federer);

        //Then
        Assert.assertEquals(true, game.isOnDeuce());
        Assert.assertEquals(GamePoint.ADVANTAGE.getName(), game.getPlayerScore(federer));
    }

    @Test
    public void should_return_win_point_for_player_when_player_has_advantage() {
        // Given
        game = set.getCurrentGame();

        // When
        game.getScore().put(nadal, GamePoint.ADVANTAGE);
        game.getScore().put(federer, GamePoint.FOURTHY);
        game.addPoint(nadal);

        //Then
        Assert.assertEquals(GamePoint.WIN_POINT.getName(), game.getPlayerScore(nadal));
        Assert.assertEquals(game.getWinner(), nadal);
    }

    @Test
    public void should_return_deuce_point_for_both_players_when_the_player_who_has_advantage_loose_the_point() {
        // Given
        game = set.getCurrentGame();

        // When
        game.getScore().put(nadal, GamePoint.ADVANTAGE);
        game.getScore().put(federer, GamePoint.FOURTHY);
        game.addPoint(federer);

        //Then
        Assert.assertEquals(true, game.isOnDeuce());
        Assert.assertEquals(GamePoint.FOURTHY.getName(), game.getPlayerScore(federer));
        Assert.assertEquals(GamePoint.FOURTHY.getName(), game.getPlayerScore(nadal));
    }
}