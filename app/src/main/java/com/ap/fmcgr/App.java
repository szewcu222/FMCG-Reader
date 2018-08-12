//Warstwa aplikacji

package com.ap.fmcgr;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Application;

public class App extends Application
{
	public static final Boolean SHOW_STATUS = false;									//Flaga trybu logowania
	public Boolean fullScreenFlag;														//Flaga trybu pe noekranowego
	public String logStr;																// a cuch przechowuj cy log odczytu danych
	
	public static final String SP_APP_STAT_FILE_NAME = 	"AppStatisticFile";				//Plik przechowuj cy dane statystyczne o aplikacji (z wy  czeniem ustawie  ustawienia)
	public static final String SP_STARTUP_CNT = 		"StartupCounter";				//Licznik uruchomie  programu
	public static final String SP_READED_TAG_CNT = 		"ReadedTagCounter";				//Klucz przechowuj cy sumaryczn  ilo   odczytanych tag w
	public static final String SP_ADD_TO_SL_CNT = 		"AddToShoppingCartCounter";		//Klucz przechowuj cy sumaryczn  ilo   tag w dodanych do listy zakup w
	
	public List<ProductInfo> currentShoppingCart = new ArrayList<ProductInfo>();		//Lista produkt w
    	
	
	
	
	
	
	
	

	/*
	 * Metody przydatne do wyznaczania statystyki dla listy zakup w
	 */
	
	//Je eli wszystkie pozycje na li cie maj  t  sam  walut  to jest ona zwracana.
	public ISO4217 getCurrencyInShoppingCart(){
		ISO4217 firstItemCurrency = null;														//Warto   null oznacza,  e waluty s  r ne. Inne warto ci oznaczaj  walut  w jakiej wyr  one s  wszystkie ceny produkt w z listy
		
		if(this.currentShoppingCart.isEmpty() == false){
			firstItemCurrency = this.currentShoppingCart.get(0).getCurrency();					//Odczytuje walute z pierwszej pozycji	
				
			for(int i=0;i<this.currentShoppingCart.size();i++){
				if(firstItemCurrency.getCurrencyNameCode().equalsIgnoreCase(this.currentShoppingCart.get(i).getCurrency().getCurrencyNameCode()) == false){				//Por wnywana jest waluta pierwszego elementu na li cie z kolejnymi
					firstItemCurrency = null;																															//Je eli cho by jedna si  r zni a to metoda zwraca warto   null co  wiadczy o r nych walutach wyra aj cych cen produkt w z listy
				}		
			}		
		}
		return firstItemCurrency;
	}
	
	//Wyznacza sumaryczn  cene wszystkich produkt w na li cie
	public BigDecimal getShoppingCartTotalPrice(){
		BigDecimal totalPrice = null;																		//Inicjalizacja zmiennej przechowuj cej sumaryczn  kwot  wszystkich pozycji
																											
		if(this.currentShoppingCart.isEmpty() == false){													//Je eli lista nie jest pusta
			if(this.getCurrencyInShoppingCart() != null){													//Je eli lista charakteryzuje si  wsp ln  walut
				totalPrice = new BigDecimal(0);
				for (int j = 0; j < this.currentShoppingCart.size(); j++){									//Sprawdzany jest ka dy element listy
					if(this.currentShoppingCart.get(j).getAmount() != null){								//Je eli element z listy nie ma pustej pozycji kwoty AI390 lub AI391
						totalPrice = totalPrice.add(this.currentShoppingCart.get(j).getAmount());			//to kwota ta dodawana jest do sumy
					}else if(this.currentShoppingCart.get(j).getPrice() != null){							//Je li pole kwoty jest puste a pole ceny jednostkowej jest wype nione
						totalPrice = totalPrice.add(this.currentShoppingCart.get(j).getAmountFromPrice());	//to oblicza kwot  i dodaje j  do sumy
					}
				}
			}
		}
		return totalPrice;
	}
	
	public BigDecimal getShoppingCartHighestPrice(){
		BigDecimal highestPrice = null;
		
		if(this.currentShoppingCart.isEmpty() == false){													//Je eli lista nie jest pusta
			if(this.getCurrencyInShoppingCart() != null){													//Je eli zosta a ustalona wsp lna waluta
				for (int j = 0; j < this.currentShoppingCart.size(); j++){									//Przeszukuje liste zakup w
					if(this.currentShoppingCart.get(j).getAmount() != null){								//Je eli znajdzie cen
						if(highestPrice == null){															//Sprawdza czy zmiennej highestPrice nadano warto
							highestPrice = this.currentShoppingCart.get(j).getAmount();						//je eli nie to zostaje ona skopiowana z bie  cej pozycji listy zakup w
						}else if(highestPrice.compareTo(this.currentShoppingCart.get(j).getAmount()) == -1){//Je eli natomiast zmienna highestPrice posiada ju  warto c to por wnuje si  j  z bierz c  pozycj  listy zakup w
								highestPrice = this.currentShoppingCart.get(j).getAmount();					//W przypadku gdy zmienna highestPrice posiada warto   mniejsz  ni  bie  ca pozycja z listy zakup w to jest ona podmieniana
						}
					}else if(this.currentShoppingCart.get(j).getAmountFromPrice() != null){					//Je eli algrotym nie znajdzie ceny ale znajdzie cene za jednostk  obi to ci to oblicza cen
						if(highestPrice == null){															//Sprawdza czy zmiennej highestPrice nadano warto
							highestPrice = this.currentShoppingCart.get(j).getAmountFromPrice();			//je eli nie to zostaje ona skopiowana z bie  cej pozycji listy zakup w
						}else if(highestPrice.compareTo(this.currentShoppingCart.get(j).getAmountFromPrice()) == -1){ //Je eli natomiast zmienna highestPrice posiada ju  warto c to por wnuje si  j  z bierz c  pozycj  listy zakup w
							highestPrice = this.currentShoppingCart.get(j).getAmountFromPrice();			//W przypadku gdy zmienna highestPrice posiada warto   mniejsz  ni  bie  ca pozycja z listy zakup w to jest ona podmieniana
						}
					}
				}
			}
		}
		return highestPrice;
	}
	
	public BigDecimal getShoppingCartLowestPrice(){
		BigDecimal lowestPrice = null;
		
		if(this.currentShoppingCart.isEmpty() == false){													//Je eli lista nie jest pusta
			if(this.getCurrencyInShoppingCart() != null){													//Je eli zosta a ustalona wsp lna waluta
				for (int j = 0; j < this.currentShoppingCart.size(); j++){									//Przeszukuje liste zakup w
					if(this.currentShoppingCart.get(j).getAmount() != null){								//Je eli znajdzie cen
						if(lowestPrice == null){															//Sprawdza czy zmiennej lowestPrice nadano warto
							lowestPrice = this.currentShoppingCart.get(j).getAmount();						//je eli nie to zostaje ona skopiowana z bie  cej pozycji listy zakup w
						}else if(lowestPrice.compareTo(this.currentShoppingCart.get(j).getAmount()) == 1){	//Je eli natomiast zmienna lowestPrice posiada ju  warto c to por wnuje si  j  z bierz c  pozycj  listy zakup w
								lowestPrice = this.currentShoppingCart.get(j).getAmount();					//W przypadku gdy zmienna lowestPrice posiada warto   wi ksz  ni  bie  ca pozycja z listy zakup w to jest ona podmieniana
						}
					}else if(this.currentShoppingCart.get(j).getAmountFromPrice() != null){					//Je eli algrotym nie znajdzie ceny ale znajdzie cene za jednostk  obi to ci to oblicza cen
						if(lowestPrice == null){															//Sprawdza czy zmiennej lowestPrice nadano warto
							lowestPrice = this.currentShoppingCart.get(j).getAmountFromPrice();				//je eli nie to zostaje ona skopiowana z bie  cej pozycji listy zakup w
						}else if(lowestPrice.compareTo(this.currentShoppingCart.get(j).getAmountFromPrice()) == 1){ //Je eli natomiast zmienna lowestPrice posiada ju  warto c to por wnuje si  j  z bierz c  pozycj  listy zakup w
							lowestPrice = this.currentShoppingCart.get(j).getAmountFromPrice();				//W przypadku gdy zmienna lowestPrice posiada warto   wi ksz  ni  bie  ca pozycja z listy zakup w to jest ona podmieniana
						}
					}
				}
			}
		}
		return lowestPrice;
	}
	
	
	//Wyznacza najbli sz  dat  produktu, kt ry jako pierwszy nie b dzie zdatny do u ycia
	public Calendar getShoppingCartFirstExpirationDate(){
		Calendar firstDate = null;
		for (int j = 0; j < this.currentShoppingCart.size(); j++){
			if(firstDate == null){														//Je eli zmienna nearestDate nie jest ustalona
				if(this.currentShoppingCart.get(j).getExpirationDate() != null){				//Je eli dana pozycja na li cie zakup w ma wype nione pole daty przydatno ci
					firstDate = this.currentShoppingCart.get(j).getExpirationDate();		//Kopiuje t  dat  do zmiennej nearestDate
				}else if(this.currentShoppingCart.get(j).getBestBeforeDate() != null){		//Je eli dana pozycja na li cie zakup w nie ma wype nionego pola daty przydatno ci ale ma dat  "najlepiej zu y  do"
					firstDate = this.currentShoppingCart.get(j).getBestBeforeDate();		//Kopiuje t  dat  do zmiennej nearestDate
				}
			}else{																			//Je eli zmienna nearestDate zosta a ju  ustalona
				if(this.currentShoppingCart.get(j).getExpirationDate() != null){				//Je eli dana pozycja na li cie zakup w ma wype nione pole daty przydatno ci
					if(firstDate.getTimeInMillis()>this.currentShoppingCart.get(j).getExpirationDate().getTimeInMillis()){		//i jest ona bli sza obecnej ni  data nearestDatee
						firstDate = this.currentShoppingCart.get(j).getExpirationDate();	//to podmienia t  dat
					}
				}else if(this.currentShoppingCart.get(j).getBestBeforeDate() != null){		//Je eli natomiast dana pozycja na li cie zakup w ma wype nione pole daty "najlepiej zu y  do"
					if(firstDate.getTimeInMillis()>this.currentShoppingCart.get(j).getBestBeforeDate().getTimeInMillis()){		//i jest ona bli sza obecnej ni  data nearestDate
						firstDate = this.currentShoppingCart.get(j).getBestBeforeDate();	//to podmieni  t  dat
					}
				}
			}	
		}
		return firstDate;
	}
	
	//Wyznacza r nic  dni pomi dzy najwcze niejsz  dat  przydatno ci a dniem dzisiejszym
	public Long getShoppingCartNrOfDaysToFirstExpirationDate(){
		Calendar today = Calendar.getInstance();
		Calendar firstExpirationDate = this.getShoppingCartFirstExpirationDate();
		if(firstExpirationDate != null){
			return (firstExpirationDate.getTimeInMillis() - today.getTimeInMillis())/(24*60*60*1000);	
		}else{
			return null;
		}
	}
	
	//Wyznacza ilo   pozycji z kr tk  dat  wa no ci (<= 7 dni)
	public Integer getShoppingCartNrOfShortShelfLifeItems (){
		Calendar today = Calendar.getInstance();
		//today.set(2015, Calendar.JUNE, 10);		
		Integer statShortShelfLife = null;	
		for (int j = 0; j < this.currentShoppingCart.size(); j++){
			long diff = 0;
			if(this.currentShoppingCart.get(j).getExpirationDate() != null){
				diff = (this.currentShoppingCart.get(j).getExpirationDate().getTimeInMillis() - today.getTimeInMillis())/(24*60*60*1000);
				if(statShortShelfLife == null){
					statShortShelfLife = 0;
				}	
				if(diff <= 7){
					statShortShelfLife++;
				}
			}else if(this.currentShoppingCart.get(j).getBestBeforeDate() != null){
				diff = (this.currentShoppingCart.get(j).getBestBeforeDate().getTimeInMillis() - today.getTimeInMillis())/(24*60*60*1000);	
				if(statShortShelfLife == null){
					statShortShelfLife = 0;
				}
				if(diff <= 7) {
					statShortShelfLife++;
				}
			}
		}
		return statShortShelfLife;
	}
}
