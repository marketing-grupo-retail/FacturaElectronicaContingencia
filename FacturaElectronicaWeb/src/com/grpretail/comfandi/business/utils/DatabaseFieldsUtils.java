package com.grpretail.comfandi.business.utils;

public class DatabaseFieldsUtils {
	
	// CACS: De un String de un solo caracter retorna un valor númerico
	public static int getNumericValue(String pStr){
		
		int answer_ = -1;
		if(pStr != null && !pStr.trim().equals("") && pStr.length() == 1){
			try{
				answer_ = Integer.parseInt(pStr);
			}catch(NumberFormatException e){
				//e.printStackTrace();
				if(pStr.equals("a"))
					return 10;
				else if (pStr.equals("b"))
					return 11;
				else if (pStr.equals("c"))
					return 12;
				else if (pStr.equals("d"))
					return 13;
				else if (pStr.equals("e"))
					return 14;
				else if (pStr.equals("f"))
					return 15;
				else if (pStr.equals("g"))
					return 16;
				else if (pStr.equals("h"))
					return 17;
				else if (pStr.equals("i"))
					return 18;
				else if (pStr.equals("j"))
					return 19;
				else if (pStr.equals("k"))
					return 20;
				else if (pStr.equals("l"))
					return 21;
				else if (pStr.equals("m"))
					return 22;
				else if (pStr.equals("n"))
					return 23;
				else if (pStr.equals("o"))
					return 24;
				else if (pStr.equals("p"))
					return 25;
				else if (pStr.equals("q"))
					return 26;
				else if (pStr.equals("r"))
					return 27;
				else if (pStr.equals("s"))
					return 28;
				else if (pStr.equals("t"))
					return 29;
				else if (pStr.equals("u"))
					return 30;
				else if (pStr.equals("v"))
					return 31;				
			}
			
			//if(pStr.equals(object)
		}
		return answer_;		
	}
	
	
}
