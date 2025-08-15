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
public class CustomerVo  implements Serializable, IValueObject {
	Long id;
	String typeId;
	String firstName;
	String lastName;

	/**
	 * @return
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return
	 */
	public Long getId() {
		return id;
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
	 * @param long1
	 */
	public void setId(Long long1) {
		id = long1;
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
	 * @param long1
	 */
	public void setTypeId(String long1) {
		typeId = long1;
	}

}
