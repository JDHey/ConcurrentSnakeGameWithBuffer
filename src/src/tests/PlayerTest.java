package src.tests;

import edu.unisa.concurrentSnakeGame.*;
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
import java.util.concurrent.ConcurrentHashMap;
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
    BufferIO myBuffer;

    @Mock
    Snake snake;

    int upKey = VK_UP;
    int downKey = VK_DOWN;
    int leftKey = VK_LEFT;
    int rightKey = VK_RIGHT;

    @Mock
    Queue<Snake> snakeQueue;

    @Mock
    ConcurrentHashMap<String,Snake> snakeMap;

  @Test
    public void args_constructor_test_01() {
        System.out.println("Testing Abstract Player  Class: args_constructor_test_01");
        PlayerLocal player = new PlayerLocal("Terry Dean",myBuffer, gameState, upKey, downKey, leftKey, rightKey);
        player.setLastKeyPressed(Player.Move.LEFT);
        myBuffer = new BufferIO();
        gameState = new GameState();
        gameState.addPlayer(player.getPlayerId());
        String expected_id = "Terry Dean";
        int expected_moves = 1;
        int expected_num_snakes = 1;
        int expected_upKey = VK_UP;
        int expected_downKey = VK_DOWN;
        int expected_leftKey = VK_LEFT;
        int expected_rightKey = VK_RIGHT;
        Enum expected = Player.Move.LEFT;
        assertEquals(expected, player.getLastKeyPressed());
        assertEquals(expected_id, player.getPlayerId());
        assertEquals(expected_num_snakes, gameState.getSnakeMap().size());
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
        Enum expected = Player.Move.NONE;
        assertEquals(expected, player.getLastKeyPressed());
    }
    @Test
    public void update_buffer_test_01() {
        System.out.println("Testing Abstract Player  Class: update_buffer_test_01");
        PlayerLocal player = new PlayerLocal("Terry Dean",myBuffer, gameState, upKey, downKey, leftKey, rightKey);
        player.setLastKeyPressed(Player.Move.LEFT);
        player.updateBuffer();
        Enum expected = Player.Move.LEFT;
        assertEquals(expected, player.getLastKeyPressed());
    }

    @Test
    public void update_game_state_01() {
        System.out.println("Testing Abstract Player  Class: update_game_state_01");
        gameState = new GameState();
        myBuffer = new BufferIO();
        PlayerLocal player = new PlayerLocal("Terry Dean", myBuffer, gameState, upKey, downKey, leftKey, rightKey);
        gameState.addPlayer(player.getPlayerId());
        snakeMap = player.getGameState().getSnakeMap();
        int expected_snakes = 1;
        assertEquals(expected_snakes, snakeMap.size());
    }
}
