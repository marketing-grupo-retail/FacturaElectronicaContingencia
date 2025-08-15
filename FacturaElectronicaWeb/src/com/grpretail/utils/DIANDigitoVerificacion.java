package com.grpretail.utils;


public class DIANDigitoVerificacion {

	
	public int calculaDigitoVerificacionDian(String pId){
		int[] vpri = new int[16];
		int x, y;
		int z;
		
		// Se limpia el Nit
		pId = pId.replaceFirst("^0+(?!$)", ""); //ceros a la izquierda
		pId = pId.replaceAll("\\s+","") ; // Espacios
		pId = pId.replaceAll ("[,.-]","") ; // Comas, puntos guiones

		// Se valida el nit
		try {
			long d = Long.valueOf(pId);
			System.out.println(d);
		} catch (Exception e) {
			System.out.println("El nit/c�dula '" + pId + "' no es v�lido(a).") ;
			return -1;
		}

		// Procedimiento
		z = pId.length() ;

		vpri[1]  =  3 ;
		vpri[2]  =  7 ;
		vpri[3]  = 13 ; 
		vpri[4]  = 17 ;
		vpri[5]  = 19 ;
		vpri[6]  = 23 ;
		vpri[7]  = 29 ;
		vpri[8]  = 37 ;
		vpri[9]  = 41 ;
		vpri[10] = 43 ;
		vpri[11] = 47 ;  
		vpri[12] = 53 ;  
		vpri[13] = 59 ; 
		vpri[14] = 67 ; 
		vpri[15] = 71 ;

		x = 0 ;
		y = 0 ;
		for  ( int i = 0; i < z; i++ )  { 
		  y = Integer.parseInt( pId.substring(i, i+1 )) ;
		  // console.log ( y + "x" + vpri[z-i] + ":" ) ;

		  x += ( y * vpri [z-i] ) ;
		  // console.log ( x ) ;    
		}

		y = x % 11 ;
		// console.log ( y ) ;

	return ( y > 1 ) ? 11 - y : y ;
	}
	
	public static String formatDianDigito(String pId) {
		int digit = new DIANDigitoVerificacion().calculaDigitoVerificacionDian(pId);
	    return pId.concat(String.valueOf(digit));
	}
	
	public static int getDianDigito(String pId) {
		return new DIANDigitoVerificacion().calculaDigitoVerificacionDian(pId);
	}
}
