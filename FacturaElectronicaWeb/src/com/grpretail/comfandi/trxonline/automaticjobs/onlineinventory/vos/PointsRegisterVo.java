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
public class PointsRegisterVo  implements Serializable, IValueObject {
	Long totalPoints;
	Long redemPoints;
	Long periodPoints;
	Long pointsToExpire;
	Date pointExpirationDate;
	Date startPeriodDate;
	Date endPeriodDate;
	String firstName;
	String lastName;	
	String typeId;
	String customerTypes;

	/**
	 * @return
	 */
	public Date getEndPeriodDate() {
		return endPeriodDate;
	}

	/**
	 * @return
	 */
	public Long getPeriodPoints() {
		return periodPoints;
	}

	/**
	 * @return
	 */
	public Date getPointExpirationDate() {
		return pointExpirationDate;
	}

	/**
	 * @return
	 */
	public Long getPointsToExpire() {
		return pointsToExpire;
	}

	/**
	 * @return
	 */
	public Long getRedemPoints() {
		return redemPoints;
	}

	/**
	 * @return
	 */
	public Date getStartPeriodDate() {
		return startPeriodDate;
	}

	/**
	 * @return
	 */
	public Long getTotalPoints() {
		return totalPoints;
	}

	/**
	 * @param date
	 */
	public void setEndPeriodDate(Date date) {
		endPeriodDate = date;
	}

	/**
	 * @param long1
	 */
	public void setPeriodPoints(Long long1) {
		periodPoints = long1;
	}

	/**
	 * @param date
	 */
	public void setPointExpirationDate(Date date) {
		pointExpirationDate = date;
	}

	/**
	 * @param long1
	 */
	public void setPointsToExpire(Long long1) {
		pointsToExpire = long1;
	}

	/**
	 * @param long1
	 */
	public void setRedemPoints(Long long1) {
		redemPoints = long1;
	}

	/**
	 * @param date
	 */
	public void setStartPeriodDate(Date date) {
		startPeriodDate = date;
	}

	/**
	 * @param long1
	 */
	public void setTotalPoints(Long long1) {
		totalPoints = long1;
	}

	/**
	 * @return
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param string
	 */
	public void setFirstName(String string) {
		firstName = string;
	}

	/**
	 * @param string
	 */
	public void setLastName(String string) {
		lastName = string;
	}

	/**
	 * @return
	 */
	public String getTypeId() {
		return typeId;
	}

	/**
	 * @param string
	 */
	public void setTypeId(String string) {
		typeId = string;
	}

	/**
	 * @return
	 */
	public String getCustomerTypes() {
		return customerTypes;
	}

	/**
	 * @param string
	 */
	public void setCustomerTypes(String string) {
		customerTypes = string;
	}

}
