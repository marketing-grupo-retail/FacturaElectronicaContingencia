package com.grpretail.facturaelectronica.comfandi.business.utils.vos;

import java.io.Serializable;

public class AlmacenVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6878970822435331573L;
	
	private Long idAlmacen;
	private String nombre;
	private String direccion;
	private String nombreMunicipio;
	private Long idMunicipio;
	private String nombreDpto;
	private Long idDepartamento;
	private Long codPostal;
	private String codPais = "CO";
	private String nombrePais = "Colombia";
	
	//TODO Falta idMunicipio, nombre departamento
	
	public Long getIdAlmacen() {
		return idAlmacen;
	}
	public void setIdAlmacen(Long idAlmacen) {
		this.idAlmacen = idAlmacen;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	
	public String getNombreMunicipio() {
		return nombreMunicipio;
	}
	public void setNombreMunicipio(String nombreMunicipio) {
		this.nombreMunicipio = nombreMunicipio.trim();
	}
	public Long getIdMunicipio() {
		return idMunicipio;
	}
	public void setIdMunicipio(Long idMunicipio) {
		this.idMunicipio = idMunicipio;
	}
	public Long getIdDepartamento() {
		return idDepartamento;
	}
	public void setIdDepartamento(Long idDepartamento) {
		this.idDepartamento = idDepartamento;
	}
	public String getCodPais() {
		return codPais;
	}
	public String getNombreDpto() {
		return nombreDpto;
	}
	public void setNombreDpto(String nombreDpto) {
		this.nombreDpto = nombreDpto.trim();
	}
	public String getNombrePais() {
		return nombrePais;
	}
	public Long getCodPostal() {
		return codPostal;
	}
	public void setCodPostal(Long codPostal) {
		this.codPostal = codPostal;
	}

}
