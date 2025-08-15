package com.grpretail.comfandi.business.utils;

public class StringUtils {
	public static void main(String[] args){
		String str_ = "abcdefghijklmnñopqrstuvwxyz1234567890ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
		if (isAlphaNumeric(str_))
			System.out.println("Es alfanumérico");
		else
			System.out.println("NO es alfanumérico");
	}
	
	public static String removeSpecialCharacters(String pStr){
		String answer_ = pStr;
		if(answer_ == null)
			answer_ = "";
		// Remueve comilla doble y la reemplaza por doble comila sencilla
		if(answer_ != null && answer_.length()>0){
			answer_ = answer_.toUpperCase();
			for(int i=0;i<answer_.length();i++){
				String char_ = answer_.substring(i, i+1);
				if(!isAlphaNumeric(char_) && !char_.equals(" ")){
					if(i>0)
						answer_ = answer_.substring(0, i) + " "+answer_.substring(i+1);
					else
						answer_ = " "+answer_.substring(i+1);
				}//else
					//answer_ = answer_.substring(i+1);
				
			}
		}	
		return answer_;		
	}
	
	public static boolean isAlphaNumeric(String pStr){
		boolean answer_ = false;
		if(pStr != null && !pStr.trim().equals("")){
			answer_ = true;
			pStr = pStr.toUpperCase();
			for(int i=0;i<pStr.length();i++){
				//System.out.println(pStr.substring(i, i+1));
				String currChar_ = pStr.substring(i, i+1);
				if(!currChar_.equals("A") && !currChar_.equals("B") && !currChar_.equals("C") &&
				   !currChar_.equals("D") && !currChar_.equals("E") && !currChar_.equals("F") &&
				   !currChar_.equals("G") && !currChar_.equals("H") && !currChar_.equals("I") &&
				   !currChar_.equals("J") && !currChar_.equals("K") && !currChar_.equals("L") &&
				   !currChar_.equals("M") && !currChar_.equals("N") && !currChar_.equals("Ñ") &&
				   !currChar_.equals("O") && !currChar_.equals("P") && !currChar_.equals("Q") &&
				   !currChar_.equals("R") && !currChar_.equals("S") && !currChar_.equals("T") &&
				   !currChar_.equals("U") && !currChar_.equals("V") && !currChar_.equals("W") &&
				   !currChar_.equals("X") && !currChar_.equals("Y") && !currChar_.equals("Z") &&
				   !currChar_.equals("1") && !currChar_.equals("2") && !currChar_.equals("3") &&
				   !currChar_.equals("4") && !currChar_.equals("5") && !currChar_.equals("6") &&
				   !currChar_.equals("7") && !currChar_.equals("8") && !currChar_.equals("9") &&
				   !currChar_.equals("0")){
					return false;
				}
			}
		}	
		return answer_;
	}
	
	public static boolean isValidCommercialStructures(String pStr){
		boolean answer_ = false;
		if(pStr != null && !pStr.trim().equals("")){
			String[] structList_ = pStr.split(",");
			answer_ = true;
			for(int i=0;i<structList_.length;i++){
				//System.out.println(structList_[i]);
				if(structList_[i].trim().length()!=15){
					System.out.println("Formato estructura incorrecto");
					return false;
				}	
			}
		}	
		return answer_;		
	}

	public static boolean isNumeric(String pStr){
		boolean answer_ = false;
		if(pStr != null && !pStr.trim().equals("")){
			answer_ = true;
			pStr = pStr.toUpperCase();
			for(int i=0;i<pStr.length();i++){
				//System.out.println(pStr.substring(i, i+1));
				String currChar_ = pStr.substring(i, i+1);
				if(!currChar_.equals("1") && !currChar_.equals("2") && !currChar_.equals("3") &&
				   !currChar_.equals("4") && !currChar_.equals("5") && !currChar_.equals("6") &&
				   !currChar_.equals("7") && !currChar_.equals("8") && !currChar_.equals("9") &&
				   !currChar_.equals("0")){
					return false;
				}
			}
		}	
		return answer_;
	}	

	public static boolean isValidDate(String pStr, String pFormato){
		boolean answer_ = false;
		if(pFormato.equals("YYYYMMDDHHMISS")){
			if(pStr != null && !pStr.trim().equals("") && pStr.length()==14){
				String strYear_ = pStr.substring(0, 4);
				try{
					int year_ = Integer.parseInt(strYear_);
					if(year_<1900){
						System.out.println("Año debe ser mayor a 1900:"+year_);
						return false;
					}	
				}catch(Exception e){
					return false;
				}
				String strMonth_ = pStr.substring(4, 6);
				try{
					int month_ = Integer.parseInt(strMonth_);
					if(month_<1 || month_>12){
						System.out.println("Mes debe estar entre 1 y 12:"+month_);
						return false;
					}	
				}catch(Exception e){
					return false;
				}			
				String strDay_ = pStr.substring(6, 8);
				try{
					int day_ = Integer.parseInt(strDay_);
					if(day_<1 || day_>31){
						System.out.println("DIa debe estar entre 1 y 31:"+day_);
						return false;
					}	
				}catch(Exception e){
					return false;
				}			
				String strHour_ = pStr.substring(8, 10);
				try{
					int hour_ = Integer.parseInt(strHour_);
					if(hour_<0 || hour_>23){
						System.out.println("Hora debe estar entre 0 y 23:"+hour_);
						return false;
					}	
				}catch(Exception e){
					return false;
				}
				String strMinute_ = pStr.substring(10, 12);
				try{
					int minute_ = Integer.parseInt(strMinute_);
					if(minute_<0 || minute_>59){
						System.out.println("Minuto debe estar entre 0 y 59:"+minute_);
						return false;
					}	
				}catch(Exception e){
					return false;
				}
				String strSecond_ = pStr.substring(12, 14);
				try{
					int second_ = Integer.parseInt(strSecond_);
					if(second_<0 || second_>59){
						System.out.println("Segundo debe estar entre 0 y 59:"+second_);
						return false;
					}	
				}catch(Exception e){
					return false;
				}
				answer_ = true;
			}else
				System.out.println("Fecha a validar es nula");
		}else if(pFormato.equals("YYYYMMDD")){
			if(pStr != null && !pStr.trim().equals("") && pStr.length()==8){
				String strYear_ = pStr.substring(0, 4);
				try{
					int year_ = Integer.parseInt(strYear_);
					if(year_<1900){
						System.out.println("Año debe ser mayor a 1900:"+year_);
						return false;
					}	
				}catch(Exception e){
					return false;
				}
				String strMonth_ = pStr.substring(4, 6);
				try{
					int month_ = Integer.parseInt(strMonth_);
					if(month_<1 || month_>12){
						System.out.println("Mes debe estar entre 1 y 12:"+month_);
						return false;
					}	
				}catch(Exception e){
					return false;
				}			
				String strDay_ = pStr.substring(6, 8);
				try{
					int day_ = Integer.parseInt(strDay_);
					if(day_<1 || day_>31){
						System.out.println("DIa debe estar entre 1 y 31:"+day_);
						return false;
					}	
				}catch(Exception e){
					return false;
				}			
				answer_ = true;
			}else
				System.out.println("Fecha a validar es nula");			
		}
		return answer_;
	}	
	
	public static String removeZeros(String pStringToProcess){
		String answer_ = "";
		if (pStringToProcess==null || pStringToProcess.trim().equals(""))
			return pStringToProcess;
		answer_ = pStringToProcess.trim();
		boolean exit_ = false;
		//for (int i=0;i<pStringToProcess.length();i++){
		while(!exit_){
			if(answer_.startsWith("0") && answer_.length()>1)
				answer_ = answer_.substring(1);
			else
				exit_ = true;
		}
		return answer_;
	}
	
}
