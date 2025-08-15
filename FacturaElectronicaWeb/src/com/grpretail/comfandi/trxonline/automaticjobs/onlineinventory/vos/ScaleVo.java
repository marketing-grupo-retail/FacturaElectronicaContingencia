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
public class ScaleVo  implements Serializable, IValueObject {
	int disc1;
	int disc2;
	int disc3;
	int disc4;
	int disc5;
	int disc6;
	int beneficDays;
	String restartFlag;
	long monthMaxItem;
	String customerType;
	
	/**
	 * @return
	 */
	public int getBeneficDays() {
		return beneficDays;
	}

	/**
	 * @return
	 */
	public int getDisc1() {
		return disc1;
	}

	/**
	 * @return
	 */
	public int getDisc2() {
		return disc2;
	}

	/**
	 * @return
	 */
	public int getDisc3() {
		return disc3;
	}

	/**
	 * @return
	 */
	public int getDisc4() {
		return disc4;
	}

	/**
	 * @return
	 */
	public int getDisc5() {
		return disc5;
	}

	/**
	 * @return
	 */
	public int getDisc6() {
		return disc6;
	}

	/**
	 * @return
	 */
	public String getRestartFlag() {
		return restartFlag;
	}

	/**
	 * @param i
	 */
	public void setBeneficDays(int i) {
		beneficDays = i;
	}

	/**
	 * @param i
	 */
	public void setDisc1(int i) {
		disc1 = i;
	}

	/**
	 * @param i
	 */
	public void setDisc2(int i) {
		disc2 = i;
	}

	/**
	 * @param i
	 */
	public void setDisc3(int i) {
		disc3 = i;
	}

	/**
	 * @param i
	 */
	public void setDisc4(int i) {
		disc4 = i;
	}

	/**
	 * @param i
	 */
	public void setDisc5(int i) {
		disc5 = i;
	}

	/**
	 * @param i
	 */
	public void setDisc6(int i) {
		disc6 = i;
	}

	/**
	 * @param string
	 */
	public void setRestartFlag(String string) {
		restartFlag = string;
	}

	/**
	 * @return
	 */
	public long getMonthMaxItem() {
		return monthMaxItem;
	}

	/**
	 * @param l
	 */
	public void setMonthMaxItem(long l) {
		monthMaxItem = l;
	}

	/**
	 * @return
	 */
	public String getCustomerType() {
		return customerType;
	}

	/**
	 * @param string
	 */
	public void setCustomerType(String string) {
		customerType = string;
	}

}
