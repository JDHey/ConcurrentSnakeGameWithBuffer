package edu.unisa.concurrentSnakeGame;
import java.awt.event.KeyEvent;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RunnableAuth implements RunnableFuture{
	String userName;
	String passWord;
	ConcurrentNavigableMap<String, String> mappy;
	boolean pass;
	boolean isAi;
	ThreadPoolExecutor executor;
	static GameState myGame;
	static BufferIO myBuffer;
	static MonitorLoggedIn monitor;

	public RunnableAuth(String username, String password, ConcurrentNavigableMap<String, String> map, boolean isAI,ThreadPoolExecutor executor
			,GameState myGame, BufferIO myBuffer, MonitorLoggedIn monitor ){
		 userName = username;
		 passWord = password;
		 mappy = map;
		 isAi = isAI;
		 this.executor = executor;
		 RunnableAuth.myGame = myGame;
		 RunnableAuth.myBuffer = myBuffer;
		 RunnableAuth.monitor = monitor;
	}
	
	@Override
	public void run() {
		try{
			pass = false;
			pass = Auth(userName,passWord,mappy, isAi, executor, myGame, myBuffer, monitor);
	
		}catch(Exception e){}
	}
	
	public static boolean Auth(String Username, String Password,ConcurrentNavigableMap<String, String> map, boolean isAi, ThreadPoolExecutor exec,
			GameState myGme, BufferIO myBuff, MonitorLoggedIn mon ){
		System.out.println("tried " + Username);
		if(map.containsKey(Username)){
			if (map.get(Username).equalsIgnoreCase(Password)){
				if(isAi){
					PlayerAI player = new PlayerAI("AI " + Username, myBuff, myGme);
					System.out.println(Username + " passed" );
					exec.execute(player);
					mon.add("AI " + Username);
					return true;
				}else{
					new PlayerLocal(Username, myBuff, myGme, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT);
					System.out.println(Username + " passed" );
					mon.add(Username);
					return true;
				}
				
			}
		}
	
		System.out.println(Username + " failed" );
		return false;
	
	}
	
	@Override
	public Object get() throws InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		return pass;
	}
	
	/**
	* The below methods are not used
	*/
	
	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		// TODO Auto-generated method stub
		return false;
	}
	
}
