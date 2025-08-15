/*
 * Created on 25/02/2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.grpretail.utils;

/**
 * @author ACadena
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *  */

import java.util.ArrayList;

public class ParseString {
	// Convierte una String con elementos separados por algún 
	// caracter (character en un arreglo de objetos (ArrayList). 
	public static ArrayList StringToArrayList (String cadena, String caracter){
		ArrayList itemList=new ArrayList();
		int longitud,longTemp;		
		if(cadena!=null){
			System.out.println("Desde StringToArrayList");
			if (cadena.length()>0){
				System.out.println("Desde StringToArrayList 1");
				longitud=cadena.length();
				System.out.println("Desde StringToArrayList 2");
				do{
					System.out.println("Desde StringToArrayList 3");
					if (cadena.lastIndexOf(caracter)>=0){
						itemList.add(cadena.substring(cadena.lastIndexOf(caracter)+1));
						System.out.println("cadena a adicionar-->"+cadena.lastIndexOf(caracter));
						System.out.println("cadena-->"+cadena);						
						cadena=cadena.substring(0,cadena.lastIndexOf(caracter));
					}else{
						itemList.add(cadena);
						break;
					}										
				}while(true);

			}
		}	
		return itemList;		
	}		
}
