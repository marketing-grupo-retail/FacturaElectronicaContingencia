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
public class CustomerTypesVo  implements Serializable, IValueObject {
	Long id;
	String code;
	String name;
	String description;

	/**
	 * @return
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return
	 */
	public String getDescription() {
		return description;
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
	public String getName() {
		return name;
	}

	/**
	 * @param long1
	 */
	public void setCode(String string) {
		code = string;
	}

	/**
	 * @param string
	 */
	public void setDescription(String string) {
		description = string;
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
	public void setName(String string) {
		name = string;
	}

}
