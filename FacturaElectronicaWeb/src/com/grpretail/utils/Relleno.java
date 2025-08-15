package com.grpretail.utils;
/**
 * Inserte aquí la descripción del tipo.
 * Fecha de creación: (10/10/2002 2:09:04 PM)
 * @author: Administrator
 */
public class Relleno {
	/**
	 * Comentario de constructor Relleno.
	 */

	public static String carga(
		String despl,
		int offset,
		char caracter,
		int pos,
		int tamano) {
			
		if (despl == null) {
			despl = "";
		}
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
		return str_.toString().substring(0, Math.min(offset, tamano));
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
}