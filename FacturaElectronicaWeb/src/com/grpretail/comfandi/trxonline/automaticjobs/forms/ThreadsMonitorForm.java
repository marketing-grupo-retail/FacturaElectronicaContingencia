package com.grpretail.comfandi.trxonline.automaticjobs.forms;

import java.awt.Button;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * Form bean for a Struts application.
 * Users may access 9 fields on this form:
 * <ul>
 * <li>btnAdicionar - [your comment here]
 * <li>articulo - [your comment here]
 * <li>idArticulo - [your comment here]
 * <li>btnTender - [your comment here]
 * <li>btnBuscar - [your comment here]
 * <li>deleteButtton - [your comment here]
 * <li>cantidad - [your comment here]
 * <li>chkDeleteRow - [your comment here]
 * <li>btnCustomer - [your comment here]
 * </ul>
 * @version 	1.0
 * @author
 */
public class ThreadsMonitorForm extends ActionForm {

	private boolean reiniciar;
	
	private String estadoTarea1;
	private String fechaEjecucion1;
	private String detalleTarea1;
	private String resultadoEjecucion1;
	private String btnTarea1;
	
	private String estadoTarea2;
	private String fechaEjecucion2;
	private String detalleTarea2;
	private String resultadoEjecucion2;
	private String btnTarea2;
	
	private String estadoTarea3;
	private String fechaEjecucion3;
	private String detalleTarea3;
	private String resultadoEjecucion3;
	private String btnTarea3;
	
	private String estadoTarea4;
	private String fechaEjecucion4;
	private String detalleTarea4;
	private String resultadoEjecucion4;
	private String btnTarea4;
	
		

	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// Reset values are provided as samples only. Change as appropriate.
		
		estadoTarea1 = null;
		fechaEjecucion1 = null;
		detalleTarea1 = null;
		resultadoEjecucion1 = null;
		btnTarea1 = null;

		estadoTarea2 = null;
		fechaEjecucion2 = null;
		detalleTarea2 = null;
		resultadoEjecucion2 = null;
		btnTarea2 = null;

		estadoTarea3 = null;
		fechaEjecucion3 = null;
		detalleTarea3 = null;
		resultadoEjecucion3 = null;
		btnTarea3 = null;

		estadoTarea4 = null;
		fechaEjecucion4 = null;
		detalleTarea4 = null;
		resultadoEjecucion4 = null;
		btnTarea4 = null;
		
				
	}

	public ActionErrors validate(
		ActionMapping mapping,
		HttpServletRequest request) {

		ActionErrors errors = new ActionErrors();
		// Validate the fields in your form, adding
		// adding each error to this.errors as found, e.g.

		// if ((field == null) || (field.length() == 0)) {
		//   errors.add("field", new org.apache.struts.action.ActionError("error.field.required"));
		// }
		return errors;

	}
	
	
	/**
	 * @return
	 */
	public boolean isReiniciar() {
		return reiniciar;
	}

	/**
	 * @return
	 */
	public String getDetalleTarea1() {
		return detalleTarea1;
	}

	/**
	 * @return
	 */
	public String getDetalleTarea2() {
		return detalleTarea2;
	}

	/**
	 * @return
	 */
	public String getDetalleTarea3() {
		return detalleTarea3;
	}

	/**
	 * @return
	 */
	public String getDetalleTarea4() {
		return detalleTarea4;
	}

	/**
	 * @return
	 */
	public String getEstadoTarea1() {
		return estadoTarea1;
	}

	/**
	 * @return
	 */
	public String getEstadoTarea2() {
		return estadoTarea2;
	}

	/**
	 * @return
	 */
	public String getEstadoTarea3() {
		return estadoTarea3;
	}

	/**
	 * @return
	 */
	public String getEstadoTarea4() {
		return estadoTarea4;
	}

	/**
	 * @return
	 */
	public String getFechaEjecucion1() {
		return fechaEjecucion1;
	}

	/**
	 * @return
	 */
	public String getFechaEjecucion2() {
		return fechaEjecucion2;
	}

	/**
	 * @return
	 */
	public String getFechaEjecucion3() {
		return fechaEjecucion3;
	}

	/**
	 * @return
	 */
	public String getFechaEjecucion4() {
		return fechaEjecucion4;
	}

	/**
	 * @return
	 */
	public String getResultadoEjecucion1() {
		return resultadoEjecucion1;
	}

	/**
	 * @return
	 */
	public String getResultadoEjecucion2() {
		return resultadoEjecucion2;
	}

	/**
	 * @return
	 */
	public String getResultadoEjecucion3() {
		return resultadoEjecucion3;
	}

	/**
	 * @return
	 */
	public String getResultadoEjecucion4() {
		return resultadoEjecucion4;
	}

	/**
	 * @param b
	 */
	public void setReiniciar(boolean b) {
		reiniciar = b;
	}


	/**
	 * @param string
	 */
	public void setDetalleTarea1(String string) {
		detalleTarea1 = string;
	}

	/**
	 * @param string
	 */
	public void setDetalleTarea2(String string) {
		detalleTarea2 = string;
	}

	/**
	 * @param string
	 */
	public void setDetalleTarea3(String string) {
		detalleTarea3 = string;
	}

	/**
	 * @param string
	 */
	public void setDetalleTarea4(String string) {
		detalleTarea4 = string;
	}

	/**
	 * @param string
	 */
	public void setEstadoTarea1(String string) {
		estadoTarea1 = string;
	}

	/**
	 * @param string
	 */
	public void setEstadoTarea2(String string) {
		estadoTarea2 = string;
	}

	/**
	 * @param string
	 */
	public void setEstadoTarea3(String string) {
		estadoTarea3 = string;
	}

	/**
	 * @param string
	 */
	public void setEstadoTarea4(String string) {
		estadoTarea4 = string;
	}

	/**
	 * @param string
	 */
	public void setFechaEjecucion1(String string) {
		fechaEjecucion1 = string;
	}

	/**
	 * @param string
	 */
	public void setFechaEjecucion2(String string) {
		fechaEjecucion2 = string;
	}

	/**
	 * @param string
	 */
	public void setFechaEjecucion3(String string) {
		fechaEjecucion3 = string;
	}

	/**
	 * @param string
	 */
	public void setFechaEjecucion4(String string) {
		fechaEjecucion4 = string;
	}

	/**
	 * @param string
	 */
	public void setResultadoEjecucion1(String string) {
		resultadoEjecucion1 = string;
	}

	/**
	 * @param string
	 */
	public void setResultadoEjecucion2(String string) {
		resultadoEjecucion2 = string;
	}

	/**
	 * @param string
	 */
	public void setResultadoEjecucion3(String string) {
		resultadoEjecucion3 = string;
	}

	/**
	 * @param string
	 */
	public void setResultadoEjecucion4(String string) {
		resultadoEjecucion4 = string;
	}

	/**
	 * @return
	 */
	public String getBtnTarea1() {
		return btnTarea1;
	}

	/**
	 * @return
	 */
	public String getBtnTarea2() {
		return btnTarea2;
	}

	/**
	 * @return
	 */
	public String getBtnTarea3() {
		return btnTarea3;
	}

	/**
	 * @return
	 */
	public String getBtnTarea4() {
		return btnTarea4;
	}

	/**
	 * @param string
	 */
	public void setBtnTarea1(String string) {
		btnTarea1 = string;
	}

	/**
	 * @param string
	 */
	public void setBtnTarea2(String string) {
		btnTarea2 = string;
	}

	/**
	 * @param string
	 */
	public void setBtnTarea3(String string) {
		btnTarea3 = string;
	}

	/**
	 * @param string
	 */
	public void setBtnTarea4(String string) {
		btnTarea4 = string;
	}

}
