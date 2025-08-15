/*
 * Created on 4/01/2011
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.grpretail.business.market.vo;

import java.io.Serializable;

/**
 * @author Alfonso
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LT_TRX_CON_PROBLEMA_Imp extends jdao.data.DataAdv implements Serializable, LT_TRX_CON_PROBLEMA {
	
	public static final String NAME = "LT_TRX_CON_PROBLEMA";
	
	private java.lang.Integer _TCP_ID;
	private java.lang.Integer _TCP_CENTRO_COSTO;
	private java.sql.Timestamp _TCP_FECHA_TRX;
	private java.lang.String _TCP_TRAMA;
	private java.lang.String _TCP_DESC_PROBLEMA;
	private java.lang.String _TCP_NOMBRE_ARCHIVO;
	private java.lang.String _TCP_NOMBRE_CENTRO_COSTO;
	
	public static final String TCP_ID = "TCP_ID";
	public static final String TCP_CENTRO_COSTO = "TCP_CENTRO_COSTO";
	public static final String TCP_FECHA_TRX = "TCP_FECHA_TRX";
	public static final String TCP_TRAMA = "TCP_TRAMA";
	public static final String TCP_DESC_PROBLEMA = "TCP_DESC_PROBLEMA";
	public static final String TCP_NOMBRE_ARCHIVO = "TCP_NOMBRE_ARCHIVO";
	public static final String TCP_NOMBRE_CENTRO_COSTO = "TCP_NOMBRE_CENTRO_COSTO";

	/**
	 * @return
	 */
	public java.lang.Integer getTCP_CENTRO_COSTO() {
		return _TCP_CENTRO_COSTO;
	}

	/**
	 * @return
	 */
	public java.lang.String getTCP_DESC_PROBLEMA() {
		return _TCP_DESC_PROBLEMA;
	}

	/**
	 * @return
	 */
	public java.sql.Timestamp getTCP_FECHA_TRX() {
		return _TCP_FECHA_TRX;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getTCP_ID() {
		return _TCP_ID;
	}

	/**
	 * @return
	 */
	public java.lang.String getTCP_NOMBRE_ARCHIVO() {
		return _TCP_NOMBRE_ARCHIVO;
	}

	/**
	 * @return
	 */
	public java.lang.String getTCP_TRAMA() {
		return _TCP_TRAMA;
	}

	/**
	 * @param integer
	 */
	public void setTCP_CENTRO_COSTO(java.lang.Integer integer) {
		_TCP_CENTRO_COSTO = integer;
	}

	/**
	 * @param string
	 */
	public void setTCP_DESC_PROBLEMA(java.lang.String string) {
		_TCP_DESC_PROBLEMA = string;
	}

	/**
	 * @param timestamp
	 */
	public void setTCP_FECHA_TRX(java.sql.Timestamp timestamp) {
		_TCP_FECHA_TRX = timestamp;
	}

	/**
	 * @param integer
	 */
	public void setTCP_ID(java.lang.Integer integer) {
		_TCP_ID = integer;
	}

	/**
	 * @param string
	 */
	public void setTCP_NOMBRE_ARCHIVO(java.lang.String string) {
		_TCP_NOMBRE_ARCHIVO = string;
	}

	/**
	 * @param string
	 */
	public void setTCP_TRAMA(java.lang.String string) {
		_TCP_TRAMA = string;
	}

	/**
	 * @return
	 */
	public java.lang.String getTCP_NOMBRE_CENTRO_COSTO() {
		return _TCP_NOMBRE_CENTRO_COSTO;
	}

	/**
	 * @param string
	 */
	public void setTCP_NOMBRE_CENTRO_COSTO(java.lang.String string) {
		_TCP_NOMBRE_CENTRO_COSTO = string;
	}

	/* (non-Javadoc)
	 * @see jdao.data.Data#getTableName()
	 */
	public String getTableName() {
		return NAME;
	}

	/* (non-Javadoc)
	 * @see jdao.data.Data#setValueField(java.lang.String, java.lang.Object)
	 */
	/**
	 * Asigna el valor a cada uno de los campos
	 */
	public void setValueField(String field, Object value) { 
	  if(value!=null){
		if (field.equals(TCP_ID)) {
			setTCP_ID((java.lang.Integer) value );
		} else if (field.equals(TCP_CENTRO_COSTO)) {
			setTCP_CENTRO_COSTO((java.lang.Integer) value );
		} else if (field.equals(TCP_FECHA_TRX)) {
			setTCP_FECHA_TRX((java.sql.Timestamp) value );
		} else if (field.equals(TCP_TRAMA)) {
			setTCP_TRAMA((java.lang.String) value );
		} else if (field.equals(TCP_DESC_PROBLEMA)) {
			setTCP_DESC_PROBLEMA((java.lang.String) value );
		} else if (field.equals(TCP_NOMBRE_ARCHIVO)) {
			setTCP_NOMBRE_ARCHIVO((java.lang.String) value );
		} 
	  }
	}

	/* (non-Javadoc)
	 * @see jdao.data.Data#getValueField(java.lang.String)
	 */
	public Object getValueField(String field) {
		if (field.equals(TCP_ID)) {
			return _TCP_ID;
		} else if (field.equals(TCP_CENTRO_COSTO)) {
			return _TCP_CENTRO_COSTO;
		} else if (field.equals(TCP_FECHA_TRX )) {
			return _TCP_FECHA_TRX;
		} else if (field.equals(TCP_TRAMA)) {
			return _TCP_TRAMA;
		} else if (field.equals(TCP_DESC_PROBLEMA)) {
			return _TCP_DESC_PROBLEMA;
		} else if (field.equals(TCP_NOMBRE_ARCHIVO)) {
			return _TCP_NOMBRE_ARCHIVO;
		} else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see jdao.data.Data#start()
	 */
	public void start() {
		String _fields[] = {TCP_ID, TCP_CENTRO_COSTO, TCP_FECHA_TRX, TCP_TRAMA, TCP_DESC_PROBLEMA, TCP_NOMBRE_ARCHIVO};
		fields = _fields;
		String _keyFields[] = {"TCP_ID"};
		keyFields = _keyFields;
		dbProp = false;
		
	}

	/* (non-Javadoc)
	 * @see jdao.data.Data#getSQLFieldType(java.lang.String)
	 */
	public int getSQLFieldType(String field) {
		if (field.equals(TCP_ID)) {
			return java.sql.Types.INTEGER;
		} else if (field.equals(TCP_CENTRO_COSTO)) {
			return java.sql.Types.INTEGER;
		} else if (field.equals(TCP_FECHA_TRX)) {
			return java.sql.Types.TIMESTAMP;
		} else if (field.equals(TCP_TRAMA)) {
			return java.sql.Types.VARCHAR;
		} else if (field.equals(TCP_DESC_PROBLEMA)) {
			return java.sql.Types.VARCHAR;
		} else if (field.equals(TCP_NOMBRE_ARCHIVO)) {
			return java.sql.Types.VARCHAR;
		}

		return 0;
	}

}
