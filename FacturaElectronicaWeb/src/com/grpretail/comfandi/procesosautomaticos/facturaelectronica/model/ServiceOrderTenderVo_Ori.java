package com.grpretail.comfandi.procesosautomaticos.facturaelectronica.model;



public class ServiceOrderTenderVo_Ori {
	public final static String ID_ADD_TENDER 		= "5"; 
	public final static String ID_REMOVE_TENDER  	= "6";
	public final static String ID_CHANGE_TENDER  	= "9";
	
	public final static int ID_CASH_TENDER_ID  	= 11;
	
	
	int tenderId;
	long tenderAmount; // Cantidad vendida del item
	boolean isVoided;
	
	public ServiceOrderTenderVo_Ori (){
		tenderId = 0; 
		tenderAmount = 0;
		isVoided = false;
	}
	
	public int getTenderId() {
		return tenderId;
	}

	public void setTenderId(int tenderId) {
		this.tenderId = tenderId;
	}

	public long getTenderAmount() {
		return tenderAmount;
	}

	public void setTenderAmount(long tenderAmount) {
		this.tenderAmount = tenderAmount;
	}

	public boolean isVoided() {
		return isVoided;
	}

	public void setVoided(boolean isVoided) {
		this.isVoided = isVoided;
	}	
	
}

