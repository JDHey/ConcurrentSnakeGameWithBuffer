package tests;

/**
 * Created by Terry on 29/05/2016.
 */
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;


public class MainTest {

    public static void main(String[] args) {

        Result result = JUnitCore.runClasses(SnakeGame_UnitTests.class);
        for (Failure failure : result.getFailures()) {
            System.err.println(failure.toString());
        }
    }
}
