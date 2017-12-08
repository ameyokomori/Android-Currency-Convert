package com.vert.Activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vert.R;
import com.vert.Calculate.CalculateRate;
import com.vert.Datebase.DBAdapter;
import com.vert.Service.CheckNetwork;
import com.vert.Service.GetRate;
import com.vert.Service.RateService;


public class VertActivity extends Activity {
	
	final static int MENU_REFRESH_RATE= Menu.FIRST;
	final static int MENU_START_SERVICE= Menu.FIRST + 1;
	final static int MENU_STOP_SERVICE = Menu.FIRST + 2;
	final static int MENU_HISTORY = Menu.FIRST + 3;
	final static int MENU_QUIT = Menu.FIRST + 4;
	
	public static TextView tv;
	public static EditText money1;
	public static EditText money2;
	Spinner spinner1;
	Spinner spinner2;
	
	private DBAdapter dbAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// �����̵߳Ĳ���
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		// ����������Ĳ���
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
				.penaltyLog().penaltyDeath().build());
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vert);
		
		spinner1 = (Spinner) findViewById(R.id.Country1);
		spinner2 = (Spinner) findViewById(R.id.Country2);
		money1 = (EditText) this.findViewById(R.id.Money1);
		money2 = (EditText) this.findViewById(R.id.Money2);
		tv = (TextView) findViewById(R.id.CurrentRate);
		tv.setTextSize(10);

		dbAdapter = new DBAdapter(this); 
		dbAdapter.open();
		
		List<String> list = new ArrayList<String>();
		list.add("�����");
		list.add("��Ԫ");
		list.add("��Ԫ");
		list.add("ŷԪ");
		list.add("�۱�");
		list.add("�ӱ�");
		list.add("��Ԫ");

		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(adapter1);

		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner2.setAdapter(adapter2);
		spinner2.setSelection(1);
		
		if (CheckNetwork.isNetworkAvailable(VertActivity.this)) {
			Toast.makeText(getApplicationContext(), "����������", Toast.LENGTH_SHORT).show();

		} else {
			Toast.makeText(getApplicationContext(), "������������", Toast.LENGTH_SHORT).show();
		}
		
		spinner1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				CalculateRate.country1 = spinner1.getSelectedItem().toString();
				CalculateRate.code1 = CalculateRate.codePresent[position];
				money1.setText(null);
				money2.setText(null);
				CalculateRate.getRateFromDB();	
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		spinner2.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				CalculateRate.country2 = spinner2.getSelectedItem().toString();
				CalculateRate.code2 = CalculateRate.codePresent[position];
				money1.setText(null);
				money2.setText(null);
				CalculateRate.getRateFromDB();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		
		money1.setOnTouchListener(new EditText.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				money1.setText(null);
				money2.setText("0");
				return false;
			}
			
		});
		
		money2.setOnTouchListener(new EditText.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				money2.setText(null);
				money1.setText("0");
				return false;
			}
			
		});
		
		money1.setOnKeyListener(new EditText.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				CalculateRate.calculateMoney();
				return false;
			}
		});

		money2.setOnKeyListener(new EditText.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				CalculateRate.calculateMoney2();
				return false;
			}
		});	
		
	}
	
	@Override
	 public boolean onCreateOptionsMenu(Menu menu){

		menu.add(0,MENU_REFRESH_RATE,0,"ˢ�»���");
		menu.add(0,MENU_START_SERVICE,1,"��ȡ����");
		menu.add(0,MENU_STOP_SERVICE,2,"ֹͣ��ȡ");
		menu.add(0,MENU_HISTORY,3,"��������");
		menu.add(0,MENU_QUIT,4,"�˳�");
		return true;
	 }
	 
	 @Override
	 public boolean onOptionsItemSelected(MenuItem item){
		 final Intent serviceIntent = new Intent(this, RateService.class);
		 Intent intent = new Intent(VertActivity.this, HistoryActivity.class);
		 switch(item.getItemId()){
		 	case MENU_REFRESH_RATE:
		 		if (CheckNetwork.isNetworkAvailable(VertActivity.this)) {
					Toast.makeText(getApplicationContext(), "����������", Toast.LENGTH_SHORT).show();
					GetRate.getRateOnline();
					CalculateRate.getRateFromDB();
			 		Toast.makeText(getApplicationContext(), "ˢ�»������", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(), "������������", Toast.LENGTH_SHORT).show();
				}		 		
		 		return true;
		 	case MENU_START_SERVICE:
		 		startService(serviceIntent);
	    		return true;	
		 	case MENU_STOP_SERVICE:
		 		stopService(serviceIntent);
	    		return true;
		 	case MENU_HISTORY:
               startActivity(intent);
	    		return true;	    		
	    	case MENU_QUIT:
	    		finish();
	    		break;
	    	}	
	    	return false;
	  }
}
