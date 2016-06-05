package tests;

import edu.unisa.concurrentSnakeGame.GameState;
import edu.unisa.concurrentSnakeGame.Player;
import edu.unisa.concurrentSnakeGame.PlayerLocal;
import edu.unisa.concurrentSnakeGame.Snake;
import org.junit.Assert;
import org.junit.Test;

import static java.awt.event.KeyEvent.*;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.cglib.transform.AbstractClassFilterTransformer;
import org.mockito.runners.MockitoJUnitRunner;

import java.awt.event.KeyEvent;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
/**
 * Created by Terry on 18/05/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class PlayerTest {
    @Mock
    private Player player;

    @Mock
    GameState gameState;

    @Mock
    Snake snake;

    int upKey = VK_UP;
    int downKey = VK_DOWN;
    int leftKey = VK_LEFT;
    int rightKey = VK_RIGHT;

    @Mock
    Queue<Snake> snakeQueue;

    @Test
    public void args_constructor_test_01() {
        System.out.println("Testing Abstract Player  Class: args_constructor_test_01");
        PlayerLocal player = new PlayerLocal("Terry Dean", gameState, upKey, downKey, leftKey, rightKey);
        gameState = new GameState();
        gameState.addSnake(snake);
        gameState.addSnake(snake);
        snakeQueue = gameState.getSnakeQueue();
        String expected_id = "Terry Dean";
        int expected_num_snakes = 2;
        int expected_upKey = VK_UP;
        int expected_downKey = VK_DOWN;
        int expected_leftKey = VK_LEFT;
        int expected_rightKey = VK_RIGHT;
        assertEquals(expected_id, player.getPlayerId());
        assertEquals(expected_num_snakes, snakeQueue.size());
        assertEquals(expected_upKey, player.getUpKey());
        assertEquals(expected_downKey, player.getDownKey());
        assertEquals(expected_leftKey, player.getLeftKey());
        assertEquals(expected_rightKey, player.getRightKey());
    }

    @Test
    public void get_player_id_test_01() {
        System.out.println("Testing Abstract Player  Class: get_player_id_test_01");
        player = Mockito.mock(Player.class, Mockito.CALLS_REAL_METHODS);
        String expected = "PlayerName";
        assertEquals(expected, player.getPlayerId(), null);
    }

    @Test
    public void get_last_key_pressed_01() {
        System.out.println("Testing Abstract Player  Class: get_last_key_pressed_01");
        player = Mockito.mock(Player.class, Mockito.CALLS_REAL_METHODS);
        player.setLastKeyPressed(Player.Move.LEFT);
        Enum expected = Player.Move.LEFT;
        assertEquals(expected, player.getLastKeyPressed());
    }

    @Test
    public void reset_last_key_pressed_01() {
        System.out.println("Testing Abstract Player  Class: reset_last_key_pressed_01");
        player = Mockito.mock(Player.class, Mockito.CALLS_REAL_METHODS);
        player.resetLastKeyPressed();
        Enum expected = Player.Move.AUTO;
        assertEquals(expected, player.getLastKeyPressed());
    }

    @Test
    public void update_game_state_01() {
        System.out.println("Testing Abstract Player  Class: update_game_state_01");
        player = new PlayerLocal("Terry Dean", gameState, upKey, downKey, leftKey, rightKey);
        // create a player and add one snake to its gameState
        int expected_size = 1;
        gameState = new GameState();
        gameState.addSnake(snake);
        player.updateGameState(gameState);
        snakeQueue = player.getGameState().getSnakeQueue();
        assertEquals(expected_size, snakeQueue.size());

        // create a new GameState add two snakes and update the player's gameState
        int expected_updated_size = 2;
        GameState gs = new GameState();
        gs.addSnake(snake);
        gs.addSnake(snake);
        player.updateGameState(gs);
        snakeQueue = player.getGameState().getSnakeQueue();
        assertEquals(expected_updated_size, snakeQueue.size());
    }
}
