package edu.unisa.concurrentSnakeGame;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RunnableAuth implements RunnableFuture{
	String userName;
	String passWord;
	ConcurrentNavigableMap<String, String> mappy;
	boolean pass;

	public RunnableAuth(String username, String password, ConcurrentNavigableMap<String, String> map){
		 userName = username;
		 passWord = password;
		 mappy = map;
		 
	}
	
	@Override
	public void run() {
		try{
			pass = false;
			pass = Auth(userName,passWord,mappy);
	
		}catch(Exception e){}
	}
	
	public static boolean Auth(String Username, String Password,ConcurrentNavigableMap<String, String> map ){
		System.out.println("tried " + Username);
		if(map.containsKey(Username)){
			if (map.get(Username).equals(Password)){
				System.out.println(Username + " passed" );
				return true;
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
