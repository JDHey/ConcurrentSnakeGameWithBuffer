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
import static org.mockito.Matchers.*;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        System.out.println("Testing  Snake Class: args_constructor_test_01");
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

    @Test
    public void get_id_test_01() {
        System.out.println("Testing  Snake Class: get_id_test_01");
        snake = Mockito.mock(Snake.class, Mockito.CALLS_REAL_METHODS);
        String expected = "Snake_ID";
        assertEquals(expected, snake.getId(), null);
    }

    @Test
    public void increaseLength_test_01() {
        System.out.println("Testing  Snake Class: increaseLength_test_01");
        snake = new Snake("mysnake", 10,10);
        int expected_default = 41;
        int expected_size_now = 61;
        assertEquals(expected_default, snake.getNodeList().size());
        snake.increaseLength(20, true);
        assertEquals(expected_size_now, snake.getNodeList().size());
    }

    @Test
    public void decrease_length_test_01() {
        System.out.println("Testing  Snake Class: decrease_length_test_01");
        snake = new Snake("mysnake", 10,10);
        int expected_default = 41;
        assertEquals(expected_default, snake.getNodeList().size());
        snake.decreaseLength(1);
        int expected_size_now = 40;
        assertEquals(expected_size_now, snake.getNodeList().size());
    }

    @Test
    public void get_head_test_01() {
        System.out.println("Testing  Snake Class: get_head_test_01");
        snake = new Snake("mysnake", 10,10);
        int size = snake.getNodeList().size();
        SnakeNode expected = snake.getNodeList().get(size  -1);
        assertEquals(expected, snake.getHead());
    }

    @Test
    public void get_tail_test_01() {
        System.out.println("Testing  Snake Class: get_tail_test_01");
        snake = new Snake("mysnake", 10,10);
        SnakeNode expected = snake.getNodeList().get(0);
        assertEquals(expected, snake.getTail());
    }

    @Test
    public void update_test_01() {
        System.out.println("Testing  Snake Class: update_test_01");

    }

    @Test
    public void move_test_01() {
        System.out.println("Testing  Snake Class: move_test_01");

    }

    @Test
    public void getDirection_test_01() {
        System.out.println("Testing  Snake Class: getDirection_test_01");
        snake = Mockito.mock(Snake.class, Mockito.CALLS_REAL_METHODS);
        int expected = 0;
        assertEquals(expected, snake.getDirection());

    }

    @Test
    public void setDirection_test_01() {
        System.out.println("Testing  Snake Class: setDirection_test_01");
        snake = Mockito.mock(Snake.class, Mockito.CALLS_REAL_METHODS);
        int expected = 2;
        snake.setDirection(2);
        assertEquals(expected, snake.getDirection());
    }

    @Test
    public void change_direction_test_01() {
        System.out.println("Testing  Snake Class: change_direction_test_01");
    }

    @Test
    public void collidesWith_test_01() {
        System.out.println("Testing  Snake Class: collidesWith_test_01");

    }




}
