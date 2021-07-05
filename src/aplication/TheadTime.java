package aplication;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.TimeUnit;

public class TheadTime extends Thread{
	
	ServerSocket s;
	
	public TheadTime(ServerSocket s){
        this.s = s;
    }
	
	public void run() {
		try {
			TimeUnit.SECONDS.sleep(5);
			s.close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
