package com.grpretail.utils;

public class ArrayUtils {
	public static int ASCENDING 	= 0;
	public static int DESCENDING 	= 1;

	public static int[] orderArray(int[] VATRates_, int pOrderType){
		for(int i=0;i<VATRates_.length;i++){
			for(int j=i+1; j<VATRates_.length;j++){
				System.out.println("VATRates_["+i+"]: "+VATRates_[i]+" - "+" VATRates_["+j+"]: "+VATRates_[j] );
				if(pOrderType == DESCENDING){
					if(VATRates_[i]<VATRates_[j]){
						int iTemp_= VATRates_[j];
						VATRates_[j] = VATRates_[i];
						VATRates_[i] = iTemp_;
					}
				}else{
					if(VATRates_[i]<VATRates_[j]){
						int iTemp_= VATRates_[j];
						VATRates_[j] = VATRates_[i];
						VATRates_[i] = iTemp_;
					}
				}
					
			}
		}
		return VATRates_;
	}
}
