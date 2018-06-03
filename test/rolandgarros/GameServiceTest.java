package rolandgarros;

import com.gonjansama.rolandgarros.Game;
import com.gonjansama.rolandgarros.GameService;
import com.gonjansama.rolandgarros.Player;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class GameServiceTest {

    @Test
    public void should_return_0_point_for_each_player_when_the_game_start() {
        Player nadal = new Player("nadal");
        Player federer = new Player("federer");
        Game game = new GameService().createGame(nadal, federer);
        game.start();
        Map<Player, Integer> score = game.getScore();
        Assert.assertEquals(new Integer(0), score.get(nadal));
        Assert.assertEquals(new Integer(0), score.get(federer));
    }

}