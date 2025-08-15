package com.grpretail.comfandi.business.utils;

public class JSONStringUtils {
	
	// CACS: Remueve los caracteres propios del lenguaje JSON
	public static String removeJSONSpecialChars(String pStr){
		String answer_ = pStr;
		if(answer_ == null)
			answer_ = "";		
		// Remueve comilla doble y la reemplaza por doble comila sencilla
		if(answer_ != null && answer_.length()>0){
			int index_ =  answer_.indexOf("\"");
			if(index_>=0)
				answer_ = answer_.substring(0, index_)+"''"+answer_.substring(index_+1);
			index_ =  answer_.indexOf("\"");
			if(index_>=0)
				return removeJSONSpecialChars(answer_);
			// Remueve símbolo {
			if(answer_ != null && answer_.length()>0){
				index_ =  answer_.indexOf("{");
				if(index_>=0)
					answer_ = answer_.substring(0, index_)+answer_.substring(index_+1);
				index_ =  answer_.indexOf("{");
				if(index_>=0)
					return removeJSONSpecialChars(answer_);
				// Remueve símbolo }
				if(answer_ != null && answer_.length()>0){
					index_ =  answer_.indexOf("}");
					if(index_>=0)
						answer_ = answer_.substring(0, index_)+answer_.substring(index_+1);
					index_ =  answer_.indexOf("}");
					if(index_>=0)
						return removeJSONSpecialChars(answer_);
					// Remueve símbolo [
					if(answer_ != null && answer_.length()>0){
						index_ =  answer_.indexOf("[");
						if(index_>=0)
							answer_ = answer_.substring(0, index_)+answer_.substring(index_+1);
						index_ =  answer_.indexOf("[");
						if(index_>=0)
							return removeJSONSpecialChars(answer_);
						// Remueve símbolo ]
						if(answer_ != null && answer_.length()>0){
							index_ =  answer_.indexOf("]");
							if(index_>=0)
								answer_ = answer_.substring(0, index_)+answer_.substring(index_+1);
							index_ =  answer_.indexOf("]");
							if(index_>=0)
								return removeJSONSpecialChars(answer_);
							// Remueve símbolo ,
							if(answer_ != null && answer_.length()>0){
								index_ =  answer_.indexOf(",");
								if(index_>=0)
									answer_ = answer_.substring(0, index_)+answer_.substring(index_+1);
								index_ =  answer_.indexOf(",");
								if(index_>=0)
									return removeJSONSpecialChars(answer_);
								// Remueve símbolo :
								if(answer_ != null && answer_.length()>0){
									index_ =  answer_.indexOf(":");
									if(index_>=0)
										answer_ = answer_.substring(0, index_)+answer_.substring(index_+1);
									index_ =  answer_.indexOf(":");
									if(index_>=0)
										return removeJSONSpecialChars(answer_);
								}
							}	
						}	
					}	
				}	
			}	
		}	
		return answer_;
	}
}
