// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 08/11/2007 10:26:23
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   TrxVentaPosCVO_Imp.java

package com.grpretail.comfandi.procesosautomaticos.facturaelectronica.vos;

import java.sql.Timestamp;

// Referenced classes of package com.sodimac.bonos.bono:
//            TrxVentaPos_Imp, TrxVentaPosCVO

public class TrxonlineUserDataInDBVO 
    
{
	//String operador;
	//String fechaHoraTrx;
	Long almacen;
	Long terminal;
	Long numTrx;
	//long operator;
	/*Long   monto;
	String EANProveedor;
	String tipoTrx;
	Long numReferencia;
	String fechaValidacion;
	String NITEntidadRecau;
	String fechaVencimiento;*/
	Timestamp fechaHora;
	String data2;
	String data3;
	String data4;
	String data5;
	String data6;
	String data7;
	String data8;
	String data9;
	String data10;
	String data11;
	Long offsetx;

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
	public String getData2() {
		return data2;
	}
	public void setData2(String data2) {
		this.data2 = data2;
	}
	public String getData3() {
		return data3;
	}
	public void setData3(String data3) {
		this.data3 = data3;
	}
	public String getData4() {
		return data4;
	}
	public void setData4(String data4) {
		this.data4 = data4;
	}
	public String getData5() {
		return data5;
	}
	public void setData5(String data5) {
		this.data5 = data5;
	}
	public String getData6() {
		return data6;
	}
	public void setData6(String data6) {
		this.data6 = data6;
	}
	public String getData7() {
		return data7;
	}
	public void setData7(String data7) {
		this.data7 = data7;
	}
	public String getData8() {
		return data8;
	}
	public void setData8(String data8) {
		this.data8 = data8;
	}
	/*
	public long getOperator() {
		return operator;
	}
	public void setOperator(long operator) {
		this.operator = operator;
	}
	*/
	public String getData9() {
		return data9;
	}
	public void setData9(String data9) {
		this.data9 = data9;
	}
	public String getData10() {
		return data10;
	}
	public void setData10(String data10) {
		this.data10 = data10;
	}
	public String getData11() {
		return data11;
	}
	public void setData11(String data11) {
		this.data11 = data11;
	}	
	public Long getOffsetx() {
		return offsetx;
	}
	public void setOffsetx(Long offsetx) {
		this.offsetx = offsetx;
	}	
}