package com.grpretail.utils;
import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import com.asic.utils.DateFormater;

public class DateFormatter {
	
	private static SimpleDateFormat aFormat = new SimpleDateFormat();
	
	private static DateFormatSymbols aDfs = new DateFormatSymbols();
	
	private static final String[] MONTH_NAMES = 
		{"ENE","FEB","MAR","ABR","MAY","JUN","JUL","AGO","SEP","OCT","NOV","DIC"};
	

	static {
		aDfs.setMonths(MONTH_NAMES);
		aFormat.setDateFormatSymbols(aDfs);
	}

	/*****************************************************************
	* Obtiene la fecha actual en String YYYYMMDD
	*/
	public static String getActualDate() {
		Calendar cal_ = Calendar.getInstance();
		int year_	= cal_.get(Calendar.YEAR);
		int month_	= cal_.get(Calendar.MONTH) + 1;
		int day_	= cal_.get(Calendar.DAY_OF_MONTH);
		return String.valueOf(year_) + 
			(month_	< 10 ? "0" : "") + String.valueOf(month_) + 
			(day_	< 10 ? "0" : "") + String.valueOf(day_);

	}

	/*****************************************************************
	* Obtiene la fecha actual más un periodo en días en String YYYYMMDD
	*/
	public static String getActualDate(int pDays) {
		Calendar cal_ = Calendar.getInstance();
		cal_.add(Calendar.DATE,pDays);
		int year_	= cal_.get(Calendar.YEAR);
		int month_	= cal_.get(Calendar.MONTH) + 1;
		int day_	= cal_.get(Calendar.DAY_OF_MONTH);
		return String.valueOf(year_) + 
			(month_	< 10 ? "0" : "") + String.valueOf(month_) + 
			(day_	< 10 ? "0" : "") + String.valueOf(day_);

	}

	/*****************************************************************
	* Obtiene la fecha actual en String YYYYMMDD
	*/
	public static String getDayOfYear() {
		Calendar cal_ = Calendar.getInstance();
		return Relleno.carga(String.valueOf(cal_.get(Calendar.DAY_OF_YEAR)),3,'0',0,3);
	}


	public static long getActualDateInSeconds() {
		String hour_ =  DateFormatter.getActualHour();

		long test_ = (Long.valueOf(DateFormatter.getActualDate().substring(0,4)).longValue()-2000)*365*86400 + 
									Long.valueOf(DateFormatter.getDayOfYear()).longValue()*86400 +
									Long.valueOf(hour_.substring(0,2)).longValue()*3600 + 
									Long.valueOf(hour_.substring(2,4)).longValue()*60 + 
									Long.valueOf(hour_.substring(4,6)).longValue();

		return test_;
	}

	/*****************************************************************
	* Obtiene la hora actual en String HHMMSS
	*/
	public static String getActualHour() {
		Calendar cal_ = Calendar.getInstance();
		
		int hour_	= cal_.get(Calendar.HOUR_OF_DAY);
		int minute_	= cal_.get(Calendar.MINUTE);
		int second_	= cal_.get(Calendar.SECOND);
		return (hour_ < 10 ? "0" : "") + String.valueOf(hour_) + 
			(minute_ < 10 ? "0" : "") + String.valueOf(minute_) +
			(second_ < 10 ? "0" : "") + String.valueOf(second_);

	}

	/*****************************************************************
	* Obtiene el tiempo actual en milisegundos
	*/
	public static int getActualMils() {
		Calendar cal_ = Calendar.getInstance();
		return cal_.get(Calendar.MILLISECOND);
	}


	/*****************************************************************
	* Obtiene el nombre del mes especificado
	*/
	public static String getMonthName(int pMonth) {
		if(pMonth > 0 && pMonth < 13) {
			return MONTH_NAMES[pMonth-1] ;
		} else {
			return "";
		}
	}
	
	public static String formatoFecha(Date pDate,String pFormat) {
		aFormat.applyPattern(pFormat);
		return aFormat.format(pDate);
	}

	public static String formatoFecha(String pDate,String pFormat) {
		aFormat.applyPattern(pFormat);
		String x = pDate;
		Date temp_ = new Date(Timestamp.valueOf(pDate).getTime());
		x = aFormat.format(temp_);
		return x;
	}

	public static double convertHour(String pHour) {
		StringTokenizer strToken_ = new StringTokenizer(pHour,":");
		int div_ = 1;
		double answer_ = 0;
		while (strToken_.hasMoreTokens()) {
			double parcial_ = 0;
			try {
				parcial_ = Double.valueOf(strToken_.nextToken()).doubleValue();
			} catch (Exception ex) {
			}
			answer_ += (parcial_/div_);
			div_ *= 60;
		}
		return answer_;
	}

	public static String formatoFecha(String pFormat) {
		//20050802010104
		String value_ =pFormat;
		StringBuffer r = new StringBuffer(value_.substring(0,4));
		r.append("-");
		r.append(value_.substring(4,6));
		r.append("-");
		r.append(pFormat.substring(6,8));
		r.append(" ");
		r.append(pFormat.substring(8,10));
		r.append(":");
		r.append(pFormat.substring(10,12));
		r.append(":");
		r.append(pFormat.substring(10,12));
		return r.toString();
	}
	
	public static String getPOSDate(Date pDate){
		SimpleDateFormat format_ = new SimpleDateFormat("yyMMddHHmmss");
		return format_.format(pDate);
	}
	
	public static String getSegMSeg(Date pDate){
		SimpleDateFormat format_ = new SimpleDateFormat("ssS");
		String seg_ = format_.format(pDate);
		seg_ = seg_.length()>=3?seg_.substring(0,3):"000";
		return seg_;
	}
	
	public static String getPOSDate0(){
		String pSeparator = ":";
		Calendar cal_ = Calendar.getInstance();
		 int year_ = cal_.get(1);
		 int month_ = cal_.get(2) + 1;
		 int day_ = cal_.get(5);
		 int hour_ = cal_.getTime().getHours();
		 int minute_ = cal_.getTime().getMinutes();
		 int second_ = cal_.getTime().getSeconds();
		 String strYear_ = year_+"";
		 //StringFormatter.align(year_+"", 2, '0', 1, 2);
		if(strYear_.length()>2)
			strYear_ = strYear_.substring(strYear_.length()-2,strYear_.length());
		else
			strYear_ = StringFormatter.align(strYear_, 2, '0', 0, 2);
		 return strYear_ + StringFormatter.align(month_+"", 2, '0', 0, 2) + StringFormatter.align(day_+"", 2, '0', 0, 2) +
				StringFormatter.align(hour_+"", 2, '0', 0, 2) + StringFormatter.align(minute_+"", 2, '0', 0, 2) + 
				StringFormatter.align(second_+"", 2, '0', 0, 2);	
	}	
	
}

