//Klasa przechowuj ca informacje o identyfikatorze

package com.ap.fmcgr;

import java.util.Locale;

import android.nfc.Tag;

public class MyTag {
	private Tag currentTag;
	private boolean doubleSubcarrierFlag;
	private boolean highDataRateFlag;
	private boolean addressedModeFlag;
	private byte[] lastRequest;
	private byte[] lastResponse;
	private byte[] uid;
	private byte protocolCode;
	private String protocolName;
	private byte manufacturerCode;
	private String tagIcName;
	private String tagManufacturerName;
	private byte dsfid;
	private byte afi;
	private byte[] memorySize;
	private int blockSize;
	private int blockNumber;
	private byte icReference;
	private boolean basedOnTwoBytesAddress;
	private boolean MultipleReadSupported;
	private boolean MemoryExceed2048bytesSize;
	private byte[] tagMemory;

	public MyTag(Tag currentTag){
		this.currentTag = currentTag;
		this.doubleSubcarrierFlag = false;
		this.highDataRateFlag = false;
		addressedModeFlag = false;
	} 
	
	public void setCurrentTag(Tag currentTag) {
		this.currentTag = currentTag;
	}

	public Tag getCurrentTag() {
		return currentTag;
	}
	
	public void setDoubleSubcarrierFlag(boolean doubleSubcarrier){
		this.doubleSubcarrierFlag = doubleSubcarrier;
	}
	
	public boolean getDoubleSubcarrierFlag(){
		return doubleSubcarrierFlag;
	}
		
	public void setHighDataRateFlag(boolean highDataRate){
		this.highDataRateFlag = highDataRate;
	}
	
	public boolean getAddressedModeFlag(){
		return addressedModeFlag;
	}
	
	public void setAddressedModeFlag(boolean addressedMode){
		this.addressedModeFlag = addressedMode;
	}
	
	public boolean getHighDataRateFlag (){
		return highDataRateFlag;
	}
	
	public void setLastRequest(byte[] lastRequest) {
		this.lastRequest = null;
		this.lastRequest = lastRequest;
	}

	public byte[] getLastRequest() {
		return lastRequest;
	}
	
	public void setLastResponse(byte[] lastResponse) {
		this.lastResponse = null;
		this.lastResponse = lastResponse;
	}

	public byte[] getLastResponse() {
		return lastResponse;
	}

	
	public void setUid(byte[] uid) {
		this.uid = uid;
	}

	public byte[] getUid() {
		return uid;
	}

	public void setProtocolCode(byte protocolCode) {
		this.protocolCode = protocolCode;
		if (protocolCode == 0xE0) this.protocolName = "ISO 15693";
		else if(protocolCode == 0xD0) this.protocolName = "ISO 14443";
		else this.protocolName = "Unknown";
	}

	public byte getProtocolCode() {
		return protocolCode;
	}
	
	public String getProtocol() {
		return protocolName;
	}
	
	public void setManufacturerCode(byte manufacturerCode) {
		this.manufacturerCode = manufacturerCode;
	}

	public byte getManufacturerCode() {
		return manufacturerCode;
	}

	public void setDsfid(byte dsfid) {
		this.dsfid = dsfid;
	}

	public byte getDsfid() {
		return dsfid;
	}

	public void setAfi(byte afi) {
		this.afi = afi;
	}

	public byte getAfi() {
		return afi;
	}

	public void setMemorySize(byte[] memorySize) {
		this.memorySize = memorySize;
	}

	public byte[] getMemorySize() {
		return memorySize;
	}

	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}

	public int getBlockSize() {
		return blockSize;
	}
	
	public void setBlockNumber(int blockSize) {
		this.blockNumber = blockSize;
	}

	public int getBlockNumber() {
		return blockNumber;
	}

	public void setIcReference(byte icReference) {
		this.icReference = icReference;
	}

	public byte getIcReference() {
		return icReference;
	}

	public void setBasedOnTwoBytesAddress(boolean basedOnTwoBytesAddress) {
		this.basedOnTwoBytesAddress = basedOnTwoBytesAddress;
	}

	public boolean isBasedOnTwoBytesAddress() {
		return basedOnTwoBytesAddress;
	}

	public void setMultipleReadSupported(boolean MultipleReadSupported) {
		this.MultipleReadSupported = MultipleReadSupported;
	}

	public boolean isMultipleReadSupported() {
		return MultipleReadSupported;
	}	
	
	public void setMemoryExceed2048bytesSize(boolean MemoryExceed2048bytesSize) {
		this.MemoryExceed2048bytesSize = MemoryExceed2048bytesSize;
	}

	public boolean isMemoryExceed2048bytesSize() {
		return MemoryExceed2048bytesSize;
	}
	
	public void setTagIcName(String tagIcName) {
		this.tagIcName = tagIcName;
	}

	public String getTagIcName() {
		return tagIcName;
	}
	
	public void setTagManufacturerName(String tagManufacturerName) {
		this.tagManufacturerName = tagManufacturerName;
	}

	public String getTagManufacturerName() {
		return tagManufacturerName;
	}
	
	public void setReadedTagMemory(byte[] readedTagMemory){
		tagMemory = readedTagMemory;
	}
	
	public byte[] getReadedTagMemory(){
		return tagMemory;
	}
	
	
	//***********************************************************************/
	 //* The function Convert byte value to a "2-char String" Format
	 //* Example : ConvertHexByteToString ((byte)0X0F) -> returns "0F"
	 //***********************************************************************/
	public static String ConvertHexByteToString (byte byteToConvert)
	 {
		 String ConvertedByte = "";
		 if (byteToConvert < 0) {
			 ConvertedByte += Integer.toString(byteToConvert + 256, 16).toUpperCase(Locale.getDefault()) + " ";
		} else if (byteToConvert <= 15) {
			ConvertedByte += "0" + Integer.toString(byteToConvert, 16).toUpperCase(Locale.getDefault()) + " ";
		} else {
			ConvertedByte += Integer.toString(byteToConvert, 16).toUpperCase(Locale.getDefault()) + " ";
		}		
		 
		 return ConvertedByte;
	 }
	
	//***********************************************************************/
	 //* the function Convert byte Array to a "String" Formated with spaces
	 //* Example : ConvertHexByteArrayToString { 0X0F ; 0X43 } -> returns "0F 43"
	 //***********************************************************************/
	public static String ConvertHexByteArrayToString (byte[] byteArrayToConvert)
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
	
	//***********************************************************************/
    //* The function Decode the tag answer for the Inventory command
  	//***********************************************************************/
  	public boolean decodeInventoryResponse (byte[] inventoryResponse){
  		/* ISO 15639-3
  		 * Odpowied  gdy error_flag nie jest ustawiony:
  		 * 		SOF - zawsze
  		 * 		Flags (1B) - zawsze
  		 * 		DSFID (1B) - zawsze
  		 * 		UID (8B) - zawsze 
  		 */
  		
  		if(inventoryResponse[0] == (byte) 0x00)			//Je eli identyfikator zwr ci  prawid ow  odpowied
  		{
  			this.setDsfid(inventoryResponse[1]);
  			this.setProtocolCode(inventoryResponse[9]);
  			this.setManufacturerCode(inventoryResponse[8]);
  			
  			byte[] uid = new byte[8];
  			for (int i = 0; i < 8; i++){
  				uid[i] = inventoryResponse[9 - i];							//Odzyskiwanie numeru UID z odpowiedzi na komend  Get System Information
  			}
  			
  			this.setUid(uid);
  			decodeUid(uid);
  			
  			return true;
  		}
  		else{
  			return false;
  		}
  	}
  	
	//***********************************************************************/
    //* The function Decode the tag answer for the GetSystemInfo command.
  	//* The function fills the values (dsfid / afi / memory size / icRef /..) 
  	//* in the this class. Return true if everything is ok.
  	//***********************************************************************/
  	public boolean decodeGetSystemInfoResponse (byte[] getSystemInfoResponse)
  	{			 
  		/* ISO 15639-3
  		 * Odpowied  gdy error_flag nie jest ustawiony:
  		 * 		SOF - zawsze
  		 * 		Flags (1B) - zawsze
  		 * 		Info Flags (1B) - zawsze: 0b - DSFID, 1b - AFI, 2b - ilo   blok w i wielko   blok w w bajtach, 3b - IC reference w UID
  		 * 		UID (8B) - zawsze 
  		 * 		DSFID (1B) - je eli ustawiony jest 0 bit z pola Info Flags, w przeciwnym razie jest pomijany
  		 * 		AFI (1B) - je eli ustawiony jest 1 bit z pola Info Flags, w przeciwnym razie jest pomijany
  		 * 		Number of blocks (1B) - je eli ustawiony jest 2 bit z pola Info Flags, w przeciwnym razie jest pomijany
  		 *		Block size in bytes (1B[0:4]) - je eli ustawiony jest 2 bit z pola Info Flags, w przeciwnym razie jest pomijany
  		 */
  		 
  		if(getSystemInfoResponse[0] == (byte) 0x00)								//Je eli identyfikator zwr ci  prawid ow  odpowied
  		{ 
  			byte[] uid = new byte[8];											//Deklaracja tablicy do przechowywania UID w potaci 8 bitowych zmiennych
  			for (int i = 0; i < 8; i++){
  				uid[i] = getSystemInfoResponse[9 - i];							//Odzyskiwanie numeru UID z odpowiedzi na komend  Get System Information
  			}
  			
  			this.setUid(uid);
  			decodeUid(uid);
  			
  			int shift = 0;
  			if((getSystemInfoResponse[1]&0x01)!=0){								//Sprawdza 0 bit Info flag (DSFID) 
  				this.setDsfid(getSystemInfoResponse[10]);				
  				shift++;
  			}
  			if((getSystemInfoResponse[1]&0x02)!=0){								//Sprawdza 1 bit Info flag (AFI) 
  				this.setAfi(getSystemInfoResponse[10+shift]);
  				shift++;
  			}
  			if((getSystemInfoResponse[1]&0x04)!=0){								//Sprawdza 2 bit Info flag (VICC memory size) 	  			
  				if(this.isBasedOnTwoBytesAddress() == false){
  	  				byte[] memSize = new byte[2];
	  				memSize[1] = getSystemInfoResponse[10+shift];
	  				shift++;
	  				memSize[0] = getSystemInfoResponse[10+shift];
	  				shift++;
	  				
	  				this.setMemorySize(memSize);
	  				this.setBlockSize(((int)this.getMemorySize()[0]&0xFF)+1);
	  				this.setBlockNumber(((int)this.getMemorySize()[1]&0xFF)+1);
  				}
  				else{
  					byte[] memSize = new byte[3];
	  				memSize[2] = getSystemInfoResponse[10+shift];
	  				shift++;
	  				memSize[1] = getSystemInfoResponse[10+shift];
	  				shift++;
	  				memSize[0] = getSystemInfoResponse[10+shift];
	  				shift++;	
	  					  				
	  				this.setMemorySize(memSize);
	  				this.setBlockSize(((int)this.getMemorySize()[0]&0xFF)+1);
	  				this.setBlockNumber((((int)this.getMemorySize()[1]&0xFF)*256)+((int)this.getMemorySize()[2]&0xFF)+1);			//problem z rzutowaniem bo przy rzutowaniu byte 0xFF do int wychodzi 0xFFFFFFFF co oznacza -1. Z tego powodu po zrzutowaniu int jest maskowany 0xFF czyli pozostaje tylko 8 pierwszych bit w
  				}
  			}
  			if((getSystemInfoResponse[1]&0x08)!=0){								//Sprawdza 3 bit Info flag (IC reference) 
  	  			this.setIcReference(getSystemInfoResponse[10+shift]);
  	  			shift++;
  			}
 			return true;
  		}
  		 
  		//if the tag has returned an error code 
  		else
  			return false;
  	 }
  	
	//***********************************************************************/
    //* The function Decode the tag UID.
  	//* The function fills the values (tagIcName / tagManufacturerName /  
  	//* basedOnTwoBytesAddress / ...) in the this class.
  	//***********************************************************************/	
  	public void decodeUid(byte[] uid){
		//******* PROTOCOL *******//
		this.setProtocolCode(uid[0]);										//Dekoduje protok  komunikacyjny
					
		//***** MANUFACTURER *****// Kody wg. ISO/IEC 7816-AM1
		switch(uid[1]){
  			case (byte) 0x00: this.setTagManufacturerName("Not Specified"); break;
  			case (byte) 0x01: this.setTagManufacturerName("Motorola"); break;
  			case (byte) 0x02: this.setTagManufacturerName("STMicroelectronics"); break;
  			case (byte) 0x03: this.setTagManufacturerName("Hitachi, Ltd"); break;
  			case (byte) 0x04: this.setTagManufacturerName("NXP Semiconductors"); break;
  			case (byte) 0x05: this.setTagManufacturerName("Infineon Technologies AG"); break;
  			case (byte) 0x06: this.setTagManufacturerName("Cylink"); break;
  			case (byte) 0x07: this.setTagManufacturerName("Texas Instrument"); break;
  			case (byte) 0x08: this.setTagManufacturerName("Fujitsu Limited"); break;
  			case (byte) 0x09: this.setTagManufacturerName("Matsushita Electronics Corporation"); break;
  			case (byte) 0x0A: this.setTagManufacturerName("NEC"); break;
  			case (byte) 0x0B: this.setTagManufacturerName("Oki Electric Industry Co. Ltd"); break;
  			case (byte) 0x0C: this.setTagManufacturerName("Toshiba Corp."); break;
  			case (byte) 0x0D: this.setTagManufacturerName("Mitsubishi Electric Corp."); break;
  			case (byte) 0x0E: this.setTagManufacturerName("Samsung Electronics Co. Ltd"); break;
  			case (byte) 0x0F: this.setTagManufacturerName("Hyundai Electronics Industries Co. Ltd"); break;
  			case (byte) 0x10: this.setTagManufacturerName("LG-Semiconductors Co. Ltd"); break;
  			case (byte) 0x11: this.setTagManufacturerName("Emosyn-EM Microelectronics"); break;
  			case (byte) 0x12: this.setTagManufacturerName("INSIDE Technology"); break;
  			case (byte) 0x13: this.setTagManufacturerName("ORGA Kartensysteme GmbH"); break;
  			case (byte) 0x14: this.setTagManufacturerName("SHARP Corporation"); break;
  			case (byte) 0x15: this.setTagManufacturerName("ATMEL"); break;
  			case (byte) 0x16: this.setTagManufacturerName("EM Microelectronic-Marin SA"); break;
  			case (byte) 0x17: this.setTagManufacturerName("KSW Microtec GmbH"); break;
  			case (byte) 0x19: this.setTagManufacturerName("XICOR, Inc."); break;
  			case (byte) 0x2B: this.setTagManufacturerName("Maxim Integrated Products"); break;
  			default: 		  this.setTagManufacturerName("Unknown manufacturer");
		}
		 
		//****** TAG IC NAME *****// 
		if(uid[1] == 0x02){													//Je eli producentem jest ST Microelectronics
  			if(uid[2] >= (byte) 0x04 && uid[2] <= (byte) 0x07){			this.setTagIcName("LRI512"); 			this.setMultipleReadSupported(false);	this.setMemoryExceed2048bytesSize(false);	this.setBasedOnTwoBytesAddress(false);
  			}else if(uid[2] >= (byte) 0x14 && uid[2] <= (byte) 0x17){	this.setTagIcName("LRI64");				this.setMultipleReadSupported(false);	this.setMemoryExceed2048bytesSize(false);	this.setBasedOnTwoBytesAddress(true);
  			}else if(uid[2] >= (byte) 0x20 && uid[2] <= (byte) 0x23){	this.setTagIcName("LRI2K");				this.setMultipleReadSupported(true);	this.setMemoryExceed2048bytesSize(false);	this.setBasedOnTwoBytesAddress(false);
  			}else if(uid[2] >= (byte) 0x28 && uid[2] <= (byte) 0x2B){	this.setTagIcName("LRIS2K");			this.setMultipleReadSupported(false);	this.setMemoryExceed2048bytesSize(false);	this.setBasedOnTwoBytesAddress(false);
  			}else if(uid[2] >= (byte) 0x2C && uid[2] <= (byte) 0x2F){	this.setTagIcName("M24LR64");			this.setMultipleReadSupported(true);	this.setMemoryExceed2048bytesSize(true);	this.setBasedOnTwoBytesAddress(true);
  			}else if(uid[2] >= (byte) 0x40 && uid[2] <= (byte) 0x43){	this.setTagIcName("LRI1K");				this.setMultipleReadSupported(true);	this.setMemoryExceed2048bytesSize(false);	this.setBasedOnTwoBytesAddress(false);
  			}else if(uid[2] >= (byte) 0x44 && uid[2] <= (byte) 0x47){	this.setTagIcName("LRIS64K");			this.setMultipleReadSupported(true);	this.setMemoryExceed2048bytesSize(true);	this.setBasedOnTwoBytesAddress(true);
  			}else if(uid[2] >= (byte) 0x48 && uid[2] <= (byte) 0x4B){	this.setTagIcName("M24LR01E");			this.setMultipleReadSupported(true);	this.setMemoryExceed2048bytesSize(false);	this.setBasedOnTwoBytesAddress(false);
  			}else if(uid[2] >= (byte) 0x4C && uid[2] <= (byte) 0x4F){	this.setTagIcName("M24LR16E");			this.setMultipleReadSupported(true);	this.setMemoryExceed2048bytesSize(true);	this.setBasedOnTwoBytesAddress(true);
  			}else if(uid[2] >= (byte) 0x50 && uid[2] <= (byte) 0x53){	this.setTagIcName("M24LR02E");			this.setMultipleReadSupported(true);	this.setMemoryExceed2048bytesSize(false);	this.setBasedOnTwoBytesAddress(false);
  			}else if(uid[2] >= (byte) 0x54 && uid[2] <= (byte) 0x57){	this.setTagIcName("M24LR32E");			this.setMultipleReadSupported(true);	this.setMemoryExceed2048bytesSize(true);	this.setBasedOnTwoBytesAddress(true);		 	 
  			}else if(uid[2] >= (byte) 0x58 && uid[2] <= (byte) 0x5B){	this.setTagIcName("M24LR04E");			this.setMultipleReadSupported(true);	this.setMemoryExceed2048bytesSize(true);	this.setBasedOnTwoBytesAddress(false);
  			}else if(uid[2] >= (byte) 0x5C && uid[2] <= (byte) 0x5F){	this.setTagIcName("M24LR64E");			this.setMultipleReadSupported(true);	this.setMemoryExceed2048bytesSize(true);	this.setBasedOnTwoBytesAddress(true);
  			}else if(uid[2] >= (byte) 0x60 && uid[2] <= (byte) 0x63){	this.setTagIcName("M24LR08E"); 			this.setMultipleReadSupported(true); 	this.setMemoryExceed2048bytesSize(true);	this.setBasedOnTwoBytesAddress(false);
  			}else if(uid[2] >= (byte) 0x64 && uid[2] <= (byte) 0x67){	this.setTagIcName("M24LR128E"); 		this.setMultipleReadSupported(true);	this.setMemoryExceed2048bytesSize(true);	this.setBasedOnTwoBytesAddress(true);		 	 
  			}else if(uid[2] >= (byte) 0x6C && uid[2] <= (byte) 0x6F){	this.setTagIcName("M24LR256E"); 		this.setMultipleReadSupported(true); 	this.setMemoryExceed2048bytesSize(true);	this.setBasedOnTwoBytesAddress(true);		 	 
  			}else if(uid[2] >= (byte) 0xF8 && uid[2] <= (byte) 0xFB){ 	this.setTagIcName("Detected product");	this.setMultipleReadSupported(true);	this.setMemoryExceed2048bytesSize(true);	this.setBasedOnTwoBytesAddress(true);
  			}else{										  				this.setTagIcName("Unknown product");	this.setMultipleReadSupported(false); 	this.setMemoryExceed2048bytesSize(false); 	this.setBasedOnTwoBytesAddress(false);
  			}
		}
		else if(uid[1]==0x04){												//Je eli producentem jest NXP
			if(uid[2] == (byte) 0x01){
				if(((uid[3]>>3)&0x03) == (byte) 0x00){			this.setTagIcName("ICODE SLI");			this.setMultipleReadSupported(false);	this.setMemoryExceed2048bytesSize(false);	this.setBasedOnTwoBytesAddress(false);
				}else if(((uid[3]>>3)&0x03) == (byte) 0x01){	this.setTagIcName("ICODE SLIX");		this.setMultipleReadSupported(false);	this.setMemoryExceed2048bytesSize(false);	this.setBasedOnTwoBytesAddress(false);
				}else if(((uid[3]>>3)&0x03) == (byte) 0x02){	this.setTagIcName("ICODE SLIX2");		this.setMultipleReadSupported(false);	this.setMemoryExceed2048bytesSize(false);	this.setBasedOnTwoBytesAddress(false);
				}else if(((uid[3]>>3)&0x03) == (byte) 0x03){	this.setTagIcName("ICODE RFU");			this.setMultipleReadSupported(false);	this.setMemoryExceed2048bytesSize(false);	this.setBasedOnTwoBytesAddress(false);
				}
			}else if(uid[2] == (byte) 0x02){  					this.setTagIcName("ICODE SLIX-S");		this.setMultipleReadSupported(false);	this.setMemoryExceed2048bytesSize(false); 	this.setBasedOnTwoBytesAddress(false);
			}else if(uid[2] == (byte) 0x03){		 			this.setTagIcName("ICODE SLIX-L");		this.setMultipleReadSupported(false);	this.setMemoryExceed2048bytesSize(false); 	this.setBasedOnTwoBytesAddress(false);
			}else{						  						this.setTagIcName("Unknown product"); 	this.setMultipleReadSupported(false);	this.setMemoryExceed2048bytesSize(false);	this.setBasedOnTwoBytesAddress(false);
  			}
		}
		else{																//Je eli producent nie nale y do powy szych
			this.setTagIcName("Unknown product");	 	this.setMultipleReadSupported(false); 	this.setMemoryExceed2048bytesSize(false); 	this.setBasedOnTwoBytesAddress(false);
		}
  	}
   	
}
