//Klasa implementujaca niskopoziomowo protok  komunikacyjny zgodny z ISO15693

package com.ap.fmcgr;

import android.nfc.Tag;
import android.nfc.tech.NfcV;
import android.util.Log;

public class ISO15693 {
	
	
	
	/***********************************************************************/
	/* The function send an command frame and return responce
	 * The argument myTag is the intent triggered with the TECH_DISCOVERED
	 */ 
	/***********************************************************************/
	public static byte[] SendCommand(Tag useTag, byte[] request){
		byte[] response = new byte[] { (byte) 0x01, (byte) 0x00};
		 		 
		NfcV nfcvTag = NfcV.get(useTag);
		try {	
			nfcvTag.close();
			nfcvTag.connect();
			response = nfcvTag.transceive(request);
			nfcvTag.close();
			 
			return response;
		}
		catch (Exception e) {												//wychwycenie wyj tku
			Log.d("NFCService", nfcvTag.toString());
			String commandName; 
			switch(request[1]){
				case 0x01: 	commandName = "Inventory Request"; break;
				case 0x20: 	commandName = "Read Single Block Request"; break;
				case 0x23: 	commandName = "Read Multiple Blocks Request"; break;
				case 0x2B: 	commandName = "Get System Information Request"; break;
				default: 	commandName = "Unknown Request";
			}
			Log.i("Exception: ",commandName + " " + e.getMessage());
		}
		return response;													//pomimo nienawi zania w komunikacji z identyfikatorem, zwraca 0x01 czyli to samo co indetyfikator w razie b  du
	}
	
	/***********************************************************************/
	/* The function send an Inventory command frame (0x26, 0x01, 0x00)
	 * Request flag (0x26) - 0b00100110 (ISO/IEC 15693-3): 
	 * 	[0]=0 A single sub-carrier frequency shall be used 
	 * 	[1]=1 High data rate shall be used
	 * 	[2]=1 Inventory flag (def 4-7 bits flag)
	 * 	[3]=0 No protocol format extension
	 * 	[4]=0 AFI field is not present
	 * 	[5]=1 One slot
	 * 	[6]=0 Option flag
	 * 	[7]=0 RFU  
	 * Inventory command (0x01),
	 * Mask length (0x00)
	 *  
	 * The argument myTag is the intent triggered with the TECH_DISCOVERED
	 */ 
	/***********************************************************************/
	public static MyTag SendInventoryCommand (MyTag myTag)
	{
		byte flag = ConvertBitesToBytes(0,0,1,0,0,1,1,0);
		byte commandCode = (byte)0x01;
		byte[] response;
				
		if(myTag.getDoubleSubcarrierFlag()) flag |= 0x01;					//Set double subcarrier frequency
		else 								flag &= ~0x01;					//Set single subcarrier frequency
		if(myTag.getHighDataRateFlag())		flag |= 0x02;					//Set high data rate
		else								flag &= ~0x02;					//Set low data rate
		
		byte[] inventoryRequest = new byte[] { (byte) flag, (byte) commandCode, (byte) 0x00 };
		//response = SendCommand(myTag.getCurrentTag(), inventoryRequest);
		response = new byte[] {(byte) 0x00, (byte) 0x00, (byte)myTag.getCurrentTag().getId()[0],(byte)myTag.getCurrentTag().getId()[1],(byte)myTag.getCurrentTag().getId()[2],(byte)myTag.getCurrentTag().getId()[3],(byte)myTag.getCurrentTag().getId()[4],(byte)myTag.getCurrentTag().getId()[5],(byte)myTag.getCurrentTag().getId()[6],(byte)myTag.getCurrentTag().getId()[7]};
		myTag.setLastRequest(inventoryRequest);
		myTag.setLastResponse(response);
		return myTag;
	}
	
	/***********************************************************************/
	/* The function send an GetSystemInformation command frame (0x22, 0x2B, UID)
	 * Request flag (0x22) - 0b00100010 (ISO/IEC 15693-3): 
	 * 	[0]=0 A single sub-carrier frequency shall be used 
	 * 	[1]=1 High data rate shall be used
	 * 	[2]=0 Inventory flag (def 4-7 bits flag)
	 * 	[3]=1 First try protocol format extension
	 * 	[4]=0 Select_flag
	 * 	[5]=1 Address_flag
	 * 	[6]=0 Option flag
	 * 	[7]=0 RFU  
	 * Get System Information command (0x2B),
	 * UID (8 bytes)
	 *  
	 * The argument myTag is the intent triggered with the TECH_DISCOVERED
	 */ 
	/***********************************************************************/	
	public static MyTag SendGetSystemInformationCommand (MyTag myTag)
	{
		//byte flag = 0<<7|0<<6|0<<5|0<<4|0<<3|0<<2|1<<1|0<<0;
		byte flag = ConvertBitesToBytes(0,0,1,0,1,0,1,0);					//Protocol Extencion flag is set
		byte commandCode = 0x2B;
		byte[] getSystemInformationRequest;
		byte[] response;
		
		if(myTag.getDoubleSubcarrierFlag()) flag |= 0x01;					//Set double subcarrier frequency
		else 								flag &= ~0x01;					//Set single subcarrier frequency
		if(myTag.getHighDataRateFlag())		flag |= 0x02;					//Set high data rate
		else								flag &= ~0x02;					//Set low data rate
		
		
		if(myTag.getAddressedModeFlag()){
			flag |= 0x20;													//Set addressed mode
			getSystemInformationRequest = new byte[] { (byte) flag, (byte) commandCode, (byte) myTag.getUid()[7],(byte) myTag.getUid()[6],(byte) myTag.getUid()[5],(byte) myTag.getUid()[4],(byte) myTag.getUid()[3],(byte) myTag.getUid()[2],(byte) myTag.getUid()[1],(byte) myTag.getUid()[0]};	
		}
		else{
			flag &= ~0x20;													//Set nonaddressed mode
			getSystemInformationRequest = new byte[] { (byte) flag, (byte) commandCode};		
		}
		response = SendCommand(myTag.getCurrentTag(), getSystemInformationRequest);
		myTag.setLastRequest(getSystemInformationRequest);
		myTag.setLastResponse(response);		
		
		if(response[0] == 0x00){
			myTag.setBasedOnTwoBytesAddress(true);
			return myTag;
		}
		else if (response[0] == 0x01){
			getSystemInformationRequest[0] &= ~0x08; 						//Clear protocol extencion flag
			response = SendCommand(myTag.getCurrentTag(), getSystemInformationRequest);
			myTag.setBasedOnTwoBytesAddress(false);
			myTag.setLastResponse(response);
			return myTag;		
		}
		else return myTag;
	}
	
	/***********************************************************************/
	/* The function send an ReadSingleBlock command frame (0x22, 0x20, UID, 1 byte blockAddress)
	 * Request flag (0x22) - 0b00100010 (ISO/IEC 15693-3): 
	 * 	[0]=0 A single sub-carrier frequency shall be used 
	 * 	[1]=1 High data rate shall be used
	 * 	[2]=0 Inventory flag (def 4-7 bits flag)
	 * 	[3]=0 No protocol format extension
	 * 	[4]=0 Select_flag
	 * 	[5]=1 Address_flag
	 * 	[6]=0 Option flag
	 * 	[7]=0 RFU  
	 * Read Single Block command (0x20),
	 * UID (8 bytes)
	 * Block address (1 byte)
	 *  
	 * The argument myTag is the intent triggered with the TECH_DISCOVERED
	 */ 
	/***********************************************************************/	
	public static MyTag SendReadSingleBlockCommand(MyTag myTag, byte blockAddress){
		
		byte flag = ConvertBitesToBytes(0,0,1,0,0,0,1,0);
		byte commandCode = 0x20;
		byte[] readSingleBlockRequest;
		byte[] response;
		
		if(myTag.getDoubleSubcarrierFlag()) flag |= 0x01;					//Set double subcarrier frequency
		else 								flag &= ~0x01;					//Set single subcarrier frequency
		if(myTag.getHighDataRateFlag())		flag |= 0x02;					//Set high data rate
		else								flag &= ~0x02;					//Set low data rate
		
		if(myTag.getAddressedModeFlag()){
			flag |= 0x20;
			readSingleBlockRequest = new byte[] { (byte) flag, (byte) commandCode, (byte) myTag.getUid()[7],(byte) myTag.getUid()[6],(byte) myTag.getUid()[5],(byte) myTag.getUid()[4],(byte) myTag.getUid()[3],(byte) myTag.getUid()[2],(byte) myTag.getUid()[1],(byte) myTag.getUid()[0], blockAddress};
		}
		else{
			flag &= ~0x20;	
			readSingleBlockRequest = new byte[] { (byte) flag, (byte) commandCode, blockAddress};
		}
		myTag.setLastRequest(readSingleBlockRequest);
		response = SendCommand(myTag.getCurrentTag(), readSingleBlockRequest);
		myTag.setLastResponse(response);
		
		return myTag;
	}
	
	/***********************************************************************/
	/* The function send an ReadSingleBlock command frame (0x22, 0x20, UID, 1 or 2 bytes blockAddress)
	 * Request flag (0x22) - 0b00100010 (ISO/IEC 15693-3): 
	 * 	[0]=0 A single sub-carrier frequency shall be used 
	 * 	[1]=1 High data rate shall be used
	 * 	[2]=0 Inventory flag (def 4-7 bits flag)
	 * 	[3]=0 No protocol format extension
	 * 	[4]=0 Select_flag
	 * 	[5]=1 Address_flag
	 * 	[6]=0 Option flag
	 * 	[7]=0 RFU  
	 * Read Single Block command (0x20),
	 * UID (8 bytes)
	 * Block address (2 byte)
	 *  
	 * The argument myTag is the intent triggered with the TECH_DISCOVERED
	 */ 
	/***********************************************************************/	
	public static MyTag SendReadSingleBlockCommand(MyTag myTag, short blockAddress){
		
		byte flag = ConvertBitesToBytes(0,0,1,0,0,0,1,0);
		byte commandCode = 0x20;
		byte[] readSingleBlockRequest;
		byte[] response;
		
		if(myTag.getDoubleSubcarrierFlag()) flag |= 0x01;					//Set double subcarrier frequency
		else 								flag &= ~0x01;					//Set single subcarrier frequency
		if(myTag.getHighDataRateFlag())		flag |= 0x02;					//Set high data rate
		else								flag &= ~0x02;					//Set low data rate
		
		if(myTag.isBasedOnTwoBytesAddress()){								//If address is 2 bytes
			if(blockAddress<myTag.getBlockNumber()){
				flag |= 0x08;												//Set protocol extension flag
				if(myTag.getAddressedModeFlag()){
					flag |= 0x20;
					readSingleBlockRequest = new byte[] { (byte) flag, (byte) commandCode, (byte) myTag.getUid()[7],(byte) myTag.getUid()[6],(byte) myTag.getUid()[5],(byte) myTag.getUid()[4],(byte) myTag.getUid()[3],(byte) myTag.getUid()[2],(byte) myTag.getUid()[1],(byte) myTag.getUid()[0], (byte)(blockAddress&0x00FF) ,(byte)((blockAddress&0xFF00)>>8)};       
				}
				else{
					flag &= ~0x20;
					readSingleBlockRequest = new byte[] { (byte) flag, (byte) commandCode, (byte)(blockAddress&0x00FF) ,(byte)((blockAddress&0xFF00)>>8)};       	
				}
				myTag.setLastRequest(readSingleBlockRequest);
				response = SendCommand(myTag.getCurrentTag(), readSingleBlockRequest);
				myTag.setLastResponse(response);
			}
			else{															//If address overragne
				byte[] errorFrame = new byte[2];
				errorFrame[0] = 0x01;
				errorFrame[1] = 0x10;
				myTag.setLastResponse(errorFrame);
			}
		}
		else{																//If address is 1 byte
			if(blockAddress<=0x00FF){										//If address is in range
				if(myTag.getAddressedModeFlag()){
					flag |= 0x20;
					readSingleBlockRequest = new byte[] { (byte) flag, (byte) commandCode, (byte) myTag.getUid()[7],(byte) myTag.getUid()[6],(byte) myTag.getUid()[5],(byte) myTag.getUid()[4],(byte) myTag.getUid()[3],(byte) myTag.getUid()[2],(byte) myTag.getUid()[1],(byte) myTag.getUid()[0], (byte)(blockAddress&0x00FF)};
				}
				else{
					flag &= ~0x20;
					readSingleBlockRequest = new byte[] { (byte) flag, (byte) commandCode, (byte)(blockAddress&0x00FF)};
				}
				myTag.setLastRequest(readSingleBlockRequest);
				response = SendCommand(myTag.getCurrentTag(), readSingleBlockRequest);
				myTag.setLastResponse(response);
			}
			else{															//If address overragne
				byte[] errorFrame = new byte[2];
				errorFrame[0] = 0x01;
				errorFrame[1] = 0x10;
				myTag.setLastResponse(errorFrame);
			}
		}	
		return myTag;
	}
	
	/***********************************************************************/
	/* The function send an ReadMultipleBlock command frame (0x22, 0x23, UID, 1 byte blockAddress)
	 * Request flag (0x22) - 0b00100010 (ISO/IEC 15693-3): 
	 * 	[0]=0 A single sub-carrier frequency shall be used 
	 * 	[1]=1 High data rate shall be used
	 * 	[2]=0 Inventory flag (def 4-7 bits flag)
	 * 	[3]=0 No protocol format extension
	 * 	[4]=0 Select_flag
	 * 	[5]=1 Address_flag
	 * 	[6]=0 Option flag
	 * 	[7]=0 RFU  
	 * Read Multiple Block command (0x23),
	 * UID (8 bytes)
	 * Block address (1 byte)
	 *  
	 * The argument myTag is the intent triggered with the TECH_DISCOVERED
	 */ 
	/***********************************************************************/	
	public static MyTag SendReadMultipleBlocksCommand(MyTag myTag, byte startBlockAddress, byte blocksNumber){
		
		byte flag = ConvertBitesToBytes(0,0,1,0,0,0,1,0);
		byte commandCode = 0x23;
		byte[] readMultipleBlocksRequest;
		byte[] response;
		
		if(myTag.getDoubleSubcarrierFlag()) flag |= 0x01;					//Set double subcarrier frequency
		else 								flag &= ~0x01;					//Set single subcarrier frequency
		if(myTag.getHighDataRateFlag())		flag |= 0x02;					//Set high data rate
		else								flag &= ~0x02;					//Set low data rate
		
		//if(blocksNumber>0x1F) blocksNumber = 0x1F;							//If blocks to read is more than 32 then limit them
				
		if(myTag.getAddressedModeFlag()){
			flag |= 0x20;
			readMultipleBlocksRequest = new byte[] { (byte) flag, (byte) commandCode, (byte) myTag.getUid()[7],(byte) myTag.getUid()[6],(byte) myTag.getUid()[5],(byte) myTag.getUid()[4],(byte) myTag.getUid()[3],(byte) myTag.getUid()[2],(byte) myTag.getUid()[1],(byte) myTag.getUid()[0], startBlockAddress, blocksNumber};
		}
		else{
			flag &= ~0x20;
			readMultipleBlocksRequest = new byte[] { (byte) flag, (byte) commandCode, startBlockAddress, blocksNumber};	
		}
		myTag.setLastRequest(readMultipleBlocksRequest);
		response = SendCommand(myTag.getCurrentTag(), readMultipleBlocksRequest);
		myTag.setLastResponse(response);
			
		return myTag;
	}
	
	/***********************************************************************/
	/* The function send an ReadMultipleBlock command frame (0x22, 0x23, UID, 1 or 2 byte blockAddress)
	 * Request flag (0x22) - 0b00100010 (ISO/IEC 15693-3): 
	 * 	[0]=0 A single sub-carrier frequency shall be used 
	 * 	[1]=1 High data rate shall be used
	 * 	[2]=0 Inventory flag (def 4-7 bits flag)
	 * 	[3]=0 No protocol format extension
	 * 	[4]=0 Select_flag
	 * 	[5]=1 Address_flag
	 * 	[6]=0 Option flag
	 * 	[7]=0 RFU  
	 * Read Multiple Block command (0x23),
	 * UID (8 bytes)
	 * Start block address (1 byte)
	 * Number of blocks to read (1 byte) - max 32 blocks (0x20 - 1 = 0x1F) (ISO15693) or max 16 blocks (0x10-1 = 0x0F) (M24LR64E)
	 *  
	 * The argument myTag is the intent triggered with the TECH_DISCOVERED
	 */ 
	/***********************************************************************/	
	public static MyTag SendReadMultipleBlocksCommand(MyTag myTag, short startBlockAddress, byte blocksNumber){
		
		byte flag = ConvertBitesToBytes(0,0,1,0,0,0,1,0);
		byte commandCode = 0x23;
		byte[] readMultipleBlocksRequest;
		byte[] response;
		
		if(myTag.getDoubleSubcarrierFlag()) flag |= 0x01;					//Set double subcarrier frequency
		else 								flag &= ~0x01;					//Set single subcarrier frequency
		if(myTag.getHighDataRateFlag())		flag |= 0x02;					//Set high data rate
		else								flag &= ~0x02;					//Set low data rate
		
		//if(blocksNumber>0x1F) blocksNumber = 0x1F;							//If blocks to read is more than 32 then limit them
		
		if(myTag.isBasedOnTwoBytesAddress()){								//If address is 2 bytes
			if(startBlockAddress<myTag.getBlockNumber()){
				flag |= 0x08;												//Set protocol extension flag
				if(myTag.getAddressedModeFlag()){
					flag |= 0x20;
					readMultipleBlocksRequest = new byte[] { (byte) flag, (byte) commandCode, (byte) myTag.getUid()[7],(byte) myTag.getUid()[6],(byte) myTag.getUid()[5],(byte) myTag.getUid()[4],(byte) myTag.getUid()[3],(byte) myTag.getUid()[2],(byte) myTag.getUid()[1],(byte) myTag.getUid()[0],(byte)(startBlockAddress&0x00FF) ,(byte)((startBlockAddress&0xFF00)>>8), (byte)blocksNumber};
				}
				else{
					flag &= ~0x20;
					readMultipleBlocksRequest = new byte[] { (byte) flag, (byte) commandCode, (byte)(startBlockAddress&0x00FF) ,(byte)((startBlockAddress&0xFF00)>>8), (byte)blocksNumber};
				}
				myTag.setLastRequest(readMultipleBlocksRequest);
				response = SendCommand(myTag.getCurrentTag(), readMultipleBlocksRequest);
				myTag.setLastResponse(response);
			}
			else{															//If address overragne
				byte[] errorFrame = new byte[2];
				errorFrame[0] = 0x01;
				errorFrame[1] = 0x10;
				myTag.setLastResponse(errorFrame);
			}
		}
		else{																//If address is 1 byte
			if(startBlockAddress<=0x00FF){									//If address is in range
				if(myTag.getAddressedModeFlag()){
					flag |= 0x20;
					readMultipleBlocksRequest = new byte[] { (byte) flag, (byte) commandCode, (byte) myTag.getUid()[7],(byte) myTag.getUid()[6],(byte) myTag.getUid()[5],(byte) myTag.getUid()[4],(byte) myTag.getUid()[3],(byte) myTag.getUid()[2],(byte) myTag.getUid()[1],(byte) myTag.getUid()[0], (byte)(startBlockAddress&0x00FF), (byte)blocksNumber};
				}
				else{
					flag &= ~0x20;
					readMultipleBlocksRequest = new byte[] { (byte) flag, (byte) commandCode, (byte)(startBlockAddress&0x00FF), (byte)blocksNumber};
				}
				myTag.setLastRequest(readMultipleBlocksRequest);
				response = SendCommand(myTag.getCurrentTag(), readMultipleBlocksRequest);
				myTag.setLastResponse(response);
			}
			else{															//If address overragne
				byte[] errorFrame = new byte[2];
				errorFrame[0] = 0x01;
				errorFrame[1] = 0x10;
				myTag.setLastResponse(errorFrame);
			}
		}		
		return myTag;
	}
	
	/***********************************************************************/
	/* The function send an several times ReadMultipleBlock command frame (0x22, 0x23, UID, 1 or 2 bytes blockAddress)
	 * Request flag (0x22) - 0b00100010 (ISO/IEC 15693-3): 
	 * 	[0]=0 A single sub-carrier frequency shall be used 
	 * 	[1]=1 High data rate shall be used
	 * 	[2]=0 Inventory flag (def 4-7 bits flag)
	 * 	[3]=0 No protocol format extension
	 * 	[4]=0 Select_flag
	 * 	[5]=1 Address_flag
	 * 	[6]=0 Option flag
	 * 	[7]=0 RFU  
	 * Read Multiple Block command (0x23),
	 * UID (8 bytes)
	 * Start block address (2 bytes)
	 * Number of blocks to read - in practice is unlimited (limited by maximum posivite number (2^31 - 1 = 2147483647) of integer types)
	 * 
	 * The argument myTag is the intent triggered with the TECH_DISCOVERED
	 */ 
	/***********************************************************************/
	public static MyTag SendSeveralReadMultipleBlocksCommand(MyTag myTag, short startBlockAddress, short blocksNumber){
		
		final int pageSize = 0x20;																	//Zak adamy podzia  pami ci chipu na 32 blokowe strony (wymagane dla M24LR64, nie wymagane dla ICode)
		int i = 0;																					//Indeks odczytanych bajt w
		boolean skipFlagByte = true;																//Flaga pomini cia bajtu Flag
		
		int intStartBlockAddress = startBlockAddress & 0x0000FFFF;									//Zmienna przechowuj c  kopie pocz tkowego adresu bez znaku
		int intBlocksNumber = (blocksNumber & 0x0000FFFF) + 1;										//Zmienna przechowuj ca kopie liczby blok w do odczytu bez znaku (+1 bo 0 oznacza odczyt jednego bloku)
		
		byte[] readBuffer = new byte[intBlocksNumber*myTag.getBlockSize()];							//Bufor odczytanych danych		
		final int maxBlocksNumberInStartPage = pageSize - intStartBlockAddress%pageSize;			//Maksymalna liczba blok w, kt re mo na odczyta  z pierwszej strony pami ci
		
		if(maxBlocksNumberInStartPage<intBlocksNumber){												//Je eli maksymalna liczba blok w jak  mo na odczyta  ze startowej strony jest mniejsza ni  ca kowita liczba blok w do odczytu to nale y podzieli  sewekcje odczytu
			//Odczyt poczatkowej strony pamieci
			myTag = SendReadMultipleBlocksCommand(myTag, (short)(startBlockAddress), (byte)((maxBlocksNumberInStartPage-1)&0x000000FF));	//Odczyt mo liwie du ej ilosci blok w ze startowej strony pami ci (Pojedyncza komenda Read Multiple blocks (32 bloki))
			if(myTag.getLastResponse()[0]==0x00){													//Je eli operacja sie powiod a
				skipFlagByte = true;																//Zerowanie indeksu odczytanych bajt w i ustawienie flagi pomini cia pierwszego bajtu (bajtu odczytanych flag)
				for(byte b: myTag.getLastResponse()){												//Kopiuje zawartosc odpowiedzi
					if(skipFlagByte==true){ skipFlagByte=false; continue; }							//Omini cie bitu flagi
																									//Doda  ewentualne omini cie lub obs ug  bajtow Block Security Status
					readBuffer[i++] = b; 															//Kopiowanie pozosta ej zawarto ci
				}	
			}
			
			//Ustalenie adres w i liczby blok w
			int nextPageStartAddress = intStartBlockAddress + maxBlocksNumberInStartPage;			//Adres poczatkowy kolejnej strony pamieci
			int blocksToRead = intBlocksNumber - maxBlocksNumberInStartPage;						//Ilo   blok w pozosta ych do odczytu
			int blocksToReadFromPage;																//Deklaracja zmiennej przechowuj cej ilo   blok w do odczytu w danej stronie

			//Odczyt pozosta ych stron pami ci
			do{
				if(blocksToRead>pageSize) 
					blocksToReadFromPage = pageSize;
				else 
					blocksToReadFromPage = blocksToRead;
				
				myTag = SendReadMultipleBlocksCommand(myTag, (short)(nextPageStartAddress&0x0000FFFF), (byte)((blocksToReadFromPage-1)&0x000000FF));	//Odczyt mo liwie du ej ilosci blok w ze startowej strony pami ci (Pojedyncza komenda Read Multiple blocks (32 bloki))
				if(myTag.getLastResponse()[0]==0x00){												//Je eli operacja sie powiod a
					skipFlagByte = true;															//Ustawiany jest adres w buforze odczytu dla danej paczki blok w oraz flaga pomini cia pierwszego bajtu (bajtu odczytanych flag)
					for(byte b: myTag.getLastResponse()){											//Kopiuje zawartosc odpowiedzi
						if(skipFlagByte==true){ skipFlagByte=false; continue; }						//Omini cie bitu flagi
																									//Doda  ewentualne omini cie lub obs ug  bajtow Block Security Status
						readBuffer[i++] = b; 														//Kopiowanie pozosta ej zawarto ci
					}	
				}
				nextPageStartAddress+=pageSize;														//Zwi ksza zmienn  przechowuj c  adres pocz tkowy kolejnej strony pami ci
				blocksToRead-=pageSize;																//Zmniejsza zmienn  przechowuj c  ilo   blok w pozosta ych do odczytu
			}while(blocksToRead>0);																	//P tla jest wykonywana do chwili w kt rej nie ma ju  blok w do odczytu
		}
		else{																						//Je eli maksymalna liczba blok w jak  mo na odczyta  ze startowej strony jest wi ksza ni  ca kiwita liczba blok w do odczytu to nie ma potrzeby dzielenia sekwencji odczytu na cz ci i mo na jednorazowo odczyta  ca y ci g
			//Odczyt pocz tkowej i zarazem jedynej strony pamieci
			myTag = SendReadMultipleBlocksCommand(myTag, (short)(startBlockAddress), (byte)(blocksNumber&0x00FF));	//Odczyt blok w ze startowej i zarazem jedynej strony pami ci (Pojedyncza komenda Read Multiple blocks (32 bloki))
			if(myTag.getLastResponse()[0]==0x00){													//Je eli operacja sie powiod a
				skipFlagByte = true;																//Zerowanie indeksu odczytanych bajt w i ustawienie flagi pomini cia pierwszego bajtu (bajtu odczytanych flag)
				for(byte b: myTag.getLastResponse()){												//Kopiuje zawartosc odpowiedzi
					if(skipFlagByte==true){ skipFlagByte=false; continue; }							//Omini cie bitu flagi
																									//Doda  ewentualne omini cie lub obs ug  bajtow Block Security Status
					readBuffer[i++] = b; 															//Kopiowanie pozosta ej zawarto ci
				}	
			}
		}
		
//		//Wersja bez podzia u na 32 blokowe strony (nie dzia a z M24LR64E)
//		byte[] readBuffer = new byte[(blocksNumber+1)*myTag.getBlockSize()];
//		int fullReadNumber = blocksNumber/0x20;
//		int restBlocks = blocksNumber%0x20;
//		int i=0, j=1, n = 0;
//		for(n = 0; n<fullReadNumber; n++){														//Odczyt n pe nych blok w
//			myTag = SendReadMultipleBlocksCommand(myTag, (short)(0x0020*n+startBlockAddress), (byte)(0x20-1));	//Pojedyncza komenda Read Multiple blocks (32 bloki)
//			if(myTag.getLastResponse()[0]==0x00){												//Je eli operacja sie powiod a
//				i = 0x20*n*4; j = 1;															//Ustawiany jest adres w buforze odczytu dla danej paczki blok w oraz flaga pomini cia pierwszego bajtu (bajtu odczytanych flag)
//				for(byte b: myTag.getLastResponse()){											//Kopiuje zawartosc odpowiedzi
//					if(j==1){ j=0; continue; }													//Omini cie bitu flagi
//					readBuffer[i++] = b; 														//Kopiowanie pozosta ej zawarto ci
//				}									
//			}
//		}
//		if(restBlocks!=0){																		//Odczyt pozosta ej reszty blokow
//			myTag = SendReadMultipleBlocksCommand(myTag, (short)(0x0020*n+startBlockAddress), (byte)(restBlocks-1));	//Pojedyncza komenda Read Multiple blocks (reszta blokow)
//			if(myTag.getLastResponse()[0]==0x00){												//Je eli operacja sie powiod a
//				i = 0x20*n*4; j = 1;															//Ustawiany jest adres w buforze odczytu dla danej paczki blok w oraz flaga pomini cia pierwszego bajtu (bajtu odczytanych flag)
//				for(byte b: myTag.getLastResponse()){											//Kopiuje zawartosc odpowiedzi
//					if(j==1){ j=0; continue; }													//Omini cie bitu flagi
//					readBuffer[i++] = b; 														//Kopiowanie pozosta ej zawarto ci
//				}									
//			}	
//		}			
		
		myTag.setReadedTagMemory(readBuffer);														//Kopiuje zawarto   bufora do odpowiedniego pola obiektu klasy MyTag
		return myTag;																				//Zwraca obiekt klasy MyTag z odczytan  pami ci  chipu
	}
	
	
	/***********************************************************************/
	/*Funkcje pomocnicze*/
	/***********************************************************************/
	public static byte ConvertBitesToBytes(int bit7,int bit6,int bit5,int bit4,int bit3,int bit2,int bit1,int bit0){
		return (byte)(bit7<<7|bit6<<6|bit5<<5|bit4<<4|bit3<<3|bit2<<2|bit1<<1|bit0);
	}		
}
