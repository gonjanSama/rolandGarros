package rolandgarros;

import com.gonjansama.rolandgarros.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class GameServiceTest {
    private GameServiceImpl gameService = new GameServiceImpl();

    @Test
    public void should_return_0_point_for_each_player_when_the_game_start() {
        Game game = gameService.createGame("nadal", "federer");
        Assert.assertNotNull(game);
        Map<Player, GamePoint> score = game.getScore();
        score.forEach((player, playerScore) -> Assert.assertEquals(GamePoint.ZERO, playerScore));
    }

    @Test
    public void should_return_15_point_for_a_player_with_0_point_when_this_player_win_a_point() {
        // Given
        Game game = gameService.createGame("nadal", "federer");

        // When
        gameService.addPointToPlayer1(game);

        // Then
        Assert.assertEquals(GamePoint.FIFTEEN, game.getPlayer1Score());
    }

    @Test
    public void should_return_30_point_for_a_player_with_15_point_when_this_player_win_a_point() {
        // Given
        Game game = gameService.createGame("nadal", "federer");

        // When
        game.getScore().put(game.getPlayer1(), GamePoint.FIFTEEN);
        gameService.addPointToPlayer1(game);

        //Then
        Assert.assertEquals(GamePoint.THIRTHY, game.getPlayer1Score());
    }

    @Test
    public void should_return_40_point_for_a_player_with_30_point_when_this_player_win_a_point() {
        // Given
        Game game = gameService.createGame("nadal", "federer");

        // When
        game.getScore().put(game.getPlayer1(), GamePoint.THIRTHY);
        gameService.addPointToPlayer1(game);

        //Then
        Assert.assertEquals(GamePoint.FOURTHY, game.getPlayer1Score());
    }

    @Test
    public void should_return_win_point_for_a_player_with_40_point_when_this_player_win_a_point() {
        // Given
        Game game = gameService.createGame("nadal", "federer");

        // When
        game.getScore().put(game.getPlayer1(), GamePoint.FOURTHY);
        gameService.addPointToPlayer1(game);

        //Then
        Assert.assertEquals(GamePoint.WIN_POINT, game.getPlayer1Score());
    }
}