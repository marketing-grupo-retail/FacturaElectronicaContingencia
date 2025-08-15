/*
 * Created on 8/09/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.grpretail.utils;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class NumberFormatter {
	//int contador = 0;
	//String mensaje = "";
	//int verifica = 0;
	private static char MILES_SEPARATOR	= '.';
	private static char DECIMAL_SYMBOL	= ',';
	private static char CURRENCY_SYMBOL = '$';
	private static int numDecimales = 2;
	private static DecimalFormat decimalFormat;
	

	/*
	public static byte[] pack(String pValue) {
		byte[] array_ = new byte[Math.round((float)pValue.length()/2)];   
 
		// Verifica si es par o impar el dato
		if (pValue.length()%2 == 1) pValue = (char)0 + pValue;

		for (int h=0,g=0;h < pValue.length();h+=2,g++) {
			array_[g]=(byte)(((pValue.charAt(h) & 0x0F) <<4) + (pValue.charAt(h+1) & 0x0F));
		}
		return array_;
	}
	

	public static char[] packChar(String pValue) {
		char[] array_ = new char[Math.round((float)pValue.length()/2)];   
 
		// Verifica si es par o impar el dato
		if (pValue.length()%2 == 1) pValue = (char)0 + pValue;
		System.out.print("char array: ");
		for (int h=0,g=0;h < pValue.length();h+=2,g++) {
			array_[g]=(char)(((pValue.charAt(h) & 0x0F) <<4) + (pValue.charAt(h+1) & 0x0F));
			System.out.print((int)array_[g] + " ");
		}
		System.out.println();
		return array_;
	}	
	public static int[] packInt(String pValue) {
		int[] array_ = new int[Math.round((float)pValue.length()/2)];   
 
		// Verifica si es par o impar el dato
		if (pValue.length()%2 == 1) pValue = (char)0 + pValue;
		System.out.print("int array; ");
		for (int h=0,g=0;h < pValue.length();h+=2,g++) {
			array_[g]=(int)(((pValue.charAt(h) & 0x0F) <<4) + (pValue.charAt(h+1) & 0x0F));
			System.out.print(array_[g] + " ");
		}
		System.out.println();
		return array_;
	}

	public static long[] packLong(String pValue) {
		long[] array_ = new long[Math.round((float)pValue.length()/2)];   
 
		// Verifica si es par o impar el dato
		if (pValue.length()%2 == 1) pValue = (char)0 + pValue;
		System.out.print("long array: ");
		for (int h=0,g=0;h < pValue.length();h+=2,g++) {
			array_[g]=(long)(((pValue.charAt(h) & 0x0F) <<4) + (pValue.charAt(h+1) & 0x0F));
			System.out.print(array_[g] + " ");
		}
		System.out.println();
		return array_;
	}

	public static String packToBinary(long pValue) {
		StringBuffer strBuff_ = new StringBuffer();
		for (int bitCounter_ = 31; bitCounter_ >= 0; bitCounter_--) {
			String str_ = null;
			if (((1 << bitCounter_) & pValue) != 0) {
				str_ = "1";
			} else {
				str_ = "0";
			}
			strBuff_.append(str_);
		}
		return strBuff_.toString();
	}
	
	public static long binaryToLong(String pValue) {
		long answer_ = 0;
		for (int i = 0; i < pValue.length(); i++) {
			String str_ = pValue.substring(i, i + 1);
			if (!str_.equals("1"))
				str_ = "0";
			answer_ += (long) Math.pow(2, pValue.length() - i - 1) * Integer.parseInt(str_);

		}
		return answer_;
	}
	
	public static long packbinaryToLong(String pValue) {
		return packlongToLong(binaryToLong(pValue));
	}
	
	public static long unPack(long pValue) {
		return packbinaryToLong(
			packToBinary(pValue)
		);
	}
	
	public static long unPack(long[] pValue) {
		long answer_ = 0;
		for (int i = 0 ; i < pValue.length ; i++) {                     
			long parcial_ = unPack(pValue[i]);  
			answer_ += (long)Math.pow(10,2*(pValue.length - i - 1)) * parcial_;

		}
		return answer_;
	}

	public static long unPack(int[] pValue) {
		long answer_ = 0;
		for (int i = 0 ; i < pValue.length ; i++) {                     
			long parcial_ = unPack(pValue[i]);  
			answer_ += (long)Math.pow(10,2*(pValue.length - i - 1)) * parcial_;

		}
		return answer_;
	}

	public static long unPack(byte[] pValue) {
		long answer_ = 0;
		for (int i = 0 ; i < pValue.length ; i++) {                     
			long parcial_ = unPack(pValue[i]);  
			answer_ += (long)Math.pow(10,2*(pValue.length - i - 1)) * parcial_;

		}
		return answer_;
	}

	public static long unPack(char[] pValue) {
		long answer_ = 0;
		for (int i = 0 ; i < pValue.length ; i++) {                     
			long parcial_ = unPack(pValue[i]);  
			answer_ += (long)Math.pow(10,2*(pValue.length - i - 1)) * parcial_;

		}
		return answer_;
	}
	
	public static long unPack(String pValue) {
		return unPack(pValue.toCharArray());
	}


	public static long packlongToLong(long pValue) {
		if ((pValue >= 16) && (pValue <= 25))
			pValue = pValue - 6;
		else if ((pValue >= 26) && (pValue <= 41))
			pValue = pValue - 12;
		else if ((pValue >= 48) && (pValue <= 57))
			pValue = pValue - 18;
		else if ((pValue >= 64) && (pValue <= 73))
			pValue = pValue - 24;
		else if ((pValue >= 80) && (pValue <= 89))
			pValue = pValue - 30;
		else if ((pValue >= 96) && (pValue <= 105))
			pValue = pValue - 36;
		else if ((pValue >= 112) && (pValue <= 121))
			pValue = pValue - 42;
		else if ((pValue >= 122) && (pValue <= 143))
			pValue = pValue - 48;
		else if ((pValue >= 144))
			pValue = pValue - 54;

		else if (pValue == -128)
			pValue = 80;
		else if (pValue == -127)
			pValue = 81;
		else if (pValue == -126)
			pValue = 82;
		else if (pValue == -125)
			pValue = 83;
		else if (pValue == -124)
			pValue = 84;
		else if (pValue == -123)
			pValue = 85;
		else if (pValue == -122)
			pValue = 86;
		else if (pValue == -121)
			pValue = 87;
		else if (pValue == -120)
			pValue = 88;
		else if (pValue == -119)
			pValue = 89;
		else if (pValue == -112)
			pValue = 90;
		else if (pValue == -111)
			pValue = 91;
		else if (pValue == -110)
			pValue = 92;
		else if (pValue == -109)
			pValue = 93;
		else if (pValue == -108)
			pValue = 94;
		else if (pValue == -107)
			pValue = 95;
		else if (pValue == -106)
			pValue = 96;
		else if (pValue == -105)
			pValue = 97;
		else if (pValue == -104)
			pValue = 98;
		else if (pValue == -103)
			pValue = 99;

		return pValue;

	}
	*/

	public static String maquilla(String cadena,int pNumDecCalc,int pNumDecForm) {
		cadena = String.valueOf(Long.valueOf(cadena.trim()).longValue());
		String strDec_;
		if (pNumDecCalc > 0) {
			strDec_ = cadena.substring(Math.max(0,cadena.length() - pNumDecCalc),cadena.length());
			int longDec_ = strDec_.length();
			cadena = cadena.equals("") ? "" : cadena.substring(0,Math.max(1,cadena.length() - pNumDecCalc));
			if (pNumDecForm > 0) {
				strDec_ = DECIMAL_SYMBOL + StringFormatter.align(String.valueOf(Math.round(Integer.parseInt(strDec_) * Math.pow(10,pNumDecForm - longDec_ -1))),
					pNumDecForm-1,'0',1,pNumDecForm-1);
			} else {
				int ajuste_ = (int)Math.round(Integer.parseInt(strDec_) * Math.pow(10,0 - longDec_));
				strDec_ = "";
				cadena = String.valueOf(Integer.parseInt(cadena) + ajuste_);
			}
			
		} else {
			strDec_ = "";
		}
		//contador = 0;
		int counter_ = 0;
		//System.out.println("Entro a formatear la siguiente cadena "+ cadena);

		if (cadena.length() >= 3) {
			//System.out.println("Maquilla Longitud "+cadena.length());

			for (int i = cadena.length(); i >= 0; i--) {
				//contador = contador + 1;
				counter_++;
				//System.out.println(" * "+contador);
				if ((counter_ % 4) == 0) {
					//System.out.println(cadena.substring(0,i));
					//System.out.println(cadena.substring(i,cadena.length()));  
					//System.out.println("  ");
					cadena = cadena.substring(0, i) + MILES_SEPARATOR + cadena.substring(i, cadena.length());
					//cadena=cadena.substring(0,i)+cadena.substring(i,cadena.length());
					i = i + 1;
					//System.out.println(cadena);

				}
			}

		}
		//mensaje = cadena;
		if (cadena.charAt(0) == MILES_SEPARATOR)
			cadena = cadena.substring(1, cadena.length());
		return (cadena + strDec_);

	}
	
	public static String eliminarChar(String pStr,char pChar) {
		int position_ = pStr.indexOf(String.valueOf(pChar));
		while (position_ != -1) {
			pStr = pStr.substring(0,position_) + ((position_ < (pStr.length() - 1)) ?
				 pStr.substring(position_ + 1,pStr.length()) : "");
			position_ = pStr.indexOf(String.valueOf(pChar));
		}
		return pStr;
	}

	public static String desmaquilla(String cadena) {
		return eliminarChar(cadena,MILES_SEPARATOR).replace(DECIMAL_SYMBOL,'.');

		/*		
		contador = 0;
		//System.out.println("Entro a desformatear la siguiente cadena "+ cadena);

		if (cadena.length() >= 3) {
			//System.out.println("Desmaquilla Longitud "+cadena.length());

			verifica = cadena.indexOf(".");
			//System.out.println("Verifica "+ verifica);

			if (verifica >= 0) {
				for (int i = cadena.length(); i >= 0; i--) {
					contador = contador + 1;
					//System.out.println(" * "+contador);
					if ((contador % 4 == 0) && (cadena.length() > contador)) {

						//System.out.println(cadena.substring(0,i-1));
						//System.out.println(cadena.substring(i,cadena.length()));  
						//System.out.println("  ");
						//cadena=cadena.substring(0,i)+cadena.substring(i,cadena.length());
						cadena = cadena.substring(0, i - 1) + cadena.substring(i, cadena.length());

						//System.out.println(cadena);

					}
				}
			}

		}
		mensaje = cadena;
		if (mensaje.charAt(0) == '.')
			mensaje = mensaje.substring(1, mensaje.length());
		//System.out.println("OK ");
		if (mensaje.length() == 9) {
			if (mensaje.charAt(1) == '.')
				mensaje = mensaje.substring(0, 1) + mensaje.substring(2, mensaje.length());
		}
		if (mensaje.length() == 8) {
			if (mensaje.charAt(1) == '.')
				mensaje = mensaje.substring(0, 1) + mensaje.substring(2, mensaje.length());
		}
		if (mensaje.length() == 10) {
			if (mensaje.charAt(1) == '.')
				mensaje = mensaje.substring(0, 1) + mensaje.substring(2, mensaje.length());
		}
		//System.out.println("OK2 ");
		return (mensaje);
		*/
	}
	

	public static String ignoreNoDigits(String pStr) {
		StringBuffer strB_ = new StringBuffer("");
		for (int i = 0; i < pStr.length(); i++) {
			char parcialChar_ = pStr.charAt(i);
			if (Character.isDigit(parcialChar_)) {
				strB_.append(parcialChar_);
			}
		}
		return strB_.toString();
	}
	
	public static String formatoMoneda(double pValor) {
		return formatoMoneda(pValor,CURRENCY_SYMBOL);
	}

	public static String formatoMoneda(double pValor,char pCurrencySymbol) {
		return pCurrencySymbol + formatoNumero(pValor);
	}


	public static String formatoMoneda(double pValor,String pFormato,char pCurrencySymbol) {
		return pCurrencySymbol + formatoNumero(pValor,pFormato);
	}

	public static String formatoMoneda(double pValor,String pFormato) {
		return formatoMoneda(pValor,pFormato,CURRENCY_SYMBOL);
	}

	public static String formatoNumero(double pValor) {
		return getDecimalFormat().format(pValor);
	}

	public static String formatoNumero(double pValor,String pFormato) {
		String answer_ = null;
		try  {
			DecimalFormatSymbols dfs_ = new DecimalFormatSymbols();
			dfs_.setDecimalSeparator(DECIMAL_SYMBOL);
			dfs_.setGroupingSeparator(MILES_SEPARATOR);

			DecimalFormat df_ = new DecimalFormat(pFormato);
			df_.setDecimalFormatSymbols(dfs_);
			
			answer_ = df_.format(pValor);
		} catch (Exception ex) {
			answer_ = getDecimalFormat().format(pValor);
		}
			
		return answer_;
	}
	
	public static String formatoNumero(double pValor,String pFormato,char pMiles,char pDecimal) {
		char orgMiles_ = MILES_SEPARATOR;
		char orgDecimal_ = DECIMAL_SYMBOL;
		setMILES_SEPARATOR(pMiles);
		setDECIMAL_SYMBOL(pDecimal);
		String answer_ = formatoNumero(pValor,pFormato);
		setDECIMAL_SYMBOL(orgDecimal_);
		setMILES_SEPARATOR(orgMiles_);
		return answer_;
	}

	private static void generateDf() {
		DecimalFormatSymbols dfs_ = new DecimalFormatSymbols();
		dfs_.setDecimalSeparator(DECIMAL_SYMBOL);
		dfs_.setGroupingSeparator(MILES_SEPARATOR);
		if (decimalFormat == null) {
			String format_ = "#,###" + (numDecimales > 0 ? 
				("." + StringFormatter.align("0",numDecimales,'0',0,numDecimales)) : "");
			decimalFormat = new DecimalFormat(format_);
		}
		decimalFormat.setDecimalFormatSymbols(dfs_);
	}
	
	public static String ajusteDecimales(String pCifra,int pNumDecimalesOrigen,int pNumDecimalesDestino,char pDecimalChar) {
		pCifra = eliminarChar(pCifra,'.');
		try {
			String formato_ = "0" + 
				(pNumDecimalesDestino > 0 ? "." : "") + 
				 StringFormatter.align("0",pNumDecimalesDestino,'0',1,pNumDecimalesDestino);
			String strValorOrigen_ = pCifra.substring(0,pCifra.length() - pNumDecimalesOrigen) + 
				"." + 
				pCifra.substring(pCifra.length() - pNumDecimalesOrigen,pCifra.length());
			double dobValorOrigen_ = Double.valueOf(strValorOrigen_).doubleValue();
			String strValorDestino_ = formatoNumero(dobValorOrigen_,formato_);
			
			if (pDecimalChar == 0) {
				return eliminarChar(strValorDestino_,DECIMAL_SYMBOL);
			} else {
				return strValorDestino_.replace(DECIMAL_SYMBOL,pDecimalChar);
			}
		} catch (Exception ex) {
			return "0";
		}
	}
	
	/**
	 * Gets the mILES_SEPARATOR
	 * @return Returns a char
	 */
	public static char getMILES_SEPARATOR() {
		return MILES_SEPARATOR;
	}
	/**
	 * Sets the mILES_SEPARATOR
	 * @param mILES_SEPARATOR The mILES_SEPARATOR to set
	 */
	public static void setMILES_SEPARATOR(char pMILES_SEPARATOR) {
		MILES_SEPARATOR = pMILES_SEPARATOR;
		generateDf();
	}


	/**
	 * Gets the dECIMAL_SYMBOL
	 * @return Returns a char
	 */
	public static char getDECIMAL_SYMBOL() {
		return DECIMAL_SYMBOL;
	}
	/**
	 * Sets the dECIMAL_SYMBOL
	 * @param dECIMAL_SYMBOL The dECIMAL_SYMBOL to set
	 */
	public static void setDECIMAL_SYMBOL(char pDECIMAL_SYMBOL) {
		DECIMAL_SYMBOL = pDECIMAL_SYMBOL;
		generateDf();
	}


	/**
	 * Gets the cURRENCY_SYMBOL
	 * @return Returns a char
	 */
	public static char getCURRENCY_SYMBOL() {
		return CURRENCY_SYMBOL;
	}
	/**
	 * Sets the cURRENCY_SYMBOL
	 * @param cURRENCY_SYMBOL The cURRENCY_SYMBOL to set
	 */
	public static void setCURRENCY_SYMBOL(char pCURRENCY_SYMBOL) {
		CURRENCY_SYMBOL = pCURRENCY_SYMBOL;
	}


	/**
	 * Gets the numDecimales
	 * @return Returns a int
	 */
	public static int getNumDecimales() {
		return numDecimales;
	}
	/**
	 * Sets the numDecimales
	 * @param numDecimales The numDecimales to set
	 */
	public static void setNumDecimales(int pNumDecimales) {
		numDecimales = pNumDecimales;
		decimalFormat = null;
		generateDf();
	}


	/**
	 * Gets the decimalFormat
	 * @return Returns a DecimalFormat
	 */
	private static DecimalFormat getDecimalFormat() {
		if (decimalFormat == null) {
			generateDf();
		}
		return decimalFormat;
	}
	public static byte[] myPack(String pValue) {
		int longitud_ = pValue.length();
		byte[] byteArray_ = new byte[Math.round((float)longitud_/2)];
		for (int i = 0 ; i < byteArray_.length ; i++) {
			String str_ = pValue.substring(Math.max(0,longitud_ - i*2 - 2),longitud_ - i*2);
			System.out.println("Conviertiendo a byte *"  + str_ + "*");
			byteArray_[byteArray_.length - i - 1] = Byte.valueOf(str_).byteValue();
		}
		return byteArray_;
	}
	
	public static String myUnpack(byte[] pValue) {
		StringBuffer answer_ = new StringBuffer();
		for (int i = 0 ; i < pValue.length ; i++) {
			String str_ = StringFormatter.align(
				String.valueOf(pValue[i]),
				2,
				'0',
				0,
				2
			);
			answer_.append(str_);
		} 
		return answer_.toString();
	}
	
	public static String myUnpack(String pValue) {
		return myUnpack(pValue.getBytes());
	}

	public static int intValue(String pValue){
		double doub_ = 0;
		try{
			doub_ = Double.valueOf(pValue).doubleValue();
		}catch(Exception e){
		}
		return (int)Math.round(doub_);
	}
	
	public static float floatValue(String pValue){
		float float_ = 0;
		try{
			float_ = Float.valueOf(pValue).floatValue();
		}catch(Exception e){
		}
		return float_;

	}
	
}