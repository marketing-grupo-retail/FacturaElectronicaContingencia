/*
 * Creado el Sep 10, 2004
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
package com.grpretail.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author hc
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
public class GenericComboVO implements Serializable {
	/**
	 *		Collection de entrada
	 */
	private Collection data;
	/**
	 *		Campos del collection que se muestran
	 *		Se introducen los nombres de los 
	 *		métodos get
	 */
	private String fieldToShow;
	/**
	 *		Campos que componen la llave primaria
	 */
	private String fieldPrimaryKey;
	/**
	 *		Fila de la lista que se seleccionará
	 */
	private Object selected =null;
	/**
	 *		Nombre de lista
	 */
	private String name = "";
	/**
	 *		Uso de on change para reenvio de página
	 */
	private String manegeOnchange= "";
	/**
	 *		Otros atributos que debe llevar el combo
	 */
	private String[] otrosAtrib;
	/**
	 *		Nombre de atributo a enviar para datos
	 */
	public final static String DATA_NAME_ATT = "aDataGL";

	/**
	 * Constructor UtilGenericListData.java
	 * 
	 */
	public GenericComboVO() {
		this.data = new ArrayList();
		this.fieldPrimaryKey = "";
		this.fieldToShow = "";
		this.name = "";
		this.selected = "-1";
		this.otrosAtrib = null;
	}

	/**
	 * Constructor UtilGenericList.java
	 * 
	 */
	public GenericComboVO(Collection pData, String pFieldPrimaryKey, String pFieldToShow,
							   String pName, Object pSelected) {
		this.data				= pData;
		this.fieldPrimaryKey	= pFieldPrimaryKey;
		this.fieldToShow		= pFieldToShow;
		this.name				= pName;
		this.selected			= pSelected;
		this.manegeOnchange		= "";
		this.otrosAtrib			= null;
	}

	/**
	 * Constructor UtilGenericList.java
	 * 
	 */
	public GenericComboVO(Collection pData, String pFieldPrimaryKey, String pFieldToShow,
							   String pName, Object pSelected, String[] pOtrosAtrib) {
		this.data				= pData;
		this.fieldPrimaryKey	= pFieldPrimaryKey;
		this.fieldToShow		= pFieldToShow;
		this.name				= pName;
		this.selected			= pSelected;
		this.manegeOnchange		= "";
		this.otrosAtrib			= pOtrosAtrib;
	}

	/**
	 * Method:  
	 * Function:  
	 * @return
	 */
	public Collection getData() {
		return data;
	}

	/**
	 * Method:  
	 * Function:  
	 * @return
	 */
	public String getFieldPrimaryKey() {
		return fieldPrimaryKey;
	}

	/**
	 * Method:  
	 * Function:  
	 * @return
	 */
	public String getFieldToShow() {
		return fieldToShow;
	}

	/**
	 * Method:  
	 * Function:  
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method:  
	 * Function:  
	 * @return
	 */
	public Object getSelected() {
		return selected;
	}

	/**
	 * Method:  
	 * Function:  
	 * @param Collection
	 */
	public void setData(Collection vector) {
		data = vector;
	}

	/**
	 * Method:  
	 * Function:  
	 * @param string
	 */
	public void setFieldPrimaryKey(String string) {
		fieldPrimaryKey = string;
	}

	/**
	 * Method:  
	 * Function:  
	 * @param string
	 */
	public void setFieldToShow(String string) {
		fieldToShow = string;
	}

	/**
	 * Method:  
	 * Function:  
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

	/**
	 * Method:
	 * Function:
	 * String
	 * @return
	 */
	public String getManegeOnchange() {
		return manegeOnchange;
	}

	/**
	 * Method:
	 * Function:
	 * void
	 * @param string
	 */
	public void setManegeOnchange(String string) {
		manegeOnchange = string;
	}
	
	/**
	 * @param object
	 */
	public void setSelected(Object object) {
		selected = object;
	}
	
	/**
	 * @return
	 */
	public String[] getOtrosAtrib() {
		return otrosAtrib;
	}

	/**
	 * @param strings
	 */
	public void setOtrosAtrib(String[] strings) {
		otrosAtrib = strings;
	}
}
