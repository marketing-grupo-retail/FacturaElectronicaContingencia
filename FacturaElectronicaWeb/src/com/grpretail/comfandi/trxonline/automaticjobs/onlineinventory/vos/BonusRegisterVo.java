/*
 * Creado el 24/06/2004
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
package com.grpretail.comfandi.trxonline.automaticjobs.onlineinventory.vos;

import java.io.Serializable;
/**
 * @author ACadena
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
public class BonusRegisterVo  implements Serializable, IValueObject {
	Long value2;
	Long balance;
	String status;




	/**
	 * @return
	 */
	public Long getBalance() {
		return balance;
	}



	/**
	 * @return
	 */
	public Long getValue2() {
		return value2;
	}

	/**
	 * @param long1
	 */
	public void setBalance(Long long1) {
		balance = long1;
	}



	/**
	 * @param long1
	 */
	public void setValue2(Long long1) {
		value2 = long1;
	}



	/**
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param string
	 */
	public void setStatus(String string) {
		status = string;
	}

}
