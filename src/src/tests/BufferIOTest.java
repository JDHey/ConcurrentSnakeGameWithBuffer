package src.tests;

import edu.unisa.concurrentSnakeGame.BufferIO;
import edu.unisa.concurrentSnakeGame.Player;
import edu.unisa.concurrentSnakeGame.PlayerNetworkData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.BlockingQueue;

import static org.junit.Assert.assertEquals;

/**
 * Created by Terry on 6/06/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class BufferIOTest {
    @Mock
    BlockingQueue<PlayerNetworkData> bufferQueue;

    @Mock
    PlayerNetworkData data;

    @Mock
    BufferIO buffer;

    @Test
    public void offer_test_01() {
        System.out.println("Testing  BufferIO Class: offer_test_01");
        buffer = new BufferIO();
        data = new PlayerNetworkData("MyTestPlayer", Player.Move.LEFT);
        buffer.offer(data);
        data = new PlayerNetworkData("MyTestPlayer", Player.Move.RIGHT);
        buffer.offer(data);
        int expected_size = 2;
        assertEquals(expected_size, buffer.getBufferQueue().size());
    }

    @Test
    public void poll_test_01() {
        System.out.println("Testing  BufferIO Class: poll_test_01");
        buffer = new BufferIO();
        data = new PlayerNetworkData("MyTestPlayer", Player.Move.LEFT);
        buffer.offer(data);
        data = new PlayerNetworkData("MyTestPlayer", Player.Move.RIGHT);
        buffer.offer(data);
        int expected_size = 2;
        assertEquals(expected_size, buffer.getBufferQueue().size());
        buffer.poll();
        int expected_after = 1;
        assertEquals(expected_after, buffer.getBufferQueue().size());
    }

    @Test
    public void take_test_01() {
        System.out.println("Testing  BufferIO Class: take_test_01");
        buffer = new BufferIO();
        data = new PlayerNetworkData("MyTestPlayer", Player.Move.LEFT);
        buffer.offer(data);
        data = new PlayerNetworkData("MyTestPlayer", Player.Move.RIGHT);
        buffer.offer(data);
        int expected_size = 2;
        assertEquals(expected_size, buffer.getBufferQueue().size());
        buffer.take();
        int expected_after = 1;
        assertEquals(expected_after, buffer.getBufferQueue().size());

    }

    @Test
    public void get_buffer_queue_test_01() {
        System.out.println("Testing  BufferIO Class: get_buffer_queue_test_01");
        buffer = new BufferIO();
        data = new PlayerNetworkData("MyTestPlayer", Player.Move.LEFT);
        buffer.offer(data);
        int expected = 1;
        assertEquals(expected, buffer.getBufferQueue().size());
    }
}
