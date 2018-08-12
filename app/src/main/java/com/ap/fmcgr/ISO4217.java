//Klasa implementuj ca rozpoznawanie walut zgodnie ze standardem ISO4217

package com.ap.fmcgr;

import java.util.Currency;
import java.util.Locale;


public class ISO4217{
	private String currencyName;
	private String currencyNameCode;
	private String currencyNumericCode;
	private Integer currencyMinorUnit;
	
	public ISO4217(String code){
		if(setCurrencyByNameCode(code)==false){									//Probuje najpierw ustalic walute po kodzie alfabetycznym
			if(setCurrencyByNumericCode(code)==false){							//a nastepnie po kodzie numerycznym
				Currency tempCurrency = Currency.getInstance(Locale.getDefault());
				setCurrencyByNameCode(tempCurrency.getCurrencyCode().substring(0, 3));
			}
		}
	}
	
	public String getCurrencyName(){
		return currencyName;
	}
	
	public String getCurrencyNameCode(){
		return currencyNameCode;
	}
	
	public String getCurrencyNumericCode(){
		return currencyNumericCode;
	}
	
	public Integer getCurrencyMinorUnit(){
		return currencyMinorUnit;
	}
	
	public boolean setCurrencyByNameCode(String currencyNameCode){
		if		(currencyNameCode.equalsIgnoreCase("ALL")) {this.currencyName="Lek"; this.currencyNumericCode="008"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("DZD")) {this.currencyName="Algerian Dinar"; this.currencyNumericCode="012"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("ARS")) {this.currencyName="Argentine Peso"; this.currencyNumericCode="032"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("AUD")) {this.currencyName="Australian Dollar"; this.currencyNumericCode="036"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("BSD")) {this.currencyName="Bahamian Dollar"; this.currencyNumericCode="044"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("BHD")) {this.currencyName="Bahraini Dinar"; this.currencyNumericCode="048"; this.currencyMinorUnit=3;}
		else if	(currencyNameCode.equalsIgnoreCase("BDT")) {this.currencyName="Taka"; this.currencyNumericCode="050"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("AMD")) {this.currencyName="Armenian Dram"; this.currencyNumericCode="051"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("BBD")) {this.currencyName="Barbados Dollar"; this.currencyNumericCode="052"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("BMD")) {this.currencyName="Bermudian Dollar"; this.currencyNumericCode="060"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("BTN")) {this.currencyName="Ngultrum"; this.currencyNumericCode="064"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("BOB")) {this.currencyName="Boliviano"; this.currencyNumericCode="068"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("BWP")) {this.currencyName="Pula"; this.currencyNumericCode="072"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("BZD")) {this.currencyName="Belize Dollar"; this.currencyNumericCode="084"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("SBD")) {this.currencyName="Solomon Islands Dollar"; this.currencyNumericCode="090"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("BND")) {this.currencyName="Brunei Dollar"; this.currencyNumericCode="096"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("MMK")) {this.currencyName="Kyat"; this.currencyNumericCode="104"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("BIF")) {this.currencyName="Burundi Franc"; this.currencyNumericCode="108"; this.currencyMinorUnit=0;}
		else if	(currencyNameCode.equalsIgnoreCase("KHR")) {this.currencyName="Riel"; this.currencyNumericCode="116"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("CAD")) {this.currencyName="Canadian Dollar"; this.currencyNumericCode="124"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("CVE")) {this.currencyName="Cabo Verde Escudo"; this.currencyNumericCode="132"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("KYD")) {this.currencyName="Cayman Islands Dollar"; this.currencyNumericCode="136"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("LKR")) {this.currencyName="Sri Lanka Rupee"; this.currencyNumericCode="144"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("CLP")) {this.currencyName="Chilean Peso"; this.currencyNumericCode="152"; this.currencyMinorUnit=0;}
		else if	(currencyNameCode.equalsIgnoreCase("CNY")) {this.currencyName="Yuan Renminbi"; this.currencyNumericCode="156"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("COP")) {this.currencyName="Colombian Peso"; this.currencyNumericCode="170"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("KMF")) {this.currencyName="Comoro Franc"; this.currencyNumericCode="174"; this.currencyMinorUnit=0;}
		else if	(currencyNameCode.equalsIgnoreCase("CRC")) {this.currencyName="Costa Rican Colon"; this.currencyNumericCode="188"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("HRK")) {this.currencyName="Croatian Kuna"; this.currencyNumericCode="191"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("CUP")) {this.currencyName="Cuban Peso"; this.currencyNumericCode="192"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("CZK")) {this.currencyName="Czech Koruna"; this.currencyNumericCode="203"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("DKK")) {this.currencyName="Danish Krone"; this.currencyNumericCode="208"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("DOP")) {this.currencyName="Dominican Peso"; this.currencyNumericCode="214"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("SVC")) {this.currencyName="El Salvador Colon"; this.currencyNumericCode="222"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("ETB")) {this.currencyName="Ethiopian Birr"; this.currencyNumericCode="230"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("ERN")) {this.currencyName="Nakfa"; this.currencyNumericCode="232"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("FKP")) {this.currencyName="Falkland Islands Pound"; this.currencyNumericCode="238"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("FJD")) {this.currencyName="Fiji Dollar"; this.currencyNumericCode="242"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("DJF")) {this.currencyName="Djibouti Franc"; this.currencyNumericCode="262"; this.currencyMinorUnit=0;}
		else if	(currencyNameCode.equalsIgnoreCase("GMD")) {this.currencyName="Dalasi"; this.currencyNumericCode="270"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("GIP")) {this.currencyName="Gibraltar Pound"; this.currencyNumericCode="292"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("GTQ")) {this.currencyName="Quetzal"; this.currencyNumericCode="320"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("GNF")) {this.currencyName="Guinea Franc"; this.currencyNumericCode="324"; this.currencyMinorUnit=0;}
		else if	(currencyNameCode.equalsIgnoreCase("GYD")) {this.currencyName="Guyana Dollar"; this.currencyNumericCode="328"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("HTG")) {this.currencyName="Gourde"; this.currencyNumericCode="332"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("HNL")) {this.currencyName="Lempira"; this.currencyNumericCode="340"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("HKD")) {this.currencyName="Hong Kong Dollar"; this.currencyNumericCode="344"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("HUF")) {this.currencyName="Forint"; this.currencyNumericCode="348"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("ISK")) {this.currencyName="Iceland Krona"; this.currencyNumericCode="352"; this.currencyMinorUnit=0;}
		else if	(currencyNameCode.equalsIgnoreCase("INR")) {this.currencyName="Indian Rupee"; this.currencyNumericCode="356"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("IDR")) {this.currencyName="Rupiah"; this.currencyNumericCode="360"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("IRR")) {this.currencyName="Iranian Rial"; this.currencyNumericCode="364"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("IQD")) {this.currencyName="Iraqi Dinar"; this.currencyNumericCode="368"; this.currencyMinorUnit=3;}
		else if	(currencyNameCode.equalsIgnoreCase("ILS")) {this.currencyName="New Israeli Sheqel"; this.currencyNumericCode="376"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("JMD")) {this.currencyName="Jamaican Dollar"; this.currencyNumericCode="388"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("JPY")) {this.currencyName="Yen"; this.currencyNumericCode="392"; this.currencyMinorUnit=0;}
		else if	(currencyNameCode.equalsIgnoreCase("KZT")) {this.currencyName="Tenge"; this.currencyNumericCode="398"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("JOD")) {this.currencyName="Jordanian Dinar"; this.currencyNumericCode="400"; this.currencyMinorUnit=3;}
		else if	(currencyNameCode.equalsIgnoreCase("KES")) {this.currencyName="Kenyan Shilling"; this.currencyNumericCode="404"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("KPW")) {this.currencyName="North Korean Won"; this.currencyNumericCode="408"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("KRW")) {this.currencyName="Won"; this.currencyNumericCode="410"; this.currencyMinorUnit=0;}
		else if	(currencyNameCode.equalsIgnoreCase("KWD")) {this.currencyName="Kuwaiti Dinar"; this.currencyNumericCode="414"; this.currencyMinorUnit=3;}
		else if	(currencyNameCode.equalsIgnoreCase("KGS")) {this.currencyName="Som"; this.currencyNumericCode="417"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("LAK")) {this.currencyName="Kip"; this.currencyNumericCode="418"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("LBP")) {this.currencyName="Lebanese Pound"; this.currencyNumericCode="422"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("LSL")) {this.currencyName="Loti"; this.currencyNumericCode="426"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("LRD")) {this.currencyName="Liberian Dollar"; this.currencyNumericCode="430"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("LYD")) {this.currencyName="Libyan Dinar"; this.currencyNumericCode="434"; this.currencyMinorUnit=3;}
		else if	(currencyNameCode.equalsIgnoreCase("MOP")) {this.currencyName="Pataca"; this.currencyNumericCode="446"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("MWK")) {this.currencyName="Kwacha"; this.currencyNumericCode="454"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("MYR")) {this.currencyName="Malaysian Ringgit"; this.currencyNumericCode="458"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("MVR")) {this.currencyName="Rufiyaa"; this.currencyNumericCode="462"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("MRO")) {this.currencyName="Ouguiya"; this.currencyNumericCode="478"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("MUR")) {this.currencyName="Mauritius Rupee"; this.currencyNumericCode="480"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("MXN")) {this.currencyName="Mexican Peso"; this.currencyNumericCode="484"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("MNT")) {this.currencyName="Tugrik"; this.currencyNumericCode="496"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("MDL")) {this.currencyName="Moldovan Leu"; this.currencyNumericCode="498"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("MAD")) {this.currencyName="Moroccan Dirham"; this.currencyNumericCode="504"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("OMR")) {this.currencyName="Rial Omani"; this.currencyNumericCode="512"; this.currencyMinorUnit=3;}
		else if	(currencyNameCode.equalsIgnoreCase("NAD")) {this.currencyName="Namibia Dollar"; this.currencyNumericCode="516"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("NPR")) {this.currencyName="Nepalese Rupee"; this.currencyNumericCode="524"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("ANG")) {this.currencyName="Netherlands Antillean Guilder"; this.currencyNumericCode="532"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("AWG")) {this.currencyName="Aruban Florin"; this.currencyNumericCode="533"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("VUV")) {this.currencyName="Vatu"; this.currencyNumericCode="548"; this.currencyMinorUnit=0;}
		else if	(currencyNameCode.equalsIgnoreCase("NZD")) {this.currencyName="New Zealand Dollar"; this.currencyNumericCode="554"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("NIO")) {this.currencyName="Cordoba Oro"; this.currencyNumericCode="558"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("NGN")) {this.currencyName="Naira"; this.currencyNumericCode="566"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("NOK")) {this.currencyName="Norwegian Krone"; this.currencyNumericCode="578"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("PKR")) {this.currencyName="Pakistan Rupee"; this.currencyNumericCode="586"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("PAB")) {this.currencyName="Balboa"; this.currencyNumericCode="590"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("PGK")) {this.currencyName="Kina"; this.currencyNumericCode="598"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("PYG")) {this.currencyName="Guarani"; this.currencyNumericCode="600"; this.currencyMinorUnit=0;}
		else if	(currencyNameCode.equalsIgnoreCase("PEN")) {this.currencyName="Nuevo Sol"; this.currencyNumericCode="604"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("PHP")) {this.currencyName="Philippine Peso"; this.currencyNumericCode="608"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("QAR")) {this.currencyName="Qatari Rial"; this.currencyNumericCode="634"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("RUB")) {this.currencyName="Russian Ruble"; this.currencyNumericCode="643"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("RWF")) {this.currencyName="Rwanda Franc"; this.currencyNumericCode="646"; this.currencyMinorUnit=0;}
		else if	(currencyNameCode.equalsIgnoreCase("SHP")) {this.currencyName="Saint Helena Pound"; this.currencyNumericCode="654"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("STD")) {this.currencyName="Dobra"; this.currencyNumericCode="678"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("SAR")) {this.currencyName="Saudi Riyal"; this.currencyNumericCode="682"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("SCR")) {this.currencyName="Seychelles Rupee"; this.currencyNumericCode="690"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("SLL")) {this.currencyName="Leone"; this.currencyNumericCode="694"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("SGD")) {this.currencyName="Singapore Dollar"; this.currencyNumericCode="702"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("VND")) {this.currencyName="Dong"; this.currencyNumericCode="704"; this.currencyMinorUnit=0;}
		else if	(currencyNameCode.equalsIgnoreCase("SOS")) {this.currencyName="Somali Shilling"; this.currencyNumericCode="706"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("ZAR")) {this.currencyName="Rand"; this.currencyNumericCode="710"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("SSP")) {this.currencyName="South Sudanese Pound"; this.currencyNumericCode="728"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("SZL")) {this.currencyName="Lilangeni"; this.currencyNumericCode="748"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("SEK")) {this.currencyName="Swedish Krona"; this.currencyNumericCode="752"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("CHF")) {this.currencyName="Swiss Franc"; this.currencyNumericCode="756"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("SYP")) {this.currencyName="Syrian Pound"; this.currencyNumericCode="760"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("THB")) {this.currencyName="Baht"; this.currencyNumericCode="764"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("TOP")) {this.currencyName="Pa anga"; this.currencyNumericCode="776"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("TTD")) {this.currencyName="Trinidad and Tobago Dollar"; this.currencyNumericCode="780"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("AED")) {this.currencyName="UAE Dirham"; this.currencyNumericCode="784"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("TND")) {this.currencyName="Tunisian Dinar"; this.currencyNumericCode="788"; this.currencyMinorUnit=3;}
		else if	(currencyNameCode.equalsIgnoreCase("UGX")) {this.currencyName="Uganda Shilling"; this.currencyNumericCode="800"; this.currencyMinorUnit=0;}
		else if	(currencyNameCode.equalsIgnoreCase("MKD")) {this.currencyName="Denar"; this.currencyNumericCode="807"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("EGP")) {this.currencyName="Egyptian Pound"; this.currencyNumericCode="818"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("GBP")) {this.currencyName="Pound Sterling"; this.currencyNumericCode="826"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("TZS")) {this.currencyName="Tanzanian Shilling"; this.currencyNumericCode="834"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("USD")) {this.currencyName="US Dollar"; this.currencyNumericCode="840"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("UYU")) {this.currencyName="Peso Uruguayo"; this.currencyNumericCode="858"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("UZS")) {this.currencyName="Uzbekistan Sum"; this.currencyNumericCode="860"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("WST")) {this.currencyName="Tala"; this.currencyNumericCode="882"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("YER")) {this.currencyName="Yemeni Rial"; this.currencyNumericCode="886"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("TWD")) {this.currencyName="New Taiwan Dollar"; this.currencyNumericCode="901"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("CUC")) {this.currencyName="Peso Convertible"; this.currencyNumericCode="931"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("ZWL")) {this.currencyName="Zimbabwe Dollar"; this.currencyNumericCode="932"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("TMT")) {this.currencyName="Turkmenistan New Manat"; this.currencyNumericCode="934"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("GHS")) {this.currencyName="Ghana Cedi"; this.currencyNumericCode="936"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("VEF")) {this.currencyName="Bolivar"; this.currencyNumericCode="937"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("SDG")) {this.currencyName="Sudanese Pound"; this.currencyNumericCode="938"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("UYI")) {this.currencyName="Uruguay Peso en Unidades Indexadas"; this.currencyNumericCode="940"; this.currencyMinorUnit=0;}
		else if	(currencyNameCode.equalsIgnoreCase("RSD")) {this.currencyName="Serbian Dinar"; this.currencyNumericCode="941"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("MZN")) {this.currencyName="Mozambique Metical"; this.currencyNumericCode="943"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("AZN")) {this.currencyName="Azerbaijanian Manat"; this.currencyNumericCode="944"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("RON")) {this.currencyName="New Romanian Leu"; this.currencyNumericCode="946"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("CHE")) {this.currencyName="WIR Euro"; this.currencyNumericCode="947"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("CHW")) {this.currencyName="WIR Franc"; this.currencyNumericCode="948"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("TRY")) {this.currencyName="Turkish Lira"; this.currencyNumericCode="949"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("XAF")) {this.currencyName="CFA Franc BEAC"; this.currencyNumericCode="950"; this.currencyMinorUnit=0;}
		else if	(currencyNameCode.equalsIgnoreCase("XCD")) {this.currencyName="East Caribbean Dollar"; this.currencyNumericCode="951"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("XOF")) {this.currencyName="CFA Franc BCEAO"; this.currencyNumericCode="952"; this.currencyMinorUnit=0;}
		else if	(currencyNameCode.equalsIgnoreCase("XPF")) {this.currencyName="CFP Franc"; this.currencyNumericCode="953"; this.currencyMinorUnit=0;}
		else if	(currencyNameCode.equalsIgnoreCase("ZMW")) {this.currencyName="Zambian Kwacha"; this.currencyNumericCode="967"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("SRD")) {this.currencyName="Surinam Dollar"; this.currencyNumericCode="968"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("MGA")) {this.currencyName="Malagasy Ariary"; this.currencyNumericCode="969"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("COU")) {this.currencyName="Unidad de Valor Real"; this.currencyNumericCode="970"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("AFN")) {this.currencyName="Afghani"; this.currencyNumericCode="971"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("TJS")) {this.currencyName="Somoni"; this.currencyNumericCode="972"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("AOA")) {this.currencyName="Kwanza"; this.currencyNumericCode="973"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("BYR")) {this.currencyName="Belarussian Ruble"; this.currencyNumericCode="974"; this.currencyMinorUnit=0;}
		else if	(currencyNameCode.equalsIgnoreCase("BGN")) {this.currencyName="Bulgarian Lev"; this.currencyNumericCode="975"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("CDF")) {this.currencyName="Congolese Franc"; this.currencyNumericCode="976"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("BAM")) {this.currencyName="Convertible Mark"; this.currencyNumericCode="977"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("EUR")) {this.currencyName="Euro"; this.currencyNumericCode="978"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("MXV")) {this.currencyName="Mexican Unidad de Inversion "; this.currencyNumericCode="979"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("UAH")) {this.currencyName="Hryvnia"; this.currencyNumericCode="980"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("GEL")) {this.currencyName="Lari"; this.currencyNumericCode="981"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("BOV")) {this.currencyName="Mvdol"; this.currencyNumericCode="984"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("PLN")) {this.currencyName="Zloty"; this.currencyNumericCode="985"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("BRL")) {this.currencyName="Brazilian Real"; this.currencyNumericCode="986"; this.currencyMinorUnit=2;}
		else if	(currencyNameCode.equalsIgnoreCase("CLF")) {this.currencyName="Unidad de Fomento"; this.currencyNumericCode="990"; this.currencyMinorUnit=4;}
		else if	(currencyNameCode.equalsIgnoreCase("USN")) {this.currencyName="US Dollar (Next day)"; this.currencyNumericCode="997"; this.currencyMinorUnit=2;} 
		else return false;
		
		this.currencyNameCode = currencyNameCode;
		return true;
	}

	public boolean setCurrencyByNumericCode(String currencyNumericCode){
		if		(currencyNumericCode.equalsIgnoreCase("008")) {this.currencyName="Lek"; this.currencyNameCode="ALL"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("012")) {this.currencyName="Algerian Dinar"; this.currencyNameCode="DZD"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("032")) {this.currencyName="Argentine Peso"; this.currencyNameCode="ARS"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("036")) {this.currencyName="Australian Dollar"; this.currencyNameCode="AUD"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("044")) {this.currencyName="Bahamian Dollar"; this.currencyNameCode="BSD"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("048")) {this.currencyName="Bahraini Dinar"; this.currencyNameCode="BHD"; this.currencyMinorUnit=3;}
		else if	(currencyNumericCode.equalsIgnoreCase("050")) {this.currencyName="Taka"; this.currencyNameCode="BDT"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("051")) {this.currencyName="Armenian Dram"; this.currencyNameCode="AMD"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("052")) {this.currencyName="Barbados Dollar"; this.currencyNameCode="BBD"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("060")) {this.currencyName="Bermudian Dollar"; this.currencyNameCode="BMD"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("064")) {this.currencyName="Ngultrum"; this.currencyNameCode="BTN"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("068")) {this.currencyName="Boliviano"; this.currencyNameCode="BOB"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("072")) {this.currencyName="Pula"; this.currencyNameCode="BWP"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("084")) {this.currencyName="Belize Dollar"; this.currencyNameCode="BZD"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("090")) {this.currencyName="Solomon Islands Dollar"; this.currencyNameCode="SBD"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("096")) {this.currencyName="Brunei Dollar"; this.currencyNameCode="BND"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("104")) {this.currencyName="Kyat"; this.currencyNameCode="MMK"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("108")) {this.currencyName="Burundi Franc"; this.currencyNameCode="BIF"; this.currencyMinorUnit=0;}
		else if	(currencyNumericCode.equalsIgnoreCase("116")) {this.currencyName="Riel"; this.currencyNameCode="KHR"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("124")) {this.currencyName="Canadian Dollar"; this.currencyNameCode="CAD"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("132")) {this.currencyName="Cabo Verde Escudo"; this.currencyNameCode="CVE"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("136")) {this.currencyName="Cayman Islands Dollar"; this.currencyNameCode="KYD"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("144")) {this.currencyName="Sri Lanka Rupee"; this.currencyNameCode="LKR"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("152")) {this.currencyName="Chilean Peso"; this.currencyNameCode="CLP"; this.currencyMinorUnit=0;}
		else if	(currencyNumericCode.equalsIgnoreCase("156")) {this.currencyName="Yuan Renminbi"; this.currencyNameCode="CNY"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("170")) {this.currencyName="Colombian Peso"; this.currencyNameCode="COP"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("174")) {this.currencyName="Comoro Franc"; this.currencyNameCode="KMF"; this.currencyMinorUnit=0;}
		else if	(currencyNumericCode.equalsIgnoreCase("188")) {this.currencyName="Costa Rican Colon"; this.currencyNameCode="CRC"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("191")) {this.currencyName="Croatian Kuna"; this.currencyNameCode="HRK"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("192")) {this.currencyName="Cuban Peso"; this.currencyNameCode="CUP"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("203")) {this.currencyName="Czech Koruna"; this.currencyNameCode="CZK"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("208")) {this.currencyName="Danish Krone"; this.currencyNameCode="DKK"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("214")) {this.currencyName="Dominican Peso"; this.currencyNameCode="DOP"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("222")) {this.currencyName="El Salvador Colon"; this.currencyNameCode="SVC"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("230")) {this.currencyName="Ethiopian Birr"; this.currencyNameCode="ETB"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("232")) {this.currencyName="Nakfa"; this.currencyNameCode="ERN"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("238")) {this.currencyName="Falkland Islands Pound"; this.currencyNameCode="FKP"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("242")) {this.currencyName="Fiji Dollar"; this.currencyNameCode="FJD"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("262")) {this.currencyName="Djibouti Franc"; this.currencyNameCode="DJF"; this.currencyMinorUnit=0;}
		else if	(currencyNumericCode.equalsIgnoreCase("270")) {this.currencyName="Dalasi"; this.currencyNameCode="GMD"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("292")) {this.currencyName="Gibraltar Pound"; this.currencyNameCode="GIP"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("320")) {this.currencyName="Quetzal"; this.currencyNameCode="GTQ"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("324")) {this.currencyName="Guinea Franc"; this.currencyNameCode="GNF"; this.currencyMinorUnit=0;}
		else if	(currencyNumericCode.equalsIgnoreCase("328")) {this.currencyName="Guyana Dollar"; this.currencyNameCode="GYD"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("332")) {this.currencyName="Gourde"; this.currencyNameCode="HTG"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("340")) {this.currencyName="Lempira"; this.currencyNameCode="HNL"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("344")) {this.currencyName="Hong Kong Dollar"; this.currencyNameCode="HKD"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("348")) {this.currencyName="Forint"; this.currencyNameCode="HUF"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("352")) {this.currencyName="Iceland Krona"; this.currencyNameCode="ISK"; this.currencyMinorUnit=0;}
		else if	(currencyNumericCode.equalsIgnoreCase("356")) {this.currencyName="Indian Rupee"; this.currencyNameCode="INR"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("360")) {this.currencyName="Rupiah"; this.currencyNameCode="IDR"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("364")) {this.currencyName="Iranian Rial"; this.currencyNameCode="IRR"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("368")) {this.currencyName="Iraqi Dinar"; this.currencyNameCode="IQD"; this.currencyMinorUnit=3;}
		else if	(currencyNumericCode.equalsIgnoreCase("376")) {this.currencyName="New Israeli Sheqel"; this.currencyNameCode="ILS"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("388")) {this.currencyName="Jamaican Dollar"; this.currencyNameCode="JMD"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("392")) {this.currencyName="Yen"; this.currencyNameCode="JPY"; this.currencyMinorUnit=0;}
		else if	(currencyNumericCode.equalsIgnoreCase("398")) {this.currencyName="Tenge"; this.currencyNameCode="KZT"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("400")) {this.currencyName="Jordanian Dinar"; this.currencyNameCode="JOD"; this.currencyMinorUnit=3;}
		else if	(currencyNumericCode.equalsIgnoreCase("404")) {this.currencyName="Kenyan Shilling"; this.currencyNameCode="KES"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("408")) {this.currencyName="North Korean Won"; this.currencyNameCode="KPW"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("410")) {this.currencyName="Won"; this.currencyNameCode="KRW"; this.currencyMinorUnit=0;}
		else if	(currencyNumericCode.equalsIgnoreCase("414")) {this.currencyName="Kuwaiti Dinar"; this.currencyNameCode="KWD"; this.currencyMinorUnit=3;}
		else if	(currencyNumericCode.equalsIgnoreCase("417")) {this.currencyName="Som"; this.currencyNameCode="KGS"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("418")) {this.currencyName="Kip"; this.currencyNameCode="LAK"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("422")) {this.currencyName="Lebanese Pound"; this.currencyNameCode="LBP"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("426")) {this.currencyName="Loti"; this.currencyNameCode="LSL"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("430")) {this.currencyName="Liberian Dollar"; this.currencyNameCode="LRD"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("434")) {this.currencyName="Libyan Dinar"; this.currencyNameCode="LYD"; this.currencyMinorUnit=3;}
		else if	(currencyNumericCode.equalsIgnoreCase("446")) {this.currencyName="Pataca"; this.currencyNameCode="MOP"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("454")) {this.currencyName="Kwacha"; this.currencyNameCode="MWK"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("458")) {this.currencyName="Malaysian Ringgit"; this.currencyNameCode="MYR"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("462")) {this.currencyName="Rufiyaa"; this.currencyNameCode="MVR"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("478")) {this.currencyName="Ouguiya"; this.currencyNameCode="MRO"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("480")) {this.currencyName="Mauritius Rupee"; this.currencyNameCode="MUR"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("484")) {this.currencyName="Mexican Peso"; this.currencyNameCode="MXN"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("496")) {this.currencyName="Tugrik"; this.currencyNameCode="MNT"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("498")) {this.currencyName="Moldovan Leu"; this.currencyNameCode="MDL"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("504")) {this.currencyName="Moroccan Dirham"; this.currencyNameCode="MAD"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("512")) {this.currencyName="Rial Omani"; this.currencyNameCode="OMR"; this.currencyMinorUnit=3;}
		else if	(currencyNumericCode.equalsIgnoreCase("516")) {this.currencyName="Namibia Dollar"; this.currencyNameCode="NAD"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("524")) {this.currencyName="Nepalese Rupee"; this.currencyNameCode="NPR"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("532")) {this.currencyName="Netherlands Antillean Guilder"; this.currencyNameCode="ANG"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("533")) {this.currencyName="Aruban Florin"; this.currencyNameCode="AWG"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("548")) {this.currencyName="Vatu"; this.currencyNameCode="VUV"; this.currencyMinorUnit=0;}
		else if	(currencyNumericCode.equalsIgnoreCase("554")) {this.currencyName="New Zealand Dollar"; this.currencyNameCode="NZD"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("558")) {this.currencyName="Cordoba Oro"; this.currencyNameCode="NIO"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("566")) {this.currencyName="Naira"; this.currencyNameCode="NGN"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("578")) {this.currencyName="Norwegian Krone"; this.currencyNameCode="NOK"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("586")) {this.currencyName="Pakistan Rupee"; this.currencyNameCode="PKR"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("590")) {this.currencyName="Balboa"; this.currencyNameCode="PAB"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("598")) {this.currencyName="Kina"; this.currencyNameCode="PGK"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("600")) {this.currencyName="Guarani"; this.currencyNameCode="PYG"; this.currencyMinorUnit=0;}
		else if	(currencyNumericCode.equalsIgnoreCase("604")) {this.currencyName="Nuevo Sol"; this.currencyNameCode="PEN"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("608")) {this.currencyName="Philippine Peso"; this.currencyNameCode="PHP"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("634")) {this.currencyName="Qatari Rial"; this.currencyNameCode="QAR"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("643")) {this.currencyName="Russian Ruble"; this.currencyNameCode="RUB"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("646")) {this.currencyName="Rwanda Franc"; this.currencyNameCode="RWF"; this.currencyMinorUnit=0;}
		else if	(currencyNumericCode.equalsIgnoreCase("654")) {this.currencyName="Saint Helena Pound"; this.currencyNameCode="SHP"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("678")) {this.currencyName="Dobra"; this.currencyNameCode="STD"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("682")) {this.currencyName="Saudi Riyal"; this.currencyNameCode="SAR"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("690")) {this.currencyName="Seychelles Rupee"; this.currencyNameCode="SCR"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("694")) {this.currencyName="Leone"; this.currencyNameCode="SLL"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("702")) {this.currencyName="Singapore Dollar"; this.currencyNameCode="SGD"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("704")) {this.currencyName="Dong"; this.currencyNameCode="VND"; this.currencyMinorUnit=0;}
		else if	(currencyNumericCode.equalsIgnoreCase("706")) {this.currencyName="Somali Shilling"; this.currencyNameCode="SOS"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("710")) {this.currencyName="Rand"; this.currencyNameCode="ZAR"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("728")) {this.currencyName="South Sudanese Pound"; this.currencyNameCode="SSP"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("748")) {this.currencyName="Lilangeni"; this.currencyNameCode="SZL"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("752")) {this.currencyName="Swedish Krona"; this.currencyNameCode="SEK"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("756")) {this.currencyName="Swiss Franc"; this.currencyNameCode="CHF"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("760")) {this.currencyName="Syrian Pound"; this.currencyNameCode="SYP"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("764")) {this.currencyName="Baht"; this.currencyNameCode="THB"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("776")) {this.currencyName="Pa anga"; this.currencyNameCode="TOP"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("780")) {this.currencyName="Trinidad and Tobago Dollar"; this.currencyNameCode="TTD"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("784")) {this.currencyName="UAE Dirham"; this.currencyNameCode="AED"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("788")) {this.currencyName="Tunisian Dinar"; this.currencyNameCode="TND"; this.currencyMinorUnit=3;}
		else if	(currencyNumericCode.equalsIgnoreCase("800")) {this.currencyName="Uganda Shilling"; this.currencyNameCode="UGX"; this.currencyMinorUnit=0;}
		else if	(currencyNumericCode.equalsIgnoreCase("807")) {this.currencyName="Denar"; this.currencyNameCode="MKD"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("818")) {this.currencyName="Egyptian Pound"; this.currencyNameCode="EGP"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("826")) {this.currencyName="Pound Sterling"; this.currencyNameCode="GBP"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("834")) {this.currencyName="Tanzanian Shilling"; this.currencyNameCode="TZS"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("840")) {this.currencyName="US Dollar"; this.currencyNameCode="USD"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("858")) {this.currencyName="Peso Uruguayo"; this.currencyNameCode="UYU"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("860")) {this.currencyName="Uzbekistan Sum"; this.currencyNameCode="UZS"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("882")) {this.currencyName="Tala"; this.currencyNameCode="WST"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("886")) {this.currencyName="Yemeni Rial"; this.currencyNameCode="YER"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("901")) {this.currencyName="New Taiwan Dollar"; this.currencyNameCode="TWD"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("931")) {this.currencyName="Peso Convertible"; this.currencyNameCode="CUC"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("932")) {this.currencyName="Zimbabwe Dollar"; this.currencyNameCode="ZWL"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("934")) {this.currencyName="Turkmenistan New Manat"; this.currencyNameCode="TMT"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("936")) {this.currencyName="Ghana Cedi"; this.currencyNameCode="GHS"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("937")) {this.currencyName="Bolivar"; this.currencyNameCode="VEF"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("938")) {this.currencyName="Sudanese Pound"; this.currencyNameCode="SDG"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("940")) {this.currencyName="Uruguay Peso en Unidades Indexadas"; this.currencyNameCode="UYI"; this.currencyMinorUnit=0;}
		else if	(currencyNumericCode.equalsIgnoreCase("941")) {this.currencyName="Serbian Dinar"; this.currencyNameCode="RSD"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("943")) {this.currencyName="Mozambique Metical"; this.currencyNameCode="MZN"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("944")) {this.currencyName="Azerbaijanian Manat"; this.currencyNameCode="AZN"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("946")) {this.currencyName="New Romanian Leu"; this.currencyNameCode="RON"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("947")) {this.currencyName="WIR Euro"; this.currencyNameCode="CHE"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("948")) {this.currencyName="WIR Franc"; this.currencyNameCode="CHW"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("949")) {this.currencyName="Turkish Lira"; this.currencyNameCode="TRY"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("950")) {this.currencyName="CFA Franc BEAC"; this.currencyNameCode="XAF"; this.currencyMinorUnit=0;}
		else if	(currencyNumericCode.equalsIgnoreCase("951")) {this.currencyName="East Caribbean Dollar"; this.currencyNameCode="XCD"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("952")) {this.currencyName="CFA Franc BCEAO"; this.currencyNameCode="XOF"; this.currencyMinorUnit=0;}
		else if	(currencyNumericCode.equalsIgnoreCase("953")) {this.currencyName="CFP Franc"; this.currencyNameCode="XPF"; this.currencyMinorUnit=0;}
		else if	(currencyNumericCode.equalsIgnoreCase("967")) {this.currencyName="Zambian Kwacha"; this.currencyNameCode="ZMW"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("968")) {this.currencyName="Surinam Dollar"; this.currencyNameCode="SRD"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("969")) {this.currencyName="Malagasy Ariary"; this.currencyNameCode="MGA"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("970")) {this.currencyName="Unidad de Valor Real"; this.currencyNameCode="COU"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("971")) {this.currencyName="Afghani"; this.currencyNameCode="AFN"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("972")) {this.currencyName="Somoni"; this.currencyNameCode="TJS"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("973")) {this.currencyName="Kwanza"; this.currencyNameCode="AOA"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("974")) {this.currencyName="Belarussian Ruble"; this.currencyNameCode="BYR"; this.currencyMinorUnit=0;}
		else if	(currencyNumericCode.equalsIgnoreCase("975")) {this.currencyName="Bulgarian Lev"; this.currencyNameCode="BGN"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("976")) {this.currencyName="Congolese Franc"; this.currencyNameCode="CDF"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("977")) {this.currencyName="Convertible Mark"; this.currencyNameCode="BAM"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("978")) {this.currencyName="Euro"; this.currencyNameCode="EUR"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("979")) {this.currencyName="Mexican Unidad de Inversion "; this.currencyNameCode="MXV"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("980")) {this.currencyName="Hryvnia"; this.currencyNameCode="UAH"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("981")) {this.currencyName="Lari"; this.currencyNameCode="GEL"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("984")) {this.currencyName="Mvdol"; this.currencyNameCode="BOV"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("985")) {this.currencyName="Zloty"; this.currencyNameCode="PLN"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("986")) {this.currencyName="Brazilian Real"; this.currencyNameCode="BRL"; this.currencyMinorUnit=2;}
		else if	(currencyNumericCode.equalsIgnoreCase("990")) {this.currencyName="Unidad de Fomento"; this.currencyNameCode="CLF"; this.currencyMinorUnit=4;}
		else if	(currencyNumericCode.equalsIgnoreCase("997")) {this.currencyName="US Dollar (Next day)"; this.currencyNameCode="USN"; this.currencyMinorUnit=2;}
		else return false;
		
		this.currencyNumericCode = currencyNumericCode;
		return true;
	}
}
