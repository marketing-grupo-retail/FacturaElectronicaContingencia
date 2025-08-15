// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 08/11/2007 10:26:23
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   TrxVentaPosCVO_Imp.java

package com.grpretail.comfandi.trxonline.automaticjobs.onlineinventory.vos;

import java.sql.Timestamp;
import java.util.List;

// Referenced classes of package com.sodimac.bonos.bono:
//            TrxVentaPos_Imp, TrxVentaPosCVO

public class TrxPagoPlanillaVO 
    
{
	String operador;
	String fechaHoraTrx;
	String almacen;
	String terminal;
	String numTrx;
	Long   monto;
	private List mediosPagoList;
	private List facturasList;
	String EANProveedor;
	String tipoTrx;
	String numReferencia;
	String fechaValidacion;
	String NITEntidadRecau;
	String fechaVencimiento;
	Timestamp fechaHoraPago;
	String tenderType;
	String cerror;
	String function;
	String period;
	//private Double cedulaOperador;	
	//String NumReferencia;
		
    public List getMediosPagoList()
    {
        return mediosPagoList;
    }

    public void setMediosPagoList(List mediosPagoList)
    {
        this.mediosPagoList = mediosPagoList;
    }


	/**
	 * @return
	 */
	public String getAlmacen() {
		return almacen;
	}

	/**
	 * @return
	 */
	public List getFacturasList() {
		return facturasList;
	}

	/**
	 * @return
	 */
	public String getFechaHoraTrx() {
		return fechaHoraTrx;
	}

	/**
	 * @return
	 */
	public String getNumTrx() {
		return numTrx;
	}

	/**
	 * @return
	 */
	public String getOperador() {
		return operador;
	}

	/**
	 * @return
	 */
	public String getTerminal() {
		return terminal;
	}

	/**
	 * @param string
	 */
	public void setAlmacen(String string) {
		almacen = string;
	}

	/**
	 * @param list
	 */
	public void setFacturasList(List list) {
		facturasList = list;
	}

	/**
	 * @param string
	 */
	public void setFechaHoraTrx(String string) {
		fechaHoraTrx = string;
	}

	/**
	 * @param string
	 */
	public void setNumTrx(String string) {
		numTrx = string;
	}

	/**
	 * @param string
	 */
	public void setOperador(String string) {
		operador = string;
	}

	/**
	 * @param string
	 */
	public void setTerminal(String string) {
		terminal = string;
	}

	/**
	 * @return
	 */
	public Long getMonto() {
		return monto;
	}

	/**
	 * @param long1
	 */
	public void setMonto(Long long1) {
		monto = long1;
	}

	/**
	 * @return
	 */
	public String getEANProveedor() {
		return EANProveedor;
	}

	/**
	 * @param string
	 */
	public void setEANProveedor(String string) {
		EANProveedor = string;
	}

	/**
	 * @return
	 */
	public String getTipoTrx() {
		return tipoTrx;
	}

	/**
	 * @param string
	 */
	public void setTipoTrx(String string) {
		tipoTrx = string;
	}

	/**
	 * @return
	 */
	public String getNumReferencia() {
		return numReferencia;
	}

	/**
	 * @param string
	 */
	public void setNumReferencia(String string) {
		numReferencia = string;
	}

	/**
	 * @return
	 */
	public String getFechaValidacion() {
		return fechaValidacion;
	}

	/**
	 * @param string
	 */
	public void setFechaValidacion(String string) {
		fechaValidacion = string;
	}

	/**
	 * @return
	 */
	public String getNITEntidadRecau() {
		return NITEntidadRecau;
	}

	/**
	 * @param string
	 */
	public void setNITEntidadRecau(String string) {
		NITEntidadRecau = string;
	}

	/**
	 * @return
	 */
	public String getFechaVencimiento() {
		return fechaVencimiento;
	}

	/**
	 * @param string
	 */
	public void setFechaVencimiento(String string) {
		fechaVencimiento = string;
	}




	/**
	 * @return
	 */
	public Timestamp getFechaHoraPago() {
		return fechaHoraPago;
	}

	/**
	 * @param timestamp
	 */
	public void setFechaHoraPago(Timestamp timestamp) {
		fechaHoraPago = timestamp;
	}

	/**
	 * @return
	 */
	public String getTenderType() {
		return tenderType;
	}

	/**
	 * @param string
	 */
	public void setTenderType(String string) {
		tenderType = string;
	}

	/**
	 * @return
	 */
	public String getCerror() {
		return cerror;
	}

	/**
	 * @param string
	 */
	public void setCerror(String string) {
		cerror = string;
	}

	/**
	 * @return
	 */
	public String getFunction() {
		return function;
	}

	/**
	 * @param string
	 */
	public void setFunction(String string) {
		function = string;
	}

	/**
	 * @return
	 */
	public String getPeriod() {
		return period;
	}

	/**
	 * @param string
	 */
	public void setPeriod(String string) {
		period = string;
	}

}