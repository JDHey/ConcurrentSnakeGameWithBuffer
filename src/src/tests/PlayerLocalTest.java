package src.tests;

import edu.unisa.concurrentSnakeGame.*;
import javafx.scene.input.KeyCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.awt.event.KeyEvent;
import java.util.Queue;

import static java.awt.event.KeyEvent.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Terry on 29/05/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class PlayerLocalTest {
    @Mock
    private PlayerLocal player;

    @Mock
    private GameState gameState;

    @Mock
    BufferIO myBuffer;

    @Mock
    private Snake snake;

    private int upKey = VK_UP;
    private int downKey = VK_DOWN;
    private int leftKey = VK_LEFT;
    private int rightKey = VK_RIGHT;

    @Mock
    private Queue<Snake> snakeQueue;

    @Mock
    PlayerWindow jFrame;

    @Mock
    KeyEvent event;

   @Test
    public void args_constructor_test_01() {
       System.out.println("Testing PlayerLocal Class: args_constructor_test_01");
       player = new PlayerLocal("Terry Dean", myBuffer, gameState, upKey, downKey, leftKey, rightKey);
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
    public void isloggedin_test_01() {
        System.out.println("Testing PlayerLocal Class: isloggedin_test_01");
        player = mock(PlayerLocal.class, Mockito.CALLS_REAL_METHODS);
        boolean expected = false;
        assertEquals(expected, player.isloggedin());
    }
    @Test
    public void setIsloggedin() {
        System.out.println("Testing PlayerLocal Class: setIsloggedin_test_01");
        player = mock(PlayerLocal.class, Mockito.CALLS_REAL_METHODS);
        boolean expected = true;
        player.setIsloggedin(true);
        assertEquals(expected, player.isloggedin());
    }

    @Test
    public void getUsername_test_01() {
        System.out.println("Testing PlayerLocal Class: getUsername_test_01");
        player = mock(PlayerLocal.class, Mockito.CALLS_REAL_METHODS);
        String expected = "username";
        when(player.getUsername()).thenReturn(expected);
        assertEquals(expected, player.getUsername());
    }

    @Test
    public void setUsername_test_01() {
        System.out.println("Testing PlayerLocal Class: setUsername_test_01");
        player = mock(PlayerLocal.class, Mockito.CALLS_REAL_METHODS);
        String expected = "Terry Dean";
        player.setUsername("Terry Dean");
        assertEquals(expected, player.getUsername());
    }


    @Test
    public void getPassword_test_01() {
        System.out.println("Testing PlayerLocal Class: getPassword_test_01");
        player = mock(PlayerLocal.class, Mockito.CALLS_REAL_METHODS);
        String expected = "*****";
        when(player.getPassword()).thenReturn(expected);
        assertEquals(expected, player.getPassword());
    }

    @Test
    public void setPassword_test_01() {
        System.out.println("Testing PlayerLocal Class: setPassword_test_01");
        player = mock(PlayerLocal.class, Mockito.CALLS_REAL_METHODS);
        String expected = "password";
        player.setPassword("password");
        assertEquals(expected, player.getPassword());
    }

    @Test
    public void getUpKey_test_01() {
        System.out.println("Testing PlayerLocal Class: getUpKey_test_01");
        player = mock(PlayerLocal.class, Mockito.CALLS_REAL_METHODS);
        int expected = 0;
        when(player.getUpKey()).thenReturn(expected);
        assertEquals(expected, player.getUpKey());
    }

    @Test
    public void setUpKey_test_01() {
        System.out.println("Testing PlayerLocal Class: setUpKey_test_01");
        player = mock(PlayerLocal.class, Mockito.CALLS_REAL_METHODS);
        int expected = 22;
        player.setUpKey(22);
        assertEquals(expected, player.getUpKey());
    }

    @Test
    public void getDownKey_test_01() {
        System.out.println("Testing PlayerLocal Class: getDownKey_test_01");
        player = mock(PlayerLocal.class, Mockito.CALLS_REAL_METHODS);
        int expected = 1;
        when(player.getDownKey()).thenReturn(expected);
        assertEquals(expected, player.getDownKey());
    }

    @Test
    public void setDownKey_test_01() {
        System.out.println("Testing PlayerLocal Class: setDownKey_test_01");
        player = mock(PlayerLocal.class, Mockito.CALLS_REAL_METHODS);
        int expected = 22;
        player.setDownKey(22);
        assertEquals(expected, player.getDownKey());
    }

    @Test
    public void getLeftKey_test_01() {
        System.out.println("Testing PlayerLocal Class: getLeftKey_test_01");
        player = mock(PlayerLocal.class, Mockito.CALLS_REAL_METHODS);
        int expected = 2;
        when(player.getLeftKey()).thenReturn(expected);
        assertEquals(expected, player.getLeftKey());
    }

    @Test
    public void setLeftKey_test_01() {
        System.out.println("Testing PlayerLocal Class: setLeftKey_test_01");
        player = mock(PlayerLocal.class, Mockito.CALLS_REAL_METHODS);
        int expected = 22;
        player.setLeftKey(22);
        assertEquals(expected, player.getLeftKey());
    }

    @Test
    public void getRightKey_test_01() {
        System.out.println("Testing PlayerLocal Class: getRightKey_test_01");
        player = mock(PlayerLocal.class, Mockito.CALLS_REAL_METHODS);
        int expected = 3;
        when(player.getRightKey()).thenReturn(expected);
        assertEquals(expected, player.getRightKey());
    }

    @Test
    public void setRightKey_test_01() {
        System.out.println("Testing PlayerLocal Class: setRightKey_test_01");
        player = mock(PlayerLocal.class, Mockito.CALLS_REAL_METHODS);
        int expected = 22;
        player.setRightKey(22);
        assertEquals(expected, player.getRightKey());
    }

   /* @Test
    public void getSnake_test_01() {
        System.out.println("Testing PlayerLocal Class: getSnake_test_01");
        player = mock(PlayerLocal.class, Mockito.CALLS_REAL_METHODS);
        Snake expected = null;
        assertEquals(expected, player.getSnake());
    }*/

  /*  @Test
    public void setSnake_test_01() {
        System.out.println("Testing PlayerLocal Class: setSnake_test_01");
        player = new PlayerLocal("Terry Dean", gameState, upKey, downKey, leftKey, rightKey);
        snake = new Snake("MySnake", 0, 0);
        player.setSnake(snake);
        Snake expected = snake;
        assertEquals(expected, player.getSnake());
    }*/

    @Test
    public void keyPressed_01() {
        System.out.println("Testing PlayerLocal Class: keyPressed_01");
        player = mock(PlayerLocal.class, Mockito.CALLS_REAL_METHODS);

    }

    @Test
    public void keyReleased_test_01() {
        System.out.println("Testing PlayerLocal Class: keyReleased_test_01");
    }

    @Test
    public void keyTyped_01() {
        System.out.println("Testing PlayerLocal Class: keyTyped_01");
    }
}
