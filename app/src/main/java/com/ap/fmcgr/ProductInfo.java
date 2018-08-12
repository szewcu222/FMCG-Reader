//Klasa interpetuj ca i przechowuj ca informacje o oznakowanym produkcie

package com.ap.fmcgr;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

public class ProductInfo {

	
	
// STA E KLASY

	public static final String _UID_STR = "UID";
	public static final String _CUR_STR = "CUR";
	public static final String _PN_STR = "PN";
	public static final String _MN_STR = "MN";
	public static final String _WA_STR = "WA";
	public static final String _AI01_STR = "AI01";
	public static final String _AI11_STR = "AI11";
	public static final String _AI15_STR = "AI15";
	public static final String _AI17_STR = "AI17";
	public static final String _AI21_STR = "AI21";
	public static final String _AI310_STR = "AI310";
	public static final String _AI311_STR = "AI311";
	public static final String _AI312_STR = "AI312";
	public static final String _AI313_STR = "AI313";
	public static final String _AI314_STR = "AI314";
	public static final String _AI315_STR = "AI315";
	public static final String _AI316_STR = "AI316";
	public static final String _AI37_STR = "AI37";
	public static final String _AI390_STR = "AI390";
	public static final String _AI391_STR = "AI391";
	public static final String _AI392_STR = "AI392";
	public static final String _AI393_STR = "AI393";
	public static final String _NI01_STR = "NI01";
	public static final String _NI02_STR = "NI02";
	public static final String _NI03_STR = "NI03";
	public static final String _NI04_STR = "NI04";
	public static final String _NI05_STR = "NI05";
	public static final String _NI06_STR = "NI06";
	public static final String _NI07_STR = "NI07";
	public static final String _NI08_STR = "NI08";
	
	public static final int keyColIndex = 0;			//Indeks kolumny klucza
	public static final int valueColIndex = 1;			//Indeks kolumny wartosci
	public static final int headerColIndex = 2;			//Indeks kolumny nagloowka
	public static final int formatedValColIndex = 3;	//Indeks kolumny sformatowanej wartosci
	public static final int visibleColIndex = 4;		//Indeks kolumny flagi widocznosci
	public static final int valueColorColIndex = 5;		//Indeks kolumny flagi widocznosci
	public static final int numberOfCol = 6;			//Ilosc   kolumn
	
	
	
	
// POLA KLASY

	HashMap<String, String> mapOfData; 					//mapa odczytanych danych (klucz, warto  )
	List<String> listOfKeys;							//lista odczytanych i aktualnie dost pnych kluczy hashmapy

	private byte[] tagUid;								//UID
	private Calendar dateOfRead;						//Data odczutu
	private ISO4217 currency;							//Waluta (dla AI391* and AI393*)
			
	private String productName;							//PN
	private String manufacturerName;					//MN
	private Uri webAddress;								//WA									
	
	private Long gtinNumber;							//AI01
	private Calendar productionDate;					//AI11
	private Calendar bestBeforeDate;					//AI15
	private Calendar expirationDate;					//AI17
	private String serialNumber;						//AI21
	private BigDecimal netWeightKg;						//AI310*
	private BigDecimal lengthM;							//AI311*
	private BigDecimal widthM;							//AI312*
	private BigDecimal heightM;							//AI313*
	private BigDecimal areaM2;							//AI314*
	private BigDecimal netVolumeL;						//AI315*
	private BigDecimal netVolumeM3;						//AI316*
	private Integer countOfTradeItems;					//AI37
	private BigDecimal amount;							//AI390* - AI391*
	private BigDecimal price;							//AI392* - AI393*
	
	private BigDecimal energy;							//NI01 - warto   energetyczna w 100 g produktu
	private BigDecimal fatTotal;						//NI02 - ca kowita zawarto   t uszczu w 100 g produktu
	private BigDecimal fatSaturated;					//NI03 - w tym zawarto   t uszcz w nasyconych w 100 g produktu
	private BigDecimal carbohydrate;					//NI04 - ca kowita zawarto   w glowodan w w 100 g produktu
	private BigDecimal sugars;							//NI05 - w tym zawarto   cukr w w 100 g produktu
	private BigDecimal protein;							//NI06 - zawarto   bia ka w 100 g produktu
	private BigDecimal sodium;							//NI07 - zawarto   soli (sodu) w 100 g produktu
	private BigDecimal roughage;						//NI08 - zawarto   b onnika w 100 g produktu
	
	List<String[]> listViewProductInfo;				//sze cio a uchowa lista odczytanych danych (klucz, warto  , nag  wek, sformatowana warto  , widoczno   i kolor)

	
	
	
// KONSTRUKTORY 
	
	public ProductInfo(){
		this.clear();
	}
	public ProductInfo(byte[] uid){
		this.clear();
		this.tagUid = uid;
    	this.mapOfData.put(_UID_STR,convertHexByteArrayToString(uid));
    	this.listOfKeys.add(_UID_STR);
	}
	public ProductInfo(byte[] uid, byte[] rawData){
		this.clear();	
		this.tagUid = uid;
    	this.mapOfData.put(_UID_STR,convertHexByteArrayToString(uid));
    	this.listOfKeys.add(_UID_STR);
		this.setMapOfDataFromRawData(rawData);
		this.fillProductInfo(this.mapOfData);
	}
	public ProductInfo(byte[] uid, HashMap <String,String> mapOfData){
		this.clear();	
		this.tagUid = uid;
    	this.mapOfData.put(_UID_STR,convertHexByteArrayToString(uid));
    	this.listOfKeys.add(_UID_STR);
		this.mapOfData = mapOfData;
		this.fillProductInfo(this.mapOfData);
	}
		

	
	
// FUNKCJE DO OCZYTU WARTO CI P L OBIEKTU
	
	public byte[] 		getUid(){
		return tagUid;
	}	
	public Calendar		getDateOfRead(){
		return dateOfRead;
	}
	public ISO4217 		getCurrency(){
		return currency;
	}
	
	public String 		getProductName() {
		return productName;
	}
	public String 		getManufacturerName() {
		return manufacturerName;
	}
	public Uri 			getWebAddress(){
		return webAddress;
	}
	public Long 		getGtinNumber() {
		return gtinNumber;
	}
	public Calendar 	getProductionDate() {
		return productionDate;
	}	
	public Calendar 	getBestBeforeDate() {
		return bestBeforeDate;
	}	
	public Calendar 	getExpirationDate() {
		return expirationDate;
	}
	public String 		getSerialNumber() {
		return serialNumber;
	}
	public BigDecimal 	getNetWeightKg() {
		return netWeightKg;
	}
	public BigDecimal 	getLengthM() {
		return lengthM;
	}
	public BigDecimal 	getWidthM() {
		return widthM;
	}	
	public BigDecimal 	getHeightM() {
		return heightM;
	}
	public BigDecimal 	getAreaM2() {
		return areaM2;
	}
	public BigDecimal	getNetVolumeL() {
		return netVolumeL;
	}
	public BigDecimal 	getNetVolumeM3() {
		return netVolumeM3;
	}
	public Integer 		getCountOfTradeItems() {
		return countOfTradeItems;
	}
	public BigDecimal 	getAmount() {
		return amount;
	}
	public BigDecimal 	getPrice() {
		return price;
	}
	public BigDecimal 	getEnergy(){
		return energy;
	}
	public BigDecimal 	getFatTotal(){
		return fatTotal;
	}
	public BigDecimal 	getFatSaturated(){
		return fatSaturated;
	}
	public BigDecimal 	getCarbohydrate(){
		return carbohydrate;
	}
	public BigDecimal 	getSugars(){
		return sugars;
	}
	public BigDecimal 	getProtein(){
		return protein;
	}
	public BigDecimal 	getSodium(){
		return sodium;
	}
	public BigDecimal 	getRoughage(){
		return roughage;
	}
		
	
	
	
// FUNKCJE OBS UGI HASH MAPY DANYCH O PRODUKCIE ZGODNIE ZE SPECYFIKACJ  GS1
// (Jedyny spos b wprowadzania danych do obiektu to podanie hash mapy a nast pnie wype enie ni  obiektu  za pomoc  metody fillProductInfo()
	
	/***********************************************************************/
	/* Funkcja zwraca list  rozpoznanych kluczy
	 */	
	/***********************************************************************/
	public List<String> getListOfKeys(){
		return listOfKeys;
	}
		
	/***********************************************************************/
	/* Funkcja zwraca hash map  obiektu
	 */	
	/***********************************************************************/
	public HashMap<String,String> getMapOfData(){
		return mapOfData;
	}
	
//	/***********************************************************************/
//	/* Funkcja kopiuje now  hash map  do hash mapy obiektu
//	 */
//	/***********************************************************************/
//	public void setMapOfData(HashMap <String,String> map){
//		this.mapOfData = map;
//	}
	
	/***********************************************************************/
	/* Funkcja dzieli otrzymany ci g bajt w na map  klucz, warto
	 * i wype nia nimi hash map  objektu
	 */
	/***********************************************************************/
	public boolean setMapOfDataFromRawData(byte[] rawData){
		boolean result = false;

		String dataStr = new String(rawData);
		dataStr = dataStr.trim();
		
		StringTokenizer strTokenizer = new StringTokenizer(dataStr, ","+"\r\n");	
		//this.readedData = Integer.toString(stringTokenizer.countTokens());
		
		if(strTokenizer.countTokens()>1){														//Je eli  a cuch rozdziela przynajmniej jeden separator
			while(strTokenizer.hasMoreTokens()){												//Powtarza odczyt  a cucha a  wszystkie klucze zostan  zidentyfikowane
			    try{
					String key = strTokenizer.nextToken();										//Odczytuje klucz
				    String value = strTokenizer.nextToken();									//Odczytuje warto
				    key = key.replaceAll("[^A-Za-z0-9 ]", "");									//Usuwa znaki nie alfanumeryczne
				    key = key.trim();															//Usuwa puste znaki na pocz tku i na ko cu  a cucha
				    value = value.replaceAll("[^ -~]", "");										//Usuwa znaki nie b d ce typowym kodem ASCII
				    value = value.trim();														//Usuwa puste znaki na pocz tki i na ko cu  a cucha
				    
				    if(key.equalsIgnoreCase(_PN_STR)){											//Nazwa towaru	   
				    	this.mapOfData.put(_PN_STR, value);
				    	result = true;			
				    }
				    else if(key.equalsIgnoreCase(_MN_STR)){										//Nazwa producenta
				    	this.mapOfData.put(_MN_STR, value);
				    	result = true;				    
				    }
				    else if(key.equalsIgnoreCase(_WA_STR)){
				    	this.mapOfData.put(_WA_STR, value);
				    	result = true;
				    }
				    else if(key.equalsIgnoreCase(_AI01_STR)){									//Numer GTIN-13
				    	if(value.length()==13 || value.length()==14){							//Wg. specyfikacji GS1 GTIN-13 jako GTIN-14 ale funkcja akceptuje r wnie  kody 13 znakowe
					    	this.mapOfData.put(_AI01_STR, value);
					    	result = true;
				    	}
				    }
				    else if(key.equalsIgnoreCase(_AI11_STR)){									//Data produkcji
				    	if(value.length()==6){													//Wg specyfikacji GS1  a cuch powinien sk ada  si  z 6 cyfr
					    	this.mapOfData.put(_AI11_STR, value);
					    	result = true;
				    	}
				    }
				    else if(key.equalsIgnoreCase(_AI15_STR)){									//Najlepiej spo y  przed
				    	if(value.length()==6){	
				    		this.mapOfData.put(_AI15_STR, value);
				    		result = true;
				    	}
				    }
				    else if(key.equalsIgnoreCase(_AI17_STR)){									//Data wa no ci
				    	if(value.length()==6){
				    		this.mapOfData.put(_AI17_STR, value);
				    		result = true;
				    	}
				    }
				    else if(key.equalsIgnoreCase(_AI21_STR)){									//Numer seryjny
				    	if(value.length()>0 && value.length()<=20){
				    		this.mapOfData.put(_AI21_STR, value);
				    		result = true;
				    	}
				    }			   
				    else if(key.startsWith(_AI310_STR)){										//Waga w kg
				    	if(key.length()>5){														//Sprawdzanie d ugo ci klucza (6 znak wyznacza miejsce dziesi tne)
					    	if(value.length()>0 && value.length()<=6){							//Je eli d ugo    a cucha warto ci mie ci si  w za o eniach podanych w specyfikacji GS1
					    		value = moveDecimalPoint(key,value);
					    		if(value != null){
						    		this.mapOfData.put(_AI310_STR, value);
						    		result = true;
					    		}
					    	}
				    	}
				    }
				    //else if(key.substring(0, 5).equalsIgnoreCase(_AI311_STR)){				//D ugo   w m
				    else if(key.startsWith(_AI311_STR)){										//D ugo   w m
				    	if(key.length()>5){														//Sprawdzanie d ugo ci klucza (6 znak wyznacza miejsce dziesi tne)
					    	if(value.length()>0 && value.length()<=6){							//Je eli d ugo    a cucha warto ci mie ci si  w za o eniach podanych w specyfikacji GS1
					    		value = moveDecimalPoint(key,value);
					    		if(value != null){
						    		this.mapOfData.put(_AI311_STR, value);
						    		result = true;
					    		}
					    	}
				    	}
				    }
				    //else if(key.substring(0, 5).equalsIgnoreCase(_AI312_STR)){				//Szeroko   w m
				    else if(key.startsWith(_AI312_STR)){
				    	if(key.length()>5){														//Sprawdzanie d ugo ci klucza (6 znak wyznacza miejsce dziesi tne)
					    	if(value.length()>0 && value.length()<=6){							//Je eli d ugo    a cucha warto ci mie ci si  w za o eniach podanych w specyfikacji GS1
					    		value = moveDecimalPoint(key,value);
					    		if(value != null){
						    		this.mapOfData.put(_AI312_STR, value);
						    		result = true;
					    		}
					    	}
				    	}
				    }
				    //else if(key.substring(0, 5).equalsIgnoreCase(_AI313_STR)){				//Wysoko  , g  boko   w m
				    else if(key.startsWith(_AI313_STR)){
				    	if(key.length()>5){														//Sprawdzanie d ugo ci klucza (6 znak wyznacza miejsce dziesi tne)
					    	if(value.length()>0 && value.length()<=6){							//Je eli d ugo    a cucha warto ci mie ci si  w za o eniach podanych w specyfikacji GS1
					    		value = moveDecimalPoint(key,value);
					    		if(value != null){
						    		this.mapOfData.put(_AI313_STR, value);
						    		result = true;
					    		}
					    	}
				    	}
				    }
				    //else if(key.substring(0, 5).equalsIgnoreCase(_AI314_STR)){				//Powierzchnia w m2
				    else if(key.startsWith(_AI314_STR)){
				    	if(key.length()>5){														//Sprawdzanie d ugo ci klucza (6 znak wyznacza miejsce dziesi tne)
					    	if(value.length()>0 && value.length()<=6){							//Je eli d ugo    a cucha warto ci mie ci si  w za o eniach podanych w specyfikacji GS1
					    		value = moveDecimalPoint(key,value);
					    		if(value != null){
						    		this.mapOfData.put(_AI314_STR, value);
						    		result = true;
					    		}
					    	}
				    	}
				    }
				    //else if(key.substring(0, 5).equalsIgnoreCase(_AI315_STR)){				//Obj to   w litrach
				    else if(key.startsWith(_AI315_STR)){
				    	if(key.length()>5){														//Sprawdzanie d ugo ci klucza (6 znak wyznacza miejsce dziesi tne)
					    	if(value.length()>0 && value.length()<=6){							//Je eli d ugo    a cucha warto ci mie ci si  w za o eniach podanych w specyfikacji GS1
					    		value = moveDecimalPoint(key,value);
					    		if(value != null){
						    		this.mapOfData.put(_AI315_STR, value);
						    		result = true;
					    		}
					    	}
				    	}
				    }
				    //else if(key.substring(0, 5).equalsIgnoreCase(_AI316_STR)){				//Obj to   w metrach^3
				    else if(key.startsWith(_AI316_STR)){	
				    	if(key.length()>5){														//Sprawdzanie d ugo ci klucza (6 znak wyznacza miejsce dziesi tne)
					    	if(value.length()>0 && value.length()<=6){							//Je eli d ugo    a cucha warto ci mie ci si  w za o eniach podanych w specyfikacji GS1
					    		value = moveDecimalPoint(key,value);
					    		if(value != null){
						    		this.mapOfData.put(_AI316_STR, value);
						    		result = true;
					    		}
					    	}
				    	}
				    }
				    else if(key.equalsIgnoreCase(_AI37_STR)){									//Liczba sztuk w na palecie
				    	if(value.length()>0 && value.length()<=8){								//Wg. specyfikacji GS1 warto   ta zawiera si  w przedziale od 1 do 8 znak w
				    		this.mapOfData.put(_AI37_STR, value);
				    		result = true;
				    	}
				    }   
				    //else if(key.substring(0, 5).equalsIgnoreCase(_AI390_STR)){				//Cena w domy lnej walucie
				    else if(key.startsWith(_AI390_STR)){										//Cena w domy lnej walucie
				    	if(key.length()>5){														//Sprawdzanie d ugo ci klucza (6 znak wyznacza miejsce dziesi tne)
					    	if(value.length()>0 && value.length()<=15){							//Je eli d ugo    a cucha warto ci mie ci si  w za o eniach podanych w specyfikacji GS1
					    		value = moveDecimalPoint(key,value);
					    		if(value != null){
						    		this.mapOfData.put(_AI390_STR, value);
						    		result = true;
					    		}
					    	}
				    	}
				    }
				    //else if(key.substring(0, 5).equalsIgnoreCase(_AI391_STR)){				//Cena w podanej walucie
				    else if(key.startsWith(_AI391_STR)){										//Cena w podanej walucie
				    	if(key.length()>5){														//Sprawdzanie d ugo ci klucza (6 znak wyznacza miejsce dziesi tne)
					    	if(value.length()>0 && value.length()<=18){							//Je eli d ugo    a cucha warto ci mie ci si  w za o eniach podanych w specyfikacji GS1
					    		value = moveDecimalPoint(key,value);
					    		if(value != null){					    		
						    		this.mapOfData.put(_CUR_STR, value.substring(0, 3));
						    		this.mapOfData.put(_AI391_STR, value.substring(3));
						    		result = true;
					    		}
					    	}
				    	}
				    }
				    //else if(key.substring(0, 5).equalsIgnoreCase(_AI392_STR)){				//Cena w domy lnej walucie
				    else if(key.startsWith(_AI392_STR)){										//Cena w domy lnej walucie
				    	if(key.length()>5){														//Sprawdzanie d ugo ci klucza (6 znak wyznacza miejsce dziesi tne)
					    	if(value.length()>0 && value.length()<=15){							//Je eli d ugo    a cucha warto ci mie ci si  w za o eniach podanych w specyfikacji GS1
					    		value = moveDecimalPoint(key,value);
					    		if(value != null){
						    		this.mapOfData.put(_AI392_STR, value);
						    		result = true;
					    		}
					    	}
				    	}
				    }
				    //else if(key.substring(0, 5).equalsIgnoreCase(_AI393_STR)){				//Cena w podanej walucie
				    else if(key.startsWith(_AI393_STR)){										//Cena w podanej walucie
				    	if(key.length()>5){														//Sprawdzanie d ugo ci klucza (6 znak wyznacza miejsce dziesi tne)
					    	if(value.length()>0 && value.length()<=18){							//Je eli d ugo    a cucha warto ci mie ci si  w za o eniach podanych w specyfikacji GS1
					    		value = moveDecimalPoint(key,value);
					    		if(value != null){					    		
						    		this.mapOfData.put(_CUR_STR, value.substring(0, 3));
						    		this.mapOfData.put(_AI393_STR, value.substring(3));
						    		result = true;
					    		}
					    	}
				    	}
				    }
				    else if(key.equalsIgnoreCase(_NI01_STR)){									//Kalorie w 100g
				    	if(value.length()>0){
				    		this.mapOfData.put(_NI01_STR, value);
				    		result = true;
				    	}
				    }
				    else if(key.equalsIgnoreCase(_NI02_STR)){									//Wszystkie t uszcze w 100g
				    	if(value.length()>0){
				    		this.mapOfData.put(_NI02_STR, value);
				    		result = true;
				    	}
				    }
				    else if(key.equalsIgnoreCase(_NI03_STR)){									//T uszcze nasycone w 100g
				    	if(value.length()>0){
				    		this.mapOfData.put(_NI03_STR, value);
				    		result = true;
				    	}
				    }
				    else if(key.equalsIgnoreCase(_NI04_STR)){									//W glowodany w 100g
				    	if(value.length()>0){
				    		this.mapOfData.put(_NI04_STR, value);
				    		result = true;
				    	}
				    }
				    else if(key.equalsIgnoreCase(_NI05_STR)){									//Cukry w 100g
				    	if(value.length()>0){
				    		this.mapOfData.put(_NI05_STR, value);
				    		result = true;
				    	}
				    }
				    else if(key.equalsIgnoreCase(_NI06_STR)){									//Bia ko w 100g
				    	if(value.length()>0){
				    		this.mapOfData.put(_NI06_STR, value);
				    		result = true;
				    	}
				    }
				    else if(key.equalsIgnoreCase(_NI07_STR)){									//S l w 100g
				    	if(value.length()>0){
				    		this.mapOfData.put(_NI07_STR, value);
				    		result = true;
				    	}
				    }
				    else if(key.equalsIgnoreCase(_NI08_STR)){									//B onnik w 100g
				    	if(value.length()>0){
				    		this.mapOfData.put(_NI08_STR, value);
				    		result = true;
				    	}
				    }
			    }catch(NoSuchElementException e){
					Log.d("No Token - NoSuchElementException, Result:", Boolean.toString(result));
					Log.i("Exception","NoSuchElementException " + e.getMessage());
			    	return result;
			    }
			}
		}	
		return result;
	}
	
	
	
	
// FUNKCJE OBS UGI HASH MAPY INFORMACJI O PRODUKCIE ZGODNIE ZE SPECYFIKACJ  GS1

	/***********************************************************************/
	/* Funkcja wype nia pola klasy na podstawie utworzonej mapy klucz, warto
	 */
	/***********************************************************************/
	public boolean fillProductInfo(HashMap <String,String> map){
		boolean result = false;
		
		this.mapOfData = map;
		this.listOfKeys.clear();
		
		if(map.containsKey(_UID_STR)){
			this.tagUid = convertHexStringToByteArray(map.get(_UID_STR).replaceAll("\\s",""));
			this.listOfKeys.add(_UID_STR);
			result = true;
		}
		if(map.containsKey(_PN_STR)){
	    	this.productName = map.get(_PN_STR);
	    	this.listOfKeys.add(_PN_STR);
	    	result = true;
		}
		if(map.containsKey(_MN_STR)){
	    	this.manufacturerName = map.get(_MN_STR);
	    	this.listOfKeys.add(_MN_STR);
	    	result = true;
		}
		if(map.containsKey(_WA_STR)){
		    try{
		    	this.webAddress = Uri.parse(map.get(_WA_STR));
		    	if(this.webAddress.getPath()!=null){
			    	this.listOfKeys.add(_WA_STR);
		    		result = true;				 
		    	}	    						    	
			}catch(NullPointerException e){
				Log.d("NullPointerException of webAddress", map.get(_WA_STR));
				Log.i("Exception","Null Pointer Exception " + e.getMessage());
			}
		}
		if(map.containsKey(_AI01_STR)){
    		try{																		//Pr buje odczyta  numer GTIN-13 z  a cucha znak w
	    		this.gtinNumber = Long.parseLong(map.get(_AI01_STR),10);				
	    		this.listOfKeys.add(_AI01_STR);
	    		result = true;
	    	}catch(NumberFormatException e){
				Log.d("Number Format Exception of gtinNumber", _AI01_STR);
				Log.i("Exception","Number Format Exception " + e.getMessage());
		    }
		}
		if(map.containsKey(_AI11_STR)){
			Integer year=0, month=0, day=0;
		    try{																		//Probuje odczytac date z  a cucha znak w
		    	year = Integer.parseInt("20"+map.get(_AI11_STR).substring(0, 2));
		    	month = Integer.parseInt(map.get(_AI11_STR).substring(2, 4))-1;			//Odejmuje jeden gdy  stycze  symbolizowany jest przez warto   0 a grudzie  przez 11
		    	day = Integer.parseInt(map.get(_AI11_STR).substring(4, 6));
		    	this.productionDate = new GregorianCalendar(year, month, day);
		    	this.listOfKeys.add(_AI11_STR);
		    	result = true; 
	    	}
		    catch(NumberFormatException e){
				Log.d("Number Format Exception of date", map.get(_AI11_STR));
				Log.i("Exception","Number Format Exception " + e.getMessage());
		    }
		}
		if(map.containsKey(_AI15_STR)){
	    	Integer year, month, day;
		    try{																		//Probuje odczytac date z  a cucha znak w
		    	year = Integer.parseInt("20"+map.get(_AI15_STR).substring(0, 2));
		    	month = Integer.parseInt(map.get(_AI15_STR).substring(2, 4))-1;			//Odejmuje jeden gdy  stycze  symbolizowany jest przez warto   0 a grudzie  przez 11
		    	day = Integer.parseInt(map.get(_AI15_STR).substring(4, 6));
		    	this.bestBeforeDate = new GregorianCalendar(year, month, day);
		    	this.listOfKeys.add(_AI15_STR);
		    	result = true;
	    	}
		    catch(NumberFormatException e){
				Log.d("Number Format Exception of date", map.get(_AI15_STR));
				Log.i("Exception","Number Format Exception " + e.getMessage());
		    }
		}
		if(map.containsKey(_AI17_STR)){
	    	int year, month, day;
		    try{															//Probuje odczytac date z  a cucha znak w
		    	year = Integer.parseInt("20"+map.get(_AI17_STR).substring(0, 2));
		    	month = Integer.parseInt(map.get(_AI17_STR).substring(2, 4))-1;			//Odejmuje jeden gdy  stycze  symbolizowany jest przez warto   0 a grudzie  przez 11
		    	day = Integer.parseInt(map.get(_AI17_STR).substring(4, 6));
		    	this.expirationDate = new GregorianCalendar(year, month, day);
		    	this.listOfKeys.add(_AI17_STR);
		    	result = true;
	    	}
		    catch(NumberFormatException e){
				Log.d("Number Format Exception of date", map.get(_AI17_STR));
				Log.i("Exception","Number Format Exception " + e.getMessage());
		    }
		}
		if(map.containsKey(_AI21_STR)){
    		this.serialNumber = map.get(_AI21_STR);
    		this.listOfKeys.add(_AI21_STR);
	    	result = true;
		}
		if(map.containsKey(_AI310_STR)){	
			try{
				this.netWeightKg = new BigDecimal(map.get(_AI310_STR));
				this.listOfKeys.add(_AI310_STR);
				result = true;
	    	}
	    	catch (NumberFormatException e){
				Log.d("Number Format Exception:", map.get(_AI310_STR));
				Log.i("Exception","Number Format Exception " + e.getMessage());
	    	}
		}
		if(map.containsKey(_AI311_STR)){	
			try{
				this.lengthM = new BigDecimal(map.get(_AI311_STR));
				this.listOfKeys.add(_AI311_STR);
				result = true;
	    	}
	    	catch (NumberFormatException e){
				Log.d("Number Format Exception:", map.get(_AI311_STR));
				Log.i("Exception","Number Format Exception " + e.getMessage());
	    	}
		}
		if(map.containsKey(_AI312_STR)){	
			try{
				this.widthM = new BigDecimal(map.get(_AI312_STR));	
				this.listOfKeys.add(_AI312_STR);
				result = true;
	    	}
	    	catch (NumberFormatException e){
				Log.d("Number Format Exception:", map.get(_AI312_STR));
				Log.i("Exception","Number Format Exception " + e.getMessage());
	    	}
		}
		if(map.containsKey(_AI313_STR)){	
			try{
				this.heightM = new BigDecimal(map.get(_AI313_STR));
				this.listOfKeys.add(_AI313_STR);
				result = true;
	    	}
	    	catch (NumberFormatException e){
				Log.d("Number Format Exception:", map.get(_AI313_STR));
				Log.i("Exception","Number Format Exception " + e.getMessage());
	    	}
		}
		if(map.containsKey(_AI314_STR)){	
			try{
				this.areaM2 = new BigDecimal(map.get(_AI314_STR));
				this.listOfKeys.add(_AI314_STR);
				result = true;
	    	}
	    	catch (NumberFormatException e){
				Log.d("Number Format Exception:", map.get(_AI314_STR));
				Log.i("Exception","Number Format Exception " + e.getMessage());
	    	}
		}
		if(map.containsKey(_AI315_STR)){	
			try{
				this.netVolumeL = new BigDecimal(map.get(_AI315_STR));	
				this.listOfKeys.add(_AI315_STR);
				result = true;
	    	}
	    	catch (NumberFormatException e){
				Log.d("Number Format Exception:", map.get(_AI315_STR));
				Log.i("Exception","Number Format Exception " + e.getMessage());
	    	}
		}
		if(map.containsKey(_AI316_STR)){	
			try{
				this.netVolumeM3 = new BigDecimal(map.get(_AI316_STR));
				this.listOfKeys.add(_AI316_STR);
				result = true;
	    	}
	    	catch (NumberFormatException e){
				Log.d("Number Format Exception:", map.get(_AI316_STR));
				Log.i("Exception","Number Format Exception " + e.getMessage());
	    	}
		}
		if(map.containsKey(_AI37_STR)){
		    try{															//Probuje odczytac date z  a cucha znak w
				this.countOfTradeItems = Integer.parseInt(map.get(_AI37_STR),10);	
				this.listOfKeys.add(_AI37_STR);
		    	result = true;
	    	}
		    catch(NumberFormatException e){
				Log.d("Number Format Exception of date", map.get(_AI37_STR));
				Log.i("Exception","Number Format Exception " + e.getMessage());
		    }
		}				
		if(map.containsKey(_AI390_STR)){	
			try{
				this.amount = new BigDecimal(map.get(_AI390_STR)).setScale(2, BigDecimal.ROUND_HALF_UP);	
				this.listOfKeys.add(_AI390_STR);
				result = true;
	    	}
	    	catch (NumberFormatException e){
				Log.d("Number Format Exception:", map.get(_AI390_STR));
				Log.i("Exception","Number Format Exception " + e.getMessage());
	    	}
		}	
		if(map.containsKey(_AI391_STR)){	
			if(map.containsKey(_CUR_STR)){
				this.currency.setCurrencyByNumericCode(map.get(_CUR_STR));
			}
			try{				
				this.amount = new BigDecimal(map.get(_AI391_STR)).setScale(2, BigDecimal.ROUND_HALF_UP);;	
				this.listOfKeys.add(_AI391_STR);
				result = true;
	    	}
	    	catch (NumberFormatException e){
				Log.d("Number Format Exception:", map.get(_AI391_STR));
				Log.i("Exception","Number Format Exception " + e.getMessage());
	    	}
		}	
		if(map.containsKey(_AI392_STR)){	
			try{
				this.price = new BigDecimal(map.get(_AI392_STR)).setScale(2, BigDecimal.ROUND_HALF_UP);;	
				this.listOfKeys.add(_AI392_STR);
				result = true;
	    	}
	    	catch (NumberFormatException e){
				Log.d("Number Format Exception:", map.get(_AI392_STR));
				Log.i("Exception","Number Format Exception " + e.getMessage());
	    	}
		}	
		if(map.containsKey(_AI393_STR)){	
			if(map.containsKey(_CUR_STR)){
				this.currency.setCurrencyByNumericCode(map.get(_CUR_STR));
			}
			try{
				
				this.price = new BigDecimal(map.get(_AI393_STR)).setScale(2, BigDecimal.ROUND_HALF_UP);;	
				this.listOfKeys.add(_AI393_STR);
				result = true;
	    	}
	    	catch (NumberFormatException e){
				Log.d("Number Format Exception:", map.get(_AI393_STR));
				Log.i("Exception","Number Format Exception " + e.getMessage());
	    	}
		}	
		if(map.containsKey(_NI01_STR)){	
	    	try{
				this.energy = new BigDecimal(map.get(_NI01_STR));			    			//Zamienia  a cuch na warto
	    		this.energy = this.energy.movePointLeft(0); 							//Przesuwanie przecinka
	    		this.listOfKeys.add(_NI01_STR);
	    		result = true;
	    	}
	    	catch (NumberFormatException e){
				Log.d("Number Format Exception Value:", map.get(_NI01_STR));
				Log.i("Exception","Number Format Exception " + e.getMessage());
	    	}
		}
		if(map.containsKey(_NI02_STR)){	
	    	try{
				this.fatTotal = new BigDecimal(map.get(_NI02_STR));			    			//Zamienia  a cuch na warto
	    		this.fatTotal = this.fatTotal.movePointLeft(1); 							//Przesuwanie przecinka
	    		this.listOfKeys.add(_NI02_STR);
	    		result = true;
	    	}
	    	catch (NumberFormatException e){
				Log.d("Number Format Exception Value:", map.get(_NI02_STR));
				Log.i("Exception","Number Format Exception " + e.getMessage());
	    	}
		}
		if(map.containsKey(_NI03_STR)){	
	    	try{
				this.fatSaturated = new BigDecimal(map.get(_NI03_STR));			    			//Zamienia  a cuch na warto
	    		this.fatSaturated = this.fatSaturated.movePointLeft(1); 							//Przesuwanie przecinka
	    		this.listOfKeys.add(_NI03_STR);
	    		result = true;
	    	}
	    	catch (NumberFormatException e){
				Log.d("Number Format Exception Value:", map.get(_NI03_STR));
				Log.i("Exception","Number Format Exception " + e.getMessage());
	    	}
		}
		if(map.containsKey(_NI04_STR)){	
	    	try{
				this.carbohydrate = new BigDecimal(map.get(_NI04_STR));			    			//Zamienia  a cuch na warto
	    		this.carbohydrate = this.carbohydrate.movePointLeft(1); 							//Przesuwanie przecinka
	    		this.listOfKeys.add(_NI04_STR);
	    		result = true;
	    	}
	    	catch (NumberFormatException e){
				Log.d("Number Format Exception Value:", map.get(_NI04_STR));
				Log.i("Exception","Number Format Exception " + e.getMessage());
	    	}
		}
		if(map.containsKey(_NI05_STR)){	
	    	try{
				this.sugars = new BigDecimal(map.get(_NI05_STR));			    			//Zamienia  a cuch na warto
	    		this.sugars = this.sugars.movePointLeft(1); 							//Przesuwanie przecinka
	    		this.listOfKeys.add(_NI05_STR);
	    		result = true;
	    	}
	    	catch (NumberFormatException e){
				Log.d("Number Format Exception Value:", map.get(_NI05_STR));
				Log.i("Exception","Number Format Exception " + e.getMessage());
	    	}
		}
		if(map.containsKey(_NI06_STR)){	
	    	try{
				this.protein = new BigDecimal(map.get(_NI06_STR));			    			//Zamienia  a cuch na warto
	    		this.protein = this.protein.movePointLeft(1); 							//Przesuwanie przecinka
	    		this.listOfKeys.add(_NI06_STR);
	    		result = true;
	    	}
	    	catch (NumberFormatException e){
				Log.d("Number Format Exception Value:", map.get(_NI06_STR));
				Log.i("Exception","Number Format Exception " + e.getMessage());
	    	}
		}
		if(map.containsKey(_NI07_STR)){	
	    	try{
				this.sodium = new BigDecimal(map.get(_NI07_STR));			    			//Zamienia  a cuch na warto
	    		this.sodium = this.sodium.movePointLeft(1); 							//Przesuwanie przecinka
	    		this.listOfKeys.add(_NI07_STR);
	    		result = true;
	    	}
	    	catch (NumberFormatException e){
				Log.d("Number Format Exception Value:", map.get(_NI07_STR));
				Log.i("Exception","Number Format Exception " + e.getMessage());
	    	}
		}
		if(map.containsKey(_NI08_STR)){	
	    	try{
				this.roughage = new BigDecimal(map.get(_NI08_STR));			    			//Zamienia  a cuch na warto
	    		this.roughage = this.roughage.movePointLeft(1); 							//Przesuwanie przecinka
	    		this.listOfKeys.add(_NI08_STR);
	    		result = true;
	    	}
	    	catch (NumberFormatException e){
				Log.d("Number Format Exception Value:", map.get(_NI08_STR));
				Log.i("Exception","Number Format Exception " + e.getMessage());
	    	}
		}		
		return result;
	}	
	
	
	
	
// FUNKCJE POMOCNICZE
	
	/***********************************************************************/
	/* Funkcja czyszcz ca pola klasy a tym samym usuwaj ca informacje
	 * bie acym o produkcie
	 */
	/***********************************************************************/
	public void clear(){
		this.tagUid = null;
		this.dateOfRead = new GregorianCalendar();		//(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, Calendar.HOUR_OF_DAY, Calendar.MINUTE);
		this.currency = new ISO4217("000");				//Warto   000 zwraca domy ln  walut
		
		this.mapOfData = new HashMap<String,String>(); 
		this.listOfKeys = new ArrayList<String>();
		this.listViewProductInfo = new ArrayList<String[]>();
		
		this.productName = null;
		this.manufacturerName = null;
		this.webAddress = null;
		this.gtinNumber = null;
		this.productionDate = null;
		this.bestBeforeDate = null;
		this.expirationDate = null;
		this.serialNumber = null;
		this.netWeightKg = null;
		this.lengthM = null;
		this.widthM = null;
		this.heightM = null;
		this.areaM2 = null;
		this.netVolumeL  = null;
		this.netVolumeM3 = null;
		this.countOfTradeItems = null;
		this.amount = null;
		this.price = null;
		
		this.energy = null;
		this.fatTotal = null;
		this.fatSaturated = null;
		this.carbohydrate = null;
		this.sugars = null;
		this.protein = null;
		this.sodium = null;
		this.roughage = null;
	}
	
	/***********************************************************************/
	/* Funkcja oblicza kwot  nale n  za produkt na podstawie znormalizowanej
	 * ceny i dost pnych p l wielko ci fizycznych
	 */
	/***********************************************************************/
	public BigDecimal getAmountFromPrice(){
		if(this.getNetWeightKg() != null){
			try{
				return this.getPrice().multiply(this.getNetWeightKg());
			}catch(NullPointerException e){					
			}
		}else if(this.getLengthM() != null){
			try{
				return this.getPrice().multiply(this.getLengthM());
			}catch(NullPointerException e){				
			}
		}else if(this.getWidthM() != null){
			try{
				return this.getPrice().multiply(this.getWidthM());
			}catch(NullPointerException e){						
			}
		}else if(this.getHeightM() != null){
			try{
				return this.getPrice().multiply(this.getHeightM());
			}catch(NullPointerException e){							
			}
		}else if(this.getAreaM2() != null){
			try{
				return this.getPrice().multiply(this.getAreaM2());
			}catch(NullPointerException e){						
			}
		}else if(this.getNetVolumeL() != null){
			try{
				return this.getPrice().multiply(this.getNetVolumeL());
			}catch(NullPointerException e){	
			}
		}else if(this.getNetVolumeM3() != null){
			try{
				return this.getPrice().multiply(this.getNetVolumeM3());
			}catch(NullPointerException e){							
			}
		}
		return null;
	}	
	
	/***********************************************************************/
	/* Funkcja dodaje do  a cha warto ci przecinek dziesi tny we wskazanym
	 * w kluczu miejscu.
	 */
	/***********************************************************************/
	private String moveDecimalPoint(String key, String value){
		BigDecimal bigDecimal = null;
		String resultStr = null;
		try{
    		int position = Integer.parseInt(key.substring(5, 6));						//Odczyt ilo   miejsc do przesuniecia przecinka
    		bigDecimal = new BigDecimal(value);			    									
    		bigDecimal = bigDecimal.movePointLeft(position); 							//Przesuwanie przecinka
    		resultStr = bigDecimal.toString();

    	}
    	catch (NumberFormatException e){
    		Log.d("Number Format Exception Key:", key);
			Log.d("Number Format Exception Value:", value);
			Log.i("Exception","Number Format Exception " + e.getMessage());
    	}
		return resultStr;
	}
	
	/***********************************************************************/
	/* Funkcja dodaje do  a cha warto ci przecinek dziesi tny we wskazanym
	 * miejscu.
	 */
	/***********************************************************************/
	@SuppressWarnings("unused")
	private String moveDecimalPoint(int shift, String value){
		BigDecimal bigDecimal = null;
		String resultStr = null;
		try{
			bigDecimal = new BigDecimal(value);			    									
    		bigDecimal = bigDecimal.movePointLeft(shift);	 				//Przesuwanie przecinka
    		resultStr = bigDecimal.toString();

    	}
    	catch (NumberFormatException e){
			Log.d("Number Format Exception Value:", value);
			Log.i("Exception","Number Format Exception " + e.getMessage());
    	}
		return resultStr;
	}
	

		
	
// FUNKCJE GENERUJ CE KOLEJNE KOLUMNY DANYCH WPISYWANYCH LISTY
		
	/***********************************************************************/
	/* Funkcja na podstawie klucza zwraca  a cuch znak w zawieraj cy nag  wek
	 * dla wy wietlanych danych
	 */
	/***********************************************************************/
	private String getListViewRowHeaderString(String key, Context context){	
		if		(key.equalsIgnoreCase(_PN_STR)) 		return context.getResources().getString(R.string.PN);
		else if	(key.equalsIgnoreCase(_MN_STR))			return context.getResources().getString(R.string.MN);
		else if	(key.equalsIgnoreCase(_WA_STR))			return context.getResources().getString(R.string.WA);
		else if	(key.equalsIgnoreCase(_AI01_STR)) 		return context.getResources().getString(R.string.AI01);
		else if	(key.equalsIgnoreCase(_AI11_STR)) 		return context.getResources().getString(R.string.AI11);
		else if	(key.equalsIgnoreCase(_AI15_STR)) 		return context.getResources().getString(R.string.AI15);
		else if	(key.equalsIgnoreCase(_AI17_STR)) 		return context.getResources().getString(R.string.AI17);
		else if	(key.equalsIgnoreCase(_AI21_STR)) 		return context.getResources().getString(R.string.AI21);
		else if	(key.equalsIgnoreCase(_AI310_STR)) 		return context.getResources().getString(R.string.AI310);
		else if	(key.equalsIgnoreCase(_AI311_STR)) 		return context.getResources().getString(R.string.AI311);
		else if	(key.equalsIgnoreCase(_AI312_STR)) 		return context.getResources().getString(R.string.AI312);
		else if	(key.equalsIgnoreCase(_AI313_STR)) 		return context.getResources().getString(R.string.AI313);
		else if	(key.equalsIgnoreCase(_AI314_STR)) 		return context.getResources().getString(R.string.AI314);
		else if	(key.equalsIgnoreCase(_AI315_STR))	 	return context.getResources().getString(R.string.AI315);
		else if	(key.equalsIgnoreCase(_AI316_STR)) 		return context.getResources().getString(R.string.AI316);
		else if	(key.equalsIgnoreCase(_AI37_STR)) 		return context.getResources().getString(R.string.AI37);
		else if	(key.equalsIgnoreCase(_AI390_STR))		return context.getResources().getString(R.string.AI390);
		else if	(key.equalsIgnoreCase(_AI391_STR))	 	return context.getResources().getString(R.string.AI391);
		else if	(key.equalsIgnoreCase(_AI392_STR)) 		return context.getResources().getString(R.string.AI392);
		else if	(key.equalsIgnoreCase(_AI393_STR))	 	return context.getResources().getString(R.string.AI393);
		else if	(key.equalsIgnoreCase(_NI01_STR)) 		return context.getResources().getString(R.string.NI01);
		else if	(key.equalsIgnoreCase(_NI02_STR)) 		return context.getResources().getString(R.string.NI02);
		else if	(key.equalsIgnoreCase(_NI03_STR)) 		return context.getResources().getString(R.string.NI03);
		else if	(key.equalsIgnoreCase(_NI04_STR)) 		return context.getResources().getString(R.string.NI04);
		else if	(key.equalsIgnoreCase(_NI05_STR)) 		return context.getResources().getString(R.string.NI05);
		else if	(key.equalsIgnoreCase(_NI06_STR)) 		return context.getResources().getString(R.string.NI06);
		else if	(key.equalsIgnoreCase(_NI07_STR)) 		return context.getResources().getString(R.string.NI07);
		else if	(key.equalsIgnoreCase(_NI08_STR)) 		return context.getResources().getString(R.string.NI08);
		else return null;		
	}
	
	/***********************************************************************/
	/* Funkcja na podstawie klucza zwraca  a cuch znak w zawieraj cy warto
	 * do wy wietlenia
	 */
	/***********************************************************************/
	private String getListViewRowValueString(String key){
		if		(key.equalsIgnoreCase(_PN_STR)) 		return this.productName;
		else if	(key.equalsIgnoreCase(_MN_STR)) 		return this.manufacturerName;
		else if	(key.equalsIgnoreCase(_WA_STR)) 		return this.webAddress.getPath();
		else if	(key.equalsIgnoreCase(_AI01_STR)) 		return Long.toString(this.gtinNumber);
		else if	(key.equalsIgnoreCase(_AI11_STR)) 		return (this.productionDate.get(Calendar.DAY_OF_MONTH) + " " +
																this.productionDate.getDisplayName(Calendar.MONTH,Calendar.LONG,Locale.getDefault()) + " " +
																this.productionDate.get(Calendar.YEAR)); 
		else if	(key.equalsIgnoreCase(_AI15_STR)) 		return (this.bestBeforeDate.get(Calendar.DAY_OF_MONTH) + " " +
				   												this.bestBeforeDate.getDisplayName(Calendar.MONTH,Calendar.LONG,Locale.getDefault()) + " " +
				   												this.bestBeforeDate.get(Calendar.YEAR)); 
		else if	(key.equalsIgnoreCase(_AI17_STR)) 		return (this.expirationDate.get(Calendar.DAY_OF_MONTH) + " " +
																this.expirationDate.getDisplayName(Calendar.MONTH,Calendar.LONG,Locale.getDefault()) + " " +
																this.expirationDate.get(Calendar.YEAR)); 
		else if	(key.equalsIgnoreCase(_AI21_STR)) 		return this.serialNumber;
		else if	(key.equalsIgnoreCase(_AI310_STR))	 	return this.netWeightKg.toString();
		else if	(key.equalsIgnoreCase(_AI311_STR))	 	return this.lengthM.toString();
		else if	(key.equalsIgnoreCase(_AI312_STR)) 		return this.widthM.toString();
		else if	(key.equalsIgnoreCase(_AI313_STR))	 	return this.heightM.toString();
		else if	(key.equalsIgnoreCase(_AI314_STR)) 		return this.areaM2.toString();
		else if	(key.equalsIgnoreCase(_AI315_STR))		return this.netVolumeL.toString();
		else if	(key.equalsIgnoreCase(_AI316_STR)) 		return this.netVolumeM3.toString();
		else if	(key.equalsIgnoreCase(_AI37_STR)) 		return Integer.toString(countOfTradeItems);
		else if	(key.equalsIgnoreCase(_AI390_STR)) 		return this.amount.toString();
		else if	(key.equalsIgnoreCase(_AI391_STR))	 	return this.amount.toString();
		else if	(key.equalsIgnoreCase(_AI392_STR))	 	return this.price.toString();
		else if	(key.equalsIgnoreCase(_AI393_STR))	 	return this.price.toString();
		else if	(key.equalsIgnoreCase(_NI01_STR)) 		return this.energy.toString();
		else if	(key.equalsIgnoreCase(_NI02_STR)) 		return this.fatTotal.toString();
		else if	(key.equalsIgnoreCase(_NI03_STR)) 		return this.fatSaturated.toString();
		else if	(key.equalsIgnoreCase(_NI04_STR)) 		return this.carbohydrate.toString();
		else if	(key.equalsIgnoreCase(_NI05_STR)) 		return this.sugars.toString();
		else if	(key.equalsIgnoreCase(_NI06_STR)) 		return this.protein.toString();
		else if	(key.equalsIgnoreCase(_NI07_STR)) 		return this.sodium.toString();
		else if	(key.equalsIgnoreCase(_NI08_STR)) 		return this.roughage.toString();
		else return null;		
	}	
	
	/***********************************************************************/
	/* Funkcja na podstawie klucza zwraca  a cuch znak w zawieraj cy jednostk
	 * dla danej warto ci
	 */
	/***********************************************************************/
	private String getListViewRowUnitString(String key){
		if		(key.equalsIgnoreCase(_PN_STR)) 		return "";
		else if	(key.equalsIgnoreCase(_MN_STR)) 		return "";
		else if	(key.equalsIgnoreCase(_WA_STR)) 		return "";
		else if	(key.equalsIgnoreCase(_AI01_STR)) 		return "";
		else if	(key.equalsIgnoreCase(_AI11_STR)) 		return "";
		else if	(key.equalsIgnoreCase(_AI15_STR)) 		return "";
		else if	(key.equalsIgnoreCase(_AI17_STR)) 		return ""; 
		else if	(key.equalsIgnoreCase(_AI21_STR)) 		return "";
		else if	(key.equalsIgnoreCase(_AI310_STR)) 		return "kg";
		else if	(key.equalsIgnoreCase(_AI311_STR)) 		return "m";
		else if	(key.equalsIgnoreCase(_AI312_STR)) 		return "m";
		else if	(key.equalsIgnoreCase(_AI313_STR)) 		return "m";
		else if	(key.equalsIgnoreCase(_AI314_STR)) 		return "m2";
		else if	(key.equalsIgnoreCase(_AI315_STR))		return "L";
		else if	(key.equalsIgnoreCase(_AI316_STR)) 		return "m3";
		else if	(key.equalsIgnoreCase(_AI37_STR)) 		return "";
		else if	(key.equalsIgnoreCase(_AI390_STR)) 		return this.currency.getCurrencyNameCode();
		else if	(key.equalsIgnoreCase(_AI391_STR)) 		return this.currency.getCurrencyNameCode();
		else if	(key.equalsIgnoreCase(_AI392_STR)) 		return this.currency.getCurrencyNameCode() + "/unit";
		else if	(key.equalsIgnoreCase(_AI393_STR)) 		return this.currency.getCurrencyNameCode() + "/unit";
		else if	(key.equalsIgnoreCase(_NI01_STR)) 		return "kcal";
		else if	(key.equalsIgnoreCase(_NI02_STR)) 		return "g";
		else if	(key.equalsIgnoreCase(_NI03_STR)) 		return "g";
		else if	(key.equalsIgnoreCase(_NI04_STR)) 		return "g";
		else if	(key.equalsIgnoreCase(_NI05_STR)) 		return "g";
		else if	(key.equalsIgnoreCase(_NI06_STR)) 		return "g";
		else if	(key.equalsIgnoreCase(_NI07_STR)) 		return "g";
		else if	(key.equalsIgnoreCase(_NI08_STR)) 		return "g";
		else return null;		
	}		
	
	/***********************************************************************/
	/* Funkcja na podstawie klucza zwraca  a cuch znak w zawieraj cy
	 * warto   logiczn  okre laj c  czy dany klucz ma by  wy wietlany na li cie
	 */
	/***********************************************************************/
	private String getListViewRowVisibleString(String key){
		if		(key.equalsIgnoreCase(_PN_STR)) 		return Boolean.toString(false);
		else if	(key.equalsIgnoreCase(_MN_STR)) 		return Boolean.toString(false);
		else if	(key.equalsIgnoreCase(_WA_STR)) 		return Boolean.toString(true);
		else if	(key.equalsIgnoreCase(_AI01_STR)) 		return Boolean.toString(true);
		else if	(key.equalsIgnoreCase(_AI11_STR)) 		return Boolean.toString(true);
		else if	(key.equalsIgnoreCase(_AI15_STR)) 		return Boolean.toString(true);
		else if	(key.equalsIgnoreCase(_AI17_STR)) 		return Boolean.toString(true);
		else if	(key.equalsIgnoreCase(_AI21_STR)) 		return Boolean.toString(true);
		else if	(key.equalsIgnoreCase(_AI310_STR)) 		return Boolean.toString(true);
		else if	(key.equalsIgnoreCase(_AI311_STR)) 		return Boolean.toString(true);
		else if	(key.equalsIgnoreCase(_AI312_STR)) 		return Boolean.toString(true);
		else if	(key.equalsIgnoreCase(_AI313_STR)) 		return Boolean.toString(true);
		else if	(key.equalsIgnoreCase(_AI314_STR)) 		return Boolean.toString(true);
		else if	(key.equalsIgnoreCase(_AI315_STR))		return Boolean.toString(true);
		else if	(key.equalsIgnoreCase(_AI316_STR)) 		return Boolean.toString(true);
		else if	(key.equalsIgnoreCase(_AI37_STR)) 		return Boolean.toString(true);
		else if	(key.equalsIgnoreCase(_AI390_STR)) 		return Boolean.toString(false);
		else if	(key.equalsIgnoreCase(_AI391_STR)) 		return Boolean.toString(false);
		else if	(key.equalsIgnoreCase(_AI392_STR)) 		return Boolean.toString(true);
		else if	(key.equalsIgnoreCase(_AI393_STR)) 		return Boolean.toString(true);
		else if	(key.equalsIgnoreCase(_NI01_STR)) 		return Boolean.toString(true);
		else if	(key.equalsIgnoreCase(_NI02_STR)) 		return Boolean.toString(true);
		else if	(key.equalsIgnoreCase(_NI03_STR)) 		return Boolean.toString(true);
		else if	(key.equalsIgnoreCase(_NI04_STR)) 		return Boolean.toString(true);
		else if	(key.equalsIgnoreCase(_NI05_STR)) 		return Boolean.toString(true);
		else if	(key.equalsIgnoreCase(_NI06_STR)) 		return Boolean.toString(true);
		else if	(key.equalsIgnoreCase(_NI07_STR)) 		return Boolean.toString(true);
		else if	(key.equalsIgnoreCase(_NI08_STR)) 		return Boolean.toString(true);
		else return null;		
	}
	
	/***********************************************************************/
	/* Funkcja na podstawie klucza zwraca  a cuch znak w zawieraj cy
	 * kod koloru dla danego wierasza listy
	 */
	/***********************************************************************/
	private String getListViewRowColorString(String key, Context context){
		if		(key.equalsIgnoreCase(_PN_STR)) 		return Integer.toString(context.getResources().getColor(R.color.darkorange));
		else if	(key.equalsIgnoreCase(_MN_STR)) 		return Integer.toString(context.getResources().getColor(R.color.darkorange));
		else if	(key.equalsIgnoreCase(_WA_STR)) 		return Integer.toString(context.getResources().getColor(R.color.fuchsia));
		else if	(key.equalsIgnoreCase(_AI01_STR)) 		return Integer.toString(context.getResources().getColor(R.color.darkorange));
		else if	(key.equalsIgnoreCase(_AI11_STR)) 		return Integer.toString(context.getResources().getColor(R.color.darkorange));
		else if	(key.equalsIgnoreCase(_AI15_STR)) 		return Integer.toString(context.getResources().getColor(R.color.darkorange));
		else if	(key.equalsIgnoreCase(_AI17_STR)) 		return Integer.toString(context.getResources().getColor(R.color.darkorange));
		else if	(key.equalsIgnoreCase(_AI21_STR)) 		return Integer.toString(context.getResources().getColor(R.color.darkorange));
		else if	(key.equalsIgnoreCase(_AI310_STR)) 		return Integer.toString(context.getResources().getColor(R.color.darkorange));
		else if	(key.equalsIgnoreCase(_AI311_STR)) 		return Integer.toString(context.getResources().getColor(R.color.darkorange));
		else if	(key.equalsIgnoreCase(_AI312_STR)) 		return Integer.toString(context.getResources().getColor(R.color.darkorange));
		else if	(key.equalsIgnoreCase(_AI313_STR)) 		return Integer.toString(context.getResources().getColor(R.color.darkorange));
		else if	(key.equalsIgnoreCase(_AI314_STR)) 		return Integer.toString(context.getResources().getColor(R.color.darkorange));
		else if	(key.equalsIgnoreCase(_AI315_STR))		return Integer.toString(context.getResources().getColor(R.color.darkorange));
		else if	(key.equalsIgnoreCase(_AI316_STR)) 		return Integer.toString(context.getResources().getColor(R.color.darkorange));
		else if	(key.equalsIgnoreCase(_AI37_STR)) 		return Integer.toString(context.getResources().getColor(R.color.darkorange));
		else if	(key.equalsIgnoreCase(_AI390_STR)) 		return Integer.toString(context.getResources().getColor(R.color.darkorange));
		else if	(key.equalsIgnoreCase(_AI391_STR)) 		return Integer.toString(context.getResources().getColor(R.color.darkorange));
		else if	(key.equalsIgnoreCase(_AI392_STR)) 		return Integer.toString(context.getResources().getColor(R.color.darkorange));
		else if	(key.equalsIgnoreCase(_AI393_STR)) 		return Integer.toString(context.getResources().getColor(R.color.darkorange));
		else if	(key.equalsIgnoreCase(_NI01_STR)) 		return Integer.toString(context.getResources().getColor(R.color.green));
		else if	(key.equalsIgnoreCase(_NI02_STR)) 		return Integer.toString(context.getResources().getColor(R.color.green));
		else if	(key.equalsIgnoreCase(_NI03_STR)) 		return Integer.toString(context.getResources().getColor(R.color.green));
		else if	(key.equalsIgnoreCase(_NI04_STR)) 		return Integer.toString(context.getResources().getColor(R.color.green));
		else if	(key.equalsIgnoreCase(_NI05_STR)) 		return Integer.toString(context.getResources().getColor(R.color.green));
		else if	(key.equalsIgnoreCase(_NI06_STR)) 		return Integer.toString(context.getResources().getColor(R.color.green));
		else if	(key.equalsIgnoreCase(_NI07_STR)) 		return Integer.toString(context.getResources().getColor(R.color.green));
		else if	(key.equalsIgnoreCase(_NI08_STR)) 		return Integer.toString(context.getResources().getColor(R.color.green));
		else return null;		
	}	

	
	
	
// FUNKCJE WYKORZYSTYWANE DO PREZENTACJI DANYCH W LISTVIEW 
	
	/***********************************************************************/
	/* Funkcja usuwa z listy wiersze o atrybucie visible false 
	 * znajduj cym si  w kolumnie o numerze visibleColIndex
	 */
	/***********************************************************************/
	public List<String[]> trimListViewProductInfo(List<String[]> stringsList){
		List<String[]> trimStringList = new ArrayList<String[]>();
		
		for(String[] temp: stringsList){
			if(Boolean.parseBoolean(temp[visibleColIndex])){
				trimStringList.add(temp);
			}
		}
		
		return trimStringList;
	}
	
	/***********************************************************************/
	/* Funkcja na podstawie wype nionych p l klasy tworzy list  tablic
	 *  a cuch w znakowych, na podstawie kt rych wype niany jest obiekt listView
	 */
	/***********************************************************************/
	public List<String[]> genListViewProductInfo(Context context){		
		this.listViewProductInfo.clear();
		
		if(this.productName!=null){
			String buffTab[] = new String[numberOfCol];
	    	buffTab[keyColIndex] = _PN_STR; 
	    	buffTab[valueColIndex] = this.mapOfData.get(_PN_STR);
	    	buffTab[headerColIndex] = this.getListViewRowHeaderString(_PN_STR,context);
	    	buffTab[formatedValColIndex] = this.getListViewRowValueString(_PN_STR)+ " " + this.getListViewRowUnitString(_PN_STR);
	    	buffTab[visibleColIndex] = getListViewRowVisibleString(_PN_STR);
	    	buffTab[valueColorColIndex] = this.getListViewRowColorString(_PN_STR, context);
	    	this.listViewProductInfo.add(buffTab);										//Dodaje wpis do listy	
		}
		if(this.manufacturerName!=null){
			String buffTab[] = new String[numberOfCol];
	    	buffTab[keyColIndex] = _MN_STR; 
	    	buffTab[valueColIndex] = this.mapOfData.get(_MN_STR);
	    	buffTab[headerColIndex] = this.getListViewRowHeaderString(_MN_STR,context);
	    	buffTab[formatedValColIndex] = this.getListViewRowValueString(_MN_STR)+ " " + this.getListViewRowUnitString(_MN_STR);
	    	buffTab[visibleColIndex] = getListViewRowVisibleString(_MN_STR);
	    	buffTab[valueColorColIndex] = this.getListViewRowColorString(_MN_STR, context);
	    	this.listViewProductInfo.add(buffTab);										//Dodaje wpis do listy	
		}
		if(this.webAddress!=null){
			String buffTab[] = new String[numberOfCol];
	    	buffTab[keyColIndex] = _WA_STR; 
	    	buffTab[valueColIndex] = this.mapOfData.get(_WA_STR);
	    	buffTab[headerColIndex] = this.getListViewRowHeaderString(_WA_STR,context);
	    	buffTab[formatedValColIndex] = this.getListViewRowValueString(_WA_STR)+ " " + this.getListViewRowUnitString(_WA_STR);
	    	buffTab[visibleColIndex] = getListViewRowVisibleString(_WA_STR);
	    	buffTab[valueColorColIndex] = this.getListViewRowColorString(_WA_STR, context);
	    	this.listViewProductInfo.add(buffTab);										//Dodaje wpis do listy	
		}
		if(this.gtinNumber!=null){
			String buffTab[] = new String[numberOfCol];
	    	buffTab[keyColIndex] = _AI01_STR; 
	    	buffTab[valueColIndex] = this.mapOfData.get(_AI01_STR);
	    	buffTab[headerColIndex] = this.getListViewRowHeaderString(_AI01_STR,context);
	    	buffTab[formatedValColIndex] = this.getListViewRowValueString(_AI01_STR)+ " " + this.getListViewRowUnitString(_AI01_STR);
	    	buffTab[visibleColIndex] = getListViewRowVisibleString(_AI01_STR);
	    	buffTab[valueColorColIndex] = this.getListViewRowColorString(_AI01_STR, context);
	    	this.listViewProductInfo.add(buffTab);										//Dodaje wpis do listy	
		}
		if(this.productionDate!=null){
			String buffTab[] = new String[numberOfCol];
	    	buffTab[keyColIndex] = _AI11_STR; 
	    	buffTab[valueColIndex] = this.mapOfData.get(_AI11_STR);
	    	buffTab[headerColIndex] = this.getListViewRowHeaderString(_AI11_STR,context);
	    	buffTab[formatedValColIndex] = this.getListViewRowValueString(_AI11_STR)+ " " + this.getListViewRowUnitString(_AI11_STR);
	    	buffTab[visibleColIndex] = getListViewRowVisibleString(_AI11_STR);
	    	buffTab[valueColorColIndex] = this.getListViewRowColorString(_AI11_STR, context);
	    	this.listViewProductInfo.add(buffTab);										//Dodaje wpis do listy	
		}
		if(this.bestBeforeDate!=null){
			String buffTab[] = new String[numberOfCol];
	    	buffTab[keyColIndex] = _AI15_STR; 
	    	buffTab[valueColIndex] = this.mapOfData.get(_AI15_STR);
	    	buffTab[headerColIndex] = this.getListViewRowHeaderString(_AI15_STR,context);
	    	buffTab[formatedValColIndex] = this.getListViewRowValueString(_AI15_STR)+ " " + this.getListViewRowUnitString(_AI15_STR);
	    	buffTab[visibleColIndex] = getListViewRowVisibleString(_AI15_STR);
	    	buffTab[valueColorColIndex] = this.getListViewRowColorString(_AI15_STR, context);
	    	this.listViewProductInfo.add(buffTab);										//Dodaje wpis do listy	
		}
		if(this.expirationDate!=null){
			String buffTab[] = new String[numberOfCol];
	    	buffTab[keyColIndex] = _AI17_STR; 
	    	buffTab[valueColIndex] = this.mapOfData.get(_AI17_STR);
	    	buffTab[headerColIndex] = this.getListViewRowHeaderString(_AI17_STR,context);
	    	buffTab[formatedValColIndex] = this.getListViewRowValueString(_AI17_STR)+ " " + this.getListViewRowUnitString(_AI17_STR);
	    	buffTab[visibleColIndex] = getListViewRowVisibleString(_AI17_STR);
	    	buffTab[valueColorColIndex] = this.getListViewRowColorString(_AI17_STR, context);
	    	this.listViewProductInfo.add(buffTab);										//Dodaje wpis do listy	
		}
		if(this.serialNumber!=null){
			String buffTab[] = new String[numberOfCol];
	    	buffTab[keyColIndex] = _AI21_STR; 
	    	buffTab[valueColIndex] = this.mapOfData.get(_AI21_STR);
	    	buffTab[headerColIndex] = this.getListViewRowHeaderString(_AI21_STR,context);
	    	buffTab[formatedValColIndex] = this.getListViewRowValueString(_AI21_STR)+ " " + this.getListViewRowUnitString(_AI21_STR);
	    	buffTab[visibleColIndex] = getListViewRowVisibleString(_AI21_STR);
	    	buffTab[valueColorColIndex] = this.getListViewRowColorString(_AI21_STR, context);
	    	this.listViewProductInfo.add(buffTab);										//Dodaje wpis do listy	
		}
		if(this.netWeightKg!=null){
			String buffTab[] = new String[numberOfCol];
	    	buffTab[keyColIndex] = _AI310_STR; 
	    	buffTab[valueColIndex] = this.mapOfData.get(_AI310_STR);
	    	buffTab[headerColIndex] = this.getListViewRowHeaderString(_AI310_STR,context);
	    	buffTab[formatedValColIndex] = this.getListViewRowValueString(_AI310_STR)+ " " + this.getListViewRowUnitString(_AI310_STR);
	    	buffTab[visibleColIndex] = getListViewRowVisibleString(_AI310_STR);
	    	buffTab[valueColorColIndex] = this.getListViewRowColorString(_AI310_STR, context);
	    	this.listViewProductInfo.add(buffTab);										//Dodaje wpis do listy	
		}
		if(this.lengthM!=null){
			String buffTab[] = new String[numberOfCol];
	    	buffTab[keyColIndex] = _AI311_STR; 
	    	buffTab[valueColIndex] = this.mapOfData.get(_AI311_STR);
	    	buffTab[headerColIndex] = this.getListViewRowHeaderString(_AI311_STR,context);
	    	buffTab[formatedValColIndex] = this.getListViewRowValueString(_AI311_STR)+ " " + this.getListViewRowUnitString(_AI311_STR);
	    	buffTab[visibleColIndex] = getListViewRowVisibleString(_AI311_STR);
	    	buffTab[valueColorColIndex] = this.getListViewRowColorString(_AI311_STR, context);
	    	this.listViewProductInfo.add(buffTab);										//Dodaje wpis do listy	
		}
		if(this.widthM!=null){
			String buffTab[] = new String[numberOfCol];
	    	buffTab[keyColIndex] = _AI312_STR; 
	    	buffTab[valueColIndex] = this.mapOfData.get(_AI312_STR);
	    	buffTab[headerColIndex] = this.getListViewRowHeaderString(_AI312_STR,context);
	    	buffTab[formatedValColIndex] = this.getListViewRowValueString(_AI312_STR)+ " " + this.getListViewRowUnitString(_AI312_STR);
	    	buffTab[visibleColIndex] = getListViewRowVisibleString(_AI312_STR);
	    	buffTab[valueColorColIndex] = this.getListViewRowColorString(_AI312_STR, context);
	    	this.listViewProductInfo.add(buffTab);										//Dodaje wpis do listy	
		}
		if(this.heightM!=null){
			String buffTab[] = new String[numberOfCol];
	    	buffTab[keyColIndex] = _AI313_STR; 
	    	buffTab[valueColIndex] = this.mapOfData.get(_AI313_STR);
	    	buffTab[headerColIndex] = this.getListViewRowHeaderString(_AI313_STR,context);
	    	buffTab[formatedValColIndex] = this.getListViewRowValueString(_AI313_STR)+ " " + this.getListViewRowUnitString(_AI313_STR);
	    	buffTab[visibleColIndex] = getListViewRowVisibleString(_AI313_STR);
	    	buffTab[valueColorColIndex] = this.getListViewRowColorString(_AI313_STR, context);
	    	this.listViewProductInfo.add(buffTab);										//Dodaje wpis do listy	
		}
		if(this.areaM2!=null){
			String buffTab[] = new String[numberOfCol];
	    	buffTab[keyColIndex] = _AI314_STR; 
	    	buffTab[valueColIndex] = this.mapOfData.get(_AI314_STR);
	    	buffTab[headerColIndex] = this.getListViewRowHeaderString(_AI314_STR,context);
	    	buffTab[formatedValColIndex] = this.getListViewRowValueString(_AI314_STR)+ " " + this.getListViewRowUnitString(_AI314_STR);
	    	buffTab[visibleColIndex] = getListViewRowVisibleString(_AI314_STR);
	    	buffTab[valueColorColIndex] = this.getListViewRowColorString(_AI314_STR, context);
	    	this.listViewProductInfo.add(buffTab);										//Dodaje wpis do listy	
		}
		if(this.netVolumeL!=null){
			String buffTab[] = new String[numberOfCol];
	    	buffTab[keyColIndex] = _AI315_STR; 
	    	buffTab[valueColIndex] = this.mapOfData.get(_AI315_STR);
	    	buffTab[headerColIndex] = this.getListViewRowHeaderString(_AI315_STR,context);
	    	buffTab[formatedValColIndex] = this.getListViewRowValueString(_AI315_STR)+ " " + this.getListViewRowUnitString(_AI315_STR);
	    	buffTab[visibleColIndex] = getListViewRowVisibleString(_AI315_STR);
	    	buffTab[valueColorColIndex] = this.getListViewRowColorString(_AI315_STR, context);
	    	this.listViewProductInfo.add(buffTab);										//Dodaje wpis do listy	
		}
		if(this.netVolumeM3!=null){
			String buffTab[] = new String[numberOfCol];
	    	buffTab[keyColIndex] = _AI316_STR; 
	    	buffTab[valueColIndex] = this.mapOfData.get(_AI316_STR);
	    	buffTab[headerColIndex] = this.getListViewRowHeaderString(_AI316_STR,context);
	    	buffTab[formatedValColIndex] = this.getListViewRowValueString(_AI316_STR)+ " " + this.getListViewRowUnitString(_AI316_STR);
	    	buffTab[visibleColIndex] = getListViewRowVisibleString(_AI316_STR);
	    	buffTab[valueColorColIndex] = this.getListViewRowColorString(_AI316_STR, context);
	    	this.listViewProductInfo.add(buffTab);										//Dodaje wpis do listy	
		}
		if(this.countOfTradeItems!=null){
			String buffTab[] = new String[numberOfCol];
	    	buffTab[keyColIndex] = _AI37_STR; 
	    	buffTab[valueColIndex] = this.mapOfData.get(_AI37_STR);
	    	buffTab[headerColIndex] = this.getListViewRowHeaderString(_AI37_STR,context);
	    	buffTab[formatedValColIndex] = this.getListViewRowValueString(_AI37_STR)+ " " + this.getListViewRowUnitString(_AI37_STR);
	    	buffTab[visibleColIndex] = getListViewRowVisibleString(_AI37_STR);
	    	buffTab[valueColorColIndex] = this.getListViewRowColorString(_AI37_STR, context);
	    	this.listViewProductInfo.add(buffTab);										//Dodaje wpis do listy	
		}
		if(this.amount!=null){
			String buffTab[] = new String[numberOfCol];
			if(this.mapOfData.containsKey(_AI390_STR)){
		    	buffTab[keyColIndex] = _AI390_STR; 
		    	buffTab[valueColIndex] = this.mapOfData.get(_AI390_STR);
		    	buffTab[headerColIndex] = this.getListViewRowHeaderString(_AI390_STR,context);
		    	buffTab[formatedValColIndex] = this.getListViewRowValueString(_AI390_STR)+ " " + this.getListViewRowUnitString(_AI390_STR);
		    	buffTab[visibleColIndex] = getListViewRowVisibleString(_AI390_STR);
		    	buffTab[valueColorColIndex] = this.getListViewRowColorString(_AI390_STR, context);
		    	this.listViewProductInfo.add(buffTab);										//Dodaje wpis do listy	
			}else if(this.mapOfData.containsKey(_AI391_STR)){
		    	buffTab[keyColIndex] = _AI391_STR; 
		    	buffTab[valueColIndex] = this.mapOfData.get(_AI391_STR);
		    	buffTab[headerColIndex] = this.getListViewRowHeaderString(_AI391_STR,context);
		    	buffTab[formatedValColIndex] = this.getListViewRowValueString(_AI391_STR)+ " " + this.getListViewRowUnitString(_AI391_STR);
		    	buffTab[visibleColIndex] = getListViewRowVisibleString(_AI391_STR);
		    	buffTab[valueColorColIndex] = this.getListViewRowColorString(_AI391_STR, context);
		    	this.listViewProductInfo.add(buffTab);										//Dodaje wpis do listy	
			}
		}
		if(this.price!=null){
			String buffTab[] = new String[numberOfCol];
			if(this.mapOfData.containsKey(_AI392_STR)){
		    	buffTab[keyColIndex] = _AI392_STR; 
		    	buffTab[valueColIndex] = this.mapOfData.get(_AI392_STR);
		    	buffTab[headerColIndex] = this.getListViewRowHeaderString(_AI392_STR,context);
		    	buffTab[formatedValColIndex] = this.getListViewRowValueString(_AI392_STR)+ " " + this.getListViewRowUnitString(_AI392_STR);
		    	buffTab[visibleColIndex] = getListViewRowVisibleString(_AI392_STR);
		    	buffTab[valueColorColIndex] = this.getListViewRowColorString(_AI392_STR, context);
		    	this.listViewProductInfo.add(buffTab);										//Dodaje wpis do listy	
			}else if(this.mapOfData.containsKey(_AI393_STR)){
		    	buffTab[keyColIndex] = _AI393_STR; 
		    	buffTab[valueColIndex] = this.mapOfData.get(_AI393_STR);
		    	buffTab[headerColIndex] = this.getListViewRowHeaderString(_AI393_STR,context);
		    	buffTab[formatedValColIndex] = this.getListViewRowValueString(_AI393_STR)+ " " + this.getListViewRowUnitString(_AI393_STR);
		    	buffTab[visibleColIndex] = getListViewRowVisibleString(_AI393_STR);
		    	buffTab[valueColorColIndex] = this.getListViewRowColorString(_AI393_STR, context);
		    	this.listViewProductInfo.add(buffTab);										//Dodaje wpis do listy	
			}
		}
		if(this.energy!=null){
			String buffTab[] = new String[numberOfCol];
	    	buffTab[keyColIndex] = _NI01_STR; 
	    	buffTab[valueColIndex] = this.mapOfData.get(_NI01_STR);
	    	buffTab[headerColIndex] = this.getListViewRowHeaderString(_NI01_STR,context);
	    	buffTab[formatedValColIndex] = this.getListViewRowValueString(_NI01_STR)+ " " + this.getListViewRowUnitString(_NI01_STR);
	    	buffTab[visibleColIndex] = getListViewRowVisibleString(_NI01_STR);
	    	buffTab[valueColorColIndex] = this.getListViewRowColorString(_NI01_STR, context);
	    	this.listViewProductInfo.add(buffTab);										//Dodaje wpis do listy	
		}
		if(this.fatTotal!=null){
			String buffTab[] = new String[numberOfCol];
	    	buffTab[keyColIndex] = _NI02_STR; 
	    	buffTab[valueColIndex] = this.mapOfData.get(_NI02_STR);
	    	buffTab[headerColIndex] = this.getListViewRowHeaderString(_NI02_STR,context);
	    	buffTab[formatedValColIndex] = this.getListViewRowValueString(_NI02_STR)+ " " + this.getListViewRowUnitString(_NI02_STR);
	    	buffTab[visibleColIndex] = getListViewRowVisibleString(_NI02_STR);
	    	buffTab[valueColorColIndex] = this.getListViewRowColorString(_NI02_STR, context);
	    	this.listViewProductInfo.add(buffTab);										//Dodaje wpis do listy	
		}
		if(this.fatSaturated!=null){
			String buffTab[] = new String[numberOfCol];
	    	buffTab[keyColIndex] = _NI03_STR; 
	    	buffTab[valueColIndex] = this.mapOfData.get(_NI03_STR);
	    	buffTab[headerColIndex] = this.getListViewRowHeaderString(_NI03_STR,context);
	    	buffTab[formatedValColIndex] = this.getListViewRowValueString(_NI03_STR)+ " " + this.getListViewRowUnitString(_NI03_STR);
	    	buffTab[visibleColIndex] = getListViewRowVisibleString(_NI03_STR);
	    	buffTab[valueColorColIndex] = this.getListViewRowColorString(_NI03_STR, context);
	    	this.listViewProductInfo.add(buffTab);										//Dodaje wpis do listy	
		}
		if(this.carbohydrate!=null){
			String buffTab[] = new String[numberOfCol];
	    	buffTab[keyColIndex] = _NI04_STR; 
	    	buffTab[valueColIndex] = this.mapOfData.get(_NI04_STR);
	    	buffTab[headerColIndex] = this.getListViewRowHeaderString(_NI04_STR,context);
	    	buffTab[formatedValColIndex] = this.getListViewRowValueString(_NI04_STR)+ " " + this.getListViewRowUnitString(_NI04_STR);
	    	buffTab[visibleColIndex] = getListViewRowVisibleString(_NI04_STR);
	    	buffTab[valueColorColIndex] = this.getListViewRowColorString(_NI04_STR, context);
	    	this.listViewProductInfo.add(buffTab);										//Dodaje wpis do listy	
		}
		if(this.sugars!=null){
			String buffTab[] = new String[numberOfCol];
	    	buffTab[keyColIndex] = _NI05_STR; 
	    	buffTab[valueColIndex] = this.mapOfData.get(_NI05_STR);
	    	buffTab[headerColIndex] = this.getListViewRowHeaderString(_NI05_STR,context);
	    	buffTab[formatedValColIndex] = this.getListViewRowValueString(_NI05_STR)+ " " + this.getListViewRowUnitString(_NI05_STR);
	    	buffTab[visibleColIndex] = getListViewRowVisibleString(_NI05_STR);
	    	buffTab[valueColorColIndex] = this.getListViewRowColorString(_NI05_STR, context);
	    	this.listViewProductInfo.add(buffTab);										//Dodaje wpis do listy	
		}
		if(this.protein!=null){
			String buffTab[] = new String[numberOfCol];
	    	buffTab[keyColIndex] = _NI06_STR; 
	    	buffTab[valueColIndex] = this.mapOfData.get(_NI06_STR);
	    	buffTab[headerColIndex] = this.getListViewRowHeaderString(_NI06_STR,context);
	    	buffTab[formatedValColIndex] = this.getListViewRowValueString(_NI06_STR)+ " " + this.getListViewRowUnitString(_NI06_STR);
	    	buffTab[visibleColIndex] = getListViewRowVisibleString(_NI06_STR);
	    	buffTab[valueColorColIndex] = this.getListViewRowColorString(_NI06_STR, context);
	    	this.listViewProductInfo.add(buffTab);										//Dodaje wpis do listy	
		}
		if(this.sodium!=null){
			String buffTab[] = new String[numberOfCol];
	    	buffTab[keyColIndex] = _NI07_STR; 
	    	buffTab[valueColIndex] = this.mapOfData.get(_NI07_STR);
	    	buffTab[headerColIndex] = this.getListViewRowHeaderString(_NI07_STR,context);
	    	buffTab[formatedValColIndex] = this.getListViewRowValueString(_NI07_STR)+ " " + this.getListViewRowUnitString(_NI07_STR);
	    	buffTab[visibleColIndex] = getListViewRowVisibleString(_NI07_STR);
	    	buffTab[valueColorColIndex] = this.getListViewRowColorString(_NI07_STR, context);
	    	this.listViewProductInfo.add(buffTab);										//Dodaje wpis do listy	
		}
		if(this.roughage!=null){
			String buffTab[] = new String[numberOfCol];
	    	buffTab[keyColIndex] = _NI08_STR; 
	    	buffTab[valueColIndex] = this.mapOfData.get(_NI08_STR);
	    	buffTab[headerColIndex] = this.getListViewRowHeaderString(_NI08_STR,context);
	    	buffTab[formatedValColIndex] = this.getListViewRowValueString(_NI08_STR)+ " " + this.getListViewRowUnitString(_NI08_STR);
	    	buffTab[visibleColIndex] = getListViewRowVisibleString(_NI08_STR);
	    	buffTab[valueColorColIndex] = this.getListViewRowColorString(_NI08_STR, context);
	    	this.listViewProductInfo.add(buffTab);										//Dodaje wpis do listy	
		}
			
		return this.listViewProductInfo;
	}

	/***********************************************************************/	
	/* Funkcja zwraca bierz c  list  tablic  a cuch w znakowych,
	 * na podstawie kt rych wype niany jest obiekt listView
	 */
	/***********************************************************************/
	public List<String[]> getListViewProductInfo(){
		return listViewProductInfo;
	}
	
	//***********************************************************************/
	 //* the function Convert byte Array to a "String" Formated with spaces
	 //* Example : convertHexByteArrayToString { 0X0F ; 0X43 } -> returns "0F 43"
	 //***********************************************************************/
	private String convertHexByteArrayToString (byte[] byteArrayToConvert)
	{
		 String ConvertedByte = ""; 
		 for(int i=0;i<byteArrayToConvert.length;i++)
		 {
			 if (byteArrayToConvert[i] < 0) {
				 ConvertedByte += Integer.toString(byteArrayToConvert[i] + 256, 16).toUpperCase(Locale.getDefault()) + " ";
			} else if (byteArrayToConvert[i] <= 15) {
				ConvertedByte += "0" + Integer.toString(byteArrayToConvert[i], 16).toUpperCase(Locale.getDefault()) + " ";
			} else {
				ConvertedByte += Integer.toString(byteArrayToConvert[i], 16).toUpperCase(Locale.getDefault()) + " ";
			}		
		 }

		 return ConvertedByte;
	}
	
	private byte[] convertHexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
}