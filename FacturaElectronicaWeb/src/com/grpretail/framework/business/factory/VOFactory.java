/*
 * Creado el Apr 20, 2004
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
package com.grpretail.framework.business.factory;

import com.grpretail.framework.business.exception.ServiceException;


/**
 * DOCUMENT ME!
 *
 * @author ramm Para cambiar la plantilla para este comentario de tipo generado
 *         vaya a Ventana&gt;Preferencias&gt;Java&gt;Generación de
 *         código&gt;Código y comentarios
 */
public interface VOFactory {
    /**
     * Método que retorna un objeto que representa una instancia de una clase
     *
     * @param String className
     *
     * @return Object
     *
     * @throws ServiceException
     */
    public Object getInstance(Class pClass) throws ServiceException;
}
