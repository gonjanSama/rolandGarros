package com.gonjansama.tennis.internal;

import com.gonjansama.tennis.Game;
import com.gonjansama.tennis.Player;
import com.gonjansama.tennis.Set;
import org.junit.Assert;
import org.junit.Test;

public class SetImplTest {
    private Player nadal = new Player("nadal", 1);
    private Player federer = new Player("federer", 2);
    private Set set;

    @Test
    public void should_start_set_with_score_0_for_each_player() {
        // Given
        set = new MatchImpl(nadal, federer).getCurrentSet();

        // When
        Integer player1Score = set.getPlayer1Score();
        Integer player2Score = set.getPlayer2Score();

        // Then
        Assert.assertEquals(Integer.valueOf(0), player1Score);
        Assert.assertEquals(Integer.valueOf(0), player2Score);
    }

    @Test
    public void  should_return_1_point_for_a_player_with_0_point_when_this_player_win_a_game() {
        // Given
        set =  new MatchImpl(nadal, federer).getCurrentSet();
        Game game =  set.getCurrentGame();
        ((GameImpl)game).getScore().put(nadal, GamePoint.FOURTHY);

        // When
        game.addPoint(nadal);

        // Then
        Assert.assertEquals(Integer.valueOf(1), set.getPlayerScore(nadal));
    }

    @Test
    public void  should_set_winner_for_a_player_with_6_points_when_his_oponent_has_4_points() {
        // Given
        set =  new MatchImpl(nadal, federer).getCurrentSet();
        set.setPlayerScore(federer, 5);
        set.setPlayerScore(nadal, 4);
        Game game =  set.getCurrentGame();
        ((GameImpl)game).getScore().put(federer, GamePoint.FOURTHY);

        // When
        game.addPoint(federer);

        // Then
        Assert.assertEquals(Integer.valueOf(6), set.getPlayerScore(federer));
        Assert.assertTrue(set.getWinner().isPresent());
        Assert.assertEquals(federer, set.getWinner().get());
        Assert.assertEquals(false, set.isInProgress());
    }

    @Test
    public void  should_not_end_the_set_when_a_player_has_6_point_and_his_oponent_has_5_points() {
        // Given
        set =  new MatchImpl(nadal, federer).getCurrentSet();
        set.setPlayerScore(federer, 5);
        set.setPlayerScore(nadal, 5);
        Game game =  set.getCurrentGame();
        ((GameImpl)game).getScore().put(federer, GamePoint.FOURTHY);

        // When
        game.addPoint(federer);

        // Then
        Assert.assertEquals(Integer.valueOf(6), set.getPlayerScore(federer));
        Assert.assertEquals(false, set.hasWinner());
        Assert.assertEquals(true, set.isInProgress());
    }

    @Test
    public void  should_set_winner_for_a_player_with_7_points_when_his_oponent_has_5_points_or_more() {
        // Given
        set =  new MatchImpl(nadal, federer).getCurrentSet();
        set.setPlayerScore(federer, 6);
        set.setPlayerScore(nadal, 5);
        Game game =  set.getCurrentGame();
        ((GameImpl)game).getScore().put(federer, GamePoint.FOURTHY);

        // When
        game.addPoint(federer);

        // Then
        Assert.assertEquals(Integer.valueOf(7), set.getPlayerScore(federer));
        Assert.assertTrue(set.getWinner().isPresent());
        Assert.assertEquals(federer, set.getWinner().get());
        Assert.assertEquals(false, set.isInProgress());
    }

    @Test
    public void should_activate_the_tie_break_rule_when_two_players_have_6_points() {
        // Given
        set =  new MatchImpl(nadal, federer).getCurrentSet();
        set.setPlayerScore(federer, 5);
        set.setPlayerScore(nadal, 6);
        Game game =  set.getCurrentGame();
        ((GameImpl)game).getScore().put(federer, GamePoint.FOURTHY);

        // When
        game.addPoint(federer);

        //Then
        Assert.assertEquals(true, set.isOnTieBreak());
    }

    @Test
    public void should_set_the_tie_break_score_when_the_tie_break_rule_is_actived() {
        // Given
        set =  new MatchImpl(nadal, federer).getCurrentSet();
        set.setPlayerScore(federer, 6);
        set.setPlayerScore(nadal, 6);
        Game game =  set.getCurrentGame();
        ((GameImpl)game).getScore().put(federer, GamePoint.FOURTHY);

        // When
        game.addPoint(federer);

        //Then
        Assert.assertEquals(true, set.isOnTieBreak());
        Assert.assertEquals(Integer.valueOf(1), set.getPlayerTieBreakScore(federer));
    }

    @Test
    public void should_set_winner_a_player_who_have_7_point_when_his_opponent_have_5_point() {
        // Given
        set =  new MatchImpl(nadal, federer).getCurrentSet();
        set.setPlayerScore(federer, 6);
        set.setPlayerScore(nadal, 6);
        set.setPlayerTieBreakScore(federer, 6);
        set.setPlayerTieBreakScore(nadal, 5);

        // When
        Game game =  set.getCurrentGame();
        ((GameImpl)game).getScore().put(federer, GamePoint.FOURTHY);
        game.addPoint(federer);

        //Then
        Assert.assertEquals(true, set.isOnTieBreak());
        Assert.assertEquals(Integer.valueOf(7), set.getPlayerTieBreakScore(federer));
        Assert.assertTrue(set.getWinner().isPresent());
        Assert.assertEquals(federer, set.getWinner().get());
    }
}
