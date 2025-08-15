/*
 ** Proyecto:
 *
 * Copyriht (C) 2003-2010 Asic S.A. Bogotá, Colombia.
 * * All rights Reserved.
 *
 * $Id: AbstractAction.java,v 1.1 2024/07/13 00:09:06 Alfonso Exp $
 *
 */
package com.grpretail.actions;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * JDOC Class Def
 *
 * @author $Author: Alfonso $
 * @version $Revision: 1.1 $
 */
public abstract class AbstractAction extends Action {
    /**
     * JDOC Method Def
     *
     * @param mapping DOCUMENT ME!
     * @param request DOCUMENT ME!
     * @param errors DOCUMENT ME!
     * @param next DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    protected ActionForward redirect(ActionMapping mapping,
        HttpServletRequest request, ActionErrors errors, String next) {
        ActionForward forward;

        if (!errors.isEmpty()) {
            saveErrors(request, errors);
        }

        // Write logic determining how the user should be forwarded.
        forward = mapping.findForward(next);

        // Finish with
        return (forward);
    }
}
