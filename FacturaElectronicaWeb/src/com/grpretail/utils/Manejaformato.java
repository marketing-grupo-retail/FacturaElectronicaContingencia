package com.grpretail.utils;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Manejaformato {
	//int contador = 0;
	//String mensaje = "";
	//int verifica = 0;
	private static char MILES_SEPARATOR	= '.';
	private static char DECIMAL_SYMBOL	= ',';
	private static char CURRENCY_SYMBOL = '$';
	private static int numDecimales = 2;
	private static DecimalFormat decimalFormat;
	

	public Manejaformato() {
	}

	public static String maquilla(String cadena,int pNumDecCalc,int pNumDecForm) {
		cadena = String.valueOf(Long.valueOf(cadena.trim()).longValue());
		String strDec_;
		if (pNumDecCalc > 0) {
			strDec_ = cadena.substring(Math.max(0,cadena.length() - pNumDecCalc),cadena.length());
			int longDec_ = strDec_.length();
			cadena = cadena.equals("") ? "" : cadena.substring(0,Math.max(1,cadena.length() - pNumDecCalc));
			if (pNumDecForm > 0) {
				strDec_ = DECIMAL_SYMBOL + Relleno.carga(String.valueOf(Math.round(Integer.parseInt(strDec_) * Math.pow(10,pNumDecForm - longDec_ -1))),
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
		return CURRENCY_SYMBOL + formatoNumero(pValor);
	}

	public static String formatoMoneda(double pValor,String pFormato) {
		return CURRENCY_SYMBOL + formatoNumero(pValor,pFormato);
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

	private static void generateDf() {
		DecimalFormatSymbols dfs_ = new DecimalFormatSymbols();
		dfs_.setDecimalSeparator(DECIMAL_SYMBOL);
		dfs_.setGroupingSeparator(MILES_SEPARATOR);
		if (decimalFormat == null) {
			String format_ = "#,###" + (numDecimales > 0 ? 
				("." + Relleno.carga("0",numDecimales,'0',0,numDecimales)) : "");
			decimalFormat = new DecimalFormat(format_);
		}
		decimalFormat.setDecimalFormatSymbols(dfs_);
	}

	public static String ajusteDecimales(String pCifra,int pNumDecimalesOrigen,int pNumDecimalesDestino,char pDecimalChar) {
		pCifra = eliminarChar(pCifra,'.');
		try {
			String formato_ = "0" + 
				(pNumDecimalesDestino > 0 ? "." : "") + 
				 Relleno.carga("0",pNumDecimalesDestino,'0',1,pNumDecimalesDestino);
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
}