package com.grpretail.comfandi.procesosautomaticos.facturaelectronica.model;

import java.util.ArrayList;
import java.util.List;

import com.grpretail.facturaelectronica.comfandi.mensajeria.vos.ServiceOrderItemVo;

public class ServiceOrderVo_Ori {
	public final static String COMPANY_IDENTIFICATION_TYPE = "NIT";
	String customerId;
	long customerIdType;
	String customerName;
	String customerLastName;
	String email;
	String address;
	String phoneNumber;
	String city;
	String customerUDIdType; // Tipo de documento guardado en el user data de cliente en el POS
	
	/************** Información Fiscal *****************/
	String fiscalFactType; // 02: Contingencia sin conex carvajal, 03: Contingencia con conex

	String fiscalPrefix; // Prefijo
	String fiscalBillNumber; // Número factura fiscal
	String fiscalResolFirstNumber; // Número inicial resolución
	String fiscalResolLastNumber; // Número final resolución
	String fiscalResolNumber; // Número de resolución
	String fiscalResolIniDate; // Fecha inicial resolución
	String fiscalResolEndDate; // Fecha final resolución
	
	
	//String planId;
	String orderCode; // C�digo de orden	
	int itemsCount; // Cantidad de items
	long totalValue; // Valor Total orden

	String store; // Almac�n
	String terminal; // N�mero de terminal
	String transaction; // Trx
	String dateTime;
	String operator;

	List itemsList; // Lista de los productos vendidos en la trx
	
	String deliveryDescription;
	long deliveryValue;
	long discount;
	long totalTrxValue;

	List tendersList; //Lista de medios de pago
	
	boolean smallBillFlag; // Indica si la factura corresponde a una mini factura
	String smallBillCodeOrNumber;
	
	
	public ServiceOrderVo_Ori(){
		customerIdType = 0;
		deliveryDescription = null;
		deliveryValue = 0;
		tendersList = new ArrayList();
		discount = 0;

		customerIdType = 0;
		customerName = "";
		customerLastName = "";
		email = "";
		address = "";
		phoneNumber = "";
		city = "";
		customerUDIdType = "";
		smallBillFlag = false;
		smallBillCodeOrNumber = "";

		fiscalPrefix = "";
		fiscalBillNumber = "";
		fiscalResolFirstNumber = "";
		fiscalResolLastNumber = "";
		fiscalResolNumber = "";
		fiscalResolIniDate = "";
		fiscalResolEndDate = "";		
		
	}
	
	// CACS: Remueve los �tems que fueron completamente devueltos en la trx
	public void removeVoidItems(){
		if (itemsList != null && itemsList.size()>0){
			for (int i=0;i<itemsList.size();i++){
				ServiceOrderItemVo currItm_ = (ServiceOrderItemVo)itemsList.get(i);
				if (currItm_.getCount() <= 0){
					itemsList.remove(i);
					i = -1;
				}	
			}
			itemsCount = itemsList.size(); 
		}		
	}

	public void groupTenders(){
		// Se borran los tender anulados
		if (tendersList != null && tendersList.size()>0){
			for (int i=0;i<tendersList.size();i++){
				ServiceOrderTenderVo_Ori currTend_ = (ServiceOrderTenderVo_Ori)tendersList.get(i);
				if (currTend_.isVoided()){
					tendersList.remove(i);
					i = -1;
				}	
			}
		}
		// Se agrupan por tender Id
		if (tendersList != null && tendersList.size()>0){
			for (int i=0;i<tendersList.size();i++){
				ServiceOrderTenderVo_Ori currTend_ = (ServiceOrderTenderVo_Ori)tendersList.get(i);
				for(int j=i+1;j<tendersList.size();j++){
					ServiceOrderTenderVo_Ori currTend2_ = (ServiceOrderTenderVo_Ori)tendersList.get(j);
					if(currTend_.getTenderId()==currTend2_.getTenderId()){
						currTend_.setTenderAmount(currTend_.getTenderAmount()+currTend2_.getTenderAmount());
						tendersList.remove(j);
						j = j-1;
						//i = -1;
					}
				}
			}
		}		
	}	
	
	public void addTender(ServiceOrderTenderVo_Ori pTenderVo, String pTenderStringType){
		if(pTenderStringType.equals(ServiceOrderTenderVo_Ori.ID_ADD_TENDER)){ // El tender se adiciona
			/*
			boolean tenderAdded_ = false;
			for(int i=0;i<tendersList.size();i++){
				ServiceOrderTenderVo tndVo_ = (ServiceOrderTenderVo)tendersList.get(i);
				if (tndVo_.getTenderAmount()==pTenderVo.getTenderAmount() && !tndVo_.isVoided()){
					// Es el tender que buscamos
					tndVo_.setTenderAmount(tndVo_.getTenderAmount()+pTenderVo.getTenderAmount());
					i = tendersList.size();
					tenderAdded_ = true;
				}else
					System.out.println("No es el tender que buscamos o ya est� anulado");
			}
			if(!tenderAdded_){ 
				System.out.println("Insertando tender");			
				tendersList.add(pTenderVo);
			}*/
			tendersList.add(pTenderVo);
		}else if(pTenderStringType.equals(ServiceOrderTenderVo_Ori.ID_REMOVE_TENDER)){ // El tender se anula
			boolean tenderRemoved_ = false;
			for(int i=0;i<tendersList.size();i++){
				ServiceOrderTenderVo_Ori tndVo_ = (ServiceOrderTenderVo_Ori)tendersList.get(i);
				if (tndVo_.getTenderAmount()==pTenderVo.getTenderAmount() && 
					tndVo_.getTenderId()==pTenderVo.getTenderId() && !tndVo_.isVoided()){
					// Es el tender que buscamos
					tndVo_.setVoided(true);
					i = tendersList.size();
					tenderRemoved_ = true;
				}else
					System.out.println("No es el tender que buscamos o ya est� anulado");
			}
			if(!tenderRemoved_) 
				System.out.println("Tender no se pudo remover. No deberIa pasar");
		}else if(pTenderStringType.equals(ServiceOrderTenderVo_Ori.ID_CHANGE_TENDER)){ // El un cambio
			boolean changeRemoved_ = false;
			for(int i=0;i<tendersList.size();i++){
				ServiceOrderTenderVo_Ori tndVo_ = (ServiceOrderTenderVo_Ori)tendersList.get(i);
				if (!tndVo_.isVoided() && tndVo_.getTenderId() == ServiceOrderTenderVo_Ori.ID_CASH_TENDER_ID){ // Es el efectivo
					System.out.println("Quitando el cambio al efectivo");
					if(tndVo_.getTenderAmount()>pTenderVo.getTenderAmount()){
						tndVo_.setTenderAmount(tndVo_.getTenderAmount()-pTenderVo.getTenderAmount());
						i = tendersList.size();
						changeRemoved_ = true;
					}else
						System.out.println("Vueltas son mayores al monto del efectivo.No deberIa pasar");

				}else
					System.out.println("Tender NO es efectivo o fue anulado");
			}
			if(!changeRemoved_){
				System.out.println("Removiendo cambio de tender diferente a efectivo");
				for(int i=0;i<tendersList.size();i++){
					ServiceOrderTenderVo_Ori tndVo_ = (ServiceOrderTenderVo_Ori)tendersList.get(i);
					if (!tndVo_.isVoided() && tndVo_.getTenderAmount()>pTenderVo.getTenderAmount()){ // El monto del tender es superior al cambio
						System.out.println("Quitando el cambio al efectivo");
						tndVo_.setTenderAmount(tndVo_.getTenderAmount()-pTenderVo.getTenderAmount());
						i = tendersList.size();
					}else
						System.out.println("Vueltas son mayores al monto del efectivo o el tender estA anulado");
				}
			}
		}
	}
	
	public int getItemsCount() {
		return itemsCount;
	}
	public void setItemsCount(int count) {
		this.itemsCount = count;
	}
	public long getTotalValue() {
		return totalValue;
	}
	public void setTotalValue(long totalValue) {
		this.totalValue = totalValue;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public List getItemsList() {
		return itemsList;
	}
	public void setItemsList(List itemsList) {
		this.itemsList = itemsList;
	}

	public void setItemValues(String pItemCode, String pDeparment, long pCount, long pEntryAmount, boolean isWeightItem, long pVATTaxRate, String pExcepmtExcludedFlag, long pConsumptionTaxRate, String pMeasureUnit, long pDiscount, long pUnitPrice, long pICUITaxRate){
		boolean itemFound_ = false;
		if (itemsList == null)
			itemsList = new ArrayList();
		long unitPrice_ = 0;
		if(pCount != 0){
			if (isWeightItem)
				unitPrice_ = pUnitPrice;
			else
				unitPrice_ = pEntryAmount/pCount;
		}
		if (itemsList.size()>0){
			for (int i=0; i<itemsList.size();i++){
				ServiceOrderItemVo currItmVo_ = (ServiceOrderItemVo)itemsList.get(i);
				if (currItmVo_.getPlu().equals(pItemCode) && (currItmVo_.getUnitValue() == unitPrice_/* || isWeightItem*/)){
					System.out.println("Item ya existe en la lista");
					itemFound_ = true;
					currItmVo_.setDepartment(pDeparment);
					currItmVo_.setCount(currItmVo_.getCount() + pCount);
					//currItmVo_.setUnitValue(currItmVo_.getUnitValue() + pEntryAmount);
					if (isWeightItem)
						currItmVo_.setWeightFlag(true);
					else
						currItmVo_.setWeightFlag(false);					
					break;
					/*if (currItmVo_.getUnitValue()==0){
						currItmVo_.setUnitValue(unitValue);
					}else{
						
					}*/
				}
			}
		}
		if (!itemFound_){
			ServiceOrderItemVo itmVo_ = new ServiceOrderItemVo();
			itmVo_.setPlu(pItemCode);
			itmVo_.setDepartment(pDeparment);
			itmVo_.setCount(pCount);
			//itmVo_.setUnitValue(pEntryAmount);
			//if(isWeightItem)
				//itmVo_.setUnitValue(pEntryAmount);
			//else
				itmVo_.setUnitValue(unitPrice_);
			if (isWeightItem)
				itmVo_.setWeightFlag(true);
			else
				itmVo_.setWeightFlag(false);
			
			// Columnas adicionadas a TPOS_ITEMENTRY
			itmVo_.setVATTaxRate(pVATTaxRate);
			
			if (pExcepmtExcludedFlag.equals("1"))
				itmVo_.setExemptedFlag(true);
			else if (pExcepmtExcludedFlag.equals("0"))
				itmVo_.setExcludedFlag(true);
			else
				System.out.println("OpciOn no VAlida");			
			itmVo_.setConsumptionTaxRate(pConsumptionTaxRate);
			itmVo_.setMeasureUnit(pMeasureUnit);
			itmVo_.setDiscountValue(pDiscount);
			itmVo_.setICUITaxRate(pICUITaxRate);
			itemsList.add(itmVo_);
		}	
	}	

	public void setUserDatasItemValues(String pItemCode, String pUnitaryPrice, String pVATTaxRate, String pExcepmtExcludedFlag, String pConsumptionTaxRate, String pMeasureUnit, String pSectionDepartment, String pDiscountValue, String pMovementSignal){
												  // plu_,            description_,     VATTaxRate_,excemptExcludedFlag_,consumTaxRate_, measureUnit, movSign_
		boolean itemFound_ = false;
		if (itemsList == null)
			itemsList = new ArrayList();
		long itmUnitPrice_ = Long.parseLong(pUnitaryPrice);		
		if (itemsList.size()>0){
			for (int i=0; i<itemsList.size();i++){
				ServiceOrderItemVo currItmVo_ = (ServiceOrderItemVo)itemsList.get(i);
				if (currItmVo_.getPlu().equals(pItemCode) && (currItmVo_.getUnitValue() == itmUnitPrice_ || currItmVo_.isWeight())){
					System.out.println("Item ya existe en la lista");
					//currItmVo_.setDescription(pItemDescription);
					currItmVo_.setMeasureUnit(pMeasureUnit);
					currItmVo_.setUnitValue(itmUnitPrice_);
					if (pExcepmtExcludedFlag.equals("01"))
						currItmVo_.setExemptedFlag(true);
					else if (pExcepmtExcludedFlag.equals("00"))
						currItmVo_.setExcludedFlag(true);
					else
						System.out.println("OpciOn no VAlida");
					try{
						int vatTaxRate_ = Integer.parseInt(pVATTaxRate);
						currItmVo_.setVATTaxRate(vatTaxRate_);
					}catch(Exception e){
						e.printStackTrace();
						currItmVo_.setVATTaxRate(0);
					}
					try{
						int consumptionTaxRate_ = Integer.parseInt(pConsumptionTaxRate);
						currItmVo_.setConsumptionTaxRate(consumptionTaxRate_);
					}catch(Exception e){
						e.printStackTrace();
						currItmVo_.setVATTaxRate(0);
					}					
					//currItmVo_.setDepartment(pSectionDepartment);
					
					// Valor de IVA
					try{
						int discountValue_ = Integer.parseInt(pDiscountValue);
						currItmVo_.setDiscountValue(discountValue_);
					}catch(Exception e){
						e.printStackTrace();
						currItmVo_.setDiscountValue(0);
					}					

					
					itemFound_ = true;
					i = itemsList.size();
					//currItmVo_.setCount(currItmVo_.getCount() + pCount);
					//break;
					
					/*if (currItmVo_.getUnitValue()==0){
						currItmVo_.setUnitValue(unitValue);
					}else{
						
					}*/
				}
			}
		}
		
		if (!itemFound_){
			ServiceOrderItemVo itmVo_ = new ServiceOrderItemVo();
			itmVo_.setPlu(pItemCode);
			//itmVo_.setDepartment(pDeparment);
			//itmVo_.setCount(pCount);
			//itmVo_.setDescription(pItemDescription);
			itmVo_.setMeasureUnit(pMeasureUnit);
			if (pExcepmtExcludedFlag.equals("01"))
				itmVo_.setExemptedFlag(true);
			else if (pExcepmtExcludedFlag.equals("00"))
				itmVo_.setExcludedFlag(true);
			else
				System.out.println("OpciOn no VAlida");	
			try{
				int vatTaxRate_ = Integer.parseInt(pVATTaxRate);
				itmVo_.setVATTaxRate(vatTaxRate_);
			}catch(Exception e){
				e.printStackTrace();
				itmVo_.setVATTaxRate(0);
			}
			try{
				int consumptionTaxRate_ = Integer.parseInt(pConsumptionTaxRate);
				itmVo_.setConsumptionTaxRate(consumptionTaxRate_);
			}catch(Exception e){
				e.printStackTrace();
				itmVo_.setConsumptionTaxRate(0);
			}
			itmVo_.setDepartment(pSectionDepartment);
			//itmVo_.setExemptExcluded(pIsExempt);
			//itmVo_.setTaxValue(pVATRate);	
			itmVo_.setUnitValue(itmUnitPrice_);
			itemsList.add(itmVo_);
		}		
	}	
	
	/*public void setUserDatasItemValues(String pItemCode, int pVATRate, String pMovSign, boolean pIsExempt, String pItemDescription){
		boolean itemFound_ = false;
		if (itemsList == null)
			itemsList = new ArrayList();
		if (itemsList.size()>0){
			for (int i=0; i<itemsList.size();i++){
				ServiceOrderItemVo currItmVo_ = (ServiceOrderItemVo)itemsList.get(i);
				if (currItmVo_.getPlu().equals(pItemCode)){
					System.out.println("Item ya existe en la lista");
					currItmVo_.setDescription(pItemDescription);
					currItmVo_.setExemptExcluded(pIsExempt);
					currItmVo_.setTaxValue(pVATRate);
					
					itemFound_ = true;
					//currItmVo_.setCount(currItmVo_.getCount() + pCount);
					//break;
				}
			}
		}
		if (!itemFound_){
			ServiceOrderItemVo itmVo_ = new ServiceOrderItemVo();
			itmVo_.setPlu(pItemCode);
			//itmVo_.setDepartment(pDeparment);
			//itmVo_.setCount(pCount);
			itmVo_.setDescription(pItemDescription);
			itmVo_.setExemptExcluded(pIsExempt);
			itmVo_.setTaxValue(pVATRate);			
			itemsList.add(itmVo_);
		}		
	}*/
	
	public String getTerminal() {
		return terminal;
	}
	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	public String getTransaction() {
		return transaction;
	}
	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}	

	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}	

	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String companyId) {
		this.customerId = companyId;
	}
	/*
	public void setCompanyIdType(String idType) {
		this.companyIdType = idType;
	}
	public String getBeneficiaryId() {
		return beneficiaryId;
	}
	public void setBeneficiaryId(String beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}	

	public String getCompanyIdType() {
		return companyIdType;
	}	

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}	
*/
	public String getDeliveryDescription() {
		return deliveryDescription;
	}

	public void setDeliveryDescription(String deliveryDescription) {
		this.deliveryDescription = deliveryDescription;
	}

	public long getDeliveryValue() {
		return deliveryValue;
	}

	public void setDeliveryValue(long deliveryValue) {
		this.deliveryValue = deliveryValue;
	}	

	public long getDiscount() {
		return discount;
	}

	public void setDiscount(long discount) {
		this.discount = discount;
	}

	public long getCustomerIdType() {
		return customerIdType;
	}

	public void setCustomerIdType(long customerIdType) {
		this.customerIdType = customerIdType;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.trim();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address.trim();
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber.trim();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		city = city;
	}	

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getCustomerUDIdType() {
		return customerUDIdType;
	}

	public void setCustomerUDIdType(String customerUDIdType) {
		this.customerUDIdType = customerUDIdType;
	}
	
	public long getTotalTrxValue() {
		return totalTrxValue;
	}

	public void setTotalTrxValue(long totalTrxValue) {
		this.totalTrxValue = totalTrxValue;
	}	
	public boolean isSmallBill() {
		return smallBillFlag;
	}

	public void setSmallBillFlag(boolean type) {
		this.smallBillFlag = type;
	}	

	public String getSmallBillCodeOrNumber() {
		return smallBillCodeOrNumber;
	}

	public void setSmallBillCodeOrNumber(String smallBillCodeOrNumber) {
		this.smallBillCodeOrNumber = smallBillCodeOrNumber;
	}

	public List getTendersList() {
		return tendersList;
	}

	public void setTendersList(List tendersList) {
		this.tendersList = tendersList;
	}

	public String getFiscalFactType() {
		return fiscalFactType;
	}

	public void setFiscalFactType(String fiscalFactType) {
		this.fiscalFactType = fiscalFactType;
	}

	public String getFiscalPrefix() {
		return fiscalPrefix;
	}

	public void setFiscalPrefix(String fiscalPrefix) {
		this.fiscalPrefix = fiscalPrefix;
	}

	public String getFiscalBillNumber() {
		return fiscalBillNumber;
	}

	public void setFiscalBillNumber(String fiscalBillNumber) {
		this.fiscalBillNumber = fiscalBillNumber;
	}

	public String getFiscalResolFirstNumber() {
		return fiscalResolFirstNumber;
	}

	public void setFiscalResolFirstNumber(String fiscalResolFirstNumber) {
		this.fiscalResolFirstNumber = fiscalResolFirstNumber;
	}

	public String getFiscalResolLastNumber() {
		return fiscalResolLastNumber;
	}

	public void setFiscalResolLastNumber(String fiscalResolLastNumber) {
		this.fiscalResolLastNumber = fiscalResolLastNumber;
	}

	public String getFiscalResolNumber() {
		return fiscalResolNumber;
	}

	public void setFiscalResolNumber(String fiscalResolNumber) {
		this.fiscalResolNumber = fiscalResolNumber;
	}

	public String getFiscalResolIniDate() {
		return fiscalResolIniDate;
	}

	public void setFiscalResolIniDate(String fiscalResolIniDate) {
		this.fiscalResolIniDate = fiscalResolIniDate;
	}

	public String getFiscalResolEndDate() {
		return fiscalResolEndDate;
	}

	public void setFiscalResolEndDate(String fiscalResolEndDate) {
		this.fiscalResolEndDate = fiscalResolEndDate;
	}	
	
}