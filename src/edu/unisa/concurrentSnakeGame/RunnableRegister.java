package edu.unisa.concurrentSnakeGame;
import java.util.concurrent.ConcurrentNavigableMap;
/**
* This Class was used to register player concurrently isn't used in main program
*/
public class RunnableRegister implements Runnable {
	String userName;
	String passWord;
	ConcurrentNavigableMap<String, String> mappy;
	
	public RunnableRegister(String username, String password, ConcurrentNavigableMap<String, String> map){
		 userName = username;
		 passWord = password;
		 mappy = map;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			System.out.println("Working");
			Thread.currentThread().sleep(3000);
			account();
			System.out.println(userName + " finished at " + System.currentTimeMillis());
			
			
			
		}catch(Exception e){}
	}
	
	public void account(){
		mappy.put(userName, passWord);
	}

}
