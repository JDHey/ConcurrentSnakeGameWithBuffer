package src.tests;

import edu.unisa.concurrentSnakeGame.Node;
import edu.unisa.concurrentSnakeGame.Player;
import edu.unisa.concurrentSnakeGame.Snake;
import edu.unisa.concurrentSnakeGame.SnakeNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static edu.unisa.concurrentSnakeGame.Snake.DEFAULT_SPEED;
import static edu.unisa.concurrentSnakeGame.Snake.STARTING_LENGTH;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

/**
 * Created by Terry on 6/06/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class SnakeTest {
    @Mock
    List<SnakeNode> nodeList;

    @Mock
    Snake snake;

    @Mock
    Node node;

    @Mock
    SnakeNode snakeNode;

    @Test
    public void args_constructor_test_01() {
        System.out.println("Testing Abstract Player  Class: args_constructor_test_01");
        nodeList = Collections.synchronizedList(new ArrayList<SnakeNode>());
        //snake = Mockito.mock(Snake.class, Mockito.CALLS_REAL_METHODS);
        snake = new Snake("MySnake", 10,10);
        String expected_id = "MySnake";
        int expected_direction = 0;
        int expected_size = STARTING_LENGTH + 1;
        Enum expected_state = Snake.State.ALIVE;
        int expected_score = 0;
        Player.Move expected_move = Player.Move.NONE;
        double expected_speed = DEFAULT_SPEED;
        assertEquals(expected_id, snake.getId());
        assertEquals(expected_direction, snake.getDirection());
        assertEquals(expected_size, snake.getNodeList().size());
        assertEquals(expected_state, snake.getCurrentState());
        assertEquals(expected_score, snake.getScore());
        assertEquals(expected_move, snake.getLastKeyPressed());
        assertEquals(expected_speed, snake.getSpeed(),0000001);
    }


}
