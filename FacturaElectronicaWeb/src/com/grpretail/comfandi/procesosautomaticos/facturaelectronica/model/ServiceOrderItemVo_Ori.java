package com.grpretail.comfandi.procesosautomaticos.facturaelectronica.model;

import java.util.List;


public class ServiceOrderItemVo_Ori {
	public static String UNITARY_MEASUREMENT_UNIT = "UD"; // Se vende por unidad
	public static String WEIGTH_MEASUREMENT_UNIT = "KG";  // Se vende por peso
	public final static String ID_TEXT_PRODUCT_WITHOUT_DESCRIPTION = "PRODUCTO SIN DESCRIPCION";
	boolean exemptedFlag; // Indica si el �tem es exento
	boolean excludedFlag; // Indica si el �tem es exclu�do

	long count; // Cantidad vendida del item
	//long taxValue; // Valor del impuesto
	long VATTaxRate; // Tasa de impuesto
	long consumptionTaxRate;
	long unitValue; // valor unitario
	String description; // Descripci�n del �tem
	String plu; // Plu del �tem
	String department; // Secci�n a la que pertenece el �tem
	List itemsList;
	String measureUnit;
	boolean weightFlag;
	long discountValue;
	long ICUITaxRate; // Tasa de impuesto ICUI
	
	public ServiceOrderItemVo_Ori (){
		exemptedFlag = false; 
		excludedFlag = false; 
		description = ID_TEXT_PRODUCT_WITHOUT_DESCRIPTION;
		measureUnit = null;
		unitValue = 0;
		department = "0";
		ICUITaxRate = 0;
	}
	
	public boolean isWeight() {
		return weightFlag;
	}
	public void setWeightFlag(boolean weightFlag) {
		this.weightFlag = weightFlag;
	}
	public List getItemsList() {
		return itemsList;
	}
	public void setItemsList(List itemsList) {
		this.itemsList = itemsList;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public long getUnitValue() {
		return unitValue;
	}
	public void setUnitValue(long unitValue) {
		this.unitValue = unitValue;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPlu() {
		return plu;
	}
	public void setPlu(String plu) {
		this.plu = plu;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}

	public String getMeasureUnit() {
		return measureUnit;
	}

	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}

	public boolean isExempted() {
		return exemptedFlag;
	}

	public void setExemptedFlag(boolean exemptedFlag) {
		this.exemptedFlag = exemptedFlag;
	}

	public boolean isExcluded() {
		return excludedFlag;
	}

	public void setExcludedFlag(boolean excludedFlag) {
		this.excludedFlag = excludedFlag;
	}	

	public long getVATTaxRate() {
		return VATTaxRate;
	}

	public void setVATTaxRate(long taxRate) {
		VATTaxRate = taxRate;
	}	

	public long getConsumptionTaxRate() {
		return consumptionTaxRate;
	}

	public void setConsumptionTaxRate(long consumptionTaxRate) {
		this.consumptionTaxRate = consumptionTaxRate;
	}	

	public long getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(long discountValue) {
		this.discountValue = discountValue;
	}	

	public long getICUITaxRate() {
		return ICUITaxRate;
	}

	public void setICUITaxRate(long taxRate) {
		ICUITaxRate = taxRate;
	}
	
}

