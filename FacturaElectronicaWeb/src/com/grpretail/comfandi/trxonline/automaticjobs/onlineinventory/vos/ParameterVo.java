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
public class ParameterVo  implements Serializable, IValueObject {
	int numberReg;
	/**
	 * @return
	 */
	public IValueObject copy(IValueObject vo){
		ParameterVo tmp = (ParameterVo)vo;
		if (vo == null) return this;
		
		setNumberReg(tmp.getNumberReg());
		return this;
	}	
	
	public int getNumberReg() {
		return numberReg;
	}

	/**
	 * @param i
	 */
	public void setNumberReg(int i) {
		numberReg = i;
	}

}
