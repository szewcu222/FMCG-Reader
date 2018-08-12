//G  wna aktywno   aplikacji - odczyt danych z identyfikatora

package com.ap.fmcgr;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
																					//Nie informuje o mo liwo ci stosowania innych obiekt w do prezentacji czasu
public class ReadsActivity extends Activity {

	private NfcAdapter mNfcAdapter;																						//Deklaracja uchwytu do modu u NFC
	private PendingIntent mPendingIntent;																				//Deklaracja objektu do przechwytywania intencji z NFC
	private IntentFilter[] mFilters;																					//Deklaracja filtru dzi ki, kt remu aplikacja reaguje jedynie na identyfikatory zgodne z ISO15693 (NFC-V)
	private String[][] mTechLists;																						//Deklaracja listy obs ugiwanych technologii (rodzaj w protoko  w komunikacyjnych - tutaj tylko NFC-V)
	
	private TextView textViewReadedUidNumber;																			//Deklaracja uchwytu do pola tekstowego wy wietlaj cego numer UID
	private TextView textViewReadedArticleName;																			//Deklaracja uchwytu do pola tekstowego wy wietlaj cego nazw  produktu
	private TextView textViewReadedManufacturerName;																	//Deklaracja uchwytu do pola tekstowego wy wietlaj cego nazw  producenta
	private TextView textViewReadedPriceValue;																			//Deklaracja uchwytu do pola tekstowego wy wietlaj cego cen  produktu
	private ListView listViewReadedTagData;																				//Deklaracja uchwytu do pola tekstowego wy wietlaj cego pozosta e dane o produkcie
	private TextView textViewStatus;																					//Deklaracja uchwytu do pola tekstowego statusu (flaga App.SHOW_STATUS = true)
	private Button btnReadedAddToCurrentShoppingCart;																	//Deklaracja uchwytu do przycisku umo liwiaj cego dodanie produktu do listy zakup w
	private Button btnReadedClear;																						//Deklaracja uchwytu do przycisku umo liwiaj cego wyczyszczenie wy wietlanych informacji o zeskanowanym produkcie (wyczyszczenie informacji o produkcie)
	private Vibrator vibr;																								//Deklaracja uchwytu do funkcji wibracji urz dzenia
	private PopupWindow popupWindowAppStat;																				//Deklaracja obiektu do wy wietlania wyskakuj cego okienka statystyk
	
	private ProductInfo productInfo;																					//Deklaracja obiektu przechowuj cego informacje o oznakowanym produkcie
	private String logStr = "";																							//Deklaracja  a cucha znak w wykorzystywanego do logowania stanu aplikacji
	private SimpleDateFormat logTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS",Locale.getDefault());									//Deklaracja zmiennej przechowuj cej czas chwili logowania
	
	@SuppressLint("InlinedApi")																							//Wy  cza ostrzerzenie dotycz ce intencji android.provider.Settings.ACTION_NFC_SETTINGS, kt ra dost pna jest dopiero od 16 poziomu API (ostrze enie pojawia o si  po mimo,  e w kodzie zadbano o sprawdzenie poziomu API)
	@Override																				
	protected void onCreate(Bundle savedInstanceState){																	//Pierwsza metoda w cyklu  ycia aktywno ci. Wywo ywana jest po uruchomieniu aplikacji (tworzy ona interfejs graficzny pierwszej aktywno ci)
		super.onCreate(savedInstanceState);																				//Metoda dziedziczy po metodzie o tej samej nazwie z klasy Activity
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);													//Dodaje spinning wheel w prawym g rnym naro niku
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);	//Ukrywa pasek powiadomie
		setContentView(R.layout.reads_activity);																		//Wczytanie layoutu
				
		incrementStartupCounter();																						//Zwi kszenie licznika uruchomie  programu
		
		textViewReadedUidNumber = (TextView) findViewById(R.id.textViewReadedUidNumber);								//Utworzenie uchwytu do pola tekstowego wy wietlaj cego numer UID
		textViewReadedArticleName = (TextView) findViewById(R.id.textViewReadedProductName);							//Utworzenie uchwytu do pola tekstowego wy wietlaj cego nazw  produktu
		textViewReadedManufacturerName = (TextView) findViewById(R.id.textViewReadedManufacturerName);					//Utworzenie uchwytu do pola tekstowego wy wietlaj cego nazw  producenta
		textViewReadedPriceValue = (TextView) findViewById(R.id.textViewReadedPriceValue);								//Utworzenie uchwytu do pola tekstowego wy wietlaj cego cen
		listViewReadedTagData = (ListView) findViewById(R.id.listViewReadedTagData);									//Utworzenie uchwytu do pola listy wy wietlaj cej pozosta e informacje o produkcie
		textViewStatus = (TextView) findViewById(R.id.textViewStatus);													//Utworzenie uchwytu do pola wy wietlaj cego informacje przydatne na etapie pisania aplikacji (flaga App.SHOW_STATUS = true)
		btnReadedAddToCurrentShoppingCart = (Button) findViewById(R.id.btnReadedAddToCurrentShoppingCart);				//Utworzenie uchwytu do przycisku dodaj cego bie  cy produkt do listy zakup w
		btnReadedClear = (Button) findViewById(R.id.btnReadedClear);													//Utworzenie uchwytu do przycisku czyszcz cego pola tekstowe tej aktywno ci i usuwaj cego informacje o produkcie
		vibr = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);													//Uchwyt do funkcji wibracji
				
		if(App.SHOW_STATUS == true) 	textViewStatus.setVisibility(TextView.VISIBLE);									//Je eli pasek status jest aktywny (flaga App.SHOW_STATUS = true) to wy wietla na dole pasek statusu
		else							textViewStatus.setVisibility(TextView.GONE);								//w przeciwnym razie jest on ukrywany
		
        btnReadedAddToCurrentShoppingCart.setOnClickListener(mReadedAddToCurrentShoppingCart);							//Uruchamia nas uchiwanie naci ni cia przycisku dodawania produkt w do listy zakup w
        btnReadedClear.setOnClickListener(mReadedClear);																//Uruchamia nas uchiwania naci ni cia przycisku czyszczenia aktywno ci odczytu i zerowania obiektu productInfo
		
	    PackageManager pm = getPackageManager();			 															//Pobiera informacje o funkcjach urz dzenia, na kt rym uruchomiono aplikacj
    	if(!pm.hasSystemFeature(PackageManager.FEATURE_NFC)){															//Je eli w urz dzeniu brak jest modu u NFC
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.nfc_module_not_detected), Toast.LENGTH_LONG).show(); 	//To wy wietlana jest informacja o braku modu u
            finish();																									//a nast pnie aplikacja jest zamykana
            return;
    	}
    	else{ 																											//Je eli urz dzenie jest wyposa one w modu  NFC
    		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);															//Tworzy uchwyt do modu u NFC
	        if (!mNfcAdapter.isEnabled()){ 																				//Je eli NFC jest aktualnie wy  czone
	        	Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.please_activate_nfc), Toast.LENGTH_LONG).show(); 	//Wy wietla pro b  o uruchomienie modu u NFC
	            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {					//W przypadku uruchomienia aplikacji na urz dzeniu z system Android 4.1 (API 16) lub nowszym
		            startActivity(new Intent(android.provider.Settings.ACTION_NFC_SETTINGS));							//Przekierowuje do okna funkcji NFC gdzie mo na j  katywowa
	            }else{																									//Je eli aplikacja zosta a uruchomina na urz dzeniu ze starsz  wersj  systemu
		            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));						//Przekierowuje do okna ustawie  transmisji bezprzewodowej
	            }       
	        }
	        else{																										//Je eli NFC jest aktualnie w  czone
	        	setFilters();																							//Ustawia filtrowanie obs ugiwanych protoko  w
		        handleIntent(getIntent());																				//Pr buje przechwyci  intencj  na wypadek gdyby program zosta  uruchomiony przez wykrycie indetyfikatora
	        }
    	}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){																		// adowanie menu podr cznego
		getMenuInflater().inflate(R.menu.reads_menu, menu);																//Zdefiniowanego w pliku reads_menu.xml
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){																//Przechwycenie naci ni cia kt rego  z wy wietlanych pozycji menu podr cznego
		Intent myIntent;																								//Deklaracja obiektu intencji wykorzystywanego do uruchamiania innych aktywno ci
		switch (item.getItemId()){																						//Identyfikuje wybran  pozycj  z menu na podstawie jej numeru id
			case R.id.action_shopping_cart:																				//Je eli id wybranej pozycji zgadza si  z numerem id pozycji action_shopping_cart
		    	myIntent = new Intent(ReadsActivity.this, ShoppingCartActivity.class);									//Definiuje intencj  prze  czenia bie  cej aktywno ci na aktywno   listy zakup w
		    	startActivity(myIntent);																				//Uruchamia now  aktywno   na podstawie zdefiniowanej intencji
			return true;																								//Zwraca true co  wiadczy o prawid owym wybraniu pozycji z menu
			case R.id.action_statistics:																				//Je eli id wybranej pozycji zgadza si  z numerem id pozycji action_statistics
				showPopupWindowReadsStat();																				//Uruchamia metod  wy wietlaj c  wyskakuj ce okienko statystyka aplikacji
			return true;																								//Zwraca true co  wiadczy o prawid owym wybraniu pozycji z menu
			case R.id.action_settings:																					//Je eli id wybranej pozycji zgadza si  z numerem id pozycji action_settings
				myIntent = new Intent(ReadsActivity.this, PreferencesActivity.class);									//Definiuje intencj  prze  czenia bie  cej aktywno ci na aktywno   ustawie  programu
				startActivity(myIntent);																				//Uruchamia now  aktywno   na podstawie zdefiniowanej intencji
			return true;																								//Zwraca true co  wiadczy o prawid owym wybraniu pozycji z menu
			case R.id.action_log:																						//Je eli id wybranej pozycji zgadza si  z numerem id pozycji action_log
				myIntent = new Intent(ReadsActivity.this, LogActivity.class);											//Definiuje intencj  prze  czenia bie  cej aktywno ci na aktywno   wy wietlaj cej log programu
				myIntent.putExtra("logStr", logStr);																		//Dodaje dodatkow  zawarto   do intencji
				startActivity(myIntent);																				//Uruchamia now  aktywno   na podstawie zdefiniowanej intencji
			return true;
			case R.id.action_about:																						//Je eli id wybranej pozycji zgadza si  z numerem id pozycji action_about
		    	myIntent = new Intent(ReadsActivity.this, AboutActivity.class);											//Definiuje intencj  prze  czenia bie  cej aktywno ci na aktywno   "O programie"
		    	startActivity(myIntent);																				//Uruchamia now  aktywno   na podstawie zdefiniowanej intencji
			return true;																								//Zwraca true co  wiadczy o prawid owym wybraniu pozycji z menu
			default:																									//Je eli id nie zgadza si  z powy szymi
				return super.onOptionsItemSelected(item);																//To wywo ywana jest metoda obs ugi menu zdefiniowana w klasie Activity
		} 		
	}
		
	@Override
	protected void onResume(){ 																							//Trzecia z koleji metoda w cyklu  ycia aktywno ci. Wywo ywana jest po metodzie onCreate() i onStart() lub po powrocie z innej aktywno ci (zar wno tej aplikacji jak i zewn trzej np. uruchomieniu NFC)
		super.onResume();																								//Wykonuje operacje zdefiniowane w metodzie po kt rej dziedziczy
        setFilters();																									//Ustawienie filtr w
	    mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, mFilters, mTechLists);				    			//Przechwycenie intencji od NFC. Metoda ta sprawia,  e wykrycie nowego identyfikatora nie powoduje kolejnego uruchomienia aplikacji tylko powoduje wys anie intencji do bie acej aktywno ci
	}
    
	@Override
	protected void onPause(){ 																							//Pierwsza w kolejno ci metoda z cyklu  ycia aktywno ci wywo ywana w chwili gdy uruchamiana jest nowa aktywno   lub program jest zamykany
		mNfcAdapter.disableForegroundDispatch(this);																	//Wy  czenie przekierowania nowych intencji od modu u NFC do otwartej aktywno ci
		super.onPause();																								//Wykonuje operacje zdefiniowane w metodzie po kt rej dziedziczy
	}	
	
	@Override
	protected void onNewIntent(Intent intent){																			//Metoda wykonywana w chwili nadej cia nowej intencji. W tym przypadku wykrycia identyfikatora
		super.onNewIntent(intent);																						//Wykonuje operacje zdefiniowane w metodzie po kt rej dziedziczy
		handleIntent(intent);																							//Uruchamia metod  obs ugi przychodz cej intencji
	}
	
	private void setFilters(){																							//Metoda konfiguruj ca filtr obs ugiwanych protoko  w NFC
        mPendingIntent = PendingIntent.getActivity(this, 0,new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);	//Konfiguruje obs ug  przeychodz cej intencji. Je eli g  wna aktywno   programu nie zosta a jeszcze uruchomiona to mo e zosta  uruchomiona przez przychodz c  intencj .
        IntentFilter filters = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);										//Definiuje filtr intencji (wywo ywanie intencji po wykryciu identyfikatora obs uguj cego protoko  wymieniony w filtrze)
        mFilters = new IntentFilter[] {filters,};																		//Wczytuje filtry zapisane w pliku nfc_tech_filter
        mTechLists = new String[][] { new String[] { android.nfc.tech.NfcV.class.getName() } };							//Tworzy list  dost pnych technologii (obs ugiwanych protoko  w)
	}
	
	private void handleIntent(Intent intent){																			//Metoda obs ugi odebranej intencji od modu u NFC
		String action = intent.getAction();																				//Odczytuje akcje zawart  w intencji
		if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)){    														//Je eli intencja odpowiada wykryciu identyfikatora RFID obs uguj cego protok  wymieniony w filtrze (tj. NFC-V czyli ISO15693)
			clearView();																								//Zeruje pola tekstowe i objekt productInfo
			Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);										//Tworzy uchwyt do identyfikatora RFID
	    	MyTag myTag = new MyTag(tagFromIntent);																		//Na podstawie uchwytu do identyfikatora tworzona jest instancja obiektu przechowuj cego informacje o chipie RFID
	    		    	
			SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());						//Odczytywane s  ustawienia aplikacji takie jak:
			myTag.setAddressedModeFlag(SP.getBoolean("pref_vcd_addressed_mode",false));									//Uruchomienie komunikacji z chipem w trybie adresowania
			myTag.setHighDataRateFlag(SP.getBoolean("pref_vicc_high_data_rate",false));									//Odpowied  chipu w trybie zwi kszonej przep ywno ci danych
			myTag.setDoubleSubcarrierFlag(SP.getBoolean("pref_vicc_double_subcarrier",false));							//Odpowied  chipu przekazywana za pomoc  modulacji z dwiema cz. podno nymi (tj. FSK zamiast ASK)

			logStr = logTime.format(new Date());																		//Odczytuje bie  cy czas i zamienia na String wg podanego w konstruktorze formatowania
			logStr += "\nmyTag.getCurrentTag().getId():\n" + MyTag.ConvertHexByteArrayToString(myTag.getCurrentTag().getId());	//Tworzony jest  a cuch znak w zawieraj cy numer UID pobrany w prost z intencji
			
	    	myTag = ISO15693.SendInventoryCommand(myTag);																//Wysy a prost  komend  inwentaryzacji bez obs ugi antykolizji
	    	byte[] inventoryResponse = myTag.getLastResponse();									    					//Przechowuje odpowied  na komend  inwentaryzacji
							    	
	    	logStr += "\n\n" + logTime.format(new Date());																//Odczytuje bie  cy czas i zamienia na String wg podanego w konstruktorze formatowania
	    	logStr += "\n(0x01) Inventory: ";																			
	    	logStr += "\n-> " + MyTag.ConvertHexByteArrayToString(myTag.getLastRequest());								// a cuch znak w jest rozszerzany o ramk  polecenia inwentaryzacji
			logStr += "\n<- " + MyTag.ConvertHexByteArrayToString(myTag.getLastResponse());								// a cuch znak w jest rozszerzany o ramk  odpowiedzi na polecenie inwentaryzacji
				    		    	
	    	if(myTag.decodeInventoryResponse(inventoryResponse)){														//Je eli prawid owo rozpoznano i zdekodowano UID identyfikatora
		    	myTag = ISO15693.SendGetSystemInformationCommand(myTag);												//Wysy ana jest komenda Get System Information
		    	byte[] getSystemInformationResponse = myTag.getLastResponse();											//Przechowuje odpowied  na komend  Get System Information
				
		    	logStr += "\n\n" + logTime.format(new Date());															//Odczytuje bie  cy czas i zamienia na String wg podanego w konstruktorze formatowania
		    	logStr += "\n(0x2B) Get System Information: ";
		    	logStr += "\n-> " + MyTag.ConvertHexByteArrayToString(myTag.getLastRequest());							// a cuch znak w jest rozszerzany o ramk  polecenia Get System Information
				logStr += "\n<- " + MyTag.ConvertHexByteArrayToString(myTag.getLastResponse());							// a cuch znak w jest rozszerzany o ramk  odpowiedzi na polecenie Get System Information
		    			    	
		    	if(myTag.decodeGetSystemInfoResponse(getSystemInformationResponse)){									//Je eli prawid owo odczytano dane o identyfikatorze z komendy
					//textViewStatus.setText(MyTag.ConvertHexByteArrayToString(myTag.getLastRequest()));				//Wy wietla ramk  komendy get system information
			    	//textViewStatus.setText(MyTag.ConvertHexByteArrayToString(getSystemInformationResponse));			//Wy wietla odpowied  na komend  GetSystemInformation
			    	//textViewStatus.setText(MyTag.ConvertHexByteToString(myTag.getIcReference()));						//Wy wietla fragment zwr conej informacji (np. ICreference)
			    	//textViewStatus.setText(MyTag.ConvertHexByteArrayToString(myTag.getMemorySize()));	   				//Wy wietla fragment zwr conej informacji (np. MemorySize)
			    	//textViewStatus.setText(Integer.toString(myTag.getBlockNumber()));									//Wy wietla wyznaczone wartosci (np. BlockNumber)
			    	//textViewStatus.setText(Integer.toString(myTag.getBlockSize()));									//Wy wietla wyznaczone wartosci (np. BlockSize)
			    	
		    		//myTag = ISO15693.SendReadSingleBlockCommand(myTag, (byte) 0x00);									//Komenda odczytu pojedynczego bloku zgodnie z ISO15693
			    	//myTag = ISO15693.SendReadSingleBlockCommand(myTag, (short) 0x0000);								//Komenda odczytu pojedynczego bloku przy adresowaniu dwoma bajtami (ST M24LR64)
			    	//myTag = ISO15693.SendReadMultipleBlocksCommand(myTag, (byte) 0x00, (byte) 0x1F);					//Komenda odczytu do 32 blok w zgodnie z ISO15693
			    	//myTag = ISO15693.SendReadMultipleBlocksCommand(myTag, (short) 0x0000, (byte) 0x1F);				//Komenda odczytu do 32 blok w przy adresowaniu dwoma bajtami (ST M24LR64)
			    	//myTag = ISO15693.SendReadMultipleBlocksCommand(myTag, (short) 0x0001, (byte) 0x1E);				//W przypadku chip w (M24LR64) zakres odczytywanych blok w musi si  mie ci  w obr bie pojedynczego sektora (0-31, 32-63 itd.)
		    		
		    		//myTag = ISO15693.SendSeveralReadMultipleBlocksCommand(myTag, (short) 0x007C, (short) 0x66);		//Wielokrotne wys anie komendy read multiple blocks w celu odczytu wi cej ni  32 blok w - metoda najbardziej uniwersalna, pozwala na odczyt dowolnej liczby blok w zar wno w trybie adresowania jednobajtowego jak i dwubajtowego z automatycznym prze aczaniem pomi dzy sektorami
		    		//myTag = ISO15693.SendSeveralReadMultipleBlocksCommand(myTag, (short) 0x0000, (short) 0x66);
		    		//myTag = ISO15693.SendSeveralReadMultipleBlocksCommand(myTag, (short) 0x0000, (short) 0x40);
		    		//myTag = ISO15693.SendSeveralReadMultipleBlocksCommand(myTag, (short) 0x0000, (short) 0x3F);
		    		//myTag = ISO15693.SendSeveralReadMultipleBlocksCommand(myTag, (short) 0x0000, (short) 0x20);
		    		//myTag = ISO15693.SendSeveralReadMultipleBlocksCommand(myTag, (short) 0x0001, (short) 0x20);
		    		//myTag = ISO15693.SendSeveralReadMultipleBlocksCommand(myTag, (short) 0x0017, (short) 0x09);
		    		//myTag = ISO15693.SendSeveralReadMultipleBlocksCommand(myTag, (short) 0x0000, (short) 0x1B);
		    		//myTag = ISO15693.SendSeveralReadMultipleBlocksCommand(myTag, (short) 0x0000, (short) 0x1C);
		    		//myTag = ISO15693.SendSeveralReadMultipleBlocksCommand(myTag, (short) 0x0000, (short) 0x20);		
		    		
		    		new ReadDataAsyncTask(myTag).execute();																//Ze wzgl du na czasoch onn  operacj  odczytu niekt rych identyfikator w, operacja ta realizowana jest w osobnym asynchronicznym w tku. Dzi ki temu aplikacja odpowiada na polecenia u ytkownika nawet w trakcie odczytu
		    	}
		    	else{
		    		logStr += "\n\n" + logTime.format(new Date());														//Odczytuje bie  cy czas i zamienia na String wg podanego w konstruktorze formatowania
		    		logStr += "\nGet System Information Fail!\n";
		    		if(App.SHOW_STATUS == true) textViewStatus.setText("Get System Information Fail!\n" + MyTag.ConvertHexByteArrayToString(myTag.getLastRequest()) + "\n" + MyTag.ConvertHexByteArrayToString(myTag.getLastResponse()));
    				else						Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.read_error_toast), Toast.LENGTH_LONG).show(); 	
		    	}
	    	}
			else{
				logStr += "\n\n" + logTime.format(new Date());															//Odczytuje bie  cy czas i zamienia na String wg podanego w konstruktorze formatowania
				logStr += "\nInventory Request Fail!";
				if(App.SHOW_STATUS == true)	textViewStatus.setText("Inventory Request Fail!" + MyTag.ConvertHexByteArrayToString(myTag.getLastRequest()) + "\n" + MyTag.ConvertHexByteArrayToString(myTag.getLastResponse()));																	//Wy wietla komunikat w polu tekstowy statusu
				else						Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.read_error_toast), Toast.LENGTH_LONG).show(); 
	    	}
		}
	}
	
	private class ReadDataAsyncTask extends AsyncTask<Void, Void, Void> {												//Asynchroniczny w tek do realizacji czasoch onnych operacji w tle
		MyTag asyncMyTag;																								//Pole klasy watku
		
		public ReadDataAsyncTask(MyTag asyncMyTag){																		//Konstruktor do przekazywania zmiennych do w tku asynchronicznego
			this.asyncMyTag = asyncMyTag;																				//Przekazuje objekt productInfo do w tku asynchronicznego
		}
				
	    @Override
	    protected void onPreExecute() {																					//Metoda wywo ywana przed uruchomieniem w tle czasoch onnej operacji
	        super.onPreExecute();
	        setProgressBarIndeterminateVisibility(true);																//Pokazuje spinning wheel w prawym g rnym naro niku
	    }
	 
	    @Override
	    protected Void doInBackground(Void... params) {																	//Metoda wykonywana w tle przez w tek (najbardziej czasoch onny fragment)
	    	asyncMyTag = ISO15693.SendSeveralReadMultipleBlocksCommand(asyncMyTag, (short) 0x0000, (short) (asyncMyTag.getBlockNumber()-1));    //Odczyt ca ej pami ci chipu
	    	
	    	logStr += "\n\n" + logTime.format(new Date());																//Odczytuje bie  cy czas i zamienia na String wg podanego w konstruktorze formatowania
	    	logStr += "\n(0x23) Read Multiple Blocks\n(last request and full response):";								//Generuje nag  wek dla etapu odczytu
			logStr += "\n0x23-> " + MyTag.ConvertHexByteArrayToString(asyncMyTag.getLastRequest());						//Loguje ostatnie polecenie
			logStr += "\n0x23<- " + MyTag.ConvertHexByteArrayToString(asyncMyTag.getReadedTagMemory());					//Loguje ca a odczytan  pami
	    	return null;
	    }
	 
	    @Override
	    protected void onProgressUpdate(Void... values) {																//Metoda wywo ywana cyklicznie w trakcie trwania
	        super.onProgressUpdate(values);
	    }
	 
	    @Override
	    protected void onPostExecute(Void result) {																		//Metoda wywo ywana na zako czenie w tku
	        super.onPostExecute(result);
	        setProgressBarIndeterminateVisibility(false);																//Ukrywa spinning wheel w prawym g rnym naro niku
	        	        
	        byte[] readBlocksResponse = asyncMyTag.getReadedTagMemory();												//Kopiuje odczytan  pami   do lokalnej tablicy bajt w
      		 		
    		productInfo = new ProductInfo(asyncMyTag.getUid());															//Przypisuje polu productInfo nowy objekt z wype nionym numerem UID
    		if(productInfo.setMapOfDataFromRawData(readBlocksResponse)){												//Na podstawie odczytanej zawarto ci pami ci chipu tworzy pary klucz (np. AI01) - dane (np. 05900531000690) tzw. hash map
    			if(productInfo.fillProductInfo(productInfo.getMapOfData())){											//Na podstawie utworzonej hash mapy wype niane s  pola objektu productInfo
	    			if(productInfo.getProductName()!=null) textViewReadedUidNumber.setText(MyTag.ConvertHexByteArrayToString(productInfo.getUid()));			//Wy wietla numer UID zapisany w objecie productInfo
		    		if(productInfo.getProductName()!=null) textViewReadedArticleName.setText(productInfo.getProductName());										//Wy wietla nazw  produktu zapisan  w objekcie productInfo
	    			if(productInfo.getManufacturerName()!=null) textViewReadedManufacturerName.setText(productInfo.getManufacturerName());						//Wy wietla nazw  producenta zapisan  w objekcie productInfo
	    			if(productInfo.getAmount()!=null)	textViewReadedPriceValue.setText(productInfo.getAmount().toString() + " " + productInfo.getCurrency().getCurrencyNameCode());	//Wy wietla cen  produktu oraz jej walut , kt re s  zapisane w objekcie productInfo

	    			final List<String[]> currentProductFeaturesList = productInfo.trimListViewProductInfo(productInfo.genListViewProductInfo(getApplicationContext()));
	    			ReadsActivityListAdapter adapter = new ReadsActivityListAdapter(ReadsActivity.this, R.layout.reads_row, currentProductFeaturesList);
	    			listViewReadedTagData.setAdapter(adapter);
	    			
	    			incrementReadedTagCounter();																		//Inkrementuje licznik odczytanych identyfikator w
	    				    		    
					listViewReadedTagData.setOnItemClickListener(new OnItemClickListener(){								//Nas uchuje naci ni cia element w listy
						  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {  		
							  String key = currentProductFeaturesList.get(position)[ProductInfo.keyColIndex];			//Pobiera klucz na podstawie pozycji
							  							  
							  if(key.equalsIgnoreCase(ProductInfo._WA_STR)){											//Sprawdza czy klikni to pole zawieraj ce adres www
								  String url = currentProductFeaturesList.get(position)[ProductInfo.valueColIndex];		//Pobiera adres www w postaci  a cucha znak w
								  try{
									  if (!url.startsWith("http://") && !url.startsWith("https://")) 					//Sprawdza czy  a cuch zaczyna si  od http lub https
										  url = "http://" + url;														//Je eli nie to uzupe nia adres o przedrostek protoko u http
									  Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));			//Tworzy now  intencj , do kt rej przekazywany jest adres uri na podstawie adresu url
									  startActivity(browserIntent);														//Uruchamia przegl dark  i przekierowuje na podan  stron  www
								  }catch(NullPointerException e){														//Wychwytuje wyj tki
										Log.d("NullPointerException of Url", url);
										Log.i("Exception","Null Pointer Exception " + e.getMessage());
								  }
							  }
							  
							  if(key.equalsIgnoreCase(ProductInfo._AI15_STR)){											//Sprawdza czy klikni to pole zawieraj ce dat  "najlepiej zu y  przed"
								  Calendar bestBeforeDate = productInfo.getBestBeforeDate();							//Pobiera dat  najlepszego zu ycia produktu
								  Calendar today = Calendar.getInstance();												//Pobiera dat  dzisi jsz
								  Long diffDays = (bestBeforeDate.getTimeInMillis() - today.getTimeInMillis())/(24*60*60*1000);	//Oblicza r nic  pomi dzy datami wyra on  w dniach
								  if(diffDays == 1){																	//Gdy pozosta  jeden dzie
									  Toast.makeText(getApplicationContext(),diffDays.toString() + " "
											  + getApplicationContext().getResources().getString(R.string.day) + " "
											  + getApplicationContext().getResources().getString(R.string.toast_to_the_end),
											  Toast.LENGTH_LONG).show();												//Wy wietla toast informuj cy o ilo ci dni w ci gu kt rych najlepiej zu y  produkt
								  }else{																				//Gdy pozosta o wi cej dni
									  Toast.makeText(getApplicationContext(),diffDays.toString() + " "
											  + getApplicationContext().getResources().getString(R.string.days) + " "
											  + getApplicationContext().getResources().getString(R.string.toast_to_the_end),
											  Toast.LENGTH_LONG).show();												//Wy wietla toast informuj cy o ilo ci dni w ci gu kt rych najlepiej zu y  produkt
								  }  
							  }
							  
							  if(key.equalsIgnoreCase(ProductInfo._AI17_STR)){											//Sprawdza czy klikni to pole zawieraj ce dat  wa no ci
								  Calendar expirationDate = productInfo.getExpirationDate();							//Pobiera dat  wa no ci produktu
								  Calendar today = Calendar.getInstance();												//Pobiera dat  dzisi jsz
								  Long diffDays = (expirationDate.getTimeInMillis() - today.getTimeInMillis())/(24*60*60*1000);	//Oblicza r nic  pomi dzy datami wyra on  w dniach
								  if(diffDays == 1){																	//Gdy pozosta  jeden dzie
									  Toast.makeText(getApplicationContext(),diffDays.toString() + " "
											  + getApplicationContext().getResources().getString(R.string.day) + " "
											  + getApplicationContext().getResources().getString(R.string.toast_to_the_end),
											  Toast.LENGTH_LONG).show();												//Wy wietla toast informuj cy o ilo ci dni do up yni cia daty wa no ci
								  }else{																				//Gdy pozosta o wi cej dni
									  Toast.makeText(getApplicationContext(),diffDays.toString() + " "
											  + getApplicationContext().getResources().getString(R.string.days) + " "
											  + getApplicationContext().getResources().getString(R.string.toast_to_the_end),
											  Toast.LENGTH_LONG).show();												//Wy wietla toast informuj cy o ilo ci dni do up yni cia daty wa no ci
								  }
							  }
						  }
					});		
					vibr.vibrate(200);																					//Sygna  wibracyjny o zako czeniu procesu odczytu
    			}
    			else{
    				logStr += "\n\n" + logTime.format(new Date());														//Odczytuje bie  cy czas i zamienia na String wg podanego w konstruktorze formatowania
    				logStr += "\nFill Object Fail!:\r\n";
    				if(App.SHOW_STATUS == true)	textViewStatus.setText("Fill Object Fail!:\r\n" + MyTag.ConvertHexByteArrayToString(readBlocksResponse));			    
    				else 						Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.read_error_toast), Toast.LENGTH_LONG).show(); 
    				
    			}
    		}	
    		else{
    			logStr += "\n\n" + logTime.format(new Date());															//Odczytuje bie  cy czas i zamienia na String wg podanego w konstruktorze formatowania
    			logStr += "\nSplit Readed Data Fail!:\r\n";
    			if(App.SHOW_STATUS == true)	textViewStatus.setText("Split Readed Data Fail!:\r\n" + MyTag.ConvertHexByteArrayToString(readBlocksResponse));
				else						Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.read_error_toast), Toast.LENGTH_LONG).show(); 
			}
	    }
	 
	    @Override
	    protected void onCancelled() {
	        super.onCancelled();
	    }
	 
	}
	
	Button.OnClickListener mReadedAddToCurrentShoppingCart = new Button.OnClickListener() {								//Metoda wywo ywana w skutek naci ni cia przycisku "Zr b"
		public void onClick(View v) {					    		
    		if(productInfo != null){																					//Sprawdza czy obiekt zosta  prawid owo zainicjowany
        		App app = (App)getApplication();																		//Od wie a zmienne globalne
        		Boolean newProductFlag = true;																			//Deklaracja flagi okre laj cej czy zeskanowano inny produkt ni  te znajduj ce si  na li cie
        		byte[] currentProductInfoUid = productInfo.getUid();													//Pobiera numer UID aktualnego produktu
    			        			
        		String tempStr = "_______Current UID_______-_______UID on list_______";
        		
        		if(currentProductInfoUid != null){																		//Sprawdza czy numer UID nie jest pusty co  wiadczy o by o nie odczytaniu identyfikatora
		    		if(app.currentShoppingCart.isEmpty()){																//Sprawcza czy lista nie jest pusta
		    			newProductFlag = true;																			//Ustawia flag  informuj c  o produkcie, kt rego nie ma na li cie
		    		}else{																								//Je eli lista nie jest pusta to nale y j  przeszuka  i sprawdzi  czy bie  cy numer UID ju  si  na niej znajduje
		    			newProductFlag = true;																			//Pierwotnie zak ada si   e numer mo e ju  by  na li cie
		        		for(ProductInfo currentShoppingCartRow: app.currentShoppingCart){								//P tla sprawdzaj ca wszystkie pozycje na li  ie
		        			byte[] currentShippingLisRowUid = currentShoppingCartRow.getUid();							//Pobiera numer UID z kolejnych wieraszy listy
		        			Boolean uidByteEqualFlag = true;															//Flaga informuj ca czy kolejne bajty numer w UID s  takie same
		        			
		        			for(int i=0;i<currentProductInfoUid.length;i++){											//Sprawdza kolejne bajty numeru UID produktu i por wnuje je z bajtami kolejnych numer w UID pozycji na li cie zakup w
		        				if(currentProductInfoUid[i] != currentShippingLisRowUid[i]){							//Je eli wykryje rozbie no   pomi dzy bajtami numer w UID to traktuje produkt jako nowy
		        					uidByteEqualFlag = false;										
		        				}
		        			}
		        			if(uidByteEqualFlag == true){ 																//Je li cho  jeden bajt w numerach UID jest r ny to mamy do czyniena z innym egzemplarzem produktu
		        				newProductFlag = false;																	//Je eli aktualnego numeru UID nie ma na li cie to obiekt traktowany jest jako nie dodany do listy zakup w
		        			}
		        			tempStr += "\n" + MyTag.ConvertHexByteArrayToString(currentProductInfoUid) + " - " + MyTag.ConvertHexByteArrayToString(currentShippingLisRowUid);		//Tworzy por wnanie numeru UID bie  cego identyfikatora z numerami produkt w na li cie
		        		}
		    		}
		    		if(newProductFlag == true){																			//Je eli produktu dotychczas nie by o na li cie
		    			app.currentShoppingCart.add(productInfo);														//Produkt dodawany jest do listy
		    			incrementAddToShoppingCartCounter();															//Inkrementuje sumaryczny licznik produkt w dodanych do listy zakup w
		    			clearView();																					//Zeruje pola tekstowe aktywno ci odczytu
		    		}else{																								//W przeciwnym razie wy wietalny jest komunikat informuj cy o tym,  e produkt znajduje si  ju  na li cie zakup w
		    			Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.product_exists_on_your_shopping_cart_toast), Toast.LENGTH_LONG).show();
		    			logStr += "\n\n" + logTime.format(new Date());													//Odczytuje bie  cy czas i zamienia na String wg podanego w konstruktorze formatowania
		    			logStr += "\n" + tempStr;																		//Dodaje komunikat do log w
		    			if(App.SHOW_STATUS == true) textViewStatus.setText(tempStr);									//Je eli pasek statusu jest aktywny to wy wietla ten komunikat na dole ekranu
		    		}        			
        		}
    		}
		}
	};		
	
	Button.OnClickListener mReadedClear = new Button.OnClickListener() {												//Metoda wywo ywana w skutek naci ni cia przycisku "Zr b"
		public void onClick(View v) {					
			clearView();																								//Zeruje pola tekstowe aktywno ci odczytu
		}
	};		
	
	private void clearView(){																							//Zeruje pola tekstowe aktywno ciu odczytuj cej dane z identyfikatora obiektu
		textViewReadedUidNumber.setText("--");	
		textViewReadedArticleName.setText("--");
		textViewReadedManufacturerName.setText("--");
		textViewReadedPriceValue.setText("--");
		listViewReadedTagData.setAdapter(null);
		
		logStr = "";																										//Zeruje logowania
		productInfo = new ProductInfo();																				//Usuwa informacje o bie acym produkcie
	}
	
	private void incrementStartupCounter(){																				//Metoda inkrementuj ca licznik uruchomie  programu
	    SharedPreferences appStat = getSharedPreferences(App.SP_APP_STAT_FILE_NAME, 0);									//Uchwyt do pliku przechowuj cego dane statystyczne aplikcji
	    Integer appStartupCnt = appStat.getInt(App.SP_STARTUP_CNT, 0);													//Pobiera bie  c  warto   licznika uruchomie  programu
	    appStartupCnt++;																								//Zwi ksza licznik uruchomie  programu o jeden
	    SharedPreferences.Editor editor = appStat.edit();																//Utworzenie tymczasowego obiektu modyfikuj cego bie  ce statystyki
	    editor.putInt(App.SP_STARTUP_CNT, appStartupCnt);																//Zmiana bie  cej warto ci licznika uruchomie  programu
	    editor.commit();																								//Zapis nowych danych do pliku przechowuj cego dane statystyczne aplikacji
	}
	
	private void incrementReadedTagCounter(){																			//Metoda inkrementuj ca licznik odczytanych identyfikator w RFID
	    SharedPreferences appStat = getSharedPreferences(App.SP_APP_STAT_FILE_NAME, 0);									//Uchwyt do pliku przechowuj cego dane statystyczne aplikcji
	    Integer readedTagCnt = appStat.getInt(App.SP_READED_TAG_CNT, 0);												//Pobiera bie  c  warto   licznika odczytanych identyfikator w
	    readedTagCnt++;																									//Zwi ksza licznik odczytanych identyfikator w o jeden
	    SharedPreferences.Editor editor = appStat.edit();																//Utworzenie tymczasowego obiektu modyfikuj cego bie  ce statystyki
	    editor.putInt(App.SP_READED_TAG_CNT, readedTagCnt);																//Zmiana bie  cej warto ci licznika odczytanych identyfikator w
	    editor.commit();																								//Zapis nowych danych do pliku przechowuj cego dane statystyczne aplikacji
	    
	    //Toast.makeText(getApplicationContext(),Integer.toString(readedTagCnt), Toast.LENGTH_LONG).show(); 
	}
	
	private void incrementAddToShoppingCartCounter(){																	//Metoda inkrementuj ca licznik produkt w dodanych do listy zakup w
	    SharedPreferences appStat = getSharedPreferences(App.SP_APP_STAT_FILE_NAME, 0);									//Uchwyt do pliku przechowuj cego dane statystyczne aplikcji
	    Integer addToShoppingCartCnt = appStat.getInt(App.SP_ADD_TO_SL_CNT, 0);											//Pobiera bie  c  warto   licznika identyfikator w dodanych do listy zakup w
	    addToShoppingCartCnt++;																							//Zwi ksza licznik identyfikator w dodanych do listy zakup w o jeden
	    SharedPreferences.Editor editor = appStat.edit();																//Utworzenie tymczasowego obiektu modyfikuj cego bie  ce statystyki
	    editor.putInt(App.SP_ADD_TO_SL_CNT, addToShoppingCartCnt);														//Zmiana bie  cej warto ci licznika identyfikator w dodanych do listy zakup w
	    editor.commit();																								//Zapis nowych danych do pliku przechowuj cego dane statystyczne aplikacji
	}
		
	@Override
	public void onBackPressed() {																						//Naci ni cie przycisku sprz towego Back
	    if (popupWindowAppStat != null && popupWindowAppStat.isShowing()) { 											//Je eli widoczne jest okno statystyk
	    	popupWindowAppStat.dismiss();																				//Powoduje jego zamkni cie
	    } else {
	        super.onBackPressed();																						//W przeciwnym razie wykonuje zwyk   czynno   cofania
	    }
	}	
	
	private void showPopupWindowReadsStat() {																			//Pokazuje okienko statystyk												
		LayoutInflater inflater = (LayoutInflater) ReadsActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//Tworzy obiekt do dynamicznego generowania layoutu na podstawie plik w XML
		final View layout = inflater.inflate(R.layout.reads_stat_popup, (ViewGroup) findViewById(R.id.readsStatPopup));	//Tworzy uchwyt do layoutu wyskakuj cego okienka statystyk aplikacji

		final TextView textViewReadsStatAppStartupCntValue = (TextView) layout.findViewById(R.id.textViewReadsStatAppStartupCntValue);					//Tworzy uchwyt do pola tekstowego licznika uruchomie  programu
		final TextView textViewReadsStatReadedTagCntValue = (TextView) layout.findViewById(R.id.textViewReadsStatReadedTagCntValue);					//Tworzy uchwyt do pola tekstowego licznika odczytanych identyfikator w
		final TextView textViewReadsStatAddToShoppingCartCntValue = (TextView) layout.findViewById(R.id.textViewReadsStatAddToShoppingCartCntValue);	//Tworzy uchwyt do pola tekstowego licznika identyfikator w dodanych do listy zakup w
		final Button btnResetStat = (Button) layout.findViewById(R.id.btnResetStat);																	//Tworzy uchwyt do przycisku resetuj cego statystyki
				
		final SharedPreferences appStat = getSharedPreferences(App.SP_APP_STAT_FILE_NAME, 0);							//Uchwyt do pliku przechowuj cego dane statystyczne aplikcji
		textViewReadsStatAppStartupCntValue.setText(Integer.toString(appStat.getInt(App.SP_STARTUP_CNT, 0)));			//Wpisuje bie  c  warto   licznika uruchomie  programu
		textViewReadsStatReadedTagCntValue.setText(Integer.toString(appStat.getInt(App.SP_READED_TAG_CNT, 0)));			//Wpisuje bie  ca warto   licznika odczytanych identyfikator w
		textViewReadsStatAddToShoppingCartCntValue.setText(Integer.toString(appStat.getInt(App.SP_ADD_TO_SL_CNT, 0)));	//Wpisuje bie  c  warto   licznika identyfikator w dodanych do listy zakup w
		
		try{		
			popupWindowAppStat = new PopupWindow(layout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, false);	//Generuje wyskakuj ce okienko o rozmiarach dopasowanych do ekranu
			popupWindowAppStat.showAtLocation(layout, Gravity.CENTER, 0, 0);																		//I umieszcza je na  rodku ekranu
			
			btnResetStat.setOnClickListener( new View.OnClickListener() {												//Uruchamia nas uchiwanie przycisku RESET
			    public void onClick(View v) {																			
					new AlertDialog.Builder(layout.getContext())														//Tworzy alert potwierdzaj cy reset ustawie
					.setTitle(R.string.alert_dialog_reset_stat_title)
					.setMessage(R.string.alert_dialog_reset_stat_message)
				    .setPositiveButton(R.string.alert_dialog_reset_stat_yes, new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 										//W przypadku potwierdzenia resetuje statystyki
				            // continue with reset
				        	SharedPreferences.Editor editor = appStat.edit();											//Utworzenie tymczasowego obiektu modyfikuj cego bie  ce statystyki
						    editor.putInt(App.SP_STARTUP_CNT, 0);														//Resetuje bie  c  warto   licznika uruchomie  programu
						    editor.putInt(App.SP_READED_TAG_CNT, 0);													//Resetuje bie  c  warto   licznika odczytanych identyfikator w
						    editor.putInt(App.SP_ADD_TO_SL_CNT, 0);														//Resetuje bie  c  warto   licznika identyfikator w dodanych do listy zakup w
						    editor.commit();																			//Zapisuje zmiany
				        
							textViewReadsStatAppStartupCntValue.setText(Integer.toString(appStat.getInt(App.SP_STARTUP_CNT, 0)));			//Wpisuje zresetowan  warto   licznika uruchomie  programu do jego pola testowego
							textViewReadsStatReadedTagCntValue.setText(Integer.toString(appStat.getInt(App.SP_READED_TAG_CNT, 0)));			//Wpisuje zresetowan  warto   licznika odczytanych identyfikator w do jego pola testowego
							textViewReadsStatAddToShoppingCartCntValue.setText(Integer.toString(appStat.getInt(App.SP_ADD_TO_SL_CNT, 0)));	//Wpisuje zresetowan  warto   licznika identyfikator w dodanych do listy zakup w do jego pola testowego
				        }
				     })
				     .setNegativeButton(R.string.alert_dialog_reset_stat_no, new DialogInterface.OnClickListener() {	//W przypadku wybrania odpowiedzi negatywnej
				        public void onClick(DialogInterface dialog, int which) { 
				            																							//Przerywa akcje i nic nie robi
				        }
				     })
				    .setIcon(R.drawable.reset_apps_stat).show();														// aduje ikonk  z zasob w aplikacji i wy wietla alert
			    }
			});
		}catch(Exception e){																							//W przypadku gdy utworzenie wyskakuj cego okienka si  nie powiedzie to przechwytuje wyj tek
			e.printStackTrace();
		}
	}
}
