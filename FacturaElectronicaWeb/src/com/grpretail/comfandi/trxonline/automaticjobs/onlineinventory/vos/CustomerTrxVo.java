/*
 * Creado el 24/06/2004
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
package com.grpretail.comfandi.trxonline.automaticjobs.onlineinventory.vos;

import java.io.Serializable;
import java.sql.Date;
/**
 * @author ACadena
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
public class CustomerTrxVo  implements Serializable, IValueObject {
	Long itemsVendidos;
	Date fechaTrx;
	int ultEscalaUtilizada;


	/**
	 * @return
	 */
	public Date getFechaTrx() {
		return fechaTrx;
	}

	/**
	 * @return
	 */
	public Long getItemsVendidos() {
		return itemsVendidos;
	}

	/**
	 * @param date
	 */
	public void setFechaTrx(Date date) {
		fechaTrx = date;
	}

	/**
	 * @param long1
	 */
	public void setItemsVendidos(Long long1) {
		itemsVendidos = long1;
	}

	/**
	 * @return
	 */
	public int getUltEscalaUtilizada() {
		return ultEscalaUtilizada;
	}

	/**
	 * @param i
	 */
	public void setUltEscalaUtilizada(int i) {
		ultEscalaUtilizada = i;
	}

}
