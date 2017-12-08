package com.vert.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vert.R;
import com.vert.Calculate.CalculateRate;
import com.vert.Datebase.DBAdapter;
import com.vert.Datebase.SaveRate;

public class HistoryActivity extends Activity {
	
	private DBAdapter dbAdapter;
	
	public TextView rateHistory;
	public TextView rateState;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);
		
		rateHistory = (TextView) findViewById(R.id.rateHistory);
		rateState = (TextView) findViewById(R.id.rateState);
		
		dbAdapter = new DBAdapter(this);
		dbAdapter.open();

		showRate();
	}
	
	public void showRate() {
		SaveRate[] saveRate = dbAdapter.queryAllData();
		String Country = CalculateRate.code1 + "to" + CalculateRate.code2;
		if (saveRate == null){
//			rateState.setText("数据库中没有数据");
			Toast.makeText(getApplicationContext(), "数据库中没有数据", Toast.LENGTH_SHORT).show();
			return;
		}
		rateState.setText("汇率趋势：");
		String msg = "";
		for (int i = 0 ; i<saveRate.length; i++){
			if(saveRate[i].Country.equals(Country))
				msg += saveRate[i].toString()+"\n";
		}
		rateHistory.setText(msg);
		dbAdapter.close();
	}

}
