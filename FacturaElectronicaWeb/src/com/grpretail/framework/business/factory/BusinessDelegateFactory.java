/*
 * Created on 28-abr-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.grpretail.framework.business.factory;

import com.grpretail.framework.business.exception.ServiceException;


/**
 * DOCUMENT ME!
 *
 * @author ramm To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and
 *         Comments
 */
public interface BusinessDelegateFactory {
    /**
     * JDOC Method Def
     *
     * @return DOCUMENT ME!
     *
     * @throws ServiceException DOCUMENT ME!
     */
    public VOFactory createVOBusinessDelegate() throws ServiceException;
}
