package com.ap.fmcgr;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class ShoppingCartActivity extends Activity {
	private ExpandableListView expandableListView;
	private LinearLayout linearLayoutShoppingCartTotalPrice;
	private TextView textViewShoppingCartTotalPriceValue;
	private Button btnSaveShoppingCart;
	private Button btnCollapseAll;
	private Button btnClearShoppingCart;
	private PopupWindow popupWindowShoppingCartStat;
	
	private SparseArray<ShoppingCartGroup> groups;
	private ShoppingCartExpandableListAdapter adapter;
	
	private App app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);	//Ukrywa pasek powiadomie
		setContentView(R.layout.shopping_cart_activity);
		expandableListView = (ExpandableListView) findViewById(R.id.listViewShoppingCart);
		textViewShoppingCartTotalPriceValue = (TextView) findViewById(R.id.textViewShoppingCartTotalPriceValue);
		linearLayoutShoppingCartTotalPrice = (LinearLayout) findViewById(R.id.linearLayoutShoppingCartTotalPrice);
		btnSaveShoppingCart = (Button) findViewById(R.id.btnShoppingCartSave);
		btnCollapseAll = (Button) findViewById(R.id.btnShoppingCartCollapse);
		btnClearShoppingCart = (Button) findViewById(R.id.btnShoppingCartClear);
		
		createData();
		
		btnSaveShoppingCart.setOnClickListener(mSaveShoppingCart);
		btnCollapseAll.setOnClickListener(mCollapseAll);
		btnClearShoppingCart.setOnClickListener(mClearShoppingCart);
		expandableListView.setOnItemLongClickListener(mListViewDeleteEntry);											//W aczenie nas uchiwania d ugiego przyci ni cia
	}	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shopping_cart_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		Intent myIntent;
		switch (item.getItemId()){
			case R.id.action_statistics:
				showPopupWindowShoppingCartStat();
			return true;
			case R.id.action_settings:
				myIntent = new Intent(ShoppingCartActivity.this, PreferencesActivity.class);
				startActivity(myIntent);
			return true;
			case R.id.action_about:
		    	myIntent = new Intent(ShoppingCartActivity.this, AboutActivity.class);		//Tworzy intencj , kt ra uruchamia now  aktywno
		    	startActivity(myIntent);		
			return true;
			default:
				return super.onOptionsItemSelected(item);
		} 		
	}
	
	//Generowanie danych dla listy
	public void createData(){
		app = (App)getApplication();
		adapter = null;
		groups = new SparseArray<ShoppingCartGroup>();
		
		for (int j = 0; j < app.currentShoppingCart.size(); j++) {
			ProductInfo productInfo;// = new ProductInfo();
			productInfo = app.currentShoppingCart.get(j);
			
			ShoppingCartGroup group = new ShoppingCartGroup(productInfo.getProductName());
			
			List<String[]> childList = new ArrayList<String[]>();
			childList = productInfo.getListViewProductInfo();
						
			for (int i = 0; i < childList.size(); i++) {
				//group.children.add(j + "." + i + " " + childList.get(i)[ProductInfo.headerColIndex] + ": \n" + childList.get(i)[ProductInfo.formatedValColIndex]);
				group.children.add(childList.get(i)[ProductInfo.headerColIndex] + ": \n\t\t\t" + childList.get(i)[ProductInfo.formatedValColIndex]);
			}
			groups.append(j, group);
		}
		
		showTotalPrice();
					
		adapter = new ShoppingCartExpandableListAdapter(this, groups);
		expandableListView.setAdapter(adapter);
	}	
	
	//Oblicza i wy wietla ca kowit  sum  nale no ci za zakupy
	public void showTotalPrice(){
		app = (App)getApplication();
		
		BigDecimal totalPrice = app.getShoppingCartTotalPrice();		
		if(totalPrice != null){
			linearLayoutShoppingCartTotalPrice.setVisibility(TextView.VISIBLE);							//Za  cza pole wy wietlaj ce cen
			textViewShoppingCartTotalPriceValue.setText(totalPrice.toString() + " " + app.getCurrencyInShoppingCart().getCurrencyNameCode());	//Wy wietla sumaryczn  cen  produkt w na li cie
			return;																						//Wychodzi z metody
		
		}else{
			totalPrice = new BigDecimal(0);																		//Je eli wcze niej nie zako czono metody
			linearLayoutShoppingCartTotalPrice.setVisibility(TextView.GONE);									//to ukrywane jest pole wy wietlaj ce cene
			textViewShoppingCartTotalPriceValue.setText(totalPrice.toString());									//i profilaktycznie wype niane zerem
			return;
		}
	}
		
	//Usuwanie grupy z listy poprzez jej d ugie naci ni cie
	ExpandableListView.OnItemLongClickListener mListViewDeleteEntry = new ExpandableListView.OnItemLongClickListener(){
		public boolean onItemLongClick(AdapterView<?> parent, View view, int groupPosition, long id) {	          	
			int cnt;
			final int properGroupPosition;
			
			for(cnt=0; cnt<groupPosition;cnt++){														//Dla wszystkich element w o indeksie mniejszym ni  obecna pozycja
				if(expandableListView.isGroupExpanded(cnt)) groupPosition-= adapter.getChildrenCount(cnt);		//Je eli grupa jest rozwinieta to zlicza liczebno   podgrupy i odejmuje ja od wartosci pozycji
			}
			properGroupPosition = cnt;
			groupPosition = properGroupPosition;																		//Ilosc powt rz   odpowiada faktycznemu indeksowi grupy
			
			new AlertDialog.Builder(ShoppingCartActivity.this)
			.setTitle(R.string.alert_dialog_delete_title)
			.setMessage(R.string.alert_dialog_delete_message)
		    .setPositiveButton(R.string.alert_dialog_delete_yes, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		            // continue with delete
					app = (App)getApplication();
					app.currentShoppingCart.remove(properGroupPosition);
					createData();
		        }
		     })
		     .setNegativeButton(R.string.alert_dialog_delete_no, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		            // do nothing
		        }
		     })
		    .setIcon(R.drawable.expandable_list_delete_entry).show();
	
			return true;
			

		}
	};

	//Naci niecie przycisku Save
	Button.OnClickListener mSaveShoppingCart = new Button.OnClickListener(){
		public void onClick(View v) {	

		}
	};
	
	//Naci ni cie przycisku Collapse
	Button.OnClickListener mCollapseAll = new Button.OnClickListener(){
		public void onClick(View v) {		
			for(int i=0;i<expandableListView.getCount();i++)
			expandableListView.collapseGroup(i);
		}
	};
	
	//Naci ni cie przycisku Clear
	Button.OnClickListener mClearShoppingCart = new Button.OnClickListener(){
		public void onClick(View v) {	
			new AlertDialog.Builder(ShoppingCartActivity.this)
			.setTitle(R.string.alert_dialog_clear_title)
			.setMessage(R.string.alert_dialog_clear_message)
		    .setPositiveButton(R.string.alert_dialog_clear_yes, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		            // continue with delete
					app = (App)getApplication();
					app.currentShoppingCart.clear();
					createData();
		        }
		     })
		     .setNegativeButton(R.string.alert_dialog_clear_no, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		            // do nothing
		        }
		     })
		    .setIcon(R.drawable.expandable_list_delete_entry).show();
		}
	};
	
	@Override
	public void onBackPressed() {																			//Naci ni cie przycisku sprz towego Back
	    if (popupWindowShoppingCartStat != null && popupWindowShoppingCartStat.isShowing()) { 				//Je eli widoczne jest okno statystyk
	        popupWindowShoppingCartStat.dismiss();															//Powoduje jego zamkni cie
	    } else {
	        super.onBackPressed();																			//W przeciwnym razie wykonuje zwyk   czynno   cofania
	    }
	}	

	//Pokazuje okienko statystyk
	private void showPopupWindowShoppingCartStat() {
		app = (App)getApplication();		
		if(app.currentShoppingCart.isEmpty() == false){											
			//Inicjalizacja zmiennych
			Integer statNumberOfItems = app.currentShoppingCart.size();
			BigDecimal statTotalPrice = app.getShoppingCartTotalPrice();
			BigDecimal statAvgPrice = new BigDecimal(0);
			BigDecimal statHighesPrice = new BigDecimal(0);
			BigDecimal statLowestPrice = new BigDecimal(0);
			Long statShortestShelfLife = Long.valueOf(0);	
			Integer statShortShelfLife = Integer.valueOf(0);	
			
			LayoutInflater inflater = (LayoutInflater) ShoppingCartActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View layout = inflater.inflate(R.layout.shopping_cart_stat_popup, (ViewGroup) findViewById(R.id.shoppingCartStatPopup));
	
			TextView textViewStatNumberOfItems = (TextView) layout.findViewById(R.id.textViewShoppingCartStatNrOfItemsValue);
			TextView textViewStatAvgPriceHeader = (TextView) layout.findViewById(R.id.textViewShoppingCartAveragePriceHeader);
			TextView textViewStatAvgPriceValue = (TextView) layout.findViewById(R.id.textViewShoppingCartAveragePriceValue);
			
			TextView textViewStatHighestPriceHeader = (TextView) layout.findViewById(R.id.textViewShoppingCartHighestPriceHeader);
			TextView textViewStatHighestPriceValue = (TextView) layout.findViewById(R.id.textViewShoppingCartHighestPriceValue);
			TextView textViewStatLowestPriceHeader = (TextView) layout.findViewById(R.id.textViewShoppingCartLowestPriceHeader);
			TextView textViewStatLowestPriceValue = (TextView) layout.findViewById(R.id.textViewShoppingCartLowestPriceValue);
			
			TextView textViewStatShortestShelfLifeHeader = (TextView) layout.findViewById(R.id.textViewShoppingCartStatTheShortestShelfLifeHeader);
			TextView textViewStatShortestShelfLifeValue = (TextView) layout.findViewById(R.id.textViewShoppingCartStatTheShortestShelfLifeValue);
			TextView textViewStatShortShelfLifeHeader = (TextView) layout.findViewById(R.id.textViewShoppingCartNumberOfShortShelfLifeHeader);
			TextView textViewStatShortShelfLifeValue = (TextView) layout.findViewById(R.id.textViewShoppingCartNumberOfShortShelfLifeValue);
			
			//Wyznacza i wy wietla ilo   element w na li cie
			textViewStatNumberOfItems.setText(Integer.toString(statNumberOfItems));
			
			//Wyznacza i wy wietla  redni  nale no   za jeden towar z listy
			if(statTotalPrice != null){															//Je eli ca kowita kwota zosta a wcze niej wyznaczona
				textViewStatAvgPriceHeader.setVisibility(TextView.VISIBLE);	
				textViewStatAvgPriceValue.setVisibility(TextView.VISIBLE);							//Uaktywniane jest wi c pole tekstowe
				statAvgPrice = statTotalPrice.divide(new BigDecimal(statNumberOfItems),2,RoundingMode.HALF_UP);
				textViewStatAvgPriceValue.setText(statAvgPrice.toString() + " " + app.getCurrencyInShoppingCart().getCurrencyNameCode());	//I kopiuje do niego zawarto   poprzednio wyznaczonej kwoty
			}else{
				textViewStatAvgPriceHeader.setVisibility(TextView.GONE);	
				textViewStatAvgPriceValue.setVisibility(TextView.GONE);	
			}
			
			//Wyznacza i wy wietla najwy sz  cen
			statHighesPrice = app.getShoppingCartHighestPrice();
			if(statHighesPrice != null){
				textViewStatHighestPriceHeader.setVisibility(TextView.VISIBLE);	
				textViewStatHighestPriceValue.setVisibility(TextView.VISIBLE);
				textViewStatHighestPriceValue.setText(statHighesPrice.toString() + " " + app.getCurrencyInShoppingCart().getCurrencyNameCode());	//I kopiuje do niego zawarto   poprzednio wyznaczonej kwoty
			}else{
				textViewStatHighestPriceHeader.setVisibility(TextView.GONE);	
				textViewStatHighestPriceValue.setVisibility(TextView.GONE);
			}
			
			//Wyznacza i wy wietla najni sz  cen
			statLowestPrice = app.getShoppingCartLowestPrice();
			if(statHighesPrice != null){
				textViewStatLowestPriceHeader.setVisibility(TextView.VISIBLE);	
				textViewStatLowestPriceValue.setVisibility(TextView.VISIBLE);
				textViewStatLowestPriceValue.setText(statLowestPrice.toString() + " " + app.getCurrencyInShoppingCart().getCurrencyNameCode());	//I kopiuje do niego zawarto   poprzednio wyznaczonej kwoty
			}else{
				textViewStatLowestPriceHeader.setVisibility(TextView.GONE);	
				textViewStatLowestPriceValue.setVisibility(TextView.GONE);
			}
									
			//Wyznacza i wy wietla najkr tszy okres przydatno ci do zu ycia
			statShortestShelfLife = app.getShoppingCartNrOfDaysToFirstExpirationDate();						//Wyznacza r nic  pomi dzy obecn  dat  a najwcze niejsz  dat  przydatno ci
			if(statShortestShelfLife != null){
				textViewStatShortestShelfLifeHeader.setVisibility(TextView.VISIBLE);	
				textViewStatShortestShelfLifeValue.setVisibility(TextView.VISIBLE);
				if (statShortestShelfLife == 1){
					textViewStatShortestShelfLifeValue.setText(statShortestShelfLife.toString() + " " + getApplicationContext().getResources().getString(R.string.day));
				}else{
					textViewStatShortestShelfLifeValue.setText(statShortestShelfLife.toString() + " " + getApplicationContext().getResources().getString(R.string.days));
				}
			}else{
				textViewStatShortestShelfLifeHeader.setVisibility(TextView.GONE);	
				textViewStatShortestShelfLifeValue.setVisibility(TextView.GONE);
			}
			
			//Wyznacza i wy wietla ilo   pozycji o kr tkiej dacie wa no ci
			statShortShelfLife = app.getShoppingCartNrOfShortShelfLifeItems();
			if(statShortShelfLife != null){
				textViewStatShortShelfLifeHeader.setVisibility(TextView.VISIBLE);	
				textViewStatShortShelfLifeValue.setVisibility(TextView.VISIBLE);
				textViewStatShortShelfLifeValue.setText(statShortShelfLife.toString() + " " + getApplicationContext().getResources().getString(R.string.pcs));
			}else{
				textViewStatShortShelfLifeHeader.setVisibility(TextView.GONE);	
				textViewStatShortShelfLifeValue.setVisibility(TextView.GONE);
			}
			
			try{		
				//popupWindow = new PopupWindow(layout, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, false);
				popupWindowShoppingCartStat = new PopupWindow(layout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, false);
				popupWindowShoppingCartStat.showAtLocation(layout, Gravity.CENTER, 0, 0);				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		else{
			//Komunikat o pustej li cie
			Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.shopping_cart_stat_no_items), Toast.LENGTH_LONG).show(); 
		}
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