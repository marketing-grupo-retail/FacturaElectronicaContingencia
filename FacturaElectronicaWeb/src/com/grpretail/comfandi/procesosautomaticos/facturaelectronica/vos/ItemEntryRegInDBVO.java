// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 08/11/2007 10:26:23
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   TrxVentaPosCVO_Imp.java

package com.grpretail.comfandi.procesosautomaticos.facturaelectronica.vos;

import java.sql.Timestamp;

// Referenced classes of package com.sodimac.bonos.bono:
//            TrxVentaPos_Imp, TrxVentaPosCVO

public class ItemEntryRegInDBVO {
	//String operador;
	//String fechaHoraTrx;
	long itemCode;
	long almacen;
	long terminal;
	long numTrx;
	long qtyOrWgt;
	long departme;
	long xprice;
	long offsetx;
	long salePrice;

	/*Long   monto;
	String EANProveedor;
	String tipoTrx;
	Long numReferencia;
	String fechaValidacion;
	String NITEntidadRecau;
	String fechaVencimiento;*/
	Timestamp fechaHora;
	String indicat31;  
	String indicat17;  
	String indicat210;
	String indicat211;
	String indicat46;
	
	long VATRate;
	
	String exemptionFlag;
	long consumptionTaxRate;
	String measureUnit;
	long discount;
	
	long consumptionTaxValue;	
	long VATValue;
	
	long taxValue;
	
	long ICUIRate;
	

	/*Date fecha;

	Time hora;
	Long tenderType;
	String periodo;
	*/
	/*
	BusinessLogicParameters.DB_COLUM_SP_STORE+" almacen,"+
	BusinessLogicParameters.DB_COLUM_SP_TERMINAL+" terminal,"+
	BusinessLogicParameters.DB_COLUM_SP_TRX_NUMBER+" transaccion,"+
	BusinessLogicParameters.DB_COLUM_SP_TRX_TIMESTAMP+" fecha,"+
	BusinessLogicParameters.DB_COLUM_SP_PAYMENT_TIME+" hora,"+
	BusinessLogicParameters.DB_COLUM_SP_REFERENCE_NUMBER+" numReferencia,"+
	BusinessLogicParameters.DB_COLUM_SP_BILL_AMOUNT+" monto,"+
	BusinessLogicParameters.DB_COLUM_SP_PERIOD+" periodo,"	
	*/
	//private Double cedulaOperador;	
	//String NumReferencia;
	public long getAlmacen() {
		return almacen;
	}
	public void setAlmacen(long almacen) {
		this.almacen = almacen;
	}
	public long getTerminal() {
		return terminal;
	}
	public void setTerminal(long terminal) {
		this.terminal = terminal;
	}
	public long getNumTrx() {
		return numTrx;
	}
	public void setNumTrx(long numTrx) {
		this.numTrx = numTrx;
	}
	public Timestamp getFechaHora() {
		return fechaHora;
	}
	public void setFechaHora(Timestamp fechaHora) {
		this.fechaHora = fechaHora;
	}
		
	public String getIndicat31() {
		return indicat31;
	}
	public void setIndicat31(String indicat31) {
		this.indicat31 = indicat31;
	}
	public String getIndicat17() {
		return indicat17;
	}
	public void setIndicat17(String indicat17) {
		this.indicat17 = indicat17;
	}
	public String getIndicat210() {
		return indicat210;
	}
	public void setIndicat210(String indicat210) {
		this.indicat210 = indicat210;
	}
	public String getIndicat46() {
		return indicat46;
	}
	public void setIndicat46(String indicat46) {
		this.indicat46 = indicat46;
	} 
	public long getQtyOrWgt() {
		return qtyOrWgt;
	}
	public void setQtyOrWgt(long qtyOrWgt) {
		this.qtyOrWgt = qtyOrWgt;
	}
	public String getIndicat211() {
		return indicat211;
	}
	public void setIndicat211(String indicat211) {
		this.indicat211 = indicat211;
	}
	public long getItemCode() {
		return itemCode;
	}
	public void setItemCode(long itemCode) {
		this.itemCode = itemCode;
	}
	public long getDepartme() {
		return departme;
	}
	public void setDepartme(long departme) {
		this.departme = departme;
	}
	public long getXprice() {
		return xprice;
	}
	public void setXprice(long xprice) {
		this.xprice = xprice;
	}
	public long getOffsetx() {
		return offsetx;
	}
	public void setOffsetx(long offsetx) {
		this.offsetx = offsetx;
	}

	public long getVATValue() {
		return VATValue;
	}
	public void setVATValue(long value) {
		VATValue = value;
	}	
	public long getConsumptionTaxValue() {
		return consumptionTaxValue;
	}
	public void setConsumptionTaxValue(long consumptionValue) {
		this.consumptionTaxValue = consumptionValue;
	}	

	public long getVATRate() {
		return VATRate;
	}
	
	public void setVATRate(long rate) {
		VATRate = rate;
	}	
	
	public String getExemptionFlag() {
		return exemptionFlag;
	}
	
	public void setExemptionFlag(String exemptionFlag) {
		this.exemptionFlag = exemptionFlag;
	}	
	
	public long getConsumptionTaxRate() {
		return consumptionTaxRate;
	}
	
	public void setConsumptionTaxRate(long consumptionTaxRate) {
		this.consumptionTaxRate = consumptionTaxRate;
	}	

	public String getMeasureUnit() {
		return measureUnit;
	}
	
	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}
	
	public long getDiscount() {
		return discount;
	}
	
	public void setDiscount(long discount) {
		this.discount = discount;
	}

	public long getSalePrice() {
		return salePrice;
	}
	
	public void setSalePrice(long salePrice) {
		this.salePrice = salePrice;
	}
	public long getTaxValue() {
		return taxValue;
	}
	public void setTaxValue(long taxValue) {
		this.taxValue = taxValue;
	}	

	public long getICUIRate() {
		return ICUIRate;
	}
	public void setICUIRate(long rate) {
		ICUIRate = rate;
	}	
	
}