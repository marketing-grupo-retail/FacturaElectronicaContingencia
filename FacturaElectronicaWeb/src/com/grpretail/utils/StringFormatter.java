/*
 * Created on 8/09/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.grpretail.utils;
/**
 * Inserte aquí la descripción del tipo.
 * Fecha de creación: (10/10/2002 2:09:04 PM)
 * @author: Administrator
 */
public class StringFormatter {
	/**
	 * Comentario de constructor Relleno.
	 */

	public static String align(
		String despl,
		int offset,
		char caracter,
		int pos,
		int tamano) {
			
		if (despl == null)
			despl = "";
		else 
			despl = despl.trim();

		int longitud = despl.length();
		int relleno = offset - longitud;
		String rta = "";

		StringBuffer str_ = new StringBuffer(((despl == null) ? "" : despl));

		switch (pos) {
			case 0 : // Alineación a la derecha
				for (int i = 0; i < relleno; i++) {
					str_.insert(0, caracter);
				}
				break;

			case 1 : // Alineación a la izquierda
				for (int i = 0; i < relleno; i++) {
					str_.append(caracter);
				}
				break;

			case 2 : // Alineación al centro
				int midRelleno_ = relleno / 2;
				for (int i = 0; i < midRelleno_; i++) {
					str_.insert(0, caracter);
				}
				for (int i = 0; i < relleno - midRelleno_; i++) {
					str_.append(caracter);
				}
				break;
		}
		return str_.toString().substring(0, Math.max(0,Math.min(offset, tamano)));
		/* 
			if (pos==1)
			{
					 for(int i=1; i<=relleno;i++)
					 {
						despl=despl+(char)caracter;
					 }
			}
			else  {
				   for(int i=1; i<=relleno;i++)
					 {
						despl=(char)caracter + despl;
					 }
		
				  }
			for(int h=0;h<tamano;h++)
			{
			  rta=rta+despl.charAt(h);
			}
		
			return(rta);
		*/
	}
	
	public static String space(int pSpaces) {
		StringBuffer str_ = new StringBuffer("");
		for (int i = 0 ; i < pSpaces ; i++) {
			str_.append(' ');
		}
		return str_.toString();
	}
	
	public static String alignIzqDer(String pStrIzq,String pStrDer,int pAncho) {
		int longIzq_ = Math.max(1,pAncho - pStrDer.length() - 1);
		return align(
			pStrIzq,
			longIzq_,
			' ',
			1,
			longIzq_)
			+ pStrDer;

	}
	
	public static String alignIzqCenDer(String pStrIzq,String pStrCen,String pStrDer,int pAncho) {
		int longCen_ = Math.min(pAncho,pStrCen.length());
		int longIzq_ = Math.max(0,pAncho - pStrCen.length())/2;
		int longDer_ = pAncho - longIzq_ - longCen_;
		return 
			align(
				pStrIzq,
				longIzq_,
				' ',
				1,
				longIzq_) + 
			align(
				pStrCen,
				longCen_,
				' ',
				2,
				longCen_) + 
			align(
				pStrDer,
				longDer_,
				' ',
				0,
				longDer_
			);
	}
	
	
	public static String replace(String pStr1,String pStr2) {
		return replace(pStr1,pStr2,null);
	}
	
	public static String replace(String pStr1,String pStr2,String pStr3) {
		pStr3 = pStr3 == null ? "" : pStr3;
		int pos1_ = -1;
		while ((pos1_ = pStr1.indexOf(pStr2)) > -1){
			pStr1 = pStr1.substring(0,pos1_) + 
			pStr3 + 
			pStr1.substring(pos1_ + pStr2.length());
		}
		return pStr1;
	}
	
	/**
	 * Calcula el número de ocurrencias de una cadena dentro de otra
	 */
	public static int occurs(String pStr1,String pStr2) {
		int answer_ = 0;
		int index_ = 0;
		while ((index_ = pStr1.indexOf(pStr2,index_)) > -1) {
			System.out.println(index_);
			answer_++;
			index_++;
		}
		return answer_;
	}

	public static String readStringToChar(String pStr,int pPos1,int pLen) {
		StringBuffer str_ = new StringBuffer();
		for (int i = pPos1 ; i < pPos1 + pLen ; i++) {
			char charParcial_ = pStr.charAt(i);
			if (charParcial_ == 13 || charParcial_ == '=') {
				str_.append('=');
			} else {
				str_.append((int)charParcial_);
			}
		}
		return str_.toString();		
	}

	public static String readStringToChar1(String pStr,int pPos1,int pLen) {
		StringBuffer str_ = new StringBuffer();
		for (int i = pPos1 ; i < pPos1 + pLen ; i++) {
			char charParcial_ = (char)(pStr.charAt(i) + 32);
			str_.append(charParcial_);
		}
		return str_.toString();		
	}
	
	public static String rtrim(String pStr){
		int ubic_ = pStr.length();
		for (int i = pStr.length() - 1 ; i >= 0 && pStr.charAt(i) == 32; i--){
			ubic_--;
		}
		return pStr.substring(0,ubic_);
	}

	public static String LRemplace(String s,char c){
		while(s.charAt(0) == c){
		  s = s.substring(1);
		}
		return s;
	  }
	
	public static String LRemplace(String s){
		while(s.charAt(0) == '0'){
		  s = s.substring(1);
		}
		return s;
	  }	

	public static String RRemplace(String s,char c){
	  int len = s.length();
	  while(s.charAt(len-1) == c){
		 s = s.substring(0,len-1);
		 len--;
	  }
	  return(s);
	}	
}