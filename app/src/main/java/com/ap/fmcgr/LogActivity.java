package com.ap.fmcgr;

import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

public class LogActivity extends ListActivity {
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);    
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);	//Ukrywa pasek powiadomie
		Intent intent = getIntent();																						//Przechwycenie danych z poprzedniej aktywno ci
		String[] values = new String[] { intent.getStringExtra("logStr") };													//Odczytanie klucza
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.log_activity, R.id.textViewLog, values); 	//Wczytanie layoutu elementu listy
	    setListAdapter(adapter);																							//Wywo anie adaptera listy
		Log.i("Log:" ,values[0]);																							//Opr cz wy wietlenie przekazuje plik logowania do okna LogCat
	}
	  
	  
	//PONI SZE FUNKCJE UMO LIWIAJ  PRZECHWYCENIE INTENCJI OD NFC
	//(dzi ki temu blokowane jest okno z wyborem akcji)
	
	private NfcAdapter mNfcAdapter;
	private PendingIntent mPendingIntent;
	private IntentFilter[] mFilters;
	private String[][] mTechLists;
	
	@Override
	protected void onResume(){
		super.onResume();		
	    mPendingIntent = PendingIntent.getActivity(this, 0,new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
	    IntentFilter filters = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
	    mFilters = new IntentFilter[] {filters,};
	    mTechLists = new String[][] { new String[] { android.nfc.tech.NfcV.class.getName() } };	   
	    mNfcAdapter = NfcAdapter.getDefaultAdapter(getApplicationContext());
	    mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, mFilters, mTechLists);
	}
	
	@Override
	protected void onPause(){
		mNfcAdapter.disableForegroundDispatch(this);
		super.onPause();
	}	
	
	@Override
	protected void onNewIntent(Intent intent){
		super.onNewIntent(intent);
		String action = intent.getAction();
		if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)){		
		}
	}
}
