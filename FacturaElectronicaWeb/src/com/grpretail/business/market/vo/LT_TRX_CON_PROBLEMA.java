/*
 * Created on 4/01/2011
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.grpretail.business.market.vo;


/**
 * @author Alfonso
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface LT_TRX_CON_PROBLEMA {
	/**
	 * @return
	 */
	public abstract java.lang.Integer getTCP_CENTRO_COSTO();
	/**
	 * @return
	 */
	public abstract java.lang.String getTCP_DESC_PROBLEMA();
	/**
	 * @return
	 */
	public abstract java.sql.Timestamp getTCP_FECHA_TRX();
	/**
	 * @return
	 */
	public abstract java.lang.Integer getTCP_ID();
	/**
	 * @return
	 */
	public abstract java.lang.String getTCP_NOMBRE_ARCHIVO();
	/**
	 * @return
	 */
	public abstract java.lang.String getTCP_TRAMA();
	/**
	 * @param integer
	 */
	public abstract void setTCP_CENTRO_COSTO(java.lang.Integer integer);
	/**
	 * @param string
	 */
	public abstract void setTCP_DESC_PROBLEMA(java.lang.String string);
	/**
	 * @param timestamp
	 */
	public abstract void setTCP_FECHA_TRX(java.sql.Timestamp timestamp);
	/**
	 * @param integer
	 */
	public abstract void setTCP_ID(java.lang.Integer integer);
	/**
	 * @param string
	 */
	public abstract void setTCP_NOMBRE_ARCHIVO(java.lang.String string);
	/**
	 * @param string
	 */
	public abstract void setTCP_TRAMA(java.lang.String string);
	/**
	 * @return
	 */
	public java.lang.String getTCP_NOMBRE_CENTRO_COSTO();
	/**
	 * @param string
	 */
	public void setTCP_NOMBRE_CENTRO_COSTO(java.lang.String string);
}