/*
 * Created on Jul 21, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.grpretail.servlets;

import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.config.ActionConfig;
import org.apache.struts.config.ModuleConfig;

/**
 * @author db2admin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ApplicationMainServlet extends org.apache.struts.action.ActionServlet {

	public static Hashtable hashConfigs;
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		//Place code here to be done when the servlet is initialized
	}

//	public void performServices(
//		HttpServletRequest request,
//		HttpServletResponse response) {
//			//
//	}
//
	/* (no Javadoc)
	 * @see org.ramm.jwaf.controller.AbstractController#processRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void process(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
	
			HttpSession session_ = req.getSession(true);
			if(getActionPath(req).equals("/welcome/login") 
			|| getActionPath(req).equals("/welcome/new_user") 
			|| getActionPath(req).equals("/welcome/registry") ){
				if ( req.getParameter("login") != null ){
					session_.setAttribute("s_login", req.getParameter("login"));
				}
			}
			loadActionsConfig(req, res);
			super.process(req, res);
			

//			RequestHelper helper_ =
//				new RequestHelper(
//					req,
//					res,
//					getServletContext(),
//					getServletConfig());
//
//			performServices(req, res);
//
//			ChainActions chain_ = new ChainActions(helper_);
//			try {
//				ActionForward forward_ = chain_.execActions();
//			} catch (Exception e) {
//				LogWriter.getLog().log(this,LogWriter.WARNING,"processRequest:",e);
//			}
			
	}

	private void loadActionsConfig( HttpServletRequest request, HttpServletResponse response ){
		
		ServletContext sc_ = getServletContext();
		
			
			if ( sc_.getAttribute("sc_actionConfigs") == null ){
				ModuleConfig mc_= getModuleConfig(request);
				ActionConfig[] actCfgs_ = mc_.findActionConfigs();
				sc_.setAttribute("sc_actionConfigs",actCfgs_);	
			}			

	}	
	
	
	//PEGR
	
	private String getActionPath(HttpServletRequest pRequest) {
			String actionPath_ = pRequest.getRequestURI();
			// quita el .do
			actionPath_ = actionPath_.substring(0,actionPath_.length()-3);
			// quita el /Project_Web y queda /directorio/action_path
			actionPath_ = actionPath_.substring(0 + pRequest.getContextPath().length(),actionPath_.length());
			return actionPath_;
		}
}
