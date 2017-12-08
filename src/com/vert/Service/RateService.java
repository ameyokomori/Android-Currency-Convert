package com.vert.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.vert.Calculate.CalculateRate;
import com.vert.Datebase.DBAdapter;

public class RateService extends Service {
	
	private DBAdapter dbAdapter ;
	private Thread workThread;
	private static int timeCounter = 1;
	

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		dbAdapter = new DBAdapter(this);
	    dbAdapter.open();
	    
	    Toast.makeText(this, "汇率更新启动", Toast.LENGTH_LONG).show();    
	    workThread = new Thread(null,backgroudWork,"WorkThread");
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		
		if (!workThread.isAlive()){
	    	  workThread.start();
		}
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		Toast.makeText(this, "汇率更新停止", Toast.LENGTH_SHORT).show();     
	     workThread.interrupt();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Runnable backgroudWork = new Runnable(){
		@Override
		public void run() {
			try {
		
				while(!Thread.interrupted()){

					GetRateOnline();
					
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};
	
	private void GetRateOnline() {
		Log.i("TIMER",String.valueOf(timeCounter));
		if (timeCounter-- < 0){
			timeCounter = 600;
			Log.i("TIMER","NOW");
			try {
				GetRate.getRateOnline();
				CalculateRate.getRateFromDB();
				Toast.makeText(this, "汇率更新成功", Toast.LENGTH_LONG).show();
				dbAdapter.close();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	

}
