/*
 ** Proyecto:
 *
 * Copyriht (C) 2003-2004 Asic S.A. Bogotá, Colombia.
 * * All rights Reserved.
 *
 * Id:
 *
 */
package com.grpretail.control;

/**
 * @author ramm
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
public class InvalidSessionException extends Exception {

	/**
	 * Excepción lanzada cuando la sesión es invalida.
	 */
	public InvalidSessionException() {
		super("InvalidSessionException");
	}

	public InvalidSessionException(String str_) {
			super(str_);
	}
}
