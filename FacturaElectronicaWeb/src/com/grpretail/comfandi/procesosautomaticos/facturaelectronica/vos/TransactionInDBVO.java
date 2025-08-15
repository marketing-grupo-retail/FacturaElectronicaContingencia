// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 08/11/2007 10:26:23
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   TrxVentaPosCVO_Imp.java

package com.grpretail.comfandi.procesosautomaticos.facturaelectronica.vos;

import java.sql.Timestamp;

// Referenced classes of package com.sodimac.bonos.bono:
//            TrxVentaPos_Imp, TrxVentaPosCVO

public class TransactionInDBVO 
    
{
	//String operador;
	//String fechaHoraTrx;
	Long almacen;
	Long terminal;
	Long numTrx;
	/*Long   monto;
	String EANProveedor;
	String tipoTrx;
	Long numReferencia;
	String fechaValidacion;
	String NITEntidadRecau;
	String fechaVencimiento;*/
	Timestamp fechaHora;
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
		
 



	
}