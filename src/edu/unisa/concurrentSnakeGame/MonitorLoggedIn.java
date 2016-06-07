package edu.unisa.concurrentSnakeGame;

import java.util.ArrayList;
import java.util.concurrent.locks.*;

public class MonitorLoggedIn {
	private ArrayList<String> loggedInPlayers = new ArrayList<String>();
	private final Lock lock = new ReentrantLock();

	public boolean search(String p){
		lock.lock();
		try{
			for(String playa : loggedInPlayers){
				if(playa.equals(p)){
					return true;
				}
			}
		}finally{ 
			lock.unlock();
		}
		return false;
	}

	public void add(String p){
		lock.lock();
		try{
		loggedInPlayers.add(p);
		}finally{
			lock.unlock();
		}

	}

	public void delete(String p){
		lock.lock();
		try{
		loggedInPlayers.remove(p);
		}finally{
			lock.unlock();
		}
	}




}
