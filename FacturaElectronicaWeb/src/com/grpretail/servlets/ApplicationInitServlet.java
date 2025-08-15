/*
 ** Proyecto:
 *
 * Copyriht (C) 2003-2010 Asic S.A. Bogotá, Colombia.
 * * All rights Reserved.
 *
 * $Id: ApplicationInitServlet.java,v 1.1 2024/07/13 00:09:05 Alfonso Exp $
 *
 */
package com.grpretail.servlets;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;


/**
 * JDOC Class Def
 *
 * @author $Author: Alfonso $
 * @version $Revision: 1.1 $
 */
public class ApplicationInitServlet extends HttpServlet implements Servlet {
    //	private static LogHelper log = LogHelper.getInstance(ApplicationInitServlet.class);
//    private ConciliatorExecutor CE;
//	private TaskExecutor periodClossingEx_;

    /**
     * Called by the application server on startup.  Be sure that this servlet
     * is the first one called (startup parameter in web.xml !!)
     *
     * @throws ServletException DOCUMENT ME!
     */
    public void init() throws ServletException {
 		loadApplicationDelegateConfig();
// 		test tst_= new test();
// 		tst_.main(null);
    }

	private void loadApplicationDelegateConfig() throws ServletException {
		   String appConfigFile_ = getServletConfig().getInitParameter("com.grpretail.servlets.properties");

		    // ensure init parameters (as detailed in the web.xml) are present
		    if (appConfigFile_ == null) {
		        throw new ServletException(
		            "No existe ningún parámetro de configuración llamado 'com.grpretail.servlets.properties' en el archivo web.xml");
		    }

//		    try {
//		        Properties properties = new Properties();
//		        properties.load(new java.io.FileInputStream(
//		                getServletContext().getRealPath(appConfigFile_)));
//
//		        // initialize the delegate factory
//		        BusinessDelegateFactoryAdapter.init(properties.getProperty(
//		                "com.grpretail.framework.business.factory"));
//
//		        LogWriter.getLog().log(LogWriter.INFO,
//		            "APPLICATION_INIT_SERVLET - Initialization complete [ok]");
//		        
//		        //APSM: Inicia el valor de la variable empresa a nivel de aplicacion.
//				ServletContext sc_ = getServletContext();
//				ApplicationConfigFacadeAdapter adapter_ = new ApplicationConfigFacadeAdapter();
//				if ( sc_.getAttribute("empresa") == null ){					
//					String valueEmpresa_ = adapter_.get("empresa");
//					sc_.setAttribute("empresa",valueEmpresa_);	
//				}
//				//APSM: Inicia el valor de las variables  variable empresa a nivel de aplicacion.
//				if ( sc_.getAttribute(ApplicationResources.PREFIJO_BONO) == null ){					
//					String valuePrefijoBono_ = adapter_.get(ApplicationResources.PREFIJO_BONO);
//					sc_.setAttribute(ApplicationResources.PREFIJO_BONO,valuePrefijoBono_);	
//				}
//				if ( sc_.getAttribute(ApplicationResources.PROYECTO_BONO) == null ){					
//					String valueProyectoBono_ = adapter_.get(ApplicationResources.PROYECTO_BONO);
//					sc_.setAttribute(ApplicationResources.PROYECTO_BONO,valueProyectoBono_);	
//				}
//				if ( sc_.getAttribute(ApplicationResources.FORMATO_BONO) == null ){					
//					String valueFormatoBono_ = adapter_.get(ApplicationResources.FORMATO_BONO);
//					sc_.setAttribute(ApplicationResources.FORMATO_BONO,valueFormatoBono_);	
//				}
//				if ( sc_.getAttribute(ApplicationResources.NIT) == null ){					
//					String valueNit_ = adapter_.get(ApplicationResources.NIT);
//					sc_.setAttribute(ApplicationResources.NIT,valueNit_);	
//				}
//				//CACS: Carga del proceso de actualización de promociones en las controladoras.
//				//PendingCommandsExecutor PCE = new PendingCommandsExecutor((String)sc_.getAttribute("empresa"));
//				//PCE.setCurrentCompany((String)sc_.getAttribute("empresa"));
//				//PCE.start();
//				
//				periodClossingEx_ = new TaskExecutor();
//				periodClossingEx_.start();
//				
//				//CACS: Carga del proceso de conciliación de archivos de .
//				CE = new ConciliatorExecutor();
//				//E.setCurrentCompany((String)sc_.getAttribute("empresa"));
//				CE.start();							
//		        
//		    } catch (ServiceException e) {
//		        String msg = e.getClass().getName()
//		            + ", on application initialization , message: "
//		            + e.getMessage();
//
//		        LogWriter.getLog().log(LogWriter.WARNING,
//		            "APPLICATION_INIT_SERVLET has failed to start up correctly, message: ServiceException: "
//		            + e.getMessage());
//		        LogWriter.getLog().log(LogWriter.WARNING, msg, e);
//
//		        throw new ServletException(msg, e);
//		    } catch (FileNotFoundException e) {
//		        LogWriter.getLog().log(LogWriter.WARNING,
//		            "APPLICATION_INIT_SERVLET has failed to start up correctly, message: FileNotFoundException: "
//		            + e.getMessage());
//		    } catch (IOException e) {
//		        LogWriter.getLog().log(LogWriter.WARNING,
//		            "APPLICATION_INIT_SERVLET has failed to start up correctly, message: IOException: "
//		            + e.getMessage());
//		    }
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.Servlet#destroy()
	 */
	public void destroy() {
//		CE.setTerminar(true);
//		periodClossingEx_.setTerminar(true);
		super.destroy();
	}

}
