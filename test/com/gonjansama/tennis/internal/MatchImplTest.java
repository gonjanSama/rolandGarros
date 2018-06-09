package com.gonjansama.tennis.internal;

import com.gonjansama.tennis.Player;
import org.junit.Assert;
import org.junit.Test;

public class MatchImplTest {
    private Player nadal = new Player("nadal", 1);
    private Player federer = new Player("federer", 2);

    @Test
    public void should_set_a_player_as_winner_when_he_wins_the_set() {
        // Given
        MatchImpl match = new MatchImpl(nadal, federer);

        // When
        SetImpl set =  match.getCurrentSet();
        set.setPlayerScore(federer, 6);
        set.setPlayerScore(nadal, 4);

        //Then
        Assert.assertEquals(federer, set.getWinner());
        Assert.assertEquals(federer, match.getWinner());
    }


    @Test
    public void should_set_a_player_as_winner_when_he_wins_the_set_on_tie_break() {
        // Given
        MatchImpl match = new MatchImpl(nadal, federer);
        SetImpl set =  match.getCurrentSet();
        set.setPlayerScore(federer, 6);
        set.setPlayerScore(nadal, 6);
        set.setPlayerTieBreakScore(federer, 6);
        set.setPlayerTieBreakScore(nadal, 5);

        // When
        GameImpl game =  set.getCurrentGame();
        game.getScore().put(federer, GamePoint.FOURTHY);
        game.addPoint(federer);

        //Then
        Assert.assertEquals(true, set.isOnTieBreak());
        Assert.assertEquals(federer, set.getWinner());
        Assert.assertEquals(federer, match.getWinner());
    }
}
