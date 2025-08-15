// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 08/11/2007 10:26:23
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   TrxVentaPosCVO_Imp.java

package com.grpretail.comfandi.procesosautomaticos.facturaelectronica.vos;

import java.sql.Timestamp;

// Referenced classes of package com.sodimac.bonos.bono:
//            TrxVentaPos_Imp, TrxVentaPosCVO

public class DiscountRegInDBVO 
    
{
	//String operador;
	//String fechaHoraTrx;
	Long almacen;
	Long terminal;
	Long numTrx;
	Timestamp fechaHora;
	long discountType;  
	long discountAmount;  
 
	public Long getAlmacen() {
		return almacen;
	}
	public void setAlmacen(Long almacen) {
		this.almacen = almacen;
	}
	public Long getTerminal() {
		return terminal;
	}
	public void setTerminal(Long terminal) {
		this.terminal = terminal;
	}
	public Long getNumTrx() {
		return numTrx;
	}
	public void setNumTrx(Long numTrx) {
		this.numTrx = numTrx;
	}
	public Timestamp getFechaHora() {
		return fechaHora;
	}
	public void setFechaHora(Timestamp fechaHora) {
		this.fechaHora = fechaHora;
	}
	public long getDiscountType() {
		return discountType;
	}
	public void setDiscountType(long discountType) {
		this.discountType = discountType;
	}
	public long getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(long discountAmount) {
		this.discountAmount = discountAmount;
	}
}