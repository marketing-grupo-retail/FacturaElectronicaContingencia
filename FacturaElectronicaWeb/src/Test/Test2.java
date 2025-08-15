package Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.grpretail.comfandi.trxonline.automaticjobs.processes.OnlineInventoryExecutor;

public class Test2 {
	/*
	static String theFileName2 = "C:/InventariosOnline/InputFiles/I999072009.DAT";
	static String theFileName1 = "C:/InventariosOnline/InputFiles/I5350718(535 18Julio2022).DAT";
	*/
	
	static String theFileName2 = "C:/InventariosOnline/InputFiles/I5140718(514 18Julio2022).DAT";
	static String theFileName1 = "C:/InventariosOnline/InputFiles/I999072110(514).DAT";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Apéndice de método generado automáticamente
		FileInputStream is_ = null;
		BufferedReader br_ = null;
		try{
			//String fileName1_ = theFileName1
			File file_ = new File(theFileName1);
			is_ = new FileInputStream(file_);
			br_ =  new BufferedReader(new InputStreamReader(is_));		
			String trama_ = br_.readLine();
			System.out.println(trama_.length());
			while (trama_ != null)  {
				System.out.println(trama_);
				if(trama_ != null && trama_.length()>=45){
					String plu_ = trama_.substring(1, 8);
					String quantity_ = trama_.substring(12, 21);
					String sign_ = trama_.substring(21, 22);
					String saleAmt_ = trama_.substring(22, 31);
					System.out.println("PLU:"+plu_+" Cantidad:"+quantity_+" Signo:"+sign_+" Monto:"+saleAmt_);
					if(!getRegisterInSecodFile(plu_, quantity_, sign_, saleAmt_))
						System.out.println("Registro no encontrado en segundo archivo");
				}
				trama_ = br_.readLine();				
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				br_.close();
			} catch (IOException e) {
				// TODO Bloque catch generado automáticamente
				e.printStackTrace();
			}
		}
	}
	
	public static boolean getRegisterInSecodFile(String pPLU, String pQuantity, String pSign, String pSaleAmt){
		boolean answer_ = false;
		FileInputStream is_ = null;
		BufferedReader br_ = null;		
		try{
			//String fileName1_ = theFileName1
			File file_ = new File(theFileName2);
			is_ = new FileInputStream(file_);
			br_ =  new BufferedReader(new InputStreamReader(is_));		
			String trama_ = br_.readLine();
			System.out.println(trama_.length());
			while (trama_ != null)  {
				System.out.println(trama_);
				if(trama_ != null && trama_.length()==45){
					String plu_ = trama_.substring(1, 8);
					String quantity_ = trama_.substring(12, 21);
					String sign_ = trama_.substring(21, 22);
					String saleAmt_ = trama_.substring(22, 31);
					System.out.println("PLU:"+plu_+" Cantidad:"+quantity_+" Signo:"+sign_+" Monto:"+saleAmt_);
					//getRegisterInSecodFile(plu_, quantity_, sign_, saleAmt_);
					if(plu_.equals(pPLU) && quantity_.equals(pQuantity) && sign_.equals(pSign) &&
					   saleAmt_.equals(saleAmt_)){
						System.out.println("Registro encontrado");
						System.out.println("PLU:"+plu_+" Cantidad:"+quantity_+" Signo:"+sign_+" Monto:"+saleAmt_);
						if(answer_)
							System.out.println("Registro encontrado dos veces");
						answer_ = true;
					}
				}
				trama_ = br_.readLine();				
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				br_.close();
			} catch (IOException e) {
				// TODO Bloque catch generado automáticamente
				e.printStackTrace();
			}
		}
		return answer_;
	}

}
