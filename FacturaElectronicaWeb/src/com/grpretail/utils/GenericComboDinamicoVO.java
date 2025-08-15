/*
 * Creado el Sep 10, 2004
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
package com.grpretail.utils;

import java.io.Serializable;

/**
 * @author hc
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
public class GenericComboDinamicoVO implements Serializable {
	/**
	 *		COLLECTION DE ENTRADA
	 */
	private Object data;
	/**
	 *		NOMBRE DE SCRIPT
	 */
	private String name;
	/**
	 *		NOMBRE DE METHODO HIJO
	 */
	private String nameMethod;
	/**
	 *		ID DEL METHODO HIJO
	 */
	private String idMethodChild;
	/**
	 *		ID DEL METHODO PADRE
	 */
	private String idMethodFather;
	/**
	 *		NOMBRE DEL METHODO QUE SE IMPLEMENTO EN UN DATA
	 */
	private String nameMethodCollectionChilds;	
	/**
	 *		NOMBRE DE TIPO
	 */
	private String typeName;
	/**
	 *		VALUE CUANDO EL TIPO ES Value
	 */
	private String value;
	/**
	 *		SI EL CAMBIO ES A UN OPENER
	 */
	private String opener = "false";
	/**
	 * Selected del combo hijo
	 */
	private String selectedHijo_ = null;
	/**
	 * Indica si es obligatorio elegir un item del combo
	 */
	private String obligatorio_ = null;
	/**
	 * Nombre del combo hijo en la pagina
	 */
	private String nombreComboHijo_ = null;
	/**
	 * Nombre del formulario
	 */
	private String nombreForm_ = "form";

	/**
	 * Method:
	 * Function:
	 * Object
	 * @return
	 */
	public Object getData() {
		return data;
	}

	/**
	 * Method:
	 * Function:
	 * String
	 * @return
	 */
	public String getIdMethodChild() {
		return idMethodChild;
	}

	/**
	 * Method:
	 * Function:
	 * String
	 * @return
	 */
	public String getIdMethodFather() {
		return idMethodFather;
	}

	/**
	 * Method:
	 * Function:
	 * String
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method:
	 * Function:
	 * String
	 * @return
	 */
	public String getNameMethod() {
		return nameMethod;
	}

	/**
	 * Method:
	 * Function:
	 * String
	 * @return
	 */
	public String getNameMethodCollectionChilds() {
		return nameMethodCollectionChilds;
	}

	/**
	 * Method:
	 * Function:
	 * void
	 * @param object
	 */
	public void setData(Object object) {
		data = object;
	}

	/**
	 * Method:
	 * Function:
	 * void
	 * @param string
	 */
	public void setIdMethodChild(String string) {
		idMethodChild = string;
	}

	/**
	 * Method:
	 * Function:
	 * void
	 * @param string
	 */
	public void setIdMethodFather(String string) {
		idMethodFather = string;
	}

	/**
	 * Method:
	 * Function:
	 * void
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

	/**
	 * Method:
	 * Function:
	 * void
	 * @param string
	 */
	public void setNameMethod(String string) {
		nameMethod = string;
	}

	/**
	 * Method:
	 * Function:
	 * void
	 * @param string
	 */
	public void setNameMethodCollectionChilds(String string) {
		nameMethodCollectionChilds = string;
	}

	/**
	 * Method:
	 * Function:
	 * String
	 * @return
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * Method:
	 * Function:
	 * void
	 * @param string
	 */
	public void setTypeName(String string) {
		typeName = string;
	}

	/**
	 * Method:
	 * Function:
	 * String
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Method:
	 * Function:
	 * void
	 * @param string
	 */
	public void setValue(String string) {
		value = string;
	}

	/**
	 * Method:
	 * Function:
	 * String
	 * @return
	 */
	public String getOpener() {
		return opener;
	}

	/**
	 * Method:
	 * Function:
	 * void
	 * @param string
	 */
	public void setOpener(String string) {
		opener = string;
	}

	/**
	 * Method:
	 * Function:
	 * String
	 * @return
	 */
	public String getSelectedHijo_() {
		return selectedHijo_;
	}

	/**
	 * Method:
	 * Function:
	 * void
	 * @param string
	 */
	public void setSelectedHijo_(String string) {
		selectedHijo_ = string;
	}
	
	/**
	 * @return
	 */
	public String getObligatorio_() {
		return obligatorio_;
	}

	/**
	 * @param string
	 */
	public void setObligatorio_(String string) {
		obligatorio_ = string;
	}
	
	/**
	 * @return
	 */
	public String getNombreComboHijo_() {
		return nombreComboHijo_;
	}

	/**
	 * @param string
	 */
	public void setNombreComboHijo_(String string) {
		nombreComboHijo_ = string;
	}
	
	/**
	 * @return
	 */
	public String getNombreForm_() {
		return nombreForm_;
	}

	/**
	 * @param string
	 */
	public void setNombreForm_(String string) {
		nombreForm_ = string;
	}
}
