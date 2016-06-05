package src.tests;

import edu.unisa.concurrentSnakeGame.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.awt.event.KeyEvent;
import java.util.Queue;

import static java.awt.event.KeyEvent.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by Terry on 1/06/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class PlayerAITest {

    @Mock
    PlayerAI player;

    @Mock
    private GameState gameState;

    @Mock
    BufferIO myBuffer;

    @Mock
    private Snake snake;

    @Mock
    private Queue<Snake> snakeQueue;

    @Mock
    PlayerWindow jFrame;

    @Mock
    KeyEvent event;

    @Test
    public void args_constructor_test_01() {
        System.out.println("Testing PlayerAI Class: args_constructor_test_01");
        player = new PlayerAI("Terry Dean", myBuffer, gameState);
        myBuffer = new BufferIO();
        gameState = new GameState();
        gameState.addPlayer(player.getPlayerId());
        String expected_id = "Terry Dean";
        int expected_num_snakes = 1;
        Player.Move expected_key = Player.Move.NONE;
        boolean expected_login = true;
        assertEquals(expected_id, player.getPlayerId());
        assertEquals(expected_num_snakes, gameState.getSnakeMap().size());
        assertEquals(expected_key, player.getLastKeyPressed());
        assertEquals(expected_login, player.isloggedin());
    }



}
